package techguns.world.structures;

import java.util.Random;

import net.minecraft.init.Blocks;
import net.minecraft.util.math.BlockPos.MutableBlockPos;
import net.minecraft.world.World;
import techguns.TGBlocks;
import techguns.util.BlockUtils;

public class WatchTowerSmall extends WorldgenStructure{
	
	int towerSize = 3;

	public WatchTowerSmall(int minX, int minY, int minZ, int maxX, int maxY,
			int maxZ, int towerSize) {
		super(minX, minY, minZ, maxX, maxY, maxZ);
		this.towerSize = towerSize;
	}

	@Override
	public void setBlocks(World world, int posX, int posY, int posZ, int sizeX,
			int sizeY, int sizeZ, int direction, BiomeColorType colorType, Random rnd) {
		
		//Center
		if (sizeX >= towerSize+2) {
			posX+= (sizeX-towerSize)/2;
		}
		if (sizeZ >= towerSize+2) {
			posZ+= (sizeZ-towerSize)/2;
		}
		
		MutableBlockPos p = new MutableBlockPos();
		
		//Posts
		for (int y = 1; y < sizeY-1; y++) {
			world.setBlockState(p.setPos(posX, posY+y, posZ), Blocks.OAK_FENCE.getDefaultState());
			world.setBlockState(p.setPos(posX+towerSize-1, posY+y, posZ), Blocks.OAK_FENCE.getDefaultState());
			world.setBlockState(p.setPos(posX, posY+y, posZ+towerSize-1), Blocks.OAK_FENCE.getDefaultState());
			world.setBlockState(p.setPos(posX+towerSize-1, posY+y, posZ+towerSize-1), Blocks.OAK_FENCE.getDefaultState());
			//center
			if (y < sizeY-3) {
				for (int x = 1; x < towerSize-1; x++ ) {
					for (int z = 1; z < towerSize-1; z++) {
						world.setBlockState(p.setPos(posX+x, posY+y, posZ+z), Blocks.PLANKS.getDefaultState());
					}
				}
			}
			
		}
		//top
		BlockUtils.fillBlocksHollow(world, p.setPos(posX, posY+sizeY-4, posZ), towerSize, 2, towerSize, Blocks.OAK_FENCE.getDefaultState());
		BlockUtils.fillBlocks(world, p.setPos(posX, posY+sizeY-1, posZ), towerSize, 1, towerSize, TGBlocks.CAMONET_TOP.getDefaultState());
		
		//Ladder
		
		int xoffset = 0; int zoffset = 0;
		int meta = 0;
		switch (direction) {
			case 0:
				xoffset = 0; zoffset=1;
				meta = 4;
				break;
			case 1:
				xoffset = 1; zoffset=0;
				meta = 2;
				break;
			case 2:
				xoffset = towerSize-1; zoffset=towerSize-2;
				meta = 5;
				break;
			case 3:
				xoffset = towerSize-2; zoffset=towerSize-1;
				meta = 3;
				break;
		}
		
		//BlockUtils.fillBlocks(world, posX+xoffset, posY+1, posZ+zoffset, 1, sizeY-3, 1, Blocks.ladder);
		
		//FIXME correct rotation
		for (int y = 1; y < sizeY-3; y++) {
			world.setBlockState(p.setPos(posX+xoffset, posY+y, posZ+zoffset), Blocks.LADDER.getStateFromMeta(meta), 3);
		}
		world.setBlockState(p.setPos(posX+xoffset, posY+sizeY-3, posZ+zoffset), Blocks.OAK_FENCE_GATE.getStateFromMeta((direction+1) % 4), 3);
		
		//TODO: Walkway
		//BlockUtils.fillBlocks(world, posX+xoffset, posY, posZ+zoffset, 1, 1, 1, Blocks.gravel);
	}
}