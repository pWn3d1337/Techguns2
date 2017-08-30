package techguns.tileentities.operation;

import net.minecraft.nbt.NBTTagCompound;

public interface ITileEntityFluidTanks {
	public void saveTanksToNBT(NBTTagCompound tags);
	public void loadTanksFromNBT(NBTTagCompound tags);
}
