package techguns.entities.npcs;

import java.util.Random;

import javax.annotation.Nullable;

import net.minecraft.entity.EntityFlying;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.entity.ai.EntityAIFindEntityNearestPlayer;
import net.minecraft.entity.ai.EntityMoveHelper;
import net.minecraft.entity.item.EntityXPOrb;
import net.minecraft.entity.monster.EntityGhast;
import net.minecraft.entity.monster.IMob;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.util.DamageSource;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.EnumDifficulty;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import techguns.TGPackets;
import techguns.TGSounds;
import techguns.Techguns;
import techguns.api.npc.INPCTechgunsShooter;
import techguns.api.npc.INpcTGDamageSystem;
import techguns.client.audio.TGSoundCategory;
import techguns.damagesystem.TGDamageSource;
import techguns.entities.projectiles.EnumBulletFirePos;
import techguns.entities.projectiles.GenericProjectile;
import techguns.entities.projectiles.RocketProjectile;
import techguns.packets.PacketPlaySound;
import techguns.util.MathUtil;

public class AttackHelicopter extends EntityFlying implements IMob, INpcTGDamageSystem, INPCTechgunsShooter
{
    private static final DataParameter<Boolean> ATTACKING = EntityDataManager.<Boolean>createKey(EntityGhast.class, DataSerializers.BOOLEAN);

    private int ambientSoundTick = 0;
    
    protected static final int TARGET_FLY_HEIGHT=24;
    
    public static final int MAX_DEATH_TIME = 100;
    
    protected static final ResourceLocation LOOT = new ResourceLocation(Techguns.MODID, "entities/attackhelicopter");
	
    
    public AttackHelicopter(World worldIn)
    {
        super(worldIn);
        this.setSize(4.0F, 4.0F);
        this.isImmuneToFire = true;
        this.experienceValue = 5;
        this.moveHelper = new AttackHelicopter.AttackHelicopterMoveHelper(this);
    }

	@Override
    @Nullable
    protected ResourceLocation getLootTable()
    {
        return LOOT;
    }
    
    protected void initEntityAI()
    {
        this.tasks.addTask(5, new AttackHelicopter.AIRandomFly(this));
        this.tasks.addTask(7, new AttackHelicopter.AILookAround(this));
        this.tasks.addTask(7, new AttackHelicopter.AIHelicopterAttack(this));
        this.targetTasks.addTask(1, new EntityAIFindEntityNearestPlayer(this));
    }

    @SideOnly(Side.CLIENT)
    public boolean isAttacking()
    {
        return ((Boolean)this.dataManager.get(ATTACKING)).booleanValue();
    }

    public void setAttacking(boolean attacking)
    {
        this.dataManager.set(ATTACKING, Boolean.valueOf(attacking));
    }

    /*public int getFireballStrength()
    {
        return this.explosionStrength;
    }*/

    /**
     * Called to update the entity's position/logic.
     */
    public void onUpdate()
    {
        super.onUpdate();

        if (!this.world.isRemote && this.world.getDifficulty() == EnumDifficulty.PEACEFUL)
        {
            this.setDead();
        }
    }

    protected void entityInit()
    {
        super.entityInit();
        this.dataManager.register(ATTACKING, Boolean.valueOf(false));
    }

    protected void applyEntityAttributes()
    {
        super.applyEntityAttributes();
        this.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(100.0D);
        this.getEntityAttribute(SharedMonsterAttributes.FOLLOW_RANGE).setBaseValue(128.0D);
       	this.getEntityAttribute(SharedMonsterAttributes.ARMOR).setBaseValue(16.0);
       	this.getEntityAttribute(SharedMonsterAttributes.ARMOR_TOUGHNESS).setBaseValue(5);
    }

    public SoundCategory getSoundCategory()
    {
        return SoundCategory.HOSTILE;
    }

    protected SoundEvent getAmbientSound()
    {
        return null; //SoundEvents.ENTITY_GHAST_AMBIENT;
    }

    protected SoundEvent getHurtSound(DamageSource damageSourceIn)
    {
        return TGSounds.HELICOPTER_HIT;
    }

