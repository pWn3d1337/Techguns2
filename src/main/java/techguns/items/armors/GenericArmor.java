package techguns.items.armors;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import org.lwjgl.input.Keyboard;

import com.mojang.realmsclient.gui.ChatFormatting;

import net.minecraft.client.model.ModelBiped;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;
import net.minecraft.util.DamageSource;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.common.ISpecialArmor;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraftforge.oredict.OreDictionary;
import techguns.TGArmors;
import techguns.TGItems;
import techguns.Techguns;
import techguns.api.damagesystem.DamageType;
import techguns.api.render.IItemTGRenderer;
import techguns.api.tginventory.ITGSpecialSlot;
import techguns.capabilities.TGExtendedPlayer;
import techguns.client.ClientProxy;
import techguns.damagesystem.TGDamageSource;
import techguns.gui.player.TGPlayerInventory;
import techguns.util.TextUtil;

public class GenericArmor extends ItemArmor implements ISpecialArmor , IItemTGRenderer{
	//private static ArrayList<Item> techgunArmors = new ArrayList<Item>();

	//For vanilla Anvil
	private ItemStack repairItem=null;
	
	//Values for RepairBench
	protected ItemStack repairMatMetal=null;
	protected ItemStack repairMatCloth=null;
	protected float repairMatRatioMetal=0.5f;
	protected int repairMatCount=0;
	
	String textureName;
	
	//ModelBiped model;	
	int modelIndex=-1;
	boolean doubleTex=true;
	
	/*public static double ARMOR_CAP = 24.0D/25.0D;
	public static float MAX_ARMOR = 24.0f;*/
	
	//item boni
	protected float SpeedBonus=0.0f;
	protected float JumpBonus=0.0f;
	protected float FallDMG=0.0f;
	protected float FallFreeHeight=0.0f;
	protected float MiningSpeedBonus=0.0f;
	protected float WaterMiningBonus=0.0f;
	protected int armorValue=0;
	protected float GunAccuracy=0.0f;
	protected float extraHearts=0.0f;
	protected float nightvision=0.0f;
	
	protected float knockbackresistance=0.0f;
	protected float stepassist =0.0f;
	
	protected float oxygen_gear=0.0f;
	protected float water_electrolyzer=0.0f;
	protected float coolingsystem=0.0f;
	
	protected float waterspeedbonus=0.0f;
	
	protected boolean hideFaceslot=false;
	protected boolean hideBackslot=false;
	
	protected TGArmorMaterial material;
	
	protected static DecimalFormat formatArmor = new DecimalFormat("#.##", DecimalFormatSymbols.getInstance(Locale.ENGLISH));
	
	protected boolean use3dRenderHack = false;
	
	public GenericArmor(String unlocalizedName, TGArmorMaterial material, String textureName, EntityEquipmentSlot type) {
	    super(material.createVanillaMaterial(), 0, type);
	    this.material=material;
	    this.textureName = textureName; //Armor Texture
		setCreativeTab(Techguns.tabTechgun);
	    this.setUnlocalizedName(unlocalizedName);
	    this.setRegistryName(new ResourceLocation(Techguns.MODID, unlocalizedName));
	   // this.setTextureName(Techguns.MODID + ":" + unlocalizedName); //Item Texture
	    //for tooltip
	    this.armorValue=Math.round(material.getArmorValueSlot(type, DamageType.PHYSICAL));
	    
	    this.setMaxDamage(this.material.getDurability(type));
	    
	    TGArmors.armors.add(this);
	   // this.repairItem= repairMaterial;
	    //techgunArmors.add(this);
	}
	
	/*public static double getArmorCap(DamageType damageType){
		return ARMOR_CAP;
	}*/
	
	/**
	 * SteamArmor (0,1)
	 * PowerArmor (2,3)
	 * ExoSuit (4,5,6)
	 * Beret (7)
	 */
	public GenericArmor setArmorModel(int index, boolean doubleTex) {
		this.modelIndex = index;
		this.doubleTex=doubleTex;
		return this;
	}

	public static boolean isTechgunArmor(ItemStack i) {
		if (i==null){
			return false;
		} else {
			return i.getItem() instanceof GenericArmor;
		}
	}
	
