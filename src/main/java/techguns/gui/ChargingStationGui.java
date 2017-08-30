package techguns.gui;

import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.util.ResourceLocation;
import techguns.Techguns;
import techguns.gui.containers.ChargingStationContainer;
import techguns.tileentities.BasicPoweredTileEnt;
import techguns.tileentities.ChargingStationTileEnt;

public class ChargingStationGui extends PoweredTileEntGui {

	public static final ResourceLocation texture = new ResourceLocation(Techguns.MODID,"textures/gui/charging_station_gui.png");
	
	ChargingStationTileEnt tile;
	
	public ChargingStationGui(InventoryPlayer ply, ChargingStationTileEnt tile) {
		super(new ChargingStationContainer(ply, tile), tile);
		this.tile=tile;
		this.tex=texture;
	}

	@Override
	protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY) {
		super.drawGuiContainerBackgroundLayer(partialTicks, mouseX, mouseY);
		int k = (this.width - this.xSize) / 2;
		int l = (this.height - this.ySize) / 2;
		
		 if (this.tile.isWorking()){
			Minecraft.getMinecraft().getTextureManager().bindTexture(texture);
	        int i1 = this.tile.getProgressScaled(26);
	        this.drawTexturedModalRect(k + 39, l + 19, 0, 167, i1+1, 10);
        }
	}
	

}
