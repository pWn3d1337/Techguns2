package techguns.util;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map.Entry;

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
import techguns.items.guns.GenericGunMeleeCharge;
import techguns.items.guns.MiningDrill;
import techguns.world.structures.WorldgenStructure.BiomeColorType;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.chunk.Chunk;
import net.minecraftforge.common.BiomeDictionary;
import net.minecraftforge.common.BiomeDictionary.Type;

public class BlockUtils {
	
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
				if(miningtool==null || stack.isEmpty() || checkMiningLevels(world,ply, b, miningtool, stack)) {
					entries.add(b);
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
}
