package techguns.recipes;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.Ingredient;
import net.minecraftforge.common.crafting.CraftingHelper;
import net.minecraftforge.common.crafting.IIngredientFactory;
import net.minecraftforge.common.crafting.JsonContext;

public class IngredientFactoryMatchNBTInt implements IIngredientFactory {

	@Override
	public Ingredient parse(JsonContext context, JsonObject json) {
		JsonElement ekey = json.get("key");
		String key = ekey.getAsString();
		
		JsonElement eValue = json.get("value");
		int value = eValue.getAsInt();
		
		ItemStack stack = CraftingHelper.getItemStackBasic(json, context);
		return new IngredientHasNBTTag(key, value, stack);
	}

}
