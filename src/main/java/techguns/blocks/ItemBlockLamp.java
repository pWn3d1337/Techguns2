package techguns.blocks;

import net.minecraft.block.Block;
import net.minecraft.block.state.BlockFaceShape;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class ItemBlockLamp extends GenericItemBlockMeta {

	public ItemBlockLamp(Block block) {
		super(block);
	}

	@Override
	public boolean canPlaceBlockOnSide(World worldIn, BlockPos pos, EnumFacing side, EntityPlayer player,
			ItemStack stack) {
		IBlockState state = worldIn.getBlockState(pos);
		BlockFaceShape shape = state.getBlockFaceShape(worldIn, pos, side);
		
		boolean isLantern = stack.getMetadata()==12 || stack.getMetadata()==13;
		
		if( shape==BlockFaceShape.SOLID || shape==BlockFaceShape.CENTER_BIG || shape== BlockFaceShape.MIDDLE_POLE_THICK || (isLantern&&(side==EnumFacing.UP||side==EnumFacing.DOWN)&&state.getBlock().canPlaceTorchOnTop(state, worldIn, pos))) {	
			return super.canPlaceBlockOnSide(worldIn, pos, side, player, stack);
		}
		return false;
	}
	
	

}
