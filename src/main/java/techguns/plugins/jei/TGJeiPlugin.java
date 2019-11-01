package techguns.plugins.jei;

import mezz.jei.api.IGuiHelper;
import mezz.jei.api.IJeiHelpers;
import mezz.jei.api.IModPlugin;
import mezz.jei.api.IModRegistry;
import mezz.jei.api.JEIPlugin;
import mezz.jei.api.ingredients.IIngredientRegistry;
import mezz.jei.api.recipe.IRecipeCategoryRegistration;
import mezz.jei.api.recipe.VanillaRecipeCategoryUid;
import net.minecraft.item.ItemStack;
import techguns.TGBlocks;
import techguns.Techguns;
import techguns.blocks.machines.EnumMachineType;
import techguns.blocks.machines.EnumMultiBlockMachineType;
import techguns.blocks.machines.EnumOreDrillType;
import techguns.blocks.machines.EnumSimpleMachineType;
import techguns.blocks.machines.EnumSimpleMachineType2;
import techguns.gui.AmmoPressGui;
import techguns.gui.UpgradeBenchGui;
import techguns.gui.BlastFurnaceGui;
import techguns.gui.ChargingStationGui;
import techguns.gui.ChemLabGui;
import techguns.gui.FabricatorGui;
import techguns.gui.GrinderGui;
import techguns.gui.MetalPressGui;
import techguns.gui.OreDrillGui;
import techguns.gui.ReactionChamberGui;
import techguns.recipes.AmmoSwitchRecipeFactory.AmmoSwitchRecipe;
import techguns.recipes.MiningToolUpgradeHeadRecipeFactory.MiningToolUpgradeRecipe;
import techguns.tileentities.BlastFurnaceTileEnt;
import techguns.tileentities.operation.BlastFurnaceRecipes;
import techguns.tileentities.operation.BlastFurnaceRecipes.BlastFurnaceRecipe;


@JEIPlugin
public class TGJeiPlugin implements IModPlugin {

