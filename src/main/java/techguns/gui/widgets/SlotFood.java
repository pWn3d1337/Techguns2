package techguns.gui.widgets;

import net.minecraft.item.ItemFood;
import net.minecraft.item.ItemStack;
import net.minecraftforge.items.ItemStackHandler;
import net.minecraftforge.items.SlotItemHandler;

public class SlotFood extends SlotItemHandler {

	public SlotFood(ItemStackHandler inv, int id, int x, int y) {
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
