package techguns.gui.containers;

import net.minecraft.entity.player.InventoryPlayer;
import techguns.tileentities.DungeonGeneratorTileEnt;

public class DungeonGeneratorContainer extends OwnedTileContainer {

	DungeonGeneratorTileEnt tile;
	
	public DungeonGeneratorContainer(InventoryPlayer player, DungeonGeneratorTileEnt ent) {
		super(player, ent);
		this.tile=ent;
	}

}