    protected SoundEvent getDeathSound()
    {
        return TGSounds.HELICOPTER_HIT;
    }

    /**
     * Returns the volume for the sounds this mob makes.
     */
    protected float getSoundVolume()
    {
        return 10.0F;
    }

    /**
     * Checks if the entity's current position is a valid location to spawn this entity.
     */
    public boolean getCanSpawnHere()
    {
        return this.rand.nextInt(20) == 0 && super.getCanSpawnHere() && this.world.getDifficulty() != EnumDifficulty.PEACEFUL;
    }

    /**
     * Will return how many at most can spawn in a chunk at once.
     */
    public int getMaxSpawnedInChunk()
    {
        return 1;
    }

    
    
    //TODO ???
    /*public static void registerFixesGhast(DataFixer fixer)
    {
        EntityLiving.registerFixesMob(fixer, EntityGhast.class);
    }*/

    @Override
	public void onEntityUpdate() {
		super.onEntityUpdate();
		
		 if (--ambientSoundTick <= 0 && this.deathTime == 0) {
        	this.playSound(TGSounds.HELICOPTER_ROTOR, 6.0f, 1.0f);
        	ambientSoundTick = 61;
        }
	}

    public float getEyeHeight()
    {
        return 0.5f;
    }
    
    
    
    @Override
	protected void onDeathUpdate() {
    	  if (this.deathTime == 0) {
	        	this.playSound(TGSounds.HELICOPTER_DEATH, 6.0f, 1.0f);
	        }    	
	    	++this.deathTime;
	        
	    	float yoffset = (float) (Math.pow(((float)deathTime/(float)MAX_DEATH_TIME),2)*10.0f);
	    	//this.setPosition(this.posX, this.posY-yoffset, this.posZ);
	    	
	        if (deathTime % 5 == 0) {
	           double d2 = this.rand.nextGaussian() * 0.02D;
	           double d0 = this.rand.nextGaussian() * 0.02D;
	           double d1 = this.rand.nextGaussian() * 0.02D;
	         //  Techguns.proxy.spawnParticle("LargeSmoke", this.world, this.posX + (double)(this.rand.nextFloat() * this.width * 2.0F) - (double)this.width, this.posY + (double)(this.rand.nextFloat() * this.height), this.posZ + (double)(this.rand.nextFloat() * this.width * 2.0F) - (double)this.width, d2, d0, d1);
	        }
	        if (deathTime % 6 == 0) {
	        	float v = 1.0f;
	        	float angle = this.rand.nextFloat()*((float)Math.PI)*2f;
      		float x = (float) Math.cos(angle);
      		float z = (float) Math.sin(angle);
      		float r = 0.5f+(this.rand.nextFloat()*0.5f);
      		/*Techguns.proxy.spawnParticle("Fireblast", this.world, this.posX+x, this.posY-yoffset, this.posZ+z, x*v*r, r, z*v*r);
      		Techguns.proxy.spawnParticle("Fireblast", this.world, this.posX+x, this.posY-yoffset, this.posZ+z, x*v*r*0.75f, r, z*v*r*0.75f);
      		Techguns.proxy.spawnParticle("Fireblast", this.world, this.posX+x, this.posY-yoffset, this.posZ+z, x*v*r*1.25f, r, z*v*r*1.25f);*/
	        }
	        

	        if (this.deathTime == MAX_DEATH_TIME)
	        {
	
	            if (!this.world.isRemote && (this.isPlayer() || this.recentlyHit > 0 && this.canDropLoot() && this.world.getGameRules().getBoolean("doMobLoot")))
	            {
	                int i = this.getExperiencePoints(this.attackingPlayer);
	                i = net.minecraftforge.event.ForgeEventFactory.getExperienceDrop(this, this.attackingPlayer, i);
	                while (i > 0)
	                {
	                    int j = EntityXPOrb.getXPSplit(i);
	                    i -= j;
	                    this.world.spawnEntity(new EntityXPOrb(this.world, this.posX, this.posY, this.posZ, j));
	                }
	            }

	            this.setDead();

	            this.playSound(TGSounds.HELICOPTER_EXPLODE, 6.0f, 1.0f);
	            
	            for (int i = 0; i < 10; ++i)
	            {
	                double d2 = this.rand.nextGaussian() * 0.02D;
	                double d0 = this.rand.nextGaussian() * 0.02D;
	                double d1 = this.rand.nextGaussian() * 0.02D;
	                //Techguns.proxy.spawnParticle("ExplosionHuge", this.world, this.posX + (double)(this.rand.nextFloat() * this.width * 2.0F) - (double)this.width, this.posY + (double)(this.rand.nextFloat() * this.height*2.0f) -10.f, this.posZ + (double)(this.rand.nextFloat() * this.width * 2.0F) - (double)this.width, d2, d0, d1);
	            	//Techguns.proxy.spawnParticle("LargeSmoke", this.world, this.posX + (double)(this.rand.nextFloat() * this.width * 2.0F) - (double)this.width, this.posY + (double)(this.rand.nextFloat() * this.height*2.0f) -10.f, this.posZ + (double)(this.rand.nextFloat() * this.width * 2.0F) - (double)this.width, d2, d0, d1);
	            }
	        }
	}



