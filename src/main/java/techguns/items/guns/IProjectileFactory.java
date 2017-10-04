package techguns.items.guns;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.world.World;
import techguns.api.damagesystem.DamageType;
import techguns.entities.projectiles.EnumBulletFirePos;
import techguns.entities.projectiles.GenericProjectile;

public interface IProjectileFactory<T extends GenericProjectile> {

	 public T createProjectile(GenericGun gun, World world, EntityLivingBase p, float damage, float speed, int TTL,
				float spread, int dmgDropStart, int dmgDropEnd, float dmgMin, float penetration, boolean blockdamage, EnumBulletFirePos firePos, float radius, double gravity);

	/**
	 * Used for Tooltip display
	 * @return
	 */
	 public DamageType getDamageType();
}
