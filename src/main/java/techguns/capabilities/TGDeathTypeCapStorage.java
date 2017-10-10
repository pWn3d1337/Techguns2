package techguns.capabilities;

import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.Capability.IStorage;

public class TGDeathTypeCapStorage implements IStorage<TGDeathTypeCap> {

	@Override
	public NBTBase writeNBT(Capability<TGDeathTypeCap> capability, TGDeathTypeCap instance, EnumFacing side) {
		final NBTTagCompound tags = new NBTTagCompound();
		return tags;
	}

	@Override
	public void readNBT(Capability<TGDeathTypeCap> capability, TGDeathTypeCap instance, EnumFacing side, NBTBase nbt) {
	}
	
}