	static class AIHelicopterAttack extends EntityAIBase
        {
            private final AttackHelicopter parentEntity;
            public int attackTimer;

            public AIHelicopterAttack(AttackHelicopter heli)
            {
                this.parentEntity = heli;
            }

            /**
             * Returns whether the EntityAIBase should begin execution.
             */
            public boolean shouldExecute()
            {
                return this.parentEntity.getAttackTarget() != null;
            }

            /**
             * Execute a one shot task or start executing a continuous task
             */
            public void startExecuting()
            {
                this.attackTimer = 0;
            }

            /**
             * Reset the task's internal state. Called when this task is interrupted by another one
             */
            public void resetTask()
            {
                this.parentEntity.setAttacking(false);
            }

            /**
             * Keep ticking a continuous task that has already been started
             */
            public void updateTask()
            {
                EntityLivingBase entitylivingbase = this.parentEntity.getAttackTarget();
                double d0 = 64.0D;

                if (entitylivingbase.getDistanceSq(this.parentEntity) < 4096.0D && this.parentEntity.canEntityBeSeen(entitylivingbase))
                {
                    World world = this.parentEntity.world;
                    ++this.attackTimer;

                    if (this.attackTimer >= 14 && (this.attackTimer <24) && (this.attackTimer % 2 == 0))
                    {
                    	if(this.attackTimer==14) {
                    		
                			TGPackets.network.sendToAllAround(new PacketPlaySound(TGSounds.HELICOPTER_BURST, this.parentEntity, 8.0f, 1.0f, false, false, TGSoundCategory.GUN_FIRE), TGPackets.targetPointAroundEnt(parentEntity, 100.0f));
                    	}
     
                        GenericProjectile bullet = new GenericProjectile(this.parentEntity.world, this.parentEntity,12.0f, 1.0f, 100, 0.05f, 30, 40, 8.0f, 0.25f,false,EnumBulletFirePos.CENTER);         
                        world.spawnEntity(bullet);

                    } else if (attackTimer == 35) {
                    	

                    	TGPackets.network.sendToAllAround(new PacketPlaySound(TGSounds.ROCKET_FIRE, this.parentEntity, 8.0f, 1.0f, false, false, TGSoundCategory.GUN_FIRE), TGPackets.targetPointAroundEnt(parentEntity, 100.0f));
                	    RocketProjectile rocket = new RocketProjectile(this.parentEntity.world, this.parentEntity,12.0f, 1.0f, 100, 0.05f, 30, 40, 8.0f, 0.25f,false,this.parentEntity.rand.nextBoolean()?EnumBulletFirePos.LEFT:EnumBulletFirePos.RIGHT, 4.0f, 0.0f);         
                        world.spawnEntity(rocket);
                    	
                    	
                    } else if (attackTimer >35) {
                    	this.attackTimer=-30;
                    }
                }
                else if (this.attackTimer > 0)
                {
                    --this.attackTimer;
                }

                this.parentEntity.setAttacking(this.attackTimer > 10);
            }
        }

