package techguns.world.structures.city;

import net.minecraft.util.math.BlockPos.MutableBlockPos;
import net.minecraft.world.World;
import techguns.util.MBlock;
import techguns.world.structures.WorldgenStructure.BiomeColorType;

public class CityRoad implements IRoad{
	
	MBlock mainBlock;
	MBlock sideBlock;

	public CityRoad(MBlock mainBlock, MBlock sideBlock) {
		this.mainBlock = mainBlock;
		this.sideBlock = sideBlock;
	}
	
	
	public void setRoadBlocks(World world, int x, int y, int z, int roadWidth, int roadLength, int heightDif, boolean alignment, BiomeColorType colorType) {
		//System.out.println("Set Road Blocks...");
		
		
		int a,b,c = 0;
		float h = 0;
		if (heightDif != 0) {
			h= ((float)roadLength / (float)heightDif);
		}
		
		//BlockUtils.flattenArea(world, x, z , alignment ? roadLength : roadWidth, !alignment ? roadLength : roadWidth, 1);
		
		int xl = 1; //Extra length
		
		MutableBlockPos p = new MutableBlockPos();
		
		for (a=0-xl; a<roadLength+(xl*2); a++) {
			if (h!= 0 && a == (int)((float)(c+1)*h) && c < heightDif) c++;
			for(b=0; b < roadWidth; b++) {
				MBlock block;
				if (roadWidth >=4 && (b > 0 && b < roadWidth-1)) block = mainBlock;
				else block = sideBlock;
				if (alignment) { //Road: in x dir
					for (int i = -1; i < 1; i++) {
						if (!(world.getBlockState(p.setPos(x+a,y+c+i,z+b)) == mainBlock.getState())) {
							world.setBlockState(p.setPos(x+a,y+c+i,z+b), block.getState(), 2);
						}
					}
					for (int i = 1; i < 4; i++) {
						world.setBlockToAir(p.setPos(x+a,y+c+i,z+b));
					}
				}else {
					for (int i = -1; i < 1; i++) {
						if (!(world.getBlockState(p.setPos(x+b,y+c+i,z+a)) == mainBlock.getState())) {
							world.setBlockState(p.setPos(x+b,y+c+i,z+a), block.getState(), 2);
						}
					}
					for (int i = 1; i < GenericCity.SET_AIR_HEIGHT; i++) {
						world.setBlockToAir(p.setPos(x+b,y+c+i,z+a));
					}
				}
			}
		}
		//System.out.println("Set Road Blocks... DONE!");
	}

}