package techguns.tileentities.operation;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

import net.minecraft.block.Block;
import net.minecraft.item.ItemStack;
import techguns.items.armors.ICamoChangeable;
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
		protected ICamoChangeable camoblock;
		protected boolean customMeta=false;
		
		public CamoBenchRecipe(Block blockType) {
			super();
			this.blockType = blockType;
			this.firstMeta=0;
			this.lastMeta=15;
		}
		
		public CamoBenchRecipe(Block blockType, ICamoChangeable camoblock) {
			super();
			this.blockType = blockType;
			this.firstMeta=0;
			this.lastMeta=camoblock.getCamoCount();
			this.customMeta=true;
			this.camoblock=camoblock;
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
			if(!customMeta) {
				int meta = item.getMetadata();
				int newMeta = back? getPrevMeta(meta) : getNextMeta(meta);
				item.setItemDamage(newMeta);
			} else if (this.camoblock!=null){
				this.camoblock.switchCamo(item, back);
			}
		}
		
		public ICamoChangeable getCamoblock() {
			return camoblock;
		}

		public boolean hasCustomMeta() {
			return customMeta;
		}

		@Override
		public List<List<ItemStack>> getItemInputs() {
			ArrayList<List<ItemStack>> list = new ArrayList<>();
			
			ArrayList<ItemStack> stacks = new ArrayList<>();
			
			if(!this.customMeta) {
				for(int i=firstMeta; i<= lastMeta; i++) {
					stacks.add(new ItemStack(blockType,1,i));
				}
			} else {
				ItemStack stack = new ItemStack(blockType,1,this.camoblock.getFirstItemCamoDamageValue());
				for (int i =0; i < this.camoblock.getCamoCount(); i++) {
					stacks.add(stack);
					stack=stack.copy();
					this.camoblock.switchCamo(stack);
				}
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
