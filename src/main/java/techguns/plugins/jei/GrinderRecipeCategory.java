package techguns.plugins.jei;

import static techguns.gui.containers.GrinderContainer.SLOTS_INPUT_Y;
import static techguns.gui.containers.GrinderContainer.SLOT_INPUT_X;
import static techguns.gui.containers.GrinderContainer.SLOT_OUTPUT0_X;

import java.util.List;

import mezz.jei.api.IGuiHelper;
import mezz.jei.api.gui.IDrawableAnimated;
import mezz.jei.api.gui.IDrawableStatic;
import mezz.jei.api.gui.IGuiItemStackGroup;
import mezz.jei.api.gui.IRecipeLayout;
import mezz.jei.api.gui.ITooltipCallback;
import mezz.jei.api.ingredients.IIngredients;
import net.minecraft.client.Minecraft;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import techguns.gui.GrinderGui;
import techguns.gui.widgets.SlotItemHandlerOutput;
import techguns.tileentities.GrinderTileEnt;
import techguns.tileentities.operation.GrinderRecipes.GrinderRecipe;
import techguns.tileentities.operation.GrinderRecipes.GrinderRecipeChance;
import techguns.util.TextUtil;

public class GrinderRecipeCategory extends BasicRecipeCategory<GrinderJeiRecipe> {

	IDrawableStatic progress1_static;
	IDrawableAnimated progress1;
	
	IDrawableStatic progress2_static;
	IDrawableAnimated progress2;
	
	public GrinderRecipeCategory(IGuiHelper guiHelper) {
		super(guiHelper, GrinderGui.texture, "grinder",TGJeiPlugin.GRINDER);
		
		this.progress1_static = guiHelper.createDrawable(GrinderGui.texture, 0, 167, 21, 21);
		this.progress1 = guiHelper.createAnimatedDrawable(progress1_static, 100, IDrawableAnimated.StartDirection.LEFT, false);
		
		this.progress2_static = guiHelper.createDrawable(GrinderGui.texture, 0, 167, 21, 21);
		this.progress2 = guiHelper.createAnimatedDrawable(progress2_static, 100, IDrawableAnimated.StartDirection.RIGHT, false);
	}

	@Override
	public void setRecipe(IRecipeLayout recipeLayout, GrinderJeiRecipe recipeWrapper, IIngredients ingredients) {
		IGuiItemStackGroup guiItemStacks = recipeLayout.getItemStacks();

		guiItemStacks.init(GrinderTileEnt.SLOT_INPUT, true, SLOT_INPUT_X+JEI_OFFSET_X, SLOTS_INPUT_Y+JEI_OFFSET_Y);
		
		for (int i=0;i<3;i++){
			for(int j=0;j<3;j++){
				guiItemStacks.init(GrinderTileEnt.SLOT_OUTPUT0+i*3+j, false, SLOT_OUTPUT0_X+j*18+JEI_OFFSET_X, SLOTS_INPUT_Y+JEI_OFFSET_Y+i*18);
			}
		}
		guiItemStacks.addTooltipCallback(new GrinderOutputTooltipCallback(recipeWrapper));

		guiItemStacks.set(ingredients);
	}
	
	@Override
	public void drawExtras(Minecraft minecraft) {
		super.drawExtras(minecraft);
		this.powerbar.draw(minecraft, 8+JEI_OFFSET_X, 17+JEI_OFFSET_Y);
		this.progress1.draw(minecraft, 32+JEI_OFFSET_X, 40+JEI_OFFSET_Y);
		this.progress2.draw(minecraft, 52+JEI_OFFSET_X, 40+JEI_OFFSET_Y);
	}

	
	protected class GrinderOutputTooltipCallback implements ITooltipCallback<ItemStack>{
		GrinderJeiRecipe jeiRec;

		public GrinderOutputTooltipCallback(GrinderJeiRecipe jeiRec) {
			super();
			this.jeiRec = jeiRec;
		}

		@Override
		public void onTooltip(int slotIndex, boolean input, ItemStack ingredient, List<String> tooltip) {
			if(!input) {
				if(jeiRec.rec instanceof GrinderRecipeChance) {
					GrinderRecipeChance r = (GrinderRecipeChance) jeiRec.rec;
					if(r.getChances()[slotIndex-GrinderTileEnt.SLOT_OUTPUT0]<1) {
						tooltip.add(tooltip.size()-1, TextUtil.transTG("oredrill.jei.chance")+": "+String.format("%.2f", r.getChances()[slotIndex-GrinderTileEnt.SLOT_OUTPUT0]*100f)+"%" );
					}
				}
			}
		}

	}

}
