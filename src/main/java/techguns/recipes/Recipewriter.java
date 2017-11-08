package techguns.recipes;

import static techguns.TGItems.*;
import static techguns.TGuns.ak47;
import static techguns.TGuns.as50;
import static techguns.TGuns.aug;
import static techguns.TGuns.biogun;
import static techguns.TGuns.blasterrifle;
import static techguns.TGuns.boltaction;
import static techguns.TGuns.chainsaw;
import static techguns.TGuns.combatshotgun;
import static techguns.TGuns.flamethrower;
import static techguns.TGuns.fraggrenade;
import static techguns.TGuns.goldenrevolver;
import static techguns.TGuns.grenadelauncher;
import static techguns.TGuns.grimreaper;
import static techguns.TGuns.guidedmissilelauncher;
import static techguns.TGuns.handcannon;
import static techguns.TGuns.lasergun;
import static techguns.TGuns.lmg;
import static techguns.TGuns.m4;
import static techguns.TGuns.m4_infiltrator;
import static techguns.TGuns.mac10;
import static techguns.TGuns.mibgun;
import static techguns.TGuns.minigun;
import static techguns.TGuns.netherblaster;
import static techguns.TGuns.nucleardeathray;
import static techguns.TGuns.pdw;
import static techguns.TGuns.pistol;
import static techguns.TGuns.powerhammer;
import static techguns.TGuns.pulserifle;
import static techguns.TGuns.revolver;
import static techguns.TGuns.rocketlauncher;
import static techguns.TGuns.sawedoff;
import static techguns.TGuns.scar;
import static techguns.TGuns.sonicshotgun;
import static techguns.TGuns.stielgranate;
import static techguns.TGuns.teslagun;
import static techguns.TGuns.thompson;
import static techguns.TGuns.vector;

import java.util.Arrays;

import static techguns.TGArmors.*;

import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.OreDictionary;
import techguns.TGBlocks;
import techguns.TGItems;
import techguns.TGuns;
import techguns.Techguns;
import techguns.blocks.BlockTGLadder;
import techguns.blocks.EnumLadderType;
import techguns.blocks.TGMetalPanelType;
import techguns.blocks.machines.EnumMachineType;
import techguns.blocks.machines.EnumMultiBlockMachineType;
import techguns.blocks.machines.EnumSimpleMachineType;
import techguns.items.guns.GenericGun;
import techguns.items.guns.ammo.AmmoType;
import techguns.items.guns.ammo.AmmoTypes;

public class Recipewriter {

	public static final String hardenedGlassOrGlass = "hardenedGlassOrGlass";
	public static final String electrumOrGold = "electrumOrGold";
	public static final String itemStackHasNBTInt = "itemstackHasNBTInt";
	
	public static void writeRecipes() {
		
		registerItemRecipes();
		
		
		RecipeJsonConverter.generateConstants();
		RecipeJsonConverter.generateFactories();
		
	}
	
