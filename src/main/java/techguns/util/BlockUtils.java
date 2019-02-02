package techguns.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map.Entry;
import java.util.Random;

import javax.annotation.Nullable;

import com.google.common.base.Function;
import com.google.common.base.Joiner;
import com.google.common.collect.Iterables;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockPos.MutableBlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.chunk.Chunk;
import net.minecraftforge.common.BiomeDictionary;
import net.minecraftforge.common.BiomeDictionary.Type;
import techguns.TGBlocks;
import techguns.items.guns.GenericGunMeleeCharge;
import techguns.world.EnumLootType;
import techguns.world.structures.WorldgenStructure;
import techguns.world.structures.WorldgenStructure.BiomeColorType;

public class BlockUtils {
	
	private static final String FILEPATH="/assets/techguns/structures/";
	
	public static final float[][] FILTER_GAUSSIAN_3x3 = new float[][] {
		{1/16f, 1/8f, 1/16f}, 
	    {1/8f, 1/4f, 1/8f},
	    {1/16f, 1/8f, 1/16f}
	};
	
	public static final float[][] FILTER_GAUSSIAN_5x5 = new float[][] {
		{.00325f, .01375f, .0235f, .01375f, .00325f},
        {.01375f, .059f, .097f, .059f, .01375f},
        {.0235f, .097f, .159f, .097f, .0235f},
        {.01375f, .059f, .097f, .059f, .01375f},
        {.00325f, .01375f, .0235f, .01375f, .00325f}
	};
	
	private static final Joiner COMMA_JOINER = Joiner.on(',');
	private static final Function<Entry<IProperty<?>, Comparable<?>>, String> MAP_ENTRY_TO_STRING = new Function<Entry<IProperty<?>, Comparable<?>>, String>() {
		@Nullable
		public String apply(@Nullable Entry<IProperty<?>, Comparable<?>> p_apply_1_) {
			if (p_apply_1_ == null) {
				return "<NULL>";
			} else {
				IProperty<?> iproperty = (IProperty) p_apply_1_.getKey();
				return iproperty.getName() + "=" + this.getPropertyName(iproperty, p_apply_1_.getValue());
			}
		}

		private <T extends Comparable<T>> String getPropertyName(IProperty<T> property, Comparable<?> entry) {
			return property.getName((T) entry);
		}
	};
	
	public static String getBlockStateVariantString(IBlockState state) {
		StringBuilder sb = new StringBuilder();
		COMMA_JOINER.appendTo(sb, Iterables.transform(state.getProperties().entrySet(), MAP_ENTRY_TO_STRING));
		return sb.toString();
	}
	
	public static BlockPos rotateAroundY(BlockPos pos, BlockPos axis, int steps) {
		int offsetX = pos.getX()-axis.getX();
		int offsetZ = pos.getZ()-axis.getZ();
		
		int newOffsetX=offsetX;
		int newOffsetZ=offsetZ;
		
		for(int i=steps; i>0; i--) {
			int tmp = newOffsetX;
			
			newOffsetX=newOffsetZ;
			newOffsetZ=-tmp;
		}
		return new BlockPos(axis.getX()+newOffsetX, pos.getY(), axis.getZ()+newOffsetZ);
	}
	
	public static BlockPos rotateAroundY(MutableBlockPos pos, BlockPos axis, int steps) {
		int offsetX = pos.getX()-axis.getX();
		int offsetZ = pos.getZ()-axis.getZ();
		
		int newOffsetX=offsetX;
		int newOffsetZ=offsetZ;
		
		for(int i=steps; i>0; i--) {
			int tmp = newOffsetX;
			
			newOffsetX=newOffsetZ;
			newOffsetZ=-tmp;
		}
		pos.setPos(axis.getX()+newOffsetX, pos.getY(), axis.getZ()+newOffsetZ);
		return pos;
	}
	
	public static BlockPos rotateAroundY(BlockPos pos, Vec3d axis, int steps) {
		
		Vec3d v_pos = new Vec3d(pos.getX()+0.5d, 0d, pos.getZ()+0.5d);
		
		double offsetX = v_pos.x-axis.x;
		double offsetZ = v_pos.z-axis.z;
		
		double newOffsetX=offsetX;
		double newOffsetZ=offsetZ;
		
		for(int i=steps; i>0; i--) {
			double tmp = newOffsetX;
			
			newOffsetX=newOffsetZ;
			newOffsetZ=-tmp;
		}
		return new BlockPos(axis.x+newOffsetX, pos.getY(), axis.z+newOffsetZ);
	}
	
