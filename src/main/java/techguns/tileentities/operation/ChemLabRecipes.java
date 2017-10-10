package techguns.tileentities.operation;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.oredict.OreDictionary;
import techguns.tileentities.ChemLabTileEnt;
import techguns.util.ItemStackOreDict;
import techguns.util.ItemUtil;

public class ChemLabRecipes {
	
	private static ArrayList<ChemLabRecipe> recipes = new ArrayList<ChemLabRecipe>();
	
	public static MachineOperation getOutputFor(ChemLabTileEnt tile){
		ItemStack slot1 = tile.input1.get();
		ItemStack slot2 = tile.input2.get();
		ItemStack slot3 = tile.input_bottle.get();
		FluidStack fluidIn = tile.getCurrentInputFluid();
		
		for (int i=0; i < recipes.size(); i++){
			ChemLabRecipe recipe = recipes.get(i);
			if ( recipe.isValidInput(slot1, slot2,slot3,fluidIn)) {
				return recipe.getOperationFor(tile);
			}
		}
		return null;		
	}
	
	public static ArrayList<ChemLabRecipe> getRecipes() {
		return recipes;
	}
	
	public static int getTotalPower(int recipeIndex){
		return recipes.get(recipeIndex).powerPerTick*100;
	}
	
	public static ChemLabRecipe getRecipe(int index){
		return recipes.get(index);
	}
	
	public static ArrayList<ChemLabRecipe> getRecipesUsing(ItemStack input){
		ArrayList<ChemLabRecipe> ret = new ArrayList<ChemLabRecipe>();
		
		for (ChemLabRecipe r : recipes){
			
			if (r.isItemPartOfRecipe(input)){
				ret.add(r);
			}
			
		}
		
		return ret;
	}
	
	public static ChemLabRecipe getRecipesFor(ItemStack output){
		for (ChemLabRecipe r: recipes){
			if (ItemUtil.isItemEqual(r.output,output)){
				return r;
			}
		}
		return null;
	}
	
	public static boolean allowInFlaskSlot(ItemStack item){
		return !item.isEmpty() && item.getItem() == Items.GLASS_BOTTLE;
	}
	
	public static boolean hasRecipeUsing(ItemStack item){
		for(int i=0;i<recipes.size();i++){
			if(recipes.get(i).isItemPartOfRecipe(item)){
				return true;
			}
		}
		return false;
	}
	
	public static boolean allowAsInput2(ItemStack slot1,ItemStack item){
		for(int i=0;i<recipes.size();i++){
			if(recipes.get(i).isValidInputSlot1_2(slot1, item)){
				return true;
			}
		}
		return false;
	}
	
	public static void addRecipe(ItemStack input1,int amount1, ItemStack input2,int amount2, ItemStack bottle,int amount3, FluidStack fluidIn, FluidStack fluidOut, ItemStack output,boolean swap,int power){
		recipes.add(new ChemLabRecipe(new ItemStackOreDict(input1),amount1,new ItemStackOreDict(input2),amount2,bottle,amount3,fluidIn,swap,output,fluidOut,power));
	}
	public static void addRecipe(String input1,int amount1, String input2,int amount2, ItemStack bottle,int amount3, FluidStack fluidIn, FluidStack fluidOut, ItemStack output,boolean swap,int power){
		recipes.add(new ChemLabRecipe(new ItemStackOreDict(input1),amount1,new ItemStackOreDict(input2),amount2,bottle,amount3,fluidIn,swap,output,fluidOut,power));
	}
	public static void addRecipe(String input1,int amount1, ItemStack input2,int amount2, ItemStack bottle,int amount3, FluidStack fluidIn, FluidStack fluidOut, ItemStack output,boolean swap,int power){
		recipes.add(new ChemLabRecipe(new ItemStackOreDict(input1),amount1,new ItemStackOreDict(input2),amount2,bottle,amount3,fluidIn,swap,output,fluidOut,power));
	}
	public static void addRecipe(ItemStack input1,int amount1, String input2,int amount2, ItemStack bottle,int amount3, FluidStack fluidIn, FluidStack fluidOut, ItemStack output,boolean swap,int power){
		recipes.add(new ChemLabRecipe(new ItemStackOreDict(input1),amount1,new ItemStackOreDict(input2),amount2,bottle,amount3,fluidIn,swap,output,fluidOut,power));
	}
	
