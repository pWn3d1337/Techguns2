package techguns.world.dungeon;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import net.minecraft.block.state.IBlockState;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3i;
import net.minecraft.world.World;
import techguns.TGBlocks;
import techguns.tileentities.TGSpawnerTileEnt;
import techguns.world.dungeon.TemplateSegment.SegmentType;
import techguns.world.dungeon.presets.IDungeonPreset;

public class MazeDungeonPath implements IDungeonPath {

	Random rand;
	
	PathSegment[][][] dungeonVolume;
	List<PathSegment> dungeonList;
	
	private int nextRoomID = 1;
	
	int sX;
	int sY;
	int sZ;
	
	//Advanced Parameters
	//---------
	public int startSegmentCount = 1; //Number of entrances to start generating from
	public int startHeightLevel = 0; //Which Y value are the start segments placed; negative numbers = top down
	//int startHeightOffset = 2; //Additional height offset beyond the actual dungeon space for entrance;
	
	//boolean allowVerticalStack = true; //Can segments be placed on top of each other
	
	public float chanceStraight = 0.5f; //next segment will go forward; -> inverse chance to left/right
	public float chanceRamp = 0.25f; //when straight, roll again for a ramp
	public float chanceRoom = 0.25f; //else, roll for a room
	public float chanceFork = 0.2f; //roll again for another direction, when not a ramp
	public float chanceUp = 0.5f; //adjust to set bias towards upwards/downwards ramps
	
	public int minRoomArea = 4;
	public int maxRoomArea = 16;
	public int minRoomWidth = 2;
	public int maxRoomWidth = 5;
	
	public boolean usePillars = true;
	public boolean useFoundations = true;
	public boolean useRoof = false; //currently uses pillar segment of top template
	public boolean useBottomLayer = false; //copy lowest layer one level lower (increases number of levels by 1)
	
	public int entranceRampLength = 0; //Start with a downwards ramp for this many segments
	private int rampPlaced = 0;
	
	//public float spawnDensity = 0.05f; //Spawners per segment count
	//---------

	private int numSegments = 0;
	
	//private static PathSegment ramp_blocker = DungeonPath.new PathSegment(-1, -1, -1);
	
	protected EnumFacing startFacing = null;
	protected BlockPos startPos = null;
	
	public MazeDungeonPath(int sX, int sY, int sZ, Random rand) {
		this.sX =sX;
		this.sY = sY;// System.out.println("sY = "+sY);
		this.sZ = sZ;
		this.rand = rand;
		dungeonVolume = new PathSegment[sX][sY][sZ];
		dungeonList = new ArrayList<PathSegment>();
		//entrancePath = new ArrayList<PathSegment>();
	}

	
	/* (non-Javadoc)
	 * @see techguns.world.dungeon.IDungeonPath#getNumSegments()
	 */
	@Override
	public int getNumSegments() {
		return numSegments;
	}

	/* (non-Javadoc)
	 * @see techguns.world.dungeon.IDungeonPath#generatePath()
	 */
	@Override
	public void generatePath() {
		if (this.useBottomLayer) { //reduce total height by one, so we can shift upwards by one
			this.sY -= 1;
		}
		
		int startX;
		int startZ;
		//EnumFacing startFacing;
		int startY = (sY+startHeightLevel)%sY;
		switch (this.rand.nextInt(4)) {
			case 0:
				startX = 0;
				startZ = sZ/2;
				startFacing = EnumFacing.getFacingFromVector(1, 0, 0);
				break;
			case 1:
				startX = sX-1;
				startZ = sZ/2;
				startFacing = EnumFacing.getFacingFromVector(-1, 0, 0);
				break;
			case 2:
				startX = sX/2;
				startZ = 0;
				startFacing = EnumFacing.getFacingFromVector(0, 0, 1);
				break;
			case 3:
			default:
				startX = sX/2;
				startZ = sZ-1;
				startFacing = EnumFacing.getFacingFromVector(0, 0, -1);
				break;
				
		}
		int startDir = startFacing.getHorizontalIndex();
		
//		Vec3i offset = startFacing.getOpposite().getDirectionVec();
//		PathSegment startSegment = new PathSegment(startX+offset.getX(), startY, startZ+offset.getZ());
//		startSegment.setConnection(startFacing, true);
//		this.entrancePath.add(startSegment);
		
		this.entranceRampLength = Math.min(this.entranceRampLength, startY);
		
		this.startPos = new BlockPos(startX,startY,startZ);
		
		generateSegment(startX, startY, startZ, startDir, null); // startSegment);
		
		
	}

