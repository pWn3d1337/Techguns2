package techguns.blocks;

import java.util.Arrays;

import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyDirection;
import net.minecraft.block.properties.PropertyInteger;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemBlock;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.event.RegistryEvent.Register;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import techguns.Techguns;
import techguns.tileentities.BioBlobTileEnt;
import techguns.util.BlockUtils;

public class BlockBioblob extends GenericBlock {

	public static final int SIZE_2_W = 1;
	public static final int SIZE_2_H = 5;
	
	public static final int SIZE_1_W = 3;
	public static final int SIZE_1_H = 4;
	
	public static final int SIZE_0_W = 5;
	public static final int SIZE_0_H = 3;
	
	public static final float FSIZE = 0.0625f;
	
	public static final PropertyInteger SIZE = PropertyInteger.create("size", 0, 2);
	protected ItemBlock itemblock;
	
	public BlockBioblob(String name) {
		super(name, Material.CLAY);
		this.setSoundType(SoundType.SLIME);
		this.setDefaultState(this.blockState.getBaseState().withProperty(FACING_ALL, EnumFacing.DOWN).withProperty(SIZE, 0));
	}

	
	protected AxisAlignedBB getBB(IBlockState state, IBlockAccess w, BlockPos pos) {
		int size=2;
		TileEntity tile = w.getTileEntity(pos);
		if(tile!=null && tile instanceof BioBlobTileEnt) {
			size = ((BioBlobTileEnt)tile).getBlobSize();
		}
		switch(size) {
		case 0:
			return getBBForRota(SIZE_0_W,SIZE_0_H,state.getValue(BlockBioblob.FACING_ALL));
		case 1:
			return getBBForRota(SIZE_1_W,SIZE_1_H,state.getValue(BlockBioblob.FACING_ALL));
		default:
			return getBBForRota(SIZE_2_W,SIZE_2_H,state.getValue(BlockBioblob.FACING_ALL));
		}
	}
	
	
	
	@Override
	public int getLightValue(IBlockState state) {
		return 7;
	}

	protected AxisAlignedBB getBBForRota(int W, int H, EnumFacing facing) {
		
		switch(facing) {
		case DOWN:
			return new AxisAlignedBB(FSIZE*W, 0, FSIZE*W, FSIZE*(16-W), FSIZE*H, FSIZE*(16-W));
		case WEST:
			return new AxisAlignedBB( 0,FSIZE*W, FSIZE*W, FSIZE*H, FSIZE*(16-W), FSIZE*(16-W));
		case NORTH:
			return new AxisAlignedBB(FSIZE*W, FSIZE*W, 0, FSIZE*(16-W), FSIZE*(16-W), FSIZE*H);
		case SOUTH:
			return new AxisAlignedBB(FSIZE*W, FSIZE*W, FSIZE*(16-H), FSIZE*(16-W), FSIZE*(16-W), 1);
		case UP:
			return new AxisAlignedBB(FSIZE*W, FSIZE*(16-H), FSIZE*W, FSIZE*(16-W), 1, FSIZE*(16-W));
		case EAST:
			return new AxisAlignedBB(FSIZE*(16-H),FSIZE*W, FSIZE*W, 1, FSIZE*(16-W), FSIZE*(16-W));
		}
		return new AxisAlignedBB(FSIZE*W, 0, FSIZE*W, FSIZE*(16-W), FSIZE*H, FSIZE*(16-W));
	}
	
	@Override
	public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos) {
		return getBB(state,source,pos);
	}

	@Override
	public AxisAlignedBB getCollisionBoundingBox(IBlockState blockState, IBlockAccess worldIn, BlockPos pos) {
		return getBB(blockState,worldIn,pos);
	}

	@Override
	public boolean isFullBlock(IBlockState state) {
		return false;
	}

	@Override
	public boolean isNormalCube(IBlockState state, IBlockAccess world, BlockPos pos) {
		return false;
	}
	
	@Override
	public boolean isOpaqueCube(IBlockState state) {
		return false;
	}

	@Override
	public void registerBlock(Register<Block> event) {
		super.registerBlock(event);
		GameRegistry.registerTileEntity(BioBlobTileEnt.class, Techguns.MODID+":bioblob");
	}

	@Override
	protected BlockStateContainer createBlockState() {
		return new BlockStateContainer(this, FACING_ALL, SIZE);
	}

	@Override
	public IBlockState getStateFromMeta(int meta) {
		return this.getDefaultState().withProperty(FACING_ALL, EnumFacing.VALUES[meta]);
	}

	@Override
	public int getMetaFromState(IBlockState state) {
		return state.getValue(FACING_ALL).getIndex();
	}

	@Override
	public IBlockState getActualState(IBlockState state, IBlockAccess worldIn, BlockPos pos) {
		TileEntity tile = worldIn.getTileEntity(pos);
		int size=0;
		if(tile!=null && tile instanceof BioBlobTileEnt) {
			size = ((BioBlobTileEnt)tile).getBlobSize();
		}
		return state.withProperty(SIZE, size);		
	}

	
	@Override
	public ItemBlock createItemBlock() {
		GenericItemBlockMeta itemblock =  new GenericItemBlockMeta(this);
		this.itemblock=itemblock;
		return itemblock;
	}

	@SideOnly(Side.CLIENT)
	@Override
	public void registerItemBlockModels() {
		IBlockState state = getDefaultState();
		ModelLoader.setCustomModelResourceLocation(this.itemblock, this.getMetaFromState(state), new ModelResourceLocation(getRegistryName(),BlockUtils.getBlockStateVariantString(state)));
	}

	@Override
	public boolean hasTileEntity(IBlockState state) {
		return true;
	}

	@Override
	public TileEntity createTileEntity(World world, IBlockState state) {
		return new BioBlobTileEnt();
	}

	
}
