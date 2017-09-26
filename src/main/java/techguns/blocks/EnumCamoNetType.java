package techguns.blocks;

import net.minecraft.util.IStringSerializable;

public enum EnumCamoNetType implements IStringSerializable {
	WOOD,
	DESERT,
	SNOW;

	@Override
	public String getName() {
		return name().toLowerCase();
	}
}
