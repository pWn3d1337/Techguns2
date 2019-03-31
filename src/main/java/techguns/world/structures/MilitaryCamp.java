package techguns.world.structures;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos.MutableBlockPos;
import net.minecraft.world.World;
import techguns.TGBlocks;
import techguns.blocks.EnumMonsterSpawnerType;
import techguns.entities.npcs.ArmySoldier;
import techguns.entities.npcs.AttackHelicopter;
import techguns.tileentities.TGSpawnerTileEnt;
import techguns.util.BlockUtils;
import techguns.util.MBlock;
import techguns.util.MathUtil.Vec2;
import techguns.world.structures.WorldgenStructure.BiomeColorType;

public class MilitaryCamp {
	
	//Static camp visuals definitions
	//---------------------
	static int outerFenceWidth = 1;
	static int outerFenceHeight = 2;
	static int outerFenceSpacing = 1;
	//static MBlock outerFenceBlock = new MBlock(Blocks.IRON_BARS,0);
	static int outerFenceGateHeight = 2;
	static int roadMaxWidth = 5;
	static int roadMinWidth = 1;
	//static MBlock roadBlock = new MBlock(Blocks.GRAVEL, 0);
	//static MBlock roadBlockSecondary = new MBlock(Blocks.CONCRETE.getDefaultState().withProperty(BlockColored.COLOR, EnumDyeColor.GRAY));//TGChiselBlocks.concrete; //TODO
	
	static MBlock groundBlock = null; //new MBlock(Blocks.sand, 0); //Null to keep original world blocks;
		
	//NO LONGER STATIC!
//	static int minSubSegmentSize = 7; //Below this size the segment must NOT be split
//	static int maxSubSegmentSize = 15; //Above this size the segment must be split
	
	static ArrayList<WorldgenStructure> structures;
	static ArrayList<WorldgenStructure> borderStructures;
	static ArrayList<WorldgenStructure> cornerStructures;
	static String[] militaryCampSpawns = new String[]{"ArmySoldier"};
	
	static int minBorderStructureSize = 30; //Below this (total) size border structures must NOT be used
	static int maxBorderStructureSize = 80; //Above this (total) size border structures must be used
	
	static int minCornerStructureSize = 20; //below this size no corner structures
	static int maxCornerStructureSize = 60; //above this size only corner structures;
	
	//--------------------
	static {
		structures = new ArrayList<WorldgenStructure>();

		structures.add(new Tent(5,4,5,7,5,7,0));
		structures.add(new Tent(3,4,3,6,5,6,1));
		structures.add(new Tent(4,4,4,8,6,8,2));
		structures.add(new Containers(2,3,2,7,3,7,2,2,2, new MBlock(TGBlocks.METAL_PANEL.getDefaultState())));

		structures.add(new CampProps(1,3,1,5,5,5, 0)); //Crates
		structures.add(new CampProps(3,5,3,6,7,6, 1)); //Crates + roof
		structures.add(new Bunker(5,3,5,8,3,8, 0)); //no roof;
		structures.add(new Barracks(7,7,7,-1,-1,-1)); //Crates + roof
		structures.add(new Helipad(7,7,7,10,-1,10));
	
		structures.add(new Tanks(4,5,4,10,-1,10));
		structures.add(new CampProps(3,5,3,10,7,10, 5)); //Crates + roof + sandbags
		structures.add(new Bunker(3,3,5,15,3,15, 2).setSwapXZ(true)); //closed Bunker1
		structures.add(new Bunker(4,3,6,15,3,15, 3).setSwapXZ(true)); //closed Bunker2
//		structures.add(new FactoryHouseSmall(9, 6, 10, 16, 6, 16).setSwapXZ(true));
		borderStructures = new ArrayList<>();
		borderStructures.add(new Bunker(5,3,5,8,3,8, 0)); //no roof;
		borderStructures.add(new Bunker(5,4,5,-1,4,-1, 1)); //roof;
		borderStructures.add(new CampProps(3,2,3,8,2,8, 2)); //Sandbags
		borderStructures.add(new CampProps(3,4,3,8,5,8, 3)); //Sandbags
		borderStructures.add(new CampProps(1,2,3,-1,4,-1, 4).setSwapXZ(true)); //Sandbags
		borderStructures.add(new Bunker(3,3,5,10,3,10, 2).setSwapXZ(true)); //closed Bunker1
		borderStructures.add(new Bunker(4,3,6,10,3,10, 3).setSwapXZ(true)); //closed Bunker2
				
		cornerStructures = new ArrayList<>();
		cornerStructures.add(new WatchTowerSmall(3, 8, 3, 4, 8, -1, 3).setSwapXZ(true));
		cornerStructures.add(new WatchTowerSmall(4, 10, 4, -1, 12, -1, 4));
	}
	
