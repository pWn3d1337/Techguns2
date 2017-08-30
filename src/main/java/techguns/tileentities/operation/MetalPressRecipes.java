package techguns.tileentities.operation;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.OreDictionary;
import techguns.util.ItemStackOreDict;

public class MetalPressRecipes {

	private static ArrayList<MetalPressRecipe> recipes = new ArrayList<MetalPressRecipe>();
	
	public static void addRecipe(ItemStack input1, ItemStack input2, ItemStack output,boolean swap){
		recipes.add(new MetalPressRecipe(new ItemStackOreDict(input1),new ItemStackOreDict(input2),swap, output));
	}
	public static void addRecipe(String input1, String input2, ItemStack output,boolean swap){
		recipes.add(new MetalPressRecipe(new ItemStackOreDict(input1),new ItemStackOreDict(input2),swap, output));
	}
	public static void addRecipe(String input1, ItemStack input2, ItemStack output,boolean swap){
		recipes.add(new MetalPressRecipe(new ItemStackOreDict(input1),new ItemStackOreDict(input2),swap, output));
	}
	public static void addRecipe(ItemStack input1, String input2, ItemStack output,boolean swap){
		recipes.add(new MetalPressRecipe(new ItemStackOreDict(input1),new ItemStackOreDict(input2),swap, output));
	}
	
	public static ItemStack getOutputFor(ItemStack slot1, ItemStack slot2){
		for (int i=0; i < recipes.size(); i++){
			MetalPressRecipe recipe = recipes.get(i);
			if ( recipe.isValidInput(slot1, slot2)) {
				return recipe.output;
			}
		}
		return ItemStack.EMPTY;		
	}
	
	public static boolean hasRecipeUsing(ItemStack item){
		for(int i=0;i<recipes.size();i++){
			if(recipes.get(i).isItemPartOfRecipe(item)){
				return true;
			}
		}
		return false;
	}
	
	public static ArrayList<MetalPressRecipe> getRecipesUsing(ItemStack input){
		ArrayList<MetalPressRecipe> ret = new ArrayList<MetalPressRecipe>();
		
		for (MetalPressRecipe r : recipes){
			
			if (r.isItemPartOfRecipe(input)){
				ret.add(r);
			}
			
		}
		
		return ret;
	}
	
	public static MetalPressRecipe getRecipesFor(ItemStack output){
		for (MetalPressRecipe r: recipes){
			if (r.isOutputFor(output)){
				return r;
			}
		}
		return null;
	}
	
	public static ArrayList<MetalPressRecipe> getRecipes() {
		return recipes;
	}
	public static int getTotalPower(int recipe){
		return 20*getTotaltime(recipe);
	}
	public static int getTotaltime(int recipe){
		return 100;
	}
	
	public static class MetalPressRecipe implements IMachineRecipe {
		public ItemStackOreDict slot1;
		public ItemStackOreDict slot2;
		public boolean allowSwap;
		public ItemStack output;
		
		public boolean isOutputFor(ItemStack item){
			return OreDictionary.itemMatches(item, output, true);
		}
		
		public boolean isItemPartOfRecipe(ItemStack item){
			return this.slot1.isEqualWithOreDict(item) || this.slot2.isEqualWithOreDict(item);
		}
		
		public MetalPressRecipe(ItemStackOreDict slot1, ItemStackOreDict slot2,
				boolean allowSwap, ItemStack output) {
			this.slot1 = slot1;
			this.slot2 = slot2;
			this.allowSwap = allowSwap;
			this.output = output;
		}
		
		//Returns if this 2 itemstacks are a valid input for this recipe;
		public boolean isValidInput(ItemStack slot1, ItemStack slot2){
			if (this.slot1.isEqualWithOreDict(slot1) && this.slot2.isEqualWithOreDict(slot2)){
				return true;
			}
			if (allowSwap && (this.slot1.isEqualWithOreDict(slot2) && this.slot2.isEqualWithOreDict(slot1))){
				return true;
			}
			return false;
		}
		
		public ArrayList<ItemStack> getInputs(int slot){
			ItemStackOreDict stack;
			if(slot==1){
				stack = slot1;
			} else {
				stack = slot2;
			}
			return stack.getItemStacks();
		}

		@Override
		public List<List<ItemStack>> getItemInputs() {
			List<List<ItemStack>> inputs = new ArrayList<List<ItemStack>>();
			
			inputs.add(this.slot1.getItemStacks());
			inputs.add(this.slot2.getItemStacks());
			
			return inputs;
		}

		@Override
		public List<List<ItemStack>> getItemOutputs() {
			List<List<ItemStack>> outputs = new ArrayList<List<ItemStack>>();
			
			ArrayList<ItemStack> output = new ArrayList<ItemStack>();
			output.add(this.output);
			outputs.add(output);
			return outputs;
		}
		
		
	}
}

