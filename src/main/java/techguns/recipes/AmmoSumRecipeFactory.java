package techguns.recipes;

import com.google.gson.JsonObject;

import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.JsonUtils;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.crafting.CraftingHelper.ShapedPrimer;
import net.minecraftforge.common.crafting.IRecipeFactory;
import net.minecraftforge.common.crafting.JsonContext;
import net.minecraftforge.oredict.ShapedOreRecipe;
import techguns.Techguns;
import techguns.api.tginventory.ITGSpecialSlot;
import techguns.api.tginventory.TGSlotType;
import techguns.items.guns.GenericGun;

public class AmmoSumRecipeFactory implements IRecipeFactory {

	public static final String AMMO_SUM_RECIPE="ammo_sum_recipe";
	protected static final ResourceLocation GROUP = new ResourceLocation(Techguns.MODID, AMMO_SUM_RECIPE );
	
	@Override
	public IRecipe parse(JsonContext context, JsonObject json) {
		ShapedOreRecipe rec = ShapedOreRecipe.factory(context, json);
		
		ShapedPrimer primer = new ShapedPrimer();
		primer.height=rec.getRecipeHeight();
		primer.width=rec.getRecipeWidth();
		primer.mirrored = JsonUtils.getBoolean(json, "mirrored", true);
		primer.input = rec.getIngredients();
		
		return new AmmoSumRecipe(GROUP, rec.getRecipeOutput(), primer);
	}

	public class AmmoSumRecipe extends ShapedOreRecipe {
		public AmmoSumRecipe(ResourceLocation group, ItemStack result, ShapedPrimer primer) {
			super(group, result, primer);
		}

		@Override
		public ItemStack getCraftingResult(InventoryCrafting var1) {
			int ammoSum=0;
			
			for (int i=0;i<var1.getSizeInventory();i++){
				//System.out.println("Slot "+i+" :"+var1.getStackInSlot(i));
				if (!var1.getStackInSlot(i).isEmpty()){
					if (var1.getStackInSlot(i).getItem()instanceof GenericGun){
						GenericGun g = (GenericGun) var1.getStackInSlot(i).getItem();
						ammoSum+=g.getCurrentAmmo(var1.getStackInSlot(i));
					} 
				}
			}
			ItemStack out = super.getCraftingResult(var1);
			NBTTagCompound tags = out.getTagCompound();
			tags.setShort("ammo", (short) ammoSum);
			
			return out;
		}
		
		
		
	}
}
