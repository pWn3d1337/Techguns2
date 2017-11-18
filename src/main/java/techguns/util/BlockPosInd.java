package techguns.util;

import net.minecraft.util.math.BlockPos;

public class BlockPosInd extends BlockPos {
	int index;
	
	public BlockPosInd(int x, int y, int z, int index) {
		super(x, y, z);
		this.index=index;
	}

	@Override
	public String toString() {
		return getX()+","+getY()+","+getZ()+","+index;
	}
}
