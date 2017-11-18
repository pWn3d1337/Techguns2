package techguns.world.structures;

import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos.MutableBlockPos;
import net.minecraft.world.World;
import techguns.TGBlocks;
import techguns.items.ItemTGDoor2x1;
import techguns.util.MBlock;
import techguns.world.BlockRotator;
import techguns.world.EnumLootType;

public class Bunker extends WorldgenStructure {

	 MBlock groundBlock = new MBlock(TGBlocks.CONCRETE,1);//new MBlock(TGBlocks.CONCRETE, 5);
	 MBlock stairsblock = new MBlock(Blocks.STONE_STAIRS, 0);
	 MBlock wallBlock = new MBlock(TGBlocks.CONCRETE, 0);//new MBlock(TGBlocks.CONCRETE, 6);
	 MBlock roofBlock = new MBlock(TGBlocks.CONCRETE, 2);//new MBlock(TGBlocks.CONCRETE, 3);
	 MBlock sandbags = new MBlock(TGBlocks.SANDBAGS, 0);
	 MBlock ladder = new MBlock(TGBlocks.LADDER_0, 0);
	 MBlock[] crateBlocks = MBlockRegister.crates; //new MBlock[] { new MBlock(Blocks.PLANKS,0), new MBlock(Blocks.PLANKS,1) } ;//TGChiselBlocks.factory_box, TGChiselBlocks.woodenCrate};
	 
	 MBlock lantern = new MBlock(Blocks.GLOWSTONE,0);//new MBlock(TGBlocks.LAMP_0, 12);
	 MBlock lamps = new MBlock(TGBlocks.LAMP_0, 0);
	 
	 int type = 0;
	
	public Bunker(int minX, int minY, int minZ, int maxX, int maxY, int maxZ, int type) {
		super(minX, minY, minZ, maxX, maxY, maxZ);
		this.type = type;
		this.lootTier=EnumLootType.TIER1;
	}

