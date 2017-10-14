package techguns.capabilities;

import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.Capability.IStorage;
import techguns.api.capabilities.ITGShooterValues;

public class TGGenericNPCDataStorage implements IStorage<TGGenericNPCData>{

	@Override
	public NBTBase writeNBT(Capability<TGGenericNPCData> capability, TGGenericNPCData instance, EnumFacing side) {
		final NBTTagCompound tags = new NBTTagCompound();
		instance.saveToNBT(tags);
		return tags;
	}

	@Override
	public void readNBT(Capability<TGGenericNPCData> capability, TGGenericNPCData instance, EnumFacing side,
			NBTBase nbt) {
		final NBTTagCompound tags = (NBTTagCompound) nbt;
		instance.loadFromNBT(tags);
	}

}
