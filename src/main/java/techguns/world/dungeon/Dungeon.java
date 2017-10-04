package techguns.world.dungeon;

import net.minecraft.world.World;
import techguns.world.dungeon.presets.IDungeonPreset;

public class Dungeon {
	
	private static final int GENERATE_ATTEMPTS = 5;
	
	IDungeonPreset preset;
	
	//maximum block extents
	int maxSizeX;
	int maxSizeY;
	int maxSizeZ;
	
	//number of segments, NOT blocks
	int sX;
	int sY;
	int sZ;

	public Dungeon(IDungeonPreset preset) { //TODO: DungeonLayout
		this.preset = preset;
	}
	
	public void generate(World world, int posX, int posY, int posZ, int maxSizeX, int maxSizeY, int maxSizeZ) {
		
		this.maxSizeX = maxSizeX;
		this.maxSizeY = maxSizeY;
		this.maxSizeZ = maxSizeZ;
		
		this.sX = maxSizeX / preset.getSizeXZ();
		this.sY = maxSizeY / preset.getSizeY();
		this.sZ = maxSizeZ / preset.getSizeXZ();
		
		//-- create path --
		
		long t_start = System.currentTimeMillis();
		
		DungeonPath path = null;
		for (int i = 0; i < GENERATE_ATTEMPTS; i++) {			
			DungeonPath p = new DungeonPath(sX, sY, sZ, world.rand);
			p.generatePath();
			if (path == null || p.getNumSegments() > path.getNumSegments()) {
				path = p;
			}
		}
		long t_path = System.currentTimeMillis();
		//--
		
		path.generateDungeon(world, posX, posY, posZ, preset);
		long t_gen = System.currentTimeMillis();
		
		System.out.println(String.format("Path: %d ms, Blocks: %d ms", t_path-t_start, t_gen-t_start));
		
	}

}
