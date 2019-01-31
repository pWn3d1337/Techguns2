package techguns.recipes;

import java.io.File;
import java.io.FileWriter;
import java.io.FilenameFilter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

import com.google.common.collect.ImmutableMap;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ResourceLocation;
import techguns.TGConfig;
import techguns.TGItems;
import techguns.Techguns;
import techguns.blocks.machines.BasicMachine;
import techguns.items.GenericItemShared;
import techguns.items.guns.GenericGun;
import techguns.items.guns.ammo.AmmoType;


/**
 * Based on:
 * https://gist.github.com/williewillus/a1a899ce5b0f0ba099078d46ae3dae6e
 * 
 * added some stuff
 */
public class RecipeJsonConverter {
	// Replace calls to GameRegistry.addShapeless/ShapedRecipe with these methods, which will dump it to a json in your dir of choice
	// Also works with OD, replace GameRegistry.addRecipe(new ShapedOreRecipe/ShapelessOreRecipe with the same calls

		private static final String recipeDirPath = "../../../src/main/resources/assets/techguns/recipes";
	
		private static HashMap<String,String> factories = new HashMap<>();
		static {
			factories.put(Recipewriter.hardenedGlassOrGlass, OreDictIngredientHardenedGlass.class.getName());
			factories.put(Recipewriter.electrumOrGold, OreDictIngredientElectrumOrGold.class.getName());
			factories.put(Recipewriter.itemStackHasNBTInt, IngredientFactoryMatchNBTInt.class.getName());
		}

		public static final String AMMO_CHANGE_COPY_NBT_RECIPE = "ammo_change_crafting";
		private static HashMap<String,String> recipe_types = new HashMap<>();
		static {
			recipe_types.put(AMMO_CHANGE_COPY_NBT_RECIPE, AmmoSwitchRecipeFactory.class.getName());
			recipe_types.put(MiningToolUpgradeHeadRecipeFactory.MINING_TOOL_UPGRADE_RECIPE, MiningToolUpgradeHeadRecipeFactory.class.getName());
			recipe_types.put(ShapedOreRecipeCopyNBTFactory.COPY_NBT_RECIPE, ShapedOreRecipeCopyNBTFactory.class.getName());
			recipe_types.put(AmmoSumRecipeFactory.AMMO_SUM_RECIPE, AmmoSumRecipeFactory.class.getName());
		}
	
		private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();
		private static File RECIPE_DIR = null;
		private static final Set<String> USED_OD_NAMES = new TreeSet<>();


		private static void setupDir() {
			if (RECIPE_DIR == null) {
				RECIPE_DIR = TGConfig.config.getConfigFile().toPath().resolve(recipeDirPath).toFile();
				
				File[] files = RECIPE_DIR.listFiles(new FilenameFilter() {
					
					@Override
					public boolean accept(File dir, String name) {
						return name.endsWith(".json");
					}
				});
				System.out.println("Deleting recipes, THIS SHOULD ONLY BE DONE IN DEV ENVIRONMENT!!!");
				Arrays.stream(files).forEach(f -> {
					f.delete();
				});
				
			}

			if (!RECIPE_DIR.exists()) {
				RECIPE_DIR.mkdir();
			}
		}

