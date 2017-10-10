package techguns.items.guns;

import techguns.entities.projectiles.GenericProjectile;

public interface IProjectileBounceFactory<T extends GenericProjectile> extends IProjectileFactory<T> {

	/*@Override
	default T createProjectile(World world, EntityLivingBase p, float damage, float speed, int TTL, float spread, int dmgDropStart, int dmgDropEnd, float dmgMin, float penetration,
			boolean blockdamage, boolean leftGun, float radius, double gravity) {
		return createProjectile(world, p, damage, speed, TTL, spread, dmgDropStart, dmgDropEnd, dmgMin, penetration, blockdamage, leftGun, radius, gravity, 3);
	}
	 
	T createProjectile(World world, EntityLivingBase p, float damage, float speed, int TTL, float spread, int dmgDropStart, int dmgDropEnd, float dmgMin, float penetration,
				boolean blockdamage, boolean leftGun, float radius, double gravity, int bounces);*/

	public T createBounceProjectile(T proj, double bounceX, double bounceY, double bounceZ);

	
}
