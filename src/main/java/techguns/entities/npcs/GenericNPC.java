package techguns.entities.npcs;

import java.util.Random;

import javax.annotation.Nullable;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.IEntityLivingData;
import net.minecraft.entity.IRangedAttackMob;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAIAttackMelee;
import net.minecraft.entity.ai.EntityAILookIdle;
import net.minecraft.entity.ai.EntityAINearestAttackableTarget;
import net.minecraft.entity.ai.EntityAISwimming;
import net.minecraft.entity.ai.EntityAIWander;
import net.minecraft.entity.ai.EntityAIWatchClosest;
import net.minecraft.entity.monster.AbstractSkeleton;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.SoundEvents;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.Item;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.util.DamageSource;
import net.minecraft.util.SoundEvent;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.EnumDifficulty;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import techguns.TGArmors;
import techguns.TGItems;
import techguns.TGuns;
import techguns.api.npc.INPCTechgunsShooter;
import techguns.api.npc.INpcTGDamageSystem;
import techguns.api.npc.factions.ITGNpcTeam;
import techguns.api.npc.factions.TGNpcFaction;
import techguns.damagesystem.DamageSystem;
import techguns.damagesystem.TGDamageSource;
import techguns.entities.ai.EntityAIHurtByTargetTGFactions;
import techguns.entities.ai.EntityAIRangedAttack;
import techguns.items.armors.GenericArmor;
import techguns.items.guns.GenericGun;

public class GenericNPC extends EntityMob implements IRangedAttackMob, INPCTechgunsShooter, INpcTGDamageSystem, ITGNpcTeam{
	 
		//private GenericGun gun = this.pickRandomGun();
		
	protected boolean hasAimedBowAnim;
	    
	    private static final DataParameter<Boolean> SWINGING_ARMS = EntityDataManager.<Boolean>createKey(AbstractSkeleton.class, DataSerializers.BOOLEAN);
	    private EntityAIRangedAttack aiRangedAttack =  null;
	    private final EntityAIAttackMelee aiAttackOnCollide = new EntityAIAttackMelee(this, 1.2D, false);
	  
	    
	    public GenericNPC(World world)
	    {
	        super(world);
	        this.tasks.addTask(1, new EntityAISwimming(this));
	        this.tasks.addTask(5, new EntityAIWander(this, 1.0D));
	        this.tasks.addTask(6, new EntityAIWatchClosest(this, EntityPlayer.class, 8.0F));
	        this.tasks.addTask(6, new EntityAILookIdle(this));
	        this.targetTasks.addTask(1, new EntityAIHurtByTargetTGFactions(this, false));
	        this.targetTasks.addTask(2, new EntityAINearestAttackableTarget(this, EntityPlayer.class, true));

	        if (world != null && !world.isRemote)
	        {
	            this.setCombatTask();
	        }
	        hasAimedBowAnim=true;
	    }

	    public boolean getHasAimedBowAnim() {
			return hasAimedBowAnim;
		}

		protected GenericGun pickRandomGun(int difficulty) {
			Random r = new Random();
			GenericGun gun;
			switch (r.nextInt(4)) {
				case 0:
					gun = (GenericGun) TGuns.revolver;
					break;
				case 1:
					gun = (GenericGun) TGuns.thompson;
					break;
				case 2:
					gun = (GenericGun) TGuns.as50;
					break;
				case 3:
					gun = (GenericGun) TGuns.grimreaper;
					break;
				case 4:
				default:
					gun = (GenericGun) TGuns.handcannon;
			}
			return gun;
		}

		@Override
	    protected void applyEntityAttributes()
	    {
	        super.applyEntityAttributes();
	        this.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(0.25D);
	    }


		@Override
	    protected SoundEvent getHurtSound(DamageSource damageSourceIn)
	    {
	        return SoundEvents.ENTITY_VILLAGER_HURT;
	    }

		@Override
	    protected SoundEvent getDeathSound()
	    {
	        return SoundEvents.ENTITY_VILLAGER_DEATH;
	    }

		@Override
		protected SoundEvent getAmbientSound() {
			return SoundEvents.ENTITY_VILLAGER_AMBIENT;
		}