	//Camp instance values
	//--------------------
	public int posX;
	public int posY;
	public int posZ;
	
	public int sizeX;
	public int sizeZ;
	//int sizeY;
	float borderChanceX = 0.0f;
	float borderChanceZ = 0.0f;
	float cornerChance = 0.0f;
	
	int minSubSegmentSize = 7; //Below this size the segment must NOT be split
	int maxSubSegmentSize = 15; //Above this size the segment must be split
	
	public BiomeColorType colorType = BiomeColorType.WOODLAND;
	
	Random rand;// = new Random(Minecraft.getMinecraft().theWorld.getSeed());
	
	CampSegment rootSegment;
	private int maxHeightDiff = 0;

	CampSegment flagSegment;
	List<Integer> spawnPositions = new ArrayList<Integer>();
	
	private double campRadiusSquared;
		
	/**
	 * @param maxHeightDiff Smoothes the terrain to this maximum height difference; 0 = superflat; -1 for no flattening
	 */
	/*public MilitaryCamp(int maxHeightDiff, World world) {
		this.maxHeightDiff = maxHeightDiff;
		rand = world.rand;
	}*/
	
	/**
	 * @param maxHeightDiff Smoothes the terrain to this maximum height difference; 0 = superflat; -1 for no flattening
	 */
	public MilitaryCamp(int maxHeightDiff, Random rnd) {
		this.maxHeightDiff = maxHeightDiff;
		rand = rnd;
	}
	
	public void init(int posX, int posY, int posZ, int sizeX, int sizeZ) {
		this.posX = posX;
		this.posY = posY;
		this.posZ = posZ;
		this.sizeX = sizeX;
		this.sizeZ = sizeZ;
		
		campRadiusSquared = Vec2.substract(
				new Vec2(this.posX, this.posZ),
				new Vec2((float)this.posX+((float)this.sizeX/2.0f), (float)this.posZ+((float)this.sizeZ/2.0f))).lenSquared();
		
		int x = posX+outerFenceWidth+outerFenceSpacing;
		int z = posZ+outerFenceWidth+outerFenceSpacing;
		int roadWidth = Math.max(roadMinWidth, Math.min(roadMaxWidth, Math.max(sizeX, sizeZ)/8));
		
		borderChanceX =  ((float)(sizeX-minBorderStructureSize)/(float)(maxBorderStructureSize - minBorderStructureSize));
		borderChanceZ =  ((float)(sizeZ-minBorderStructureSize)/(float)(maxBorderStructureSize - minBorderStructureSize));
		cornerChance =  ((float)(((sizeX+sizeZ)/2)-minCornerStructureSize)/(float)(maxCornerStructureSize - minCornerStructureSize));
				
		if (Math.min(sizeX, sizeZ) < 10) minSubSegmentSize = 5;
		else minSubSegmentSize = 7;
		
		if (Math.min(sizeX, sizeZ) <= 45) maxSubSegmentSize = 15;
		else maxSubSegmentSize = Math.min(sizeX, sizeZ) / 3;
		
		this.rootSegment = new CampSegment(x, posY, z, sizeX - 2*(outerFenceWidth+outerFenceSpacing), sizeZ - 2*(outerFenceWidth+outerFenceSpacing), roadWidth, 0);
		this.rootSegment.isCenter = true;
		this.rootSegment.splitStep = 0;
		this.rootSegment.generateSubSegments();
		
	}
	
