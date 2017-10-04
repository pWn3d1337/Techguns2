package techguns.plugins.jei;

import static techguns.gui.containers.FabricatorContainer.SLOTS_ROW1_Y;
import static techguns.gui.containers.FabricatorContainer.SLOT_INPUT1_X;
import static techguns.gui.containers.FabricatorContainer.SLOT_OUTPUT_X;
import static techguns.gui.containers.FabricatorContainer.SLOT_OUTPUT_Y;
import static techguns.gui.containers.FabricatorContainer.SLOT_PLATE_X;
import static techguns.gui.containers.FabricatorContainer.SLOT_POWDER_X;
import static techguns.gui.containers.FabricatorContainer.SLOT_WIRES_X;

import mezz.jei.api.IGuiHelper;
import mezz.jei.api.gui.IDrawableAnimated;
import mezz.jei.api.gui.IDrawableStatic;
import mezz.jei.api.gui.IGuiItemStackGroup;
import mezz.jei.api.gui.IRecipeLayout;
import mezz.jei.api.ingredients.IIngredients;
import net.minecraft.client.Minecraft;
import techguns.gui.FabricatorGui;
import techguns.tileentities.FabricatorTileEntMaster;

public class FabricatorJeiRecipeCategory extends BasicRecipeCategory<FabricatorJeiRecipe> {

	IDrawableStatic progress_static;
	IDrawableAnimated progress;
	
	public FabricatorJeiRecipeCategory(IGuiHelper guiHelper) {
		super(guiHelper, FabricatorGui.texture, "fabricator", TGJeiPlugin.FABRICATOR);
		this.progress_static = guiHelper.createDrawable(FabricatorGui.texture, 0, 167, 90, 10);
		this.progress = guiHelper.createAnimatedDrawable(progress_static, 100, IDrawableAnimated.StartDirection.LEFT, false);
	}

	@Override
	public void setRecipe(IRecipeLayout recipeLayout, FabricatorJeiRecipe recipeWrapper, IIngredients ingredients) {
		IGuiItemStackGroup guiItemStacks = recipeLayout.getItemStacks();
		
		guiItemStacks.init(FabricatorTileEntMaster.SLOT_INPUT1,true,SLOT_INPUT1_X+JEI_OFFSET_X,SLOTS_ROW1_Y+JEI_OFFSET_Y);
		guiItemStacks.init(FabricatorTileEntMaster.SLOT_WIRES,true,SLOT_WIRES_X+JEI_OFFSET_X,SLOTS_ROW1_Y+JEI_OFFSET_Y);
		guiItemStacks.init(FabricatorTileEntMaster.SLOT_POWDER,true,SLOT_POWDER_X+JEI_OFFSET_X,SLOTS_ROW1_Y+JEI_OFFSET_Y);
		guiItemStacks.init(FabricatorTileEntMaster.SLOT_PLATE,true,SLOT_PLATE_X+JEI_OFFSET_X,SLOTS_ROW1_Y+JEI_OFFSET_Y);

		guiItemStacks.init(FabricatorTileEntMaster.SLOT_OUTPUT, false, SLOT_OUTPUT_X+JEI_OFFSET_X, SLOT_OUTPUT_Y+JEI_OFFSET_Y);	
		
		guiItemStacks.set(ingredients);
	}

	@Override
	public void drawExtras(Minecraft minecraft) {
		super.drawExtras(minecraft);
		this.powerbar.draw(minecraft, 8+JEI_OFFSET_X, 17+JEI_OFFSET_Y);
		this.progress.draw(minecraft, 22+JEI_OFFSET_X, 54+JEI_OFFSET_Y);
	}

	
}
