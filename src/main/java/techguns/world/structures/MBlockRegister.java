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
import techguns.blocks.TGMetalPanelType;
import techguns.util.MBlock;
import techguns.util.MultiMBlock;
import techguns.world.BlockRotator;

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
}
