package techguns.entities.ai;

import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.IRangedAttackMob;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.util.math.MathHelper;

public class EntityAIRangedAttack extends EntityAIBase
{
    /** The entity the AI instance has been applied to */
    private final EntityLiving entityHost;
    /** The entity (as a RangedAttackMob) the AI instance has been applied to. */
    private final IRangedAttackMob rangedAttackEntityHost;
    private EntityLivingBase attackTarget;
    /**
     * A decrementing tick that spawns a ranged attack once this value reaches 0. It is then set back to the
     * maxRangedAttackTime.
     */
    private int rangedAttackTime;
    private double entityMoveSpeed;
    private int ticksTargetSeen;
    private int attackTimeVariance;
    /** The maximum time the AI has to wait before peforming another ranged attack. */
    private int maxRangedAttackTime;
    private float attackRange;
    private float attackRange_2;
    
    //GUN HANDLING:
    private int maxBurstCount; //Total number of shots in burst.
    private int burstCount; //shots left in current burst.
    private int shotDelay; //delay between shots in burst.
    

//    public EntityAIRangedAttack(IRangedAttackMob p_i1649_1_, double p_i1649_2_, int p_i1649_4_, float p_i1649_5_)
//    {
//        this(p_i1649_1_, p_i1649_2_, p_i1649_4_, p_i1649_4_, p_i1649_5_);
//    }

    public EntityAIRangedAttack(IRangedAttackMob shooter, double moveSpeed, int attackTimeVariance, int attackTime, float attackRange, int maxBurstCount, int shotDelay)
    {
        this.rangedAttackTime = -1;

        if (!(shooter instanceof EntityLivingBase))
        {
            throw new IllegalArgumentException("ArrowAttackGoal requires Mob implements RangedAttackMob");
        }
        else
        {
            this.rangedAttackEntityHost = shooter;
            this.entityHost = (EntityLiving)shooter;
            this.entityMoveSpeed = moveSpeed;
            this.attackTimeVariance = attackTimeVariance;
            this.maxRangedAttackTime = attackTime;
            this.attackRange = attackRange;
            this.attackRange_2 = attackRange * attackRange;
            this.setMutexBits(3);
            
            this.maxBurstCount = maxBurstCount;
            this.burstCount = maxBurstCount;
            this.shotDelay = shotDelay;
        }
    }

    /**
     * Returns whether the EntityAIBase should begin execution.
     */
    public boolean shouldExecute()
    {
        EntityLivingBase entitylivingbase = this.entityHost.getAttackTarget();

        if (entitylivingbase == null)
        {
            return false;
        }
        else
        {
            this.attackTarget = entitylivingbase;
            return true;
        }
    }

    /**
     * Returns whether an in-progress EntityAIBase should continue executing
     */
    public boolean continueExecuting()
    {
        return this.shouldExecute() || !this.entityHost.getNavigator().noPath();
    }

    /**
     * Resets the task
     */
    public void resetTask()
    {
        this.attackTarget = null;
        this.ticksTargetSeen = 0;
        this.rangedAttackTime = -1;
    }

    /**
     * Updates the task
     */
    public void updateTask()
    {
        double d0 = this.entityHost.getDistanceSq(this.attackTarget.posX, this.attackTarget.posY/*this.attackTarget.boundingBox.minY TODO??*/, this.attackTarget.posZ);
        boolean targetInSight = this.entityHost.getEntitySenses().canSee(this.attackTarget);

        if (targetInSight)
        {
            ++this.ticksTargetSeen;
        }
        else
        {
            this.ticksTargetSeen = 0;
        }

        if (d0 <= (double)this.attackRange_2 && this.ticksTargetSeen >= 20)
        {
            this.entityHost.getNavigator().clearPathEntity();
        }
        else
        {
            this.entityHost.getNavigator().tryMoveToEntityLiving(this.attackTarget, this.entityMoveSpeed);
        }

        this.entityHost.getLookHelper().setLookPositionWithEntity(this.attackTarget, 30.0F, 30.0F);
        float f;

        if (--this.rangedAttackTime == 0)
        {
            if (d0 > (double)this.attackRange_2 || !targetInSight)
            {
                return;
            }

            f = MathHelper.sqrt(d0) / this.attackRange;
            
            float f1 = f;

            if (f < 0.1F)
            {
                f1 = 0.1F;
            }

            if (f1 > 1.0F)
            {
                f1 = 1.0F;
            }

            this.rangedAttackEntityHost.attackEntityWithRangedAttack(this.attackTarget, f1);
            
            if (maxBurstCount > 0) burstCount--;
            if (burstCount > 0) {
            	this.rangedAttackTime = shotDelay;
            }else {
            	burstCount = maxBurstCount;
            	this.rangedAttackTime = MathHelper.floor(f * (float)(this.maxRangedAttackTime - this.attackTimeVariance) + (float)this.attackTimeVariance);
            }
        }
        else if (this.rangedAttackTime < 0)
        {
            f = MathHelper.sqrt(d0) / this.attackRange;
            this.rangedAttackTime = MathHelper.floor(f * (float)(this.maxRangedAttackTime - this.attackTimeVariance) + (float)this.attackTimeVariance);
        }
    }
}