	@Override
	public EnumFacing getEntranceRotation() {
		return this.startFacing;
	}

	@Override
	public BlockPos getStartPos() {
		return this.startPos;
	}

	/* (non-Javadoc)
	 * @see techguns.world.dungeon.IDungeonPath#generateSegment(int, int, int, int, techguns.world.dungeon.DungeonPath.PathSegment)
	 */
	@Override
	public void generateSegment(int x, int y, int z, int dir, PathSegment prev) {		
		EnumFacing facing = EnumFacing.byHorizontalIndex(dir);
		PathSegment segment = new PathSegment(x,y,z);	
		if (prev != null) {
			segment.setConnection(facing.getOpposite(), true);
			prev.setConnection(facing, true);
		}else {
			segment.isEntrance = true;
		}
		this.addSegment(segment);
		
		int maxRolls = 3;		
		int roll = 0;
		
		int nextDir;
		boolean success = false;
		boolean canRollAgain = true;
		boolean fork = false;
		
		//Place downwards entrance ramp
		if (this.rampPlaced < this.entranceRampLength) {
			nextDir = dir;
			Vec3i nextPos = segment.getNextPos(nextDir);
			EnumFacing nextFacing = EnumFacing.byHorizontalIndex(nextDir);
			Vec3i offset = nextFacing.getDirectionVec();
			int dy = -1;
			Vec3i elevPos = new Vec3i(x, y+dy,z);
			Vec3i elevNextPos = new Vec3i(x+offset.getX(), y+dy, z+offset.getZ());
			
			if (isWithinBounds(nextPos) && isWithinBounds(elevNextPos)) {
				
				PathSegment rampDummy = new PathSegment(elevPos.getX(), elevPos.getY(), elevPos.getZ());
				this.addSegment(rampDummy);
				segment.isRamp = true;
				rampDummy.isRamp = true;			
				segment.elevation = -1;
				rampDummy.elevation = 1;
				rampDummy.rampRotation = nextFacing.getOpposite().getHorizontalIndex();	

				generateSegment(elevNextPos.getX(), elevNextPos.getY(), elevNextPos.getZ(), nextDir, rampDummy);
	
				success = true;
			}
			this.rampPlaced++;
		}
		
		
		while (!success && roll++ < maxRolls) {
			if (segment.isEntrance) {
				nextDir = dir;
				canRollAgain = false;
			}else {
				nextDir = getRandomDir(dir);
			}
			EnumFacing nextFacing = EnumFacing.byHorizontalIndex(nextDir);
			Vec3i nextPos = segment.getNextPos(nextDir);
			if (isWithinBounds(nextPos)) {
				if (dir == nextDir && !segment.isEntrance && !fork && rand.nextFloat() < chanceRamp) { //ramp
					float f = rand.nextFloat();
					int dy = f < chanceUp ? 1 : -1;
					Vec3i elevPos = new Vec3i(x, y+dy,z);
					if (isWithinBounds(elevPos) && !isOccupied(elevPos)) {
						Vec3i offset = nextFacing.getDirectionVec();
						Vec3i elevNextPos = new Vec3i(x+offset.getX(), y+dy, z+offset.getZ());
						boolean cont = false;
						if (!isOccupied(elevNextPos)) {
							success = true;
							cont = true;
						}else {
							PathSegment seg = this.get(elevNextPos);
							if (seg != null && !seg.isRamp && !seg.isEntrance) {
								//segment.setConnection(nextDir, true);
								seg.setConnection(nextFacing.getOpposite(), true);
								success = true;
								cont = false;
							}
						}
						if (success) {
							canRollAgain = false;
							PathSegment rampDummy = new PathSegment(elevPos.getX(), elevPos.getY(), elevPos.getZ());
							this.addSegment(rampDummy);
							segment.isRamp = true;
							rampDummy.isRamp = true;
							if (dy == 1) { //ramp up
								segment.elevation = 1;
								rampDummy.elevation = -1;
								segment.rampRotation = nextDir;
							}else { //ramp down
								segment.elevation = -1;
								rampDummy.elevation = 1;
								rampDummy.rampRotation = nextFacing.getOpposite().getHorizontalIndex();	
							}

							if (cont) {
								generateSegment(elevNextPos.getX(), elevNextPos.getY(), elevNextPos.getZ(), nextDir, rampDummy);
							}
						}
					}
					
				} else if (!isOccupied(nextPos)) {	
					if (rand.nextFloat() < chanceRoom) {//Try to place a Room
						//check left and right first
						int area = minRoomArea + rand.nextInt(maxRoomArea-minRoomArea);
						boolean b = tryGrowRoom(nextPos,segment, nextFacing, minRoomWidth+rand.nextInt(maxRoomWidth-minRoomWidth), minRoomWidth+rand.nextInt(maxRoomWidth-minRoomWidth), area);
						if (b) {
							success = true;
						}else { //just a regular segment it is
						
							generateSegment(nextPos.getX(), nextPos.getY(), nextPos.getZ(), nextDir, segment);
							success = true;
						}
						
					}else { //Regular Segment
						generateSegment(nextPos.getX(), nextPos.getY(), nextPos.getZ(), nextDir, segment);
						success = true;
					}
				}else { // if (!segment.isRamp){ //Connect to existing segment
					PathSegment seg = this.get(nextPos);
					if (seg != null && !seg.isRamp && !seg.isEntrance) {
						segment.setConnection(nextFacing, true);
						seg.setConnection(EnumFacing.byHorizontalIndex(nextDir).getOpposite(), true);
						success = true;
					}
				}
			}
			if (success && canRollAgain && rand.nextFloat() < chanceFork) {
				success = false;
				fork = true;
			}
		}
	}
		
