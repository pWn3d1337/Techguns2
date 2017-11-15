package techguns.plugins.jei;

import java.util.ArrayList;
import java.util.List;

import mezz.jei.api.IGuiHelper;
import mezz.jei.api.IJeiHelpers;
import mezz.jei.api.gui.ICraftingGridHelper;
import mezz.jei.api.gui.IGuiItemStackGroup;
import mezz.jei.api.gui.IRecipeLayout;
import mezz.jei.api.ingredients.IIngredients;
import mezz.jei.api.recipe.wrapper.ICustomCraftingRecipeWrapper;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.nbt.NBTTagCompound;
import techguns.items.guns.GenericGun;
import techguns.items.guns.ammo.AmmoType;
import techguns.recipes.AmmoSwitchRecipeFactory.AmmoSwitchRecipe;

public class AmmoSwitchRecipeWrapper /*extends ShapelessRecipeWrapper<AmmoSwitchRecipe>*/ implements ICustomCraftingRecipeWrapper {
	
	public List<ItemStack> inputs = new ArrayList<>();
	public ItemStack output;
	
	protected IGuiHelper guiHelper;
	protected ICraftingGridHelper craftingGridHelper;
	
	public AmmoSwitchRecipeWrapper(IJeiHelpers jeiHelpers, AmmoSwitchRecipe recipe) {
		//super(jeiHelpers, recipe);
		
		this.guiHelper = jeiHelpers.getGuiHelper();
		this.craftingGridHelper= this.guiHelper.createCraftingGridHelper(1, 0);
		
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
		
		String variant = type.getAmmoVariantKeyfor(ammo,0);
		tags.setString("ammovariant", variant);
		output.setTagCompound(tags);
	}

	@Override
	public void getIngredients(IIngredients ingredients) {
		ingredients.setInputs(ItemStack.class, this.inputs);
		ingredients.setOutput(ItemStack.class, this.output);
	}

	@Override
	public void setRecipe(IRecipeLayout recipeLayout, IIngredients ingredients) {
		IGuiItemStackGroup guiItemStacks = recipeLayout.getItemStacks();
		
		List<List<ItemStack>> inputs = ingredients.getInputs(ItemStack.class);
		List<List<ItemStack>> outputs = ingredients.getOutputs(ItemStack.class);

		craftingGridHelper.setInputs(guiItemStacks, inputs);
		recipeLayout.setShapeless();
		
		guiItemStacks.set(0, outputs.get(0));
		
	}

}
