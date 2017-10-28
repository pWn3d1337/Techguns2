package techguns.entities.projectiles;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import techguns.TGPackets;
import techguns.api.damagesystem.DamageType;
import techguns.damagesystem.TGDamageSource;
import techguns.deatheffects.EntityDeathUtils.DeathType;
import techguns.items.guns.GenericGun;
import techguns.items.guns.IProjectileFactory;
import techguns.packets.PacketSpawnParticle;

public class BlasterProjectile extends GenericProjectile {

	public BlasterProjectile(World worldIn) {
		super(worldIn);
	}

	public BlasterProjectile(World worldIn, double posX, double posY, double posZ, float yaw, float pitch, float damage, float speed, int TTL, float spread, float dmgDropStart,
			float dmgDropEnd, float dmgMin, float penetration, boolean blockdamage, EnumBulletFirePos leftGun) {
		super(worldIn, posX, posY, posZ, yaw, pitch, damage, speed, TTL, spread, dmgDropStart, dmgDropEnd, dmgMin, penetration, blockdamage, leftGun);
	}

	public BlasterProjectile(World par2World, EntityLivingBase p, float damage, float speed, int TTL, float spread, float dmgDropStart, float dmgDropEnd, float dmgMin,
			float penetration, boolean blockdamage, EnumBulletFirePos leftGun) {
		super(par2World, p, damage, speed, TTL, spread, dmgDropStart, dmgDropEnd, dmgMin, penetration, blockdamage, leftGun);
	}

	@Override
	protected TGDamageSource getProjectileDamageSource() {
		TGDamageSource src = TGDamageSource.causeEnergyDamage(this, this.shooter, DeathType.LASER);
		src.armorPenetration = this.penetration;
		src.setNoKnockback();
		return src;
	}

	public static class Factory implements IProjectileFactory<BlasterProjectile> {

		@Override
		public BlasterProjectile createProjectile(GenericGun gun, World world, EntityLivingBase p, float damage, float speed, int TTL, float spread, float dmgDropStart, float dmgDropEnd,
				float dmgMin, float penetration, boolean blockdamage, EnumBulletFirePos firePos, float radius, double gravity) {
			return new BlasterProjectile(world,p,damage,speed,TTL,spread,dmgDropStart,dmgDropEnd,dmgMin,penetration,blockdamage,firePos);
		}

		@Override
		public DamageType getDamageType() {
			return DamageType.ENERGY;
		}
		
	}

	@Override
	protected void hitBlock(RayTraceResult raytraceResultIn) {
		if (!this.world.isRemote){
			Vec3d hitVec = raytraceResultIn.hitVec;
			TGPackets.network.sendToAllAround(new PacketSpawnParticle("LaserGunImpact", hitVec.x, hitVec.y, hitVec.z), TGPackets.targetPointAroundEnt(this, 35.0f));
		}
	}

	@Override
	protected float inWaterUpdateBehaviour(float f1) {
		//no slowdown/bubbles in water

		if (this.isWet()) {
			this.extinguish();
		}
		return f1;
	}

	
	
}
