package techguns.plugins.jei;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import mezz.jei.api.IJeiHelpers;
import mezz.jei.api.recipe.IStackHelper;
import techguns.gui.PoweredTileEntGui;
import techguns.gui.TGBaseGui;
import techguns.tileentities.ChargingStationTileEnt;
import techguns.tileentities.operation.ChargingStationRecipe;
import techguns.util.TextUtil;

public class ChargingStationJeiRecipe extends BasicRecipeWrapper {

	ChargingStationRecipe rec;
	
	public ChargingStationJeiRecipe(ChargingStationRecipe recipe) {
		super(recipe);
		this.rec=recipe;
	}

	@Override
	protected int getRFperTick() {
		return ChargingStationTileEnt.CHARGERATE;
	}
	
	@Override
	public List<String> getTooltipStrings(int mouseX, int mouseY) {
		if (TGBaseGui.isInRect(mouseX, mouseY, 8+BasicRecipeCategory.JEI_OFFSET_X, 17+BasicRecipeCategory.JEI_OFFSET_Y, 6, 60)) {
			
			List<String> tooltip = new ArrayList<>();
			tooltip.add(TextUtil.trans("techguns.container.power")+":");
			
			
			double duration = ((double)rec.chargeAmount) / ((double)this.getRFperTick());
			
			tooltip.add("-"+this.getRFperTick()+" "+PoweredTileEntGui.POWER_UNIT+"/t");
			tooltip.add("-"+rec.chargeAmount+" "+PoweredTileEntGui.POWER_UNIT);
			tooltip.add(TextUtil.trans("techguns.container.chargingstation.duration")+": "+String.format("%.2f", duration/20.0f)+"s");
			
			return tooltip;
		} else {
			return Collections.emptyList();
		}
		
	}

	public static List<ChargingStationJeiRecipe> getRecipes(IJeiHelpers helpers) {
		IStackHelper stackHelper = helpers.getStackHelper();
	
		List<ChargingStationJeiRecipe> recipes = new ArrayList<ChargingStationJeiRecipe>();
		
		ChargingStationRecipe.getRecipes().forEach(r -> recipes.add(new ChargingStationJeiRecipe(r)));
		
		return recipes;
	}
}
