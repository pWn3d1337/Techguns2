package techguns.world;

import net.minecraft.block.Block;
import net.minecraft.block.BlockHorizontal;
import net.minecraft.block.BlockTorch;
import net.minecraft.block.properties.PropertyDirection;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.util.EnumFacing;

public class BlockRotator {

	public static IBlockState getRotatedHorizontal(IBlockState state, int times) {
		
		if(state.getProperties().containsKey(BlockHorizontal.FACING)) {
			return getRotatedWithProperty(state, times, BlockHorizontal.FACING);
			
		} else if (state.getBlock()==Blocks.TORCH) {
			return getRotatedWithProperty(state, times, BlockTorch.FACING);
		}
		
		
		return state;
	}
	
	protected static IBlockState getRotatedWithProperty(IBlockState state, int times, PropertyDirection property) {
		EnumFacing facing = state.getValue(property);
		while (times-- > 0) {
			facing = facing.rotateYCCW();
		}
		return state.withProperty(property, facing);	
	}
}
