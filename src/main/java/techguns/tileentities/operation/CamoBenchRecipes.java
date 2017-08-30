package techguns.tileentities.operation;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

import net.minecraft.block.Block;
import net.minecraft.item.ItemStack;
import techguns.plugins.jei.CamoBenchJeiRecipe;

public class CamoBenchRecipes {

	protected static HashMap<Block, CamoBenchRecipe> recipes = new HashMap<>();
	protected static ArrayList<CamoBenchRecipe> recipe_list = new ArrayList<>();
	
	public static void addRecipe(CamoBenchRecipe r) {
		recipes.put(r.blockType, r);
		recipe_list.add(r);
	}
	
	public static CamoBenchRecipe getRecipeFor(Block b) {
		return recipes.get(b);
	}
	
	public static class CamoBenchRecipe implements IMachineRecipe {
		
		protected Block blockType;
		protected int firstMeta;
		protected int lastMeta;
		
		public CamoBenchRecipe(Block blockType) {
			super();
			this.blockType = blockType;
			this.firstMeta=0;
			this.lastMeta=15;
		}
		
		protected int getNextMeta(int m) {
			if(++m>lastMeta) {
				m=firstMeta;
			}
			return m;
		}
		
		protected int getPrevMeta(int m) {
			if(--m<firstMeta) {
				m= lastMeta;
			}
			return m;
		}
		
		public int getCamoCount() {
			return lastMeta-firstMeta+1;
		}
		
		public void switchCamo(ItemStack item, boolean back) {
			int meta = item.getMetadata();
			int newMeta = back? getPrevMeta(meta) : getNextMeta(meta);
			item.setItemDamage(newMeta);
		}

		@Override
		public List<List<ItemStack>> getItemInputs() {
			ArrayList<List<ItemStack>> list = new ArrayList<>();
			
			ArrayList<ItemStack> stacks = new ArrayList<>();
			
			for(int i=firstMeta; i<= lastMeta; i++) {
				stacks.add(new ItemStack(blockType,1,i));
			}
			
			list.add(stacks);
			return list;
		}

		@Override
		public List<List<ItemStack>> getItemOutputs() {
			return this.getItemInputs();
		}
		
		
	}

	public static ArrayList<CamoBenchRecipe> getRecipes() {
		/*Set<Block> keys = recipes.keySet();
		ArrayList<CamoBenchRecipe> rec = new ArrayList<>();
		keys.forEach(k -> rec.add(recipes.get(k)));
		return rec;*/
		return recipe_list;
	}
	
}
