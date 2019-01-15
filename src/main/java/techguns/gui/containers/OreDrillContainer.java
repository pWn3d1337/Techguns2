package techguns.gui.containers;

import net.minecraft.entity.player.InventoryPlayer;
import techguns.tileentities.BasicMachineTileEnt;
import techguns.tileentities.OreDrillTileEntMaster;

public class OreDrillContainer extends BasicMachineContainer {

	OreDrillTileEntMaster tile;
	
	public OreDrillContainer(InventoryPlayer player, OreDrillTileEntMaster ent) {
		super(player, ent);
		tile=ent;
		

		this.addPlayerInventorySlots(player);
	}

}
