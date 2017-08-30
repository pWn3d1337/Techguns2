package techguns.plugins.jei;

import mezz.jei.api.IGuiHelper;
import mezz.jei.api.gui.IDrawableAnimated;
import mezz.jei.api.gui.ITickTimer;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import techguns.gui.ChemLabGui;

public class ProgressBarChemLab implements IDrawableAnimated {

	protected ITickTimer timer;
	protected IGuiHelper guiHelper;
	
	protected int w;
	protected int h;
	
	public ProgressBarChemLab(IGuiHelper guiHelper, int w, int h) {
		super();
		this.guiHelper = guiHelper;
		this.timer = guiHelper.createTickTimer(100, 100, false);
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
		
		int k = xOffset-81;
		int l = yOffset-17;
		
		minecraft.getTextureManager().bindTexture(ChemLabGui.texture);
		
		float prog = ((float)this.timer.getValue())/((float)this.timer.getMaxValue());
		int i1 = (int) ((prog < 0.2f ? (prog * 5) : 1.0f) * 25);
		
		this.drawTexturedModalRect(k + 81, l + 19, 178, 5, 8, i1);

		if (prog >= 0.2f) {
			int i2 = (int) ((prog < 0.4f ? (prog - 0.2f) * 5 : 1.0f) * 9);
			this.drawTexturedModalRect(k + 88, l + 39, 186, 25, i2, 1);
		}

		if (prog >= 0.4f) {
			int i3 = (int) ((prog < 0.6f ? (prog - 0.4f) * 5 : 1.0f) * 20);
			this.drawTexturedModalRect(k + 97, l + 23 + (20 - i3), 194, 9 + (20 - i3), 14, i3);
		}

		if (prog >= 0.6f) {
			int i4 = (int) ((prog < 0.8f ? (prog - 0.6f) * 5 : 1.0f) * 16);
			this.drawTexturedModalRect(k + 108, l + 17, 205, 3, i4, 6);
		}
		if (prog >= 0.8f) {
			int i5 = (int) (((prog - 0.8f) * 5) * 24);
			this.drawTexturedModalRect(k + 117, l + 20, 214, 6, 14, i5 + 1);
		}
		
	}
	private void drawTexturedModalRect(int x, int y, int u, int v, int width, int height) {
		Gui.drawModalRectWithCustomSizedTexture(x, y, u, v, width, height, 256, 256);
	}


}
