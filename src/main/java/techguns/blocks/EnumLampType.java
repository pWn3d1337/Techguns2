package techguns.blocks;

import net.minecraft.util.IStringSerializable;

public enum EnumLampType implements IStringSerializable {
	YELLOW,
	WHITE,
	YELLOW_LANTERN,
	WHITE_LANTERN;

	@Override
	public String getName() {
		return this.name().toLowerCase();
	}
}
