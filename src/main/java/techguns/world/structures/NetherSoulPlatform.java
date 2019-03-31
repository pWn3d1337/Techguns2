package techguns.world.structures;

import java.util.ArrayList;
import java.util.Random;

import net.minecraft.init.Blocks;
import net.minecraft.world.World;
import techguns.TGBlocks;
import techguns.blocks.EnumMonsterSpawnerType;
import techguns.entities.npcs.CyberDemon;
import techguns.entities.npcs.Ghastling;
import techguns.util.BlockUtils;
import techguns.util.MBlock;
import techguns.world.dungeon.presets.specialblocks.MBlockTGSpawner;

public class NetherSoulPlatform extends WorldgenStructure {
	
	static ArrayList<MBlock> blockList = new ArrayList<MBlock>();
	static short[][] blocks;
	
	static {
		blockList.add(new MBlock(TGBlocks.NETHER_METAL, 3));
		blockList.add(new MBlock(Blocks.AIR, 0));
		blockList.add(new MBlock(TGBlocks.NETHER_METAL, 4));
		blockList.add(new MBlock(Blocks.SKULL, 1));
		blockList.add(new MBlock(TGBlocks.NETHER_METAL, 5));
		blockList.add(new MBlock(Blocks.SOUL_SAND, 0));
		blockList.add(new MBlock(TGBlocks.NETHER_METAL, 7));
		blockList.add(new MBlock(Blocks.GLOWSTONE, 0));
		blockList.add(new MBlock(Blocks.NETHER_BRICK_FENCE, 0));
		blockList.add(new MBlockTGSpawner(EnumMonsterSpawnerType.HOLE,3,2,200,1).addMobType(Ghastling.class, 1));
		
		blocks = BlockUtils.loadStructureFromFile("nether_soul_platform");
	}

	public NetherSoulPlatform() {
		super(13,10,13,13,10,13);
	}

	@Override
	public void setBlocks(World world, int posX, int posY, int posZ, int sizeX, int sizeY, int sizeZ, int direction,
			BiomeColorType colorType, Random rnd) {
		
		int centerX, centerZ;
		int hoffset = -2;
		
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
		BlockUtils.placeFoundationNether(world, blocks, blockList, posX, posY+hoffset, posZ, centerX, centerZ, direction, 0,16);
		BlockUtils.placeScannedStructure(world, blocks, blockList, posX, posY+hoffset, posZ, centerX, centerZ, direction, 0,this.lootTier,colorType);
		BlockUtils.placeScannedStructure(world, blocks, blockList, posX, posY+hoffset, posZ, centerX, centerZ, direction, 1,this.lootTier,colorType);
		
	}

	
	
}
