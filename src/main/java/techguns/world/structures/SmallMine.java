package techguns.world.structures;

import java.util.ArrayList;
import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.world.World;
import techguns.TGBlocks;
import techguns.blocks.EnumMonsterSpawnerType;
import techguns.blocks.EnumOreClusterType;
import techguns.entities.npcs.ZombieMiner;
import techguns.util.BlockUtils;
import techguns.util.MBlock;
import techguns.util.MBlockBiomeColorType;
import techguns.util.MultiMBlock;
import techguns.util.MultiMMBlock;
import techguns.world.dungeon.presets.specialblocks.MBlockTGSpawner;

public class SmallMine extends WorldgenStructure {

	static short[][] blocks;
	static ArrayList<MBlock> blockList = new ArrayList<MBlock>();
	static {
		
		blockList.add(new MBlock(Blocks.AIR, 0));
		blockList.add(new MBlock(Blocks.STONE, 0));
		blockList.add(new MBlock(Blocks.LOG, 0));
		blockList.add(new MBlockBiomeColorType(new Block[] {Blocks.GRASS, Blocks.SNOW, Blocks.SAND, Blocks.NETHERRACK }, new int[] {0,0,0,0}));
		blockList.add(new MBlock(Blocks.LOG, 8));
		blockList.add(new MultiMMBlock(new MBlock[] {new MBlockBiomeColorType(new Block[] {Blocks.GRASS, Blocks.SNOW, Blocks.SAND, Blocks.NETHERRACK }, new int[] {0,0,0,0}), new MBlock(Blocks.AIR,0)}, new int[] {4,1}));
		blockList.add(new MBlock(Blocks.RAIL, 1));
		blockList.add(new MBlock(Blocks.LOG, 4));
		blockList.add(new MBlock(Blocks.STONE_STAIRS, 1));
		blockList.add(new MBlock(Blocks.RAIL, 3));
		blockList.add(new MBlock(Blocks.TORCH, 3));
		blockList.add(new MBlock(Blocks.TORCH, 4));
		blockList.add(new MBlock(Blocks.TORCH, 2));
		blockList.add(new MultiMBlock(new Block[]{Blocks.COAL_ORE,Blocks.STONE},new int[]{0,0}, new int[]{1,4}));
		blockList.add(new MBlockTGSpawner(EnumMonsterSpawnerType.HOLE,2,1,200,1).addMobType(ZombieMiner.class, 1));
		blockList.add(new MultiMBlock(new Block[]{TGBlocks.ORE_CLUSTER,Blocks.COAL_ORE},new int[]{EnumOreClusterType.COAL.ordinal(),0}, new int[]{1,1}));
		blockList.add(new MBlock(TGBlocks.ORE_CLUSTER,EnumOreClusterType.COAL.ordinal()));
	
		blocks = BlockUtils.loadStructureFromFile("small_mine");
	}

	public SmallMine(int minX, int minY, int minZ, int maxX, int maxY, int maxZ) {
		super(minX, minY, minZ, maxX, maxY, maxZ);
	}

	@Override
	public void setBlocks(World world, int posX, int posY, int posZ, int sizeX, int sizeY, int sizeZ, int direction,
			BiomeColorType colorType, Random rnd) {
		
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
				BlockUtils.cleanUpwards(world, blocks, blockList, posX, posY, posZ, centerX, centerZ, direction, 0, 4);
				//BlockUtils.placeFoundation(world, blocks, blockList, posX, posY, posZ, centerX, centerZ, direction, 0,1);
				BlockUtils.placeScannedStructure(world, blocks, blockList, posX, posY-5, posZ, centerX, centerZ, direction, 0,this.lootTier,colorType);
				BlockUtils.placeScannedStructure(world, blocks, blockList, posX, posY-5, posZ, centerX, centerZ, direction, 1,this.lootTier,colorType);
		
	}

}