	public void setBlocks(World world,Random rnd) {
		this.colorType = BlockUtils.getBiomeType(world, posX+(sizeX/2), posZ+(sizeZ/2));
		
		MutableBlockPos p = new MutableBlockPos();
		
		if (maxHeightDiff != -1) {
			BlockUtils.removeJunkInArea(world, posX-1, posZ-1, sizeX+2, sizeZ+2);
			BlockUtils.flattenArea(world, posX, posZ, sizeX, sizeZ, maxHeightDiff);
			BlockUtils.apply2DHeightmapFilter(world, posX-1, posZ-1, sizeX+2, sizeZ+2, BlockUtils.FILTER_GAUSSIAN_5x5);
		}
		
		if (sizeX < ((outerFenceWidth+outerFenceSpacing)*2)+1 || sizeZ < ((outerFenceWidth+outerFenceSpacing)*2)+1) {
			return;
		}
		
		//Set ground
		if (groundBlock != null) {
			IBlockState state = groundBlock.getState();
			for (int i = 0; i < sizeX; i++) {
				for (int j = 0; j < sizeZ; j++) {
					if (maxHeightDiff != 0) posY = getGroundY(world, posX+i, posZ+j);
					world.setBlockState(p.setPos(posX+i, posY, posZ+j), state, 2);
				}
			}
		}	
		
		//Set fence
		IBlockState outerfencestate = MBlockRegister.MILBASE_FENCE.getState();
		for (int k = 1; k < outerFenceHeight+1; k++) {
			for (int i = 0; i < sizeX; i++) {
				if (maxHeightDiff != 0) posY = getGroundY(world, posX+i, posZ);
				world.setBlockState(p.setPos(posX+i, posY+k, posZ), outerfencestate, 2);
				if (maxHeightDiff != 0) posY = getGroundY(world, posX+i, posZ+sizeZ-1);
				world.setBlockState(p.setPos(posX+i, posY+k, posZ+sizeZ-1), outerfencestate, 2);
			}
			for (int j = 1; j < sizeZ-1; j++) {
				if (maxHeightDiff != 0) posY = getGroundY(world, posX, posZ+j);
				world.setBlockState(p.setPos(posX, posY+k, posZ+j), outerfencestate, 2);
				if (maxHeightDiff != 0) posY = getGroundY(world, posX+sizeX-1, posZ+j);
				world.setBlockState(p.setPos(posX+sizeX-1, posY+k, posZ+j), outerfencestate, 2);
			}
		}
		
		//Set Roads & Subsegments
		this.rootSegment.setBlocks(world,rnd);
		
		initCampFlagEntity(world);
		
	}
	
	private void initCampFlagEntity(World world) {
		if (flagSegment == null) {
			//System.err.println("Fail!");
			return;
		}
		
		BlockUtils.flattenArea(world, flagSegment.x, flagSegment.z , flagSegment.sizeX, flagSegment.sizeZ, 0);
		//TODO: Place Center Structure
		int posX = flagSegment.x+(flagSegment.sizeX/2);
		int posZ = flagSegment.z+(flagSegment.sizeZ/2);
		int posY = getGroundY(world, posX, posZ)+1;
		
		/*world.setBlock(posX, posY, posZ, TGBlocks.basicMachine, 7, 2);
		((CampFlagTileEnt)world.getTileEntity(posX, posY, posZ)).init(
				sizeX, sizeZ,
				posX-this.posX, posY-this.posY, posZ-this.posZ,
				spawnPosAsArray(spawnPositions), militaryCampSpawns);*/
		
		
		
		MutableBlockPos p = new MutableBlockPos(posX,posY,posZ);
		
		world.setBlockState(p, TGBlocks.MONSTER_SPAWNER.getDefaultState().withProperty(TGBlocks.MONSTER_SPAWNER.TYPE,EnumMonsterSpawnerType.SOLDIER_SPAWN),3);
		TileEntity tile = world.getTileEntity(p);
		if(tile!=null && tile instanceof TGSpawnerTileEnt) {
			TGSpawnerTileEnt spawner = (TGSpawnerTileEnt) tile;
			spawner.addMobType(AttackHelicopter.class, 1);
			spawner.setParams(1, 1, 200,0);
			
			int h = Math.min(posY+64,world.getActualHeight())-posY;
			spawner.setSpawnHeightOffset(h);
		}
		
		for (int i=0;i<this.spawnPositions.size();i+=3) {
			int x =posX + this.spawnPositions.get(i) - (posX-this.posX);
			int y =posY + this.spawnPositions.get(i+1) - (posY-this.posY);
			int z =posZ + this.spawnPositions.get(i+2) - (posZ-this.posZ);;
			
			//System.out.println("SpawnPos!");
			
			world.setBlockState(p.setPos(x,y,z), TGBlocks.MONSTER_SPAWNER.getDefaultState().withProperty(TGBlocks.MONSTER_SPAWNER.TYPE,EnumMonsterSpawnerType.SOLDIER_SPAWN),3);
			
			tile = world.getTileEntity(p);
			if(tile!=null && tile instanceof TGSpawnerTileEnt) {
				TGSpawnerTileEnt spawner = (TGSpawnerTileEnt) tile;
				spawner.addMobType(ArmySoldier.class, 1);
				spawner.setParams(3, 1, 200,0);
			}
		}
		
	}

