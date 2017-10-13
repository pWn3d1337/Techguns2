package techguns.blocks;

import net.minecraft.util.IStringSerializable;

public enum EnumMonsterSpawnerType implements IStringSerializable {
	HOLE;

	@Override
	public String getName() {
		return name().toLowerCase();
	}

}
