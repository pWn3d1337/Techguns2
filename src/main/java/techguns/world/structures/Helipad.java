package techguns.world.structures;

import java.util.Random;

import net.minecraft.util.math.BlockPos.MutableBlockPos;
import net.minecraft.world.World;

public class Helipad extends WorldgenStructure{


	public Helipad(int minX, int minY, int minZ, int maxX, int maxY, int maxZ) {
		super(minX, minY, minZ, maxX, maxY, maxZ);
	}

	@Override
	public void setBlocks(World world, int posX, int posY, int posZ, int sizeX,
			int sizeY, int sizeZ, int direction, BiomeColorType colorType, Random rnd) {
		if (sizeX < 7 || sizeZ < 7) {
			return;
		}
		if (sizeX > 9) {
			int offset = (sizeX-9)/2;
			posX+=offset;
			sizeX=9;
		}
		if (sizeZ > 9) {
			int offset = (sizeZ-9)/2;
			posZ+=offset;
			sizeZ=9;
		}
		
		MutableBlockPos p = new MutableBlockPos();
		
		//Outer wireframe floor
		if (sizeX==9 && sizeZ == 9) {
			for (int z = 1; z<sizeZ-1; z++) {
				world.setBlockState(p.setPos(posX, posY, posZ+z), MBlockRegister.HELIPAD_WIREFRAME.getState(), 2);
				world.setBlockState(p.setPos(posX+sizeX-1, posY, posZ+z), MBlockRegister.HELIPAD_WIREFRAME.getState(), 2);
			}
			posX++;
			sizeX=7;		
			for (int x = 0; x<sizeX; x++) {
				world.setBlockState(p.setPos(posX+x, posY, posZ), MBlockRegister.HELIPAD_WIREFRAME.getState(), 2);
				world.setBlockState(p.setPos(posX+x, posY, posZ+sizeZ-1), MBlockRegister.HELIPAD_WIREFRAME.getState(), 2);
			}
			posZ++;
			sizeZ=7;
		}else {
			sizeX=7;
			sizeZ=7;
		}
		
		//Pad
		for (int x = 0; x < sizeX; x++) {
			for (int z = 0; z < sizeZ; z++) {
				if (x==0 || z==0 || x==sizeX-1 || z==sizeZ-1) {
					world.setBlockState(p.setPos(posX+x, posY+1, posZ+z), MBlockRegister.HELIPAD_HAZARDBLOCK.getState(), 2);
				}else {
					world.setBlockState(p.setPos(posX+x, posY+1, posZ+z), MBlockRegister.HELIPAD_CONCRETE.getState(), 2);
				}
			}
		}
		
		//H
		world.setBlockState(p.setPos(posX+2, posY+1, posZ+2), MBlockRegister.HELIPAD_GLOWBLOCK.getState(), 2);
		world.setBlockState(p.setPos(posX+3, posY+1, posZ+2), MBlockRegister.HELIPAD_GLOWBLOCK.getState(), 2);
		world.setBlockState(p.setPos(posX+4, posY+1, posZ+2), MBlockRegister.HELIPAD_GLOWBLOCK.getState(), 2);
		world.setBlockState(p.setPos(posX+3, posY+1, posZ+3), MBlockRegister.HELIPAD_GLOWBLOCK.getState(), 2);
		world.setBlockState(p.setPos(posX+2, posY+1, posZ+4), MBlockRegister.HELIPAD_GLOWBLOCK.getState(), 2);
		world.setBlockState(p.setPos(posX+3, posY+1, posZ+4), MBlockRegister.HELIPAD_GLOWBLOCK.getState(), 2);
		world.setBlockState(p.setPos(posX+4, posY+1, posZ+4), MBlockRegister.HELIPAD_GLOWBLOCK.getState(), 2);
	}

}
