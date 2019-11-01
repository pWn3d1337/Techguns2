package techguns.entities.projectiles;

import elucent.albedo.event.GatherLightsEvent;
import elucent.albedo.lighting.ILightProvider;
import elucent.albedo.lighting.Light;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.Optional;
import techguns.TGPackets;
import techguns.Techguns;
import techguns.api.damagesystem.DamageType;
import techguns.client.ClientProxy;
import techguns.damagesystem.TGDamageSource;
import techguns.damagesystem.TGExplosion;
import techguns.deatheffects.EntityDeathUtils.DeathType;
import techguns.items.guns.GenericGun;
import techguns.items.guns.IProjectileFactory;
import techguns.packets.PacketSpawnParticle;
import techguns.util.MathUtil;

@Optional.Interface(iface="elucent.albedo.lighting.ILightProvider", modid="albedo")
public class RocketProjectile extends GenericProjectile implements ILightProvider {

	public RocketProjectile(World par2World, EntityLivingBase p, float damage, float speed, int TTL, float spread, float dmgDropStart, float dmgDropEnd, float dmgMin,
			float penetration, boolean blockdamage, EnumBulletFirePos leftGun, float radius, double gravity) {
		super(par2World, p, damage, speed, TTL, spread, dmgDropStart, dmgDropEnd, dmgMin, penetration, blockdamage, leftGun);
		this.radius=radius;
		this.gravity=gravity;
	}

	public RocketProjectile(World worldIn, double posX, double posY, double posZ, float yaw, float pitch, float damage, float speed, int TTL, float spread, float dmgDropStart,
			float dmgDropEnd, float dmgMin, float penetration, boolean blockdamage, EnumBulletFirePos leftGun, float radius, double gravity) {
		super(worldIn, posX, posY, posZ, yaw, pitch, damage, speed, TTL, spread, dmgDropStart, dmgDropEnd, dmgMin, penetration, blockdamage, leftGun);
		this.radius=radius;
		this.gravity=gravity;
	}
	
	public RocketProjectile(World worldIn) {
		super(worldIn);
		if (worldIn.isRemote) {
			this.createTrailFX();
		}
	}
	
	protected void createTrailFX() {
		ClientProxy.get().createFXOnEntity("RocketLauncherExhaust", this);
		//ClientProxy.get().createFXOnEntity("TestFXTrail", this);
	}
	

	@Override
	protected void onHitEffect(EntityLivingBase ent, RayTraceResult rayTraceResult) {
		this.explodeRocket();
	}

	@Override
	protected void hitBlock(RayTraceResult raytraceResultIn) {
		this.explodeRocket();
	}

	protected void explodeRocket(){
		if (!this.world.isRemote){
			TGPackets.network.sendToAllAround(new PacketSpawnParticle("RocketExplosion", this.posX,this.posY,this.posZ), TGPackets.targetPointAroundEnt(this, 50.0f));
			//TGPackets.network.sendToAllAround(new PacketSpawnParticle("TestFX", this.posX,this.posY,this.posZ), TGPackets.targetPointAroundEnt(this, 50.0f));

    		//ProjectileExplosion explosion = new ProjectileExplosion(worldObj, this.posX, this.posY, this.posZ, this.shooter, radius, (int)damage, radius*0.5f, radius*1.5f);
			//Explosion explosion = new Explosion(world, this,this.posX,this.posY, this.posZ, 5, blockdamage, blockdamage);
			//explosion.doExplosionA();
			//explosion.doExplosionB(true);
			
			TGExplosion explosion = new TGExplosion(world, this.shooter, this, posX, posY, posZ, this.damage, this.damageMin, this.damageDropStart,this.damageDropEnd, this.blockdamage?0.5:0.0);
			
			explosion.doExplosion(true);
		}else {
			Techguns.proxy.createLightPulse(this.posX, this.posY, this.posZ, 5, 15, 10.0f, 1.0f, 1f, 0.9f, 0.5f);
		}
		this.setDead();
	}
	
	
	
	@Override
	protected TGDamageSource getProjectileDamageSource() {
		TGDamageSource dmgsrc = TGDamageSource.causeExplosionDamage(this, this.shooter, DeathType.GORE);
		dmgsrc.goreChance = 1.0f;
		dmgsrc.knockbackMultiplier=3.0f;
		return dmgsrc;
	}
	
	public static class Factory implements IProjectileFactory<RocketProjectile> {

		@Override
		public RocketProjectile createProjectile(GenericGun gun, World world, EntityLivingBase p, float damage, float speed, int TTL, float spread, float dmgDropStart, float dmgDropEnd,
				float dmgMin, float penetration, boolean blockdamage, EnumBulletFirePos firePos, float radius, double gravity) {
			return new RocketProjectile(world,p,damage,speed,TTL,spread,dmgDropStart,dmgDropEnd,dmgMin,penetration,blockdamage,firePos,radius,gravity);
		}

		@Override
		public DamageType getDamageType() {
			return DamageType.EXPLOSION;
		}
		
	}
	
	@Optional.Method(modid="albedo")
	@Override
	public Light provideLight() {
		return Light.builder()
				.pos(MathUtil.getInterpolatedEntityPos(this))
				.color(1f,1f, 1f)
				.radius(3)
				.build();
	}

	@Optional.Method(modid="albedo")
	@Override
	public void gatherLights(GatherLightsEvent evt, Entity ent) {
	}
}
