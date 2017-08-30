package techguns.gui.widgets;

import net.minecraft.item.ItemStack;
import techguns.api.guns.IGenericGun;
import techguns.tileentities.operation.ItemStackHandlerPlus;

public class SlotTurretGun extends SlotMachineInput {

	public SlotTurretGun(ItemStackHandlerPlus itemHandler, int index, int xPosition, int yPosition) {
		super(itemHandler, index, xPosition, yPosition);
	}

	@Override
	public boolean isItemValid(ItemStack stack) {
		return stack.getItem() instanceof IGenericGun;
	}

	@Override
	public int getSlotStackLimit() {
		return 1;
	}
	
	@Override
	public String getSlotTexture() {
		return SlotTG.TURRETGUNSLOT_TEX.toString();
	}
}
