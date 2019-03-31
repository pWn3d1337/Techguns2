package techguns;

import java.util.ArrayList;

import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraft.item.Item.ToolMaterial;
import net.minecraft.item.ItemStack;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.common.util.EnumHelper;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraftforge.oredict.OreDictionary;
import net.minecraftforge.registries.IForgeRegistry;
import techguns.api.tginventory.TGSlotType;
import techguns.blocks.EnumDoorType;
import techguns.init.ITGInitializer;
import techguns.items.BuildingScanTool;
import techguns.items.GenericItem;
import techguns.items.GenericItemShared;
import techguns.items.GenericItemShared.SharedItemEntry;
import techguns.items.ItemRadAway;
import techguns.items.ItemRadpills;
import techguns.items.ItemTGDoor2x1;
import techguns.items.ItemTGDoor3x3;
import techguns.items.WorldGenTestTool;
import techguns.items.additionalslots.ItemAntiGravPack;
import techguns.items.additionalslots.ItemGasMask;
import techguns.items.additionalslots.ItemGlider;
import techguns.items.additionalslots.ItemJetpack;
import techguns.items.additionalslots.ItemJumpPack;
import techguns.items.additionalslots.ItemNightVisionGoggles;
import techguns.items.additionalslots.ItemScubaTanks;
import techguns.items.additionalslots.ItemTacticalMask;
import techguns.items.armors.GenericShield;
import techguns.items.guns.ammo.AmmoType;
import techguns.items.guns.ammo.AmmoTypes;
import techguns.items.tools.TGCrowbar;
import techguns.items.tools.TGSword;
import techguns.recipes.Recipewriter;
import techguns.tools.ItemJsonCreator;

public class TGItems implements ITGInitializer{

	/**
	 * FIXME DISABLE THIS BEFORE RELEASE
	 */
	public static boolean WRITE_ITEM_JSON = false;
	/**
	 * FIXME DISABLE THIS BEFORE RELEASE
	 */
	public static boolean WRITE_RECIPES = false;
	
	
	public static final ArrayList<GenericItem> ITEMLIST = new ArrayList<>();
	
	public static final ArrayList<Item> ITEMLIST_VANILLA_ITEM = new ArrayList<>();
		
	public static GenericItemShared SHARED_ITEM;
	
	public static ItemStack STONE_BULLETS;
	public static ItemStack PISTOL_ROUNDS;
	public static ItemStack SHOTGUN_ROUNDS;
	public static ItemStack RIFLE_ROUNDS;
	public static ItemStack RIFLE_ROUNDS_STACK;
	public static ItemStack SNIPER_ROUNDS;
	public static ItemStack GRENADE_40MM;
	public static ItemStack ADVANCED_ROUNDS;
	public static ItemStack ROCKET;
	
	public static ItemStack SMG_MAGAZINE;
	public static ItemStack SMG_MAGAZINE_EMPTY;
	public static ItemStack PISTOL_MAGAZINE;
	public static ItemStack PISTOL_MAGAZINE_EMPTY;
	public static ItemStack ASSAULTRIFLE_MAGAZINE;
	public static ItemStack ASSAULTRIFLE_MAGAZINE_EMPTY;
	public static ItemStack MINIGUN_DRUM;
	public static ItemStack MINIGUN_DRUM_EMPTY;
	public static ItemStack LMG_MAGAZINE;
	public static ItemStack LMG_MAGAZINE_EMPTY;
	public static ItemStack AS50_MAGAZINE;
	public static ItemStack AS50_MAGAZINE_EMPTY;
	
	public static ItemStack ADVANCED_MAGAZINE;
	public static ItemStack ADVANCED_MAGAZINE_EMPTY;
	
	public static ItemStack COMPRESSED_AIR_TANK;
	public static ItemStack COMPRESSED_AIR_TANK_EMPTY;
	
	public static ItemStack BIO_TANK;
	public static ItemStack BIO_TANK_EMPTY;
	
	public static ItemStack FUEL_TANK;
	public static ItemStack FUEL_TANK_EMPTY;
	
	public static ItemStack ENERGY_CELL;
	public static ItemStack ENERGY_CELL_EMPTY;
	
	public static ItemStack NUCLEAR_POWERCELL;
	public static ItemStack NUCLEAR_POWERCELL_EMPTY;
	
	/**
	 * MATERIALS
	 */
	public static ItemStack HEAVY_CLOTH;
	public static ItemStack PROTECTIVE_FIBER;
	
	public static ItemStack BIOMASS;
	
	/**
	 * GUN PARTS
	 */
	public static ItemStack RECEIVER_IRON;
	public static ItemStack RECEIVER_STEEL;
	public static ItemStack RECEIVER_OBSIDIAN_STEEL;
	public static ItemStack RECEIVER_CARBON;
	
	public static ItemStack BARREL_STONE;
	public static ItemStack BARREL_IRON;
	//public static ItemStack BARREL_STEEL;
	public static ItemStack BARREL_OBSIDIAN_STEEL;
	public static ItemStack BARREL_CARBON;
	public static ItemStack BARREL_LASER;
	
	public static ItemStack STOCK_WOOD;
	public static ItemStack STOCK_PLASTIC;
	public static ItemStack STOCK_CARBON;
	
	/**
	 * Metals
	 */
	public static ItemStack STEAMARMOR_PLATE;
	public static ItemStack PLATE_IRON;
	public static ItemStack PLATE_COPPER;
	public static ItemStack PLATE_TIN;
	public static ItemStack PLATE_BRONZE;
	public static ItemStack PLATE_STEEL;
	public static ItemStack PLATE_OBSIDIAN_STEEL;
	public static ItemStack PLATE_LEAD;
	public static ItemStack PLATE_TITANIUM;
	public static ItemStack PLATE_CARBON;
	
