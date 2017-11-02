package techguns.world.structures;

import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.util.math.BlockPos.MutableBlockPos;
import net.minecraft.world.World;
import techguns.TGBlocks;
import techguns.blocks.EnumCamoNetType;
import techguns.blocks.EnumLampType;
import techguns.util.BlockUtils;
import techguns.util.MBlock;

public class CampProps extends WorldgenStructure {
	
	MBlock groundBlock = new MBlock(Blocks.GRAVEL, 0);
	IBlockState groundstate = groundBlock.getState();
	
	MBlock[] crateBlocks = MBlockRegister.crates; //new MBlock[] { new MBlock(Blocks.PLANKS,0), new MBlock(Blocks.PLANKS,1) }; //TGChiselBlocks.factory_box, TGChiselBlocks.woodenCrate};
	
	MBlock sandbags = new MBlock(TGBlocks.SANDBAGS, 0);
	IBlockState sandbagsState = sandbags.getState();
	
	IBlockState lampstate = TGBlocks.LAMP_0.getDefaultState().withProperty(TGBlocks.LAMP_0.LAMP_TYPE, EnumLampType.YELLOW_LANTERN);
	
	int variant = -1;

	public CampProps(int minX, int minY, int minZ, int maxX, int maxY, int maxZ) {
		super(minX, minY, minZ, maxX, maxY, maxZ);
		this.lootTier=2;
	}
	
	public CampProps(int minX, int minY, int minZ, int maxX, int maxY, int maxZ, int variant) {
		this(minX, minY, minZ, maxX, maxY, maxZ);
		this.variant = variant;
		this.lootTier=2;
	}

	@Override
	public void setBlocks(World world, int posX, int posY, int posZ, int sizeX,
			int sizeY, int sizeZ, int direction, BiomeColorType colorType, Random rnd) {
		
		EnumCamoNetType camoMeta;
		switch (colorType) {
		case DESERT:
			camoMeta = EnumCamoNetType.DESERT;
			break;
		case SNOW:
			camoMeta = EnumCamoNetType.SNOW;
			break;
		case WOODLAND:
		default:
			camoMeta = EnumCamoNetType.WOOD;
			break;	
		}

		if (this.variant == -1) variant = world.rand.nextInt(2);
		
		switch (variant) {
			case 0: //pile of crates
		    default:
				setCrateBlocks(world, posX, posY, posZ, sizeX, sizeY, sizeZ,rnd);
				break;
			case 1: //crate + roof
				setRoofBlocks(world, posX, posY, posZ, sizeX, sizeY, sizeZ, camoMeta);
				setCrateBlocks(world, posX+1, posY, posZ+1, sizeX-2, sizeY-2, sizeZ-2,rnd);				
				break;
			case 2: //Sandbags
				setSandbagBlocks(world, posX, posY, posZ, sizeX, sizeY, sizeZ, direction);
				break;
			case 3: //Sandbags + roof
				setRoofBlocks(world, posX, posY+1, posZ, sizeX, sizeY-1, sizeZ, camoMeta);
				setSandbagBlocks(world, posX, posY, posZ, sizeX, sizeY, sizeZ, direction);
				break;
			case 4: //row of sandbags
				setSandbagRow(world, posX, posY, posZ, sizeX, sizeY, sizeZ, direction);
				break;
			case 5: //Sandbags + roof
				setRoofBlocks(world, posX, posY+1, posZ, sizeX, sizeY-1, sizeZ, camoMeta);
				setSandbagBlocks(world, posX, posY, posZ, sizeX, sizeY, sizeZ, direction);
				setCrateBlocks(world, posX+1, posY, posZ+1, sizeX-2, sizeY-2, sizeZ-2,rnd);				
				break;
		}
		
	}
	
	private void setSandbagRow(World world, int px, int py, int pz,
			int sizeX, int sizeY, int sizeZ, int direction) {

		MutableBlockPos pos = new MutableBlockPos();

		int x = 0;
		int z = 0;
		int i = 1;
		for (int y = 1; y < sizeY; y++) {
			if (sizeX > sizeZ) {
				z = sizeZ/2;
				for (x = i; x < sizeX-i; x++) {
					world.setBlockState(pos.setPos(px+x, py+y, pz+z), sandbagsState, 2);
				}
			}else {
				x = sizeX/2;
				for (z = i; z < sizeZ-i; z++) {
					world.setBlockState(pos.setPos(px+x, py+y, pz+z),sandbagsState, 2);
				}
			}
			i++;
		}
	}

