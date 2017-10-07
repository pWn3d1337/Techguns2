package techguns.entities.projectiles;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.world.World;
import techguns.api.damagesystem.DamageType;
import techguns.items.guns.GenericGun;
import techguns.items.guns.IProjectileFactory;

public class AdvancedBulletProjectile extends GenericProjectile{


	public AdvancedBulletProjectile(World world, EntityLivingBase p, float damage, float speed, int TTL, float spread,
			float dmgDropStart, float dmgDropEnd, float dmgMin, float penetration, boolean blockdamage, EnumBulletFirePos leftGun) {
		super(world, p, damage, speed, TTL, spread, dmgDropStart, dmgDropEnd, dmgMin, penetration, blockdamage, leftGun);
	}
	
	public AdvancedBulletProjectile(World worldIn) {
		super(worldIn);
	}
	

	public static class Factory implements IProjectileFactory<AdvancedBulletProjectile> {

		@Override
		public AdvancedBulletProjectile createProjectile(GenericGun gun, World world, EntityLivingBase p, float damage, float speed, int TTL, float spread, float dmgDropStart, float dmgDropEnd,
				float dmgMin, float penetration, boolean blockdamage, EnumBulletFirePos firePos, float radius, double gravity) {
			return new AdvancedBulletProjectile(world,p,damage,speed,TTL,spread,dmgDropStart,dmgDropEnd,dmgMin,penetration,blockdamage,firePos);
		}

		@Override
		public DamageType getDamageType() {
			return DamageType.PROJECTILE;
		}
		
	}

}