	public static void registerItemRecipes(){
		RecipeJsonConverter.addShapedRecipe(newStack(BARREL_STONE,1), "sss","   ","sss", 's', "stone");
        RecipeJsonConverter.addShapedRecipe(newStack(STOCK_WOOD,1), "ww", " w", 'w', "logWood");
        
        RecipeJsonConverter.addShapedRecipe(newStack(BARREL_IRON,1), "iii","   ","iii", 'i', "ingotIron");
   
        RecipeJsonConverter.addShapedRecipe(newStack(MECHANICAL_PARTS_IRON,1)," i ", "ifi", " i ", 'f', Items.FLINT, 'i', "ingotIron");
        
        RecipeJsonConverter.addShapedRecipe(newStack(STOCK_PLASTIC,1),"ppp","  p", 'p', "sheetPlastic");
             
        RecipeJsonConverter.addShapedRecipe(newStack(BARREL_OBSIDIAN_STEEL, 1), "ooo","   ","ooo", 'o', INGOT_OBSIDIAN_STEEL);
        
        RecipeJsonConverter.addShapedRecipe(newStack(PUMP_MECHANISM, 1), "nnn", "gpg", "nnn", 'n', "nuggetIron", 'g', "blockGlass", 'p', Blocks.PISTON);
        
        RecipeJsonConverter.addShapedRecipe(newStack(BARREL_LASER,1), "fff","ggl","fff", 'f', electrumOrGold, 'g', hardenedGlassOrGlass, 'l', LASER_FOCUS);
        
        RecipeJsonConverter.addShapedRecipe(newStack(HEAVY_CLOTH,3), " w ","wlw"," w ", 'w', Blocks.WOOL, 'l', Items.LEATHER);
        
        
        RecipeJsonConverter.addShapelessRecipe(newStack(STONE_BULLETS,16), "cobblestone", "cobblestone", "cobblestone", Items.GUNPOWDER);
        
        RecipeJsonConverter.addShapedRecipe(newStack(PISTOL_ROUNDS,8), "clc","cgc","ccc", 'c', "nuggetCopper", 'l', "ingotLead", 'g', Items.GUNPOWDER);
        
        RecipeJsonConverter.addShapedRecipe(newStack(SHOTGUN_ROUNDS,5), "lll","cgc","ccc", 'c', "nuggetCopper", 'l', "nuggetLead", 'g', Items.GUNPOWDER);
        
        
        RecipeJsonConverter.addShapedRecipe(newStack(PISTOL_MAGAZINE_EMPTY,4), "i i","imi","igi", 'i', "nuggetIron",'g', "ingotIron", 'm', MECHANICAL_PARTS_IRON);
        RecipeJsonConverter.addShapelessRecipe(newStack(PISTOL_MAGAZINE, 1), PISTOL_MAGAZINE_EMPTY, PISTOL_ROUNDS,PISTOL_ROUNDS,PISTOL_ROUNDS);
    
        RecipeJsonConverter.addShapedRecipe(newStack(SMG_MAGAZINE_EMPTY,4), "i i","i i","imi", 'i', "nuggetIron", 'm', MECHANICAL_PARTS_IRON);
        RecipeJsonConverter.addShapelessRecipe(newStack(SMG_MAGAZINE, 1), SMG_MAGAZINE_EMPTY, PISTOL_ROUNDS, PISTOL_ROUNDS);
       
        RecipeJsonConverter.addShapedRecipe(newStack(ASSAULTRIFLE_MAGAZINE_EMPTY,4),"s s","s s","sms", 's', "nuggetSteel",'m', MECHANICAL_PARTS_IRON);
        
        RecipeJsonConverter.addShapelessRecipe(newStack(ASSAULTRIFLE_MAGAZINE, 1), ASSAULTRIFLE_MAGAZINE_EMPTY, RIFLE_ROUNDS, RIFLE_ROUNDS, RIFLE_ROUNDS);
        
       
        RecipeJsonConverter.addShapedRecipe(newStack(BIO_TANK_EMPTY,1),"sss","sgs","sgs", 's', "nuggetSteel", 'g', "blockGlass");
 
        RecipeJsonConverter.addShapedRecipe(newStack(BIOMASS,4)," g ","gbg"," g ", 'g', "dyeGreen", 'b', "slimeball");
        
        RecipeJsonConverter.addShapedRecipe(newStack(ROCKET,4), " s ","sts","sgs", 's', "nuggetIron", 't', Blocks.TNT, 'g', Items.GUNPOWDER);
        
        RecipeJsonConverter.addShapedRecipe(newStack(LMG_MAGAZINE_EMPTY,4), " ii", "imi","iii", 'i', "ingotSteel", 'm', MECHANICAL_PARTS_IRON);
        RecipeJsonConverter.addShapedRecipe(newStack(LMG_MAGAZINE,1), "bbb", "bmb","bbb", 'b', RIFLE_ROUNDS, 'm', LMG_MAGAZINE_EMPTY);
        RecipeJsonConverter.addShapelessRecipe(newStack(LMG_MAGAZINE,1), LMG_MAGAZINE_EMPTY, RIFLE_ROUNDS_STACK, RIFLE_ROUNDS_STACK);
        
        RecipeJsonConverter.addShapedRecipe(newStack(AS50_MAGAZINE_EMPTY,4),"s s","s s","sms", 's', "ingotSteel", 'm', MECHANICAL_PARTS_IRON);
     
        RecipeJsonConverter.addShapelessRecipe(newStack(AS50_MAGAZINE,1),AS50_MAGAZINE_EMPTY, SNIPER_ROUNDS, SNIPER_ROUNDS);
        
        RecipeJsonConverter.addShapedRecipe(newStack(RECEIVER_IRON,1), "iii"," mn","  n", 'i', "ingotIron", 'n', "nuggetIron",'m', MECHANICAL_PARTS_IRON);
        RecipeJsonConverter.addShapedRecipe(newStack(RECEIVER_STEEL,1), "iii"," mn","  n", 'i', "ingotSteel", 'n', "nuggetSteel",'m', MECHANICAL_PARTS_OBSIDIAN_STEEL);
        RecipeJsonConverter.addShapedRecipe(newStack(RECEIVER_OBSIDIAN_STEEL,1), "ppp"," mi","  i", 'i', "ingotObsidianSteel", 'p', "plateObsidianSteel",'m', MECHANICAL_PARTS_CARBON);
        RecipeJsonConverter.addShapedRecipe(newStack(RECEIVER_CARBON,1), "ppp"," mi","  i", 'i', "ingotObsidianSteel", 'p', "plateCarbon",'m', MECHANICAL_PARTS_CARBON);
        

        
        RecipeJsonConverter.addShapedRecipe(newStack(ADVANCED_MAGAZINE_EMPTY,4),"i i","i i","imi",'i', "ingotObsidianSteel", 'm',MECHANICAL_PARTS_OBSIDIAN_STEEL);
        RecipeJsonConverter.addShapelessRecipe(newStack(ADVANCED_MAGAZINE,1), ADVANCED_MAGAZINE_EMPTY, ADVANCED_ROUNDS, ADVANCED_ROUNDS, ADVANCED_ROUNDS);
        
        RecipeJsonConverter.addShapedRecipe(newStack(WIRE_COPPER,1)," cc"," c ","cc ",'c', "nuggetCopper");
        
        RecipeJsonConverter.addShapedRecipe(newStack(ELECTRIC_ENGINE,1), "wrw","imi","wrw", 'w', "wireCopper", 'i', "ingotIron", 'r', "dustRedstone", 'm', MECHANICAL_PARTS_IRON);
	
        RecipeJsonConverter.addShapedRecipe(newStack(TGItems.CIRCUIT_BOARD_BASIC,4),"cgc","rir","cgc", 'i', "plateIron", 'c', "wireCopper", 'g', "dyeGreen", 'r', "dustRedstone");
	
       
        RecipeJsonConverter.addShapedRecipe(newStack(BARREL_CARBON,1), "iii","   ","iii", 'i', "plateCarbon");
        RecipeJsonConverter.addShapedRecipe(newStack(STOCK_CARBON,1), "iii"," pp", 'i', "plateCarbon", 'p', "sheetPlastic");
        
      
        RecipeJsonConverter.addShapelessRecipe(newStack(CIRCUIT_BOARD_ELITE,1), "circuitBasic", "wireGold", "gemLapis");
        
        RecipeJsonConverter.addShapedRecipe(newStack(FUEL_TANK_EMPTY,4), "p","g","p", 'g', "blockGlass", 'p', "sheetPlastic");
        
       
        RecipeJsonConverter.addShapelessRecipe(newStack(RIFLE_ROUNDS_STACK,1), RIFLE_ROUNDS,RIFLE_ROUNDS,RIFLE_ROUNDS,RIFLE_ROUNDS);
        RecipeJsonConverter.addShapelessRecipe(newStack(RIFLE_ROUNDS,4), RIFLE_ROUNDS_STACK);
        
        RecipeJsonConverter.addShapedRecipe(newStack(MINIGUN_DRUM_EMPTY,4), "sss","pmp","sss",'s', "ingotSteel", 'p', "sheetPlastic", 'm', MECHANICAL_PARTS_OBSIDIAN_STEEL);
        RecipeJsonConverter.addShapelessRecipe(newStack(MINIGUN_DRUM,1),MINIGUN_DRUM_EMPTY, RIFLE_ROUNDS_STACK,RIFLE_ROUNDS_STACK,RIFLE_ROUNDS_STACK,RIFLE_ROUNDS_STACK);
	
        RecipeJsonConverter.addShapedRecipe(newStack(COIL,1), " wi","wiw","iw ", 'i', "ingotIron", 'w', "wireCopper");
        
        RecipeJsonConverter.addShapedRecipe(newStack(MACHINE_UPGRADE_STACK,1), "pgp","ncn","pnp", 'p', "plateIron", 'n', "ingotGold",'g', "dyeGreen", 'c', new ItemStack(Blocks.CHEST,1));
    	
        RecipeJsonConverter.addShapedRecipe(newStack(TURRET_ARMOR_IRON,1),"ppp"," p "," p ", 'p', "plateIron");
        RecipeJsonConverter.addShapedRecipe(newStack(TURRET_ARMOR_STEEL,1),"ppp"," p "," p ", 'p', "plateSteel");
        RecipeJsonConverter.addShapedRecipe(newStack(TURRET_ARMOR_OBSIDIAN_STEEL,1),"ppp"," p "," p ", 'p', "plateObsidianSteel");
        RecipeJsonConverter.addShapedRecipe(newStack(TURRET_ARMOR_CARBON,1),"ppp"," p "," p ", 'p', "plateCarbon");
        
        RecipeJsonConverter.addShapedRecipe(newStack(GLIDER_BACKBACK,1), "hhh","shs","hhh", 'h', TGItems.HEAVY_CLOTH, 's', "ingotIron");
        RecipeJsonConverter.addShapedRecipe(newStack(GLIDER_WING,1), "sss","hhh","hhh", 'h', TGItems.HEAVY_CLOTH, 's', "ingotIron");
        RecipeJsonConverter.addShapelessRecipe(new ItemStack(GLIDER,1), TGItems.GLIDER_BACKBACK, TGItems.GLIDER_WING, TGItems.GLIDER_WING);
  
        RecipeJsonConverter.addShapedRecipe(newStack(TGItems.GAS_MASK_FILTER,1), "iii","ici","iii", 'i', "nuggetIron", 'c', Items.COAL);
        RecipeJsonConverter.addShapedRecipe(new ItemStack(TGItems.GAS_MASK,1), "grg","rrr","rfr", 'r', "itemRubber", 'f', TGItems.GAS_MASK_FILTER, 'g', "paneGlass");
        RecipeJsonConverter.addShapelessRecipe(new ItemStack(TGItems.GAS_MASK,1,0), new ItemStack(TGItems.GAS_MASK,1,OreDictionary.WILDCARD_VALUE), TGItems.GAS_MASK_FILTER);
    
        RecipeJsonConverter.addShapedRecipe(new ItemStack(NIGHTVISION_GOGGLES,1), "rhr","cec","gsg", 'r', "itemRubber", 'h', TGItems.HEAVY_CLOTH, 'c',"circuitBasic",'e', TGItems.REDSTONE_BATTERY, 'g', "blockGlass", 's', "ingotIron");
        RecipeJsonConverter.addShapedRecipe(new ItemStack(NIGHTVISION_GOGGLES,1,NIGHTVISION_GOGGLES.getMaxDamage()), "rhr","cec","gsg", 'r', "itemRubber", 'h', TGItems.HEAVY_CLOTH, 'c',"circuitBasic",'e', TGItems.REDSTONE_BATTERY_EMPTY, 'g', "blockGlass", 's', "ingotIron");
    
        RecipeJsonConverter.addShapedRecipe(new ItemStack(TGItems.JETPACK,1,0), "f f","pgp","rmr",'f', TGItems.FUEL_TANK, 'p', "plateObsidianSteel", 'g', TGItems.GLIDER, 'r', TGItems.ROCKET, 'm', MECHANICAL_PARTS_OBSIDIAN_STEEL);
        RecipeJsonConverter.addShapedRecipe(new ItemStack(TGItems.JETPACK,1,0), "f f","pgp","rmr",'f', TGItems.FUEL_TANK, 'p', "plateObsidianSteel", 'g', new ItemStack(TGItems.JUMPPACK,1,OreDictionary.WILDCARD_VALUE), 'r', TGItems.ROCKET, 'm', MECHANICAL_PARTS_OBSIDIAN_STEEL);
        
        RecipeJsonConverter.addShapedRecipe(new ItemStack(TGItems.ANTI_GRAV_PACK,1,0), "tet","cac","mbm",'t', "plateTitanium", 'e', TGItems.NUCLEAR_POWERCELL, 'b', TGItems.GLIDER_BACKBACK, 'c', "circuitElite", 'm', MECHANICAL_PARTS_CARBON, 'a', TGItems.ANTI_GRAV_CORE);
        
        
        RecipeJsonConverter.addShapedRecipe(new ItemStack(TGItems.SCUBA_TANKS,1,0), "p p","aia","p p", 'p', "sheetPlastic", 'a', TGItems.COMPRESSED_AIR_TANK, 'i', "plateIron");
        
        RecipeJsonConverter.addShapedRecipe(new ItemStack(TGItems.JUMPPACK,1,0), "n n","pbp","c c", 'p', "plateIron", 'c', TGItems.COMPRESSED_AIR_TANK, 'n', "nuggetIron", 'b', TGItems.newStack(TGItems.GLIDER_BACKBACK, 1));
           
        RecipeJsonConverter.addShapedRecipe(newStack(OXYGEN_MASK,1), " p ","rpr", 'p', "sheetPlastic", 'r', "itemRubber");
        
        RecipeJsonConverter.addShapedRecipe(new ItemStack(TGItems.TACTICAL_MASK,1,0),"ngo","cpc","pep", 'p', "sheetPlastic", 'n', new ItemStack(TGItems.NIGHTVISION_GOGGLES,1,OreDictionary.WILDCARD_VALUE), 'c', "circuitBasic", 'g', new ItemStack(TGItems.GAS_MASK,1,OreDictionary.WILDCARD_VALUE), 'o', TGItems.OXYGEN_MASK, 'e', TGItems.ENERGY_CELL);
        RecipeJsonConverter.addShapedRecipe(new ItemStack(TGItems.TACTICAL_MASK,1,0),"ogn","cpc","pep", 'p', "sheetPlastic", 'n', new ItemStack(TGItems.NIGHTVISION_GOGGLES,1,OreDictionary.WILDCARD_VALUE), 'c', "circuitBasic", 'g', new ItemStack(TGItems.GAS_MASK,1,OreDictionary.WILDCARD_VALUE), 'o', TGItems.OXYGEN_MASK, 'e', TGItems.ENERGY_CELL);
        
        
        RecipeJsonConverter.addShapedRecipe(new ItemStack(TGItems.COMBAT_KNIFE,1), " s","p ", 's', "plateSteel", 'p', "sheetPlastic");
        
        RecipeJsonConverter.addShapedRecipe(TGItems.newStack(TGItems.RC_HEAT_RAY,1), "cwc","plp","plp", 'c', "circuitElite", 'w', "wireGold", 'p', "plateSteel", 'l', Blocks.REDSTONE_LAMP);
	
        //ingots/nugget
        RecipeJsonConverter.addShapelessRecipe(TGItems.INGOT_COPPER, "nuggetCopper","nuggetCopper","nuggetCopper","nuggetCopper","nuggetCopper","nuggetCopper","nuggetCopper","nuggetCopper","nuggetCopper");
        RecipeJsonConverter.addShapelessRecipe(TGItems.INGOT_LEAD, "nuggetLead","nuggetLead","nuggetLead","nuggetLead","nuggetLead","nuggetLead","nuggetLead","nuggetLead","nuggetLead");
        RecipeJsonConverter.addShapelessRecipe(TGItems.INGOT_STEEL, "nuggetSteel","nuggetSteel","nuggetSteel","nuggetSteel","nuggetSteel","nuggetSteel","nuggetSteel","nuggetSteel","nuggetSteel");
        
        RecipeJsonConverter.addShapelessRecipe(TGItems.newStack(TGItems.NUGGET_COPPER, 9), "ingotCopper");
        RecipeJsonConverter.addShapelessRecipe(TGItems.newStack(TGItems.NUGGET_LEAD, 9), "ingotLead");
        RecipeJsonConverter.addShapelessRecipe(TGItems.newStack(TGItems.NUGGET_STEEL, 9), "ingotSteel");
        
        ItemStack rc = new ItemStack(TGBlocks.MULTIBLOCK_MACHINE,1, EnumMultiBlockMachineType.REACTIONCHAMBER_HOUSING.getIndex());
        RecipeJsonConverter.addShapedRecipe(new ItemStack(TGBlocks.MULTIBLOCK_MACHINE,9,EnumMultiBlockMachineType.REACTIONCHAMBER_HOUSING.getIndex()), "sms","pcp","ses", 's', "plateSteel", 'm', MECHANICAL_PARTS_CARBON, 'p', TGItems.CYBERNETIC_PARTS, 'e', "circuitElite",'c', new ItemStack(TGBlocks.BASIC_MACHINE,1, EnumMachineType.CHEM_LAB.getIndex()));
        RecipeJsonConverter.addShapedRecipe(new ItemStack(TGBlocks.MULTIBLOCK_MACHINE,1,EnumMultiBlockMachineType.REACTIONCHAMBER_CONTROLLER.getIndex()), " g ","crc"," g ", 'g', hardenedGlassOrGlass, 'c', "circuitElite",  'r', rc);
        RecipeJsonConverter.addShapedRecipe(new ItemStack(TGBlocks.MULTIBLOCK_MACHINE,6,EnumMultiBlockMachineType.REACTIONCHAMBER_GLASS.getIndex()), "rgr","rgr","rgr", 'r', rc, 'g', hardenedGlassOrGlass);
        
        ItemStack fab = new ItemStack(TGBlocks.MULTIBLOCK_MACHINE,1, EnumMultiBlockMachineType.FABRICATOR_HOUSING.getIndex());
        RecipeJsonConverter.addShapedRecipe(new ItemStack(TGBlocks.MULTIBLOCK_MACHINE,4,EnumMultiBlockMachineType.FABRICATOR_HOUSING.getIndex()), "sms","pep","scs", 's', "plateSteel", 'm', MECHANICAL_PARTS_CARBON, 'p', TGItems.CYBERNETIC_PARTS, 'e', TGItems.ELECTRIC_ENGINE, 'c', "circuitElite");
        RecipeJsonConverter.addShapedRecipe(new ItemStack(TGBlocks.MULTIBLOCK_MACHINE,1,EnumMultiBlockMachineType.FABRICATOR_CONTROLLER.getIndex()), " g ","cfc"," g ", 'g', hardenedGlassOrGlass, 'c', "circuitElite",  'f', fab);
        RecipeJsonConverter.addShapedRecipe(new ItemStack(TGBlocks.MULTIBLOCK_MACHINE,4,EnumMultiBlockMachineType.FABRICATOR_GLASS.getIndex()), "fgf","g g","fgf", 'f', fab, 'g', hardenedGlassOrGlass);
     

        RecipeJsonConverter.addShapedRecipe(new ItemStack(TGItems.CROWBAR,1), " np", " p ", "p  ", 'p', "plateSteel", 'n', "nuggetSteel");
        
        RecipeJsonConverter.addShapedRecipe(new ItemStack(TGBlocks.BASIC_MACHINE,1,EnumMachineType.AMMO_PRESS.getIndex()), "ili","cec","iri", 'i', "ingotIron", 'c', "ingotCopper", 'l', "ingotLead",'e', TGItems.ELECTRIC_ENGINE, 'r', "dustRedstone");
        RecipeJsonConverter.addShapedRecipe(new ItemStack(TGBlocks.BASIC_MACHINE,1,EnumMachineType.METAL_PRESS.getIndex()), "ibi","iei","iri", 'i', "ingotIron", 'b', "blockIron",'e', TGItems.ELECTRIC_ENGINE, 'r', "dustRedstone");
        RecipeJsonConverter.addShapedRecipe(new ItemStack(TGBlocks.BASIC_MACHINE,1,EnumMachineType.METAL_PRESS.getIndex()), "ibi","iei","iri", 'i', "ingotIron", 'b', "plateIron",'e', TGItems.ELECTRIC_ENGINE, 'r', "dustRedstone");
        
        RecipeJsonConverter.addShapedRecipe(new ItemStack(TGBlocks.BASIC_MACHINE,1,EnumMachineType.TURRET.getIndex()), "pip","cec","prp", 'p', "plateIron", 'c', new ItemStack(Blocks.CONCRETE, 1, OreDictionary.WILDCARD_VALUE), 'e', TGItems.ELECTRIC_ENGINE, 'r', "dustRedstone", 'i', "circuitBasic");
        
       
        RecipeJsonConverter.addShapedRecipe(new ItemStack(TGBlocks.BASIC_MACHINE,1,EnumMachineType.CHEM_LAB.getIndex()), "igi","geg","iri", 'i', "ingotIron", 'g', new ItemStack(Items.GLASS_BOTTLE), 'e', TGItems.ELECTRIC_ENGINE, 'r', "dustRedstone");
       
        int repair_bench_meta = TGBlocks.SIMPLE_MACHINE.getMetaFromState(TGBlocks.SIMPLE_MACHINE.getDefaultState().withProperty(TGBlocks.SIMPLE_MACHINE.MACHINE_TYPE, EnumSimpleMachineType.REPAIR_BENCH));
        RecipeJsonConverter.addShapedRecipe(new ItemStack(TGBlocks.SIMPLE_MACHINE,1,repair_bench_meta), "pmp","ici","iii", 'm', MECHANICAL_PARTS_IRON, 'c', "workbench", 'i', "nuggetIron", 'p', "plateIron");
        
        int charging_station_meta = TGBlocks.SIMPLE_MACHINE.getMetaFromState(TGBlocks.SIMPLE_MACHINE.getDefaultState().withProperty(TGBlocks.SIMPLE_MACHINE.MACHINE_TYPE, EnumSimpleMachineType.CHARGING_STATION));    
        RecipeJsonConverter.addShapedRecipe(new ItemStack(TGBlocks.SIMPLE_MACHINE,1,charging_station_meta), "sgs","cbc","sgs", 's', "plateSteel", 'g', "wireGold", 'b', "circuitBasic", 'c', TGItems.COIL);
    	
        int camo_bench_meta = TGBlocks.SIMPLE_MACHINE.getMetaFromState(TGBlocks.SIMPLE_MACHINE.getDefaultState().withProperty(TGBlocks.SIMPLE_MACHINE.MACHINE_TYPE, EnumSimpleMachineType.CAMO_BENCH));      
        RecipeJsonConverter.addShapedRecipe(new ItemStack(TGBlocks.SIMPLE_MACHINE,1,camo_bench_meta), "ddd","ici","iii", 'd', "dye", 'c', "workbench", 'i', "nuggetIron");
        
        
        RecipeJsonConverter.addShapedRecipe(TGItems.newStack(TGItems.QUARTZ_ROD,1), "q  ", " q ", "  q", 'q', "gemQuartz");
        RecipeJsonConverter.addShapedRecipe(TGItems.newStack(TGItems.QUARTZ_ROD,1), "  q", " q ", "q  ", 'q', "gemQuartz");
        
        
        RecipeJsonConverter.addShapedRecipe(new ItemStack(TGBlocks.LAMP_0,16,0),"iii","grg","ggg",'i',"nuggetIron",'r', "dustRedstone",'g', "paneGlass");
        RecipeJsonConverter.addShapelessRecipe(new ItemStack(TGBlocks.LAMP_0,1,0), new ItemStack(TGBlocks.LAMP_0,1,6));
        RecipeJsonConverter.addShapelessRecipe(new ItemStack(TGBlocks.LAMP_0,1,6), new ItemStack(TGBlocks.LAMP_0,1,0));
        
        RecipeJsonConverter.addShapedRecipe(new ItemStack(TGBlocks.LAMP_0,16,12),"iii","grg","iii",'i',"nuggetIron",'r', "dustRedstone",'g', "paneGlass");
        RecipeJsonConverter.addShapelessRecipe(new ItemStack(TGBlocks.LAMP_0,1,13), new ItemStack(TGBlocks.LAMP_0,1,12));
        RecipeJsonConverter.addShapelessRecipe(new ItemStack(TGBlocks.LAMP_0,1,12), new ItemStack(TGBlocks.LAMP_0,1,13));
        
        RecipeJsonConverter.addShapelessRecipe(new ItemStack(TGBlocks.SANDBAGS,8), "itemRubber", "sand", "sand", "sand", "sand", "sand", "sand", "sand", "sand");
        
        RecipeJsonConverter.addShapedRecipe(new ItemStack(TGBlocks.CAMONET,8), "sds", "sds", 's', "stickWood", 'd', "dirt");
        RecipeJsonConverter.addShapedRecipe(new ItemStack(TGBlocks.CAMONET_TOP,16), "wdw","dsd", "wdw", 's', "string", 'd', "dirt", 'w', "stickWood");
 
        RecipeJsonConverter.addShapedRecipe(new ItemStack(TGItems.BUNKER_DOOR,2), "pp","pp","pp", 'p', "plateIron");
    
        RecipeJsonConverter.addShapedRecipe(TGItems.newStack(TGItems.TACTICAL_NUKE_WARHEAD, 2), "pcp","tut","pcp", 'p', "plateLead", 't', TGItems.TGX, 'c', "circuitBasic", 'u', "ingotUraniumEnriched");
        
        RecipeJsonConverter.addShapelessRecipe(TGItems.newStack(TGItems.ROCKET_NUKE, 1), TGItems.newStack(TGItems.ROCKET, 1), TGItems.newStack(TGItems.TACTICAL_NUKE_WARHEAD, 1));
   
        //incendiary ammo
        RecipeJsonConverter.addShapelessRecipe(newStack(RIFLE_ROUNDS_STACK_INCENDIARY,1), RIFLE_ROUNDS_INCENDIARY,RIFLE_ROUNDS_INCENDIARY,RIFLE_ROUNDS_INCENDIARY,RIFLE_ROUNDS_INCENDIARY);
        RecipeJsonConverter.addShapelessRecipe(newStack(RIFLE_ROUNDS_INCENDIARY,4), RIFLE_ROUNDS_STACK_INCENDIARY);
       
        RecipeJsonConverter.addShapelessRecipe(newStack(MINIGUN_DRUM_INCENDIARY,1),MINIGUN_DRUM_EMPTY, RIFLE_ROUNDS_STACK_INCENDIARY,RIFLE_ROUNDS_STACK_INCENDIARY,RIFLE_ROUNDS_STACK_INCENDIARY,RIFLE_ROUNDS_STACK_INCENDIARY);
	
        RecipeJsonConverter.addShapelessRecipe(newStack(LMG_MAGAZINE_INCENDIARY,1), LMG_MAGAZINE_EMPTY, RIFLE_ROUNDS_STACK_INCENDIARY, RIFLE_ROUNDS_STACK_INCENDIARY);
        RecipeJsonConverter.addShapelessRecipe(newStack(LMG_MAGAZINE_INCENDIARY,1), LMG_MAGAZINE_EMPTY, RIFLE_ROUNDS_INCENDIARY, RIFLE_ROUNDS_INCENDIARY, RIFLE_ROUNDS_INCENDIARY, RIFLE_ROUNDS_INCENDIARY, RIFLE_ROUNDS_INCENDIARY, RIFLE_ROUNDS_INCENDIARY, RIFLE_ROUNDS_INCENDIARY, RIFLE_ROUNDS_INCENDIARY);
        
        RecipeJsonConverter.addShapelessRecipe(newStack(AS50_MAGAZINE_INCENDIARY,1),AS50_MAGAZINE_EMPTY, SNIPER_ROUNDS_INCENDIARY, SNIPER_ROUNDS_INCENDIARY);
        RecipeJsonConverter.addShapelessRecipe(newStack(PISTOL_MAGAZINE_INCENDIARY, 1), PISTOL_MAGAZINE_EMPTY, PISTOL_ROUNDS_INCENDIARY,PISTOL_ROUNDS_INCENDIARY,PISTOL_ROUNDS_INCENDIARY);
        RecipeJsonConverter.addShapelessRecipe(newStack(SMG_MAGAZINE_INCENDIARY, 1), SMG_MAGAZINE_EMPTY, PISTOL_ROUNDS_INCENDIARY, PISTOL_ROUNDS_INCENDIARY);
        RecipeJsonConverter.addShapelessRecipe(newStack(ASSAULTRIFLE_MAGAZINE_INCENDIARY, 1), ASSAULTRIFLE_MAGAZINE_EMPTY, RIFLE_ROUNDS_INCENDIARY, RIFLE_ROUNDS_INCENDIARY, RIFLE_ROUNDS_INCENDIARY);
        
        //tool upgrades
        RecipeJsonConverter.addShapedRecipe(newStack(TGItems.POWERHAMMERHEAD_OBSIDIAN,1), "p  ","pii","p  ", 'p', "plateObsidianSteel", 'i', "ingotSteel");
        RecipeJsonConverter.addShapedRecipe(newStack(TGItems.POWERHAMMERHEAD_CARBON,1), "p  ","pii","p  ", 'p', "plateCarbon", 'i', "ingotObsidianSteel");
        
        RecipeJsonConverter.addShapedRecipe(newStack(TGItems.MININGDRILLHEAD_OBSIDIAN,1), " dd","ddd"," dd", 'd', "plateObsidianSteel");
        RecipeJsonConverter.addShapedRecipe(newStack(TGItems.MININGDRILLHEAD_CARBON,1), " dd","ddd"," dd", 'd', "plateCarbon");
        
        RecipeJsonConverter.addShapedRecipe(newStack(TGItems.CHAINSAWBLADES_OBSIDIAN,1), " pm","m  "," pm", 'p', "plateSteel", 'm', TGItems.MECHANICAL_PARTS_OBSIDIAN_STEEL);
        RecipeJsonConverter.addShapedRecipe(newStack(TGItems.CHAINSAWBLADES_CARBON,1), " pm","m  "," pm", 'p', "plateObsidianSteel", 'm', TGItems.MECHANICAL_PARTS_CARBON);
      
        RecipeJsonConverter.addShapedRecipe(newStack(TGItems.REDSTONE_BATTERY,2), "nwn","nrn","nrn", 'n', "nuggetCopper", 'r',"dustRedstone", 'w', "wireCopper");
        RecipeJsonConverter.addShapelessRecipe(newStack(TGItems.REDSTONE_BATTERY, 1), TGItems.REDSTONE_BATTERY_EMPTY, "dustRedstone");
        
        RecipeJsonConverter.addShapedRecipe(new ItemStack(TGBlocks.NEONLIGHT_BLOCK,16), "nnn", "gsg", "nnn", 'n', "nuggetIron", 'g', "paneGlass", 's', "dustGlowstone");
        
        RecipeJsonConverter.addShapedRecipe(new ItemStack(TGBlocks.CONCRETE,16), "ccc","cbc","ccc", 'b', Blocks.IRON_BARS, 'c', new ItemStack(Blocks.CONCRETE, 1, OreDictionary.WILDCARD_VALUE));
        
        RecipeJsonConverter.addShapedRecipe(new ItemStack(TGBlocks.METAL_STAIRS,6,7), "b  ", "bb ", "bbb", 'b', new ItemStack(TGBlocks.METAL_PANEL,1,TGMetalPanelType.PANEL_LARGE_BORDER.ordinal()));
        RecipeJsonConverter.addShapelessRecipe(new ItemStack(TGBlocks.METAL_PANEL,1,TGMetalPanelType.PANEL_LARGE_BORDER.ordinal()), new ItemStack(TGBlocks.METAL_STAIRS,1,7));
        
        RecipeJsonConverter.addShapedRecipe(new ItemStack(TGBlocks.METAL_STAIRS,6,15), "b  ", "bb ", "bbb", 'b', new ItemStack(TGBlocks.METAL_PANEL,1,TGMetalPanelType.STEELFRAME_DARK.ordinal()));
        RecipeJsonConverter.addShapelessRecipe(new ItemStack(TGBlocks.METAL_PANEL,1,TGMetalPanelType.STEELFRAME_DARK.ordinal()), new ItemStack(TGBlocks.METAL_STAIRS,1,15));
        
       
        String[] plateTypes = {"plateIron", "plateTin"};
        Arrays.stream(plateTypes).forEach(p -> {
	        RecipeJsonConverter.addShapedRecipe(new ItemStack(TGItems.DOOR3x3,1,0), "ppp", "sps","ppp", 's', Blocks.PISTON, 'p', p);
	        RecipeJsonConverter.addShapedRecipe(new ItemStack(TGBlocks.LADDER_0,16,TGBlocks.LADDER_0.getMetaFromState(TGBlocks.LADDER_0.getDefaultState())),"iii"," i ","iii", 'i', p);
	        RecipeJsonConverter.addShapedRecipe(newStack(COMPRESSED_AIR_TANK_EMPTY,7),"plp","p p","ppp", 'p', p, 'l', Blocks.LEVER);
	        RecipeJsonConverter.addShapedRecipe(new ItemStack(TGBlocks.METAL_PANEL,32), "sss","psp","sss", 's', "stone", 'p', p);
        });
        
        addGunRecipes();
        addArmorRecipes();
        addUpgradeRecipes();
        addAmmoChangeRecipes();
	}
	
