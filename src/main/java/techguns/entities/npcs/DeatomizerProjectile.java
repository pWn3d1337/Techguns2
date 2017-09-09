package techguns.entities.npcs;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.world.World;
import techguns.api.damagesystem.DamageType;
import techguns.entities.projectiles.AdvancedBulletProjectile;
import techguns.entities.projectiles.EnumBulletFirePos;
import techguns.entities.projectiles.GenericProjectile;
import techguns.items.guns.GenericGun;
import techguns.items.guns.IProjectileFactory;

public class DeatomizerProjectile extends GenericProjectile {

	public DeatomizerProjectile(World worldIn) {
		super(worldIn);
	}

	public DeatomizerProjectile(World worldIn, double posX, double posY, double posZ, float yaw, float pitch,
			float damage, float speed, int TTL, float spread, int dmgDropStart, int dmgDropEnd, float dmgMin,
			float penetration, boolean blockdamage, EnumBulletFirePos firePos) {
		super(worldIn, posX, posY, posZ, yaw, pitch, damage, speed, TTL, spread, dmgDropStart, dmgDropEnd, dmgMin, penetration,
				blockdamage, firePos);
	}
	
	public DeatomizerProjectile(World par2World, EntityLivingBase p, float damage, float speed, int TTL, float spread,
			int dmgDropStart, int dmgDropEnd, float dmgMin, float penetration, boolean blockdamage,
			EnumBulletFirePos firePos) {
		super(par2World, p, damage, speed, TTL, spread, dmgDropStart, dmgDropEnd, dmgMin, penetration, blockdamage, firePos);
	}

	public static class Factory implements IProjectileFactory<DeatomizerProjectile> {

		@Override
		public DeatomizerProjectile createProjectile(GenericGun gun, World world, EntityLivingBase p, float damage, float speed, int TTL, float spread, int dmgDropStart, int dmgDropEnd,
				float dmgMin, float penetration, boolean blockdamage, EnumBulletFirePos firePos, float radius, double gravity) {
			return new DeatomizerProjectile(world,p,damage,speed,TTL,spread,dmgDropStart,dmgDropEnd,dmgMin,penetration,blockdamage,firePos);
		}

		@Override
		public DamageType getDamageType() {
			return DamageType.ENERGY;
		}
		
	}
	

}
