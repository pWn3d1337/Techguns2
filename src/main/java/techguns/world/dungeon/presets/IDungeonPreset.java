package techguns.world.dungeon.presets;

import techguns.world.dungeon.DungeonPath;
import techguns.world.dungeon.DungeonSegment;
import techguns.world.dungeon.TemplateSegment;
import techguns.world.dungeon.TemplateSegment.SegmentType;

public interface IDungeonPreset {
	
	//Dungeon Presets
	public final static IDungeonPreset PRESET_TECHFORTRESS = new PresetTechFortress();
	
	

	public DungeonSegment getSegment(SegmentType type, int y, int yMin, int yMax, boolean isSegmentAbove, boolean isSegmentBelow, int seed);
	
	public int getSizeXZ();
	public int getSizeY();
	
	public void initDungeonPath(DungeonPath path);
}