	public static void addUpgradeRecipes() {
		RecipeJsonConverter.addShapelessMiningHeadUpgradeRecipe(TGuns.miningdrill, TGItems.MININGDRILLHEAD_CARBON, "miningHead",0);
		RecipeJsonConverter.addShapelessMiningHeadUpgradeRecipe(TGuns.powerhammer, TGItems.POWERHAMMERHEAD_OBSIDIAN, "miningHead",0);
		RecipeJsonConverter.addShapelessMiningHeadUpgradeRecipe(TGuns.powerhammer, TGItems.POWERHAMMERHEAD_CARBON, "miningHead",1);
		RecipeJsonConverter.addShapelessMiningHeadUpgradeRecipe(TGuns.chainsaw, TGItems.CHAINSAWBLADES_OBSIDIAN, "miningHead",0);
		RecipeJsonConverter.addShapelessMiningHeadUpgradeRecipe(TGuns.chainsaw, TGItems.CHAINSAWBLADES_CARBON, "miningHead",1);
	}
	
	public static void addAmmoChangeRecipes() {
		GenericGun.guns.forEach(g -> {
			AmmoType a = g.getAmmoType();
			if(a.hasMultipleVariants()) {
				a.getVariants().forEach(v -> {
					RecipeJsonConverter.addShapelessAmmoSwapRecipe(g, a, v.getKey());
				});
			}
		});
		//RecipeJsonConverter.addShapelessAmmoSwapRecipe(TGuns.combatshotgun, AmmoTypes.SHOTGUN_ROUNDS, AmmoTypes.TYPE_INCENDIARY);
		//RecipeJsonConverter.addShapelessAmmoSwapRecipe(TGuns.combatshotgun, AmmoTypes.SHOTGUN_ROUNDS, AmmoTypes.TYPE_DEFAULT);
	}
	
