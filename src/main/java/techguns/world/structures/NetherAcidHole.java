package techguns.world.structures;

import java.util.ArrayList;
import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.world.World;
import techguns.TGBlocks;
import techguns.TGFluids;
import techguns.blocks.EnumMonsterSpawnerType;
import techguns.entities.npcs.CyberDemon;
import techguns.util.BlockUtils;
import techguns.util.MBlock;
import techguns.util.MultiMBlock;
import techguns.world.dungeon.presets.specialblocks.MBlockTGSpawner;

public class NetherAcidHole extends WorldgenStructure {

	static ArrayList<MBlock> blockList = new ArrayList<MBlock>();
	static short[][] blocks;
	static {
		blockList.add(MBlockRegister.NETHERRACK_ROCKY);
		blockList.add(MBlockRegister.AIR);
		blockList.add(new MBlock(Blocks.SOUL_SAND, 0));
		blockList.add(new MultiMBlock(new Block[]{TGFluids.BLOCK_FLUID_ACID,Blocks.NETHERRACK},new int[]{0,0}, new int[]{1,1}));
		blockList.add(new MBlock(TGFluids.BLOCK_FLUID_ACID, 0));
		
		blocks = BlockUtils.loadStructureFromFile("nether_acid_hole");
	}
	
	
	public NetherAcidHole() {
		super(9,6,9, 9,6,9);
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
