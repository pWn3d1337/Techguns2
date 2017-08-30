package techguns.capabilities;

import net.minecraft.nbt.NBTBase;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityInject;
import net.minecraftforge.common.capabilities.CapabilityManager;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.common.capabilities.ICapabilitySerializable;
import techguns.Techguns;
import techguns.api.capabilities.ITGExtendedPlayer;

public class TGExtendedPlayerCapProvider implements ICapabilitySerializable<NBTBase> {

	@CapabilityInject(ITGExtendedPlayer.class)
	public static final Capability<ITGExtendedPlayer> TG_EXTENDED_PLAYER = null;
	
	/**
	 * The ID of this capability.
	 */
	public static final ResourceLocation ID = new ResourceLocation(Techguns.MODID, "extendedPlayer");
	
	public static final EnumFacing DEFAULT_FACING = null;
	
	private ITGExtendedPlayer instance; // = TG_EXTENDED_PLAYER.getDefaultInstance();
		
	public TGExtendedPlayerCapProvider(ITGExtendedPlayer caps) {
		this.instance = caps;
	}

	@Override
	public boolean hasCapability(Capability<?> capability, EnumFacing facing) {
		return capability == TG_EXTENDED_PLAYER;
	}

	@Override
	public <T> T getCapability(Capability<T> capability, EnumFacing facing) {
		return capability == TG_EXTENDED_PLAYER ? TG_EXTENDED_PLAYER.<T> cast(this.instance) : null;
	}

	@Override
	public NBTBase serializeNBT() {
		 return TG_EXTENDED_PLAYER.getStorage().writeNBT(TG_EXTENDED_PLAYER, this.instance, DEFAULT_FACING);
	}

	@Override
	public void deserializeNBT(NBTBase nbt) {
		TG_EXTENDED_PLAYER.getStorage().readNBT(TG_EXTENDED_PLAYER, this.instance, DEFAULT_FACING, nbt);
	}
	
}
