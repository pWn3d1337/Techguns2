package techguns.tileentities;

import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ITickable;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import techguns.TGItems;
import techguns.tileentities.operation.MachineOperation;


public abstract class BasicMachineTileEnt extends BasicPoweredTileEnt implements ITickable {

	public MachineOperation currentOperation = null;
	public int totaltime=0;
	public int progress = 0;
	
	public BasicMachineTileEnt(int inventorySize, boolean hasRotation, int maximumPower) {
		super(inventorySize, hasRotation, maximumPower);
	}
	
	 @Override
	public void readClientDataFromNBT(NBTTagCompound tags) {
		super.readClientDataFromNBT(tags);
		this.totaltime=tags.getInteger("totaltime");
		this.progress=tags.getInteger("progress");
		
		this.readOperationFromNBT(tags);
	}

	@Override
	public void writeClientDataToNBT(NBTTagCompound tags) {
		super.writeClientDataToNBT(tags);
		tags.setInteger("totaltime", this.totaltime);
		tags.setInteger("progress", this.progress);
		
		if(this.currentOperation!=null) {
			this.currentOperation.writeToNBT(tags);
		}
	}

	public void readOperationFromNBT(NBTTagCompound tags) {
		if(tags.hasKey("operation")) {
			this.currentOperation=new MachineOperation(null, null, null,null, 0);
			this.currentOperation.readFromNBT(tags);
		} else {
			this.currentOperation=null;
		}
	}
	
	/**
     * Returns an integer between 0 and the passed value representing how close the current item is to being completely
     * cooked
     */
    @SideOnly(Side.CLIENT)
    public int getProgressScaled(int limit)
    {
    	if (totaltime!=0){
    		return this.progress * limit / this.totaltime;
    	} else {
    		return 0;
    	}
    }
    
    /**
     * Returns an integer between 0 and the passed value representing how close the current item is to being completely
     * cooked
     */
    @SideOnly(Side.CLIENT)
    public int getPowerScaled(int limit)
    {
    	if (totaltime!=0){
    		return this.energy.getMaxEnergyStored() * limit / this.energy.getMaxEnergyStored();
    	} else {
    		return 0;
    	}
    }
    
    @SideOnly(Side.CLIENT)
    public float getProgress() {
    	if(this.totaltime>0){
    		return (float)this.progress / (float)this.totaltime;
    	}
    	return 0.0f;
    }
    
    public boolean isWorking(){
    	return this.currentOperation!=null;
    }
    
    public ItemStack onFluidContainerInteract(ItemStack fluidContainer){
    	return fluidContainer;
    }

    protected abstract int getNeededPower();
    
    public int getMaxMachineUpgradeMultiplier(int slot) {
    	ItemStack upg = this.inventory.getStackInSlot(slot);
    	if (!upg.isEmpty() && upg.getItem() == TGItems.SHARED_ITEM && upg.getItemDamage() == TGItems.MACHINE_UPGRADE_STACK.getItemDamage()) {
    		return 1+upg.getCount();
    	}
    	return 1;
    }
    
    /**
     * used by multiblocks to disable machine when broken
     * @return
     */
    protected boolean enabled(){
    	return true;
    }
        
	@Override
	public void update() {
		if (this.isRedstoneEnabled()&&this.enabled()){
			if (this.currentOperation != null) {

				if (this.consumePower(this.getNeededPower()*this.currentOperation.getStackMultiplier())) {
					this.progress++;

					playAmbientSound();

					if (progress >= totaltime) {

						if (!this.world.isRemote) {
							this.finishedOperation();
						}
						this.progress = 0;
						this.totaltime = 0;
						this.currentOperation = null;

						if (!this.world.isRemote){
							checkAndStartOperation();
							this.needUpdate();
						}
					}
				}

			}  else {

				if(!this.world.isRemote && this.contentsChanged) {
					//System.out.println("CHECK AND START OPERATION");
					checkAndStartOperation();
				}

			}
		}
		
	}
	protected abstract void checkAndStartOperation();

	protected abstract void finishedOperation();

	protected abstract void playAmbientSound();

}
