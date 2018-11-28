package techguns;

import java.util.ArrayList;

import net.minecraft.init.Items;
import net.minecraft.init.SoundEvents;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraftforge.registries.IForgeRegistry;
import techguns.init.ITGInitializer;
import techguns.items.armors.ArmorPowerType;
import techguns.items.armors.GenericArmor;
import techguns.items.armors.GenericArmorMultiCamo;
import techguns.items.armors.PoweredArmor;
import techguns.items.armors.TGArmorMaterial;
import techguns.tools.ItemJsonCreator;

public class TGArmors implements ITGInitializer {
	public static ArrayList<GenericArmor> armors = new ArrayList<>();
	
	public static final int ARMORMODEL_STEAM_ARMOR_0 = 0;
	public static final int ARMORMODEL_STEAM_ARMOR_1 = 1;
	public static final int ARMORMODEL_POWER_ARMOR_0 = 2;
	public static final int ARMORMODEL_POWER_ARMOR_1 = 3;
	public static final int ARMORMODEL_EXO_SUIT_0 = 4;
	public static final int ARMORMODEL_EXO_SUIT_1 = 5;
	public static final int ARMORMODEL_EXO_SUIT_2 = 6;
	public static final int ARMORMODEL_BERET_0 = 7;
	public static final int ARMORMODEL_COAT_0 = 8;
	public static final int ARMORMODEL_COAT_1 = 9;
	public static final int ARMORMODEL_COAT_2 = 10;
	public static final int ARMORMODEL_COAT_3 = 11;
	public static final int ARMORMODEL_STEAM_ARMOR_2 = 12;
	public static final int ARMORMODEL_POWER_ARMOR_2 = 13;
	
	
	public static TGArmorMaterial T1_COMBAT = new TGArmorMaterial("T1_COMBAT", 60, 0, 15.0f, SoundEvents.ITEM_ARMOR_EQUIP_LEATHER,0f);
	public static TGArmorMaterial T1_SCOUT = new TGArmorMaterial("T1_SCOUT", 60, 0, 13.0f,  SoundEvents.ITEM_ARMOR_EQUIP_LEATHER,0f);
	public static TGArmorMaterial T1_MINER = new TGArmorMaterial("T1_MINER", 60, 0, 13.0f,  SoundEvents.ITEM_ARMOR_EQUIP_LEATHER,0f);
	public static TGArmorMaterial T1_STEAM = new TGArmorMaterial("T1_STEAM", 200, 0, 18.0f,  SoundEvents.ITEM_ARMOR_EQUIP_IRON,3f).setArmorFire(17.0f).setArmorExplosion(17.0f).setArmorEnergy(16.0f).setArmorIce(15.0f).setArmorLightning(15.0f).setArmorPoison(10.0f).setArmorRadiation(6.0f).setPenetrationResistance(0.1f);
	
	public static TGArmorMaterial T2_HAZMAT = new TGArmorMaterial("T2_HAZMAT", 80, 0, 8.0f, SoundEvents.ITEM_ARMOR_EQUIP_LEATHER,0f).setArmorFire(14.0f).setArmorExplosion(12.0f).setArmorEnergy(14.0f).setArmorIce(14.0f).setArmorLightning(14.0f).setArmorPoison(20.0f).setArmorRadiation(20.0f);
	
	
	public static TGArmorMaterial T2_COMBAT = new TGArmorMaterial("T2_COMBAT", 72, 0, 18.0f, SoundEvents.ITEM_ARMOR_EQUIP_LEATHER,2f);
	public static TGArmorMaterial T2_COMMANDO = new TGArmorMaterial("T2_COMMANDO", 72, 0, 18.0f, SoundEvents.ITEM_ARMOR_EQUIP_LEATHER,1f).setArmorFire(15.0f).setArmorExplosion(15.0f).setArmorEnergy(15.0f).setArmorIce(15.0f).setArmorLightning(15.0f).setArmorPoison(10.0f).setArmorRadiation(5.0f);
	
	public static TGArmorMaterial T2_RIOT = new TGArmorMaterial("T2_RIOT", 96, 0, 18.5f, SoundEvents.ITEM_ARMOR_EQUIP_LEATHER,2.5f).setArmorFire(18.0f).setArmorExplosion(18.0f).setArmorEnergy(18.0f).setArmorIce(17.0f).setArmorLightning(17.0f).setArmorPoison(16.0f).setArmorRadiation(16.0f).setPenetrationResistance(0.1f);
	
	
	public static TGArmorMaterial T3_COMBAT = new TGArmorMaterial("T3_COMBAT", 240, 0, 19.0f, SoundEvents.ITEM_ARMOR_EQUIP_CHAIN,3.5f).setArmorFire(18.0f).setArmorExplosion(18.0f).setArmorEnergy(18.0f).setArmorIce(18.0f).setArmorLightning(18.0f).setArmorPoison(10.0f).setArmorRadiation(6.0f).setPenetrationResistance(0.15f);
	
