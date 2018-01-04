package techguns.blocks;

import net.minecraft.block.Block;
import net.minecraft.block.BlockLadder;
import net.minecraft.block.SoundType;
import net.minecraft.block.state.BlockFaceShape;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.NonNullList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.event.RegistryEvent.Register;
import techguns.util.BlockUtils;

public class BlockTGSlimyLadder extends BlockLadder implements IGenericBlock {

	protected ItemBlock itemblock;
	
	public BlockTGSlimyLadder(String name) {
		super();
		this.setSoundType(SoundType.SLIME);
		this.init(this, name, true);
		this.setDefaultState(this.getDefaultState().withProperty(FACING, EnumFacing.SOUTH));
	}

	@Override
	public void getSubBlocks(CreativeTabs tab, NonNullList<ItemStack> items) {
		items.add(new ItemStack(this,1,this.getMetaFromState(getDefaultState())));
	}
	
	@Override
	public int damageDropped(IBlockState state) {
		return this.getMetaFromState(getDefaultState());
	}

	@Override
	public ItemBlock createItemBlock() {
		this.itemblock= new GenericItemBlockMeta(this);
		return itemblock;
	}

	@Override
	public void registerBlock(Register<Block> event) {
		event.getRegistry().register(this);
	}

	@Override
	public void registerItemBlockModels() {
		 for (EnumFacing enumfacing : EnumFacing.Plane.HORIZONTAL) {
			 IBlockState state = getDefaultState().withProperty(FACING, enumfacing);
             ModelLoader.setCustomModelResourceLocation(this.itemblock, this.getMetaFromState(state),  new ModelResourceLocation(getRegistryName(),BlockUtils.getBlockStateVariantString(state)));
         }
	}

    protected boolean canAttachTo(World p_193392_1_, BlockPos p_193392_2_, EnumFacing p_193392_3_)
    {
        IBlockState iblockstate = p_193392_1_.getBlockState(p_193392_2_);
        boolean flag = isExceptBlockForAttachWithPiston(iblockstate.getBlock());
        return !flag && iblockstate.getBlockFaceShape(p_193392_1_, p_193392_2_, p_193392_3_) == BlockFaceShape.SOLID && !iblockstate.canProvidePower();
    }
	
	@Override
	public boolean canPlaceBlockAt(World worldIn, BlockPos pos) {
		if (!super.canPlaceBlockAt(worldIn, pos))
			return false;
		for (EnumFacing enumfacing : EnumFacing.Plane.HORIZONTAL) {
			if (this.canAttachTo(worldIn, pos.offset(enumfacing.getOpposite()), enumfacing)) {
				return true;
			}
		}
		return false;
	}

	
}
