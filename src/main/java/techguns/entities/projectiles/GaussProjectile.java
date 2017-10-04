package techguns.entities.projectiles;

import elucent.albedo.lighting.ILightProvider;
import elucent.albedo.lighting.Light;
import io.netty.buffer.ByteBuf;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.Optional;
import techguns.TGPackets;
import techguns.Techguns;
import techguns.api.damagesystem.DamageType;
import techguns.client.ClientProxy;
import techguns.items.guns.GenericGun;
import techguns.items.guns.IProjectileFactory;
import techguns.packets.PacketSpawnParticle;
import techguns.util.MathUtil;

@Optional.Interface(iface="elucent.albedo.lighting.ILightProvider", modid="albedo")
public class GaussProjectile extends AdvancedBulletProjectile implements ILightProvider {

	public GaussProjectile(World par2World, EntityLivingBase p, float damage, float speed, int TTL, float spread,
			int dmgDropStart, int dmgDropEnd, float dmgMin, float penetration, boolean blockdamage, EnumBulletFirePos leftGun) {
		super(par2World, p, damage, speed, TTL, spread, dmgDropStart, dmgDropEnd, dmgMin, penetration, blockdamage, leftGun);
		// TODO Auto-generated constructor stub
	}

//	public GaussProjectile(World worldIn, double posX, double posY, double posZ, float yaw, float pitch, float damage,
//			float speed, int TTL, float spread, int dmgDropStart, int dmgDropEnd, float dmgMin, float penetration,
//			boolean blockdamage, boolean leftGun) {
//		super(worldIn, posX, posY, posZ, yaw, pitch, damage, speed, TTL, spread, dmgDropStart, dmgDropEnd, dmgMin, penetration,
//				blockdamage, leftGun);
//		// TODO Auto-generated constructor stub
//	}
	
	public GaussProjectile(World worldIn) {
		super(worldIn);
		if(worldIn.isRemote) {
			ClientProxy.get().createFXOnEntity("GaussProjectileTrail", this);
		}
	}

	
//	@Override
//	public void onUpdate() {
//		// TODO Auto-generated method stub
//		super.onUpdate();
//		//System.out.println(String.format("Motion: X=%.3f, Y=%.3f, Z=%.3f", motionX, motionY, motionZ));
//	}
	
	@Override
	protected void onHitEffect(EntityLivingBase ent, RayTraceResult rayTraceResult) {
		super.onHitEffect(ent, rayTraceResult);
		double x = rayTraceResult.hitVec.x;
		double y = rayTraceResult.hitVec.y;
		double z = rayTraceResult.hitVec.z;
		if (!this.world.isRemote) {
			TGPackets.network.sendToAllAround(new PacketSpawnParticle("GaussRifleImpact_Block", x,y,z), TGPackets.targetPointAroundEnt(this, 50.0f));
		}else {
			Techguns.proxy.createLightPulse(x,y,z, 5, 10, 3.0f, 1.0f, 0.5f, 0.75f, 1f);
		}
		
	}

	@Override
	protected void hitBlock(RayTraceResult raytraceResultIn) {
		super.hitBlock(raytraceResultIn);
		double x = raytraceResultIn.hitVec.x;
		double y = raytraceResultIn.hitVec.y;
		double z = raytraceResultIn.hitVec.z;
		
		if (!this.world.isRemote) {
			TGPackets.network.sendToAllAround(new PacketSpawnParticle("GaussRifleImpact_Block", x,y,z), TGPackets.targetPointAroundEnt(this, 50.0f));
		}else {
			Techguns.proxy.createLightPulse(x,y,z, 5, 10, 3.0f, 1.0f, 0.5f, 0.75f, 1f);
		}
	}
	
	public static class Factory implements IProjectileFactory<GaussProjectile> {

		@Override
		public GaussProjectile createProjectile(GenericGun gun, World world, EntityLivingBase p, float damage, float speed, int TTL, float spread, int dmgDropStart, int dmgDropEnd,
				float dmgMin, float penetration, boolean blockdamage, EnumBulletFirePos firePos, float radius, double gravity) {
			return new GaussProjectile(world,p,damage,speed,TTL,spread,dmgDropStart,dmgDropEnd,dmgMin,penetration,blockdamage,firePos);
		}

		@Override
		public DamageType getDamageType() {
			return DamageType.PROJECTILE;
		}

	}
	
	@Override
	public void setVelocity(double x, double y, double z) {
		// TODO Auto-generated method stub
	}
	
	
	@Override
	public void writeSpawnData(ByteBuf buffer) {
		super.writeSpawnData(buffer);
		buffer.writeDouble(this.motionX);
		buffer.writeDouble(this.motionY);
		buffer.writeDouble(this.motionZ);
	}

	@Override
	public void readSpawnData(ByteBuf additionalData) {
		super.readSpawnData(additionalData);
		this.motionX=additionalData.readDouble();
		this.motionY=additionalData.readDouble();
		this.motionZ=additionalData.readDouble();
	}
	
	@Optional.Method(modid="albedo")
	@Override
	public Light provideLight() {
		return Light.builder()
				.pos(MathUtil.getInterpolatedEntityPos(this))
				.color(0.5f,0.75f, 1f)
				.radius(2.5f)
				.build();
	}
}
