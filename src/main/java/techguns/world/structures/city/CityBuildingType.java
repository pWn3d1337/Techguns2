package techguns.world.structures.city;

import java.util.ArrayList;

import net.minecraft.init.Blocks;
import net.minecraft.item.ItemDoor;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos.MutableBlockPos;
import net.minecraft.world.World;
import techguns.TGBlocks;
import techguns.util.BlockUtils;
import techguns.util.MBlock;
import techguns.world.structures.MBlockRegister;
import techguns.world.structures.WorldgenStructure.BiomeColorType;

public class CityBuildingType{
	
	public static CityBuildingType building1;
	public static CityBuildingType building2;
	public static CityBuildingType building3;
	public static CityBuildingType building4;
	public static CityBuildingType building5;
	public static CityBuildingType building6;
	
	//ArrayList<MBlock> blocklist = new ArrayList<MBlock>();
	
	BuildingStyle style;
	int wall_x = 2;
	int wall_y = 5;
	int wall_z = 1;
	int wall_el = 1; //extra length
	int section_height = 4;
	int floor_height = 1;
	
	int yOffset = 0;
	
	int[][] wallPattern;
	int[][] doorPattern;
	int doorWidth = 1;
	int doorOffset = 1;
	
	//MBlock stairs;
	//MBlock stairs_2;
	MBlock ladder;
	
	MBlock lamp;
	
	boolean roofLikeFloor=false;
	int roofBorderIndex=0;
	
