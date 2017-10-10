package techguns.plugins.jei;

import java.util.ArrayList;
import java.util.List;

import mezz.jei.api.IJeiHelpers;
import mezz.jei.api.recipe.IStackHelper;
import techguns.tileentities.MetalPressTileEnt;
import techguns.tileentities.operation.MetalPressRecipes;
import techguns.tileentities.operation.MetalPressRecipes.MetalPressRecipe;

public class MetalPressJeiRecipe extends BasicRecipeWrapper {

	MetalPressRecipe recipe;
	
	public MetalPressJeiRecipe(MetalPressRecipe recipe) {
		super(recipe);
		this.recipe=recipe;
	}

	@Override
	protected int getRFperTick() {
		return MetalPressTileEnt.POWER_PER_TICK;
	}
	
	public static List<MetalPressJeiRecipe> getRecipes(IJeiHelpers helpers) {
		IStackHelper stackHelper = helpers.getStackHelper();
	
		List<MetalPressJeiRecipe> recipes = new ArrayList<MetalPressJeiRecipe>();
		
		ArrayList<MetalPressRecipe> m_recipes = MetalPressRecipes.getRecipes();
		m_recipes.forEach(r -> recipes.add(new MetalPressJeiRecipe(r)));
		
		return recipes;
	}
	
}
