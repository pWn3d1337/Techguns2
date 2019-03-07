package techguns.plugins.jei;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import mezz.jei.api.IGuiHelper;
import mezz.jei.api.IJeiHelpers;
import mezz.jei.api.gui.IGuiFluidStackGroup;
import mezz.jei.api.gui.IGuiItemStackGroup;
import mezz.jei.api.gui.IRecipeLayout;
import mezz.jei.api.ingredients.IIngredients;
import mezz.jei.api.recipe.IStackHelper;
import net.minecraft.client.Minecraft;
import net.minecraft.util.ResourceLocation;
import techguns.gui.UpgradeBenchGui;
import techguns.gui.containers.UpgradeBenchContainer;
import techguns.tileentities.UpgradeBenchTileEnt;
import techguns.tileentities.operation.UpgradeBenchRecipes;

public class UpgradeBenchJeiRecipeCategory extends BasicRecipeCategory<UpgradeBenchJeiRecipe> {

	UpgradeBenchAnimation anim;
	
	public UpgradeBenchJeiRecipeCategory(IGuiHelper guiHelper) {
		super(guiHelper, UpgradeBenchGui.texture, "upgradebench", TGJeiPlugin.UPGRADE_BENCH);
		this.anim = new UpgradeBenchAnimation(guiHelper, 89, 31);
	}

	@Override
	public void setRecipe(IRecipeLayout recipeLayout, UpgradeBenchJeiRecipe recipeWrapper, IIngredients ingredients) {
		IGuiItemStackGroup guiItemStacks = recipeLayout.getItemStacks();
		IGuiFluidStackGroup guiFluidStacks = recipeLayout.getFluidStacks();
		

		guiItemStacks.init(0, true, UpgradeBenchContainer.SLOT_INPUT_X+JEI_OFFSET_X, UpgradeBenchContainer.SLOT_Y+JEI_OFFSET_Y);
		guiItemStacks.init(1, true, UpgradeBenchContainer.SLOT_UPGRADE_X+JEI_OFFSET_X, UpgradeBenchContainer.SLOT_Y+JEI_OFFSET_Y);
		
		guiItemStacks.init(2, false, UpgradeBenchContainer.SLOT_OUTPUT_X+JEI_OFFSET_X, UpgradeBenchContainer.SLOT_Y+JEI_OFFSET_Y);
		
		guiItemStacks.set(ingredients);
		guiFluidStacks.set(ingredients);
	}

	@Override
	public void drawExtras(Minecraft minecraft) {
		this.anim.draw(minecraft,JEI_OFFSET_X+1,JEI_OFFSET_Y+1);
	}
}
