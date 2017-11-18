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
import techguns.world.dungeon.presets.specialblocks.MBlockTGSpawner;

public class FactoryHouseSmall extends WorldgenStructure{
	static ArrayList<MBlock> blockList = new ArrayList<MBlock>();
	static short[][] blocks;
	static {
		blockList.add(new MBlock(TGBlocks.CONCRETE, 2));
		blockList.add(MBlockRegister.TECHNICAL_BLOCK_SCAFFOLD);
		blockList.add(MBlockRegister.FACTORY_PLATE);
		blockList.add(new MBlock(Blocks.GLASS_PANE, 0));
		blockList.add(MBlockRegister.TECHNICAL_CONCRETE);
		blockList.add(MBlockRegister.FACTORY_CRATE);
		blockList.add(new MBlock(Blocks.CHEST, 5));
		blockList.add(new MBlock(Blocks.AIR, 0));
		blockList.add(new MBlock(TGBlocks.LAMP_0, EnumFacing.NORTH.ordinal()));
		blockList.add(new MBlock(TGBlocks.LAMP_0, EnumFacing.WEST.ordinal()));
		blockList.add(MBlockRegister.FACTORY_PLATE_DOTTED);
		blockList.add(new MBlock(Blocks.CRAFTING_TABLE, 0));
		blockList.add(new MBlock(TGBlocks.BUNKER_DOOR.getDefaultState().withProperty(TGBlocks.BUNKER_DOOR.FACING, EnumFacing.SOUTH).withProperty(TGBlocks.BUNKER_DOOR.HINGE, EnumHingePosition.LEFT).withProperty(TGBlocks.BUNKER_DOOR.HALF, EnumDoorHalf.LOWER)));//TGBlocks.BUNKER_DOOR, 7));
		blockList.add(new MBlock(TGBlocks.BUNKER_DOOR.getDefaultState().withProperty(TGBlocks.BUNKER_DOOR.FACING, EnumFacing.SOUTH).withProperty(TGBlocks.BUNKER_DOOR.HINGE, EnumHingePosition.LEFT).withProperty(TGBlocks.BUNKER_DOOR.HALF, EnumDoorHalf.UPPER)));//TGBlocks.BUNKER_DOOR, 8));
		blockList.add(new MBlock(TGBlocks.LAMP_0, EnumFacing.UP.ordinal()));
		blockList.add(new MBlock(TGBlocks.LAMP_0, EnumFacing.SOUTH.ordinal()));
		blockList.add(new MBlock(Blocks.FURNACE, 3));
		blockList.add(new MBlock(TGBlocks.LAMP_0, EnumFacing.EAST.ordinal()));
		blockList.add(new MBlock(TGBlocks.LADDER_0.getDefaultState().withProperty(TGBlocks.LADDER_0.FACING, EnumFacing.SOUTH)));
		blockList.add(new MBlock(Blocks.IRON_BARS,0));//ChiselBlocks.iron_bars, 6));
		blockList.add(new MBlock(TGBlocks.LAMP_0, EnumFacing.WEST.ordinal()));
		blockList.add(new MBlockTGSpawner(EnumMonsterSpawnerType.HOLE,5,2,150,2).addMobType(ZombieMiner.class, 1));
		
		blocks = BlockUtils.loadStructureFromFile("factoryBuildingSmall");

	}

	public FactoryHouseSmall(int minX, int minY, int minZ, int maxX, int maxY,
			int maxZ) {
		super(minX, minY, minZ, maxX, maxY, maxZ);
		this.setSwapXZ(true);
	}

	@Override
	public void setBlocks(World world, int posX, int posY, int posZ, int sizeX,
			int sizeY, int sizeZ, int direction, BiomeColorType colorType, Random rnd) {
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
		BlockUtils.placeScannedStructure(world, blocks, blockList, posX, posY, posZ, centerX, centerZ, direction, 0,this.lootTier);
		BlockUtils.placeScannedStructure(world, blocks, blockList, posX, posY, posZ, centerX, centerZ, direction, 1,this.lootTier);
	}
}