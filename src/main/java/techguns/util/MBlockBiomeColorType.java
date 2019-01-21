package techguns.util;

import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.math.BlockPos.MutableBlockPos;
import net.minecraft.world.World;
import techguns.world.BlockRotator;
import techguns.world.EnumLootType;
import techguns.world.structures.WorldgenStructure.BiomeColorType;

public class MBlockBiomeColorType extends MBlock {

	protected Block[] blocks;
	protected int[] metas;
	protected IBlockState[] states;
	
	public MBlockBiomeColorType(Block[] blocks, int[] metas){
		super(blocks[0],metas[0]);
		this.blocks=blocks;
		this.metas=metas;
		this.states = new IBlockState[blocks.length];
		
		for (int i=0;i<blocks.length; i++) {
			this.states[i]=blocks[i].getStateFromMeta(metas[i]);
		}
	}

	@Override
	public void setBlock(World w, MutableBlockPos pos, int rotation, EnumLootType loottype, BiomeColorType biome) {
		int index = biome.ordinal();
		if(index>=states.length) {
			index=0;
		}
		
		IBlockState targetState = BlockRotator.getRotatedHorizontal(states[index], rotation);
		w.setBlockState(pos, targetState);
		if(this.hasTileEntity) {
			this.tileEntityPostPlacementAction(w, targetState, pos, rotation);
		}
	}
	
	
}
