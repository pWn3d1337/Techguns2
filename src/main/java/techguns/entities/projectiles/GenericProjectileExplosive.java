package techguns.entities.projectiles;

import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.Explosion;
import net.minecraft.world.World;
import techguns.TGPackets;
import techguns.Techguns;
import techguns.api.damagesystem.DamageType;
import techguns.damagesystem.TGDamageSource;
import techguns.damagesystem.TGExplosion;
import techguns.deatheffects.EntityDeathUtils.DeathType;
import techguns.items.guns.GenericGun;
import techguns.items.guns.IProjectileFactory;
import techguns.items.guns.ammo.DamageModifier;
import techguns.packets.PacketSpawnParticle;

public class GenericProjectileExplosive extends GenericProjectile {

	public GenericProjectileExplosive(World worldIn) {
		super(worldIn);
	}

	public GenericProjectileExplosive(World worldIn, double posX, double posY, double posZ, float yaw, float pitch,
			float damage, float speed, int TTL, float spread, float dmgDropStart, float dmgDropEnd, float dmgMin,
			float penetration, boolean blockdamage, EnumBulletFirePos firePos) {
		super(worldIn, posX, posY, posZ, yaw, pitch, damage, speed, TTL, spread, dmgDropStart, dmgDropEnd, dmgMin, penetration,
				blockdamage, firePos);
	}

	public GenericProjectileExplosive(World par2World, EntityLivingBase p, float damage, float speed, int TTL,
			float spread, float dmgDropStart, float dmgDropEnd, float dmgMin, float penetration, boolean blockdamage,
			EnumBulletFirePos firePos) {
		super(par2World, p, damage, speed, TTL, spread, dmgDropStart, dmgDropEnd, dmgMin, penetration, blockdamage, firePos);
	}
	
	@Override
	protected TGDamageSource getProjectileDamageSource() {
		TGDamageSource src = TGDamageSource.causeExplosionDamage(this, this.shooter, DeathType.GORE);
		src.armorPenetration = this.penetration;
		src.goreChance=1.0f;
		src.knockbackMultiplier=3.0f;
		return src;
	}

	@Override
	protected void onHitEffect(EntityLivingBase ent, RayTraceResult raytraceResultIn) {
		//super.onHitEffect(ent, rayTraceResultIn);
		this.explode(raytraceResultIn.hitVec.x, raytraceResultIn.hitVec.y, raytraceResultIn.hitVec.z);
	}

	@Override
	protected void hitBlock(RayTraceResult raytraceResultIn) {
		this.explode(raytraceResultIn.hitVec.x, raytraceResultIn.hitVec.y, raytraceResultIn.hitVec.z);
	}
	
	protected void explode(double x, double y, double z) {
		if (!this.world.isRemote){
			TGPackets.network.sendToAllAround(new PacketSpawnParticle("MiningChargeBlockExplosion", x,y,z), TGPackets.targetPointAroundEnt(this, 100.0f));

			//TGExplosion explosion = new TGExplosion(world, this.shooter, this, x, y, z, this.damage, this.damageMin, 0.25f, 2.5f,/*this.damageDropStart,this.damageDropEnd,*/ this.blockdamage?0.05f:0.0);

			//explosion.doExplosion(true);
			
			Explosion exp = new Explosion(world, this, x, y, z, 1.5f, false, this.blockdamage);
			exp.doExplosionA();
			exp.doExplosionB(false);
			
		}else {
			Techguns.proxy.createLightPulse(x, y, z, 5, 15, 3.0f, 0.5f, 1f, 0.9f, 0.5f);
		}
		this.setDead();
	}
	
	public static class Factory implements IProjectileFactory<GenericProjectileExplosive> {

		protected DamageModifier mod = new DamageModifier().setDmg(1.15f, 0f);
		
		@Override
		public DamageModifier getDamageModifier() {
			return mod;
		}
		
		@Override
		public GenericProjectileExplosive createProjectile(GenericGun gun, World world, EntityLivingBase p,
				float damage, float speed, int TTL, float spread, float dmgDropStart, float dmgDropEnd, float dmgMin,
				float penetration, boolean blockdamage, EnumBulletFirePos firePos, float radius, double gravity) {
			return new GenericProjectileExplosive(world, p, mod.getDamage(damage), speed, TTL, spread, dmgDropStart, dmgDropEnd, mod.getDamage(dmgMin), penetration, blockdamage, firePos);
		}

		@Override
		public DamageType getDamageType() {
			return DamageType.EXPLOSION;
		}
		
	}
}