	public static MutableBlockPos rotateAroundY(MutableBlockPos pos, Vec3d axis, int steps) {
		
		Vec3d v_pos = new Vec3d(pos.getX()+0.5d, 0d, pos.getZ()+0.5d);
		
		double offsetX = v_pos.x-axis.x;
		double offsetZ = v_pos.z-axis.z;
		
		double newOffsetX=offsetX;
		double newOffsetZ=offsetZ;
		
		for(int i=steps; i>0; i--) {
			double tmp = newOffsetX;
			
			newOffsetX=newOffsetZ;
			newOffsetZ=-tmp;
		}
		pos.setPos(axis.x+newOffsetX, pos.getY(), axis.z+newOffsetZ);
		return pos;
	}
	
	protected static boolean checkMiningLevels(World world, EntityPlayer ply, BlockPos b, GenericGunMeleeCharge miningtool, ItemStack stack) {
		IBlockState state = world.getBlockState(b);
		String tool = state.getBlock().getHarvestTool(state);
		
		if (state.getBlock().canCollideCheck(state, false) && state.getBlock().canHarvestBlock(world, b, ply)) {
			if(tool==null) {
				return true;
			} else {
				return state.getBlock().getHarvestLevel(state) <= miningtool.getHarvestLevel(stack, tool, ply, state);
			}
		}
		return false;
	}
	
	public static List<BlockPos> getBlockPlaneAroundAxisForMining(World world, EntityPlayer ply, BlockPos center, EnumFacing.Axis axis, int radius, boolean includeCenter, @Nullable GenericGunMeleeCharge miningtool, ItemStack stack){
		List<BlockPos> entries = new ArrayList<BlockPos>();
		
		Iterable<BlockPos> blocks;
		switch(axis) {
			case X:
				blocks = BlockPos.getAllInBox(center.add(0, -radius, -radius), center.add(0, radius, radius));
				break;
			case Y:
				blocks = BlockPos.getAllInBox(center.add(-radius,0, -radius), center.add(radius,0, radius));
				break;
			case Z:
			default:
				blocks = BlockPos.getAllInBox(center.add(-radius, -radius,0), center.add(radius, radius,0));
				break;
		}

		blocks.forEach(b -> {
			if(includeCenter || !b.equals(center)) {
				if(world.getTileEntity(b)==null) {
					if(miningtool==null || stack.isEmpty() || checkMiningLevels(world,ply, b, miningtool, stack)) {
						entries.add(b);
					}
				}
			}
		});
		return entries;
	}
	
	/**
 	 * return -1 when top block is not liquid , used to determine valid waterspawns
 	 * @param w
 	 * @param x
 	 * @param z
 	 * @return
 	 */
 	public static int getHeightValueLiquid(World w, int wx, int wz){

 		Chunk chunk = w.getChunkFromChunkCoords(wx>>4, wz>>4);
        int x = wx;
        int z = wz;
        int k = chunk.getTopFilledSegment() + 15;
        wx &= 15;

        int y=0;
        
        IBlockState block = null;
        for (wz &= 15; k > 0; --k)
        {
            block = chunk.getBlockState(wx, k, wz);

            if (block.getMaterial()!=Material.AIR)
            {
                y = k + 1;
                break;
            }
        }
 			
 		if(block!=null && !block.getMaterial().isLiquid()){
 			return -1;
 		}
 		return y;
 	}
 	
	
	public static int getValidSpawnYWater(World world, int x, int z, int sizeX, int sizeZ, int heightDiffLimit){
 		int h0 = getHeightValueLiquid(world, x, z);
		int h1 = getHeightValueLiquid(world, x+sizeX, z);
		int h2 = getHeightValueLiquid(world, x, z+sizeZ);
		int h3 = getHeightValueLiquid(world, x+sizeX, z+sizeZ);
		
		if(h0<0 || h1<0 || h2 <0 || h3<0){
			return -1;
		}

		if( !((MathUtil.abs(h0-h1)<heightDiffLimit) && (MathUtil.abs(h0-h2)<heightDiffLimit) && (MathUtil.abs(h0-h3)<heightDiffLimit)) ){
 			return -1;
 		}

 		return getMedianHeight(h0,h1,h2,h3);
 	}
	
 	private static int getMedianHeight(int h0, int h1, int h2, int h3){
 		int[] height = {h0,h1,h2,h3};
 		Arrays.sort(height);
 		return (height[1]+height[2])/2;
 	}
 	