	public GenericArmor setSpeedBoni(float speed,float jump){
		this.SpeedBonus=speed;
		this.JumpBonus=jump;
		return this;
	}
	
	public GenericArmor setFallProtection(float multiplier, float freeheight){
		this.FallDMG = multiplier;
		this.FallFreeHeight = freeheight;
		return this;
	}
	
	public GenericArmor setMiningBoni(float bonus){
		this.MiningSpeedBonus=bonus;
		return this;
	}
	
	public GenericArmor setMiningBoniWater(float bonus){
		this.WaterMiningBonus=bonus;
		return this;
	}
	
	public GenericArmor setGunBonus(float acc){
		this.GunAccuracy=acc;
		return this;
	}
	
	public GenericArmor setHealthBonus(int bonusHearts){
		this.extraHearts=(float)bonusHearts;
		return this;
	}
	
	public GenericArmor setDurability(int dur){
		this.setMaxDamage(dur);
		return this;
	}
	
	public GenericArmor setKnockbackResistance(float resistpercent){
		this.knockbackresistance=resistpercent;
		return this;
	}
	
	public GenericArmor setStepAssist(float stepassist){
		this.stepassist=stepassist;
		return this;
	}
	
	public GenericArmor setOxygenGear(float value){
		this.oxygen_gear=value;
		return this;
	}
	
	public GenericArmor setCoolingSystem(float value){
		this.coolingsystem = value;
		return this;
	}
	
	public GenericArmor setWaterspeedBonus(float value){
		this.waterspeedbonus = value;
		return this;
	}
	
	public GenericArmor setUseRenderHack() {
		this.use3dRenderHack=true;
		return this;
	}
	
	public float getBonus(TGArmorBonus type){
		switch(type){
			case SPEED:
				return this.SpeedBonus;
			case JUMP:
				return this.JumpBonus;
			case FALLDMG:
				return this.FallDMG;
			case FREEHEIGHT:
				return this.FallFreeHeight;
			case BREAKSPEED:
				return this.MiningSpeedBonus;
			case BREAKSPEED_WATER:
				return this.WaterMiningBonus;
			case GUN_ACCURACY:
				return this.GunAccuracy;
			case EXTRA_HEART:
				return this.extraHearts;
			case NIGHTVISION:
				return this.nightvision;
			case KNOCKBACK_RESISTANCE:
				return this.knockbackresistance;
			case STEPASSIST:
				return this.stepassist;
			case OXYGEN_GEAR:
				return this.oxygen_gear;
			case WATER_ELECTROLYZER:
				return this.water_electrolyzer;
			case COOLING_SYSTEM:
				return this.coolingsystem;
			case SPEED_WATER:
				return this.waterspeedbonus;
				
		default:
			break;
		}
		return 0.0f;
	}
	
	public GenericArmor addEnchantment(){
		
		return this;
	}
	
	public static float getArmorBonusForPlayer(EntityPlayer ply, TGArmorBonus type, boolean consumePower){
		float bonus = 0.0f;
		TGExtendedPlayer props = TGExtendedPlayer.get(ply);
		if (props==null){
			return 0.0f;
		}
		for (int i=0; i<4; i++){
			ItemStack armor = ply.inventory.armorInventory.get(i);
			if (GenericArmor.isTechgunArmor(armor)){
				
				
				if (! (armor.getItem() instanceof PoweredArmor)){
					if (armor.getItemDamage()<armor.getMaxDamage()-1){
						float bonusVal=((GenericArmor) armor.getItem()).getBonus(type);
						bonus+=bonusVal;
					}
				} else {
					//Powered Armor
					PoweredArmor pwrarmor = (PoweredArmor) armor.getItem();
					boolean power = pwrarmor.hasPower(armor);
					float bonusVal=0;
					
					if (armor.getItemDamage()<armor.getMaxDamage()-1){
						if (power){
							bonusVal = pwrarmor.getBonus(type);
							bonus+=bonusVal;
							if (consumePower && bonusVal>0.0f){
								PoweredArmor.consumePower(armor, pwrarmor.applyPowerConsumptionOnAction(type,ply));
							}
						} else  {
							bonusVal = pwrarmor.getBonusUnpowered(type);
							bonus+=bonusVal;
						}
					}
					
				}
			}
		}
		
		if(props!=null){
			
			bonus+=getBonusForSlot(props.tg_inventory.inventory.get(TGPlayerInventory.SLOT_FACE), type, consumePower, ply);
			bonus+=getBonusForSlot(props.tg_inventory.inventory.get(TGPlayerInventory.SLOT_BACK), type, consumePower, ply);
			bonus+=getBonusForSlot(props.tg_inventory.inventory.get(TGPlayerInventory.SLOT_HAND), type, consumePower, ply);
		}
		
		return bonus;
	}
	
