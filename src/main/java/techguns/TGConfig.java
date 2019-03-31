package techguns;

import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.fml.client.event.ConfigChangedEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

@Mod.EventBusSubscriber(modid = Techguns.MODID)
public class TGConfig {
	public static Configuration config;
	
	public static boolean debug;
	
	public static boolean addCopperIngots;
	public static boolean addLeadNuggets;
	public static boolean addCopperNuggets;
	public static boolean addBronzeIngots;
	public static boolean addTinIngots;
	public static boolean addLeadIngots;
	public static boolean addSteelIngots;
	public static boolean addSteelNuggets;

	public static boolean keepLavaRecipesWhenFuelIsPresent;
	
	public static boolean cl_lockSpeedFov;
	public static float cl_fixedSprintFov;
	
	public static boolean cl_enableDeathFX;
	public static boolean cl_enableDeathFX_Gore;
	
	public static boolean disableAutofeeder;
	public static boolean machinesNeedNoPower;
	
	public static boolean doOreGenCopper;
	public static boolean doOreGenTin;
	public static boolean doOreGenLead;
	public static boolean doOreGenUranium;
	public static boolean doOreGenTitanium;
	
	/**
	 * IDS
	 */
	public static int dataWatcherID_FaceSlot;
	public static int dataWatcherID_BackSlot;
	
	//public static int GUI_ID_tgplayerInventory;
	/**
	 * NPC Spawns
	 */
	public static int distanceSpawnLevel0;
	public static int distanceSpawnLevel1;
	public static int distanceSpawnLevel2;
	
	public static int spawnWeightZombieSoldier;
	public static int spawnWeightZombieFarmer;
	public static int spawnWeightZombieMiner;
	public static int spawnWeightZombiePigmanSoldier;
	public static int spawnWeightCyberDemon;
	public static int spawnWeightSkeletonSoldier;
	
	public static int spawnWeightBandit;
	
	public static int spawnWeightPsychoSteve;
	
	public static int spawnWeightTGOverworld;
	public static int spawnWeightTGNether;
	
	public static int cl_sortPassesPerTick;
	
	public static float damagePvP;
	public static float damageTurretToPlayer;
	public static float damageFactorNPC;
	
	public static int spawnWeightTGStructureSmall;
	public static int spawnWeightTGStructureBig;
	public static int spawnWeightTGStructureMedium;
	
	//structure spawning
	public static boolean doWorldspawn;
	
	public static float explosiveChargeMaxBlockHardness;
	public static float explosiveChargeAdvancedMaxBlockHardness;
	
	
	public static boolean limitUnsafeModeToOP;
	
	public static boolean WIP_disableRadiationSystem;
	/**
	 * FLUID RECIPES
	 */
	public static String[] fluidListOil;
	public static String[] fluidListOilWorldspawn;
	public static String[] fluidListFuel;

	public static float oreDrillMultiplierOres;
	public static float oreDrillMultiplierPower;
	public static float oreDrillMultiplierFuel;
	public static float oreDrillFuelValueFuel;

	/** ore cluster values **/
	public static int mininglevel_coal;
	public static int mininglevel_common_metal;
	public static int mininglevel_rare_metal;
	public static int mininglevel_shiny_metal;
	public static int mininglevel_uranium;
	public static int mininglevel_common_gem;
	public static int mininglevel_shiny_gem;
	public static int mininglevel_nether_crystal;
	public static int mininglevel_oil;

	public static double oremult_coal;
	public static double oremult_common_metal;
	public static double oremult_rare_metal;
	public static double oremult_shiny_metal;
	public static double oremult_uranium;
	public static double oremult_common_gem;
	public static double oremult_shiny_gem;
	public static double oremult_nether_crystal;
	public static double oremult_oil;
	
	public static double powermult_coal;
	public static double powermult_common_metal;
	public static double powermult_rare_metal;
	public static double powermult_shiny_metal;
	public static double powermult_uranium;
	public static double powermult_common_gem;
	public static double powermult_shiny_gem;
	public static double powermult_nether_crystal;
	public static double powermult_oil;
	