	private boolean tryGrowRoom(Vec3i pos, PathSegment prev, EnumFacing dir, int maxWidth, int maxLength, int preferredArea) {
		
		int x = pos.getX();
		int y = pos.getY(); //This won't change
		int z = pos.getZ();
		
		Vec3i vFront = dir.getDirectionVec();
		Vec3i vRight = dir.rotateY().getDirectionVec();
		Vec3i vLeft = dir.rotateYCCW().getDirectionVec();
		
		int l = 0;
		
		int bestl = 0;
		int bestw_l = 0;
		int bestw_r = 0;
		float bestValue = Float.MAX_VALUE; //actually is a penalty
		
		int maxw_l = Integer.MAX_VALUE;
		int maxw_r = Integer.MAX_VALUE;
		
		
		boolean stopL = false;
		while (l < maxLength && !stopL ) {
			Vec3i posF = new Vec3i(x + vFront.getX()*l, y, z+vFront.getZ()*l);
			if (!isWithinBounds(posF) || isOccupied(posF)) {
				stopL = true;
				//l = l-1;
				break;
			}
			
			boolean stop = false;
			int w_l = 0;
			int w_r = 0;
			//Grow one step forward
			//Grow Left
			while (w_l < maxWidth && w_l < maxw_l && !stop) {
				Vec3i pos_ = new Vec3i(x + vLeft.getX() * (w_l+1) + vFront.getX()*l, y, z + vLeft.getZ() * (w_l+1) + vFront.getZ()*l);
				if (!isWithinBounds(pos_) || isOccupied(pos_)) {
					stop = true;
					//w_l = w_l-1;
					maxw_l = w_l;
				}else {
					w_l++;
				}
			}
			//Grow Right
			stop = false;
			while (w_r < maxWidth && w_r < maxw_r &&  !stop) {
				Vec3i pos_ = new Vec3i(x + vRight.getX() * (w_r+1) + vFront.getX()*l, y, z + vRight.getZ() * (w_r+1) + vFront.getZ()*l);
				if (!isWithinBounds(pos_) || isOccupied(pos_)) {
					stop = true;
					//w_r = w_r-1;
					maxw_r = w_r;
				}else {
					w_r++;
				}
			}
			
			//Penalty value
			int area = ( w_l+w_r+1) * (l+1);
			float area_ratio = (float)Math.max(area, preferredArea) / (float)Math.min(area, preferredArea);
			float aspect_ratio = (float)Math.max(( w_l+w_r+1), (l+1)) / (float)Math.min(( w_l+w_r+1), (l+1));			
			float value = area_ratio + aspect_ratio;
			
			if (value < bestValue) {
				bestValue = value;
				bestl = l;
				bestw_l = w_l;
				bestw_r = w_r;
			}
			l++;
			
		}
		
		if (bestl < 2 || (bestw_l+bestw_r+1) < 2) {
			return false;
		}else {
			
			Vec3i p1 = new Vec3i (x + vLeft.getX() * bestw_l, y, z + vLeft.getZ() * bestw_l);
			Vec3i p2 = new Vec3i (x + vRight.getX() * bestw_r + vFront.getX()*bestl, y, z + vRight.getZ() * bestw_r + vFront.getZ()*bestl);
			
			Vec3i min = new Vec3i (Math.min(p1.getX(),p2.getX()), y, Math.min(p1.getZ(),p2.getZ()));
			Vec3i max = new Vec3i (Math.max(p1.getX(),p2.getX()), y, Math.max(p1.getZ(),p2.getZ()));
			
			if (!isWithinBounds(min) || !isWithinBounds(max)) {
				System.out.println("What is this shit?");
				System.out.println(min.toString());
				System.out.println(max.toString());
			}
			
			
			//int numDoors = this.rand.nextInt(preferredArea / 4);
			placeRoomSegments(min, max);
			prev.setConnection(dir, true); //main entrance
			PathSegment segment = this.get(pos);
			if (segment != null) {
				segment.setConnection(dir.getOpposite(), true);
			}else {
				System.out.println("shit");
				//segment.printPattern();
			}
			
			return true;
		}
	}
		
