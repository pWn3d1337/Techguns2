package techguns.capabilities;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import techguns.entities.npcs.GenericNPC;
import techguns.tileentities.TGSpawnerTileEnt;

public class TGGenericNPCData {
	
	protected BlockPos spawnerPos;
	
	public boolean hasSpawner() {
		return spawnerPos!=null;
	}

	public BlockPos getSpawnerPos() {
		return spawnerPos;
	}
	
	public void setSpawnerPos(BlockPos spawnerPos) {
		this.spawnerPos = spawnerPos;
	}

	public static TGGenericNPCData get(GenericNPC npc){
		return (TGGenericNPCData) npc.getCapability(TGGenericNPCDataCapProvider.TG_GENERICNPC_DATA, null);
	}
	
	public void tryRelink(GenericNPC npc) {
		if(this.hasSpawner()) {
			TileEntity tile = npc.world.getTileEntity(this.getSpawnerPos());
			if(tile!=null && tile instanceof TGSpawnerTileEnt) {
				((TGSpawnerTileEnt)tile).relinkNPC(npc);
			}
		}
	}
	
	public void saveToNBT(final NBTTagCompound tags) {
		if(spawnerPos!=null) {
			tags.setBoolean("hasSpawner", true);
			tags.setInteger("spawnerX", spawnerPos.getX());
			tags.setInteger("spawnerY", spawnerPos.getY());
			tags.setInteger("spawnerZ", spawnerPos.getZ());
		}
	}
	public void loadFromNBT(final NBTTagCompound tags) {
		if(tags.getBoolean("hasSpawner")) {
			int x = tags.getInteger("spawnerX");
			int y = tags.getInteger("spawnerY");
			int z = tags.getInteger("spawnerZ");
			this.spawnerPos = new BlockPos(x, y, z);
		}
	}
	
}
