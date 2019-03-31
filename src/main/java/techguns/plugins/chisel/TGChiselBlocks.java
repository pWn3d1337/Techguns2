package techguns.plugins.chisel;

import net.minecraft.block.Block;
import net.minecraftforge.fml.common.registry.GameRegistry.ObjectHolder;
import techguns.util.MBlock;
import techguns.world.structures.MBlockRegister;

public class TGChiselBlocks {
	
	@ObjectHolder("chisel:planks-oak")
	public static final Block CHISEL_OAK_PLANKS = null;
	
	@ObjectHolder("chisel:technical")
	public static final Block CHISEL_TECHNICAL = null;
	
	@ObjectHolder("chisel:technical1")
	public static final Block CHISEL_TECHNICAL2 = null;
	
	@ObjectHolder("chisel:factory")
	public static final Block CHISEL_FACTORY = null;
	
	@ObjectHolder("chisel:cobblestone")
	public static final Block CHISEL_COBBLESTONE = null;
	
	@ObjectHolder("chisel:iron")
	public static final Block CHISEL_IRON = null;
	
	@ObjectHolder("chisel:netherrack")
	public static final Block CHISEL_NETHERRACK = null;
	
	@ObjectHolder("chisel:netherbrick")
	public static final Block CHISEL_NETHERBRICK = null;
	
	@ObjectHolder("chisel:stonebrick1")
	public static final Block CHISEL_STONEBRICK1 = null;
	
	@ObjectHolder("chisel:stonebrick")
	public static final Block CHISEL_STONEBRICK = null;
	
	@ObjectHolder("chisel:stonebrick2")
	public static final Block CHISEL_STONEBRICK2 = null;
	
	@ObjectHolder("chisel:andesite")
	public static final Block CHISEL_ANDESITE = null;
	
	@ObjectHolder("chisel:andesite1")
	public static final Block CHISEL_ANDESITE1 = null;
	
	@ObjectHolder("chisel:bricks")
	public static final Block CHISEL_BRICKS = null;
	
	@ObjectHolder("chisel:laboratory")
	public static final Block CHISEL_LABORATORY = null;
	
	@ObjectHolder("chisel:limestone")
	public static final Block CHISEL_LIMESTONE = null;
	
	@ObjectHolder("chisel:limestone2")
	public static final Block CHISEL_LIMESTONE2 = null;
	
	@ObjectHolder("chisel:ironpane")
	public static final Block CHISEL_IRON_PANE = null;
	
	@ObjectHolder("chisel:concrete_blue1")
	public static final Block CHISEL_CONCRETE_BLUE1 = null;
	