	public void setRoadBlocks(World world, int x, int y, int z, int roadWidth, int roadLength, boolean alignment) {
		int a,b;
		
		MutableBlockPos p = new MutableBlockPos();
		BlockUtils.flattenArea(world, x, z , alignment ? roadLength : roadWidth, !alignment ? roadLength : roadWidth, 1);
		
		for (a=0; a<roadLength; a++) {
			for(b=0; b < roadWidth; b++) {
				MBlock block;
				if (roadWidth >=4 && (b > 0 && b < roadWidth-1)) block = MBlockRegister.MILBASE_ROADBLOCK_SECONDARY;
				else block = MBlockRegister.MILBASE_ROADBLOCK;
				if (alignment) { //Road: in x dir
					if (maxHeightDiff != 0) y = getGroundY(world, x+a, z+b);
					world.setBlockState(p.setPos(x+a,y,z+b), block.getState(), 2);
				}else {
					if (maxHeightDiff != 0) y = getGroundY(world, x+b, z+a);
					world.setBlockState(p.setPos(x+b,y,z+a), block.getState(), 2);
				}
			}
		}
		
		//Add Spawnpoint
		int spawnX;
		int spawnY;
		int spawnZ;
		if (alignment) {
			spawnX = x+(roadLength/2);
			spawnZ = z+(roadWidth/2);
		}else {
			spawnZ = z+(roadLength/2);
			spawnX = x+(roadWidth/2);
		}
		spawnY = getGroundY(world,spawnX,spawnZ)+1;
		spawnPositions.add(spawnX-posX);
		spawnPositions.add(spawnY-posY);
		spawnPositions.add(spawnZ-posZ);
	}
	

	public void setFenceGateBlocks(World world, int x, int y, int z, int roadWidth, int length, int height, boolean alignment) {
		int a,b;
		MutableBlockPos p = new MutableBlockPos();
		
		for (a=0; a<length; a++) {
			for(b=0; b < roadWidth; b++) {
				for (int c = 0; c < height+10; c++) { //+10 = cheap workaround
					if (alignment) { //Road: in x dir
						if (maxHeightDiff != 0) y = getGroundY(world, x+a, z+b);
						if (world.getBlockState(p.setPos(x+a,y+c+1,z+b)) == MBlockRegister.MILBASE_FENCE.getState())
							world.setBlockToAir(p.setPos(x+a,y+c+1,z+b));
					}else {
						if (maxHeightDiff != 0) y = getGroundY(world, x+b, z+a);
						if (world.getBlockState(p.setPos(x+b,y+c+1,z+a)) == MBlockRegister.MILBASE_FENCE.getState())
							world.setBlockToAir(p.setPos(x+b,y+c+1,z+a));
					}
				}
			}
		}
		
	}
	
	private int getGroundY(World world, int x, int z) {
		return world.getHeight(x, z)-1;
	}
	
	
	class CampSegment {
		int x;
		int y;
		int z;
		
		int sizeX;
		int sizeZ;
		int roadWidth;
		
		boolean isCenter;
		
		CampSegment[] subSegments;
		boolean subAlignment = false; //false = n/s (x), true = e/w (z);
		int direction = 0; //0=n, 1=e, 2=s, 3=w;  TODO: Remove subAlignments
		
