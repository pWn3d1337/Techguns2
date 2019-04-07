package techguns.world.structures;

import java.util.ArrayList;
import java.util.Random;

import net.minecraft.block.BlockDoor.EnumDoorHalf;
import net.minecraft.block.BlockDoor.EnumHingePosition;
import net.minecraft.init.Blocks;
import net.minecraft.util.EnumFacing;
import net.minecraft.world.World;
import techguns.TGBlocks;
import techguns.blocks.EnumMonsterSpawnerType;
import techguns.entities.npcs.ZombieMiner;
import techguns.util.BlockUtils;
import techguns.util.MBlock;
import techguns.world.dungeon.presets.specialblocks.MBlockChestLoottable;
import techguns.world.dungeon.presets.specialblocks.MBlockTGSpawner;

public class NetherDungeonEntrance extends WorldgenStructure {

	static ArrayList<MBlock> blockList = new ArrayList<MBlock>();
	static short[][] blocks;
	static {
		blockList.add(new MBlock(TGBlocks.NETHER_METAL, 0));
		blockList.add(new MBlock(TGBlocks.NETHER_METAL, 8));
		blockList.add(new MBlock(TGBlocks.NETHER_METAL, 9));
		blockList.add(new MBlock(TGBlocks.NETHER_METAL, 7));
		blockList.add(new MBlock(TGBlocks.DOOR3x3, 4));
		blockList.add(new MBlock(TGBlocks.DOOR3x3, 12));
		blockList.add(new MBlock(Blocks.NETHER_BRICK_STAIRS, 1));
		
		blocks = BlockUtils.loadStructureFromFile("netherdungeon_entrance");
	}
	
	public NetherDungeonEntrance() {
		super(10,6,7,10,6,7);
		this.setXYZSize(10, 6, 7);
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
		
		BlockUtils.placeScannedStructure(world, blocks, blockList, posX, posY, posZ, centerX, centerZ, direction, 0,this.lootTier,colorType);
		//BlockUtils.placeScannedStructure(world, blocks, blockList, posX, posY, posZ, centerX, centerZ, direction, 1,this.lootTier,colorType);
	}
}
