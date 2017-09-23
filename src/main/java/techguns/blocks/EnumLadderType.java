package techguns.blocks;

import net.minecraft.util.IStringSerializable;

public enum EnumLadderType implements IStringSerializable {
	METAL,
	SHINY,
	RUSTY,
	CARBON;

	@Override
	public String getName() {
		return this.name().toLowerCase();
	}
}