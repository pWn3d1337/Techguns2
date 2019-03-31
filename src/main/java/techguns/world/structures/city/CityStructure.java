package techguns.world.structures.city;

import java.util.Random;

import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import techguns.util.BlockUtils;
import techguns.world.structures.WorldgenStructure;

public class CityStructure extends WorldgenStructure {
	
	@Override
	public void setBlocks(World world, int posX, int posY, int posZ, int sizeX, int sizeY, int sizeZ, int direction, BiomeColorType colorType, Random rnd) {
		//NOT NEEDED
		//spawnStructureWorldgen(world, posX>>4, posY>>4, sizeX, sizeY, sizeZ, rnd, world.getBiome(new BlockPos(posX,posY,posZ)));
	}

	@Override
	protected int getStep() {
		return 16;
	}

	
	
	@Override
	public void spawnStructureWorldgen(World world, int chunkX, int chunkZ, int sizeX, int sizeY, int sizeZ, Random rnd,
			Biome biome) {

		int x = chunkX*16;
		int z = chunkZ*16;
		
		int y = BlockUtils.getValidSpawnYArea(world, x, z, sizeX, sizeZ,5,getStep());
		
		if(  y>0){
		
			BlockUtils.removeJunkInArea(world, x-5, z-5, sizeX+5, sizeZ+5);
			BlockUtils.flattenArea(world, x-1, z-1, sizeX+2, sizeZ+2, 1);
			BlockUtils.apply2DHeightmapFilter(world, x-4, z-4, sizeX+4, sizeZ+4, BlockUtils.FILTER_GAUSSIAN_5x5);
			
			GenericCity.testCity.createCity(world, x, y, z, sizeX, sizeY, sizeZ, BiomeColorType.WOODLAND, rnd);
			
			
			//building.setBlocks(world, x,BlockUtils.getHeightValueNoTrees(world, x, z)-1, z, sizeX, sizeY, sizeZ, random.nextInt(4), BiomeColorType.WOODLAND);
		
		} else {
			if(sizeX>=32 && sizeZ>=32){
				//generateSurface(world, rnd, x,z, sizeX-16, sizeZ-16, true);
				this.spawnStructureWorldgen(world, chunkX, chunkZ, sizeX-16, sizeY-1, sizeZ-16, rnd, biome);
				
			}
			
		}
		
	}

	@Override
	public int getSizeX(Random rnd) {
		return 32+rnd.nextInt(48);
	}

	@Override
	public int getSizeZ(Random rnd) {
		return 32+rnd.nextInt(48);
	}
}
