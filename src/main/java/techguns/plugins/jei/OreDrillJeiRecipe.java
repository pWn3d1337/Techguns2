package techguns.plugins.jei;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import mezz.jei.api.IJeiHelpers;
import mezz.jei.api.ingredients.IIngredientRegistry;
import mezz.jei.api.recipe.IStackHelper;
import techguns.TGConfig;
import techguns.TGOreClusters;
import techguns.TGOreClusters.OreCluster;
import techguns.TGOreClusters.OreClusterWeightedEntry;
import techguns.Techguns;
import techguns.blocks.EnumOreClusterType;
import techguns.gui.PoweredTileEntGui;
import techguns.gui.TGBaseGui;
import techguns.tileentities.operation.ChargingStationRecipe;
import techguns.tileentities.operation.IMachineRecipe;
import techguns.util.TextUtil;

public class OreDrillJeiRecipe extends BasicRecipeWrapper {

	protected final OreDrillMachineRecipe mr;
	
	public OreDrillJeiRecipe(OreDrillMachineRecipe recipe) {
		super(recipe);
		mr=recipe;
	}

	@Override
	public List<String> getTooltipStrings(int mouseX, int mouseY) {
		if (TGBaseGui.isInRect(mouseX, mouseY, 8+BasicRecipeCategory.JEI_OFFSET_X, 17+BasicRecipeCategory.JEI_OFFSET_Y, 6, 60)) {
			
			List<String> tooltip = new ArrayList<>();
			//tooltip.add(TextUtil.trans("techguns.container.power")+":");
			
			//calculate 
			OreCluster cluster = Techguns.orecluster.getClusterForType(mr.getType());
			
			//if clustersize, mininglevel are 1, formula results in 4.5 * ... 
			double ore_per_hour = 4.5*cluster.getMultiplier_amount()*TGConfig.oreDrillMultiplierOres;
			int powerTick = (int) (8 * ore_per_hour * cluster.getMultiplier_power()*TGConfig.oreDrillMultiplierPower);
			
			tooltip.add(TextUtil.transTG("oredrill.powerperore")+": ");
			tooltip.add((int)((powerTick*20*60*60)/ore_per_hour) + " " + PoweredTileEntGui.POWER_UNIT);
			//tooltip.add("-"+this.getRFperTick()+" "+PoweredTileEntGui.POWER_UNIT+"/t");
			//tooltip.add("-"+this.getRFperTick()*this.getDuration()+" "+PoweredTileEntGui.POWER_UNIT);
			
			return tooltip;
		} else {
			return Collections.emptyList();
		}
	}

	@Override
	protected int getRFperTick() {
		return 0;
	}

	public float getChance() {
		return mr.getChance();
	}
	
	public static List<OreDrillJeiRecipe> getRecipes(IJeiHelpers helpers, IIngredientRegistry registry) {
		IStackHelper stackHelper = helpers.getStackHelper();
	
		List<OreDrillJeiRecipe> recipes = new ArrayList<OreDrillJeiRecipe>();
		
		for(EnumOreClusterType type: EnumOreClusterType.values()){
			OreCluster cluster = Techguns.orecluster.getClusterForType(type);
			
			ArrayList<OreClusterWeightedEntry> entries = cluster.getOreEntries();
			for(OreClusterWeightedEntry we : entries) {
				recipes.add(new OreDrillJeiRecipe(new OreDrillMachineRecipe(we, type,registry.getFuels())));
			}
		}
		
		return recipes;
	}
}