	static {
		
		ArrayList<MBlock> stoneWalls = new ArrayList<MBlock>();
		stoneWalls.add(MBlockRegister.STONE_BRICKS_PANEL);
		stoneWalls.add(MBlockRegister.STONE_BRICKS_SMALL);
		stoneWalls.add(MBlockRegister.COBBLESTONE_1);
		stoneWalls.add(MBlockRegister.COBBLESTONE_2);
		stoneWalls.add(MBlockRegister.COBBLESTONE_3);
		stoneWalls.add(MBlockRegister.ANDESITE_3);
		
		ArrayList<MBlock> stoneFloors = new ArrayList<MBlock>();
		stoneFloors.add(MBlockRegister.COBBLESTONE_7);
		stoneFloors.add(MBlockRegister.STONE_BRICKS_SUNKEN);
		stoneFloors.add(new MBlock(Blocks.STONE,6)); //polished andesite
		stoneFloors.add(new MBlock(Blocks.STONE,3)); //polished diorite
		stoneFloors.add(new MBlock(Blocks.STONE,2)); //polished granite
		
		
		ArrayList<MBlock> stoneBorder = new ArrayList<MBlock>();
		stoneBorder.add(MBlockRegister.ANDESITE_11);
		stoneBorder.add(MBlockRegister.ANDESITE_4);
		stoneBorder.add(MBlockRegister.COBBLESTONE_13);
		stoneBorder.add(MBlockRegister.COBBLESTONE_15);
		stoneBorder.add(MBlockRegister.ANDESITE1_0);
		
		ArrayList<MBlock> glassPanes = new ArrayList<MBlock>();
		glassPanes.add(new MBlock(Blocks.GLASS_PANE,0));//TGChiselBlocks.glass_bubble);
		
		BuildingStyle stoneBuilding = new BuildingStyle(stoneWalls, glassPanes, stoneFloors, stoneBorder);
		
		MBlock[] woodStair = new MBlock[]{new MBlock(Blocks.OAK_STAIRS, 0),MBlockRegister.BARRACKS_WOOD_SCAFFOLD};
		stoneBuilding.addStairStyle(woodStair);
		
	
		BuildingStyle factoryBuilding = new BuildingStyle(MBlockRegister.FACTORY_PLATE, new MBlock(Blocks.STAINED_GLASS_PANE,8), MBlockRegister.FACTORY_WIREFRAME);
		factoryBuilding.addStairStyle(woodStair);
		
		//BRICK BUILDING STYLE
		
		ArrayList<MBlock> brickWalls = new ArrayList<>();
		brickWalls.add(MBlockRegister.BRICKS_WIDE);
		//ArrayList<MBlock> brickFloors = new ArrayList<>();
		//brickFloors.add(new MBlock(ChiselBlocks.cobblestone,10));
		ArrayList<MBlock> brickBorders = new ArrayList<>();
		brickBorders.add(MBlockRegister.BRICKS_WEATHERED);
		
		BuildingStyle brickBuilding = new BuildingStyle(brickWalls, glassPanes, stoneFloors, brickBorders);
		brickBuilding.addStairStyle(woodStair);
				
		
		// LAB BUILDING STYLE
		ArrayList<MBlock> labWalls = new ArrayList<>();
		labWalls.add(MBlockRegister.LABORATORY_WALL);
		
		ArrayList<MBlock> labWindows = new ArrayList<>();
		labWindows.add(new MBlock(Blocks.STAINED_GLASS_PANE, 0));
		
		ArrayList<MBlock> labFloors = new ArrayList<>();
		labFloors.add(MBlockRegister.LABORATORY_FLOOR);
		
		ArrayList<MBlock> labBorders = new ArrayList<>();
		labBorders.add(MBlockRegister.LABORATORY_BORDER);
		
		ArrayList<MBlock> labDoorDecoration = new ArrayList<>();
		labDoorDecoration.add(MBlockRegister.LABORATORY_DECORATION);
		
		MBlock[] labStair = new MBlock[]{new MBlock(Blocks.QUARTZ_STAIRS,0),labWalls.get(0)};
		
		
		BuildingStyle labBuilding = new BuildingStyle(labWalls, labWindows, labFloors, labBorders, labDoorDecoration);
		labBuilding.addStairStyle(labStair);
		
		
		
		// Limestone BUILDING STYLE
		ArrayList<MBlock> limeWalls = new ArrayList<>();
		limeWalls.add(MBlockRegister.LIME_WALL);
		
		ArrayList<MBlock> limeFloors = new ArrayList<>();
		limeFloors.add(MBlockRegister.LIME_FLOOR);
		
		ArrayList<MBlock> limeBorders = new ArrayList<>();
		limeBorders.add(MBlockRegister.LIME_BORDER);
		
		ArrayList<MBlock> limeDoorDecoration = new ArrayList<>();
		limeDoorDecoration.add(MBlockRegister.LIME_DECORATION);
		
		//MBlock[] limeStair = new MBlock[]{new MBlock(ChiselBlocks.limestoneStairs[0],8,true,BlockType.STAIRS2_CHISEL),new MBlock(ChiselBlocks.limestone,1)};
		
		
		BuildingStyle limeBuilding = new BuildingStyle(limeWalls, glassPanes, limeFloors, limeBorders, limeDoorDecoration);
		limeBuilding.addStairStyle(woodStair);
		
		
		
		
		building1 = new CityBuildingType(2, 5, 1, 4, 1, stoneBuilding);
	//	building1.blocklist.add(TGChiselBlocks.stonebrick_panel); //0
	//	building1.blocklist.add(TGChiselBlocks.glass_bubble); //1
	//	building1.blocklist.add(TGChiselBlocks.cobblestone_largetile); //2		
		building1.wallPattern = new int[][] {{0,0},
										   {0,1},
										   {0,1},
										   {0,0},
										   {3,3}};		
		building1.doorPattern = new int[][] {{0,0},
										   {0,0},
										   {0,-1},
										   {0,-1},
										   {3,3}};
		
		building1.roofLikeFloor=true;
		building1.roofBorderIndex=3;
		building1.doorWidth = 1;
		building1.doorOffset = 1;
		//-----------------------
		building2 = new CityBuildingType(3, 4, 1, 3, 1, factoryBuilding);
	//	building2.blocklist.add(TGChiselBlocks.factory_wall); //0
	//	building2.blocklist.add(TGChiselBlocks.glass_bubble); //1
	//	building2.blocklist.add(TGChiselBlocks.factory_wireframe); //2		
		building2.wallPattern = new int[][] {{0,0,0},
											 {0,1,1},
											 {0,0,0},
											 {2,2,2}};		
		building2.doorPattern = new int[][] {{0, 0, 0},
											 {0,-1,-1},
											 {0,-1,-1},
											 {2, 2, 2}};
		building2.doorWidth = 2;
		building2.doorOffset = 1;
		//-----------------------
		building3 = new CityBuildingType(3, 4, 1, 3, 1, stoneBuilding);
	//	building3.blocklist.add(TGChiselBlocks.stonebrick_small); //0
//		building3.blocklist.add(new MBlock(Blocks.glass_pane, 0)); //1
	//	building3.blocklist.add(TGChiselBlocks.stonebrick_sunken); //2		
		building3.wallPattern = new int[][] {{0,0,0},
											 {0,1,1},
											 {0,0,0},
											 {3,3,3}};		
		building3.doorPattern = new int[][] {{0, 0, 0},
											 {0,-1,-1},
											 {0,-1,-1},
											 {3, 3, 3}};
		building3.roofLikeFloor=true;
		building3.roofBorderIndex=3;
		building3.doorWidth = 2;
		building3.doorOffset = 1;
		//-----------------------
		building4 = new CityBuildingType(3, 4, 1, 3, 1, brickBuilding);
//		building4.blocklist.add(new MBlock(ChiselBlocks.brickCustom,3)); //0
//		building4.blocklist.add(new MBlock(Blocks.glass_pane, 0)); //1
//		building4.blocklist.add(new MBlock(ChiselBlocks.cobblestone,10)); //2		
//		building4.blocklist.add(new MBlock(ChiselBlocks.brickCustom,2)); //3		
		building4.wallPattern = new int[][] {{0,0,0},
											 {0,1,1},
											 {0,0,0},
											 {3,3,3}};		
		building4.doorPattern = new int[][] {{0, 0, 0},
											 {0,-1,-1},
											 {0,-1,-1},
											 {3, 3, 3}};
		building4.doorWidth = 2;
		building4.doorOffset = 1;
		building4.roofLikeFloor=true;
		building4.roofBorderIndex=3;
		//-----------------------
		building5 = new CityBuildingType(3, 5, 1, 4, 1, labBuilding);
//		building5.blocklist.add(new MBlock(ChiselBlocks.laboratoryblock,2)); //0
//		building5.blocklist.add(new MBlock(ChiselBlocks.stainedGlassPane[3], 11)); //1
	//	building5.blocklist.add(new MBlock(ChiselBlocks.laboratoryblock,8)); //2		
//		building5.blocklist.add(new MBlock(ChiselBlocks.laboratoryblock,4)); //3	
//		building5.blocklist.add(new MBlock(ChiselBlocks.laboratoryblock,15)); //4	
		building5.wallPattern = new int[][] {{0,0,0},
											 {0,1,1},
											 {0,1,1},
											 {0,0,0},
											 {3,3,3}};		
		building5.doorPattern = new int[][] {{0, 0, 0},
											 {0, 4, 4},
											 {0,-1,-1},
											 {0,-1,-1},
											 {3, 3, 3}};
		building5.doorWidth = 2;
		building5.doorOffset = 1;
//		building5.stairs = new MBlock(Blocks.quartz_stairs,0,true,BlockType.STAIRS);
//		building5.stairs_2 = building5.blocklist.get(0);
		building5.roofLikeFloor=true;
		building5.roofBorderIndex=3;
		
		//-----------------------
		building6 = new CityBuildingType(3, 4, 1, 3, 1, limeBuilding);
//		building6.blocklist.add(new MBlock(ChiselBlocks.limestone,5)); //0
//		building6.blocklist.add(new MBlock(ChiselBlocks.stainedGlassPane[3], 11)); //1
//		building6.blocklist.add(new MBlock(ChiselBlocks.limestone,13)); //2		
//		building6.blocklist.add(new MBlock(ChiselBlocks.limestone,9)); //3	
		building6.wallPattern = new int[][] {{0,0,0},
											 {0,1,1},
											 {0,0,0},
											 {3,3,3}};		
		building6.doorPattern = new int[][] {{0, 0, 0},
											 {0,-1,-1},
											 {0,-1,-1},
											 {3, 3, 3}};
		building6.doorWidth = 2;
		building6.doorOffset = 1;
	//	building6.stairs = new MBlock(ChiselBlocks.limestoneStairs[0],8,true,BlockType.STAIRS2_CHISEL);
	//	building6.stairs_2 = new MBlock(ChiselBlocks.limestone,1);
		building6.roofLikeFloor=true;
		building6.roofBorderIndex=3;
	}


