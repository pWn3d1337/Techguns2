package techguns.entities.projectiles;

import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import techguns.TGPackets;
import techguns.Techguns;
import techguns.api.damagesystem.DamageType;
import techguns.damagesystem.TGDamageSource;
import techguns.deatheffects.EntityDeathUtils.DeathType;
import techguns.items.guns.GenericGun;
import techguns.items.guns.IProjectileFactory;
import techguns.packets.PacketSpawnParticle;

public class LaserProjectile extends AbstractBeamProjectile{

	public LaserProjectile(World worldIn, double posX, double posY, double posZ, float yaw, float pitch, float damage,
			float speed, int TTL, float spread, float dmgDropStart, float dmgDropEnd, float dmgMin, float penetration,
			boolean blockdamage, EnumBulletFirePos leftGun) {
		super(worldIn, posX, posY, posZ, yaw, pitch, damage, speed, TTL, spread, dmgDropStart, dmgDropEnd, dmgMin, penetration,
				blockdamage, leftGun);
		trace();
	}
	
	public LaserProjectile(World par2World, EntityLivingBase p, float damage, float speed, int TTL, float spread,
			float dmgDropStart, float dmgDropEnd, float dmgMin, float penetration, boolean blockdamage, EnumBulletFirePos leftGun) {
		super(par2World, p, damage, speed, TTL, spread, dmgDropStart, dmgDropEnd, dmgMin, penetration, blockdamage, leftGun);
		trace();
	}

	public LaserProjectile(World worldIn) {
		super(worldIn);
	}

	@Override
	protected void doImpactEffects(Material mat, RayTraceResult rayTraceResult, SoundType sound) {
		if(!world.isRemote) {
			Vec3d hitVec = rayTraceResult.hitVec;
			TGPackets.network.sendToAllAround(new PacketSpawnParticle("LaserGunImpact", hitVec.x, hitVec.y, hitVec.z), TGPackets.targetPointAroundEnt(this, 35.0f));		
		}
		//Techguns.proxy.createFX("LaserGunImpact", world, hitVec.x, hitVec.y, hitVec.z,0,0,0);
	}
	
//	protected void createImpactEffect(Vec3d hitVec) {
//		TGPackets.network.sendToAllAround(new PacketSpawnParticle("LaserGunImpact", hitVec.x, hitVec.y, hitVec.z), TGPackets.targetPointAroundEnt(this, 35.0f));
//	}
	
	@Override
	public void onUpdate() {
		--this.ticksToLive;
		if (this.ticksToLive<=0){
			this.setDead();
		}
	}
	
	@Override
	protected TGDamageSource getProjectileDamageSource() {
		TGDamageSource src = TGDamageSource.causeEnergyDamage(this, this.shooter, DeathType.LASER);
    	src.goreChance = 1.0f;
    	src.armorPenetration = this.penetration;
    	src.setNoKnockback();
    	return src;
	}
	
	public static class Factory implements IProjectileFactory<LaserProjectile> {
		@Override
		public LaserProjectile createProjectile(GenericGun gun, World world, EntityLivingBase p, float damage, float speed,
				int TTL, float spread, float dmgDropStart, float dmgDropEnd, float dmgMin, float penetration,
				boolean blockdamage, EnumBulletFirePos firePos, float radius, double gravity) {
			return new LaserProjectile(world, p, damage, speed, 7, spread, dmgDropStart, dmgDropEnd, dmgMin, penetration, blockdamage, firePos);
		}

		@Override
		public DamageType getDamageType() {
			return DamageType.ENERGY;
		}
	}

	@Override
	public boolean shouldRenderInPass(int pass) {
		return pass==1;
	}
	
	
}
