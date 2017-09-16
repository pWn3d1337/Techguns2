package techguns.world.dungeon;

import java.util.Random;

import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.Vec3i;
import net.minecraft.world.World;
import techguns.world.dungeon.TemplateSegment.SegmentType;

public class DungeonPath {

	Random rand;
	
	PathSegment[][][] dungeonVolume;
	
	int sX;
	int sY;
	int sZ;
	
	//private static PathSegment ramp_blocker = DungeonPath.new PathSegment(-1, -1, -1);
	
	public DungeonPath(int sX, int sY, int sZ, Random rand) {
		this.sX =sX;
		this.sY = sY;
		this.sZ = sZ;
		this.rand = rand;
		dungeonVolume = new PathSegment[sX][sY][sZ];
	}


	public void generatePath() {
		int startX = 0;
		int startY = sY/2;
		int startZ = sZ/2;
		int startDir = EnumFacing.getFacingFromVector(1, 0, 0).getHorizontalIndex();
		generateSegment(startX, startY, startZ, startDir, null);
	}
	
	public void generateSegment(int x, int y, int z, int dir, PathSegment prev) {		
		EnumFacing facing = EnumFacing.getHorizontal(dir);
		PathSegment segment = new PathSegment(x,y,z);	
		segment.setConnection(facing.getOpposite(), true);
		if (prev != null) prev.setConnection(facing, true);
		this.addSegment(segment);
		
		int maxRolls = 3;		
		int roll = 0;
		
		int nextDir;
		boolean success = false;
		boolean canRollAgain = true;
		boolean fork = false;
		
		while (!success && roll++ < maxRolls) {
			nextDir = getRandomDir(dir);
			EnumFacing nextFacing = EnumFacing.getHorizontal(nextDir);
			Vec3i nextPos = segment.getNextPos(nextDir);
			if (isWithinBounds(nextPos)) {
				if (dir == nextDir && !fork && rand.nextFloat() > 0.75f) { //ramp
					int dy = rand.nextBoolean() ? 1 : -1;
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
							if (seg != null && !seg.isRamp) {
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
							
//							segment.elevation = dy;
//							segment.isRamp = true;
//							PathSegment rampDummy = new PathSegment(elevPos.getX(), elevPos.getY(), elevPos.getZ());
//							rampDummy.elevation = -dy;
//							rampDummy.isRamp = true;
//							this.addSegment(rampDummy);
//							if (success && !cont) rampDummy.setConnection(nextFacing, true);
////							if (dy == -1) {
////								segment.rampRotation = nextDir;
////								rampDummy.rampRotation = nextFacing.getOpposite().getHorizontalIndex();	
////							}else {
//								segment.rampRotation = nextFacing.getOpposite().getHorizontalIndex();
//								rampDummy.rampRotation = nextDir;
//					//		}
							if (cont) {
								generateSegment(elevNextPos.getX(), elevNextPos.getY(), elevNextPos.getZ(), nextDir, rampDummy);
							}
						}
					}
					
				} else if (!isOccupied(nextPos)) {	
					if (rand.nextFloat() < 0.25f) {//Try to place a Room
						//check left and right first
						int area = 4 + rand.nextInt(12);
						boolean b = tryGrowRoom(nextPos,segment, nextFacing, 2+rand.nextInt(3), 2+rand.nextInt(3), area);
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
					if (seg != null && !seg.isRamp) {
						segment.setConnection(nextFacing, true);
						seg.setConnection(EnumFacing.getHorizontal(nextDir).getOpposite(), true);
						success = true;
					}
				}
			}
			if (success && canRollAgain && rand.nextFloat() > 0.8f) {
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
			/*
			//Place actual room
			prev.setConnection(dir, true);
			
			//int x1 = 
			
			for (int i = 0; i <= bestl; i++) {
				
				for (int j = 0; j <= bestw_l; j++) {
					Vec3i pos_ = new Vec3i(x + vLeft.getX() * j + vFront.getX()*i, y, z + vLeft.getZ() * j + vFront.getZ()*i);
					PathSegment segment = addRoomSegment(pos_, dir, i, j, 0, bestl, bestw_l, bestw_r);
//					PathSegment segment = new PathSegment(pos_.getX(), pos_.getY(), pos_.getZ());
//					addSegment(segment);

					if (i == 0 && j == 0) { //DOOR
						segment.setConnection(dir.getOpposite(), true);
					}
//					int baseRot = ((dir.getHorizontalIndex()+2) % 4) * 2 + 1;
//					if (l > 0) { //back
//						segment.setConnection(dir.getOpposite(), true);
//						if (j < bestw_l) { //back left
//							segment.setConnection((baseRot + 5)%8, true);
//						}
//					}
//					if (l < bestl) { //front
//						segment.setConnection(dir, true);
//						if (j < bestw_l) { //front left
//							segment.setConnection((baseRot + 7)%8, true);
//						}
//						
//					}
//					if (j < bestw_l) {//left
//						segment.setConnection((baseRot + 6)%8, true);
//					}
				}
				
				for (int j = 1; j <= bestw_r; j++) {
					Vec3i pos_ = new Vec3i(x + vRight.getX() * j + vFront.getX()*i, y, z + vRight.getZ() * j + vFront.getZ()*i);
					
					PathSegment segment = addRoomSegment(pos_, dir, i, 0, j, bestl, bestw_l, bestw_r);
//					PathSegment segment = new PathSegment(pos_.getX(), pos_.getY(), pos_.getZ());
//					addSegment(segment);
//					int baseRot = ((dir.getHorizontalIndex()+2) % 4) * 2 + 1;
//					if (l > 0) { //back
//						segment.setConnection(dir.getOpposite(), true);
//						if (j < bestw_r) { //back right
//							segment.setConnection((baseRot + 3)%8, true);
//						}
//					}
//					if (l < bestl) { //front
//						segment.setConnection(dir, true);
//						if (j < bestw_r) { //front right
//							segment.setConnection((baseRot + 1)%8, true);
//						}
//					}
//					if (j < bestw_r) {//left
//						segment.setConnection((baseRot + 2)%8, true);
//					}
				}
			}
			*/
			
//			//Place doors
//			int numDoors = this.rand.nextInt(preferredArea / 8);
//			for (int i = 0; i < numDoors; i++) {
//				float f = rand.nextFloat();
//			}
			
			return true;
		}
	}
		
	private void placeRoomSegments(Vec3i min, Vec3i max) {
		int y = min.getY();//=constant
		for (int x = min.getX(); x <= max.getX(); x++) {
			for (int z = min.getZ(); z <= max.getZ(); z++) {
				PathSegment segment = new PathSegment(x,y,z);
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
				if (seg != null && !seg.isRamp) {
					segment.setConnection(facing, true);
					seg.setConnection(facing.getOpposite(), true);
				}
			}
		}
	}


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
			

//		int x = pos.getX();
//		int y = pos.getY(); //This won't change
//		int z = pos.getZ();
//		
//		Vec3i vFront = dir.getDirectionVec();
//		Vec3i vRight = dir.rotateY().getDirectionVec();
//		Vec3i vLeft = dir.rotateYCCW().getDirectionVec();
//		
//		int l = 0;
//		int w = 0;
//		boolean stop = false;
//		while (l < maxLength && w < maxWidth && !stop) {
//			boolean free = true;
//			//Grow Left
//			w++;
//			for (int i = 0; i < l; i++) {
//				Vec3i pos_ = new Vec3i(x + vLeft.getX() * w + vFront.getX()*i, y, z + vLeft.getZ() * w + vFront.getZ()*i);
//			}
//			//Grow Right
//			for (int i = 0; i < l; i++) {
//				Vec3i pos_ = new Vec3i(x + vRight.getX() * w + vFront.getX()*i, y, z + vRight.getZ() * w + vFront.getZ()*i);
//			}
//			//Grow Front
//			l++;
//			for (int i = 0; i < w; i++) {
//				Vec3i pos_ = new Vec3i(x + vRight.getX() * w + vFront.getX()*i, y, z + vRight.getZ() * w + vFront.getZ()*i);
//			}
//			
//		}
//		
//		
//		return false;
//	}
		
		
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
		}
	}


