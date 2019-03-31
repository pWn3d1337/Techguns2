package techguns.world.structures;

import net.minecraft.block.Block;
import net.minecraft.block.BlockDirectional;
import net.minecraft.block.BlockHorizontal;
import net.minecraft.block.BlockStairs;
import net.minecraft.block.BlockStairs.EnumHalf;
import net.minecraft.init.Blocks;
import net.minecraft.item.EnumDyeColor;
import net.minecraft.util.EnumFacing;
import techguns.TGBlocks;
import techguns.blocks.EnumConcreteType;
import techguns.blocks.TGMetalPanelType;
import techguns.util.MBlock;
import techguns.util.MBlockCamoNetTop;
import techguns.util.MultiMBlock;
import techguns.world.BlockRotator;
import techguns.world.dungeon.presets.specialblocks.RandomStateMBlock;

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
	
	public static MultiMBlock SUPPLY_CRATES = new MultiMBlock(new Block[] {TGBlocks.MILITARY_CRATE,TGBlocks.MILITARY_CRATE,TGBlocks.MILITARY_CRATE,TGBlocks.MILITARY_CRATE,TGBlocks.MILITARY_CRATE,TGBlocks.MILITARY_CRATE,TGBlocks.MILITARY_CRATE,TGBlocks.MILITARY_CRATE,TGBlocks.MILITARY_CRATE},
															  new int[] {0,1,2,3,4,5,6,7,8}, new int[] {1,1,1,1,1,1,1,1,1});
	
	public static MultiMBlock SUPPLY_CRATES_CHANCE = new MultiMBlock(new Block[] {TGBlocks.MILITARY_CRATE,TGBlocks.MILITARY_CRATE,TGBlocks.MILITARY_CRATE,TGBlocks.MILITARY_CRATE,TGBlocks.MILITARY_CRATE,TGBlocks.MILITARY_CRATE,TGBlocks.MILITARY_CRATE,TGBlocks.MILITARY_CRATE,TGBlocks.MILITARY_CRATE, Blocks.AIR},
			  new int[] {0,1,2,3,4,5,6,7,8,0}, new int[] {1,1,1,1,1,1,1,1,1,9});
	
	public static MBlock AIR = new MBlock(Blocks.AIR.getDefaultState());
	
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
	public static MBlock FACTORY_PLATE_DOTTED_GREY = new MBlock(Blocks.CONCRETE,EnumDyeColor.SILVER.ordinal());
	public static MBlock FACTORY_PLATE_VERY_RUSTY = new MBlock(Blocks.CONCRETE,EnumDyeColor.GRAY.ordinal());
	public static MBlock FACTORY_PLATE_WORN_COLUMN = new MBlock(Blocks.HARDENED_CLAY,EnumDyeColor.SILVER.ordinal());
	public static MBlock FACTORY_WIREFRAME = new MBlock(TGBlocks.CONCRETE,3);
	public static MBlock FACTORY_SHINY_METAL = new MBlock(TGBlocks.METAL_PANEL,TGMetalPanelType.PANEL_LARGE_BORDER.ordinal());
	
	public static MBlock TECHNICAL_CONCRETE = new MBlock(TGBlocks.CONCRETE,1); //new MBlock(Blocks.CONCRETE,EnumDyeColor.GRAY.ordinal());
	public static MBlock TECHNICAL_BLOCK_SCAFFOLD = new MBlock(TGBlocks.METAL_PANEL.getDefaultState().withProperty(TGBlocks.METAL_PANEL.TYPE, TGMetalPanelType.STEELFRAME_SCAFFOLD));
	public static MBlock TECHNICAL_PIPES = new MBlock(TGBlocks.CONCRETE,EnumConcreteType.CONCRETE_BROWN_PIPES.ordinal());
	public static MBlock TECHNICAL_GRATE = new MBlock(TGBlocks.METAL_PANEL,TGMetalPanelType.STEELFRAME_DARK.ordinal());
	
	public static MBlock TECHNICAL2_SCAFFOLD_TRANSPARENT = new MBlock(TGBlocks.METAL_PANEL,TGMetalPanelType.STEELFRAME_SCAFFOLD.ordinal());
	public static MBlock TECHNICAL2_FAN = new MBlock(TGBlocks.METAL_PANEL,TGMetalPanelType.STEELFRAME_DARK.ordinal());
	
	public static MBlock IRON_BLOCK_VENTS = new MBlock(Blocks.IRON_BLOCK,0);
	public static MBlock IRON_BLOCK_SMALL_INGOTS = new MBlock(Blocks.IRON_BLOCK,0);
	
	public static MBlock ALUMINIUM_STAIRS_WEST = new MBlock(TGBlocks.METAL_STAIRS.getDefaultState().withProperty(TGBlocks.METAL_STAIRS.FACING, EnumFacing.WEST).withProperty(TGBlocks.METAL_STAIRS.HALF, EnumHalf.BOTTOM));
	public static MBlock ALUMINIUM_STAIRS_EAST = new MBlock(TGBlocks.METAL_STAIRS.getDefaultState().withProperty(TGBlocks.METAL_STAIRS.FACING, EnumFacing.EAST).withProperty(TGBlocks.METAL_STAIRS.HALF, EnumHalf.BOTTOM));
	
	//nether chisel blocks
	public static MBlock NETHERRACK_ROCKY = new MBlock(Blocks.NETHERRACK,0); //5
	public static MBlock NETHERRACK_C1 = new MBlock(Blocks.NETHERRACK,0);
	public static MBlock NETHERRACK_C2 = new MBlock(Blocks.NETHERRACK,0);
	public static MBlock NETHERRACK_C12 = new MBlock(Blocks.NETHERRACK,0);
	public static MBlock NETHERRACK_C8 = new MBlock(Blocks.NETHERRACK,0);
	
	public static MBlock NETHERBRICKS_C13 = new MBlock(Blocks.NETHER_BRICK,0);
	
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
	
	//cobblestone
	public static MultiMBlock COBBLESTONE_MOSSY_RND = new MultiMBlock(new Block[]{Blocks.COBBLESTONE,Blocks.MOSSY_COBBLESTONE},new int[]{0,0}, new int[]{2,1});
	public static MBlock COBBLESTONE_FLOOR = new MBlock(Blocks.STONE.getDefaultState());
	
	//limestone
	public static MBlock STONEBRICK_STAIRS_NORTH_INV = new MBlock(Blocks.STONE_BRICK_STAIRS.getDefaultState().withProperty(BlockHorizontal.FACING, EnumFacing.NORTH).withProperty(BlockStairs.HALF, EnumHalf.TOP)); //new MultiMBlock(new Block[]{ChiselBlocks.limestoneStairs[0], ChiselBlocks.limestoneStairs[3]},new int[]{8,0}, new int[]{1,1})
	public static MBlock STONEBRICK_STAIRS_SOUTH_INV = new MBlock(Blocks.STONE_BRICK_STAIRS.getDefaultState().withProperty(BlockHorizontal.FACING, EnumFacing.SOUTH).withProperty(BlockStairs.HALF, EnumHalf.TOP)); //new MultiMBlock(new Block[]{ChiselBlocks.limestoneStairs[0], ChiselBlocks.limestoneStairs[3]},new int[]{8,0}, new int[]{1,1})
	
	public static MBlock STONEBRICK_STAIRS_EAST_CRACKED_RND = new MultiMBlock(new Block[] {Blocks.STONE_BRICK_STAIRS, Blocks.AIR}, new int[] {Blocks.STONE_BRICK_STAIRS.getMetaFromState(Blocks.STONE_BRICK_STAIRS.getDefaultState().withProperty(BlockHorizontal.FACING, EnumFacing.EAST)),0}, new int[] {4,1});
	public static MBlock STONEBRICK_STAIRS_WEST_CRACKED_RND = new MultiMBlock(new Block[] {Blocks.STONE_BRICK_STAIRS, Blocks.AIR}, new int[] {Blocks.STONE_BRICK_STAIRS.getMetaFromState(Blocks.STONE_BRICK_STAIRS.getDefaultState().withProperty(BlockHorizontal.FACING, EnumFacing.WEST)),0}, new int[] {4,1});
	
	public static MBlock STONEBRICK_STAIRS_EAST_CRACKED_INV_RND = new MultiMBlock(new Block[] {Blocks.STONE_BRICK_STAIRS, Blocks.AIR}, new int[] {Blocks.STONE_BRICK_STAIRS.getMetaFromState(Blocks.STONE_BRICK_STAIRS.getDefaultState().withProperty(BlockHorizontal.FACING, EnumFacing.EAST).withProperty(BlockStairs.HALF, EnumHalf.TOP)),0}, new int[] {4,1});
	public static MBlock STONEBRICK_STAIRS_WEST_CRACKED_INV_RND = new MultiMBlock(new Block[] {Blocks.STONE_BRICK_STAIRS, Blocks.AIR}, new int[] {Blocks.STONE_BRICK_STAIRS.getMetaFromState(Blocks.STONE_BRICK_STAIRS.getDefaultState().withProperty(BlockHorizontal.FACING, EnumFacing.WEST).withProperty(BlockStairs.HALF, EnumHalf.TOP)),0}, new int[] {4,1});
	
	
	public static MBlock STONEBRICK_CRACKED_RND = new MultiMBlock(new Block[] {Blocks.STONEBRICK, Blocks.STONEBRICK, Blocks.STONEBRICK, Blocks.AIR}, new int[]{0,1,2,0}, new int[]{3,3,3,2}); //new MultiMBlock(new Block[]{ChiselBlocks.limestone,ChiselBlocks.limestone,Blocks.AIR},new int[]{1,6,0}, new int[]{2,2,1})
	
	//bricks
	public static MBlock BRICKS_CRACKED_RND = new MBlock(Blocks.BRICK_BLOCK,0); //new MultiMBlock(new Block[]{ChiselBlocks.brickCustom,ChiselBlocks.brickCustom},new int[]{3,5}, new int[]{2,2})

	//stone brick panel
	public static MBlock STONE_BRICKS_PANEL = new MBlock(Blocks.STONE,0);
	public static MBlock STONE_BRICKS_SMALL = new MBlock(Blocks.STONEBRICK,0);
	
	public static MBlock STONE_BRICKS_SUNKEN = new MBlock(Blocks.STONEBRICK,0);
	
	public static MBlock COBBLESTONE_1 = new MBlock(Blocks.COBBLESTONE,0);
	public static MBlock COBBLESTONE_2 = new MBlock(Blocks.COBBLESTONE,0);
	public static MBlock COBBLESTONE_3 = new MBlock(Blocks.COBBLESTONE,0);
	
	public static MBlock COBBLESTONE_13 = new MBlock(Blocks.STONEBRICK,0);
	public static MBlock COBBLESTONE_15 = new MBlock(Blocks.STONEBRICK,0);
	
	public static MBlock COBBLESTONE_7 = new MBlock(Blocks.STONE,6);
	
	public static MBlock ANDESITE_3 = new MBlock(Blocks.STONE,6);
	public static MBlock ANDESITE_4 = new MBlock(Blocks.STONE,6);
	public static MBlock ANDESITE_11 = new MBlock(Blocks.STONE,6);
	
	public static MBlock ANDESITE1_0 = new MBlock(Blocks.STONE,6);
	
	public static MBlock BRICKS_WEATHERED = new MBlock(Blocks.BRICK_BLOCK,0);
	public static MBlock BRICKS_WIDE = new MBlock(Blocks.BRICK_BLOCK,0);
	
	public static MBlock LABORATORY_WALL = new MBlock(Blocks.QUARTZ_BLOCK,0);
	public static MBlock LABORATORY_FLOOR = new MBlock(Blocks.STONE,6);
	public static MBlock LABORATORY_BORDER = new MBlock(Blocks.QUARTZ_BLOCK,2);
	public static MBlock LABORATORY_DECORATION = new MBlock(Blocks.QUARTZ_BLOCK,1);
	
	public static MBlock LIME_WALL = new MBlock(Blocks.SANDSTONE,2);
	public static MBlock LIME_FLOOR = new MBlock(Blocks.SANDSTONE,0);
	public static MBlock LIME_BORDER = new MBlock(Blocks.SANDSTONE,1);
	public static MBlock LIME_DECORATION = new MBlock(Blocks.SANDSTONE,1);
	
	public static MBlock IRON_PANE_MODERN_FENCE = new MBlock(Blocks.IRON_BARS,0);
	
	public static MBlock OAK_PLANKS_1 = new MBlock(Blocks.PLANKS,0);
	
	public static MBlock BLUE_CONCRETE_SMALL_BRICKS = new MBlock(Blocks.CONCRETE,11);
	
	public static MBlock GAS_STATION_CONSOLE = new MBlock(Blocks.QUARTZ_STAIRS,1);
	
	public static MBlock SURIVIVOR_HIDEOUT_IRON_BOTTOM = new MBlock(Blocks.IRON_BLOCK,0);
	public static MBlock SURIVIVOR_HIDEOUT_IRON_TOP = new MBlock(Blocks.IRON_BLOCK,0);
	
	public static MBlockCamoNetTop CAMO_NET_TOP = new MBlockCamoNetTop();
}