	public static ItemStack PLASTIC_SHEET;
	public static ItemStack RUBBER_BAR;
	
	public static ItemStack MECHANICAL_PARTS_IRON;
	public static ItemStack MECHANICAL_PARTS_OBSIDIAN_STEEL;
	public static ItemStack MECHANICAL_PARTS_CARBON;
	
	public static ItemStack WIRE_COPPER;
	public static ItemStack WIRE_GOLD;
	public static ItemStack CARBON_FIBERS;
	
	public static ItemStack CIRCUIT_BOARD_BASIC;
	public static ItemStack CIRCUIT_BOARD_ELITE;
	public static ItemStack POWER_ARMOR_PLATING;
	
	public static ItemStack COIL;
	public static ItemStack CYBERNETIC_PARTS;
	public static ItemStack ELECTRIC_ENGINE;
	
	public static ItemStack LASER_FOCUS;
	public static ItemStack PUMP_MECHANISM;
	public static ItemStack RAD_EMITTER;
	public static ItemStack SONIC_EMITTER;
	
	public static ItemStack TGX;
	public static ItemStack NETHER_CHARGE;
	public static ItemStack TREATED_LEATHER;
	public static ItemStack ORE_TITANIUM;
	
	public static ItemStack RAW_PLASTIC;
	public static ItemStack RAW_RUBBER;
	public static ItemStack YELLOWCAKE;
	public static ItemStack ENRICHED_URANIUM;
	
	public static ItemStack INGOT_COPPER;
	public static ItemStack INGOT_TIN;
	public static ItemStack INGOT_BRONZE;
	public static ItemStack INGOT_STEEL;
	public static ItemStack INGOT_OBSIDIAN_STEEL;
	public static ItemStack INGOT_LEAD;
	public static ItemStack INGOT_TITANIUM;
	
	public static ItemStack NUGGET_COPPER;
	public static ItemStack NUGGET_LEAD;
	public static ItemStack NUGGET_STEEL;
	
	//
	public static ItemStack GAS_MASK_FILTER;
	public static ItemStack GLIDER_BACKBACK;
	public static ItemStack GLIDER_WING;
	public static ItemStack ANTI_GRAV_CORE;
	
	public static ItemStack OXYGEN_MASK;
	
	public static ItemStack MACHINE_UPGRADE_STACK;
	
	public static ItemStack REDSTONE_BATTERY;
	public static ItemStack REDSTONE_BATTERY_EMPTY;
	
	public static ItemStack GAUSSRIFLE_SLUGS;
	public static ItemStack BARREL_GAUSS;
	
	//Ammo Variants
	public static ItemStack SHOTGUN_ROUNDS_INCENDIARY;
	public static ItemStack AS50_MAGAZINE_INCENDIARY;
	public static ItemStack SNIPER_ROUNDS_INCENDIARY;
	public static ItemStack PISTOL_ROUNDS_INCENDIARY;
	public static ItemStack RIFLE_ROUNDS_INCENDIARY;
	public static ItemStack RIFLE_ROUNDS_STACK_INCENDIARY;
	public static ItemStack PISTOL_MAGAZINE_INCENDIARY;
	public static ItemStack MINIGUN_DRUM_INCENDIARY;
	public static ItemStack SMG_MAGAZINE_INCENDIARY;
	public static ItemStack ASSAULTRIFLE_MAGAZINE_INCENDIARY;
	public static ItemStack LMG_MAGAZINE_INCENDIARY;
	
	public static ItemStack ROCKET_NUKE;
	public static ItemStack TACTICAL_NUKE_WARHEAD;
	
	public static ItemStack BARREL_TITANIUM;
	public static ItemStack RECEIVER_TITANIUM;
	public static ItemStack PLASMA_GENERATOR;
	
	public static ItemStack SNIPER_ROUNDS_EXPLOSIVE;
	public static ItemStack AS50_MAGAZINE_EXPLOSIVE;
	
	public static ItemStack ROCKET_HIGH_VELOCITY;
	
	/**
	 * ADDITONAL SLOT ITEMS
	 */
	public static ItemGasMask GAS_MASK;
	public static ItemGlider GLIDER;
	
	public static ItemNightVisionGoggles NIGHTVISION_GOGGLES;
	public static ItemJumpPack JUMPPACK;
	public static ItemJetpack JETPACK;
	public static ItemScubaTanks SCUBA_TANKS;
	public static ItemTacticalMask TACTICAL_MASK;
	public static ItemAntiGravPack ANTI_GRAV_PACK;
	
	public static ItemStack WORKING_GLOVES;
	
	public static ItemStack TURRET_ARMOR_IRON;
	public static ItemStack TURRET_ARMOR_STEEL;
	public static ItemStack TURRET_ARMOR_OBSIDIAN_STEEL;
	public static ItemStack TURRET_ARMOR_CARBON;
	
	public static ItemStack QUARTZ_ROD;
	public static ItemStack RC_HEAT_RAY;
	public static ItemStack RC_UV_EMITTER;
	
	public static ItemStack MININGDRILLHEAD_OBSIDIAN;
	public static ItemStack MININGDRILLHEAD_CARBON;
	
	public static ItemStack POWERHAMMERHEAD_OBSIDIAN;
	public static ItemStack POWERHAMMERHEAD_CARBON;
	
	public static ItemStack CHAINSAWBLADES_OBSIDIAN;
	public static ItemStack CHAINSAWBLADES_CARBON;
	