	private void placeRoomSegments(Vec3i min, Vec3i max) {
		int roomID = nextRoomID++;
		
		int y = min.getY();//=constant
		for (int x = min.getX(); x <= max.getX(); x++) {
			for (int z = min.getZ(); z <= max.getZ(); z++) {
				PathSegment segment = new PathSegment(x,y,z);
				segment.roomID = roomID;
				this.addSegment(segment);
				//Connections
				if (x > min.getX()) segment.setConnection(7, true);
				if (x < max.getX()) segment.setConnection(3, true);
				if (z > min.getZ()) segment.setConnection(1, true);
				if (z < max.getZ()) segment.setConnection(5, true);
				if (x != min.getX() && z != min.getZ()) segment.setConnection(0,  true);
				if (x != min.getX() && z != max.getZ()) segment.setConnection(6,  true);
				if (x != max.getX() && z != min.getZ()) segment.setConnection(2,  true);
				if (x != max.getX() && z != max.getZ()) segment.setConnection(4,  true);
			}
		}
		
		//Doors/Exits
		//-X side:
		int length = (max.getZ()-min.getZ())+1;
		int numDoors = rand.nextInt(length);
		for (int i = 0; i < numDoors; i++) {
			int z = rand.nextInt(length);
			Vec3i pos_ = new Vec3i(min.getX(), y, min.getZ()+z);
			PathSegment segment = this.get(pos_);
			EnumFacing facing = EnumFacing.WEST;
			placeDoor(new Vec3i(pos_.getX(), y, pos_.getZ()), facing, segment);
		}
		//+X side
		numDoors = rand.nextInt(length);
		for (int i = 0; i < numDoors; i++) {
			int z = rand.nextInt(length);
			Vec3i pos_ = new Vec3i(max.getX(), y, min.getZ()+z);
			PathSegment segment = this.get(pos_);
			EnumFacing facing = EnumFacing.EAST;
			placeDoor(new Vec3i(pos_.getX(), y, pos_.getZ()), facing, segment);
		}
		//-Z side
		length = (max.getX() - min.getX())+1;
		numDoors = rand.nextInt(length);
		for (int i = 0; i < numDoors; i++) {
			int x = rand.nextInt(length);
			Vec3i pos_ = new Vec3i(min.getX()+x, y, min.getZ());
			PathSegment segment = this.get(pos_);
			EnumFacing facing = EnumFacing.NORTH;
			placeDoor(new Vec3i(pos_.getX(), y, pos_.getZ()), facing, segment);
		}
		//+Z side
		numDoors = rand.nextInt(length);
		for (int i = 0; i < numDoors; i++) {
			int x = rand.nextInt(length);
			Vec3i pos_ = new Vec3i(min.getX()+x, y, max.getZ());
			PathSegment segment = this.get(pos_);
			EnumFacing facing = EnumFacing.SOUTH;
			placeDoor(new Vec3i(pos_.getX(), y, pos_.getZ()), facing, segment);
		}
	}
	