	public static void addGunRecipes() {
		    RecipeJsonConverter.addShapedRecipe(new ItemStack(handcannon,1, handcannon.getMaxDamage()), "bfs", 'b', TGItems.BARREL_STONE, 'f', Items.FLINT_AND_STEEL, 's', TGItems.STOCK_WOOD);
	        
	        RecipeJsonConverter.addShapedRecipe(new ItemStack(pistol,1), "bsp"," fp","  m", 'b', TGItems.BARREL_OBSIDIAN_STEEL, 'f', TGItems.MECHANICAL_PARTS_OBSIDIAN_STEEL, 'p', TGItems.PLASTIC_SHEET, 's', "plateSteel", 'm', TGItems.PISTOL_MAGAZINE);
	        RecipeJsonConverter.addShapedRecipe(new ItemStack(pistol,1,pistol.getMaxDamage()), "bsp"," fp","  m", 'b', TGItems.BARREL_OBSIDIAN_STEEL, 'f', TGItems.MECHANICAL_PARTS_OBSIDIAN_STEEL, 'p', TGItems.PLASTIC_SHEET,'s',"plateSteel", 'm', TGItems.PISTOL_MAGAZINE_EMPTY);
	               
	        RecipeJsonConverter.addShapedRecipe(new ItemStack(revolver,1, revolver.getMaxDamage()), "bmf"," s ", 'f', Items.FLINT_AND_STEEL, 'b', TGItems.BARREL_IRON, 's', TGItems.STOCK_WOOD, 'm', TGItems.MECHANICAL_PARTS_IRON);
	             
	        RecipeJsonConverter.addShapedRecipe(new ItemStack(sawedoff,1, sawedoff.getMaxDamage()), "bfw","bf ", 'b', TGItems.BARREL_IRON, 'f', Items.FLINT_AND_STEEL, 'w', TGItems.STOCK_WOOD);
	       
	        RecipeJsonConverter.addShapedRecipe(new ItemStack(thompson,1), "bfs"," m ", 'f', TGItems.RECEIVER_IRON, 'b', TGItems.BARREL_IRON, 's', TGItems.STOCK_WOOD, 'm', TGItems.SMG_MAGAZINE);
	        RecipeJsonConverter.addShapedRecipe(new ItemStack(thompson,1,thompson.getMaxDamage()), "bfs"," m ", 'f', TGItems.RECEIVER_IRON, 'b', TGItems.BARREL_IRON, 's', TGItems.STOCK_WOOD, 'm', TGItems.SMG_MAGAZINE_EMPTY);
	        
	        RecipeJsonConverter.addShapedRecipe(new ItemStack(m4,1)," gr","bfs","nmi", 'g', "paneGlass", 'r', "dustRedstone", 'b', TGItems.BARREL_OBSIDIAN_STEEL, 'f', TGItems.RECEIVER_STEEL, 's', TGItems.STOCK_PLASTIC, 'n', "nuggetIron",'m', TGItems.ASSAULTRIFLE_MAGAZINE, 'i', "ingotIron");
	        RecipeJsonConverter.addShapedRecipe(new ItemStack(m4,1,m4.getMaxDamage())," gr","bfs","nmi", 'g', "paneGlass", 'r', "dustRedstone", 'b', TGItems.BARREL_OBSIDIAN_STEEL, 'f', TGItems.RECEIVER_STEEL, 's', TGItems.STOCK_PLASTIC, 'n', "nuggetIron",'m', TGItems.ASSAULTRIFLE_MAGAZINE_EMPTY, 'i', "ingotIron");
	                
	        RecipeJsonConverter.addShapedRecipe(new ItemStack(boltaction,1, boltaction.getMaxDamage()), "gi ", "bfs", 'g', "blockGlass", 'b', TGItems.BARREL_OBSIDIAN_STEEL, 'f', TGItems.RECEIVER_IRON, 's', TGItems.STOCK_WOOD,'i', "plateIron");
	        
	        RecipeJsonConverter.addShapedRecipe(new ItemStack(combatshotgun,1, combatshotgun.getMaxDamage()), "bfs","i  ",'b', TGItems.BARREL_OBSIDIAN_STEEL, 'f', TGItems.RECEIVER_STEEL, 's', TGItems.STOCK_PLASTIC, 'i', "ingotSteel");
	        
	        RecipeJsonConverter.addShapedRecipe(new ItemStack(flamethrower,1), "prs","fm ",'r', TGItems.RECEIVER_IRON, 'p', TGItems.PUMP_MECHANISM, 's', TGItems.STOCK_PLASTIC, 'f', Items.FLINT_AND_STEEL, 'm', TGItems.FUEL_TANK);
	        RecipeJsonConverter.addShapedRecipe(new ItemStack(flamethrower,1,flamethrower.getMaxDamage()), "prs","fm ",'r', TGItems.RECEIVER_IRON, 'p', TGItems.PUMP_MECHANISM, 's', TGItems.STOCK_PLASTIC, 'f', Items.FLINT_AND_STEEL, 'm', TGItems.FUEL_TANK_EMPTY);
	        
	        RecipeJsonConverter.addShapedRecipe(new ItemStack(biogun,1), "frs"," m ",'r', TGItems.RECEIVER_STEEL, 'f', TGItems.PUMP_MECHANISM, 's', TGItems.STOCK_PLASTIC, 'm', TGItems.BIO_TANK);
	        RecipeJsonConverter.addShapedRecipe(new ItemStack(biogun,1,biogun.getMaxDamage()), "frs"," m ",'r', TGItems.RECEIVER_STEEL, 'f', TGItems.PUMP_MECHANISM, 's', TGItems.STOCK_PLASTIC, 'm', TGItems.BIO_TANK_EMPTY);
	            
	        RecipeJsonConverter.addShapedRecipe(new ItemStack(lasergun,1)," gd","brs"," em", 'g', "paneGlass", 'd', "dustRedstone", 'b', TGItems.BARREL_LASER, 'm', TGItems.MECHANICAL_PARTS_CARBON, 's', TGItems.STOCK_PLASTIC, 'e', TGItems.ENERGY_CELL, 'r', TGItems.RECEIVER_OBSIDIAN_STEEL);
	        RecipeJsonConverter.addShapedRecipe(new ItemStack(lasergun,1,lasergun.getMaxDamage())," gd","brs"," em", 'g', "paneGlass", 'd', "dustRedstone", 'b', TGItems.BARREL_LASER, 'm', TGItems.MECHANICAL_PARTS_CARBON, 's', TGItems.STOCK_PLASTIC, 'e', TGItems.ENERGY_CELL_EMPTY, 'r', TGItems.RECEIVER_OBSIDIAN_STEEL);      
	        
	        RecipeJsonConverter.addShapedRecipe(new ItemStack(blasterrifle,1),"pcg","bms"," e ", 'g', hardenedGlassOrGlass,'p', "plateCarbon", 'c', "circuitElite", 'b', TGItems.BARREL_LASER, 'm', TGItems.RECEIVER_CARBON, 's', TGItems.STOCK_CARBON, 'e', TGItems.ENERGY_CELL);
	        RecipeJsonConverter.addShapedRecipe(new ItemStack(blasterrifle,1,blasterrifle.getMaxDamage()),"pcg","bms"," e ", 'g', hardenedGlassOrGlass,'p', "plateCarbon", 'c', "circuitElite", 'b', TGItems.BARREL_LASER, 'm', TGItems.RECEIVER_CARBON, 's', TGItems.STOCK_CARBON, 'e', TGItems.ENERGY_CELL_EMPTY);
	        
	        
	        RecipeJsonConverter.addShapedRecipe(new ItemStack(ak47,1),"bfs","wm ", 'b', TGItems.BARREL_OBSIDIAN_STEEL, 'f', TGItems.RECEIVER_IRON, 's', TGItems.STOCK_WOOD, 'w', "logWood",'m', TGItems.ASSAULTRIFLE_MAGAZINE);
	        RecipeJsonConverter.addShapedRecipe(new ItemStack(ak47,1,ak47.getMaxDamage()),"bfs","wm ", 'b', TGItems.BARREL_OBSIDIAN_STEEL, 'f', TGItems.RECEIVER_IRON, 's', TGItems.STOCK_WOOD, 'w', "logWood",'m', TGItems.ASSAULTRIFLE_MAGAZINE_EMPTY);
	          
	        RecipeJsonConverter.addShapedRecipe(new ItemStack(as50,1),"dpd","bbf", " mt", 'f', TGItems.RECEIVER_OBSIDIAN_STEEL, 'b', TGItems.BARREL_OBSIDIAN_STEEL, 't', TGItems.STOCK_PLASTIC,'m', TGItems.AS50_MAGAZINE, 'd', "gemDiamond", 'p', "plateObsidianSteel");
	        RecipeJsonConverter.addShapedRecipe(new ItemStack(as50,1,as50.getMaxDamage()),"dpd","bbf", " mt", 'f', TGItems.RECEIVER_OBSIDIAN_STEEL, 'b', TGItems.BARREL_OBSIDIAN_STEEL, 't', TGItems.STOCK_PLASTIC,'m', TGItems.AS50_MAGAZINE_EMPTY, 'd', "gemDiamond", 'p', "plateObsidianSteel");
	        
	        RecipeJsonConverter.addShapedRecipe(new ItemStack(rocketlauncher,1),"rbb"," f ", 'r', TGItems.ROCKET, 'b', TGItems.BARREL_OBSIDIAN_STEEL, 'f', TGItems.RECEIVER_STEEL);

	        RecipeJsonConverter.addShapedRecipe(new ItemStack(lmg,1), " gr","bfs"," m ", 'g', "paneGlass", 'r', "dustRedstone", 'b', TGItems.BARREL_OBSIDIAN_STEEL, 'f', TGItems.RECEIVER_OBSIDIAN_STEEL, 's', TGItems.STOCK_PLASTIC,'m', TGItems.LMG_MAGAZINE);
	        RecipeJsonConverter.addShapedRecipe(new ItemStack(lmg,1,lmg.getMaxDamage()), " gr","bfs"," m ", 'g', "paneGlass", 'r', "dustRedstone", 'b', TGItems.BARREL_OBSIDIAN_STEEL, 'f', TGItems.RECEIVER_OBSIDIAN_STEEL, 's', TGItems.STOCK_PLASTIC,'m', TGItems.LMG_MAGAZINE_EMPTY);
	        
	        RecipeJsonConverter.addShapedRecipe(new ItemStack(stielgranate,16), " it"," wi","i  ",'i', "ingotIron",'w', "plankWood", 't', Blocks.TNT);
	        
	        RecipeJsonConverter.addShapedRecipe(new ItemStack(minigun,1),"bbe","bbr","bbm", 'b', TGItems.BARREL_OBSIDIAN_STEEL, 'e', TGItems.ELECTRIC_ENGINE, 'r', TGItems.RECEIVER_OBSIDIAN_STEEL, 'm', TGItems.MINIGUN_DRUM);
	        RecipeJsonConverter.addShapedRecipe(new ItemStack(minigun,1,minigun.getMaxDamage()),"bbe","bbr","bbm", 'b', TGItems.BARREL_OBSIDIAN_STEEL, 'e', TGItems.ELECTRIC_ENGINE, 'r', TGItems.RECEIVER_OBSIDIAN_STEEL, 'm', TGItems.MINIGUN_DRUM_EMPTY);
	                
	        RecipeJsonConverter.addShapedRecipe(new ItemStack(pdw,1),"brs"," m ", 'b', TGItems.BARREL_CARBON, 'r', TGItems.RECEIVER_CARBON, 's', TGItems.STOCK_CARBON, 'm', TGItems.ADVANCED_MAGAZINE);
	        RecipeJsonConverter.addShapedRecipe(new ItemStack(pdw,1,pdw.getMaxDamage()),"brs"," m ", 'b', TGItems.BARREL_CARBON, 'r', TGItems.RECEIVER_CARBON, 's', TGItems.STOCK_CARBON, 'm', TGItems.ADVANCED_MAGAZINE_EMPTY);
	        
	        RecipeJsonConverter.addShapedRecipe(new ItemStack(pulserifle,1),"dpc","brs"," m ", 'b', TGItems.BARREL_CARBON, 'r', TGItems.RECEIVER_CARBON, 's', TGItems.STOCK_CARBON, 'm', TGItems.ADVANCED_MAGAZINE, 'd', "gemDiamond", 'p', "plateTitanium", 'c', "circuitElite");
	        RecipeJsonConverter.addShapedRecipe(new ItemStack(pulserifle,1,pulserifle.getMaxDamage()),"dpc","brs"," m ", 'b', TGItems.BARREL_CARBON, 'r', TGItems.RECEIVER_CARBON, 's', TGItems.STOCK_CARBON, 'm', TGItems.ADVANCED_MAGAZINE_EMPTY, 'd', "gemDiamond", 'p', "plateTitanium", 'c', "circuitElite");
	         
	        RecipeJsonConverter.addShapedRecipe(new ItemStack(powerhammer,1), " m ", "spr", " mc", 'p', new ItemStack(Blocks.PISTON,1), 'r', TGItems.RECEIVER_IRON, 'm', TGItems.MECHANICAL_PARTS_IRON, 's', "plateIron", 'c', TGItems.COMPRESSED_AIR_TANK);
	        RecipeJsonConverter.addShapedRecipe(new ItemStack(powerhammer,1,powerhammer.getMaxDamage()), " m ", "spr", " mc", 'p', new ItemStack(Blocks.PISTON,1), 'r', TGItems.RECEIVER_IRON, 'm', TGItems.MECHANICAL_PARTS_IRON, 's', "plateIron", 'c', TGItems.COMPRESSED_AIR_TANK_EMPTY);
	        	        
	        //RecipeJsonConverter.addRecipe(new ShapelessOreRecipeCopyNBT(new ItemStack(powerHammerAdv,1), new ItemStack(powerHammer,1), "gemDiamond", "gemDiamond", "gemDiamond"));
	        
	        RecipeJsonConverter.addShapedRecipe(new ItemStack(teslagun,1), " gd", "crs", " e ", 'g', "paneGlass", 'd', "dustRedstone", 'c', TGItems.COIL, 'r', TGItems.RECEIVER_CARBON, 's', TGItems.STOCK_CARBON, 'e', TGItems.ENERGY_CELL);
	        RecipeJsonConverter.addShapedRecipe(new ItemStack(teslagun,1,teslagun.getMaxDamage()), " gd", "crs", " e ", 'g', "paneGlass", 'd', "dustRedstone", 'c', TGItems.COIL, 'r', TGItems.RECEIVER_CARBON, 's', TGItems.STOCK_CARBON, 'e', TGItems.ENERGY_CELL_EMPTY);
	        
	        
	        RecipeJsonConverter.addShapedRecipe(new ItemStack(goldenrevolver,1), "ggg","grg","ggg", 'g', "ingotGold", 'r', new ItemStack(revolver,1,0));
	        
	        RecipeJsonConverter.addShapedRecipe(new ItemStack(grimreaper,1), "rr ", "rrc", 'r', new ItemStack(guidedmissilelauncher,1,0), 'c', TGItems.RECEIVER_CARBON);
	        
	        //RecipeJsonConverter.addRecipe(new ShapelessOreRecipe(new ItemStack(m4_uq,1), new ItemStack(m4,1,OreDictionary.WILDCARD_VALUE), Items.nether_star));
	        RecipeJsonConverter.addShapedRecipe(new ItemStack(m4_infiltrator,1), "gpg","wwm","rri", 'm', new ItemStack(m4,1), 'g', hardenedGlassOrGlass, 'p', "plateSteel", 'w', "blockWool",'i',"ingotSteel", 'r', "dustRedstone");
	        RecipeJsonConverter.addShapedRecipe(new ItemStack(m4_infiltrator,1,m4_infiltrator.getMaxDamage()), "gpg","wwm","rri", 'm', new ItemStack(m4,1,m4.getMaxDamage()), 'g', hardenedGlassOrGlass, 'p', "plateSteel", 'w', "blockWool",'i',"ingotSteel", 'r', "dustRedstone");
	                	        
	        RecipeJsonConverter.addShapedRecipe(new ItemStack(netherblaster,1), "cpc","bns","cpc", 'c', TGItems.CYBERNETIC_PARTS, 'p', "plateObsidianSteel", 'b', TGItems.BARREL_OBSIDIAN_STEEL, 'n', TGItems.NETHER_CHARGE, 's', TGItems.PUMP_MECHANISM);
	                
	        RecipeJsonConverter.addShapedRecipe(new ItemStack(aug,1),"gpg","bfs","i m", 'g', "paneGlass", 'p', "plateSteel", 'b', TGItems.BARREL_OBSIDIAN_STEEL, 'f', TGItems.RECEIVER_STEEL, 's', TGItems.STOCK_PLASTIC, 'm', TGItems.ASSAULTRIFLE_MAGAZINE, 'i', "sheetPlastic");
	        RecipeJsonConverter.addShapedRecipe(new ItemStack(aug,1,aug.getMaxDamage()),"gpg","bfs","i m", 'g', "paneGlass", 'p', "plateSteel", 'b', TGItems.BARREL_OBSIDIAN_STEEL, 'f', TGItems.RECEIVER_STEEL, 's', TGItems.STOCK_PLASTIC, 'm', TGItems.ASSAULTRIFLE_MAGAZINE_EMPTY, 'i', "sheetPlastic");
	        
	        RecipeJsonConverter.addShapedRecipe(new ItemStack(grenadelauncher,1,grenadelauncher.getMaxDamage()), "mrs"," p ", 'm', TGItems.BARREL_OBSIDIAN_STEEL, 'r', TGItems.RECEIVER_STEEL, 's', TGItems.STOCK_PLASTIC, 'p', "plateSteel");
	    
	        RecipeJsonConverter.addShapedRecipe(new ItemStack(chainsaw,1), "ccp","mmr","ccf", 'c', TGItems.MECHANICAL_PARTS_IRON, 'm', "plateIron", 'r', TGItems.RECEIVER_IRON, 'p', TGItems.PLASTIC_SHEET, 'f', TGItems.FUEL_TANK);
	        RecipeJsonConverter.addShapedRecipe(new ItemStack(chainsaw,1,chainsaw.getMaxDamage()), "ccp","mmr","ccf", 'c', TGItems.MECHANICAL_PARTS_IRON, 'm', "plateIron", 'r', TGItems.RECEIVER_IRON, 'p', TGItems.PLASTIC_SHEET, 'f', TGItems.FUEL_TANK_EMPTY);
		
	        RecipeJsonConverter.addShapedRecipe(new ItemStack(sonicshotgun,1), " p ", "ers", " c ", 'p', "plateTitanium", 'e', TGItems.SONIC_EMITTER, 'r', TGItems.RECEIVER_CARBON, 's', TGItems.STOCK_CARBON, 'c', TGItems.ENERGY_CELL);
	        RecipeJsonConverter.addShapedRecipe(new ItemStack(sonicshotgun,1,sonicshotgun.getMaxDamage()), " p ", "ers", " c ", 'p', "plateTitanium", 'e', TGItems.SONIC_EMITTER, 'r', TGItems.RECEIVER_CARBON, 's', TGItems.STOCK_CARBON, 'c', TGItems.ENERGY_CELL_EMPTY);
		
	       // RecipeJsonConverter.addShapedRecipe(new ItemStack(nukeLauncher,1)," pp","rbb"," f ", 'r', TGItems.rocketAmmoNuclear, 'b', TGItems.BARREL_OBSIDIAN_STEEL, 'f', TGItems.RECEIVER_CARBON, 'p', "plateLead");
	       // RecipeJsonConverter.addShapedRecipe(new ItemStack(nukeLauncher,1,nukeLauncher.getMaxDamage())," pp"," bb"," f ", 'b', TGItems.BARREL_OBSIDIAN_STEEL, 'f', TGItems.RECEIVER_CARBON, 'p', "plateLead");

	        RecipeJsonConverter.addShapedRecipe(new ItemStack(nucleardeathray,1),"ltl","ers"," m ", 'l', "plateLead", 't', "plateTitanium", 'e', TGItems.RAD_EMITTER, 'r', TGItems.RECEIVER_CARBON, 's', TGItems.STOCK_CARBON, 'm', TGItems.NUCLEAR_POWERCELL);
	        RecipeJsonConverter.addShapedRecipe(new ItemStack(nucleardeathray,1,nucleardeathray.getMaxDamage()),"ltl","ers"," m ", 'l', "plateLead", 't', "plateTitanium", 'e', TGItems.RAD_EMITTER, 'r', TGItems.RECEIVER_CARBON, 's', TGItems.STOCK_CARBON, 'm', TGItems.NUCLEAR_POWERCELL_EMPTY);
	        
	        RecipeJsonConverter.addShapedRecipe(new ItemStack(mac10,1)," pi","bri"," m ", 'p', "sheetPlastic", 'i', "nuggetIron", 'r', TGItems.RECEIVER_STEEL, 'b', TGItems.BARREL_OBSIDIAN_STEEL, 'm', TGItems.SMG_MAGAZINE);	    
	        RecipeJsonConverter.addShapedRecipe(new ItemStack(mac10,1,mac10.getMaxDamage())," pi","bri"," m ", 'p', "sheetPlastic", 'i', "nuggetIron", 'r', TGItems.RECEIVER_STEEL, 'b', TGItems.BARREL_OBSIDIAN_STEEL, 'm', TGItems.SMG_MAGAZINE_EMPTY);
	        
	        RecipeJsonConverter.addShapedRecipe(new ItemStack(scar,1),"gpd","brs", " m ", 'r', TGItems.RECEIVER_OBSIDIAN_STEEL, 'b', TGItems.BARREL_OBSIDIAN_STEEL, 's', TGItems.STOCK_PLASTIC,'m', TGItems.ASSAULTRIFLE_MAGAZINE, 'd', "gemDiamond", 'p', "sheetPlastic", 'g', hardenedGlassOrGlass);
	        RecipeJsonConverter.addShapedRecipe(new ItemStack(scar,1,scar.getMaxDamage()),"gpd","brs", " m ", 'r', TGItems.RECEIVER_OBSIDIAN_STEEL, 'b', TGItems.BARREL_OBSIDIAN_STEEL, 's', TGItems.STOCK_PLASTIC,'m', TGItems.ASSAULTRIFLE_MAGAZINE_EMPTY, 'd', "gemDiamond", 'p', "sheetPlastic", 'g',hardenedGlassOrGlass);
	        
	        RecipeJsonConverter.addShapedRecipe(new ItemStack(vector,1)," gd","brs", " m ", 'r', TGItems.RECEIVER_OBSIDIAN_STEEL, 'b', TGItems.BARREL_OBSIDIAN_STEEL, 's', TGItems.STOCK_PLASTIC,'m', TGItems.SMG_MAGAZINE, 'd', "dustRedstone", 'g', "paneGlass");
	        RecipeJsonConverter.addShapedRecipe(new ItemStack(vector,1,vector.getMaxDamage())," gd","brs", " m ", 'r', TGItems.RECEIVER_OBSIDIAN_STEEL, 'b', TGItems.BARREL_OBSIDIAN_STEEL, 's', TGItems.STOCK_PLASTIC,'m', TGItems.SMG_MAGAZINE_EMPTY, 'd', "dustRedstone", 'g', "paneGlass");
	        
	        RecipeJsonConverter.addShapedRecipe(new ItemStack(mibgun,1), "bsp"," fp","  m", 'b', TGItems.BARREL_LASER, 'f', TGItems.MECHANICAL_PARTS_CARBON, 'p', "plateTitanium", 's', TGItems.SONIC_EMITTER, 'm', TGItems.ENERGY_CELL);
	        RecipeJsonConverter.addShapedRecipe(new ItemStack(mibgun,1,mibgun.getMaxDamage()), "bsp"," fp","  m", 'b', TGItems.BARREL_LASER, 'f', TGItems.MECHANICAL_PARTS_CARBON, 'p', "plateTitanium", 's', TGItems.SONIC_EMITTER, 'm', TGItems.ENERGY_CELL_EMPTY);
	       
	        RecipeJsonConverter.addShapedRecipe(new ItemStack(fraggrenade,16), " if","iti"," i ",'i', "ingotSteel",'f', Items.FLINT_AND_STEEL, 't', Blocks.TNT);
	 
	}
	
