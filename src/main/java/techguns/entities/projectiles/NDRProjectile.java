package techguns.entities.projectiles;

import io.netty.buffer.ByteBuf;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import techguns.TGPackets;
import techguns.TGRadiationSystem;
import techguns.Techguns;
import techguns.api.damagesystem.DamageType;
import techguns.client.ClientProxy;
import techguns.damagesystem.TGDamageSource;
import techguns.deatheffects.EntityDeathUtils.DeathType;
import techguns.items.guns.GenericGun;
import techguns.items.guns.IProjectileFactory;
import techguns.packets.PacketSpawnParticle;
import techguns.util.MathUtil;

public class NDRProjectile extends AbstractBeamProjectile {

	public static final int BEAM_LIFETIME = 10;
	
	public int shooterID;
	
	public NDRProjectile(World worldIn) {
		super(worldIn);
		if (worldIn.isRemote) {
			Techguns.proxy.createFXOnEntity("BeamGunMuzzleFX", this);
		}
	}
	
	public NDRProjectile(World par2World, EntityLivingBase p, float damage, float speed, int TTL, float spread,
			float dmgDropStart, float dmgDropEnd, float dmgMin, float penetration, boolean blockdamage, EnumBulletFirePos leftGun) {
		super(par2World, p, damage, speed, TTL, spread, dmgDropStart, dmgDropEnd, dmgMin, penetration, blockdamage, leftGun);
	}

	@Override
	public void writeSpawnData(ByteBuf buffer) {
		super.writeSpawnData(buffer);
		if (this.shooter != null)
			buffer.writeInt(this.shooter.getEntityId());
		else buffer.writeInt(0);
	}

	@Override
	public void readSpawnData(ByteBuf additionalData) {
		super.readSpawnData(additionalData);
		this.shooterID=additionalData.readInt();
	}
	
	@Override
	public void onUpdate() {
		//Update position to shooter
		double targetX = 0;
		double targetY = 0;
		double targetZ = 0;
		double dx, dy, dz;			
		float f = 100.0F;
		Vec3d vec3;
	    Vec3d motion;
		if (this.shooter == null) {
			Entity e = this.world.getEntityByID(shooterID);
			if (e instanceof EntityLivingBase) this.shooter = (EntityLivingBase)e;
		}
		if (shooter != null) {
			dx = (double) (-MathHelper.sin(shooter.rotationYawHead / 180.0F
					* (float) Math.PI)
					* MathHelper.cos(shooter.rotationPitch / 180.0F
							* (float) Math.PI) * f);
			dz = (double) (MathHelper.cos(shooter.rotationYawHead / 180.0F
					* (float) Math.PI)
					* MathHelper.cos(shooter.rotationPitch / 180.0F
							* (float) Math.PI) * f);
			dy = (double) (-MathHelper.sin((shooter.rotationPitch) / 180.0F
					* (float) Math.PI) * f);
	
			this.posX = shooter.posX;				
		    this.posY = shooter.posY;
		    this.posZ = shooter.posZ;
		    		    
			motion = new Vec3d(dx, dy, dz).normalize();
			this.motionX = motion.x * speed;
			this.motionY = motion.y * speed;
			this.motionZ = motion.z * speed;
			
			//System.out.println(String.format("motion: %.3f, %.3f, %.3f", this.motionX, this.motionY, this.motionZ));
			

		    if (Techguns.proxy.isClientPlayerAndIn1stPerson(shooter)){
		    	 Vec3d offset = this.getFPOffset();
		    	 offset = offset.rotatePitch((float) (shooter.rotationPitch*MathUtil.D2R)).rotateYaw((float) ((-90.0-shooter.rotationYawHead)*MathUtil.D2R));
		         //MathUtil.rotateAroundZ(offset, shooter.rotationPitch*MathUtil.D2R);
		         //MathUtil.rotateAroundY(offset, ((-90.0-shooter.rotationYawHead)*MathUtil.D2R));		         
		         this.posX+=offset.x;
		         this.posY+=offset.y+shooter.getEyeHeight();
		         this.posZ+=offset.z;		         
//TODO
//		    } else if (shooter instanceof NPCTurret) {
//		    	Vec3 offset = Vec3.createVectorHelper(0, shooter.getEyeHeight() -0.10000000149011612D, -0.1);
//		         MathUtil.rotateAroundZ(offset, shooter.rotationPitch*MathUtil.D2R);
//		         MathUtil.rotateAroundY(offset, ((-90.0-shooter.rotationYawHead)*MathUtil.D2R));
//		         
//		         this.posX+=offset.xCoord;
//		         this.posY+=offset.yCoord;
//		         this.posZ+=offset.zCoord;	    	
		    }else {
		    	this.posX-=(double)(MathHelper.cos(shooter.rotationYaw / 180.0F * (float)Math.PI) * 0.16F);
		        this.posY += shooter.getEyeHeight() -0.10000000149011612D + this.get3PYOffset();
		        this.posZ -= (double)(MathHelper.sin(shooter.rotationYaw / 180.0F * (float)Math.PI) * 0.16F);
		    }
		    
		    vec3 = new Vec3d(this.posX, this.posY, this.posZ);
		    
		    this.rotationPitch = shooter.rotationPitch;
		    this.rotationYaw = shooter.rotationYawHead;
//		}else {
//			
//			dx = (double) (-MathHelper.sin(this.rotationYaw / 180.0F
//					* (float) Math.PI)
//					* MathHelper.cos(this.rotationPitch / 180.0F
//							* (float) Math.PI) * f);
//			dz = (double) (MathHelper.cos(this.rotationYaw / 180.0F
//					* (float) Math.PI)
//					* MathHelper.cos(this.rotationPitch / 180.0F
//							* (float) Math.PI) * f);
//			dy = (double) (-MathHelper.sin((this.rotationPitch) / 180.0F
//					* (float) Math.PI) * f);
//			vec3 = new Vec3d(this.posX, this.posY, this.posZ);
		}
		//---------
		trace();
		--this.ticksToLive;
		if (this.ticksToLive<=0){
			this.setDead();
		}	
	}
	
