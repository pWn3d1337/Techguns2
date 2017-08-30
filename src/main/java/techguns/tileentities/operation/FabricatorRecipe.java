package techguns.tileentities.operation;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.item.ItemStack;
import techguns.TGItems;
import techguns.tileentities.FabricatorTileEntMaster;
import techguns.util.ItemStackOreDict;

public class FabricatorRecipe implements IMachineRecipe{

	public static ArrayList<ItemStackOreDict> items_wireslot = new ArrayList<>();
	
	public static ArrayList<ItemStackOreDict> items_powderslot = new ArrayList<>();
	
	public static ArrayList<ItemStackOreDict> items_plateslot = new ArrayList<>();
	
	
	public static ItemStackOreDict copperWires = new ItemStackOreDict("wireCopper",1);
	public static ItemStackOreDict goldWires = new ItemStackOreDict("wireGold",1);
	
	public static ItemStackOreDict redstone = new ItemStackOreDict("dustRedstone",1);
	
	public static ItemStackOreDict plastic = new ItemStackOreDict("sheetPlastic",1);
	
	public static ItemStackOreDict circuit_basic = new ItemStackOreDict("circuitBasic",1);
	public static ItemStackOreDict circuit_elite = new ItemStackOreDict("circuitElite",1);
	
	public static ItemStackOreDict mechanicalPartsT3 = new ItemStackOreDict(TGItems.MECHANICAL_PARTS_CARBON,1);
	
	public static ItemStackOreDict carbonPlate = new ItemStackOreDict("plateCarbon",1);
	public static ItemStackOreDict titaniumPlate = new ItemStackOreDict("plateTitanium",1);
	public static ItemStackOreDict leadPlate = new ItemStackOreDict("plateLead",1);
	
	static{
		items_wireslot.add(copperWires);
		items_wireslot.add(goldWires);
		items_wireslot.add(circuit_basic);
		items_wireslot.add(circuit_elite);
		
		items_powderslot.add(redstone);
		items_powderslot.add(mechanicalPartsT3);
		
		items_plateslot.add(plastic);
		items_plateslot.add(carbonPlate);
		items_plateslot.add(titaniumPlate);
		items_plateslot.add(leadPlate);
	}

	
	public static ArrayList<FabricatorRecipe> recipes = new ArrayList<FabricatorRecipe>();
	
	/**
	 * NON-STATIC VARIABLES
	 */
	
	public ItemStackOreDict inputItem;
	public byte amountInput;
	
	public ItemStackOreDict wireSlot;
	public byte amountWire;
	
	public ItemStackOreDict powderSlot;
	public byte amountPowder;
	
	public ItemStackOreDict plateSlot;
	public byte amountPlates;

	public ItemStack outputItem;
	public byte amountOutput;
	
	
	public static void addRecipe(ItemStackOreDict inputItem, int amountInput, ItemStackOreDict wireSlot, int amountWire, ItemStackOreDict powderSlot,
			int amountPowder, ItemStackOreDict plateSlot, int amountPlates, ItemStack outputItem, int amountOutput){
		recipes.add(new FabricatorRecipe(inputItem, amountInput, wireSlot, amountWire, powderSlot, amountPowder, plateSlot, amountPlates, outputItem, amountOutput));
	}
	
	
	public boolean usesItem(ItemStack itm){
		return this.inputItem.isEqualWithOreDict(itm) || this.wireSlot.isEqualWithOreDict(itm) || this.powderSlot.isEqualWithOreDict(itm) || this.plateSlot.isEqualWithOreDict(itm);
	}
	
	
	public FabricatorRecipe(ItemStackOreDict inputItem, int amountInput, ItemStackOreDict wireSlot, int amountWire, ItemStackOreDict powderSlot,
			int amountPowder, ItemStackOreDict plateSlot, int amountPlates, ItemStack outputItem, int amountOutput) {
		this.inputItem = inputItem;
		this.amountInput = (byte) amountInput;
		this.wireSlot = wireSlot;
		this.amountWire = (byte) amountWire;
		this.powderSlot = powderSlot;
		this.amountPowder = (byte) amountPowder;
		this.plateSlot = plateSlot;
		this.amountPlates = (byte) amountPlates;
		this.outputItem = outputItem;
		this.amountOutput = (byte) amountOutput;
	}
	
	private boolean matches(ItemStack input, ItemStack wires, ItemStack powder, ItemStack plate){
		
		if (this.inputItem.isEqualWithOreDict(input) && input.getCount()>=this.amountInput){
			if (this.wireSlot.isEmpty() || (!wires.isEmpty() && this.wireSlot.isEqualWithOreDict(wires) && wires.getCount()>=this.amountWire)){
				if (this.powderSlot.isEmpty() || (!powder.isEmpty() && this.powderSlot.isEqualWithOreDict(powder) && powder.getCount()>=this.amountPowder)){
					if (this.plateSlot.isEmpty() || (!plate.isEmpty() && this.plateSlot.isEqualWithOreDict(plate) && plate.getCount()>=this.amountPlates)){
						return true;
					}
				}
			}
		}
		return false;
	}
	
	
	public static boolean itemStackInList(ArrayList<ItemStackOreDict> list, ItemStack stack){
		for(int i=0;i<list.size();i++){
			ItemStackOreDict orestack = list.get(i);
			if(orestack.isEqualWithOreDict(stack)){
				return true;
			}
		}
		return false;
	}
	