    static class AILookAround extends EntityAIBase
        {
            private final AttackHelicopter parentEntity;

            public AILookAround(AttackHelicopter heli)
            {
                this.parentEntity = heli;
                this.setMutexBits(2);
            }

            /**
             * Returns whether the EntityAIBase should begin execution.
             */
            public boolean shouldExecute()
            {
                return true;
            }

            /**
             * Keep ticking a continuous task that has already been started
             */
            public void updateTask()
            {
                if (this.parentEntity.getAttackTarget() == null)
                {
                    this.parentEntity.rotationYaw = -((float)MathHelper.atan2(this.parentEntity.motionX, this.parentEntity.motionZ)) * (180F / (float)Math.PI);
                    this.parentEntity.renderYawOffset = this.parentEntity.rotationYaw;
                    this.parentEntity.rotationPitch = 0f;
                }
                else
                {
                    EntityLivingBase target = this.parentEntity.getAttackTarget();
                    double d0 = 96.0D;

                    if (target.getDistanceSq(this.parentEntity) < (96.0*96.0))
                    {
                    	//this.parentEntity.getLookHelper().setLookPositionWithEntity(target, 45.0f, 45.0f);
                        double d1 = target.posX - this.parentEntity.posX;
                        double d2 = target.posZ - this.parentEntity.posZ;
                        this.parentEntity.rotationYaw = -((float)MathHelper.atan2(d1, d2)) * (180F / (float)Math.PI);
                        this.parentEntity.renderYawOffset = this.parentEntity.rotationYaw;
                                                
                        //Calculate the pitch
                        Vec3d pos = new Vec3d(this.parentEntity.posX, this.parentEntity.posY, this.parentEntity.posZ);
                        Vec3d target_pos = new Vec3d(target.posX, target.posY, target.posZ);
                        
                        Vec3d groundPoint = new Vec3d(pos.x, target_pos.y, pos.z);
                        
                        if (pos.y>groundPoint.y) {
	                        
	                        Vec3d toTarget = target_pos.subtract(pos).normalize();
	                        Vec3d toGround = groundPoint.subtract(pos).normalize();
	                        
	                        float acos = (float) (Math.acos(toTarget.dotProduct(toGround))*MathUtil.R2D);
                        	float angle = -MathUtil.clamp(90.0f-acos, 0f, 90f);
                        	this.parentEntity.getLookHelper().setLookPositionWithEntity(target, 45.0f, angle);
                        } 
                    }
                }
            }
        }

    static class AIRandomFly extends EntityAIBase
        {
            private final AttackHelicopter parentEntity;

            public AIRandomFly(AttackHelicopter heli)
            {
                this.parentEntity = heli;
                this.setMutexBits(1);
            }

            /**
             * Returns whether the EntityAIBase should begin execution.
             */
            public boolean shouldExecute()
            {
                EntityMoveHelper entitymovehelper = this.parentEntity.getMoveHelper();

                if (!entitymovehelper.isUpdating())
                {
                    return true;
                }
                else
                {
                    double d0 = entitymovehelper.getX() - this.parentEntity.posX;
                    double d1 = entitymovehelper.getY() - this.parentEntity.posY;
                    double d2 = entitymovehelper.getZ() - this.parentEntity.posZ;
                    double d3 = d0 * d0 + d1 * d1 + d2 * d2;
                    return d3 < 1.0D || d3 > 3600.0D;
                }
            }

            /**
             * Returns whether an in-progress EntityAIBase should continue executing
             */
            public boolean shouldContinueExecuting()
            {
                return false;
            }

