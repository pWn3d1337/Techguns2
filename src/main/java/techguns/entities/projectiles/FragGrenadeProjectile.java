package techguns.entities.projectiles;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import techguns.api.damagesystem.DamageType;
import techguns.entities.projectiles.GrenadeProjectile.Factory;
import techguns.items.guns.IGrenadeProjectileFactory;

public class FragGrenadeProjectile extends GrenadeProjectile {

	public FragGrenadeProjectile(GrenadeProjectile other, double x, double y, double z) {
		super(other, x, y, z);
	}

	public FragGrenadeProjectile(World worldIn, double posX, double posY, double posZ, float yaw, float pitch,
			float damage, float speed, int TTL, float spread, float penetration, boolean blockdamage,
			EnumBulletFirePos leftGun, double gravity, float radius, int bounces) {
		super(worldIn, posX, posY, posZ, yaw, pitch, damage, speed, TTL, spread, penetration, blockdamage, leftGun, gravity,
				radius, bounces);
		init();
	}

	public FragGrenadeProjectile(World par2World, EntityLivingBase p, float damage, float speed, int TTL, float spread,
			float penetration, boolean blockdamage, EnumBulletFirePos leftGun, double gravity, float radius,
			int bounces) {
		super(par2World, p, damage, speed, TTL, spread, penetration, blockdamage, leftGun, gravity, radius, bounces);
		init();
	}

	public FragGrenadeProjectile(World worldIn) {
		super(worldIn);
		init();
	}
	
	private void init() {
		this.bounces=2;
	}
	
	
	
	@Override
	protected GrenadeProjectile createBounceProjectile(Vec3d hitvec) {
		return new Factory().createBounceProjectile(this, hitvec.x, hitvec.y, hitvec.z);
	}

	public static class Factory implements IGrenadeProjectileFactory<FragGrenadeProjectile> {

		@Override
		public FragGrenadeProjectile createProjectile(World world, EntityLivingBase p, float damage, float speed, int TTL, float spread, float penetration, boolean blockdamage,
				EnumBulletFirePos leftGun, float radius, double gravity, float charge, int bounces) {
			return new FragGrenadeProjectile(world, p, damage, speed, TTL, spread, penetration, blockdamage, leftGun, gravity, radius, bounces);
		}

		@Override
		public DamageType getDamageType() {
			return DamageType.EXPLOSION;
		}

		@Override
		public FragGrenadeProjectile createBounceProjectile(FragGrenadeProjectile proj, double bounceX, double bounceY, double bounceZ) {
			return new FragGrenadeProjectile(proj, bounceX, bounceY, bounceZ);
		}
		
	}

}