	@Override
	protected void doImpactEffects(Material mat, RayTraceResult rayTraceResult, SoundType sound) {
		Vec3d hitVec = rayTraceResult.hitVec;
		//TGPackets.network.sendToAllAround(new PacketSpawnParticle("BeamGunImpactFX", hitVec.x, hitVec.y, hitVec.z), TGPackets.targetPointAroundEnt(this, 35.0f));
		Techguns.proxy.createFX("BeamGunImpactFX", this.world, hitVec.x, hitVec.y, hitVec.z, 0f, 0f, 0f);

	}
	
//	@Override
//	protected void createImpactEffect(Vec3d hitVec) {
//		TGPackets.network.sendToAllAround(new PacketSpawnParticle("BeamGunImpactFX", hitVec.x, hitVec.y, hitVec.z), TGPackets.targetPointAroundEnt(this, 35.0f));
//	}
	
	@Override
	public boolean shouldRenderInPass(int pass) {
		return pass==1;
	}

	
	public Vec3d getFPOffset(){
		//return new Vec3d(0,0,0);
		return new Vec3d(0, -0.08, 0.12);
	}

	public float get3PYOffset(){
		return 0.0f;
	}
	
	public float getBeam3PYOffset() {
		return 0.0f;
	}
	
	
	
	@Override
	protected void onHitEffect(EntityLivingBase ent, RayTraceResult rayTraceResultIn) {
		super.onHitEffect(ent, rayTraceResultIn);
		if(TGRadiationSystem.isEnabled()) {
			ent.addPotionEffect(new PotionEffect(TGRadiationSystem.radiation_effect,40,4,false,true));
		}
	}

	@Override
	protected TGDamageSource getProjectileDamageSource() {
		TGDamageSource src = TGDamageSource.causeRadiationDamage(this, this.shooter, DeathType.LASER);
		src.armorPenetration = this.penetration;
		src.setNoKnockback();
		return src;
	}



	public static class Factory implements IProjectileFactory<NDRProjectile> {
		@Override
		public NDRProjectile createProjectile(GenericGun gun, World world, EntityLivingBase p, float damage, float speed,
				int TTL, float spread, float dmgDropStart, float dmgDropEnd, float dmgMin, float penetration,
				boolean blockdamage, EnumBulletFirePos firePos, float radius, double gravity) {
			return new NDRProjectile(world, p, damage, speed, BEAM_LIFETIME, spread, dmgDropStart, dmgDropEnd, dmgMin, penetration, blockdamage, firePos);
		}

		@Override
		public DamageType getDamageType() {
			return DamageType.RADIATION;
		}
	}
}
