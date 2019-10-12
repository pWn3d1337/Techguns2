package techguns.world.dungeon.presets;

import java.util.Random;

import techguns.tileentities.TGSpawnerTileEnt;
import techguns.world.dungeon.DungeonSegment;
import techguns.world.dungeon.IDungeonPath;
import techguns.world.dungeon.MazeDungeonPath;
import techguns.world.dungeon.MazeDungeonPath.PathSegment;
import techguns.world.dungeon.TemplateSegment.SegmentType;

public interface IDungeonPreset {
	
	//Dungeon Presets
	public final static IDungeonPreset PRESET_TECHFORTRESS = new PresetTechFortress();
	public final static IDungeonPreset PRESET_CASTLE = new PresetCastle();
	public final static IDungeonPreset PRESET_NETHER = new PresetNetherDungeonUnderground();
	

	public DungeonSegment getSegment(SegmentType type, int y, int yMin, int yMax, boolean isSegmentAbove, boolean isSegmentBelow, int seed);
	
	public int getSizeXZ();
	public int getSizeY();
	
	//NPC stuff
	
	/**
	 * NPC spawners per number of dungeon segments.
	 */
	public default float getSpawnDensity() {
		return 0.1f;
	}
	
	/**
	 * The floor height of the dungeon segments
	 */
	public default int getSpawnYOffset(PathSegment seg) {
		return 1;
	}
	
	public default IDungeonPath getDungeonPath(int sX, int sY, int sZ, Random rand) {
		return new MazeDungeonPath(sX, sY, sZ, rand);
	}
	
	public void initDungeonPath(IDungeonPath path);

	public void initSpawner(TGSpawnerTileEnt spawner);
}
