package techguns.world.structures;

import net.minecraft.init.Blocks;
import net.minecraft.item.EnumDyeColor;
import techguns.TGBlocks;
import techguns.blocks.TGMetalPanelType;
import techguns.util.MBlock;

public class MBlockRegister {
	public static MBlock[] crates = {
			new MBlock(TGBlocks.MILITARY_CRATE,0),
			new MBlock(TGBlocks.MILITARY_CRATE,1),
			new MBlock(TGBlocks.MILITARY_CRATE,2),
			new MBlock(TGBlocks.MILITARY_CRATE,3),
			new MBlock(TGBlocks.MILITARY_CRATE,4),
			new MBlock(TGBlocks.MILITARY_CRATE,5),
			new MBlock(TGBlocks.MILITARY_CRATE,6),
			new MBlock(TGBlocks.MILITARY_CRATE,7),
			new MBlock(TGBlocks.MILITARY_CRATE,8)	
	};
	
	public static MBlock WOOD_STAIRS_OAK = new MBlock (Blocks.OAK_STAIRS, 0);
	public static MBlock TGLAMP = new MBlock (TGBlocks.LAMP_0, 0);
	public static MBlock GRAVEL = new MBlock (Blocks.GRAVEL, 0);
	public static MBlock GLASS_PANE = new MBlock (Blocks.GLASS_PANE, 0);
	
	//Blocks that get changed by chisel
	public static MBlock BARRACKS_WOOD_PILLAR = new MBlock(Blocks.LOG,0);//TGChiselBlocks.wood_pillar;
	public static MBlock BARRACKS_WOOD_WALL = new MBlock(Blocks.PLANKS,0);//TGChiselBlocks.wood_wall;
	public static MBlock BARRACKS_WOOD_FLOOR = new MBlock(Blocks.PLANKS,0);//TGChiselBlocks.wood_floor;
	public static MBlock BARRACKS_WOOD_SCAFFOLD = new MBlock(Blocks.PLANKS,5);//TGChiselBlocks.wood_scaffold;
	public static MBlock BARRACKS_WOOD_ROOF = new MBlock(Blocks.PLANKS,1); //TGChiselBlocks.wood_roof;
	public static MBlock BARRACKS_WOOD_ROOFSLAB = new MBlock(Blocks.WOODEN_SLAB,1); //TGChiselBlocks.wood_roofSlab;
	
	//chisel blocks with fallbacks
	public static MBlock FACTORY_PLATE_DOTTED = new MBlock(Blocks.HARDENED_CLAY,EnumDyeColor.GRAY.ordinal());
	public static MBlock FACTORY_PLATE = new MBlock(Blocks.BRICK_BLOCK,0);
	public static MBlock FACTORY_CRATE = new MBlock(TGBlocks.METAL_PANEL.getDefaultState().withProperty(TGBlocks.METAL_PANEL.TYPE, TGMetalPanelType.CONTAINER_RED));
	
	public static MBlock TECHNICAL_CONCRETE = new MBlock(TGBlocks.CONCRETE,1); //new MBlock(Blocks.CONCRETE,EnumDyeColor.GRAY.ordinal());
	public static MBlock TECHNICAL_BLOCK_SCAFFOLD = new MBlock(TGBlocks.METAL_PANEL.getDefaultState().withProperty(TGBlocks.METAL_PANEL.TYPE, TGMetalPanelType.STEELFRAME_SCAFFOLD));
	
	//Military Base
	public static MBlock MILBASE_FENCE = new MBlock(Blocks.IRON_BARS,0);
	public static MBlock MILBASE_ROADBLOCK = new MBlock(Blocks.GRAVEL, 0);
	public static MBlock MILBASE_ROADBLOCK_SECONDARY = new MBlock(TGBlocks.CONCRETE,2); //new MBlock(Blocks.CONCRETE.getDefaultState().withProperty(BlockColored.COLOR, EnumDyeColor.GRAY));
	
	//Tanks
	public static MBlock TANKS_BASE = new MBlock(TGBlocks.METAL_PANEL,7);//TGChiselBlocks.technical_scaffold;
	public static MBlock TANKS_WALL = new MBlock(TGBlocks.METAL_PANEL,0);//TGChiselBlocks.factory_wall;
	public static MBlock TANKS_BORDER = new MBlock(TGBlocks.METAL_PANEL,3);//TGChiselBlocks.factory_hazard;
	
	//Helipad
	public static MBlock HELIPAD_GLOWBLOCK = new MBlock(TGBlocks.NEONLIGHT_BLOCK,4);
	public static MBlock HELIPAD_WIREFRAME = new MBlock(TGBlocks.METAL_PANEL, 6);
	public static MBlock HELIPAD_CONCRETE = new MBlock(TGBlocks.CONCRETE, 3);
	public static MBlock HELIPAD_HAZARDBLOCK = new MBlock(Blocks.CONCRETE, EnumDyeColor.YELLOW.ordinal());
	
}
