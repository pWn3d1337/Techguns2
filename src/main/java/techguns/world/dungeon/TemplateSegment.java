package techguns.world.dungeon;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import net.minecraft.util.math.Vec3i;
import techguns.tileentities.DungeonScannerTileEnt;

public class TemplateSegment {
	
	public static HashMap<SegmentType, TemplateSegment> templateSegments = new HashMap<>();
	
	static {
		addSegment(SegmentType.STRAIGHT, 0, 0);
		addSegment(SegmentType.CURVE, 0, 1);
		addSegment(SegmentType.FORK, 0, 2);
		addSegment(SegmentType.CROSS, 0, 3);
		addSegment(SegmentType.END, 0, 4);
		
		addSegment(SegmentType.RAMP, 1, 0, 2); //sizeY = 2
		
		addSegment(SegmentType.ROOM_WALL, 2, 0);
		addSegment(SegmentType.ROOM_CORNER, 2, 1);
		addSegment(SegmentType.ROOM_INNER, 2, 2);
		addSegment(SegmentType.ROOM_DOOR, 2, 3);
		addSegment(SegmentType.ROOM_DOOR_CORNER1, 2, 4);
		addSegment(SegmentType.ROOM_DOOR_CORNER2, 2, 5);
		addSegment(SegmentType.ROOM_DOOR_CORNER_DOUBLE, 2, 6);
		
	}
	
	private static void addSegment(SegmentType type, int row, int col) {
		templateSegments.put(type, new TemplateSegment(type, row, col));
	}
	
	private static void addSegment(SegmentType type, int row, int col, int sizeY) {
		templateSegments.put(type, new TemplateSegment(type, row, col, sizeY));
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
	
	public TemplateSegment(SegmentType type, int row, int col) {
		this.col = col;
		this.row = row;
		this.type = type;
	}
	
	public TemplateSegment(SegmentType type, int row, int col, int sizeY) {
		this(type, row, col);
		this.sizeY = sizeY;
	}

	public enum SegmentType {
		STRAIGHT, CURVE, FORK, CROSS, END,
		RAMP,
		ROOM_WALL, ROOM_CORNER, ROOM_INNER, ROOM_DOOR, ROOM_DOOR_CORNER1, ROOM_DOOR_CORNER2, ROOM_DOOR_CORNER_DOUBLE
	}
	
}
