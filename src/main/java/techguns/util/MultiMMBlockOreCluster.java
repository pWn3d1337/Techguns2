package techguns.util;

import java.util.Random;

import net.minecraft.util.math.BlockPos.MutableBlockPos;
import net.minecraft.world.World;
import techguns.world.EnumLootType;
import techguns.world.structures.WorldgenStructure.BiomeColorType;

public abstract class MultiMMBlockOreCluster extends MultiMMBlock {

	public MultiMMBlockOreCluster(MBlock[] mblocks, int[] weights) {
		super(mblocks, weights);
	}

	public abstract void setBlock(World w, MutableBlockPos pos, int rotation, EnumLootType loottype, BiomeColorType biome, int index, Random rnd);
}
