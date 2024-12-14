package techguns.blocks;

import net.minecraft.block.SoundType;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IStringSerializable;
import techguns.Techguns;
import techguns.items.armors.ICamoChangeable;

public class GenericBlockMetaEnumCamoChangeable<T extends Enum<T> & IStringSerializable> extends GenericBlockMetaEnum<T> implements ICamoChangeable {

	/**
	 * Only used when chisel is loaded
	 */
	public Object[] variationData;
	/**
	 * Only used when chisel is loaded
	 */
	protected int chiselIndex;
	
	public GenericBlockMetaEnumCamoChangeable(String name, Material mat, Class<T> clazz) {
		super(name, mat, clazz);
	}

	public GenericBlockMetaEnumCamoChangeable(String name, Material mat, MapColor mc, SoundType soundType,
			Class<T> clazz) {
		super(name, mat, mc, soundType, clazz);
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
		return "tile."+Techguns.MODID+"."+this.getRegistryName().getPath()+"."+getCurrentCamoIndex(item)+".name";
	}

}
