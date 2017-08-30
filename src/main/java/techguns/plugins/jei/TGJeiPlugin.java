package techguns.plugins.jei;

import mezz.jei.api.IGuiHelper;
import mezz.jei.api.IJeiHelpers;
import mezz.jei.api.IModPlugin;
import mezz.jei.api.IModRegistry;
import mezz.jei.api.JEIPlugin;
import mezz.jei.api.ingredients.IIngredientRegistry;
import mezz.jei.api.recipe.IRecipeCategoryRegistration;
import net.minecraft.item.ItemStack;
import techguns.TGBlocks;
import techguns.Techguns;
import techguns.blocks.machines.EnumMachineType;
import techguns.blocks.machines.EnumMultiBlockMachineType;
import techguns.blocks.machines.EnumSimpleMachineType;
import techguns.gui.AmmoPressGui;
import techguns.gui.ChargingStationGui;
import techguns.gui.ChemLabGui;
import techguns.gui.FabricatorGui;
import techguns.gui.MetalPressGui;


@JEIPlugin
public class TGJeiPlugin implements IModPlugin {

	public static final String AMMO_PRESS = Techguns.MODID+".ammopress";
	public static final String METAL_PRESS = Techguns.MODID+".metalpress";
	public static final String CHEM_LAB = Techguns.MODID+".chemlab";
	public static final String FABRICATOR = Techguns.MODID+".fabricator";
	public static final String CAMO_BENCH = Techguns.MODID+".camobench";
	public static final String CHARGING_STATION = Techguns.MODID+".chargingstation";

	@Override
	public void registerCategories(IRecipeCategoryRegistration registry) {
		final IJeiHelpers jeiHelpers = registry.getJeiHelpers();
		final IGuiHelper guiHelper = jeiHelpers.getGuiHelper();
		
		registry.addRecipeCategories(
					new AmmoPressRecipeCategory(guiHelper),
					new MetalPressJeiRecipeCategory(guiHelper),
					new ChemLabJeiRecipeCategory(guiHelper),
					new FabricatorJeiRecipeCategory(guiHelper),
					new CamoBenchJeiRecipeCategory(guiHelper),
					new ChargingStationJeiRecipeCategory(guiHelper)
				);
	}
	@Override
	public void register(IModRegistry registry) {
		final IIngredientRegistry ingredientRegistry = registry.getIngredientRegistry();
		final IJeiHelpers jeiHelpers = registry.getJeiHelpers();
		
		registry.addRecipes(AmmoPressJeiRecipe.getRecipes(jeiHelpers), AMMO_PRESS);
		registry.addRecipes(MetalPressJeiRecipe.getRecipes(jeiHelpers), METAL_PRESS);
		registry.addRecipes(ChemLabJeiRecipe.getRecipes(jeiHelpers), CHEM_LAB);
		registry.addRecipes(FabricatorJeiRecipe.getRecipes(jeiHelpers), FABRICATOR);
		registry.addRecipes(CamoBenchJeiRecipe.getRecipes(jeiHelpers), CAMO_BENCH);
		registry.addRecipes(ChargingStationJeiRecipe.getRecipes(jeiHelpers), CHARGING_STATION);
		
		registry.addRecipeClickArea(AmmoPressGui.class, 119, 36, 19, 22, AMMO_PRESS);
		registry.addRecipeClickArea(MetalPressGui.class, 119, 36, 19, 22, METAL_PRESS);
		registry.addRecipeClickArea(ChemLabGui.class, 80, 15, 52, 30, CHEM_LAB);
		registry.addRecipeClickArea(FabricatorGui.class, 20, 52, 102, 12, FABRICATOR);
		//NO CLICKAREA for camobench
		registry.addRecipeClickArea(ChargingStationGui.class, 38, 18, 28, 12, CHARGING_STATION);
		
		registry.addRecipeCatalyst(new ItemStack(TGBlocks.BASIC_MACHINE,1,TGBlocks.BASIC_MACHINE.getMetaFromState(TGBlocks.BASIC_MACHINE.getDefaultState().withProperty(TGBlocks.BASIC_MACHINE.MACHINE_TYPE, EnumMachineType.AMMO_PRESS))), AMMO_PRESS);
		registry.addRecipeCatalyst(new ItemStack(TGBlocks.BASIC_MACHINE,1,TGBlocks.BASIC_MACHINE.getMetaFromState(TGBlocks.BASIC_MACHINE.getDefaultState().withProperty(TGBlocks.BASIC_MACHINE.MACHINE_TYPE, EnumMachineType.METAL_PRESS))), METAL_PRESS);
		registry.addRecipeCatalyst(new ItemStack(TGBlocks.BASIC_MACHINE,1,TGBlocks.BASIC_MACHINE.getMetaFromState(TGBlocks.BASIC_MACHINE.getDefaultState().withProperty(TGBlocks.BASIC_MACHINE.MACHINE_TYPE, EnumMachineType.CHEM_LAB))), CHEM_LAB);
		registry.addRecipeCatalyst(new ItemStack(TGBlocks.MULTIBLOCK_MACHINE,1,TGBlocks.MULTIBLOCK_MACHINE.getMetaFromState(TGBlocks.MULTIBLOCK_MACHINE.getDefaultState().withProperty(TGBlocks.MULTIBLOCK_MACHINE.MACHINE_TYPE, EnumMultiBlockMachineType.FABRICATOR_CONTROLLER))), FABRICATOR);
		registry.addRecipeCatalyst(new ItemStack(TGBlocks.SIMPLE_MACHINE,1,TGBlocks.SIMPLE_MACHINE.getMetaFromState(TGBlocks.SIMPLE_MACHINE.getDefaultState().withProperty(TGBlocks.SIMPLE_MACHINE.MACHINE_TYPE, EnumSimpleMachineType.CAMO_BENCH))), CAMO_BENCH);
		registry.addRecipeCatalyst(new ItemStack(TGBlocks.SIMPLE_MACHINE,1,TGBlocks.SIMPLE_MACHINE.getMetaFromState(TGBlocks.SIMPLE_MACHINE.getDefaultState().withProperty(TGBlocks.SIMPLE_MACHINE.MACHINE_TYPE, EnumSimpleMachineType.CHARGING_STATION))), CHARGING_STATION);
	}
	
	
	
}