 	/**
 	 * return -1 when liquid is top block, used to determine valid landspawns
 	 * @param w
 	 * @param x
 	 * @param z
 	 * @return
 	 */
 	public static int getHeightValueNoTreesIgnoreLiquid(World w, int x, int z){
 		MutableBlockPos p = new MutableBlockPos(x, 0, z);
 		int y = w.getTopSolidOrLiquidBlock(p).getY();

 		IBlockState bs = w.getBlockState(p.setPos(x, y, z));
 		Block b = bs.getBlock();
 		
 		if(bs.getMaterial().isLiquid()){
 			return -1;
 		}
 		
 		while (y>0 && (b.isLeaves(bs, w, p) /*|| b.isWood(w, x, y, z)*/ || b.isFoliage(w, p))){
 			y--;
 			bs = w.getBlockState(p.setPos(x, y, z));
 			b = bs.getBlock();
 		}
 		
 		return y;
 	}
 	
 	public static int getValidSpawnYArea(World world, int x, int z, int sizeX, int sizeZ, int heightDiffLimit, int step){
 		
 		int count=0;
 		int min=255;
 		int max=0;
 		int sum=0;
 				
 		int ix=0;
 		while(ix<=sizeX){
 		
 			int iz=0;
 			while(iz<=sizeZ){
 	 			
 				int h=getHeightValueNoTreesIgnoreLiquid(world, x+ix, z+iz); 
 				
 				if(h<0){
 					return -1;
 				}
 				
 				if (h<min){
 					min=h;
 				}
 				if(h>max){
 					max=h;
 				}
 				sum+=h;
 				count++;	
 				
 				if(iz<=sizeZ+step){
 					iz+=step;
 				} else if(iz<sizeZ && iz+step > sizeZ){
 					iz=sizeZ;
 				}
 				
 	 		}
 			
 			if(ix<=sizeX+step){
				ix+=step;
			} else if(ix<sizeX && ix+step > sizeX){
				ix=sizeX;
			}
 		}
 		
 		//System.out.println("HeightDiff:"+(max-min) +" Limit:"+heightDiffLimit);
 		
 		if((max-min)>heightDiffLimit){
 			return -1;
 		}
 		
 		return sum/count;
 		
 	}
 	
 	public static int getValidSpawnY(World world, int x, int z, int sizeX, int sizeZ, int heightDiffLimit){
 		
 		int h0 = getHeightValueNoTreesIgnoreLiquid(world, x, z);
		int h1 = getHeightValueNoTreesIgnoreLiquid(world, x+sizeX, z);
		int h2 = getHeightValueNoTreesIgnoreLiquid(world, x, z+sizeZ);
		int h3 = getHeightValueNoTreesIgnoreLiquid(world, x+sizeX, z+sizeZ);

		if(h0<0 || h1<0 || h2 <0 || h3<0){
			return -1;
		}

 		if( !((MathUtil.abs(h0-h1)<heightDiffLimit) && (MathUtil.abs(h0-h2)<heightDiffLimit) && (MathUtil.abs(h0-h3)<heightDiffLimit)) ){

 			return -1;
 		}
 		return getMedianHeight(h0,h1,h2,h3); /*solidblocks &&*/ 
 	}
 	
 	/**
 	 * return -1 when liquid is top block, used to determine valid landspawns
 	 * @param w
 	 * @param x
 	 * @param z
 	 * @return
 	 */
 	public static int getHeightValueUnderwater(World w, int x, int z){
 		MutableBlockPos p = new MutableBlockPos();
 		int y = w.getTopSolidOrLiquidBlock(p.setPos(x,0,z)).getY();
 		//w.getTopBlock(x, z);
 		
 		IBlockState bs = w.getBlockState(p.setPos(x, y, z));
 		Block b = bs.getBlock();
 		
 		while (y>0 && (b.isLeaves(bs, w, p) /*|| b.isWood(w, x, y, z)*/ || b.isFoliage(w, p) || bs.getMaterial().isLiquid())){
 			y--;
 			bs = w.getBlockState(p.setPos(x, y, z));
 			b=bs.getBlock();
 		}
 		
 		
 		/*for(int i=0; i<20; i++){
 			w.setBlock(x, y+i, z, Blocks.diamond_block);
 		}*/
 		
 		
 		return y;
 	}
 	
