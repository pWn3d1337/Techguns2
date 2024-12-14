package techguns.tileentities;

import net.minecraft.block.state.IBlockState;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import techguns.TGBlocks;
import techguns.blocks.machines.BasicMachine;
import techguns.blocks.machines.MultiBlockMachine;
import techguns.blocks.machines.multiblocks.MultiBlockMachineSchematic;
import techguns.blocks.machines.multiblocks.MultiBlockRegister;

public abstract class MultiBlockMachineTileEntMaster extends BasicMachineTileEnt {

	protected boolean formed=false;
	protected EnumFacing multiblockDirection;
	
	public MultiBlockMachineTileEntMaster(int inventorySize, int maximumPower) {
		super(inventorySize, false, maximumPower);
	}

	public EnumFacing getMultiblockDirection() {
		return multiblockDirection;
	}

	public boolean isFormed() {
		return formed;
	}
	
	@Override
	protected boolean enabled() {
		return this.isFormed();
	}

	@Override
	protected BasicMachine getMachineBlockType() {
		return TGBlocks.ORE_DRILL_BLOCK;
	}
	
	@Override
	public void readClientDataFromNBT(NBTTagCompound tags) {
		super.readClientDataFromNBT(tags);
		this.formed = tags.getBoolean("formed");
		if (this.formed) {
			this.multiblockDirection = EnumFacing.byIndex(tags.getByte("multiblockDirection"));
		}
	}

	@Override
	public void writeClientDataToNBT(NBTTagCompound tags) {
		super.writeClientDataToNBT(tags);
		tags.setBoolean("formed", this.formed);
		if (this.formed) {
			tags.setByte("multiblockDirection", (byte) this.multiblockDirection.getIndex());
		}
	}

	@Override
	public void onBlockBreak() {
		super.onBlockBreak();
		this.onMultiBlockBreak();
	}
	
	@Override
	public boolean canBeWrenchRotated() {
		return false;
	}

	@Override
	public boolean canBeWrenchDismantled() {
		return false;
	}

	public void onMultiBlockBreak() {
		if(!this.world.isRemote && this.formed) {
			MultiBlockMachineSchematic multiblock = MultiBlockRegister.REGISTER.get(this.getClass());
			if(multiblock!=null) {
				multiblock.unform(this.world, this);
			}
		}
	}
	
	public void form(EnumFacing facing) {
		this.formed=true;
		if(!this.world.isRemote) {
			this.multiblockDirection=facing;
			this.world.setBlockState(getPos(), this.world.getBlockState(getPos()).withProperty(MultiBlockMachine.FORMED, true),3);
			this.needUpdate();
		}
	}
	
	public void unform() {
		this.formed=false;
		if(!this.world.isRemote){
			IBlockState bs = world.getBlockState(getPos());
			if (bs.getBlock()==this.getMachineBlockType()) {
				this.world.setBlockState(getPos(), this.world.getBlockState(getPos()).withProperty(MultiBlockMachine.FORMED, false),3);
			}
		}
	}
	
	@Override
	public boolean shouldRefresh(World world, BlockPos pos, IBlockState oldState, IBlockState newState) {
		return (oldState.getBlock()!=newState.getBlock()) || (oldState.getValue(getMachineBlockType().MACHINE_TYPE) != newState.getValue(getMachineBlockType().MACHINE_TYPE));
	}
	
	public abstract AxisAlignedBB getBBforSlave(BlockPos slavePos);
	
}
