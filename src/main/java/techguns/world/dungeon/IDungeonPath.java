package techguns.world.dungeon;

import java.util.Random;

import net.minecraft.world.World;
import techguns.world.dungeon.MazeDungeonPath.PathSegment;
import techguns.world.dungeon.presets.IDungeonPreset;

public interface IDungeonPath {

	public int getNumSegments();

	public void generatePath();

	public void generateSegment(int x, int y, int z, int dir, PathSegment prev);

	public void generateDungeon(World world, int posX, int posY, int posZ, IDungeonPreset preset);

	public void generateNPCSpawners(World world, int posX, int posY, int posZ, IDungeonPreset preset);
}