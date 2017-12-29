package techguns.blocks;

import net.minecraft.block.Block;
import net.minecraft.block.BlockLadder;
import net.minecraft.block.SoundType;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.NonNullList;
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

}
