package techguns.entities.npcs;

import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.common.capabilities.Capability;
import techguns.capabilities.TGSpawnerNPCData;
import techguns.tileentities.TGSpawnerTileEnt;

/**
 * Interface with default methods to emulate multi-inheritance for NPC classes
 */
public interface ITGSpawnerNPC {

	boolean getTryLink();
	void setTryLink(boolean value);
	
	/**
	 * call this in Entity onUpdate()
	 * @param w
	 */
	default void onUpdateSpawner(World w) {
		if(getTryLink()) {
			setTryLink(false);
			if(!w.isRemote) {
				TGSpawnerNPCData dat = TGSpawnerNPCData.get(this);
				if(dat!=null) {
					dat.tryRelink(w, this);
				}
			}
		}
	}
	
	/**
	 * call this in onDeath
	 * @param w
	 * @param isDead
	 */
	default void onDeathSpawner(World w, boolean dead) {
		if(!w.isRemote && dead) {
			TGSpawnerNPCData dat = TGSpawnerNPCData.get(this);
			if(dat!=null) {
				BlockPos spawner = dat.getSpawnerPos();
				if(spawner!=null) {
					TileEntity tile = w.getTileEntity(spawner);
					if (tile!=null && tile instanceof TGSpawnerTileEnt) {
						((TGSpawnerTileEnt)tile).killedEntity(this);
					}
				}
			}
		}
	}
	
	/**
	 * call this in despawnEntity()
	 * @param w
	 * @param isDead
	 */
	default void despawnEntitySpawner(World w, boolean dead) {
		if(!w.isRemote && dead) {
			TGSpawnerNPCData dat = TGSpawnerNPCData.get(this);
			if(dat!=null) {
				BlockPos spawner = dat.getSpawnerPos();
				if(spawner!=null) {
					TileEntity tile = w.getTileEntity(spawner);
					if (tile!=null && tile instanceof TGSpawnerTileEnt) {
						((TGSpawnerTileEnt)tile).despawnedEntity(this);
					}
				}
			}
		}
	}
	
	TGSpawnerNPCData getCapability(Capability<TGSpawnerNPCData> tgGenericnpcData);
}
