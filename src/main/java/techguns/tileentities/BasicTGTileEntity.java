package techguns.tileentities;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.SPacketUpdateTileEntity;
import net.minecraft.server.management.PlayerChunkMapEntry;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.world.WorldServer;

public class BasicTGTileEntity extends TileEntity {

	/**
	 * Has this tileent rotation saved in the tile?
	 */
	protected boolean hasRotation;
	//How the tile is rotated
	public byte rotation=0;
	
	public BasicTGTileEntity(boolean hasRotation) {
		super();
		this.hasRotation = hasRotation;
	}
	
	protected int getUseRangeSquared() {
		return 64;
	}

	public boolean isUseableByPlayer(EntityPlayer player) {
		if (this.world.getTileEntity(this.pos) != this) {
			return false;
		} else {
			return player.getDistanceSq((double) this.pos.getX() + 0.5D, (double) this.pos.getY() + 0.5D,
					(double) this.pos.getZ() + 0.5D) <= getUseRangeSquared();
		}
	}
	
	@Override
	public SPacketUpdateTileEntity getUpdatePacket() {
		NBTTagCompound tags = new NBTTagCompound();
		this.writeClientDataToNBT(tags);
		return new SPacketUpdateTileEntity(pos, 1, tags);
	}

	@Override
	public void onDataPacket(NetworkManager net, SPacketUpdateTileEntity packet) {
		this.readClientDataFromNBT(packet.getNbtCompound());
	}

	@Override
	public NBTTagCompound getUpdateTag() {
		NBTTagCompound tags= super.getUpdateTag();
		this.writeClientDataToNBT(tags);
		return tags;
	}

	@Override
	public void handleUpdateTag(NBTTagCompound tag) {
		super.handleUpdateTag(tag);
		this.readClientDataFromNBT(tag);
	}

	@Override
	public NBTTagCompound writeToNBT(NBTTagCompound compound) {
		this.writeClientDataToNBT(compound);
		return super.writeToNBT(compound);
	}
	
	@Override
	public void readFromNBT(NBTTagCompound compound) {
		this.readClientDataFromNBT(compound);
		super.readFromNBT(compound);
	}
	
	/**
	 * write all data the client needs too, to nbt.
	 * @param compound
	 * @return
	 */
	public void writeClientDataToNBT(NBTTagCompound tags) {
		if (this.hasRotation) {
			tags.setByte("rotation", this.rotation);
		}
	}
	
	/**
	 * read all data the client needs from nbt
	 * @param compound
	 */
	public void readClientDataFromNBT(NBTTagCompound tags) {
		if(this.hasRotation) {
			this.rotation =(byte) (tags.getByte("rotation") % 4);
		}
	}
	
	public boolean hasRotation() {
		return hasRotation;
	}
	
	public void rotateTile() {
		if(!this.world.isRemote && this.hasRotation) {
			this.rotation = (byte) (this.rotation+1 %4);
			this.needUpdate();
		}
	}
	
	public void rotateTile(EnumFacing sideHit) {
		this.rotateTile();
	}
	
	public boolean canBeWrenchRotated() {
		return true;
	}
	
	public boolean canBeWrenchDismantled() {
		return true;
	}
	
	/**
	 * called serverside when this tileent should send out updated to client
	 */
	public void needUpdate(){
		//this.world.markBlockRangeForRenderUpdate(pos, pos);
		if(!this.world.isRemote) {
			
			ChunkPos cp = this.world.getChunk(getPos()).getPos();
			PlayerChunkMapEntry entry = ((WorldServer)this.world).getPlayerChunkMap().getEntry(cp.x, cp.z);
			if (entry!=null) {
				entry.sendPacket(this.getUpdatePacket());
			}
		}
		this.markDirty();
	}
	
}