	private static float getBonusForSlot(ItemStack stack, TGArmorBonus type, boolean consumePower, EntityPlayer ply) {
		float bonus=0;
		if(!stack.isEmpty()){
			ITGSpecialSlot item = (ITGSpecialSlot) stack.getItem();
			if(stack.getItem()==TGItems.SHARED_ITEM || (stack.getItemDamage()<stack.getMaxDamage()) || !stack.getItem().isDamageable()){
				bonus+=item.getBonus(type,stack, consumePower, ply);
			}
		}
		return bonus;
	}
	
	private String getSingleTexture(){
		return Techguns.MODID + ":textures/armor/" + this.textureName + ".png";
	}
	private String getDoubleTexture(){
		return Techguns.MODID + ":textures/armor/" + this.textureName + "_" + (this.armorType == EntityEquipmentSlot.LEGS ? "2" : "1") + ".png";
	}
	
	protected boolean hasDoubleTexture(){
		return this.doubleTex;
	}
	
	private String trans(String text){
		return TextUtil.trans(Techguns.MODID+"."+text);
	}
	
	@Override
	public ModelBiped getArmorModel(EntityLivingBase entityLiving, ItemStack itemStack, EntityEquipmentSlot armorSlot, ModelBiped _default) {
		if(modelIndex >=0 ){
			
			ModelBiped model = ClientProxy.armorModels[this.modelIndex];
			
			model.bipedHead.showModel = armorSlot == EntityEquipmentSlot.HEAD;
			model.bipedHeadwear.showModel = armorSlot == EntityEquipmentSlot.HEAD;
			
			if (!itemStack.isEmpty() && itemStack.getItem()==TGArmors.t2_beret){
				model.bipedHeadwear.showModel = false;
			}
			
			model.bipedBody.showModel = armorSlot == EntityEquipmentSlot.CHEST || armorSlot == EntityEquipmentSlot.LEGS;
			model.bipedRightArm.showModel = armorSlot == EntityEquipmentSlot.CHEST;
			model.bipedLeftArm.showModel = armorSlot == EntityEquipmentSlot.CHEST;
			model.bipedRightLeg.showModel = armorSlot == EntityEquipmentSlot.LEGS || armorSlot == EntityEquipmentSlot.FEET;
			model.bipedLeftLeg.showModel = armorSlot == EntityEquipmentSlot.LEGS || armorSlot == EntityEquipmentSlot.FEET;
			model.isSneak = entityLiving.isSneaking();
			model.isRiding = entityLiving.isRiding();
			model.isChild = entityLiving.isChild();
			
			//model.leftArmPose = _default.leftArmPose;
			//model.rightArmPose = _default.rightArmPose;
			
			//model.swingProgress = _default.swingProgress;
			
			/*model.heldItemRight = entityLiving.getEquipmentInSlot(0) != null ? 1 :0;
			if(entityLiving instanceof EntityPlayer){
				ItemStack stack=entityLiving.getHeldItem();
				if(stack!=null && stack.getItem() instanceof GenericGun){
					model.aimedBow=true;
				} else {			            
					model.aimedBow =((EntityPlayer)entityLiving).getItemInUseDuration() > 2 && ((EntityPlayer)entityLiving).getItemInUse().getItemUseAction() == EnumAction.bow;
				}
				
				if (stack != null && stack.getItemUseAction() == EnumAction.block && ((EntityPlayer)entityLiving).getItemInUseCount()>0){
					model.heldItemRight = 3;
				}
				
			} else if (entityLiving instanceof GenericNPC){
				
				if (((GenericNPC)entityLiving).getHasAimedBowAnim()){
					ItemStack stack=entityLiving.getHeldItem();
					if(stack!=null && stack.getItem() instanceof GenericGun){
						model.aimedBow=true;
					}
				}
			}*/
			return model;
		}
		return null;
	}