	public static int upgrade_xp_cost;
	
	public static String[] biomeBlacklist;
	
	public static boolean spawnOreClusterStructures;
	
	/**
	 * CATEGORIES
	 */
	private static final String CATEGORY_ENABLING_ITEMS = "Disable Items";
	
	public static final String CLIENTSIDE = "Clientside";
	private static final String ID_CONFLICTS = "ID Conflicts";
	private static final String WORLDGEN="World Generation";
	private static final String DAMAGE_FACTORS="Damage Factors";
	private static final String ORE_DRILLS = "Ore Drills";
	

	public static void init(FMLPreInitializationEvent event){
		//Load the config file
		config = new Configuration(event.getSuggestedConfigurationFile());
		config.load();
		
		initValues();
	}
	
	public static void initValues() {
		config.addCustomCategoryComment(CLIENTSIDE, "Clientside options, can be changed when playing on a server");
		
		debug = config.getBoolean("debug", config.CATEGORY_GENERAL, false, "Enable debug options and unfinished stuff, disable this for playing.");
		
		upgrade_xp_cost = config.getInt("UpgradeXPCost", config.CATEGORY_GENERAL, 20, 0, 10000, "Base XP value for Upgrade Bench recipes (enchants)");
		
		limitUnsafeModeToOP = config.getBoolean("RestrictUnsafeModeToOP", config.CATEGORY_GENERAL, false, "Only opped players can use the unsafe mode of guns, this OVERRIDES the permission setting 'techguns.allowunsafemode'");
		
		disableAutofeeder = config.getBoolean("disableAutofeeder", config.CATEGORY_GENERAL, false, "Disable automatic feeding of Food in the Techguns tab. Disable autofeeding if you think it breaks the balance");	
		machinesNeedNoPower = config.getBoolean("machinesNeedNoPower", config.CATEGORY_GENERAL, false, "Machines don't need power, activate this if you don't want to install a mod with generators and still be able to use the machines");
		
		keepLavaRecipesWhenFuelIsPresent = config.getBoolean("keepLavaRecipesWhenFuelIsPresent", config.CATEGORY_GENERAL, false, "Keep recipes with lava instead of fuel even when fuel is present. Fuels need to be added by other mods");
			
		addCopperIngots = config.getBoolean("addCopperIngot", CATEGORY_ENABLING_ITEMS, true, "Add copper ingots.");
		addCopperNuggets = config.getBoolean("addCopperNugget", CATEGORY_ENABLING_ITEMS, true, "Add copper nuggets.");
		addTinIngots = config.getBoolean("addTinIngot", CATEGORY_ENABLING_ITEMS, true, "Add tin ingots.");
		addBronzeIngots = config.getBoolean("addBronzeIngot", CATEGORY_ENABLING_ITEMS, true, "Add bronze ingots.");
		
		addLeadIngots = config.getBoolean("addLeadIngot", CATEGORY_ENABLING_ITEMS, true, "Add lead ingots.");
		addLeadNuggets = config.getBoolean("addLeadNugget", CATEGORY_ENABLING_ITEMS, true, "Add Lead nuggets.");
		
		addSteelIngots = config.getBoolean("addSteelIngot", CATEGORY_ENABLING_ITEMS, true, "Adds Steel ingots.");
		addSteelNuggets = config.getBoolean("addSteelNugget", CATEGORY_ENABLING_ITEMS, true, "Adds Steel nuggets.");
	
		
		distanceSpawnLevel0 = config.getInt("DistanceSpawnLevel0", "NPC Spawn", 500, 0, Integer.MAX_VALUE, "Up to which distance to worldspawn only mobs with danger level up to 0 will spawn");
		distanceSpawnLevel1 = config.getInt("DistanceSpawnLevel1", "NPC Spawn", 1000, 0, Integer.MAX_VALUE, "Up to which distance to worldspawn only mobs with danger level up to 1 will spawn");
		distanceSpawnLevel2 = config.getInt("DistanceSpawnLevel2", "NPC Spawn", 2500, 0, Integer.MAX_VALUE, "Up to which distance to worldspawn only mobs with danger level up to 2 will spawn");
				
		spawnWeightTGOverworld = config.getInt("Techguns Spawnweight Overworld", "NPC Spawn", 600, 0, 10000, "Spawn weigth of Techguns NPCs, determines how many TG npcs spawn");
		spawnWeightTGNether = config.getInt("Techguns Spawnweight NEther", "NPC Spawn", 300, 0, 10000, "Spawn weigth of Techguns NPCs in the Nether, determines how many TG npcs spawn");
		
		spawnWeightZombieSoldier  = config.getInt("SpawnWeightZombieSoldier", "NPC Spawn", 100, 0, 10000, "Spawn weight for spawning Zombie Soldiers, at 0 spawn will not be registered");
		
		spawnWeightZombieFarmer  = config.getInt("SpawnWeightZombieFarmer", "NPC Spawn", 200, 0, 10000, "Spawn weight for spawning Zombie Farmers, at 0 spawn will not be registered");
		
		spawnWeightZombieMiner = config.getInt("SpawnWeightZombieMiner", "NPC Spawn", 200, 0, 10000, "Spawn weight for spawning Zombie Miners, at 0 spawn will not be registered");
		
		spawnWeightZombiePigmanSoldier  = config.getInt("SpawnWeightZombiePigmanSoldier", "NPC Spawn", 100, 0, 10000, "Spawn weight for spawning Zombie Pigman Soldiers (Nether only), at 0 spawn will not be registered");
		
		spawnWeightCyberDemon = config.getInt("SpawnWeightCyberDemon", "NPC Spawn", 30, 0, 10000, "Spawn weight for spawning Cyber Demons (Nether only), at 0 spawn will not be registered");
		
		spawnWeightBandit = config.getInt("SpawnWeightBandit", "NPC Spawn", 50, 0, 10000, "Spawn weight for spawning Bandit groups, at 0 spawn will not be registered");
		
		spawnWeightSkeletonSoldier = config.getInt("SpawnWeightSkeletonSoldier", "NPC Spawn", 100, 0, 10000, "Spawn weight for spawning Skeleton Soldiers, at 0 spawn will not be registered");
		
		spawnWeightPsychoSteve = config.getInt("SpawnWeightPsychoSteve", "NPC Spawn", 3, 0, 10000, "Spawn weight for spawning Psycho Steve, early game boss, don't set to high value, at 0 spawn will not be registered");
	
		biomeBlacklist = config.getStringList("BiomeBlacklist", "NPC Spawn", new String[]{""}, "Biome Registry names (e.g: minecraft:mushroom_island) that are excluded from Techguns monster spawning");
		
		
		damagePvP = config.getFloat("DamagePvP", DAMAGE_FACTORS, 0.5f, 0.0f, 100.0f, "Damage factor Techguns weapons deal when fired from players against other players, is zero when PvP is disabled");
		
		damageTurretToPlayer = config.getFloat("DamageTurretToPlayer", DAMAGE_FACTORS, 0.5f, 0.0f, 100.0f, "Damage factor Techguns Turrets deal when hitting players");
		
		damageFactorNPC = config.getFloat("DamageFactorNPC", DAMAGE_FACTORS, 1.0f, 0.0f, 100.0f, "Damage factor for all NPCs other than turrets, they already have a difficulty dependent damage penalty, this can be used to further reduce their damage, or increase it");
	
		
		//dataWatcherID_FaceSlot = config.getInt("DataWatcherID_FaceSlot", ID_CONFLICTS, 23, 2,31, "The ID used for DataWatcher synchronization of the face slot for Players, the ID must not conflict with vanilla or other mods slots, see http://www.minecraftforge.net/wiki/Datawatcher for details. Never useable for EntityPlayer (used by vanilla minecraft): 0,1, 6,7,8,9, 16,17,18");
		
		//dataWatcherID_BackSlot = config.getInt("DataWatcherID_BackSlot", ID_CONFLICTS, 24, 2,31, "The ID used for DataWatcher synchronization of the back slot for Players, the ID must not conflict with vanilla or other mods slots, see http://www.minecraftforge.net/wiki/Datawatcher for details. Never useable for EntityPlayer (used by vanilla minecraft): 0,1, 6,7,8,9, 16,17,18");
		
	//	GUI_ID_tgplayerInventory = config.getInt("TechgunsGUI_TabID", ID_CONFLICTS, 17, 0, 1000, "ID for the button used by the Techguns inventory tab.");
		
	
		doOreGenCopper = config.getBoolean("doOreGenCopper", WORLDGEN, true, "Generate Copper Ore, disable if other mod does");
		
		doOreGenTin = config.getBoolean("doOreGenTin", WORLDGEN, true, "Generate Tin Ore, disable if other mod does");
		
		doOreGenLead = config.getBoolean("doOreGenLead", WORLDGEN, true, "Generate Lead Ore, disable if other mod does");
		
		doOreGenTitanium = config.getBoolean("doOreGenTitanium", WORLDGEN, true, "Generate Titanium, not generated by most mods mods, leave it on in most cases");

		doOreGenUranium = config.getBoolean("doOreGenUranium", WORLDGEN, true, "Generate Uranium, disable if other mod already adds it and you want only 1 type. OreDictEntry:'oreUranium' ");
		
		doWorldspawn = config.getBoolean("SpawnStructures", WORLDGEN, true, "Should Structures (military bases) be spawned in the world?");
		
		spawnWeightTGStructureBig = config.getInt("StructureSpawnWeightBig", WORLDGEN, 64, 16, 100000, "Every X chunks it's tried to spawnn a Big building. This is in both dimensions, ChunkX, and ChunkY modulo <this Value> must be 0");
		spawnWeightTGStructureSmall = config.getInt("StructureSpawnWeightSmall", WORLDGEN, 16, 4, 100000, "Every X chunks it's tried to spawnn a Small building. This is in both dimensions, ChunkX, and ChunkY modulo <this Value> must be 0");
		spawnWeightTGStructureMedium = config.getInt("StructureSpawnWeightMedium", WORLDGEN, 32, 8, 100000, "Every X chunks it's tried to spawnn a Medium building. This is in both dimensions, ChunkX, and ChunkY modulo <this Value> must be 0");
		
		
		spawnOreClusterStructures = config.getBoolean("SpawnOreClusterStructures", WORLDGEN, true, "When worldgen is enabled, include structure spawns that contain ore clusters.");
		
		explosiveChargeMaxBlockHardness = config.getFloat("ExplosiveChargeMaxHardness", config.CATEGORY_GENERAL, 30.0f, 0.0f, Float.MAX_VALUE, "Highest blockHardness normal explosive charges can break, obsidian is 50.0)");
		
		explosiveChargeAdvancedMaxBlockHardness = config.getFloat("ExplosiveChargeAdvancedMaxHardness", config.CATEGORY_GENERAL, 100.0f, 0.0f, Float.MAX_VALUE, "Highest blockHardness advanced explosive charges can break, obsidian is 50.0)");
		
		
		cl_enableDeathFX = config.getBoolean("EnableDeathEffects", CLIENTSIDE, true, "Enable Death Effects, pure clientside check.");
		cl_enableDeathFX_Gore = config.getBoolean("EnableGoreDeathEffect", CLIENTSIDE, true, "Enable the gore Death Effect, requires DeathEffects to be enabled, pure clientside check.");
	
		
		cl_lockSpeedFov = config.getBoolean("LockSpeedDependantFov", CLIENTSIDE, true, "Counters the speed dependant FOV change. This also stops FOV changes while sprinting. Don't activate if another mod does this too, pure clientside check.");
		
		cl_fixedSprintFov = config.getFloat("FixedSprintFovMultiplier", CLIENTSIDE, 1.15f, 1.0f, 10.0f, "Multiply the FOV while sprinting by this value independent from the actual speed, has no effect when LockSpeedDependantFov is false, pure clientside check.");
		
		cl_sortPassesPerTick = config.getInt("ParticleDepthSortPasses", CLIENTSIDE, 10, 0, 20, "How many bubble sort passes should be performed each tick on particles. 0=off. Clientside");
		
		
		WIP_disableRadiationSystem = config.getBoolean("WIP_disableRadiationSystem", config.CATEGORY_GENERAL, true, "Disable Radiation for players. Radiation system is WIP, only use in creative for testing");

		fluidListFuel = config.getStringList("FluidListFuel", "Fluid Recipes", new String[]{"fuel", "refined_fuel", "biofuel", "biodiesel", "diesel", "gasoline", "fluiddiesel", "fluidnitrodiesel", "fliudnitrofuel", "refined_biofuel", "fire_water", "rocket_fuel"}, "Fluids that can be used to fill up fuel tanks");
		fluidListOil = config.getStringList("FluidListOil", "Fluid Recipes", new String[]{"oil", "tree_oil", "crude_oil", "fluidoil", "seed_oil"}, "Fluids that are treated as oil.");
		fluidListOilWorldspawn = config.getStringList("FluidListOilWorldspawn", "Fluid Recipes", new String[]{"oil", "crude_oil"}, "Fluids that are treated as oil for worlspawn and oil ore clusters.");

		
		oreDrillMultiplierOres = config.getFloat("oreDrillMultiplierOre", ORE_DRILLS, 1.0f, 0.001f, 1000.0f, "Multiplier to default rate on how many ores an ore drill produces");
		
		oreDrillMultiplierPower = config.getFloat("oreDrillMultiplierPower", ORE_DRILLS, 1.0f, 0f, 1000.0f, "Multiplier to default rate on how much power an ore drill requires");
	
		oreDrillMultiplierFuel = config.getFloat("oreDrillFuelMultiplier", ORE_DRILLS, 1000, 1, 100000, "Multiplier to calculate value of furnace fuel burn time = RF for ore Drill. burnTime* <THIS_VALUE> = RF. Only for internal use of the ore Drill, no real RF generation.");
		
		oreDrillFuelValueFuel = config.getFloat("oreDrillFuelValueFuel", ORE_DRILLS, 100, 1, 100000, "Fuel value for Liquid Fuel for use in ore Drills, this is per Millibucket, not Bucket, so 1/1000 of bucket value");
		
		mininglevel_coal = config.getInt("cluster_mininglevel_coal", ORE_DRILLS, 				      0, 0, 10, "Mining Level for coal ore clusters");
		mininglevel_common_metal = config.getInt("cluster_mininglevel_common_metal", ORE_DRILLS, 	  0, 0, 10, "Mining Level for common metal ore clusters");
		mininglevel_rare_metal = config.getInt("cluster_mininglevel_rare_metal", ORE_DRILLS,          1, 0, 10, "Mining Level for rare metal ore clusters");
		mininglevel_shiny_metal = config.getInt("cluster_mininglevel_shiny_metal", ORE_DRILLS ,       2, 0, 10, "Mining Level for shiny metal ore clusters");
		mininglevel_uranium = config.getInt("cluster_mininglevel_uranium", ORE_DRILLS,  		      3, 0, 10, "Mining Level for uranium ore clusters");
		mininglevel_common_gem = config.getInt("cluster_mininglevel_common_gem", ORE_DRILLS,          1, 0, 10, "Mining Level for common gem ore clusters");
		mininglevel_shiny_gem = config.getInt("cluster_mininglevel_shiny_gem", ORE_DRILLS,            3, 0, 10, "Mining Level for shiny gem ore clusters");
		mininglevel_nether_crystal = config.getInt("cluster_mininglevel_nether_crystal", ORE_DRILLS,  2, 0, 10, "Mining Level for nether crystal ore clusters");
		mininglevel_oil = config.getInt("cluster_mininglevel_oil", ORE_DRILLS,  					  2, 0, 10, "Mining Level for oil clusters");
			
		oremult_coal = config.getFloat("cluster_oremult_coal", ORE_DRILLS,  				   10f, 0.0001f, 1000f, "Ore Multiplier for coal ore clusters");
		oremult_common_metal = config.getFloat("cluster_oremult_common_metal", ORE_DRILLS,      5f, 0.0001f, 1000f, "Ore Multiplier for common metal ore clusters");
		oremult_rare_metal = config.getFloat("cluster_oremult_rare_metal", ORE_DRILLS,        2.5f, 0.0001f, 1000f, "Ore Multiplier for rare metal ore clusters");
		oremult_shiny_metal = config.getFloat("cluster_oremult_shiny_metal", ORE_DRILLS,        1f, 0.0001f, 1000f, "Ore Multiplier for shiny metal ore clusters");
		oremult_uranium = config.getFloat("cluster_oremult_uranium", ORE_DRILLS,              0.5f, 0.0001f, 1000f, "Ore Multiplier for uranium ore clusters");
		oremult_common_gem = config.getFloat("cluster_oremult_common_gem", ORE_DRILLS,          5f, 0.0001f, 1000f, "Ore Multiplier for common gem ore clusters");
		oremult_shiny_gem = config.getFloat("cluster_oremult_shiny_gem", ORE_DRILLS, 		  0.2f, 0.0001f, 1000f, "Ore Multiplier for shiny gem ore clusters");
		oremult_nether_crystal = config.getFloat("cluster_oremult_nether_crystal", ORE_DRILLS,  4f, 0.0001f, 1000f, "Ore Multiplier for nether crystal ore clusters");
		oremult_oil = config.getFloat("cluster_oremult_oil", ORE_DRILLS,  						4f, 0.0001f, 1000f, "Ore Multiplier for oil clusters");
		
		powermult_coal = config.getFloat("cluster_powermult_coal", ORE_DRILLS,                      0.1f, 0.0001f, 1000f, "Power Multiplier for coal ore clusters");
		powermult_common_metal = config.getFloat("cluster_powermult_common_metal", ORE_DRILLS,      0.2f, 0.0001f, 1000f, "Power Multiplier for common metal ore clusters");
		powermult_rare_metal = config.getFloat("cluster_powermult_rare_metal", ORE_DRILLS,          0.4f, 0.0001f, 1000f, "Power Multiplier for rare metal ore clusters");
		powermult_shiny_metal = config.getFloat("cluster_powermult_shiny_metal", ORE_DRILLS, 		1.0f, 0.0001f, 1000f, "Power Multiplier for shiny metal ore clusters");
		powermult_uranium = config.getFloat("cluster_powermult_uranium", ORE_DRILLS, 				1.0f, 0.0001f, 1000f, "Power Multiplier for uranium ore clusters");
		powermult_common_gem = config.getFloat("cluster_powermult_common_gem", ORE_DRILLS,  		0.2f, 0.0001f, 1000f, "Power Multiplier for common gem ore clusters");
		powermult_shiny_gem = config.getFloat("cluster_powermult_shiny_gem", ORE_DRILLS,            1.0f, 0.0001f, 1000f, "Power Multiplier for shiny gem ore clusters");
		powermult_nether_crystal = config.getFloat("cluster_powermult_nether_crystal", ORE_DRILLS,  0.5f, 0.0001f, 1000f, "Power Multiplier for nether crystal ore clusters");
		powermult_oil = config.getFloat("cluster_powermult_oil", ORE_DRILLS,  						1.0f, 0.0001f, 1000f, "Power Multiplier for oil clusters");
		
		
		if(config.hasChanged()) {
			config.save();
		}
	}
	
	@SubscribeEvent
	public static void onConfigChanged(ConfigChangedEvent.OnConfigChangedEvent event){
		if(event.getModID().equalsIgnoreCase(Techguns.MODID))
		{
			initValues();
		}
	}

}
