package techguns.world.structures;

import java.util.ArrayList;
import java.util.Random;

import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import techguns.TGBlocks;
import techguns.TGuns;
import techguns.Techguns;
import techguns.blocks.EnumMonsterSpawnerType;
import techguns.entities.npcs.Bandit;
import techguns.entities.npcs.ZombieFarmer;
import techguns.entities.npcs.ZombieMiner;
import techguns.entities.npcs.ZombieSoldier;
import techguns.util.BlockUtils;
import techguns.util.MBlock;
import techguns.util.MBlockCamoNetTop;
import techguns.util.MultiMMBlockIndexRoll;
import techguns.world.dungeon.presets.specialblocks.MBlockChestLoottable;
import techguns.world.dungeon.presets.specialblocks.MBlockTGSpawner;

public class SurvivorHideout extends WorldgenStructure {

private static final ResourceLocation CHEST_LOOT = new ResourceLocation(Techguns.MODID,"chests/survivor_hideout");
	
	static ArrayList<MBlock> blockList = new ArrayList<MBlock>();
	static short[][] blocks;
	static {
		blockList.add(new MBlock("minecraft:dirt", 1));
		blockList.add(new MBlock("minecraft:fence", 0));
		blockList.add(MBlockRegister.AIR);
		blockList.add(MBlockRegister.SURIVIVOR_HIDEOUT_IRON_BOTTOM);
		blockList.add(new MBlock("techguns:sandbags", 0));
		blockList.add(MBlockRegister.SURIVIVOR_HIDEOUT_IRON_TOP);
		blockList.add(new MBlock(TGBlocks.LAMP_0, 12));
		blockList.add(new MBlockChestLoottable(Blocks.CHEST, 2, CHEST_LOOT));
		blockList.add(new MBlock("minecraft:stonebrick", 0));
		blockList.add(new MBlockTGSpawner(EnumMonsterSpawnerType.HOLE,3,2,200,1).addMobType(Bandit.class, 1));
		blockList.add(new MBlock("minecraft:planks", 0));
		blockList.add(new MBlockChestLoottable(Blocks.CHEST, 4, CHEST_LOOT));
		blockList.add(new MBlock("minecraft:oak_stairs", 0));
		blockList.add(new MultiMMBlockIndexRoll(new MBlock[] {new MBlock(TGBlocks.METAL_PANEL, 0), new MBlock(TGBlocks.METAL_PANEL, 1), new MBlock(TGBlocks.METAL_PANEL, 2), new MBlock(TGBlocks.METAL_PANEL, 3)}, new int[] {1,1,1,1}));//new MBlock("techguns:metalpanel", 0));
		blockList.add(new MBlock("minecraft:planks", 1));
		blockList.add(new MBlock("minecraft:stone_slab", 3));
		blockList.add(MBlockRegister.CAMO_NET_TOP);
		blockList.add(new MBlock("minecraft:bed", 10));
		blockList.add(new MBlock("minecraft:bed", 2));
		blockList.add(new MBlock("minecraft:torch", 1));
		blockList.add(new MBlock("minecraft:crafting_table", 0));
		blockList.add(new MBlockTGSpawner(EnumMonsterSpawnerType.HOLE,2,1,200,1).addMobType(Bandit.class, 1).setWeaponOverride(new ItemStack(TGuns.boltaction)));
		blockList.add(new MBlock("minecraft:furnace", 3));
		blockList.add(new MBlock("techguns:ladder0", 0));
		blockList.add(new MBlock("techguns:simplemachine", 9));
		blockList.add(new MBlockChestLoottable(Blocks.CHEST, 3, CHEST_LOOT));
		
		blocks = BlockUtils.loadStructureFromFile("survivor_hideout");
	}
	
	public SurvivorHideout() {
		super(11,11,19,11,11,19);
		setXYZSize(11, 11, 19);
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
		
		int indexroll = rnd.nextInt(4);
		
		BlockUtils.cleanUpwards(world, blocks, blockList, posX, posY, posZ, centerX, centerZ, direction, 0, 7);
		BlockUtils.placeFoundation(world, blocks, blockList, posX, posY, posZ, centerX, centerZ, direction, 0,3);
		BlockUtils.placeScannedStructure(world, blocks, blockList, posX, posY, posZ, centerX, centerZ, direction, 0,this.lootTier,colorType, indexroll, rnd);
		BlockUtils.placeScannedStructure(world, blocks, blockList, posX, posY, posZ, centerX, centerZ, direction, 1,this.lootTier,colorType);
	}

}
