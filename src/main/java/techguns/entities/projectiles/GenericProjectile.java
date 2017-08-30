package techguns.entities.projectiles;

import java.util.List;

import javax.annotation.Nullable;

import com.google.common.base.Predicate;
import com.google.common.base.Predicates;

import elucent.albedo.lighting.ILightProvider;
import elucent.albedo.lighting.Light;
import io.netty.buffer.ByteBuf;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.IProjectile;
import net.minecraft.entity.monster.EntityEnderman;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.entity.projectile.EntityArrow;
import net.minecraft.init.SoundEvents;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.play.server.SPacketChangeGameState;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EntitySelectors;
import net.minecraft.util.EnumHandSide;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.Optional;
import net.minecraftforge.fml.common.registry.IEntityAdditionalSpawnData;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import techguns.api.damagesystem.DamageType;
import techguns.api.npc.factions.ITGNpcTeam;
import techguns.damagesystem.DamageSystem;
import techguns.damagesystem.TGDamageSource;
import techguns.deatheffects.EntityDeathUtils.DeathType;
import techguns.factions.TGNpcFactions;
import techguns.items.guns.GenericGun;
import techguns.items.guns.IProjectileFactory;

public class GenericProjectile extends Entity implements IProjectile, IEntityAdditionalSpawnData {

	public static final Predicate<Entity> BULLET_TARGETS = Predicates.and(EntitySelectors.CAN_AI_TARGET,
			EntitySelectors.IS_ALIVE, new Predicate<Entity>() {
				public boolean apply(@Nullable Entity ent) {
					return ent.canBeCollidedWith();
				}
			});

	float damage;
	public float speed = 1.0f; // speed in blocks per tick

	protected int ticksToLive = 100;
	protected int lifetime = 100; // TTL but not reduced per tick

	// DMG drop stats
	int damageDropStart;
	int damageDropEnd;
	float damageMin;
	protected EntityLivingBase shooter;

	float penetration = 0.0f;

	boolean silenced = false;
	protected boolean blockdamage = false;

	boolean posInitialized = false;
	double startX;
	double startY;
	double startZ;

	float radius=0.0f;
	double gravity=0.0f;
	
	public GenericProjectile(World worldIn) {
		super(worldIn);
		this.isImmuneToFire = true;
		this.setNoGravity(true);
		this.height=0.5f;
	}

	private static EnumBulletFirePos getLeftHand(EntityLivingBase elb, EnumBulletFirePos firePos){
		if (elb instanceof EntityPlayer){
			EntityPlayer p = (EntityPlayer) elb;
			if(p.getPrimaryHand() == EnumHandSide.LEFT){
				if(firePos==EnumBulletFirePos.LEFT) {
					return EnumBulletFirePos.RIGHT;
				} else if (firePos==EnumBulletFirePos.RIGHT) {
					return EnumBulletFirePos.LEFT;
				}
			}
		}
		return firePos;
	}
	
	public GenericProjectile(World par2World, EntityLivingBase p, float damage, float speed, int TTL, float spread,
			int dmgDropStart, int dmgDropEnd, float dmgMin, float penetration, boolean blockdamage, EnumBulletFirePos firePos) {
		this(par2World, p.posX, p.posY + p.getEyeHeight(), p.posZ, p.rotationYawHead, p.rotationPitch, damage, speed,
				TTL, spread, dmgDropStart, dmgDropEnd, dmgMin, penetration, blockdamage, getLeftHand(p,firePos));
		this.shooter = p;
	}

