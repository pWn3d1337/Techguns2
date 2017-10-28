package techguns.entities.projectiles;

import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.World;
import techguns.TGPackets;
import techguns.TGSounds;
import techguns.api.damagesystem.DamageType;
import techguns.damagesystem.TGDamageSource;
import techguns.deatheffects.EntityDeathUtils.DeathType;
import techguns.items.guns.GenericGun;
import techguns.items.guns.IChargedProjectileFactory;
import techguns.packets.PacketSpawnParticle;

public class PowerHammerProjectile extends GenericProjectile {

	public PowerHammerProjectile(World worldIn, double posX, double posY, double posZ, float yaw, float pitch,
			float damage, float speed, int TTL, float spread, float dmgDropStart, float dmgDropEnd, float dmgMin,
			float penetration, boolean blockdamage, EnumBulletFirePos firePos) {
		super(worldIn, posX, posY, posZ, yaw, pitch, damage, speed, TTL, spread, dmgDropStart, dmgDropEnd, dmgMin, penetration,
				blockdamage, firePos);
	}

	public PowerHammerProjectile(World par2World, EntityLivingBase p, float damage, float speed, int TTL, float spread,
			float dmgDropStart, float dmgDropEnd, float dmgMin, float penetration, boolean blockdamage,
			EnumBulletFirePos firePos) {
		super(par2World, p, damage, speed, TTL, spread, dmgDropStart, dmgDropEnd, dmgMin, penetration, blockdamage, firePos);
	}

	public PowerHammerProjectile(World worldIn) {
		super(worldIn);
	}

	@Override
	protected void onHitEffect(EntityLivingBase ent, RayTraceResult rayTraceResult) {
		if(!this.world.isRemote) {
	    	double x = rayTraceResult.hitVec.x;
	    	double y = rayTraceResult.hitVec.y;
	    	double z = rayTraceResult.hitVec.z;
			this.world.playSound(x, y, z, TGSounds.POWERHAMMER_IMPACT, SoundCategory.PLAYERS, 1.0f, 1.0f, false);
			TGPackets.network.sendToAllAround(new PacketSpawnParticle("PowerhammerImpact",x,y,z), TGPackets.targetPointAroundEnt(this, 32.0f));
		}
	}

	@Override
	protected void doImpactEffects(Material mat, RayTraceResult rayTraceResult, SoundType sound) {
		if(!this.world.isRemote) {
	    	double x = rayTraceResult.hitVec.x;
	    	double y = rayTraceResult.hitVec.y;
	    	double z = rayTraceResult.hitVec.z;
			this.world.playSound(x, y, z, TGSounds.POWERHAMMER_IMPACT, SoundCategory.PLAYERS, 1.0f, 1.0f, false);
			TGPackets.network.sendToAllAround(new PacketSpawnParticle("PowerhammerImpact",x,y,z), TGPackets.targetPointAroundEnt(this, 32.0f));
		}
	}
	
	
	@Override
	protected TGDamageSource getProjectileDamageSource() {
		TGDamageSource src = new TGDamageSource("powerhammer", this, this.shooter, DamageType.PHYSICAL, DeathType.GORE);
    	src.goreChance=1.0f;
    	src.armorPenetration=this.penetration;
    	return src;
	}


	public static class Factory implements IChargedProjectileFactory<PowerHammerProjectile> {

		@Override
		public PowerHammerProjectile createProjectile(GenericGun gun, World world, EntityLivingBase p, float damage, float speed, int TTL, float spread, float dmgDropStart, float dmgDropEnd,
				float dmgMin, float penetration, boolean blockdamage, EnumBulletFirePos firePos, float radius, double gravity) {
			return new PowerHammerProjectile(world,p,damage,speed,TTL,spread,dmgDropStart,dmgDropEnd,dmgMin,penetration,blockdamage,firePos);
		}

		@Override
		public DamageType getDamageType() {
			return DamageType.PHYSICAL;
		}

		@Override
		public PowerHammerProjectile createChargedProjectile(World world, EntityLivingBase p, float damage, float speed,
				int TTL, float spread, float dmgDropStart, float dmgDropEnd, float dmgMin, float penetration,
				boolean blockdamage, EnumBulletFirePos firePos, float radius, double gravity, float charge,
				int ammoConsumed) {
			//TODO check Charged Damage
			return new PowerHammerProjectile(world,p,damage*ammoConsumed,speed,TTL,spread,dmgDropStart,dmgDropEnd,dmgMin*ammoConsumed,penetration,blockdamage,firePos);
		}
		
	}
	

}
