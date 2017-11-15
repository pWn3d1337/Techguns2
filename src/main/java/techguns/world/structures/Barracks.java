package techguns.world.structures;

import java.util.Random;

import net.minecraft.init.Blocks;
import net.minecraft.item.ItemDoor;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos.MutableBlockPos;
import net.minecraft.world.World;
import techguns.util.MBlock;
import techguns.world.BlockRotator;

public class Barracks extends WorldgenStructure {
	
	MBlock b_pillar = MBlockRegister.BARRACKS_WOOD_PILLAR; //TGChiselBlocks.wood_pillar;
	MBlock b_wall = MBlockRegister.BARRACKS_WOOD_WALL; //TGChiselBlocks.wood_wall;
	MBlock b_floor = MBlockRegister.BARRACKS_WOOD_FLOOR; //TGChiselBlocks.wood_floor;
	MBlock b_scaffold = MBlockRegister.BARRACKS_WOOD_SCAFFOLD; //TGChiselBlocks.wood_scaffold;
	MBlock b_roof = MBlockRegister.BARRACKS_WOOD_ROOF; //TGChiselBlocks.wood_roof;
	MBlock b_roofSlab = MBlockRegister.BARRACKS_WOOD_ROOFSLAB; //TGChiselBlocks.wood_roofSlab;
	MBlock b_stairs = MBlockRegister.WOOD_STAIRS_OAK;
	MBlock b_torch = MBlockRegister.TGLAMP;
	MBlock b_ground = MBlockRegister.GRAVEL;
	MBlock b_window = MBlockRegister.GLASS_PANE;
	
	
	public Barracks(int minX, int minY, int minZ, int maxX, int maxY, int maxZ) {
		super(minX, minY, minZ, maxX, maxY, maxZ);
	}

	@Override
	public void setBlocks(World world, int posX, int posY, int posZ, int sizeX,
			int sizeY, int sizeZ, int direction, BiomeColorType colorType, Random rnd) {
		
		MutableBlockPos p = new MutableBlockPos();
		
		EnumFacing facing = directionToFacing(direction);
		
		//----------------------
		//Size adjustments
		//-----------------------
		
		//remove 2 in direction to fit Stairs/Walkway
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
		default:
			sizeZ-=2;
			break;
		}
			
		sizeX-= ((sizeX-1) % 4);
		sizeZ-= ((sizeZ-1) % 4);
				
		
		/*  TRRRRRRRRT   - 6 - roof
		 * SSSSSSSSSSSSS - 5 - roof
		 * SWOWSWOWSWOWS - 4 - wall
		 * SWWWSWWWSWWWS - 3 - wall
		 * SSSSSSSSSSSSS - 2 - floor
		 * P   P   P   P - 1 - pillars
		 * GGGGGGGGGGGGG - 0 - ground
		 */
		
		//fixed height;
		sizeY = 7;
		
