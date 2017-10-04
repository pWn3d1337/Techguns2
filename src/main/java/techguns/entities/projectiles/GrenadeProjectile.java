package techguns.entities.projectiles;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.init.SoundEvents;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import techguns.TGPackets;
import techguns.api.damagesystem.DamageType;
import techguns.damagesystem.TGExplosion;
import techguns.items.guns.IGrenadeProjectileFactory;
import techguns.packets.PacketSpawnParticle;

public class GrenadeProjectile extends GenericProjectile {

	int bounces=3;
	
	protected boolean bounced=false;
	
	public GrenadeProjectile(World worldIn) {
		super(worldIn);
	}

	public GrenadeProjectile(World worldIn, double posX, double posY, double posZ, float yaw, float pitch, float damage, float speed, int TTL, float spread, float penetration, boolean blockdamage, EnumBulletFirePos leftGun, double gravity, float radius, int bounces) {
		super(worldIn, posX, posY, posZ, yaw, pitch, damage, speed, TTL, spread, 0,0,0, penetration, blockdamage, leftGun);
		this.bounces=bounces;
		this.gravity=gravity;
		this.radius=radius;
	}
	
	public GrenadeProjectile(World par2World, EntityLivingBase p, float damage, float speed, int TTL, float spread, float penetration, boolean blockdamage, EnumBulletFirePos leftGun, double gravity, float radius, int bounces) {
		super(par2World, p, damage, speed, TTL, spread, 0, 0, 0, penetration, blockdamage, leftGun);
		this.bounces=bounces;
		this.gravity=gravity;
		this.radius=radius;
	}
	
	/**
	 * Copy on bounce constructor
	 * @param other
	 * @param x
	 * @param y
	 * @param z
	 */
	public GrenadeProjectile(GrenadeProjectile other, double x, double y, double z) {
		super(other.world,x,y,z,other.rotationYaw, other.rotationPitch, other.damage, other.speed, other.ticksToLive, 1.0f,0,0,0, other.penetration, other.blockdamage, EnumBulletFirePos.CENTER);
		this.shooter=other.shooter;
		this.bounces = other.bounces-1;
		this.gravity=other.gravity;
		this.radius=other.radius;
	}
	
	@Override
	protected void hitBlock(RayTraceResult raytraceResultIn) {
		if (this.bounces>0 && !bounced) {
			
			EnumFacing sideHit = (raytraceResultIn.sideHit);
     
			Vec3d motion = new Vec3d(this.motionX, this.motionY, this.motionZ);
			
			float len = this.getBounceSpeedFactor();
			
			Vec3d refl;
			switch(sideHit) {
			case EAST:
			case WEST:
				refl = new Vec3d(-this.motionX, this.motionY, this.motionZ);
				break;
			case NORTH:
			case SOUTH:
				refl = new Vec3d(this.motionX, this.motionY, -this.motionZ);
				break;
			case DOWN:
			case UP:
			default:
				refl = new Vec3d(this.motionX, -this.motionY, this.motionZ);
				break;
			}
			
        	this.playBounceSound();
        	
        	if (!this.world.isRemote){
        		GrenadeProjectile gren = createBounceProjectile(raytraceResultIn.hitVec);	
        	
        		gren.motionX = refl.x * len;
        		gren.motionY = refl.y * len;
        		gren.motionZ = refl.z * len;
        		gren.setPosition(gren.posX+gren.motionX, gren.posY+gren.motionY, gren.posZ+gren.motionZ);
        		
        		this.world.spawnEntity(gren);
        		
        		createProjectileTrail(gren);

        	}
        	this.bounced=true;
    	} else if(bounced) {
    		this.setDead();
    	}else {
    		this.explode();
    	}
	}

	protected GrenadeProjectile createBounceProjectile(Vec3d hitvec) {
		return new Factory().createBounceProjectile(this, hitvec.x, hitvec.y, hitvec.z);	
	}
	
	protected void playBounceSound() {
		//this.world.playSoundAtEntity(this, "mob.irongolem.hit", 2.0F, 1.0F );
		world.playSound(this.posX, this.posY, this.posZ, SoundEvents.ENTITY_IRONGOLEM_HURT, SoundCategory.PLAYERS, 1.0f, 1.0f, false);
	}

	protected void createProjectileTrail(GrenadeProjectile gren) {
		// TODO Auto-generated method stub
		
	}

	protected float getBounceSpeedFactor() {
		return 0.5f;
	}

	@Override
	protected void onHitEffect(EntityLivingBase ent, RayTraceResult rayTraceResult) {
		this.explode();
	}

	protected void explode(){
		if (!this.world.isRemote){
			TGPackets.network.sendToAllAround(new PacketSpawnParticle("RocketExplosion", this.posX,this.posY,this.posZ), TGPackets.targetPointAroundEnt(this, 50.0f));

			/*Explosion explosion = new Explosion(world, this,this.posX,this.posY, this.posZ, 5, blockdamage, blockdamage);
			explosion.doExplosionA();
			explosion.doExplosionB(true);
			*/
			TGExplosion exp = new TGExplosion(world, shooter, this, posX, posY, posZ, this.damage, this.damageMin, this.damageDropStart, this.damageDropEnd, 0);
			exp.doExplosion(true);

		}
		this.setDead();
	}
	
	@Override
	protected void readEntityFromNBT(NBTTagCompound tags) {
		super.readEntityFromNBT(tags);
		this.bounces=tags.getByte("bounces");
		this.bounced=tags.getBoolean("bounced");
	}

	@Override
	protected void writeEntityToNBT(NBTTagCompound tags) {
		super.writeEntityToNBT(tags);
		tags.setByte("bounces", (byte) this.bounces);
		tags.setBoolean("bounced", this.bounced);
	}

	public static class Factory implements IGrenadeProjectileFactory<GrenadeProjectile> {

		@Override
		public GrenadeProjectile createProjectile(World world, EntityLivingBase p, float damage, float speed, int TTL, float spread, float penetration, boolean blockdamage,
				EnumBulletFirePos leftGun, float radius, double gravity, float charge, int bounces) {
			return new GrenadeProjectile(world, p, damage, speed, TTL, spread, penetration, blockdamage, leftGun, gravity, radius, bounces);
		}

		@Override
		public DamageType getDamageType() {
			return DamageType.EXPLOSION;
		}

		@Override
		public GrenadeProjectile createBounceProjectile(GrenadeProjectile proj, double bounceX, double bounceY, double bounceZ) {
			return new GrenadeProjectile(proj, bounceX, bounceY, bounceZ);
		}
		
	}
}
