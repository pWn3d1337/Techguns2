package techguns.plugins.jei;

import java.util.ArrayList;
import java.util.List;

import mezz.jei.api.IJeiHelpers;
import mezz.jei.api.recipe.IStackHelper;
import techguns.tileentities.operation.ChemLabRecipes;
import techguns.tileentities.operation.ChemLabRecipes.ChemLabRecipe;

public class ChemLabJeiRecipe extends BasicRecipeWrapper {

	ChemLabRecipe recipe;
	
	public ChemLabJeiRecipe(ChemLabRecipe recipe) {
		super(recipe);
		this.recipe=recipe;
	}

	@Override
	protected int getRFperTick() {
		return recipe.powerPerTick;
	}

	public static List<ChemLabJeiRecipe> getRecipes(IJeiHelpers helpers) {
		IStackHelper stackHelper = helpers.getStackHelper();
	
		List<ChemLabJeiRecipe> recipes = new ArrayList<ChemLabJeiRecipe>();
		
		ArrayList<ChemLabRecipe> m_recipes = ChemLabRecipes.getRecipes();
		m_recipes.forEach(r -> recipes.add(new ChemLabJeiRecipe(r)));
		
		return recipes;
	}
}
