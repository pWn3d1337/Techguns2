package techguns.entities.projectiles;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;
import techguns.api.damagesystem.DamageType;
import techguns.capabilities.TGExtendedPlayer;
import techguns.items.guns.GenericGun;
import techguns.items.guns.IChargedProjectileFactory;
import techguns.items.guns.IProjectileFactory;
import techguns.items.guns.ammo.DamageModifier;

public class GuidedMissileProjectileHV extends GuidedMissileProjectile {

	public GuidedMissileProjectileHV(World worldIn) {
		super(worldIn);
	}

	public GuidedMissileProjectileHV(World par2World, EntityLivingBase p, float damage, float speed, int TTL,
			float spread, float dmgDropStart, float dmgDropEnd, float dmgMin, float penetration, boolean blockdamage,
			EnumBulletFirePos leftGun, float radius, double gravity) {
		super(par2World, p, damage, speed, TTL, spread, dmgDropStart, dmgDropEnd, dmgMin, penetration, blockdamage, leftGun,
				radius, gravity);
	}

	public GuidedMissileProjectileHV(World par2World, EntityLivingBase p, float damage, float speed, int TTL,
			float spread, float dmgDropStart, float dmgDropEnd, float dmgMin, float penetration, boolean blockdamage,
			EnumBulletFirePos leftGun, float radius, Entity target) {
		super(par2World, p, damage, speed, TTL, spread, dmgDropStart, dmgDropEnd, dmgMin, penetration, blockdamage, leftGun,
				radius, target);
	}

	
	public static class Factory implements IChargedProjectileFactory<GuidedMissileProjectileHV> {

		protected DamageModifier mod = new DamageModifier().setRadius(0.75f, 0f).setRange(0.75f, 0f).setVelocity(2.0f, 0f);
		
		@Override
		public GuidedMissileProjectileHV createProjectile(GenericGun gun, World world, EntityLivingBase p, float damage,
				float speed, int TTL, float spread, float dmgDropStart, float dmgDropEnd, float dmgMin,
				float penetration, boolean blockdamage, EnumBulletFirePos firePos, float radius, double gravity) {
			
			Entity target = null;
			if (p instanceof EntityPlayer) {
				TGExtendedPlayer epc = TGExtendedPlayer.get((EntityPlayer)p);
				if (epc.lockOnEntity != null && epc.lockOnTicks >= ((GenericGun)p.getActiveItemStack().getItem()).getLockOnTicks()) {
					target = epc.lockOnEntity;
				}
				//epc.lockOnEntity = null;
				//epc.lockOnTicks = 0;
			}

			DamageModifier mod = getDamageModifier();
			
			if (target != null) {
				return new GuidedMissileProjectileHV(world,p,mod.getDamage(damage),mod.getVelocity(speed),mod.getTTL(TTL),spread,mod.getRange(dmgDropStart),mod.getRange(dmgDropEnd),mod.getDamage(dmgMin),penetration,blockdamage,firePos,mod.getRadius(radius), target);
			} else {
				return new GuidedMissileProjectileHV(world,p,mod.getDamage(damage),mod.getVelocity(speed),mod.getTTL(TTL),spread,mod.getRange(dmgDropStart),mod.getRange(dmgDropEnd),mod.getDamage(dmgMin),penetration,blockdamage,firePos,mod.getRadius(radius),0.01f);
			}
		}

		
		
		@Override
		public DamageModifier getDamageModifier() {
			return mod;
		}

		@Override
		public DamageType getDamageType() {
			return DamageType.EXPLOSION;
		}

		@Override
		public GuidedMissileProjectileHV createChargedProjectile(World world, EntityLivingBase p, float damage,
				float speed, int TTL, float spread, float dmgDropStart, float dmgDropEnd, float dmgMin,
				float penetration, boolean blockdamage, EnumBulletFirePos firePos, float radius, double gravity,
				float charge, int ammoConsumed) {
			return null;
		}
		
	}
}
