package techguns;

import java.util.ArrayList;

import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.init.Enchantments;
import net.minecraft.init.Items;
import net.minecraft.init.PotionTypes;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionUtils;
import net.minecraftforge.common.brewing.BrewingRecipeRegistry;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.oredict.OreDictionary;
import techguns.api.radiation.TGRadiation;
import techguns.blocks.EnumOreType;
import techguns.items.armors.ICamoChangeable;
import techguns.tileentities.operation.AmmoPressBuildPlans;
import techguns.tileentities.operation.UpgradeBenchRecipes;
import techguns.tileentities.operation.UpgradeBenchRecipes.UpgradeBenchRecipe;
import techguns.tileentities.operation.BlastFurnaceRecipes;
import techguns.tileentities.operation.BlastFurnaceRecipes.BlastFurnaceRecipe;
import techguns.tileentities.operation.CamoBenchRecipes;
import techguns.tileentities.operation.CamoBenchRecipes.CamoBenchRecipe;
import techguns.tileentities.operation.ChargingStationRecipe;
import techguns.tileentities.operation.ChemLabRecipes;
import techguns.tileentities.operation.FabricatorRecipe;
import techguns.tileentities.operation.GrinderRecipes;
import techguns.tileentities.operation.MetalPressRecipes;
import techguns.tileentities.operation.ReactionBeamFocus;
import techguns.tileentities.operation.ReactionChamberRecipe;
import techguns.tileentities.operation.ReactionChamberRecipe.RiskType;
import techguns.util.ItemStackOreDict;
import techguns.util.ItemUtil;

/**
* Class that contains recipes for machines
*
*/
public class TGMachineRecipes {

