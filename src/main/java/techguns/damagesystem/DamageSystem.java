package techguns.damagesystem;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.MobEffects;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.Item;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.CombatRules;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EntityDamageSource;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.GameRules;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.relauncher.ReflectionHelper;
import techguns.TGConfig;
import techguns.api.damagesystem.DamageType;
import techguns.api.npc.INpcTGDamageSystem;
import techguns.entities.npcs.NPCTurret;
import techguns.items.armors.GenericArmor;

public class DamageSystem {

	public static float getDamageFactor(EntityLivingBase attacker, EntityLivingBase target) {
		if (attacker instanceof EntityPlayer && target instanceof EntityPlayer){
			if (FMLCommonHandler.instance().getMinecraftServerInstance().isPVPEnabled()){
				return TGConfig.damagePvP;
			} else {
				return 0.0f;
			}
		} else if (target instanceof EntityPlayer){
			if ( attacker instanceof NPCTurret){
				return TGConfig.damageTurretToPlayer;
			} else {
				return TGConfig.damageFactorNPC;
			}

		} else if (attacker instanceof EntityPlayer){
			return 1.0f;
		}
		
		return TGConfig.damageFactorNPC;
	}
	
	
	protected static Field ENT_rand = ReflectionHelper.findField(Entity.class, "rand", "field_70146_Z");
	
	protected static Field ELB_idleTime = ReflectionHelper.findField(EntityLivingBase.class, "idleTime", "field_70708_bq");
	
	protected static Field ELB_lastDamage = ReflectionHelper.findField(EntityLivingBase.class, "lastDamage", "field_110153_bc");
	
	protected static Field ELB_recentlyHit = ReflectionHelper.findField(EntityLivingBase.class, "recentlyHit", "field_70718_bc");
	protected static Field ELB_attackingPlayer = ReflectionHelper.findField(EntityLivingBase.class, "attackingPlayer", "field_70717_bb");
	
	protected static Field ELB_lastDamageSource = ReflectionHelper.findField(EntityLivingBase.class, "lastDamageSource", "field_189750_bF");
	protected static Field ELB_lastDamageStamp = ReflectionHelper.findField(EntityLivingBase.class, "lastDamageStamp", "field_189751_bG");
	
	protected static Method ELB_canBlockDamageSource = ReflectionHelper.findMethod(EntityLivingBase.class, "canBlockDamageSource", "func_184583_d", DamageSource.class);
	
	protected static Method ELB_damageShield = ReflectionHelper.findMethod(EntityLivingBase.class, "damageShield", "func_184590_k", float.class);
	
	protected static Method ELB_blockUsingShield = ReflectionHelper.findMethod(EntityLivingBase.class, "blockUsingShield", "func_190629_c", EntityLivingBase.class);
	
	protected static Method ELB_damageEntity = ReflectionHelper.findMethod(EntityLivingBase.class, "damageEntity", "func_70665_d", DamageSource.class, float.class);
	
	protected static Method ELB_setBeenAttacked = ReflectionHelper.findMethod(EntityLivingBase.class, "markVelocityChanged", "func_70018_K");
	
	protected static Method ELB_checkTotemDeathProtection = ReflectionHelper.findMethod(EntityLivingBase.class, "checkTotemDeathProtection", "func_190628_d", DamageSource.class);
	
	protected static Method ELB_getDeathSound = ReflectionHelper.findMethod(EntityLivingBase.class, "getDeathSound", "func_184615_bR");
	protected static Method ELB_getSoundVolume = ReflectionHelper.findMethod(EntityLivingBase.class, "getSoundVolume", "func_70599_aP");
	protected static Method ELB_getSoundPitch = ReflectionHelper.findMethod(EntityLivingBase.class, "getSoundPitch", "func_70647_i");
	
	protected static Method ELB_playHurtSound = ReflectionHelper.findMethod(EntityLivingBase.class, "playHurtSound", "func_184581_c", DamageSource.class);
	
