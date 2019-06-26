package techguns.entities.projectiles;

import elucent.albedo.event.GatherLightsEvent;
import elucent.albedo.lighting.ILightProvider;
import elucent.albedo.lighting.Light;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.Optional;
import techguns.TGPackets;
import techguns.TGuns;
import techguns.Techguns;
import techguns.api.damagesystem.DamageType;
import techguns.client.ClientProxy;
import techguns.damagesystem.TGDamageSource;
import techguns.deatheffects.EntityDeathUtils.DeathType;
import techguns.items.guns.GenericGun;
import techguns.items.guns.IProjectileFactory;
import techguns.packets.PacketSpawnParticle;
import techguns.util.MathUtil;

@Optional.Interface(iface="elucent.albedo.lighting.ILightProvider", modid="albedo")
public class AlienBlasterProjectile extends GenericProjectile implements ILightProvider {

	public static final int ENTITY_IGNITE_TIME = 3;
	
	public AlienBlasterProjectile(World worldIn) {
		super(worldIn);
		if(worldIn.isRemote) {
			ClientProxy.get().createFXOnEntity("AlienBlasterTrail", this);
		}
	}

	public AlienBlasterProjectile(World worldIn, double posX, double posY, double posZ, float yaw, float pitch,
			float damage, float speed, int TTL, float spread, float dmgDropStart, float dmgDropEnd, float dmgMin,
			float penetration, boolean blockdamage, EnumBulletFirePos firePos) {
		super(worldIn, posX, posY, posZ, yaw, pitch, damage, speed, TTL, spread, dmgDropStart, dmgDropEnd, dmgMin, penetration,
				blockdamage, firePos);
	}

	public AlienBlasterProjectile(World par2World, EntityLivingBase p, float damage, float speed, int TTL, float spread,
			float dmgDropStart, float dmgDropEnd, float dmgMin, float penetration, boolean blockdamage,
			EnumBulletFirePos firePos) {
		super(par2World, p, damage, speed, TTL, spread, dmgDropStart, dmgDropEnd, dmgMin, penetration, blockdamage, firePos);
	}

	@Override
	protected void doImpactEffects(Material mat, RayTraceResult rayTraceResult, SoundType sound) {
		/*if (!this.world.isRemote){
			double x = rayTraceResult.hitVec.x;
			double y = rayTraceResult.hitVec.y;
			double z = rayTraceResult.hitVec.z;
			TGPackets.network.sendToAllAround(new PacketSpawnParticle("AlienExplosion", x,y,z), TGPackets.targetPointAroundEnt(this, 32.0f));
		}*/
		Techguns.proxy.createFX("AlienExplosion", world, rayTraceResult.hitVec.x, rayTraceResult.hitVec.y, rayTraceResult.hitVec.z, 0,0,0);
	}
	
	@Override
	protected void onHitEffect(EntityLivingBase ent, RayTraceResult rayTraceResultIn) {
		ent.setFire(ENTITY_IGNITE_TIME);
	}

	@Override
	protected void hitBlock(RayTraceResult raytraceResultIn) {
		super.hitBlock(raytraceResultIn); //Does impact effect
		
		 if (this.blockdamage){
			 burnBlocks(world, raytraceResultIn, 0.35);
		 }
	}

	@Override
	protected TGDamageSource getProjectileDamageSource() {
		TGDamageSource src = TGDamageSource.causeFireDamage(this, this.shooter, DeathType.LASER);
    	src.armorPenetration = this.penetration;
    	src.goreChance=0.25f;
    	return src;
	}

	public static class Factory implements IProjectileFactory<AlienBlasterProjectile> {

		@Override
		public AlienBlasterProjectile createProjectile(GenericGun gun, World world, EntityLivingBase p, float damage, float speed, int TTL, float spread, float dmgDropStart, float dmgDropEnd,
				float dmgMin, float penetration, boolean blockdamage, EnumBulletFirePos firePos, float radius, double gravity) {
			return new AlienBlasterProjectile(world,p,damage,speed,TTL,spread,dmgDropStart,dmgDropEnd,dmgMin,penetration,blockdamage,firePos);
		}

		@Override
		public DamageType getDamageType() {
			return DamageType.FIRE;
		}
		
	}

	@Optional.Method(modid="albedo")
	@Override
	public Light provideLight() {
		return Light.builder()
				.pos(MathUtil.getInterpolatedEntityPos(this))
				.color(TGuns.alienblaster.light_r,TGuns.alienblaster.light_g,TGuns.alienblaster.light_b)
				.radius(4)
				.build();
	}

	@Optional.Method(modid="albedo")
	@Override
	public void gatherLights(GatherLightsEvent arg0, Entity arg1) {
		// TODO Auto-generated method stub
		
	}
	
	
	
}