	public static void addRecipe(ItemStackOreDict input1,int amount1, ItemStackOreDict input2,int amount2, ItemStack bottle,int amount3, FluidStack fluidIn, FluidStack fluidOut, ItemStack output,boolean swap,int power){
		recipes.add(new ChemLabRecipe(input1,amount1,input2,amount2,bottle,amount3,fluidIn,swap,output,fluidOut,power));
	}

	public static class ChemLabRecipe implements IMachineRecipe {
		public ItemStackOreDict slot1;
		public ItemStackOreDict slot2;
		public ItemStack slot3;
		public FluidStack fluidIn;
		public FluidStack fluidOutput;
		public boolean allowSwap;
		public ItemStack output;
		public int[] amounts=new int[4];
		public int powerPerTick=5;
		
		
		public boolean isItemPartOfRecipe(ItemStack item){
			return (!this.slot1.isEmpty() && this.slot1.isEqualWithOreDict(item)) || (!this.slot2.isEmpty() && this.slot2.isEqualWithOreDict(item)) || (!this.slot3.isEmpty() && ItemUtil.isItemEqual(this.slot3, item));
		}
		
		public ChemLabRecipe(ItemStackOreDict slot1,int amount1, ItemStackOreDict slot2,int amount2, ItemStack bottle,int amount3, FluidStack fluidIn,
				boolean allowSwap, ItemStack output, FluidStack fluidOutput, int powerPerTick) {
			this.slot1 = slot1;
			this.slot2 = slot2;
			this.slot3 = bottle;
			if(this.slot3==null) {
				this.slot3=ItemStack.EMPTY;
			}
			this.fluidIn=fluidIn;
			this.fluidOutput=fluidOutput;
			this.allowSwap = allowSwap;
			this.output = output;
			if(this.output==null) {
				this.output=ItemStack.EMPTY;
			}
			this.amounts[0]=amount1;
			this.amounts[1]=amount2;
			this.amounts[2]=amount3;
			this.amounts[3]=fluidIn!=null?fluidIn.amount:0;
			this.powerPerTick=powerPerTick;
		}
		
		//Returns if this contents are a valid input for this recipe;
		public boolean isValidInput(ItemStack slot1, ItemStack slot2, ItemStack slot3, FluidStack fluidIn){
			//System.out.println("Slot1:"+slot1+"Slot2:"+slot2+"Slot3"+slot3);
			if (this.slot1.isEqualWithOreDict(slot1) && this.slot2.isEqualWithOreDict(slot2) && ((!this.slot3.isEmpty() && OreDictionary.itemMatches(this.slot3,slot3,true)) || (this.slot3.isEmpty() && slot3.isEmpty()))) {
				return checkLiquid(fluidIn) && checkAmounts(slot1,slot2,slot3,false);
			}
			if (allowSwap && this.slot1.isEqualWithOreDict(slot2) && this.slot2.isEqualWithOreDict(slot1) && ((!this.slot3.isEmpty() && OreDictionary.itemMatches(this.slot3,slot3,true)) || (this.slot3.isEmpty() && slot3.isEmpty()))){
				return checkLiquid(fluidIn) && checkAmounts(slot1,slot2,slot3,true);
			}
			return false;
		}
		
		public boolean isValidInputSlot1_2(ItemStack slot1, ItemStack slot2){
			if (this.slot1.isEqualWithOreDict(slot1) && this.slot2.isEqualWithOreDict(slot2)) {
				return true;
			}
			if (allowSwap && (this.slot1.isEqualWithOreDict(slot2) && this.slot2.isEqualWithOreDict(slot1))){
				return true;
			}
			return false;
		}
		
		
		private boolean isEnoughOrNull(ItemStack item, int amount){
			if (item.isEmpty()){
				return amount==0;
			} else {
				return item.getCount()>=amount;
			}
		}
		
