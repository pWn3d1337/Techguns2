package techguns.tileentities;

import java.util.UUID;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import techguns.api.machines.ITGTileEntSecurity;
import techguns.factions.TGNpcFactions;
import static techguns.gui.ButtonConstants.*;

public class BasicOwnedTileEnt extends BasicInventoryTileEnt implements ITGTileEntSecurity {
	
	public BasicOwnedTileEnt(int inventorySize, boolean hasRotation) {
		super(inventorySize, hasRotation);
	}

	/**
	 * Accessible by:
	 *  0 - anyone
	 *  1 - friends FTBUtilities is installed, else anyone
	 *  2 - restricted to owner
	 */
	protected byte security = 0;

	protected UUID owner;

	/**
	 * Sets the owner of this tileent on placing
	 * 
	 * @param ply
	 */
	@Override
	public void setOwner(EntityPlayer ply) {
		UUID id = ply.getGameProfile().getId();
		if (id != null) {
			this.owner = id;
			this.security = 0;
		}
	}

	@Override
	public boolean isOwnedByPlayer(EntityPlayer ply) {
		if (this.owner == null) {
			return false;
		}
		return this.owner.equals(ply.getGameProfile().getId());
	}

	@Override
	public UUID getOwner() {
		return this.owner;
	}

	/**
	 * 0 - everyone 1 - friends if FTButilities installed, otherwise like 0 2 -
	 * owner only
	 * 
	 * @return
	 */
	@Override
	public byte getSecurity() {
		return this.security;
	}

	@Override
	public void readClientDataFromNBT(NBTTagCompound tags) {
		super.readClientDataFromNBT(tags);

		this.security = tags.getByte("security");

		String own = tags.getString("owner");
		if (own != null && !own.equals("")) {
			try {
				this.owner = UUID.fromString(own);
			} catch (IllegalArgumentException e) {
				this.owner = null;
			}
		}
	}

	@Override
	public void writeClientDataToNBT(NBTTagCompound tags) {
		super.writeClientDataToNBT(tags);
		if (this.owner != null) {
			tags.setByte("security", this.security);
			tags.setString("owner", this.owner.toString());
		}
	}

	@Override
	public void buttonClicked(int id, EntityPlayer ply, String data) {
		if (id==BUTTON_ID_SECURITY &&(this.isOwnedByPlayer(ply))){
			this.security++;
			this.security = (byte) (this.security%3);
			if(!this.world.isRemote){
				this.needUpdate();
			}
		} else {
			super.buttonClicked(id, ply, data);
		}
	}
	
	@Override
	public boolean isUseableByPlayer(EntityPlayer player) {
		if(super.isUseableByPlayer(player)){
			if (this.owner==null || this.security ==0){
				return true;
			} else if (this.isOwnedByPlayer(player)) {
				return true;
			} else if (this.security==1){
				return !TGNpcFactions.isHostile(this.owner, player.getGameProfile().getId());
			}
						
		}
		return false;
	}

	public void setSecurity(byte security) {
		this.security = security;
	}
}