	protected static Method ELB_applyPotionDamageCalculations = ReflectionHelper.findMethod(EntityLivingBase.class, "applyPotionDamageCalculations", "func_70672_c", DamageSource.class, float.class);
	protected static Method ELB_damageArmor = ReflectionHelper.findMethod(EntityLivingBase.class, "damageArmor", "func_70675_k", float.class);
	
	public static float getTotalArmorAgainstType(EntityPlayer ply, DamageType type){
		float value=0.0f;
		
		for(int i=0;i<4; i++){
			ItemStack armor = ply.inventory.armorInventory.get(i);//ply.inventory.armorInventory[i];
			if(armor!=null){
				Item item = armor.getItem();
				
				if(item instanceof GenericArmor){
					value+=((GenericArmor)item).getArmorValue(armor, type);				
				} else if (item instanceof ItemArmor){
					if(type==DamageType.PHYSICAL){
						value += ((ItemArmor) item).getArmorMaterial().getDamageReductionAmount(((ItemArmor)item).armorType);
					}
				}
				
			}
			
		}
		
		return value;
	}
	
	/**
	 * Default behavior when unspecified
	 */
	public static float getArmorAgainstDamageTypeDefault(EntityLivingBase elb, float armor, DamageType damageType){
		switch(damageType){
			case PHYSICAL:
			case PROJECTILE:
				return armor;
				
			case EXPLOSION:
			case ENERGY:
			case ICE:
			case LIGHTNING:
			case DARK:
				return armor*0.5f;
			case FIRE:
				if(elb.isImmuneToFire()){
					return armor*2;
				} else {
					return armor*0.5f;
				}
				
			case POISON:
				return 0;
			case RADIATION:
				return 0;
			case UNRESISTABLE:
			default:
				return 0;
		}
		
	}
	
