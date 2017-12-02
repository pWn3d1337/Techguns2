package techguns.blocks;

import net.minecraft.util.IStringSerializable;

public enum EnumDebugBlockType implements IStringSerializable {
	AIRMARKER,
	ANTIAIRMARKER,
	INTERIORMARKER_NORTH,
	INTERIORMARKER_EAST,
	INTERIORMARKER_SOUTH,
	INTERIORMARKER_WEST;

	@Override
	public String getName() {
		return name().toLowerCase();
	}
}
