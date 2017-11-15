package techguns.items.guns;

import java.util.HashMap;

import techguns.entities.projectiles.GenericProjectile;
import techguns.items.guns.ammo.AmmoType;
import techguns.items.guns.ammo.AmmoVariant;

public class ProjectileSelector<T extends GenericProjectile> {

	protected HashMap<String, IProjectileFactory<T>> map = new HashMap<>();
	public AmmoType ammoType;
	
	/**
	 * Amount of Variants in type has to be the same as number of passed Factories
	 * @param type
	 * @param factories
	 */
	public ProjectileSelector(AmmoType type, IProjectileFactory<T>... factories) {
		this.ammoType=type;
		for(int i=0;i<type.getVariants().size();i++) {
			AmmoVariant v = type.getVariants().get(i);
			map.put(v.getKey(), factories[i]);
		}
		
	}

	public IProjectileFactory<T> getFactoryForType(String key){
		if(map.containsKey(key)) {
			return map.get(key);
		}
		return map.get("default");
	}
}
