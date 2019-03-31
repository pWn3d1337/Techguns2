package techguns.world.structures;

import java.util.ArrayList;
import java.util.Random;

import net.minecraft.init.Blocks;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import techguns.TGBlocks;
import techguns.Techguns;
import techguns.blocks.EnumMonsterSpawnerType;
import techguns.entities.npcs.Ghastling;
import techguns.entities.npcs.ZombiePigmanSoldier;
import techguns.util.BlockUtils;
import techguns.util.MBlock;
import techguns.world.dungeon.presets.specialblocks.MBlockChestLoottable;
import techguns.world.dungeon.presets.specialblocks.MBlockTGSpawner;

public class NetherLoot01 extends WorldgenStructure {

	private static final ResourceLocation CHEST_LOOT = new ResourceLocation(Techguns.MODID,"chests/factory_building");
		
	static ArrayList<MBlock> blockList = new ArrayList<MBlock>();
	static short[][] blocks;
	
	static {		
		blockList.add(MBlockRegister.NETHERRACK_ROCKY);
		blockList.add(new MBlock(TGBlocks.NETHER_METAL, 3));
		blockList.add(MBlockRegister.AIR);
		blockList.add(new MBlock(Blocks.NETHER_BRICK_FENCE, 0));
		blockList.add(new MBlock(Blocks.NETHERRACK, 0));
		blockList.add(new MBlock(Blocks.SKULL, 1));
		blockList.add(new MBlockChestLoottable(Blocks.CHEST, 5, CHEST_LOOT));
		blockList.add(new MBlockTGSpawner(EnumMonsterSpawnerType.HOLE,2,1,200,1).addMobType(ZombiePigmanSoldier.class, 1));
		
		blocks = BlockUtils.loadStructureFromFile("nether_loot_01");
	}

	
	public NetherLoot01() {
		super(6,10,6,6,10,6);
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
		BlockUtils.placeFoundationNether(world, blocks, blockList, posX, posY+hoffset, posZ, centerX, centerZ, direction, 0,16);
		BlockUtils.placeScannedStructure(world, blocks, blockList, posX, posY+hoffset, posZ, centerX, centerZ, direction, 0,this.lootTier,colorType);
		BlockUtils.placeScannedStructure(world, blocks, blockList, posX, posY+hoffset, posZ, centerX, centerZ, direction, 1,this.lootTier,colorType);
	}

}
