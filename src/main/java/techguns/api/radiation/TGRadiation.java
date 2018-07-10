package techguns.api.radiation;

import net.minecraft.entity.ai.attributes.IAttribute;
import net.minecraft.entity.ai.attributes.RangedAttribute;
import techguns.Techguns;

public class TGRadiation {
	/**
	 * Radiation is calculated as Level of Radiation effect - radiation resistance. Radiation strength will be in the low integer area [0-4]
	 * this field will be initialized in the pre-init phase of Techguns
	 */
	public static IAttribute RADIATION_RESISTANCE = null;
}
