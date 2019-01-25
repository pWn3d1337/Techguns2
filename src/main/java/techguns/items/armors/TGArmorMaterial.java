package techguns.items.armors;

import java.util.ArrayList;

import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemArmor.ArmorMaterial;
import net.minecraft.util.SoundEvent;
import net.minecraftforge.common.util.EnumHelper;
import techguns.Techguns;
import techguns.api.damagesystem.DamageType;

public class TGArmorMaterial {
	
	public static ArrayList<TGArmorMaterial> MATERIALS = new ArrayList<>();
	
	public String name;
	
	/**
	 * The sum of default durability weight values
	 */
	private static final int VANILLA_DURABILITY_FACTOR_SUM=55;
	
	protected float armorPhys=0;
	protected float armorProjectile=0;
	protected float armorExplosion=0;
	protected float armorEnergy=0;
	protected float armorFire=0;
	protected float armorLightning=0;
	protected float armorIce=0;
	protected float armorDark=0;
	protected float armorPoison=0;
	protected float armorRadiation=0;
	
	protected float penetrationResistance=0.0f;
	
	protected float factorHead=0.25f;
	protected float factorBoots=0.20f;
	protected float factorLegs=0.25f;
	protected float factorChest=0.30f;
	
	protected float durHead=0.25f;
	protected float durBoots=0.25f;
	protected float durLegs=0.25f;
	protected float durChest=0.25f;
	
	protected float toughness;
	
	protected SoundEvent equipSound;
	
	protected int baseDur;
	protected int enchantability;
	
	protected String texture;
	
	protected String modid;
	
	
	/**
	 * Don't use this constructor outside of techguns, use the one with modid as 1st parameter
	 * 
	 * Set the armor values with standard factors, phys and proj are equal to armor, rad/poison are 0, all others are 0.75*armor;
	 * @param armor
	 * @return
	 */
	public TGArmorMaterial(String name, int baseDurability, int enchantability, float armor, SoundEvent equipSound, float toughness){
		this(Techguns.MODID,name,baseDurability,enchantability,armor,equipSound,toughness);
	}
	
	/**
	 * Set the armor values with standard factors, phys and proj are equal to armor, rad/poison are 0, all others are 0.75*armor;
	 * @param armor
	 * @return
	 */
	public TGArmorMaterial(String modid, String name, int baseDurability, int enchantability, float armor, SoundEvent equipSound, float toughness){
		this.name=name;
		this.baseDur=baseDurability;
		this.enchantability=enchantability;
		float f=0.75f;
		this.armorPhys=armor;
		this.armorProjectile=armor;
		this.armorFire=armor*f;
		this.armorExplosion=armor*f;
		this.armorEnergy=armor*f;
		this.armorIce=armor*f;
		this.armorLightning=armor*f;
		this.armorDark=armor*f;
		this.armorPoison=armor*f;
		this.armorRadiation=0.0f;
		this.toughness=toughness;
		this.equipSound=equipSound;
		this.texture=name.toLowerCase();
		this.modid=modid;
		
		MATERIALS.add(this);
	}
	
	/**
	 * Set all 'Elemental' or 'Magic' values: fire, lightning, ice and energy
	 * @param value
	 * @return
	 */
	public TGArmorMaterial setArmorElemental(float value) {
		this.armorEnergy=value;
		this.armorFire=value;
		this.armorIce=value;
		this.armorLightning=value;
		return this;
	}
	
	
	public TGArmorMaterial setPartArmorSplit(float helm, float chest, float legs, float boots){
		this.factorHead=helm;
		this.factorChest=chest;
		this.factorLegs=legs;
		this.factorBoots=boots;
		return this;
	}
	
	
	public TGArmorMaterial setPartDurabilitySplit(float helm, float chest, float legs, float boots){
		this.durHead=helm;
		this.durChest=chest;
		this.durLegs=legs;
		this.durBoots=boots;
		return this;
	}
	
	@Deprecated
	public TGArmorMaterial setPenetrationResistance(float penresist){
		this.penetrationResistance = penresist;
		return this;
	}
	
	public float getArmorValueTotal(DamageType damageType){
		switch(damageType){
			case ENERGY:
				return this.armorEnergy;
			case EXPLOSION:
				return this.armorExplosion;
			case FIRE:
				return this.armorFire;
			case ICE:
				return this.armorIce;
			case LIGHTNING:
				return this.armorLightning;
			case PHYSICAL:
				return this.armorPhys;
			case POISON:
				return this.armorPoison;
			case PROJECTILE:
				return this.armorProjectile;
			case RADIATION:
				return this.armorRadiation;
			case DARK:
				return this.armorDark;
			case UNRESISTABLE:
			default:
				return 0;
		}
	}
	
