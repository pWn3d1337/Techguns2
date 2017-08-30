package techguns.capabilities;

import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.Capability.IStorage;
import techguns.api.capabilities.ITGShooterValues;

public class TGShooterValuesStorage implements IStorage<ITGShooterValues> {

	@Override
	public NBTBase writeNBT(Capability<ITGShooterValues> capability, ITGShooterValues instance, EnumFacing side) {
		final NBTTagCompound tags = new NBTTagCompound();
		return tags;
	}

	@Override
	public void readNBT(Capability<ITGShooterValues> capability, ITGShooterValues instance, EnumFacing side, NBTBase nbt) {
	}

}
