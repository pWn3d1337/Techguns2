package techguns.util;

import java.util.Random;

import net.minecraft.block.state.IBlockState;
import net.minecraft.util.math.BlockPos.MutableBlockPos;
import net.minecraft.world.World;
import techguns.world.BlockRotator;
import techguns.world.EnumLootType;
import techguns.world.structures.WorldgenStructure.BiomeColorType;

public class MultiMMBlock extends MBlock {

	protected MBlock[] mBlocks;
	protected int[] weights;
	protected IBlockState[] states;

	protected int totalWeight;
	
	public MultiMMBlock(MBlock[] mblocks, int[] weights) {
		super(mblocks[0]);
		this.mBlocks= mblocks;
		this.weights=weights;
		this.calcTotalweight();
	}
	
	protected void calcTotalweight(){
		this.totalWeight=0;
		for (int i=0;i<this.weights.length;i++){
			this.totalWeight += weights[i];
		}
	}

	@Override
	public void setBlock(World w, MutableBlockPos pos, int rotation, EnumLootType loottype, BiomeColorType biome) {
		int index = this.rollBlockIndex(w.rand);
		this.mBlocks[index].setBlock(w, pos, rotation, loottype, biome);
	}

	/**
	 * Roll index of the next block to use;
	 * @return
	 */
	protected int rollBlockIndex(Random rnd){
		int roll = rnd.nextInt(this.totalWeight+1);

		int sum =0;
		for (int i =0; i<this.mBlocks.length; i++){
			sum+=this.weights[i];
			if (roll <= sum){
				return i;
			}		
		}		
		return weights.length;
	}
	
}