	public float getArmorValueSlot(EntityEquipmentSlot slot, DamageType type){
		float materialArmor = this.getArmorValueTotal(type);
		switch(slot){
			case HEAD: //helmet:
				return this.factorHead*materialArmor;
			case CHEST: //chest
				return this.factorChest*materialArmor;
			case LEGS: //legs
				return this.factorLegs*materialArmor;
			case FEET: //boots
				return this.factorBoots*materialArmor;
			default:
				return 0;
		}
	}
	
	public float getArmor(ItemArmor armor, DamageType type){
		return this.getArmorValueSlot(armor.armorType, type);
	}

	/**
	 * gets a vanilla armor material from this
	 * @return
	 */
	public ArmorMaterial createVanillaMaterial(){
		/*int[] av =new int[4];
		av[3]=Math.round(this.getArmorValueSlot(EntityEquipmentSlot.HEAD, DamageType.PHYSICAL));
		av[2]=Math.round(this.getArmorValueSlot(EntityEquipmentSlot.CHEST, DamageType.PHYSICAL));
		av[1]=Math.round(this.getArmorValueSlot(EntityEquipmentSlot.LEGS, DamageType.PHYSICAL));
		av[0]=Math.round(this.getArmorValueSlot(EntityEquipmentSlot.FEET, DamageType.PHYSICAL));*/
		
		return EnumHelper.addArmorMaterial(name,this.modid+":"+this.texture, baseDur,new int[]{0,0,0,0}, enchantability, equipSound, toughness);
	}
	
	public TGArmorMaterial setArmorProjectile(float armorProjectile) {
		this.armorProjectile = armorProjectile;
		return this;
	}

	public TGArmorMaterial setArmorExplosion(float armorExplosion) {
		this.armorExplosion = armorExplosion;
		return this;
	}

	public TGArmorMaterial setArmorEnergy(float armorEnergy) {
		this.armorEnergy = armorEnergy;
		return this;
	}

	public TGArmorMaterial setArmorFire(float armorFire) {
		this.armorFire = armorFire;
		return this;
	}

	public TGArmorMaterial setArmorLightning(float armorLightning) {
		this.armorLightning = armorLightning;
		return this;
	}

	public TGArmorMaterial setArmorIce(float armorIce) {
		this.armorIce = armorIce;
		return this;
	}

	public TGArmorMaterial setArmorPoison(float armorPoison) {
		this.armorPoison = armorPoison;
		return this;
	}

	public TGArmorMaterial setArmorRadiation(float armorRadiation) {
		this.armorRadiation = armorRadiation;
		return this;
	}

	public TGArmorMaterial setArmorPhys(float armorPhys) {
		this.armorPhys = armorPhys;
		return this;
	}
	
	public int getDurability(EntityEquipmentSlot type){
		switch(type){
			case HEAD: //helmet:
				return Math.round(this.durHead*VANILLA_DURABILITY_FACTOR_SUM*this.baseDur);
			case CHEST: //chest
				return Math.round(this.durChest*VANILLA_DURABILITY_FACTOR_SUM*this.baseDur);
			case LEGS: //legs
				return Math.round(this.durLegs*VANILLA_DURABILITY_FACTOR_SUM*this.baseDur);
			case FEET: //boots
				return Math.round(this.durBoots*VANILLA_DURABILITY_FACTOR_SUM*this.baseDur);
			default:
				return 0;
		}
	}

	@Deprecated 
	/**
	 * No longer used, replaced by toughness
	 * @return
	 */
	public float getPenetrationResistance() {
		return penetrationResistance;
	}
	
	public void setArmorValueForType(DamageType type, float value) {
		switch (type) {
		case DARK:
			this.armorDark=value;
			break;
		case ENERGY:
			this.armorEnergy=value;
			break;
		case EXPLOSION:
			this.armorExplosion=value;
			break;
		case FIRE:
			this.armorFire=value;
			break;
		case ICE:
			this.armorIce=value;
			break;
		case LIGHTNING:
			this.armorLightning=value;
			break;
		case PHYSICAL:
			this.armorPhys=value;
			break;
		case POISON:
			this.armorPoison=value;
			break;
		case PROJECTILE:
			this.armorProjectile=value;
			break;
		case RADIATION:
			this.armorRadiation=value;
			break;
		case UNRESISTABLE:
			break;
		default:
			break;
		
		}
	}
}	