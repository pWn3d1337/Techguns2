package techguns.gui.widgets;

import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntityFurnace;
import techguns.api.tginventory.ITGSpecialSlot;
import techguns.api.tginventory.TGSlotType;
import techguns.tileentities.operation.ItemStackHandlerPlus;

public class SlotFurnaceFuelTG extends SlotMachineInput {

	public SlotFurnaceFuelTG(ItemStackHandlerPlus itemHandler, int index, int xPosition, int yPosition) {
		super(itemHandler, index, xPosition, yPosition);
	}

	@Override
	public boolean isItemValid(ItemStack item) {
		return TileEntityFurnace.getItemBurnTime(item)>0;
	}

}
