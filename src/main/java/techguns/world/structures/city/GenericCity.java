package techguns.world.structures.city;

import java.util.ArrayList;
import java.util.Random;

import net.minecraft.init.Blocks;
import net.minecraft.world.World;
import techguns.TGBlocks;
import techguns.util.BlockUtils;
import techguns.util.MBlock;
import techguns.world.structures.WorldgenStructure;
import techguns.world.structures.WorldgenStructure.BiomeColorType;

public class GenericCity { //City Type

	public final static int SET_AIR_HEIGHT = 3;
	
	//City Definitions
	public static GenericCity testCity;
	
	static {
		testCity = new GenericCity(20,80,12,30,-1,new int[]{6,4,4,2,1},new CityRoad(new MBlock(TGBlocks.CONCRETE,3), new MBlock(TGBlocks.CONCRETE,2)), 0.5f);
		testCity.structures.add(new CityBuilding(CityBuildingType.building1, 3, 5, 5, -1, 16, -1).setSwapXZ(true));
		testCity.structures.add(new CityBuilding(CityBuildingType.building2, 4, 5, 4, -1, 16, -1));
		testCity.structures.add(new CityBuilding(CityBuildingType.building3, 4, 5, 4, -1, 16, -1));
		testCity.structures.add(new CityBuilding(CityBuildingType.building4, 4, 5, 4, -1, 16, -1));
		testCity.structures.add(new CityBuilding(CityBuildingType.building5, 4, 5, 4, -1, 16, -1));
		testCity.structures.add(new CityBuilding(CityBuildingType.building6, 4, 5, 4, -1, 16, -1));
	}
	//---
	//City Type Attributes
	int sizeMin;
	int sizeMax;	
	int minSubSegmentSize = 8; //Below this size the segment must NOT be split
	int maxSubSegmentSize = 16; //Above this size the segment must be split
	int maxHeightDiff = 3;
	float fuzzyBorder = 0.0f;
	//Random rand;// = new Random(Minecraft.getMinecraft().theWorld.getSeed());

	ArrayList<WorldgenStructure> structures;
	int[] roadWidth;
	public IRoad roadType;	
	//---
	
	public GenericCity(int sizeMin, int sizeMax, int minSubSegmentSize,
			int maxSubSegmentSize, int maxHeightDiff, int[] roadWidth,
			IRoad roadType, float fuzzyBorder) {
		super();
		this.sizeMin = sizeMin;
		this.sizeMax = sizeMax;
		this.minSubSegmentSize = minSubSegmentSize;
		this.maxSubSegmentSize = maxSubSegmentSize;
		this.maxHeightDiff = maxHeightDiff;
		this.roadWidth = roadWidth;
		this.roadType = roadType;
		this.structures = new ArrayList<WorldgenStructure>();
		this.fuzzyBorder = fuzzyBorder;
	}
	
	
	public void createCity(World world, int posX, int posY, int posZ, int sizeX, int sizeY, int sizeZ, BiomeColorType colorType,Random rnd) {
		//TODO: Generalize this!		
		if (Math.min(sizeX, sizeZ) < 14) minSubSegmentSize = 7;
		else minSubSegmentSize = 10;
		
		if (Math.min(sizeX, sizeZ) <= 45) maxSubSegmentSize = 15;
		else maxSubSegmentSize = Math.min(sizeX, sizeZ) / 3;
		
		if (colorType == null) {
			colorType = BlockUtils.getBiomeType(world, posX+(sizeX/2), posZ+(sizeZ/2));
		}
		
		//PREPARE SEGMENTS
		//------------
		
		GenericCitySegment rootSegment = new GenericCitySegment(this, posX, posY, posZ, sizeX, sizeZ, roadWidth[0], 0, colorType, rnd, null);
		rootSegment.isCenter = true;
		rootSegment.splitStep = 0;
		//System.out.println("Generate Subsegments...");
		boolean b = rootSegment.generateSubSegments();
		if (b) {
			//System.out.println("Generate Subsegments... DONE!");
			setBlocks(world, posX, posY, posZ, sizeX, sizeY, sizeZ, colorType, rootSegment,rnd);
		}else {
			//System.out.println("Generate Subsegments... ERROR!");
		}
		
		
		//SET BLOCKS
		//---------------
		
	}


	private void setBlocks(World world, int posX, int posY, int posZ, int sizeX, int sizeY, int sizeZ, BiomeColorType colorType, GenericCitySegment rootSegment, Random rnd) {
			
		BlockUtils.removeJunkInArea(world, posX-1, posZ-1, sizeX+2, sizeZ+2);
		BlockUtils.apply2DHeightmapFilter(world, posX-1, posZ-1, sizeX+2, sizeZ+2, BlockUtils.FILTER_GAUSSIAN_5x5);
		if (maxHeightDiff != -1) {			
			BlockUtils.flattenArea(world, posX, posZ, sizeX, sizeZ, maxHeightDiff);
			
		}
		
		//Set Roads & Subsegments
		//System.out.println("Set Blocks...");
		rootSegment.setBlocks(world,rnd);
		//System.out.println("Set Blocks...DONE!");
				
	}
	
	
	
	public void placeStructure(World world, GenericCitySegment segment, Random rnd) {
		if (segment.isBorderSegment() && segment.splitStep > 2 && segment.rand.nextFloat() < fuzzyBorder) {
			return;
		}
		
		BlockUtils.fillBlocksAir(world, segment.x,segment.y+1,segment.z, segment.sizeX, SET_AIR_HEIGHT, segment.sizeZ);
		
		WorldgenStructure structure = getMatchingStructure(segment.sizeX, -1, segment.sizeZ, segment.rand);
				
		int height;
	    if (structure.maxY == -1 || structure.maxY < structure.minY) height = structure.minY;
	    else height = structure.minY+segment.rand.nextInt(structure.maxY+1-structure.minY);
		
	    BlockUtils.flattenArea(world, segment.x, segment.z , segment.sizeX, segment.sizeZ, 0);
	    int y = BlockUtils.getGroundY(world, segment.x+segment.sizeX/2, segment.z+segment.sizeZ/2);
		structure.setBlocks(world, segment.x, segment.y, segment.z, segment.sizeX, height, segment.sizeZ, segment.direction, segment.colorType, rnd);
	}
	
	protected WorldgenStructure getMatchingStructure(int sizeX, int sizeY, int sizeZ, Random rand) {
		ArrayList<WorldgenStructure> matchingStructures = new ArrayList<>();
		
		for (WorldgenStructure str : structures) {
			if (str.canSwapXZ) {
				if ( ((sizeX >= str.minX && sizeZ >= str.minZ) || (sizeZ >= str.minX && sizeX >= str.minZ))
						&& (((str.maxX == -1 || sizeX <= str.maxX) && (str.maxZ == -1 || sizeZ <= str.maxZ)) ||
						   ((str.maxX == -1 || sizeZ <= str.maxX) && (str.maxZ == -1 || sizeX <= str.maxZ)) )) {
						matchingStructures.add(str);
					}
			}else {
				if (sizeX >= str.minX && sizeZ >= str.minZ &&
					(str.maxX == -1 || sizeX <= str.maxX) &&
					(str.maxZ == -1 || sizeZ <= str.maxZ)) {
					matchingStructures.add(str);
				}
			}
		}
		if (!matchingStructures.isEmpty()) {
			return matchingStructures.get(rand.nextInt(matchingStructures.size()));

		}else {
			return new EmptyPlane(new MBlock(Blocks.COBBLESTONE,0));
		}
	}

	
	
}
