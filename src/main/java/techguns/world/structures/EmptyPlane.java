package techguns.world.structures;

import java.util.Random;

import net.minecraft.block.state.IBlockState;
import net.minecraft.util.math.BlockPos.MutableBlockPos;
import net.minecraft.world.World;

public class EmptyPlane extends WorldgenStructure {

	IBlockState block;
	
	public EmptyPlane(IBlockState block) {
		super (1, 1, 1, -1, -1, -1);
		this.block = block;
	}
	
	public EmptyPlane(int minX, int minY, int minZ, int maxX, int maxY, int maxZ, IBlockState block) {
		super(minX, minY, minZ, maxX, maxY, maxZ);
		this.block = block;
	}

	@Override
	public void setBlocks(World world, int posX, int posY, int posZ, int sizeX,
			int sizeY, int sizeZ, int direction, BiomeColorType colorType, Random rnd) {
		MutableBlockPos p = new MutableBlockPos();
		for (int i = 0; i < sizeX; i++) {			
			for (int j = 0; j < sizeZ; j++) {		
				world.setBlockState(p.setPos(posX+i, posY, posZ+j), block);
			}
		}
	}
}
