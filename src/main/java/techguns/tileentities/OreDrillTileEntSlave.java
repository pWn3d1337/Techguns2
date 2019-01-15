package techguns.tileentities;

import net.minecraft.block.state.IBlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import techguns.TGBlocks;
import techguns.blocks.machines.EnumOreDrillType;
import techguns.blocks.machines.MultiBlockMachine;

public class OreDrillTileEntSlave extends MultiBlockMachineTileEntSlave {

	@Override
	public MultiBlockMachine getMachineBlockType() {
		return TGBlocks.ORE_DRILL_BLOCK;
	}

	@Override
	public boolean shouldRefresh(World world, BlockPos pos, IBlockState oldState, IBlockState newState) {
		if(newState.getBlock()==oldState.getBlock()) {
			if(oldState.getValue(getMachineBlockType().MACHINE_TYPE) == EnumOreDrillType.SCAFFOLD && newState.getValue(getMachineBlockType().MACHINE_TYPE)==EnumOreDrillType.SCAFFOLD_HIDDEN) {
				return false;
			}
		}
		return super.shouldRefresh(world, pos, oldState, newState);
	}

	
	
}
