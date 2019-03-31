package techguns.world.structures;

import java.util.ArrayList;
import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import techguns.TGBlocks;
import techguns.TGuns;
import techguns.blocks.EnumMonsterSpawnerType;
import techguns.entities.npcs.CyberDemon;
import techguns.entities.npcs.ZombiePigmanSoldier;
import techguns.util.BlockUtils;
import techguns.util.MBlock;
import techguns.util.MultiMBlock;
import techguns.world.dungeon.presets.specialblocks.MBlockTGSpawner;
import techguns.world.structures.WorldgenStructure.BiomeColorType;

public class NetherOreClusterCastle extends WorldgenStructure {

	static ArrayList<MBlock> blockList = new ArrayList<MBlock>();
	static short[][] blocks;
	static {
		blockList.add(new MBlock(TGBlocks.NETHER_METAL, 0));
		blockList.add(new MBlock(TGBlocks.NETHER_METAL, 7));
		blockList.add(MBlockRegister.NETHERBRICKS_C13);
		blockList.add(MBlockRegister.AIR);
		blockList.add(new MBlock(Blocks.NETHER_BRICK_FENCE, 0));
		blockList.add(new MBlock(TGBlocks.NETHER_METAL, 6));
		blockList.add(new MBlockTGSpawner(EnumMonsterSpawnerType.HOLE,2,1,200,0).addMobType(ZombiePigmanSoldier.class, 1).setWeaponOverride(new ItemStack(TGuns.boltaction)));
		blockList.add(new MultiMBlock(new Block[] {TGBlocks.ORE_CLUSTER, Blocks.AIR}, new int[] {7,0}, new int[] {40,60}));
		blockList.add(new MBlock(TGBlocks.ORE_CLUSTER, 7));
		
		blocks = BlockUtils.loadStructureFromFile("nether_orecluster_castle");
	}
	
	public NetherOreClusterCastle() {
		super(11,9,11,11,9,11);
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
