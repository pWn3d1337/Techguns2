package techguns.world.structures;

import java.util.Random;

import net.minecraft.init.Blocks;
import net.minecraft.item.EnumDyeColor;
import net.minecraft.util.math.BlockPos.MutableBlockPos;
import net.minecraft.world.World;
import techguns.TGBlocks;
import techguns.util.MBlock;

public class Helipad extends WorldgenStructure{

	protected static MBlock glowblock = new MBlock(TGBlocks.NEONLIGHT_BLOCK,4);
	protected static MBlock wireframe = new MBlock(TGBlocks.METAL_PANEL, 6);
	protected static MBlock concrete_dark = new MBlock(TGBlocks.CONCRETE, 3);
	protected static MBlock hazardblock = new MBlock(Blocks.CONCRETE, EnumDyeColor.YELLOW.ordinal());
	
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
				world.setBlockState(p.setPos(posX, posY, posZ+z), wireframe.getState(), 2);
				world.setBlockState(p.setPos(posX+sizeX-1, posY, posZ+z), wireframe.getState(), 2);
			}
			posX++;
			sizeX=7;		
			for (int x = 0; x<sizeX; x++) {
				world.setBlockState(p.setPos(posX+x, posY, posZ), wireframe.getState(), 2);
				world.setBlockState(p.setPos(posX+x, posY, posZ+sizeZ-1), wireframe.getState(), 2);
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
					world.setBlockState(p.setPos(posX+x, posY+1, posZ+z), hazardblock.getState(), 2);
				}else {
					world.setBlockState(p.setPos(posX+x, posY+1, posZ+z), concrete_dark.getState(), 2);
				}
			}
		}
		
		//H
		world.setBlockState(p.setPos(posX+2, posY+1, posZ+2), glowblock.getState(), 2);
		world.setBlockState(p.setPos(posX+3, posY+1, posZ+2), glowblock.getState(), 2);
		world.setBlockState(p.setPos(posX+4, posY+1, posZ+2), glowblock.getState(), 2);
		world.setBlockState(p.setPos(posX+3, posY+1, posZ+3), glowblock.getState(), 2);
		world.setBlockState(p.setPos(posX+2, posY+1, posZ+4), glowblock.getState(), 2);
		world.setBlockState(p.setPos(posX+3, posY+1, posZ+4), glowblock.getState(), 2);
		world.setBlockState(p.setPos(posX+4, posY+1, posZ+4), glowblock.getState(), 2);
	}

}
