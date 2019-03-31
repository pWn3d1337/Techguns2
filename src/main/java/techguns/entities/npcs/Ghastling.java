package techguns.entities.npcs;

import javax.annotation.Nullable;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityFlying;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.entity.ai.EntityAIFindEntityNearestPlayer;
import net.minecraft.entity.ai.EntityAIHurtByTarget;
import net.minecraft.entity.ai.EntityAILookIdle;
import net.minecraft.entity.ai.EntityAIMoveTowardsRestriction;
import net.minecraft.entity.ai.EntityAINearestAttackableTarget;
import net.minecraft.entity.ai.EntityAIWanderAvoidWater;
import net.minecraft.entity.ai.EntityAIWatchClosest;
import net.minecraft.entity.ai.attributes.IAttributeInstance;
import net.minecraft.entity.monster.EntityBlaze;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.monster.IMob;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.projectile.EntitySmallFireball;
import net.minecraft.init.SoundEvents;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.pathfinding.PathNodeType;
import net.minecraft.util.DamageSource;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.EnumDifficulty;
import net.minecraft.world.World;
import net.minecraft.world.storage.loot.LootTableList;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import techguns.TGPackets;
import techguns.TGSounds;
import techguns.Techguns;
import techguns.api.npc.INPCTechgunsShooter;
import techguns.api.npc.INpcTGDamageSystem;
import techguns.capabilities.TGSpawnerNPCData;
import techguns.client.audio.TGSoundCategory;
import techguns.damagesystem.TGDamageSource;
import techguns.entities.projectiles.AlienBlasterProjectile;
import techguns.entities.projectiles.EnumBulletFirePos;
import techguns.entities.projectiles.GenericProjectile;
import techguns.entities.projectiles.RocketProjectile;
import techguns.packets.PacketPlaySound;

public class Ghastling extends EntityMob implements IMob, INpcTGDamageSystem, INPCTechgunsShooter, ITGSpawnerNPC {

    private static final DataParameter<Boolean> ATTACKING = EntityDataManager.<Boolean>createKey(Ghastling.class, DataSerializers.BOOLEAN);

    protected static final ResourceLocation LOOT = new ResourceLocation(Techguns.MODID, "entities/ghastling");
    
    protected boolean tryLink=true;
    
	public Ghastling(World worldIn) {
		super(worldIn);
		this.setSize(1.0F, 2.1F);
		this.setPathPriority(PathNodeType.WATER, -1.0F);
        this.setPathPriority(PathNodeType.LAVA, 8.0F);
        this.setPathPriority(PathNodeType.DANGER_FIRE, 0.0F);
        this.setPathPriority(PathNodeType.DAMAGE_FIRE, 0.0F);
        this.isImmuneToFire = true;
        this.experienceValue = 10;
	}
	
	@Override
    @Nullable
    protected ResourceLocation getLootTable()
    { 
        return LOOT;
        //return LootTableList.ENTITIES_GHAST;
    }
    

    /**
     * Checks to make sure the light is not too bright where the mob is spawning
     */
    protected boolean isValidLightLevel()
    {
        return true;
    }
	
