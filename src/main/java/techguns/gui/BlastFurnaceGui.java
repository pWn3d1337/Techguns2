package techguns.gui;

import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.util.ResourceLocation;
import techguns.TGConfig;
import techguns.Techguns;
import techguns.gui.containers.BlastFurnaceContainer;
import techguns.tileentities.BasicPoweredTileEnt;
import techguns.tileentities.BlastFurnaceTileEnt;
import techguns.util.TextUtil;

public class BlastFurnaceGui extends PoweredTileEntGui {

	public static final ResourceLocation texture = new ResourceLocation(Techguns.MODID,"textures/gui/blast_furnace_gui.png");
	
	protected BlastFurnaceTileEnt tile;
	
	public BlastFurnaceGui(InventoryPlayer ply,BlastFurnaceTileEnt tileent) {
		super(new BlastFurnaceContainer(ply, tileent), tileent);
		this.tile=tileent;
		this.tex=texture;
		this.upgradeSlotX=BlastFurnaceContainer.SLOT_UPGRADE_X-1;
		this.upgradeSlotY=BlastFurnaceContainer.SLOT_UPGRADE_Y-1;
	}

	@Override
	protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY) {
		super.drawGuiContainerBackgroundLayer(partialTicks, mouseX, mouseY);
		int k = (this.width - this.xSize) / 2;
		int l = (this.height - this.ySize) / 2;
		
		if (this.tile.isWorking()) {
			Minecraft.getMinecraft().getTextureManager().bindTexture(texture);
			int i1 = this.tile.getProgressScaled(90);
			this.drawTexturedModalRect(k + 19, l + 53, 0, 167, i1 + 1, 11);
		}

	}

	/*@Override
	protected void drawGuiContainerForegroundLayer(int mouseX, int mouseY) {
		super.drawGuiContainerForegroundLayer(mouseX, mouseY);
		
		int mx = mouseX - (this.width-this.xSize)/2;
        int my = mouseY - (this.height-this.ySize)/2;
		
		 if (isInRect(mx,my,19,53,90,11)){

			 if(this.tile.currentOperation!=null) {
				 this.drawHoveringText(this.tile.progress+"/"+this.tile.totaltime, mx, my);
			 }
        }
	}*/
	
	
}
