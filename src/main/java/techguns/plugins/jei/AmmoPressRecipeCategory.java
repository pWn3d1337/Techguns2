package techguns.plugins.jei;

import static techguns.gui.containers.AmmoPressContainer.SLOTS_INPUT_Y;
import static techguns.gui.containers.AmmoPressContainer.SLOTS_OUTPUT_Y;
import static techguns.gui.containers.AmmoPressContainer.SLOT_METAL1_X;
import static techguns.gui.containers.AmmoPressContainer.SLOT_METAL2_X;
import static techguns.gui.containers.AmmoPressContainer.SLOT_OUTPUT_X;
import static techguns.gui.containers.AmmoPressContainer.SLOT_POWDER_X;

import mezz.jei.api.IGuiHelper;
import mezz.jei.api.gui.IDrawableAnimated;
import mezz.jei.api.gui.IDrawableStatic;
import mezz.jei.api.gui.IGuiItemStackGroup;
import mezz.jei.api.gui.IRecipeLayout;
import mezz.jei.api.ingredients.IIngredients;
import net.minecraft.client.Minecraft;
import techguns.gui.AmmoPressGui;
import techguns.tileentities.AmmoPressTileEnt;

public class AmmoPressRecipeCategory extends BasicRecipeCategory<AmmoPressJeiRecipe> {

	IDrawableStatic progress_static;
	IDrawableAnimated progress;
	
	public AmmoPressRecipeCategory(IGuiHelper guiHelper) {
		super(guiHelper, AmmoPressGui.texture, "ammopress",TGJeiPlugin.AMMO_PRESS);
		
		this.progress_static = guiHelper.createDrawable(AmmoPressGui.texture, 180, 0, 10, 21);
		this.progress = guiHelper.createAnimatedDrawable(progress_static, 100, IDrawableAnimated.StartDirection.TOP, false);
	}

	@Override
	public void setRecipe(IRecipeLayout recipeLayout, AmmoPressJeiRecipe recipeWrapper, IIngredients ingredients) {
		IGuiItemStackGroup guiItemStacks = recipeLayout.getItemStacks();

		guiItemStacks.init(AmmoPressTileEnt.SLOT_METAL1, true, SLOT_METAL1_X+JEI_OFFSET_X, SLOTS_INPUT_Y+JEI_OFFSET_Y);
		guiItemStacks.init(AmmoPressTileEnt.SLOT_METAL2, true, SLOT_METAL2_X+JEI_OFFSET_X, SLOTS_INPUT_Y+JEI_OFFSET_Y);
		guiItemStacks.init(AmmoPressTileEnt.SLOT_POWDER, true, SLOT_POWDER_X+JEI_OFFSET_X, SLOTS_INPUT_Y+JEI_OFFSET_Y);
		
		guiItemStacks.init(AmmoPressTileEnt.SLOT_OUTPUT, false, SLOT_OUTPUT_X+JEI_OFFSET_X, SLOTS_OUTPUT_Y+JEI_OFFSET_Y);

		guiItemStacks.set(ingredients);
	}

	
	
	@Override
	public void drawExtras(Minecraft minecraft) {
		super.drawExtras(minecraft);
		this.powerbar.draw(minecraft, 8+JEI_OFFSET_X, 17+JEI_OFFSET_Y);
		this.progress.draw(minecraft, 124+JEI_OFFSET_X, 37+JEI_OFFSET_Y);
	}


}
