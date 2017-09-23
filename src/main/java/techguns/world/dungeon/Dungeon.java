package techguns.world.dungeon;

import net.minecraft.world.World;

public class Dungeon {
	
	private static final int GENERATE_ATTEMPTS = 5;
	
	DungeonTemplate template;
	
	//maximum block extents
	int maxSizeX;
	int maxSizeY;
	int maxSizeZ;
	
	//number of segments, NOT blocks
	int sX;
	int sY;
	int sZ;

	public Dungeon(DungeonTemplate template) { //TODO: DungeonLayout
		this.template = template;
	}
	
	public void generate(World world, int posX, int posY, int posZ, int maxSizeX, int maxSizeY, int maxSizeZ) {
		
		this.maxSizeX = maxSizeX;
		this.maxSizeY = maxSizeY;
		this.maxSizeZ = maxSizeZ;
		
		this.sX = maxSizeX / template.sizeXZ;
		this.sY = maxSizeY / template.sizeY;
		this.sZ = maxSizeZ / template.sizeXZ;
		
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
		
		path.generateDungeon(world, posX, posY, posZ, template);
		long t_gen = System.currentTimeMillis();
		
		System.out.println(String.format("Path: %d ms, Blocks: %d ms", t_path-t_start, t_gen-t_start));
		
	}

}
