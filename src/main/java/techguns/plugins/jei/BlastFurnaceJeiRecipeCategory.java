package techguns.plugins.jei;

import static techguns.gui.containers.BlastFurnaceContainer.SLOT_INPUT1_X;
import static techguns.gui.containers.BlastFurnaceContainer.SLOT_INPUT2_X;
import static techguns.gui.containers.BlastFurnaceContainer.SLOTS_ROW1_Y;
import static techguns.gui.containers.BlastFurnaceContainer.SLOT_OUTPUT_Y;
import static techguns.gui.containers.BlastFurnaceContainer.SLOT_OUTPUT_X;

import mezz.jei.api.IGuiHelper;
import mezz.jei.api.gui.IDrawableAnimated;
import mezz.jei.api.gui.IDrawableStatic;
import mezz.jei.api.gui.IGuiItemStackGroup;
import mezz.jei.api.gui.IRecipeLayout;
import mezz.jei.api.ingredients.IIngredients;
import net.minecraft.client.Minecraft;
import net.minecraft.util.ResourceLocation;
import techguns.gui.BlastFurnaceGui;
import techguns.tileentities.BlastFurnaceTileEnt;

public class BlastFurnaceJeiRecipeCategory extends BasicRecipeCategory<BlastFurnaceJeiRecipe> {

	IDrawableStatic progress_static;
	IDrawableAnimated progress;
	
	public BlastFurnaceJeiRecipeCategory(IGuiHelper guiHelper) {
		super(guiHelper, BlastFurnaceGui.texture, "blast_furnace", TGJeiPlugin.BLAST_FURNACE);

		this.progress_static = guiHelper.createDrawable(BlastFurnaceGui.texture, 0, 167, 90, 10);
		this.progress = guiHelper.createAnimatedDrawable(progress_static, 100, IDrawableAnimated.StartDirection.LEFT, false);
	}

	@Override
	public void setRecipe(IRecipeLayout recipeLayout, BlastFurnaceJeiRecipe recipeWrapper, IIngredients ingredients) {
		IGuiItemStackGroup guiItemStacks = recipeLayout.getItemStacks();
		
		guiItemStacks.init(BlastFurnaceTileEnt.SLOT_INPUT1,true,SLOT_INPUT1_X+JEI_OFFSET_X,SLOTS_ROW1_Y+JEI_OFFSET_Y);
		guiItemStacks.init(BlastFurnaceTileEnt.SLOT_INPUT2,true,SLOT_INPUT2_X+JEI_OFFSET_X,SLOTS_ROW1_Y+JEI_OFFSET_Y);

		guiItemStacks.init(BlastFurnaceTileEnt.SLOT_OUTPUT, false, SLOT_OUTPUT_X+JEI_OFFSET_X, SLOT_OUTPUT_Y+JEI_OFFSET_Y);
		
		
		guiItemStacks.set(ingredients);
	}

	@Override
	public void drawExtras(Minecraft minecraft) {
		super.drawExtras(minecraft);
		this.powerbar.draw(minecraft, 8+JEI_OFFSET_X, 17+JEI_OFFSET_Y);
		this.progress.draw(minecraft, 20+JEI_OFFSET_X, 54+JEI_OFFSET_Y);
	}

}
