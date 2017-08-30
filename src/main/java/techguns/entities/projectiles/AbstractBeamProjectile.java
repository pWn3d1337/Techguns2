package techguns.entities.projectiles;

import io.netty.buffer.ByteBuf;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import techguns.TGPackets;
import techguns.packets.PacketSpawnParticle;

public abstract class AbstractBeamProjectile extends GenericProjectile {

	public double distance = -1d;
	public float laserPitch = 0.0f;
	public float laserYaw = 0.0f;
	public short maxTicks = 0;
	
	public AbstractBeamProjectile(World worldIn) {
		super(worldIn);
	}

	public AbstractBeamProjectile(World par2World, EntityLivingBase p, float damage, float speed, int TTL, float spread,
			int dmgDropStart, int dmgDropEnd, float dmgMin, float penetration, boolean blockdamage, EnumBulletFirePos leftGun) {
		super(par2World, p, damage, speed, TTL, spread, dmgDropStart, dmgDropEnd, dmgMin, penetration, blockdamage,
				leftGun);
		maxTicks = (short) this.ticksToLive;
	}

	public AbstractBeamProjectile(World worldIn, double posX, double posY, double posZ, float yaw, float pitch,
			float damage, float speed, int TTL, float spread, int dmgDropStart, int dmgDropEnd, float dmgMin,
			float penetration, boolean blockdamage, EnumBulletFirePos leftGun) {
		super(worldIn, posX, posY, posZ, yaw, pitch, damage, speed, TTL, spread, dmgDropStart, dmgDropEnd, dmgMin,
				penetration, blockdamage, leftGun);
		maxTicks = (short) this.ticksToLive;
	}
	
	protected void trace() {	
		Vec3d vec3d1 = new Vec3d(this.posX, this.posY, this.posZ);
		Vec3d vec3d = new Vec3d(this.posX + this.motionX, this.posY + this.motionY, this.posZ + this.motionZ);
		RayTraceResult raytraceresult = this.world.rayTraceBlocks(vec3d1, vec3d, false, true, false);

		if (raytraceresult != null) {
			vec3d = new Vec3d(raytraceresult.hitVec.x, raytraceresult.hitVec.y, raytraceresult.hitVec.z);
		}

		Entity entity = this.findEntityOnPath(vec3d1, vec3d);

		if (entity != null) {
			raytraceresult = new RayTraceResult(entity);
		}

		if (raytraceresult != null && raytraceresult.entityHit instanceof EntityPlayer) {
			EntityPlayer entityplayer = (EntityPlayer) raytraceresult.entityHit;

			if (this.shooter instanceof EntityPlayer && !((EntityPlayer) this.shooter).canAttackPlayer(entityplayer)) {
				raytraceresult = null;
			}
		}

		if (raytraceresult != null) {
			this.onHit(raytraceresult);
			this.isDead = false;
			Vec3d hitVec = raytraceresult.hitVec;
			distance = vec3d1.distanceTo(hitVec);
			if (!this.world.isRemote){
				this.createImpactEffect(hitVec);
			}
		}
		laserPitch = this.rotationPitch;
		laserYaw = this.rotationYaw;
		if (distance <= 0) {
			distance = this.speed;
		}
	}
	
	protected void createImpactEffect(Vec3d hitVec) {}
	
	@Override
	public void onUpdate() {
		--this.ticksToLive;
		if (this.ticksToLive<=0){
			this.setDead();
		}
	}
	
	@Override
	public void writeSpawnData(ByteBuf buffer) {
		super.writeSpawnData(buffer);
		buffer.writeDouble(this.distance);
		buffer.writeFloat(this.laserPitch);
		buffer.writeFloat(this.laserYaw);
		buffer.writeShort(this.maxTicks);
	}

	@Override
	public void readSpawnData(ByteBuf additionalData) {
		super.readSpawnData(additionalData);
		this.distance=additionalData.readDouble();
		this.laserPitch = additionalData.readFloat();
		this.laserYaw = additionalData.readFloat();
		this.maxTicks = additionalData.readShort();
	}
}