	public static ItemStack INFUSION_BAG;
	
	//Tools/weapons
	public static Item COMBAT_KNIFE;
	public static Item CROWBAR;
	
	public static Item WORLDGEN_TEST_TOOL;
	public static Item BUIDLING_SCAN_TOOL;
	
	public static ItemTGDoor2x1 BUNKER_DOOR;
	
	//rad stuff
	public static ItemRadAway RAD_AWAY;
	public static ItemRadpills RAD_PILLS;
	
	//DrillHeads
	public static ItemStack OREDRILLHEAD_STEEL;
	public static ItemStack OREDRILLHEAD_OBSIDIANSTEEL;
	public static ItemStack OREDRILLHEAD_CARBON;

	public static ItemStack OREDRILLHEAD_MEDIUM_STEEL;
	public static ItemStack OREDRILLHEAD_MEDIUM_OBSIDIANSTEEL;
	public static ItemStack OREDRILLHEAD_MEDIUM_CARBON;
	
	public static ItemStack OREDRILLHEAD_LARGE_STEEL;
	public static ItemStack OREDRILLHEAD_LARGE_OBSIDIANSTEEL;
	public static ItemStack OREDRILLHEAD_LARGE_CARBON;
	
	//armor upgrades
	public static ItemStack UPGRADE_PROTECTION_1;
	public static ItemStack UPGRADE_BLAST_PROTECTION_1;
	public static ItemStack UPGRADE_PROJECTILE_PROTECTION_1;
	
	public static ItemStack UPGRADE_PROTECTION_2;
	public static ItemStack UPGRADE_BLAST_PROTECTION_2;
	public static ItemStack UPGRADE_PROJECTILE_PROTECTION_2;
	
	public static ItemStack UPGRADE_PROTECTION_3;
	public static ItemStack UPGRADE_BLAST_PROTECTION_3;
	public static ItemStack UPGRADE_PROJECTILE_PROTECTION_3;
	
	//TOOL Materials
	static ToolMaterial TG_STEEL = EnumHelper.addToolMaterial("Steel", 2, 1000, 7.5f, 2.5f, 12);
		
	//DOORS
	public static ItemTGDoor3x3<EnumDoorType> DOOR3x3;
	
