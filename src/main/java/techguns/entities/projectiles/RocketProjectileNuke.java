package techguns.entities.projectiles;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.SoundCategory;
import net.minecraft.world.World;
import techguns.TGPackets;
import techguns.TGSounds;
import techguns.Techguns;
import techguns.api.damagesystem.DamageType;
import techguns.damagesystem.TGDamageSource;
import techguns.damagesystem.TGExplosion;
import techguns.deatheffects.EntityDeathUtils.DeathType;
import techguns.items.guns.GenericGun;
import techguns.items.guns.IProjectileFactory;
import techguns.items.guns.ammo.DamageModifier;
import techguns.packets.PacketSpawnParticle;

public class RocketProjectileNuke extends RocketProjectile {

	public RocketProjectileNuke(World par2World, EntityLivingBase p, float damage, float speed, int TTL, float spread,
			float dmgDropStart, float dmgDropEnd, float dmgMin, float penetration, boolean blockdamage,
			EnumBulletFirePos leftGun, float radius, double gravity) {
		super(par2World, p, damage, speed, TTL, spread, dmgDropStart, dmgDropEnd, dmgMin, penetration, blockdamage, leftGun,
				radius, gravity);
	}

	public RocketProjectileNuke(World worldIn, double posX, double posY, double posZ, float yaw, float pitch,
			float damage, float speed, int TTL, float spread, float dmgDropStart, float dmgDropEnd, float dmgMin,
			float penetration, boolean blockdamage, EnumBulletFirePos leftGun, float radius, double gravity) {
		super(worldIn, posX, posY, posZ, yaw, pitch, damage, speed, TTL, spread, dmgDropStart, dmgDropEnd, dmgMin, penetration,
				blockdamage, leftGun, radius, gravity);
	}

	public RocketProjectileNuke(World worldIn) {
		super(worldIn);
	}

	@Override
	protected void explodeRocket() {
		if (!this.world.isRemote){
			TGPackets.network.sendToAllAround(new PacketSpawnParticle("NukeExplosion", this.posX,this.posY,this.posZ), TGPackets.targetPointAroundEnt(this, 150.0f));		
			TGExplosion explosion = new TGExplosion(world, this.shooter, this, posX, posY, posZ, this.damage, this.damageMin, this.damageDropStart, this.damageDropEnd, this.blockdamage?1.0:0.0);
			explosion.setDmgSrc(getProjectileDamageSource());
			
			explosion.doExplosion(false);
			this.world.playSound((EntityPlayer)null, this.posX, this.posY, this.posZ, TGSounds.NUKE_EXPLOSION, SoundCategory.BLOCKS, 4.0F, 1.0F);    	
		}else {
			Techguns.proxy.createLightPulse(this.posX, this.posY, this.posZ, 5, 80, 80.0f, 1.0f, 1f, 0.9f, 0.5f);
		}
		this.setDead();
	}
	
	@Override
	protected TGDamageSource getProjectileDamageSource() {
		TGDamageSource dmgsrc = TGDamageSource.causeExplosionDamage(this, this.shooter, DeathType.LASER);
		dmgsrc.goreChance = 1.0f;
		dmgsrc.knockbackMultiplier=3.0f;
		return dmgsrc;
	}

	public static class Factory implements IProjectileFactory<RocketProjectileNuke> {

		protected DamageModifier mod = new DamageModifier().setDmg(5f, 0f).setRadius(5f, 0f).setRange(5f, 0f);
		
		@Override
		public DamageModifier getDamageModifier() {
			return mod;
		}

		@Override
		public RocketProjectileNuke createProjectile(GenericGun gun, World world, EntityLivingBase p, float damage, float speed, int TTL, float spread, float dmgDropStart, float dmgDropEnd,
				float dmgMin, float penetration, boolean blockdamage, EnumBulletFirePos firePos, float radius, double gravity) {
			return new RocketProjectileNuke(world,p,mod.getDamage(damage),speed,TTL,spread,mod.getRange(dmgDropStart),mod.getRange(dmgDropEnd),mod.getDamage(dmgMin),penetration,blockdamage,firePos,mod.getRadius(radius),gravity);
		}

		@Override
		public DamageType getDamageType() {
			return DamageType.EXPLOSION;
		}
		
	}
	
	
}
