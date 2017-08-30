package techguns.blocks.machines.multiblocks;

import net.minecraft.util.math.BlockPos;

public class SlavePos {
	int offsetX;
	int offsetY;
	int offsetZ;
	
	public SlavePos(int offsetX, int offsetY, int offsetZ) {
		super();
		this.offsetX = offsetX;
		this.offsetY = offsetY;
		this.offsetZ = offsetZ;
	}

	public SlavePos(BlockPos pos2, BlockPos pos1) {
		super();
		this.offsetX = pos2.getX()-pos1.getX();
		this.offsetY = pos2.getY()-pos1.getY();
		this.offsetZ = pos2.getZ()-pos1.getZ();
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + offsetX;
		result = prime * result + offsetY;
		result = prime * result + offsetZ;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		SlavePos other = (SlavePos) obj;
		if (offsetX != other.offsetX)
			return false;
		if (offsetY != other.offsetY)
			return false;
		if (offsetZ != other.offsetZ)
			return false;
		return true;
	}
	@Override
	public String toString() {
		return "SlavePos [offsetX=" + offsetX + ", offsetY=" + offsetY + ", offsetZ=" + offsetZ + "]";
	}
	
}