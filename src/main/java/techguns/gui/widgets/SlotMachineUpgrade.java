package techguns.gui.widgets;

import net.minecraft.item.ItemStack;
import techguns.TGItems;
import techguns.tileentities.operation.ItemStackHandlerPlus;

public class SlotMachineUpgrade extends SlotMachineInput {

	protected final int index;
	
	public SlotMachineUpgrade(ItemStackHandlerPlus itemHandler, int index, int xPosition, int yPosition) {
		super(itemHandler, index, xPosition, yPosition);
		this.index=index;
	}
	
	@Override
	public boolean isItemValid(ItemStack item) {
		return (!item.isEmpty() && item.getItem()==TGItems.SHARED_ITEM && item.getItemDamage()==TGItems.MACHINE_UPGRADE_STACK.getItemDamage());
	}

	@Override
	public int getSlotStackLimit() {
		return 7;
	}	

}
