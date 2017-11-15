package techguns.world;
import java.util.Random;

import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.chunk.IChunkProvider;
import net.minecraft.world.gen.IChunkGenerator;
import net.minecraftforge.common.BiomeDictionary;
import net.minecraftforge.fml.common.IWorldGenerator;
import techguns.TGConfig;
import techguns.world.structures.WorldgenStructure;

public class WorldGenTGStructureSpawn implements IWorldGenerator {
	//Structure building = new FactoryHouseSmall(9, 6, 10, 16, 6, 16);
	
	@Override
	public void generate(Random random, int chunkX, int chunkZ, World world, IChunkGenerator chunkGenerator,
			IChunkProvider chunkProvider) {
	
		if (world.provider.getDimension()==0){
			//Biome biome = world.getWorldChunkManager().getBiomeGenAt(chunkX*16, chunkZ*16);
			Biome biome = world.getBiome(new BlockPos(chunkX*16,64,chunkZ*16));
			
			generateSurface(world, random, chunkX, chunkZ, biome);
		}
	}

	private void generateEnd(World world, Random random, int ChunkX, int ChunkZ, Biome biome) {
	}

	private void generateNether(World world, Random random, int ChunkX, int ChunkZ, Biome biome) {

	}

	private void generateSurface(World world, Random random, int cx, int cz, Biome biome) {
				
		int SPAWNWEIGHT_SMALL = TGConfig.spawnWeightTGStructureSmall;
		int SPAWNWEIGHT_BIG = TGConfig.spawnWeightTGStructureBig;
		int SPAWNWEIGHT_MEDIUM = TGConfig.spawnWeightTGStructureMedium;
		
		StructureSize size = null;
		int sizeX = 0;
		int sizeZ = 0;
		int sizeY = 0;
		
		if((cx % SPAWNWEIGHT_BIG == 0) && (cz % SPAWNWEIGHT_BIG ==0)){
			size=StructureSize.BIG;
//			sizeX = 32+random.nextInt(48);
//		    sizeZ = 32+random.nextInt(48);
		} else if ((cx % SPAWNWEIGHT_MEDIUM == 0) && (cz % SPAWNWEIGHT_MEDIUM ==0)){
			
			size = StructureSize.MEDIUM;
		} else if ((cx % SPAWNWEIGHT_SMALL == 0) && (cz % SPAWNWEIGHT_SMALL ==0)){
			size=StructureSize.SMALL;
			
			//sizeX=16;
			//sizeZ=16;
			
		}
		
		
		if(size!=null){
		
			StructureLandType type = StructureLandType.LAND;
			
			if (BiomeDictionary.hasType(biome,BiomeDictionary.Type.OCEAN)){
				type = StructureLandType.WATER;
				//System.out.println("Get WATER TYPE STRUCTURE!!!");
			}
				
			WorldgenStructure s = TGStructureSpawnRegister.choseStructure(random, biome, size, type);

			if (s!=null){
				
				sizeX=s.getSizeX(random);
				sizeZ=s.getSizeZ(random);
				
				sizeY = s.getSizeY(random);

				s.spawnStructureWorldgen(world, cx, cz, sizeX, sizeY, sizeZ, random, biome);
	
				
				
			}
			
		}
		
	}



}
