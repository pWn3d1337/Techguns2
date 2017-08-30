package techguns.api.machines;

import java.util.UUID;

import net.minecraft.entity.player.EntityPlayer;

public interface ITGTileEntSecurity {
	public void setOwner(EntityPlayer ply);
	public boolean isOwnedByPlayer(EntityPlayer ply);
	public UUID getOwner();
	
	/**
	 * 0 - everyone
	 * 1 - friends if FTButilities installed, otherwise like 0
	 * 2 - owner only
	 * @return
	 */
	public byte getSecurity();
}

