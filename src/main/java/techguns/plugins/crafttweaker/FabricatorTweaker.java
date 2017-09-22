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
import techguns.tileentities.operation.FabricatorRecipe;
import techguns.util.ItemStackOreDict;
import techguns.util.ItemUtil;

@ZenClass("mods.techguns.Fabricator")
public class FabricatorTweaker {
	
	@ZenMethod
	public static void removeRecipe(IItemStack output){
		CraftTweakerAPI.apply(new removeInputAction(output));
	}
	

	@ZenMethod
	public static void addRecipe(IItemStack input,int amount,IItemStack wire,int amount_wire, IItemStack powder, int amount_powder, IItemStack plate, int amount_plate, IItemStack output){
	CraftTweakerAPI.apply(new addInputAction(
	   TGCraftTweakerHelper.toItemStackOreDict(input),amount,
	   TGCraftTweakerHelper.toItemStackOreDict(wire), amount_wire,
	   TGCraftTweakerHelper.toItemStackOreDict(powder), amount_powder,
	   TGCraftTweakerHelper.toItemStackOreDict(plate), amount_plate,
	   output));
	}

	@ZenMethod
	public static void addRecipe(IItemStack input,int amount,IItemStack wire,int amount_wire, IItemStack powder, int amount_powder, String plate, int amount_plate, IItemStack output){
	CraftTweakerAPI.apply(new addInputAction(
	   TGCraftTweakerHelper.toItemStackOreDict(input),amount,
	   TGCraftTweakerHelper.toItemStackOreDict(wire), amount_wire,
	   TGCraftTweakerHelper.toItemStackOreDict(powder), amount_powder,
	   TGCraftTweakerHelper.toItemStackOreDict(plate), amount_plate,
	   output));
	}

	@ZenMethod
	public static void addRecipe(IItemStack input,int amount,IItemStack wire,int amount_wire, String powder, int amount_powder, IItemStack plate, int amount_plate, IItemStack output){
	CraftTweakerAPI.apply(new addInputAction(
	   TGCraftTweakerHelper.toItemStackOreDict(input),amount,
	   TGCraftTweakerHelper.toItemStackOreDict(wire), amount_wire,
	   TGCraftTweakerHelper.toItemStackOreDict(powder), amount_powder,
	   TGCraftTweakerHelper.toItemStackOreDict(plate), amount_plate,
	   output));
	}

	@ZenMethod
	public static void addRecipe(IItemStack input,int amount,IItemStack wire,int amount_wire, String powder, int amount_powder, String plate, int amount_plate, IItemStack output){
	CraftTweakerAPI.apply(new addInputAction(
	   TGCraftTweakerHelper.toItemStackOreDict(input),amount,
	   TGCraftTweakerHelper.toItemStackOreDict(wire), amount_wire,
	   TGCraftTweakerHelper.toItemStackOreDict(powder), amount_powder,
	   TGCraftTweakerHelper.toItemStackOreDict(plate), amount_plate,
	   output));
	}

	@ZenMethod
	public static void addRecipe(IItemStack input,int amount,String wire,int amount_wire, IItemStack powder, int amount_powder, IItemStack plate, int amount_plate, IItemStack output){
	CraftTweakerAPI.apply(new addInputAction(
	   TGCraftTweakerHelper.toItemStackOreDict(input),amount,
	   TGCraftTweakerHelper.toItemStackOreDict(wire), amount_wire,
	   TGCraftTweakerHelper.toItemStackOreDict(powder), amount_powder,
	   TGCraftTweakerHelper.toItemStackOreDict(plate), amount_plate,
	   output));
	}

	@ZenMethod
	public static void addRecipe(IItemStack input,int amount,String wire,int amount_wire, IItemStack powder, int amount_powder, String plate, int amount_plate, IItemStack output){
	CraftTweakerAPI.apply(new addInputAction(
	   TGCraftTweakerHelper.toItemStackOreDict(input),amount,
	   TGCraftTweakerHelper.toItemStackOreDict(wire), amount_wire,
	   TGCraftTweakerHelper.toItemStackOreDict(powder), amount_powder,
	   TGCraftTweakerHelper.toItemStackOreDict(plate), amount_plate,
	   output));
	}

