package techguns.items.guns;

import java.util.List;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import techguns.TGSounds;
import techguns.capabilities.TGExtendedPlayer;
import techguns.client.audio.TGSoundCategory;
import techguns.entities.projectiles.GenericProjectile;
import techguns.items.guns.ammo.AmmoType;
import techguns.util.SoundUtil;

public class GuidedMissileLauncher extends GenericGunCharge {
	
	public static final float LOCK_RANGE = 150.0f;
	public static final float LOCK_ERROR_THRESHOLD = 0.5f;
	//public static final int LOCK_TICKS = 10; //ticks required for lock completion
	//public static final int LOCK_COMPLETE_TICKS = 20; //This is value is used when the lock is complete

	public GuidedMissileLauncher(String name, ChargedProjectileSelector projectile_selector, boolean semiAuto,
			int minFiretime, int clipsize, int reloadtime, float damage, SoundEvent firesound, SoundEvent reloadsound,
			int TTL, float accuracy, float fullChargeTime, int ammoConsumedOnFullCharge) {
		super(name, projectile_selector, semiAuto, minFiretime, clipsize, reloadtime, damage, firesound, reloadsound, TTL,
				accuracy, fullChargeTime, ammoConsumedOnFullCharge);
	}

	@Override
	public void onUsingTick(ItemStack stack, EntityLivingBase player, int count) {
		TGExtendedPlayer epc = TGExtendedPlayer.get((EntityPlayer)player);
//		if (epc.isReloading(false)) {
//			this.onPlayerStoppedUsing(stack, player.world, player, 0);
//			return;
//		}
//		
		super.onUsingTick(stack, player, count);
		//System.out.println("onUsingTick: "+count);
		traceTarget(player);
		
		//Handle sounds
		if (player instanceof EntityPlayer) {			
			if (epc.lockOnTicks >= this.lockOnTicks) {
				if (count % 4 == 0) SoundUtil.playSoundOnEntityGunPosition(player.world, player, TGSounds.LOCKED_BEEP, SOUND_DISTANCE, 1.0F, false, false, TGSoundCategory.PLAYER_EFFECT);
			}else if (epc.lockOnTicks > 0) {
				if (count % 4 == 0) SoundUtil.playSoundOnEntityGunPosition(player.world, player, TGSounds.LOCKON_BEEP, SOUND_DISTANCE, 1.0F, false, false, TGSoundCategory.PLAYER_EFFECT);
			}else {
				if (count % 12 == 0) SoundUtil.playSoundOnEntityGunPosition(player.world, player, TGSounds.LOCKON_BEEP, SOUND_DISTANCE, 0.5F, false, false, TGSoundCategory.PLAYER_EFFECT);
			}
		}
	}

	@Override
	protected void startCharge(ItemStack item, World world, EntityPlayer player) {
		//TGExtendedPlayer txp = TGExtendedPlayer.get(player);
		//if (txp.isReloading(false)) return;
		//if (((GenericGunCharge)item.getItem()).getAmmoLeft(item) <= 0) return;
		
		super.startCharge(item, world, player);
		
		//if (this.getAmmoLeft(item) <= 0) {
		TGExtendedPlayer epc = TGExtendedPlayer.get(player);
		epc.lockOnEntity = null;
		epc.lockOnTicks = -1;
	}
	
	protected void traceTarget(EntityLivingBase shooter) {
		Vec3d vec3d1 = new Vec3d(shooter.posX, shooter.posY+shooter.getEyeHeight(), shooter.posZ);
		shooter.getLookVec();
		Vec3d vec3d = vec3d1.add(shooter.getLookVec().scale(LOCK_RANGE));
		
		
		RayTraceResult raytraceresult = shooter.world.rayTraceBlocks(vec3d1, vec3d, false, true, false);

		
		//System.out.println("Tracking target...");
		
		if (raytraceresult != null) {
			vec3d = new Vec3d(raytraceresult.hitVec.x, raytraceresult.hitVec.y, raytraceresult.hitVec.z);
		}

		Entity entity = this.findEntityOnPath(shooter, vec3d1, vec3d);

		if (entity != null) {
			raytraceresult = new RayTraceResult(entity);
		}

		if (raytraceresult != null && raytraceresult.entityHit instanceof EntityPlayer) {
			EntityPlayer entityplayer = (EntityPlayer) raytraceresult.entityHit;

			if (shooter instanceof EntityPlayer && !((EntityPlayer) shooter).canAttackPlayer(entityplayer)) {
				raytraceresult = null;
			}
		}

		if (shooter instanceof EntityPlayer ) {
			TGExtendedPlayer epc = TGExtendedPlayer.get((EntityPlayer)shooter);
			if (raytraceresult != null && raytraceresult.entityHit != null) {
				if (epc.lockOnEntity != null && epc.lockOnEntity.isEntityAlive()) {
					if (epc.lockOnEntity == raytraceresult.entityHit) {
						epc.lockOnTicks++;
						if(epc.lockOnTicks >= this.lockOnTicks) {
							//LOCK COMPLETED!
							epc.lockOnTicks = this.lockOnTicks+this.lockOnPersistTicks;
						}
					}else {
						if (epc.lockOnTicks > 0) {
							epc.lockOnTicks--;
						}else {
							epc.lockOnEntity = null;
							epc.lockOnTicks = 0;
						}
					}
				}else {
					epc.lockOnEntity = raytraceresult.entityHit;
					epc.lockOnTicks = 1;
				}
			}else {
				if (epc.lockOnEntity != null && !epc.lockOnEntity.isEntityAlive()) {
					epc.lockOnEntity = null;
					epc.lockOnTicks = 0;
				}else {
					if (epc.lockOnTicks > 0) {
						epc.lockOnTicks--;
					}else {
						epc.lockOnEntity = null;
					}
				}
			}
//			if (epc.lockOnEntity != null) {
//				System.out.println("Locking on: "+epc.lockOnEntity.getName()+" - Status: "+epc.lockOnTicks);
//			}
		}
	}
	
	
	protected Entity findEntityOnPath(EntityLivingBase shooter, Vec3d start, Vec3d end) {
		Entity entity = null;
		Vec3d ray = shooter.getLookVec().scale(LOCK_RANGE);
		List<Entity> list = shooter.world.getEntitiesInAABBexcluding(shooter,
				shooter.getEntityBoundingBox().expand(ray.x, ray.y, ray.z).grow(1.0D),
				GenericProjectile.BULLET_TARGETS);
		double d0 = 0.0D;
		
		Entity prevTarget = null;
		if (shooter instanceof EntityPlayer ) {
			TGExtendedPlayer epc = TGExtendedPlayer.get((EntityPlayer)shooter);
			prevTarget = epc.lockOnEntity;
		}
		

		for (int i = 0; i < list.size(); ++i) {
			Entity entity1 = list.get(i);

			AxisAlignedBB axisalignedbb = entity1.getEntityBoundingBox().grow(LOCK_ERROR_THRESHOLD);
			RayTraceResult raytraceresult = axisalignedbb.calculateIntercept(start, end);

			if (raytraceresult != null) {
				if (prevTarget == null || entity != prevTarget) {
					if (entity1 == prevTarget) {
						entity = prevTarget;
					}else {		
						double d1 = start.squareDistanceTo(raytraceresult.hitVec);
		
						if (d1 < d0 || d0 == 0.0D) {
							entity = entity1;
							d0 = d1;
						}
					}
				}
			}
		}

		return entity;
	}
	
	@Override
	public int consumeAmmoCharge(ItemStack item, float f, boolean creative) {
		return 0;
	}
}
