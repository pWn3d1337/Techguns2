package techguns.blocks;

import net.minecraft.util.IStringSerializable;

public interface IEnumOreClusterType extends IStringSerializable {

	public int getMiningLevel();
	public float getMultiplier();
	
}
