package techguns.tileentities;

import net.minecraft.block.Block;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentTranslation;
import techguns.TGBlocks;
import techguns.TGItems;
import techguns.TGSounds;
import techguns.Techguns;
import techguns.blocks.machines.MultiBlockMachine;
import techguns.tileentities.operation.FabricatorRecipe;
import techguns.tileentities.operation.FabricatorRecipe.RecipeData;
import techguns.tileentities.operation.ItemStackHandlerPlus;
import techguns.tileentities.operation.MachineSlotItem;

public class FabricatorTileEntMaster extends MultiBlockMachineTileEntMaster {

	protected static final float SOUND_VOLUME=0.5f;
	
	public static final int SLOT_INPUT1 =0;
	public static final int SLOT_WIRES=1;
	public static final int SLOT_POWDER=2;
	public static final int SLOT_PLATE=3;
	public static final int SLOT_OUTPUT=4;
	public static final int SLOT_UPGRADE=5;
	
	
	public MachineSlotItem input;
	public MachineSlotItem wireslot;
	public MachineSlotItem powderslot;
	public MachineSlotItem plateslot;
	
	public static final int powerPerTick=80;
	
	public FabricatorTileEntMaster() {
		super(6, 100000);
		
		input = new MachineSlotItem(this, SLOT_INPUT1);
		wireslot = new MachineSlotItem(this, SLOT_WIRES);
		powderslot = new MachineSlotItem(this, SLOT_POWDER);
		plateslot = new MachineSlotItem(this, SLOT_PLATE);
		
		this.inventory = new ItemStackHandlerPlus(6) {

			@Override
			protected void onContentsChanged(int slot) {
				super.onContentsChanged(slot);
				setContentsChanged(true);
			}

			@Override
			protected boolean allowItemInSlot(int slot, ItemStack stack) {
				switch (slot) {
				case SLOT_INPUT1:
				case SLOT_WIRES:
				case SLOT_POWDER:
				case SLOT_PLATE:
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

	public boolean isItemValidForSlot(int slot, ItemStack stack) {
		return this.getValidSlotForItemInMachine(stack)==slot;
	}
	
	@Override
	public ITextComponent getDisplayName() {
		return new TextComponentTranslation(Techguns.MODID+".container.fabricator", new Object[0]);
	}

	@Override
	public int getNeededPower() {
		return powerPerTick;
	}

	
	
	@Override
	public AxisAlignedBB getRenderBoundingBox() {
		if(this.isFormed()) {
			BlockPos p = this.getPos();
			EnumFacing left = multiblockDirection.rotateY();
			
			BlockPos other = p.offset(multiblockDirection, 2).offset(left, 2).offset(EnumFacing.UP,2);
			BlockPos first = p.offset(multiblockDirection.getOpposite(),2).offset(left.getOpposite(),1).offset(EnumFacing.DOWN,1);
			return new AxisAlignedBB(first,other);
		} else {
			return super.getRenderBoundingBox();
		}
	}

	@Override
	protected void checkAndStartOperation() {
		this.contentsChanged=false;
		RecipeData data = FabricatorRecipe.getRecipeDataFor(this.inventory.getStackInSlot(SLOT_INPUT1), this.inventory.getStackInSlot(FabricatorTileEntMaster.SLOT_WIRES), this.inventory.getStackInSlot(FabricatorTileEntMaster.SLOT_POWDER), this.inventory.getStackInSlot(FabricatorTileEntMaster.SLOT_PLATE), this.getMaxMachineUpgradeMultiplier(SLOT_UPGRADE), this.inventory.getStackInSlot(FabricatorTileEntMaster.SLOT_OUTPUT));
		if (data!=null){
		
			this.currentOperation = data.createOperation(this);

			/*if(data.plateConsumption>0){
				ItemStack plate = this.content[SLOT_PLATE];
				if (plate!=null){
					this.plateUsed = plate.copy();
					this.plateUsed.stackSize=1;
				}
			}*/
			this.input.consume(data.inputConsumption*data.stackMultiplier);
			this.wireslot.consume(data.wireConsumption*data.stackMultiplier);
			this.powderslot.consume(data.powderConsumption*data.stackMultiplier);
			this.plateslot.consume(data.plateConsumption*data.stackMultiplier);

			this.totaltime=100;
			this.progress=0;
			
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
				this.inventory.getStackInSlot(SLOT_OUTPUT).grow(itemOut.getCount());
			} else {
				this.inventory.setStackInSlot(SLOT_OUTPUT, itemOut);
			}
		}
	}

	@Override
	protected void playAmbientSound() {
		if (this.world.isRemote && this.progress == 1) {
			//machines.fabricatorWork
			world.playSound(this.getPos().getX(), this.getPos().getY(), this.getPos().getZ(), TGSounds.FABRICATOR_WORK, SoundCategory.BLOCKS, SOUND_VOLUME, 1.0F, true );
		}
	}

	@Override
	protected MultiBlockMachine getMachineBlockType() {
		return TGBlocks.MULTIBLOCK_MACHINE;
	}
	
	public int getValidSlotForItemInMachine(ItemStack stack) {
		if(FabricatorRecipe.itemStackInList(FabricatorRecipe.items_wireslot, stack)){
			return SLOT_WIRES;
		}
		if(FabricatorRecipe.itemStackInList(FabricatorRecipe.items_powderslot, stack)){
			return SLOT_POWDER;
		}
		if(FabricatorRecipe.itemStackInList(FabricatorRecipe.items_plateslot, stack)){
			return SLOT_PLATE;
		}
		
		if (TGItems.isMachineUpgrade(stack)) {
			return SLOT_UPGRADE;
		}
		
		return SLOT_INPUT1;
	}

	@Override
	public AxisAlignedBB getBBforSlave(BlockPos slavePos) {
		return Block.FULL_BLOCK_AABB;
	}

}