	@Override
	public void setBlocks(World world, int posX, int posY, int posZ, int sizeX,
			int sizeY, int sizeZ, int direction, BiomeColorType colorType, Random rnd) {
		
			MutableBlockPos p = new MutableBlockPos();
		
			//Adjust direction from size
			if ((sizeX <= 4) && (sizeZ >= 5)) {
				if (direction == 0 || direction == 2) {
					direction = (direction+1) % 4;
				}
			}else if ((sizeZ <= 4) && (sizeX >= 5)) {
				if (direction == 1 || direction == 3) {
					direction = (direction+1) % 4;
				}
			}
		
			switch (direction) {
			case 0:
				posX+=2;
				sizeX-=2;
				break;
			case 1:
				posZ+=2;
				sizeZ-=2;
				break;
			case 2:
				sizeX-=2;
				break;
			case 3:
				sizeZ-=2;
				break;
		 }
			
		EnumFacing facing = directionToFacing(direction);

			
		 
		 /*  
		  * 2  WWWWWWWWW
		  * 1  W   W   W
		  * 0 BBWWWWWWWWWBB
		  *-1 BBGGGGGGGGGBB
		  */
	 
		 for (int y = -1; y < sizeY; y++) {
			for (int x = 0; x < sizeX; x++) {
				for (int z = 0; z < sizeZ; z++) {
					if (y == -1) {
						//setBlocks groundblock
						world.setBlockState(p.setPos(posX+x, posY+y, posZ+z), groundBlock.getState(), 2);
					}else if (y == 0) {
						if (x== 0 || x == sizeX-1 || z==0 || z == sizeZ-1) {
							//setBlocks wallblock
							world.setBlockState(p.setPos(posX+x, posY+y, posZ+z), wallBlock.getState(), 2);
						} else {
							world.setBlockToAir(p.setPos(posX+x, posY+y, posZ+z));
						}
					}else if (y == 1) {
						if (type == 2 || type == 3) { //no windows
							if (x== 0 || x == sizeX-1 || z==0 || z == sizeZ-1) {
								//setBlocks wallblock
								world.setBlockState(p.setPos(posX+x, posY+y, posZ+z), wallBlock.getState(), 2);
							}				
						}else {
							if (((x== 0 || x == sizeX-1) && (z==0 || z == sizeZ-1 || z % 3 == 0)) ||
									((x== 0 || x == sizeX-1 || x % 3 == 0) && (z==0 || z == sizeZ-1 ))) {
								//setBlocks wallblock 
								world.setBlockState(p.setPos(posX+x, posY+y, posZ+z), wallBlock.getState(), 2);
							}
						}
					}else if (y == 2) {
						if (x== 0 || x == sizeX-1 || z==0 || z == sizeZ-1) {
							//setBlocks wallblock
							world.setBlockState(p.setPos(posX+x, posY+y, posZ+z), wallBlock.getState(), 2);
						}else {
							//setBlocks roofblock
							world.setBlockState(p.setPos(posX+x, posY+y, posZ+z), roofBlock.getState(), 2);
						}
					}else if (y == 3 && (type == 1 || type == 3)) { //Sandbag roof;
						if (x== 0 || x == sizeX-1 || z==0 || z == sizeZ-1) {
							//setBlocks wallblock
							world.setBlockState(p.setPos(posX+x, posY+y, posZ+z), sandbags.getState(), 2);
						}
					}
					
				}
			}
		}
	 
		//---------------------
		//Entrance
		//---------------------
		
		int xoffset, zoffset; //Door/center positions
		int dx, dz;
		
		switch (direction) {
			case 0:
				xoffset = 0; zoffset=sizeZ/2;
				dx = -1; dz =0;
				break;
			case 1:
				xoffset = sizeX/2; zoffset=0;
				dx = 0; dz = -1;
				break;
			case 2:
				xoffset = sizeX-1; zoffset=sizeZ/2;
				dx = 1; dz=0;
				break;
			case 3:
			default:
				xoffset = sizeX/2; zoffset=sizeZ-1;
				dx = 0; dz=1;
				break;
		}
		
		//Door
		world.setBlockToAir(p.setPos(posX+xoffset, posY+1, posZ+zoffset));
		world.setBlockToAir(p.setPos(posX+xoffset, posY+0, posZ+zoffset));
		
		//ItemDoor.placeDoorBlock(world, posX+xoffset, posY+0, posZ+zoffset, BlockUtils.getBlockDirMeta(BlockType.DOOR, direction), TGBlocks.door01);
		ItemTGDoor2x1.placeDoor(world, p.setPos(posX+xoffset, posY+0, posZ+zoffset), facing, TGBlocks.BUNKER_DOOR, true);
		
		//Doorway
		world.setBlockState(p.setPos(posX+xoffset+((dx == 0) ? 1 : 0), posY+0, posZ+zoffset+((dz == 0) ? 1 : 0)), wallBlock.getState(), 2);
		world.setBlockState(p.setPos(posX+xoffset+((dx == 0) ? 1 : 0), posY+1, posZ+zoffset+((dz == 0) ? 1 : 0)), wallBlock.getState(), 2);
		world.setBlockState(p.setPos(posX+xoffset+((dx == 0) ? -1 : 0), posY+0, posZ+zoffset+((dz == 0) ? -1 : 0)), wallBlock.getState(), 2);
		world.setBlockState(p.setPos(posX+xoffset+((dx == 0) ? -1 : 0), posY+1, posZ+zoffset+((dz == 0) ? -1 : 0)), wallBlock.getState(), 2);
		
		world.setBlockState(p.setPos(posX+xoffset+((dx == 0) ? 1 : dx), posY-1, posZ+zoffset+((dz == 0) ? 1 : dz)), wallBlock.getState(), 2);
		world.setBlockState(p.setPos(posX+xoffset+((dx == 0) ? -1 : dx), posY-1, posZ+zoffset+((dz == 0) ? -1 : dz)), wallBlock.getState(), 2);
		world.setBlockState(p.setPos(posX+xoffset+((dx == 0) ? 1 : dx*2), posY-1, posZ+zoffset+((dz == 0) ? 1 : dz*2)), wallBlock.getState(), 2);
		world.setBlockState(p.setPos(posX+xoffset+((dx == 0) ? -1 : dx*2), posY-1, posZ+zoffset+((dz == 0) ? -1 : dz*2)), wallBlock.getState(), 2);		
		world.setBlockState(p.setPos(posX+xoffset+((dx == 0) ? 1 : dx), posY+0, posZ+zoffset+((dz == 0) ? 1 : dz)), wallBlock.getState(), 2);
		world.setBlockState(p.setPos(posX+xoffset+((dx == 0) ? -1 : dx), posY+0, posZ+zoffset+((dz == 0) ? -1 : dz)), wallBlock.getState(), 2);
		world.setBlockState(p.setPos(posX+xoffset+((dx == 0) ? 1 : dx*2), posY+0, posZ+zoffset+((dz == 0) ? 1 : dz*2)), wallBlock.getState(), 2);
		world.setBlockState(p.setPos(posX+xoffset+((dx == 0) ? -1 : dx*2), posY+0, posZ+zoffset+((dz == 0) ? -1 : dz*2)), wallBlock.getState(), 2);
		
		world.setBlockState(p.setPos(posX+xoffset+((dx == 0) ? 1 : dx), posY+2, posZ+zoffset+((dz == 0) ? 1 : dz)), BlockRotator.getWithFacing(lamps.getState(), facing.getOpposite()), 2);
		world.setBlockState(p.setPos(posX+xoffset+((dx == 0) ? -1 : dx), posY+2, posZ+zoffset+((dz == 0) ? -1 : dz)),BlockRotator.getWithFacing(lamps.getState(), facing.getOpposite()), 2);
		
		world.setBlockState(p.setPos(posX+xoffset, posY-1, posZ+zoffset), groundBlock.getState(), 2);
		world.setBlockState(p.setPos(posX+xoffset+dx, posY-1, posZ+zoffset+dz), groundBlock.getState(), 2);
		world.setBlockToAir(p.setPos(posX+xoffset+dx, posY+0, posZ+zoffset+dz));
		world.setBlockToAir(p.setPos(posX+xoffset+dx, posY+1, posZ+zoffset+dz));
		
		//Stairs
		world.setBlockState(p.setPos(posX+xoffset+dx*2, posY+0, posZ+zoffset+dz*2), BlockRotator.getWithFacing(stairsblock.getState(), facing), 2);
		world.setBlockToAir(p.setPos(posX+xoffset+dx*2, posY+1, posZ+zoffset+dz*2));
		
		
		//Inner objects
		//Crates
		if (type == 2 || type == 3) {
			//Lamp
			world.setBlockState(p.setPos(posX+xoffset-dx, posY+1, posZ+zoffset-dz), BlockRotator.getWithFacing(lamps.getState(), EnumFacing.UP), 2);
//			if (type == 2) {
//				setCrateBlocks(world, posX-dx+1, posY, posZ-dz+1, sizeX-1-Math.abs(dx), 2, sizeZ-1-Math.abs(dz));
//			}else {
				setCrateBlocks(world, p, posX+2, posY, posZ+2, sizeX-4, 2, sizeZ-4);
			//}
			
			
		}else {
			//Lamp
			world.setBlockState(p.setPos(posX+(sizeX/2), posY+1, posZ+(sizeZ/2)), BlockRotator.getWithFacing(lamps.getState(), EnumFacing.UP), 2);
		
		}
		
		
		
		//Ladder
		if (type == 1 || type == 3) {
			int meta = 0;
			EnumFacing ladderFacing=EnumFacing.NORTH;
			switch (direction) {
				case 0: //-X
					xoffset = sizeX-2;
					zoffset = sizeZ-2;
					dx = 1; dz=0;
					meta = 2;
					ladderFacing=EnumFacing.WEST;
					break;
				case 1: //-Z
					zoffset = sizeZ-2;
					xoffset = 1;
					dx = 0; dz = 1;
					meta = 0;
					ladderFacing=EnumFacing.NORTH;
					break;
				case 2: //+X
					xoffset = 1;
					zoffset = 1;
					dx = -1; dz = 0;
					meta = 3;
					ladderFacing=EnumFacing.EAST;
					break;
				case 3: //+Z
					xoffset = sizeX-2;
					zoffset = 1;
					dx = 0; dz = -1;
					meta = 1;
					ladderFacing=EnumFacing.SOUTH;
					break;
			}
			//meta = (meta+2) % 4;
			
			world.setBlockToAir(p.setPos(posX+xoffset, posY+2, posZ+zoffset));
			world.setBlockState(p.setPos(posX+xoffset+dx, posY+1, posZ+zoffset+dz), wallBlock.getState(), 2);
			
			/*world.setBlockState(p.setPos(posX+xoffset, posY+0, posZ+zoffset), ladder.block, ladder.meta+meta, 2);
			world.setBlockState(p.setPos(posX+xoffset, posY+1, posZ+zoffset), ladder.block, ladder.meta+meta, 2);
			world.setBlockState(p.setPos(posX+xoffset, posY+2, posZ+zoffset), ladder.block, ladder.meta+meta, 2);*/
			world.setBlockState(p.setPos(posX+xoffset, posY+0, posZ+zoffset), BlockRotator.getWithFacing(ladder.getState(),ladderFacing), 2);
			world.setBlockState(p.setPos(posX+xoffset, posY+1, posZ+zoffset), BlockRotator.getWithFacing(ladder.getState(),ladderFacing), 2);
			world.setBlockState(p.setPos(posX+xoffset, posY+2, posZ+zoffset), BlockRotator.getWithFacing(ladder.getState(),ladderFacing), 2);
		}
		
	}

