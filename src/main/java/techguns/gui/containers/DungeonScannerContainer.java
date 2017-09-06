package techguns.gui.containers;

import net.minecraft.entity.player.InventoryPlayer;
import techguns.tileentities.BasicOwnedTileEnt;
import techguns.tileentities.DungeonScannerTileEnt;

public class DungeonScannerContainer extends OwnedTileContainer{

	DungeonScannerTileEnt tile;
	
	public DungeonScannerContainer(InventoryPlayer player, DungeonScannerTileEnt ent) {
		super(player, ent);
		this.tile=ent;
	}

}
