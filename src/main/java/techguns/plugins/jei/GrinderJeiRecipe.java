package techguns.plugins.jei;

import java.util.ArrayList;
import java.util.List;

import mezz.jei.api.IJeiHelpers;
import mezz.jei.api.recipe.IStackHelper;
import techguns.tileentities.GrinderTileEnt;
import techguns.tileentities.operation.AmmoPressBuildPlans;
import techguns.tileentities.operation.GrinderRecipes;
import techguns.tileentities.operation.GrinderRecipes.GrinderRecipe;
import techguns.tileentities.operation.IMachineRecipe;

public class GrinderJeiRecipe extends BasicRecipeWrapper {

	GrinderRecipe rec;
	
	public GrinderJeiRecipe(GrinderRecipe recipe) {
		super(recipe);
		rec = recipe;
	}

	public GrinderRecipe getRecipe() {
		return this.rec;
	}
	
	@Override
	protected int getRFperTick() {
		return GrinderTileEnt.POWER_PER_TICK;
	}
	
	public static List<GrinderJeiRecipe> getRecipes(IJeiHelpers helpers) {
		IStackHelper stackHelper = helpers.getStackHelper();

		List<GrinderJeiRecipe> recipes = new ArrayList<>();
		GrinderRecipes.recipes.forEach(r -> recipes.add(new GrinderJeiRecipe(r)));
		
		return recipes;
	}

}