	private void setCrateBlocks(World world, MutableBlockPos p, int posX, int posY, int posZ, int sizeX,
			int sizeY, int sizeZ) {
		Random r = world.rand;
		for (int x = 0; x < sizeX; x++) {
			for (int z=0; z <sizeZ; z++) {
				
				float chestroll = r.nextFloat();
				Block chest=null;
				
				/*if (chestroll<=0.05f){
					chest = TGBlocks.tgchest;
				} else if ( chestroll<=0.1f){
					chest = TGBlocks.tgchest_weapon;
				} */
				if (chestroll <=0.1f) {
					chest = Blocks.CHEST;
				}
				
				
				if (chest==null){
					
					for (int y = 0; y < sizeY; y++) {
						if (!world.isAirBlock(p.setPos(posX+x, posY+y-1, posZ+z)) && r.nextFloat() > 0.5) {
							int index = r.nextInt(crateBlocks.length);
							world.setBlockState(p.setPos(posX+x, posY+y, posZ+z), crateBlocks[index].getState(), 2);
						}
					}
				} else {
					
					//TODO CHEST LOOT
					int meta = r.nextInt(4);
					//MBlock TGChest = new MBlock(chest,meta);
					world.setBlockState(p.setPos(posX+x, posY, posZ+z), BlockRotator.getRotatedHorizontal(chest.getDefaultState(),meta));
					//TGChest.setBlock(world, posX+x, posY, posZ+z, 0, this.lootTier);
					
				}
			}
		}
	}

}
