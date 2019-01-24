package techguns.plugins.jei;

import java.util.List;

import com.mojang.realmsclient.gui.ChatFormatting;

import mezz.jei.api.IGuiHelper;
import mezz.jei.api.gui.IDrawableAnimated;
import mezz.jei.api.gui.IDrawableStatic;
import mezz.jei.api.gui.IGuiFluidStackGroup;
import mezz.jei.api.gui.IGuiItemStackGroup;
import mezz.jei.api.gui.IRecipeLayout;
import mezz.jei.api.gui.ITooltipCallback;
import mezz.jei.api.ingredients.IIngredients;
import net.minecraft.client.Minecraft;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fluids.FluidStack;
import techguns.gui.AmmoPressGui;
import techguns.gui.OreDrillGui;
import techguns.gui.containers.OreDrillContainer;
import techguns.tileentities.OreDrillTileEntMaster;
import techguns.util.TextUtil;

public class OreDrillJeiRecipeCategory extends BasicRecipeCategory<OreDrillJeiRecipe> {

	protected IDrawableStatic tank_overlay;
	protected IDrawableStatic progress_static;
	protected IDrawableAnimated progress;
	
	public OreDrillJeiRecipeCategory(IGuiHelper guiHelper) {
		super(guiHelper, OreDrillGui.texture, "oredrill", TGJeiPlugin.ORE_DRILL);
		tank_overlay = guiHelper.createDrawable(OreDrillGui.texture, 177, 40, 10, 50);
		
		this.progress_static = guiHelper.createDrawable(OreDrillGui.texture, 177, 1, 25, 36);
		this.progress = guiHelper.createAnimatedDrawable(progress_static, 100, IDrawableAnimated.StartDirection.TOP, false);
	}

	@Override
	public void setRecipe(IRecipeLayout recipeLayout, OreDrillJeiRecipe recipeWrapper, IIngredients ingredients) {
		IGuiItemStackGroup guiItemStacks = recipeLayout.getItemStacks();
		IGuiFluidStackGroup guiFluidStacks = recipeLayout.getFluidStacks();
		
		/*guiItemStacks.init(ChemLabTileEnt.SLOT_INPUT1,true,SLOT_INPUT1_X+JEI_OFFSET_X,SLOTS_ROW1_Y+JEI_OFFSET_Y);
		guiItemStacks.init(ChemLabTileEnt.SLOT_INPUT2,true,SLOT_INPUT2_X+JEI_OFFSET_X,SLOTS_ROW1_Y+JEI_OFFSET_Y);
		guiItemStacks.init(ChemLabTileEnt.SLOT_BOTTLE,true,SLOT_INPUT1_X+JEI_OFFSET_X,SLOTS_ROW2_Y+JEI_OFFSET_Y);
		
		guiItemStacks.init(ChemLabTileEnt.SLOT_OUTPUT, false, SLOT_OUTPUT_X+JEI_OFFSET_X, SLOTS_ROW1_Y+JEI_OFFSET_Y);
	*/
		guiItemStacks.init(OreDrillTileEntMaster.SLOT_DRILL, true, OreDrillContainer.SLOT_DRILL_X+JEI_OFFSET_X, OreDrillContainer.SLOTS_ROW1_Y+JEI_OFFSET_Y);
		guiItemStacks.init(1, true, 69, 40);
		guiItemStacks.init(2, true, OreDrillContainer.SLOT_DRILL_X+JEI_OFFSET_X, OreDrillContainer.SLOTS_FURNACE_Y+JEI_OFFSET_Y);
		
		guiItemStacks.init(3, false, OreDrillContainer.SLOTS_OUTPUT_X+JEI_OFFSET_X, OreDrillContainer.SLOTS_ROW1_Y+JEI_OFFSET_Y);
		guiItemStacks.addTooltipCallback(new OreDrillOutputTooltipCallback(recipeWrapper));
		
		guiFluidStacks.init(0, true, OreDrillGui.INPUT_TANK_X+2+JEI_OFFSET_X, OreDrillGui.TANK_Y+1+JEI_OFFSET_Y, OreDrillGui.TANK_W, OreDrillGui.TANK_H, OreDrillTileEntMaster.CAPACITY_INPUT_TANK, false, tank_overlay);
		guiFluidStacks.init(1, false, OreDrillGui.OUTPUT_TANK_X+2+JEI_OFFSET_X, OreDrillGui.TANK_Y+1+JEI_OFFSET_Y, OreDrillGui.TANK_W, OreDrillGui.TANK_H, OreDrillTileEntMaster.CAPACITY_OUTPUT_TANK, false, tank_overlay);
		guiFluidStacks.addTooltipCallback(new TankTooltipCallback(recipeWrapper));
		
		guiItemStacks.set(ingredients);
		guiFluidStacks.set(ingredients);
	}

	protected class OreDrillOutputTooltipCallback implements ITooltipCallback<ItemStack>{
		OreDrillJeiRecipe jeiRec;

		public OreDrillOutputTooltipCallback(OreDrillJeiRecipe jeiRec) {
			super();
			this.jeiRec = jeiRec;
		}

		@Override
		public void onTooltip(int slotIndex, boolean input, ItemStack ingredient, List<String> tooltip) {
			if(!input) {
				tooltip.add(tooltip.size()-1, TextUtil.transTG("oredrill.jei.chance")+": "+String.format("%.2f", jeiRec.getChance()*100f)+"%" );
			}
		}

	}
	
	protected class TankTooltipCallback implements ITooltipCallback<FluidStack>{
		OreDrillJeiRecipe jeiRec;

		public TankTooltipCallback(OreDrillJeiRecipe jeiRec) {
			super();
			this.jeiRec = jeiRec;
		}

		@Override
		public void onTooltip(int slotIndex, boolean input, FluidStack ingredient, List<String> tooltip) {
			if(!input) {
				tooltip.add(tooltip.size()-1, TextUtil.transTG("oredrill.jei.chance")+": "+String.format("%.2f", jeiRec.getChance()*100f)+"%" );
			}
		}
		
	}
	
	@Override
	public void drawExtras(Minecraft minecraft) {
		super.drawExtras(minecraft);
		this.powerbar.draw(minecraft, 8+JEI_OFFSET_X, 17+JEI_OFFSET_Y);
		this.progress.draw(minecraft, 74+JEI_OFFSET_X, 18+JEI_OFFSET_Y);
	}
}