		public static void addShapedRecipe(ItemStack result, Object... components) {
			setupDir();

			// GameRegistry.addShapedRecipe(result, components);

			Map<String, Object> json = new HashMap<>();

			List<String> pattern = new ArrayList<>();
			int i = 0;
			while (i < components.length && components[i] instanceof String) {
				pattern.add((String) components[i]);
				i++;
			}
			json.put("pattern", pattern);

			boolean isOreDict = false;
			Map<String, Map<String, Object>> key = new HashMap<>();
			Character curKey = null;
			for (; i < components.length; i++) {
				Object o = components[i];
				if (o instanceof Character) {
					if (curKey != null)
						throw new IllegalArgumentException("Provided two char keys in a row");
					curKey = (Character) o;
				} else {
					if (curKey == null)
						throw new IllegalArgumentException("Providing object without a char key");
					if (o instanceof String)
						isOreDict = true;
					key.put(Character.toString(curKey), serializeItem(o));
					curKey = null;
				}
			}
			json.put("key", key);
			json.put("type", isOreDict ? "forge:ore_shaped" : "minecraft:crafting_shaped");
			json.put("result", serializeItem(result));

			// names the json the same name as the output's registry name
			// repeatedly adds _alt if a file already exists
			// janky I know but it works
			String suffix = result.getItem().getHasSubtypes() ? "_" + result.getItemDamage() : "";
			
			String name = result.getItem().getRegistryName().getResourcePath();
			if(result.getItem()  instanceof GenericItemShared) {
				suffix = suffix +"_"+ TGItems.SHARED_ITEM.get(result.getItemDamage()).getName();
			}
			if(result.getItem() instanceof ItemBlock) {
				ItemBlock ib = (ItemBlock) result.getItem();
				
				Block b = ib.getBlock();
				
				if (b instanceof BasicMachine) {
					BasicMachine bm = (BasicMachine) b;
					suffix += "_"+ bm.getNameSuffix(result.getMetadata());
				}
			}
			
			File f = new File(RECIPE_DIR, name + suffix + ".json");

			while (f.exists()) {
				suffix += "_alt";
				f = new File(RECIPE_DIR, name + suffix + ".json");
			}

			try (FileWriter w = new FileWriter(f)) {
				GSON.toJson(json, w);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		public static void addShapelessRecipe(ItemStack result, Object... components)
		{
			setupDir();

			// addShapelessRecipe(result, components);

			Map<String, Object> json = new HashMap<>();

			boolean isOreDict = false;
			List<Map<String, Object>> ingredients = new ArrayList<>();
			for (Object o : components) {
				if (o instanceof String)
					isOreDict = true;
				ingredients.add(serializeItem(o));
			}
			json.put("ingredients", ingredients);
			json.put("type", isOreDict ? "forge:ore_shapeless" : "minecraft:crafting_shapeless");
			json.put("result", serializeItem(result));

			// names the json the same name as the output's registry name
			// repeatedly adds _alt if a file already exists
			// janky I know but it works
			String suffix = result.getItem().getHasSubtypes() ? "_" + result.getItemDamage() : "";
			
			String name = result.getItem().getRegistryName().getResourcePath();
			if(result.getItem()  instanceof GenericItemShared) {
				suffix = suffix +"_"+ TGItems.SHARED_ITEM.get(result.getItemDamage()).getName();
			} 

			if(result.getItem() instanceof ItemBlock) {
				ItemBlock ib = (ItemBlock) result.getItem();
				
				Block b = ib.getBlock();
				
				if (b instanceof BasicMachine) {
					BasicMachine bm = (BasicMachine) b;
					suffix += "_"+ bm.getNameSuffix(result.getMetadata());
				}
			}
			
			
			File f = new File(RECIPE_DIR, name + suffix + ".json");

			while (f.exists()) {
				suffix += "_alt";
				f = new File(RECIPE_DIR, name + suffix + ".json");
			}

			try (FileWriter w = new FileWriter(f)) {
				GSON.toJson(json, w);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		public static void addTGManualRecipe( Object... components) {
			setupDir();

			// addShapelessRecipe(result, components);

			Map<String, Object> json = new HashMap<>();

			boolean isOreDict = false;
			List<Map<String, Object>> ingredients = new ArrayList<>();
			for (Object o : components) {
				if (o instanceof String)
					isOreDict = true;
				ingredients.add(serializeItem(o));
			}
			json.put("ingredients", ingredients);
			json.put("type", isOreDict ? "forge:ore_shapeless" : "minecraft:crafting_shapeless");
			NBTTagCompound tags = new NBTTagCompound();
			tags.setString("patchouli:book", Techguns.MODID+":techguns_manual");
			json.put("result", serializeItemFromResourceLocation(new ResourceLocation("patchouli", "guide_book"), tags));

			// names the json the same name as the output's registry name
			// repeatedly adds _alt if a file already exists
			// janky I know but it works
			String suffix = "";
			
			String name = "techguns_manual";//result.getItem().getRegistryName().getResourcePath();
			
			File f = new File(RECIPE_DIR, name + suffix + ".json");

			while (f.exists()) {
				suffix += "_alt";
				f = new File(RECIPE_DIR, name + suffix + ".json");
			}

			try (FileWriter w = new FileWriter(f)) {
				GSON.toJson(json, w);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
		public static void addShapelessAmmoSwapRecipe(GenericGun gun, AmmoType type, String key)
		{
			setupDir();

			// addShapelessRecipe(result, components);

			Map<String, Object> json = new HashMap<>();

			boolean isOreDict = false;
			List<Map<String, Object>> ingredients = new ArrayList<>();
			/*for (Object o : components) {
				if (o instanceof String)
					isOreDict = true;
				ingredients.add(serializeItem(o));
			}*/
			ingredients.add(serializeItem(new ItemStack(gun,1)));
			ingredients.add(serializeItem(type.getAmmo(type.getIDforVariantKey(key))[0]));
			ItemStack result = new ItemStack(gun,1);
			
			json.put("ingredients", ingredients);
			json.put("type", "techguns:"+AMMO_CHANGE_COPY_NBT_RECIPE);
			json.put("result", serializeItem(result));

			// names the json the same name as the output's registry name
			// repeatedly adds _alt if a file already exists
			// janky I know but it works
			String suffix = "_ammo_"+key;
			
			String name = result.getItem().getRegistryName().getResourcePath();

			
			File f = new File(RECIPE_DIR, name + suffix + ".json");

			while (f.exists()) {
				suffix += "_alt";
				f = new File(RECIPE_DIR, name + suffix + ".json");
			}

			try (FileWriter w = new FileWriter(f)) {
				GSON.toJson(json, w);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
		public static void addShapelessMiningHeadUpgradeRecipe(GenericGun gun, ItemStack upgradeItem, String key, int value)
		{
			setupDir();

			// addShapelessRecipe(result, components);

			Map<String, Object> json = new HashMap<>();

			boolean isOreDict = false;
			List<Map<String, Object>> ingredients = new ArrayList<>();
			/*for (Object o : components) {
				if (o instanceof String)
					isOreDict = true;
				ingredients.add(serializeItem(o));
			}*/
			ingredients.add(serializeItemNBTTag(new ItemStack(gun,1),key, value));
			ingredients.add(serializeItem(upgradeItem));
			ItemStack result = new ItemStack(gun,1);
			
			json.put("ingredients", ingredients);
			json.put("type", "techguns:"+MiningToolUpgradeHeadRecipeFactory.MINING_TOOL_UPGRADE_RECIPE);
			json.put("result", serializeItem(result));

			// names the json the same name as the output's registry name
			// repeatedly adds _alt if a file already exists
			// janky I know but it works
			String suffix = "_upgrade_"+upgradeItem.getItem().getUnlocalizedName();
			
			String name = result.getItem().getRegistryName().getResourcePath();

			
			File f = new File(RECIPE_DIR, name + suffix + ".json");

			while (f.exists()) {
				suffix += "_alt";
				f = new File(RECIPE_DIR, name + suffix + ".json");
			}

			try (FileWriter w = new FileWriter(f)) {
				GSON.toJson(json, w);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
		public static void addShapedCopyNBTRecipe(ItemStack result, Object... components) {
			addShapedRecipeSpecialType(result, new ResourceLocation(Techguns.MODID, ShapedOreRecipeCopyNBTFactory.COPY_NBT_RECIPE), components);
		}
		
		public static void addShapedAmmoSumRecipe(ItemStack result, Object... components) {
			addShapedRecipeSpecialType(result, new ResourceLocation(Techguns.MODID, AmmoSumRecipeFactory.AMMO_SUM_RECIPE), components);
		}
		
		public static void addShapedRecipeSpecialType(ItemStack result, ResourceLocation type, Object... components) {
			setupDir();

			// GameRegistry.addShapedRecipe(result, components);

			Map<String, Object> json = new HashMap<>();

			List<String> pattern = new ArrayList<>();
			int i = 0;
			while (i < components.length && components[i] instanceof String) {
				pattern.add((String) components[i]);
				i++;
			}
			json.put("pattern", pattern);

			boolean isOreDict = true;
			Map<String, Map<String, Object>> key = new HashMap<>();
			Character curKey = null;
			for (; i < components.length; i++) {
				Object o = components[i];
				if (o instanceof Character) {
					if (curKey != null)
						throw new IllegalArgumentException("Provided two char keys in a row");
					curKey = (Character) o;
				} else {
					if (curKey == null)
						throw new IllegalArgumentException("Providing object without a char key");
					if (o instanceof String)
						isOreDict = true;
					key.put(Character.toString(curKey), serializeItem(o));
					curKey = null;
				}
			}
			json.put("key", key);
			//json.put("type", isOreDict ? "forge:ore_shaped" : "minecraft:crafting_shaped");
			json.put("type", type.toString());
			json.put("result", serializeItem(result));

			// names the json the same name as the output's registry name
			// repeatedly adds _alt if a file already exists
			// janky I know but it works
			String suffix = result.getItem().getHasSubtypes() ? "_" + result.getItemDamage() : "";
			
			String name = result.getItem().getRegistryName().getResourcePath();
			if(result.getItem()  instanceof GenericItemShared) {
				suffix = suffix +"_"+ TGItems.SHARED_ITEM.get(result.getItemDamage()).getName();
			}
			if(result.getItem() instanceof ItemBlock) {
				ItemBlock ib = (ItemBlock) result.getItem();
				
				Block b = ib.getBlock();
				
				if (b instanceof BasicMachine) {
					BasicMachine bm = (BasicMachine) b;
					suffix += "_"+ bm.getNameSuffix(result.getMetadata());
				}
			}
			
			File f = new File(RECIPE_DIR, name + suffix + ".json");

			while (f.exists()) {
				suffix += "_alt";
				f = new File(RECIPE_DIR, name + suffix + ".json");
			}

			try (FileWriter w = new FileWriter(f)) {
				GSON.toJson(json, w);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
		private static Map<String, Object> serializeItem(Object thing) {
			if (thing instanceof Item) {
				return serializeItem(new ItemStack((Item) thing));
			}
			if (thing instanceof Block) {
				return serializeItem(new ItemStack((Block) thing));
			}
			if (thing instanceof ItemStack) {
				ItemStack stack = (ItemStack) thing;
				Map<String, Object> ret = new HashMap<>();
				ret.put("item", stack.getItem().getRegistryName().toString());
				if (stack.getItem().getHasSubtypes() || stack.getItemDamage() != 0) {
					ret.put("data", stack.getItemDamage());
				}
				if (stack.getCount() > 1) {
					ret.put("count", stack.getCount());
				}
				
				if (stack.hasTagCompound()) {
					throw new IllegalArgumentException("nbt not implemented");
				}

				return ret;
			}
			if (thing instanceof String) {
				Map<String, Object> ret = new HashMap<>();
				USED_OD_NAMES.add((String) thing);
				ret.put("item", "#" + ((String) thing).toUpperCase(Locale.ROOT));
				return ret;
			}

			throw new IllegalArgumentException("Not a block, item, stack, or od name");
		}
		
		private static Map<String, Object> serializeItemFromResourceLocation(ResourceLocation item, NBTTagCompound tags) {

			//ItemStack stack = (ItemStack) thing;
			
			Map<String, Object> ret = new HashMap<>();
			ret.put("item", item.toString());
			
			
			Map<String,Object> tags_m = new HashMap<>();
			for(String s: tags.getKeySet()) {
				String str = tags.getString(s);
				if(str!=null && !str.isEmpty())
				tags_m.put(s, str);
			}
			
			ret.put("nbt", tags_m);
			
			/*if (stack.getItem().getHasSubtypes() || stack.getItemDamage() != 0) {
				ret.put("data", stack.getItemDamage());
			}*/
			/*if (stack.getCount() > 1) {
				ret.put("count", stack.getCount());
			}*/
			
			/*if (stack.hasTagCompound()) {
				throw new IllegalArgumentException("nbt not implemented");
			}*/

			return ret;
			
		}
		
		private static Map<String, Object> serializeItemNBTTag(ItemStack stack, String key, Object value) {
	
			Map<String, Object> ret = new HashMap<>();
			
			ret.put("type", Recipewriter.itemStackHasNBTInt);
			ret.put("item", stack.getItem().getRegistryName().toString());
			if (stack.getItem().getHasSubtypes() || stack.getItemDamage() != 0) {
				ret.put("data", stack.getItemDamage());
			}
			if (stack.getCount() > 1) {
				ret.put("count", stack.getCount());
			}
			
			ret.put("key", key);
			ret.put("value", value);

			return ret;
		}

		public static void generateConstants() {
			List<Map<String, Object>> json = new ArrayList<>();
			for (String s : USED_OD_NAMES) {
				Map<String, Object> entry = new HashMap<>();
				
				entry.put("name", s.toUpperCase(Locale.ROOT));
				if(factories.containsKey(s)) {
					entry.put("ingredient", ImmutableMap.of("type", s));
				} else {
					entry.put("ingredient", ImmutableMap.of("type", "forge:ore_dict", "ore", s));
				}
				json.add(entry);
			}

			try (FileWriter w = new FileWriter(new File(RECIPE_DIR, "_constants.json"))) {
				GSON.toJson(json, w);
			} catch (IOException e) {
				e.printStackTrace();
			}
	}
		
		public static void generateFactories() {
			
			Map<String, Object> entry = new HashMap<>();
			entry.put("ingredients", factories);
			entry.put("recipes", recipe_types);
			
			
			try (FileWriter w = new FileWriter(new File(RECIPE_DIR, "_factories.json"))) {
				GSON.toJson(entry, w);
			} catch (IOException e) {
				e.printStackTrace();
			}
	}
		
}
