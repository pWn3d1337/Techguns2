package techguns.blocks;

import net.minecraft.block.SoundType;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.IStringSerializable;

public class GenericBlockMetaEnumCamoChangeableLightlevel<T extends Enum<T> & IStringSerializable & IEnumLightlevel> extends GenericBlockMetaEnumCamoChangeable<T>{

	public GenericBlockMetaEnumCamoChangeableLightlevel(String name, Material mat, Class<T> clazz) {
		super(name, mat, clazz);
	}
	
	public GenericBlockMetaEnumCamoChangeableLightlevel(String name, Material mat, MapColor mc, SoundType soundType,
			Class<T> clazz) {
		super(name, mat, mc, soundType, clazz);
	}
	
	@Override
	public int getLightValue(IBlockState state) {
		IEnumLightlevel type = state.getValue(TYPE);
		return type.getLightlevel();
	}
}
