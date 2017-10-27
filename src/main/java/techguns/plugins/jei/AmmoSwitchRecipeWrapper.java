package techguns.plugins.jei;

import java.util.ArrayList;
import java.util.List;

import mezz.jei.api.IJeiHelpers;
import mezz.jei.api.ingredients.IIngredients;
import mezz.jei.plugins.vanilla.crafting.ShapelessRecipeWrapper;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.nbt.NBTTagCompound;
import techguns.items.guns.GenericGun;
import techguns.items.guns.ammo.AmmoType;
import techguns.recipes.AmmoSwitchRecipeFactory.AmmoSwitchRecipe;

public class AmmoSwitchRecipeWrapper extends ShapelessRecipeWrapper<AmmoSwitchRecipe> {
	public List<ItemStack> inputs = new ArrayList<>();
	public ItemStack output;
	
	public AmmoSwitchRecipeWrapper(IJeiHelpers jeiHelpers, AmmoSwitchRecipe recipe) {
		super(jeiHelpers, recipe);
		
		ItemStack ammo=ItemStack.EMPTY;
		ItemStack gun = ItemStack.EMPTY;
		
		for (Ingredient i : recipe.getIngredients()) {
			ItemStack it = i.getMatchingStacks()[0]; 
			if(!it.isEmpty() && !(it.getItem() instanceof GenericGun)) {
				ammo = it;
				inputs.add(it);
			} else if (!it.isEmpty() && it.getItem() instanceof GenericGun) {
				gun = it;
				inputs.add(it);
			}
		}
		
		output = recipe.getRecipeOutput();
		GenericGun g = (GenericGun) output.getItem();
		
		if(gun.getTagCompound()==null) {
			g.onCreated(gun, null, null);
		}
		NBTTagCompound tags = gun.getTagCompound().copy();
		AmmoType type = g.getAmmoType();
		
		String variant = type.getAmmoVariantKeyfor(ammo);
		tags.setString("ammovariant", variant);
		output.setTagCompound(tags);
	}

	@Override
	public void getIngredients(IIngredients ingredients) {
		ingredients.setInputs(ItemStack.class, this.inputs);
		ingredients.setOutput(ItemStack.class, this.output);
	}

}
