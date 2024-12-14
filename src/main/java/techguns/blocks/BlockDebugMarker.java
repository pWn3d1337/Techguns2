package techguns.blocks;

import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.IStringSerializable;
import net.minecraft.util.NonNullList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import techguns.TGConfig;

public class BlockDebugMarker extends GenericBlockMetaEnum<EnumDebugBlockType> {

	public BlockDebugMarker(String name, Material mat) {
		super(name, mat, EnumDebugBlockType.class);
	}

	@Override
	public BlockRenderLayer getRenderLayer() {
		return BlockRenderLayer.CUTOUT;
	}
	
	@Override
	public boolean isFullBlock(IBlockState state) {
		return false;
	}

	@Override
	public boolean isOpaqueCube(IBlockState state) {
		return false;
	}

	@Override
	public boolean isNormalCube(IBlockState state) {
		return false;
	}

	@Override
	public boolean isFullCube(IBlockState state) {
		return false;
	}

	@Override
	public int damageDropped(IBlockState state) {
		EnumDebugBlockType type = state.getValue(TYPE);
		if(type==EnumDebugBlockType.INTERIORMARKER_NORTH || type==EnumDebugBlockType.INTERIORMARKER_EAST || type==EnumDebugBlockType.INTERIORMARKER_SOUTH || type==EnumDebugBlockType.INTERIORMARKER_WEST) {
			return EnumDebugBlockType.INTERIORMARKER_NORTH.ordinal();
		}
		return super.damageDropped(state);
	}

	@Override
	public void getSubBlocks(CreativeTabs tab, NonNullList<ItemStack> items) {
		if(TGConfig.debug) {
			items.add(new ItemStack(this,1,this.getMetaFromState(getDefaultState().withProperty(TYPE, EnumDebugBlockType.AIRMARKER))));
			items.add(new ItemStack(this,1,this.getMetaFromState(getDefaultState().withProperty(TYPE, EnumDebugBlockType.ANTIAIRMARKER))));
			items.add(new ItemStack(this,1,this.getMetaFromState(getDefaultState().withProperty(TYPE, EnumDebugBlockType.INTERIORMARKER_NORTH))));
		}
	}

	@Override
	public IBlockState getStateForPlacement(World world, BlockPos pos, EnumFacing facing, float hitX, float hitY,
			float hitZ, int meta, EntityLivingBase placer, EnumHand hand) {
		if(meta>=EnumDebugBlockType.INTERIORMARKER_NORTH.ordinal() && meta <= EnumDebugBlockType.INTERIORMARKER_WEST.ordinal()) {
			switch(placer.getHorizontalFacing().getOpposite()) {
			case WEST:
				return this.getDefaultState().withProperty(TYPE, EnumDebugBlockType.INTERIORMARKER_WEST);
			case EAST:
				return this.getDefaultState().withProperty(TYPE, EnumDebugBlockType.INTERIORMARKER_EAST);
			case NORTH:
				return this.getDefaultState().withProperty(TYPE, EnumDebugBlockType.INTERIORMARKER_NORTH);
			case SOUTH:
				return this.getDefaultState().withProperty(TYPE, EnumDebugBlockType.INTERIORMARKER_SOUTH);
			default:
				break;
			}
		}
		return super.getStateForPlacement(world, pos, facing, hitX, hitY, hitZ, meta, placer, hand);
	}
	
}
