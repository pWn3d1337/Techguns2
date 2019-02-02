package techguns.plugins.crafttweaker;

import java.util.Iterator;

import crafttweaker.CraftTweakerAPI;
import crafttweaker.IAction;
import crafttweaker.api.item.IItemStack;
import crafttweaker.api.minecraft.CraftTweakerMC;
import net.minecraft.item.ItemStack;
import stanhebben.zenscript.annotations.ZenClass;
import stanhebben.zenscript.annotations.ZenMethod;
import techguns.tileentities.operation.MetalPressRecipes;
import techguns.tileentities.operation.MetalPressRecipes.MetalPressRecipe;
import techguns.util.ItemStackOreDict;
import techguns.util.ItemUtil;

@ZenClass("mods.techguns.MetalPress")
public class MetalPressTweaker {

	
	@ZenMethod
	public static void addRecipe(IItemStack input1,IItemStack input2, IItemStack output, boolean allowSwap){
		CraftTweakerAPI.apply(new addInputAction(TGCraftTweakerHelper.toItemStackOreDict(input1),TGCraftTweakerHelper.toItemStackOreDict(input2),allowSwap,output));
	}
	@ZenMethod
	public static void addRecipe(String input1,IItemStack input2, IItemStack output, boolean allowSwap){
		CraftTweakerAPI.apply(new addInputAction(TGCraftTweakerHelper.toItemStackOreDict(input1),TGCraftTweakerHelper.toItemStackOreDict(input2),allowSwap,output));
	}
	@ZenMethod
	public static void addRecipe(IItemStack input1,String input2, IItemStack output, boolean allowSwap){
		CraftTweakerAPI.apply(new addInputAction(TGCraftTweakerHelper.toItemStackOreDict(input1),TGCraftTweakerHelper.toItemStackOreDict(input2),allowSwap,output));
	}
	@ZenMethod
	public static void addRecipe(String input1,String input2, IItemStack output, boolean allowSwap){
		CraftTweakerAPI.apply(new addInputAction(TGCraftTweakerHelper.toItemStackOreDict(input1),TGCraftTweakerHelper.toItemStackOreDict(input2),allowSwap,output));
	}
	
	
	@ZenMethod
	public static void removeRecipe(IItemStack output){
		CraftTweakerAPI.apply(new removeInputAction((ItemStackOreDict)null,(ItemStackOreDict)null,output));
	}
	
	@ZenMethod
	public static void removeRecipe(String input1,String input2, IItemStack output){
		CraftTweakerAPI.apply(new removeInputAction(TGCraftTweakerHelper.toItemStackOreDict(input1),TGCraftTweakerHelper.toItemStackOreDict(input2),output));
	}
	@ZenMethod
	public static void removeRecipe(IItemStack input1,String input2, IItemStack output){
		CraftTweakerAPI.apply(new removeInputAction(TGCraftTweakerHelper.toItemStackOreDict(input1),TGCraftTweakerHelper.toItemStackOreDict(input2),output));
	}
	@ZenMethod
	public static void removeRecipe(String input1,IItemStack input2, IItemStack output){
		CraftTweakerAPI.apply(new removeInputAction(TGCraftTweakerHelper.toItemStackOreDict(input1),TGCraftTweakerHelper.toItemStackOreDict(input2),output));
	}
	@ZenMethod
	public static void removeRecipe(IItemStack input1,IItemStack input2, IItemStack output){
		CraftTweakerAPI.apply(new removeInputAction(TGCraftTweakerHelper.toItemStackOreDict(input1),TGCraftTweakerHelper.toItemStackOreDict(input2),output));
	}
	
	
	private static class addInputAction implements IAction
	{

		ItemStackOreDict input1;
		ItemStackOreDict input2;
		boolean swap;
		ItemStack output;
		
		public addInputAction(ItemStackOreDict input1, ItemStackOreDict input2, boolean swap, IItemStack output) {
			super();
			this.input1 = input1;
			this.input2 = input2;
			this.swap = swap;
			this.output = CraftTweakerMC.getItemStack(output);
		}
		
		@Override
		public void apply() {
			MetalPressRecipes.getRecipes().add(new MetalPressRecipe(input1, input2, swap, output));
		}
		
		@Override
		public String describe() {
			return "Add "+(input1)+"+"+(input2)+"-->"+output+" to MetalPress";
		}
	
	}
	
	private static class removeInputAction implements IAction
	{

		ItemStackOreDict input1;
		ItemStackOreDict input2;
		ItemStack output;
		
		public removeInputAction(ItemStackOreDict input1, ItemStackOreDict input2, IItemStack output) {
			super();
			this.input1 = input1;
			this.input2 = input2;
			this.output = CraftTweakerMC.getItemStack(output);
		}
		
		@Override
		public void apply() {
			
			Iterator<MetalPressRecipe> iter = MetalPressRecipes.getRecipes().iterator();
			
			while (iter.hasNext()){
				MetalPressRecipe rec = iter.next();
								
					if (ItemUtil.isItemEqual(rec.output, this.output)){
						
						if (input1!=null && !input1.isEmpty()){
						
							if (rec.slot1.matches(this.input1) && rec.slot2.matches(this.input2)){
								iter.remove();
							}	
								
						} else {
							iter.remove();
						}
					}
			}
			
		}
		
		@Override
		public String describe() {
			if (this.input1!=null && !this.input1.isEmpty()){
				return "Removed Recipe "+input1+"+"+input2+"-->"+this.output+" from MetalPress";
			}
			return "Removed Recipe(s) for "+this.output+" from MetalPress";
		}
		
		
	}
	
}
