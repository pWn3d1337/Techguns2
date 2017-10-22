package techguns.entities.projectiles;

import elucent.albedo.lighting.ILightProvider;
import elucent.albedo.lighting.Light;
import io.netty.buffer.ByteBuf;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.RayTraceResult.Type;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.Optional;
import techguns.TGPackets;
import techguns.TGSounds;
import techguns.Techguns;
import techguns.api.damagesystem.DamageType;
import techguns.client.ClientProxy;
import techguns.items.guns.GenericGun;
import techguns.items.guns.IProjectileFactory;
import techguns.packets.PacketSpawnParticle;
import techguns.packets.PacketSpawnParticleOnEntity;
import techguns.util.MathUtil;

@Optional.Interface(iface="elucent.albedo.lighting.ILightProvider", modid="albedo")
public class GaussProjectile extends AdvancedBulletProjectile implements ILightProvider {

	public GaussProjectile(World par2World, EntityLivingBase p, float damage, float speed, int TTL, float spread,
			float dmgDropStart, float dmgDropEnd, float dmgMin, float penetration, boolean blockdamage, EnumBulletFirePos leftGun) {
		super(par2World, p, damage, speed, TTL, spread, dmgDropStart, dmgDropEnd, dmgMin, penetration, blockdamage, leftGun);
	}

	public GaussProjectile(World worldIn) {
		super(worldIn);
		if(worldIn.isRemote) {
			ClientProxy.get().createFXOnEntity("GaussProjectileTrail", this);
		}
	}

	@Override
	protected void doImpactEffects(Material mat, RayTraceResult rayTraceResult, SoundType sound) {
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
    	
    	if(sound==SoundType.STONE) {
			this.world.playSound(x, y, z, TGSounds.BULLET_IMPACT_STONE, SoundCategory.AMBIENT, 1.0f, 1.0f, distdelay);
			Techguns.proxy.createFX("Impact_BulletRock_Blue", world, x, y, z, 0.0D, 0.0D, 0.0D, pitch, yaw);
			
		} else if(sound==SoundType.WOOD || sound==SoundType.LADDER) {
			this.world.playSound(x, y, z, TGSounds.BULLET_IMPACT_WOOD, SoundCategory.AMBIENT, 1.0f, 1.0f, distdelay);
			Techguns.proxy.createFX("Impact_BulletWood_Blue", world, x, y, z, 0.0D, 0.0D, 0.0D, pitch, yaw);
			
		} else if(sound==SoundType.GLASS) {
			this.world.playSound(x, y, z, TGSounds.BULLET_IMPACT_GLASS, SoundCategory.AMBIENT, 1.0f, 1.0f, distdelay);
			Techguns.proxy.createFX("Impact_BulletGlass_Blue", world, x, y, z, 0.0D, 0.0D, 0.0D, pitch, yaw);
			
		} else if(sound==SoundType.METAL || sound==SoundType.ANVIL) {
			this.world.playSound(x, y, z, TGSounds.BULLET_IMPACT_METAL, SoundCategory.AMBIENT, 1.0f, 1.0f, distdelay);
			Techguns.proxy.createFX("Impact_BulletMetal_Blue", world, x, y, z, 0.0D, 0.0D, 0.0D, pitch, yaw);
			
		} else if(sound ==SoundType.GROUND || sound == SoundType.SAND) {
			this.world.playSound(x, y, z, TGSounds.BULLET_IMPACT_DIRT, SoundCategory.AMBIENT, 1.0f, 1.0f, distdelay);
	    	Techguns.proxy.createFX("Impact_BulletDirt_Blue", world, x, y, z, 0.0D, 0.0D, 0.0D, pitch, yaw);
	    	
		} else {
			this.world.playSound(x, y, z, TGSounds.BULLET_IMPACT_DIRT, SoundCategory.AMBIENT, 1.0f, 1.0f, distdelay);
	    	Techguns.proxy.createFX("Impact_BulletDefault_Blue", world, x, y, z, 0.0D, 0.0D, 0.0D, pitch, yaw);
			//this.world.spawnParticle(EnumParticleTypes.SMOKE_NORMAL, x, y, z, 0.0D, 0.0D, 0.0D);
		}		
	}
	
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
		public GaussProjectile createProjectile(GenericGun gun, World world, EntityLivingBase p, float damage, float speed, int TTL, float spread, float dmgDropStart, float dmgDropEnd,
				float dmgMin, float penetration, boolean blockdamage, EnumBulletFirePos firePos, float radius, double gravity) {
			//-0.14 -0.09 0.5
			float offsetX = 0.0f;
			if (firePos == EnumBulletFirePos.RIGHT) offsetX = -0.14f;
			else if (firePos == EnumBulletFirePos.LEFT) offsetX = 0.14f;			
			float offsetY = -0.09f;
			float offsetZ = 0.5f;
			
			TGPackets.network.sendToAllAround(new PacketSpawnParticleOnEntity("GaussFireFX", p, offsetX, offsetY, offsetZ, true), TGPackets.targetPointAroundEnt(p, 25.0f));
			return new GaussProjectile(world,p,damage,speed,TTL,spread,dmgDropStart,dmgDropEnd,dmgMin,penetration,blockdamage,firePos);
		}

		@Override
		public DamageType getDamageType() {
			return DamageType.PROJECTILE;
		}

	}
	
	@Override
	public void setVelocity(double x, double y, double z) {
		// TODO ?
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
