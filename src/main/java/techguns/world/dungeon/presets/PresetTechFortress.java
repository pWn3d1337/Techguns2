package techguns.world.dungeon.presets;

import java.util.ArrayList;
import java.util.Random;

import techguns.entities.npcs.StormTrooper;
import techguns.tileentities.TGSpawnerTileEnt;
import techguns.world.dungeon.DungeonSegment;
import techguns.world.dungeon.DungeonTemplate;
import techguns.world.dungeon.IDungeonPath;
import techguns.world.dungeon.MazeDungeonPath;
import techguns.world.dungeon.TemplateSegment.SegmentType;

public class PresetTechFortress implements IDungeonPreset{

	int sizeXZ = 0;
	int sizeY = 0;
	
	ArrayList<DungeonTemplate> mainTemplates = new ArrayList<>();
	ArrayList<DungeonTemplate> topTemplates = new ArrayList<>();
	//ArrayList<DungeonTemplate> bottomTemplates = new ArrayList<>();
	
	public PresetTechFortress() {
		mainTemplates.add(DungeonTemplate.dungeonTemplates.get("tfmain"));
		
		topTemplates.add(DungeonTemplate.dungeonTemplates.get("tfupper"));
	}

	@Override
	public DungeonSegment getSegment(SegmentType type, int y, int yMin, int yMax, boolean isSegmentAbove, boolean isSegmentBelow, int seed) {
		Random rand = new Random(seed);
		if (!isSegmentAbove && y > 0) { //y == yMax) {
			return topTemplates.get(rand.nextInt(topTemplates.size())).segments.get(type);
		}else {
			return mainTemplates.get(rand.nextInt(topTemplates.size())).segments.get(type);
		}
	}

	@Override
	public int getSizeXZ() {
		if (this.sizeXZ == 0) this.sizeXZ = this.mainTemplates.get(0).sizeXZ;
		return this.sizeXZ;
	}

	@Override
	public int getSizeY() {
		if (this.sizeY == 0) this.sizeY = this.mainTemplates.get(0).sizeY;
		return this.sizeY;
	}

	@Override
	public void initDungeonPath(IDungeonPath d_path) {
		MazeDungeonPath path = (MazeDungeonPath) d_path;
		path.startHeightLevel = 1;
		path.chanceStraight = 0.8f; //next segment will go forward; -> inverse chance to left/right
		path.chanceRamp = 0.5f; //when straight, roll again for a ramp
		path.chanceRoom = 0.25f; //else, roll for a room
		path.chanceFork = 0.4f; //roll again for another direction, when not a ramp
		path.chanceUp = 0.65f;
		path.useFoundations = true;
		path.usePillars = true;
		path.useRoof = true;
	}

	@Override
	public void initSpawner(TGSpawnerTileEnt spawner) {
		spawner.setParams(2, 2, 200, 2);
		spawner.addMobType(StormTrooper.class, 1);
	}

	
}
