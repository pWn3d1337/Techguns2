package techguns.plugins.jei;

import mezz.jei.api.IGuiHelper;
import mezz.jei.api.gui.IDrawableAnimated;
import mezz.jei.api.gui.ITickTimer;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import techguns.gui.ReactionChamberGui;
import techguns.tileentities.operation.ReactionChamberOperation;

public class ProgressBarReactionChamberIntensityJump implements IDrawableAnimated {

	protected ITickTimer timer;
	protected IGuiHelper guiHelper;
	
	protected int w;
	protected int h;
	
	protected int intensity;
	protected int margin;
	
	public ProgressBarReactionChamberIntensityJump(IGuiHelper guiHelper, int w, int h, int intensity, int margin) {
		super();
		this.guiHelper = guiHelper;
		int totalTime = 3*ReactionChamberOperation.RECIPE_TICKRATE;
		this.timer = guiHelper.createTickTimer(totalTime, totalTime, false);
		
		this.intensity = intensity;
		this.margin = margin;
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
	
		int height = 4*this.intensity;
		int u_offset=0;
		if(this.margin>0) {
			float prog = ((float)this.timer.getValue())/((float)this.timer.getMaxValue());
			float jump_prog = (float) Math.sin(prog*2.0f*Math.PI);
			
			/*if(jump_prog>0.25f) {
				jump_prog-=0.25f;
			} else if(jump_prog<-0.25f){
				jump_prog+=0.25f;
			}*/
			
			int val = (int) Math.round(jump_prog*margin);
			if(val>this.margin) {
				val=margin;
			} else if(val<-this.margin) {
				val=-margin;
			}
			
			height = 4* (this.intensity+val);
			
			//System.out.println("Prog"+prog+" JumpProg"+jump_prog+" Val:"+val+ " Int:"+intensity+" Mrg:"+this.margin);
			
			u_offset=8;
		}
		this.drawTexturedModalRect(xOffset, yOffset+40-height, 190+u_offset, 40-height, 5, height);
		
		this.drawTexturedModalRect(xOffset-2, yOffset+40-1-(4*this.intensity), 178, 22, 5, 3);
		
	}
	private void drawTexturedModalRect(int x, int y, int u, int v, int width, int height) {
		Gui.drawModalRectWithCustomSizedTexture(x, y, u, v, width, height, 256, 256);
	}
}