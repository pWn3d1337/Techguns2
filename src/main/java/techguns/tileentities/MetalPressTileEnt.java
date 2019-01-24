package techguns.tileentities;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraftforge.oredict.OreDictionary;
import techguns.TGItems;
import techguns.TGSounds;
import techguns.gui.ButtonConstants;
import techguns.tileentities.operation.ItemStackHandlerPlus;
import techguns.tileentities.operation.MachineOperation;
import techguns.tileentities.operation.MachineSlotItem;
import techguns.tileentities.operation.MetalPressRecipes;

public class MetalPressTileEnt extends BasicMachineTileEnt {

	protected static final float SOUND_VOLUME=0.5f;
	
	public static final int SLOT_INPUT1=0;
	public static final int SLOT_INPUT2=1;
	public static final int SLOT_OUTPUT=2;
	public static final int SLOT_UPGRADE=3;
	
	public static final int BUTTON_ID_AUTOSPLIT = ButtonConstants.BUTTON_ID_REDSTONE+1;
	
	int minSoundDelay = 0;
	byte autoSlitMode =0;
	
	public static final int POWER_PER_TICK=20;
	
	public MachineSlotItem input1;
	public MachineSlotItem input2;
	
	public MetalPressTileEnt() {
		super(4,true,20000);
		input1 = new MachineSlotItem(this, SLOT_INPUT1);
		input2 = new MachineSlotItem(this, SLOT_INPUT2);
		
		this.inventory = new ItemStackHandlerPlus(4) {

			@Override
			protected void onContentsChanged(int slot) {
				super.onContentsChanged(slot);
				setContentsChanged(true);
			}

			@Override
			protected boolean allowItemInSlot(int slot, ItemStack stack) {
				switch (slot) {
				case SLOT_INPUT1:
				case SLOT_INPUT2:
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
		return new TextComponentTranslation("techguns.container.metalpress", new Object[0]);
	}
	
	@Override
	public void readClientDataFromNBT(NBTTagCompound tags) {
		super.readClientDataFromNBT(tags);
		this.autoSlitMode=tags.getByte("autoSplitMode");
	}



	@Override
	public void writeClientDataToNBT(NBTTagCompound tags) {
		super.writeClientDataToNBT(tags);
		tags.setByte("autoSplitMode",this.autoSlitMode);
	}

	@Override
	protected int getNeededPower() {
		if (this.currentOperation!=null) {
			return POWER_PER_TICK*this.currentOperation.getStackMultiplier();
		}
		return 0;
	}

	protected void splitSlot(MachineSlotItem src, MachineSlotItem target) {
		int amount = src.get().getCount();
		int amount2 = amount/2;
		int amount1 = amount-amount2;
		
		ItemStack targetStack = src.get().copy();
		src.get().setCount(amount1);
		targetStack.setCount(amount2);
		target.setStackInSlot(targetStack);
	}
	
	@Override
	protected void checkAndStartOperation() {
		
		//check for stackSplit
		if (this.autoSlitMode>0) {
			if(!this.input1.get().isEmpty() && this.input2.get().isEmpty() && this.input1.get().getCount()>1) {
				if(!MetalPressRecipes.getOutputFor(this.input1.get(), this.input1.get()).isEmpty()) {
					this.splitSlot(input1, input2);
				}
			} else if (this.input1.get().isEmpty() && !this.input2.get().isEmpty() && this.input2.get().getCount()>1) {
				if(!MetalPressRecipes.getOutputFor(this.input2.get(), this.input2.get()).isEmpty()) {
					this.splitSlot(input2, input1);
				}			
			}
		}
		
		
		ItemStack output = MetalPressRecipes.getOutputFor(input1.get(), input2.get());//AmmoPressBuildPlans.getOutputFor(content[0],content[1], this.buildPlan);//AmmoPressRecipe.isRecipe(input);

		this.setContentsChanged(false);
		
		if (!output.isEmpty() && canOutput(output,SLOT_OUTPUT)){
					
			//check for multistack operation
			ItemStack outputStack = TGItems.newStack(output, output.getCount());
			int maxStack=this.getMaxMachineUpgradeMultiplier(SLOT_UPGRADE);
			
			int originalStacksize = output.getCount();
			int multiplier=1;
			int i;
			for (i=maxStack;i>1;--i){
				outputStack.setCount(output.getCount()*i);

				if (canOutput(outputStack,SLOT_OUTPUT)){
					if(this.input1.get().getCount()>=i && this.input2.get().getCount()>=i){
						multiplier=i;
						break;
					}
				}
				
			}
			
			this.input1.consume(multiplier);
			this.input2.consume(multiplier);
			
			this.progress=0;
			this.totaltime=100;
		
			ItemStack input1 = this.input1.getTypeWithSize(1);
			ItemStack input2 = this.input2.getTypeWithSize(1);
			
			this.currentOperation = new MachineOperation(output, input1,input2);
			this.currentOperation.setStackMultiplier(multiplier);
			
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
			//this.inventory.getStackInSlot(SLOT_OUTPUT).grow(currentOperation.getItemOutput0().getCount());
			this.inventory.insertItemNoCheck(SLOT_OUTPUT, currentOperation.getItemOutput0(), false);
		}
	}

	@Override
	protected void playAmbientSound() {
		float prog = (float)this.progress / (float)this.totaltime;

		int soundTick1 = Math.round((float)this.totaltime*0.075f);
		int halfTime = (Math.round((float)totaltime*0.5f));
		
		if (this.progress == soundTick1 || this.progress == soundTick1+halfTime) {
			world.playSound(this.getPos().getX(), this.getPos().getY(), this.getPos().getZ(), TGSounds.METAL_PRESS_WORK,SoundCategory.BLOCKS, SOUND_VOLUME, 1.0F, true );
		}
	}

	public byte getAutoSplitMode(){
		return this.autoSlitMode;
	}
	
	public void setAutoSplitMode(byte mode){
		this.autoSlitMode=mode;
	}
	
	public boolean isItemValidForSlot(int slot, ItemStack item) {
		
		boolean empty1 = this.input1.get().isEmpty();
		boolean empty2 = this.input2.get().isEmpty();
		
		if (empty1 && empty2) {
			return MetalPressRecipes.hasRecipeUsing(item);
		} else if (empty1 && slot== SLOT_INPUT1) {
			return !MetalPressRecipes.getOutputFor(item, this.input2.get()).isEmpty();
		} else if (empty2 && slot== SLOT_INPUT2) {
			return !MetalPressRecipes.getOutputFor(this.input1.get(),item).isEmpty();
		} else {
			return this.getInventory().getStackInSlot(slot).isItemEqual(item);
		}
	}
	
	public int getValidSlotForItemInMachine(ItemStack item){
		if(!this.input1.get().isEmpty() && OreDictionary.itemMatches(this.input1.get(), item, true)){
			return SLOT_INPUT1;
		} else if (!this.input2.get().isEmpty() && OreDictionary.itemMatches(this.input2.get(), item, true)){
			return SLOT_INPUT2;
		} else if (this.input1.get().isEmpty() && MetalPressRecipes.hasRecipeUsing(item)){
			return SLOT_INPUT1;
		} else if(!this.input1.get().isEmpty() && this.input2.get().isEmpty() && (!MetalPressRecipes.getOutputFor(this.input1.get(),item).isEmpty())){
			return SLOT_INPUT2;
		} else if( !item.isEmpty() && item.getItem()==TGItems.MACHINE_UPGRADE_STACK.getItem() && item.getItemDamage()==TGItems.MACHINE_UPGRADE_STACK.getItemDamage()){
			return SLOT_UPGRADE;
		}
		return -1;
	}
	
	@Override
	public void buttonClicked(int id, EntityPlayer ply, String data) {
		if(id==BUTTON_ID_AUTOSPLIT){
			if (this.isUseableByPlayer(ply)){
				this.changeAutoSplitMode();
			}
		} else {
			super.buttonClicked(id, ply, data);
		}
	}

	protected void changeAutoSplitMode(){
		if(this.autoSlitMode==0){
			this.autoSlitMode=1;
		} else {
			this.autoSlitMode=0;
		}
		this.setContentsChanged(true);
	}
}
