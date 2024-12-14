package techguns.blocks;

import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyBool;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.state.BlockFaceShape;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.IStringSerializable;
import net.minecraft.util.NonNullList;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import techguns.Techguns;
import techguns.items.armors.ICamoChangeable;

public class BlockTGLamp<T extends Enum<T> & IStringSerializable> extends GenericBlock implements ICamoChangeable {
	
	public PropertyEnum<T> LAMP_TYPE;
	protected Class<T> clazz;
	protected BlockStateContainer blockStateOverride;
	protected GenericItemBlockMeta itemblock;
	
	public static final PropertyBool CONNECT_DOWN = PropertyBool.create("down");
	public static final PropertyBool CONNECT_UP = PropertyBool.create("up");
	public static final PropertyBool CONNECT_N = PropertyBool.create("north");
	public static final PropertyBool CONNECT_S = PropertyBool.create("south");
	public static final PropertyBool CONNECT_W = PropertyBool.create("west");
	public static final PropertyBool CONNECT_E = PropertyBool.create("east");
    
	protected static final double lamp_side=0.25D;
	protected static final double lamp_side_max=1.0D-lamp_side;
	protected static final double lamp_h=0.1875D;
	
	public static final AxisAlignedBB LAMP_DOWN_AABB = new AxisAlignedBB(lamp_side, 0, lamp_side, lamp_side_max, lamp_h, lamp_side_max);
	public static final AxisAlignedBB LAMP_UP_AABB = new AxisAlignedBB(lamp_side, 1.0D-lamp_h, lamp_side, lamp_side_max, 1.0D, lamp_side_max);
	public static final AxisAlignedBB LAMP_NORTH_AABB = new AxisAlignedBB(lamp_side, lamp_side, 0.0D, lamp_side_max, lamp_side_max, lamp_h);
	public static final AxisAlignedBB LAMP_SOUTH_AABB = new AxisAlignedBB(lamp_side, lamp_side, 1.0D-lamp_h, lamp_side_max, lamp_side_max, 1.0D);

	public static final AxisAlignedBB LAMP_WEST_AABB = new AxisAlignedBB(0.0D, lamp_side, lamp_side, lamp_h, lamp_side_max, lamp_side_max);
	public static final AxisAlignedBB LAMP_EAST_AABB = new AxisAlignedBB(1.0D-lamp_h, lamp_side, lamp_side, 1.0D, lamp_side_max, lamp_side_max);
	
	protected static final double lantern_side=0.25D;
	protected static final double lantern_side_max=1.0D-lamp_side;
	protected static final double lantern_h=0.125D;
	public static final AxisAlignedBB LANTERN_AABB = new AxisAlignedBB(lantern_side, lantern_h, lantern_side, lantern_side_max, 1.0D-lantern_h, lamp_side_max);
	
	public BlockTGLamp(String name,  Class<T> clazz) {
		super(name, Material.IRON, MapColor.YELLOW);
		this.setSoundType(SoundType.GLASS);
		this.clazz=clazz;
		this.LAMP_TYPE = PropertyEnum.create("lamp_type", clazz);
		this.blockStateOverride = new BlockStateContainer.Builder(this).add(LAMP_TYPE).add(FACING_ALL).add(CONNECT_DOWN)
				.add(CONNECT_UP).add(CONNECT_N).add(CONNECT_S).add(CONNECT_W).add(CONNECT_E).build();
		this.setDefaultState(this.getBlockState().getBaseState());
		
		setHardness(0.25f);
		this.setSoundType(SoundType.GLASS);
		setLightLevel(1.0f);
		setLightOpacity(0);
	}
	
	public BlockStateContainer getBlockState() {
		return this.blockStateOverride;
	}
	
	@Override
	public int damageDropped(IBlockState state) {
		return this.getMetaFromState(getDefaultState().withProperty(LAMP_TYPE, state.getValue(LAMP_TYPE)));
	}
	
