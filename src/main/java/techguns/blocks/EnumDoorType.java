package techguns.blocks;

import net.minecraft.util.IStringSerializable;

public enum EnumDoorType implements IStringSerializable {
	METAL,
	HANGAR_UP;

	@Override
	public String getName() {
		return name().toLowerCase();
	}

}
