package techguns.world.structures;

import java.util.Random;

import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import techguns.util.BlockUtils;

public class AlienBugNestStructure extends WorldgenStructure {

	@Override
	public void setBlocks(World world, int posX, int posY, int posZ, int sizeX, int sizeY, int sizeZ, int direction,
			BiomeColorType colorType, Random rnd) {
		//NOT NEEDED, DUMMY
	}


	@Override
	public void spawnStructureWorldgen(World world, int chunkX, int chunkZ, int sizeX, int sizeY, int sizeZ, Random rnd, Biome biome) {

		int x = chunkX*16;
		int z = chunkZ*16;
		
		int y = BlockUtils.getValidSpawnY(world, x, z, sizeX, sizeZ,256);
		
		if(y>0) {
			//System.out.println("Do Spawn!");
			new AlienBugNest(x,y-2,z,16+rnd.nextInt(16), sizeY, 16+rnd.nextInt(16), rnd).setBlocks(world);
		}
		
	}

}