	public static TGArmorMaterial T3_POWER = new TGArmorMaterial("T3_POWER", 360, 0, 20.0f, SoundEvents.ITEM_ARMOR_EQUIP_IRON,6f).setArmorFire(19.0f).setArmorExplosion(20.0f).setArmorEnergy(20.0f).setArmorIce(19.0f).setArmorLightning(19.0f).setArmorPoison(15.0f).setArmorRadiation(17.0f).setPenetrationResistance(0.25f);
	
	public static TGArmorMaterial T3_MINER = new TGArmorMaterial("T3_MINER",300,0,20.0f, SoundEvents.ITEM_ARMOR_EQUIP_IRON,2.5f).setArmorFire(20.0f).setArmorExplosion(20.0f).setArmorEnergy(20.0f).setArmorIce(20.0f).setArmorLightning(20.0f).setArmorPoison(20.0f).setArmorRadiation(20.0f).setPenetrationResistance(0.15f);
	
	//Same as T3_Combat
	public static TGArmorMaterial T3_EXO = new TGArmorMaterial("T3_EXO", 300, 0, 20.0f, SoundEvents.ITEM_ARMOR_EQUIP_CHAIN,3.5f).setArmorFire(18.5f).setArmorExplosion(18.5f).setArmorEnergy(18.5f).setArmorIce(18.5f).setArmorLightning(18.5f).setArmorPoison(10.0f).setArmorRadiation(8.0f).setPenetrationResistance(0.15f);
	
	public static TGArmorMaterial T2_BERET = new TGArmorMaterial("T2_BERET",60,0,8.0f, SoundEvents.ITEM_ARMOR_EQUIP_LEATHER,0);

	public static GenericArmor t1_combat_Helmet;
	public static GenericArmor t1_combat_Chestplate;
	public static GenericArmor t1_combat_Leggings;
	public static GenericArmor t1_combat_Boots;
	
	public static GenericArmor t1_scout_Helmet;
	public static GenericArmor t1_scout_Chestplate;
	public static GenericArmor t1_scout_Leggings;
	public static GenericArmor t1_scout_Boots;
	
	public static GenericArmor t1_miner_Helmet;
	public static GenericArmor t1_miner_Chestplate;
	public static GenericArmor t1_miner_Leggings;
	public static GenericArmor t1_miner_Boots;
	
	public static GenericArmor steam_Helmet;
	public static GenericArmor steam_Chestplate;
	public static GenericArmor steam_Leggings;
	public static GenericArmor steam_Boots;

	public static GenericArmor hazmat_Helmet;
	public static GenericArmor hazmat_Chestplate;
	public static GenericArmor hazmat_Leggings;
	public static GenericArmor hazmat_Boots;
	
	public static GenericArmor t2_combat_Helmet;
	public static GenericArmor t2_combat_Chestplate;
	public static GenericArmor t2_combat_Leggings;
	public static GenericArmor t2_combat_Boots;
	
	public static GenericArmor t2_commando_Helmet;
	public static GenericArmor t2_commando_Chestplate;
	public static GenericArmor t2_commando_Leggings;
	public static GenericArmor t2_commando_Boots;
	
	public static GenericArmor t2_riot_Helmet;
	public static GenericArmor t2_riot_Chestplate;
	public static GenericArmor t2_riot_Leggings;
	public static GenericArmor t2_riot_Boots;
	
	public static GenericArmor t3_combat_Helmet;
	public static GenericArmor t3_combat_Chestplate;
	public static GenericArmor t3_combat_Leggings;
	public static GenericArmor t3_combat_Boots;
	
	public static GenericArmor t3_power_Helmet;
	public static GenericArmor t3_power_Chestplate;
	public static GenericArmor t3_power_Leggings;
	public static GenericArmor t3_power_Boots;
	
	public static GenericArmor t3_miner_Helmet;
	public static GenericArmor t3_miner_Chestplate;
	public static GenericArmor t3_miner_Leggings;
	public static GenericArmor t3_miner_Boots;
	
	public static GenericArmor t3_exo_Helmet;
	public static GenericArmor t3_exo_Chestplate;
	public static GenericArmor t3_exo_Leggings;
	public static GenericArmor t3_exo_Boots;
	
