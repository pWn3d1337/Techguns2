package techguns.entities.projectiles;

import java.util.List;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import techguns.api.damagesystem.DamageType;
import techguns.items.guns.GenericGun;
import techguns.items.guns.IProjectileFactory;
import techguns.util.MathUtil;

public class TeslaProjectile extends AbstractBeamProjectile{

	public static final int TTL = 10;
	public static final float CHAIN_RANGE = 8.0f;
	public static final int CHAIN_TARGETS = 4;
	public static final float CHAIN_DAMAGE_FACTOR = 0.75f;
	
	public long seed = 0;
	
	protected int chainTargets = CHAIN_TARGETS;
	protected Entity prevTarget = null;
	
	public TeslaProjectile(World worldIn) {
		super(worldIn);
		this.seed = worldIn.rand.nextLong();
	}
	
//	public TeslaProjectile(World worldIn, double posX, double posY, double posZ, float yaw, float pitch, float damage,
//			float speed, int TTL, float spread, float dmgDropStart, float dmgDropEnd, float dmgMin, float penetration,
//			boolean blockdamage, boolean leftGun) {
//		super(worldIn, posX, posY, posZ, yaw, pitch, damage, speed, TTL, spread, dmgDropStart, dmgDropEnd, dmgMin, penetration,
//				blockdamage, leftGun);
//	}
	
	public TeslaProjectile(World par2World, EntityLivingBase p, float damage, float speed, int TTL, float spread,
			float dmgDropStart, float dmgDropEnd, float dmgMin, float penetration, boolean blockdamage, EnumBulletFirePos leftGun, int chainTargets) {
		super(par2World, p, damage, speed, TTL, spread, dmgDropStart, dmgDropEnd, dmgMin, penetration, blockdamage, leftGun);
		this.chainTargets = chainTargets;
		trace();
	}
	
	public TeslaProjectile(World world, EntityLivingBase shooter, Entity source, EntityLivingBase target, int chainTargets, float damage, float speed, int TTL, float dmgDropStart,
			float dmgDropEnd, float dmgMin, float penetration, boolean blockdamage) {
		super(world, shooter, damage, speed, TTL, 0, dmgDropStart, dmgDropEnd, dmgMin, penetration, blockdamage, EnumBulletFirePos.CENTER);
		maxTicks = (short) this.ticksToLive;
		this.chainTargets = chainTargets;
		this.prevTarget = target;
		//TODO Align Projectile from Source to Target
		this.posX = source.posX;
		this.posY = source.posY+ source.getEyeHeight()*0.5f;
		this.posZ = source.posZ;
		Vec3d src = new Vec3d(posX, posY, posZ);
		Vec3d tgt = new Vec3d(target.posX, target.posY+target.getEyeHeight()*0.5f, target.posZ);
		Vec3d dir = tgt.subtract(src).normalize();
		
		this.distance = src.distanceTo(tgt);
		this.laserPitch = (float) (Math.asin(-dir.y) * MathUtil.R2D);
		this.laserYaw = (float) (Math.atan2(dir.x, dir.z) * MathUtil.R2D);
		
		System.out.printf("pitch : %.3f,  yaw : %.3f,  distance : %.3f\n", laserPitch, laserYaw, distance);
		
		this.rotationPitch = laserPitch;
		this.rotationYaw = laserYaw;
		
		this.motionX = dir.x*this.speed;
		this.motionY = dir.y*this.speed;
		this.motionZ = dir.z*this.speed;
		//trace();
		
		if (distance <= 0) {
			distance = this.speed;
		}
		
		RayTraceResult raytraceresult = new RayTraceResult(target);

		if (raytraceresult != null) {
			this.onHit(raytraceresult);
			this.isDead = false;
//			if (!this.world.isRemote){
//				this.createImpactEffect(tgt);
//			}
		}
	}
	
	@Override
	protected void onHit(RayTraceResult raytraceResultIn) {
		super.onHit(raytraceResultIn);
		if (!world.isRemote) {
			Entity lastTarget = raytraceResultIn.entityHit;
			if (lastTarget != null && this.chainTargets > 0) {
				EntityLivingBase nextTarget = findNextTarget(lastTarget);
				if (nextTarget != null) {
					TeslaProjectile proj = new TeslaProjectile(world, this.shooter, lastTarget, nextTarget, chainTargets-1, this.damage*CHAIN_DAMAGE_FACTOR, this.speed, TTL, this.damageDropStart,  this.damageDropEnd, this.damageMin, this.penetration, this.blockdamage);
					world.spawnEntity(proj);
				}
			}
		}
	}
	
	private EntityLivingBase findNextTarget(Entity lastTarget) {
		List list = this.world.getEntitiesWithinAABBExcludingEntity(lastTarget, new AxisAlignedBB(lastTarget.posX
						- CHAIN_RANGE, lastTarget.posY
						- CHAIN_RANGE, lastTarget.posZ
						- CHAIN_RANGE, lastTarget.posX
						+ CHAIN_RANGE, lastTarget.posY
						+ CHAIN_RANGE, lastTarget.posZ
						+ CHAIN_RANGE));
		
		for (int i1 = 0; i1 < list.size(); ++i1)
        {
			Object o = list.get(i1);
			if (o instanceof EntityLivingBase) {
				EntityLivingBase entity = (EntityLivingBase)o;
	            
	            double distance = entity.getDistance(lastTarget.posX, lastTarget.posY+lastTarget.getEyeHeight()*0.5f, lastTarget.posZ);
	            
	            if (distance < CHAIN_RANGE && entity.isEntityAlive() && entity != lastTarget) {
            		if (!entity.equals(shooter) && !entity.equals(prevTarget)) {
            			Vec3d vec3d1 = new Vec3d(lastTarget.posX, lastTarget.posY+lastTarget.getEyeHeight()*0.5f, lastTarget.posZ);
            			Vec3d vec3d2 = new Vec3d(entity.posX, entity.posY+entity.getEyeHeight()*0.5f, entity.posZ);
            			RayTraceResult raytraceresult = this.world.rayTraceBlocks(vec3d1, vec3d2, false, true, false);
            			
            			if (raytraceresult == null)
            				return entity;
            		}
	            }
			}
        }
		return null;
	}
	
	@Override
	public boolean shouldRenderInPass(int pass) {
		return pass==1;
	}
	
	public static class Factory implements IProjectileFactory<TeslaProjectile> {
		@Override
		public TeslaProjectile createProjectile(GenericGun gun, World world, EntityLivingBase p, float damage, float speed,
				int TTL, float spread, float dmgDropStart, float dmgDropEnd, float dmgMin, float penetration,
				boolean blockdamage, EnumBulletFirePos firePos, float radius, double gravity) {
			return new TeslaProjectile(world, p, damage, speed, TeslaProjectile.TTL, spread, dmgDropStart, dmgDropEnd, dmgMin, penetration, blockdamage, firePos, CHAIN_TARGETS);
		}

		@Override
		public DamageType getDamageType() {
			return DamageType.ENERGY;
		}
	}

}
