package techguns.entities.projectiles;

import io.netty.buffer.ByteBuf;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import techguns.TGPackets;
import techguns.Techguns;
import techguns.api.damagesystem.DamageType;
import techguns.capabilities.TGExtendedPlayer;
import techguns.client.ClientProxy;
import techguns.damagesystem.TGExplosion;
import techguns.items.guns.GenericGun;
import techguns.items.guns.GuidedMissileLauncher;
import techguns.items.guns.IChargedProjectileFactory;
import techguns.items.guns.IProjectileFactory;
import techguns.packets.PacketSpawnParticle;
import techguns.util.MathUtil;

public class GuidedMissileProjectile extends RocketProjectile{

	public static final double MAX_TURN_ANGLE = 9.0 *MathUtil.D2R; //= 180° per second
	
	public Entity target;
	
	
	public GuidedMissileProjectile(World par2World, EntityLivingBase p, float damage, float speed, int TTL,
			float spread, int dmgDropStart, int dmgDropEnd, float dmgMin, float penetration, boolean blockdamage,
			EnumBulletFirePos leftGun, float radius, double gravity) {
		super(par2World, p, damage, speed, TTL, spread, dmgDropStart, dmgDropEnd, dmgMin, penetration, blockdamage, leftGun,
				radius, gravity);
	}
	
	public GuidedMissileProjectile(World par2World, EntityLivingBase p, float damage, float speed, int TTL,
			float spread, int dmgDropStart, int dmgDropEnd, float dmgMin, float penetration, boolean blockdamage,
			EnumBulletFirePos leftGun, float radius, Entity target) {
		super(par2World, p, damage, speed, TTL, spread, dmgDropStart, dmgDropEnd, dmgMin, penetration, blockdamage, leftGun,
				radius, 0.0f);
		this.target = target;
	}
	
	public GuidedMissileProjectile(World worldIn) {
		super(worldIn);
//		TGExtendedPlayer epc = TGExtendedPlayer.get(Minecraft.getMinecraft().player);
//		epc.lockOnEntity = null;
//		epc.lockOnTicks = 0;
	}
	
	@Override
	protected void createTrailFX() {
		ClientProxy.get().createFXOnEntity("GuidedMissileExhaust", this);
	}

	
	@Override
	protected void explodeRocket() {
		if (!this.world.isRemote){
			TGPackets.network.sendToAllAround(new PacketSpawnParticle("GuidedMissileExplosion", this.posX,this.posY,this.posZ), TGPackets.targetPointAroundEnt(this, 50.0f));
			TGExplosion explosion = new TGExplosion(world, this.shooter, this, posX, posY, posZ, this.damage, this.damage*0.1, 1.0, 2.0, this.blockdamage?0.25:0.0);
			
			explosion.doExplosion(true);
		}else {
			Techguns.proxy.createLightPulse(this.posX, this.posY, this.posZ, 5, 15, 10.0f, 1.0f, 1f, 0.9f, 0.5f);
		}
		this.setDead();
	}
	
	@Override
	public void readSpawnData(ByteBuf additionalData) {
		super.readSpawnData(additionalData);
		int entityID = additionalData.readInt();
		if (entityID != -1)
			this.target = this.world.getEntityByID(entityID); 
	}
	
	@Override
	public void writeSpawnData(ByteBuf buffer) {
		super.writeSpawnData(buffer);
		if (target != null)
			buffer.writeInt(target.getEntityId());
		else
			buffer.writeInt(-1);
	}
	
	@Override
	public void onUpdate() {
		//Update Motion
		if (this.target != null) {
			Vec3d motion = new Vec3d(motionX, motionY, motionZ);
			double speed = motion.lengthVector();
			
			Vec3d v2 = new Vec3d(target.posX, target.posY+target.height*0.5f, target.posZ).subtract(new Vec3d(this.posX, this.posY, this.posZ)).normalize();
			Vec3d v1 = motion.normalize();
			
			double angle = Math.acos(v1.dotProduct(v2));
			Vec3d axis = v1.crossProduct(v2).normalize();
			
			//angle = Math.min(angle, MAX_TURN_ANGLE);
			
			if (angle < MAX_TURN_ANGLE) {
				motion = v2.scale(speed);
			}else {
				motion = MathUtil.rotateVector(v1, axis, MAX_TURN_ANGLE).scale(speed);
			}
			
			this.motionX = motion.x;
			this.motionY = motion.y;
			this.motionZ = motion.z;
		}
		System.out.println("Motion.length="+new Vec3d(motionX, motionY, motionZ).lengthVector());
		super.onUpdate();		
	}
	

//	@Override
//	public void setVelocity(double x, double y, double z) {
//		// TODO Auto-generated method stub
//	}

	public static class Factory implements IChargedProjectileFactory<GuidedMissileProjectile> {

		@Override
		public GuidedMissileProjectile createProjectile(GenericGun gun, World world, EntityLivingBase p, float damage, float speed, int TTL, float spread, int dmgDropStart, int dmgDropEnd,
				float dmgMin, float penetration, boolean blockdamage, EnumBulletFirePos firePos, float radius, double gravity) {
		
			Entity target = null;
			if (p instanceof EntityPlayer) {
				TGExtendedPlayer epc = TGExtendedPlayer.get((EntityPlayer)p);
				if (epc.lockOnEntity != null && epc.lockOnTicks >= ((GenericGun)p.getActiveItemStack().getItem()).getLockOnTicks()) {
					target = epc.lockOnEntity;
				}
	//			epc.lockOnEntity = null;
	//			epc.lockOnTicks = 0;
			}

			if (target != null) {
				return new GuidedMissileProjectile(world,p,damage,speed,TTL,spread,dmgDropStart,dmgDropEnd,dmgMin,penetration,blockdamage,firePos,radius, target);
			}else {
				return new GuidedMissileProjectile(world,p,damage,speed,TTL,spread,dmgDropStart,dmgDropEnd,dmgMin,penetration,blockdamage,firePos,radius,0.01f);
			}
		}

		@Override
		public DamageType getDamageType() {
			return DamageType.EXPLOSION;
		}

		@Override
		public GuidedMissileProjectile createChargedProjectile(World world, EntityLivingBase p, float damage,
				float speed, int TTL, float spread, int dmgDropStart, int dmgDropEnd, float dmgMin, float penetration,
				boolean blockdamage, EnumBulletFirePos firePos, float radius, double gravity, float charge, int ammoConsumed) {
			if (p instanceof EntityPlayer) {
				TGExtendedPlayer epc = TGExtendedPlayer.get((EntityPlayer)p);
				epc.lockOnEntity = null;
				epc.lockOnTicks = 0;
			}			
			return null;
		}
		
	}
}