	public GenericProjectile(World worldIn, double posX, double posY, double posZ, float yaw, float pitch, float damage,
			float speed, int TTL, float spread, int dmgDropStart, int dmgDropEnd, float dmgMin, float penetration,
			boolean blockdamage, EnumBulletFirePos firePos) {
		this(worldIn);
		
		this.shooter = null;
		this.setSize(0.25F, 0.25F);

		this.setLocationAndAngles(posX, posY, posZ, yaw + (float) (spread - (2 * Math.random() * spread)) * 40.0f,
				pitch + (float) (spread - (2 * Math.random() * spread)) * 40.0f);

		if (firePos==EnumBulletFirePos.RIGHT) {
			this.posX -= (double) (MathHelper.cos(this.rotationYaw / 180.0F * (float) Math.PI) * 0.16F);
			//this.posY -= 0.10000000149011612D;
			this.posZ -= (double) (MathHelper.sin(this.rotationYaw / 180.0F * (float) Math.PI) * 0.16F);
		} else if(firePos==EnumBulletFirePos.LEFT) {
			this.posX += (double) (MathHelper.cos(this.rotationYaw / 180.0F * (float) Math.PI) * 0.16F);
			//this.posY -= 0.10000000149011612D;
			this.posZ += (double) (MathHelper.sin(this.rotationYaw / 180.0F * (float) Math.PI) * 0.16F);
		} 
		this.posY -= 0.10000000149011612D;
		
		this.setPosition(this.posX, this.posY, this.posZ);
		// this.yOffset = 0.0F;
		float f = 0.4F;
		this.motionX = (double) (-MathHelper.sin(this.rotationYaw / 180.0F * (float) Math.PI)
				* MathHelper.cos(this.rotationPitch / 180.0F * (float) Math.PI) * f);
		this.motionZ = (double) (MathHelper.cos(this.rotationYaw / 180.0F * (float) Math.PI)
				* MathHelper.cos(this.rotationPitch / 180.0F * (float) Math.PI) * f);
		this.motionY = (double) (-MathHelper.sin((this.rotationPitch) / 180.0F * (float) Math.PI) * f);
		this.setThrowableHeading(this.motionX, this.motionY, this.motionZ, 1.5f, 1.0F);

		Vec3d motion = new Vec3d(this.motionX, this.motionY, this.motionZ);
		motion.normalize();
		this.motionX = motion.x;
		this.motionY = motion.y;
		this.motionZ = motion.z;

		this.motionX *= speed;
		this.motionY *= speed;
		this.motionZ *= speed;
		this.damage = damage;
		this.speed = speed;
		this.ticksToLive = TTL;
		this.lifetime = TTL;
		this.penetration = penetration;

		this.damageMin = dmgMin;
		this.damageDropStart = dmgDropStart;
		this.damageDropEnd = dmgDropEnd;
		this.blockdamage = blockdamage;
	}

	/**
	 * Similar to setArrowHeading, it's point the throwable entity to a x, y, z
	 * direction.
	 */
	@Override
	public void setThrowableHeading(double x, double y, double z, float velocity, float inaccuracy) {
		float f = MathHelper.sqrt(x * x + y * y + z * z);
		x = x / (double) f;
		y = y / (double) f;
		z = z / (double) f;
		x = x + this.rand.nextGaussian() * 0.007499999832361937D * (double) inaccuracy;
		y = y + this.rand.nextGaussian() * 0.007499999832361937D * (double) inaccuracy;
		z = z + this.rand.nextGaussian() * 0.007499999832361937D * (double) inaccuracy;
		x = x * (double) velocity;
		y = y * (double) velocity;
		z = z * (double) velocity;
		this.motionX = x;
		this.motionY = y;
		this.motionZ = z;
		float f1 = MathHelper.sqrt(x * x + z * z);
		this.rotationYaw = (float) (MathHelper.atan2(x, z) * (180D / Math.PI));
		this.rotationPitch = (float) (MathHelper.atan2(y, (double) f1) * (180D / Math.PI));
		this.prevRotationYaw = this.rotationYaw;
		this.prevRotationPitch = this.rotationPitch;
	}

