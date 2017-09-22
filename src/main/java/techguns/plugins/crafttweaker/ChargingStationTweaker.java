package techguns.plugins.crafttweaker;

import java.util.ArrayList;
import java.util.Iterator;

import crafttweaker.CraftTweakerAPI;
import crafttweaker.IAction;
import crafttweaker.api.item.IItemStack;
import crafttweaker.api.minecraft.CraftTweakerMC;
import net.minecraft.item.ItemStack;
import stanhebben.zenscript.annotations.ZenClass;
import stanhebben.zenscript.annotations.ZenMethod;
import techguns.tileentities.operation.ChargingStationRecipe;
import techguns.util.ItemStackOreDict;

@ZenClass("mods.techguns.ChargingStation")
public class ChargingStationTweaker {
	
	private ChargingStationTweaker(){};
	
	@ZenMethod
	public static void addRecipe(IItemStack input, IItemStack output, int RFAmount){
		CraftTweakerAPI.apply(new addInputAction(new ItemStackOreDict(CraftTweakerMC.getItemStack(input)), CraftTweakerMC.getItemStack(output), RFAmount));
	}

	@ZenMethod
	public static void addRecipe(String input, IItemStack output, int RFAmount){
		CraftTweakerAPI.apply(new addInputAction(new ItemStackOreDict(input), CraftTweakerMC.getItemStack(output), RFAmount));
	}
	
	@ZenMethod
	public static void removeRecipe(IItemStack input){
		CraftTweakerAPI.apply(new removeInputAction(new ItemStackOreDict(CraftTweakerMC.getItemStack(input))));
	}

	@ZenMethod
	public static void removeRecipe(String input){
		CraftTweakerAPI.apply(new removeInputAction(new ItemStackOreDict(input)));
	}
	
	
	private static class addInputAction implements IAction
	{

		ChargingStationRecipe added_recipe=null;
		ItemStackOreDict input;
		ItemStack output;
		int rf;
		
		public addInputAction(ItemStackOreDict input, ItemStack output, int RFAmount) {
			super();
			this.input = input;
			this.output=output;
			this.rf = RFAmount;
		}

		@Override
		public void apply() {
			this.added_recipe=ChargingStationRecipe.addRecipe(input, output, rf);
		}

	
		@Override
		public String describe() {
			return "Add "+(input)+" to ChargingStation";
		}

	}
	
	private static class removeInputAction implements IAction
	{
		ItemStackOreDict input;
			
		public removeInputAction(ItemStackOreDict input) {
			this.input=input;
		}

		@Override
		public void apply() {
			ArrayList<ChargingStationRecipe> recipes = ChargingStationRecipe.getRecipes();
			
			Iterator<ChargingStationRecipe> iter = recipes.iterator();
			
			while (iter.hasNext()){
				
				ChargingStationRecipe rec = iter.next();
				
				if (rec.input.matches(this.input)){
					iter.remove();					
				}
			}
			
		}

		@Override
		public String describe() {
			return "Remove "+(input)+" from ChargingStation."; //: "+removed_recipes.size()+ " removed recipe(s)";
		}
		
	}
		
}
