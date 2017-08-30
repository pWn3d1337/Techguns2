package techguns.gui.widgets;

import net.minecraft.client.Minecraft;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.client.config.GuiButtonExt;
import net.minecraftforge.fml.client.config.GuiUtils;
import techguns.Techguns;
import techguns.api.machines.ITGTileEntSecurity;

public class GuiButtonSecurity extends GuiButtonExt {

	protected ITGTileEntSecurity tileent;
	public static final ResourceLocation texture_security = new ResourceLocation(Techguns.MODID,"textures/gui/ammo_press_gui.png");//new ResourceLocation("textures/gui/container/ammopressgui.png");
	
	
	public GuiButtonSecurity(int id, int xPos, int yPos, int width, int height, String displayString,ITGTileEntSecurity tile) {
		super(id, xPos, yPos, width, height, displayString);
		this.tileent=tile;
	}
		
	@Override
	public void drawButton(Minecraft mc, int mouseX, int mouseY, float partial) {
		 if (this.visible)
	        {
	            this.hovered = mouseX >= this.x && mouseY >= this.y && mouseX < this.x + this.width && mouseY < this.y + this.height;
	            int k = this.getHoverState(this.hovered);
	            GuiUtils.drawContinuousTexturedBox(BUTTON_TEXTURES, this.x, this.y, 0, 46 + k * 20, this.width, this.height, 200, 20, 2, 3, 2, 2, this.zLevel);
	            this.mouseDragged(mc, mouseX, mouseY);
	           
	            Minecraft.getMinecraft().renderEngine.bindTexture(texture_security);
	            byte mode = tileent.getSecurity();
	            this.drawTexturedModalRect(this.x+2, this.y+2, 234, 58+(16*mode), 16, 16);
	          
	        }
	}

	
}