 	public static int getHeightValueNoTrees(World w, int x, int z){
 		MutableBlockPos p = new MutableBlockPos();
 		int y = getTopSolidOrLiquidBlock(w, x, z);

 		IBlockState bs = w.getBlockState(p.setPos(x, y, z));
 		Block b = bs.getBlock();
 		
 		while (y>0 && (b.isLeaves(bs, w, p) || b.isWood(w, p) || b.isFoliage(w, p))){
 			y--;
 			bs = w.getBlockState(p.setPos(x, y, z));
 			b= bs.getBlock();
 		}
 		
 		return y;
 	}
 	
 	/**
 	 * Like "getTopSolidOrLiquidBlock" from World, but gets first non air block
 	 * @param w
 	 * @param xCoord
 	 * @param zCoord
 	 * @return
 	 */
 	public static int getHeightValueTopBlockAll(World w, int xCoord, int zCoord)
    {
        Chunk chunk = w.getChunkFromChunkCoords(xCoord>>4, zCoord>>4);
        int x = xCoord;
        int z = zCoord;
        int k = chunk.getTopFilledSegment() + 15;
        xCoord &= 15;

        for (zCoord &= 15; k > 0; --k)
        {
            IBlockState block = chunk.getBlockState(xCoord, k, zCoord);
            
            
            if (!(block.getMaterial()==Material.AIR))
            {
                return k + 1;
            }
        }

        return -1;
    }
 	
	 /**
     * get the highest block, liquid or Solid
     */
    private static int getTopSolidOrLiquidBlock(World w, int posx, int posz)
    {
        Chunk chunk = w.getChunkFromChunkCoords(posx>>4, posz>>4);
        int x = posx;
        int z = posz;
        int k = chunk.getTopFilledSegment() + 15;
        posx &= 15;

        MutableBlockPos p = new MutableBlockPos();
        for (posz &= 15; k > 0; --k)
        {
            IBlockState block = chunk.getBlockState(posx, k, posz);
            Block b = block.getBlock();
            
            if ( (block.getMaterial().blocksMovement() && block.getMaterial() != Material.LEAVES && !b.isFoliage(w, p.setPos(x, k, z))) || block.getMaterial().isLiquid() )
            {
                return k + 1;
            }
        }

        return -1;
    }
 	
 	public static void removeJunkInArea(World world, int posX, int posZ, int sizeX, int sizeZ) {
		MutableBlockPos pos = new MutableBlockPos();
		for (int x = 0; x < sizeX; x++) {
			for (int z = 0; z <sizeZ; z++) {
				int y = BlockUtils.getHeightValueTopBlockAll(world, posX+x, posZ+z);//world.getHeightValue(posX+x, posZ+z);
				boolean stop = false;
				for (; y > 0 && !stop; y--) {
					IBlockState bs = world.getBlockState(pos.setPos(posX+x, y, posZ+z));
					if (!BlockUtils.isGroundBlock(bs)) {
						world.setBlockToAir(pos);
					}else {
						stop = true;
					}
				}
			}
		}
	}
 	
	public static void fillBlocks(World world, MutableBlockPos start, int sizeX, int sizeY, int sizeZ, IBlockState block) {
		int x = start.getX();
		int y = start.getY();
		int z = start.getZ();
		for (int i = 0; i < sizeX; i++) {			
			for (int j = 0; j < sizeZ; j++) {
				for (int k = 0; k < sizeY; k++) {
					world.setBlockState(start.setPos(x+i, y+k, z+j), block);
				}	
			}
		}
	}
	
	public static void fillBlocksHollow(World world, MutableBlockPos start, int sizeX, int sizeY, int sizeZ, IBlockState block) {
		int x = start.getX();
		int y = start.getY();
		int z = start.getZ();
		
		for (int i = 0; i < sizeX; i++) {			
			for (int j = 0; j < sizeZ; j++) {
				for (int k = 0; k < sizeY; k++) {
					if (j == 0 || j == sizeZ-1 || i == 0 || i == sizeX-1) {
						world.setBlockState(start.setPos(x+i, y+k, z+j), block);
					}
				}	
			}
		}
	}
 	
	public static BiomeColorType getBiomeType(World world, int posX, int posZ) {
		Biome biome = world.getBiomeForCoordsBody(new BlockPos(posX, 0, posZ));
		
		if (BiomeDictionary.hasType(biome, Type.SANDY) || BiomeDictionary.hasType(biome, Type.SAVANNA) || BiomeDictionary.hasType(biome, Type.WASTELAND)) {
			return BiomeColorType.DESERT;
		} else if ( BiomeDictionary.hasType(biome, Type.SNOWY)) {
			return BiomeColorType.SNOW;
		} else {
			return BiomeColorType.WOODLAND;
		}
	}
	