    /**
     * Static copy of EntityLivingBase.attackEntityFrom with some changes
     * @throws IllegalAccessException 
     * @throws IllegalArgumentException 
     * @throws InvocationTargetException 
     */
    public static boolean attackEntityFrom(EntityLivingBase ent, DamageSource source, float amount) throws IllegalArgumentException, IllegalAccessException, InvocationTargetException
    {
        //if (!net.minecraftforge.common.ForgeHooks.onLivingAttack(this, source, amount)) return false;
    	
    	TGDamageSource dmgsrc = TGDamageSource.getFromGenericDamageSource(source);
    	
        if (ent.isEntityInvulnerable(source))
        {
            return false;
        }
        else if (ent.world.isRemote)
        {
            return false;
        }
        else
        {
            //ent.idleTime = 0;
            ELB_idleTime.setInt(ent, 0);

            if (ent.getHealth() <= 0.0F)
            {
                return false;
            }
            else if (source.isFireDamage() && ent.isPotionActive(MobEffects.FIRE_RESISTANCE))
            {
                return false;
            }
            else
            {
                float f = amount;

                if ((source == DamageSource.ANVIL || source == DamageSource.FALLING_BLOCK) && !ent.getItemStackFromSlot(EntityEquipmentSlot.HEAD).isEmpty())
                {
                    ent.getItemStackFromSlot(EntityEquipmentSlot.HEAD).damageItem((int)(amount * 4.0F + ((java.util.Random)ENT_rand.get(ent)).nextFloat() * amount * 2.0F), ent);
                    amount *= 0.75F;
                }

                boolean flag = false;

                ELB_canBlockDamageSource.invoke(ent, source);
                
                if (amount > 0.0F && ((Boolean)ELB_canBlockDamageSource.invoke(ent, source)))
                {
                    //ent.damageShield(amount);
                	ELB_damageShield.invoke(ent, amount);
                    amount = 0.0F;

                    if (!source.isProjectile())
                    {
                        Entity entity = source.getImmediateSource();

                        if (entity instanceof EntityLivingBase)
                        {
                            //ent.blockUsingShield((EntityLivingBase)entity);
                            ELB_blockUsingShield.invoke(ent, (EntityLivingBase)entity);
                        }
                    }

                    flag = true;
                }

                ent.limbSwingAmount = 1.5F;
                boolean flag1 = true;

                if (!dmgsrc.ignoreHurtresistTime && ((float)ent.hurtResistantTime > (float)ent.maxHurtResistantTime / 2.0F))
                {
                    if (amount <= ELB_lastDamage.getFloat(ent))//ent.lastDamage)
                    {
                        return false;
                    }

                    //ent.damageEntity(source, amount - ELB_lastDamage.getFloat(ent));//ent.lastDamage);
                    ELB_damageEntity.invoke(ent, source, amount - ELB_lastDamage.getFloat(ent));
                    //ent.lastDamage = amount;
                    ELB_lastDamage.setFloat(ent, amount);
                    flag1 = false;
                }
                else
                {
                    //ent.lastDamage = amount;
                	if (!dmgsrc.ignoreHurtresistTime) {
	                    ELB_lastDamage.setFloat(ent, amount);
	                    ent.hurtResistantTime = ent.maxHurtResistantTime;
                	}
                    //ent.damageEntity(source, amount);
                    ELB_damageEntity.invoke(ent, source, amount);
                    if(!dmgsrc.ignoreHurtresistTime) {
	                    ent.maxHurtTime = 10;
	                    ent.hurtTime = ent.maxHurtTime;
                    }
                }

                ent.attackedAtYaw = 0.0F;
                Entity entity1 = source.getTrueSource();

                if (entity1 != null)
                {
                    if (entity1 instanceof EntityLivingBase)
                    {
                        ent.setRevengeTarget((EntityLivingBase)entity1);
                    }

                    if (entity1 instanceof EntityPlayer)
                    {
                        //ent.recentlyHit = 100;
                        ELB_recentlyHit.setInt(ent, 100);
                        //ent.attackingPlayer = (EntityPlayer)entity1;
                        ELB_attackingPlayer.set(ent, (EntityPlayer)entity1);
                    }
                    else if (entity1 instanceof net.minecraft.entity.passive.EntityTameable)
                    {
                        net.minecraft.entity.passive.EntityTameable entitywolf = (net.minecraft.entity.passive.EntityTameable)entity1;

                        if (entitywolf.isTamed())
                        {
                            //ent.recentlyHit = 100;
                            ELB_recentlyHit.setInt(ent, 100);
                            //ent.attackingPlayer = null;
                            ELB_attackingPlayer.set(ent, null);
                        }
                    }
                }

                if (flag1)
                {
                    if (flag)
                    {
                        ent.world.setEntityState(ent, (byte)29);
                    }
                    else if (source instanceof EntityDamageSource && ((EntityDamageSource)source).getIsThornsDamage())
                    {
                        ent.world.setEntityState(ent, (byte)33);
                    }
                    else
                    {
                        byte b0;

                        if (source == DamageSource.DROWN)
                        {
                            b0 = 36;
                        }
                        else if (source.isFireDamage())
                        {
                            b0 = 37;
                        }
                        else
                        {
                            b0 = 2;
                        }

                        ent.world.setEntityState(ent, b0);
                    }

                    if (source != DamageSource.DROWN && (!flag || amount > 0.0F))
                    {
                        //ent.setBeenAttacked();
                    	ELB_setBeenAttacked.invoke(ent);
                    }

                    if (entity1 != null)
                    {
                        double d1 = entity1.posX - ent.posX;
                        double d0;

                        for (d0 = entity1.posZ - ent.posZ; d1 * d1 + d0 * d0 < 1.0E-4D; d0 = (Math.random() - Math.random()) * 0.01D)
                        {
                            d1 = (Math.random() - Math.random()) * 0.01D;
                        }

                        ent.attackedAtYaw = (float)(MathHelper.atan2(d0, d1) * (180D / Math.PI) - (double)ent.rotationYaw);
                        float knockback_strength = 0.4F*dmgsrc.knockbackMultiplier;
                        if (knockback_strength>0) {
                        	ent.knockBack(entity1, knockback_strength, d1, d0);
                        }
                    }
                    else
                    {
                        ent.attackedAtYaw = (float)((int)(Math.random() * 2.0D) * 180);
                    }
                }

                if (ent.getHealth() <= 0.0F)
                {
                    //if (!ent.checkTotemDeathProtection(source))
                	if (!((Boolean)ELB_checkTotemDeathProtection.invoke(ent, source)))
                    {
                        SoundEvent soundevent = (SoundEvent)ELB_getDeathSound.invoke(ent);//ent.getDeathSound();

                        if (flag1 && soundevent != null)
                        {
                            ent.playSound(soundevent, (float)ELB_getSoundVolume.invoke(ent), (float)ELB_getSoundPitch.invoke(ent));//ent.getSoundVolume(), ent.getSoundPitch());
                        }

                        ent.onDeath(source);
                    }
                }
                else if (flag1)
                {
                    //ent.playHurtSound(source);
                    ELB_playHurtSound.invoke(ent, source);
                }

                boolean flag2 = !flag || amount > 0.0F;

                if (flag2)
                {
                    //ent.lastDamageSource = source;
                    ELB_lastDamageSource.set(ent, source);
                    //ent.lastDamageStamp = ent.world.getTotalWorldTime();
                    ELB_lastDamageStamp.setLong(ent, ent.world.getTotalWorldTime());
                }

                if (ent instanceof EntityPlayerMP)
                {
                    CriteriaTriggers.ENTITY_HURT_PLAYER.trigger((EntityPlayerMP)ent, source, f, amount, flag);
                }

                if (entity1 instanceof EntityPlayerMP)
                {
                    CriteriaTriggers.PLAYER_HURT_ENTITY.trigger((EntityPlayerMP)entity1, ent, source, f, amount, flag);
                }

                //return parameter workaround
                if(flag2) {
                	dmgsrc.setAttackSuccessful();
                }
                return flag2;
            }
        }
    }

    
    public static void livingHurt(EntityLivingBase elb, DamageSource damageSrc, float damageAmount) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
    	damageAmount = ELB_applyArmorCalculations(elb,damageSrc, damageAmount);
        //damageAmount = elb.applyPotionDamageCalculations(damageSrc, damageAmount);
        damageAmount = (Float)ELB_applyPotionDamageCalculations.invoke(elb, damageSrc, damageAmount);
        float f = damageAmount;
        damageAmount = Math.max(damageAmount - elb.getAbsorptionAmount(), 0.0F);
        elb.setAbsorptionAmount(elb.getAbsorptionAmount() - (f - damageAmount));

