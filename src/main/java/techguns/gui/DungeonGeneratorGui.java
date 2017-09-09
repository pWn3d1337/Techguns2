package techguns.gui;

import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.util.ResourceLocation;
import techguns.Techguns;
import techguns.gui.containers.DungeonGeneratorContainer;
import techguns.tileentities.BasicOwnedTileEnt;
import techguns.tileentities.DungeonGeneratorTileEnt;
import techguns.tileentities.DungeonScannerTileEnt;

public class DungeonGeneratorGui extends OwnedTileEntGui {

public static final ResourceLocation texture = new ResourceLocation(Techguns.MODID,"textures/gui/dungeon_scanner_gui.png");// new
	
	protected DungeonGeneratorTileEnt tileEnt;
	
	public DungeonGeneratorGui(InventoryPlayer ply, DungeonGeneratorTileEnt tile) {
		super(new DungeonGeneratorContainer(ply, tile), tile);
		this.tex=texture;
		this.tileEnt=tile;
	}

	
}
