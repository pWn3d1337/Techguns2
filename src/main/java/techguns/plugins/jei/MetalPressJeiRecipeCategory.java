package techguns.plugins.jei;

import mezz.jei.api.IGuiHelper;
import mezz.jei.api.gui.IDrawableAnimated;
import mezz.jei.api.gui.IDrawableStatic;
import mezz.jei.api.gui.IGuiItemStackGroup;
import mezz.jei.api.gui.IRecipeLayout;
import mezz.jei.api.ingredients.IIngredients;
import net.minecraft.client.Minecraft;
import net.minecraft.util.ResourceLocation;
import techguns.gui.MetalPressGui;
import techguns.gui.containers.MetalPressContainer;
import techguns.tileentities.MetalPressTileEnt;

import static techguns.gui.containers.MetalPressContainer.*;

public class MetalPressJeiRecipeCategory extends BasicRecipeCategory<MetalPressJeiRecipe> {

	IDrawableStatic progress_static;
	IDrawableAnimated progress;
	
	public MetalPressJeiRecipeCategory(IGuiHelper guiHelper) {
		super(guiHelper, MetalPressGui.texture, "metalpress", TGJeiPlugin.METAL_PRESS);
		
		this.progress_static = guiHelper.createDrawable(MetalPressGui.texture, 180, 0, 10, 21);
		this.progress = guiHelper.createAnimatedDrawable(progress_static, 100, IDrawableAnimated.StartDirection.TOP, false);
	}

	@Override
	public void setRecipe(IRecipeLayout recipeLayout, MetalPressJeiRecipe recipeWrapper, IIngredients ingredients) {
		IGuiItemStackGroup guiItemStacks = recipeLayout.getItemStacks();
		
		guiItemStacks.init(MetalPressTileEnt.SLOT_INPUT1,true,SLOT_INPUT1_X+JEI_OFFSET_X,SLOT_INPUTS_Y+JEI_OFFSET_Y);
		guiItemStacks.init(MetalPressTileEnt.SLOT_INPUT2,true,SLOT_INPUT2_X+JEI_OFFSET_X,SLOT_INPUTS_Y+JEI_OFFSET_Y);

		guiItemStacks.init(MetalPressTileEnt.SLOT_OUTPUT, false, SLOT_OUTPUT_X+JEI_OFFSET_X, SLOT_OUTPUTS_Y+JEI_OFFSET_Y);
		
		
		guiItemStacks.set(ingredients);
	}
	
	@Override
	public void drawExtras(Minecraft minecraft) {
		super.drawExtras(minecraft);
		this.powerbar.draw(minecraft, 8+JEI_OFFSET_X, 17+JEI_OFFSET_Y);
		this.progress.draw(minecraft, 124+JEI_OFFSET_X, 37+JEI_OFFSET_Y);
	}


}