		private boolean checkAmounts(ItemStack slot1, ItemStack slot2, ItemStack slot3, boolean swapped12){
			ItemStack i1=slot1;
			ItemStack i2=slot2;
			if (swapped12){
				i2 = slot1;
				i1 = slot2;
			}
			
			return isEnoughOrNull(i1,this.amounts[0]) && isEnoughOrNull(i2,this.amounts[1]) && isEnoughOrNull(slot3,this.amounts[2]);
		}
		
		private boolean checkLiquid(FluidStack fluid){
			if( this.fluidIn==null){
				return true;
			} else if(fluid==null){
				//need fluid but input is null
				return false;
			}
			if (this.fluidIn.getFluid() == fluid.getFluid()){
				return this.fluidIn.amount<=fluid.amount;
			}
			return false;
		}
		
		public MachineOperation getOperationFor(ChemLabTileEnt tile) {
			ArrayList<ItemStack> inputs = new ArrayList<ItemStack>();
			
			ItemStack input1 = tile.input1.get().copy();
			input1.setCount(this.amounts[0]);
			
			ItemStack input2 = tile.input2.get().copy();
			input2.setCount(this.amounts[1]);
			
			ItemStack bottle = tile.input_bottle.get().copy();
			bottle.setCount(this.amounts[2]);
			
			inputs.add(input1);
			inputs.add(input2);
			inputs.add(bottle);
			
			ArrayList<FluidStack> fluidsIn = new ArrayList<FluidStack>();
			if(this.fluidIn!=null) {
				FluidStack fluidin = this.fluidIn.copy();
				fluidin.amount = this.amounts[3];
				fluidsIn.add(fluidin);
			}
			
			ArrayList<ItemStack> outputs = new ArrayList<ItemStack>();
			ItemStack output = this.output.copy();
			outputs.add(output);
			
			ArrayList<FluidStack> fluidsOut = new ArrayList<FluidStack>();
			if(this.fluidOutput!=null) {
				FluidStack fluidout = this.fluidOutput.copy();
				fluidsOut.add(fluidout);
			}
			
			MachineOperation op = new MachineOperation(inputs, outputs, fluidsIn, fluidsOut,1);
			op.setPowerPerTick(this.powerPerTick);
			return op;
		}

		@Override
		public List<List<ItemStack>> getItemInputs() {
			ArrayList<List<ItemStack>> ret = new ArrayList<>();
			
			ret.add(this.slot1.getItemStacks(this.amounts[0]));
			ret.add(this.slot2.getItemStacks(this.amounts[1]));
			
			ArrayList<ItemStack> bottle = new ArrayList<>();
			ItemStack b = this.slot3.copy();
			b.setCount(this.amounts[2]);
			bottle.add(b);
			ret.add(bottle);
			return ret;
		}

		@Override
		public List<List<ItemStack>> getItemOutputs() {
			ArrayList<List<ItemStack>> ret = new ArrayList<>();
			ArrayList<ItemStack> out = new ArrayList<>();
			out.add(this.output);
			ret.add(out);
			return ret;
		}

		@Override
		public List<List<FluidStack>> getFluidInputs() {
			ArrayList<List<FluidStack>> ret = new ArrayList<>();
			ArrayList<FluidStack> f = new ArrayList<>();
			
			f.add(this.fluidIn);
			
			ret.add(f);
			return ret;
		}

		@Override
		public List<List<FluidStack>> getFluidOutputs() {
			ArrayList<List<FluidStack>> ret = new ArrayList<>();
			ArrayList<FluidStack> f = new ArrayList<>();
			
			f.add(this.fluidOutput);
			
			ret.add(f);
			return ret;
		}
		
		
	}
}