package techguns.world.structures;

import java.util.Random;

import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import techguns.util.BlockUtils;

public class MilitaryBaseStructure extends WorldgenStructure {

	public MilitaryBaseStructure(int minX, int minY, int minZ, int maxX, int maxY, int maxZ) {
		super(minX, minY, minZ, maxX, maxY, maxZ);
		
	}

	@Override
	public void setBlocks(World world, int posX, int posY, int posZ, int sizeX, int sizeY, int sizeZ, int direction, BiomeColorType colorType, Random rnd) {
		//NOT NEEDED, DUMMY
	}

	@Override
	protected int getStep() {
		return 8;
	}

	@Override
	public void spawnStructureWorldgen(World world, int chunkX, int chunkZ, int sizeX, int sizeY, int sizeZ, Random rnd, Biome biome) {

		int x = chunkX*16;
		int z = chunkZ*16;
		
		int y = BlockUtils.getValidSpawnYArea(world, x, z, sizeX, sizeZ,5,getStep());
		
		if(  y>0){
		
			BlockUtils.removeJunkInArea(world, x-1, z-1, sizeX+2, sizeZ+2);
			//BlockUtils.flattenArea(world, x-1, z-1, sizeX+2, sizeZ+2, 0);
			//BlockUtils.apply2DHeightmapFilter(world, x-1, z-1, sizeX+2, sizeZ+2, BlockUtils.FILTER_GAUSSIAN_5x5);
			
			MilitaryCamp camp = new MilitaryCamp(4,rnd);
		    camp.init(x, y, z, sizeX, sizeZ);
		    camp.setBlocks(world,rnd);
			
			
			//building.setBlocks(world, x,BlockUtils.getHeightValueNoTrees(world, x, z)-1, z, sizeX, sizeY, sizeZ, random.nextInt(4), BiomeColorType.WOODLAND);
		
		} else {
			if(sizeX>=32 && sizeZ>=32){
				//generateSurface(world, rnd, x,z, sizeX-16, sizeZ-16, true);
				this.spawnStructureWorldgen(world, chunkX, chunkZ, sizeX-16, sizeY, sizeZ-16, rnd, biome);
				
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
	
	@Override
	public int getSizeY(Random rnd) {
		return 8;
	}
	
}