	public static void postInit() {
		if(CHISEL_OAK_PLANKS!=null) {
			MBlockRegister.BARRACKS_WOOD_FLOOR = new MBlock(CHISEL_OAK_PLANKS,7);
			//MBlockRegister.WOOD_PILLAR = new MBlock(CHISEL_OAK_PLANKS,11);
			MBlockRegister.BARRACKS_WOOD_SCAFFOLD = new MBlock(CHISEL_OAK_PLANKS,12);
			//MBlockRegister.WOOD_WALL = new MBlock(CHISEL_OAK_PLANKS,12);
			MBlockRegister.BARRACKS_WOOD_FLOOR = new MBlock(CHISEL_OAK_PLANKS,10);
			
			MBlockRegister.OAK_PLANKS_1 = new MBlock(CHISEL_OAK_PLANKS,1);
		}
		
		if(CHISEL_TECHNICAL!=null) {
			MBlock scaffold = new MBlock(CHISEL_TECHNICAL,0);
			
			MBlockRegister.TANKS_BASE = scaffold;
			
			MBlockRegister.TECHNICAL_CONCRETE = new MBlock(CHISEL_TECHNICAL,12);
			MBlockRegister.TECHNICAL_BLOCK_SCAFFOLD = scaffold;
			
			MBlockRegister.TECHNICAL_PIPES = new MBlock(CHISEL_TECHNICAL,5);
			MBlockRegister.TECHNICAL_GRATE = new MBlock(CHISEL_TECHNICAL,13);
			
			MBlockRegister.SURIVIVOR_HIDEOUT_IRON_BOTTOM = new MBlock(CHISEL_TECHNICAL,12);
		}
		if(CHISEL_TECHNICAL2!=null) {
			MBlockRegister.TECHNICAL2_FAN = new MBlock(CHISEL_TECHNICAL2,1);
			MBlockRegister.TECHNICAL2_SCAFFOLD_TRANSPARENT = new MBlock(CHISEL_TECHNICAL2,0);
		}
		
		if(CHISEL_FACTORY!=null) {
			MBlock chiselHazard = new MBlock(CHISEL_FACTORY, 6);
			MBlock factorywall = new MBlock(CHISEL_FACTORY, 1);
			
			MBlockRegister.TANKS_WALL = factorywall;
			MBlockRegister.TANKS_BORDER = chiselHazard;
			
			MBlockRegister.HELIPAD_WIREFRAME = new MBlock(CHISEL_FACTORY, 4);
			MBlockRegister.HELIPAD_HAZARDBLOCK = chiselHazard;
			
			MBlockRegister.FACTORY_PLATE = factorywall;
			MBlockRegister.FACTORY_PLATE_DOTTED = new MBlock(CHISEL_FACTORY, 0);
			MBlockRegister.FACTORY_CRATE = new MBlock(CHISEL_FACTORY, 9);
			
			MBlockRegister.FACTORY_PLATE_DOTTED_GREY = new MBlock(CHISEL_FACTORY,3);
			MBlockRegister.FACTORY_PLATE_VERY_RUSTY = new MBlock(CHISEL_FACTORY,2);
			MBlockRegister.FACTORY_PLATE_WORN_COLUMN = new MBlock(CHISEL_FACTORY,15);
			MBlockRegister.FACTORY_SHINY_METAL = new MBlock(CHISEL_FACTORY,12);
			MBlockRegister.FACTORY_WIREFRAME = new MBlock(CHISEL_FACTORY,4);
			
			MBlockRegister.SURIVIVOR_HIDEOUT_IRON_TOP = new MBlock(CHISEL_FACTORY,9);
			
		}
		if(CHISEL_COBBLESTONE!=null) {
			MBlockRegister.COBBLESTONE_FLOOR = new MBlock(CHISEL_COBBLESTONE,7);
			
			MBlockRegister.COBBLESTONE_1 = new MBlock(CHISEL_COBBLESTONE,1);
			MBlockRegister.COBBLESTONE_2 = new MBlock(CHISEL_COBBLESTONE,2);
			MBlockRegister.COBBLESTONE_3 = new MBlock(CHISEL_COBBLESTONE,3);
			
			MBlockRegister.COBBLESTONE_7 = new MBlock(CHISEL_COBBLESTONE,7);
			MBlockRegister.COBBLESTONE_13 = new MBlock(CHISEL_COBBLESTONE,13);
			MBlockRegister.COBBLESTONE_15 = new MBlock(CHISEL_COBBLESTONE,15);
		}
		if(CHISEL_IRON!=null) {
			MBlockRegister.IRON_BLOCK_VENTS = new MBlock(CHISEL_IRON,13);
			MBlockRegister.IRON_BLOCK_SMALL_INGOTS = new MBlock(CHISEL_IRON,1);
		}
		if(CHISEL_NETHERRACK!=null) {
			MBlockRegister.NETHERRACK_ROCKY = new MBlock(CHISEL_NETHERRACK,5);
			MBlockRegister.NETHERRACK_C1 = new MBlock(CHISEL_NETHERRACK,1);
			MBlockRegister.NETHERRACK_C2 = new MBlock(CHISEL_NETHERRACK,2);
			MBlockRegister.NETHERRACK_C8 = new MBlock(CHISEL_NETHERRACK,8);
			MBlockRegister.NETHERRACK_C12 = new MBlock(CHISEL_NETHERRACK,12);
		}
		if(CHISEL_NETHERBRICK!=null) {
			MBlockRegister.NETHERBRICKS_C13 = new MBlock(CHISEL_NETHERBRICK,13);
		}
		if(CHISEL_STONEBRICK1!=null) {
			MBlockRegister.STONE_BRICKS_PANEL = new MBlock(CHISEL_STONEBRICK1,2);
		}
		if(CHISEL_STONEBRICK!=null) {
			MBlockRegister.STONE_BRICKS_SMALL = new MBlock(CHISEL_STONEBRICK,8);
		}
		if (CHISEL_STONEBRICK2!=null) {
			MBlockRegister.STONE_BRICKS_SUNKEN = new MBlock(CHISEL_STONEBRICK2,7);
		}
		if(CHISEL_ANDESITE!=null) {
			MBlockRegister.ANDESITE_3 = new MBlock(CHISEL_ANDESITE,3);
			MBlockRegister.ANDESITE_4 = new MBlock(CHISEL_ANDESITE,4);
			MBlockRegister.ANDESITE_11 = new MBlock(CHISEL_ANDESITE,11);
		}
		if(CHISEL_ANDESITE1!=null) {
			MBlockRegister.ANDESITE1_0 = new MBlock(CHISEL_ANDESITE1,0);
		}
		if(CHISEL_BRICKS!=null) {
			MBlockRegister.BRICKS_WEATHERED = new MBlock(CHISEL_BRICKS,1);
			MBlockRegister.BRICKS_WIDE = new MBlock(CHISEL_BRICKS,3);
		}
		if(CHISEL_LABORATORY!=null) {
			MBlockRegister.LABORATORY_WALL = new MBlock(CHISEL_LABORATORY,2);
			MBlockRegister.LABORATORY_FLOOR = new MBlock(CHISEL_LABORATORY,8);
			MBlockRegister.LABORATORY_BORDER = new MBlock(CHISEL_LABORATORY,4);
			MBlockRegister.LABORATORY_DECORATION = new MBlock(CHISEL_LABORATORY,15);
			MBlockRegister.GAS_STATION_CONSOLE = new MBlock(CHISEL_LABORATORY,15);
		}
		if(CHISEL_LIMESTONE!=null) {
			MBlockRegister.LIME_WALL = new MBlock(CHISEL_LIMESTONE,1);
			MBlockRegister.LIME_FLOOR = new MBlock(CHISEL_LIMESTONE,7);
			MBlockRegister.LIME_BORDER = new MBlock(CHISEL_LIMESTONE,11);
		}
		if(CHISEL_LIMESTONE2!=null) {
			MBlockRegister.LIME_DECORATION = new MBlock(CHISEL_LIMESTONE2,2);
		}
		if(CHISEL_IRON_PANE!=null) {
			MBlockRegister.IRON_PANE_MODERN_FENCE = new MBlock(CHISEL_IRON_PANE, 12);
		}
		if(CHISEL_CONCRETE_BLUE1!=null) {
			MBlockRegister.BLUE_CONCRETE_SMALL_BRICKS = new MBlock(CHISEL_CONCRETE_BLUE1, 9);
		}
	}
}
