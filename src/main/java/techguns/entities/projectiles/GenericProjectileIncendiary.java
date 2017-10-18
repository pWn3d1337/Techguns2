package techguns.entities.projectiles;

import io.netty.buffer.ByteBuf;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.RayTraceResult.Type;
import net.minecraft.world.World;
import techguns.TGSounds;
import techguns.Techguns;
import techguns.api.damagesystem.DamageType;
import techguns.client.ClientProxy;
import techguns.damagesystem.TGDamageSource;
import techguns.deatheffects.EntityDeathUtils.DeathType;
import techguns.items.guns.GenericGun;
import techguns.items.guns.IProjectileFactory;
import techguns.items.guns.ammo.DamageModifier;

public class GenericProjectileIncendiary extends GenericProjectile {

	protected boolean showFireTrail=false;
	
	public GenericProjectileIncendiary(World worldIn) {
		super(worldIn);
	}

	public GenericProjectileIncendiary(World worldIn, double posX, double posY, double posZ, float yaw, float pitch,
			float damage, float speed, int TTL, float spread, float dmgDropStart, float dmgDropEnd, float dmgMin,
			float penetration, boolean blockdamage, EnumBulletFirePos firePos, boolean fireTrail) {
		super(worldIn, posX, posY, posZ, yaw, pitch, damage, speed, TTL, spread, dmgDropStart, dmgDropEnd, dmgMin, penetration,
				blockdamage, firePos);
		this.showFireTrail=fireTrail;
	}

	public GenericProjectileIncendiary(World par2World, EntityLivingBase p, float damage, float speed, int TTL,
			float spread, float dmgDropStart, float dmgDropEnd, float dmgMin, float penetration, boolean blockdamage,
			EnumBulletFirePos firePos, boolean fireTrail) {
		super(par2World, p, damage, speed, TTL, spread, dmgDropStart, dmgDropEnd, dmgMin, penetration, blockdamage, firePos);
		this.showFireTrail=fireTrail;
	}

	@Override
	protected TGDamageSource getProjectileDamageSource() {
		TGDamageSource src = TGDamageSource.causeFireDamage(this, this.shooter, DeathType.DEFAULT);
		src.armorPenetration = this.penetration;
		src.setNoKnockback();
		return src;
	}

	@Override
	protected void onHitEffect(EntityLivingBase ent, RayTraceResult rayTraceResultIn) {
		super.onHitEffect(ent, rayTraceResultIn);
		if(!ent.isImmuneToFire()) {
			ent.setFire(3);
		}
	}
	
	@Override
	protected void doImpactEffects(Material mat, RayTraceResult rayTraceResult, SoundType sound) {
		//super.doImpactEffects(mat, rayTraceResult, sound);
		
		double x = rayTraceResult.hitVec.x;
    	double y = rayTraceResult.hitVec.y;
    	double z = rayTraceResult.hitVec.z;
    	boolean distdelay=true;
    	
    	float pitch = 0.0f;
    	float yaw = 0.0f;
    	if (rayTraceResult.typeOfHit == Type.BLOCK) {
    		if (rayTraceResult.sideHit == EnumFacing.UP) {
    			pitch = -90.0f;
    		}else if (rayTraceResult.sideHit == EnumFacing.DOWN) {
    			pitch = 90.0f;
    		}else {
    			yaw = rayTraceResult.sideHit.getHorizontalAngle();
    		}
    	}else {
    		pitch = -this.rotationPitch;
    		yaw = -this.rotationYaw;
    	}    	
		
		Techguns.proxy.createFX("Impact_IncendiaryBullet", world, x, y, z, 0.0D, 0.0D, 0.0D, pitch, yaw);
		
		if (this.showFireTrail) {
			this.world.playSound(x, y, z, TGSounds.BULLET_IMPACT_DIRT, SoundCategory.AMBIENT, 1.0f, 1.0f, distdelay);
		}else {
			super.doImpactEffects(mat, rayTraceResult, sound);
		}
			
	}

	@Override
	public void writeSpawnData(ByteBuf buffer) {
		super.writeSpawnData(buffer);
		buffer.writeBoolean(showFireTrail);
	}

	@Override
	public void readSpawnData(ByteBuf additionalData) {
		super.readSpawnData(additionalData);
		this.showFireTrail=additionalData.readBoolean();		
		if (showFireTrail) {
			ClientProxy.get().createFXOnEntity("IncendiaryShotgunTrail", this);
		}
	}

	
	
//	@Override
//	public void onUpdate() {
//		super.onUpdate();
//		if (this.showFireTrail && !this.isInWater()) {
//			this.world.spawnParticle(EnumParticleTypes.FLAME, this.posX - this.motionX * 0.25D,
//						this.posY - this.motionY * 0.25D, this.posZ - this.motionZ * 0.25D, this.motionX*0.5, this.motionY*0.5,
//						this.motionZ*0.5);
//		}
//	}



	public static class Factory implements IProjectileFactory<GenericProjectile>{

		protected DamageModifier mod = new DamageModifier().setDmg(1.1f, 0f);
		
		protected boolean fireTrail;
			
		public Factory(boolean fireTrail) {
			this.fireTrail = fireTrail;
		}

		@Override
		public DamageModifier getDamageModifier() {
			return mod;
		}
		
		@Override
		public GenericProjectileIncendiary createProjectile(GenericGun gun, World world, EntityLivingBase p,
				float damage, float speed, int TTL, float spread, float dmgDropStart, float dmgDropEnd, float dmgMin,
				float penetration, boolean blockdamage, EnumBulletFirePos firePos, float radius, double gravity) {
			return new GenericProjectileIncendiary(world, p, mod.getDamage(damage), speed, TTL, spread, dmgDropStart, dmgDropEnd, mod.getDamage(dmgMin), penetration, blockdamage, firePos, this.fireTrail);
		}

		@Override
		public DamageType getDamageType() {
			return DamageType.FIRE;
		}
		
	}
	
}
