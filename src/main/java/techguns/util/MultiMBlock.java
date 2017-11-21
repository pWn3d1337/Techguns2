package techguns.util;

import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.math.BlockPos.MutableBlockPos;
import net.minecraft.world.World;
import techguns.world.BlockRotator;
import techguns.world.EnumLootType;

public class MultiMBlock extends MBlock {

	protected Block[] blocks;
	protected int[] metas;
	protected int[] weights;
	protected IBlockState[] states;

	protected int totalWeight;
	
	public MultiMBlock(Block[] blocks, int[] metas, int[] weights){
		super(blocks[0],metas[0]);
		this.blocks=blocks;
		this.metas=metas;
		this.weights=weights;
		this.states = new IBlockState[blocks.length];
		for (int i=0;i<blocks.length; i++) {
			this.states[i]=blocks[i].getStateFromMeta(metas[i]);
		}
		this.calcTotalweight();
	}
	
	private void calcTotalweight(){
		this.totalWeight=0;
		for (int i=0;i<this.weights.length;i++){
			this.totalWeight += weights[i];
		}
	}
	
	@Override
	public void setBlock(World w, MutableBlockPos pos, int rotation, EnumLootType loottype) {
		int index = this.rollBlockIndex(w.rand);
		
		IBlockState targetState = BlockRotator.getRotatedHorizontal(states[index], rotation);
		w.setBlockState(pos, targetState);
		if(this.hasTileEntity) {
			this.tileEntityPostPlacementAction(w, targetState, pos, rotation);
		}
	}

	/**
	 * Roll index of the next block to use;
	 * @return
	 */
	protected int rollBlockIndex(Random rnd){
		int roll = rnd.nextInt(this.totalWeight+1);

		int sum =0;
		for (int i =0; i<this.blocks.length; i++){
			sum+=this.weights[i];
			if (roll <= sum){
				return i;
			}		
		}		
		return weights.length;
	}
}
