package techguns.capabilities;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import techguns.entities.npcs.ITGSpawnerNPC;
import techguns.tileentities.TGSpawnerTileEnt;

public class TGSpawnerNPCData {
	
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

	public static TGSpawnerNPCData get(ITGSpawnerNPC npc){
		return (TGSpawnerNPCData) npc.getCapability(TGSpawnerNPCDataCapProvider.TG_GENERICNPC_DATA);
	}
	
	public void tryRelink(World w, ITGSpawnerNPC npc) {
		if(this.hasSpawner()) {
			TileEntity tile = w.getTileEntity(this.getSpawnerPos());
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
