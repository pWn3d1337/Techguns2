package techguns.gui.containers;

import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.IContainerListener;
import techguns.tileentities.BasicOwnedTileEnt;

public abstract class OwnedTileContainer extends TGBaseContainer {

	protected BasicOwnedTileEnt tile;
	protected byte lastSecurity = 0;
	
	public static final int FIELD_SYNC_ID_SECURITY=0;
	
	public OwnedTileContainer(InventoryPlayer player, BasicOwnedTileEnt ent) {
		super(player, ent);
		this.tile=ent;
	}

	@Override
	public void detectAndSendChanges() {
		super.detectAndSendChanges();
		
        for (int j = 0; j < this.listeners.size(); ++j)
        {
            IContainerListener listener = this.listeners.get(j);
            
            if (this.lastSecurity!=this.tile.getSecurity()) {
            	listener.sendWindowProperty(this, FIELD_SYNC_ID_SECURITY, this.tile.getSecurity());
            }

            
        }
        this.lastSecurity=this.tile.getSecurity();
	}

	@Override
	public void updateProgressBar(int id, int data) {
		if (id == FIELD_SYNC_ID_SECURITY) {
			this.tile.setSecurity((byte) data);
		} else {
			super.updateProgressBar(id, data);
		}
	}
}
