package techguns.gui;

import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.util.ResourceLocation;
import techguns.Techguns;
import techguns.gui.containers.BlastFurnaceContainer;
import techguns.gui.containers.GrinderContainer;
import techguns.tileentities.BasicPoweredTileEnt;
import techguns.tileentities.GrinderTileEnt;

public class GrinderGui extends PoweredTileEntGui {
	public static final ResourceLocation texture = new ResourceLocation(Techguns.MODID,"textures/gui/grinder_gui.png");
	
	protected GrinderTileEnt tile;
	public GrinderGui(InventoryPlayer ply, GrinderTileEnt tile) {
		super(new GrinderContainer(ply, tile), tile);
		
		this.tile=tile;
		this.tex=texture;
		this.upgradeSlotX=GrinderContainer.SLOT_UPGRADE_X-1;
		this.upgradeSlotY=GrinderContainer.SLOT_UPGRADE_Y-1;
	}

	@Override
	protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY) {
		super.drawGuiContainerBackgroundLayer(partialTicks, mouseX, mouseY);
		int k = (this.width - this.xSize) / 2;
		int l = (this.height - this.ySize) / 2;
		
		if (this.tile.isWorking()) {
			Minecraft.getMinecraft().getTextureManager().bindTexture(texture);
			int i1 = this.tile.getProgressScaled(21);
			this.drawTexturedModalRect(k + 31, l + 39, 0, 167, i1 + 1, 22);
			
			int i2 = 20-i1;
			this.drawTexturedModalRect(k + 31+20+i2, l + 39, 0+i2, 167, i1+1, 22);
		}

	}
}
