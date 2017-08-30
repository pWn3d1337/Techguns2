package techguns.capabilities;

import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.Capability.IStorage;
import techguns.api.capabilities.ITGExtendedPlayer;

public class TGExtendedPlayerStorage implements IStorage<ITGExtendedPlayer> {

	@Override
	public NBTBase writeNBT(Capability<ITGExtendedPlayer> capability, ITGExtendedPlayer instance, EnumFacing side) {
		final NBTTagCompound tags = new NBTTagCompound();
		instance.saveToNBT(tags);
		return tags;
	}

	@Override
	public void readNBT(Capability<ITGExtendedPlayer> capability, ITGExtendedPlayer instance, EnumFacing side, NBTBase nbt) {
		final NBTTagCompound tags = (NBTTagCompound) nbt;
		instance.loadFromNBT(tags);
	}

}
