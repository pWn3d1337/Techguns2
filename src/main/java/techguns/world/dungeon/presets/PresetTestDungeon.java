package techguns.world.dungeon.presets;

import net.minecraft.util.ResourceLocation;
import techguns.Techguns;
import techguns.entities.npcs.ZombieSoldier;
import techguns.tileentities.TGSpawnerTileEnt;
import techguns.world.dungeon.DungeonSegment;
import techguns.world.dungeon.DungeonTemplate;
import techguns.world.dungeon.IDungeonPath;
import techguns.world.dungeon.MazeDungeonPath;
import techguns.world.dungeon.TemplateSegment.SegmentType;

public class PresetTestDungeon implements IDungeonPreset {

	private static final ResourceLocation LOOTTABLE = new ResourceLocation(Techguns.MODID, "chests/castle");
	DungeonTemplate template = DungeonTemplate.dungeonTemplates.get("ncdung1").setLoottable(LOOTTABLE);
	
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
	public void initDungeonPath(IDungeonPath d_path) {
		MazeDungeonPath path = (MazeDungeonPath) d_path;
		path.startHeightLevel = 5;
		path.chanceStraight = 0.9f; //next segment will go forward; -> inverse chance to left/right
		path.chanceRamp = 0.25f; //when straight, roll again for a ramp
		path.chanceRoom = 0.25f; //else, roll for a room
		path.chanceFork = 0.1f; //roll again for another direction, when not a ramp
		path.chanceUp = 0.35f;
		path.useFoundations = false;
		path.usePillars = false;
		path.useRoof = false;
	}

	@Override
	public void initSpawner(TGSpawnerTileEnt spawner) {
		spawner.setParams(2, 1, 200, 2);
		spawner.addMobType(ZombieSoldier.class, 1);
	}

}
