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
import techguns.items.guns.GenericGunMeleeCharge;
import techguns.recipes.MiningToolUpgradeHeadRecipeFactory.MiningToolUpgradeRecipe;

public class MiningToolUpgradeRecipeWrapper /*extends ShapelessRecipeWrapper<MiningToolUpgradeRecipe>*/ implements ICustomCraftingRecipeWrapper {
	public List<ItemStack> inputs = new ArrayList<>();
	public ItemStack output;
	
	protected IGuiHelper guiHelper;
	protected ICraftingGridHelper craftingGridHelper;
	
	public MiningToolUpgradeRecipeWrapper(IJeiHelpers jeiHelpers, MiningToolUpgradeRecipe rec) {
		//super(jeiHelpers, rec);
		
		this.guiHelper = jeiHelpers.getGuiHelper();
		this.craftingGridHelper= this.guiHelper.createCraftingGridHelper(1, 0);
		
		ItemStack head=ItemStack.EMPTY;
		ItemStack gun = ItemStack.EMPTY;
		
		for (Ingredient i : rec.getIngredients()) {
			ItemStack it = i.getMatchingStacks()[0]; 
			if(!it.isEmpty() && !(it.getItem() instanceof GenericGunMeleeCharge)) {
				head = it;
				inputs.add(it);
			} else if (!it.isEmpty() && it.getItem() instanceof GenericGunMeleeCharge) {
				gun = it;
				inputs.add(it);
			}
		}
		
		output = rec.getRecipeOutput();
		GenericGunMeleeCharge g = (GenericGunMeleeCharge) output.getItem();
		
		NBTTagCompound tags = gun.getTagCompound().copy();
		output.setTagCompound(tags);
		output.getTagCompound().setInteger("miningHead", g.getMiningHeadLevelForHead(head));
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

	/*public static List<MiningToolUpgradeRecipeWrapper> getMiningToolUpgradeRecipes() {
		List<MiningToolUpgradeRecipeWrapper> recipes = new ArrayList<>();
		
		MiningToolUpgradeHeadRecipeFactory.RECIPES.forEach(r -> {
			recipes.add(new MiningToolUpgradeRecipeWrapper(null,r));
		});
		
		return recipes;
	}*/

}
