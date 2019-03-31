package techguns.util;

import java.util.Random;

import net.minecraft.util.math.BlockPos.MutableBlockPos;
import net.minecraft.world.World;
import techguns.world.EnumLootType;
import techguns.world.structures.WorldgenStructure.BiomeColorType;

public class MBlockOreClusterTypeOre extends MultiMMBlockIndexRoll {
	MBlock stone;
	float stonechance=0;
	
	public MBlockOreClusterTypeOre(MBlock[] ore, int[] weights, MBlock stone, float stonechance) {
		super(ore, weights);
		this.stone=stone;
		this.stonechance=stonechance;
	}
	
	@Override
	public void setBlock(World w, MutableBlockPos pos, int rotation, EnumLootType loottype, BiomeColorType biome,
			int index, Random rnd) {
		if(stonechance>0 && rnd.nextFloat()<stonechance) {
			stone.setBlock(w, pos, rotation, loottype, biome);
		} else {
			this.mBlocks[index].setBlock(w, pos, rotation, loottype, biome);
		}
	}

}
