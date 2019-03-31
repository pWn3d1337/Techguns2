package techguns.world.structures.city;

import java.util.Random;

import net.minecraft.world.World;
import techguns.util.BlockUtils;
import techguns.util.MathUtil.Vec2;
import techguns.world.structures.WorldgenStructure.BiomeColorType;

public class GenericCitySegment {
	int x;
	int y;
	int z;
	
	int sizeX;
	int sizeZ;
	int roadWidth;
	
	boolean isCenter;
	
	GenericCitySegment rootSegment;
	private float rootRadiusSquared;
	GenericCitySegment[] subSegments;
	
	boolean subAlignment = false; //false = n/s (x), true = e/w (z);
	int direction = 0; //0=n, 1=e, 2=s, 3=w;  TODO: Remove subAlignments
	
	GenericCity cityType;
	BiomeColorType colorType;
	
	public Random rand;
	
	//GenericStructure structure = null;
	
	int splitStep;

	public GenericCitySegment(GenericCity cityType, int x, int y, int z, int sizeX, int sizeZ, int roadWidth, int direction, BiomeColorType colorType, Random rand, GenericCitySegment rootSegment) {
		super();
		this.cityType = cityType;
		this.x = x;
		this.y = y;
		this.z = z;
		this.sizeX = sizeX;
		this.sizeZ = sizeZ;
		this.roadWidth = roadWidth;
		this.direction = direction;
		this.colorType = colorType;
		this.rand = rand;
		if (rootSegment == null) {
			this.rootSegment = this;
			this.rootRadiusSquared = (float) Vec2.substract(
					new Vec2(this.x, this.z),
					new Vec2((float)this.x+((float)this.sizeX/2.0f), (float)this.z+((float)this.sizeZ/2.0f))).lenSquared();
		}else {
			this.rootSegment = rootSegment;
		}
	}
	
	public void setBlocks(World world,Random rnd) {
		//System.out.println("SubSegments:"+this.subSegments.length);
		
//		if (splitStep > 2) {
//			BlockUtils.flattenAreaToMaxHeight(world, x, z , sizeX, sizeZ);
//		}
		
		if (subSegments != null && subSegments.length >= 2) {
			for (int i = 0; i < subSegments.length; i++) {
				subSegments[i].setBlocks(world,rnd);				
				//place road
				if (i < subSegments.length-1) {
					if (subAlignment) {
						int y1 = BlockUtils.getGroundY(world, this.x, subSegments[i].z+subSegments[i].sizeZ);
						int y2 = BlockUtils.getGroundY(world, this.x+sizeX, subSegments[i].z+subSegments[i].sizeZ+roadWidth);
						int heightDif = y2-y1;
						cityType.roadType.setRoadBlocks(world, this.x, this.y, subSegments[i].z+subSegments[i].sizeZ, this.roadWidth, sizeX, heightDif, subAlignment, colorType);
					}else{
						int y1 = BlockUtils.getGroundY(world, subSegments[i].x+subSegments[i].sizeX, this.z);
						int y2 = BlockUtils.getGroundY(world, subSegments[i].x+subSegments[i].sizeX+roadWidth, this.z+sizeZ);
						int heightDif = y2-y1;
						cityType.roadType.setRoadBlocks(world, subSegments[i].x+subSegments[i].sizeX, this.y, this.z, this.roadWidth, sizeZ, heightDif, subAlignment, colorType);	
					}
				}
			}
		}else {
			cityType.placeStructure(world, this, rnd);
		}		
	}
	
