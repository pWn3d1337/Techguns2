package techguns.gui;

import net.minecraft.client.gui.GuiButton;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.util.ResourceLocation;
import techguns.Techguns;
import techguns.gui.containers.DungeonScannerContainer;
import techguns.tileentities.BasicOwnedTileEnt;
import techguns.tileentities.DungeonScannerTileEnt;

public class DungeonScannerGui extends OwnedTileEntGui {

	public static final ResourceLocation texture = new ResourceLocation(Techguns.MODID,"textures/gui/ammo_press_gui.png");// new
	
	public DungeonScannerGui(InventoryPlayer ply, DungeonScannerTileEnt tile) {
		super(new DungeonScannerContainer(ply, tile), tile);
		this.tex=texture;
	}

	@Override
	protected void drawGuiContainerForegroundLayer(int mouseX, int mouseY) {
		// TODO Auto-generated method stub
		super.drawGuiContainerForegroundLayer(mouseX, mouseY);
	}

	@Override
	protected void actionPerformed(GuiButton guibutton) {
		// TODO Auto-generated method stub
		super.actionPerformed(guibutton);
	}

	@Override
	protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY) {
		// TODO Auto-generated method stub
		super.drawGuiContainerBackgroundLayer(partialTicks, mouseX, mouseY);
	}

	@Override
	public void initGui() {
		// TODO Auto-generated method stub
		super.initGui();
	}
	
	

}