	private void setSandbagBlocks(World world, int posX, int posY, int posZ,
			int sizeX, int sizeY, int sizeZ, int direction) {
		
		MutableBlockPos p = new MutableBlockPos();
		
	//	for (int y = 0; y < 2; y++) {
			for (int x = 0; x < sizeX; x++) {
				for (int z = 0; z < sizeZ; z++ ) {
			//		if (y == 0) {
			//			world.setBlock(posX+x, posY+y, posZ+z, groundBlock.block, groundBlock.meta, 2);
			//		}else if (y == 1) {
						if (x== 0 || x == sizeX-1 || z==0 || z == sizeZ-1) {
							world.setBlockState(p.setPos(posX+x, posY+1, posZ+z), sandbagsState, 2);
						}
			//		}
				}
			}
		//}
			
			
			int xoffset, zoffset; //Door/center positions
			int dx, dz;
			
			switch (direction) {
				case 0:
					xoffset = 0; zoffset=(sizeZ/2);
					break;
				case 1:
					xoffset = (sizeX/2); zoffset=0;
					break;
				case 2:
					xoffset = sizeX-1; zoffset=(sizeZ/2);
					break;
				case 3:
				default:
					xoffset = (sizeX/2); zoffset=sizeZ-1;
					break;
			}
			
			//opening
			world.setBlockToAir(p.setPos(posX+xoffset, posY+1, posZ+zoffset));
		
	}

	private void setRoofBlocks(World world, int posX, int posY, int posZ,
			int sizeX, int sizeY, int sizeZ, EnumCamoNetType camo) {
		
		MutableBlockPos p = new MutableBlockPos();
		BlockUtils.fillBlocks(world, p.setPos(posX, posY+1, posZ), 1, sizeY-2, 1, Blocks.OAK_FENCE.getDefaultState());
		BlockUtils.fillBlocks(world, p.setPos(posX+sizeX-1, posY+1, posZ), 1, sizeY-2, 1, Blocks.OAK_FENCE.getDefaultState());
		BlockUtils.fillBlocks(world, p.setPos(posX, posY+1, posZ+sizeZ-1), 1, sizeY-2, 1, Blocks.OAK_FENCE.getDefaultState());
		BlockUtils.fillBlocks(world, p.setPos(posX+sizeX-1, posY+1, posZ+sizeZ-1), 1, sizeY-2, 1, Blocks.OAK_FENCE.getDefaultState());
		BlockUtils.fillBlocks(world, p.setPos(posX, posY+sizeY-1, posZ), sizeX, 1, sizeZ, TGBlocks.CAMONET_TOP.getDefaultState().withProperty(TGBlocks.CAMONET_TOP.TYPE, camo));
	}

	public void setCrateBlocks(World world, int posX, int posY, int posZ, int sizeX,
			int sizeY, int sizeZ, Random rnd) {

		MutableBlockPos p = new MutableBlockPos();
		
//		boolean placeLamp = false;
//		if (((sizeX+sizeZ)/2.0f)/6.0f > r.nextFloat()) placeLamp = true;
		
		for (int x = 0; x < sizeX; x++) {
			for (int z=0; z <sizeZ; z++) {
				world.setBlockState(p.setPos(posX+x, posY, posZ+z), groundstate, 2);
				
				//roll for special chest
				float chestroll = rnd.nextFloat();
				Block chest=null;
				
				/*if (chestroll<=0.05f){
					chest = TGBlocks.tgchest;
				} else if ( chestroll<=0.1f){
					chest = TGBlocks.tgchest_weapon;
				} */
				
				
				if (chest==null){
				
					for (int y = 1; y < sizeY; y++) {
						if (!isFreeSpace(world, posX+x, posY+y-1, posZ+z) && rnd.nextFloat() > 0.5f) {
							int index = rnd.nextInt(crateBlocks.length);
	//						if (placeLamp && (y == sizeY-1 || r.nextFloat() > 0.25f*y)) {
	//							world.setBlock(posX+x, posY+y, posZ+z, TGBlocks.lamp01, 7, 2);
	//							placeLamp = false;
	//						}else {
								world.setBlockState(p.setPos(posX+x, posY+y, posZ+z), crateBlocks[index].getState(), 2);
							//}
						}
					}
				} /*else {
					int meta = 2+rnd.nextInt(4);
					MBlock TGChest = new MBlock(chest,meta);
					TGChest.setBlock(world, posX+x, posY+1, posZ+z, 0, this.lootTier);

				}*/
			}
		}
		
		if (((sizeX+sizeZ)/2.0f)/6.0f > rnd.nextFloat()) { //place lantern
			for (int y = sizeY; y > 1; y--) {
				for (int t = 0; t < 3; t++) {
					int x = rnd.nextInt(sizeX);
					int z = rnd.nextInt(sizeZ);
					if (world.isAirBlock(p.setPos(posX+x, posY+y, posZ+z)) && !isFreeSpace(world,posX+x,posY+y-1,posZ+z)) {
						world.setBlockState(p, lampstate, 2);
						return;
					}
				}
			}
		}
	}
	
	public boolean isFreeSpace(World world, int x, int y, int z) {
		MutableBlockPos p = new MutableBlockPos();	
		return world.isAirBlock(p.setPos(x, y, z)) || world.getBlockState(p).getBlock() == Blocks.SNOW_LAYER || world.getBlockState(p).getBlock() == TGBlocks.LAMP_0;
	}
}