 	private static boolean isGroundBlock(IBlockState b) {
		Material mat = b.getMaterial();
		return mat == Material.GROUND ||
				mat == Material.GRASS ||
				mat == Material.ROCK ||
				mat == Material.SAND ||
				mat == Material.CLAY;
	}
 	
 	public static int getValidSpawnYUnderwater(World world, int x, int z, int sizeX, int sizeZ, int heightDiffLimit){
 		
 		int h0 = getHeightValueUnderwater(world, x, z);
		int h1 = getHeightValueUnderwater(world, x+sizeX, z);
		int h2 = getHeightValueUnderwater(world, x, z+sizeZ);
		int h3 = getHeightValueUnderwater(world, x+sizeX, z+sizeZ);	

		if(h0<0 || h1<0 || h2 <0 || h3<0){
			return -1;
		}

 		if( !((MathUtil.abs(h0-h1)<heightDiffLimit) && (MathUtil.abs(h0-h2)<heightDiffLimit) && (MathUtil.abs(h0-h3)<heightDiffLimit)) ){
 			return -1;
 		}

 		return getMedianHeight(h0,h1,h2,h3); /*solidblocks &&*/ 
 	}
 	

 	
 	public static void flattenArea(World world, int posX, int posZ, int sizeX, int sizeZ, int maxHeightDiff) {
		int posY;
		int heightSum = 0;
		int count = (sizeX*sizeZ);
		int min = -1;
		int max = 0;
		ArrayList<Integer> heights = new ArrayList<Integer>();
		for (int x = 0; x < sizeX; x++) {
			for (int z = 0; z <sizeZ; z++) {
				int y = getHeightValueNoTrees(world,posX+x, posZ+z)-1;
				
				heights.add(new Integer(y));
				heightSum+=y;
				if (y > max) max = y;
				if (y < min || min == -1) min = y;
			}
		}
		
		int span = max-min;
		
		float median=0;
		Collections.sort(heights);
		if (heights.size()>2){
			if (heights.size() % 2 ==0){
				median = (heights.get(((int)Math.floor(heights.size()/2))-1) + heights.get(((int)Math.ceil(heights.size()/2))-1)) /2;
			} else {
				median = heights.get((heights.size()/2)-1);
			}
		}
		
		
		float averageHeight = median; //(float)heightSum / (float)count;
		float f = averageHeight - ((float)maxHeightDiff/2.0f);
		//System.out.println("Avg:"+averageHeight+" - f:"+f + " - min:"+min+" - max:"+max);
		
		MutableBlockPos p = new MutableBlockPos();
		for (int x = 0; x < sizeX; x++) {
			for (int z = 0; z <sizeZ; z++) {
				int y =getHeightValueNoTrees(world, posX+x, posZ+z)-1;
				int newY;
				if (span <= maxHeightDiff) newY = y;
				else newY = Math.round(((((float)(y-min)/(float)span)*(float)maxHeightDiff) + f));
				//System.out.println("y: "+y+" - newY:"+newY);
				adjustHeightAtPos(world, posX+x, posZ+z, y, p, newY);

			}
		}
	}
 	
 	private static void adjustHeightAtPos(World world, int x, int z, int y, MutableBlockPos p, int newY) {
		if (newY  > y) {
			IBlockState b = world.getBlockState(p.setPos(x, y, z));
			if (!isGroundBlock(b)) {
				b = Blocks.DIRT.getDefaultState();
			}
			for (int i = y+1; i <= newY; i++) {
				world.setBlockState(p.setPos(x, i, z), b, 2);
			}
		}else if (newY < y) {
			for (int i = y; i > newY; i--) {
				world.setBlockToAir(p.setPos(x, i, z));
			}
		}
	}
 	
	public static void apply2DHeightmapFilter(World world, int posX, int posZ, int sizeX, int sizeZ, float[][] filter) {
		int xoffset = -filter.length/2;
		int zoffset = -filter[0].length/2;
		MutableBlockPos p = new MutableBlockPos();
		for (int x = 0; x < sizeX; x++) {
			for (int z = 0; z <sizeZ; z++) {
				int y = getHeightValueNoTrees(world,posX+x, posZ+z)-1;
				float newY = 0.0f;
				for (int i = 0; i < filter.length; i++) {
					for (int j = 0; j < filter[i].length; j++){
						newY += (getHeightValueNoTrees(world,posX+x+xoffset+i, posZ+z+zoffset+j)-1.0f) * filter[i][j];
					}
				}			
				adjustHeightAtPos(world, posX+x, posZ+z, y, p, Math.round(newY));						
			}
		}
	}
	
