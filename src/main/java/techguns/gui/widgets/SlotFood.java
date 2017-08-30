package techguns.gui.widgets;

import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemFood;
import net.minecraft.item.ItemStack;

public class SlotFood extends Slot {

	public SlotFood(IInventory inv, int id, int x, int y) {
		super(inv, id, x, y);
	}

	@Override
	public boolean isItemValid(ItemStack item) {
		return item.getItem() instanceof ItemFood;
	}	
	
	@Override
	public String getSlotTexture() {
		return SlotTG.FOODSLOT_TEX.toString();
	}
}
