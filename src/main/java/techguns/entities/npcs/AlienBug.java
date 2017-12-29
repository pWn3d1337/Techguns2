package techguns.entities.npcs;

import javax.annotation.Nullable;

import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAIAttackMelee;
import net.minecraft.entity.ai.EntityAIHurtByTarget;
import net.minecraft.entity.ai.EntityAILeapAtTarget;
import net.minecraft.entity.ai.EntityAILookIdle;
import net.minecraft.entity.ai.EntityAINearestAttackableTarget;
import net.minecraft.entity.ai.EntityAISwimming;
import net.minecraft.entity.ai.EntityAIWanderAvoidWater;
import net.minecraft.entity.ai.EntityAIWatchClosest;
import net.minecraft.entity.monster.EntityIronGolem;
import net.minecraft.entity.monster.EntitySpider;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.DamageSource;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import techguns.TGPackets;
import techguns.TGSounds;
import techguns.api.npc.INpcTGDamageSystem;
import techguns.api.npc.factions.ITGNpcTeam;
import techguns.api.npc.factions.TGNpcFaction;
import techguns.capabilities.TGSpawnerNPCData;
import techguns.client.audio.TGSoundCategory;
import techguns.damagesystem.TGDamageSource;
import techguns.packets.PacketPlaySound;

public class AlienBug extends EntitySpider implements ITGNpcTeam, INpcTGDamageSystem, ITGSpawnerNPC {

	 protected int attackTimer;
	
	 protected static final ResourceLocation LOOT = null; //TODO //new ResourceLocation(Techguns.MODID, "entities/alienbug");
	 
	 protected boolean tryLink=true;
	
	@Override
    @Nullable
    protected ResourceLocation getLootTable()
    {
        return LOOT;
    }
	
	@Override
   protected void applyEntityAttributes()
   {
       super.applyEntityAttributes();
       this.getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).setBaseValue(4.0D);
       this.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(20.0D);
       this.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(1.0D);
   }
	
	public AlienBug(World w) {
		super(w);
		this.setSize(1.1F, 1.2F);
	}
	
	
	 @Override
	protected SoundEvent getAmbientSound() {
		return TGSounds.ALIENBUG_IDLE;
	}


	@Override
	protected SoundEvent getHurtSound(DamageSource damageSourceIn) {
		return TGSounds.ALIENBUG_HURT;
	}


	@Override
	protected void playStepSound(BlockPos pos, Block blockIn) {
		this.playSound(TGSounds.ALIENBUG_STEP, 0.15F, 1.0F);
	}

	@Override
	protected SoundEvent getDeathSound() {
		return TGSounds.ALIENBUG_DEATH;
	}


	@Override
	protected boolean isValidLightLevel() {
		return true;
	}


	@SideOnly(Side.CLIENT)
   public int getAttackTimer()
   {
       return this.attackTimer;
   }
	
	@Override
	public void onLivingUpdate() {
		super.onLivingUpdate();
		if (this.attackTimer > 0)
       {
           --this.attackTimer;
       }
	}

	@Override
	protected void initEntityAI() {
		this.tasks.addTask(1, new EntityAISwimming(this));
		this.tasks.addTask(3, new EntityAILeapAtTarget(this, 0.4F));
		this.tasks.addTask(4, new AlienBug.AISpiderAttack(this));
		this.tasks.addTask(5, new EntityAIWanderAvoidWater(this, 0.8D));
		this.tasks.addTask(6, new EntityAIWatchClosest(this, EntityPlayer.class, 8.0F));
		this.tasks.addTask(6, new EntityAILookIdle(this));
		this.targetTasks.addTask(1, new EntityAIHurtByTarget(this, false, new Class[0]));
		this.targetTasks.addTask(2, new AlienBug.AIAlienBugTarget(this, EntityPlayer.class));
		this.targetTasks.addTask(3, new AlienBug.AIAlienBugTarget(this, EntityIronGolem.class));
	}
	
	static class AIAlienBugTarget<T extends EntityLivingBase> extends EntityAINearestAttackableTarget<T> {
		public AIAlienBugTarget(AlienBug bug, Class<T> classTarget) {
			super(bug, classTarget, true);
		}

		@Override
		public void startExecuting() {
			super.startExecuting();
			if(!this.taskOwner.world.isRemote) {
				TGPackets.network.sendToAllAround(new PacketPlaySound(TGSounds.ALIENBUG_AGGRO, this.taskOwner, 1.0f, 1.0f,
						false, false, TGSoundCategory.HOSTILE), TGPackets.targetPointAroundEnt(taskOwner, 24.0f));
			}
		}

	}

	static class AISpiderAttack extends EntityAIAttackMelee {
		public AISpiderAttack(EntitySpider spider) {
			super(spider, 1.0D, true);
		}

		/**
		 * Returns whether an in-progress EntityAIBase should continue executing
		 */
		public boolean shouldContinueExecuting() {
			float f = this.attacker.getBrightness();

			if (f >= 0.5F && this.attacker.getRNG().nextInt(100) == 0) {
				this.attacker.setAttackTarget((EntityLivingBase) null);
				return false;
			} else {
				return super.shouldContinueExecuting();
			}
		}

		protected double getAttackReachSqr(EntityLivingBase attackTarget) {
			return (double) (4.0F + attackTarget.width);
		}
	}
	 
	@Override
	public boolean attackEntityAsMob(Entity ent) {
	   boolean b =super.attackEntityAsMob(ent);
       this.attackTimer = 10;

       if(!this.world.isRemote) {
    	   TGPackets.network.sendToAllAround(new PacketPlaySound(TGSounds.ALIENBUG_BITE, this, 2.0f, 1.0f,false,false, TGSoundCategory.HOSTILE), TGPackets.targetPointAroundEnt(this, 24.0f));
       }

       this.world.setEntityState(this, (byte)4);
          
       return b;
	}

	@Override
	public TGNpcFaction getTGFaction() {
		return TGNpcFaction.HOSTILE;
	}

	@Override
	public void handleStatusUpdate(byte id) {
		if (id == 4) {
			this.attackTimer = 10;
		} else {
			super.handleStatusUpdate(id);
		}
	}

	@Override
	public float getTotalArmorAgainstType(TGDamageSource dmgsrc) {
		switch( dmgsrc.damageType){
			case ENERGY:
			case EXPLOSION:
			case ICE:
			case LIGHTNING:
				return 5.0f;
			case PHYSICAL:
				return 10.0f;
			case POISON:
				return 20.0f;
			case PROJECTILE:
				return 10.0f;
			case RADIATION:
				return 20.0f;
			case FIRE:
			case UNRESISTABLE:
			default:
				return 0;
		}
	}

	@Override
	public float getPenetrationResistance(TGDamageSource dmgsrc) {
		return 0.0f;
	}

	@Override
	public boolean getTryLink() {
		return this.tryLink;
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
}