	private void placeDoor(Vec3i pos, EnumFacing facing, PathSegment segment) {
		Vec3i offset = facing.getDirectionVec();
		Vec3i nextPos = new Vec3i(pos.getX()+offset.getX(), pos.getY(), pos.getZ()+offset.getZ());
		if (isWithinBounds(nextPos)) {
			if (!isOccupied(nextPos)) { //continue new path
				generateSegment(nextPos.getX(), nextPos.getY(), nextPos.getZ(), facing.getHorizontalIndex(), segment);
			}else { //connect to existing segment
				PathSegment seg = this.get(nextPos);
				if (seg != null && !seg.isRamp &&!seg.isEntrance) {
					segment.setConnection(facing, true);
					seg.setConnection(facing.getOpposite(), true);
				}
			}
		}
	}

	@Deprecated
	private PathSegment addRoomSegment(Vec3i pos, EnumFacing dir, int l, int w_l, int w_r, int maxL, int maxW_l, int maxW_r ) {
		PathSegment segment = new PathSegment(pos.getX(), pos.getY(), pos.getZ());
		addSegment(segment);
		int baseRot = ((dir.getHorizontalIndex()+2) % 4) * 2 + 1;
		
		//workaround
		if (maxW_r == 0 && w_l > 0) w_r = 1;
		if (maxW_l == 0 && w_r > 0) w_l = 1;
		
		if (l > 0) { //back
			segment.setConnection(dir.getOpposite(), true);
			if (w_l < maxW_l) { //back left
				segment.setConnection((baseRot + 5)%8, true);
			}
			if (w_r < maxW_r) { //back right
				segment.setConnection((baseRot + 3)%8, true);
			}
		}
		if (l < maxL) { //front
			segment.setConnection(dir, true);
			if (w_l < maxW_l) { //front left
				segment.setConnection((baseRot + 7)%8, true);
			}
			if (w_r < maxW_r) { //front right
				segment.setConnection((baseRot + 1)%8, true);
			}			
		}
		if (w_l < maxW_l) {//left
			segment.setConnection((baseRot + 6)%8, true);
		}
		if (w_r < maxW_r) {//right
			segment.setConnection((baseRot + 2)%8, true);
		}
		
		return segment;
	}
			

		
		
	private int countFreeSpaceInDir(Vec3i pos, EnumFacing dir) {
		Vec3i offset = dir.getDirectionVec();
		int i = 0;
		while (isWithinBounds(pos) && ! isOccupied(pos)) {
			pos = new Vec3i(pos.getX() + offset.getX(), pos.getY(), pos.getZ()+offset.getZ());
			i++;
		}
		return i;
	}


	private void addSegment(PathSegment seg) {
		if (seg.x >= 0 && seg.x < sX &&
			seg.y >= 0 && seg.y < sY &&
			seg.z >= 0 && seg.z < sZ) {
			dungeonVolume[seg.x][seg.y][seg.z] = seg;
			dungeonList.add(seg);
			this.numSegments ++;
		}
	}

	private boolean isOccupied(Vec3i pos) {
		return dungeonVolume[pos.getX()][pos.getY()][pos.getZ()] != null;
	}


	//Horizontal dir only
	private int getRandomDir(int dir) {
		float f = rand.nextFloat();
		if (f < chanceStraight) {
			return dir;
		}else if (f < chanceStraight + (1.0f-chanceStraight)*0.5f) {
			return EnumFacing.byHorizontalIndex(dir).rotateY().getHorizontalIndex();
		}else {
			return EnumFacing.byHorizontalIndex(dir).rotateYCCW().getHorizontalIndex();
		}	
	}
	
