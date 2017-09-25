package techguns.blocks;

import net.minecraft.util.IStringSerializable;

public enum EnumDoorType implements IStringSerializable {
	METAL;

	@Override
	public String getName() {
		return name().toLowerCase();
	}

}
