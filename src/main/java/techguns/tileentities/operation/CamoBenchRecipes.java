package techguns.tileentities.operation;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import net.minecraft.block.Block;
import net.minecraft.item.ItemStack;
import techguns.items.armors.ICamoChangeable;

public class CamoBenchRecipes {

	protected static HashMap<Block, CamoBenchRecipe> recipes = new HashMap<>();
	//protected static ArrayList<CamoBenchRecipe> recipe_list = new ArrayList<>();
	
	public static void addRecipe(CamoBenchRecipe r) {
		recipes.put(r.blockType, r);
		//recipe_list.add(r);
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
				for (int i =0; i <= this.camoblock.getCamoCount(); i++) {
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
	
	public static class TGLampCamoBenchRecipe extends CamoBenchRecipe {
		protected int num;
		public TGLampCamoBenchRecipe(Block blockType, int num) {
			super(blockType);
			this.num=num;
			this.customMeta=true;
			this.camoblock = (ICamoChangeable) blockType;
		}

		@Override
		protected int getNextMeta(int m) {
			if (m==0) {
				return 6;
			} else if (m==6) {
				return 0;
			} else if (m==12) {
				return 13;
			} else if (m==13) {
				return 12;
			}
			return 0;
		}

		@Override
		protected int getPrevMeta(int m) {
			return getNextMeta(m);
		}

		@Override
		public int getCamoCount() {
			return 2;
		}

		public List<List<ItemStack>> getItemInputs(int num) {
			ArrayList<List<ItemStack>> list = new ArrayList<>();
			
			ArrayList<ItemStack> stacks = new ArrayList<>();
			
			if(num==0) {
				stacks.add(new ItemStack(blockType,1,0));
				stacks.add(new ItemStack(blockType,1,6));
			} else {
				stacks.add(new ItemStack(blockType,1,12));
				stacks.add(new ItemStack(blockType,1,13));
			}
			
			list.add(stacks);
			return list;
		}

		@Override
		public List<List<ItemStack>> getItemInputs() {
			return getItemInputs(num);
		}
	
	}

	public static ArrayList<CamoBenchRecipe> getRecipes() {
		/*Set<Block> keys = recipes.keySet();
		ArrayList<CamoBenchRecipe> rec = new ArrayList<>();
		keys.forEach(k -> rec.add(recipes.get(k)));
		return rec;*/
		
		Iterator<Block> it = recipes.keySet().iterator();
		ArrayList<CamoBenchRecipe> recipe_list = new ArrayList<>();
		
		while(it.hasNext()) {
			CamoBenchRecipe rec = recipes.get(it.next());
			recipe_list.add(rec);
			if(rec instanceof TGLampCamoBenchRecipe) {
				recipe_list.add(new TGLampCamoBenchRecipe(rec.blockType, 1));
			} 
		}
		
		return recipe_list;
	}
	
}
