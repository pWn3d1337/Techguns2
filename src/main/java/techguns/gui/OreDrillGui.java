package techguns.gui;

import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.util.ResourceLocation;
import techguns.Techguns;
import techguns.gui.containers.OreDrillContainer;
import techguns.tileentities.BasicPoweredTileEnt;
import techguns.tileentities.FabricatorTileEntMaster;
import techguns.tileentities.OreDrillTileEntMaster;

public class OreDrillGui extends PoweredTileEntGui {
	public static final ResourceLocation texture = new ResourceLocation(Techguns.MODID,"textures/gui/ore_drill_gui.png");

	protected OreDrillTileEntMaster tile;
	
	public OreDrillGui(InventoryPlayer ply, OreDrillTileEntMaster tileent) {
		super(new OreDrillContainer(ply, tileent), tileent);
		this.tex=texture;
		this.tile=tileent;
		this.hasUpgradeSlot=false;
	}

	@Override
	protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY) {
		super.drawGuiContainerBackgroundLayer(partialTicks, mouseX, mouseY);
		int k = (this.width - this.xSize) / 2;
		int l = (this.height - this.ySize) / 2;
		
		if (this.tile.isWorking()) {
			Minecraft.getMinecraft().getTextureManager().bindTexture(texture);
			int i1 = this.tile.getProgressScaled(36);
	        this.drawTexturedModalRect(k + 73, l + 17, 177, 1, 25, i1+1);
		}

	}

}
