package techguns.gui.containers;

import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.IContainerListener;
import techguns.tileentities.BasicOwnedTileEnt;
import techguns.tileentities.Door3x3TileEntity;

public class Door3x3Container extends OwnedTileContainer {

	public static final int FIELD_SYNC_ID_DOORMODE=FIELD_SYNC_ID_SECURITY+1;
	public static final int FIELD_SYNC_ID_AUTCLOSE=FIELD_SYNC_ID_SECURITY+2;
	
	Door3x3TileEntity tile;
	
	protected byte lastDoormode=0;
	protected boolean lastAutoclosemode=false;

	
	public Door3x3Container(InventoryPlayer player, Door3x3TileEntity ent) {
		super(player, ent);
		this.tile=ent;
	}

	@Override
	public void detectAndSendChanges() {
		super.detectAndSendChanges();
		
        for (int j = 0; j < this.listeners.size(); ++j)
        {
            IContainerListener listener = this.listeners.get(j);
            
            if (this.lastDoormode!=this.tile.getDoormode()) {
            	listener.sendWindowProperty(this, FIELD_SYNC_ID_DOORMODE, this.tile.getDoormode());
            }
            if (this.lastAutoclosemode!=this.tile.isAutoClose()) {
            	listener.sendWindowProperty(this, FIELD_SYNC_ID_AUTCLOSE, this.tile.isAutoClose()?1:0);
            }
            
        }
        this.lastDoormode=this.tile.getDoormode();
        this.lastAutoclosemode=this.tile.isAutoClose();
	}

	@Override
	public void updateProgressBar(int id, int data) {
		if ( id == FIELD_SYNC_ID_AUTCLOSE) {
			this.tile.setAutoClose(data>0);
		} else if (id == FIELD_SYNC_ID_DOORMODE) {
			this.tile.setDoormode((byte) data);
		} else {
			super.updateProgressBar(id, data);
		}
	}
	

}
