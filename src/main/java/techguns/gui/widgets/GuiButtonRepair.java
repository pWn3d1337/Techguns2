package techguns.gui.widgets;

import net.minecraft.client.Minecraft;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.client.config.GuiButtonExt;
import net.minecraftforge.fml.client.config.GuiUtils;
import techguns.Techguns;

public class GuiButtonRepair extends GuiButtonExt {
	private static final ResourceLocation texture = new ResourceLocation(Techguns.MODID, "textures/gui/repair_bench_gui.png");
	
	public GuiButtonRepair(int id, int xPos, int yPos, int width, int height) {
		super(id, xPos, yPos, width, height, "");
	}
	
	@Override
	public void drawButton(Minecraft mc, int mouseX, int mouseY, float partial) {
		if (this.visible) {
			this.hovered = mouseX >= this.x && mouseY >= this.y && mouseX < this.x + this.width && mouseY < this.y + this.height;
			int k = this.getHoverState(this.hovered);
			GuiUtils.drawContinuousTexturedBox(BUTTON_TEXTURES, this.x, this.y, 0, 46 + k * 20, this.width, this.height, 200, 20, 2, 3, 2, 2, this.zLevel);
			this.mouseDragged(mc, mouseX, mouseY);
			
			Minecraft.getMinecraft().getTextureManager().bindTexture(texture);
			this.drawTexturedModalRect(this.x+1, this.y+1, 176, 0, 12, 12);
		}
	}
}