    protected void initEntityAI()
    {
    	 this.tasks.addTask(4, new AIFireballAttack(this));
         this.tasks.addTask(5, new EntityAIMoveTowardsRestriction(this, 1.0D));
         this.tasks.addTask(7, new EntityAIWanderAvoidWater(this, 1.0D, 0.0F));
         this.tasks.addTask(8, new EntityAIWatchClosest(this, EntityPlayer.class, 8.0F));
         this.tasks.addTask(8, new EntityAILookIdle(this));
         this.targetTasks.addTask(1, new EntityAIHurtByTarget(this, true, new Class[0]));
         this.targetTasks.addTask(2, new EntityAINearestAttackableTarget(this, EntityPlayer.class, true));
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

    @Override
    protected void entityInit()
    {
        super.entityInit();
        this.dataManager.register(ATTACKING, Boolean.valueOf(false));
    }

    protected void applyEntityAttributes()
    {
        super.applyEntityAttributes();
        this.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(20.0D);
        this.getEntityAttribute(SharedMonsterAttributes.FOLLOW_RANGE).setBaseValue(64.0D);
       	this.getEntityAttribute(SharedMonsterAttributes.ARMOR).setBaseValue(10.0);
       	this.getEntityAttribute(SharedMonsterAttributes.ARMOR_TOUGHNESS).setBaseValue(0);
    }
	
	@Override
	public SoundCategory getSoundCategory()
    {
        return SoundCategory.HOSTILE;
    }
	
	 protected SoundEvent getAmbientSound()
    {
        return SoundEvents.ENTITY_GHAST_AMBIENT;
    }

    protected SoundEvent getHurtSound(DamageSource damageSourceIn)
    {
        return SoundEvents.ENTITY_GHAST_HURT;
    }

    protected SoundEvent getDeathSound()
    {
        return SoundEvents.ENTITY_GHAST_DEATH;
    }
	
    /**
     * Returns the volume for the sounds this mob makes.
     */
    protected float getSoundVolume()
    {
        return 10.0F;
    }
    
    public float getEyeHeight()
    {
        return 1.5f;
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
	public float getTotalArmorAgainstType(TGDamageSource dmgsrc) {
		return 0;
	}

	@Override
	public float getPenetrationResistance(TGDamageSource dmgsrc) {
		return 0;
	}

	protected static class AIGhastlingAttack extends EntityAIBase
    {
        private final Ghastling parentEntity;
        public int attackTimer;

        public AIGhastlingAttack(Ghastling mob)
        {
            this.parentEntity = mob;
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
            //double d0 = 64.0D;

            if (entitylivingbase.getDistanceSq(this.parentEntity) < 4096.0D && this.parentEntity.canEntityBeSeen(entitylivingbase))
            {
                World world = this.parentEntity.world;
                ++this.attackTimer;

                /*if (this.attackTimer >= 14 && (this.attackTimer <24) && (this.attackTimer % 2 == 0))
                {
                	if(this.attackTimer==14) {
                		
            			TGPackets.network.sendToAllAround(new PacketPlaySound(TGSounds.HELICOPTER_BURST, this.parentEntity, 8.0f, 1.0f, false, false, TGSoundCategory.GUN_FIRE), TGPackets.targetPointAroundEnt(parentEntity, 100.0f));
                	}
 
                    GenericProjectile bullet = new GenericProjectile(this.parentEntity.world, this.parentEntity,12.0f, 1.0f, 100, 0.05f, 30, 40, 8.0f, 0.25f,false,EnumBulletFirePos.CENTER);         
                    world.spawnEntity(bullet);

                } else*/
                if (attackTimer == 15) {
                	

                   TGPackets.network.sendToAllAround(new PacketPlaySound(TGSounds.NETHERBLASTER_FIRE, this.parentEntity, 1.0f, 1.0f, false, false, TGSoundCategory.GUN_FIRE), TGPackets.targetPointAroundEnt(parentEntity, 100.0f));
            	    //RocketProjectile rocket = new RocketProjectile(this.parentEntity.world, this.parentEntity,12.0f, 1.0f, 100, 0.05f, 30, 40, 8.0f, 0.25f,false,this.parentEntity.rand.nextBoolean()?EnumBulletFirePos.LEFT:EnumBulletFirePos.RIGHT, 4.0f, 0.0f);         
                   AlienBlasterProjectile rocket = new AlienBlasterProjectile(this.parentEntity.world, parentEntity, 10, 1.5f, 200, 0.05f, 200, 200, 10, 0, false, EnumBulletFirePos.CENTER);
            	    
            	    world.spawnEntity(rocket);
                	
                	
                } else if (attackTimer >30) {
                	this.attackTimer=0;
                }
            }
            else if (this.attackTimer > 0)
            {
                --this.attackTimer;
            }

            this.parentEntity.setAttacking(this.attackTimer > 10);
        }
    }

	protected static class AIFireballAttack extends EntityAIBase
     {
         private final Ghastling parentEntity;
         private int attackStep;
         private int attackTime;

         public AIFireballAttack(Ghastling mob)
         {
             this.parentEntity = mob;
             this.setMutexBits(3);
         }

         /**
          * Returns whether the EntityAIBase should begin execution.
          */
         public boolean shouldExecute()
         {
             EntityLivingBase entitylivingbase = this.parentEntity.getAttackTarget();
             return entitylivingbase != null && entitylivingbase.isEntityAlive();
         }

         /**
          * Execute a one shot task or start executing a continuous task
          */
         public void startExecuting()
         {
             this.attackStep = 0;
         }

         /**
          * Reset the task's internal state. Called when this task is interrupted by another one
          */
         public void resetTask()
         {
             //this.parentEntity.setOnFire(false);
        	 this.parentEntity.setAttacking(false);
         }

         /**
          * Keep ticking a continuous task that has already been started
          */
         public void updateTask()
         {
             --this.attackTime;
             EntityLivingBase entitylivingbase = this.parentEntity.getAttackTarget();
             double d0 = this.parentEntity.getDistanceSq(entitylivingbase);

             if (d0 < 4.0D)
             {
                 if (this.attackTime <= 0)
                 {
                     this.attackTime = 20;
                     this.parentEntity.attackEntityAsMob(entitylivingbase);
                 }

                 this.parentEntity.getMoveHelper().setMoveTo(entitylivingbase.posX, entitylivingbase.posY, entitylivingbase.posZ, 1.0D);
             }
             else if (d0 < this.getFollowDistance() * this.getFollowDistance())
             {
                 double d1 = entitylivingbase.posX - this.parentEntity.posX;
                 double d2 = entitylivingbase.getEntityBoundingBox().minY + (double)(entitylivingbase.height / 2.0F) - (this.parentEntity.posY + (double)(this.parentEntity.height / 2.0F));
                 double d3 = entitylivingbase.posZ - this.parentEntity.posZ;

                 if (this.attackTime <= 0)
                 {
                     ++this.attackStep;

                     if (this.attackStep == 1)
                     {
                         this.attackTime = 30;
                         this.parentEntity.setAttacking(true);
                     }
                     else if (this.attackStep <= 4)
                     {
                         this.attackTime = 6;
                     }
                     else
                     {
                         this.attackTime = 50;
                         this.attackStep = 0;
                         //this.parentEntity.setOnFire(false);
                         this.parentEntity.setAttacking(false);
                     }

                     if (this.attackStep > 1)
                     {
                         float f = MathHelper.sqrt(MathHelper.sqrt(d0)) * 0.5F;
                         this.parentEntity.world.playEvent((EntityPlayer)null, 1018, new BlockPos((int)this.parentEntity.posX, (int)this.parentEntity.posY, (int)this.parentEntity.posZ), 0);

                         /*for (int i = 0; i < 1; ++i)
                         {
                             EntitySmallFireball entitysmallfireball = new EntitySmallFireball(this.parentEntity.world, this.parentEntity, d1 + this.parentEntity.getRNG().nextGaussian() * (double)f, d2, d3 + this.parentEntity.getRNG().nextGaussian() * (double)f);
                             entitysmallfireball.posY = this.parentEntity.posY + (double)(this.parentEntity.height / 2.0F) + 0.5D;
                             this.parentEntity.world.spawnEntity(entitysmallfireball);
                         }*/
                        // TGPackets.network.sendToAllAround(new PacketPlaySound(TGSounds.NETHERBLASTER_FIRE, this.parentEntity, 1.0f, 1.0f, false, false, TGSoundCategory.GUN_FIRE), TGPackets.targetPointAroundEnt(parentEntity, 100.0f));
                 	    //RocketProjectile rocket = new RocketProjectile(this.parentEntity.world, this.parentEntity,12.0f, 1.0f, 100, 0.05f, 30, 40, 8.0f, 0.25f,false,this.parentEntity.rand.nextBoolean()?EnumBulletFirePos.LEFT:EnumBulletFirePos.RIGHT, 4.0f, 0.0f);         
                        AlienBlasterProjectile rocket = new AlienBlasterProjectile(this.parentEntity.world, parentEntity, 6, 1.5f, 200, 0.05f, 200, 200, 6, 0, false, EnumBulletFirePos.CENTER);
                 	    
                 	    this.parentEntity.world.spawnEntity(rocket);
                         
                     }
                 }

                 this.parentEntity.getLookHelper().setLookPositionWithEntity(entitylivingbase, 10.0F, 10.0F);
             }
             else
             {
                 this.parentEntity.getNavigator().clearPath();
                 this.parentEntity.getMoveHelper().setMoveTo(entitylivingbase.posX, entitylivingbase.posY, entitylivingbase.posZ, 1.0D);
             }

             super.updateTask();
         }

         private double getFollowDistance()
         {
             IAttributeInstance iattributeinstance = this.parentEntity.getEntityAttribute(SharedMonsterAttributes.FOLLOW_RANGE);
             return iattributeinstance == null ? 16.0D : iattributeinstance.getAttributeValue();
         }
     }
	
	/***
	 * Spawner linking behavior
	 */
	@Override
	public boolean getTryLink() {
		return tryLink;
	}

	@Override
	public void setTryLink(boolean value) {
		this.tryLink=value;
	}

	@Override
	public TGSpawnerNPCData getCapability(Capability<TGSpawnerNPCData> tgGenericnpcData) {
		return this.getCapability(tgGenericnpcData, null);
	}
	
	@Override
	protected void despawnEntity() {
		super.despawnEntity();
		this.despawnEntitySpawner(world, dead);
	}

	@Override
	public void onDeath(DamageSource cause) {
		super.onDeath(cause);
		this.onDeathSpawner(world, dead);
	}
	
    /**
     * Called to update the entity's position/logic.
     */
	@Override
    public void onUpdate()
    {
        super.onUpdate();

        if (!this.world.isRemote && this.world.getDifficulty() == EnumDifficulty.PEACEFUL)
        {
            this.setDead();
        }
        
        this.onUpdateSpawner(world);
    }
}
