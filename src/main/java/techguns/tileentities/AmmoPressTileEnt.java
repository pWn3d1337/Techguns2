package techguns.tileentities;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.ISidedInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentTranslation;
import techguns.TGItems;
import techguns.TGSounds;
import techguns.gui.ButtonConstants;
import techguns.tileentities.operation.AmmoPressBuildPlans;
import techguns.tileentities.operation.ItemStackHandlerPlus;
import techguns.tileentities.operation.MachineOperation;
import techguns.tileentities.operation.MachineSlotItem;

public class AmmoPressTileEnt extends BasicMachineTileEnt {

	public static final int SLOT_METAL1=0;
	public static final int SLOT_METAL2=1;
	public static final int SLOT_POWDER=2;
	public static final int SLOT_OUTPUT=3;
	public static final int SLOT_UPGRADE=4;
	
	public static final int BUTTON_ID_NEXT = ButtonConstants.BUTTON_ID_REDSTONE+1;
	public static final int BUTTON_ID_PREV = ButtonConstants.BUTTON_ID_REDSTONE+2;
	
	public MachineSlotItem inputMetal1;
	public MachineSlotItem inputMetal2;
	public MachineSlotItem inputPowder;
	
	public static final int POWER_PER_TICK=5;
	
	public AmmoPressTileEnt() {
		super(5, true, 20000);
		inputMetal1=new MachineSlotItem(this,SLOT_METAL1);
		inputMetal2=new MachineSlotItem(this,SLOT_METAL2);
		inputPowder=new MachineSlotItem(this,SLOT_POWDER);
		
		this.inventory = new ItemStackHandlerPlus(5) {

			@Override
			protected void onContentsChanged(int slot) {
				super.onContentsChanged(slot);
				setContentsChanged(true);
			}

			@Override
			protected boolean allowItemInSlot(int slot, ItemStack stack) {
				switch (slot) {
				case SLOT_METAL1:
					return AmmoPressBuildPlans.isInList(stack, AmmoPressBuildPlans.metal1);
				case SLOT_METAL2:
					return AmmoPressBuildPlans.isInList(stack, AmmoPressBuildPlans.metal2);
				case SLOT_POWDER:
					return AmmoPressBuildPlans.isInList(stack, AmmoPressBuildPlans.powder);
				case SLOT_OUTPUT:
					return false;
				case SLOT_UPGRADE:
					return TGItems.isMachineUpgrade(stack);
				}
				return true;
			}

			@Override
			protected boolean allowExtractFromSlot(int slot, int amount) {
				return slot == SLOT_OUTPUT;
			}
		};
		
	}
	
	public byte buildPlan = 0;
	private final byte maxPlan=3;
	
	@Override
	public void readClientDataFromNBT(NBTTagCompound tags) {
		super.readClientDataFromNBT(tags);
		this.buildPlan=tags.getByte("BuildPlan");
	}

	@Override
	public void writeClientDataToNBT(NBTTagCompound tags) {
		super.writeClientDataToNBT(tags);
		tags.setByte("BuildPlan", this.buildPlan);		
	}
	
	@Override
	public ITextComponent getDisplayName() {
		return new TextComponentTranslation("techguns.container.ammopress", new Object[0]);
	}

	@Override
	public void buttonClicked(int id, EntityPlayer ply){
		if(id<BUTTON_ID_NEXT){
			super.buttonClicked(id, ply);

		} else {
		
			if (this.isUseableByPlayer(ply)){
				//System.out.println("button"+id);
				switch(id){
					case BUTTON_ID_NEXT:
						this.buildPlan++;
						break;
					case BUTTON_ID_PREV:
						this.buildPlan--;
						break;
					}
					if (this.buildPlan>this.maxPlan){
						this.buildPlan=0;
					} else if (this.buildPlan<0) {
						this.buildPlan=this.maxPlan;
					}
					this.needUpdate();
			}
		}
	}

	@Override
	protected int getNeededPower() {
		return POWER_PER_TICK;
	}

	@Override
	protected void checkAndStartOperation() {
		this.setContentsChanged(false);
				
		ItemStack output = AmmoPressBuildPlans.getOutputFor(this.inventory.getStackInSlot(SLOT_METAL1), this.inventory.getStackInSlot(SLOT_METAL2), this.inventory.getStackInSlot(SLOT_POWDER), this.buildPlan);

		if (!output.isEmpty() && canOutput(output, SLOT_OUTPUT)) {
			
			int maxStack=this.getMaxMachineUpgradeMultiplier(SLOT_UPGRADE);
			
			//try higher stacksize
			//int originalStacksize = output.getCount();
			ItemStack outputStack = TGItems.newStack(output, output.getCount());
			int i;
			int multiplier=1;
			for (i=maxStack;i>1;--i){
				outputStack.setCount(output.getCount()*i);
				if (canOutput(outputStack,SLOT_OUTPUT)){
					if (this.inputMetal1.canConsume(i) && this.inputMetal2.canConsume(2*i)&& this.inputPowder.canConsume(i)) {
					//	output=outputStack;
						multiplier=i;
						break;
					}
				}
			}
			ItemStack metal1 = this.inputMetal1.getTypeWithSize(1);
			ItemStack metal2 = this.inputMetal2.getTypeWithSize(2);
			ItemStack powder = this.inputPowder.getTypeWithSize(1);

			this.inputMetal1.consume(multiplier);
			this.inputMetal2.consume(multiplier*2);
			this.inputPowder.consume(multiplier);
			//
			//System.out.println("Starting with stackmultiplier:"+this.stackmultiplier);
			this.currentOperation = new MachineOperation(output, metal1,metal2,powder);
			this.currentOperation.setStackMultiplier(multiplier);
			this.progress = 0;
			this.totaltime = 100;
			
			if (!this.world.isRemote){
				this.needUpdate();
			}
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
		int soundTick1 = Math.round((float)this.totaltime*0.05f);
		int soundTick2 = Math.round((float)this.totaltime*0.30f);
		//System.out.println("T1: "+soundTick1+"; T2: "+soundTick2);
		
		int halfTime = (Math.round((float)totaltime*0.5f));
		
		double x,y,z;
		x=this.pos.getX()+0.5d;
		y=this.pos.getY()+0.5d;
		z=this.pos.getZ()+0.5d;

		if (this.progress == soundTick1 || this.progress == soundTick1+halfTime) {
			world.playSound(x, y, z, TGSounds.AMMO_PRESS_WORK1,SoundCategory.BLOCKS, 1.0F, 1.0F, true );
		}else if (this.progress == soundTick2 || this.progress == soundTick2+halfTime) {
			world.playSound(x, y,z, TGSounds.AMMO_PRESS_WORK2,SoundCategory.BLOCKS, 1.0F, 1.0F, true );
		}
	}
	
	public static int getValidSlotForItemInMachine(ItemStack item){
		if (AmmoPressBuildPlans.isValidFor(item, SLOT_METAL1)){
			return SLOT_METAL1;
		} else if (AmmoPressBuildPlans.isValidFor(item, SLOT_METAL2)){
			return SLOT_METAL2;
		} else if (AmmoPressBuildPlans.isValidFor(item, SLOT_POWDER)){
			return SLOT_POWDER;
		} else if( TGItems.isMachineUpgrade(item)){
			return SLOT_UPGRADE;
		}
		return -1;
	}
	
}
