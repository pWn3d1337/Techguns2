package techguns.plugins.jei;

import mezz.jei.api.IGuiHelper;
import mezz.jei.api.gui.IGuiItemStackGroup;
import mezz.jei.api.gui.IRecipeLayout;
import mezz.jei.api.ingredients.IIngredients;
import techguns.gui.CamoBenchGui;
import techguns.gui.containers.CamoBenchContainer;

public class CamoBenchJeiRecipeCategory extends BasicRecipeCategory<CamoBenchJeiRecipe>{

	protected static final int CAMO_BENCH_OFFSET_X=-4;
	protected static final int CAMO_BENCH_OFFSET_Y=2;
	
	public CamoBenchJeiRecipeCategory(IGuiHelper guiHelper) {
		super(guiHelper, CamoBenchGui.texture, "camobench", TGJeiPlugin.CAMO_BENCH);
		background = guiHelper.createDrawable(CamoBenchGui.texture, 7-CAMO_BENCH_OFFSET_X, 15-CAMO_BENCH_OFFSET_Y, 162, 63);
	}

	@Override
	public void setRecipe(IRecipeLayout recipeLayout, CamoBenchJeiRecipe recipeWrapper, IIngredients ingredients) {
		IGuiItemStackGroup guiItemStacks = recipeLayout.getItemStacks();

		guiItemStacks.init(0,true,CamoBenchContainer.SLOT_INPUT_X+JEI_OFFSET_X+CAMO_BENCH_OFFSET_X, CamoBenchContainer.SLOT_INPUT_Y+JEI_OFFSET_Y+CAMO_BENCH_OFFSET_Y);
		
		guiItemStacks.set(ingredients);
	}
	
	

}
