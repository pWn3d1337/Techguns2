package techguns.plugins.jei;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import mezz.jei.api.IJeiHelpers;
import mezz.jei.api.gui.IRecipeLayout;
import mezz.jei.api.ingredients.IIngredients;
import mezz.jei.api.recipe.IRecipeWrapper;
import mezz.jei.api.recipe.wrapper.ICustomCraftingRecipeWrapper;
import mezz.jei.api.recipe.wrapper.IShapedCraftingRecipeWrapper;
import mezz.jei.plugins.vanilla.crafting.ShapelessRecipeWrapper;
import mezz.jei.plugins.vanilla.crafting.TippedArrowRecipeWrapper;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.potion.PotionType;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.oredict.ShapedOreRecipe;
import techguns.items.guns.GenericGun;
import techguns.items.guns.GenericGunMeleeCharge;
import techguns.recipes.MiningToolUpgradeHeadRecipeFactory;
import techguns.recipes.MiningToolUpgradeHeadRecipeFactory.MiningToolUpgradeRecipe;

public class MiningToolUpgradeRecipeWrapper extends ShapelessRecipeWrapper<MiningToolUpgradeRecipe> {
	public List<ItemStack> inputs = new ArrayList<>();
	public ItemStack output;
	
	public MiningToolUpgradeRecipeWrapper(IJeiHelpers jeiHelpers, MiningToolUpgradeRecipe rec) {
		super(jeiHelpers, rec);
		
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

	/*public static List<MiningToolUpgradeRecipeWrapper> getMiningToolUpgradeRecipes() {
		List<MiningToolUpgradeRecipeWrapper> recipes = new ArrayList<>();
		
		MiningToolUpgradeHeadRecipeFactory.RECIPES.forEach(r -> {
			recipes.add(new MiningToolUpgradeRecipeWrapper(null,r));
		});
		
		return recipes;
	}*/

}
