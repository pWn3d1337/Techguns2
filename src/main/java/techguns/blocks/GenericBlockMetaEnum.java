package techguns.blocks;

import net.minecraft.block.Block;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IStringSerializable;
import net.minecraft.util.NonNullList;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.event.RegistryEvent.Register;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import techguns.Techguns;
import techguns.util.BlockUtils;

public class GenericBlockMetaEnum<T extends Enum<T> & IStringSerializable> extends GenericBlock {

	protected Class<T> clazz;
	
	protected BlockStateContainer blockStateOverride;
	
	public PropertyEnum<T> TYPE;
	
	protected GenericItemBlockMeta itemblock;
	
	public GenericBlockMetaEnum(String name, Material mat, Class<T> clazz) {
		this(name,mat,mat.getMaterialMapColor(),clazz);
	}

	public GenericBlockMetaEnum(String name, Material mat, MapColor mc,  Class<T> clazz) {
		super(name, mat, mc);
		this.clazz=clazz;
		TYPE = PropertyEnum.create("type",clazz);
		this.blockStateOverride = new BlockStateContainer.Builder(this).add(TYPE).build();
		this.setDefaultState(this.getBlockState().getBaseState());
	}

	@Override
	public BlockStateContainer getBlockState() {
		return this.blockStateOverride;
	}
	
	@Override
	public int damageDropped(IBlockState state) {
		return this.getMetaFromState(getDefaultState().withProperty(TYPE, state.getValue(TYPE)));
	}
	
	@Override
	public void getSubBlocks(CreativeTabs tab, NonNullList<ItemStack> items) {
		if(tab == this.getCreativeTabToDisplayOn()){
			for (T t : clazz.getEnumConstants()) {
				items.add(new ItemStack(this,1,this.getMetaFromState(getDefaultState().withProperty(TYPE, t))));
			}
		}
	}
	
	@Override
	public ItemBlock createItemBlock() {
		this.itemblock =  new GenericItemBlockMeta(this);
		return itemblock;
	}

	@Override
	public int getMetaFromState(IBlockState state) {
		return state.getValue(TYPE).ordinal();
	}

	@Override
	public IBlockState getStateFromMeta(int meta) {
		return this.getDefaultState()
	    .withProperty(TYPE, clazz.getEnumConstants()[meta]);
    }
	
	public Class<T> getClazz() {
		return clazz;
	}

	public GenericItemBlockMeta getItemblock() {
		return itemblock;
	}

	@SideOnly(Side.CLIENT)
	@Override
	public void registerItemBlockModels() {
		for(int i = 0; i< clazz.getEnumConstants().length;i++) {
			IBlockState state = getDefaultState().withProperty(TYPE, clazz.getEnumConstants()[i]);
			ModelLoader.setCustomModelResourceLocation(this.itemblock, this.getMetaFromState(state), new ModelResourceLocation(getRegistryName(),BlockUtils.getBlockStateVariantString(state)));
		}
	}
}
