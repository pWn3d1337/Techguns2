package techguns.tileentities;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraftforge.energy.CapabilityEnergy;
import net.minecraftforge.energy.IEnergyStorage;
import techguns.TGBlocks;
import techguns.TGItems;
import techguns.TGSounds;
import techguns.Techguns;
import techguns.blocks.machines.BasicMachine;
import techguns.blocks.machines.SimpleMachine;
import techguns.tileentities.operation.ChargingStationRecipe;
import techguns.tileentities.operation.ItemStackHandlerPlus;
import techguns.tileentities.operation.MachineOperation;
import techguns.tileentities.operation.MachineSlotItem;

public class ChargingStationTileEnt extends BasicMachineTileEnt {

	public static final int SLOT_INPUT=0;
	public static final int SLOT_OUTPUT=1;
	public static final int SLOT_UPGRADE=2;
	
	protected int lastsound=0;
	
	MachineSlotItem input;
	
	public static final int CHARGERATE=800;
	public static final int ITEMCHARGERATE=1600;
	
	public boolean charging =false;
	
	public ChargingStationTileEnt() {
		super(3, false, 100000);
		input = new MachineSlotItem(this, SLOT_INPUT);

		this.inventory = new ItemStackHandlerPlus(3) {

			@Override
			protected void onContentsChanged(int slot) {
				super.onContentsChanged(slot);
				setContentsChanged(true);
			}

			@Override
			protected boolean allowItemInSlot(int slot, ItemStack stack) {
				switch (slot) {
				case SLOT_INPUT:
					return isItemValidForSlot(slot, stack);
				case SLOT_OUTPUT:
					return false;
				case SLOT_UPGRADE:
					return TGItems.isMachineUpgrade(stack);
				}
				return false;
			}
			
			@Override
			protected boolean allowExtractFromSlot(int slot, int amount) {
				return slot == SLOT_OUTPUT;
			}
		};
		
	}

	@Override
	public ITextComponent getDisplayName() {
		return new TextComponentTranslation(Techguns.MODID+".container.chargingstation", new Object[0]);
	}

	public boolean isItemValidForSlot(int slot, ItemStack item) {
		return slot == this.getValidSlotForItemInMachine(item);
	}

	public int getValidSlotForItemInMachine(ItemStack item) {
		if( TGItems.isMachineUpgrade(item)){
			return SLOT_UPGRADE;
		}
		if(!item.isEmpty() && item.hasCapability(CapabilityEnergy.ENERGY, null)){
			return SLOT_INPUT;
		}
		if (ChargingStationRecipe.getRecipeFor(item)!=null){
			return SLOT_INPUT;
		}
		return -1;
	}

	@Override
	protected int getNeededPower() {
		if(this.currentOperation!=null) {
			return this.currentOperation.getPowerPerTick();
		}
		return 0;
	}

	@Override
	protected void checkAndStartOperation() {
		this.contentsChanged=false;
		if(!this.input.get().isEmpty()) {
			
			ChargingStationRecipe rec = ChargingStationRecipe.getRecipeFor(this.input.get());
			
			if(rec!=null) {
				int stackMultiplier=this.getMaxMachineUpgradeMultiplier(SLOT_UPGRADE);
				
				stackMultiplier = Math.min(stackMultiplier, this.input.get().getCount()/rec.input.stackSize);
				
				stackMultiplier = this.getMaxOutputAmount(rec.output,stackMultiplier);
			
				
				if (stackMultiplier>0) {
					
					ItemStack input = this.input.get().copy();
					input.setCount(rec.input.stackSize);
					MachineOperation op = new MachineOperation(rec.output.copy(), input);
					op.setStackMultiplier(stackMultiplier);
					op.setPowerPerTick(this.getChargeRate());
					
					this.input.consume(rec.input.stackSize*stackMultiplier);
					
					this.currentOperation=op;	
					this.totaltime = (int) ((double)rec.chargeAmount/((double)this.getChargeRate()));
					System.out.println("TotalTime:"+this.totaltime);
					this.progress=0;
					if(!this.world.isRemote) {
						this.needUpdate();
					}
				}
					
			}
			
		}

	}
	
	protected int getChargeRate() {
		return CHARGERATE;
	}
	
	protected int getItemChargeRate() {
		return ITEMCHARGERATE;
	}

	protected int getMaxOutputAmount(ItemStack item, int maxMulti) {
		if (this.inventory.getStackInSlot(SLOT_OUTPUT).isEmpty()){
			return Math.min(item.getMaxStackSize(),item.getCount()*maxMulti);
		} else {
			
			if (this.inventory.getStackInSlot(SLOT_OUTPUT).getItem()==item.getItem() && this.inventory.getStackInSlot(SLOT_OUTPUT).getItemDamage()==item.getItemDamage()){
				int multi=maxMulti;
				while(multi>0 && (inventory.getStackInSlot(SLOT_OUTPUT).getCount() + item.getCount()*multi)>inventory.getStackInSlot(SLOT_OUTPUT).getMaxStackSize()){
					multi--;
				}
				return multi;
			}
			return 0;
		}
	}

