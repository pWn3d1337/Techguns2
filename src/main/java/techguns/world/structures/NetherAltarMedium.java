package techguns.world.structures;

import java.util.ArrayList;
import java.util.Random;

import net.minecraft.init.Blocks;
import net.minecraft.world.World;
import techguns.TGBlocks;
import techguns.blocks.EnumMonsterSpawnerType;
import techguns.entities.npcs.CyberDemon;
import techguns.util.BlockUtils;
import techguns.util.MBlock;
import techguns.world.dungeon.presets.specialblocks.MBlockTGSpawner;
import techguns.world.structures.WorldgenStructure.BiomeColorType;

public class NetherAltarMedium extends WorldgenStructure {

	static ArrayList<MBlock> blockList = new ArrayList<MBlock>();
	static short[][] blocks;
	static {
		blockList.add(new MBlock(TGBlocks.NETHER_METAL, 0));
		blockList.add(new MBlock(TGBlocks.NETHER_METAL, 6));
		blockList.add(new MBlock(Blocks.NETHER_BRICK_FENCE, 0));
		blockList.add(MBlockRegister.AIR);
		blockList.add(new MBlock(TGBlocks.NETHER_METAL, 2));
		blockList.add(new MBlock(TGBlocks.NETHER_METAL, 8));
		blockList.add(new MBlock(TGBlocks.NETHER_METAL, 1));
		blockList.add(new MBlock(TGBlocks.NETHER_METAL, 7));
		blockList.add(new MBlock(TGBlocks.NETHER_METAL, 9));
		blockList.add(new MBlock(Blocks.NETHER_BRICK_STAIRS, 0));
		blockList.add(new MBlockTGSpawner(EnumMonsterSpawnerType.HOLE,3,1,200,1).addMobType(CyberDemon.class, 1));
		blockList.add(new MBlock(Blocks.NETHER_BRICK_STAIRS, 2));
		blockList.add(new MBlock(Blocks.NETHER_BRICK_STAIRS, 3));
		blockList.add(new MBlock(Blocks.NETHER_BRICK, 0));
		blockList.add(new MBlock(Blocks.NETHER_BRICK_STAIRS, 1));
		
		blocks = BlockUtils.loadStructureFromFile("nether_altar_medium");
	}
	
	public NetherAltarMedium() {
		super(16,9,16, 16,9,16);
	}

	@Override
	public void setBlocks(World world, int posX, int posY, int posZ, int sizeX, int sizeY, int sizeZ, int direction,
			BiomeColorType colorType, Random rnd) {
		
		int centerX, centerZ;
		int hoffset = -1;
		
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
