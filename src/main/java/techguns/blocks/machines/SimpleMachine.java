package techguns.blocks.machines;

import net.minecraft.block.Block;
import net.minecraft.block.BlockHorizontal;
import net.minecraft.block.properties.PropertyDirection;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.IStringSerializable;
import net.minecraft.util.Mirror;
import net.minecraft.util.Rotation;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import techguns.api.machines.IMachineType;
import techguns.util.BlockUtils;

/**
 * A Machine that has 4 rotation types as block property
 */
public class SimpleMachine<T extends Enum<T> & IStringSerializable & IMachineType> extends BasicMachine<T> {
	
	public static final PropertyDirection FACING = BlockHorizontal.FACING; //PropertyDirection.create("facing", EnumFacing.Plane.HORIZONTAL);
	
	public SimpleMachine(String name, Class<T> clazz) {
		super(name, clazz);
		this.blockStateOverride = new BlockStateContainer.Builder(this).add(FACING).add(MACHINE_TYPE).build();
		this.setDefaultState(this.getBlockState().getBaseState());
	}
	
	@Override
	public int getMetaFromState(IBlockState state) {
		return state.getValue(FACING).getHorizontalIndex() << 2 | state.getValue(MACHINE_TYPE).getIndex();
	}

	@Override
	public IBlockState getStateFromMeta(int meta) {
		return this.getDefaultState()
	    .withProperty(FACING, EnumFacing.byHorizontalIndex(meta >> 2))
	    .withProperty(MACHINE_TYPE, clazz.getEnumConstants()[meta & 0b11]);
    }

	/*@Override
	public boolean isFullCube(IBlockState state) {
		
		return true;
	}*/

	
	
	@Override
	public boolean isFullCube(IBlockState state) {
		T t = state.getValue(MACHINE_TYPE);
		return t.isFullCube();
	}
	
	@Override
	public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos) {
		T t = state.getValue(MACHINE_TYPE);
		return t.getBoundingBox(state, source, pos);
	}

	@Override
	public boolean isOpaqueCube(IBlockState state) {
		/**
		 * Required to check this, because vanilla block constructor calls this method too early.
		 */
		Block b= state.getBlock();
		if(b.getRegistryName()==null) {
			return false;
		}
		
		T t = state.getValue(MACHINE_TYPE);
		return t.isFullCube();
	}
	
	  /**
     * Called by ItemBlocks just before a block is actually set in the world, to allow for adjustments to the
     * IBlockstate
     */
	@Override
    public IBlockState getStateForPlacement(World worldIn, BlockPos pos, EnumFacing facing, float hitX, float hitY, float hitZ, int meta, EntityLivingBase placer)
    {
        return this.getStateFromMeta(meta).withProperty(FACING, placer.getHorizontalFacing().getOpposite());
    }
	
	
	@SideOnly(Side.CLIENT)
	@Override
	public void registerItemBlockModels() {
		for(int i = 0; i< clazz.getEnumConstants().length;i++) {
			IBlockState state = getDefaultState().withProperty(MACHINE_TYPE, clazz.getEnumConstants()[i]);
			if(clazz.getEnumConstants()[i].hasCustomModelLocation()) {
				clazz.getEnumConstants()[i].setCustomModelLocation(this.itemblock, this.getMetaFromState(state), getRegistryName(), state);
			} else {
				ModelLoader.setCustomModelResourceLocation(this.itemblock, this.getMetaFromState(state), new ModelResourceLocation(getRegistryName(),BlockUtils.getBlockStateVariantString(state)));
			}
		}
	}

	@Override
	public IBlockState withRotation(IBlockState state, Rotation rot) {
		EnumFacing facing = state.getValue(FACING);
		switch(rot) {
		case CLOCKWISE_180:
			return state.withProperty(FACING, facing.getOpposite());
		case CLOCKWISE_90:
			return state.withProperty(FACING, facing.rotateY());
		case COUNTERCLOCKWISE_90:
			return state.withProperty(FACING, facing.rotateYCCW());
		case NONE:
		default:
			return state;
		}
	}

	@Override
	public IBlockState withMirror(IBlockState state, Mirror mirrorIn) {
		EnumFacing facing = state.getValue(FACING);
		return state.withProperty(FACING,mirrorIn.mirror(facing));
	}

	@Override
	public boolean rotateBlock(World world, BlockPos pos, EnumFacing axis) {
		if (axis == EnumFacing.DOWN || axis==EnumFacing.UP) {
			IBlockState state = world.getBlockState(pos);
			IBlockState statenew = state.withProperty(FACING, state.getValue(FACING).rotateY());
			world.setBlockState(pos, statenew, 3);
			return true;
		}
		return false;
	}
	
	
}
