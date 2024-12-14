package techguns.blocks;

import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.state.BlockFaceShape;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraftforge.client.model.ModelLoader;

public class BlockTGCamoNetTop extends GenericBlockMetaEnumCamoChangeable<EnumCamoNetType> {

	public static PropertyEnum<EnumConnectionType> CONNECTION = BlockTGCamoNet.CONNECTION;
	
	
	public BlockTGCamoNetTop(String name) {
		super(name, Material.CLOTH, MapColor.GREEN, SoundType.CLOTH, EnumCamoNetType.class);
		this.blockStateOverride = new BlockStateContainer.Builder(this).add(TYPE).add(CONNECTION).build();
		this.setDefaultState(this.getBlockState().getBaseState());
	}
	
	
	private static final float height = 1/16f;
    //private static final float width = 0.5625F;
    //private static final float width2 = 0.75F;	
	
	protected static final AxisAlignedBB[] bounding_boxes = {
		//no connections
		new AxisAlignedBB(4/16d, 0, 4/16d, 12/16d, height, 12/16d),
		//w
		new AxisAlignedBB(0, 0, 4/16d, 9/16d, height, 12/16d),
		//s
		new AxisAlignedBB(4/16d, 0, 7/16d, 12/16d, height, 1d),
		//sw
		new AxisAlignedBB(0, 0, 7/16d, 9/16d, height, 1d),
		//e
		new AxisAlignedBB(7/16d, 0, 4/16d, 1d, height, 12/16d),
		//ew
		new AxisAlignedBB(0, 0, 4/16d, 1d, height, 12/16d),
		//es
		new AxisAlignedBB(7/16d, 0, 7/16d, 1d, height, 1d),
		//esw
		new AxisAlignedBB(0, 0, 7/16d, 1d, height, 1d),
		//n
		new AxisAlignedBB(4/16d, 0, 0, 12/16d, height, 9/16d),
		//nw
		new AxisAlignedBB(0, 0, 0, 9/16d, height, 9/16d),
		//ns
		new AxisAlignedBB(4/16d, 0, 0, 12/16d, height, 1d),
		//nsw
		new AxisAlignedBB(0, 0, 0, 9/16d, height, 1d),
		//ne
		new AxisAlignedBB(7/16d, 0, 0, 1d, height, 9/16d),
		//new
		new AxisAlignedBB(0, 0, 0, 1d, height, 9/16d),
		//nes
		new AxisAlignedBB(7/16d, 0, 0, 1d, height, 1d),
		//nesw
		new AxisAlignedBB(0, 0, 0, 1d, height, 1d)

	};

	@Override
	public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess worldIn, BlockPos pos) {
		boolean north = canConnectTo(worldIn, pos, EnumFacing.NORTH);
		boolean east = canConnectTo(worldIn, pos, EnumFacing.EAST);
		boolean south = canConnectTo(worldIn, pos, EnumFacing.SOUTH);
		boolean west = canConnectTo(worldIn, pos, EnumFacing.WEST);
		
		int index = EnumConnectionType.get(north, east, south, west).ordinal();
		
		return bounding_boxes[index];
	}

	
	/**
     * Get the actual Block state of this Block at the given position. This applies properties not visible in the
     * metadata, such as fence connections.
     */
    @Override
    public IBlockState getActualState(IBlockState state, IBlockAccess worldIn, BlockPos pos)
    {
    	boolean n = canConnectTo(worldIn, pos, EnumFacing.NORTH);
    	boolean e = canConnectTo(worldIn, pos, EnumFacing.EAST);
    	boolean s = canConnectTo(worldIn, pos, EnumFacing.SOUTH);
    	boolean w = canConnectTo(worldIn, pos, EnumFacing.WEST);
    	
        return state.withProperty(CONNECTION, EnumConnectionType.get(n, e, s, w));
    }
	
	public boolean isOpaqueCube(IBlockState state) {
		return false;
	}

	public boolean isFullCube(IBlockState state) {
		return false;
	}
	
	private boolean canConnectTo(IBlockAccess world, BlockPos pos, EnumFacing facing)
    {
        BlockPos other = pos.offset(facing);
        Block block = world.getBlockState(other).getBlock();
        return block instanceof BlockTGCamoNetTop;
    }

	@Override
	public BlockRenderLayer getRenderLayer() {
		return BlockRenderLayer.CUTOUT;
	}


	@Override
	public BlockFaceShape getBlockFaceShape(IBlockAccess p_193383_1_, IBlockState p_193383_2_, BlockPos p_193383_3_,
			EnumFacing p_193383_4_) {
		return BlockFaceShape.UNDEFINED;
	}

	@Override
	public void registerItemBlockModels() {
		for(int i = 0; i< clazz.getEnumConstants().length;i++) {
			ModelLoader.setCustomModelResourceLocation(this.itemblock, i, new ModelResourceLocation(getRegistryName()+"_inventory","type="+clazz.getEnumConstants()[i].getName()));
		}
	}
	
}
