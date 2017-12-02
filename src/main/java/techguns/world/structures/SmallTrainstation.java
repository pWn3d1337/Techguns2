package techguns.world.structures;

import java.util.ArrayList;
import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.world.World;
import techguns.TGBlocks;
import techguns.blocks.EnumMonsterSpawnerType;
import techguns.entities.npcs.ZombieMiner;
import techguns.util.BlockUtils;
import techguns.util.MBlock;
import techguns.util.MultiMBlock;
import techguns.world.dungeon.presets.specialblocks.MBlockTGSpawner;

public class SmallTrainstation extends WorldgenStructure {
	static ArrayList<MBlock> blockList = new ArrayList<MBlock>();
	static short[][] blocks;
	static {
		blockList.add(new MBlock(Blocks.GRAVEL, 0));
		
		blockList.add(new MultiMBlock(new Block[]{Blocks.RAIL,Blocks.AIR},new int[]{0,0}, new int[]{2,1}));
		
		blockList.add(MBlockRegister.COBBLESTONE_MOSSY_RND);
		blockList.add(MBlockRegister.STONEBRICK_STAIRS_EAST_CRACKED_RND);
		
		blockList.add(MBlockRegister.COBBLESTONE_FLOOR);

		blockList.add(MBlockRegister.BRICKS_CRACKED_RND);
		
		blockList.add(new MBlock(Blocks.AIR, 0));
		blockList.add(new MultiMBlock(new Block[]{Blocks.GLASS_PANE,Blocks.AIR},new int[]{4,0}, new int[]{1,1}));
		
		blockList.add(MBlockRegister.STONEBRICK_STAIRS_WEST_CRACKED_INV_RND);//new MultiMBlock(new Block[]{ChiselBlocks.limestoneStairs[0], ChiselBlocks.limestoneStairs[3], Blocks.AIR},new int[]{13,5,0}, new int[]{2,2,1}));
		
		blockList.add(MBlockRegister.STONEBRICK_CRACKED_RND);
		
		
		blockList.add(new MBlock(Blocks.OAK_FENCE, 0));
		blockList.add(new MBlock(Blocks.WOODEN_PRESSURE_PLATE, 0));
		blockList.add(new MBlock(Blocks.WOODEN_SLAB, 8));
		blockList.add(new MBlock(Blocks.LADDER, 3));
		blockList.add(new MBlock(Blocks.CHEST, 2));
		//blockList.add(new MBlock(Block.getBlockFromName("tile.chisel.limestone_stairs.0"), 14));
		blockList.add(MBlockRegister.STONEBRICK_STAIRS_SOUTH_INV); //new MultiMBlock(new Block[]{ChiselBlocks.limestoneStairs[0], ChiselBlocks.limestoneStairs[3], Blocks.air},new int[]{14,6,0}, new int[]{2,2,1}, new BlockType[]{BlockType.STAIRS2_CHISEL,BlockType.STAIRS, BlockType.TG}, new boolean[]{true,true,false}));
		
		
		//blockList.add(new MBlock(Block.getBlockFromName("tile.chisel.limestone_stairs.0"), 15));
		blockList.add(MBlockRegister.STONEBRICK_STAIRS_NORTH_INV);//new MultiMBlock(new Block[]{ChiselBlocks.limestoneStairs[0], ChiselBlocks.limestoneStairs[3], Blocks.AIR},new int[]{15,7,0}, new int[]{2,2,1}, new BlockType[]{BlockType.STAIRS2_CHISEL,BlockType.STAIRS, BlockType.TG}, new boolean[]{true,true,false}));
		
		blockList.add(new MBlock(Blocks.FURNACE, 4));
		blockList.add(new MBlock(Blocks.CRAFTING_TABLE, 0));
		//blockList.add(new MBlock(Block.getBlockFromName("tile.chisel.limestone_stairs.0"), 12));
		blockList.add(MBlockRegister.STONEBRICK_STAIRS_EAST_CRACKED_INV_RND);//new MultiMBlock(new Block[]{ChiselBlocks.limestoneStairs[0], ChiselBlocks.limestoneStairs[3], Blocks.AIR},new int[]{12,4,0}, new int[]{2,2,1}, new BlockType[]{BlockType.STAIRS2_CHISEL,BlockType.STAIRS, BlockType.TG}, new boolean[]{true,true,false}));
		
		
		//blockList.add(new MBlock(Block.getBlockFromName("tile.chisel.limestone_stairs.0"), 9));
		blockList.add(MBlockRegister.STONEBRICK_STAIRS_WEST_CRACKED_RND); //new MultiMBlock(new Block[]{ChiselBlocks.limestoneStairs[0], ChiselBlocks.limestoneStairs[3], Blocks.AIR},new int[]{9,1,0}, new int[]{2,2,1}, new BlockType[]{BlockType.STAIRS2_CHISEL,BlockType.STAIRS, BlockType.TG}, new boolean[]{true,true,false}));
		
		blockList.add(new MBlockTGSpawner(EnumMonsterSpawnerType.HOLE,3,2,200,1).addMobType(ZombieMiner.class, 1));
		
		blocks = BlockUtils.loadStructureFromFile("smallTrainstation");
	}
	
	public SmallTrainstation(int minX, int minY, int minZ, int maxX, int maxY, int maxZ) {
		super(minX, minY, minZ, maxX, maxY, maxZ);
	}

	@Override
	public void setBlocks(World world, int posX, int posY, int posZ, int sizeX, int sizeY, int sizeZ, int direction, BiomeColorType colorType, Random rnd) {
	int centerX, centerZ;
		
		if (((sizeX < this.minX) && (sizeZ > this.minX) && (sizeX >= this.minZ))
		   ||((sizeZ < this.minZ) && (sizeX > this.minZ) && (sizeZ >= this.minX)))
		{
			direction = (direction+1) % 4;
			centerZ = (int) (sizeX/2.0f);
			centerX = (int) (sizeZ/2.0f);
		}else {
			centerX = (int) (sizeX/2.0f);
			centerZ = (int) (sizeZ/2.0f);
		}
		BlockUtils.cleanUpwards(world, blocks, blockList, posX, posY, posZ, centerX, centerZ, direction, 0, 7);
		BlockUtils.placeFoundation(world, blocks, blockList, posX, posY, posZ, centerX, centerZ, direction, 0,1);
		BlockUtils.placeScannedStructure(world, blocks, blockList, posX, posY, posZ, centerX, centerZ, direction, 0,this.lootTier);
		BlockUtils.placeScannedStructure(world, blocks, blockList, posX, posY, posZ, centerX, centerZ, direction, 1,this.lootTier);
	}
}