	    /**
	     * Drop 0-2 items of this living's type. @param par1 - Whether this entity has recently been hit by a player. @param
	     * par2 - Level of Looting used to kill this mob.
	     */
	    @Override
	    protected void dropFewItems(boolean hitByPlayer, int level)
	    {
	        int j;
	        int k;

	        j = this.rand.nextInt(3 + level);

	        for (k = 0; k < j; ++k)
	        {
	        	ItemStack it = this.getRandomItemFromLoottable();
	        	if (it !=null){
	        		//this.dropItem(it.getItem(),it.stackSize,it.getItemDamage());
	        		this.entityDropItem(TGItems.newStack(it, it.getCount()), 0.0f);
	        	}
	        }
	    }

	    protected ItemStack getRandomItemFromLoottable(){
	    	return null;
	    }
	        
	    
	    /**
	     * Called only once on an entity when first time spawned, via egg, mob spawner, natural spawning etc, but not called
	     * when entity is reloaded from nbt. Mainly used for initializing attributes and inventory
	     */
	    @Nullable
	    public IEntityLivingData onInitialSpawn(DifficultyInstance difficulty, @Nullable IEntityLivingData livingdata)
	    {
	        livingdata = super.onInitialSpawn(difficulty, livingdata);
	        this.setEquipmentBasedOnDifficulty(difficulty);
	       // this.setEnchantmentBasedOnDifficulty(difficulty);
	        this.setCombatTask();
	    //    this.setCanPickUpLoot(this.rand.nextFloat() < 0.55F * difficulty.getClampedAdditionalDifficulty());
	        this.setCanPickUpLoot(false);
	        
	        return livingdata;
	    }
	    
	    
	    @Override
	    protected void setEquipmentBasedOnDifficulty(DifficultyInstance difficulty)
	    {
	    	int d = Math.round(difficulty.getClampedAdditionalDifficulty()*3f);
	    	this.addRandomArmor(d);
	    } 
	    
	    
	    protected void addRandomArmor(int difficulty){
	        this.setItemStackToSlot(EntityEquipmentSlot.MAINHAND, new ItemStack(this.pickRandomGun(difficulty)));
	    	
	    	this.setItemStackToSlot(EntityEquipmentSlot.HEAD, new ItemStack(TGArmors.t1_combat_Helmet));
	        this.setItemStackToSlot(EntityEquipmentSlot.CHEST,new ItemStack(TGArmors.t1_combat_Chestplate));
	        this.setItemStackToSlot(EntityEquipmentSlot.LEGS, new ItemStack(TGArmors.t1_combat_Leggings));
	        this.setItemStackToSlot(EntityEquipmentSlot.FEET, new ItemStack(TGArmors.t1_combat_Boots));
	        
	    }
	    
	    
	  /*  @Override
	    public IEntityLivingData onSpawnWithEgg(IEntityLivingData p_110161_1_)
	    {
	        p_110161_1_ = super.onSpawnWithEgg(p_110161_1_);

            //this.tasks.addTask(4, this.aiRangedAttack);
            //this.addRandomArmor();
            //this.enchantEquipment();
            //this.setCombatTask();
	        this.onSpawnByManager(this.rand.nextInt(TGEntities.MAX_NPC_DIFFICULTY+1));
	        
	        return p_110161_1_;
	    }*/

	    public void onSpawnByManager(int difficulty) {
	        this.setCanPickUpLoot(false);	
	    	this.addRandomArmor(difficulty);
	    	this.setCombatTask();
	    }
	    
	    
	    /**
	     * sets this entity's combat AI.
	     */
	    public void setCombatTask()
	    {
	        this.tasks.removeTask(this.aiAttackOnCollide);
	        this.tasks.removeTask(this.aiRangedAttack);
	        ItemStack itemstack = this.getHeldItemMainhand();

	        if (itemstack != null && itemstack.getItem() instanceof techguns.items.guns.GenericGun)
	        {
	        	GenericGun gun = (GenericGun) itemstack.getItem();
	        	this.aiRangedAttack = gun.getAIAttack(this);
	            this.tasks.addTask(4, this.aiRangedAttack);
	        }
	        else
	        {
	            this.tasks.addTask(4, this.aiAttackOnCollide);
	        }
	    }