	private boolean isWithinBounds(Vec3i pos) {
		if (this.entranceRampLength > this.rampPlaced) {
			return pos.getX() >= 0 && pos.getX() < sX &&
					pos.getY() >= 0 && pos.getY() < sY &&
					pos.getZ() >= 0 && pos.getZ() < sZ;
		}else {
			return pos.getX() >= 0 && pos.getX() < sX &&
					pos.getY() >= 0 && pos.getY() < sY-this.entranceRampLength &&
					pos.getZ() >= 0 && pos.getZ() < sZ;
		}
		
	}
	
	private PathSegment get(Vec3i index) {
		return dungeonVolume[index.getX()][index.getY()][index.getZ()];
	}

	public class PathSegment {
		public boolean isEntrance;
		int x;
		int y;
		int z;
		
		boolean isRamp = false; //Ramps can't connect
		int elevation = 0;
		int rampRotation = -1;
		
		int roomID = -1;
		
		//boolean[][] connections = new boolean[3][3];
		//byte connections = 0;
		boolean[] pattern = new boolean[8];
		
		
		public PathSegment(int x, int y, int z) {
			super();
			this.x = x;
			this.y = y;
			this.z = z;
		}

		//dir = Horizontal EnumFacing
		public Vec3i getNextPos(int dir) {
			Vec3i offset = EnumFacing.byHorizontalIndex(dir).getDirectionVec();
			return new Vec3i(x+offset.getX(), y+offset.getY(), z+offset.getZ());
		}
		
		public Vec3i getNextPos(EnumFacing facing) {
			Vec3i offset = facing.getDirectionVec();
			return new Vec3i(x+offset.getX(), y+offset.getY(), z+offset.getZ());
		}
		
		public boolean connectionAt(int index) {
			return pattern[index];
		}
		
		public boolean connectionAt(EnumFacing facing) {
		//	Vec3i vec = facing.getDirectionVec();
		//	return this.connections[1+vec.getX()][1+vec.getZ()];
		//	return (this.connections & dirToByte(facing)) == this.connections;
			
			int i = ((facing.getHorizontalIndex()+2) % 4) * 2 + 1;
			return pattern[i];
		}
		
		public void setConnection(int index, boolean connected) {
			this.pattern[index] = connected;
		}
		
		public void setConnection(EnumFacing facing, boolean connected) {
			int i = ((facing.getHorizontalIndex()+2) % 4) * 2 + 1;
			pattern[i] = connected;

		}
		

		
		public boolean match(boolean[] o, int rotations) {
			int i = rotations*2;
			
			return (pattern.length == 8 && o.length == 8) &&
					pattern[0] == o[(i+0)%8] &&
					pattern[1] == o[(i+1)%8] &&
					pattern[2] == o[(i+2)%8] &&
					pattern[3] == o[(i+3)%8] &&
					pattern[4] == o[(i+4)%8] &&
					pattern[5] == o[(i+5)%8] &&
					pattern[6] == o[(i+6)%8] &&
					pattern[7] == o[(i+7)%8];
					
		}

		public int getRampRotation() {
			return this.rampRotation;
		}
		
		public void printPattern() {
			StringBuilder sb = new StringBuilder();
			char t = '1';
			char f = '0';
			if (pattern[0]) sb.append(t); else sb.append(f);
			if (pattern[1]) sb.append(t); else sb.append(f);
			if (pattern[2]) sb.append(t); else sb.append(f);
			sb.append("\n");
			if (pattern[7]) sb.append(t); else sb.append(f);
			sb.append(" ");
			if (pattern[3]) sb.append(t); else sb.append(f);
			sb.append("\n");
			if (pattern[6]) sb.append(t); else sb.append(f);
			if (pattern[5]) sb.append(t); else sb.append(f);
			if (pattern[4]) sb.append(t); else sb.append(f);
			System.out.println(sb.toString());
		}

		public boolean allowSpawner() {
			return !(this.isEntrance || this.isRamp);
		}

	}
	