	public static String formatAV(float armorValue, DamageType damageType){
		//float percent = (float) ((armorValue/GenericArmor.MAX_ARMOR)*GenericArmor.getArmorCap(damageType));
		return formatArmor.format(armorValue); //+ " ("+ formatArmor.format(percent*100)+"%)";
	}
	
	protected void addArmorvaluesInformation(ItemStack item, List list){
		
		EntityEquipmentSlot slot = this.armorType;
		
		if (this.getPenetrationResistance()>0.0f){
			list.add(ChatFormatting.DARK_GRAY+trans("armorTooltip.penetrationResistance")+" "+formatArmor.format(this.getPenetrationResistance()*100)+"%");
		}
		
		list.add(ChatFormatting.DARK_GRAY+" AR: "+formatAV(this.material.getArmorValueSlot(slot, DamageType.PHYSICAL),DamageType.PHYSICAL));
		list.add(ChatFormatting.GRAY+" PR: "+formatAV(this.material.getArmorValueSlot(slot, DamageType.PROJECTILE),DamageType.PROJECTILE));
		list.add(ChatFormatting.DARK_RED+" EX: "+formatAV(this.material.getArmorValueSlot(slot, DamageType.EXPLOSION),DamageType.EXPLOSION));
		list.add(ChatFormatting.DARK_AQUA+" E: "+formatAV(this.material.getArmorValueSlot(slot, DamageType.ENERGY),DamageType.ENERGY));
				
		list.add(ChatFormatting.RED+" F: "+formatAV(this.material.getArmorValueSlot(slot, DamageType.FIRE),DamageType.FIRE));
		list.add(ChatFormatting.AQUA+" I: "+formatAV(this.material.getArmorValueSlot(slot, DamageType.ICE),DamageType.ICE));
		list.add(ChatFormatting.YELLOW+" L: "+formatAV(this.material.getArmorValueSlot(slot, DamageType.LIGHTNING),DamageType.LIGHTNING));
		list.add(ChatFormatting.DARK_GREEN+" P: "+formatAV(this.material.getArmorValueSlot(slot, DamageType.POISON),DamageType.POISON));
		list.add(ChatFormatting.DARK_GRAY+" D: "+formatAV(this.material.getArmorValueSlot(slot, DamageType.DARK),DamageType.DARK));
		list.add(ChatFormatting.GREEN+" RAD: "+formatAV(this.material.getArmorValueSlot(slot, DamageType.RADIATION),DamageType.RADIATION));		
	}