	@ZenMethod
	public static void addRecipe(IItemStack input,int amount,String wire,int amount_wire, String powder, int amount_powder, IItemStack plate, int amount_plate, IItemStack output){
	CraftTweakerAPI.apply(new addInputAction(
	   TGCraftTweakerHelper.toItemStackOreDict(input),amount,
	   TGCraftTweakerHelper.toItemStackOreDict(wire), amount_wire,
	   TGCraftTweakerHelper.toItemStackOreDict(powder), amount_powder,
	   TGCraftTweakerHelper.toItemStackOreDict(plate), amount_plate,
	   output));
	}

	@ZenMethod
	public static void addRecipe(IItemStack input,int amount,String wire,int amount_wire, String powder, int amount_powder, String plate, int amount_plate, IItemStack output){
	CraftTweakerAPI.apply(new addInputAction(
	   TGCraftTweakerHelper.toItemStackOreDict(input),amount,
	   TGCraftTweakerHelper.toItemStackOreDict(wire), amount_wire,
	   TGCraftTweakerHelper.toItemStackOreDict(powder), amount_powder,
	   TGCraftTweakerHelper.toItemStackOreDict(plate), amount_plate,
	   output));
	}

	@ZenMethod
	public static void addRecipe(String input,int amount,IItemStack wire,int amount_wire, IItemStack powder, int amount_powder, IItemStack plate, int amount_plate, IItemStack output){
	CraftTweakerAPI.apply(new addInputAction(
	   TGCraftTweakerHelper.toItemStackOreDict(input),amount,
	   TGCraftTweakerHelper.toItemStackOreDict(wire), amount_wire,
	   TGCraftTweakerHelper.toItemStackOreDict(powder), amount_powder,
	   TGCraftTweakerHelper.toItemStackOreDict(plate), amount_plate,
	   output));
	}

	@ZenMethod
	public static void addRecipe(String input,int amount,IItemStack wire,int amount_wire, IItemStack powder, int amount_powder, String plate, int amount_plate, IItemStack output){
	CraftTweakerAPI.apply(new addInputAction(
	   TGCraftTweakerHelper.toItemStackOreDict(input),amount,
	   TGCraftTweakerHelper.toItemStackOreDict(wire), amount_wire,
	   TGCraftTweakerHelper.toItemStackOreDict(powder), amount_powder,
	   TGCraftTweakerHelper.toItemStackOreDict(plate), amount_plate,
	   output));
	}

	@ZenMethod
	public static void addRecipe(String input,int amount,IItemStack wire,int amount_wire, String powder, int amount_powder, IItemStack plate, int amount_plate, IItemStack output){
	CraftTweakerAPI.apply(new addInputAction(
	   TGCraftTweakerHelper.toItemStackOreDict(input),amount,
	   TGCraftTweakerHelper.toItemStackOreDict(wire), amount_wire,
	   TGCraftTweakerHelper.toItemStackOreDict(powder), amount_powder,
	   TGCraftTweakerHelper.toItemStackOreDict(plate), amount_plate,
	   output));
	}

	@ZenMethod
	public static void addRecipe(String input,int amount,IItemStack wire,int amount_wire, String powder, int amount_powder, String plate, int amount_plate, IItemStack output){
	CraftTweakerAPI.apply(new addInputAction(
	   TGCraftTweakerHelper.toItemStackOreDict(input),amount,
	   TGCraftTweakerHelper.toItemStackOreDict(wire), amount_wire,
	   TGCraftTweakerHelper.toItemStackOreDict(powder), amount_powder,
	   TGCraftTweakerHelper.toItemStackOreDict(plate), amount_plate,
	   output));
	}

