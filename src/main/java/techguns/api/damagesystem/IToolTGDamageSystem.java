package techguns.api.damagesystem;

import net.minecraft.util.DamageSource;
import techguns.damagesystem.TGDamageSource;

public interface IToolTGDamageSystem {
	public TGDamageSource getDamageSource(DamageSource original);
}