	public static void addRecipes(){
		
		ItemStack GOLD_OR_ELECTRUM=ItemStack.EMPTY;
		if(OreDictionary.doesOreNameExist("ingotElectrum")) {
			GOLD_OR_ELECTRUM = OreDictionary.getOres("ingotElectrum").get(0);
		} else {
			GOLD_OR_ELECTRUM = new ItemStack(Items.GOLD_INGOT,1);
		}
		
		//AMMO PRESS
		ArrayList<String> metal2 = new ArrayList<String>();
		ArrayList<String> metal1 = new ArrayList<String>();
		ArrayList<String> powders = new ArrayList<String>();
		metal2.add("ingotCopper");
		metal2.add("ingotTin");
		metal2.add("ingotIron");
		metal2.add("ingotBronze");
		metal1.add("ingotSteel");
		metal1.add("ingotLead");
		powders.add("gunpowder");
		AmmoPressBuildPlans.init(metal1, metal2, powders);
		
		//METAL PRESS
		//MetalPressRecipes.addRecipe("ingotSteel", new ItemStack(Blocks.OBSIDIAN,1), TGItems.newStack(TGItems.INGOT_OBSIDIAN_STEEL,1), true);
		MetalPressRecipes.addRecipe("ingotTin","ingotTin",TGItems.newStack(TGItems.PLATE_TIN,2), true);
		MetalPressRecipes.addRecipe("ingotCopper","ingotCopper",TGItems.newStack(TGItems.PLATE_COPPER,2), true);
		MetalPressRecipes.addRecipe("ingotBronze","ingotBronze",TGItems.newStack(TGItems.PLATE_BRONZE,2), true);
		MetalPressRecipes.addRecipe("ingotIron","ingotIron",TGItems.newStack(TGItems.PLATE_IRON,2), true);
		MetalPressRecipes.addRecipe("ingotSteel","ingotSteel",TGItems.newStack(TGItems.PLATE_STEEL,2), true);
		MetalPressRecipes.addRecipe("ingotLead","ingotLead",TGItems.newStack(TGItems.PLATE_LEAD,2), true);
		MetalPressRecipes.addRecipe("plateIron", new ItemStack(Items.FLINT,1), TGItems.newStack(TGItems.MECHANICAL_PARTS_IRON, 1), true);
		MetalPressRecipes.addRecipe("plateObsidianSteel", "gemQuartz", TGItems.newStack(TGItems.MECHANICAL_PARTS_OBSIDIAN_STEEL, 1), true);
		MetalPressRecipes.addRecipe("plateCarbon", new ItemStack(Items.BLAZE_ROD), TGItems.newStack(TGItems.MECHANICAL_PARTS_CARBON, 2), true);
		MetalPressRecipes.addRecipe("plateCopper", "plateCopper", TGItems.newStack(TGItems.WIRE_COPPER, 8), true);
		MetalPressRecipes.addRecipe("fiberCarbon", "fiberCarbon", TGItems.newStack(TGItems.PLATE_CARBON, 2), true);
		MetalPressRecipes.addRecipe("plateObsidianSteel", TGItems.newStack(TGItems.TGX, 1), TGItems.newStack(TGItems.ADVANCED_ROUNDS, 16), true);
		MetalPressRecipes.addRecipe("ingotObsidianSteel", "ingotObsidianSteel", TGItems.newStack(TGItems.PLATE_OBSIDIAN_STEEL, 2), true);
		MetalPressRecipes.addRecipe("plateSteel", "plateBronze", TGItems.newStack(TGItems.STEAMARMOR_PLATE, 1), true);
		MetalPressRecipes.addRecipe("ingotGold", "ingotGold", TGItems.newStack(TGItems.WIRE_GOLD, 2), true);
		MetalPressRecipes.addRecipe("plateIron", new ItemStack(Blocks.TNT,1), TGItems.newStack(TGItems.GRENADE_40MM, 16), true);
		MetalPressRecipes.addRecipe("ingotTitanium","ingotTitanium",TGItems.newStack(TGItems.PLATE_TITANIUM,2), true);
		MetalPressRecipes.addRecipe("plateObsidianSteel","plateTitanium",TGItems.newStack(TGItems.GAUSSRIFLE_SLUGS,4), true);
		MetalPressRecipes.addRecipe(TGItems.newStack(TGItems.SNIPER_ROUNDS_INCENDIARY, 1), TGItems.newStack(TGItems.TGX, 1), TGItems.newStack(TGItems.SNIPER_ROUNDS_EXPLOSIVE, 1), true);
		
		//CHEM LAB

		if(ItemUtil.existsInOredict("dustCoal")){
			ChemLabRecipes.addRecipe("dustRedstone",1, "dustCoal",1, null,0, new FluidStack(FluidRegistry.WATER,250), null, new ItemStack(Items.GUNPOWDER,1), true,5);
		} else {
			ChemLabRecipes.addRecipe("dustRedstone",1, new ItemStack(Items.COAL),1, null,0, new FluidStack(FluidRegistry.WATER,250), null, new ItemStack(Items.GUNPOWDER,1), true,5);
		}
		
		if(!TGFluids.oils.isEmpty()){
			//ChemLabRecipes.addRecipe("slimeball", 1, new ItemStack(Items.COAL), 1, null, 0, new FluidStack(FluidRegistry.WATER,1000), new FluidStack(TGFluids.OIL,250), null, true, 20);
			
			TGFluids.oils.forEach(f -> {
				ChemLabRecipes.addRecipe("itemRawRubber", 1, (ItemStack)null, 0, null, 0, new FluidStack(f,500), null, TGItems.newStack(TGItems.RAW_PLASTIC, 1), false, 25);
			});
		} else {
			ChemLabRecipes.addRecipe("itemRawRubber", 1, new ItemStack(Items.COAL,1), 1, null, 0, new FluidStack(TGFluids.WATER,1000), null, TGItems.newStack(TGItems.RAW_PLASTIC, 1), true, 25);
		}
		
		ChemLabRecipes.addRecipe(TGItems.BIOMASS, 1, new ItemStack(Items.GUNPOWDER), 1, null, 0, new FluidStack(TGFluids.WATER,1000), new FluidStack(TGFluids.ACID,1000), null, true, 20);
		
		if (ItemUtil.existsInOredict("itemBioFuel")){
			ChemLabRecipes.addRecipe("itemBioFuel",4, TGItems.newStack(TGItems.BIO_TANK_EMPTY,1), 1, null, 0, new FluidStack(TGFluids.WATER,500), null, TGItems.newStack(TGItems.BIO_TANK, 1), false, 1);
			ChemLabRecipes.addRecipe("itemBioFuel",4, new ItemStack(Items.GUNPOWDER), 1, null, 0, new FluidStack(TGFluids.WATER,1000), new FluidStack(TGFluids.ACID,1000), null, false, 20);
				
		}
		
		
		ItemStack fuelTank = TGItems.newStack(TGItems.FUEL_TANK, 1);
		ItemStack fuelTankEmpty = TGItems.newStack(TGItems.FUEL_TANK_EMPTY, 1);
		if(!TGFluids.fuels.isEmpty()){
			TGFluids.fuels.forEach(f -> {
				ChemLabRecipes.addRecipe("gunpowder", 1, "gemLapis", 1, null, 0, new FluidStack(f,250), null, TGItems.newStack(TGItems.TGX, 1), true, 20);
				ChemLabRecipes.addRecipe((ItemStack)null, 0, (ItemStack)null, 0, fuelTankEmpty, 1, new FluidStack(f,250), null, fuelTank, false, 1);
				//ChemLabRecipes.addRecipe(fuelTank, 1, (ItemStack)null, 0, null, 0, null, new FluidStack(f,250), fuelTankEmpty, false, 1);
				ChemLabRecipes.addRecipe(TGItems.newStack(TGItems.ROCKET, 1), 1, (ItemStack)null, 0, null, 0, new FluidStack(f,125), null, TGItems.newStack(TGItems.ROCKET_HIGH_VELOCITY,1), false, 5);
			});
		} 
		if (TGFluids.fuels.isEmpty() || TGConfig.keepLavaRecipesWhenFuelIsPresent){
			ChemLabRecipes.addRecipe("gunpowder", 1, "gemLapis", 1, null, 0, new FluidStack(FluidRegistry.LAVA,500), null, TGItems.newStack(TGItems.TGX, 1), true, 20);
			ChemLabRecipes.addRecipe(fuelTankEmpty, 1, (ItemStack)null, 0, null, 0, new FluidStack(TGFluids.LAVA,500), null, fuelTank, false, 1);
			//ChemLabRecipes.addRecipe(fuelTank, 1, (ItemStack)null, 0, null, 0, null,new FluidStack(TGFluids.LAVA,500), fuelTankEmpty, false, 1);
			ChemLabRecipes.addRecipe(TGItems.newStack(TGItems.ROCKET, 1), 1, (ItemStack)null, 0, null, 0, new FluidStack(TGFluids.LAVA,250), null, TGItems.newStack(TGItems.ROCKET_HIGH_VELOCITY,1), false, 5);
			
		}
		
		ChemLabRecipes.addRecipe("gemDiamond", 1, new ItemStack(Items.BLAZE_POWDER,1), 1, null, 0, new FluidStack(TGFluids.LAVA,1000), null, TGItems.newStack(TGItems.CARBON_FIBERS, 2), true, 25);
		
		ItemStackOreDict stackLogWood = new ItemStackOreDict("logWood").setNoStrictMode();
		ItemStackOreDict nullStack = new ItemStackOreDict((ItemStack)null);
		ChemLabRecipes.addRecipe(stackLogWood, 1, nullStack, 0, null, 0, new FluidStack(TGFluids.WATER,1000), null, TGItems.newStack(TGItems.RAW_RUBBER, 1), false, 20);
		
		ChemLabRecipes.addRecipe(TGItems.newStack(TGItems.BIOMASS, 1),1, (ItemStack)null, 0, TGItems.newStack(TGItems.BIO_TANK_EMPTY,1), 1, new FluidStack(TGFluids.WATER,500), null, TGItems.newStack(TGItems.BIO_TANK, 1), false, 1);
		
		ChemLabRecipes.addRecipe(new ItemStack(Items.COAL,1), 1, (ItemStack)null, 0, TGItems.newStack(TGItems.COMPRESSED_AIR_TANK_EMPTY, 1), 1, new FluidStack(TGFluids.WATER,250), null, TGItems.newStack(TGItems.COMPRESSED_AIR_TANK, 1), true, 5);
		
		ChemLabRecipes.addRecipe(new ItemStack(Blocks.NETHERRACK), 1, new ItemStack(Blocks.SOUL_SAND), 1, null, 0, new FluidStack(TGFluids.LAVA,1000), null, TGItems.newStack(TGItems.NETHER_CHARGE, 4), true, 20);
		
		ItemStackOreDict uranium = new ItemStackOreDict("oreUranium").setNoStrictMode();

		ChemLabRecipes.addRecipe(uranium, 1, nullStack, 0, null, 0, new FluidStack(TGFluids.ACID,250), null, TGItems.newStack(TGItems.YELLOWCAKE, 3), false, 20);
		
		ChemLabRecipes.addRecipe(TGItems.ENRICHED_URANIUM, 1, (ItemStack)null, 0, TGItems.NUCLEAR_POWERCELL_EMPTY, 1, new FluidStack(TGFluids.WATER,1000), null, TGItems.newStack(TGItems.NUCLEAR_POWERCELL, 1), true, 40);
		
		if (TGFluids.MILK!=null){
			ChemLabRecipes.addRecipe(new ItemStack(Items.DYE,1,2), 1, "itemRawRubber", 1, null, 0, new FluidStack(TGFluids.MILK,500), null, new ItemStack(Items.SLIME_BALL), true, 25);
		}
		
		ChemLabRecipes.addRecipe(new ItemStack(Items.LEATHER,2), 2, "slimeball", 1, null, 0, new FluidStack(TGFluids.ACID,500), null, TGItems.newStack(TGItems.TREATED_LEATHER,2), true, 20);

		ChemLabRecipes.addRecipe(new ItemStack(Items.ROTTEN_FLESH), 1, (ItemStack)null, 0, null, 0, new FluidStack(TGFluids.WATER,500), null, new ItemStack(Items.LEATHER,1), false, 15);
		
		ChemLabRecipes.addRecipe(new ItemStack(Blocks.GRAVEL), 1, new ItemStack(Blocks.SAND), 1, null, 0, new FluidStack(TGFluids.WATER,250), null, new ItemStack(Blocks.CONCRETE,2,8), false, 5);
		ChemLabRecipes.addRecipe(new ItemStack(Blocks.GRAVEL), 1, new ItemStack(Blocks.DIRT), 1, null, 0, new FluidStack(TGFluids.WATER,250), null, new ItemStack(Blocks.CLAY,2), false, 5);

		
		ChemLabRecipes.addRecipe(TGItems.RIFLE_ROUNDS, 1, new ItemStack(Items.BLAZE_POWDER), 1, null, 0, new FluidStack(TGFluids.LAVA,250), null, TGItems.newStack(TGItems.RIFLE_ROUNDS_INCENDIARY,1), false, 25);
		ChemLabRecipes.addRecipe(TGItems.PISTOL_ROUNDS, 2, new ItemStack(Items.BLAZE_POWDER), 1, null, 0, new FluidStack(TGFluids.LAVA,250), null, TGItems.newStack(TGItems.PISTOL_ROUNDS_INCENDIARY,2), false, 25);
		ChemLabRecipes.addRecipe(TGItems.SNIPER_ROUNDS, 1, new ItemStack(Items.BLAZE_POWDER), 1, null, 0, new FluidStack(TGFluids.LAVA,250), null, TGItems.newStack(TGItems.SNIPER_ROUNDS_INCENDIARY,1), false, 25);
		ChemLabRecipes.addRecipe(TGItems.SHOTGUN_ROUNDS, 8, new ItemStack(Items.BLAZE_POWDER), 1, null, 0, new FluidStack(TGFluids.LAVA,250), null, TGItems.newStack(TGItems.SHOTGUN_ROUNDS_INCENDIARY,8), false, 25);
		
		ChemLabRecipes.addRecipe( new ItemStack(Items.SUGAR), 4, new ItemStack(Items.SPECKLED_MELON),1,new ItemStack(Items.GLASS_BOTTLE),1, new FluidStack(TGFluids.MILK, 1000), null, new ItemStack(TGItems.RAD_PILLS, 4), true, 20);
		ChemLabRecipes.addRecipe( new ItemStack(Items.NETHER_WART), 1, new ItemStack(Items.SPECKLED_MELON), 1,TGItems.newStack(TGItems.PLASTIC_BAG, 1),1, new FluidStack(TGFluids.ACID, 250), null, new ItemStack(TGItems.RAD_AWAY, 1), true, 25);
		
		
		/**
		 * FABRICATOR
		 */
		FabricatorRecipe.addRecipe(new ItemStackOreDict("ingotGold"), 1, FabricatorRecipe.copperWires, 1, FabricatorRecipe.redstone, 3, FabricatorRecipe.plastic, 1, TGItems.ENERGY_CELL_EMPTY, 1);
		FabricatorRecipe.addRecipe(new ItemStackOreDict(new ItemStack(Blocks.SOUL_SAND,1)), 1, FabricatorRecipe.goldWires, 1, FabricatorRecipe.redstone, 1, FabricatorRecipe.plastic, 1, TGItems.CYBERNETIC_PARTS, 1);
		FabricatorRecipe.addRecipe(new ItemStackOreDict("ingotTitanium"), 2, FabricatorRecipe.circuit_basic, 4, FabricatorRecipe.mechanicalPartsT3, 1, FabricatorRecipe.carbonPlate, 4, TGItems.POWER_ARMOR_PLATING, 2);
		FabricatorRecipe.addRecipe(new ItemStackOreDict(TGItems.newStack(TGItems.COIL, 1)), 1, FabricatorRecipe.circuit_elite, 2, FabricatorRecipe.mechanicalPartsT3, 1, FabricatorRecipe.titaniumPlate, 1, TGItems.SONIC_EMITTER, 1);		
		FabricatorRecipe.addRecipe(new ItemStackOreDict(TGItems.newStack(TGItems.ENRICHED_URANIUM, 1)), 1, FabricatorRecipe.circuit_elite, 2, FabricatorRecipe.mechanicalPartsT3, 2, FabricatorRecipe.leadPlate, 2, TGItems.RAD_EMITTER, 1);		
		
		FabricatorRecipe.addRecipe(new ItemStackOreDict("ingotSteel", 2), 1, FabricatorRecipe.circuit_basic, 1, FabricatorRecipe.redstone, 4, FabricatorRecipe.leadPlate, 2, TGItems.NUCLEAR_POWERCELL_EMPTY, 1);		
		
		/**
		 * Camo Bench
		 */
		CamoBenchRecipes.addRecipe(new CamoBenchRecipe(Blocks.WOOL));
		CamoBenchRecipes.addRecipe(new CamoBenchRecipe(Blocks.CONCRETE));
		CamoBenchRecipes.addRecipe(new CamoBenchRecipe(Blocks.CONCRETE_POWDER));
		CamoBenchRecipes.addRecipe(new CamoBenchRecipe(Blocks.STAINED_HARDENED_CLAY));
		CamoBenchRecipes.addRecipe(new CamoBenchRecipe(Blocks.STAINED_GLASS));
		CamoBenchRecipes.addRecipe(new CamoBenchRecipe(Blocks.STAINED_GLASS_PANE));
		//CamoBenchRecipes.addRecipe(new CamoBenchRecipe(Blocks.BED));
		CamoBenchRecipes.addRecipe(new CamoBenchRecipe(Blocks.STANDING_BANNER));
		CamoBenchRecipes.addRecipe(new CamoBenchRecipe(Blocks.CARPET));
		
		TGBlocks.BLOCKLIST.forEach(b -> {
			if (b instanceof ICamoChangeable) {
				if (((ICamoChangeable)b).addBlockCamoChangeRecipes() ) {
					CamoBenchRecipes.addRecipe(new CamoBenchRecipe((Block)b, (ICamoChangeable)b));
				}
			}
		});
		CamoBenchRecipes.addRecipe(new CamoBenchRecipes.TGLampCamoBenchRecipe(TGBlocks.LAMP_0,0));
	
		/**
		 * Blast Furnace
		 */
		BlastFurnaceRecipes.addRecipe(new ItemStack(Items.IRON_INGOT,4), new ItemStack(Items.COAL,1), TGItems.newStack(TGItems.INGOT_STEEL, 4), 10, 800);
		BlastFurnaceRecipes.addRecipe(new ItemStack(Items.IRON_INGOT,4), new ItemStack(Items.COAL,1,1), TGItems.newStack(TGItems.INGOT_STEEL, 4), 10, 800);
		BlastFurnaceRecipes.addRecipe("ingotSteel",1, new ItemStack(Blocks.OBSIDIAN,1), TGItems.newStack(TGItems.INGOT_OBSIDIAN_STEEL, 1), 10, 200);
		BlastFurnaceRecipes.addRecipe("ingotCopper", 3,"ingotTin",1,TGItems.newStack(TGItems.INGOT_BRONZE, 4), 10, 100);
		
		/**
		 * CHARGING STATION
		 */
		ChargingStationRecipe.addRecipe(new ItemStackOreDict(TGItems.ENERGY_CELL_EMPTY), TGItems.ENERGY_CELL, 50000);
		ChargingStationRecipe.addRecipe(new ItemStackOreDict(TGItems.REDSTONE_BATTERY_EMPTY), TGItems.REDSTONE_BATTERY, 10000);
		
		
		/**
		 * REACTION CHAMBER
		 */
		//Beam focus
		ReactionBeamFocus.addBeamFocus(TGItems.RC_HEAT_RAY, 100, TGSounds.REACTION_CHAMBER_HEATRAY_WORK);
		ReactionBeamFocus.addBeamFocus(TGItems.RC_UV_EMITTER, 100, TGSounds.CHEM_LAB_WORK);
		
		//Recipes
										/**
										 * ID (STR), INPUT-STACK, FOCUS, FLUID_TYPE, ITEM_OUTPUTS, TICKS, REQ_COMPLETION, INTENSITY, INTENSITY_MARGIN, LIQUIDLEVEL, LIQUID_CONSUMPTION, INSTABILITY_CHANCE, RISK, RF_TICK
										 */
		ReactionChamberRecipe.addRecipe("RC_UV_WHEAT",new ItemStackOreDict(new ItemStack(Items.WHEAT_SEEDS, 1)), TGItems.RC_UV_EMITTER, FluidRegistry.WATER, new ItemStack[]{new ItemStack(Items.WHEAT,1), new ItemStack(Items.WHEAT_SEEDS,2)}, 10, 5, 3, 1, 1, 1000, 0.5f, RiskType.EXPLOSION_LOW,50000);
		
		ReactionChamberRecipe.addRecipe("RC_LASER_FOCUS",new ItemStackOreDict("gemDiamond",1), TGItems.RC_HEAT_RAY, TGFluids.LIQUID_REDSTONE, new ItemStack[]{TGItems.newStack(TGItems.LASER_FOCUS, 1)}, 10, 5, 3, 1, 4, 4000, 0.5f, RiskType.BREAK_ITEM,100000);
		
		ReactionChamberRecipe.addRecipe("RC_TITANIUM",new ItemStackOreDict(new ItemStack(TGBlocks.TG_ORE, 1,EnumOreType.ORE_TITANIUM.ordinal())), TGItems.RC_HEAT_RAY, TGFluids.ACID, new ItemStack[]{TGItems.newStack(TGItems.ORE_TITANIUM, 2),new ItemStack(Blocks.IRON_ORE, 1)}, 2, 1, 5, 0, 3, 100,0f, RiskType.BREAK_ITEM, 25000);
		
		ReactionChamberRecipe.addRecipe("RC_BLAZEROD",new ItemStackOreDict(TGItems.newStack(TGItems.QUARTZ_ROD, 1)), TGItems.RC_HEAT_RAY, TGFluids.LAVA, new ItemStack[]{new ItemStack(Items.BLAZE_ROD, 1)}, 5, 3, 7, 2, 4, 1000,0.5f, RiskType.BREAK_ITEM, 250000);
		
		ReactionChamberRecipe.addRecipe("RC_GLOWSTONE",new ItemStackOreDict("blockRedstone"), TGItems.RC_HEAT_RAY, TGFluids.LAVA, new ItemStack[]{new ItemStack(Blocks.GLOWSTONE, 1)}, 5, 3, 7, 2, 4, 1000,0.5f, RiskType.EXPLOSION_MEDIUM, 250000);
		
		ReactionChamberRecipe.addRecipe("RC_ANTIGRAV",new ItemStackOreDict(new ItemStack(Items.NETHER_STAR)), TGItems.RC_HEAT_RAY, TGFluids.LIQUID_ENDER, new ItemStack[]{TGItems.newStack(TGItems.ANTI_GRAV_CORE, 1)}, 10, 7, 8, 2, 4, 4000, 1f, RiskType.EXPLOSION_LOW, 500000);
		
		ReactionChamberRecipe.addRecipe("RC_URANIUM",new ItemStackOreDict("dustUranium"), TGItems.RC_HEAT_RAY, TGFluids.WATER, new ItemStack[]{TGItems.newStack(TGItems.ENRICHED_URANIUM, 1)}, 5, 4, 7, 0, 3, 1000, 0f, RiskType.BREAK_ITEM, 250000);
		
		
		//Smelting recipes
		GameRegistry.addSmelting(TGBlocks.TG_ORE.getStackFor(EnumOreType.ORE_COPPER), TGItems.INGOT_COPPER, 0.5f);
		GameRegistry.addSmelting(TGBlocks.TG_ORE.getStackFor(EnumOreType.ORE_TIN), TGItems.INGOT_TIN, 0.5f);
		GameRegistry.addSmelting(TGBlocks.TG_ORE.getStackFor(EnumOreType.ORE_LEAD), TGItems.INGOT_LEAD, 1.0f);
		
		GameRegistry.addSmelting(TGItems.TURRET_ARMOR_IRON, new ItemStack(Items.IRON_INGOT,5), 0f);
		GameRegistry.addSmelting(TGItems.TURRET_ARMOR_STEEL, TGItems.newStack(TGItems.INGOT_STEEL,5), 0f);
		GameRegistry.addSmelting(TGItems.TURRET_ARMOR_OBSIDIAN_STEEL, TGItems.newStack(TGItems.INGOT_OBSIDIAN_STEEL,5), 0f);
		
		GameRegistry.addSmelting(TGItems.BARREL_IRON, new ItemStack(Items.IRON_INGOT,6), 0f);
		GameRegistry.addSmelting(TGItems.BARREL_OBSIDIAN_STEEL, TGItems.newStack(TGItems.INGOT_OBSIDIAN_STEEL,6), 0f);
		
		GameRegistry.addSmelting(TGItems.PLATE_BRONZE, TGItems.INGOT_BRONZE, 0f);
		GameRegistry.addSmelting(TGItems.PLATE_COPPER, TGItems.INGOT_COPPER, 0f);
		GameRegistry.addSmelting(TGItems.PLATE_TIN, TGItems.INGOT_TIN, 0f);
		GameRegistry.addSmelting(TGItems.PLATE_LEAD, TGItems.INGOT_LEAD, 0f);
		GameRegistry.addSmelting(TGItems.PLATE_IRON, new ItemStack(Items.IRON_INGOT,1), 0f);
		GameRegistry.addSmelting(TGItems.PLATE_OBSIDIAN_STEEL, TGItems.INGOT_OBSIDIAN_STEEL, 0f);
		GameRegistry.addSmelting(TGItems.PLATE_TITANIUM, TGItems.INGOT_TITANIUM, 0f);
		GameRegistry.addSmelting(TGItems.PLATE_STEEL, TGItems.INGOT_STEEL, 0f);
		
		GameRegistry.addSmelting(TGItems.RAW_RUBBER, TGItems.RUBBER_BAR,0);
		GameRegistry.addSmelting(TGItems.RAW_PLASTIC,TGItems.PLASTIC_SHEET, 0);
		
		GameRegistry.addSmelting(TGItems.ORE_TITANIUM, TGItems.INGOT_TITANIUM, 0);
		
		//brewing
		ItemStack awkwardPotion = PotionUtils.addPotionToItemStack(new ItemStack(Items.POTIONITEM), PotionTypes.AWKWARD);
		ItemStack radPotion = PotionUtils.addPotionToItemStack(new ItemStack(Items.POTIONITEM), TGRadiationSystem.RAD_POTION);
		
		BrewingRecipeRegistry.addRecipe(awkwardPotion, "dustUranium", radPotion);
		BrewingRecipeRegistry.addRecipe(awkwardPotion, TGItems.newStack(TGItems.ENRICHED_URANIUM, 1), PotionUtils.addPotionToItemStack(new ItemStack(Items.POTIONITEM), TGRadiationSystem.RAD_POTION_SEVERE));
		
		BrewingRecipeRegistry.addRecipe(awkwardPotion, "ingotLead", PotionUtils.addPotionToItemStack(new ItemStack(Items.POTIONITEM), TGRadiationSystem.RAD_RESISTANCE_POTION));
		
		BrewingRecipeRegistry.addRecipe(radPotion, new ItemStack(Items.GOLDEN_CARROT), PotionUtils.addPotionToItemStack(new ItemStack(Items.POTIONITEM), TGRadiationSystem.RAD_REGENERATION_POTION));
		BrewingRecipeRegistry.addRecipe(radPotion, new ItemStack(Items.SPECKLED_MELON), PotionUtils.addPotionToItemStack(new ItemStack(Items.POTIONITEM), TGRadiationSystem.RAD_REGENERATION_POTION));
	
		/**
		 * Grinder recipes
		 */
		
		GrinderRecipes.addGenericArmorRecipes();
		
		//some pre defined things
		ItemStack[] BARREL_STONE = {new ItemStack(Blocks.COBBLESTONE,3)};
		ItemStack[] BARREL_IRON = {new ItemStack(Items.IRON_INGOT,3)};
		ItemStack[] BARREL_OBSI = {TGItems.newStack(TGItems.INGOT_OBSIDIAN_STEEL, 3)};
		ItemStack[] BARREL_CARBON = {TGItems.newStack(TGItems.CARBON_FIBERS, 3)};
		
		ItemStack[] RECEIVER_OBSI = {TGItems.newStack(TGItems.INGOT_OBSIDIAN_STEEL, 3)};
		
		GrinderRecipes.addRecipe(TGuns.handcannon, BARREL_STONE, ironAndWood(0,1,4));
		GrinderRecipes.addRecipe(TGuns.sawedoff, ironAndWood(4,1,0), new ItemStack[] {new ItemStack(Items.FLINT,1)});
		GrinderRecipes.addRecipe(TGuns.revolver, ironAndWood(3,1,0));
		GrinderRecipes.addRecipe(TGuns.goldenrevolver, ironAndWood(3,1,0), new ItemStack[] {new ItemStack(Items.GOLD_INGOT,4)});
		GrinderRecipes.addRecipe(TGuns.thompson, ironAndWood(4,1,0));
		GrinderRecipes.addRecipe(TGuns.ak47, BARREL_OBSI, ironAndWood(4,1,0));
		GrinderRecipes.addRecipe(TGuns.boltaction, BARREL_OBSI, ironAndWood(4,1,0));
		GrinderRecipes.addRecipe(TGuns.m4, BARREL_OBSI, steelAndPlastic(2,2,0));
		GrinderRecipes.addRecipe(TGuns.m4_infiltrator, BARREL_OBSI, steelAndPlastic(3,2,0), new ItemStack[] {new ItemStack(Items.REDSTONE,1)});
		GrinderRecipes.addRecipe(TGuns.pistol, BARREL_OBSI, steelAndPlastic(0, 1, 0), ironAndWood(2,0,0));
		GrinderRecipes.addRecipe(TGuns.combatshotgun, BARREL_OBSI, steelAndPlastic(2, 2, 0));
		GrinderRecipes.addRecipe(TGuns.mac10, BARREL_OBSI, steelAndPlastic(1,0,5), ironAndWood(1,0,0));
		GrinderRecipes.addRecipe(TGuns.flamethrower, steelAndPlastic(0, 2, 0), ironAndWood(2, 0, 0));
		GrinderRecipes.addRecipe(TGuns.rocketlauncher, new ItemStack[] {TGItems.newStack(TGItems.INGOT_OBSIDIAN_STEEL,6)}, steelAndPlastic(1, 0, 5));
		GrinderRecipes.addRecipe(TGuns.grimreaper, new ItemStack[] {TGItems.newStack(TGItems.INGOT_OBSIDIAN_STEEL, 32), TGItems.newStack(TGItems.CIRCUIT_BOARD_ELITE, 1), TGItems.newStack(TGItems.CARBON_FIBERS, 1)});
		GrinderRecipes.addRecipe(TGuns.grenadelauncher, BARREL_OBSI, steelAndPlastic(2, 2, 0));
		GrinderRecipes.addRecipe(TGuns.aug, BARREL_OBSI, steelAndPlastic(2, 2, 0));
		GrinderRecipes.addRecipe(TGuns.netherblaster, new ItemStack[] {TGItems.newStack(TGItems.INGOT_OBSIDIAN_STEEL, 4), TGItems.newStack(TGItems.CYBERNETIC_PARTS, 2)});
		GrinderRecipes.addRecipe(TGuns.biogun, steelAndPlastic(2, 2, 0), new ItemStack[] {new ItemStack(Blocks.GLASS,2)});
		GrinderRecipes.addRecipe(TGuns.teslagun, carbonPlasticAndRedstone(3, 2, 2));
		GrinderRecipes.addRecipe(TGuns.lmg, new ItemStack[] {TGItems.newStack(TGItems.INGOT_OBSIDIAN_STEEL, 6), TGItems.newStack(TGItems.INGOT_STEEL, 4), TGItems.newStack(TGItems.PLASTIC_SHEET, 2)});
		GrinderRecipes.addRecipe(TGuns.minigun, new ItemStack[] {TGItems.newStack(TGItems.INGOT_OBSIDIAN_STEEL, 20), TGItems.newStack(TGItems.INGOT_STEEL, 2), TGItems.newStack(TGItems.ELECTRIC_ENGINE, 1)});
		GrinderRecipes.addRecipe(TGuns.as50, new ItemStack[] {TGItems.newStack(TGItems.INGOT_OBSIDIAN_STEEL, 9), TGItems.newStack(TGItems.PLASTIC_SHEET, 2), new ItemStack(Items.DIAMOND, 1)});
		GrinderRecipes.addRecipe(TGuns.vector, obsiAndPlastic(5, 2));
		GrinderRecipes.addRecipe(TGuns.scar, obsiAndPlastic(5, 2));
		GrinderRecipes.addRecipe(TGuns.lasergun, obsiAndPlastic(2, 2), new ItemStack[] {new ItemStack(Items.REDSTONE,20), TGItems.newStack(GOLD_OR_ELECTRUM, 3)});
		GrinderRecipes.addRecipe(TGuns.blasterrifle, carbonPlasticAndRedstone(3, 1, 20), new ItemStack[] {TGItems.newStack(GOLD_OR_ELECTRUM, 3)});
	//TODO: BlasterShotgun NoREcipe
		GrinderRecipes.addRecipe(TGuns.sonicshotgun, carbonPlasticAndRedstone(3, 2, 2), new ItemStack[] {TGItems.newStack(TGItems.INGOT_TITANIUM, 1)});
		GrinderRecipes.addRecipe(TGuns.pdw, carbonPlasticAndRedstone(6, 1, 0), obsiAndPlastic(1, 0));
		GrinderRecipes.addRecipe(TGuns.pulserifle, carbonPlasticAndRedstone(6, 1, 0), obsiAndPlastic(1, 0));
		GrinderRecipes.addRecipe(TGuns.mibgun,  new ItemStack[] {TGItems.newStack(GOLD_OR_ELECTRUM, 3), TGItems.newStack(TGItems.INGOT_TITANIUM, 1), new ItemStack(Items.REDSTONE,20)});
		//TODO: AlienBlaster norecipe
		GrinderRecipes.addRecipe(TGuns.powerhammer, ironAndWood(4,0,0), new ItemStack[] {new ItemStack(Items.FLINT,1)});
		GrinderRecipes.addRecipe(TGuns.chainsaw, ironAndWood(5,0,0), new ItemStack[] {TGItems.newStack(TGItems.PLASTIC_SHEET, 1)});
		GrinderRecipes.addRecipe(TGuns.nucleardeathray, new ItemStack[] {TGItems.newStack(TGItems.CARBON_FIBERS, 6), TGItems.newStack(TGItems.INGOT_LEAD, 3), TGItems.newStack(TGItems.MECHANICAL_PARTS_CARBON,1), TGItems.newStack(TGItems.CIRCUIT_BOARD_ELITE, 1)});
		GrinderRecipes.addRecipe(TGuns.gaussrifle, new ItemStack[] {TGItems.newStack(TGItems.CARBON_FIBERS, 9), TGItems.newStack(TGItems.INGOT_TITANIUM, 1), TGItems.newStack(TGItems.PLASTIC_SHEET, 1), TGItems.newStack(TGItems.CIRCUIT_BOARD_ELITE, 1)});
		GrinderRecipes.addRecipe(TGuns.guidedmissilelauncher, new ItemStack[] {TGItems.newStack(TGItems.INGOT_OBSIDIAN_STEEL, 8), TGItems.newStack(TGItems.WIRE_COPPER, 1)});
		GrinderRecipes.addRecipe(TGuns.miningdrill, obsiAndPlastic(6, 2));
		GrinderRecipes.addRecipe(TGuns.tfg, new ItemStack[] {TGItems.newStack(TGItems.INGOT_TITANIUM, 5), TGItems.newStack(TGItems.INGOT_LEAD,4), TGItems.newStack(TGItems.CARBON_FIBERS, 4)});
	
		GrinderRecipes.addRecipe(TGItems.PLATE_CARBON, new ItemStack[] {TGItems.newStack(TGItems.CARBON_FIBERS, 1)});
		
		//Ammo crush
		GrinderRecipes.addRecipeChance(TGItems.RIFLE_ROUNDS, new ItemStack[] {TGItems.newStack(TGItems.NUGGET_LEAD, 1), TGItems.newStack(TGItems.NUGGET_COPPER, 2), new ItemStack(Items.GUNPOWDER)}, new double[] {1d,1d,0.125d});
		GrinderRecipes.addRecipeChance(TGItems.PISTOL_ROUNDS, new ItemStack[] {TGItems.newStack(TGItems.NUGGET_LEAD, 1), TGItems.newStack(TGItems.NUGGET_COPPER, 1), new ItemStack(Items.GUNPOWDER)}, new double[] {0.75d,1.5d,1.0d/12.0d});
		GrinderRecipes.addRecipeChance(TGItems.SNIPER_ROUNDS, new ItemStack[] {TGItems.newStack(TGItems.NUGGET_LEAD, 2), TGItems.newStack(TGItems.NUGGET_COPPER, 4), new ItemStack(Items.GUNPOWDER)}, new double[] {1d,1d,0.25d});
		GrinderRecipes.addRecipeChance(TGItems.SHOTGUN_ROUNDS, new ItemStack[] {TGItems.newStack(TGItems.NUGGET_LEAD, 1), TGItems.newStack(TGItems.NUGGET_COPPER, 1), new ItemStack(Items.GUNPOWDER)}, new double[] {0.5d,1d,0.0625d});
		
		GrinderRecipes.addRecipeChance(TGItems.RIFLE_ROUNDS_INCENDIARY, new ItemStack[] {TGItems.newStack(TGItems.NUGGET_LEAD, 1), TGItems.newStack(TGItems.NUGGET_COPPER, 2), new ItemStack(Items.GUNPOWDER), new ItemStack(Items.BLAZE_POWDER)}, new double[] {1d,1d,0.125d, 0.125d});
		GrinderRecipes.addRecipeChance(TGItems.PISTOL_ROUNDS_INCENDIARY, new ItemStack[] {TGItems.newStack(TGItems.NUGGET_LEAD, 1), TGItems.newStack(TGItems.NUGGET_COPPER, 1), new ItemStack(Items.GUNPOWDER), new ItemStack(Items.BLAZE_POWDER)}, new double[] {0.75d,1.5d,1.0d/12.0d, 0.125d});
		GrinderRecipes.addRecipeChance(TGItems.SNIPER_ROUNDS_INCENDIARY, new ItemStack[] {TGItems.newStack(TGItems.NUGGET_LEAD, 2), TGItems.newStack(TGItems.NUGGET_COPPER, 4), new ItemStack(Items.GUNPOWDER), new ItemStack(Items.BLAZE_POWDER)}, new double[] {1d,1d,0.25d, 0.125d});
		GrinderRecipes.addRecipeChance(TGItems.SHOTGUN_ROUNDS_INCENDIARY, new ItemStack[] {TGItems.newStack(TGItems.NUGGET_LEAD, 1), TGItems.newStack(TGItems.NUGGET_COPPER, 1), new ItemStack(Items.GUNPOWDER), new ItemStack(Items.BLAZE_POWDER)}, new double[] {0.5d,1d,0.0625d, 0.125d});
		
		
		/**
		 * Upgrade bench recipes
		 */
		//UpgradeBenchRecipes.addRecipe(TGItems.PLATE_CARBON, Enchantments.PROTECTION, 4);
		
		UpgradeBenchRecipes.addRecipe(TGItems.UPGRADE_PROTECTION_1, Enchantments.PROTECTION, 1);
		UpgradeBenchRecipes.addRecipe(TGItems.UPGRADE_PROJECTILE_PROTECTION_1, Enchantments.PROJECTILE_PROTECTION, 1);
		UpgradeBenchRecipes.addRecipe(TGItems.UPGRADE_BLAST_PROTECTION_1, Enchantments.BLAST_PROTECTION, 1);
		
		UpgradeBenchRecipes.addRecipe(TGItems.UPGRADE_PROTECTION_2, Enchantments.PROTECTION, 2);
		UpgradeBenchRecipes.addRecipe(TGItems.UPGRADE_PROJECTILE_PROTECTION_2, Enchantments.PROJECTILE_PROTECTION, 2);
		UpgradeBenchRecipes.addRecipe(TGItems.UPGRADE_BLAST_PROTECTION_2, Enchantments.BLAST_PROTECTION, 2);
		
		UpgradeBenchRecipes.addRecipe(TGItems.UPGRADE_PROTECTION_3, Enchantments.PROTECTION, 3);
		UpgradeBenchRecipes.addRecipe(TGItems.UPGRADE_PROJECTILE_PROTECTION_3, Enchantments.PROJECTILE_PROTECTION, 3);
		UpgradeBenchRecipes.addRecipe(TGItems.UPGRADE_BLAST_PROTECTION_3, Enchantments.BLAST_PROTECTION, 3);
	}	
	
	
	private static ItemStack[] ironAndWood(int iron, int wood, int nuggets) {
		int size = (iron>0?1:0)+(wood>0?1:0)+(nuggets>0?1:0);
		ItemStack[] ret = new ItemStack[size];
		int i =0;
		if(iron>0) {
			ret[i++]=new ItemStack(Items.IRON_INGOT, iron);
		}
		if(wood>0) {
			ret[i++]=new ItemStack(Blocks.LOG, wood);
		}
		if(nuggets>0) {
			ret[i++]=new ItemStack(Items.IRON_NUGGET, nuggets);
		}
		return ret;
	}
	