	public static final String AMMO_PRESS = Techguns.MODID+".ammopress";
	public static final String METAL_PRESS = Techguns.MODID+".metalpress";
	public static final String CHEM_LAB = Techguns.MODID+".chemlab";
	public static final String FABRICATOR = Techguns.MODID+".fabricator";
	public static final String CAMO_BENCH = Techguns.MODID+".camobench";
	public static final String CHARGING_STATION = Techguns.MODID+".chargingstation";
	public static final String REACTION_CHAMBER = Techguns.MODID+".reactionchamber";
	public static final String ORE_DRILL = Techguns.MODID+".oredrill";
	public static final String BLAST_FURNACE = Techguns.MODID+".blastfurnace";
	public static final String GRINDER = Techguns.MODID+".grinder";
	public static final String UPGRADE_BENCH = Techguns.MODID+".upgradebench";
	
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
					new ChargingStationJeiRecipeCategory(guiHelper),
					new ReactionChamberJeiRecipeCategory(guiHelper),
					new OreDrillJeiRecipeCategory(guiHelper),
					new BlastFurnaceJeiRecipeCategory(guiHelper),
					new GrinderRecipeCategory(guiHelper),
					new UpgradeBenchJeiRecipeCategory(guiHelper)
				);
	}
	@Override
	public void register(IModRegistry registry) {
		final IIngredientRegistry ingredientRegistry = registry.getIngredientRegistry();
		final IJeiHelpers jeiHelpers = registry.getJeiHelpers();
			
		registry.handleRecipes(MiningToolUpgradeRecipe.class, recipe -> new MiningToolUpgradeRecipeWrapper(jeiHelpers, recipe), VanillaRecipeCategoryUid.CRAFTING);
		registry.handleRecipes(AmmoSwitchRecipe.class, recipe -> new AmmoSwitchRecipeWrapper(jeiHelpers, recipe), VanillaRecipeCategoryUid.CRAFTING);
		
		registry.addRecipes(AmmoPressJeiRecipe.getRecipes(jeiHelpers), AMMO_PRESS);
		registry.addRecipes(MetalPressJeiRecipe.getRecipes(jeiHelpers), METAL_PRESS);
		registry.addRecipes(ChemLabJeiRecipe.getRecipes(jeiHelpers), CHEM_LAB);
		registry.addRecipes(FabricatorJeiRecipe.getRecipes(jeiHelpers), FABRICATOR);
		registry.addRecipes(CamoBenchJeiRecipe.getRecipes(jeiHelpers), CAMO_BENCH);
		registry.addRecipes(ChargingStationJeiRecipe.getRecipes(jeiHelpers), CHARGING_STATION);
		registry.addRecipes(ReactionChamberJeiRecipe.getRecipes(jeiHelpers), REACTION_CHAMBER);
		registry.addRecipes(OreDrillJeiRecipe.getRecipes(jeiHelpers, ingredientRegistry), ORE_DRILL);
		registry.addRecipes(BlastFurnaceJeiRecipe.getRecipes(jeiHelpers), BLAST_FURNACE);
		registry.addRecipes(GrinderJeiRecipe.getRecipes(jeiHelpers), GRINDER);
		registry.addRecipes(UpgradeBenchJeiRecipe.getRecipes(jeiHelpers), UPGRADE_BENCH);
		
		registry.addRecipeClickArea(AmmoPressGui.class, 119, 36, 19, 22, AMMO_PRESS);
		registry.addRecipeClickArea(MetalPressGui.class, 119, 36, 19, 22, METAL_PRESS);
		registry.addRecipeClickArea(ChemLabGui.class, 80, 15, 52, 30, CHEM_LAB);
		registry.addRecipeClickArea(FabricatorGui.class, 20, 52, 96, 12, FABRICATOR);
		//registry.addRecipeClickArea(OreDrillGui.class, 30, 45, 20, 20, ORE_DRILL);
		//NO CLICKAREA for camobench
		registry.addRecipeClickArea(ChargingStationGui.class, 38, 18, 28, 12, CHARGING_STATION);
		registry.addRecipeClickArea(ReactionChamberGui.class, 66, 60, 102, 14, REACTION_CHAMBER);
		registry.addRecipeClickArea(BlastFurnaceGui.class, 18, 52, 90, 12, BLAST_FURNACE);
		registry.addRecipeClickArea(GrinderGui.class, 30, 38, 43, 23, GRINDER);
		registry.addRecipeClickArea(UpgradeBenchGui.class, 73, 40, 38, 30, UPGRADE_BENCH);
		
		registry.addRecipeCatalyst(new ItemStack(TGBlocks.BASIC_MACHINE,1,TGBlocks.BASIC_MACHINE.getMetaFromState(TGBlocks.BASIC_MACHINE.getDefaultState().withProperty(TGBlocks.BASIC_MACHINE.MACHINE_TYPE, EnumMachineType.AMMO_PRESS))), AMMO_PRESS);
		registry.addRecipeCatalyst(new ItemStack(TGBlocks.BASIC_MACHINE,1,TGBlocks.BASIC_MACHINE.getMetaFromState(TGBlocks.BASIC_MACHINE.getDefaultState().withProperty(TGBlocks.BASIC_MACHINE.MACHINE_TYPE, EnumMachineType.METAL_PRESS))), METAL_PRESS);
		registry.addRecipeCatalyst(new ItemStack(TGBlocks.BASIC_MACHINE,1,TGBlocks.BASIC_MACHINE.getMetaFromState(TGBlocks.BASIC_MACHINE.getDefaultState().withProperty(TGBlocks.BASIC_MACHINE.MACHINE_TYPE, EnumMachineType.CHEM_LAB))), CHEM_LAB);
		registry.addRecipeCatalyst(new ItemStack(TGBlocks.MULTIBLOCK_MACHINE,1,TGBlocks.MULTIBLOCK_MACHINE.getMetaFromState(TGBlocks.MULTIBLOCK_MACHINE.getDefaultState().withProperty(TGBlocks.MULTIBLOCK_MACHINE.MACHINE_TYPE, EnumMultiBlockMachineType.FABRICATOR_CONTROLLER))), FABRICATOR);
		registry.addRecipeCatalyst(new ItemStack(TGBlocks.SIMPLE_MACHINE,1,TGBlocks.SIMPLE_MACHINE.getMetaFromState(TGBlocks.SIMPLE_MACHINE.getDefaultState().withProperty(TGBlocks.SIMPLE_MACHINE.MACHINE_TYPE, EnumSimpleMachineType.CAMO_BENCH))), CAMO_BENCH);
		registry.addRecipeCatalyst(new ItemStack(TGBlocks.SIMPLE_MACHINE,1,TGBlocks.SIMPLE_MACHINE.getMetaFromState(TGBlocks.SIMPLE_MACHINE.getDefaultState().withProperty(TGBlocks.SIMPLE_MACHINE.MACHINE_TYPE, EnumSimpleMachineType.CHARGING_STATION))), CHARGING_STATION);
		registry.addRecipeCatalyst(new ItemStack(TGBlocks.MULTIBLOCK_MACHINE,1,TGBlocks.MULTIBLOCK_MACHINE.getMetaFromState(TGBlocks.MULTIBLOCK_MACHINE.getDefaultState().withProperty(TGBlocks.MULTIBLOCK_MACHINE.MACHINE_TYPE, EnumMultiBlockMachineType.REACTIONCHAMBER_CONTROLLER))), REACTION_CHAMBER);
		registry.addRecipeCatalyst(new ItemStack(TGBlocks.ORE_DRILL_BLOCK,1,TGBlocks.ORE_DRILL_BLOCK.getMetaFromState(TGBlocks.ORE_DRILL_BLOCK.getDefaultState().withProperty(TGBlocks.ORE_DRILL_BLOCK.MACHINE_TYPE, EnumOreDrillType.CONTROLLER))), ORE_DRILL);
		registry.addRecipeCatalyst(new ItemStack(TGBlocks.SIMPLE_MACHINE,1,TGBlocks.SIMPLE_MACHINE.getMetaFromState(TGBlocks.SIMPLE_MACHINE.getDefaultState().withProperty(TGBlocks.SIMPLE_MACHINE.MACHINE_TYPE, EnumSimpleMachineType.BLAST_FURNACE))), BLAST_FURNACE);
		registry.addRecipeCatalyst(new ItemStack(TGBlocks.SIMPLE_MACHINE2,1,TGBlocks.SIMPLE_MACHINE2.getMetaFromState(TGBlocks.SIMPLE_MACHINE2.getDefaultState().withProperty(TGBlocks.SIMPLE_MACHINE2.MACHINE_TYPE, EnumSimpleMachineType2.GRINDER))), GRINDER);
		registry.addRecipeCatalyst(new ItemStack(TGBlocks.SIMPLE_MACHINE2,1,TGBlocks.SIMPLE_MACHINE2.getMetaFromState(TGBlocks.SIMPLE_MACHINE2.getDefaultState().withProperty(TGBlocks.SIMPLE_MACHINE2.MACHINE_TYPE, EnumSimpleMachineType2.ARMOR_BENCH))), UPGRADE_BENCH);
	}
	
	
	
}
