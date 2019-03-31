package techguns.util;

import java.util.Random;

import net.minecraft.util.math.BlockPos.MutableBlockPos;
import net.minecraft.world.World;
import techguns.world.EnumLootType;
import techguns.world.structures.WorldgenStructure.BiomeColorType;

public class MultiMMBlockIndexRoll extends MultiMMBlock {

	public MultiMMBlockIndexRoll(MBlock[] mblocks, int[] weights) {
		super(mblocks, weights);
	}

	public void setBlock(World w, MutableBlockPos pos, int rotation, EnumLootType loottype, BiomeColorType biome, int index, Random rnd) {
		this.mBlocks[index].setBlock(w, pos, rotation, loottype, biome);
	}
}
