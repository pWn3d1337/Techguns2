package techguns.entities.projectiles;

import elucent.albedo.event.GatherLightsEvent;
import elucent.albedo.lighting.ILightProvider;
import elucent.albedo.lighting.Light;
import io.netty.buffer.ByteBuf;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.Optional;
import net.minecraftforge.fml.common.network.NetworkRegistry.TargetPoint;
import net.minecraftforge.fml.common.registry.IEntityAdditionalSpawnData;
import techguns.TGPackets;
import techguns.TGSounds;
import techguns.Techguns;
import techguns.api.damagesystem.DamageType;
import techguns.client.audio.TGSoundCategory;
import techguns.damagesystem.TGExplosion;
import techguns.items.guns.GenericGun;
import techguns.items.guns.IChargedProjectileFactory;
import techguns.packets.PacketPlaySound;
import techguns.packets.PacketSpawnParticle;
import techguns.packets.PacketSpawnParticleOnEntity;
import techguns.util.MathUtil;
import techguns.util.SoundUtil;

@Optional.Interface(iface="elucent.albedo.lighting.ILightProvider", modid="albedo")
public class TFGProjectile extends GenericProjectile implements IEntityAdditionalSpawnData, ILightProvider{

	public float size = 1.0f;


	public TFGProjectile(World worldIn, double posX, double posY, double posZ, float yaw, float pitch, float damage, float speed, int TTL, float spread, float dmgDropStart,
			float dmgDropEnd, float dmgMin, float penetration, boolean blockdamage, EnumBulletFirePos leftGun, double gravity, float size) {
		super(worldIn, posX, posY, posZ, yaw, pitch, damage, speed, TTL, spread, dmgDropStart, dmgDropEnd, dmgMin, penetration, blockdamage, leftGun);
		this.size=size;
		this.gravity=gravity;
	}

	public TFGProjectile(World par2World, EntityLivingBase p, float damage, float speed, int TTL, float spread, float dmgDropStart, float dmgDropEnd, float dmgMin,
			float penetration, boolean blockdamage, EnumBulletFirePos leftGun, double gravity, float size) {
		super(par2World, p, damage, speed, TTL, spread, dmgDropStart, dmgDropEnd, dmgMin, penetration, blockdamage, leftGun);
		this.size=size;
		this.gravity=gravity;
	}
	
	public TFGProjectile(World worldIn) {
		super(worldIn);
	}
	
	@Override
	protected void onHitEffect(EntityLivingBase ent, RayTraceResult rayTraceResult) {
		this.explode();
	}

	@Override
	protected void hitBlock(RayTraceResult raytraceResultIn) {
		this.explode();
	}

	protected void explode(){
		if (!this.world.isRemote){
			float exp_dmgMax = this.damage*0.66f * size;
			float exp_dmgMin = this.damage*0.33f * size;
			float exp_r1 = size*1.0f;
			float exp_r2 = size*2.0f;
			
			TGPackets.network.sendToAllAround(new PacketSpawnParticle("TFGExplosion", this.posX,this.posY,this.posZ, size*0.75f), TGPackets.targetPointAroundEnt(this, 100.0f));
			
			TGExplosion explosion = new TGExplosion(world, this.shooter, this, posX, posY, posZ, exp_dmgMax, exp_dmgMin, exp_r1, exp_r2, this.blockdamage?0.5:0.0);
			explosion.blockDropChance = 0.1f;
			
			explosion.doExplosion(false);
			TGPackets.network.sendToAllAround(new PacketPlaySound(TGSounds.TFG_EXPLOSION, this, 5.0f, 1.0f, false, false, false, TGSoundCategory.EXPLOISON), TGPackets.targetPointAroundEnt(this, 200.0f));
			
			//SoundUtil.playSoundAtEntityPos(world, this, TGSounds.TFG_EXPLOSION, 1.0f, 1.0f, false, TGSoundCategory.EXPLOISON);
			
			if (this.size > 3.0f) {
				//SoundUtil.playSoundAtEntityPos(world, this, TGSounds.TFG_EXPLOSION_ECHO, 1.0f, 1.0f, false, TGSoundCategory.EXPLOISON);
				TGPackets.network.sendToAllAround(new PacketPlaySound(TGSounds.TFG_EXPLOSION_ECHO, this, 10.0f, 1.0f, false, false, false, TGSoundCategory.EXPLOISON), TGPackets.targetPointAroundEnt(this, 200.0f));
				
			}
			
		}else {
			Techguns.proxy.createLightPulse(this.posX, this.posY, this.posZ, 5, 15, 5f+(size), 1f+(size*0.5f), 0.5f, 1.0f, 0.5f);
		}
		this.setDead();
	}

	@Override
	public void writeSpawnData(ByteBuf buffer) {
		super.writeSpawnData(buffer);
		buffer.writeFloat(this.size);
	}

	@Override
	public void readSpawnData(ByteBuf additionalData) {
		super.readSpawnData(additionalData);
		this.size=additionalData.readFloat();
		Techguns.proxy.createFXOnEntity("TFGTrail", this, this.size*0.75f);
	}
	
	public static class Factory implements IChargedProjectileFactory<TFGProjectile> {

		@Override
		public TFGProjectile createProjectile(GenericGun gun, World world, EntityLivingBase p, float damage, float speed, int TTL, float spread, float dmgDropStart, float dmgDropEnd,
				float dmgMin, float penetration, boolean blockdamage, EnumBulletFirePos firePos, float radius, double gravity) {
			return this.createChargedProjectile(world, p, damage, speed, TTL, spread, dmgDropStart, dmgDropEnd, dmgMin, penetration, blockdamage, firePos, radius, gravity, 0f, 1);
		}

		@Override
		public DamageType getDamageType() {
			return DamageType.ENERGY;
		}
		
		@Override
		public TFGProjectile createChargedProjectile(World world, EntityLivingBase p, float damage, float speed, int TTL, float spread, float dmgDropStart, float dmgDropEnd,
				float dmgMin, float penetration, boolean blockdamage, EnumBulletFirePos firePos, float radius, double gravity, float charge, int ammoConsumed) {
			TFGProjectile proj = new TFGProjectile(world,p,damage,speed,TTL,spread,dmgDropStart,dmgDropEnd,dmgMin,penetration,blockdamage,firePos,gravity,ammoConsumed);
			proj.size = 1.0f+(charge*3.0f);
			if (charge >= 1.0f) proj.size += 1.0f;
			//-0.14f, -0.10f, 0.42f
//			float offsetX = 0.0f;
//			if (firePos == EnumBulletFirePos.RIGHT) offsetX = -0.14f;
//			else if (firePos == EnumBulletFirePos.LEFT) offsetX = 0.14f;			
//			float offsetY = -0.1f;
//			float offsetZ = 0.42f;
//			
//			TGPackets.network.sendToAllAround(new PacketSpawnParticleOnEntity("TFGFire", p, offsetX, offsetY, offsetZ, true), TGPackets.targetPointAroundEnt(p, 50.0f));			
			return proj;
		
		}
		
	}
	
	@Optional.Method(modid="albedo")
	@Override
	public Light provideLight() {
		return Light.builder()
				.pos(MathUtil.getInterpolatedEntityPos(this))
				.color(0.5f,1.0f, 0.5f)
				.radius(2f+(size*0.5f))
				.build();
	}

	@Optional.Method(modid="albedo")
	@Override
	public void gatherLights(GatherLightsEvent arg0, Entity arg1) {
		// TODO Auto-generated method stub
		
	}
}