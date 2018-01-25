package techguns.blocks;

import net.minecraft.util.IStringSerializable;

public enum EnumNetherMetalType implements IStringSerializable {
	PANEL,
	GRATE1,
	GRATE2,
	GREY_DARK,
	GREY,
	GREY_TILES;

	@Override
	public String getName() {
		return name().toLowerCase();
	}
}
