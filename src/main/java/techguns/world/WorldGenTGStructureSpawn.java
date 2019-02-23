package techguns.world;
import java.util.Random;

import net.minecraft.init.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.chunk.Chunk;
import net.minecraft.world.chunk.IChunkProvider;
import net.minecraft.world.gen.IChunkGenerator;
import net.minecraftforge.common.BiomeDictionary;
import net.minecraftforge.fml.common.IWorldGenerator;
import techguns.TGBlocks;
import techguns.TGConfig;
import techguns.util.BlockUtils;
import techguns.util.MathUtil;
import techguns.world.structures.WorldgenStructure;

public class WorldGenTGStructureSpawn implements IWorldGenerator {
	//Structure building = new FactoryHouseSmall(9, 6, 10, 16, 6, 16);
	
	public static final int NETHER_STRUCT_MIN_Y=20;
	public static final int NETHER_STRUCT_MAX_y=100;
	
	@Override
	public void generate(Random random, int chunkX, int chunkZ, World world, IChunkGenerator chunkGenerator,
			IChunkProvider chunkProvider) {
	
		//overworld
		if (world.provider.getDimension()==0){
			Biome biome = world.getBiome(new BlockPos(chunkX*16,64,chunkZ*16));
			generateSurface(world, random, chunkX, chunkZ, biome);
			
		//Nether
		} /*else if (world.provider.getDimension()==-1) {
			Biome biome = world.getBiome(new BlockPos(chunkX*16,64,chunkZ*16));
			generateNether(world, random, chunkX, chunkZ, biome);
			
		}*/
	}

	private void generateEnd(World world, Random random, int ChunkX, int ChunkZ, Biome biome) {
	}

		
	private void generateNether(World world, Random random, int cx, int cz, Biome biome) {

		//if((cx % 2 == 0) && (cz % 2 ==0)){
		
			Chunk c = world.getChunkFromChunkCoords(cx, cz);
			
			
		
				
			int h = BlockUtils.getCaveHeight(world, cx, cz, 10, NETHER_STRUCT_MIN_Y, NETHER_STRUCT_MAX_y);
			
			if(h>=0) {
			
			/*	world.setBlockState(new BlockPos(cx*16+7,h+1,cz*16+7), TGBlocks.ORE_CLUSTER.getDefaultState(), 2);
				
				for(int i=120;i>h+1;i--) {
					world.setBlockState(new BlockPos(cx*16+7,i,cz*16+7), Blocks.REDSTONE_BLOCK.getDefaultState(), 2);
				}
				
				for(int i=4;i<h+1;i++) {
					world.setBlockState(new BlockPos(cx*16+7,i,cz*16+7), Blocks.LAPIS_BLOCK.getDefaultState(), 2);
				}
			*/	
			}
		//}
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
