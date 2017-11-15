package techguns.items.armors;

import java.util.List;

import com.mojang.realmsclient.gui.ChatFormatting;

import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.SoundCategory;
import net.minecraft.world.World;
import techguns.TGItems;
import techguns.TGSounds;
import techguns.util.InventoryUtil;
import techguns.util.TextUtil;

public class PoweredArmor extends GenericArmorMultiCamo {

	protected ArmorPowerType powerType;
	protected ItemStack battery = null;
	protected ItemStack battery_empty = null;
	
	public int maxpower;

	//
	protected float SpeedBonusUnpowered=0.0f;
	protected float JumpBonusUnpowered=0.0f;
	protected float FallDMGUnpowered=0.0f;
	protected float FallFreeHeightUnpowered=0.0f;
	protected float MiningSpeedBonusUnpowered=0.0f;
	protected float WaterMiningBonusUnpowered=0.0f;
	protected int armorValueUnpowered=0;
	protected float GunAccuracyUnpowered=0.0f;
	protected float extraHeartsUnpowered=0.0f;
	protected float nightvisionUnpowered=0.0f;
	protected float knockbackresistanceUnpowered=0.0f;

	protected float stepassistUnpowerd =0.0f;
	
	protected float oxygen_gearUnpowered=0.0f;
	protected float water_electrolyzerUnpowered=0.0f;
	protected float coolingsystemUnpowered=0.0f;
	
	protected float waterspeedbonusUnpowered=0.0f;
	
	public PoweredArmor(String unlocalizedName, TGArmorMaterial material,
			String[] textureNames, EntityEquipmentSlot type, ArmorPowerType powerType, int maxpower) {
		super(unlocalizedName, material, textureNames, type);
		this.powerType = powerType;
		this.maxpower=maxpower;
	}

	public PoweredArmor setBattery(ItemStack battery) {
		this.battery = battery;
		return this;
	}

	public PoweredArmor setEmptyBattery(ItemStack emptyBattery) {
		this.battery_empty = emptyBattery;
		return this;
	}

	/*public static void consumePower(ItemStack armor, int amount) {
		armor.setItemDamage(armor.getItemDamage() + amount);
		if (armor.getItemDamage() > armor.getMaxDamage()) {
			armor.setItemDamage(armor.getMaxDamage());
		}
	}*/

	public ItemStack getBattery() {
		return battery;
	}

	public ItemStack getBattery_empty() {
		return battery_empty;
	}

	public float getBonusUnpowered(TGArmorBonus type) {
		switch(type){
			case SPEED:
				return this.SpeedBonusUnpowered;
			case JUMP:
				return this.JumpBonusUnpowered;
			case FALLDMG:
				return this.FallDMGUnpowered;
			case FREEHEIGHT:
				return this.FallFreeHeightUnpowered;
			case BREAKSPEED:
				return this.MiningSpeedBonusUnpowered;
			case BREAKSPEED_WATER:
				return this.WaterMiningBonusUnpowered;
			case GUN_ACCURACY:
				return this.GunAccuracyUnpowered;
			case EXTRA_HEART:
				return this.extraHeartsUnpowered;
			case NIGHTVISION:
				return this.nightvisionUnpowered;
			case KNOCKBACK_RESISTANCE:
				return this.knockbackresistanceUnpowered;
			case STEPASSIST:
				return this.stepassistUnpowerd;
			case OXYGEN_GEAR:
				return this.oxygen_gearUnpowered;
			case WATER_ELECTROLYZER:
				return this.water_electrolyzerUnpowered;
			case COOLING_SYSTEM:
				return this.coolingsystemUnpowered;
			case SPEED_WATER:
				return this.waterspeedbonusUnpowered;
		default:
			break;
		}
		return 0.0f;
	}

	public PoweredArmor setSpeedBoni(float speed, float jump, float speed_unpowered, float jump_unpowered) {
		this.SpeedBonus=speed;
		this.JumpBonus=jump;
		this.SpeedBonusUnpowered=speed_unpowered;
		this.JumpBonusUnpowered=jump_unpowered;
		return this;
	}

	public PoweredArmor setFallProtection(float multiplier, float freeheight, float multiplier_unpowered, float freeheight_unpowered) {
		this.FallDMG=multiplier;
		this.FallFreeHeight=freeheight;
		this.FallDMGUnpowered=multiplier_unpowered;
		this.FallFreeHeightUnpowered=freeheight_unpowered;
		return this;
	}

	public PoweredArmor setMiningBoni(float bonus, float bonus_unpowered) {
		this.MiningSpeedBonus=bonus;
		this.MiningSpeedBonusUnpowered=bonus_unpowered;
		return this;
	}

