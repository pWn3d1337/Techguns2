package techguns.world.structures;

import java.util.ArrayList;
import java.util.Random;

import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import techguns.TGBlocks;
import techguns.Techguns;
import techguns.blocks.EnumMonsterSpawnerType;
import techguns.entities.npcs.ZombieFarmer;
import techguns.entities.npcs.ZombieMiner;
import techguns.entities.npcs.ZombiePoliceman;
import techguns.entities.npcs.ZombieSoldier;
import techguns.util.BlockUtils;
import techguns.util.MBlock;
import techguns.world.dungeon.presets.specialblocks.MBlockChestLoottable;
import techguns.world.dungeon.presets.specialblocks.MBlockItemFrame;
import techguns.world.dungeon.presets.specialblocks.MBlockTGSpawner;
import techguns.world.structures.WorldgenStructure.BiomeColorType;

public class GasStation extends WorldgenStructure {

private static final ResourceLocation CHEST_LOOT = new ResourceLocation(Techguns.MODID,"chests/gasstation");
	
	static ArrayList<MBlock> blockList = new ArrayList<MBlock>();
	static short[][] blocks;
	static {
		blockList.add(new MBlock("techguns:concrete", 3));
		blockList.add(MBlockRegister.AIR);
		blockList.add(MBlockRegister.AIR); //new MBlockItemFrame(new ItemStack(Blocks.GLASS_PANE), EnumFacing.WEST));
		blockList.add(new MBlock("minecraft:quartz_block", 0));
		blockList.add(new MBlock("minecraft:lever", 3));
		blockList.add(MBlockRegister.GAS_STATION_CONSOLE); //new MBlock("minecraft:quartz_stairs", 1));
		blockList.add(new MBlock("techguns:lamp0", 11));
		blockList.add(new MBlock("minecraft:stonebrick", 0));
		blockList.add(MBlockRegister.COBBLESTONE_7);
		blockList.add(new MBlock("minecraft:brick_block", 0));
		blockList.add(new MBlock("minecraft:wooden_door", 2));
		blockList.add(new MBlock("minecraft:stone", 6));
		blockList.add(new MBlock("minecraft:glass_pane", 0));
		blockList.add(new MBlock("minecraft:wooden_door", 8));
		blockList.add(new MBlock("minecraft:stone_slab", 0));
		blockList.add(new MBlock("minecraft:wooden_slab", 8));
		blockList.add(new MBlock("techguns:lamp0", 7));
		blockList.add(new MBlock("minecraft:double_stone_slab", 0));
		blockList.add(new MBlock("minecraft:stone_slab", 8));
		blockList.add(new MBlockTGSpawner(EnumMonsterSpawnerType.HOLE,3,2,200,1).addMobType(ZombieSoldier.class, 1).addMobType(ZombieFarmer.class, 1).addMobType(ZombieMiner.class, 1));
		blockList.add(new MBlock("minecraft:trapdoor", 10));
		blockList.add(new MBlock("minecraft:trapdoor", 14));
		blockList.add(new MBlock("minecraft:planks", 0));
		blockList.add(new MBlockChestLoottable(Blocks.CHEST, 4, CHEST_LOOT));
		
		blocks = BlockUtils.loadStructureFromFile("gasstation");
	}
	
	public GasStation() {
		super(9,7,12,9,7,12);
		this.setXYZSize(9, 7, 12);
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