	    /**
	     * Attack the specified entity using a ranged attack.
	     */
	    @Override
	    public void attackEntityWithRangedAttack(EntityLivingBase target, float distance)
	    {

	    	if (this.getHeldItemMainhand() == null) {
	    		return;
	    	}
	    	else {
	    		Item gun = this.getHeldItemMainhand().getItem();
	    		if (gun instanceof techguns.items.guns.GenericGun) {
	    			
	    			EnumDifficulty difficulty = this.world.getDifficulty();
	    	    	float acc=1.0f;
	    	    	switch(difficulty){
	    	    		case EASY:
	    	    			acc=1.3f;
	    	    			break;
	    	    		case NORMAL:
	    	    			acc = 1.15f;
	    	    			break;
	    	    		case HARD:
	    	    			acc = 1.0f;
	    	    			break;
						default:
							break;
	    	    	}
	    			
	    			((GenericGun) gun).fireWeaponFromNPC(this,0.5f,acc);
	    		}
		
	    	}
	    	
	    }


	    /**
	     * (abstract) Protected helper method to read subclass entity data from NBT.
	     */
	    @Override
	    public void readEntityFromNBT(NBTTagCompound p_70037_1_)
	    {
	        super.readEntityFromNBT(p_70037_1_);

	        this.setCombatTask();
	    }

	    /**
	     * (abstract) Protected helper method to write subclass entity data to NBT.
	     */
	    @Override
	    public void writeEntityToNBT(NBTTagCompound p_70014_1_)
	    {
	        super.writeEntityToNBT(p_70014_1_);
	    }

	    /**
	     * Returns the Y Offset of this entity.
	     */
	    public double getYOffset()
	    {
	        return super.getYOffset() - 0.5D;
	    }

		@Override
		public float getWeaponPosX() {
			return 0;//-0.35f;
		}

		@Override
		public float getWeaponPosY() {
			return 0;//1.0f;
		}

		@Override
		public float getWeaponPosZ() {
			return 0;//;1.1f;
		}

		@Override
		public float getTotalArmorAgainstType(TGDamageSource dmgsrc) {
			Iterable<ItemStack> inv = this.getArmorInventoryList();
			//ItemStack[] inv = new ItemStack[]{this.getEquipmentInSlot(1),this.getEquipmentInSlot(2),this.getEquipmentInSlot(3),this.getEquipmentInSlot(4)};
			float totalArmor=0.0f;
			for (ItemStack stack: inv){
				if(stack!=null && stack.getItem() instanceof ItemArmor){
					
					if(stack.getItem() instanceof GenericArmor){
						GenericArmor genArmor = (GenericArmor) stack.getItem();
						float armorvalue = genArmor.getArmorValue(stack, dmgsrc.damageType);
						totalArmor+=armorvalue;
					} else {
						ItemArmor armor = (ItemArmor) stack.getItem();
						float armorvalue = armor.getArmorMaterial().getDamageReductionAmount(armor.armorType);
						armorvalue = DamageSystem.getArmorAgainstDamageTypeDefault(this, armorvalue, dmgsrc.damageType);
						totalArmor+=armorvalue;
					}
				}
			}
			
			return totalArmor;
		}

		@Override
		public float getPenetrationResistance(TGDamageSource dmgsrc) {
			return 0.0f;
		}

		@Override
		public TGNpcFaction getTGFaction() {
			return TGNpcFaction.HOSTILE;
		}

		@Override
		public void setSwingingArms(boolean swingingArms) {
	        this.dataManager.set(SWINGING_ARMS, Boolean.valueOf(swingingArms));
		}
	    
	    @SideOnly(Side.CLIENT)
	    public boolean isSwingingArms()
	    {
	        return ((Boolean)this.dataManager.get(SWINGING_ARMS)).booleanValue();
	    }

		@Override
		protected void despawnEntity() {
			super.despawnEntity();
			if(this.isDead) {
				System.out.println("Despawned Entity:"+this);
			}
		}
	    
	    
}