package techguns.plugins.jei;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

import com.mojang.realmsclient.gui.ChatFormatting;

import mezz.jei.api.IGuiHelper;
import mezz.jei.api.IJeiHelpers;
import mezz.jei.api.gui.IDrawableAnimated;
import mezz.jei.api.gui.IDrawableStatic;
import mezz.jei.api.recipe.IStackHelper;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import techguns.gui.PoweredTileEntGui;
import techguns.gui.ReactionChamberGui;
import techguns.gui.TGBaseGui;
import techguns.tileentities.operation.AmmoPressBuildPlans;
import techguns.tileentities.operation.IMachineRecipe;
import techguns.tileentities.operation.ReactionChamberOperation;
import techguns.tileentities.operation.ReactionChamberRecipe;
import techguns.util.TextUtil;

import static techguns.plugins.jei.BasicRecipeCategory.JEI_OFFSET_X;
import static techguns.plugins.jei.BasicRecipeCategory.JEI_OFFSET_Y;

public class ReactionChamberJeiRecipe extends BasicRecipeWrapper {

	protected ReactionChamberRecipe recipe;
	
	IDrawableStatic progress_static;
	IDrawableAnimated progress;
	
	IDrawableAnimated completion_bar;
	
	IDrawableAnimated intensity_bar;
	
	protected byte fluidlevel;
	protected byte intensity;
	protected byte requiredCompletion;
	protected byte recipeticks;
	protected byte intensityMargin;
	protected float chanceToJump;
	protected boolean stable;
	
	public ReactionChamberJeiRecipe(ReactionChamberRecipe recipe, IGuiHelper guiHelper) {
		super(recipe);
		this.recipe=recipe;
		
		this.progress_static = guiHelper.createDrawable(ReactionChamberGui.texture, 0, 175, 101, 4);
		this.progress = guiHelper.createAnimatedDrawable(progress_static, this.recipe.ticks*ReactionChamberOperation.RECIPE_TICKRATE, IDrawableAnimated.StartDirection.LEFT, false);
		
		this.completion_bar = new ProgressBarReactionChamberCompletion(guiHelper, 100, 4,this.recipe.ticks,recipe.requiredCompletion);
		this.fluidlevel = recipe.liquidLevel;
		this.intensity = recipe.preferredIntensity;
		this.intensity_bar = new ProgressBarReactionChamberIntensityJump(guiHelper, 5, 40, recipe.preferredIntensity, recipe.intensityMargin);
		this.requiredCompletion = recipe.requiredCompletion;
		this.recipeticks=recipe.ticks;
		this.intensityMargin=recipe.intensityMargin;
		this.chanceToJump = recipe.instability;
		this.stable=recipe.isStable();
	}

	@Override
	protected int getRFperTick() {
		return this.recipe.RFTick;
	}

	public static List<ReactionChamberJeiRecipe> getRecipes(IJeiHelpers helpers) {
		IStackHelper stackHelper = helpers.getStackHelper();
	
		List<ReactionChamberJeiRecipe> recipes = new ArrayList<>();
		
		Iterator<String> it = ReactionChamberRecipe.getRecipes().keySet().iterator();
		while(it.hasNext()) {
			ReactionChamberRecipe rec = ReactionChamberRecipe.getRecipes().get(it.next());
			recipes.add(new ReactionChamberJeiRecipe(rec,helpers.getGuiHelper()));
		}
		
		return recipes;
	}

