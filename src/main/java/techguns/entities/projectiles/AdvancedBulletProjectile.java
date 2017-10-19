package techguns.entities.projectiles;

import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.RayTraceResult.Type;
import net.minecraft.world.World;
import techguns.TGSounds;
import techguns.Techguns;
import techguns.api.damagesystem.DamageType;
import techguns.items.guns.GenericGun;
import techguns.items.guns.IProjectileFactory;

public class AdvancedBulletProjectile extends GenericProjectile{


	public AdvancedBulletProjectile(World world, EntityLivingBase p, float damage, float speed, int TTL, float spread,
			float dmgDropStart, float dmgDropEnd, float dmgMin, float penetration, boolean blockdamage, EnumBulletFirePos leftGun) {
		super(world, p, damage, speed, TTL, spread, dmgDropStart, dmgDropEnd, dmgMin, penetration, blockdamage, leftGun);
	}
	
	public AdvancedBulletProjectile(World worldIn) {
		super(worldIn);
	}
	

	public static class Factory implements IProjectileFactory<AdvancedBulletProjectile> {

		@Override
		public AdvancedBulletProjectile createProjectile(GenericGun gun, World world, EntityLivingBase p, float damage, float speed, int TTL, float spread, float dmgDropStart, float dmgDropEnd,
				float dmgMin, float penetration, boolean blockdamage, EnumBulletFirePos firePos, float radius, double gravity) {
			return new AdvancedBulletProjectile(world,p,damage,speed,TTL,spread,dmgDropStart,dmgDropEnd,dmgMin,penetration,blockdamage,firePos);
		}

		@Override
		public DamageType getDamageType() {
			return DamageType.PROJECTILE;
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

	
}
