package techguns.gui.containers;

import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.IContainerListener;
import techguns.tileentities.BasicOwnedTileEnt;
import techguns.tileentities.BasicPoweredTileEnt;

public abstract class PoweredTileContainer extends OwnedTileContainer {

	BasicPoweredTileEnt tile;
	
	protected byte lastRedstoneMode=0;
	
	public static final int FIELD_SYNC_ID_REDSTONE=FIELD_SYNC_ID_SECURITY+1;
	
	public PoweredTileContainer(InventoryPlayer player, BasicPoweredTileEnt ent) {
		super(player, ent);
		this.tile=ent;
	}

	@Override
	public void detectAndSendChanges() {
		super.detectAndSendChanges();
		
        for (int j = 0; j < this.listeners.size(); ++j)
        {
            IContainerListener listener = this.listeners.get(j);
            
            if (this.lastRedstoneMode!=this.tile.getRedstoneBehaviour()) {
            	listener.sendWindowProperty(this, FIELD_SYNC_ID_REDSTONE, this.tile.getRedstoneBehaviour());
            }

            
        }
        this.lastRedstoneMode=this.tile.getRedstoneBehaviour();
	}

	@Override
	public void updateProgressBar(int id, int data) {
		if (id == FIELD_SYNC_ID_REDSTONE) {
			this.tile.setRedstoneBehaviour((byte) data);
		} else {
			super.updateProgressBar(id, data);
		}
	}
	
}
