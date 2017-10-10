package techguns.gui.widgets;

import javax.annotation.Nonnull;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraftforge.items.SlotItemHandler;
import techguns.tileentities.operation.ItemStackHandlerPlus;

public class SlotMachineInput extends SlotItemHandler {

	protected final int index;
	protected ItemStackHandlerPlus inventory;
	
	public SlotMachineInput(ItemStackHandlerPlus itemHandler, int index, int xPosition, int yPosition) {
		super(itemHandler, index, xPosition, yPosition);
		this.index=index;
		this.inventory = itemHandler;
	}
	
    /**
     * Return whether this slot's stack can be taken from this slot.
     */
    @Override
    public boolean canTakeStack(EntityPlayer playerIn)
    {
        return !inventory.extractFromGui(index, 1, true).isEmpty();
    }

    /**
     * Decrease the size of the stack in slot (first int arg) by the amount of the second int arg. Returns the new
     * stack.
     */
    @Override
    @Nonnull
    public ItemStack decrStackSize(int amount)
    {
        return inventory.extractFromGui(index, amount, false);
    }
	
}
