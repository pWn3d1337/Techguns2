package techguns.items.guns;

import techguns.entities.projectiles.GenericProjectile;
import techguns.items.guns.ammo.AmmoType;

public class ChargedProjectileSelector<T extends GenericProjectile> extends ProjectileSelector<T> {

	public ChargedProjectileSelector(AmmoType type, IChargedProjectileFactory<T>... factories) {
		super(type, factories);
	}

	@Override
	public IChargedProjectileFactory<T> getFactoryForType(String key) {
		return (IChargedProjectileFactory<T>) super.getFactoryForType(key);
	}
	
	
}
