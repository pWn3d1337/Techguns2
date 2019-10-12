package techguns.blocks;

import net.minecraft.util.IStringSerializable;

public enum EnumDoorType implements IStringSerializable {
	METAL,
	HANGAR_UP,
	HANGAR_DOWN,
	NETHER;

	@Override
	public String getName() {
		return name().toLowerCase();
	}

}
