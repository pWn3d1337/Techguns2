package techguns.world.structures;

import java.util.ArrayList;
import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.world.World;
import techguns.TGBlocks;
import techguns.blocks.EnumMonsterSpawnerType;
import techguns.entities.npcs.CyberDemon;
import techguns.util.BlockUtils;
import techguns.util.MBlock;
import techguns.util.MultiMBlock;
import techguns.world.dungeon.presets.specialblocks.MBlockTGSpawner;
import techguns.world.structures.WorldgenStructure.BiomeColorType;

public class NetherOreClusterSmall extends WorldgenStructure {

	static ArrayList<MBlock> blockList = new ArrayList<MBlock>();
	static short[][] blocks;
	static {
		blockList.add(new MBlock(Blocks.MAGMA, 0));
		blockList.add(MBlockRegister.AIR);
		blockList.add(new MultiMBlock(new Block[] {TGBlocks.ORE_CLUSTER, Blocks.NETHERRACK}, new int[] {7,0}, new int[] {50,50}));
		blockList.add(new MBlock(TGBlocks.ORE_CLUSTER, 7));
		blockList.add(new MultiMBlock(new Block[] {TGBlocks.ORE_CLUSTER, Blocks.AIR}, new int[] {7,0}, new int[] {50,50}));
		
		blocks = BlockUtils.loadStructureFromFile("nether_orecluster_small");
	}
	
	public NetherOreClusterSmall() {
		super(3,3,3,3,3,3);
	}

	@Override
	public void setBlocks(World world, int posX, int posY, int posZ, int sizeX, int sizeY, int sizeZ, int direction,
			BiomeColorType colorType, Random rnd) {
		
		int centerX, centerZ;
		int hoffset = 1;
		
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
		BlockUtils.placeFoundationNether(world, blocks, blockList, posX, posY+hoffset, posZ, centerX, centerZ, direction, 0,2);
		BlockUtils.placeScannedStructure(world, blocks, blockList, posX, posY+hoffset, posZ, centerX, centerZ, direction, 0,this.lootTier,colorType);
		BlockUtils.placeScannedStructure(world, blocks, blockList, posX, posY+hoffset, posZ, centerX, centerZ, direction, 1,this.lootTier,colorType);
	}

}