	@ZenMethod
	public static void addRecipe(String input,int amount,String wire,int amount_wire, IItemStack powder, int amount_powder, IItemStack plate, int amount_plate, IItemStack output){
	CraftTweakerAPI.apply(new addInputAction(
	   TGCraftTweakerHelper.toItemStackOreDict(input),amount,
	   TGCraftTweakerHelper.toItemStackOreDict(wire), amount_wire,
	   TGCraftTweakerHelper.toItemStackOreDict(powder), amount_powder,
	   TGCraftTweakerHelper.toItemStackOreDict(plate), amount_plate,
	   output));
	}

	@ZenMethod
	public static void addRecipe(String input,int amount,String wire,int amount_wire, IItemStack powder, int amount_powder, String plate, int amount_plate, IItemStack output){
	CraftTweakerAPI.apply(new addInputAction(
	   TGCraftTweakerHelper.toItemStackOreDict(input),amount,
	   TGCraftTweakerHelper.toItemStackOreDict(wire), amount_wire,
	   TGCraftTweakerHelper.toItemStackOreDict(powder), amount_powder,
	   TGCraftTweakerHelper.toItemStackOreDict(plate), amount_plate,
	   output));
	}

	@ZenMethod
	public static void addRecipe(String input,int amount,String wire,int amount_wire, String powder, int amount_powder, IItemStack plate, int amount_plate, IItemStack output){
	CraftTweakerAPI.apply(new addInputAction(
	   TGCraftTweakerHelper.toItemStackOreDict(input),amount,
	   TGCraftTweakerHelper.toItemStackOreDict(wire), amount_wire,
	   TGCraftTweakerHelper.toItemStackOreDict(powder), amount_powder,
	   TGCraftTweakerHelper.toItemStackOreDict(plate), amount_plate,
	   output));
	}

	@ZenMethod
	public static void addRecipe(String input,int amount,String wire,int amount_wire, String powder, int amount_powder, String plate, int amount_plate, IItemStack output){
	CraftTweakerAPI.apply(new addInputAction(
	   TGCraftTweakerHelper.toItemStackOreDict(input),amount,
	   TGCraftTweakerHelper.toItemStackOreDict(wire), amount_wire,
	   TGCraftTweakerHelper.toItemStackOreDict(powder), amount_powder,
	   TGCraftTweakerHelper.toItemStackOreDict(plate), amount_plate,
	   output));
	}

	private static boolean checkLists(ItemStackOreDict item, byte slotId){
		boolean checkWire = checkInList(FabricatorRecipe.items_wireslot, item);
		boolean checkPowder = checkInList(FabricatorRecipe.items_powderslot, item);
		boolean checkPlate = checkInList(FabricatorRecipe.items_plateslot, item);
		
		if (!checkWire && !checkPowder && !checkPlate){
			//item is not in list, add to list
			switch(slotId){
				case 0:
					FabricatorRecipe.items_wireslot.add(item);
					break;
				case 1:
					FabricatorRecipe.items_powderslot.add(item);
					break;
				case 2:
					FabricatorRecipe.items_plateslot.add(item);
					break;
			}
			
			return true;
		} else if ( (checkWire&&slotId==0&&!checkPowder&&!checkPlate) || (checkPowder&&slotId==1&&!checkWire&&!checkPlate) || (checkPlate&&slotId==2&&!checkWire&&!checkPowder)) {
			return true;
		} else {
			return false;
		}
		
	}
	
	private static boolean checkInList(ArrayList<ItemStackOreDict> list, ItemStackOreDict item){
		for (ItemStackOreDict it : list){
			if (it.matches(item)){
				return true;
			}
		}
		return false;
	}
	
	private static class addInputAction implements IAction
	{

		FabricatorRecipe added_recipe=null;
		ItemStackOreDict input;
		int amount;
		ItemStackOreDict input_wire;
		int amount_wire;
		ItemStackOreDict input_powder;
		int amount_powder;
		ItemStackOreDict input_plate;
		int amount_plate;

