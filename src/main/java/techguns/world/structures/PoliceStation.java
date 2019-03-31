package techguns.world.structures;

import java.util.ArrayList;
import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import techguns.TGBlocks;
import techguns.Techguns;
import techguns.blocks.EnumMonsterSpawnerType;
import techguns.entities.npcs.ZombieMiner;
import techguns.entities.npcs.ZombiePoliceman;
import techguns.entities.npcs.ZombieSoldier;
import techguns.util.BlockUtils;
import techguns.util.MBlock;
import techguns.util.MultiMBlock;
import techguns.world.dungeon.presets.specialblocks.MBlockChestLoottable;
import techguns.world.dungeon.presets.specialblocks.MBlockTGSpawner;

public class PoliceStation extends WorldgenStructure {

	private static final ResourceLocation CHEST_LOOT = new ResourceLocation(Techguns.MODID,"chests/policestation");
	
	static ArrayList<MBlock> blockList = new ArrayList<MBlock>();
	static short[][] blocks;
	static {
		blockList.add(new MBlock(TGBlocks.CONCRETE, 0));
		blockList.add(MBlockRegister.IRON_BLOCK_SMALL_INGOTS);
		blockList.add(MBlockRegister.IRON_PANE_MODERN_FENCE);
		blockList.add(MBlockRegister.AIR);
		blockList.add(new MBlock(TGBlocks.SANDBAGS, 0));
		blockList.add(new MBlock(TGBlocks.LAMP_0, 11));
		blockList.add(new MBlock(Blocks.STONEBRICK, 0));
		blockList.add(MBlockRegister.BLUE_CONCRETE_SMALL_BRICKS);
		blockList.add(new MBlock(TGBlocks.BUNKER_DOOR, 2));
		blockList.add(new MBlock(TGBlocks.BUNKER_DOOR, 9));
		blockList.add(new MBlock(Blocks.GLASS_PANE, 0));
		blockList.add(MBlockRegister.OAK_PLANKS_1);
		blockList.add(new MBlockChestLoottable(Blocks.CHEST, 2, CHEST_LOOT));
		blockList.add(MBlockRegister.COBBLESTONE_7);
		blockList.add(new MBlockTGSpawner(EnumMonsterSpawnerType.SOLDIER_SPAWN,3,1,200,1).addMobType(ZombiePoliceman.class, 1));
		blockList.add(new MBlock(TGBlocks.LAMP_0, 7));
		blockList.add(new MBlock(Blocks.RAIL, 6));
		blockList.add(new MBlock(Blocks.RAIL, 9));
		blockList.add(new MBlock(TGBlocks.LADDER_0, 4));
		blockList.add(new MBlock(Blocks.RAIL, 7));
		blockList.add(new MBlock(Blocks.RAIL, 8));
		blockList.add(new MBlock(Blocks.STONE_SLAB, 8));
		blockList.add(new MBlockChestLoottable(Blocks.CHEST, 3, CHEST_LOOT));
		blockList.add(new MBlock(Blocks.CRAFTING_TABLE, 0));
		blockList.add(new MBlock(Blocks.OAK_FENCE_GATE, 0));
		blockList.add(new MBlock(TGBlocks.BUNKER_DOOR, 1));
		blockList.add(new MBlock(TGBlocks.BUNKER_DOOR, 8));
		
		blocks = BlockUtils.loadStructureFromFile("policestation");
	}
	
	public PoliceStation() {
		super(13,8,13,13,8,13);
		this.setXYZSize(13, 8, 13);
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
		BlockUtils.cleanUpwards(world, blocks, blockList, posX, posY, posZ, centerX, centerZ, direction, 0, 7);
		BlockUtils.placeFoundation(world, blocks, blockList, posX, posY, posZ, centerX, centerZ, direction, 0,3);
		BlockUtils.placeScannedStructure(world, blocks, blockList, posX, posY, posZ, centerX, centerZ, direction, 0,this.lootTier,colorType);
		BlockUtils.placeScannedStructure(world, blocks, blockList, posX, posY, posZ, centerX, centerZ, direction, 1,this.lootTier,colorType);

	}

}