	@Override
	public void addInformation(ItemStack item, World worldIn, List<String> list, ITooltipFlag flagIn) {
		super.addInformation(item, worldIn, list, flagIn);

		if (Keyboard.isKeyDown(Keyboard.KEY_LSHIFT) || Keyboard.isKeyDown(Keyboard.KEY_RSHIFT)){
			
			list.add(trans("armorTooltip.durability")+": "+(item.getMaxDamage()-item.getItemDamage())+"/"+(item.getMaxDamage()));
			//list.add(trans("armorTooltip.armorvalue")+": " +this.armorValue);
			
			list.add(trans("armorTooltip.resistances")+":");
			//list.add(EnumChatFormatting.DARK_GRAY+" AR:"+this.material.getArmorValueSlot(this.armorType, DamageType.PHYSICAL));
			this.addArmorvaluesInformation(item, list);
			
			if(this.getBonus(TGArmorBonus.EXTRA_HEART)>0.0f){
				list.add(trans("armorTooltip.healthbonus")+": +"+(int)this.getBonus(TGArmorBonus.EXTRA_HEART)+" "+trans("armorTooltip.hearts"));
			} else if (this.getBonus(TGArmorBonus.SPEED)>0.0f){
				list.add(trans("armorTooltip.movespeed")+": +"+this.getBonus(TGArmorBonus.SPEED)*100.0f+"%");
			} else if(this.getBonus(TGArmorBonus.JUMP)>0.0f){
				list.add(trans("armorTooltip.jumpheight")+": +"+this.getBonus(TGArmorBonus.JUMP));
			} if(this.getBonus(TGArmorBonus.FALLDMG)>0.0f){
				list.add(trans("armorTooltip.falldamage")+": -"+this.getBonus(TGArmorBonus.FALLDMG)*100.0f+"%");
			} if(this.getBonus(TGArmorBonus.FREEHEIGHT)>0.0f){
				list.add(trans("armorTooltip.fallheight")+": -"+this.getBonus(TGArmorBonus.FREEHEIGHT));
			} if(this.getBonus(TGArmorBonus.BREAKSPEED)>0.0f){
				list.add(trans("armorTooltip.miningspeed")+": +"+this.getBonus(TGArmorBonus.BREAKSPEED)*100.0f+"%");
			} if(this.getBonus(TGArmorBonus.BREAKSPEED_WATER)>0.0f){
				list.add(trans("armorTooltip.underwatermining")+": +"+this.getBonus(TGArmorBonus.BREAKSPEED_WATER)*100.0f+"%");
			} if(this.getBonus(TGArmorBonus.KNOCKBACK_RESISTANCE)>0.0f){
				list.add(trans("armorTooltip.knockbackresistance")+": +"+this.getBonus(TGArmorBonus.KNOCKBACK_RESISTANCE)*100.0f+"%");
			} if(this.getBonus(TGArmorBonus.NIGHTVISION)>0.0f){
				list.add(trans("armorTooltip.nightvision"));
			} if(this.getBonus(TGArmorBonus.STEPASSIST)>0.0f){
				list.add(trans("armorTooltip.stepassist"));
			} if(this.getBonus(TGArmorBonus.OXYGEN_GEAR)>0.0f){
				list.add(trans("armorTooltip.oxygengear"));
			} if(this.getBonus(TGArmorBonus.COOLING_SYSTEM)>0.0f){
				list.add(trans("armorTooltip.coolingsystem"));
			}	
		} else {
			this.addMinimalInformation(item, list);
			list.add(TextUtil.trans("techguns.gun.tooltip.shift1")+" "+ChatFormatting.GREEN+TextUtil.trans("techguns.gun.tooltip.shift2")+" "+ChatFormatting.GRAY+TextUtil.trans("techguns.gun.tooltip.shift3"));
		}

	}

	/**
	 * to override in subclasses
	 * @param item
	 * @param player
	 * @param list
	 * @param b
	 * @return
	 */
	protected void addMinimalInformation(ItemStack item, List<String> list){
		list.add(trans("armorTooltip.durability")+": "+(item.getMaxDamage()-item.getItemDamage())+"/"+(item.getMaxDamage()));	
	}
	
	public float getArmorValue(ItemStack armor, DamageType type){
		return this.material.getArmorValueSlot(armorType, type);
	}
	
	public float getPenetrationResistance(){
		return this.material.getPenetrationResistance();
	}
	
	@Override
	public boolean getIsRepairable(ItemStack item, ItemStack mat) {
		GenericArmor armor=(GenericArmor) item.getItem();
		if(armor.repairItem!=null){
			return OreDictionary.itemMatches(armor.repairItem, mat, true);
		}
			return false;
	}
	
	@Override
	public void damageArmor(EntityLivingBase entity, ItemStack stack,
			DamageSource source, int damage, int slot) {
		
		//System.out.println("Damage Armour!");
		if (!(entity instanceof EntityPlayerMP) || !((EntityPlayer)entity).capabilities.isCreativeMode)
        {
            if (stack.isItemStackDamageable())
            {
            	
            	//Keep Armours at 1 durability.
            	int maxDmg = stack.getMaxDamage()-1-stack.getItemDamage();
            	//System.out.println("MAxDmg:"+maxDmg);
            	if (damage>maxDmg){
            		damage=maxDmg;
            		//System.out.println("Damage Reduced from "+damage+" to "+maxDmg);
            	}
            	            	
            	
                if (stack.attemptDamageItem(damage, entity.getRNG(), (EntityPlayerMP) entity))
                {
                	entity.renderBrokenItemStack(stack);

                	if (stack.getItemDamage()>stack.getMaxDamage()){
                		stack.setItemDamage(stack.getMaxDamage());
                	}
                }
            }
        }
		
	}

