package techguns.blocks;

import net.minecraft.util.IStringSerializable;

public enum EnumConcreteType implements IStringSerializable{
	
	CONCRETE_BROWN,
	CONCRETE_BROWN_LIGHT,
	CONCRETE_GREY,
	CONCRETE_GREY_DARK; 

	@Override
	public String getName() {
		return this.name().toLowerCase();
	}

}