	/* (non-Javadoc)
	 * @see techguns.world.dungeon.IDungeonPath#generateDungeon(net.minecraft.world.World, int, int, int, techguns.world.dungeon.presets.IDungeonPreset)
	 */
	@Override
	public void generateDungeon(World world, int posX, int posY, int posZ, IDungeonPreset preset) {
		
		if (this.useBottomLayer) { 
			//shift all segments upwards by 1
			this.cloneBottomLayer();
		}
		
		for (int i = 0; i < sX; i++) {
			for (int j = 0; j < sY; j++) {
				for (int k = 0; k < sZ; k++) {
					boolean above = false;
					for (int y = j+1; y < sY; y++) { //Check if there is a segment above this one
						if (this.dungeonVolume[i][y][k] != null) {
							above = true;
							break;
						}
					}
					boolean below = false;
					boolean directlyBelow = false;
					for (int y = j-1; y >= 0; y--) { //Check if there is a segment above this one
						if (this.dungeonVolume[i][y][k] != null) {
							below = true;
							if (y == j-1) directlyBelow = true;
							break;
						}
					}
					
					PathSegment segment = dungeonVolume[i][j][k];
					if (segment != null) {
						//SegmentType segType = segment.findSegmentType();
						if (segment.isRamp) {
							if (segment.elevation == 1) {
								//TemplateSegment tempSeg = TemplateSegment.templateSegments.get(SegmentType.RAMP);
								int px = posX+(i*preset.getSizeXZ());
								int py = posY+(j*preset.getSizeY());
								int pz = posZ+(k*preset.getSizeXZ());
								int r = segment.getRampRotation();
								//Workaround
								if (r== 0 || r == 2) r = (r+2)%4;
								//template.segments.get(SegmentType.RAMP).placeSegment(world, px, py, pz, (r+1) %4);
								preset.getSegment(SegmentType.RAMP, segment.y, 0, this.sY, above, below, world.rand.nextInt()).placeSegment(world, px, py, pz, (r+1) %4);
							}else {
								//Roof segment
								int r = dungeonVolume[i][j-1][k].getRampRotation();
								if (r== 0 || r == 2) r = (r+2)%4;
								if (useRoof && !above && segment.y+1 < this.sY) {
									int px = posX+(i*preset.getSizeXZ());
									int py = posY+((j+1)*preset.getSizeY());
									int pz = posZ+(k*preset.getSizeXZ());
									int seed = segment.roomID > 0 ? segment.roomID : world.rand.nextInt();		
									preset.getSegment(SegmentType.END, -1, 0, this.sY, above, below, seed).placeSegment(world, px, py, pz, (r+1) %4);
								}
							}
						}else {
						
							boolean ok = false;
							for (TemplateSegment tempSeg : TemplateSegment.templateSegments.values()) {
								if (!tempSeg.match) continue;
								boolean match = false;
								int r = 0;
								while (!match && r < tempSeg.rotations) {
									if (segment.match(tempSeg.pattern, r)) {
										match = true;
									}else {
										r++;
									}
								}
								if (match) {
									int px = posX+(i*preset.getSizeXZ());
									int py = posY+(j*preset.getSizeY());
									int pz = posZ+(k*preset.getSizeXZ());
									if (tempSeg.type == SegmentType.END && j == (sY+this.startHeightLevel)%sY) {										
										preset.getSegment(SegmentType.ENTRANCE, segment.y, 0, this.sY, above, below, world.rand.nextInt()).placeSegment(world, px, py, pz, r);
									}else {										
										int seed = segment.roomID > 0 ? segment.roomID : world.rand.nextInt();									
										preset.getSegment(tempSeg.type, segment.y, 0, this.sY, above, below, seed).placeSegment(world, px, py, pz, r);
									}
									ok = true;
									//Roof segment
									if (useRoof && !above && segment.y >= startHeightLevel && segment.y+1 < this.sY) {
										py = posY+((j+1)*preset.getSizeY());
										int seed = segment.roomID > 0 ? segment.roomID : world.rand.nextInt();		
										preset.getSegment(tempSeg.type, -1, 0, this.sY, above, below, seed).placeSegment(world, px, py, pz, r);
									}
									break;
								}
							}
							if (!ok) {
								System.out.println("couldn't match segment " + segment.toString());
								segment.printPattern();
							}
						}
					}else {
						if (useFoundations && above && !below) {
							int px = posX+(i*preset.getSizeXZ());
							int py = posY+(j*preset.getSizeY());
							int pz = posZ+(k*preset.getSizeXZ());
							preset.getSegment(SegmentType.FOUNDATION, j, 0, this.sY, above, below, world.rand.nextInt()).placeSegment(world, px, py, pz, 0);
						}else if (usePillars && above ) {
							int px = posX+(i*preset.getSizeXZ());
							int py = posY+(j*preset.getSizeY());
							int pz = posZ+(k*preset.getSizeXZ());
							preset.getSegment(SegmentType.PILLARS, j, 0, this.sY, above, below, world.rand.nextInt()).placeSegment(world, px, py, pz, 0);
						}else if (useRoof && directlyBelow && !above) {
							//do nothing
							
//							int px = posX+(i*preset.getSizeXZ());
//							int py = posY+(j*preset.getSizeY());
//							int pz = posZ+(k*preset.getSizeXZ());
//							preset.getSegment(SegmentType.PILLARS, -1, 0, this.sY, above, below, world.rand.nextInt()).placeSegment(world, px, py, pz, 0);		
						}
					}
				}
			}
		}
	}
	
