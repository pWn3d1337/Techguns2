package techguns.plugins.crafttweaker;

import crafttweaker.CraftTweakerAPI;
import crafttweaker.IAction;
import crafttweaker.api.item.IItemStack;
import crafttweaker.api.minecraft.CraftTweakerMC;
import net.minecraft.item.ItemStack;
import stanhebben.zenscript.annotations.ZenClass;
import stanhebben.zenscript.annotations.ZenMethod;
import techguns.tileentities.operation.BlastFurnaceRecipes;
import techguns.tileentities.operation.GrinderRecipes;

@ZenClass("mods.techguns.Grinder")
public class GrinderTweaker {

	@ZenMethod
	public static void addRecipe(IItemStack input, IItemStack[] outputs) {
		CraftTweakerAPI.apply(new addInputAction(input, outputs));
	}
	
	@ZenMethod
	public static void addRecipe(IItemStack input, IItemStack[] outputs, double[] chances) {
		CraftTweakerAPI.apply(new addChanceInputAction(input, outputs, chances));
	}
	
	@ZenMethod
	public static void removeRecipe(IItemStack input) {
		CraftTweakerAPI.apply(new removeInputAction(input));
	}
	
	private static class removeInputAction implements IAction {

		ItemStack input;
			
		public removeInputAction(IItemStack input) {
			super();
			this.input = CraftTweakerMC.getItemStack(input);
		}

		@Override
		public void apply() {
			GrinderRecipes.removeRecipeFor(input);
		}

		@Override
		public String describe() {
			return "Remove Recipe for "+input+" from Grinder";
		}
		
	}
	
	private static class addInputAction implements IAction {

		ItemStack input;
		ItemStack[] outputs;
		
		public addInputAction(IItemStack input, IItemStack[] outputs) {
			super();
			this.input = CraftTweakerMC.getItemStack(input);
			this.outputs = CraftTweakerMC.getItemStacks(outputs);
		}

		@Override
		public void apply() {
			if(outputs.length<=9) {
				GrinderRecipes.addRecipe(input, outputs);
			}
		}

		@Override
		public String describe() {
			if(outputs.length<=9) {
				return "Add Recipe for "+input+" to Grinder";
			} else {
				return "Can't add grinder Recipes with more than 9 different outputs";
			}
		}
		
	}
	private static class addChanceInputAction extends addInputAction {
		double[] chances;
		
		public addChanceInputAction(IItemStack input, IItemStack[] outputs, double[] chances) {
			super(input, outputs);
			this.chances=chances;
		}
		
		@Override
		public void apply() {
			if(outputs.length<=9 && outputs.length==chances.length) {
				GrinderRecipes.addRecipeChance(input, outputs, chances);
			}
		}

		@Override
		public String describe() {
			if(outputs.length<=9 && outputs.length==chances.length) {
				return "Add Recipe for "+input+" to Grinder";
			} else {
				return "Grinder Recipes must not have more than 9 different outputs and chances and input array sizes must match!";
			}
		}
		
	}
}
