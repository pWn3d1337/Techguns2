package techguns.api.npc;

import techguns.damagesystem.TGDamageSource;

public interface INpcTGDamageSystem {
	public float getTotalArmorAgainstType(TGDamageSource dmgsrc);
	public float getPenetrationResistance(TGDamageSource dmgsrc);
}