//		PathSegment segment = new PathSegment(x,y,z);
//		if (prev != null) prev.setConnection(dir, true);
//		segment.setConnection(EnumFacing.getHorizontal(dir).getOpposite(), true);
//		
//		int nextDir = -1;
//		boolean lockedDir = false;
//		if (prev != null && segment.y != prev.y) { //this is a ramp (or the entrance)
//			segment.isRamp = true;
//			segment.elevation = segment.y - prev.y;
//			segment.rampRotation = dir;
//			nextDir = dir; //next dir = same as prev dir
//			lockedDir = true;
//		}
//		dungeonVolume[x][y][z] = segment;
//
//		int triesLeft = 3;
//		while (triesLeft-- > 0) {
//			//Next Segment:
//			if (!lockedDir) nextDir = getRandomDir(dir);
//			Vec3i nextPos = segment.getNextPos(nextDir);
//
//			if (isWithinBounds(nextPos)) {
//				if (!isOccupied(nextPos)) { //Check if there is space
//		
//					//TODO: proper values
//					float f = rand.nextFloat();
//					if (f > 0.75f) {//Create Ramp up or down
//						int dy = 1;
//						Vec3i rampPos = null;
//						if (rand.nextBoolean()) dy = -1;		
//						if (segment.y+dy < this.sY && segment.y+dy >= 0) {
//							rampPos = new Vec3i(nextPos.getX(), nextPos.getY()+dy, nextPos.getZ());
//						}else if (segment.y-dy < this.sY && segment.y-dy >= 0) {
//							rampPos = new Vec3i(nextPos.getX(), nextPos.getY()-dy, nextPos.getZ());
//							dy = -dy;
//						}
//						if (rampPos != null && isWithinBounds(rampPos)) {
//							if (!isOccupied(rampPos)) {
//								EnumFacing facing = EnumFacing.getHorizontal(nextDir);
//								Vec3i offset = facing.getDirectionVec();
//								Vec3i rampPos2 = new Vec3i(rampPos.getX()+offset.getX(), rampPos.getY(), rampPos.getZ()+offset.getZ());
//								if (isWithinBounds(rampPos2)) {
//									PathSegment seg = this.get(rampPos2);
//									if (seg == null || !seg.isRamp) {
//										PathSegment blocker = new PathSegment(nextPos.getX(), nextPos.getY(), nextPos.getZ());
//										blocker.isRamp = true;
//										blocker.elevation = dy;
//										blocker.rampRotation = facing.getOpposite().getHorizontalIndex();
//										dungeonVolume[nextPos.getX()][nextPos.getY()][nextPos.getZ()] = blocker;
//										nextPos = rampPos;
//									}
//								}
//							}
//						}
//					}
//					
//					generateSegment(nextPos.getX(),nextPos.getY(),nextPos.getZ(), nextDir, segment);
//					
//					float f2 = rand.nextFloat();
//					if (f2 > 0.25f) { //0.25 chance to roll again (=fork)
//						return;
//					}
//				}else if (!segment.isRamp){ //Connect to existing segment
//					PathSegment seg = this.get(segment.getNextPos(nextDir));
//					if (seg != null && !seg.isRamp) {
//						segment.setConnection(nextDir, true);
//						seg.setConnection(EnumFacing.getHorizontal(nextDir).getOpposite(), true);
//						return;
//					}
//				}
//			}
//		}
		
	


	//dir = horizontal enumfacing
