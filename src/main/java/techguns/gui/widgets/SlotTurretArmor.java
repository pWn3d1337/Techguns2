package techguns.gui.widgets;

import net.minecraft.item.ItemStack;
import techguns.api.tginventory.ITGSpecialSlot;
import techguns.api.tginventory.TGSlotType;
import techguns.tileentities.operation.ItemStackHandlerPlus;

public class SlotTurretArmor extends SlotMachineInput {

	public SlotTurretArmor(ItemStackHandlerPlus itemHandler, int index, int xPosition, int yPosition) {
		super(itemHandler, index, xPosition, yPosition);
	}

	@Override
	public boolean isItemValid(ItemStack stack) {
		return (stack.getItem() instanceof ITGSpecialSlot && ((ITGSpecialSlot)stack.getItem()).getSlot(stack)==TGSlotType.TURRETARMOR);
	}

	@Override
	public int getSlotStackLimit() {
		return 1;
	}

	@Override
	public String getSlotTexture() {
		return SlotTG.TURTETARMORSLOT_TEX.toString();
	}

	
}
