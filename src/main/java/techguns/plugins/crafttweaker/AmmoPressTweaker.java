package techguns.plugins.crafttweaker;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import crafttweaker.CraftTweakerAPI;
import crafttweaker.IAction;
import crafttweaker.api.item.IIngredient;
import crafttweaker.api.item.IItemStack;
import crafttweaker.api.minecraft.CraftTweakerMC;
import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.OreDictionary;
import stanhebben.zenscript.annotations.ZenClass;
import stanhebben.zenscript.annotations.ZenMethod;
import techguns.tileentities.operation.AmmoPressBuildPlans;

@ZenClass("mods.techguns.AmmoPress")
public class AmmoPressTweaker {

	private AmmoPressTweaker(){};
	
	@ZenMethod
	public static void addMetal1(IIngredient input){
		CraftTweakerAPI.apply(new addInputAction(input, (byte)0));
	}
	
	@ZenMethod
	public static void addMetal2(IIngredient input){
		CraftTweakerAPI.apply(new addInputAction(input, (byte)1));
	}
	
	@ZenMethod
	public static void addPowder(IIngredient input){
		CraftTweakerAPI.apply(new addInputAction(input, (byte)2));
	}
	
	@ZenMethod
	public static void removeMetal1(IIngredient input){
		CraftTweakerAPI.apply(new removeInputAction(input, (byte)0));
	}
	
	@ZenMethod
	public static void removeMetal2(IIngredient input){
		CraftTweakerAPI.apply(new removeInputAction(input, (byte)1));
	}
	
	@ZenMethod
	public static void removePowder(IIngredient input){
		CraftTweakerAPI.apply(new removeInputAction(input, (byte)2));
	}
	

	private static void addToList(IIngredient item, ArrayList<ItemStack> list){
		List<IItemStack> items = item.getItems();
		
		for(int i =0;i<items.size();i++){
			ItemStack it = CraftTweakerMC.getItemStack(items.get(i));
			if(it!=null){
				list.add(it);
			}
		}
	}

	private static void removeFromList(IIngredient item, ArrayList<ItemStack> list){
		List<IItemStack> items = item.getItems();
		
		for(int i =0;i<items.size();i++){
			ItemStack it = CraftTweakerMC.getItemStack(items.get(i));
			
			Iterator<ItemStack> iter = list.iterator();
			while (iter.hasNext()){
				ItemStack input = iter.next();
				//System.out.println("Checking:"+it.toString()+"->"+input.toString());
				if (OreDictionary.itemMatches(it, input, false)){
					//System.out.println("MATCH!");
					iter.remove();
				}
			}
		}
	}

	private static String getSlotName(byte type){
		switch(type){
			case 1:
				return "Metal2";
			case 2:
				return "Powder";
			case 0:
			default:
				return "Metal1";
		}
	}
	
	private static class addInputAction implements IAction
	{

		IIngredient input;
		ArrayList<ItemStack> list;
		byte type;
		
		public addInputAction(IIngredient input, byte type) {
			super();
			this.input = input;
			this.type=type;
			switch(type){
				case 1:
					this.list = AmmoPressBuildPlans.metal2;
					break;
				case 2:
					this.list = AmmoPressBuildPlans.powder;
					break;
				case 0:
				default:
					this.list = AmmoPressBuildPlans.metal1;
					break;
			}
		}

		@Override
		public void apply() {
			addToList(input, list);
		}

		@Override
		public String describe() {
			return "Add "+TGCraftTweakerHelper.getItemNames(input)+" to AmmoPressSlot: "+getSlotName(type);
		}

	
		
	}
	
	private static class removeInputAction implements IAction
	{

		IIngredient input;
		ArrayList<ItemStack> list;
		byte type;
		
		public removeInputAction(IIngredient input, byte type) {
			super();
			this.input = input;
			this.type=type;
			switch(type){
				case 1:
					this.list = AmmoPressBuildPlans.metal2;
					break;
				case 2:
					this.list = AmmoPressBuildPlans.powder;
					break;
				case 0:
				default:
					this.list = AmmoPressBuildPlans.metal1;
					break;
			}
		}

		@Override
		public void apply() {
			removeFromList(input, list);
		}

	
		@Override
		public String describe() {
			return "Remove "+TGCraftTweakerHelper.getItemNames(input)+" to AmmoPressSlot: "+getSlotName(type);
		}
		
	}
	
}
