package techguns.plugins.jei;

import static techguns.gui.containers.ChemLabContainer.SLOTS_ROW1_Y;
import static techguns.gui.containers.ChemLabContainer.SLOTS_ROW2_Y;
import static techguns.gui.containers.ChemLabContainer.SLOT_INPUT1_X;
import static techguns.gui.containers.ChemLabContainer.SLOT_INPUT2_X;
import static techguns.gui.containers.ChemLabContainer.SLOT_OUTPUT_X;

import mezz.jei.api.IGuiHelper;
import mezz.jei.api.gui.IDrawableAnimated;
import mezz.jei.api.gui.IDrawableStatic;
import mezz.jei.api.gui.IGuiFluidStackGroup;
import mezz.jei.api.gui.IGuiItemStackGroup;
import mezz.jei.api.gui.IRecipeLayout;
import mezz.jei.api.ingredients.IIngredients;
import net.minecraft.client.Minecraft;
import techguns.gui.ChemLabGui;
import techguns.tileentities.ChemLabTileEnt;

public class ChemLabJeiRecipeCategory extends BasicRecipeCategory<ChemLabJeiRecipe> {

	
	protected IDrawableStatic tank_overlay;
	protected IDrawableAnimated progress;
	
	public ChemLabJeiRecipeCategory(IGuiHelper guiHelper) {
		super(guiHelper, ChemLabGui.texture, "chemlab", TGJeiPlugin.CHEM_LAB);
		tank_overlay = guiHelper.createDrawable(ChemLabGui.texture, 177, 32, 10, 50);
		progress = new ProgressBarChemLab(guiHelper, 54, 31);
	}

	@Override
	public void setRecipe(IRecipeLayout recipeLayout, ChemLabJeiRecipe recipeWrapper, IIngredients ingredients) {
		IGuiItemStackGroup guiItemStacks = recipeLayout.getItemStacks();
		IGuiFluidStackGroup guiFluidStacks = recipeLayout.getFluidStacks();
		
		guiItemStacks.init(ChemLabTileEnt.SLOT_INPUT1,true,SLOT_INPUT1_X+JEI_OFFSET_X,SLOTS_ROW1_Y+JEI_OFFSET_Y);
		guiItemStacks.init(ChemLabTileEnt.SLOT_INPUT2,true,SLOT_INPUT2_X+JEI_OFFSET_X,SLOTS_ROW1_Y+JEI_OFFSET_Y);
		guiItemStacks.init(ChemLabTileEnt.SLOT_BOTTLE,true,SLOT_INPUT1_X+JEI_OFFSET_X,SLOTS_ROW2_Y+JEI_OFFSET_Y);
		
		guiItemStacks.init(ChemLabTileEnt.SLOT_OUTPUT, false, SLOT_OUTPUT_X+JEI_OFFSET_X, SLOTS_ROW1_Y+JEI_OFFSET_Y);
	
		guiFluidStacks.init(0, true, ChemLabGui.INPUT_TANK_X+2+JEI_OFFSET_X, ChemLabGui.TANK_Y+1+JEI_OFFSET_Y, ChemLabGui.TANK_W, ChemLabGui.TANK_H, ChemLabTileEnt.CAPACITY_INPUT_TANK, false, tank_overlay);
		guiFluidStacks.init(1, false, ChemLabGui.OUTPUT_TANK_X+2+JEI_OFFSET_X, ChemLabGui.TANK_Y+1+JEI_OFFSET_Y, ChemLabGui.TANK_W, ChemLabGui.TANK_H, ChemLabTileEnt.CAPACITY_OUTPUT_TANK, false, tank_overlay);
		
		guiItemStacks.set(ingredients);
		guiFluidStacks.set(ingredients);
	}
	
	@Override
	public void drawExtras(Minecraft minecraft) {
		super.drawExtras(minecraft);
		this.powerbar.draw(minecraft, 8+JEI_OFFSET_X, 17+JEI_OFFSET_Y);
		this.progress.draw(minecraft, 82+JEI_OFFSET_X, 18+JEI_OFFSET_Y);
	}

}
