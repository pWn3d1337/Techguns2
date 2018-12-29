package techguns.plugins.crafttweaker;

import java.util.Iterator;

import crafttweaker.CraftTweakerAPI;
import crafttweaker.IAction;
import crafttweaker.api.item.IItemStack;
import crafttweaker.api.liquid.ILiquidStack;
import crafttweaker.api.minecraft.CraftTweakerMC;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.FluidStack;
import stanhebben.zenscript.annotations.ZenClass;
import stanhebben.zenscript.annotations.ZenMethod;
import techguns.tileentities.operation.ChemLabRecipes;
import techguns.tileentities.operation.ChemLabRecipes.ChemLabRecipe;
import techguns.util.ItemStackOreDict;
import techguns.util.ItemUtil;

@ZenClass("mods.techguns.ChemLab")
public class ChemLabTweaker {

	@ZenMethod
	public static void addRecipe(String input1,int amount1,String input2,int amount2, ILiquidStack fluidIn, boolean allowSwap, IItemStack output, ILiquidStack fluidOut, int rfTick){
		CraftTweakerAPI.apply(new addInputAction(TGCraftTweakerHelper.toItemStackOreDict(input1),amount1,TGCraftTweakerHelper.toItemStackOreDict(input2),amount2,fluidIn,allowSwap,output, fluidOut,rfTick));
	}
	
	@ZenMethod
	public static void addRecipe(IItemStack input1,int amount1,String input2,int amount2, ILiquidStack fluidIn, boolean allowSwap, IItemStack output, ILiquidStack fluidOut, int rfTick){
		CraftTweakerAPI.apply(new addInputAction(TGCraftTweakerHelper.toItemStackOreDict(input1),amount1,TGCraftTweakerHelper.toItemStackOreDict(input2),amount2,fluidIn,allowSwap,output, fluidOut,rfTick));
	}
	
	@ZenMethod
	public static void addRecipe(String input1,int amount1,IItemStack input2,int amount2, ILiquidStack fluidIn, boolean allowSwap, IItemStack output, ILiquidStack fluidOut, int rfTick){
		CraftTweakerAPI.apply(new addInputAction(TGCraftTweakerHelper.toItemStackOreDict(input1),amount1,TGCraftTweakerHelper.toItemStackOreDict(input2),amount2,fluidIn,allowSwap,output, fluidOut,rfTick));
	}
	
	@ZenMethod
	public static void addRecipe(IItemStack input1,int amount1,IItemStack input2,int amount2, ILiquidStack fluidIn, boolean allowSwap, IItemStack output, ILiquidStack fluidOut, int rfTick){
		CraftTweakerAPI.apply(new addInputAction(TGCraftTweakerHelper.toItemStackOreDict(input1),amount1,TGCraftTweakerHelper.toItemStackOreDict(input2),amount2,fluidIn,allowSwap,output, fluidOut,rfTick));
	}
	
	@ZenMethod
	public static void removeRecipe(IItemStack output, ILiquidStack fluidOut){
		CraftTweakerAPI.apply(new removeInputAction(output, fluidOut));
	}
	
	
	private static class addInputAction implements IAction
	{

		ChemLabRecipe added_recipe=null;
		ItemStackOreDict input1;
		ItemStackOreDict input2;
		//ItemStackOreDict bottle=null;
		FluidStack fluidIn;
		FluidStack fluidOut;
		boolean swap;
		ItemStack output = ItemStack.EMPTY;
		int power;
		int amount1;
		int amount2;
		
		public addInputAction(ItemStackOreDict input1, int amount1, ItemStackOreDict input2, int amount2, ILiquidStack fluidIn, boolean swap, IItemStack output,ILiquidStack fluidOut, int power) {
			super();
			this.input1 = input1;
			this.amount1=amount1;
			this.input2 = input2;
			this.amount2=amount2;
			this.fluidIn = CraftTweakerMC.getLiquidStack(fluidIn);
			this.fluidOut = CraftTweakerMC.getLiquidStack(fluidOut);
			this.swap = swap;
			this.output = CraftTweakerMC.getItemStack(output);
			this.power = power;
			
			if (amount2<=0){
				this.input2 = ItemStackOreDict.EMPTY;
			}
			
			if (this.fluidIn.amount<=0){
				this.fluidIn=null;
			}
			
			if (this.fluidOut.amount<=0){
				this.fluidOut=null;
			}
			
			if (!this.output.isEmpty() && this.output.getCount()<=0){
				this.output=ItemStack.EMPTY;
			}
			
		}

		@Override
		public void apply() {
			added_recipe = new ChemLabRecipe(input1,amount1,input2,amount2,ItemStack.EMPTY,0,fluidIn,swap,output,fluidOut,power);
			ChemLabRecipes.getRecipes().add(added_recipe);
		}
		
		@Override
		public String describe() {
			return "Add Recipe for"+(this.output.isEmpty()?"NO_ITEM":this.output)+"/"+(this.fluidOut==null?"NO_FLUID":this.fluidOut.getUnlocalizedName())+" to ChemLab";
		}
	
	}
	
	private static class removeInputAction implements IAction
	{

		ItemStackOreDict input1;
		ItemStackOreDict input2;
		//ItemStackOreDict bottle=null;
		FluidStack fluidIn;
		FluidStack fluidOut;
		ItemStack output;
		boolean checkInput;
		
		public removeInputAction(IItemStack output,ILiquidStack fluidOut) {
			super();
			this.fluidOut = CraftTweakerMC.getLiquidStack(fluidOut);
			this.output = CraftTweakerMC.getItemStack(output);
			checkInput=false;
		}
		
		
		public removeInputAction(IItemStack output,ILiquidStack fluidOut, ItemStackOreDict input1, ItemStackOreDict input2, ILiquidStack fluidIn) {
			super();
			this.fluidOut = CraftTweakerMC.getLiquidStack(fluidOut);
			this.output = CraftTweakerMC.getItemStack(output);
			checkInput=true;
			this.input1=input1;
			this.input2=input2;
			this.fluidIn = CraftTweakerMC.getLiquidStack(fluidIn);
		}
		
		
		
		@Override
		public void apply() {
			
			Iterator<ChemLabRecipe> iter = ChemLabRecipes.getRecipes().iterator();
			
			while (iter.hasNext()){
				ChemLabRecipe rec = iter.next();
								
					if (ItemUtil.isItemEqual(rec.output, this.output) && ItemUtil.isFluidEqual(rec.fluidOutput, this.fluidOut)){
						
						if (checkInput){
						
							if (rec.slot1.matches(input1) && rec.slot2.matches(input2) && ItemUtil.isFluidEqual(rec.fluidIn, this.fluidIn)){							
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
			return "Removed Recipe(s) for "+(this.output.isEmpty()?"NO_ITEM":this.output)+"/"+(this.fluidOut==null?"NO_FLUID":this.fluidOut.getUnlocalizedName())+" from ChemLab";
		}

	}	
	
}