	public PoweredArmor setMiningBoniWater(float bonus, float bonus_unpowered) {
		this.WaterMiningBonus=bonus;
		this.WaterMiningBonusUnpowered=bonus_unpowered;
		return this;
	}

	public PoweredArmor setGunBonus(float acc,float acc_unpowered) {
		this.GunAccuracy=acc;
		this.GunAccuracyUnpowered=acc_unpowered;
		return this;
	}

	public PoweredArmor setHealthBonus(int bonusHearts, int bonusHeartsUnpowered) {
		this.extraHearts=bonusHearts;
		this.extraHeartsUnpowered=bonusHeartsUnpowered;
		return this;
	}

	public PoweredArmor setKnockbackResistance(float resistpercent,float resistpercent_unpowered) {
		this.knockbackresistance=resistpercent;
		this.knockbackresistanceUnpowered=resistpercent_unpowered;
		return this;
	}

	public PoweredArmor setStepAssist(float stepassist, float stepassist_unpowered) {
		this.stepassist=stepassist;
		this.stepassistUnpowerd=stepassist_unpowered;
		return this;
	}

	public PoweredArmor setOxygenGear(float value, float unpowered_value){
		this.oxygen_gear=value;
		this.oxygen_gearUnpowered = unpowered_value;
		return this;
	}
	
	public PoweredArmor setCoolingSystem(float value,  float unpowered_value){
		this.coolingsystem = value;
		this.coolingsystemUnpowered = unpowered_value;
		return this;
	}
	
	public PoweredArmor setWaterspeedBonus(float value,  float unpowered_value){
		this.waterspeedbonus = value;
		this.waterspeedbonusUnpowered = unpowered_value;
		return this;
	}
	
	public static void consumePower(ItemStack armor, int amount) {
		NBTTagCompound tags = armor.getTagCompound();
		if(tags==null){
			tags=new NBTTagCompound();
			armor.setTagCompound(tags);
			tags.setInteger("maxpower", ((PoweredArmor)armor.getItem()).maxpower);
		}
		int power=tags.getInteger("power");
		power-=amount;
		if(power<0){
			power=0;
		}
		tags.setInteger("power", power);
	}
	
	/*public static int setPowered(ItemStack armor, int max) {
		int dmg = armor.getItemDamage();
		if (dmg > max) {
			armor.setItemDamage(armor.getItemDamage() - max);
			return max;
		} else {
			armor.setItemDamage(0);
			return dmg;
		}
	}*/

	public int setPowered(ItemStack armor, int max) {
		NBTTagCompound tags = armor.getTagCompound();
		if(tags==null){
			tags=new NBTTagCompound();
			armor.setTagCompound(tags);
			//tags.setInteger("maxpower", ((PoweredArmor)armor.getItem()).maxpower);
		}
		int power=tags.getInteger("power");
		/*int maxpower=tags.getInteger("maxpower");
		
		if (maxpower==0){
			maxpower= ((PoweredArmor)armor.getItem()).maxpower;
			tags.setInteger("maxpower", maxpower);
		}*/
		
		int missing = maxpower-power;
		
		if(missing>max){
			tags.setInteger("power", power+max);
			return max;
		} else {
			tags.setInteger("power", maxpower);
			return missing;
		}
	}
	
	protected void setFullyPowered(ItemStack armor){
		NBTTagCompound tags = armor.getTagCompound();
		if(tags==null){
			tags=new NBTTagCompound();
			armor.setTagCompound(tags);
			tags.setInteger("maxpower", ((PoweredArmor)armor.getItem()).maxpower);
		}
		/*int maxpower = tags.getInteger("maxpower");
		if(maxpower==0){
			maxpower=((PoweredArmor)armor.getItem()).maxpower;
			tags.setInteger("maxpower", maxpower);
		}*/
		tags.setInteger("power", maxpower);
	}
	
	protected static boolean hasPower(ItemStack armor){
		return getPower(armor)>0;
	}
	
	public static int getPower(ItemStack armor){
		NBTTagCompound tags = armor.getTagCompound();
		if(tags==null){
			tags=new NBTTagCompound();
			armor.setTagCompound(tags);
		}
		return tags.getInteger("power");
	}
	
	protected static void powerSlots(EntityPlayer player, ArmorPowerType power, ItemStack chest, boolean consumeTick){
		for (int i = 0; i < 4; i++) {
			ItemStack armor = player.inventory.armorInventory.get(i);
			// System.out.println("Slot"+i+":"+armor.getDisplayName());
			if (armor != null && armor.getItem() instanceof PoweredArmor) {
				if (i != 2) {
					
	
						if (((PoweredArmor) armor.getItem()).powerType == power) {
							int max = getPower(chest);
							int dmg = ((PoweredArmor)armor.getItem()).setPowered(armor, max);
							consumePower(chest, dmg);
						}
						if (consumeTick){
							consumePower(armor,1);
						}
					
	
				} else if(consumeTick) {
					consumePower(armor,1);
				}
			}
		}
	}
	