	@Override
	public void preInit(FMLPreInitializationEvent event) {
		SHARED_ITEM = new GenericItemShared();
		
		STONE_BULLETS = SHARED_ITEM.addsharedVariant("stonebullets",TGSlotType.AMMOSLOT); 
		PISTOL_ROUNDS = SHARED_ITEM.addsharedVariant("pistolrounds",TGSlotType.AMMOSLOT);
		SHOTGUN_ROUNDS = SHARED_ITEM.addsharedVariant("shotgunrounds",TGSlotType.AMMOSLOT);
		RIFLE_ROUNDS = SHARED_ITEM.addsharedVariant("riflerounds",TGSlotType.AMMOSLOT);
		SNIPER_ROUNDS = SHARED_ITEM.addsharedVariant("sniperrounds",TGSlotType.AMMOSLOT);
		GRENADE_40MM = SHARED_ITEM.addsharedVariant("40mmgrenade",TGSlotType.AMMOSLOT);
		ADVANCED_ROUNDS = SHARED_ITEM.addsharedVariant("advancedrounds");
		ROCKET = SHARED_ITEM.addsharedVariant("rocket", true, TGSlotType.AMMOSLOT);
		RIFLE_ROUNDS_STACK = SHARED_ITEM.addsharedVariant("rifleroundsstack",TGSlotType.AMMOSLOT);
		
		SMG_MAGAZINE = SHARED_ITEM.addsharedVariant("smgmagazine",TGSlotType.AMMOSLOT);
		SMG_MAGAZINE_EMPTY = SHARED_ITEM.addsharedVariant("smgmagazineempty",TGSlotType.AMMOSLOT);
		PISTOL_MAGAZINE = SHARED_ITEM.addsharedVariant("pistolmagazine",TGSlotType.AMMOSLOT);
		PISTOL_MAGAZINE_EMPTY = SHARED_ITEM.addsharedVariant("pistolmagazineempty",TGSlotType.AMMOSLOT);
		ASSAULTRIFLE_MAGAZINE = SHARED_ITEM.addsharedVariant("assaultriflemagazine",true, TGSlotType.AMMOSLOT);
		ASSAULTRIFLE_MAGAZINE_EMPTY = SHARED_ITEM.addsharedVariant("assaultriflemagazineempty",true, TGSlotType.AMMOSLOT);
		LMG_MAGAZINE = SHARED_ITEM.addsharedVariant("lmgmagazine",true, TGSlotType.AMMOSLOT);
		LMG_MAGAZINE_EMPTY = SHARED_ITEM.addsharedVariant("lmgmagazineempty",true, TGSlotType.AMMOSLOT);
		MINIGUN_DRUM = SHARED_ITEM.addsharedVariant("minigundrum", TGSlotType.AMMOSLOT);
		MINIGUN_DRUM_EMPTY = SHARED_ITEM.addsharedVariant("minigundrumempty", TGSlotType.AMMOSLOT);
		AS50_MAGAZINE = SHARED_ITEM.addsharedVariant("as50magazine", true, TGSlotType.AMMOSLOT);
		AS50_MAGAZINE_EMPTY = SHARED_ITEM.addsharedVariant("as50magazineempty",true, TGSlotType.AMMOSLOT);
		
		ADVANCED_MAGAZINE = SHARED_ITEM.addsharedVariant("advancedmagazine",TGSlotType.AMMOSLOT);
		ADVANCED_MAGAZINE_EMPTY = SHARED_ITEM.addsharedVariant("advancedmagazineempty",TGSlotType.AMMOSLOT);
		
		COMPRESSED_AIR_TANK = SHARED_ITEM.addsharedVariant("compressedairtank", TGSlotType.AMMOSLOT);
		COMPRESSED_AIR_TANK_EMPTY = SHARED_ITEM.addsharedVariant("compressedairtankempty", TGSlotType.AMMOSLOT);
		
		BIO_TANK = SHARED_ITEM.addsharedVariant("biotank", TGSlotType.AMMOSLOT);
		BIO_TANK_EMPTY = SHARED_ITEM.addsharedVariant("biotankempty", TGSlotType.AMMOSLOT);
		
		FUEL_TANK = SHARED_ITEM.addsharedVariant("fueltank", TGSlotType.AMMOSLOT);
		FUEL_TANK_EMPTY = SHARED_ITEM.addsharedVariant("fueltankempty", TGSlotType.AMMOSLOT);
		
		ENERGY_CELL = SHARED_ITEM.addsharedVariant("energycell", TGSlotType.AMMOSLOT);
		ENERGY_CELL_EMPTY = SHARED_ITEM.addsharedVariant("energycellempty", TGSlotType.AMMOSLOT);
		
		NUCLEAR_POWERCELL = SHARED_ITEM.addsharedVariant("nuclearpowercell", TGSlotType.AMMOSLOT);
		NUCLEAR_POWERCELL_EMPTY = SHARED_ITEM.addsharedVariant("nuclearpowercelldepleted", TGSlotType.AMMOSLOT);
		
		
		/**
		 * GUNPARTS
		 */
		RECEIVER_IRON = SHARED_ITEM.addsharedVariant("ironreceiver");
		RECEIVER_STEEL = SHARED_ITEM.addsharedVariant("steelreceiver");
		RECEIVER_OBSIDIAN_STEEL = SHARED_ITEM.addsharedVariant("obsidiansteelreceiver");
		RECEIVER_CARBON = SHARED_ITEM.addsharedVariant("carbonreceiver");
		
		BARREL_STONE = SHARED_ITEM.addsharedVariant("stonebarrel");
		BARREL_IRON = SHARED_ITEM.addsharedVariant("ironbarrel");
		//BARREL_STEEL = SHARED_ITEM.addsharedVariant("steelbarrel");
		BARREL_OBSIDIAN_STEEL = SHARED_ITEM.addsharedVariant("obsidiansteelbarrel");
		BARREL_CARBON = SHARED_ITEM.addsharedVariant("carbonbarrel");
		BARREL_LASER = SHARED_ITEM.addsharedVariant("laserbarrel");
		
		STOCK_WOOD = SHARED_ITEM.addsharedVariant("woodstock");
		STOCK_PLASTIC = SHARED_ITEM.addsharedVariant("plasticstock");
		STOCK_CARBON = SHARED_ITEM.addsharedVariant("carbonstock");

		STEAMARMOR_PLATE = SHARED_ITEM.addsharedVariant("steamarmorplate");
		PLATE_IRON =  SHARED_ITEM.addsharedVariant("plateiron");
		PLATE_COPPER =  SHARED_ITEM.addsharedVariant("platecopper");
		PLATE_TIN =  SHARED_ITEM.addsharedVariant("platetin");
		PLATE_BRONZE =  SHARED_ITEM.addsharedVariant("platebronze");
		PLATE_STEEL =  SHARED_ITEM.addsharedVariant("platesteel");
		PLATE_OBSIDIAN_STEEL=  SHARED_ITEM.addsharedVariant("plateobsidiansteel");
		PLATE_LEAD =  SHARED_ITEM.addsharedVariant("platelead");
		PLATE_CARBON =  SHARED_ITEM.addsharedVariant("platecarbon");
		PLATE_TITANIUM =  SHARED_ITEM.addsharedVariant("platetitanium");
		
		PLASTIC_SHEET =  SHARED_ITEM.addsharedVariant("plasticsheet");
		RUBBER_BAR =  SHARED_ITEM.addsharedVariant("rubberbar");
		
		MECHANICAL_PARTS_IRON = SHARED_ITEM.addsharedVariant("mechanicalpartsiron");
		MECHANICAL_PARTS_OBSIDIAN_STEEL = SHARED_ITEM.addsharedVariant("mechanicalpartsobsidiansteel");
		MECHANICAL_PARTS_CARBON = SHARED_ITEM.addsharedVariant("mechanicalpartscarbon");
		
		HEAVY_CLOTH = SHARED_ITEM.addsharedVariant("heavycloth");
		BIOMASS = SHARED_ITEM.addsharedVariant("biomass");
		
		WIRE_COPPER = SHARED_ITEM.addsharedVariant("copperwire"); 
		WIRE_GOLD = SHARED_ITEM.addsharedVariant("goldwire"); 
		CARBON_FIBERS = SHARED_ITEM.addsharedVariant("carbonfibers");
		
		CIRCUIT_BOARD_BASIC = SHARED_ITEM.addsharedVariant("circuitboard");
		CIRCUIT_BOARD_ELITE = SHARED_ITEM.addsharedVariant("circuitboardelite");
		POWER_ARMOR_PLATING = SHARED_ITEM.addsharedVariant("powerplating");
		
		COIL = SHARED_ITEM.addsharedVariant("coil");
		CYBERNETIC_PARTS = SHARED_ITEM.addsharedVariant("cyberneticparts");
		ELECTRIC_ENGINE = SHARED_ITEM.addsharedVariant("electricengine");
		
		LASER_FOCUS = SHARED_ITEM.addsharedVariant("laserfocus");
		PUMP_MECHANISM = SHARED_ITEM.addsharedVariant("pumpmechanism");
		RAD_EMITTER = SHARED_ITEM.addsharedVariant("rademitter");
		SONIC_EMITTER = SHARED_ITEM.addsharedVariant("sonicemitter");
		
		TGX = SHARED_ITEM.addsharedVariant("tgx");
		NETHER_CHARGE = SHARED_ITEM.addsharedVariant("nethercharge", TGSlotType.AMMOSLOT);
		TREATED_LEATHER = SHARED_ITEM.addsharedVariant("treatedleather");
		ORE_TITANIUM = SHARED_ITEM.addsharedVariant("oretitanium");
		
		INGOT_COPPER = SHARED_ITEM.addsharedVariantOptional("ingotcopper",TGConfig.addCopperIngots);
		INGOT_TIN = SHARED_ITEM.addsharedVariantOptional("ingottin",TGConfig.addTinIngots);
		INGOT_BRONZE = SHARED_ITEM.addsharedVariantOptional("ingotbronze",TGConfig.addBronzeIngots);
		INGOT_LEAD = SHARED_ITEM.addsharedVariantOptional("ingotlead",TGConfig.addLeadIngots);
		INGOT_STEEL = SHARED_ITEM.addsharedVariantOptional("ingotsteel",TGConfig.addSteelIngots);
		INGOT_OBSIDIAN_STEEL = SHARED_ITEM.addsharedVariant("ingotobsidiansteel");
		INGOT_TITANIUM = SHARED_ITEM.addsharedVariant("ingottitanium");
		
		NUGGET_COPPER = SHARED_ITEM.addsharedVariantOptional("nuggetcopper",TGConfig.addCopperNuggets);
		NUGGET_LEAD = SHARED_ITEM.addsharedVariantOptional("nuggetlead", TGConfig.addLeadNuggets);
		NUGGET_STEEL = SHARED_ITEM.addsharedVariantOptional("nuggetsteel", TGConfig.addSteelNuggets);
		
		GAS_MASK_FILTER = SHARED_ITEM.addsharedVariant("gasmaskfilter");
		GLIDER_BACKBACK = SHARED_ITEM.addsharedVariant("gliderbackpack");
		GLIDER_WING = SHARED_ITEM.addsharedVariant("gliderwing");
		ANTI_GRAV_CORE = SHARED_ITEM.addsharedVariant("antigravcore");
		
		OXYGEN_MASK = SHARED_ITEM.addsharedVariant("oxygenmask", false, TGSlotType.FACESLOT, 1, true);
		
		MACHINE_UPGRADE_STACK = SHARED_ITEM.addsharedVariant("machinestackupgrade", false, TGSlotType.NORMAL, 7, true);
		
		RAW_RUBBER = SHARED_ITEM.addsharedVariant("rawrubber");
		RAW_PLASTIC = SHARED_ITEM.addsharedVariant("rawplastic");
		YELLOWCAKE = SHARED_ITEM.addsharedVariant("yellowcake");
		ENRICHED_URANIUM = SHARED_ITEM.addsharedVariant("enricheduranium");
		
		TURRET_ARMOR_IRON = SHARED_ITEM.addsharedVariant("turretarmoriron",TGSlotType.TURRETARMOR);
		TURRET_ARMOR_STEEL = SHARED_ITEM.addsharedVariant("turretarmorsteel",TGSlotType.TURRETARMOR);
		TURRET_ARMOR_OBSIDIAN_STEEL = SHARED_ITEM.addsharedVariant("turretarmorobsidiansteel",TGSlotType.TURRETARMOR);
		TURRET_ARMOR_CARBON = SHARED_ITEM.addsharedVariant("turretarmorcarbon",TGSlotType.TURRETARMOR);
		
		QUARTZ_ROD = SHARED_ITEM.addsharedVariant("quartzrod");
		RC_HEAT_RAY = SHARED_ITEM.addsharedVariant("rcheatray",false,TGSlotType.REACTION_CHAMBER_FOCUS,1,true);
		RC_UV_EMITTER = SHARED_ITEM.addsharedVariant("rcuvemitter",false,TGSlotType.REACTION_CHAMBER_FOCUS,1,true);
		
		SHOTGUN_ROUNDS_INCENDIARY = SHARED_ITEM.addsharedVariant("shotgunrounds_incendiary", TGSlotType.AMMOSLOT);
		AS50_MAGAZINE_INCENDIARY = SHARED_ITEM.addsharedVariant("as50magazine_incendiary", true, TGSlotType.AMMOSLOT);
		SNIPER_ROUNDS_INCENDIARY = SHARED_ITEM.addsharedVariant("sniperrounds_incendiary",TGSlotType.AMMOSLOT);
		
		PISTOL_ROUNDS_INCENDIARY = SHARED_ITEM.addsharedVariant("pistolrounds_incendiary",TGSlotType.AMMOSLOT);
		RIFLE_ROUNDS_INCENDIARY = SHARED_ITEM.addsharedVariant("riflerounds_incendiary",TGSlotType.AMMOSLOT);
		RIFLE_ROUNDS_STACK_INCENDIARY = SHARED_ITEM.addsharedVariant("rifleroundsstack_incendiary",TGSlotType.AMMOSLOT);
		
		SMG_MAGAZINE_INCENDIARY = SHARED_ITEM.addsharedVariant("smgmagazine_incendiary",TGSlotType.AMMOSLOT);
		PISTOL_MAGAZINE_INCENDIARY = SHARED_ITEM.addsharedVariant("pistolmagazine_incendiary",TGSlotType.AMMOSLOT);
		MINIGUN_DRUM_INCENDIARY = SHARED_ITEM.addsharedVariant("minigundrum_incendiary", TGSlotType.AMMOSLOT);
		ASSAULTRIFLE_MAGAZINE_INCENDIARY = SHARED_ITEM.addsharedVariant("assaultriflemagazine_incendiary",true, TGSlotType.AMMOSLOT);
		LMG_MAGAZINE_INCENDIARY = SHARED_ITEM.addsharedVariant("lmgmagazine_incendiary",true, TGSlotType.AMMOSLOT);
		
		ROCKET_NUKE = SHARED_ITEM.addsharedVariant("rocket_nuke", true, TGSlotType.AMMOSLOT);
		TACTICAL_NUKE_WARHEAD = SHARED_ITEM.addsharedVariant("tacticalnukewarhead");
		
		MININGDRILLHEAD_OBSIDIAN = SHARED_ITEM.addsharedVariant("miningdrillhead_obsidian");
		MININGDRILLHEAD_CARBON = SHARED_ITEM.addsharedVariant("miningdrillhead_carbon");
		
		POWERHAMMERHEAD_OBSIDIAN = SHARED_ITEM.addsharedVariant("powerhammerhead_obsidian");
		POWERHAMMERHEAD_CARBON = SHARED_ITEM.addsharedVariant("powerhammerhead_carbon");
		
		CHAINSAWBLADES_OBSIDIAN = SHARED_ITEM.addsharedVariant("chainsawblades_obsidian");
		CHAINSAWBLADES_CARBON = SHARED_ITEM.addsharedVariant("chainsawblades_carbon");
		
		REDSTONE_BATTERY = SHARED_ITEM.addsharedVariant("redstone_battery", TGSlotType.AMMOSLOT);
		REDSTONE_BATTERY_EMPTY = SHARED_ITEM.addsharedVariant("redstone_battery_empty", TGSlotType.AMMOSLOT);
		
		GAUSSRIFLE_SLUGS = SHARED_ITEM.addsharedVariant("gaussrifleslugs", TGSlotType.AMMOSLOT);
		
		BARREL_GAUSS = SHARED_ITEM.addsharedVariant("gaussbarrel");
		
		BARREL_TITANIUM =  SHARED_ITEM.addsharedVariant("shieldedtitaniumbarrel");
		RECEIVER_TITANIUM =  SHARED_ITEM.addsharedVariant("titaniumreceiver");
		PLASMA_GENERATOR =  SHARED_ITEM.addsharedVariant("plasmagenerator");
		
		WORKING_GLOVES = SHARED_ITEM.addsharedVariant("workinggloves", false, TGSlotType.HANDSLOT, 1, true);
		PROTECTIVE_FIBER = SHARED_ITEM.addsharedVariant("protectivefiber");
		
		OREDRILLHEAD_STEEL = SHARED_ITEM.addsharedVariant("oredrillsmall_steel", false, TGSlotType.DRILL_SMALL, 1, true);
		OREDRILLHEAD_OBSIDIANSTEEL = SHARED_ITEM.addsharedVariant("oredrillsmall_obsidiansteel", false, TGSlotType.DRILL_SMALL, 1, true);
		OREDRILLHEAD_CARBON = SHARED_ITEM.addsharedVariant("oredrillsmall_carbon", false, TGSlotType.DRILL_SMALL, 1, true);
		
		OREDRILLHEAD_MEDIUM_STEEL = SHARED_ITEM.addsharedVariant("oredrillmedium_steel", false, TGSlotType.DRILL_MEDIUM, 1, true);
		OREDRILLHEAD_MEDIUM_OBSIDIANSTEEL = SHARED_ITEM.addsharedVariant("oredrillmedium_obsidiansteel", false, TGSlotType.DRILL_MEDIUM, 1, true);
		OREDRILLHEAD_MEDIUM_CARBON = SHARED_ITEM.addsharedVariant("oredrillmedium_carbon", false, TGSlotType.DRILL_MEDIUM, 1, true);

		OREDRILLHEAD_LARGE_STEEL = SHARED_ITEM.addsharedVariant("oredrilllarge_steel", false, TGSlotType.DRILL_LARGE, 1, true);
		OREDRILLHEAD_LARGE_OBSIDIANSTEEL = SHARED_ITEM.addsharedVariant("oredrilllarge_obsidiansteel", false, TGSlotType.DRILL_LARGE, 1, true);
		OREDRILLHEAD_LARGE_CARBON = SHARED_ITEM.addsharedVariant("oredrilllarge_carbon", false, TGSlotType.DRILL_LARGE, 1, true);
		
		AS50_MAGAZINE_EXPLOSIVE = SHARED_ITEM.addsharedVariant("as50magazine_explosive", true, TGSlotType.AMMOSLOT);
		SNIPER_ROUNDS_EXPLOSIVE = SHARED_ITEM.addsharedVariant("sniperrounds_explosive",TGSlotType.AMMOSLOT);
		
		ROCKET_HIGH_VELOCITY = SHARED_ITEM.addsharedVariant("rocket_high_velocity",true,TGSlotType.AMMOSLOT);
		
		INFUSION_BAG = SHARED_ITEM.addsharedVariant("infusionbag");
		
		UPGRADE_PROTECTION_1 = SHARED_ITEM.addsharedVariant("upgrade_protection_1", false, TGSlotType.ARMOR_UPGRADE, 1, true);
		UPGRADE_PROJECTILE_PROTECTION_1 = SHARED_ITEM.addsharedVariant("upgrade_projectile_protection_1", false, TGSlotType.ARMOR_UPGRADE, 1, true);
		UPGRADE_BLAST_PROTECTION_1 = SHARED_ITEM.addsharedVariant("upgrade_blast_protection_1", false, TGSlotType.ARMOR_UPGRADE, 1, true);
		
		UPGRADE_PROTECTION_2 = SHARED_ITEM.addsharedVariant("upgrade_protection_2", false, TGSlotType.ARMOR_UPGRADE, 1, true);
		UPGRADE_PROJECTILE_PROTECTION_2 = SHARED_ITEM.addsharedVariant("upgrade_projectile_protection_2", false, TGSlotType.ARMOR_UPGRADE, 1, true);
		UPGRADE_BLAST_PROTECTION_2 = SHARED_ITEM.addsharedVariant("upgrade_blast_protection_2", false, TGSlotType.ARMOR_UPGRADE, 1, true);
		
		UPGRADE_PROTECTION_3 = SHARED_ITEM.addsharedVariant("upgrade_protection_3", false, TGSlotType.ARMOR_UPGRADE, 1, true);
		UPGRADE_PROJECTILE_PROTECTION_3 = SHARED_ITEM.addsharedVariant("upgrade_projectile_protection_3", false, TGSlotType.ARMOR_UPGRADE, 1, true);
		UPGRADE_BLAST_PROTECTION_3 = SHARED_ITEM.addsharedVariant("upgrade_blast_protection_3", false, TGSlotType.ARMOR_UPGRADE, 1, true);
		
		RAD_AWAY = new ItemRadAway("radaway");
		RAD_PILLS = new ItemRadpills("radpills");
		
		/**
		 * Additional Slot items
		 */
		GAS_MASK=new ItemGasMask("gasmask",1, 300).setRadresist(1f);
		GLIDER = new ItemGlider("glider",1,-1);
		JUMPPACK = new ItemJumpPack("jumppack",4, 1000);
		SCUBA_TANKS = new ItemScubaTanks("scubatanks", 1, 600);
		NIGHTVISION_GOGGLES = new ItemNightVisionGoggles("nightvisiongoggles", 1, 24000);
		JETPACK = new ItemJetpack("jetpack", 4, 5000);
		TACTICAL_MASK = new ItemTacticalMask("tacticalmask", 4, 24000);	
		ANTI_GRAV_PACK = new ItemAntiGravPack("antigravpack", 5, 12000);
		
		DOOR3x3 = new ItemTGDoor3x3<EnumDoorType>("item_door3x3",EnumDoorType.class);
		
		COMBAT_KNIFE = new TGSword(TG_STEEL, "combatknife");
		CROWBAR = new TGCrowbar(TG_STEEL, "crowbar");
		
		if(TGConfig.debug) {
			WORLDGEN_TEST_TOOL = new WorldGenTestTool("worldgentesttool");
			BUIDLING_SCAN_TOOL = new BuildingScanTool("buildingscantool");
		}
		
		BUNKER_DOOR = new ItemTGDoor2x1("item_bunkerdoor");
		
		ITEMLIST_VANILLA_ITEM.add(COMBAT_KNIFE);
		ITEMLIST_VANILLA_ITEM.add(CROWBAR);
		
		if(WRITE_ITEM_JSON && event.getSide()==Side.CLIENT){
			ItemJsonCreator.writeJsonFilesForSharedItem(SHARED_ITEM);
			for(GenericItem item : ITEMLIST){
				ItemJsonCreator.writeJsonFilesForGenericItem(item);
			}
			for(Item item : ITEMLIST_VANILLA_ITEM){
				ItemJsonCreator.writeJsonFilesForGenericItem(item);
			}
		}
	}
	
