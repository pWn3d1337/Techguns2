package techguns.gui.widgets;

import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import techguns.tileentities.operation.AmmoPressBuildPlans;
import techguns.tileentities.operation.ItemStackHandlerPlus;

public class SlotAmmoPressInput extends SlotMachineInput {
	protected final int index;
	
	protected ResourceLocation bgtex;
	
	public SlotAmmoPressInput(ItemStackHandlerPlus itemHandler, int index, int xPosition, int yPosition, ResourceLocation bgtex) {
		super(itemHandler, index, xPosition, yPosition);
		this.index=index;
		this.bgtex = bgtex;
	}

	@Override
	public boolean isItemValid(ItemStack stack) {
		return AmmoPressBuildPlans.isValidFor(stack, index);
	}

	@Override
	public String getSlotTexture() {
		return bgtex.toString();
	}
	
}