		//GenericStructure structure = null;
		
		int splitStep;
		
		public CampSegment(int x, int y, int z, int sizeX, int sizeZ, int roadWidth, int direction) {
			super();
			this.x = x;
			this.y = y;
			this.z = z;
			this.sizeX = sizeX;
			this.sizeZ = sizeZ;
			this.roadWidth = Math.max(roadMinWidth, roadWidth);
			this.direction = direction;
		}
		
		public void setBlocks(World world,Random rnd) {
			if (subSegments != null && subSegments.length >= 2) {
				for (int i = 0; i < subSegments.length; i++) {
					subSegments[i].setBlocks(world,rnd);				
					//place road
					if (i < subSegments.length-1) {
						if (this == rootSegment) { //Main-road
							int extralength = (outerFenceWidth+outerFenceSpacing+1);
							if (subAlignment) {
								setRoadBlocks(world, this.x-extralength, this.y, subSegments[i].z+subSegments[i].sizeZ, this.roadWidth, sizeX+(2*extralength), subAlignment);
								//Create Fence gate
								setFenceGateBlocks(world, x-(outerFenceSpacing+outerFenceWidth), y, subSegments[i].z+subSegments[i].sizeZ, this.roadWidth, outerFenceWidth, outerFenceHeight, subAlignment);
								setFenceGateBlocks(world, x+sizeX+outerFenceSpacing, y, subSegments[i].z+subSegments[i].sizeZ, this.roadWidth, outerFenceWidth, outerFenceHeight, subAlignment);
								
							}else{
								setRoadBlocks(world, subSegments[i].x+subSegments[i].sizeX, this.y, this.z-extralength, this.roadWidth, sizeZ+(2*extralength), subAlignment);
								//Create Fence gate
								setFenceGateBlocks(world, subSegments[i].x+subSegments[i].sizeX, y, z-(outerFenceSpacing+outerFenceWidth), this.roadWidth, outerFenceWidth, outerFenceHeight, subAlignment);
								setFenceGateBlocks(world, subSegments[i].x+subSegments[i].sizeX, y, z+sizeZ+outerFenceSpacing, this.roadWidth, outerFenceWidth, outerFenceHeight, subAlignment);
								
							}
						}else {					
							if (subAlignment) setRoadBlocks(world, this.x-1, this.y, subSegments[i].z+subSegments[i].sizeZ, this.roadWidth, sizeX+2, subAlignment);
							else setRoadBlocks(world, subSegments[i].x+subSegments[i].sizeX, this.y, this.z-1, this.roadWidth, sizeZ+2, subAlignment);
						}
					}
				}
			}else {
				if (isCenter && flagSegment == null) {
				//flag
					flagSegment = this;
				}else {
				//place structure
					WorldgenStructure structure;
					
					if (shouldPlaceCornerStructure()) {
						structure = getMatchingStructure(cornerStructures);
					}else if (shouldPlaceBorderStructure()) {
						structure = getMatchingStructure(borderStructures);
					}else {
						structure = getMatchingStructure(structures);
					}
					
					
					int height;
				    if (structure.maxY == -1 || structure.maxY < structure.minY) height = structure.minY;
				    else height = structure.minY+rand.nextInt(structure.maxY+1-structure.minY);
					
				    BlockUtils.flattenArea(world, x, z , sizeX, sizeZ, 0);
				    y = getGroundY(world, x+sizeX/2, z+sizeZ/2);
					structure.setBlocks(world, x, y, z, sizeX, height, sizeZ, this.direction, colorType, rnd);
				}
			}
			
		}
		
		private WorldgenStructure getMatchingStructure(ArrayList<WorldgenStructure> list) {
			ArrayList<WorldgenStructure> matchingStructures = new ArrayList<>();
			
			for (WorldgenStructure str : list) {
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
				return new EmptyPlane(Blocks.COBBLESTONE.getDefaultState());
			}
		}

