package techguns.blocks;

import net.minecraft.util.IStringSerializable;

public enum EnumTGSandHardTypes implements IStringSerializable{
	BUGNEST_SAND;

	@Override
	public String getName() {
		return name().toLowerCase();
	}
}
