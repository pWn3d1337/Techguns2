package techguns.blocks;

import net.minecraft.util.IStringSerializable;

public enum EnumDoorState implements IStringSerializable {
	CLOSED,
	OPENED,
	OPENING,
	CLOSING;

	@Override
	public String getName() {
		return name().toLowerCase();
	}

}
