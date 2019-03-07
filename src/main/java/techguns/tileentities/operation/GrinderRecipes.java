package techguns.tileentities.operation;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
import net.minecraftforge.fluids.FluidStack;
import techguns.TGArmors;
import techguns.items.armors.GenericArmor;
import techguns.items.guns.GenericGun;
import techguns.tileentities.GrinderTileEnt;
import techguns.util.ItemStackOreDict;

public class GrinderRecipes {
	
	public static ArrayList<GrinderRecipe> recipes = new ArrayList<>();
	
	public static void addRecipe(ItemStackOreDict input, ItemStack... outputs) {
		recipes.add(new GrinderRecipe(input, outputs));
	}
	
	public static void addRecipe(ItemStack input, ItemStack... outputs) {
		recipes.add(new GrinderRecipe(new ItemStackOreDict(input, input.getCount()), outputs));
	}
	
	public static void removeRecipeFor(ItemStack input) {
		Iterator<GrinderRecipe> it = recipes.iterator();
		while(it.hasNext()) {
			GrinderRecipe r = it.next();
			if(r.input.matches(input)) {
				it.remove();
			}
		}
	}
	
	public static void addRecipe(GenericGun gun, ItemStack[]... outputs) {
		ArrayList<ItemStack> al = new ArrayList<>();
		Arrays.stream(outputs).forEach(o -> Arrays.stream(o).forEach(p -> al.add(p)));
		ItemStack input = new ItemStack(gun,1);
		recipes.add(new GrinderRecipe(new ItemStackOreDict(input, input.getCount()), al.toArray(new ItemStack[al.size()])));
	}
	
	public static void addRecipe(ItemStack input, ItemStack[]... outputs) {
		ArrayList<ItemStack> al = new ArrayList<>();
		Arrays.stream(outputs).forEach(o -> Arrays.stream(o).forEach(p -> al.add(p)));
		
		recipes.add(new GrinderRecipe(new ItemStackOreDict(input, input.getCount()), al.toArray(new ItemStack[al.size()])));
	}
	
	public static void addRecipeChance(ItemStack input, ItemStack[] outputs, double[] chances) {
				
		recipes.add(new GrinderRecipeChance(new ItemStackOreDict(input, input.getCount()), outputs, chances));
	}
	
	public static void addGenericArmorRecipes() {
		TGArmors.armors.forEach(a -> {
			recipes.add(new GrinderRecipeGenericArmor(a));
		});
	}
	
	public static MachineOperation getOperationForInput(ItemStack input,  GrinderTileEnt tile) {
		for(GrinderRecipe r: recipes) {
			if(r.matchesInput(new ItemStackOreDict(input,1))) {
				return r.getOperation(input, tile);
			}
		}
		return null;
	}
	
	public static boolean hasRecipeForInput(ItemStack input) {
		for(GrinderRecipe r: recipes) {
			if(r.matchesInput(new ItemStackOreDict(input,1))) {
				return true;
			}
		}
		return false;
	}
		
	public static class GrinderRecipe implements IMachineRecipe {
		protected ItemStackOreDict input;
		
		protected ItemStack[] outputs;

		public GrinderRecipe(ItemStackOreDict input, ItemStack... outputs) {
			super();
			this.input = input;
			this.outputs = outputs;
		}
		
		public boolean matchesInput(ItemStackOreDict input) {
			return this.input.matches(input);
		}
		
		public MachineOperation getOperation(ItemStack input, GrinderTileEnt tile) {
			
			ArrayList<ItemStack> inputs = new ArrayList<>(1);
			ItemStack it = input.copy();
			it.setCount(1);
			inputs.add(it);
			
			ArrayList<ItemStack> outputs = new ArrayList<>(this.outputs.length);
			Arrays.stream(this.outputs).forEach(i -> outputs.add(i));
			
			MachineOperation op = new MachineOperation(inputs, outputs, null, null, 1);
			return op;
		}
		
		@Override
		public List<List<ItemStack>> getItemInputs() {
			List<List<ItemStack>> list = IMachineRecipe.super.getItemInputs();
			list.add(input.getItemStacks());
			return list;
		}

		@Override
		public List<List<ItemStack>> getItemOutputs() {
			List<List<ItemStack>> list = IMachineRecipe.super.getItemOutputs();
			Arrays.stream(this.outputs).forEach(s -> {
				ArrayList<ItemStack> list2 = new ArrayList<>();
				list2.add(s);
				list.add(list2);
			});
			return list;
		}
		
	}
	
	public static class GrinderRecipeChance extends GrinderRecipe {

		protected double[] chances;
		
		public GrinderRecipeChance(ItemStackOreDict input, ItemStack[] outputs, double[] chances) {
			super(input, outputs);
			this.chances=chances;
			if(this.outputs.length!=this.chances.length) {
				throw new IllegalArgumentException("Output and Chances array must be same size!");
			}
		}

		public double[] getChances() {
			return chances;
		}

		@Override
		public MachineOperationChance getOperation(ItemStack input,  GrinderTileEnt tile) {
			ArrayList<ItemStack> inputs = new ArrayList<>(1);
			ItemStack it = input.copy();
			it.setCount(1);
			inputs.add(it);
			
			ArrayList<ItemStack> outputs = new ArrayList<>(this.outputs.length);
			Arrays.stream(this.outputs).forEach(i -> outputs.add(i));
			
			/*for(int i=0;i<this.outputs.length; i++) {
				if(chances[i]>=1f || tile.getWorld().rand.nextFloat() <=chances[i]) {
					outputs.add(this.outputs[i]);
				}
			}*/
			
			return new MachineOperationChance(inputs, outputs, null, null, 1, chances);
		}
	}
	
	public static class GrinderRecipeGenericArmor extends GrinderRecipe {
		protected GenericArmor armor;
		
		public GrinderRecipeGenericArmor(GenericArmor armor) {
			super(new ItemStackOreDict(new ItemStack(armor),1), ItemStack.EMPTY);
			this.armor = armor;
		}

		
		
		@Override
		public MachineOperation getOperation(ItemStack input, GrinderTileEnt tile) {
			ArrayList<ItemStack> inputs = new ArrayList<>(1);
			inputs.add(input);
			
			ArrayList<ItemStack> outputs = new ArrayList<>();
			
			ItemStack inverted = input.copy();
			inverted.setItemDamage(input.getMaxDamage()-input.getItemDamage());;
			this.armor.getRepairMats(inverted).forEach(i -> outputs.add(i.copy()));
			
			MachineOperation op = new MachineOperation(inputs, outputs, null, null, 1);
			return op;
		}

		@Override
		public boolean matchesInput(ItemStackOreDict input) {
			return input.item !=null && !input.isEmpty() && this.armor == input.item.getItem();
		}

		@Override
		public List<List<ItemStack>> getItemInputs() {
			List<List<ItemStack>> list = new ArrayList<List<ItemStack>>();
			List<ItemStack> list2 = NonNullList.<ItemStack>withSize(1, new ItemStack(armor));
			list.add(list2);
			return list;
		}
		
		@Override
		public List<List<ItemStack>> getItemOutputs() {
			List<List<ItemStack>> list = new ArrayList<List<ItemStack>>();
			armor.getRepairMats(new ItemStack(armor,1,armor.getMaxDamage())).forEach(i ->{
				list.add(NonNullList.<ItemStack>withSize(1, i));
			});
			return list;
		}
	}
}
