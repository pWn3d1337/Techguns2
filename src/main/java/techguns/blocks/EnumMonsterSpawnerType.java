package techguns.blocks;

import net.minecraft.util.IStringSerializable;

public enum EnumMonsterSpawnerType implements IStringSerializable {
	HOLE,
	SOLDIER_SPAWN;

	@Override
	public String getName() {
		return name().toLowerCase();
	}

}
