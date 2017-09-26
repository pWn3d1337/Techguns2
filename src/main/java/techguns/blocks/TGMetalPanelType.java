package techguns.blocks;

import net.minecraft.util.IStringSerializable;

public enum TGMetalPanelType implements IStringSerializable{
	CONTAINER_RED,
	CONTAINER_GREEN,
	CONTAINER_BLUE,
	CONTAINER_ORANGE,
	PANEL_LARGE_BORDER,
	STEELFRAME_BLUE,
	STEELFRAME_DARK,
	STEELFRAME_SCAFFOLD;

	@Override
	public String getName() {
		return name().toLowerCase();
	}

	public boolean isEnabled() {
		return true;
	}
}