		//Outer Building
		for (int x = 0; x < sizeX; x++) {
			for (int z = 0; z < sizeZ; z++) {
				for (int y = 0; y < sizeY; y++) {
					if (y ==0) { //Ground
						world.setBlockState(p.setPos(posX+x, posY+y, posZ+z), b_ground.getState(), 2);
					}else if (y== 1) { //Pillars
						if (x % 4 == 0 && z % 4 == 0) {
							world.setBlockState(p.setPos(posX+x, posY+y, posZ+z), b_pillar.getState(), 2);
						}
					}else if( y==2) { //Floor
						if (x==0 || z==0 || x== sizeX-1 || z == sizeZ-1) {
							world.setBlockState(p.setPos(posX+x, posY+y, posZ+z), b_scaffold.getState(), 2);
						}else {
							world.setBlockState(p.setPos(posX+x, posY+y, posZ+z), b_floor.getState(), 2);		
						}
					}else if (y <= 4){ //Wall
						if (x==0 || z==0 || x== sizeX-1 || z == sizeZ-1) {
							if (x % 4 == 0 && z % 4 == 0) {
								world.setBlockState(p.setPos(posX+x, posY+y, posZ+z), b_scaffold.getState(), 2);
							}else {
								if (y==4 && (x % 4 ==2 || z % 4 == 2)) { //Window
									world.setBlockState(p.setPos(posX+x, posY+y, posZ+z), b_window.getState(), 2);
								}else{
									world.setBlockState(p.setPos(posX+x, posY+y, posZ+z), b_wall.getState(), 2);
								}
							}
						}
					}else if (y == 5) { //roof/scaffold
						if (x==0 || z==0 || x== sizeX-1 || z == sizeZ-1) {
							world.setBlockState(p.setPos(posX+x, posY+y, posZ+z), b_scaffold.getState(), 2);
						}
					}else if (y == 6) {
						if (x==0 || z==0 || x== sizeX-1 || z == sizeZ-1) {
							world.setBlockState(p.setPos(posX+x, posY+y, posZ+z), b_roofSlab.getState(), 2);
						}else {
							world.setBlockState(p.setPos(posX+x, posY+y, posZ+z), b_roof.getState(), 2);		
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
				xoffset = 0; zoffset=((sizeZ/8)*4)+2;
				dx = -1; dz =0;
				break;
			case 1:
				xoffset = ((sizeX/8)*4)+2; zoffset=0;
				dx = 0; dz = -1;
				break;
			case 2:
				xoffset = sizeX-1; zoffset=((sizeZ/8)*4)+2;
				dx = 1; dz=0;
				break;
			case 3:
			default:
				xoffset = ((sizeX/8)*4)+2; zoffset=sizeZ-1;
				dx = 0; dz=1;
				break;
		}
		
		//Door
		world.setBlockToAir(p.setPos(posX+xoffset, posY+4, posZ+zoffset));
		world.setBlockToAir(p.setPos(posX+xoffset, posY+3, posZ+zoffset));
		ItemDoor.placeDoor(world, p.setPos(posX+xoffset, posY+3, posZ+zoffset), facing.getOpposite(), Blocks.OAK_DOOR, false);
		//ItemDoor.placeDoorBlock(world, posX+xoffset, posY+3, posZ+zoffset, BlockUtils.getBlockDirMeta(BlockType.DOOR, direction), Blocks.wooden_door);

		//Lamp
		world.setBlockState(p.setPos(posX+xoffset+dx, posY+5, posZ+zoffset+dz), BlockRotator.getWithFacing(b_torch.getState(),facing.getOpposite()), 2);
		
		//Ramp
		world.setBlockState(p.setPos(posX+xoffset+dx, posY+2, posZ+zoffset+dz), BlockRotator.getWithFacing(b_stairs.getState(),facing.getOpposite()), 2);
		world.setBlockState(p.setPos(posX+xoffset+dx*2, posY+1, posZ+zoffset+dz*2), BlockRotator.getWithFacing(b_stairs.getState(),facing.getOpposite()), 2);
		
		//------------------------
		//Indoor stuff & Furniture
		//------------------------
		
		int xHalf = sizeX/2;
		int zHalf = sizeZ/2;
		
		for (int x = 1; x < sizeX-1; x++) {
			for (int z = 1; z < sizeZ-1; z++) {
				for (int y = 3; y < 6; y++) {
					if (x==1 || z==1 || x== sizeX-2 || z == sizeZ-2) {
						if (y==5 && (x % 4 ==2 || z % 4 == 2)) { //torch
							world.setBlockState(p.setPos(posX+x, posY+y, posZ+z), BlockRotator.getWithFacing(b_torch.getState(),EnumFacing.UP), 2);
						}
						if (y == 3 && (x % 4 == 0 || z % 4 == 0) && x!= xoffset && z!= zoffset) {
							
							EnumFacing chestrot = EnumFacing.EAST;
							if (z % 4==0) {
								if(x>xHalf) {
									chestrot =chestrot.getOpposite();
								}
							} else if(x%4==0) {
								chestrot=EnumFacing.SOUTH;
								if(z>zHalf) {
									chestrot =chestrot.getOpposite();
								}
							}
							world.setBlockState(p.setPos(posX+x, posY+y, posZ+z), BlockRotator.getWithFacing(Blocks.CHEST.getDefaultState(),chestrot), 2);
							
							//TODO: chest loot
							
							/*TileEntityChest tileentitychest = (TileEntityChest)world.getTileEntity(posX+x, posY+y, posZ+z);

			                if (tileentitychest != null)
			                {
			                    WeightedRandomChestContent.generateChestContents(world.rand, ChestGenHooks.getItems(TGItems.CHESTGEN_MILBASEBARRACKS, world.rand), tileentitychest, ChestGenHooks.getCount(TGItems.CHESTGEN_MILBASEBARRACKS,world.rand));
			                }*/
							
						}
					}else{
						if (x==2 || z == 2 || x== sizeX-3 || z== sizeZ-3) {		
							//Beds
							if (sizeX / 4 > 1 || sizeZ / 4 > 1) {
								if (y==3 && (x % 4 ==2 && z % 4 == 2) && x!= xoffset && z!= zoffset) {
									if (x==2) {
										world.setBlockState(p.setPos(posX+x, posY+y, posZ+z), Blocks.BED.getStateFromMeta(1), 3);
										world.setBlockState(p.setPos(posX+x-1, posY+y, posZ+z), Blocks.BED.getStateFromMeta(1+8), 3);
									}else if (x==sizeX-3) {
										world.setBlockState(p.setPos(posX+x, posY+y, posZ+z), Blocks.BED.getStateFromMeta(3), 3);
										world.setBlockState(p.setPos(posX+x+1, posY+y, posZ+z), Blocks.BED.getStateFromMeta(3+8), 3);
									}else if (z==2) {
										world.setBlockState(p.setPos(posX+x, posY+y, posZ+z), Blocks.BED.getStateFromMeta(2), 3);
										world.setBlockState(p.setPos(posX+x, posY+y, posZ+z-1), Blocks.BED.getStateFromMeta(2+8), 3);
									}else if (z==sizeZ-3) {
										world.setBlockState(p.setPos(posX+x, posY+y, posZ+z), Blocks.BED.getStateFromMeta(0), 3);
										world.setBlockState(p.setPos(posX+x, posY+y, posZ+z+1), Blocks.BED.getStateFromMeta(0+8), 3);
									}					
								}
							}
//							if (y == 3 && (x % 4 == 0 || z % 4 == 0) && x!= xoffset && z!= zoffset) {
//								world.setBlock(posX+x, posY+y, posZ+z, Blocks.chest, 0, 2);
//							}
								
						}
						//more lamps
						if (y == 5 && x % 8 == 0 && z % 8 == 0) {
							world.setBlockState(p.setPos(posX+x, posY+y, posZ+z), BlockRotator.getWithFacing(b_torch.getState(),EnumFacing.UP), 2);
						}
					}
				}
			}
		}
	}
	

}