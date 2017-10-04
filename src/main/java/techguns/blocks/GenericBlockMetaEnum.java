package techguns.blocks;

import net.minecraft.block.SoundType;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IStringSerializable;
import net.minecraft.util.NonNullList;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import techguns.Techguns;
import techguns.items.armors.ICamoChangeable;
import techguns.util.BlockUtils;

public class GenericBlockMetaEnum<T extends Enum<T> & IStringSerializable> extends GenericBlock implements ICamoChangeable {

	protected Class<T> clazz;
	
	protected BlockStateContainer blockStateOverride;
	
	public PropertyEnum<T> TYPE;
	
	protected GenericItemBlockMeta itemblock;
	
	public GenericBlockMetaEnum(String name, Material mat, Class<T> clazz) {
		this(name,mat,mat.getMaterialMapColor(),SoundType.STONE, clazz);
	}

	public GenericBlockMetaEnum(String name, Material mat, MapColor mc,  SoundType soundType, Class<T> clazz) {
		super(name, mat, mc);
		this.clazz=clazz;
		TYPE = PropertyEnum.create("type",clazz);
		this.blockStateOverride = new BlockStateContainer.Builder(this).add(TYPE).build();
		this.setDefaultState(this.getBlockState().getBaseState());
		this.setSoundType(soundType);
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

	@Override
	public int getCamoCount() {
		return clazz.getEnumConstants().length-1;
	}

	@Override
	public int switchCamo(ItemStack item, boolean back) {
		IBlockState state = this.getStateFromMeta(item.getMetadata());
		
		int type = state.getValue(TYPE).ordinal();
		
		if(back) {
			type--;
			if(type<0) {
				type=clazz.getEnumConstants().length-1;
			}
		} else {
			type++;
			if(type>=clazz.getEnumConstants().length) {
				type=0;
			}
		}
		int newmeta = this.getMetaFromState(state.withProperty(TYPE, clazz.getEnumConstants()[type]));
		item.setItemDamage(newmeta);
		
		return newmeta;
	}

	@Override
	public int getCurrentCamoIndex(ItemStack item) {
		int meta = item.getMetadata();
		IBlockState state = this.getStateFromMeta(meta);
		return state.getValue(TYPE).ordinal();
	}

	@Override
	public String getCurrentCamoName(ItemStack item) {
		return "tile."+Techguns.MODID+"."+this.getRegistryName().getResourcePath()+"."+getCurrentCamoIndex(item)+".name";
	}

	
	
}