	private static ItemStack[] steelAndPlastic(int steel, int plastic, int nuggets) {
		int size = (steel>0?1:0)+(plastic>0?1:0)+(nuggets>0?1:0);
		ItemStack[] ret = new ItemStack[size];
		int i =0;
		if(steel>0) {
			ret[i++]=TGItems.newStack(TGItems.INGOT_STEEL, steel);
		}
		if(plastic>0) {
			ret[i++]=TGItems.newStack(TGItems.PLASTIC_SHEET, plastic);
		}
		if(nuggets>0) {
			ret[i++]=TGItems.newStack(TGItems.INGOT_STEEL, nuggets);
		}
		return ret;
	}
	
	private static ItemStack[] obsiAndPlastic(int steel, int plastic) {
		int size = (steel>0?1:0)+(plastic>0?1:0);
		ItemStack[] ret = new ItemStack[size];
		int i =0;
		if(steel>0) {
			ret[i++]=TGItems.newStack(TGItems.INGOT_OBSIDIAN_STEEL, steel);
		}
		if(plastic>0) {
			ret[i++]=TGItems.newStack(TGItems.PLASTIC_SHEET, plastic);
		}
		return ret;
	}
	
	private static ItemStack[] carbonPlasticAndRedstone(int carbon, int plastic, int redstone) {
		int size = (carbon>0?1:0)+(plastic>0?1:0)+(redstone>0?1:0);
		ItemStack[] ret = new ItemStack[size];
		int i =0;
		if(carbon>0) {
			ret[i++]=TGItems.newStack(TGItems.CARBON_FIBERS, carbon);
		}
		if(plastic>0) {
			ret[i++]=TGItems.newStack(TGItems.PLASTIC_SHEET, plastic);
		}
		if(redstone>0) {
			ret[i++]=new ItemStack(Items.REDSTONE, redstone);
		}
		return ret;
	}
}