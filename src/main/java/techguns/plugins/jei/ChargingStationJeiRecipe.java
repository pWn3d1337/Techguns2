package techguns.plugins.jei;

import java.util.ArrayList;
import java.util.List;

import mezz.jei.api.IJeiHelpers;
import mezz.jei.api.recipe.IStackHelper;
import techguns.tileentities.operation.AmmoPressBuildPlans;
import techguns.tileentities.operation.ChargingStationRecipe;
import techguns.tileentities.operation.IMachineRecipe;

public class ChargingStationJeiRecipe extends BasicRecipeWrapper {

	ChargingStationRecipe rec;
	
	public ChargingStationJeiRecipe(ChargingStationRecipe recipe) {
		super(recipe);
		this.rec=recipe;
	}

	@Override
	protected int getRFperTick() {
		return rec.chargeAmount;
	}

	public static List<ChargingStationJeiRecipe> getRecipes(IJeiHelpers helpers) {
		IStackHelper stackHelper = helpers.getStackHelper();
	
		List<ChargingStationJeiRecipe> recipes = new ArrayList<ChargingStationJeiRecipe>();
		
		ChargingStationRecipe.getRecipes().forEach(r -> recipes.add(new ChargingStationJeiRecipe(r)));
		
		return recipes;
	}
}
