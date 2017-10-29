package techguns.items.guns;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.world.World;
import techguns.api.damagesystem.DamageType;
import techguns.entities.projectiles.EnumBulletFirePos;
import techguns.entities.projectiles.GenericProjectile;
import techguns.items.guns.ammo.DamageModifier;

public interface IProjectileFactory<T extends GenericProjectile> {

	 public T createProjectile(GenericGun gun, World world, EntityLivingBase p, float damage, float speed, int TTL,
				float spread, float dmgDropStart, float dmgDropEnd, float dmgMin, float penetration, boolean blockdamage, EnumBulletFirePos firePos, float radius, double gravity);

	/**
	 * Used for Tooltip display
	 * @return
	 */
	 public DamageType getDamageType();
	 
	 public default DamageModifier getDamageModifier() {
		 return DamageModifier.DEFAULT_MODIFIER;
	 }
}