	public int applyPowerConsumptionOnAction(TGArmorBonus type,EntityPlayer player){
		
		if (type==TGArmorBonus.JUMP){
			if (this.armorType==EntityEquipmentSlot.LEGS){ //Legs
				if (!player.world.isRemote)
		        {
    				//player.world.playSoundAtEntity(player, "techguns:effects.steamarmorJump", 0.66F, 1.0F );
					player.world.playSound(player.posX, player.posY, player.posZ, TGSounds.STEAM_JUMP, SoundCategory.PLAYERS, 1f, 1f, false);
		        }
				return 5;				
			}
		} else if (type ==TGArmorBonus.FALLDMG){
			if (this.armorType==EntityEquipmentSlot.FEET){
				/*if (!player.worldObj.isRemote)
		        {
    				player.worldObj.playSoundAtEntity(player, "random.door_close", 1.0F, 1.0F );
		        }*/
				return 5;
			}
		} else if (type ==TGArmorBonus.COOLING_SYSTEM){
			if (this.armorType==EntityEquipmentSlot.CHEST){
				return 1;
			}
		}
		
		
		return 0;
	}
	
	
	/**
	 * Called from tick handler to reduce durability
	 */
	public static void calculateConsumptionTick(EntityPlayer player) {
		//ArrayList<Integer> powerArmors = new ArrayList<Integer>();
		boolean moving = false;
		//System.out.println("Player motion:"+player.motionX+", "+player.motionY+", "+player.motionZ);
		float f=0.001f;
		if (player.motionX > f || player.motionZ > f || player.motionX < -f || player.motionZ< -f) {
			moving = true;
		}

		ArmorPowerType power = null;

		ItemStack chest = player.inventory.armorInventory.get(2);
		if (chest != null && chest.getItem() instanceof PoweredArmor) {
			power = ((PoweredArmor) chest.getItem()).powerType;
		}

		int modTick = (int) (player.world.getTotalWorldTime() % 20);
		powerSlots(player,power,chest,(modTick==0)&&moving);
		

		if (chest != null && chest.getItem() instanceof PoweredArmor) {
			PoweredArmor powerChest = (PoweredArmor) chest.getItem();
			if (!hasPower(chest)){
				
				//System.out.println("Reloading the chest!!!");
				
				//chest is empty, try reload;
				if (InventoryUtil.consumeAmmoPlayer(player,powerChest.battery)) {
	    			
    				if (powerChest.battery_empty!=null){
    					int amount = InventoryUtil.addAmmoToPlayerInventory(player, TGItems.newStack(powerChest.battery_empty, 1));
    					if(amount >0 && !player.world.isRemote){
    						player.world.spawnEntity(new EntityItem(player.world, player.posX, player.posY, player.posZ, TGItems.newStack(powerChest.battery_empty, amount)));
    					}
    					
    					//player.inventory.addItemStackToInventory(new ItemStack(powerChest.battery_empty.getItem(),1,powerChest.battery_empty.getItemDamage()));
    				}
    				
    				powerChest.setFullyPowered(chest); //chest.setItemDamage(0);
    				
    				powerSlots(player,power,chest,false);
    				
    				
				} else {
					
					//can't reload
				}
			}
		}
		//

	}

	
	
	@Override
	public void addInformation(ItemStack item, World worldIn, List<String> list, ITooltipFlag flagIn) {
		
		NBTTagCompound tags = item.getTagCompound();
		if(tags==null){
			tags=new NBTTagCompound();
			item.setTagCompound(tags);
			tags.setInteger("power", 0);
			//tags.setInteger("maxpower", ((PoweredArmor)item.getItem()).maxpower);
		}
		int power=tags.getInteger("power");
		//int maxpower=tags.getInteger("maxpower");
		list.add(ChatFormatting.AQUA+"Power: "+power+"/"+maxpower);
		
		if (this.armorType==EntityEquipmentSlot.CHEST){
			list.add(ChatFormatting.AQUA+"PowerType: "+this.powerType.toString()+ " ("+TextUtil.trans(this.battery.getUnlocalizedName()+".name")+")");
		} else {
			list.add(ChatFormatting.AQUA+"PowerType: "+this.powerType.toString());
		}
		
		super.addInformation(item, worldIn, list, flagIn);
	}

	@Override
	public void onCreated(ItemStack stack, World world, EntityPlayer player) {
		super.onCreated(stack, world, player);
		NBTTagCompound tags = stack.getTagCompound();
		if(tags==null){
			tags=new NBTTagCompound();
			stack.setTagCompound(tags);
		}
		tags.setInteger("power", 0);
		//tags.setInteger("maxpower", this.maxpower);
	}
	
}