	public static void addArmorRecipes() {
		//Recipes:
		RecipeJsonConverter.addShapedRecipe(new ItemStack(t1_combat_Helmet,1), "iii","c c", 'i', "ingotIron", 'c', TGItems.HEAVY_CLOTH);
		RecipeJsonConverter.addShapedRecipe(new ItemStack(t1_combat_Chestplate,1), "c c","iii","ccc", 'i', "ingotIron", 'c', TGItems.HEAVY_CLOTH);
		RecipeJsonConverter.addShapedRecipe(new ItemStack(t1_combat_Leggings,1), "iii","c c","c c", 'i', "ingotIron", 'c', TGItems.HEAVY_CLOTH);
		RecipeJsonConverter.addShapedRecipe(new ItemStack(t1_combat_Boots,1), "c c","i i", 'i', "ingotIron", 'c', TGItems.HEAVY_CLOTH);
		
		RecipeJsonConverter.addShapedRecipe(new ItemStack(t1_miner_Helmet,1), "iyi","c c",'i', "ingotIron", 'c', TGItems.HEAVY_CLOTH, 'y', "dyeYellow");
		RecipeJsonConverter.addShapedRecipe(new ItemStack(t1_miner_Chestplate,1), "c c","ccc","ici", 'i', "ingotIron", 'c', TGItems.HEAVY_CLOTH);
		RecipeJsonConverter.addShapedRecipe(new ItemStack(t1_miner_Leggings,1), "ici","c c","c c", 'i', "ingotIron", 'c', TGItems.HEAVY_CLOTH);
		RecipeJsonConverter.addShapedRecipe(new ItemStack(t1_miner_Boots,1), "c c","i i", 'i', "nuggetIron", 'c', TGItems.HEAVY_CLOTH);
		
		RecipeJsonConverter.addShapedRecipe(new ItemStack(t1_scout_Helmet,1), "ccc","c c", 'c', TGItems.HEAVY_CLOTH);
		RecipeJsonConverter.addShapedRecipe(new ItemStack(t1_scout_Chestplate,1), "c c","ccc","ccc", 'c', TGItems.HEAVY_CLOTH);
		RecipeJsonConverter.addShapedRecipe(new ItemStack(t1_scout_Leggings,1), "ccc","c c","c c", 'c', TGItems.HEAVY_CLOTH);
		RecipeJsonConverter.addShapedRecipe(new ItemStack(t1_scout_Boots,1), "c c","c c", 'c', TGItems.HEAVY_CLOTH);
		
		RecipeJsonConverter.addShapedRecipe(new ItemStack(t2_combat_Helmet,1), "iii","h h", 'h', TGItems.HEAVY_CLOTH,'i', "ingotObsidianSteel");
		RecipeJsonConverter.addShapedRecipe(new ItemStack(t2_combat_Chestplate,1), "h h","iii","iii", 'h', TGItems.HEAVY_CLOTH,'i', "ingotObsidianSteel");
		RecipeJsonConverter.addShapedRecipe(new ItemStack(t2_combat_Leggings,1), "ihi","i i","h h", 'h', TGItems.HEAVY_CLOTH,'i', "ingotObsidianSteel");
		RecipeJsonConverter.addShapedRecipe(new ItemStack(t2_combat_Boots,1), "h h","i i", 'h',TGItems.HEAVY_CLOTH,'i', "ingotObsidianSteel");

		RecipeJsonConverter.addShapedRecipe(new ItemStack(t2_commando_Helmet,1), "ihi","hgh", 'h', "itemRubber",'i', "ingotObsidianSteel",'g',"paneGlass");
		RecipeJsonConverter.addShapedRecipe(new ItemStack(t2_commando_Chestplate,1), "h h","hih","iii", 'h', "itemRubber",'i', "ingotObsidianSteel");
		RecipeJsonConverter.addShapedRecipe(new ItemStack(t2_commando_Leggings,1), "hih","i i","h h", 'h', "itemRubber",'i', "ingotObsidianSteel");
		RecipeJsonConverter.addShapedRecipe(new ItemStack(t2_commando_Boots,1), "i i","h h", 'h',"itemRubber",'i', "ingotObsidianSteel");
		
		RecipeJsonConverter.addShapedRecipe(new ItemStack(t3_combat_Helmet,1), "ppp","f f", 'f', "fiberCarbon",'p', "plateCarbon");
		RecipeJsonConverter.addShapedRecipe(new ItemStack(t3_combat_Chestplate,1), "p p","ppp","fpf", 'f', "fiberCarbon",'p', "plateCarbon");
		RecipeJsonConverter.addShapedRecipe(new ItemStack(t3_combat_Leggings,1), "pfp","p p","f f", 'f', "fiberCarbon",'p', "plateCarbon");
		RecipeJsonConverter.addShapedRecipe(new ItemStack(t3_combat_Boots,1), "f f","p p", 'f', "fiberCarbon",'p', "plateCarbon");
		
		RecipeJsonConverter.addShapedRecipe(new ItemStack(steam_Helmet,1), "sss","s s", 's', TGItems.STEAMARMOR_PLATE);
		RecipeJsonConverter.addShapedRecipe(new ItemStack(steam_Chestplate,1), "s s","sss","sss", 's', TGItems.STEAMARMOR_PLATE);
		RecipeJsonConverter.addShapedRecipe(new ItemStack(steam_Leggings,1), "sss","s s","s s", 's', TGItems.STEAMARMOR_PLATE);
		RecipeJsonConverter.addShapedRecipe(new ItemStack(steam_Boots,1), "s s","s s", 's', TGItems.STEAMARMOR_PLATE);
		
		RecipeJsonConverter.addShapedRecipe(new ItemStack(t3_power_Helmet,1), "cpc","pgp", 'p', TGItems.POWER_ARMOR_PLATING, 'g', "paneGlass", 'c', "circuitElite");
		RecipeJsonConverter.addShapedRecipe(new ItemStack(t3_power_Chestplate,1), "p p","ece","ppp", 'p', TGItems.POWER_ARMOR_PLATING, 'c', "circuitElite", 'e', TGItems.ELECTRIC_ENGINE);
		RecipeJsonConverter.addShapedRecipe(new ItemStack(t3_power_Leggings,1), "pcp","e e","p p", 'p', TGItems.POWER_ARMOR_PLATING, 'c', "circuitElite", 'e', TGItems.ELECTRIC_ENGINE);
		RecipeJsonConverter.addShapedRecipe(new ItemStack(t3_power_Boots,1), "e e","p p", 'p', TGItems.POWER_ARMOR_PLATING, 'e',TGItems.ELECTRIC_ENGINE);
	
		RecipeJsonConverter.addShapedRecipe(new ItemStack(t3_miner_Helmet,1), "pcp","lgl", 'p', "plateCarbon", 'g', "paneGlass", 'c', "circuitElite", 'l', "plateTitanium");
		RecipeJsonConverter.addShapedRecipe(new ItemStack(t3_miner_Chestplate,1), "p p","lcl","lpl", 'p',  "plateCarbon", 'c', "circuitElite", 'l', "plateTitanium");
		RecipeJsonConverter.addShapedRecipe(new ItemStack(t3_miner_Leggings,1), "ppp","c c","l l", 'p',  "plateCarbon", 'c', "circuitElite", 'l', "plateTitanium");
		RecipeJsonConverter.addShapedRecipe(new ItemStack(t3_miner_Boots,1), "l l","p p", 'p',  "plateCarbon", 'l',"plateTitanium");
	
		RecipeJsonConverter.addShapelessRecipe(new ItemStack(t3_exo_Helmet,1), new ItemStack(t3_combat_Helmet,1,OreDictionary.WILDCARD_VALUE),TGItems.MECHANICAL_PARTS_CARBON, "circuitElite", TGItems.ELECTRIC_ENGINE, "ingotTitanium");
		RecipeJsonConverter.addShapelessRecipe(new ItemStack(t3_exo_Chestplate,1), new ItemStack(t3_combat_Chestplate,1,OreDictionary.WILDCARD_VALUE),TGItems.MECHANICAL_PARTS_CARBON, "circuitElite", TGItems.ELECTRIC_ENGINE, "ingotTitanium","ingotTitanium");
		RecipeJsonConverter.addShapelessRecipe(new ItemStack(t3_exo_Leggings,1), new ItemStack(t3_combat_Leggings,1,OreDictionary.WILDCARD_VALUE),TGItems.MECHANICAL_PARTS_CARBON, "circuitElite", TGItems.ELECTRIC_ENGINE, "ingotTitanium","ingotTitanium");
		RecipeJsonConverter.addShapelessRecipe(new ItemStack(t3_exo_Boots,1), new ItemStack(t3_combat_Boots,1,OreDictionary.WILDCARD_VALUE),TGItems.MECHANICAL_PARTS_CARBON, "circuitElite", TGItems.ELECTRIC_ENGINE, "ingotTitanium");
		
		RecipeJsonConverter.addShapedRecipe(new ItemStack(t2_riot_Helmet,1), "php","l l",'p', "plateLead", 'l', TGItems.newStack(TGItems.TREATED_LEATHER, 1), 'h', new ItemStack(t2_combat_Helmet,1,OreDictionary.WILDCARD_VALUE));
		RecipeJsonConverter.addShapedRecipe(new ItemStack(t2_riot_Chestplate,1), "p p","lcl","lpl",'p', "plateLead", 'l', TGItems.newStack(TGItems.TREATED_LEATHER, 1), 'c', new ItemStack(t2_combat_Chestplate,1,OreDictionary.WILDCARD_VALUE));
		RecipeJsonConverter.addShapedRecipe(new ItemStack(t2_riot_Leggings,1), "lhl","p p","l l",'p', "plateLead", 'l', TGItems.newStack(TGItems.TREATED_LEATHER, 1), 'h', new ItemStack(t2_combat_Leggings,1,OreDictionary.WILDCARD_VALUE));
		RecipeJsonConverter.addShapedRecipe(new ItemStack(t2_riot_Boots,1), "lhl","p p",'p', "plateLead", 'l', TGItems.newStack(TGItems.TREATED_LEATHER, 1), 'h', new ItemStack(t2_combat_Boots,1,OreDictionary.WILDCARD_VALUE));
		
		RecipeJsonConverter.addShapedRecipe(new ItemStack(t2_beret,1), " cc","c c", 'c', TGItems.HEAVY_CLOTH);
		
	}
	
