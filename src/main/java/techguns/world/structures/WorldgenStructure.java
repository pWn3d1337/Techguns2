package techguns.world.structures;

import java.util.Random;

import net.minecraft.util.EnumFacing;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import net.minecraftforge.common.BiomeDictionary;
import techguns.util.BlockUtils;
import techguns.world.EnumLootType;
import techguns.world.WorldGenTGStructureSpawn;

public abstract class WorldgenStructure {
	public int minX; //Minimum size
	public int minY;
	public int minZ;
	
	public int maxX; //Maximum size
	public int maxY;
	public int maxZ;
	
	//public int prefX; //preferred size
	//public int prefY;
	//public int prefZ;
	
	public int sizeX;
	public int sizeY;
	public int sizeZ;
	//unrotated center
	public int centX;
	public int centZ;
	
	public boolean canSwapXZ; //IF (min/max) X != Z ... X and Z can be swapped
	
	public boolean removeJunkOnWorldspawn=false;
		
	public static BiomeDictionary.Type[] coldTypes = {BiomeDictionary.Type.COLD, BiomeDictionary.Type.SNOWY};
	public static BiomeDictionary.Type[] sandTypes = {BiomeDictionary.Type.SANDY, BiomeDictionary.Type.SAVANNA.BEACH, BiomeDictionary.Type.MESA};
	public static BiomeDictionary.Type[] netherTypes = {BiomeDictionary.Type.NETHER};
	
	public EnumLootType lootTier=EnumLootType.TIER0;
	
	protected int heightdiffLimit=3;
	
	public WorldgenStructure(){
		this(0,0,0,0,0,0);
	}
	
	protected int getStep(){
		return 4;
	}
	
	public WorldgenStructure(int minX, int minY, int minZ, int maxX, int maxY, int maxZ) {
		super();
		this.minX = minX;
		this.minY = minY;
		this.minZ = minZ;
		this.maxX = maxX;
		this.maxY = maxY;
		this.maxZ = maxZ;
	}
	
	public WorldgenStructure setXZSize(int x, int z){
		this.sizeX=x;
		this.sizeZ=z;
		this.centX = (int) (sizeX/2.0f);
		this.centZ = (int) (sizeZ/2.0f);
		return this;
	}
	
	public WorldgenStructure setXYZSize(int x, int y, int z){
		this.sizeX=x;
		this.sizeY=y;
		this.sizeZ=z;
		this.centX = (int) (sizeX/2.0f);
		this.centZ = (int) (sizeZ/2.0f);
		return this;
	}
	
	public int getSizeX(Random rnd) {
		return sizeX;
	}
	
	public int getSizeZ(Random rnd) {
		return sizeZ;
	}
	
	public int getSizeY(Random rnd) {
		return sizeY;
	}
	
	public WorldgenStructure setSwapXZ(boolean b) {
		this.canSwapXZ = b;
		return this;
	}
	
	/**
	* 
	* @param world
	* @param posX
	* @param posY
	* @param posZ
	* @param sizeX
	* @param sizeY
	* @param sizeZ
	* @param direction - where does it point; 0=WEST(-x), 1=NORTH(-z), 2=EAST(+x); 3=SOUTH(+z);
	* @param rnd TODO
	*/
	public abstract void setBlocks(World world, int posX, int posY, int posZ, int sizeX, int sizeY, int sizeZ, int direction, BiomeColorType colorType, Random rnd);
	
	
	public static int[] rotatePoint(int x, int z, int direction, int centerX, int centerZ){
				
		int a = x-centerX;
		int b = z-centerZ;
		
		for (int i = 0; i < direction; i++) {
			int a2 = a;		
			a = b;
			b = -a2;
		}
		
		x = a + centerX;
		z = b + centerZ;
		
		return new int[]{x,z};
	}
	
	public void spawnStructureWorldgen(World world, int chunkX, int chunkZ, int sizeX, int sizeY, int sizeZ, Random rnd, Biome biome){
		int direction = rnd.nextInt(4);

		
		int sizeXr = direction==0||direction==2 ? sizeX : sizeZ;
		int sizeZr = direction==0||direction==2 ? sizeZ : sizeX;
		
		int centerX = (int) (sizeX/2.0f);
		int centerZ = (int) (sizeZ/2.0f);
		
		/**
		 * Rotate 4 corner points
		 */
		int[] p0 = rotatePoint(0, 0, direction,centerX,centerZ);
		int[] p1 = rotatePoint(sizeX, 0, direction,centerX,centerZ);
		int[] p2 = rotatePoint(0, sizeZ, direction,centerX,centerZ);
		int[] p3 = rotatePoint(sizeX, sizeZ, direction,centerX,centerZ);
		
		int minX = Math.min(Math.min(p0[0], p1[0]),Math.min(p2[0], p3[0]));
		int minZ = Math.min(Math.min(p0[1], p1[1]),Math.min(p2[1], p3[1]));
		
		int x=minX+chunkX*16;//-cXr;
		int z=minZ+chunkZ*16;//-cZr;
				
	
		int offsetX=this.getRotationShiftX(direction);
		int offsetZ=this.getRotationShiftZ(direction);
		
		int y =BlockUtils.getValidSpawnYArea(world, x+offsetX, z+offsetZ, sizeXr, sizeZr,this.heightdiffLimit,getStep());
		
		//System.out.println("Chosen SpawnHeight:"+y);
		
		if (y<0){
			return;
		}
		
		if(removeJunkOnWorldspawn){
			BlockUtils.removeJunkInArea(world, x-1, z-1, sizeXr+2, sizeZr+2);
		}
		
		this.setBlocks(world, x, y-1, z, sizeX, sizeY, sizeZ, direction, getBiomeColorTypeFromBiome(biome), rnd);
	}
	