	public boolean generateSubSegments() {
		int size, size2;
		if (this.sizeX>=this.sizeZ) {
			size = sizeX;
			size2 = sizeZ;
			subAlignment = false;
		} else {
			size = sizeZ;
			size2 = sizeX;
			subAlignment = true;
		}
		
		if (size <= 0) {
			//System.out.println("WTF!");
			return false;
		}
		
		/**
		 * Always split long segments.
		 */
		//boolean splitLong = (size*3) >= size2;
				
		double s = Math.sqrt((double)(size-cityType.minSubSegmentSize)/(double)(cityType.maxSubSegmentSize-cityType.minSubSegmentSize));
		//System.out.println("size="+size+", s="+s+" - centerfactor:"+getCenterFactor());
		if (  size2 < cityType.minSubSegmentSize || ( (size < cityType.maxSubSegmentSize && rand.nextFloat() > (s*0.75f + getCenterFactor()*0.25f)))) {
			//System.out.println("Don't split.");
			subSegments = null;
			return false;
//		}else if (isBorderSegment() && splitStep > 2 && rand.nextFloat() < cityType.fuzzyBorder) {
//			subSegments = null;
//			return false;
		} else {
			//Split it!
			int count = 2;
			//Multisplit 50% Chance
			
			if (this != rootSegment && rand.nextFloat() >= 0.5) {
			   count = Math.max(2,Math.round(1.0f+ (float)rand.nextFloat() * 0.5f * (((float)size-(((float)(cityType.minSubSegmentSize+1))/2.0f))/(float)(roadWidth+2))));	
			}
			//int splitSize = Math.round((((float)size/(float)count) - ((float)roadWidth)*0.5f));				
			
			double[] parts = new double[count];
			double sum = 0;
			for (int i = 0; i < count; i++) {
				parts[i] = 1.0 + rand.nextFloat();
				sum+=parts[i];
			}
			
			int sizeTotal=0;
			
			subSegments = new GenericCitySegment[count];
			int x1 = x;
			int z1 = z;
			for (int i = 0; i < count; i++) {		
				int splitSize;
				int dir;
				if (i >= count-1) {
					splitSize = size-sizeTotal;
					if (subAlignment) dir = 1;
					else dir = 0;
				} else{
					splitSize = (int)Math.round((parts[i]/sum)*(double)(size-(roadWidth*count)));
					if (subAlignment) dir = 3;
					else dir = 2;
				}
				
				//System.out.println("SplitSize="+splitSize);
				int nextRoadWidth = cityType.roadWidth[Math.min(cityType.roadWidth.length-1, this.splitStep+1)];
				if (!subAlignment) {
					subSegments[i] = new GenericCitySegment(cityType, x1+sizeTotal,y, z1, splitSize, sizeZ, nextRoadWidth, dir, colorType, rand, rootSegment);
				}else {
					subSegments[i] = new GenericCitySegment(cityType, x1, y, z1+sizeTotal, sizeX, splitSize, nextRoadWidth, dir, colorType, rand, rootSegment);
				}
				sizeTotal+= splitSize+roadWidth;
				
				if (this.isCenter && this.splitStep <= 1 && i == (count-1)/2) {
					subSegments[i].isCenter = true;
				}else if (this.isCenter && i == count-1) {
					subSegments[i].isCenter = true;
				}
				
				subSegments[i].splitStep = this.splitStep+1;
				subSegments[i].generateSubSegments();
			}	
		}
		return true;
	}
	
	public float getCenterFactor() {
		float f = 0f;
		
		float segmentX = (float)this.x+((float)this.sizeX/2.0f);
		float segmentZ = (float)this.z+((float)this.sizeZ/2.0f);
		
		Vec2 vSeg = new Vec2(segmentX, segmentZ);
		
		float rootX = (float)rootSegment.x+((float)rootSegment.sizeX/2.0f);
		float rootZ = (float)rootSegment.z+((float)rootSegment.sizeZ/2.0f);
		
		Vec2 vRoot = new Vec2(rootX, rootZ);
		
		return 1.0f - (float) ((Vec2.substract(vRoot, vSeg).lenSquared())/ rootSegment.rootRadiusSquared);

	}
	
	public boolean isBorderSegment() {
		
		boolean isBorderX = (this.x <= this.rootSegment.x ||
			this.x+this.sizeX >= this.rootSegment.x + this.rootSegment.sizeX);
		boolean isBorderZ = (this.z <= this.rootSegment.z ||
			this.z+this.sizeZ >= this.rootSegment.z + this.rootSegment.sizeZ);
		//return (isBorderX && borderChanceX > rand.nextFloat()) || (isBorderZ && borderChanceZ > rand.nextFloat());
		return isBorderX || isBorderZ;
	}
}