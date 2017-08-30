package techguns.gui;

import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.ResourceLocation;
import techguns.Techguns;
import techguns.gui.containers.FabricatorContainer;
import techguns.tileentities.FabricatorTileEntMaster;

public class FabricatorGui extends PoweredTileEntGui {

	public static final ResourceLocation texture = new ResourceLocation(Techguns.MODID,"textures/gui/fabricator_gui.png");

	protected FabricatorTileEntMaster tile;
	
	public FabricatorGui(InventoryPlayer ply,FabricatorTileEntMaster tileent) {
		super(new FabricatorContainer(ply,tileent),tileent);
		this.tile=tileent;
		this.tex=texture;
		this.upgradeSlotX=FabricatorContainer.SLOT_UPGRADE_X-1;
		this.upgradeSlotY=FabricatorContainer.SLOT_UPGRADE_Y-1;
	}

	@Override
	protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY) {
		super.drawGuiContainerBackgroundLayer(partialTicks, mouseX, mouseY);
		int k = (this.width - this.xSize) / 2;
		int l = (this.height - this.ySize) / 2;
		
		if (this.tile.isWorking()) {
			Minecraft.getMinecraft().getTextureManager().bindTexture(texture);
			int i1 = this.tile.getProgressScaled(90);
			this.drawTexturedModalRect(k + 21, l + 53, 0, 167, i1 + 1, 11);
		}

	}

	
}