	public static void registerItems(RegistryEvent.Register<Item> event){
		IForgeRegistry<Item> reg = event.getRegistry();
		
		reg.register(SHARED_ITEM);
		
		for(GenericItem item : ITEMLIST){
			reg.register(item);
		}
		
		for(Item item : ITEMLIST_VANILLA_ITEM){
			reg.register(item);
		}
		registerItemsToOreDict();
	}

	@SideOnly(Side.CLIENT)
    public static void initModels() {
		SHARED_ITEM.initModel();
		for(GenericItem item : ITEMLIST){
			item.initModel();
		}
		ITEMLIST_VANILLA_ITEM.forEach(i -> {
			 ModelLoader.setCustomModelResourceLocation(i, 0, new ModelResourceLocation(i.getRegistryName(), "inventory"));
		});
    }
	
	@Override
	public void init(FMLInitializationEvent event) {
		
		//set ammoTypeEntries for Magazines (right click action)
		

		//bullets
		//	default
		setAmmoTypeEntryForMagazine(PISTOL_MAGAZINE, AmmoTypes.PISTOL_MAGAZINE, AmmoTypes.TYPE_DEFAULT);
		setAmmoTypeEntryForMagazine(ASSAULTRIFLE_MAGAZINE, AmmoTypes.ASSAULT_RIFLE_MAGAZINE, AmmoTypes.TYPE_DEFAULT);
		setAmmoTypeEntryForMagazine(AS50_MAGAZINE, AmmoTypes.AS50_MAGAZINE, AmmoTypes.TYPE_DEFAULT);
		setAmmoTypeEntryForMagazine(SMG_MAGAZINE, AmmoTypes.SMG_MAGAZINE, AmmoTypes.TYPE_DEFAULT);
		setAmmoTypeEntryForMagazine(LMG_MAGAZINE, AmmoTypes.LMG_MAGAZINE, AmmoTypes.TYPE_DEFAULT);
		setAmmoTypeEntryForMagazine(MINIGUN_DRUM, AmmoTypes.MINIGUN_AMMO_DRUM, AmmoTypes.TYPE_DEFAULT);
		
		//incendiary
		setAmmoTypeEntryForMagazine(PISTOL_MAGAZINE_INCENDIARY, AmmoTypes.PISTOL_MAGAZINE, AmmoTypes.TYPE_INCENDIARY);
		setAmmoTypeEntryForMagazine(ASSAULTRIFLE_MAGAZINE_INCENDIARY, AmmoTypes.ASSAULT_RIFLE_MAGAZINE, AmmoTypes.TYPE_INCENDIARY);
		setAmmoTypeEntryForMagazine(AS50_MAGAZINE_INCENDIARY, AmmoTypes.AS50_MAGAZINE, AmmoTypes.TYPE_INCENDIARY);
		setAmmoTypeEntryForMagazine(SMG_MAGAZINE_INCENDIARY, AmmoTypes.SMG_MAGAZINE, AmmoTypes.TYPE_INCENDIARY);
		setAmmoTypeEntryForMagazine(LMG_MAGAZINE_INCENDIARY, AmmoTypes.LMG_MAGAZINE, AmmoTypes.TYPE_INCENDIARY);
		setAmmoTypeEntryForMagazine(MINIGUN_DRUM_INCENDIARY, AmmoTypes.MINIGUN_AMMO_DRUM, AmmoTypes.TYPE_INCENDIARY);
		
		//explosive
		setAmmoTypeEntryForMagazine(AS50_MAGAZINE_EXPLOSIVE, AmmoTypes.AS50_MAGAZINE, AmmoTypes.TYPE_EXPLOSIVE);
		
		//advanced rounds
		setAmmoTypeEntryForMagazine(ADVANCED_MAGAZINE, AmmoTypes.ADVANCED_MAGAZINE, AmmoTypes.TYPE_DEFAULT);
		
	}