	@Override
	public void onUpdate() {
		if (!this.posInitialized && !this.world.isRemote) {
			this.initStartPos();
		}
		this.lastTickPosX = this.posX;
		this.lastTickPosY = this.posY;
		this.lastTickPosZ = this.posZ;

		super.onUpdate();

		if (this.prevRotationPitch == 0.0F && this.prevRotationYaw == 0.0F) {
			float f = MathHelper.sqrt(this.motionX * this.motionX + this.motionZ * this.motionZ);
			this.rotationYaw = (float) (MathHelper.atan2(this.motionX, this.motionZ) * (180D / Math.PI));
			this.rotationPitch = (float) (MathHelper.atan2(this.motionY, (double) f) * (180D / Math.PI));
			this.prevRotationYaw = this.rotationYaw;
			this.prevRotationPitch = this.rotationPitch;
		}

		--this.ticksToLive;
		Vec3d vec3d1 = new Vec3d(this.posX, this.posY, this.posZ);
		Vec3d vec3d = new Vec3d(this.posX + this.motionX, this.posY + this.motionY, this.posZ + this.motionZ);
		RayTraceResult raytraceresult = this.world.rayTraceBlocks(vec3d1, vec3d, false, true, false);
		vec3d1 = new Vec3d(this.posX, this.posY, this.posZ);
		vec3d = new Vec3d(this.posX + this.motionX, this.posY + this.motionY, this.posZ + this.motionZ);

		if (raytraceresult != null) {
			vec3d = new Vec3d(raytraceresult.hitVec.x, raytraceresult.hitVec.y, raytraceresult.hitVec.z);
		}

		Entity entity = this.findEntityOnPath(vec3d1, vec3d);

		if (entity != null) {
			raytraceresult = new RayTraceResult(entity);
		}

		if (raytraceresult != null && raytraceresult.entityHit instanceof EntityPlayer) {
			EntityPlayer entityplayer = (EntityPlayer) raytraceresult.entityHit;

			if (this.shooter instanceof EntityPlayer && !((EntityPlayer) this.shooter).canAttackPlayer(entityplayer)) {
				raytraceresult = null;
			}
		}

		if (raytraceresult != null) {
			this.onHit(raytraceresult);
		}

		/*
		 * if (this.getIsCritical()) { for (int k = 0; k < 4; ++k) {
		 * this.world.spawnParticle(EnumParticleTypes.CRIT, this.posX +
		 * this.motionX * (double) k / 4.0D, this.posY + this.motionY * (double)
		 * k / 4.0D, this.posZ + this.motionZ * (double) k / 4.0D,
		 * -this.motionX, -this.motionY + 0.2D, -this.motionZ); } }
		 */

		this.posX += this.motionX;
		this.posY += this.motionY;
		this.posZ += this.motionZ;
		float f4 = MathHelper.sqrt(this.motionX * this.motionX + this.motionZ * this.motionZ);
		this.rotationYaw = (float) (MathHelper.atan2(this.motionX, this.motionZ) * (180D / Math.PI));

		for (this.rotationPitch = (float) (MathHelper.atan2(this.motionY, (double) f4)
				* (180D / Math.PI)); this.rotationPitch
						- this.prevRotationPitch < -180.0F; this.prevRotationPitch -= 360.0F) {
			;
		}

		while (this.rotationPitch - this.prevRotationPitch >= 180.0F) {
			this.prevRotationPitch += 360.0F;
		}

		while (this.rotationYaw - this.prevRotationYaw < -180.0F) {
			this.prevRotationYaw -= 360.0F;
		}

		while (this.rotationYaw - this.prevRotationYaw >= 180.0F) {
			this.prevRotationYaw += 360.0F;
		}

		this.rotationPitch = this.prevRotationPitch + (this.rotationPitch - this.prevRotationPitch) * 0.2F;
		this.rotationYaw = this.prevRotationYaw + (this.rotationYaw - this.prevRotationYaw) * 0.2F;
		float f1 = 0.99F;
		float f2 = 0.05F;

		if (this.isInWater()) {
			for (int i = 0; i < 4; ++i) {
				float f3 = 0.25F;
				this.world.spawnParticle(EnumParticleTypes.WATER_BUBBLE, this.posX - this.motionX * 0.25D,
						this.posY - this.motionY * 0.25D, this.posZ - this.motionZ * 0.25D, this.motionX, this.motionY,
						this.motionZ);
			}

			f1 = 0.6F;
		}

		if (this.isWet()) {
			this.extinguish();
		}

		this.motionX *= (double) f1;
		this.motionY *= (double) f1;
		this.motionZ *= (double) f1;

		/*if (!this.hasNoGravity()) {
			this.motionY -= 0.05000000074505806D;
		}*/
		if (this.gravity != 0) {
			this.motionY -= this.gravity;
		}

		this.setPosition(this.posX, this.posY, this.posZ);
		this.doBlockCollisions();
		
		if (this.ticksToLive<=0){
			this.setDead();
		}
	}

    protected void initStartPos(){
    	this.startX=this.posX;
    	this.startY=this.posY;
    	this.startZ=this.posZ;
    	this.posInitialized=true;
    }

