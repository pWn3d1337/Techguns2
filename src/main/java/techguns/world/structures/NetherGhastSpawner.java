package techguns.world.structures;

import java.util.ArrayList;
import java.util.Random;

import net.minecraft.init.Blocks;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import techguns.TGBlocks;
import techguns.Techguns;
import techguns.blocks.EnumMonsterSpawnerType;
import techguns.entities.npcs.CyberDemon;
import techguns.entities.npcs.Ghastling;
import techguns.util.BlockUtils;
import techguns.util.MBlock;
import techguns.util.MBlockVanillaSpawner;
import techguns.world.dungeon.presets.specialblocks.MBlockChestLoottable;
import techguns.world.dungeon.presets.specialblocks.MBlockTGSpawner;

public class NetherGhastSpawner extends WorldgenStructure {

	private static final ResourceLocation CHEST_LOOT = new ResourceLocation(Techguns.MODID,"chests/factory_building");
	
	static ArrayList<MBlock> blockList = new ArrayList<MBlock>();
	static short[][] blocks;
	static {		
		blockList.add(MBlockRegister.NETHERRACK_C2);
		blockList.add(MBlockRegister.AIR);
		blockList.add(MBlockRegister.NETHERRACK_C1);
		blockList.add(MBlockRegister.NETHERRACK_ROCKY);
		blockList.add(new MBlock(Blocks.GLOWSTONE, 0));
		blockList.add(new MBlock(TGBlocks.NETHER_METAL, 3));
		blockList.add(new MBlock(Blocks.IRON_BARS, 0));
		blockList.add(new MBlock(Blocks.SKULL, 1));
		blockList.add(new MBlock(Blocks.SOUL_SAND, 0));
		blockList.add(new MBlockVanillaSpawner(Ghastling.class));
		blockList.add(MBlockRegister.NETHERRACK_C12);
		blockList.add(MBlockRegister.NETHERRACK_C8);
		blockList.add(new MBlockChestLoottable(Blocks.CHEST, 3, CHEST_LOOT));
		
		blocks = BlockUtils.loadStructureFromFile("nether_ghast_spawner");
	}
	
	public NetherGhastSpawner() {
		super(10,14,10, 10,14,10);
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
