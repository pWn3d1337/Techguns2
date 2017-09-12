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
					generateSegment(nextPos.getX(), nextPos.getY(), nextPos.getZ(), nextDir, segment);
					success = true;
				}else { // if (!segment.isRamp){ //Connect to existing segment
					PathSegment seg = this.get(nextPos);
					if (seg != null && !seg.isRamp) {
						segment.setConnection(nextDir, true);
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
		
		public boolean connectionAt(int dir) {
			return connectionAt(EnumFacing.getHorizontal(dir));
		}
		
		public boolean connectionAt(EnumFacing facing) {
		//	Vec3i vec = facing.getDirectionVec();
		//	return this.connections[1+vec.getX()][1+vec.getZ()];
		//	return (this.connections & dirToByte(facing)) == this.connections;
			
			int i = ((facing.getHorizontalIndex()+2) % 4) * 2 + 1;
			return pattern[i];
		}
		
		public void setConnection(int dir, boolean connected) {
			this.setConnection(EnumFacing.getHorizontal(dir), connected);
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
									break;
								}
							}
						}
					}
				}
			}
		}
	}

}
