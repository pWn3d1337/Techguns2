package techguns.plugins.jei;


import java.util.List;

import com.mojang.realmsclient.gui.ChatFormatting;

import mezz.jei.api.IGuiHelper;
import mezz.jei.api.gui.IDrawableStatic;
import mezz.jei.api.gui.IGuiFluidStackGroup;
import mezz.jei.api.gui.IGuiItemStackGroup;
import mezz.jei.api.gui.IRecipeLayout;
import mezz.jei.api.gui.ITooltipCallback;
import mezz.jei.api.ingredients.IIngredients;
import net.minecraft.client.Minecraft;
import net.minecraftforge.fluids.FluidStack;
import techguns.gui.ChemLabGui;
import techguns.gui.ReactionChamberGui;
import techguns.gui.containers.ReactionChamberContainer;
import techguns.tileentities.ReactionChamberTileEntMaster;
import techguns.util.TextUtil;

public class ReactionChamberJeiRecipeCategory extends BasicRecipeCategory<ReactionChamberJeiRecipe>{

	protected IDrawableStatic tank_overlay;
	
	public ReactionChamberJeiRecipeCategory(IGuiHelper guiHelper) {
		super(guiHelper, ReactionChamberGui.texture, "reactionchamber", TGJeiPlugin.REACTION_CHAMBER);
		
		tank_overlay = guiHelper.createDrawable(ReactionChamberGui.texture, 177, 32, 10, 50);
	}

	@Override
	public void setRecipe(IRecipeLayout recipeLayout, ReactionChamberJeiRecipe recipeWrapper,
			IIngredients ingredients) {
		IGuiItemStackGroup guiItemStacks = recipeLayout.getItemStacks();
		IGuiFluidStackGroup guiFluidStacks = recipeLayout.getFluidStacks();
		

		guiItemStacks.init(ReactionChamberTileEntMaster.SLOT_INPUT, true, ReactionChamberContainer.SLOT_INPUT_X+JEI_OFFSET_X, ReactionChamberContainer.SLOTS_ROW1_Y+JEI_OFFSET_Y);
		guiItemStacks.init(ReactionChamberTileEntMaster.SLOT_FOCUS, true, ReactionChamberContainer.SLOT_FOCUS_X+JEI_OFFSET_X, ReactionChamberContainer.SLOTS_ROW1_Y+JEI_OFFSET_Y);
		
		
		guiItemStacks.init(ReactionChamberTileEntMaster.SLOT_OUTPUT, false, ReactionChamberContainer.SLOT_OUTPUT_X+JEI_OFFSET_X, ReactionChamberContainer.SLOT_OUTPUT_Y+JEI_OFFSET_Y);
		guiItemStacks.init(ReactionChamberTileEntMaster.SLOT_OUTPUT+1, false, ReactionChamberContainer.SLOT_OUTPUT_X+JEI_OFFSET_X+18, ReactionChamberContainer.SLOT_OUTPUT_Y+JEI_OFFSET_Y);
		guiItemStacks.init(ReactionChamberTileEntMaster.SLOT_OUTPUT+2, false, ReactionChamberContainer.SLOT_OUTPUT_X+JEI_OFFSET_X, ReactionChamberContainer.SLOT_OUTPUT_Y+JEI_OFFSET_Y+18);
		guiItemStacks.init(ReactionChamberTileEntMaster.SLOT_OUTPUT+3, false, ReactionChamberContainer.SLOT_OUTPUT_X+JEI_OFFSET_X+18, ReactionChamberContainer.SLOT_OUTPUT_Y+JEI_OFFSET_Y+18);

		
		guiFluidStacks.init(0, true, ChemLabGui.INPUT_TANK_X+2+JEI_OFFSET_X, ChemLabGui.TANK_Y+1+JEI_OFFSET_Y, ChemLabGui.TANK_W, ChemLabGui.TANK_H, ReactionChamberTileEntMaster.CAPACITY_INPUT_TANK, false, tank_overlay);
		guiFluidStacks.addTooltipCallback(new TankTooltipCallback(recipeWrapper));
		
		guiItemStacks.set(ingredients);
		guiFluidStacks.set(ingredients);
	}
	
	protected class TankTooltipCallback implements ITooltipCallback<FluidStack>{
		ReactionChamberJeiRecipe jeiRec;

		public TankTooltipCallback(ReactionChamberJeiRecipe jeiRec) {
			super();
			this.jeiRec = jeiRec;
		}

		@Override
		public void onTooltip(int slotIndex, boolean input, FluidStack ingredient, List<String> tooltip) {
			int consumption = jeiRec.recipe.liquidConsumtion;
			tooltip.add(tooltip.size()-1, ChatFormatting.GRAY+TextUtil.trans("techguns.container.reactionchamber.consumption")+": "+String.format("%,d",consumption)+" mB");
		}
		
	}
	
	@Override
	public void drawExtras(Minecraft minecraft) {
		super.drawExtras(minecraft);
		this.powerbar.draw(minecraft, 8+JEI_OFFSET_X, 17+JEI_OFFSET_Y);
	}

}