	@Override
	public List<String> getTooltipStrings(int mouseX, int mouseY) {

		if (TGBaseGui.isInRect(mouseX, mouseY, 66+JEI_OFFSET_X, 60+JEI_OFFSET_Y, 102, 14)) {
			List<String> tooltip = new ArrayList<>();
			tooltip.add(TextUtil.trans("techguns.container.reactionchamber.completion")+": "+ChatFormatting.GREEN+this.requiredCompletion);
			tooltip.add(TextUtil.trans("techguns.container.reactionchamber.recipeticks")+": "+ChatFormatting.RED+this.recipeticks);
			tooltip.add(TextUtil.trans("techguns.container.reactionchamber.time")+": "+(this.recipeticks*ReactionChamberOperation.RECIPE_TICKRATE)/20+"s");
			return tooltip;
		} else if(TGBaseGui.isInRect(mouseX, mouseY, 113+JEI_OFFSET_X, 16+JEI_OFFSET_Y, 7, 41)) {
			List<String> tooltip = new ArrayList<>();
			tooltip.add(TextUtil.trans("techguns.container.reactionchamber.intensity")+": "+this.intensity);
			if(!stable) {
				tooltip.add(TextUtil.trans("techguns.container.reactionchamber.intensitymargin")+": +/-"+this.intensityMargin);
				tooltip.add(TextUtil.trans("techguns.container.reactionchamber.chance")+": "+((int)(this.chanceToJump*100))+"%");
			} else {
				tooltip.add(ChatFormatting.GREEN+TextUtil.trans("techguns.container.reactionchamber.stable"));
			}
			return tooltip;
		} else if(TGBaseGui.isInRect(mouseX, mouseY, 96+JEI_OFFSET_X, 38+JEI_OFFSET_Y, 12, 12)) {
			List<String> tooltip = new ArrayList<>();
			tooltip.add(TextUtil.trans("techguns.container.reactionchamber.risk")+": "+this.recipe.risk);
			return tooltip;
		} else if (TGBaseGui.isInRect(mouseX, mouseY, 8+BasicRecipeCategory.JEI_OFFSET_X, 17+BasicRecipeCategory.JEI_OFFSET_Y, 6, 60)) {
			
			List<String> tooltip = new ArrayList<>();
			tooltip.add(TextUtil.trans("techguns.container.power")+":");
			
			tooltip.add("-"+this.getRFperTick()+" "+PoweredTileEntGui.POWER_UNIT+"/"+TextUtil.trans("techguns.container.reactionchamber.recipetick"));
			tooltip.add("-"+this.getRFperTick()*this.requiredCompletion+" "+PoweredTileEntGui.POWER_UNIT);
			
			return tooltip;
		} else {
			return Collections.EMPTY_LIST;
		}
		
	}
	
	@Override
	public void drawInfo(Minecraft minecraft, int recipeWidth, int recipeHeight, int mouseX, int mouseY) {
		this.progress.draw(minecraft, 68+JEI_OFFSET_X, 70+JEI_OFFSET_Y);
		
		this.completion_bar.draw(minecraft, 68+JEI_OFFSET_X, 62+JEI_OFFSET_Y);
		
		this.intensity_bar.draw(minecraft, 115+JEI_OFFSET_X, 17+JEI_OFFSET_Y);
		
		int level=0;
		switch(this.recipe.risk) {
			case BREAK_ITEM:
			case NONE:
				break;
			case RAD_LOW:
			case RAD_MEDIUM:
				level=1;
				break;
			case EXPLOSION_LOW:
			case EXPLOSION_MEDIUM:
			case RAD_HIGH:
				level=2;
				break;
			case EXPLOSION_HIGH:
			case UNFORSEEN_CONSEQUENCES:
				level=3;
				break;
			default:
				break;
		}
		
		Minecraft.getMinecraft().getTextureManager().bindTexture(ReactionChamberGui.texture);
		Gui.drawModalRectWithCustomSizedTexture(JEI_OFFSET_X+96, JEI_OFFSET_Y+38, 205, 12*level, 12, 12, 256, 256);
		
		minecraft.fontRenderer.drawString(this.fluidlevel*10 + "%", 34+1+JEI_OFFSET_X, 50+1+JEI_OFFSET_Y, 4210752);
		minecraft.fontRenderer.drawString(this.intensity + "", 121+1+JEI_OFFSET_X, 18+1+JEI_OFFSET_Y, 4210752);
		
	}
	
	
}