        if (damageAmount != 0.0F)
        {
            float f1 = elb.getHealth();
            elb.setHealth(f1 - damageAmount);
            elb.getCombatTracker().trackDamage(damageSrc, f1, damageAmount);
            elb.setAbsorptionAmount(elb.getAbsorptionAmount() - damageAmount);
        }
    }
    
    /**
     * Reduces damage, depending on armor
     */
    public static float ELB_applyArmorCalculations(EntityLivingBase elb, DamageSource source, float damage) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException
    {
        if (!source.isUnblockable())
        {
            ELB_damageArmor.invoke(elb, damage);
             
            TGDamageSource dmgsrc = TGDamageSource.getFromGenericDamageSource(source);
            INpcTGDamageSystem tg = (INpcTGDamageSystem) elb;
            
            float toughness = tg.getToughnessAfterPentration(elb, dmgsrc);
            System.out.println("DamageBefore:"+damage);
            System.out.println("Armor:"+tg.getTotalArmorAgainstType(dmgsrc));
            System.out.println("Pen:"+dmgsrc.armorPenetration);
            System.out.println("ToughnessAfterPen:"+toughness);
            
            damage = CombatRules.getDamageAfterAbsorb(damage, tg.getTotalArmorAgainstType(dmgsrc), toughness);
        }

        System.out.println("DamageAfter:"+damage);
        return damage;
    }
}
