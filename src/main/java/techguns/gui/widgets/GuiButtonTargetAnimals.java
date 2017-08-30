package techguns.gui.widgets;

import net.minecraft.client.Minecraft;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.client.config.GuiButtonExt;
import net.minecraftforge.fml.client.config.GuiUtils;
import techguns.Techguns;
import techguns.tileentities.TurretTileEnt;

public class GuiButtonTargetAnimals extends GuiButtonExt {

	public static final ResourceLocation texture = new ResourceLocation(Techguns.MODID,"textures/gui/turret_base_gui.png");
	
	protected TurretTileEnt tile;
	
	public GuiButtonTargetAnimals(int id, int xPos, int yPos, int width, int height, TurretTileEnt tile) {
		super(id, xPos, yPos, width, height, "");
		this.tile=tile;
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
	            byte mode = (byte) (tile.attackAnimals?1:0);
	            this.drawTexturedModalRect(this.x+2, this.y+2, 176, 6+(16*mode), 16, 16);
	          
	        }
	}

}