	public static RecipeData getRecipeDataFor(ItemStack input, ItemStack wires, ItemStack powder, ItemStack plate, int maxMultiplier, ItemStack outputSlot){
		FabricatorRecipe match=null;	
		
		for (int i=0; i<recipes.size();i++){
			FabricatorRecipe rec = recipes.get(i);
			
			if(rec.matches(input, wires, powder, plate)){
				match=rec;
				break;
			}
		}
		
		if (match!=null && canMerge(outputSlot, match.outputItem, match.amountOutput)){
			
			//calculate multiplier
			int maxMultiInput = getMaxMulti(input.getCount(), match.amountInput, maxMultiplier);
			int maxMultiWires=0;
			if (!wires.isEmpty()){
				maxMultiWires = getMaxMulti(wires.getCount(), match.amountWire, maxMultiplier);
			} else {
				maxMultiWires = 8;
			}
			int maxMultiPowder;
			if (!powder.isEmpty()){
				maxMultiPowder = getMaxMulti(powder.getCount(), match.amountPowder, maxMultiplier);
			} else {
				maxMultiPowder = 8;
			}
			int maxMultiPlate = getMaxMulti(plate.getCount(), match.amountPlates, maxMultiplier);
			int maxMultiOutput = getMaxMultiOutput(outputSlot.isEmpty()?0:outputSlot.getCount(), match.amountOutput, maxMultiplier, match.outputItem.getMaxStackSize());
			
			int multi = getMinArgument(maxMultiInput, maxMultiWires, maxMultiPowder, maxMultiPlate, maxMultiOutput);
			
			RecipeData data = new RecipeData(match.outputItem, match.amountOutput, match.amountInput, match.amountWire, match.amountPowder, match.amountPlates, multi);
			return data;
		}
		
		
		return null;
	}
	
	private static boolean canMerge(ItemStack content, ItemStack newStack, int newAmount){
		if (content.isEmpty()){
			return true;
		}
		if(content.getItem()==newStack.getItem() && content.getItemDamage()== newStack.getItemDamage()){
			return content.getCount()+newAmount <= content.getMaxStackSize();
		}
		return false;
	}
	
	
	private static int getMaxMulti(int stackSize, int consumption, int maxMulti){
		int mult = maxMulti;
		
		while(mult*consumption > stackSize){
			mult--;
		}
		return mult;
	}
	
	private static int getMaxMultiOutput(int stackSize, int amount, int maxMulti, int maxStackSize){
		int mult = maxMulti;
		
		while((mult*amount)+stackSize > maxStackSize){
			mult--;
		}
		return mult;
	}
	
	private static int getMinArgument(int... values){
		int min=Integer.MAX_VALUE;
		for(int i=0;i<values.length;i++){
			int x = values[i];
			if (x<min){
				min=x;
			}
		}
		return min;
	}
	
	
	public static class RecipeData {
		public ItemStack output;
		public byte outputAmount;
		public byte inputConsumption;
		public byte wireConsumption;
		public byte powderConsumption;
		public byte plateConsumption;
		public byte stackMultiplier;
		public RecipeData(ItemStack output, int outputAmount, int inputConsumption, int wireConsumption, int powderConsumption, int plateConsumption, int stackMultiplier) {
			super();
			this.output = output;
			this.outputAmount = (byte) outputAmount;
			this.inputConsumption = (byte) inputConsumption;
			this.wireConsumption = (byte) wireConsumption;
			this.powderConsumption = (byte) powderConsumption;
			this.plateConsumption = (byte) plateConsumption;
			this.stackMultiplier = (byte) stackMultiplier;
		}
		
		public MachineOperation createOperation(FabricatorTileEntMaster tile) {
						
			ItemStack stack = tile.input.get().copy();
			stack.setCount(inputConsumption);
			
			ItemStack wires = tile.wireslot.get().copy();
			if(!wires.isEmpty()) {
				wires.setCount(wireConsumption);
			}
			
			ItemStack powder = tile.powderslot.get().copy();
			if(!powder.isEmpty()) {
				powder.setCount(powderConsumption);
			}
			
			ItemStack plates = tile.plateslot.get().copy();
			if (!plates.isEmpty()) {
				plates.setCount(plateConsumption);
			}
			
			ItemStack out = output.copy();
			out.setCount(outputAmount);
			
			MachineOperation op = new MachineOperation(out, stack, wires, powder, plates);
			op.setStackMultiplier(stackMultiplier);
			op.setPowerPerTick(tile.getNeededPower());
			return op;
		}
	}
	
	public static ArrayList<FabricatorRecipe> getRecipes() {
		return recipes;
	}


	@Override
	public List<List<ItemStack>> getItemInputs() {
		ArrayList<List<ItemStack>> ret = new ArrayList<>();
		ret.add(this.inputItem.getItemStacks(this.amountInput));
		ret.add(this.wireSlot.getItemStacks(this.amountWire));
		ret.add(this.powderSlot.getItemStacks(this.amountPowder));
		ret.add(this.plateSlot.getItemStacks(this.amountPlates));
		return ret;

	}

	@Override
	public List<List<ItemStack>> getItemOutputs() {
		ArrayList<List<ItemStack>> ret = new ArrayList<>();
		ArrayList<ItemStack> output = new ArrayList<>();
		output.add(this.outputItem);
		ret.add(output);
		return ret;
	}	
}
