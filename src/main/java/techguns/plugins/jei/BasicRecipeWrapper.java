package techguns.plugins.jei;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import mezz.jei.api.ingredients.IIngredients;
import mezz.jei.api.ingredients.VanillaTypes;
import mezz.jei.api.recipe.IRecipeWrapper;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.FluidStack;
import techguns.gui.PoweredTileEntGui;
import techguns.gui.TGBaseGui;
import techguns.tileentities.operation.IMachineRecipe;
import techguns.util.TextUtil;

public abstract class BasicRecipeWrapper implements IRecipeWrapper {
	
	public IMachineRecipe recipe;
	
	public BasicRecipeWrapper(IMachineRecipe recipe) {
		super();
		this.recipe = recipe;
	}

	

	@Override
	public List<String> getTooltipStrings(int mouseX, int mouseY) {

		if (TGBaseGui.isInRect(mouseX, mouseY, 8+BasicRecipeCategory.JEI_OFFSET_X, 17+BasicRecipeCategory.JEI_OFFSET_Y, 6, 60)) {
			
			List<String> tooltip = new ArrayList<>();
			tooltip.add(TextUtil.trans("techguns.container.power")+":");
			
			tooltip.add("-"+this.getRFperTick()+" "+PoweredTileEntGui.POWER_UNIT+"/t");
			tooltip.add("-"+this.getRFperTick()*this.getDuration()+" "+PoweredTileEntGui.POWER_UNIT);
			
			return tooltip;
		} else {
			return Collections.emptyList();
		}
		
	}
	
	protected int getDuration() {
		return 20*5;
	}

	protected abstract int getRFperTick();
	
	@Override
	public void getIngredients(IIngredients ingredients) {
		
		List<List<ItemStack>> inputs = recipe.getItemInputs();
		if(inputs!=null && !inputs.isEmpty()) {
			ingredients.setInputLists(VanillaTypes.ITEM, inputs);
		}
		
		List<List<FluidStack>> fluidsIn = recipe.getFluidInputs();
		if(fluidsIn!=null && !fluidsIn.isEmpty()) {
			ingredients.setInputLists(VanillaTypes.FLUID, fluidsIn);
		}
		
		List<List<ItemStack>> outputs = recipe.getItemOutputs();
		if(outputs!=null && !outputs.isEmpty()) {
			ingredients.setOutputLists(VanillaTypes.ITEM, outputs);
		}
		
		List<List<FluidStack>> fluidsOut = recipe.getFluidOutputs();
		if(fluidsOut!=null && !fluidsOut.isEmpty()) {
			ingredients.setOutputLists(VanillaTypes.FLUID, fluidsOut);
		}
	}
	
}