	public static short[][] loadStructureFromFile(String filename) {
		try {
			String path = FILEPATH+filename;
			BufferedReader br = new BufferedReader(new InputStreamReader(WorldgenStructure.class.getResourceAsStream(path)));
			int count = Integer.parseInt(br.readLine());
			short[][] blocks = new short[count][4];
			String line;
			int i = 0;
			while ((line = br.readLine()) != null) {
				String[] s = line.split(",");
				for (int j = 0; j < s.length; j++) {
					blocks[i][j] = Short.parseShort(s[j]);
				}
				i++;
			}
			
			return blocks;
			
		}catch (IOException e) {
			e.printStackTrace();
			return new short[0][0];
		}
	}
	
	public static void cleanUpwards(World world, short[][]blocks, ArrayList<MBlock> blockList, int posX, int posY, int posZ, int centerX, int centerZ, int rotation, int pass, int range) {
		//System.out.println("Blocks.length = "+blocks.length + "Blocks[0].length = "+blocks[0].length);
		
		MutableBlockPos p = new MutableBlockPos();
		BlockPos axis = new BlockPos(posX+centerX, 1, posZ+centerZ);
		for (int i = 0; i < blocks.length; i++) {
			int x = blocks[i][0];
			int y = blocks[i][1];
			int z = blocks[i][2];
			//System.out.println("blocklist:"+blockList.size());
			if (y==0){
				MBlock block = new MBlock(Blocks.AIR.getDefaultState());
					
				for (int o=1; o<=range; o++){

					//BlockUtils.setBlockRotated(world, block, posX, posY+o, posZ, x, z, centerX, centerZ, rotation,0);
					BlockUtils.setMBlockRotated(world, block, p.setPos(posX+x, posY+y+o, posZ+z), axis, rotation, null, BiomeColorType.WOODLAND);
					
					//setBlockRotatedReplaceAirOnly(world, block, posX, posY-o, posZ, x, z, centerX, centerZ, rotation);
					//world.setBlock(posX+x, posY-o, posZ+z, Blocks.diamond_block);
				}
			}
		}
	}
	
	public static void placeScannedStructure(World world, short[][]blocks, ArrayList<MBlock> blockList, int posX, int posY, int posZ, int centerX, int centerZ, int rotation, int pass, EnumLootType loottype, BiomeColorType biome) {
		placeScannedStructure(world, blocks, blockList, posX, posY, posZ, centerX, centerZ, rotation, pass, loottype, biome, 0, null);
	}
	
	public static void placeScannedStructure(World world, short[][]blocks, ArrayList<MBlock> blockList, int posX, int posY, int posZ, int centerX, int centerZ, int rotation, int pass, EnumLootType loottype, BiomeColorType biome, int indexRoll, Random rnd) {
		//System.out.println("Blocks.length = "+blocks.length + "Blocks[0].length = "+blocks[0].length);
		MutableBlockPos p = new MutableBlockPos();
		BlockPos axis = new BlockPos(posX+centerX, 1, posZ+centerZ);
		for (int i = 0; i < blocks.length; i++) {
			int x = blocks[i][0];
			int y = blocks[i][1];
			int z = blocks[i][2];
			//System.out.println("blocklist:"+blockList.size());
			
			/*if(i==0){
				System.out.println("place with Center:"+centerX+","+centerZ);
			}*/
			
			MBlock block = blockList.get(blocks[i][3]);
						
			if (block.getPass() == pass) {
				if(block instanceof MultiMMBlockOreCluster) {
					BlockUtils.setMBlockRotatedOreclusterBlock(world, (MultiMMBlockOreCluster)block, p.setPos(posX+x, posY+y, posZ+z), axis, rotation, loottype, biome, indexRoll, rnd);
				} else {
					BlockUtils.setMBlockRotated(world, block, p.setPos(posX+x, posY+y, posZ+z), axis, rotation, loottype, biome);
				}
				//BlockUtils.setBlockRotated(world, block, posX, posY+y, posZ, x, z, centerX, centerZ, rotation, lootTier);
			}
		}
	}
	