	public boolean isHideFaceslot() {
		return hideFaceslot;
	}

	public GenericArmor setHideFaceslot(boolean hideFaceslot) {
		this.hideFaceslot = hideFaceslot;
		return this;
	}

	public boolean isHideBackslot() {
		return hideBackslot;
	}

	public GenericArmor setHideBackslot(boolean hideBackslot) {
		this.hideBackslot = hideBackslot;
		return this;
	}
	
	
	public GenericArmor setRepairMats(ItemStack metal, ItemStack cloth, float metalpercent, int totalmats){
		if(metal!=null){
			this.repairItem = TGItems.newStack(metal, 1);
		} else if (cloth!=null){
			this.repairItem = TGItems.newStack(cloth, 1);
		}
		
		if(metal!=null){
			this.repairMatMetal=TGItems.newStack(metal, 1);
		}
		if(cloth!=null){
			this.repairMatCloth=TGItems.newStack(cloth, 1);
		}
		this.repairMatCount=totalmats;
		this.repairMatRatioMetal = metalpercent;
		
		return this;
	}
	
	public boolean canRepairOnRepairBench(ItemStack item){
		GenericArmor armor = (GenericArmor) item.getItem();
		return armor.repairMatCount>0;
	}
	
	public ArrayList<ItemStack> getRepairMats(ItemStack item){
		ArrayList<ItemStack> mats = new ArrayList<ItemStack>();
		
		if (item.getItemDamage()>0){
			GenericArmor armor = (GenericArmor) item.getItem();
			
			float dmgpercent = (item.getItemDamage()*1.0f)/((item.getMaxDamage()-1)*1.0f);
				
			int count =  (int) Math.ceil(armor.repairMatCount*dmgpercent);
			
			int metalcount = (int) Math.ceil(count*armor.repairMatRatioMetal);
			int clothcount = count-metalcount;
			
			if(armor.repairMatMetal!=null && metalcount>0){
				mats.add(TGItems.newStack(armor.repairMatMetal, metalcount));
			}
			if(armor.repairMatCloth!=null && clothcount>0){
				mats.add(TGItems.newStack(armor.repairMatCloth, clothcount));
		}
		}
		return mats;
	}

	@Override
	public String getUnlocalizedName(ItemStack s) {
		return "techguns."+super.getUnlocalizedName(s);
	}

	@Override
	public ArmorProperties getProperties(EntityLivingBase player, ItemStack armor, DamageSource source, double damage, int slot) {
		TGDamageSource src = TGDamageSource.getFromGenericDamageSource(source);

		int absorbMax = armor.getMaxDamage()-1-armor.getItemDamage();
		
		ArmorProperties props = new ArmorProperties(0, 0, absorbMax);
		
		props.Armor = this.getArmorValue(armor, src.damageType);
		props.Toughness = Math.max(this.material.toughness - src.armorPenetration,0);
		
		//System.out.println("Armor"+props.Armor);
		//System.out.println("Toughness"+props.Toughness);
		
		return props;
	}

	@Override
	public int getArmorDisplay(EntityPlayer player, ItemStack armor, int slot) {
		if (armor.getItemDamage()>=armor.getMaxDamage()-1){
			return 0;
		} else {
			return Math.round(this.armorValue); 
		}
	}
	
	@SideOnly(Side.CLIENT)
    public void initModel() {
        ModelLoader.setCustomModelResourceLocation(this, 0, new ModelResourceLocation(getRegistryName(), "inventory"));
    }

	@Override
	public boolean shouldUseRenderHack(ItemStack stack) {
		return this.use3dRenderHack;
	}
	
}

