package techguns.gui;

import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.client.config.GuiButtonExt;
import techguns.Techguns;
import techguns.gui.containers.MetalPressContainer;
import techguns.tileentities.BasicPoweredTileEnt;
import techguns.tileentities.MetalPressTileEnt;
import techguns.util.TextUtil;

public class MetalPressGui extends PoweredTileEntGui {
	public static final ResourceLocation texture = new ResourceLocation(Techguns.MODID,"textures/gui/metal_press_gui.png");
	
	MetalPressTileEnt tile;
	
	public MetalPressGui(InventoryPlayer ply, MetalPressTileEnt tileent) {
		super(new MetalPressContainer(ply, tileent),tileent);
		this.tile = tileent;
		this.tex = texture;
	}

	@Override
	protected void drawGuiContainerForegroundLayer(int mouseX, int mouseY) {
		int x = 0;
		int y = 0;
		int mx = mouseX - (this.width - this.xSize) / 2;
		int my = mouseY - (this.height - this.ySize) / 2;
		int color = 4210752; // 0xff101010;

		String s2 = "";
		switch (this.tile.getAutoSplitMode()) {
		case 0:
			s2 = TextUtil.trans("techguns.container.info.off");
			break;
		case 1:
			s2 = TextUtil.trans("techguns.container.info.on");
			break;
		}
		this.fontRenderer.drawString(TextUtil.trans("techguns.container.metalpress.autosplit") + ":", x + 20, y + 30, color);
		this.fontRenderer.drawString(s2, x + 20, y + 40, color);

		super.drawGuiContainerForegroundLayer(mouseX, mouseY);
	}
	
	@Override
	protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY) {
		super.drawGuiContainerBackgroundLayer(partialTicks, mouseX, mouseY);
		
		int k = (this.width - this.xSize) / 2;
        int l = (this.height - this.ySize) / 2;
		
		if (this.tile.isWorking()) {
			int i1 = this.tile.getProgressScaled(21);
			// this.drawTexturedModalRect(k + 79, l + 34, 176, 14, i1 + 1, 16);
			this.drawTexturedModalRect(k + 119, l + 36, 176, 0, 19, i1 + 1);
		}

	}

	@Override
	public void initGui() {
		super.initGui();
		this.buttonList.add(new GuiButtonExt(MetalPressTileEnt.BUTTON_ID_AUTOSPLIT, this.guiLeft+20, this.guiTop+50, 40, 20, TextUtil.trans("techguns.button.switch")));
	}
	
}