	public static void placeFoundation(World world, short[][]blocks, ArrayList<MBlock> blockList, int posX, int posY, int posZ, int centerX, int centerZ, int rotation, int pass, int range) {
		//System.out.println("Blocks.length = "+blocks.length + "Blocks[0].length = "+blocks[0].length);
		MutableBlockPos p = new MutableBlockPos();
		BlockPos axis = new BlockPos(posX+centerX, 1, posZ+centerZ);
		for (int i = 0; i < blocks.length; i++) {
			int x = blocks[i][0];
			int y = blocks[i][1];
			int z = blocks[i][2];
			//System.out.println("blocklist:"+blockList.size());
			if (y==0){
				MBlock block = blockList.get(blocks[i][3]);
				if (block.getPass() == pass) {
					
					for (int o=1; o<=range; o++){
						
						/*if(world.isAirBlock(posX+x, posY-o, posZ+z)) {
							BlockUtils.setBlockRotated(world, block, posX, posY-o, posZ, x, z, centerX, centerZ, rotation);
						}*/
						//setBlockRotatedReplaceAirOnly(world, block, posX, posY-o, posZ, x, z, centerX, centerZ, rotation);
						
						setMBlockRotatedReplaceableOnly(world, block, p.setPos(posX+x, posY+y-o, posZ+z), axis, rotation, null);
						//world.setBlock(posX+x, posY-o, posZ+z, Blocks.diamond_block);
					}
				}
			}
		}
	}
	
	public static void setMBlockRotated(World w, MBlock mblock, MutableBlockPos pos, BlockPos axis, int rotation, EnumLootType loottype, BiomeColorType biome) {
		BlockUtils.rotateAroundY(pos, axis, rotation);
		mblock.setBlock(w, pos, rotation, loottype,biome);
	}
	
	public static void setMBlockRotatedOreclusterBlock(World w, MultiMMBlockOreCluster mblock, MutableBlockPos pos, BlockPos axis, int rotation, EnumLootType loottype, BiomeColorType biome, int indexRoll, Random rnd) {
		BlockUtils.rotateAroundY(pos, axis, rotation);
		mblock.setBlock(w, pos, rotation, loottype,biome,indexRoll,rnd);
	}
	
	public static void setMBlockRotatedReplaceableOnly(World w, MBlock mblock, MutableBlockPos pos, BlockPos axis, int rotation, EnumLootType loottype) {
		BlockUtils.rotateAroundY(pos, axis, rotation);
		mblock.setBlockReplaceableOnly(w, pos, rotation, loottype, BiomeColorType.WOODLAND);
	}

	/**
	  * Fills a 'hollow' sphere, where block1 is the inner block and block2 is the hull
	  */
	 public static void fillSphere2(World world, int posX, int posY, int posZ, float radius, MBlock block1, MBlock block2) {
		 fillSphere2(world,posX,posY,posZ,radius, 1.0f, block1, block2);
	 }
	 
	 public static void fillSphere2(World world, int posX, int posY, int posZ, float radius, float thickness, MBlock block1, MBlock block2) {
		Vec3d center = new Vec3d(posX, posY, posZ);
		
		float rsquared = (float) Math.pow(radius, 2.0f);
		float rsquared2 = (float) Math.pow(radius-thickness, 2.0f);
		
		MutableBlockPos p = new MutableBlockPos();
		
		int r = ((int)(radius));
		for(int i = -r; i <= r; i++) {
			for(int j = -r; j <= r; j++) {
				for(int k = -r; k <= r; k++) {
					int x = posX + i;
					int y = posY + j;
					int z = posZ + k;
					Vec3d v = new Vec3d(x, y, z);
					float dsquared = (float) center.squareDistanceTo(v);
					if (dsquared < rsquared2) {
						block1.setBlock(world, p.setPos(x, y, z), 0);
					}else if (dsquared < rsquared) {
						block2.setBlock(world, p.setPos(x, y, z), 0);
					}
				}
			}
		}	
	}
	 
