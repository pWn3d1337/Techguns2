package techguns.tileentities;

import java.util.Random;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidActionResult;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidTank;
import net.minecraftforge.fluids.FluidUtil;
import net.minecraftforge.fluids.capability.CapabilityFluidHandler;
import net.minecraftforge.fluids.capability.FluidTankPropertiesWrapper;
import net.minecraftforge.fluids.capability.IFluidHandler;
import net.minecraftforge.fluids.capability.IFluidHandlerItem;
import net.minecraftforge.fluids.capability.IFluidTankProperties;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.oredict.OreDictionary;
import techguns.TGItems;
import techguns.TGSounds;
import techguns.Techguns;
import techguns.gui.ButtonConstants;
import techguns.tileentities.operation.ChemLabRecipes;
import techguns.tileentities.operation.FluidTankPlus;
import techguns.tileentities.operation.ITileEntityFluidTanks;
import techguns.tileentities.operation.ItemStackHandlerPlus;
import techguns.tileentities.operation.MachineOperation;
import techguns.tileentities.operation.MachineSlotFluid;
import techguns.tileentities.operation.MachineSlotItem;
import techguns.util.ItemUtil;

public class ChemLabTileEnt extends BasicMachineTileEnt implements ITileEntityFluidTanks{

	private Random rng = new Random();
	
	protected static final float SOUND_VOLUME=0.65f;
	
	public static final int BUTTON_ID_DUMP_INPUT = ButtonConstants.BUTTON_ID_REDSTONE+1;
	public static final int BUTTON_ID_DUMP_OUTPUT = ButtonConstants.BUTTON_ID_REDSTONE+2;
	public static final int BUTTON_ID_TOGGLE_DRAIN = ButtonConstants.BUTTON_ID_REDSTONE+3;	
	
	public ChemLabFluidHandler fluidHandler;
	
	public FluidTank inputTank;
	public FluidTank outputTank;
	
	protected boolean drainInput=false;
	
	public static final int SLOT_INPUT1=0;
	public static final int SLOT_INPUT2=1;
	public static final int SLOT_BOTTLE=2;
	public static final int SLOT_OUTPUT=3;
	public static final int SLOT_UPGRADE=4;
	
	public MachineSlotItem input1;
	public MachineSlotItem input2;
	public MachineSlotItem input_bottle;
	
	public MachineSlotFluid input_fluid;
	
	public static final int CAPACITY_INPUT_TANK=8*Fluid.BUCKET_VOLUME;
	public static final int CAPACITY_OUTPUT_TANK=16*Fluid.BUCKET_VOLUME;
	
