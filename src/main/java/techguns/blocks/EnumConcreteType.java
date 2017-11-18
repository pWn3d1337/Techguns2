package techguns.blocks;

import net.minecraft.util.IStringSerializable;

public enum EnumConcreteType implements IStringSerializable{
	
	CONCRETE_BROWN,
	CONCRETE_BROWN_LIGHT,
	CONCRETE_GREY,
	CONCRETE_GREY_DARK,
	CONCRETE_BROWN_PIPES,
	CONCRETE_BROWN_LIGHT_SCAFF;

	@Override
	public String getName() {
		return this.name().toLowerCase();
	}

}
