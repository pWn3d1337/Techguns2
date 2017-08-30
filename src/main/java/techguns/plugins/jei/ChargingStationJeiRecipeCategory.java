package techguns.plugins.jei;

import mezz.jei.api.IGuiHelper;
import mezz.jei.api.gui.IGuiItemStackGroup;
import mezz.jei.api.gui.IRecipeLayout;
import mezz.jei.api.ingredients.IIngredients;
import net.minecraft.util.ResourceLocation;
import techguns.gui.ChargingStationGui;
import techguns.tileentities.ChargingStationTileEnt;
import techguns.tileentities.FabricatorTileEntMaster;
import static techguns.gui.containers.ChargingStationContainer.*;

public class ChargingStationJeiRecipeCategory extends BasicRecipeCategory<ChargingStationJeiRecipe>{

	public ChargingStationJeiRecipeCategory(IGuiHelper guiHelper) {
		super(guiHelper, ChargingStationGui.texture, "chargingstation", TGJeiPlugin.CHARGING_STATION);
	}

	@Override
	public void setRecipe(IRecipeLayout recipeLayout, ChargingStationJeiRecipe recipeWrapper,
			IIngredients ingredients) {
		IGuiItemStackGroup guiItemStacks = recipeLayout.getItemStacks();
		
		guiItemStacks.init(ChargingStationTileEnt.SLOT_INPUT,true,SLOT_INPUT_X+JEI_OFFSET_X,SLOTS_ROW1_Y+JEI_OFFSET_Y);

		guiItemStacks.init(ChargingStationTileEnt.SLOT_OUTPUT, false, SLOT_OUTPUT_X+JEI_OFFSET_X, SLOTS_ROW1_Y+JEI_OFFSET_Y);	
		
		guiItemStacks.set(ingredients);
	}

}
