package techguns.blocks;

import net.minecraft.util.IStringSerializable;

public enum EnumTGSlimyType implements IStringSerializable {
	BUGNEST_EGGS;

	@Override
	public String getName() {
		return name().toLowerCase();
	}
}
