package techguns.world;

import java.util.HashMap;

import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import techguns.util.MBlock;

public class BlockData {

	private final static int DEFAULT_LAYER = 1;
	
	public final static int MIN_LAYER = 0; //Expand when needed
	public final static int MAX_LAYER = 5;
	
	private static HashMap<MBlock, Integer> blockLayers_meta = new HashMap<>();
	private static HashMap<Block, Integer> blockLayers = new HashMap<>();
	
	static {
		blockLayers.put(Blocks.TORCH, 2);
		blockLayers.put(Blocks.IRON_TRAPDOOR, 2);
		blockLayers.put(Blocks.TRAPDOOR, 2);
		blockLayers.put(Blocks.ACACIA_DOOR, 2);
		blockLayers.put(Blocks.BIRCH_DOOR, 2);
		blockLayers.put(Blocks.DARK_OAK_DOOR, 2);
		blockLayers.put(Blocks.IRON_DOOR, 2);
		blockLayers.put(Blocks.JUNGLE_DOOR, 2);
		blockLayers.put(Blocks.OAK_DOOR, 2);
		blockLayers.put(Blocks.SPRUCE_DOOR, 2);
		blockLayers.put(Blocks.LADDER, 2);
		blockLayers.put(Blocks.RAIL, 2);
		blockLayers.put(Blocks.ACTIVATOR_RAIL, 2);
		blockLayers.put(Blocks.DETECTOR_RAIL, 2);
		blockLayers.put(Blocks.GOLDEN_RAIL, 2);
		blockLayers.put(Blocks.REDSTONE_WIRE, 2);
		blockLayers.put(Blocks.REDSTONE_TORCH, 2);
		blockLayers.put(Blocks.WATER, 2);
		blockLayers.put(Blocks.LAVA, 2);
		blockLayers.put(Blocks.LEVER, 2);
		blockLayers.put(Blocks.STONE_BUTTON,2);
		blockLayers.put(Blocks.WOODEN_BUTTON, 2);
	}
	
	public static int getBlockLayer(IBlockState state) {
		MBlock mb = new MBlock(state);
		Integer layer = blockLayers_meta.get(mb);
		if (layer != null) {
			return layer;
		}else {
			layer = blockLayers.get(state.getBlock());
			if (layer != null) {
				return layer;
			}else {
				return DEFAULT_LAYER;
			}
		}
	}
}
