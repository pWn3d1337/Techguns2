package techguns.blocks;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.NonNullList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.event.RegistryEvent.Register;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import techguns.Techguns;
import techguns.util.BlockUtils;

public class BlockTGOre extends GenericBlock {

	public static PropertyEnum<EnumOreType> ORE_TYPE = PropertyEnum.create("type",EnumOreType.class);
	protected GenericItemBlockMeta itemblock;
	
	public BlockTGOre(String name) {
		super(name, Material.ROCK);
		this.setDefaultState(this.blockState.getBaseState().withProperty(ORE_TYPE, EnumOreType.ORE_COPPER));
	}
	
	@Override
	public ItemBlock createItemBlock() {
		GenericItemBlockMeta itemblock =  new GenericItemBlockMeta(this);
		this.itemblock=itemblock;
		return itemblock;
	}
	
	@Override
	public int getMetaFromState(IBlockState state) {
		return state.getValue(ORE_TYPE).ordinal();
	}

	@Override
	public IBlockState getStateFromMeta(int meta) {
		return this.getDefaultState()
	    .withProperty(ORE_TYPE, EnumOreType.class.getEnumConstants()[meta]);
    }
	
	@Override
	public int damageDropped(IBlockState state) {
		return this.getMetaFromState(getDefaultState().withProperty(ORE_TYPE, state.getValue(ORE_TYPE)));
	}
	
	@Override
	public int getLightValue(IBlockState state) {
		EnumOreType type = state.getValue(ORE_TYPE);
		return type.getLightlevel();
	}

	@Override
	public float getBlockHardness(IBlockState blockState, World worldIn, BlockPos pos) {
		EnumOreType type = blockState.getValue(ORE_TYPE);
		return type.getHardness();
	}

	@Override
	public String getHarvestTool(IBlockState state) {
		return "pickaxe";
	}

	@Override
	public int getHarvestLevel(IBlockState state) {
		EnumOreType type = state.getValue(ORE_TYPE);
		return type.getMininglevel();
	}

	@SideOnly(Side.CLIENT)
	@Override
	public void registerItemBlockModels() {
		for(int i = 0; i< EnumOreType.class.getEnumConstants().length;i++) {
			IBlockState state = getDefaultState().withProperty(ORE_TYPE, EnumOreType.class.getEnumConstants()[i]);
			ModelLoader.setCustomModelResourceLocation(this.itemblock, this.getMetaFromState(state), new ModelResourceLocation(getRegistryName(),BlockUtils.getBlockStateVariantString(state)));
		}
	}

	@Override
	public void getSubBlocks(CreativeTabs tab, NonNullList<ItemStack> items) {
		if(tab == this.getCreativeTabToDisplayOn()){
			for (EnumOreType t : EnumOreType.class.getEnumConstants()) {
				if (t.isEnabled()) {
					items.add(new ItemStack(this,1,this.getMetaFromState(getDefaultState().withProperty(ORE_TYPE, t))));
				}
			}
		}
	}
	
	@Override
	protected BlockStateContainer createBlockState() {
		return new BlockStateContainer(this, new IProperty[] { ORE_TYPE });
	}
}
