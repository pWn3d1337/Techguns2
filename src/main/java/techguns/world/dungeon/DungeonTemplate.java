package techguns.world.dungeon;

import java.io.ObjectOutputStream;
import java.util.HashMap;

import net.minecraft.world.World;
import techguns.tileentities.DungeonScannerTileEnt;
import techguns.world.dungeon.TemplateSegment.SegmentType;

public class DungeonTemplate {

	public static HashMap<String, DungeonTemplate> dungeonTemplates;
	
	public int sizeXZ;
	public int sizeY;
	
	public HashMap<SegmentType, DungeonSegment> segments;
	
	
	public DungeonTemplate(int sizeXZ, int sizeY) {
		super();
		this.sizeXZ = sizeXZ;
		this.sizeY = sizeY;
	}



	public static DungeonTemplate scanTemplate(World world, int posX, int posY, int posZ, int sizeXZ, int sizeY, String name) {
		DungeonTemplate template = new DungeonTemplate(sizeXZ, sizeY);
		
		for (SegmentType type : TemplateSegment.templateSegments.keySet()) {
			DungeonSegment segment = new DungeonSegment(template, type);
			segment.scanBlocks(world, posX, posY, posZ);
		}
		
		dungeonTemplates.put(name, template);
		
		return template;
	}

}