            /**
             * Execute a one shot task or start executing a continuous task
             */
            public void startExecuting()
            {
                Random random = this.parentEntity.getRNG();
                double d0 = this.parentEntity.posX + (double)((random.nextFloat() * 2.0F - 1.0F) * 16.0F);
                //double d1 = this.parentEntity.posY + (double)((random.nextFloat() * 2.0F - 1.0F) * 16.0F);
                double d2 = this.parentEntity.posZ + (double)((random.nextFloat() * 2.0F - 1.0F) * 16.0F);
                this.parentEntity.getMoveHelper().setMoveTo(d0, getGroundLevel(this.parentEntity)+TARGET_FLY_HEIGHT, d2, 1.0D);
            }
            
    		private int getGroundLevel(AttackHelicopter entity) {	
    			return entity.world.getHeight((int)entity.posX, (int)entity.posZ);
    		}

        }

    static class AttackHelicopterMoveHelper extends EntityMoveHelper
        {
            private final AttackHelicopter parentEntity;
            private int courseChangeCooldown;

            public AttackHelicopterMoveHelper(AttackHelicopter heli)
            {
                super(heli);
                this.parentEntity = heli;
            }

            public void onUpdateMoveHelper()
            {
                if (this.action == EntityMoveHelper.Action.MOVE_TO)
                {
                    double d0 = this.posX - this.parentEntity.posX;
                    double d1 = this.posY - this.parentEntity.posY;
                    double d2 = this.posZ - this.parentEntity.posZ;
                    double d3 = d0 * d0 + d1 * d1 + d2 * d2;

                    if (this.courseChangeCooldown-- <= 0)
                    {
                        this.courseChangeCooldown += this.parentEntity.getRNG().nextInt(5) + 2;
                        d3 = (double)MathHelper.sqrt(d3);

                        if (this.isNotColliding(this.posX, this.posY, this.posZ, d3))
                        {
                            this.parentEntity.motionX += d0 / d3 * 0.1D;
                            this.parentEntity.motionY += d1 / d3 * 0.1D;
                            this.parentEntity.motionZ += d2 / d3 * 0.1D;
                        }
                        else
                        {
                            this.action = EntityMoveHelper.Action.WAIT;
                        }
                    }
                }
            }
            
            /**
             * Checks if entity bounding box is not colliding with terrain
             */
            private boolean isNotColliding(double x, double y, double z, double p_179926_7_)
            {
                double d0 = (x - this.parentEntity.posX) / p_179926_7_;
                double d1 = (y - this.parentEntity.posY) / p_179926_7_;
                double d2 = (z - this.parentEntity.posZ) / p_179926_7_;
                AxisAlignedBB axisalignedbb = this.parentEntity.getEntityBoundingBox();

                for (int i = 1; (double)i < p_179926_7_; ++i)
                {
                    axisalignedbb = axisalignedbb.offset(d0, d1, d2);

                    if (!this.parentEntity.world.getCollisionBoxes(this.parentEntity, axisalignedbb).isEmpty())
                    {
                        return false;
                    }
                }

                return true;
            }
        }

	@Override
	public float getTotalArmorAgainstType(TGDamageSource dmgsrc) {
		switch(dmgsrc.damageType){
			case EXPLOSION:
				return 0.0f;
			case LIGHTNING:
			case ENERGY:
				return 10.0f;
			case FIRE:
			case ICE:
			case PHYSICAL:
			case PROJECTILE:
			case POISON:
			case RADIATION:
				return 20.0f;
			case UNRESISTABLE:
		default:
			return 0.0f;
		}
	}

	@Override
	public float getPenetrationResistance(TGDamageSource dmgsrc) {
		switch(dmgsrc.damageType){
		case PROJECTILE:
			return 0.4f;
		case EXPLOSION:
		case LIGHTNING:
		case ENERGY:
		case FIRE:
		case ICE:
		case PHYSICAL:
		case POISON:
		case RADIATION:
		case UNRESISTABLE:
			return 0.0f;
	default:
		return 0.0f;
	}
	}

	@Override
	public float getWeaponPosX() {
		return 0;
	}

	@Override
	public float getWeaponPosY() {
		return 0;
	}

	@Override
	public float getWeaponPosZ() {
		return 0;
	}

	@Override
	public float getBulletOffsetSide() {
		return 2.0f;
	}
    
	
}
