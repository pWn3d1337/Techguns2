package techguns.world.dungeon;

import net.minecraft.world.World;

public class Dungeon {
	
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
		DungeonPath path = new DungeonPath(sX, sY, sZ, world.rand);
		path.generatePath();
		//--
		
		path.generateDungeon(world, posX, posY, posZ, template);
		
	}

}
