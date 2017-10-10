package techguns.plugins.jei;

import java.util.ArrayList;
import java.util.List;

import mezz.jei.api.IJeiHelpers;
import mezz.jei.api.recipe.IStackHelper;
import techguns.tileentities.FabricatorTileEntMaster;
import techguns.tileentities.operation.FabricatorRecipe;

public class FabricatorJeiRecipe extends BasicRecipeWrapper {

	FabricatorRecipe rec;
	
	public FabricatorJeiRecipe(FabricatorRecipe recipe) {
		super(recipe);
		this.rec=recipe;
	}

	@Override
	protected int getRFperTick() {
		return FabricatorTileEntMaster.powerPerTick;
	}

	public static List<FabricatorJeiRecipe> getRecipes(IJeiHelpers helpers) {
		IStackHelper stackHelper = helpers.getStackHelper();
	
		List<FabricatorJeiRecipe> recipes = new ArrayList<FabricatorJeiRecipe>();
		
		ArrayList<FabricatorRecipe> m_recipes = FabricatorRecipe.getRecipes();
		m_recipes.forEach(r -> recipes.add(new FabricatorJeiRecipe(r)));
		
		return recipes;
	}
}
