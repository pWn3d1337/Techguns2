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
	
	public static void postInit() {
		if(CHISEL_OAK_PLANKS!=null) {
			MBlockRegister.BARRACKS_WOOD_FLOOR = new MBlock(CHISEL_OAK_PLANKS,7);
			//MBlockRegister.WOOD_PILLAR = new MBlock(CHISEL_OAK_PLANKS,11);
			MBlockRegister.BARRACKS_WOOD_SCAFFOLD = new MBlock(CHISEL_OAK_PLANKS,12);
			//MBlockRegister.WOOD_WALL = new MBlock(CHISEL_OAK_PLANKS,12);
			MBlockRegister.BARRACKS_WOOD_FLOOR = new MBlock(CHISEL_OAK_PLANKS,10);
		}
		
		if(CHISEL_TECHNICAL!=null) {
			MBlock scaffold = new MBlock(CHISEL_TECHNICAL,0);
			
			MBlockRegister.TANKS_BASE = scaffold;
			
			MBlockRegister.TECHNICAL_CONCRETE = new MBlock(CHISEL_TECHNICAL,12);
			MBlockRegister.TECHNICAL_BLOCK_SCAFFOLD = scaffold;
			
			MBlockRegister.TECHNICAL_PIPES = new MBlock(CHISEL_TECHNICAL,5);
			MBlockRegister.TECHNICAL_GRATE = new MBlock(CHISEL_TECHNICAL,13);
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
			
		}
		if(CHISEL_COBBLESTONE!=null) {
			MBlockRegister.COBBLESTONE_FLOOR = new MBlock(CHISEL_COBBLESTONE,7);
		}
		if(CHISEL_IRON!=null) {
			MBlockRegister.IRON_BLOCK_VENTS = new MBlock(CHISEL_IRON,13);
		}
		
	}
}