	 public static void fillCylinder(World world, int x1, int y1, int z1, int x2, int y2, int z2, float radius, MBlock block1, MBlock block2) {
		Vec3d v1 = new Vec3d(x1, y1, z1);
		Vec3d v2 = new Vec3d(x2, y2, z2);
		Vec3d v2v1 = v1.subtract(v2);
		double l = v2v1.lengthVector();
		//Vec3 dir = v2v1.normalize();
		int tmp = 0;
		if (x1 > x2) { tmp = x2; x2 = x1; x1 = tmp;}
		if (y1 > y2) {tmp = y2; y2 = y1; y1 = tmp;}
		if (z1 > z2) {tmp = z2; z2 = z1; z1 = tmp;}
		
		int r = (int)radius;
		
		MutableBlockPos p = new MutableBlockPos();
		
		for (int x = x1-r; x < x2+r; x++) {
			for (int y = y1-r; y < y2+r; y++) {
				for (int z = z1-r; z < z2+r; z++) {
					Vec3d v0 = new Vec3d(x, y, z);
					Vec3d v1v0 = v1.subtract(v0);
					Vec3d v_ = v2v1.crossProduct(v1v0);
					double distance = v_.lengthVector()/l;
					
					if (distance < radius-1.0f) {
						block1.setBlock(world, p.setPos(x, y, z), 0);
					}else if (distance < radius) {
						block2.setBlock(world, p.setPos(x, y, z), 0);
					}
				}
			}
		}
		
	}
 
	 public static void drawLine(World world, int x1, int y1, int z1, int x2, int y2, int z2, MBlock block) {

		 MutableBlockPos p = new MutableBlockPos();
		 
        int dx = x2 - x1;
        int dy = y2 - y1;
        int dz = z2 - z1;

        int ax = Math.abs(dx) << 1;
        int ay = Math.abs(dy) << 1;
        int az = Math.abs(dz) << 1;

        int signx = (int) Math.signum(dx);
        int signy = (int) Math.signum(dy);
        int signz = (int) Math.signum(dz);

        int x = x1;
        int y = y1;
        int z = z1;

        int deltax, deltay, deltaz;
        if (ax >= Math.max(ay, az)) /* x dominant */ {
            deltay = ay - (ax >> 1);
            deltaz = az - (ax >> 1);
            while (true) {
                block.setBlock(world, p.setPos(x, y, z), 0);
                if (x == x2) {
                    return;
                }


                if (deltay >= 0) {
                    y += signy;
                    deltay -= ax;
                }

                if (deltaz >= 0) {
                    z += signz;
                    deltaz -= ax;
                }

                x += signx;
                deltay += ay;
                deltaz += az;
            }
        } else if (ay >= Math.max(ax, az)) /* y dominant */ {
            deltax = ax - (ay >> 1);
            deltaz = az - (ay >> 1);
            while (true) {
            	block.setBlock(world, p.setPos(x, y, z), 0);
                if (y == y2) {
                    return;
                }
                
//	                //Keep 4-way neighborhood when going up/down
//	                if (deltax >= 0 && deltaz >= 0) {
//	                	block.setBlock(world, x+signx, y, z+signz, 0);
//	                }

                if (deltax >= 0) {
                	block.setBlock(world, p.setPos(x+signx, y, z), 0);
                    x += signx;
                    deltax -= ay;
                }

                if (deltaz >= 0) {
                	block.setBlock(world, p.setPos(x, y, z+signz), 0);
                    z += signz;
                    deltaz -= ay;
                }

                y += signy;
                deltax += ax;
                deltaz += az;
            }
        } else if (az >= Math.max(ax, ay)) /* z dominant */ {
            deltax = ax - (az >> 1);
            deltay = ay - (az >> 1);
            while (true) {
            	block.setBlock(world, p.setPos(x, y, z), 0);
                if (z == z2) {
                    return;
                }

                if (deltax >= 0) {
                    x += signx;
                    deltax -= az;
                }

                if (deltay >= 0) {
                    y += signy;
                    deltay -= az;
                }

                z += signz;
                deltax += ax;
                deltay += ay;
            }
        }
	}
	 
	 public static void fillSphere(World world, int posX, int posY, int posZ, float radius, MBlock block) {
		Vec3d center = new Vec3d(posX, posY, posZ);
		
		float rsquared = (float) Math.pow(radius, 2.0f);
		
		MutableBlockPos p=new MutableBlockPos();
		
		int r = ((int)(radius));
		for(int i = -r; i <= r; i++) {
			for(int j = -r; j <= r; j++) {
				for(int k = -r; k <= r; k++) {
					int x = posX + i;
					int y = posY + j;
					int z = posZ + k;
					Vec3d v = new Vec3d(x, y, z);
					float dsquared = (float) center.squareDistanceTo(v);
					if (dsquared < rsquared) {
						block.setBlock(world, p.setPos(x, y, z), 0);
					}
				}
			}
		}	
	}
	
}
