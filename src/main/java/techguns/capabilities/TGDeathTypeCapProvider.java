package techguns.capabilities;

import net.minecraft.nbt.NBTBase;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityInject;
import net.minecraftforge.common.capabilities.ICapabilitySerializable;
import techguns.Techguns;

public class TGDeathTypeCapProvider implements ICapabilitySerializable<NBTBase> {

	@CapabilityInject(TGDeathTypeCap.class)
	public static final Capability<TGDeathTypeCap> TG_DEATHTYPE_CAP = null;
	
	private TGDeathTypeCap instance; // = TG_DEATHTYPE_CAP.getDefaultInstance();
	
	public static final EnumFacing DEFAULT_FACING = null;
	
	/**
	 * The ID of this capability.
	 */
	public static final ResourceLocation ID = new ResourceLocation(Techguns.MODID, "deathType");
	
	public TGDeathTypeCapProvider(TGDeathTypeCap cap) {
		this.instance = cap;
	}

	@Override
	public boolean hasCapability(Capability<?> capability, EnumFacing facing) {
		return capability == TG_DEATHTYPE_CAP;
	}

	@Override
	public <T> T getCapability(Capability<T> capability, EnumFacing facing) {
		return capability == TG_DEATHTYPE_CAP ? TG_DEATHTYPE_CAP.<T> cast(this.instance) : null;
	}

	@Override
	public NBTBase serializeNBT() {
		 return TG_DEATHTYPE_CAP.getStorage().writeNBT(TG_DEATHTYPE_CAP, this.instance, DEFAULT_FACING);
	}

	@Override
	public void deserializeNBT(NBTBase nbt) {
		TG_DEATHTYPE_CAP.getStorage().readNBT(TG_DEATHTYPE_CAP, this.instance, DEFAULT_FACING, nbt);
	}	

	
}
