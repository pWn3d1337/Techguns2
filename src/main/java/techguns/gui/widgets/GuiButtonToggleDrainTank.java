package techguns.gui.widgets;

import net.minecraft.client.Minecraft;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.client.config.GuiButtonExt;
import net.minecraftforge.fml.client.config.GuiUtils;
import techguns.Techguns;
import techguns.tileentities.ChemLabTileEnt;

public class GuiButtonToggleDrainTank extends GuiButtonExt {
	private static final ResourceLocation texture = new ResourceLocation(Techguns.MODID,"textures/gui/chem_lab_gui.png");
	private ChemLabTileEnt tileent;
	
	public GuiButtonToggleDrainTank(int id, int xPos, int yPos, int width, int height, ChemLabTileEnt tileEnt) {
		super(id, xPos, yPos, width, height, "");
		this.tileent=tileEnt;
	}
	
	@Override
	public void drawButton(Minecraft mc, int mouseX, int mouseY, float partial) {
		 if (this.visible)
	        {
	            this.hovered = mouseX >= this.x && mouseY >= this.y && mouseX < this.x + this.width && mouseY < this.y + this.height;
	            int k = this.getHoverState(this.hovered);
	            GuiUtils.drawContinuousTexturedBox(BUTTON_TEXTURES, this.x, this.y, 0, 46 + k * 20, this.width, this.height, 200, 20, 2, 3, 2, 2, this.zLevel);
	            this.mouseDragged(mc, mouseX, mouseY);
	           
	            Minecraft.getMinecraft().renderEngine.bindTexture(texture);
	            byte mode = tileent.getDrainMode();
	            this.drawTexturedModalRect(this.x+1, this.y+1, 188, 31+(12*mode), 12, 12);
	          
	        }
	}
}