	@Nullable
	protected Entity findEntityOnPath(Vec3d start, Vec3d end) {
		Entity entity = null;
		List<Entity> list = this.world.getEntitiesInAABBexcluding(this,
				this.getEntityBoundingBox().expand(this.motionX, this.motionY, this.motionZ).grow(1.0D),
				BULLET_TARGETS);
		double d0 = 0.0D;

		for (int i = 0; i < list.size(); ++i) {
			Entity entity1 = list.get(i);

			if (entity1 != this.shooter ) {
				AxisAlignedBB axisalignedbb = entity1.getEntityBoundingBox().grow(0.30000001192092896D);
				RayTraceResult raytraceresult = axisalignedbb.calculateIntercept(start, end);

				if (raytraceresult != null) {
					double d1 = start.squareDistanceTo(raytraceresult.hitVec);

					if (d1 < d0 || d0 == 0.0D) {
						entity = entity1;
						d0 = d1;
					}
				}
			}
		}

		return entity;
	}

	@Override
	public float getEyeHeight() {
		return 0.25f;
	}

	/**
	 * Called when the arrow hits a block or an entity
	 */
	protected void onHit(RayTraceResult raytraceResultIn) {
		
		if (raytraceResultIn.entityHit != null && !this.world.isRemote) {

			TGDamageSource src = getProjectileDamageSource();

			if (raytraceResultIn.entityHit instanceof EntityLivingBase) {
				EntityLivingBase ent = (EntityLivingBase) raytraceResultIn.entityHit;

				// Check for Headshot

				/*
				 * double heightDiff = this.posY-ent.posY;
				 * //System.out.println("HeightDiff:"+heightDiff); if
				 * (this.isHeadshot(ent, heightDiff)){
				 * this.worldObj.playSoundAtEntity(ent,
				 * "techguns:effects.bulletimpact", 1.0F, 1.0F ); dmg=dmg*2.0f;
				 * }
				 */

				float dmg = DamageSystem.getDamageFactor(this.shooter, ent) * this.getDamage();

				if (dmg > 0.0f) {
					
					if (src.knockbackMultiplier==0.0f){
	                	TGDamageSource knockback = TGDamageSource.getKnockbackDummyDmgSrc(this, this.shooter);
		        		ent.attackEntityFrom(knockback, 0.01f);
        			}
					
					ent.attackEntityFrom(src, dmg);
					if (src.wasSuccessful()) {
						
						if (ent instanceof EntityLiving) {
							this.setAIRevengeTarget(((EntityLiving) ent));
						}
	
						this.onHitEffect(ent);
					}
				}

			} else {
				raytraceResultIn.entityHit.attackEntityFrom(src, this.getDamage());

			}

			this.setDead();
		}

		// if (!this.worldObj.isRemote)
		// {
		if (raytraceResultIn.typeOfHit == raytraceResultIn.typeOfHit.BLOCK) {

			// TODO add check if block blocks movement

			// IBlockState bs =
			// this.world.getBlockState(raytraceResultIn.getBlockPos());
			// Block b = bs.getBlock();
			// System.out.println("HitBlock of Type:"+b);

			// if (!b.getBlocksMovement(this.world,
			// raytraceResultIn.getBlockPos())) {

			// hit a block
			this.hitBlock(raytraceResultIn);
			// } else {

			// }

			this.setDead();
			// }
		}
		// System.out.println("Impact!");

	}

	protected void setAIRevengeTarget(EntityLiving ent) {
		boolean shootBack = false;
		if (this.shooter != null) {
			if (shooter instanceof ITGNpcTeam && ent instanceof ITGNpcTeam) {
				shootBack = TGNpcFactions.isHostile(((ITGNpcTeam) shooter).getTGFaction(),
						((ITGNpcTeam) ent).getTGFaction());
				// System.out.println("Shoot Back?:"+shootBack);
			} else {

				if (!(shooter instanceof EntityPlayer)) {
					shootBack = true;
					// System.out.println("Shoot Back!:"+shooter.toString());
				}
			}
		}

		if (shootBack) {
			// System.out.println("shooting back against:"+shooter+" from:
			// "+ent);
			ent.setRevengeTarget(shooter);
			// ent.setLastAttacker(shooter);
		}
	}

	public void shiftForward(float factor) {
		this.posX += this.motionX * factor;
		this.posY += this.motionY * factor;
		this.posZ += this.motionZ * factor;
	}

	
	/**
	 * Override in subclass for entity hit effect (potion effects etc)
	 * @param ent
	 */
	protected void onHitEffect(EntityLivingBase ent) {
	}

