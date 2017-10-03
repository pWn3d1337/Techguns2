package techguns.gui.containers;

import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.IContainerListener;
import techguns.tileentities.BasicInventoryTileEnt;
import techguns.tileentities.BasicMachineTileEnt;

public abstract class BasicMachineContainer extends RedstoneTileContainer {	
	protected int lastProgress = 0;
	protected int lastTotaltime = 0;
	protected int lastPowerStored = 0;
	
	protected BasicMachineTileEnt tile;
	
	public static final int FIELD_SYNC_ID_PROGRESS = FIELD_SYNC_ID_REDSTONE+1;
	public static final int FIELD_SYNC_ID_TOTALTIME = FIELD_SYNC_ID_REDSTONE+2;
	public static final int FIELD_SYNC_ID_POWER_STORED = FIELD_SYNC_ID_REDSTONE+3;
	
	public BasicMachineContainer(InventoryPlayer player, BasicMachineTileEnt ent) {
		super(player, ent);
		this.tile=ent;
	}

	@Override
	public void detectAndSendChanges() {
		super.detectAndSendChanges();
		
        for (int j = 0; j < this.listeners.size(); ++j)
        {
            IContainerListener listener = this.listeners.get(j);
            
            if (this.lastProgress!=this.tile.progress) {
            	listener.sendWindowProperty(this, FIELD_SYNC_ID_PROGRESS, this.tile.progress);
            }
            
            if (this.lastTotaltime!=this.tile.totaltime) {
            	listener.sendWindowProperty(this, FIELD_SYNC_ID_TOTALTIME, this.tile.totaltime);
            }
            
            if (this.tile.isWorking() || (this.lastPowerStored!=this.tile.getEnergyStorage().getEnergyStored())) {
            	listener.sendWindowProperty(this, FIELD_SYNC_ID_POWER_STORED, (int) (this.tile.getEnergyStorage().getEnergyStored()*0.01));
            }
        }
        this.lastProgress=this.tile.progress;
        this.lastTotaltime=this.tile.totaltime;
        this.lastPowerStored=this.tile.getEnergyStorage().getEnergyStored();
	}

	@Override
	public void updateProgressBar(int id, int data) {
		switch(id) {
			case FIELD_SYNC_ID_PROGRESS:
				this.tile.progress=data;
				break;
			case FIELD_SYNC_ID_TOTALTIME:
				this.tile.totaltime=data;
				break;
			case FIELD_SYNC_ID_POWER_STORED:
				this.tile.getEnergyStorage().setEnergyStored(data*100);
				break;
			default:
				super.updateProgressBar(id, data);
		}
	}

	
}
