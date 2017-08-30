package techguns.items.guns;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.world.World;
import techguns.entities.projectiles.EnumBulletFirePos;
import techguns.entities.projectiles.GenericProjectile;

public interface IChargedProjectileFactory<T extends GenericProjectile> extends IProjectileFactory<T> {

	 public T createChargedProjectile(World world, EntityLivingBase p, float damage, float speed, int TTL, float spread,
				int dmgDropStart, int dmgDropEnd, float dmgMin, float penetration, boolean blockdamage, EnumBulletFirePos firePos, float radius, double gravity, float charge, int ammoConsumed);

	
}
