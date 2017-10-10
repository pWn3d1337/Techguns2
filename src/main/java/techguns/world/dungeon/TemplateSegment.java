package techguns.world.dungeon;

import java.util.HashMap;

import net.minecraft.util.math.Vec3i;
import techguns.tileentities.DungeonScannerTileEnt;

public class TemplateSegment {
	
	public static HashMap<SegmentType, TemplateSegment> templateSegments = new HashMap<>();
	
	/*     -Z   
	 *  
	 *    0 1 2
	 *-X  7 X 3  +X
	 *    6 5 4
	 *    
	 *     +Z
	 */
	
	static {
		boolean t = true;
		boolean f = false;
		addSegment(SegmentType.STRAIGHT, 0, 0, 1, new boolean[]{f,f,f, t, f, f, f, t}, 2);
		addSegment(SegmentType.CURVE, 0, 1, 1, new boolean[]{f,f,f, t, f, t, f, f}, 4);
		addSegment(SegmentType.FORK, 0, 2, 1, new boolean[]{f,f,f, t, f, t, f, t}, 4);
		addSegment(SegmentType.CROSS, 0, 3, 1, new boolean[]{f,t,f, t, f, t, f, t}, 1);
		addSegment(SegmentType.END, 0, 4, 1, new boolean[]{f,f,f, t, f, f, f, f}, 4);
		
		addSegment(SegmentType.RAMP, 1, 0, 2, false); // new boolean[]{f,f,f, t, f, f, f, t}, 4); //sizeY = 2
		addSegment(SegmentType.ENTRANCE, 1, 1, 1, false);
		
		addSegment(SegmentType.ROOM_WALL, 2, 0, 1, new boolean[]{f,f,f, t, t, t, t, t}, 4);
		addSegment(SegmentType.ROOM_CORNER, 2, 1, 1, new boolean[]{f,f,f, t, t, t, f, f}, 4);
		addSegment(SegmentType.ROOM_INNER, 2, 2, 1,new boolean[]{t,t,t, t, t, t, t, t}, 1);
		addSegment(SegmentType.ROOM_DOOR, 2, 3, 1, new boolean[]{f,t,f, t, t, t, t, t}, 4);
		addSegment(SegmentType.ROOM_DOOR_CORNER1, 2, 4, 1, new boolean[]{f,t,f, t, t, t, f, f}, 4);
		addSegment(SegmentType.ROOM_DOOR_CORNER2, 2, 5 ,1, new boolean[]{f,f,f, t, t, t, f, t}, 4);
		addSegment(SegmentType.ROOM_DOOR_CORNER_DOUBLE, 2, 6, 1, new boolean[]{f,t,f, t, t, t, f, t}, 4);
		
		addSegment(SegmentType.FOUNDATION, 3, 0, 1, false);
		addSegment(SegmentType.PILLARS, 3, 1, 1, false);
	}
	
	private static void addSegment(SegmentType type, int row, int col) {
		templateSegments.put(type, new TemplateSegment(type, row, col));
	}
	
	private static void addSegment(SegmentType type, int row, int col, int sizeY) {
		templateSegments.put(type, new TemplateSegment(type, row, col, sizeY));
	}
	
	private static void addSegment(SegmentType type, int row, int col, int sizeY, boolean[] pattern, int rotations) {
		templateSegments.put(type, new TemplateSegment(type, row, col, sizeY).setPattern(pattern, rotations));
	}
	
	private static void addSegment(SegmentType type, int row, int col, int sizeY, boolean match) {
		templateSegments.put(type, new TemplateSegment(type, row, col, sizeY).setMatch(match));
	}
	
	//get maximum extends of the dungeon template
	public static Vec3i getMaxExtents(int sizeXZ, int sizeY) {
		int maxCol = 0;
		int maxRow = 0;
		int maxHeight = 0;
		for (TemplateSegment temp : TemplateSegment.templateSegments.values()) {
			if (temp.col > maxCol) maxCol = temp.col;
			if (temp.row > maxRow) maxRow = temp.row;
			if (temp.sizeY > maxHeight) maxHeight = temp.sizeY;
		}
		int spacing = DungeonScannerTileEnt.SPACING;
		return new Vec3i((maxCol+1) * (sizeXZ+spacing), sizeY*maxHeight, (maxCol+1) * (sizeXZ+spacing));
	}

	//indices for template scanner
	public int col;
	public int row;
	
	public int sizeY = 1; //= number of stacked segments
	//public int sizeX; //not supported for now
	//public int sizeZ;
	
	SegmentType type;
	
	public int rotations = 1; //1-4
	
	public boolean match = true; //should this segment be used to match a DungeonPath ?
	
	public boolean[] pattern = new boolean[8]; //connection pattern
	/*
	 * 0 1 2
	 * 7 X 3
	 * 6 5 4
	 */
	
	public TemplateSegment(SegmentType type, int row, int col) {
		this.col = col;
		this.row = row;
		this.type = type;
	}
	
	public TemplateSegment(SegmentType type, int row, int col, int sizeY) {
		this(type, row, col);
		this.sizeY = sizeY;
	}
	
	public TemplateSegment setPattern(boolean[] pattern, int rotations) {
		this.pattern = pattern;
		this.rotations = rotations;
		return this;
	}
	
	public TemplateSegment setMatch (boolean match) {
		this.match = match;
		return this;
	}
	

	public enum SegmentType {
		STRAIGHT, CURVE, FORK, CROSS, END,
		RAMP, ENTRANCE,
		ROOM_WALL, ROOM_CORNER, ROOM_INNER, ROOM_DOOR, ROOM_DOOR_CORNER1, ROOM_DOOR_CORNER2, ROOM_DOOR_CORNER_DOUBLE,
		FOUNDATION, PILLARS;
//		
//		public boolean match = true;
//		private SegmentType() {
//			this(true);
//		}
//		
//		private SegmentType(boolean match) {
//			this.match = match;
//		}
	}
	
}
