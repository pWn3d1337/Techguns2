package techguns.tileentities.operation;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
import net.minecraftforge.oredict.OreDictionary;
import techguns.TGItems;
import techguns.tileentities.AmmoPressTileEnt;

public class AmmoPressBuildPlans {
	public static final int AMMOUNT_PISTOL = 12;
	public static final int AMMOUNT_SHOTGUN = 16;
	public static final int AMMOUNT_RIFLE = 8;
	public static final int AMMOUNT_SNIPER = 4;
	public static ArrayList<ItemStack> metal1 = new ArrayList<ItemStack>();
	public static ArrayList<ItemStack> metal2 = new ArrayList<ItemStack>();
	public static ArrayList<ItemStack> powder = new ArrayList<ItemStack>();

	public static void init(ArrayList<String> metal1_names, ArrayList<String> metal2_names, ArrayList<String> powder_names) {

		if (metal1_names != null) {
			for (int i = 0; i < metal1_names.size(); i++) {
				String name = metal1_names.get(i);
				NonNullList<ItemStack> ores = OreDictionary.getOres(name);
				if (ores.size() > 0) {
					for (int j = 0; j < ores.size(); j++) {
						metal1.add(ores.get(j));
					}
				}
			}
		}

		if (metal2_names != null) {
			for (int i = 0; i < metal2_names.size(); i++) {
				String name = metal2_names.get(i);
				NonNullList<ItemStack> ores = OreDictionary.getOres(name);
				if (ores.size() > 0) {
					for (int j = 0; j < ores.size(); j++) {
						ItemStack stack = ores.get(j).copy();
						stack.setCount(2);
						metal2.add(stack);
					}
				}
			}
		}

		if (powder_names != null) {
			for (int i = 0; i < powder_names.size(); i++) {
				String name = powder_names.get(i);
				NonNullList<ItemStack> ores = OreDictionary.getOres(name);
				if (ores.size() > 0) {
					for (int j = 0; j < ores.size(); j++) {
						powder.add(ores.get(j));
					}
				}
			}
		}

	}

	public static int getAmountForRecipe(int itemdmg) {
		if (itemdmg == TGItems.PISTOL_ROUNDS.getItemDamage()) {
			return AMMOUNT_PISTOL;
		} else if (itemdmg == TGItems.SHOTGUN_ROUNDS.getItemDamage()) {
			return AMMOUNT_SHOTGUN;
		} else if (itemdmg == TGItems.RIFLE_ROUNDS.getItemDamage()) {
			return AMMOUNT_RIFLE;
		} else if (itemdmg == TGItems.SNIPER_ROUNDS.getItemDamage()) {
			return AMMOUNT_SNIPER;
		}
		return 1;
	}

	public static ItemStack[] getPossibleResults() {
		return new ItemStack[] { TGItems.newStack(TGItems.PISTOL_ROUNDS, AMMOUNT_PISTOL), TGItems.newStack(TGItems.SHOTGUN_ROUNDS, AMMOUNT_SHOTGUN),
				TGItems.newStack(TGItems.RIFLE_ROUNDS, AMMOUNT_RIFLE), TGItems.newStack(TGItems.SNIPER_ROUNDS, AMMOUNT_SNIPER) };
	}

	public static boolean isInList(ItemStack it, ArrayList<ItemStack> list) {
		boolean valid = false;
		// System.out.println("Check for:"+it.getDisplayName());
		for (int i = 0; i < list.size(); i++) {

			// System.out.println("---Check
			// against:"+list.get(i).getDisplayName());
			if (OreDictionary.itemMatches(list.get(i), it, false)) {
				// System.out.println("---Valid");
				valid = true;
				break;
			} else {
				// System.out.println("---NOT VALID");
			}
		}
		return valid;
	}