		ItemStack output;

		boolean warning =false;
		
		public addInputAction(ItemStackOreDict input, int amount, ItemStackOreDict input_wire, int amount_wire,
				ItemStackOreDict input_powder, int amount_powder, ItemStackOreDict input_plate, int amount_plate, IItemStack output) {
			super();
			this.input = input;
			this.amount = amount;
			this.input_wire = input_wire;
			this.amount_wire = amount_wire;
			this.input_powder = input_powder;
			this.amount_powder = amount_powder;
			this.input_plate = input_plate;
			this.amount_plate = amount_plate;
			this.output = CraftTweakerMC.getItemStack(output);
			
			if(amount_wire<=0){
				this.input_wire=ItemStackOreDict.EMPTY;
			}
			if(amount_powder<=0){
				this.input_powder=ItemStackOreDict.EMPTY;
			}
			
			if (!checkLists(input_wire,(byte)0)){
				warning=true;
			}
			if (!checkLists(input_powder,(byte)1)){
				warning=true;
			}
			if (!checkLists(input_plate,(byte)2)){
				warning=true;
			}
		}
		
		@Override
		public void apply() {
			added_recipe = new FabricatorRecipe(input, amount, input_wire, amount_wire, input_powder, amount_powder, input_plate, amount_plate, output, output.getCount());
			FabricatorRecipe.getRecipes().add(added_recipe);
		}

		@Override
		public String describe() {
			if (!warning){
				return "Add Recipe for "+output+" to Fabricator";
			} else {
				return "WARNING while adding Fabricator Recipe for "+output+": One input already in use in another slot by other recipes! THIS WILL NOT WORK!";
			}
		}

	}

	private static void removeFromLists(ItemStackOreDict wire, ItemStackOreDict powder, ItemStackOreDict plate){
		
		if (checkListRemoval(0, wire)){
			removeFromList(FabricatorRecipe.items_wireslot, wire);
		}
		
		if (checkListRemoval(1, powder)){
			removeFromList(FabricatorRecipe.items_powderslot, powder);
		}
		
		if (checkListRemoval(2, plate)){
			removeFromList(FabricatorRecipe.items_plateslot, plate);
		}
		
	}
	
	private static void removeFromList(ArrayList<ItemStackOreDict> list, ItemStackOreDict item){
		
		Iterator<ItemStackOreDict> it = list.iterator();
		while (it.hasNext()){
			ItemStackOreDict i = it.next();
			
			if (i.matches(item)){
				it.remove();
			}
		}
	}
	
	private static boolean checkListRemoval(int slot, ItemStackOreDict wire){
		
		ArrayList<FabricatorRecipe> rec = FabricatorRecipe.getRecipes();
		for (int i=0; i<rec.size(); i++){
			if (slot==0){
				if (rec.get(i).wireSlot.matches(wire)){
					return false;
				}
			} else if (slot==1){
				if (rec.get(i).powderSlot.matches(wire)){
					return false;
				}
			} else if (slot==2){
				if (rec.get(i).plateSlot.matches(wire)){
					return false;
				}
			}
		}
		
		return true;
	}
	
	private static class removeInputAction implements IAction
	{

		ItemStack output;
		
		public removeInputAction(IItemStack output) {
			super();
			this.output = CraftTweakerMC.getItemStack(output);
		}
		

		@Override
		public void apply() {
			
			Iterator<FabricatorRecipe> iter = FabricatorRecipe.getRecipes().iterator();
			
			while (iter.hasNext()){
				FabricatorRecipe rec = iter.next();
								
				if (ItemUtil.isItemEqual(rec.outputItem, this.output)){
					
						//removed_recipes.add(rec);
						iter.remove();
						removeFromLists(rec.wireSlot,rec.powderSlot,rec.plateSlot);
				}
			}
		}	
			
		@Override
		public String describe() {
			return "Removed Recipe(s) for "+(this.output)+" from Fabricator";
		}

	}
}
