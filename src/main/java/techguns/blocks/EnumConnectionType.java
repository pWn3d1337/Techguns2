package techguns.blocks;

import net.minecraft.util.IStringSerializable;

public enum EnumConnectionType implements IStringSerializable {
	NONE,
	W,
	S,
	SW,
	E,
	EW,
	ES,
	ESW,
	N,
	NW,
	NS,
	NSW,
	NE,
	NEW,
	NES,
	NESW;

	@Override
	public String getName() {
		return name().toLowerCase();
	}

	public static EnumConnectionType get(boolean n, boolean e, boolean s, boolean w) {
		int num=0;
		if(n) num+=8;
		if(e) num+=4;
		if(s) num+=2;
		if(w) num+=1;
		return EnumConnectionType.values()[num];
	}
	
	
}
