package techguns.capabilities;

import net.minecraft.nbt.NBTBase;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityInject;
import net.minecraftforge.common.capabilities.ICapabilitySerializable;
import techguns.Techguns;
import techguns.api.capabilities.ITGShooterValues;

public class TGSpawnerNPCDataCapProvider implements ICapabilitySerializable<NBTBase> {

	@CapabilityInject(TGSpawnerNPCData.class)
	public static final Capability<TGSpawnerNPCData> TG_GENERICNPC_DATA = null;
	
	/**
	 * The ID of this capability.
	 */
	public static final ResourceLocation ID = new ResourceLocation(Techguns.MODID, "genericNPCData");
	
	public static final EnumFacing DEFAULT_FACING = null;
	
	private TGSpawnerNPCData instance; // = TG_SHOOTER_VALUES.getDefaultInstance();
		
	public TGSpawnerNPCDataCapProvider(TGSpawnerNPCData caps) {
		this.instance = caps;
	}
	
	@Override
	public boolean hasCapability(Capability<?> capability, EnumFacing facing) {
		return capability == TG_GENERICNPC_DATA;
	}

	@Override
	public <T> T getCapability(Capability<T> capability, EnumFacing facing) {
		return capability == TG_GENERICNPC_DATA ? TG_GENERICNPC_DATA.<T> cast(this.instance) : null;
	}

	@Override
	public NBTBase serializeNBT() {
		 return TG_GENERICNPC_DATA.getStorage().writeNBT(TG_GENERICNPC_DATA, this.instance, DEFAULT_FACING);
	}

	@Override
	public void deserializeNBT(NBTBase nbt) {
		TG_GENERICNPC_DATA.getStorage().readNBT(TG_GENERICNPC_DATA, this.instance, DEFAULT_FACING, nbt);
	}

}