	/**
	 * Expand dungeon Y size by 1, shift dungeon upwards by 1, clone Bottom Layer
	 */
	private void cloneBottomLayer() {
		PathSegment[][][] dungeonVolume2 = new PathSegment[sX][sY+1][sZ];
		for (int i = 0; i < sX; i++) {
			for (int j = 0; j < sY; j++) {
				for (int k = 0; k < sZ; k++) {
					if (dungeonVolume[i][j][k] != null) {
						dungeonVolume2[i][j+1][k] = dungeonVolume[i][j][k];
						dungeonVolume2[i][j+1][k].y++;
						if (j == 0) {
							PathSegment seg2 = new PathSegment(i, j, k);
							PathSegment seg = dungeonVolume[i][j][k];
							seg2.pattern = seg.pattern;
							dungeonVolume2[i][j][k] = seg2;
						}
					}
				}
			}
		}
		this.dungeonVolume = dungeonVolume2;
		sY = sY+1;
		this.startPos = new BlockPos(startPos.getX(), startPos.getY()+1, startPos.getZ());
	}


	/* (non-Javadoc)
	 * @see techguns.world.dungeon.IDungeonPath#generateNPCSpawners(net.minecraft.world.World, int, int, int, techguns.world.dungeon.presets.IDungeonPreset)
	 */
	@Override
	public void generateNPCSpawners(World world, int posX, int posY, int posZ, IDungeonPreset preset) {
		List<PathSegment> spawnPositions = this.getSpawnPositions(preset.getSpawnDensity());
	
		for (PathSegment seg : spawnPositions) {
			int px = posX+(seg.x*preset.getSizeXZ()+(int)(preset.getSizeXZ()*0.5f));
			int py = posY+(seg.y*preset.getSizeY())+preset.getSpawnYOffset(seg);
			int pz = posZ+(seg.z*preset.getSizeXZ()+(int)(preset.getSizeXZ()*0.5f));
			BlockPos pos = new BlockPos(px, py, pz);
			if(pos.getY()>=1) {
				IBlockState state = TGBlocks.MONSTER_SPAWNER.getDefaultState();
				world.setBlockState(pos, state, 2);
				
				TileEntity te = world.getTileEntity(pos);
				if (te != null && te instanceof TGSpawnerTileEnt) {
					TGSpawnerTileEnt spawner = (TGSpawnerTileEnt)te;
					preset.initSpawner(spawner);
				}
			}
		}
	}
	
	private List<PathSegment> getSpawnPositions(float spawnDensity) {
		int maxTries = 5;
		int tries = 0;
		
		int numSpawns = (int)((float)this.getNumSegments() * spawnDensity);
		
		List<PathSegment> spawners = new ArrayList<PathSegment>();
		
		while (numSpawns > 0 && tries < maxTries) {
			int index = this.rand.nextInt(this.dungeonList.size());
			PathSegment seg = this.dungeonList.get(index);
			if (seg != null && !spawners.contains(seg) && seg.allowSpawner()) {
				spawners.add(seg);
				numSpawns--;
				tries = 0;
			}else {
				tries++;
			}
		}
		
		return spawners;
	}

}
