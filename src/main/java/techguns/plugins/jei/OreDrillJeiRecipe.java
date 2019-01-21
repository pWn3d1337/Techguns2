package techguns.plugins.jei;

import java.util.ArrayList;
import java.util.List;

import mezz.jei.api.IJeiHelpers;
import mezz.jei.api.recipe.IStackHelper;
import techguns.TGOreClusters;
import techguns.TGOreClusters.OreCluster;
import techguns.TGOreClusters.OreClusterWeightedEntry;
import techguns.Techguns;
import techguns.blocks.EnumOreClusterType;
import techguns.tileentities.operation.ChargingStationRecipe;
import techguns.tileentities.operation.IMachineRecipe;

public class OreDrillJeiRecipe extends BasicRecipeWrapper {

	protected final OreDrillMachineRecipe mr;
	
	public OreDrillJeiRecipe(OreDrillMachineRecipe recipe) {
		super(recipe);
		mr=recipe;
	}

	@Override
	protected int getRFperTick() {
		return 0;
	}

	public float getChance() {
		return mr.getChance();
	}
	
	public static List<OreDrillJeiRecipe> getRecipes(IJeiHelpers helpers) {
		IStackHelper stackHelper = helpers.getStackHelper();
	
		List<OreDrillJeiRecipe> recipes = new ArrayList<OreDrillJeiRecipe>();
		
		for(EnumOreClusterType type: EnumOreClusterType.values()){
			OreCluster cluster = Techguns.orecluster.getClusterForType(type);
			
			ArrayList<OreClusterWeightedEntry> entries = cluster.getOreEntries();
			for(OreClusterWeightedEntry we : entries) {
				recipes.add(new OreDrillJeiRecipe(new OreDrillMachineRecipe(we, type)));
			}
		}
		
		return recipes;
	}
}
