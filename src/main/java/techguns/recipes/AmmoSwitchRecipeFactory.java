package techguns.recipes;

import com.google.gson.JsonObject;

import net.minecraft.block.Block;
import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.JsonUtils;
import net.minecraft.util.NonNullList;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.crafting.CraftingHelper.ShapedPrimer;
import net.minecraftforge.common.crafting.IRecipeFactory;
import net.minecraftforge.common.crafting.JsonContext;
import net.minecraftforge.oredict.ShapedOreRecipe;
import net.minecraftforge.oredict.ShapelessOreRecipe;
import techguns.Techguns;
import techguns.api.tginventory.ITGSpecialSlot;
import techguns.api.tginventory.TGSlotType;
import techguns.items.GenericItemShared;
import techguns.items.guns.GenericGun;
import techguns.items.guns.ammo.AmmoType;

public class AmmoSwitchRecipeFactory implements IRecipeFactory {

	@Override
	public IRecipe parse(JsonContext context, JsonObject json) {
		ShapelessOreRecipe recipe = ShapelessOreRecipe.factory(context, json);

        return new AmmoSwitchRecipe(new ResourceLocation(Techguns.MODID, "ammo_change_crafting"), recipe.getIngredients(), recipe.getRecipeOutput());
	}

	public static class AmmoSwitchRecipe extends ShapelessOreRecipe {

		public AmmoSwitchRecipe(ResourceLocation group, NonNullList<Ingredient> input, ItemStack result) {
			super(group, input, result);
		}

		@Override
		public ItemStack getCraftingResult(InventoryCrafting var1) {
			int slot=0;
			
			int slot_ammo=0;
			
			for (int i=0;i<var1.getSizeInventory();i++){
				//System.out.println("Slot "+i+" :"+var1.getStackInSlot(i));
				if (var1.getStackInSlot(i)!=null){
					if (var1.getStackInSlot(i).getItem()instanceof GenericGun){
						//System.out.println("Copy from Slot:"+i);
						slot=i;
					} else if (var1.getStackInSlot(i).getItem() instanceof ITGSpecialSlot && ( ((ITGSpecialSlot)var1.getStackInSlot(i).getItem()).getSlot(var1.getStackInSlot(i)) == TGSlotType.AMMOSLOT)) {
						slot_ammo=i;
					}
				}
			}
			ItemStack gun = var1.getStackInSlot(slot);
			ItemStack ammo = var1.getStackInSlot(slot_ammo);
			
			NBTTagCompound tags = ((ItemStack)var1.getStackInSlot(slot)).getTagCompound();
			NBTTagCompound newTags=null;
			if(tags!=null){
				newTags = (NBTTagCompound) tags.copy();
				
				GenericGun g = (GenericGun) gun.getItem();
				AmmoType type = g.getAmmoType();
				
				String variant = type.getAmmoVariantKeyfor(ammo);
				newTags.setString("ammovariant", variant);
				
				if(g.getAmmoCount()>1) {
					newTags.setShort("ammo", (short) 1);
				} else {
					newTags.setShort("ammo", (short)g.getClipsize());
				}
			}
			ItemStack out = super.getCraftingResult(var1);
			if(newTags!=null){
				out.setTagCompound(newTags);
			}
			
			return out;
		}
		
		
		
		
	}
}
