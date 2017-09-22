package techguns.blocks.machines;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.google.common.base.Predicate;

import net.minecraft.block.Block;
import net.minecraft.block.BlockHorizontal;
import net.minecraft.block.properties.PropertyBool;
import net.minecraft.block.properties.PropertyDirection;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumBlockRenderType;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.IStringSerializable;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.common.property.ExtendedBlockState;
import net.minecraftforge.common.property.IExtendedBlockState;
import net.minecraftforge.common.property.PropertyFloat;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import techguns.api.machines.IMachineType;
import techguns.tileentities.MultiBlockMachineTileEntMaster;
import techguns.tileentities.MultiBlockMachineTileEntSlave;
import techguns.util.BlockUtils;

public class MultiBlockMachine<T extends Enum<T> & IStringSerializable & IMachineType> extends BasicMachine<T> {
	public static final PropertyBool FORMED = PropertyBool.create("formed");
	

	public static final PropertyDirection MULTIBLOCK_DIRECTION = BlockHorizontal.FACING; 
	
	public MultiBlockMachine(String name, Class<T> clazz) {
		super(name, clazz);
		this.blockStateOverride = new BlockStateContainer.Builder(this).add(MACHINE_TYPE).add(FORMED).add(MULTIBLOCK_DIRECTION).build();
		this.setDefaultState( this.getBlockState().getBaseState().withProperty(FORMED, false).withProperty(MULTIBLOCK_DIRECTION, EnumFacing.SOUTH));
	}

	@Override
	public int getMetaFromState(IBlockState state) {
		return (state.getValue(FORMED)?8:0) + state.getValue(MACHINE_TYPE).getIndex();
	}
	
	@Override
	public IBlockState getStateFromMeta(int meta) {
		return this.getDefaultState()
	    .withProperty(FORMED, meta>=8)
	    .withProperty(MACHINE_TYPE, clazz.getEnumConstants()[meta>=8?meta-8:meta]);
	}
	
	@Override
	public IBlockState getActualState(IBlockState state, IBlockAccess world, BlockPos pos) {
		IBlockState s = world.getBlockState(pos);
		TileEntity tile = world.getTileEntity(pos);
		if(tile instanceof MultiBlockMachineTileEntMaster) {
			MultiBlockMachineTileEntMaster mastertile = (MultiBlockMachineTileEntMaster) tile;
			if(mastertile.isFormed()) {
				EnumFacing dir = mastertile.getMultiblockDirection();
				s = s.withProperty(MULTIBLOCK_DIRECTION, dir);
				//System.out.println("Get ACTUAL STATE:"+s);
				return s;
			}
		}
		return s.withProperty(MULTIBLOCK_DIRECTION, EnumFacing.SOUTH);
	}

	/**
     * Called by ItemBlocks just before a block is actually set in the world, to allow for adjustments to the
     * IBlockstate
     */
	@Override
    public IBlockState getStateForPlacement(World worldIn, BlockPos pos, EnumFacing facing, float hitX, float hitY, float hitZ, int meta, EntityLivingBase placer)
    {
        return this.getStateFromMeta(meta).withProperty(FORMED,false);
    }

	/*@Override
	public boolean hasCustomBreakingProgress(IBlockState state) {
		return state.getValue(FORMED);
	}*/

	@Override
	public EnumBlockRenderType getRenderType(IBlockState state) {
		/*boolean formed = state.getValue(FORMED);
		if (formed) {
			return EnumBlockRenderType.ENTITYBLOCK_ANIMATED;
		}*/
		T t = state.getValue(MACHINE_TYPE);
		return t.getRenderType();
	}
	
	@Override
	public boolean isFullBlock(IBlockState state) {
		return this.isFullCube(state);
	}

	@Override
	public boolean isFullCube(IBlockState state) {
		/*boolean formed = state.getValue(FORMED);
		if (formed) {
			System.out.println("FullCube:false");
			return false;
		}
		T t = state.getValue(MACHINE_TYPE);
		System.out.println("FullCube T:"+t.isFullCube());
		return t.isFullCube();*/
		return false;
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
		return this.isFullCube(state);
		/*
		boolean formed = state.getValue(FORMED);
		if (formed) {
			return false;
		}
		T t = state.getValue(MACHINE_TYPE);
		return t.isFullCube();*/
	}
	
	@Override
	public AxisAlignedBB getCollisionBoundingBox(IBlockState blockState, IBlockAccess worldIn, BlockPos pos) {
		if(blockState.getValue(FORMED)) {
			
			TileEntity tile = worldIn.getTileEntity(pos);
			if(tile instanceof MultiBlockMachineTileEntSlave) {
				return ((MultiBlockMachineTileEntSlave) tile).getFormedCollisionBoundingBox();
			}
			
		}
		
		return FULL_BLOCK_AABB;
	}
	
	@Override
	public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos) {
		return this.getCollisionBoundingBox(state, source, pos);
	}

	@SideOnly(Side.CLIENT)
	@Override
	public void registerItemBlockModels() {
		for(int i = 0; i< clazz.getEnumConstants().length;i++) {
			IBlockState state = getDefaultState().withProperty(MACHINE_TYPE, clazz.getEnumConstants()[i]);
			ModelLoader.setCustomModelResourceLocation(this.itemblock, this.getMetaFromState(state), new ModelResourceLocation(getRegistryName(),BlockUtils.getBlockStateVariantString(state)));
		}
	}

	@Override
	public boolean rotateBlock(World world, BlockPos pos, EnumFacing axis) {
		//NO ROTATING ON MULTIBLOCK MACHINES
		return false;
	}
	
	
}
