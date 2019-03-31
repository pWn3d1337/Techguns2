package techguns.entities.npcs;

import java.util.Random;

import net.minecraft.entity.EntityFlying;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.entity.ai.EntityMoveHelper;
import net.minecraft.entity.monster.IMob;
import net.minecraft.util.DamageSource;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.EnumDifficulty;
import net.minecraft.world.World;
import net.minecraftforge.common.capabilities.Capability;
import techguns.api.npc.INPCTechgunsShooter;
import techguns.api.npc.INpcTGDamageSystem;
import techguns.capabilities.TGSpawnerNPCData;
import techguns.util.MathUtil;

public abstract class GenericFlyingMob extends EntityFlying implements IMob, INpcTGDamageSystem, INPCTechgunsShooter, ITGSpawnerNPC {

	protected boolean tryLink=true;
	
	public GenericFlyingMob(World worldIn) {
		super(worldIn);
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
	
	protected abstract int getTargetFlyHeight();
	
	protected static class AILookAround extends EntityAIBase
    {
        private final GenericFlyingMob parentEntity;

        public AILookAround(GenericFlyingMob mob)
        {
            this.parentEntity = mob;
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

	protected static class AIRandomFly extends EntityAIBase
    {
        private final GenericFlyingMob parentEntity;

        public AIRandomFly(GenericFlyingMob mob)
        {
            this.parentEntity = mob;
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
            this.parentEntity.getMoveHelper().setMoveTo(d0, getGroundLevel(this.parentEntity)+this.parentEntity.getTargetFlyHeight(), d2, 1.0D);
        }
        
		private int getGroundLevel(GenericFlyingMob entity) {	
			return entity.world.getHeight((int)entity.posX, (int)entity.posZ);
		}

    }

	protected static class FlyingMobMoveHelper extends EntityMoveHelper
    {
        private final GenericFlyingMob parentEntity;
        private int courseChangeCooldown=0;

        public FlyingMobMoveHelper(GenericFlyingMob mob)
        {
            super(mob);
            this.parentEntity = mob;
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

}
