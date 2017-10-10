package techguns.items.guns;

import java.util.ArrayList;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import techguns.TGPackets;
import techguns.entities.projectiles.EnumBulletFirePos;
import techguns.entities.projectiles.SonicShotgunProjectile;
import techguns.items.guns.ammo.AmmoType;
import techguns.packets.PacketSpawnParticleOnEntity;
import techguns.util.MathUtil;

public class SonicShotgun extends GenericGun {

	public SonicShotgun(boolean addToGunList, String name, ProjectileSelector projectile_selector, boolean semiAuto,
			int minFiretime, int clipsize, int reloadtime, float damage, SoundEvent firesound, SoundEvent reloadsound,
			int TTL, float accuracy) {
		super(addToGunList, name, projectile_selector, semiAuto, minFiretime, clipsize, reloadtime, damage, firesound,
				reloadsound, TTL, accuracy);
	}

	public SonicShotgun(String name, ProjectileSelector projectileSelector, boolean semiAuto, int minFiretime,
			int clipsize, int reloadtime, float damage, SoundEvent firesound, SoundEvent reloadsound, int TTL,
			float accuracy) {
		super(name, projectileSelector, semiAuto, minFiretime, clipsize, reloadtime, damage, firesound, reloadsound, TTL,
				accuracy);
	}

	@Override
	protected void spawnProjectile(World world, EntityLivingBase player, ItemStack itemstack, float spread,
			float offset, float damagebonus, EnumBulletFirePos firePos) {
		
		int count = 5; //Base projectile count per ring
		int rings = 2; //number of rings
		
		spread = 0.075f;
		
		//Center
		ArrayList<Entity> entitiesHit = new ArrayList<Entity>();
		SonicShotgunProjectile proj = new SonicShotgunProjectile(world, player, damage*damagebonus, speed, this.ticksToLive, 0f, this.damageDropStart, this.damageDropEnd, this.damageMin*damagebonus, this.penetration, getDoBlockDamage(player), firePos);    	
		proj.entitiesHit = entitiesHit;
		proj.mainProjectile = true;
		if (offset>0.0f){
    		proj.shiftForward(offset);
    	}
    	world.spawnEntity(proj);
    	TGPackets.network.sendToAllAround(new PacketSpawnParticleOnEntity("SonicShotgunTrail", proj), TGPackets.targetPointAroundEnt(proj, 100.0f));
    	
		
		//Vec3 motion = Vec3.createVectorHelper(proj.motionX, proj.motionY, proj.motionZ).normalize(); //Shoot direction
		//Vec3 pos = Vec3.createVectorHelper(proj.posX, proj.posY, proj.posZ);
    	
    	//Rings
		for (int j = 1; j <= rings; j++) {
			
			float angle =(float) ((Math.PI*2.0)/(double)(count*j));
			for (int i = 0; i < (count*j); i++) {
			
				proj = new SonicShotgunProjectile(world, player, damage*damagebonus, speed, this.ticksToLive, 0f, this.damageDropStart, this.damageDropEnd, this.damageMin*damagebonus, this.penetration, getDoBlockDamage(player), firePos);    	
				proj.entitiesHit = entitiesHit;
				Vec3d dir = new Vec3d(1,0,0); //Vec3.createVectorHelper(1, 0, 0);
		
				dir = dir.rotateYaw(spread*j);
				dir = dir.rotatePitch(angle*i);
								
				dir = MathUtil.rotateVec3dAroundZ(dir, (float)(player.rotationPitch*MathUtil.D2R));
				
				dir = dir.rotateYaw((float) ((-player.rotationYawHead-90.0)*MathUtil.D2R));
				
				proj.motionX = dir.x*speed;
				proj.motionY = dir.y*speed;
				proj.motionZ = dir.z*speed;
				
				//System.out.println("MOTION:"+proj.motionX+","+proj.motionY+","+proj.motionZ);
				
		    	if (offset>0.0f){
		    		proj.shiftForward(offset);
		    	}
		
		    	world.spawnEntity(proj);
			}
		}
	}
	
	

}