	public CityBuildingType(int wall_x, int wall_y, int wall_z,
			int section_height, int floor_height, BuildingStyle style) {
		super();
		this.wall_x = wall_x;
		this.wall_y = wall_y;
		this.wall_z = wall_z;
		this.section_height = section_height;
		this.floor_height = floor_height;
		this.yOffset = 0;
		
		this.style = style;
		
//		this.stairs = new MBlock(Blocks.oak_stairs, 0, true, BlockType.STAIRS);
//		this.stairs_2 = TGChiselBlocks.wood_scaffold;
		this.ladder = new MBlock(TGBlocks.LADDER_0, 0);//, true, BlockType.TG);
		this.lamp = new MBlock(TGBlocks.LAMP_0, 1);
	}

	public void setBlocks(World world, int posX, int posY, int posZ, int sizeX,
			int sizeY, int sizeZ, int direction, BiomeColorType colorType) {
		
		//roll a variation
		ArrayList<MBlock> blocklist = this.style.rollVariation();
		MBlock[] stairType = this.style.rollStairVariation();
		MBlock stairs = stairType[0];
		MBlock stairs_2 = stairType[1];
		
		
		int stairsdir = direction;
		if (((direction == 0 || direction == 2) && (sizeX-2 < wall_y+1)) || 
			((direction == 1 || direction == 3) && (sizeZ-2 < wall_y+1))) {
			stairsdir = (stairsdir+1) % 4;
		}

		int segX = (sizeX-wall_el)/wall_x; //number of segments X;	
		int sx = (segX*wall_x)+wall_el;
		
		int segZ = (sizeZ-wall_el)/wall_x; //number of segments Z;	
		int sz = (segZ*wall_x)+wall_el;
		
		int segY = ((int)(sizeY/wall_y)); 
		int sy = segY*wall_y;
		
		boolean isSmall = (Math.max(sx-2, sz-2) < wall_y+1) || (Math.min(sx, sz) < 4);
		
		for (int i = 0; i < segY; i++) {
			int y = posY+yOffset+(i*wall_y);
			placeFloor(world, posX+1, y, posZ+1, sx-2, floor_height, sz-2, direction, colorType, blocklist);
			if (i >= 1) placeLamps(world, posX+1, y-1, posZ+1, sx-2, 1, sz-2, direction, colorType);
			
			placeWall(world, posX, y, posZ, segX, 1, 0, 0, colorType, (i==0 && direction==0), blocklist);
			placeWall(world, posX+sx-1, y, posZ, segZ, 0, 1, 1, colorType, (i==0 && direction==1), blocklist);
			placeWall(world, posX+sx-1, y, posZ+sz-1, segX, -1, 0, 2, colorType, (i == 0 && direction==2), blocklist);
			placeWall(world, posX, y, posZ+sz-1, segZ, 0, -1, 3, colorType, (i == 0 && direction==3), blocklist);
		}
		//Roof
		int y2 = (posY+yOffset+(segY*wall_y));
		
		if(!roofLikeFloor){
			placeFloor(world, posX, y2, posZ, sx, 1, sz, direction, colorType, blocklist);
		} else {
			placeFloor(world, posX+1, y2, posZ+1, sx-2, 1, sz-2, direction, colorType, blocklist);
		}
		placeLamps(world, posX+1, y2-1, posZ+1, sx-2, 1, sz-2, direction, colorType);
		
		
		MutableBlockPos p = new MutableBlockPos();
		//Roof Wall
		MBlock block = blocklist.get(0);
		MBlock block2 = blocklist.get(roofBorderIndex);
		for (int i = 0; i < sx; i++) {
			for (int j = 0; j < sz; j++) {
				if (i==0 || i == sx-1 || j == 0 || j == sz-1) {
					block.setBlock(world, p.setPos(posX+i, y2+1, posZ+j), 0);
					if(roofLikeFloor){
						block2.setBlock(world, p.setPos(posX+i, y2, posZ+j), 0);
					}
				}
			}
		}
	
		//Stairs
		int dx = 0,dz = 0, px = 0, pz = 0;
		switch (stairsdir) {
			case 0: dx=0; dz=-1; px = posX+sx-2; pz = posZ+sz-2; break;
			case 1: dx=1; dz=0; px = posX+1; pz = posZ+sz-2; break;
			case 2: dx=0; dz =1; px = posX+1; pz = posZ+1; break;
			case 3: dx=-1; dz =0; px = posX+sx-2; pz = posZ+1; break;
		}
		
		for (int i = 0; i < segY-1; i++) {
			int y = posY+yOffset+(i*wall_y);
			if (!isSmall) { //Place Stairs
				if ((i % 2) == 1) {
					Staircase.placeStaircase(world, px+dx, y+1, pz+dz, wall_y, (stairsdir+2)%4, true, colorType, stairs, stairs_2);
				}else {
					Staircase.placeStaircase(world, px, y+1, pz, wall_y, (stairsdir+2)%4, false, colorType, stairs, stairs_2);
				}	
			}else { //Place Ladder
				for (int j = 0; j < wall_y; j++) {
					if (j < wall_y-1) {
						p.setPos(px-dx, y+i+1, pz-dz);
						block.setBlock(world, p, 0);
					}
					p.setPos(px, y+j+1, pz);
					ladder.setBlock(world, p, (stairsdir-1)%4);
				}
			}
		}
		//Roof Ladder
		for (int i = 0; i < wall_y; i++) {
			int y = posY+yOffset+((segY-1)*wall_y);
			if (i < wall_y-1) {
				p.setPos(px-dx, y+i+1, pz-dz);
				block.setBlock(world, p, 0);
			}
			p.setPos(px, y+i+1, pz);
			ladder.setBlock(world, p, (stairsdir-1)%4);
		}
		//world.setBlock(px, y2+1, pz, Blocks.TRAPDOOR);
		world.setBlockState(p.setPos(px, y2+1, pz), Blocks.TRAPDOOR.getDefaultState());
	}
	
