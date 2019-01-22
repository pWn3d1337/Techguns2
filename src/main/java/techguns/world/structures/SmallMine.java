package techguns.world.structures;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.world.World;
import techguns.TGBlocks;
import techguns.blocks.EnumMonsterSpawnerType;
import techguns.blocks.EnumOreClusterType;
import techguns.blocks.EnumOreType;
import techguns.entities.npcs.ZombieMiner;
import techguns.util.BlockUtils;
import techguns.util.MBlock;
import techguns.util.MBlockBiomeColorType;
import techguns.util.MBlockOreClusterTypeOre;
import techguns.util.MBlockOreclusterType;
import techguns.util.MultiMBlock;
import techguns.util.MultiMMBlock;
import techguns.world.dungeon.presets.specialblocks.MBlockTGSpawner;

public class SmallMine extends WorldgenStructure {

	static short[][] blocks;
	static ArrayList<MBlock> blockList = new ArrayList<MBlock>();
	static EnumOreClusterType[] types = {EnumOreClusterType.COAL, EnumOreClusterType.COMMON_METAL, EnumOreClusterType.COMMON_GEM, EnumOreClusterType.RARE_METAL, EnumOreClusterType.SHINY_METAL, EnumOreClusterType.SHINY_GEM, EnumOreClusterType.URANIUM};
	static MBlock[] ores = {new MBlock(Blocks.COAL_ORE,0),
			new MultiMBlock(new Block[] {Blocks.IRON_ORE, TGBlocks.TG_ORE, TGBlocks.TG_ORE}, new int[] {0,EnumOreType.ORE_COPPER.ordinal(), EnumOreType.ORE_TIN.ordinal()}, new int[] {1,1,1}),
			new MultiMBlock(new Block[] {Blocks.REDSTONE_ORE, Blocks.LAPIS_ORE}, new int[] {0,0}, new int[] {1,1}),
			new MBlock(TGBlocks.TG_ORE, EnumOreType.ORE_LEAD.ordinal()),
			new MultiMBlock(new Block[] {Blocks.GOLD_ORE, TGBlocks.TG_ORE}, new int[] {0,EnumOreType.ORE_TITANIUM.ordinal()}, new int[] {1,1}),
			new MultiMBlock(new Block[] {Blocks.DIAMOND_ORE, Blocks.EMERALD_ORE, Blocks.STONE}, new int[] {0,0,0}, new int[] {2,1,3}),
			new MultiMBlock(new Block[] {TGBlocks.TG_ORE, Blocks.STONE}, new int[] {EnumOreType.ORE_URANIUM.ordinal(),0}, new int[] {1,1}),
	};
	static int[] oreWeights = {1,1,1,1,1,1,1};
	static int[] clusterWeights = {10,10,10,5,2,2,2};
	static int totalweight=0;
	static {
		Arrays.stream(clusterWeights).forEach(i -> totalweight+=i);
		blockList.add(new MBlock(Blocks.AIR, 0));
		blockList.add(new MBlock(Blocks.STONE, 0));
		blockList.add(new MBlock(Blocks.LOG, 0));
		blockList.add(new MBlockBiomeColorType(new Block[] {Blocks.GRASS, Blocks.SNOW, Blocks.SAND, Blocks.NETHERRACK }, new int[] {0,0,0,0}));
		blockList.add(new MBlock(Blocks.LOG, 8));
		blockList.add(new MultiMMBlock(new MBlock[] {new MBlockBiomeColorType(new Block[] {Blocks.GRASS, Blocks.GRASS, Blocks.SAND, Blocks.NETHERRACK }, new int[] {0,0,0,0}), new MBlock(Blocks.AIR,0)}, new int[] {4,1}));
		blockList.add(new MBlock(Blocks.RAIL, 1));
		blockList.add(new MBlock(Blocks.LOG, 4));
		blockList.add(new MBlock(Blocks.STONE_STAIRS, 1));
		blockList.add(new MBlock(Blocks.RAIL, 3));
		blockList.add(new MBlock(Blocks.TORCH, 3));
		blockList.add(new MBlock(Blocks.TORCH, 4));
		blockList.add(new MBlock(Blocks.TORCH, 2));
		blockList.add(new MBlockOreClusterTypeOre(ores, oreWeights, new MBlock(Blocks.STONE,0),0.75f));
		blockList.add(new MBlockTGSpawner(EnumMonsterSpawnerType.HOLE,2,1,200,1).addMobType(ZombieMiner.class, 1));
		blockList.add(new MBlockOreclusterType(types, clusterWeights, 0.5f, ores));
		blockList.add(new MBlockOreclusterType(types, clusterWeights, 0, null));
	
		blocks = BlockUtils.loadStructureFromFile("small_mine");
	}

	public SmallMine() {
		super(17, 11, 11, 17, 11, 11);
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
				
		
				int indexRoll=rollBlockIndex(rnd, totalweight, clusterWeights);
		
				BlockUtils.cleanUpwards(world, blocks, blockList, posX, posY, posZ, centerX, centerZ, direction, 0, 4);
				//BlockUtils.placeFoundation(world, blocks, blockList, posX, posY, posZ, centerX, centerZ, direction, 0,1);
				BlockUtils.placeScannedStructure(world, blocks, blockList, posX, posY-5, posZ, centerX, centerZ, direction, 0,this.lootTier,colorType,indexRoll,rnd);
				BlockUtils.placeScannedStructure(world, blocks, blockList, posX, posY-5, posZ, centerX, centerZ, direction, 1,this.lootTier,colorType);
		
	}

}
