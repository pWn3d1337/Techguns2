package techguns.tileentities;

import java.util.List;

import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.server.management.PlayerChunkMapEntry;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import techguns.TGPackets;
import techguns.blocks.BlockTGDoor3x3;
import techguns.packets.PacketDoorStateChange;
import techguns.packets.PacketUpdateTileEntTanks;

public class Door3x3TileEntity extends BasicOwnedTileEnt {

	protected int textureType=0;
	protected long lastStateChangeTime=0L;
	
	public Door3x3TileEntity() {
		super(0, false);
	}

	@Override
	public void readClientDataFromNBT(NBTTagCompound tags) {
		super.readClientDataFromNBT(tags);
		this.textureType=tags.getByte("texturetype");
	}

	@Override
	public void writeClientDataToNBT(NBTTagCompound tags) {
		super.writeClientDataToNBT(tags);
		tags.setByte("texturetype",(byte) this.textureType);
	}

	@Override
	public boolean shouldRefresh(World world, BlockPos pos, IBlockState oldState, IBlockState newState) {
		return !((oldState.getBlock()==newState.getBlock()) && (newState.getValue(BlockTGDoor3x3.MASTER) && oldState.getValue(BlockTGDoor3x3.MASTER) && 
				(oldState.getValue(BlockTGDoor3x3.ZPLANE)==newState.getValue(BlockTGDoor3x3.ZPLANE))));
	}
	
	@Override
	public boolean hasFastRenderer() {
		return true;
	}

	public long getLastStateChangeTime() {
		return lastStateChangeTime;
	}
	
	public void setLastStateChangeTime(long lastStateChangeTime) {
		this.lastStateChangeTime = lastStateChangeTime;
	}

	/**
	 * Send out timing to clients
	 */
	public void changeStateServerSide() {
		if (!this.world.isRemote) {
			ChunkPos cp = this.world.getChunkFromBlockCoords(getPos()).getPos();
			PlayerChunkMapEntry entry = ((WorldServer) this.world).getPlayerChunkMap().getEntry(cp.x, cp.z);
	
			try {
				List<EntityPlayerMP> players = (List<EntityPlayerMP>) ReactionChamberTileEntMaster.playerChunkMapEntry_Players.get(entry);
				IMessage packet = new PacketDoorStateChange(this);
				for (EntityPlayerMP entityplayermp : players) {
					TGPackets.network.sendTo(packet, entityplayermp);
				}
			} catch (IllegalArgumentException e) {
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			}
		}
	}
}
