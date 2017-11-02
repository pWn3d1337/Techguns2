package techguns.world.structures;

import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.util.math.BlockPos.MutableBlockPos;
import net.minecraft.world.World;
import techguns.util.BlockUtils;
import techguns.util.MBlock;

public class Containers extends WorldgenStructure{

	int containerWidth = 2;
	int containerHeight = 2;
	int containerMinLength = 2;
	IBlockState containerBlock=null;
	
	public Containers(int minX, int minY, int minZ, int maxX, int maxY, int maxZ, int containerWidth, int containerHeight, int containerMinLength, MBlock containerBlock) {
		super(minX, minY, minZ, maxX, maxY, maxZ);
		this.containerWidth = containerWidth;
		this.containerHeight = containerHeight;
		this.containerMinLength = containerMinLength;
		this.containerBlock = containerBlock.getState();
	}
	
	public Containers(int minX, int minY, int minZ, int maxX, int maxY, int maxZ, MBlock containerBlock) {
		super(minX, minY, minZ, maxX, maxY, maxZ);
		this.containerBlock = containerBlock.getState();
	}

	@Override
	public void setBlocks(World world, int posX, int posY, int posZ, int sizeX,
			int sizeY, int sizeZ, int direction, BiomeColorType colorType, Random rnd) {
		int count; //Number of containers
		int offset = 0;
		
		MutableBlockPos pos = new MutableBlockPos();
		if (sizeX > sizeZ) {
			count = (int)(((float)sizeZ+1.0f)/((float)containerWidth+1.0f));		
			for (int i = 0;  i < count; i++) {
				BlockUtils.fillBlocks(world, pos.setPos(posX, posY+1, posZ+offset), sizeX, containerHeight, containerWidth, containerBlock);
				offset+=(containerWidth+1);
			}
			
		}else {
			count = (int)(((float)sizeX+1.0f)/((float)containerWidth+1.0f));			
			for (int i = 0;  i < count; i++) {
				BlockUtils.fillBlocks(world, pos.setPos(posX+offset, posY+1, posZ), containerWidth, containerHeight, sizeZ, containerBlock);
				offset+=(containerWidth+1);
			}
		}
		
		
	}

}