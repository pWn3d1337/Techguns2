package techguns.tileentities;

import net.minecraft.block.BlockHorizontal;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraftforge.fluids.FluidStack;
import techguns.TGItems;
import techguns.TGSounds;
import techguns.Techguns;
import techguns.blocks.machines.BasicMachine;
import techguns.gui.ButtonConstants;
import techguns.tileentities.operation.BlastFurnaceRecipes;
import techguns.tileentities.operation.ChemLabRecipes;
import techguns.tileentities.operation.FabricatorRecipe;
import techguns.tileentities.operation.ItemStackHandlerPlus;
import techguns.tileentities.operation.MachineOperation;
import techguns.tileentities.operation.MachineSlotItem;
import techguns.tileentities.operation.MetalPressRecipes;
import techguns.util.ItemUtil;

public class BlastFurnaceTileEnt extends BasicMachineTileEnt {

	public static final int SLOT_INPUT1=0;
	public static final int SLOT_INPUT2=1;
	public static final int SLOT_OUTPUT=2;
	public static final int SLOT_UPGRADE=3;
	
	public MachineSlotItem input1;
	public MachineSlotItem input2;
	
	public BlastFurnaceTileEnt() {
		super(4, false, 40000);
		
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
		return new TextComponentTranslation(Techguns.MODID+".container.blast_furnace", new Object[0]);
	}


	protected boolean isItemValidForSlot(int slot, ItemStack stack) {
		if(slot==SLOT_INPUT1){
			return BlastFurnaceRecipes.hasRecipeWithInputForSlot(stack,0);
		} else if (slot==SLOT_INPUT2){
			return BlastFurnaceRecipes.hasRecipeWithInputForSlot(stack,1);
		} 
		return false;
	}
	
	
	public int getValidSlotForItemInMachine(ItemStack stack) {
		if(BlastFurnaceRecipes.hasRecipeWithInputForSlot(stack,0)){
			return SLOT_INPUT1;
		}
		if(BlastFurnaceRecipes.hasRecipeWithInputForSlot(stack,1)){
			return SLOT_INPUT2;
		}
		if (TGItems.isMachineUpgrade(stack)) {
			return SLOT_UPGRADE;
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
		
		MachineOperation op = BlastFurnaceRecipes.getOutputFor(this);
		
		this.setContentsChanged(false);
		

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
		
			this.input1.consume(op.getNeededAmountItem(SLOT_INPUT1));
			this.input2.consume(op.getNeededAmountItem(SLOT_INPUT2));
			
			this.currentOperation = op;
			this.progress = 0;
			this.totaltime = op.getTime();

			if (!this.world.isRemote) {
				this.needUpdate();
			}
		}
	}

	protected boolean canOutput(MachineOperation output) {
		if (this.canOutput(output.getItemOutput0(),SLOT_OUTPUT)){
			return true;
		}
		return false;
	}

	protected boolean canConsume(MachineOperation output) {
		int multi = output.getStackMultiplier();
		ItemStack in1 = output.getInputs().get(0);
		ItemStack in2 = output.getInputs().get(1);
		
		return this.input1.canConsumeWithMultiplier(in1,multi) &&
			   this.input2.canConsumeWithMultiplier(in2,multi);
	}
	
	@Override
	protected void finishedOperation() {
		if (this.inventory.getStackInSlot(SLOT_OUTPUT).isEmpty()) {
			this.inventory.setStackInSlot(SLOT_OUTPUT, currentOperation.getItemOutput0());
		} else {
			this.inventory.insertItemNoCheck(SLOT_OUTPUT, currentOperation.getItemOutput0(), false);
		}
	}

	@SuppressWarnings("incomplete-switch")
	@Override
	protected void playAmbientSound() {
		if(this.world.isRemote) {

			if(this.progress%35==0) {
				world.playSound(this.getPos().getX(), this.getPos().getY(), this.getPos().getZ(), SoundEvents.BLOCK_FURNACE_FIRE_CRACKLE,SoundCategory.BLOCKS, 0.75f, 1f, true );
			}
			if (this.progress%20==0) {
				float p = this.world.rand.nextFloat()*0.15f;
				world.playSound(this.getPos().getX(), this.getPos().getY(), this.getPos().getZ(), SoundEvents.BLOCK_FIRE_AMBIENT,SoundCategory.BLOCKS, 0.75f, 0.5f+p, true );
			
		        EnumFacing enumfacing = world.getBlockState(getPos()).getValue(BlockHorizontal.FACING);
		        double d0 = (double)pos.getX() + 0.5D;
		        double d1 = (double)pos.getY() + this.world.rand.nextDouble() * 6.0D / 16.0D + 3.0D/16.0D;
		        double d2 = (double)pos.getZ() + 0.5D;
		        double d3 = 0.52D;
		        double d4 = this.world.rand.nextDouble() * 0.6D - 0.3D;
		
		        switch (enumfacing)
		        {
		            case WEST:
		                world.spawnParticle(EnumParticleTypes.SMOKE_NORMAL, d0 - 0.52D, d1, d2 + d4, 0.0D, 0.0D, 0.0D);
		                world.spawnParticle(EnumParticleTypes.FLAME, d0 - 0.52D, d1, d2 + d4, 0.0D, 0.0D, 0.0D);
		                break;
		            case EAST:
		                world.spawnParticle(EnumParticleTypes.SMOKE_NORMAL, d0 + 0.52D, d1, d2 + d4, 0.0D, 0.0D, 0.0D);
		                world.spawnParticle(EnumParticleTypes.FLAME, d0 + 0.52D, d1, d2 + d4, 0.0D, 0.0D, 0.0D);
		                break;
		            case NORTH:
		                world.spawnParticle(EnumParticleTypes.SMOKE_NORMAL, d0 + d4, d1, d2 - 0.52D, 0.0D, 0.0D, 0.0D);
		                world.spawnParticle(EnumParticleTypes.FLAME, d0 + d4, d1, d2 - 0.52D, 0.0D, 0.0D, 0.0D);
		                break;
		            case SOUTH:
		                world.spawnParticle(EnumParticleTypes.SMOKE_NORMAL, d0 + d4, d1, d2 + 0.52D, 0.0D, 0.0D, 0.0D);
		                world.spawnParticle(EnumParticleTypes.FLAME, d0 + d4, d1, d2 + 0.52D, 0.0D, 0.0D, 0.0D);
		                break;
		        }
			}
		}
	}

	
}
