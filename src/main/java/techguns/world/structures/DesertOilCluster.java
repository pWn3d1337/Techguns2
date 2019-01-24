package techguns.world.structures;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.world.World;
import techguns.TGBlocks;
import techguns.TGFluids;
import techguns.blocks.EnumMonsterSpawnerType;
import techguns.blocks.EnumOreClusterType;
import techguns.blocks.EnumOreType;
import techguns.entities.npcs.AlienBug;
import techguns.entities.npcs.ArmySoldier;
import techguns.entities.npcs.Commando;
import techguns.util.BlockUtils;
import techguns.util.MBlock;
import techguns.util.MBlockOreClusterTypeOre;
import techguns.util.MBlockOreclusterType;
import techguns.util.MultiMBlock;
import techguns.util.MultiMMBlock;
import techguns.world.dungeon.presets.specialblocks.MBlockTGSpawner;

public class DesertOilCluster extends WorldgenStructure {	
	static ArrayList<MBlock> blockList = new ArrayList<MBlock>();
	static short[][] blocks;
	static EnumOreClusterType[] types = {EnumOreClusterType.OIL};
	static MBlock[] ores = null;
	static MBlock oilblock;
	static MBlock oilblockSand;
	
	static int[] oreWeights = {1};
	static int[] clusterWeights = {10};
	static int totalweight=0;
	static {
		Arrays.stream(clusterWeights).forEach(i -> totalweight+=i);
		
		if(TGFluids.OIL_WORLDSPAWN!=null) {
			MBlock oil = new MBlock(TGFluids.OIL_WORLDSPAWN.getBlock().getDefaultState());
			ores = new MBlock[] {oil};
			oilblock = oil;
			oilblockSand = new MultiMMBlock(new MBlock[] {oil, new MBlock(Blocks.SAND,0)}, new int[] {1,1});
		} else {
			ores = new MBlock[] {new MBlock(Blocks.SANDSTONE,0)};
			oilblock = new MBlock(Blocks.LAVA,0);
			oilblockSand = new MBlock(Blocks.MAGMA,0);
		}
		
		blockList.add(new MBlock(Blocks.SANDSTONE,0));
		blockList.add(new MBlock(TGBlocks.SANDBAGS,0));
		blockList.add(new MBlock(Blocks.AIR,0));
		blockList.add(new MBlockTGSpawner(EnumMonsterSpawnerType.SOLDIER_SPAWN,3,1,200,1).addMobType(ArmySoldier.class, 4).addMobType(Commando.class, 1));
		blockList.add(oilblockSand);
		blockList.add(new MBlockOreclusterType(types, clusterWeights, 0.5f, ores));
		blockList.add(oilblock);
		blockList.add(new MBlockOreclusterType(types, clusterWeights, 0, null));
		blockList.add(oilblock);
		
		blocks = BlockUtils.loadStructureFromFile("desert_oil_cluster");
	}
	
	public DesertOilCluster() {
		super(11, 10, 11, 11, 10, 11);
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
		
		BlockUtils.cleanUpwards(world, blocks, blockList, posX, posY, posZ, centerX, centerZ, direction, 0, 6);
		BlockUtils.placeScannedStructure(world, blocks, blockList, posX, posY-4, posZ, centerX, centerZ, direction, 0,this.lootTier,colorType,indexRoll,rnd);
		BlockUtils.placeScannedStructure(world, blocks, blockList, posX, posY-4, posZ, centerX, centerZ, direction, 1,this.lootTier,colorType);
	}

}
