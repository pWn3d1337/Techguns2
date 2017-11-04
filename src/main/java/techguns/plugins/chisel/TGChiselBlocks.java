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
	
	@ObjectHolder("chisel:factory")
	public static final Block CHISEL_FACTORY = null;
	
	
	public static void postInit() {
		if(CHISEL_OAK_PLANKS!=null) {
			MBlockRegister.BARRACKS_WOOD_FLOOR = new MBlock(CHISEL_OAK_PLANKS,7);
			//MBlockRegister.WOOD_PILLAR = new MBlock(CHISEL_OAK_PLANKS,11);
			MBlockRegister.BARRACKS_WOOD_SCAFFOLD = new MBlock(CHISEL_OAK_PLANKS,12);
			//MBlockRegister.WOOD_WALL = new MBlock(CHISEL_OAK_PLANKS,12);
			MBlockRegister.BARRACKS_WOOD_FLOOR = new MBlock(CHISEL_OAK_PLANKS,10);
		}
		
		if(CHISEL_TECHNICAL!=null) {
			MBlockRegister.TANKS_BASE = new MBlock(CHISEL_TECHNICAL, 0);
		}
		if(CHISEL_FACTORY!=null) {
			MBlock chiselHazard = new MBlock(CHISEL_FACTORY, 6);
			
			MBlockRegister.TANKS_WALL = new MBlock(CHISEL_FACTORY, 1);
			MBlockRegister.TANKS_BORDER = chiselHazard;
			
			MBlockRegister.HELIPAD_WIREFRAME = new MBlock(CHISEL_FACTORY, 4);
			MBlockRegister.HELIPAD_HAZARDBLOCK = chiselHazard;
		}
	}
}
