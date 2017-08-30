package techguns.blocks.machines;

import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.property.IUnlistedProperty;

public class PropertyUnlistedRotation implements IUnlistedProperty<EnumFacing> {

	@Override
	public String getName() {
		return "unlisted_rotation";
	}

	@Override
	public boolean isValid(EnumFacing value) {
		return value==EnumFacing.NORTH || value == EnumFacing.SOUTH || value==EnumFacing.EAST || value==EnumFacing.WEST;
	}

	@Override
	public Class<EnumFacing> getType() {
		return EnumFacing.class;
	}

	@Override
	public String valueToString(EnumFacing value) {
		return value.getName();
	}

}
