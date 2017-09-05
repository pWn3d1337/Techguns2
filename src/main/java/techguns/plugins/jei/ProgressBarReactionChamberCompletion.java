package techguns.plugins.jei;

import mezz.jei.api.IGuiHelper;
import mezz.jei.api.gui.IDrawableAnimated;
import mezz.jei.api.gui.ITickTimer;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import techguns.gui.ChemLabGui;
import techguns.gui.ReactionChamberGui;
import techguns.tileentities.operation.ReactionChamberOperation;

public class ProgressBarReactionChamberCompletion implements IDrawableAnimated {

	protected ITickTimer timer;
	protected IGuiHelper guiHelper;
	
	protected int w;
	protected int h;
	
	protected int recipeTicks;
	protected int totalTime;
	
	protected int guiStepWidth;
	protected int requiredCompletion;
	
	public ProgressBarReactionChamberCompletion(IGuiHelper guiHelper, int w, int h, int recipeTicks, int reqCompletion) {
		super();
		this.guiHelper = guiHelper;
		this.totalTime = recipeTicks*ReactionChamberOperation.RECIPE_TICKRATE;
		this.timer = guiHelper.createTickTimer(totalTime, totalTime, false);
		this.recipeTicks=recipeTicks;
		this.guiStepWidth = (int) (100.0f/(float)reqCompletion);
		this.requiredCompletion = reqCompletion;
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
		
		minecraft.getTextureManager().bindTexture(ReactionChamberGui.texture);
	
		int completion_totalTime = this.requiredCompletion*ReactionChamberOperation.RECIPE_TICKRATE;
		
		float prog = ((float)this.timer.getValue())/((float)completion_totalTime);
		if(prog>1.0f) {
			prog=1.0f;
		}
		
		int steps = ((int) (prog*completion_totalTime))/((int)((float)completion_totalTime/(float)this.requiredCompletion));
		//System.out.println("P:"+prog+" TT:"+totalTime+" guiSW:"+this.guiStepWidth + "-> Steps:"+steps);
		
		
		//System.out.println("Timer value:"+timer.getValue()+" Prog:"+prog+" Steps:"+steps+ " guiSW:"+this.guiStepWidth+ " Ticks:"+this.recipeTicks);
		
		
		this.drawTexturedModalRect(xOffset, yOffset, 0, 167, steps*this.guiStepWidth, 4);
		
	}
	private void drawTexturedModalRect(int x, int y, int u, int v, int width, int height) {
		Gui.drawModalRectWithCustomSizedTexture(x, y, u, v, width, height, 256, 256);
	}

}
