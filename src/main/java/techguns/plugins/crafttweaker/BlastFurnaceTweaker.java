package techguns.plugins.crafttweaker;

import crafttweaker.CraftTweakerAPI;
import crafttweaker.IAction;
import crafttweaker.api.item.IItemStack;
import crafttweaker.api.minecraft.CraftTweakerMC;
import net.minecraft.item.ItemStack;
import stanhebben.zenscript.annotations.ZenClass;
import stanhebben.zenscript.annotations.ZenMethod;
import techguns.tileentities.operation.BlastFurnaceRecipes;
import techguns.tileentities.operation.MetalPressRecipes;
import techguns.tileentities.operation.MetalPressRecipes.MetalPressRecipe;
import techguns.util.ItemStackOreDict;

@ZenClass("mods.techguns.BlastFurnace")
public class BlastFurnaceTweaker {
	
	@ZenMethod
	public static void addRecipe(String input1, int amount1, String input2, int amount2, IItemStack output, int power, int duration) {
		CraftTweakerAPI.apply(new addInputAction(new ItemStackOreDict(input1), amount1, new ItemStackOreDict(input2), amount2, output, power, duration));
	}
	
	@ZenMethod
	public static void addRecipe(IItemStack input1, String input2, int amount2, IItemStack output, int power, int duration) {
		CraftTweakerAPI.apply(new addInputAction(input1, new ItemStackOreDict(input2), amount2, output, power, duration));
	}
	
	@ZenMethod
	public static void addRecipe(String input1, int amount1, IItemStack input2, IItemStack output, int power, int duration) {
		CraftTweakerAPI.apply(new addInputAction(new ItemStackOreDict(input1), amount1, input2, output, power, duration));
	}
	
	@ZenMethod
	public static void addRecipe(IItemStack input1, IItemStack input2, IItemStack output, int power, int duration) {
		CraftTweakerAPI.apply(new addInputAction(input1, input2, output, power, duration));
	}
	
	@ZenMethod
	public static void removeRecipe(IItemStack output) {
		CraftTweakerAPI.apply(new removeInputAction(output));
	}
	
	private static class removeInputAction implements IAction {

		ItemStack output;
		
		
		
		public removeInputAction(IItemStack output) {
			super();
			this.output = CraftTweakerMC.getItemStack(output);
		}

		@Override
		public void apply() {
			BlastFurnaceRecipes.removeRecipesFor(output);
		}

		@Override
		public String describe() {
			return "Remove Recipe(s) for "+output+" from BlastFurnace";
		}
		
	}
	
	private static class addInputAction implements IAction {

		ItemStackOreDict input1;
		ItemStackOreDict input2;
		ItemStack output;
		int amount1;
		int amount2;
		int powerPerTick;
		int duration;
		
		public addInputAction(ItemStackOreDict input1, int amount1, ItemStackOreDict input2, int amount2, IItemStack output, int power, int duration) {
			super();
			this.input1 = input1;
			this.input2 = input2;
			this.output = CraftTweakerMC.getItemStack(output);
			this.amount1=amount1;
			this.amount2=amount2;
			this.powerPerTick=power;
			this.duration=duration;
		}
		
		public addInputAction(IItemStack input1, ItemStackOreDict input2, int amount2, IItemStack output, int power, int duration) {
			super();
			this.input1 = new ItemStackOreDict(CraftTweakerMC.getItemStack(input1));
			this.input2 = input2;
			this.output = CraftTweakerMC.getItemStack(output);
			this.amount1=input1.getAmount();
			this.amount2=amount2;
			this.powerPerTick=power;
			this.duration=duration;
		}
		
		public addInputAction(ItemStackOreDict input1, int amount1, IItemStack input2, IItemStack output, int power, int duration) {
			super();
			this.input1 = input1;
			this.input2 = new ItemStackOreDict(CraftTweakerMC.getItemStack(input2));
			this.output = CraftTweakerMC.getItemStack(output);
			this.amount1=amount1;
			this.amount2=input2.getAmount();
			this.powerPerTick=power;
			this.duration=duration;
		}
		
		public addInputAction(IItemStack input1, IItemStack input2, IItemStack output, int power, int duration) {
			super();
			this.input1 = new ItemStackOreDict(CraftTweakerMC.getItemStack(input1));
			this.input2 = new ItemStackOreDict(CraftTweakerMC.getItemStack(input2));
			this.output = CraftTweakerMC.getItemStack(output);
			this.amount1=input1.getAmount();
			this.amount2=input2.getAmount();
			this.powerPerTick=power;
			this.duration=duration;
		}
		
		public boolean isValid() {
			if(this.input1.hasItems() && this.input2.hasItems()) {
				return !this.output.isEmpty();
			}
			return false;
		}
		
		@Override
		public void apply() {
			if(this.isValid()) {
				
				if(input1.isOreDict() && input2.isOreDict()) {
					BlastFurnaceRecipes.addRecipe(input1.oreDictName, amount1, input2.oreDictName, amount2, output, powerPerTick, duration);
				} else if (input1.isOreDict() && ! input2.isOreDict()) {
					ItemStack it2= input2.item.copy();
					it2.setCount(amount2);
					BlastFurnaceRecipes.addRecipe(input1.oreDictName, amount1, it2, output, powerPerTick, duration);
				} else if( ! input1.isOreDict() && input2.isOreDict()) {
					ItemStack it1= input1.item.copy();
					it1.setCount(amount1);
					BlastFurnaceRecipes.addRecipe(it1, input2.oreDictName, amount2, output, powerPerTick, duration);
				} else {
					ItemStack it1= input1.item.copy();
					ItemStack it2= input2.item.copy();
					it1.setCount(amount1);
					it2.setCount(amount2);
					BlastFurnaceRecipes.addRecipe(it1, it2, output, powerPerTick, duration);
				}
			}
		}
		
		@Override
		public String describe() {
			if(this.isValid()) {
				return "Add "+(input1)+"+"+(input2)+"-->"+output+" to BlastFurnace";
			} else {
				return "Failed to add Recipe: "+(input1)+"+"+(input2)+"-->"+output+" to BlastFurnace";
			}
		}
	
	}

}
