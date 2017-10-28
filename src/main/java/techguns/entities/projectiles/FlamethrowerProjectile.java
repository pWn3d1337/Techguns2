package techguns.entities.projectiles;

import elucent.albedo.lighting.ILightProvider;
import elucent.albedo.lighting.Light;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.init.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.Optional;
import techguns.TGPackets;
import techguns.api.damagesystem.DamageType;
import techguns.client.ClientProxy;
import techguns.damagesystem.TGDamageSource;
import techguns.deatheffects.EntityDeathUtils.DeathType;
import techguns.items.guns.GenericGun;
import techguns.items.guns.IProjectileFactory;
import techguns.packets.PacketSpawnParticle;
import techguns.packets.PacketSpawnParticleOnEntity;
import techguns.util.MathUtil;

@Optional.Interface(iface="elucent.albedo.lighting.ILightProvider", modid="albedo")
public class FlamethrowerProjectile extends GenericProjectile implements ILightProvider {

	boolean piercing = false;
	
	float chanceToIgnite = 0.5f;
	int entityIgniteTime = 3;
	
	public FlamethrowerProjectile(World worldIn) {
		super(worldIn);
		ClientProxy.get().createFXOnEntity("FlamethrowerTrail", this);
	}

	public FlamethrowerProjectile(World worldIn, double posX, double posY, double posZ, float yaw, float pitch, float damage, float speed, int TTL, float spread, float dmgDropStart,
			float dmgDropEnd, float dmgMin, float penetration, boolean blockdamage, EnumBulletFirePos leftGun, double gravity) {
		super(worldIn, posX, posY, posZ, yaw, pitch, damage, speed, TTL, spread, dmgDropStart, dmgDropEnd, dmgMin, penetration, blockdamage, leftGun);
		this.gravity=gravity;
	}
	
	public FlamethrowerProjectile(World par2World, EntityLivingBase p, float damage, float speed, int TTL, float spread, float dmgDropStart, float dmgDropEnd, float dmgMin,
			float penetration, boolean blockdamage, EnumBulletFirePos leftGun, double gravity) {
		super(par2World, p, damage, speed, TTL, spread, dmgDropStart, dmgDropEnd, dmgMin, penetration, blockdamage, leftGun);
		this.gravity=gravity;
	}

	@Override
	protected TGDamageSource getProjectileDamageSource() {
		TGDamageSource src = TGDamageSource.causeFireDamage(this, this.shooter, DeathType.DEFAULT);
    	src.armorPenetration = this.penetration;
    	src.setNoKnockback();
    	return src;
	}

	@Override
	protected void onHitEffect(EntityLivingBase ent, RayTraceResult rayTraceResult) {
		ent.setFire(this.entityIgniteTime);
	}

	@Override
	protected void hitBlock(RayTraceResult mop) {

		if (this.blockdamage) {
			burnBlocks(world, mop, chanceToIgnite);
		}
		
		TGPackets.network.sendToAllAround(new PacketSpawnParticle("FlamethrowerImpact", this.posX,this.posY,this.posZ), TGPackets.targetPointAroundEnt(this, 50.0f));

	}
	
	public static class Factory implements IProjectileFactory<FlamethrowerProjectile> {

		@Override
		public FlamethrowerProjectile createProjectile(GenericGun gun, World world, EntityLivingBase p, float damage, float speed, int TTL, float spread, float dmgDropStart, float dmgDropEnd,
				float dmgMin, float penetration, boolean blockdamage, EnumBulletFirePos firePos, float radius, double gravity) {
			//offset = -0.15 -0.05 0.5
			float offsetX = 0.0f;
			if (firePos == EnumBulletFirePos.RIGHT) offsetX = -0.15f;
			else if (firePos == EnumBulletFirePos.LEFT) offsetX = 0.15f;			
			float offsetY = -0.05f;
			float offsetZ = 0.5f;
			TGPackets.network.sendToAllAround(new PacketSpawnParticleOnEntity("FlamethrowerFireFX", p, offsetX, offsetY, offsetZ, true), TGPackets.targetPointAroundEnt(p, 25.0f));
			
			return new FlamethrowerProjectile(world,p,damage,speed,TTL,spread,dmgDropStart,dmgDropEnd,dmgMin,penetration,blockdamage,firePos,gravity);
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
				.color(1.0f, 0.8f, 0f)
				.radius(2.5f)
				.build();
	}
}