	public static void notyetimplemented() {
   /*     
        
     
     //	
        RecipeJsonConverter.addShapedRecipe(new ItemStack(TGBlocks.oreDrill,8,0), "pbp","bpb","pbp", 'p', "plateIron", 'b', new ItemStack(Blocks.iron_bars,1)));
        RecipeJsonConverter.addShapedRecipe(new ItemStack(TGBlocks.oreDrill,8,1), "bbb","bpb","bbb", 'p', "plateIron", 'b', new ItemStack(Blocks.iron_bars,1)));
        RecipeJsonConverter.addShapedRecipe(new ItemStack(TGBlocks.oreDrill,4,2), "ipi","ipi","ipi", 'p', "plateIron", 'i', "ingotIron"));
        RecipeJsonConverter.addShapedRecipe(new ItemStack(TGBlocks.oreDrill,4,3), "bbb","beb","bbb", 'p', "plateIron", 'b', new ItemStack(Blocks.iron_bars,1),'e', TGItems.electricEngine));
        RecipeJsonConverter.addShapedRecipe(new ItemStack(TGBlocks.oreDrill,1,4), "pcp","cec","pcp", 'p', "plateIron", 'c', "circuitBasic",'e', TGItems.electricEngine));
	
        RecipeJsonConverter.addShapedRecipe(new ItemStack(TGBlocks.container,8,0), "i i"," c ", "i i", 'i', "ingotIron", 'c', Blocks.chest));
        
        RecipeJsonConverter.addShapedRecipe(newStack(oreDrillSmallSteel,1),"sps","sps"," s ", 's', "ingotSteel", 'p', "plateSteel"));
        RecipeJsonConverter.addShapedRecipe(newStack(oreDrillSmallObsidianSteel,1),"sps","sps"," s ", 's', "ingotObsidianSteel", 'p', "plateObsidianSteel"));
        RecipeJsonConverter.addShapedRecipe(newStack(oreDrillSmallCarbon,1),"sss","sss"," s ",  's', "plateCarbon"));
        
        RecipeJsonConverter.addShapedRecipe(newStack(oreDrillMediumSteel,1),"d d"," d ", 'd', TGItems.oreDrillSmallSteel));
        RecipeJsonConverter.addShapedRecipe(newStack(oreDrillMediumObsidianSteel,1),"d d"," d ", 'd', TGItems.oreDrillSmallObsidianSteel));
        RecipeJsonConverter.addShapedRecipe(newStack(oreDrillMediumCarbon,1),"d d"," d ", 'd', TGItems.oreDrillSmallCarbon));
        
        RecipeJsonConverter.addShapedRecipe(newStack(oreDrillLargeSteel,1),"d d"," d ", 'd', TGItems.oreDrillMediumSteel));
        RecipeJsonConverter.addShapedRecipe(newStack(oreDrillLargeObsidianSteel,1),"d d"," d ", 'd', TGItems.oreDrillMediumObsidianSteel));
        RecipeJsonConverter.addShapedRecipe(newStack(oreDrillLargeCarbon,1),"d d"," d ", 'd', TGItems.oreDrillMediumCarbon));
        	
        RecipeJsonConverter.addShapedRecipe(new ItemStack(TGBlocks.basicMachine,8,6), "rtr","tct","rtr", 'r', "itemRubber", 't', Blocks.tnt, 'c', "circuitBasic"));
        RecipeJsonConverter.addShapedRecipe(new ItemStack(TGBlocks.basicMachine,4,8), "rtr","tct","rtr", 'r', "sheetPlastic", 't', TGItems.tgx, 'c', "circuitBasic"));
	
      
        RecipeJsonConverter.addShapedRecipe(new ItemStack(TGItems.oreClusterScanner,1), "g g", "pcp","pep", 'g', "wireGold", 'p', "plateSteel", 'c', "circuitElite", 'e', TGItems.energyCell));
	
	
        RecipeJsonConverter.addShapedRecipe(new ItemStack(TGBlocks.metalpanel,32,0), "sss","sps","sss", 's', "stone", 'p', "plateCopper"));
        RecipeJsonConverter.addShapedRecipe(new ItemStack(TGBlocks.metalpanel2,64,0), "sss","psp","sss", 's', "stone", 'p', "plateCopper"));
          
        RecipeJsonConverter.addShapedRecipe(new ItemStack(TGItems.camoNetPlacer,1,0),"sns",'s',"stickWood", 'n', new ItemStack(TGBlocks.camoNetPane,1,0)));
        RecipeJsonConverter.addShapedRecipe(new ItemStack(TGItems.camoNetPlacer,1,1),"sns",'s',"stickWood", 'n', new ItemStack(TGBlocks.camoNetPane,1,1)));
        RecipeJsonConverter.addShapedRecipe(new ItemStack(TGItems.camoNetPlacer,1,2),"sns",'s',"stickWood", 'n', new ItemStack(TGBlocks.camoNetPane,1,2)));
        

                
        RecipeJsonConverter.addShapedRecipe(new ItemStack(TGBlocks.tgchest,1), "n n", " c ", "n n", 'c', Blocks.chest, 'n', "nuggetIron"));
        RecipeJsonConverter.addShapedRecipe(new ItemStack(TGBlocks.tgchest_weapon,1), "pcp", 'c', Blocks.chest, 'p', "plateIron"));
        */
	}
}
