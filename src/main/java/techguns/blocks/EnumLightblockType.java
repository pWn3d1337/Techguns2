package techguns.blocks;

import net.minecraft.util.IStringSerializable;

public enum EnumLightblockType implements IStringSerializable {
	NEONTUBES2,
	NEONTUBES2_ROTATED,
	NEONTUBES4,
	NEONTUBES4_ROTATED,
	NEONSQUARE_WHITE,;

	@Override
	public String getName() {
		return name().toLowerCase();
	}
}