	public void spawnStructureCaveWorldgen(World world, int chunkX, int chunkZ, int sizeX, int sizeY, int sizeZ, Random rnd, Biome biome){
		int direction = rnd.nextInt(4);

		
		int sizeXr = direction==0||direction==2 ? sizeX : sizeZ;
		int sizeZr = direction==0||direction==2 ? sizeZ : sizeX;
		
		int centerX = (int) (sizeX/2.0f);
		int centerZ = (int) (sizeZ/2.0f);
		
		/**
		 * Rotate 4 corner points
		 */
		int[] p0 = rotatePoint(0, 0, direction,centerX,centerZ);
		int[] p1 = rotatePoint(sizeX, 0, direction,centerX,centerZ);
		int[] p2 = rotatePoint(0, sizeZ, direction,centerX,centerZ);
		int[] p3 = rotatePoint(sizeX, sizeZ, direction,centerX,centerZ);
		
		int minX = Math.min(Math.min(p0[0], p1[0]),Math.min(p2[0], p3[0]));
		int minZ = Math.min(Math.min(p0[1], p1[1]),Math.min(p2[1], p3[1]));
		
		int x=minX+chunkX*16;//-cXr;
		int z=minZ+chunkZ*16;//-cZr;
				
	
		int offsetX=this.getRotationShiftX(direction);
		int offsetZ=this.getRotationShiftZ(direction);
		
		
		//int y =BlockUtils.getValidSpawnYArea(world, x+offsetX, z+offsetZ, sizeXr, sizeZr,this.heightdiffLimit,getStep());
		int y = BlockUtils.getCaveHeight(world, chunkX, chunkZ, 10, WorldGenTGStructureSpawn.NETHER_STRUCT_MIN_Y, WorldGenTGStructureSpawn.NETHER_STRUCT_MAX_y);
		
		
		//System.out.println("Chosen SpawnHeight:"+y);
		
		if (y<0){
			return;
		}
		
		/*if(removeJunkOnWorldspawn){
			BlockUtils.removeJunkInArea(world, x-1, z-1, sizeXr+2, sizeZr+2);
		}*/
		
		this.setBlocks(world, x, y-1, z, sizeX, sizeY, sizeZ, direction, getBiomeColorTypeFromBiome(biome), rnd);
	}
	
	protected static BiomeColorType getBiomeColorTypeFromBiome(Biome biome){
		if (isInBiomeList(coldTypes, biome)){
			return BiomeColorType.SNOW;
		} else if (isInBiomeList(sandTypes, biome)){
			return BiomeColorType.DESERT;
		} else if (isInBiomeList(netherTypes, biome)){
			return BiomeColorType.NETHER;
		}
		
		return BiomeColorType.WOODLAND;
	}
	
	private static boolean isInBiomeList(BiomeDictionary.Type[] list, Biome biome){
		for (int i =0; i<list.length; i++){
			if (BiomeDictionary.hasType(biome, list[i])){
				return true;
			}
		}
		return false;
	}
	
	protected int getRotationShiftX(int direction){
		return 0;
	}
	protected int getRotationShiftZ(int direction){
		return 0;
	}
	
	//W-N-E-S
	public static EnumFacing directionToFacing(int direction) {
		switch(direction) {
		case 0:
			return EnumFacing.WEST;
		case 2:
			return EnumFacing.EAST;
		case 3:
			return EnumFacing.SOUTH;
		default:
			return EnumFacing.NORTH;
		}
	}
	
	public enum BiomeColorType {
		WOODLAND, SNOW, DESERT, NETHER;
	}
	
	protected static int rollBlockIndex(Random rnd, int totalWeight, int[] weights){
		int roll = rnd.nextInt(totalWeight+1);

		int sum =0;
		for (int i =0; i<weights.length; i++){
			sum+=weights[i];
			if (roll <= sum){
				return i;
			}		
		}		
		return weights.length;
	}
}