	public void placeLamps(World world, int posX, int posY, int posZ, int sizeX,
			int sizeY, int sizeZ, int direction, BiomeColorType colorType) {
		int density = 3;

		MutableBlockPos p = new MutableBlockPos();
		
		int ix = (sizeX-1) % density;
		int iz = (sizeZ-1) % density;
		for (int x = posX + (ix/2); x < (posX+sizeX-(1-(ix/2))); x+=(density)) {
			for (int z = posZ + (iz/2); z < (posZ+sizeZ-(1-(iz/2))); z+=(density)) {
				lamp.setBlock(world, p.setPos(x, posY, z), 0);
			}
		}
	}



	public void placeWall(World world, int posX, int posY, int posZ, int segments, int dx, int dz, int direction, BiomeColorType colorType, boolean placeDoor, ArrayList<MBlock> blocklist) {		
		int mid = (segments/2);
		
		MutableBlockPos p = new MutableBlockPos();
		
		for (int i = 0; i < segments; i++) {
			if (placeDoor && i == mid) { //Place door segment
				for (int j =0; j < wall_x; j++) {
					for (int l = 1; l <= wall_y; l++) {
						int b = doorPattern[wall_y-l][j];
						int offset = (i*wall_x)+j;
						MBlock block;
						if (b >= 0) block = blocklist.get(b);
						else block = new MBlock(Blocks.AIR, 0);
						block.setBlock(world, p.setPos(posX+(offset*dx), posY+l-1, posZ+(offset*dz)), direction);
					}
				}
				int offset = (i*wall_x)+doorOffset;
				for (int k = 0; k < doorWidth; k++) {
					//ItemDoor.placeDoorBlock(world, posX+((offset+k)*dx), posY+1, posZ+((offset+k)*dz), BlockUtils.getBlockDirMeta(BlockType.DOOR, (direction+3)%4), Blocks.wooden_door);
					
					EnumFacing doorFacing = EnumFacing.HORIZONTALS[(direction+3)%4];
					ItemDoor.placeDoor(world, p.setPos(posX+((offset+k)*dx), posY+1, posZ+((offset+k)*dz)), doorFacing, Blocks.OAK_DOOR, false);
				}
				}else { //Place normal Wall segment
				for (int j =0; j < wall_x; j++) {
					for (int l = 1; l <= wall_y; l++) {
						MBlock block = blocklist.get(wallPattern[wall_y-l][j]);
						int offset = (i*wall_x)+j;
						block.setBlock(world, p.setPos(posX+(offset*dx), posY+l-1, posZ+(offset*dz)), direction);
					}
				}
			}
		}
	}
	
	public void placeFloor(World world, int posX, int posY, int posZ, int sizeX,
			int sizeY, int sizeZ, int direction, BiomeColorType colorType, ArrayList<MBlock> blocklist) {
		MBlock block = blocklist.get(2);
		MutableBlockPos p = new MutableBlockPos();
		for (int x = posX; x < posX+sizeX; x++) {
			for (int z = posZ; z < posZ+sizeZ; z++) {
				block.setBlock(world, p.setPos(x, posY, z), 0);
			}
		}
	}		
			
}

