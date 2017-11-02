package techguns.world.structures;

import java.util.Random;

import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.util.math.BlockPos.MutableBlockPos;
import net.minecraft.world.World;
import techguns.TGBlocks;
import techguns.util.MBlock;
import techguns.world.Structure;

public class Tent extends WorldgenStructure {
	
	int type;
	MBlock roofBlock = new MBlock(TGBlocks.CAMONET_TOP, 0);
	MBlock wallBlock = new MBlock(TGBlocks.CAMONET, 0);
	MBlock groundBlock = new MBlock(Blocks.GRAVEL, 0);
	
	IBlockState roofState = roofBlock.getState();
	IBlockState wallState = wallBlock.getState();
	IBlockState groundState = groundBlock.getState();
	
	public Tent(int minX, int minY, int minZ, int maxX, int maxY, int maxZ, int type) {
		super(minX, minY, minZ, maxX, maxY, maxZ);
		this.type = type;
	}

	@Override
	public void setBlocks(World world, int posX, int posY, int posZ, int sizeX,
			int sizeY, int sizeZ, int direction, BiomeColorType colorType, Random rnd) {
		
		if (type == 0) {
		//1 sized border
			posX++;
			posZ++;
			sizeX-=2;
			sizeZ-=2;	
		//
		}
		
		//BiomeColorType biometype = BlockUtils.getBiomeType(world, posX, posZ);
		int camoMeta;
		switch (colorType) {
		case DESERT:
			camoMeta = 2;
			break;
		case SNOW:
			camoMeta = 1;
			break;
		case WOODLAND:
		default:
			camoMeta = 0;
			break;	
		}
		
		
		MutableBlockPos p = new MutableBlockPos();
		//Tent walls and roof;
		for (int i = 0; i < sizeX; i++) {			
			for (int j = 0; j < sizeZ; j++) {
				for (int k = 0; k < sizeY; k++) {
					p.setPos(posX+i,posY+k,posZ+j);
					if (k==0) {
						world.setBlockState(p, groundState, 2);
					}else if (k<sizeY-1) {
						if (j == 0 || j == sizeZ-1 || i == 0 || i == sizeX-1) {
							world.setBlockState(p, wallState, 2);
						}
					}else {
						world.setBlockState(p, roofState, 2);
					}
				}	
			}
		}
		
		if (type == 0 || type == 1) {
		//Tent door and walkway
			switch (direction) {
				case 0:// (-x)
					world.setBlockToAir(p.setPos(posX, posY+1, posZ+(sizeZ/2)));
					world.setBlockToAir(p.setPos(posX, posY+2, posZ+(sizeZ/2)));
					if (type==0) world.setBlockState(p.setPos(posX-1, posY, posZ+(sizeZ/2)), groundState, 2);
					break;
				case 1://(-z)
					world.setBlockToAir(p.setPos(posX+(sizeX/2), posY+1, posZ));
					world.setBlockToAir(p.setPos(posX+(sizeX/2), posY+2, posZ));
					if (type==0) world.setBlockState(p.setPos(posX+(sizeX/2), posY, posZ-1), groundState, 2);
					break;
				case 2://(+x)
					world.setBlockToAir(p.setPos(posX+sizeX-1, posY+1, posZ+(sizeZ/2)));
					world.setBlockToAir(p.setPos(posX+sizeX-1, posY+2, posZ+(sizeZ/2)));
					if (type==0) world.setBlockState(p.setPos(posX+sizeX, posY, posZ+(sizeZ/2)), groundState, 2);
					break;		
				case 3:// (+z)
					world.setBlockToAir(p.setPos(posX+(sizeX/2), posY+1, posZ+sizeZ-1));
					world.setBlockToAir(p.setPos(posX+(sizeX/2), posY+2, posZ+sizeZ-1));
					if (type==0) world.setBlockState(p.setPos(posX+(sizeX/2), posY, posZ+sizeZ), groundState, 2);
					break;
			}
		}
		
		if (type == 2) {
			
			
			for (int y = 1; y < sizeY-1; y++) {
				if (direction == 0 || direction == 2) {
					for (int z = 1; z < sizeZ-1; z++) {
						if (z % 3 != 0) {
							int x = (direction==0) ? 0 : sizeX-1;
							world.setBlockToAir(p.setPos(posX+x, posY+y, posZ+z));
						}
					}
				}else {
					for (int x = 1; x < sizeX-1; x++) {
						if (x % 3 != 1) {
							int z = (direction==1) ? 0 : sizeZ-1;
							world.setBlockToAir(p.setPos(posX+x, posY+y, posZ+z));
						}
					}
				}
			}
		}
	}

}
