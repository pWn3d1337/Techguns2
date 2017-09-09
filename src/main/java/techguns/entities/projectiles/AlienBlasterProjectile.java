package techguns.entities.projectiles;

import net.minecraft.block.material.Material;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.World;
import techguns.TGPackets;
import techguns.api.damagesystem.DamageType;
import techguns.client.ClientProxy;
import techguns.damagesystem.TGDamageSource;
import techguns.deatheffects.EntityDeathUtils.DeathType;
import techguns.items.guns.GenericGun;
import techguns.items.guns.IProjectileFactory;
import techguns.packets.PacketSpawnParticle;

public class AlienBlasterProjectile extends GenericProjectile {

	public static final int ENTITY_IGNITE_TIME = 3;
	
	public AlienBlasterProjectile(World worldIn) {
		super(worldIn);
		if(worldIn.isRemote) {
			ClientProxy.get().createFXOnEntity("AlienBlasterTrail", this);
		}
	}

	public AlienBlasterProjectile(World worldIn, double posX, double posY, double posZ, float yaw, float pitch,
			float damage, float speed, int TTL, float spread, int dmgDropStart, int dmgDropEnd, float dmgMin,
			float penetration, boolean blockdamage, EnumBulletFirePos firePos) {
		super(worldIn, posX, posY, posZ, yaw, pitch, damage, speed, TTL, spread, dmgDropStart, dmgDropEnd, dmgMin, penetration,
				blockdamage, firePos);
	}

	public AlienBlasterProjectile(World par2World, EntityLivingBase p, float damage, float speed, int TTL, float spread,
			int dmgDropStart, int dmgDropEnd, float dmgMin, float penetration, boolean blockdamage,
			EnumBulletFirePos firePos) {
		super(par2World, p, damage, speed, TTL, spread, dmgDropStart, dmgDropEnd, dmgMin, penetration, blockdamage, firePos);
	}

	@Override
	protected void doImpactEffects(Material mat, RayTraceResult rayTraceResult) {
		if (!this.world.isRemote){
			double x = rayTraceResult.hitVec.x;
			double y = rayTraceResult.hitVec.y;
			double z = rayTraceResult.hitVec.z;
			TGPackets.network.sendToAllAround(new PacketSpawnParticle("AlienExplosion", x,y,z), TGPackets.targetPointAroundEnt(this, 32.0f));
		}
	}

	
	
	@Override
	protected void onHitEffect(EntityLivingBase ent, RayTraceResult rayTraceResultIn) {
		ent.setFire(ENTITY_IGNITE_TIME);
	}

	@Override
	protected void hitBlock(RayTraceResult raytraceResultIn) {
		super.hitBlock(raytraceResultIn); //Does impact effect
		
		//TODO burn block
		/* if (this.blockdamage){
			 burnblock(mop,mop.blockX,mop.blockY,mop.blockZ);
		 }*/
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
		public AlienBlasterProjectile createProjectile(GenericGun gun, World world, EntityLivingBase p, float damage, float speed, int TTL, float spread, int dmgDropStart, int dmgDropEnd,
				float dmgMin, float penetration, boolean blockdamage, EnumBulletFirePos firePos, float radius, double gravity) {
			return new AlienBlasterProjectile(world,p,damage,speed,TTL,spread,dmgDropStart,dmgDropEnd,dmgMin,penetration,blockdamage,firePos);
		}

		@Override
		public DamageType getDamageType() {
			return DamageType.FIRE;
		}
		
	}
	
	
	
}
