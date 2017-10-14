package techguns.world.dungeon.presets;

import techguns.world.dungeon.DungeonPath;
import techguns.world.dungeon.DungeonSegment;
import techguns.world.dungeon.DungeonTemplate;
import techguns.world.dungeon.TemplateSegment.SegmentType;

public class PresetTemplateTest implements IDungeonPreset{

	DungeonTemplate template;
	
	public PresetTemplateTest(DungeonTemplate template) {
		super();
		this.template = template;
	}

	@Override
	public DungeonSegment getSegment(SegmentType type, int y, int yMin, int yMax, boolean isSegmentAbove,
			boolean isSegmentBelow, int seed) {
		return template.segments.get(type);
	}

	@Override
	public int getSizeXZ() {
		return template.sizeXZ;
	}

	@Override
	public int getSizeY() {
		return template.sizeY;
	}

	@Override
	public void initDungeonPath(DungeonPath path) {
	}

}
