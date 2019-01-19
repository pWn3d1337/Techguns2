package techguns.tileentities.operation;

import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
import net.minecraftforge.items.ItemStackHandler;

public class ItemStackHandlerPlus extends ItemStackHandler {

	public ItemStackHandlerPlus() {
		super();
	}

	public ItemStackHandlerPlus(int size) {
		super(size);
	}

	public ItemStackHandlerPlus(NonNullList<ItemStack> stacks) {
		super(stacks);
	}

	@Override
	public ItemStack insertItem(int slot, ItemStack stack, boolean simulate) {
		if(!allowItemInSlot(slot, stack)) {
			return stack;
		}
		return super.insertItem(slot, stack, simulate);
	}

	public ItemStack insertItemNoCheck(int slot, ItemStack stack, boolean simulate) {
		return super.insertItem(slot, stack, simulate);
	}
	
	
	@Override
	public ItemStack extractItem(int slot, int amount, boolean simulate) {
		if (!allowExtractFromSlot(slot, amount)){
			return ItemStack.EMPTY;
		}
		return super.extractItem(slot, amount, simulate);
	}

	protected boolean allowItemInSlot(int slot, ItemStack stack) {
		return true;
	}
	
	protected boolean allowExtractFromSlot(int slot, int amount) {
		return true;
	}
	
	/**
	 * Extract without check
	 */
	public ItemStack extractWithoutCheck(int slot, int amount, boolean simulate) {
		return super.extractItem(slot, amount, simulate);
	}
}