//	private boolean canConnect(Vec3i pos), int dir) {
//		PathSegment seg = this.get(pos);
//		return seg == null || (!seg.i);// && seg.connectionAt(dir));
//	}
//	
//	private boolean canConnect(Vec3i pos, EnumFacing facing) {
//		PathSegment seg = this.get(pos);
//		return seg == null || (seg.canConnect);// && seg.connectionAt(facing));
//	}


	private boolean isOccupied(Vec3i pos) {
		return dungeonVolume[pos.getX()][pos.getY()][pos.getZ()] != null;
	}


	//Horizontal dir only
	private int getRandomDir(int dir) {
		//TODO: Proper values
		float f = rand.nextFloat();
		if (f > 0.5f) {
			return dir;
		}else if (f > 0.25f) {
			return EnumFacing.getHorizontal(dir).rotateY().getHorizontalIndex();
		}else {
			return EnumFacing.getHorizontal(dir).rotateYCCW().getHorizontalIndex();
		}	
	}
	
	private boolean isWithinBounds(Vec3i pos) {
		return pos.getX() >= 0 && pos.getX() < sX &&
				pos.getY() >= 0 && pos.getY() < sY &&
				pos.getZ() >= 0 && pos.getZ() < sZ;
	}
	
	private PathSegment get(Vec3i index) {
		return dungeonVolume[index.getX()][index.getY()][index.getZ()];
	}

	class PathSegment {
		int x;
		int y;
		int z;
		
		boolean isRamp = false; //Ramps can't connect
		int elevation = 0;
		int rampRotation = -1;
		
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
			Vec3i offset = EnumFacing.getHorizontal(dir).getDirectionVec();
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

	}
	

	public void generateDungeon(World world, int posX, int posY, int posZ, DungeonTemplate template) {
		for (int i = 0; i < sX; i++) {
			for (int j = 0; j < sY; j++) {
				for (int k = 0; k < sZ; k++) {
					PathSegment segment = dungeonVolume[i][j][k];
					if (segment != null) {
						//SegmentType segType = segment.findSegmentType();
						if (segment.isRamp) {
							if (segment.elevation == 1) {
								//TemplateSegment tempSeg = TemplateSegment.templateSegments.get(SegmentType.RAMP);
								int px = posX+(i*template.sizeXZ);
								int py = posY+(j*template.sizeY);
								int pz = posZ+(k*template.sizeXZ);
								int r = segment.getRampRotation();
								//Workaround
								if (r== 0 || r == 2) r = (r+2)%4;
								template.segments.get(SegmentType.RAMP).placeSegment(world, px, py, pz, (r+1) %4);
							}
						}else {
						
							boolean ok = false;
							for (TemplateSegment tempSeg : TemplateSegment.templateSegments.values()) {
								if (tempSeg.sizeY > 1) /*|| !segment.isRamp)*/ continue;
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
									int px = posX+(i*template.sizeXZ);
									int py = posY+(j*template.sizeY);
									int pz = posZ+(k*template.sizeXZ);
									template.segments.get(tempSeg.type).placeSegment(world, px, py, pz, r);
									ok = true;
									break;
								}
							}
							if (!ok) {
								System.out.println("couldn't match segment " + segment.toString());
								segment.printPattern();
							}
						}
					}
				}
			}
		}
	}

}
