package techguns.tileentities;

import java.util.UUID;

import net.minecraft.client.renderer.texture.ITickable;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.SPacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;
import techguns.api.machines.ITGTileEntSecurity;

public class TileEntityBioBlob extends TileEntity implements ITGTileEntSecurity, ITickable {

	protected UUID owner;
	
	protected static final int NUMTICKS=60;
	protected int ticks=NUMTICKS;
	
	public byte size =1;
	
	@Override
	public void setOwner(EntityPlayer ply) {
		UUID id = ply.getGameProfile().getId();
		if (id != null) {
			this.owner = id;
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
		return owner;
	}

	@Override
	public byte getSecurity() {
		return 0;
	}

	/**
	 * Return the bioblob size [1-3]
	 * @return
	 */
	public int getBlobSize(){
		return size;
	}
	
	@Override
	public void tick() {
		this.ticks--;
		if (ticks <=0){
		
			if (this.size>1){
				size--;
				this.ticks = NUMTICKS;
				if(!this.world.isRemote){
					this.needUpdate();
				}
			} else {
				//remove blob
				this.world.setBlockState(this.pos,Blocks.AIR.getDefaultState());
			}
			
		}
		
	}
	
	public void needUpdate(){
		this.world.markBlockRangeForRenderUpdate(getPos(), getPos());
		this.markDirty();
	}
	
	@Override
	public SPacketUpdateTileEntity getUpdatePacket() {
		NBTTagCompound tags = new NBTTagCompound();
		this.writeToNBT(tags);
		return new SPacketUpdateTileEntity(pos, 1, tags);
	}

	@Override
	public void onDataPacket(NetworkManager net, SPacketUpdateTileEntity packet) {
		this.readFromNBT(packet.getNbtCompound());
	}

	@Override
	public NBTTagCompound getUpdateTag() {
		NBTTagCompound tags= super.getUpdateTag();
		this.writeToNBT(tags);
		return tags;
	}

	@Override
	public void handleUpdateTag(NBTTagCompound tag) {
		super.handleUpdateTag(tag);
		this.readFromNBT(tag);
	}

	@Override
	public void readFromNBT(NBTTagCompound tags) {
		super.readFromNBT(tags);
		byte oldSize = size;
		ticks=tags.getInteger("BlobTicks");
		size= tags.getByte("size");
		if (this.world!=null && this.world.isRemote && size!=oldSize){
			this.world.markBlockRangeForRenderUpdate(this.pos, this.pos);
		}
	}

	@Override
	public NBTTagCompound writeToNBT(NBTTagCompound compound) {
		NBTTagCompound tags= super.writeToNBT(compound);
		tags.setInteger("BlobTicks", ticks);
		tags.setByte("size", size);
		return tags;
	}

	

}
