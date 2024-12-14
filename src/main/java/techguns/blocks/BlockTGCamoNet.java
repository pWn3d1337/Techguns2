package techguns.blocks;

import java.util.List;

import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.state.BlockFaceShape;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.entity.Entity;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.client.model.ModelLoader;

public class BlockTGCamoNet extends GenericBlockMetaEnumCamoChangeable<EnumCamoNetType> {

	public static PropertyEnum<EnumConnectionType> CONNECTION = PropertyEnum.create("connection", EnumConnectionType.class);
	
	public BlockTGCamoNet(String name) {
		super(name, Material.CLOTH, MapColor.GREEN, SoundType.CLOTH, EnumCamoNetType.class);
		this.blockStateOverride = new BlockStateContainer.Builder(this).add(TYPE).add(CONNECTION).build();
		this.setDefaultState(this.getBlockState().getBaseState());
	}

	public boolean isOpaqueCube(IBlockState state) {
		return false;
	}

	public boolean isFullCube(IBlockState state) {
		return false;
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
    
    protected static final AxisAlignedBB COLLIDE_CENTER = new AxisAlignedBB(7/16d, 0, 7/16d, 9/16d, 1d, 9/16d);
    protected static final AxisAlignedBB COLLIDE_SOUTH = new AxisAlignedBB(7/16d,0,9/16d,9/16d,1d,1d);
    protected static final AxisAlignedBB COLLIDE_NORTH = new AxisAlignedBB(7/16d,0,0,9/16d,1d,7/16d);
    
    protected static final AxisAlignedBB COLLIDE_EAST = new AxisAlignedBB(9/16d,0,7/16d,1d,1d,9/16d);
    protected static final AxisAlignedBB COLLIDE_WEST = new AxisAlignedBB(0,0,7/16d,7/16d,1d,9/16d);
    
    @Override
	public AxisAlignedBB getCollisionBoundingBox(IBlockState blockState, IBlockAccess worldIn, BlockPos pos) {
		return COLLIDE_CENTER;
	}

	@Override
	public void addCollisionBoxToList(IBlockState state, World worldIn, BlockPos pos, AxisAlignedBB entityBox,
			List<AxisAlignedBB> collidingBoxes, Entity entityIn, boolean p_185477_7_) {

    	super.addCollisionBoxToList(state, worldIn, pos, entityBox, collidingBoxes, entityIn, p_185477_7_);
    	
    	boolean n = canConnectTo(worldIn, pos, EnumFacing.NORTH);
    	boolean e = canConnectTo(worldIn, pos, EnumFacing.EAST);
    	boolean s = canConnectTo(worldIn, pos, EnumFacing.SOUTH);
    	boolean w = canConnectTo(worldIn, pos, EnumFacing.WEST);
    	
    	if(n) addCollisionBoxToList(pos, entityBox, collidingBoxes, COLLIDE_NORTH);
    	if(e) addCollisionBoxToList(pos, entityBox, collidingBoxes, COLLIDE_EAST);
    	if(s) addCollisionBoxToList(pos, entityBox, collidingBoxes, COLLIDE_SOUTH);
    	if(w) addCollisionBoxToList(pos, entityBox, collidingBoxes, COLLIDE_WEST);
    	
	}

	@Override
	public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess worldIn, BlockPos pos) {

		float f = 0.4375F;
		float f1 = 0.5625F;
		float f2 = 0.4375F;
		float f3 = 0.5625F;
		boolean north = canConnectTo(worldIn, pos, EnumFacing.NORTH);
		boolean east = canConnectTo(worldIn, pos, EnumFacing.EAST);
		boolean south = canConnectTo(worldIn, pos, EnumFacing.SOUTH);
		boolean west = canConnectTo(worldIn, pos, EnumFacing.WEST);

		if (!(north || east || south || west)) {
			return new AxisAlignedBB(f, 0.0F, f2, f1, 1.0F, f3);
		}

		if ((!west || !east) && (west || east || north || south)) {
			if (west && !east) {
				f = 0.0F;
			} else if (!west && east) {
				f1 = 1.0F;
			}
		} else {
			f = 0.0F;
			f1 = 1.0F;
		}

		if ((!north || !south) && (west || east || north || south)) {
			if (north && !south) {
				f2 = 0.0F;
			} else if (!north && south) {
				f3 = 1.0F;
			}
		} else {
			f2 = 0.0F;
			f3 = 1.0F;
		}
		return new AxisAlignedBB(f, 0.0F, f2, f1, 1.0F, f3);
	}

	private boolean canConnectTo(IBlockAccess world, BlockPos pos, EnumFacing facing)
    {
        BlockPos other = pos.offset(facing);
        Block block = world.getBlockState(other).getBlock();
        return block instanceof BlockTGCamoNet;
    }

	@Override
	public BlockRenderLayer getRenderLayer() {
		return BlockRenderLayer.CUTOUT;
	}

	
	
	@Override
	public BlockFaceShape getBlockFaceShape(IBlockAccess p_193383_1_, IBlockState p_193383_2_, BlockPos p_193383_3_,
			EnumFacing p_193383_4_) {
		return BlockFaceShape.MIDDLE_POLE_THIN;
	}

	@Override
	public void registerItemBlockModels() {
		for(int i = 0; i< clazz.getEnumConstants().length;i++) {
			ModelLoader.setCustomModelResourceLocation(this.itemblock, i, new ModelResourceLocation(getRegistryName()+"_inventory","type="+clazz.getEnumConstants()[i].getName()));
		}
	}
    
	
}