	public ChemLabTileEnt() {
		super(5, true, 20000);
		
		this.inputTank = new FluidTankPlus(this,CAPACITY_INPUT_TANK);
		this.inputTank.setTileEntity(this);
		
		this.outputTank = new FluidTankPlus(this,CAPACITY_OUTPUT_TANK);
		this.outputTank.setTileEntity(this);
		
		this.fluidHandler= new ChemLabFluidHandler(this);
		
		input1 = new MachineSlotItem(this, SLOT_INPUT1);
		input2 = new MachineSlotItem(this, SLOT_INPUT2);
		input_bottle = new MachineSlotItem(this, SLOT_BOTTLE);
		input_fluid = new MachineSlotFluid(inputTank);
		
		this.inventory = new ItemStackHandlerPlus(5) {

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
				case SLOT_BOTTLE:
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
		return new TextComponentTranslation(Techguns.MODID+".container.chemlab", new Object[0]);
	}

	@Override
	public boolean hasCapability(Capability<?> capability, EnumFacing facing) {
		return capability == CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY || super.hasCapability(capability, facing);
	}
	
	@Override
	public <T> T getCapability(Capability<T> capability, EnumFacing facing) {
		return capability == CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY ? (T)fluidHandler : super.getCapability(capability, facing);
	}
	
	public boolean isItemValidForSlot(int slot, ItemStack item) {
		if(slot==SLOT_BOTTLE){
			return ChemLabRecipes.allowInFlaskSlot(item);
		} else if(slot==SLOT_INPUT1 && this.input1.get().isEmpty() && this.input2.get().isEmpty()){
			return !ChemLabRecipes.allowInFlaskSlot(item) && ChemLabRecipes.hasRecipeUsing(item);
		} else if (slot==SLOT_INPUT1 && this.input1.get().isEmpty() && !this.input2.get().isEmpty()){
			return !ChemLabRecipes.allowInFlaskSlot(item) && ChemLabRecipes.allowAsInput2(this.input2.get(), item);
		} else if (slot==SLOT_INPUT1 && !this.input1.get().isEmpty()){
			return ItemUtil.isItemEqual(this.input1.get(), item);
		} else if (slot==SLOT_INPUT2 && !this.input1.get().isEmpty()){
			return !ChemLabRecipes.allowInFlaskSlot(item) && ChemLabRecipes.allowAsInput2(this.input1.get(), item);
		} else if (slot==SLOT_INPUT2 && !this.input2.get().isEmpty()){
			return ItemUtil.isItemEqual(this.input2.get(), item);
		}
		
		return false;
	}

	
	@Override
	public void readClientDataFromNBT(NBTTagCompound tags) {
		super.readClientDataFromNBT(tags);
		drainInput=tags.getBoolean("drainInput");
		
		NBTTagCompound inputTankTags = tags.getCompoundTag("inputTank");
		this.inputTank.readFromNBT(inputTankTags);
		
		NBTTagCompound outputTankTags = tags.getCompoundTag("outputTank");
		this.outputTank.readFromNBT(outputTankTags);
	}

	@Override
	public void writeClientDataToNBT(NBTTagCompound tags) {
		super.writeClientDataToNBT(tags);
		tags.setBoolean("drainInput", this.drainInput);
		
		NBTTagCompound inputTankTags = new NBTTagCompound();
		this.inputTank.writeToNBT(inputTankTags);
		tags.setTag("inputTank", inputTankTags);
		
		NBTTagCompound outputTankTags = new NBTTagCompound();
		this.outputTank.writeToNBT(outputTankTags);
		tags.setTag("outputTank", outputTankTags);
	}

	@Override
	protected int getNeededPower() {
		if(this.currentOperation!=null) {
			return this.currentOperation.getPowerPerTick();
		}
		return 0;
	}

	protected boolean canOutput(MachineOperation output) {
		if (this.canOutput(output.getItemOutput0(),SLOT_OUTPUT)){
			// check liquid output
			FluidStack out = output.getFluidOutput0();
			if (out==null) {
				return true;
			} else {
				return this.outputTank.canFillFluidType(out) && this.outputTank.fill(out, false)==out.amount;
			}
		}
		return false;
	}
	
	protected boolean canConsume(MachineOperation output) {
		int multi = output.getStackMultiplier();
		ItemStack in1 = output.getInputs().get(0);
		ItemStack in2 = output.getInputs().get(1);
		ItemStack bottle = output.getInputs().get(2);
		
		FluidStack fluidIn =null;
		int amount=0;
		if (!output.getFluid_inputs().isEmpty()) {
			fluidIn = output.getFluid_inputs().get(0);
			if (fluidIn!=null) {
				amount=fluidIn.amount;
			}
		}
		
		return this.input1.canConsumeWithMultiplier(in1,multi) &&
			   this.input2.canConsumeWithMultiplier(in2,multi) &&
			   this.input_bottle.canConsumeWithMultiplier(bottle,multi) && 
			   this.input_fluid.canConsume(amount*multi);
	}
	
	protected void consume(MachineOperation output) {
		this.input1.consume(output.getNeededAmountItem(SLOT_INPUT1));
		this.input2.consume(output.getNeededAmountItem(SLOT_INPUT2));
		this.input_bottle.consume(output.getNeededAmountItem(SLOT_BOTTLE));
		
		this.input_fluid.consume(output.getNeededAmountFluid(0));
	}
	
	@Override
	protected void checkAndStartOperation() {
		this.setContentsChanged(false);
		MachineOperation op = ChemLabRecipes.getOutputFor(this);
	
		if (op != null && canOutput(op)) {
			
			//check multiplier
			
			int maxStack=this.getMaxMachineUpgradeMultiplier(SLOT_UPGRADE);
			
			int multiplier=1;
			//try higher stacksize
			int i;
			for (i=maxStack;i>1;--i){
				op.setStackMultiplier(i);
				if(this.canOutput(op) && canConsume(op)){
					multiplier=i;
					break;
				}
				
			}
			
			op.setStackMultiplier(multiplier);
			
			//drain
			this.consume(op);
			
			this.currentOperation = op;
			this.progress = 0;
			this.totaltime = 100;

			if (!this.world.isRemote) {
				this.needUpdate();
			}
		}
	}

	@Override
	protected void finishedOperation() {

		ItemStack itemOut = this.currentOperation.getItemOutput0();
		if (!itemOut.isEmpty()) {
			if (!this.inventory.getStackInSlot(SLOT_OUTPUT).isEmpty()) {
				this.inventory.insertItemNoCheck(SLOT_OUTPUT, itemOut, false);
				//this.inventory.getStackInSlot(SLOT_OUTPUT).grow(itemOut.getCount());
			} else {
				this.inventory.setStackInSlot(SLOT_OUTPUT, itemOut);
			}
		}
		
		FluidStack fluidOut = this.currentOperation.getFluidOutput0();
		if(fluidOut!=null) {
			this.outputTank.fillInternal(fluidOut, true);
		}

	}

	@Override
	protected void playAmbientSound() {
		int soundTick1 = 1;
		int halfTime = (Math.round((float) totaltime * 0.5f));
		if (this.progress == soundTick1 || this.progress == soundTick1 + halfTime) {
			//worldObj.playSound(this.xCoord, this.yCoord, this.zCoord, "techguns:machines.chemlabWork", 1.0F, 1.0F, true);
			world.playSound(this.getPos().getX(), this.getPos().getY(), this.getPos().getZ(), TGSounds.CHEM_LAB_WORK,SoundCategory.BLOCKS, SOUND_VOLUME, 1.0F, true );
		}

		if ( this.world.isRemote) {
			int delay = 10;
			if (this.progress % delay == 0) {
				world.spawnParticle(EnumParticleTypes.SPELL, this.getPos().getX()+0.5d + rng.nextFloat(), this.getPos().getY()+0.5d + rng.nextFloat(), this.getPos().getZ()+0.5d + rng.nextFloat(), 0, 1, 0);
			}
		}

	}
	
	public FluidStack getCurrentInputFluid() {
		return this.inputTank.getFluid();
	}
	
	public FluidStack getCurrentOutputFluid() {
		return this.outputTank.getFluid();
	}
	
	

	public int getValidSlotForItemInMachine(ItemStack item) {
		if (ChemLabRecipes.allowInFlaskSlot(item)) {
			if (this.input_bottle.get().isEmpty()) {
				return SLOT_BOTTLE;
			} else if (OreDictionary.itemMatches(this.input_bottle.get(), item, true)) {
				return SLOT_BOTTLE;
			}
		} else if (!this.input1.get().isEmpty() && OreDictionary.itemMatches(this.input1.get(), item, true)) {
			return SLOT_INPUT1;
		} else if (!this.input2.get().isEmpty() && OreDictionary.itemMatches(this.input2.get(), item, true)) {
			return SLOT_INPUT2;
		} else if (this.input1.get().isEmpty() && ChemLabRecipes.hasRecipeUsing(item)) {
			return SLOT_INPUT1;
		} else if (!this.input1.get().isEmpty() && this.input2.get().isEmpty() && (ChemLabRecipes.allowAsInput2(this.input1.get(), item))) {
			return SLOT_INPUT2;
		} else if (TGItems.isMachineUpgrade(item)) {
			return SLOT_UPGRADE;
		}
		return -1;
	}

	@Override
	public void saveTanksToNBT(NBTTagCompound tags) {
		NBTTagCompound inputTankTags = new NBTTagCompound();
		this.inputTank.writeToNBT(inputTankTags);
		tags.setTag("inputTank", inputTankTags);
		
		NBTTagCompound outputTankTags = new NBTTagCompound();
		this.outputTank.writeToNBT(outputTankTags);
		tags.setTag("outputTank", outputTankTags);
	}

	@Override
	public void loadTanksFromNBT(NBTTagCompound tags) {
		NBTTagCompound inputTank = tags.getCompoundTag("inputTank");
		this.inputTank.readFromNBT(inputTank);
		
		NBTTagCompound outputTank = tags.getCompoundTag("outputTank");
		this.outputTank.readFromNBT(outputTank);
	}

	public byte getDrainMode() {
		return (byte) (this.drainInput?1:0);
	}
	
	@Override
	public void buttonClicked(int id, EntityPlayer ply, String data) {
		if(id<BUTTON_ID_DUMP_INPUT){
			super.buttonClicked(id, ply, data);
		} else {
			if (this.isUseableByPlayer(ply)){
				switch(id){
					case BUTTON_ID_DUMP_INPUT: //drain input
						this.inputTank.setFluid(null);
						this.needUpdate();
						break;
					case BUTTON_ID_DUMP_OUTPUT: //drain output
						this.outputTank.setFluid(null);
						this.needUpdate();
						break;
					case BUTTON_ID_TOGGLE_DRAIN: //toogle drain
						this.drainInput=!this.drainInput;
						this.needUpdate();
						break;
				}
			}
		}
	}
	
	public static class ChemLabFluidHandler implements IFluidHandler {

		private ChemLabTileEnt tile;
		protected IFluidTankProperties[] tankProperties;
		
		public ChemLabFluidHandler(ChemLabTileEnt tile) {
			super();
			this.tile = tile;
		}

		@Override
		public IFluidTankProperties[] getTankProperties() {
			if ( tankProperties == null) {
				this.tankProperties =  new IFluidTankProperties[]{new FluidTankPropertiesWrapper(tile.inputTank), new FluidTankPropertiesWrapper(tile.outputTank)};
			}
			return tankProperties;
		}

		@Override
		public int fill(FluidStack resource, boolean doFill) {
			return tile.inputTank.fill(resource, doFill);
		}

		@Override
		public FluidStack drain(FluidStack resource, boolean doDrain) {
			if(!tile.drainInput) {
				return tile.outputTank.drain(resource, doDrain);
			} else {
				return tile.inputTank.drain(resource, doDrain);
			}
		}

		@Override
		public FluidStack drain(int maxDrain, boolean doDrain) {
			if(!tile.drainInput) {
				return tile.outputTank.drain(maxDrain, doDrain);
			} else {
				return tile.inputTank.drain(maxDrain, doDrain);
			}
		}
	}

	@Override
	public boolean onFluidContainerInteract(EntityPlayer player, EnumHand hand, IFluidHandlerItem fluidhandleritem, ItemStack stack) {
		if(!this.isUseableByPlayer(player)) return false;
		
		boolean interacted = false;
		
		if(this.drainInput) {
			interacted = FluidUtil.interactWithFluidHandler(player, hand, this.inputTank);
		} else {
			
			IItemHandler playerInventory = player.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, null);
            if (playerInventory != null)
            {
                FluidActionResult fluidActionResult = FluidUtil.tryFillContainerAndStow(stack, this.outputTank, playerInventory, Integer.MAX_VALUE, player,true);
                if (!fluidActionResult.isSuccess())
                {
                    fluidActionResult = FluidUtil.tryEmptyContainerAndStow(stack, this.inputTank, playerInventory, Integer.MAX_VALUE, player, true);
                }

                if (fluidActionResult.isSuccess())
                {
                    player.setHeldItem(hand, fluidActionResult.getResult());
                    interacted=true;
                }
            }	
			
		}
		
		return interacted;

	}
	
}
