package techguns.world;

import net.minecraft.block.Block;
import net.minecraft.block.BlockHorizontal;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.EnumFacing;

public class BlockRotator {

	public static IBlockState getRotatedHorizontal(IBlockState state, int times) {
		EnumFacing facing = state.getValue(BlockHorizontal.FACING);
		while (times-- > 0) {
			facing = facing.rotateYCCW();
		}
		return state.withProperty(BlockHorizontal.FACING, facing);		
	}
}
