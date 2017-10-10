package techguns.plugins.jei;

import static techguns.gui.containers.ChargingStationContainer.SLOTS_ROW1_Y;
import static techguns.gui.containers.ChargingStationContainer.SLOT_INPUT_X;
import static techguns.gui.containers.ChargingStationContainer.SLOT_OUTPUT_X;

import mezz.jei.api.IGuiHelper;
import mezz.jei.api.gui.IDrawableAnimated;
import mezz.jei.api.gui.IDrawableStatic;
import mezz.jei.api.gui.IGuiItemStackGroup;
import mezz.jei.api.gui.IRecipeLayout;
import mezz.jei.api.ingredients.IIngredients;
import net.minecraft.client.Minecraft;
import techguns.gui.ChargingStationGui;
import techguns.tileentities.ChargingStationTileEnt;

public class ChargingStationJeiRecipeCategory extends BasicRecipeCategory<ChargingStationJeiRecipe>{

	IDrawableStatic progress_static;
	IDrawableAnimated progress;
	
	public ChargingStationJeiRecipeCategory(IGuiHelper guiHelper) {
		super(guiHelper, ChargingStationGui.texture, "chargingstation", TGJeiPlugin.CHARGING_STATION);
		
		this.progress_static = guiHelper.createDrawable(ChargingStationGui.texture, 0, 167, 26, 10);
		this.progress = guiHelper.createAnimatedDrawable(progress_static, 100, IDrawableAnimated.StartDirection.LEFT, false);
	}

	
	
	@Override
	public void setRecipe(IRecipeLayout recipeLayout, ChargingStationJeiRecipe recipeWrapper,
			IIngredients ingredients) {
		IGuiItemStackGroup guiItemStacks = recipeLayout.getItemStacks();
		
		guiItemStacks.init(ChargingStationTileEnt.SLOT_INPUT,true,SLOT_INPUT_X+JEI_OFFSET_X,SLOTS_ROW1_Y+JEI_OFFSET_Y);

		guiItemStacks.init(ChargingStationTileEnt.SLOT_OUTPUT, false, SLOT_OUTPUT_X+JEI_OFFSET_X, SLOTS_ROW1_Y+JEI_OFFSET_Y);	
		
		guiItemStacks.set(ingredients);
	}

	@Override
	public void drawExtras(Minecraft minecraft) {
		super.drawExtras(minecraft);
		this.powerbar.draw(minecraft, 8+JEI_OFFSET_X, 17+JEI_OFFSET_Y);
		this.progress.draw(minecraft, 40+JEI_OFFSET_X, 20+JEI_OFFSET_Y);
	}
}