	@Override
	protected void finishedOperation() {
		if (this.inventory.getStackInSlot(SLOT_OUTPUT).isEmpty()) {
			this.inventory.setStackInSlot(SLOT_OUTPUT, currentOperation.getItemOutput0());
		} else {
			this.inventory.getStackInSlot(SLOT_OUTPUT).grow(currentOperation.getItemOutput0().getCount());
		}
	}

	@Override
	protected void playAmbientSound() {
		if (this.world.isRemote){
			
			EntityPlayer ply = Techguns.proxy.getPlayerClient();
			Double distSq = this.getDistanceSq(ply.posX, ply.posY, ply.posZ);
			
			if (distSq < 100){

				int blockrot = this.world.getBlockState(this.getPos()).getValue(SimpleMachine.FACING).getHorizontalIndex();
			
				if(blockrot!=2){
					Techguns.proxy.createFX("ChargeFlare2", this.world, this.getPos().getX()+0.5d, this.getPos().getY()+0.5d, this.getPos().getZ()+0.3d, 0,0,0);
				}
				if(blockrot!=0){
					Techguns.proxy.createFX("ChargeFlare2", this.world, this.getPos().getX()+0.5d, this.getPos().getY()+0.5d, this.getPos().getZ()+0.7d, 0,0,0);
				}
				if(blockrot!=1){
					Techguns.proxy.createFX("ChargeFlare2", this.world, this.getPos().getX()+0.3d, this.getPos().getY()+0.5d, this.getPos().getZ()+0.5d, 0,0,0);
				}
				if(blockrot!=3){
					Techguns.proxy.createFX("ChargeFlare2", this.world, this.getPos().getX()+0.7d, this.getPos().getY()+0.5d, this.getPos().getZ()+0.5d, 0,0,0);
				}
			}
			
			if (this.lastsound>0){
				this.lastsound--;
			}
			if (this.lastsound<=0){
				this.lastsound=20;
				world.playSound(this.getPos().getX(), this.getPos().getY(), this.getPos().getZ(), TGSounds.CHARGING_STATION_WORK, SoundCategory.BLOCKS, 1.0F, 1.0F, false );
			}
			
		}
	}
	
	@Override
	public void readClientDataFromNBT(NBTTagCompound tags) {
		super.readClientDataFromNBT(tags);
		this.charging=tags.getBoolean("charging");
	}

	@Override
	public void writeClientDataToNBT(NBTTagCompound tags) {
		super.writeClientDataToNBT(tags);
		tags.setBoolean("charging", this.charging);
	}

	@Override
	public void update() {
		//super.update();
		
		if (this.isRedstoneEnabled()){
						
			if (this.currentOperation!= null) {
				
				if (this.consumePower(this.currentOperation.getPowerPerTick())){
					this.progress++;
					
					playAmbientSound();
					
					if (!this.world.isRemote && progress >= totaltime){
			
						this.finishedOperation();

						this.progress=0;
						this.totaltime=0;
						this.currentOperation=null;
						
						checkAndStartOperation();
						this.needUpdate();
					}
				}
				
			} else if (!this.input.get().isEmpty() && this.input.get().hasCapability(CapabilityEnergy.ENERGY, null)){
				
				
				IEnergyStorage itm = this.input.get().getCapability(CapabilityEnergy.ENERGY, null);
				
				//IEnergyContainerItem itm = (IEnergyContainerItem) this.content[0].getItem();
				
				if(itm.receiveEnergy(this.getItemChargeRate(), true) > 0){
							
					int maxcharge = Math.min(this.energy.getEnergyStored(),this.getItemChargeRate());
									
					int pwr = itm.receiveEnergy(maxcharge, false);
					this.energy.extractEnergy(pwr, false);
					
					if (!this.world.isRemote){
						if(!this.charging){
							this.charging=true;
							this.needUpdate();
						}
					} else {
						playAmbientSound();
					} 
					
				} else {
					if (!this.world.isRemote){
						if(this.inventory.getStackInSlot(SLOT_OUTPUT).isEmpty()){
							this.inventory.setStackInSlot(SLOT_OUTPUT, this.inventory.getStackInSlot(SLOT_INPUT));
							this.inventory.setStackInSlot(SLOT_INPUT, ItemStack.EMPTY);
						}
						this.charging=false;
						this.needUpdate();
					}
				}

			} else {
				if(!this.world.isRemote){
					if(this.charging){
						this.charging=false;
						this.needUpdate();
					}
					if(this.contentsChanged) {
						this.checkAndStartOperation();
					}
				}
			}
		}
	
		
		
	}

	@Override
	protected SimpleMachine getMachineBlockType() {
		return TGBlocks.SIMPLE_MACHINE;
	}

	
}