	/**
	 * Override for block hit effect
	 * @param raytraceResultIn
	 */
	protected void hitBlock(RayTraceResult raytraceResultIn) {
	}

    /**
     * Updates the entity motion clientside, called by packets from the server
     */
    @SideOnly(Side.CLIENT)
    @Override
    public void setVelocity(double x, double y, double z)
    {
        this.motionX = x;
        this.motionY = y;
        this.motionZ = z;

        if (this.prevRotationPitch == 0.0F && this.prevRotationYaw == 0.0F)
        {
            float f = MathHelper.sqrt(x * x + z * z);
            this.rotationPitch = (float)(MathHelper.atan2(y, (double)f) * (180D / Math.PI));
            this.rotationYaw = (float)(MathHelper.atan2(x, z) * (180D / Math.PI));
            this.prevRotationPitch = this.rotationPitch;
            this.prevRotationYaw = this.rotationYaw;
            this.setLocationAndAngles(this.posX, this.posY, this.posZ, this.rotationYaw, this.rotationPitch);
        }
    }
	
	/**
	 * Override to change damage type etc
	 * 
	 * @return
	 */
	protected TGDamageSource getProjectileDamageSource() {
		TGDamageSource src = TGDamageSource.causeBulletDamage(this, this.shooter, DeathType.GORE);
		src.armorPenetration = this.penetration;
		src.setNoKnockback();
		return src;
	}

	/**
	 * Return the travelled distance
	 * 
	 * @return
	 */
	protected double getDistanceTravelled() {
		Vec3d start = new Vec3d(this.startX, this.startY, this.startZ);
		Vec3d end = new Vec3d(this.posX, this.posY, this.posZ);
		return start.distanceTo(end);
	}

    public GenericProjectile setSilenced(){
    	this.silenced=true;
    	return this;
    }
	
	/**
	 * Get Damage for distance
	 * 
	 * @return
	 */
	protected float getDamage() {

		if (this.damageDropEnd==0) { //not having damage drop
			return this.damage;
		}
		
		double distance = this.getDistanceTravelled();

		double dropStart = this.damageDropStart;
		double dropEnd = this.damageDropEnd;

		if (distance <= dropStart) {
			return this.damage;
		} else if (distance > dropEnd) {
			return this.damageMin;
		} else {
			float factor = 1.0f - (float) ((distance - dropStart) / (dropEnd - dropStart));
			return (this.damageMin + (this.damage - this.damageMin) * factor);
		}
	}

	@Override
	protected void entityInit() {
	}

	@Override
	protected void readEntityFromNBT(NBTTagCompound tags) {

		this.damage = tags.getFloat("damage");
		this.speed = tags.getFloat("speed");

		this.ticksToLive = tags.getShort("ticksToLive");
		this.lifetime = tags.getShort("lifetime");

		this.damageDropStart = tags.getShort("damageDropStart");
		this.damageDropEnd = tags.getShort("damageDropEnd");
		this.damageMin = tags.getFloat("damageMin");

	}

	@Override
	protected void writeEntityToNBT(NBTTagCompound tags) {

		tags.setFloat("damage", this.damage);
		tags.setFloat("speed", this.speed);

		tags.setShort("ticksToLive", (short) this.ticksToLive);
		tags.setShort("lifetime", (short) this.lifetime);

		tags.setShort("damageDropStart", (short) this.damageDropStart);
		tags.setShort("damageDropEnd", (short) this.damageDropEnd);
		tags.setFloat("damageMin", this.damageMin);
	}

	@Override
	public void writeSpawnData(ByteBuf buffer) {
		buffer.writeFloat(this.speed);
	}

	@Override
	public void readSpawnData(ByteBuf additionalData) {
		this.speed=additionalData.readFloat();
	}
	
	public static class Factory implements IProjectileFactory<GenericProjectile> {

		@Override
		public GenericProjectile createProjectile(GenericGun gun, World world, EntityLivingBase p, float damage, float speed, int TTL, float spread, int dmgDropStart, int dmgDropEnd,
				float dmgMin, float penetration, boolean blockdamage, EnumBulletFirePos firePos, float radius, double gravity) {
			return new GenericProjectile(world,p,damage,speed,TTL,spread,dmgDropStart,dmgDropEnd,dmgMin,penetration,blockdamage,firePos);
		}

		@Override
		public DamageType getDamageType() {
			return DamageType.PROJECTILE;
		}
		
	}
}
