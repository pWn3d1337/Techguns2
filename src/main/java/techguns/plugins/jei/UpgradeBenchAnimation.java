package techguns.plugins.jei;

import java.awt.Color;

import mezz.jei.api.IGuiHelper;
import mezz.jei.api.gui.IDrawableAnimated;
import mezz.jei.api.gui.ITickTimer;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import techguns.gui.ChemLabGui;
import techguns.gui.UpgradeBenchGui;

public class UpgradeBenchAnimation implements IDrawableAnimated {

	protected ITickTimer timer;
	protected ITickTimer timer_hue;
	protected IGuiHelper guiHelper;
	
	protected int w;
	protected int h;
	
	
	
	public UpgradeBenchAnimation(IGuiHelper guiHelper, int w, int h) {
		this.w = w;
		this.h = h;
		
		this.guiHelper = guiHelper;
		this.timer = guiHelper.createTickTimer(3, 3, true);
		this.timer_hue = guiHelper.createTickTimer(359, 359, false);
	}

	@Override
	public int getWidth() {
		return w;
	}

	@Override
	public int getHeight() {
		return h;
	}

	@Override
	public void draw(Minecraft minecraft, int xOffset, int yOffset) {
		minecraft.getTextureManager().bindTexture(UpgradeBenchGui.texture);
		
		int k = xOffset;
		int l = yOffset;
		
		float hue = this.timer_hue.getValue()/360f;
		int i = this.timer.getValue();
		
		int x = i>=2 ? 0 : 89;
		int y = i%2==1 ? 0 : 31;
		
		Color c = new Color(Color.HSBtoRGB(hue, 1f, 1f));
		
		drawModalRectWithCustomSizedTextureWithColor(k + 37, l + 40, 0+x, 167 +y, 89, 31, 256, 256, c.getRed(),c.getGreen(),c.getBlue(),255);
	}

    /**
     * Draws a textured rectangle at z = 0. Args: x, y, u, v, width, height, textureWidth, textureHeight
     */
    protected static void drawModalRectWithCustomSizedTextureWithColor(int x, int y, float u, float v, int width, int height, float textureWidth, float textureHeight, int r, int g, int b, int a)
    {
        float f = 1.0F / textureWidth;
        float f1 = 1.0F / textureHeight;
        Tessellator tessellator = Tessellator.getInstance();
        BufferBuilder bufferbuilder = tessellator.getBuffer();
        bufferbuilder.begin(7, DefaultVertexFormats.POSITION_TEX_COLOR);
        bufferbuilder.pos((double)x, (double)(y + height), 0.0D).tex((double)(u * f), (double)((v + (float)height) * f1)).color(r, g, b, a).endVertex();
        bufferbuilder.pos((double)(x + width), (double)(y + height), 0.0D).tex((double)((u + (float)width) * f), (double)((v + (float)height) * f1)).color(r, g, b, a).endVertex();
        bufferbuilder.pos((double)(x + width), (double)y, 0.0D).tex((double)((u + (float)width) * f), (double)(v * f1)).color(r, g, b, a).endVertex();
        bufferbuilder.pos((double)x, (double)y, 0.0D).tex((double)(u * f), (double)(v * f1)).color(r, g, b, a).endVertex();
        tessellator.draw();
    }
}