		public boolean generateSubSegments() {
			int size;
			if (this.sizeX>=this.sizeZ) {
				size = sizeX;
				subAlignment = false;
			} else {
				size = sizeZ;
				subAlignment = true;
			}
			
			if (size <= 0) {
				System.out.println("WTF!");
				return false;
			}
			
			double s = Math.sqrt((double)(size-minSubSegmentSize)/(double)(maxSubSegmentSize-minSubSegmentSize));
			//System.out.println("size="+size+", s="+s+" - centerfactor:"+getCenterFactor());
			if (size < minSubSegmentSize || (size < maxSubSegmentSize && rand.nextFloat() > (s*0.75f + getCenterFactor()*0.25f))) {
				//System.out.println("Don't split.");
				subSegments = null;
				return false;
			} else {
				//Split it!
				
				
				int count = 2;
				//Multisplit 50% Chance
				//int count = 2;
				
				if (this != rootSegment && rand.nextFloat() >= 0.5) {
				   count = Math.max(2,Math.round(1.0f+ (float)rand.nextFloat() * 0.5f * (((float)size-(((float)(minSubSegmentSize+1))/2.0f))/(float)(roadWidth+2))));	
				}
				//int splitSize = Math.round((((float)size/(float)count) - ((float)roadWidth)*0.5f));				
				
				double[] parts = new double[count];
				double sum = 0;
				for (int i = 0; i < count; i++) {
					parts[i] = 1.0 + rand.nextFloat();
					sum+=parts[i];
				}
				
				int sizeTotal=0;

				
				
				//System.out.println("Split! count="+count);// + ", splitSize="+splitSize+ ", sizeLast="+sizeLast);
				
				subSegments = new CampSegment[count];
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
					if (!subAlignment) {
						subSegments[i] = new CampSegment(x1+sizeTotal,y, z1, splitSize, sizeZ, roadWidth-1, dir);
					}else {
						subSegments[i] = new CampSegment(x1, y, z1+sizeTotal, sizeX, splitSize, roadWidth-1, dir);
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
			
			float campX = (float)MilitaryCamp.this.posX+((float)MilitaryCamp.this.sizeX/2.0f);
			float campZ = (float)MilitaryCamp.this.posZ+((float)MilitaryCamp.this.sizeZ/2.0f);
			
			Vec2 vCamp = new Vec2(campX, campZ);
			
			return 1.0f - (float) ((Vec2.substract(vCamp, vSeg).lenSquared())/ campRadiusSquared);

		}
		
		public boolean shouldPlaceBorderStructure() {
			int border = MilitaryCamp.this.outerFenceWidth + MilitaryCamp.this.outerFenceSpacing;
			boolean isBorderX = (this.x <= MilitaryCamp.this.posX+border ||
				this.x+this.sizeX >= MilitaryCamp.this.posX + MilitaryCamp.this.sizeX-border);
			boolean isBorderZ = (this.z <= MilitaryCamp.this.posZ+border ||
				this.z+this.sizeZ >= MilitaryCamp.this.posZ + MilitaryCamp.this.sizeZ-border);
			return (isBorderX && borderChanceX > rand.nextFloat()) || (isBorderZ && borderChanceZ > rand.nextFloat());
		}
		
		public boolean shouldPlaceCornerStructure() {
			int border = MilitaryCamp.this.outerFenceWidth + MilitaryCamp.this.outerFenceSpacing;
			boolean isCorner = ((this.x <= MilitaryCamp.this.posX+border && this.z <= MilitaryCamp.this.posZ+border) ||
					(this.x <= MilitaryCamp.this.posX+border && this.z+this.sizeZ >= MilitaryCamp.this.posZ + MilitaryCamp.this.sizeZ-border) ||
					(this.x+this.sizeX >= MilitaryCamp.this.posX + MilitaryCamp.this.sizeX-border && this.z <= MilitaryCamp.this.posZ+border)||
					(this.x+this.sizeX >= MilitaryCamp.this.posX + MilitaryCamp.this.sizeX-border && this.z+this.sizeZ >= MilitaryCamp.this.posZ + MilitaryCamp.this.sizeZ-border));
			return isCorner && cornerChance > rand.nextFloat();
		}
		
	
		
	}


	public int[] spawnPosAsArray(List<Integer> list) {
		int[] s= new int[list.size()];
		for (int i = 0; i < list.size(); i++) {
			s[i] = list.get(i).intValue();			
		}
		return s;
	}

}