	public static void setAmmoTypeEntryForMagazine(ItemStack fullMag, AmmoType ammoType, String variant) {
		SharedItemEntry entry = SHARED_ITEM.get(fullMag.getItemDamage());
		entry.setAmmoType(ammoType.getBullet(ammoType.getIDforVariantKey(variant))[0], ammoType.getEmptyMag()[0], ammoType.getBulletsPerMag());
	}
	
	@Override
	public void postInit(FMLPostInitializationEvent event) {
		if(WRITE_RECIPES) {
			Recipewriter.writeRecipes();
		}
	}
	
	
	public static ItemStack newStack(ItemStack stack, int size) {
		ItemStack ret = stack.copy();
		ret.setCount(size);
		return ret;
	}

	public static void registerItemsToOreDict() {
		 registerIfEnabled("ingotObsidianSteel", INGOT_OBSIDIAN_STEEL); 
		 
		 registerIfEnabled("plateIron", PLATE_IRON);
		 registerIfEnabled("plateTin", PLATE_TIN);
		 registerIfEnabled("plateCopper", PLATE_COPPER);
		 registerIfEnabled("plateBronze", PLATE_BRONZE);
		 registerIfEnabled("plateSteel", PLATE_STEEL);
		 registerIfEnabled("plateLead", PLATE_LEAD);
		 registerIfEnabled("plateCarbon", PLATE_CARBON);
		 registerIfEnabled("fiberCarbon", CARBON_FIBERS);
		 registerIfEnabled("plateObsidianSteel", PLATE_OBSIDIAN_STEEL);
		 registerIfEnabled("circuitBasic", CIRCUIT_BOARD_BASIC);
		 registerIfEnabled("wireCopper", WIRE_COPPER);
		 registerIfEnabled("itemRubber", RUBBER_BAR);
		 registerIfEnabled("wireGold", WIRE_GOLD);
		 registerIfEnabled("circuitElite", CIRCUIT_BOARD_ELITE);
		 registerIfEnabled("ingotTitanium", INGOT_TITANIUM);
		 registerIfEnabled("oreTitanium", ORE_TITANIUM);
		 registerIfEnabled("plateTitanium", PLATE_TITANIUM);
		 registerIfEnabled("sheetPlastic", PLASTIC_SHEET);
		 registerIfEnabled("dustUranium", TGItems.YELLOWCAKE);
		 registerIfEnabled("ingotUraniumEnriched", TGItems.ENRICHED_URANIUM);
		 
		 OreDictionary.registerOre("ingotCopper", INGOT_COPPER);
		 OreDictionary.registerOre("ingotBronze", INGOT_BRONZE);
		 OreDictionary.registerOre("ingotLead", INGOT_LEAD);
		 OreDictionary.registerOre("ingotTin", INGOT_TIN);
		 OreDictionary.registerOre("ingotSteel", INGOT_STEEL);
		 
		 OreDictionary.registerOre("nuggetCopper", NUGGET_COPPER);
		 OreDictionary.registerOre("nuggetLead", NUGGET_LEAD);
		 OreDictionary.registerOre("nuggetSteel", NUGGET_STEEL);
		 
		 registerIfEnabled("itemRawRubber", RAW_RUBBER);
		 registerIfEnabled("rawPlastic", RAW_PLASTIC);
	}
	public static void registerIfEnabled(String oreName, ItemStack item) {
		if (SHARED_ITEM.get(item.getItemDamage()).isEnabled()) {
			OreDictionary.registerOre(oreName, item);
		}
		
	}
	public static boolean isMachineUpgrade(ItemStack stack) {
		return !stack.isEmpty() && stack.getItem()==SHARED_ITEM && stack.getItemDamage()==MACHINE_UPGRADE_STACK.getItemDamage();
	}
}
