package techguns.capabilities;

import net.minecraft.entity.EntityLivingBase;
import techguns.api.capabilities.AttackTime;
import techguns.api.capabilities.ITGShooterValues;

public class TGShooterValues implements ITGShooterValues {

	protected AttackTime attackTime = new AttackTime();
	
	@Override
	public AttackTime getAttackTime(boolean offHand) {
		return attackTime;
	}
	
	public static TGShooterValues get(EntityLivingBase ent){
		return (TGShooterValues) ent.getCapability(TGShooterValuesCapProvider.TG_SHOOTER_VALUES, null);
	}

	@Override
	public boolean isRecoiling(boolean offHand) {
		return attackTime.isRecoiling();
	}

	@Override
	public boolean isReloading(boolean offHand) {
		return attackTime.isReloading();
	}
}
