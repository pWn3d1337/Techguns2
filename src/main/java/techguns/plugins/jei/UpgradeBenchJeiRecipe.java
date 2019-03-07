package techguns.plugins.jei;

import techguns.tileentities.operation.UpgradeBenchRecipes.UpgradeBenchRecipe;
import techguns.util.TextUtil;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import mezz.jei.api.IJeiHelpers;
import mezz.jei.api.recipe.IStackHelper;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import techguns.tileentities.operation.UpgradeBenchRecipes;
import techguns.TGConfig;
import techguns.tileentities.operation.IMachineRecipe;

public class UpgradeBenchJeiRecipe extends BasicRecipeWrapper {
	protected UpgradeBenchRecipe recipe;
	
	public UpgradeBenchJeiRecipe(UpgradeBenchRecipe recipe) {
		super(recipe);
		this.recipe=recipe;
	}

	@Override
	protected int getRFperTick() {
		return 0;
	}
	
	public static List<UpgradeBenchJeiRecipe> getRecipes(IJeiHelpers helpers) {
		IStackHelper stackHelper = helpers.getStackHelper();
	
		List<UpgradeBenchJeiRecipe> recipes = new ArrayList<>();
		
		UpgradeBenchRecipes.recipes.forEach(r -> {
			recipes.add(new UpgradeBenchJeiRecipe(r));
		});
		
		return recipes;
	}

	@Override
	public List<String> getTooltipStrings(int mouseX, int mouseY) {
		return Collections.emptyList();
	}

	@Override
	public void drawInfo(Minecraft minecraft, int recipeWidth, int recipeHeight, int mouseX, int mouseY) {

		int xp = this.recipe.getLevel()*TGConfig.upgrade_xp_cost;
		drawXPText(minecraft.fontRenderer,  TextUtil.transTG("gui.xpcost")+": "+xp, 95+BasicRecipeCategory.JEI_OFFSET_X, 17+BasicRecipeCategory.JEI_OFFSET_Y , 8453920 );
		
	}

	protected void drawXPText(FontRenderer fontRenderer, String s, int x, int y, int color) {
		int j = -16777216 | (color & 16579836) >> 2 | color & -16777216;
		
        fontRenderer.drawString(s, x, y+1, j);
        fontRenderer.drawString(s, x + 1, y, j);
        fontRenderer.drawString(s, x + 1, y+1, j);
        
        fontRenderer.drawString(s, x, y, color);
	}
	
}