	public static GenericArmor t2_beret;
	/**
	 * ARMORS
	 */

	@Override
	public void preInit(FMLPreInitializationEvent event) {
		t1_combat_Helmet = new GenericArmor("t1_combat_helmet", T1_COMBAT, "t1_combat",EntityEquipmentSlot.HEAD).setRepairMats(new ItemStack(Items.IRON_INGOT,1), TGItems.HEAVY_CLOTH, 0.5f, 2).setKnockbackResistance(0.05f); //0 for helmet
		t1_combat_Chestplate = new GenericArmor("t1_combat_chestplate", T1_COMBAT, "t1_combat", EntityEquipmentSlot.CHEST).setRepairMats(new ItemStack(Items.IRON_INGOT,1), TGItems.HEAVY_CLOTH, 0.5f, 4).setKnockbackResistance(0.20f); // 1 for chestplate
		t1_combat_Leggings = new GenericArmor("t1_combat_leggings", T1_COMBAT, "t1_combat", EntityEquipmentSlot.LEGS).setRepairMats(new ItemStack(Items.IRON_INGOT,1), TGItems.HEAVY_CLOTH, 1.0f/3.0f, 3).setKnockbackResistance(0.10f); // 2 for leggings
		t1_combat_Boots = new GenericArmor("t1_combat_boots", T1_COMBAT, "t1_combat", EntityEquipmentSlot.FEET).setRepairMats(new ItemStack(Items.IRON_INGOT,1), TGItems.HEAVY_CLOTH, 0.5f, 2).setKnockbackResistance(0.05f); // 3 for boots

		String[] t1_scout_textures = {"t1_scout", "t1_scout_forest","t1_scout_snow","t1_scout_black"};
		t1_scout_Helmet = new GenericArmorMultiCamo("t1_scout_helmet", T1_SCOUT, t1_scout_textures, EntityEquipmentSlot.HEAD).setSpeedBoni(0.125f,0.02f).setRepairMats(ItemStack.EMPTY, TGItems.HEAVY_CLOTH, 0f, 2); //0 for helmet
		t1_scout_Chestplate = new GenericArmorMultiCamo("t1_scout_chestplate", T1_SCOUT, t1_scout_textures, EntityEquipmentSlot.CHEST).setSpeedBoni(0.125f,0.02f).setRepairMats(ItemStack.EMPTY, TGItems.HEAVY_CLOTH, 0f, 4); // 1 for chestplate
		t1_scout_Leggings = new GenericArmorMultiCamo("t1_scout_leggings", T1_SCOUT, t1_scout_textures, EntityEquipmentSlot.LEGS).setSpeedBoni(0.125f,0.02f).setRepairMats(ItemStack.EMPTY, TGItems.HEAVY_CLOTH, 0f, 3); // 2 for leggings
		t1_scout_Boots = new GenericArmorMultiCamo("t1_scout_boots", T1_SCOUT, t1_scout_textures, EntityEquipmentSlot.FEET).setSpeedBoni(0.125f,0.1f).setFallProtection(0.2f, 1.0f).setRepairMats(ItemStack.EMPTY, TGItems.HEAVY_CLOTH, 0f, 2); // 3 for boots
	
		//T1 Bob
		String[] t1_miner_textures = {"t1_miner", "t1_miner_red","t1_miner_green","t1_miner_black"};
		t1_miner_Helmet = new GenericArmorMultiCamo("t1_miner_helmet", T1_MINER, t1_miner_textures, EntityEquipmentSlot.HEAD).setCamoNameSuffix("helmet").setSpeedBoni(0.08f,0.00f).setMiningBoni(0.05f).setRepairMats(new ItemStack(Items.IRON_INGOT,1), TGItems.HEAVY_CLOTH, 0.5f, 2); //0 for helmet
		t1_miner_Chestplate = new GenericArmorMultiCamo("t1_miner_chestplate", T1_MINER, t1_miner_textures, EntityEquipmentSlot.CHEST).setSpeedBoni(0.08f,0.0f).setMiningBoni(0.05f).setRepairMats(new ItemStack(Items.IRON_INGOT,1), TGItems.HEAVY_CLOTH, 0.5f, 4); // 1 for chestplate
		t1_miner_Leggings = new GenericArmorMultiCamo("t1_miner_leggings", T1_MINER, t1_miner_textures, EntityEquipmentSlot.LEGS).setSpeedBoni(0.08f,0.0f).setMiningBoni(0.05f).setRepairMats(new ItemStack(Items.IRON_INGOT,1), TGItems.HEAVY_CLOTH, 1.0f/3.0f, 2); // 2 for leggings
		t1_miner_Boots = new GenericArmorMultiCamo("t1_miner_boots", T1_MINER, t1_miner_textures, EntityEquipmentSlot.FEET).setSpeedBoni(0.08f,0.1f).setMiningBoni(0.05f).setFallProtection(0.2f, 1.0f).setRepairMats(new ItemStack(Items.IRON_INGOT,1), TGItems.HEAVY_CLOTH, 0.5f, 2); // 3 for boots
		
		String[] steam_armor_textures = {"steam_armor", "steam_armor_rusty", "steam_armor_iron", "steam_armor_hazard"};
		steam_Helmet = new PoweredArmor("steam_helmet", T1_STEAM, steam_armor_textures, EntityEquipmentSlot.HEAD,ArmorPowerType.STEAM,10).setMiningBoni(0.05f,0.0f).setHealthBonus(1,0).setSpeedBoni(0.05f,0.03f,0.0f,0.0f).setRepairMats(TGItems.STEAMARMOR_PLATE, TGItems.STEAMARMOR_PLATE, 1.0f, 2).setArmorModel(ARMORMODEL_STEAM_ARMOR_2,false).setHideFaceslot(true).setKnockbackResistance(0.15f).setUseRenderHack();
		steam_Chestplate = new PoweredArmor("steam_chestplate", T1_STEAM, steam_armor_textures, EntityEquipmentSlot.CHEST,ArmorPowerType.STEAM,3600).setSpeedBoni(0.05f,0.03f,0.0f,0.0f).setMiningBoni(0.05f,0.0f).setHealthBonus(2,0).setBattery(TGItems.COMPRESSED_AIR_TANK).setEmptyBattery(TGItems.COMPRESSED_AIR_TANK_EMPTY).setRepairMats(TGItems.STEAMARMOR_PLATE, TGItems.STEAMARMOR_PLATE, 1.0f, 4).setArmorModel(ARMORMODEL_STEAM_ARMOR_0,false).setHideBackslot(true).setHideGloveslot(true).setKnockbackResistance(0.30f).setUseRenderHack(); // 1 for chestplate
		steam_Leggings = new PoweredArmor("steam_leggings", T1_STEAM, steam_armor_textures, EntityEquipmentSlot.LEGS,ArmorPowerType.STEAM,10).setSpeedBoni(0.05f,0.03f,0f,0f).setMiningBoni(0.05f,0f).setHealthBonus(1,0).setRepairMats(TGItems.STEAMARMOR_PLATE, TGItems.STEAMARMOR_PLATE, 1.0f, 3).setArmorModel(ARMORMODEL_STEAM_ARMOR_1,false).setKnockbackResistance(0.20f).setUseRenderHack(); // 2 for leggings
		steam_Boots = new PoweredArmor("steam_boots", T1_STEAM, steam_armor_textures, EntityEquipmentSlot.FEET,ArmorPowerType.STEAM,10).setSpeedBoni(0.05f,0.15f,0,0).setMiningBoni(0.05f,0).setFallProtection(0.2f, 1.0f,0,0).setHealthBonus(1,0).setStepAssist(1.0f,0).setRepairMats(TGItems.STEAMARMOR_PLATE, TGItems.STEAMARMOR_PLATE, 1.0f, 2).setArmorModel(ARMORMODEL_STEAM_ARMOR_0,false).setKnockbackResistance(0.15f).setUseRenderHack(); // 3 for boots
	
		String[] hazmat_textures = {"hazmatsuit", "hazmatsuit_yellow"}; //FIXME: REPAIR MATS
		hazmat_Helmet = new GenericArmorMultiCamo("hazmat_helmet", T2_HAZMAT, hazmat_textures, EntityEquipmentSlot.HEAD).setSpeedBoni(0.0f,0.00f).setMiningBoni(0.0f).setRADResistance(1f).setRepairMats(new ItemStack(Items.IRON_INGOT,1), TGItems.HEAVY_CLOTH, 0.5f, 2); //0 for helmet
		hazmat_Chestplate = new GenericArmorMultiCamo("hazmat_chestplate", T2_HAZMAT, hazmat_textures, EntityEquipmentSlot.CHEST).setSpeedBoni(0.0f,0.0f).setMiningBoni(0.0f).setRADResistance(1f).setRepairMats(new ItemStack(Items.IRON_INGOT,1), TGItems.HEAVY_CLOTH, 0.5f, 4); // 1 for chestplate
		hazmat_Leggings = new GenericArmorMultiCamo("hazmat_leggings", T2_HAZMAT, hazmat_textures, EntityEquipmentSlot.LEGS).setSpeedBoni(0.0f,0.0f).setMiningBoni(0.0f).setRADResistance(1f).setRepairMats(new ItemStack(Items.IRON_INGOT,1), TGItems.HEAVY_CLOTH, 1.0f/3.0f, 2); // 2 for leggings
		hazmat_Boots = new GenericArmorMultiCamo("hazmat_boots", T2_HAZMAT, hazmat_textures, EntityEquipmentSlot.FEET).setSpeedBoni(0.0f,0.0f).setMiningBoni(0.0f).setRADResistance(1f).setFallProtection(0.1f, 0.5f).setRepairMats(new ItemStack(Items.IRON_INGOT,1), TGItems.HEAVY_CLOTH, 0.5f, 2); // 3 for boots
	
		//T2 Combat Armor
		String[] t2_combat_textures = {"t2_combat", "t2_combat_wood","t2_combat_desert","t2_combat_arctic","t2_combat_swat","t2_combat_security"};
		t2_combat_Helmet = new GenericArmorMultiCamo("t2_combat_helmet", T2_COMBAT, t2_combat_textures, EntityEquipmentSlot.HEAD).setSpeedBoni(0.1f,0.0f).setRepairMats(TGItems.INGOT_OBSIDIAN_STEEL, TGItems.HEAVY_CLOTH, 0.5f, 2).setKnockbackResistance(0.10f); //0 for helmet
		t2_combat_Chestplate = new GenericArmorMultiCamo("t2_combat_chestplate", T2_COMBAT, t2_combat_textures, EntityEquipmentSlot.CHEST).setSpeedBoni(0.1f, 0.0f).setRepairMats(TGItems.INGOT_OBSIDIAN_STEEL, TGItems.HEAVY_CLOTH, 0.5f, 4).setKnockbackResistance(0.25f); // 1 for chestplate
		t2_combat_Leggings = new GenericArmorMultiCamo("t2_combat_leggings", T2_COMBAT, t2_combat_textures, EntityEquipmentSlot.LEGS).setSpeedBoni(0.1f, 0.0f).setRepairMats(TGItems.INGOT_OBSIDIAN_STEEL, TGItems.HEAVY_CLOTH, 1.0f/3.0f, 3).setKnockbackResistance(0.15f); // 2 for leggings
		t2_combat_Boots = new GenericArmorMultiCamo("t2_combat_boots", T2_COMBAT, t2_combat_textures, EntityEquipmentSlot.FEET).setSpeedBoni(0.1f, 0.1f).setRepairMats(TGItems.INGOT_OBSIDIAN_STEEL, TGItems.HEAVY_CLOTH, 0.5f, 2).setKnockbackResistance(0.10f); // 3 for boots
		
		//T2 Commando Armor
		t2_commando_Helmet = new GenericArmor("t2_commando_helmet", T2_COMMANDO, "t2_commando", EntityEquipmentSlot.HEAD).setSpeedBoni(0.10f,0.f).setRepairMats(TGItems.INGOT_OBSIDIAN_STEEL, TGItems.RUBBER_BAR, 0.5f, 2).setMiningBoniWater(1.25f).setGunBonus(0.05f).setKnockbackResistance(0.05f).setOxygenGear(1.0f); //0 for helmet
		t2_commando_Chestplate = new GenericArmor("t2_commando_chestplate", T2_COMMANDO, "t2_commando", EntityEquipmentSlot.CHEST).setSpeedBoni(0.10f,0.f).setRepairMats(TGItems.INGOT_OBSIDIAN_STEEL, TGItems.RUBBER_BAR, 0.5f, 4).setMiningBoniWater(1.25f).setGunBonus(0.05f).setKnockbackResistance(0.20f); // 1 for chestplate
		t2_commando_Leggings = new GenericArmor("t2_commando_leggings", T2_COMMANDO, "t2_commando", EntityEquipmentSlot.LEGS).setSpeedBoni(0.10f,0.f).setRepairMats(TGItems.INGOT_OBSIDIAN_STEEL, TGItems.RUBBER_BAR, 1.0f/3.0f, 3).setMiningBoniWater(1.25f).setGunBonus(0.05f).setKnockbackResistance(0.10f); // 2 for leggings
		t2_commando_Boots = new GenericArmor("t2_commando_boots", T2_COMMANDO, "t2_commando", EntityEquipmentSlot.FEET).setSpeedBoni(0.10f,0.1f).setRepairMats(TGItems.INGOT_OBSIDIAN_STEEL, TGItems.RUBBER_BAR, 0.5f, 2).setMiningBoniWater(1.25f).setGunBonus(0.05f).setKnockbackResistance(0.05f).setFallProtection(0.2f, 1.0f); // 3 for boots
		
		//T2.5 Riot Gear Armor
		String[] t2_riot_textures = {"t2_riot"};
		t2_riot_Helmet = new GenericArmorMultiCamo("t2_riot_helmet", T2_RIOT, t2_riot_textures, EntityEquipmentSlot.HEAD).setSpeedBoni(0.12f,0.f).setRepairMats(TGItems.INGOT_OBSIDIAN_STEEL, TGItems.TREATED_LEATHER, 0.5f, 2).setKnockbackResistance(0.125f).setArmorModel(TGArmors.ARMORMODEL_COAT_0, true); //0 for helmet
		t2_riot_Chestplate = new GenericArmorMultiCamo("t2_riot_chestplate", T2_RIOT, t2_riot_textures, EntityEquipmentSlot.CHEST).setSpeedBoni(0.12f, 0.f).setRepairMats(TGItems.INGOT_OBSIDIAN_STEEL, TGItems.TREATED_LEATHER, 0.5f, 4).setKnockbackResistance(0.275f).setArmorModel(TGArmors.ARMORMODEL_COAT_1, true); // 1 for chestplate
		t2_riot_Leggings = new GenericArmorMultiCamo("t2_riot_leggings", T2_RIOT, t2_riot_textures, EntityEquipmentSlot.LEGS).setSpeedBoni(0.13f, 0.f).setRepairMats(TGItems.INGOT_OBSIDIAN_STEEL, TGItems.TREATED_LEATHER, 1.0f/3.0f, 3).setKnockbackResistance(0.175f).setArmorModel(TGArmors.ARMORMODEL_COAT_2, true); // 2 for leggings
		t2_riot_Boots = new GenericArmorMultiCamo("t2_riot_boots", T2_RIOT, t2_riot_textures, EntityEquipmentSlot.FEET).setSpeedBoni(0.13f, 0.15f).setRepairMats(TGItems.INGOT_OBSIDIAN_STEEL, TGItems.TREATED_LEATHER, 0.5f, 2).setKnockbackResistance(0.125f).setArmorModel(TGArmors.ARMORMODEL_COAT_3,true); // 3 for boots
		
		//T3 Combat Armor
		String[] t3_combat_textures = {"t3_combat", "t3_combat_silver", "t3_combat_green"};
		t3_combat_Helmet = new GenericArmorMultiCamo("t3_combat_helmet", T3_COMBAT, t3_combat_textures, EntityEquipmentSlot.HEAD).setSpeedBoni(0.1f,0.0f).setRepairMats(TGItems.PLATE_CARBON, TGItems.CARBON_FIBERS, 0.5f, 2).setKnockbackResistance(0.15f); //0 for helmet
		t3_combat_Chestplate = new GenericArmorMultiCamo("t3_combat_chestplate", T3_COMBAT, t3_combat_textures, EntityEquipmentSlot.CHEST).setSpeedBoni(0.1f, 0.0f).setRepairMats(TGItems.PLATE_CARBON, TGItems.CARBON_FIBERS, 0.5f, 4).setKnockbackResistance(0.30f); // 1 for chestplate
		t3_combat_Leggings = new GenericArmorMultiCamo("t3_combat_leggings", T3_COMBAT, t3_combat_textures, EntityEquipmentSlot.LEGS).setSpeedBoni(0.1f, 0.0f).setRepairMats(TGItems.PLATE_CARBON, TGItems.CARBON_FIBERS, 1.0f/3.0f, 3).setKnockbackResistance(0.20f); // 2 for leggings
		t3_combat_Boots = new GenericArmorMultiCamo("t3_combat_boots", T3_COMBAT, t3_combat_textures, EntityEquipmentSlot.FEET).setSpeedBoni(0.1f, 0.1f).setRepairMats(TGItems.PLATE_CARBON, TGItems.CARBON_FIBERS, 0.5f, 2).setKnockbackResistance(0.15f); // 3 for boots
				
		//T3 power armor
		String[] t3_power_armor_textures = {"powerarmor", "powerarmor_dark", "powerarmor_dark2"};
		t3_power_Helmet = new PoweredArmor("t3_power_helmet", T3_POWER, t3_power_armor_textures, EntityEquipmentSlot.HEAD,ArmorPowerType.RF,10).setMiningBoni(0.05f,0.0f).setHealthBonus(1,0).setSpeedBoni(0.05f,0.03f,0.0f,0.0f).setRepairMats(TGItems.POWER_ARMOR_PLATING, TGItems.WIRE_GOLD, 0.5f, 2).setArmorModel(TGArmors.ARMORMODEL_POWER_ARMOR_2,false).setHideFaceslot(true).setKnockbackResistance(0.20f).setUseRenderHack(); //0 for helmet
		t3_power_Chestplate = new PoweredArmor("t3_power_chestplate", T3_POWER, t3_power_armor_textures, EntityEquipmentSlot.CHEST,ArmorPowerType.RF,3600).setSpeedBoni(0.05f,0.03f,0.0f,0.0f).setMiningBoni(0.05f,0.0f).setHealthBonus(2,0).setBattery(TGItems.ENERGY_CELL).setEmptyBattery(TGItems.ENERGY_CELL_EMPTY).setRepairMats(TGItems.POWER_ARMOR_PLATING, TGItems.WIRE_GOLD, 0.5f, 4).setArmorModel(TGArmors.ARMORMODEL_POWER_ARMOR_0,false).setHideBackslot(false).setHideGloveslot(true).setKnockbackResistance(0.35f).setUseRenderHack(); // 1 for chestplate
		t3_power_Leggings = new PoweredArmor("t3_power_leggings", T3_POWER, t3_power_armor_textures, EntityEquipmentSlot.LEGS,ArmorPowerType.RF,10).setSpeedBoni(0.05f,0.03f,0f,0f).setMiningBoni(0.05f,0f).setHealthBonus(1,0).setRepairMats(TGItems.POWER_ARMOR_PLATING, TGItems.WIRE_GOLD, 1.0f/3.0f, 3).setArmorModel(TGArmors.ARMORMODEL_POWER_ARMOR_1,false).setKnockbackResistance(0.25f).setUseRenderHack(); // 2 for leggings
		t3_power_Boots = new PoweredArmor("t3_power_boots", T3_POWER, t3_power_armor_textures, EntityEquipmentSlot.FEET,ArmorPowerType.RF,10).setSpeedBoni(0.05f,0.15f,0,0).setMiningBoni(0.05f,0).setFallProtection(0.2f, 1.0f,0,0).setHealthBonus(1,0).setStepAssist(1.0f,0).setRepairMats(TGItems.POWER_ARMOR_PLATING, TGItems.WIRE_GOLD, 0.5f, 2).setArmorModel(TGArmors.ARMORMODEL_POWER_ARMOR_0,false).setKnockbackResistance(0.20f).setUseRenderHack(); // 3 for boots
		
		//T3 Mining Armor
		String[] t3_miner_textures = {"hevsuit", "hevsuit_silver", "hevsuit_black"};
		t3_miner_Helmet = new PoweredArmor("t3_miner_helmet", T3_MINER, t3_miner_textures, EntityEquipmentSlot.HEAD,ArmorPowerType.RF,10).setSpeedBoni(0.1f,0.3f,0,0).setWaterspeedBonus(1.25f, 0).setMiningBoni(0.1f,0).setMiningBoniWater(1.25f,0).setOxygenGear(1.0f,0.0f).setHideFaceslot(true).setRepairMats(TGItems.PLATE_CARBON, TGItems.CIRCUIT_BOARD_ELITE, 0.5f, 2).setKnockbackResistance(0.10f); //0 for helmet
		t3_miner_Chestplate = new PoweredArmor("t3_miner_chestplate", T3_MINER, t3_miner_textures, EntityEquipmentSlot.CHEST,ArmorPowerType.RF,10000).setBattery(TGItems.ENERGY_CELL).setEmptyBattery(TGItems.ENERGY_CELL_EMPTY).setSpeedBoni(0.1f, 0.03f,0,0).setWaterspeedBonus(1.25f, 0).setMiningBoni(0.1f,0).setMiningBoniWater(1.25f,0).setCoolingSystem(1.0f,0).setRepairMats(TGItems.PLATE_CARBON, TGItems.CIRCUIT_BOARD_ELITE, 0.5f, 4).setHideGloveslot(true).setKnockbackResistance(0.25f); // 1 for chestplate
		t3_miner_Leggings = new PoweredArmor("t3_miner_leggings", T3_MINER, t3_miner_textures, EntityEquipmentSlot.LEGS,ArmorPowerType.RF,10).setSpeedBoni(0.1f, 0.03f,0,0).setWaterspeedBonus(1.25f, 0).setMiningBoni(0.1f,0).setMiningBoniWater(1.25f,0).setRepairMats(TGItems.PLATE_CARBON, TGItems.CIRCUIT_BOARD_ELITE, 1.0f/3.0f, 3).setKnockbackResistance(0.15f); // 2 for leggings
		t3_miner_Boots = new PoweredArmor("t3_miner_boots", T3_MINER, t3_miner_textures, EntityEquipmentSlot.FEET,ArmorPowerType.RF,10).setSpeedBoni(0.1f, 0.15f,0,0).setWaterspeedBonus(1.25f, 0).setMiningBoni(0.1f,0).setMiningBoniWater(1.25f,0).setStepAssist(1.0f,0).setFallProtection(0.5f, 3.0f,0,0).setRepairMats(TGItems.PLATE_CARBON, TGItems.CIRCUIT_BOARD_ELITE, 0.5f, 2).setKnockbackResistance(0.10f); // 3 for boots
		
		String[] exo_armor_textures = {"t3_exo","t3_exo_silver","t3_exo_green"};
		t3_exo_Helmet = new PoweredArmor("t3_exo_helmet", T3_EXO, exo_armor_textures, EntityEquipmentSlot.HEAD,ArmorPowerType.RF,10).setSpeedBoni(0.2f,0.1f,0.0f,0.0f).setMiningBoni(0.075f,0.0f).setRepairMats(TGItems.PLATE_CARBON, TGItems.CARBON_FIBERS, 0.5f, 2).setArmorModel(TGArmors.ARMORMODEL_EXO_SUIT_0,true).setHideFaceslot(false).setKnockbackResistance(0.15f); //0 for helmet
		t3_exo_Chestplate = new PoweredArmor("t3_exo_chestplate", T3_EXO, exo_armor_textures, EntityEquipmentSlot.CHEST,ArmorPowerType.RF,7200).setSpeedBoni(0.2f,0.1f,0.0f,0.0f).setMiningBoni(0.075f,0.0f).setBattery(TGItems.ENERGY_CELL).setEmptyBattery(TGItems.ENERGY_CELL_EMPTY).setRepairMats(TGItems.PLATE_CARBON, TGItems.CARBON_FIBERS, 0.5f, 4).setArmorModel(TGArmors.ARMORMODEL_EXO_SUIT_2,true).setHideBackslot(false).setKnockbackResistance(0.30f); // 1 for chestplate
		t3_exo_Leggings = new PoweredArmor("t3_exo_leggings", T3_EXO, exo_armor_textures, EntityEquipmentSlot.LEGS,ArmorPowerType.RF,10).setSpeedBoni(0.2f,0.1f,0f,0f).setMiningBoni(0.075f,0f).setRepairMats(TGItems.PLATE_CARBON, TGItems.CARBON_FIBERS, 1.0f/3.0f, 3).setArmorModel(TGArmors.ARMORMODEL_EXO_SUIT_1,true).setKnockbackResistance(0.20f); // 2 for leggings
		t3_exo_Boots = new PoweredArmor("t3_exo_boots", T3_EXO, exo_armor_textures, EntityEquipmentSlot.FEET,ArmorPowerType.RF,10).setSpeedBoni(0.2f,0.3f,0,0).setMiningBoni(0.075f,0).setFallProtection(0.5f, 5.0f,0,0).setStepAssist(1.0f,0).setRepairMats(TGItems.PLATE_CARBON, TGItems.CARBON_FIBERS, 0.5f, 2).setArmorModel(TGArmors.ARMORMODEL_EXO_SUIT_2,true).setKnockbackResistance(0.15f); // 3 for boots
		
		String[] t2_beret_textures = {"beret_texture", "beret_texture_black", "beret_texture_green"};
		t2_beret = new GenericArmorMultiCamo("t2_beret", T2_BERET, t2_beret_textures, EntityEquipmentSlot.HEAD).setSpeedBoni(0.1f,0.0f).setRepairMats(TGItems.HEAVY_CLOTH, TGItems.HEAVY_CLOTH, 1.0f, 2).setArmorModel(TGArmors.ARMORMODEL_BERET_0,false); //0 for helmet
		
		
		if(TGItems.WRITE_ITEM_JSON && event.getSide()==Side.CLIENT){
			armors.forEach(a -> ItemJsonCreator.writeItemJsonFileForPath("models/item/", a.getRegistryName().getResourcePath(),a.getRegistryName().getResourcePath()));
		}

	}
	
	public static void registerItems(RegistryEvent.Register<Item> event){
		IForgeRegistry<Item> reg = event.getRegistry();
		armors.forEach(a -> reg.register(a));
	}
	
	@SideOnly(Side.CLIENT)
    public static void initModels() {
		armors.forEach(a -> a.initModel());
    }
	
	@Override
	public void init(FMLInitializationEvent event) {
	}
	@Override
	public void postInit(FMLPostInitializationEvent event) {

	}
	
}