	public static boolean isValidFor(ItemStack input, int slot) {
		switch (slot) {
		case AmmoPressTileEnt.SLOT_METAL1:
			return isInList(input, metal1);
		case AmmoPressTileEnt.SLOT_METAL2:
			return isInList(input, metal2);
		case AmmoPressTileEnt.SLOT_POWDER:
			return isInList(input, powder);
		default:
			return false;
		}
	}

	public static int getTotalPower(int recipeIndex) {
		return 100 * 5;
	}

	/*
	 * private static boolean itemStacksEqualForRecipe(ItemStack i1, ItemStack
	 * i2){ return (OreDictionary.itemMatches(i1, i2,true) ) &&
	 * (i2.stackSize>=i1.stackSize);
	 * 
	 * }
	 */

	public static ItemStack getOutputFor(ItemStack metal1, ItemStack metal2, ItemStack powder, int plan) {
		if (isValidFor(metal1, 0) && isValidFor(metal2, 1) && isValidFor(powder, 2)) {

			if (metal1.getCount() >= 1 && metal2.getCount() >= 2 && powder.getCount() >= 1) {

				switch (plan) {
				case 0:
					return new ItemStack(TGItems.PISTOL_ROUNDS.getItem(), AMMOUNT_PISTOL, TGItems.PISTOL_ROUNDS.getItemDamage());
				case 1:
					return new ItemStack(TGItems.SHOTGUN_ROUNDS.getItem(), AMMOUNT_SHOTGUN, TGItems.SHOTGUN_ROUNDS.getItemDamage());
				case 2:
					return new ItemStack(TGItems.RIFLE_ROUNDS.getItem(), AMMOUNT_RIFLE, TGItems.RIFLE_ROUNDS.getItemDamage());
				case 3:
					return new ItemStack(TGItems.SNIPER_ROUNDS.getItem(), AMMOUNT_SNIPER, TGItems.SNIPER_ROUNDS.getItemDamage());
				}

			}

		} else {
			// System.out.println("Not valid inputs");
		}
		return ItemStack.EMPTY;
	}
	
	public static class AmmoPressMachineRecipe implements IMachineRecipe{

		byte plan;
		
		public AmmoPressMachineRecipe(int plan) {
			super();
			this.plan = (byte) plan;
		}

		@Override
		public List<List<ItemStack>> getItemInputs() {
			List<List<ItemStack>> inputs = new ArrayList<List<ItemStack>>();
			
			inputs.add(metal1);
			inputs.add(metal2);
			inputs.add(powder);
			
			return inputs;
		}

		@Override
		public List<List<ItemStack>> getItemOutputs() {
			List<List<ItemStack>> outputs = new ArrayList<List<ItemStack>>();
			ArrayList<ItemStack> output = new ArrayList<ItemStack>();
			
			ItemStack stack = ItemStack.EMPTY;
			switch (plan) {
			case 0:
				stack= new ItemStack(TGItems.PISTOL_ROUNDS.getItem(), AMMOUNT_PISTOL, TGItems.PISTOL_ROUNDS.getItemDamage());
				break;
			case 1:
				stack = new ItemStack(TGItems.SHOTGUN_ROUNDS.getItem(), AMMOUNT_SHOTGUN, TGItems.SHOTGUN_ROUNDS.getItemDamage());
				break;
			case 2:
				stack = new ItemStack(TGItems.RIFLE_ROUNDS.getItem(), AMMOUNT_RIFLE, TGItems.RIFLE_ROUNDS.getItemDamage());
				break;
			case 3:
				stack = new ItemStack(TGItems.SNIPER_ROUNDS.getItem(), AMMOUNT_SNIPER, TGItems.SNIPER_ROUNDS.getItemDamage());
				break;
			}
			output.add(stack);
			outputs.add(output);
			return outputs;
		}
	}
	
	public static IMachineRecipe getRecipeForType(int type) {
		IMachineRecipe rec = new AmmoPressMachineRecipe(type);
		return rec;
	}
}