	@Override
    public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos)
    {
        state = this.getActualState(state, source, pos);
       if(state.getValue(LAMP_TYPE)==clazz.getEnumConstants()[0] || state.getValue(LAMP_TYPE)==clazz.getEnumConstants()[1]) {
    	   switch(state.getValue(FACING_ALL)) {
		case UP:
			return LAMP_UP_AABB;
		case EAST:
			return LAMP_EAST_AABB;
		case NORTH:
			return LAMP_NORTH_AABB;
		case SOUTH:
			return LAMP_SOUTH_AABB;
		case WEST:
			return LAMP_WEST_AABB;
		case DOWN:
		default:
			return LAMP_DOWN_AABB;
    	   }
       } else {
    	   return LANTERN_AABB;
       }
       
       //return super.getBoundingBox(state, source, pos);
    }

	
	  /**
     * Used to determine ambient occlusion and culling when rebuilding chunks for render
     */
    public boolean isOpaqueCube(IBlockState state)
    {
        return false;
    }

    public boolean isFullCube(IBlockState state)
    {
        return false;
    }
    
	@Override
	public ItemBlock createItemBlock() {
		this.itemblock =  new ItemBlockLamp(this);
		return itemblock;
	}

	@Override
	public IBlockState getStateFromMeta(int meta) {
		IBlockState s = this.getDefaultState();
		if (meta <6) {
			s = s.withProperty(LAMP_TYPE, this.clazz.getEnumConstants()[0]).withProperty(FACING_ALL,EnumFacing.byIndex(meta));
		} else if (meta <12) {
			s = s.withProperty(LAMP_TYPE, this.clazz.getEnumConstants()[1]).withProperty(FACING_ALL,EnumFacing.byIndex(meta-6));
		} else if (meta == 12) {
			s = s.withProperty(LAMP_TYPE, this.clazz.getEnumConstants()[2]);
		} else if (meta == 13) {
			s = s.withProperty(LAMP_TYPE, this.clazz.getEnumConstants()[3]);
		}
		return s;
	}

	@Override
	public int getMetaFromState(IBlockState state) {
		T t = state.getValue(LAMP_TYPE);
		if (t.ordinal() == 0) {
			return state.getValue(FACING_ALL).getIndex();
		} else if (t.ordinal() == 1) {
			return 6+state.getValue(FACING_ALL).getIndex();
		} else if (t.ordinal() == 2) {
			return 12;
		} else if (t.ordinal() == 3) {
			return 13;
		}
		return 0;
	}

	@Override
	public IBlockState getActualState(IBlockState state, IBlockAccess worldIn, BlockPos pos) {
		int type = state.getValue(LAMP_TYPE).ordinal();
		if (type==0 || type==1) {
			return state.withProperty(CONNECT_UP, false).withProperty(CONNECT_DOWN,false).withProperty(CONNECT_E,false)
					.withProperty(CONNECT_W, false).withProperty(CONNECT_N, false).withProperty(CONNECT_S, false);
		} else {
			
			boolean u = this.canPlaceAt(worldIn, pos, EnumFacing.UP);
			boolean d = this.canPlaceAt(worldIn, pos, EnumFacing.DOWN);
			boolean n = this.canPlaceAt(worldIn, pos, EnumFacing.NORTH);
			boolean s = this.canPlaceAt(worldIn, pos, EnumFacing.SOUTH);
			boolean w = this.canPlaceAt(worldIn, pos, EnumFacing.WEST);
			boolean e = this.canPlaceAt(worldIn, pos, EnumFacing.EAST);
			
			return state.withProperty(CONNECT_UP, u).withProperty(CONNECT_DOWN,d).withProperty(CONNECT_E,e)
					.withProperty(CONNECT_W, w).withProperty(CONNECT_N, n).withProperty(CONNECT_S, s);
			
		}
	}
	
	@Override
	public IBlockState getStateForPlacement(World world, BlockPos pos, EnumFacing facing, float hitX, float hitY,
			float hitZ, int meta, EntityLivingBase placer, EnumHand hand) {
		IBlockState state = this.getStateFromMeta(meta);
		return state.withProperty(FACING_ALL, facing.getOpposite());
	}

	@Override
	public void getSubBlocks(CreativeTabs tab, NonNullList<ItemStack> items) {
		for (T t : clazz.getEnumConstants()) {
			items.add(new ItemStack(this,1,this.getMetaFromState(getDefaultState().withProperty(LAMP_TYPE, t))));
		}
	}

	@Override
	public boolean canPlaceBlockAt(World worldIn, BlockPos pos)
    {
        for (EnumFacing enumfacing : FACING_ALL.getAllowedValues())
        {
            if (this.canPlaceAt(worldIn, pos, enumfacing))
            {
                return true;
            }
        }

        return false;
    }

    private boolean canPlaceAt(IBlockAccess worldIn, BlockPos pos, EnumFacing facing)
    {
        BlockPos blockpos = pos.offset(facing);
        IBlockState iblockstate = worldIn.getBlockState(blockpos);
        Block block = iblockstate.getBlock();
        BlockFaceShape blockfaceshape = iblockstate.getBlockFaceShape(worldIn, blockpos, facing);

        if ((facing.equals(EnumFacing.UP)||facing.equals(EnumFacing.DOWN)) && this.canPlaceOn(worldIn, blockpos))
        {
            return true;
        }
        else if (facing != EnumFacing.UP && facing != EnumFacing.DOWN)
        {
            return !isExceptBlockForAttachWithPiston(block) && blockfaceshape == BlockFaceShape.SOLID;
        }
        else
        {
            return false;
        }
    }
    
    private boolean canPlaceOn(IBlockAccess worldIn, BlockPos pos)
    {
        IBlockState state = worldIn.getBlockState(pos);
        return state.getBlock().canPlaceTorchOnTop(state, worldIn, pos);
    }
    
    /**
     * Called after the block is set in the Chunk data, but before the Tile Entity is set
     */
    @Override
    public void onBlockAdded(World worldIn, BlockPos pos, IBlockState state)
    {
        this.checkForDrop(worldIn, pos, state);
    }

    /**
     * Called when a neighboring block was changed and marks that this state should perform any checks during a neighbor
     * change. Cases may include when redstone power is updated, cactus blocks popping off due to a neighboring solid
     * block, etc.
     */
    @Override
    public void neighborChanged(IBlockState state, World worldIn, BlockPos pos, Block blockIn, BlockPos fromPos)
    {
        if (!this.checkForDrop(worldIn, pos, state))
        {
            return;
        }
        else
        {
            EnumFacing enumfacing = (EnumFacing)state.getValue(FACING_ALL);
            EnumFacing.Axis enumfacing$axis = enumfacing.getAxis();
            //EnumFacing enumfacing1 = enumfacing.getOpposite();
            BlockPos blockpos = pos.offset(enumfacing);
            boolean flag = false;

            if (enumfacing$axis.isHorizontal() && worldIn.getBlockState(blockpos).getBlockFaceShape(worldIn, blockpos, enumfacing) != BlockFaceShape.SOLID)
            {
                flag = true;
            }
            else if (enumfacing$axis.isVertical() && !this.canPlaceOn(worldIn, blockpos))
            {
                flag = true;
            }

            if (flag)
            {
                this.dropBlockAsItem(worldIn, pos, state, 0);
                worldIn.setBlockToAir(pos);
            }
        }
    }
    
    protected boolean checkForDrop(World worldIn, BlockPos pos, IBlockState state)
    {
        if (state.getBlock() == this && this.canPlaceAt(worldIn, pos, (EnumFacing)state.getValue(FACING_ALL)))
        {
            return true;
        }
        else
        {
            if (worldIn.getBlockState(pos).getBlock() == this)
            {
                this.dropBlockAsItem(worldIn, pos, state, 0);
                worldIn.setBlockToAir(pos);
            }

            return false;
        }
    }
    
	@SideOnly(Side.CLIENT)
	@Override
	public void registerItemBlockModels() {
		for(int i = 0; i< clazz.getEnumConstants().length;i++) {
			IBlockState state = getDefaultState().withProperty(LAMP_TYPE, clazz.getEnumConstants()[i]);
			ModelLoader.setCustomModelResourceLocation(itemblock, this.getMetaFromState(state), new ModelResourceLocation(new ResourceLocation(Techguns.MODID,"lamp_inventory_"+clazz.getEnumConstants()[i].getName()),"inventory"));
		}
	}

	@Override
	public BlockFaceShape getBlockFaceShape(IBlockAccess p_193383_1_, IBlockState state, BlockPos p_193383_3_,
			EnumFacing p_193383_4_) {
		return BlockFaceShape.UNDEFINED;
	}

	@Override
	public int getCamoCount() {
		return 1; //actually maxcamo
	}

	@Override
	public int switchCamo(ItemStack item, boolean back) {
		IBlockState state = this.getStateFromMeta(item.getMetadata());
		int type = state.getValue(LAMP_TYPE).ordinal();
		int newmeta=0;
		if(type==0) {
			newmeta = this.getMetaFromState(this.getDefaultState().withProperty(LAMP_TYPE, clazz.getEnumConstants()[1]));
		} else if (type==1) {
			newmeta = this.getMetaFromState(this.getDefaultState().withProperty(LAMP_TYPE, clazz.getEnumConstants()[0]));
		} else if (type==2) {
			newmeta = this.getMetaFromState(this.getDefaultState().withProperty(LAMP_TYPE, clazz.getEnumConstants()[3]));
		} else if (type==3) {
			newmeta = this.getMetaFromState(this.getDefaultState().withProperty(LAMP_TYPE, clazz.getEnumConstants()[2]));
		}
		item.setItemDamage(newmeta);
		
		return newmeta;
	}

	@Override
	public int getCurrentCamoIndex(ItemStack item) {
		IBlockState state = this.getStateFromMeta(item.getMetadata());
		int type = state.getValue(LAMP_TYPE).ordinal();
		if(type==0 || type==2 ) {
			return 0;
		} else {
			return 1;
		}
	}

	@Override
	public String getCurrentCamoName(ItemStack item) {
		return Techguns.MODID+"."+this.getRegistryName().getPath()+".camoname."+this.getCurrentCamoIndex(item);
	}

	@Override
	public boolean addBlockCamoChangeRecipes() {
		return false;
	}
	
}
