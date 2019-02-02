package techguns.tileentities.operation;

import net.minecraft.item.ItemStack;
import techguns.tileentities.BasicMachineTileEnt;

public class MachineSlotItem {
	protected BasicMachineTileEnt tile;
	protected int slotID;
	
	public MachineSlotItem(BasicMachineTileEnt tile, int slotID) {
		super();
		this.tile = tile;
		this.slotID = slotID;
	}

	/**
	 * consumes a specific amount
	 */
	public void consume(int amount) {
		if(amount >0) {
			/*ItemStack stack = tile.getInventory().getStackInSlot(slotID);
			stack.shrink(amount);*/
			this.tile.getInventory().extractWithoutCheck(slotID, amount, false);
		}
	}
	
	public ItemStack getTypeWithSize(int size) {
		ItemStack stack = tile.getInventory().getStackInSlot(slotID).copy();
		stack.setCount(size);
		return stack;
	}
	
	public boolean canConsume(int amount) {
		ItemStack stack = tile.getInventory().getStackInSlot(slotID);
		return !stack.isEmpty() && stack.getCount()>=amount;
	}
	
	public boolean canConsumeWithMultiplier(ItemStack stack, int multiplier) {
		if (stack.isEmpty()) {
			return true;
		}
		return stack.getCount()*multiplier <= this.get().getCount();
	}
	
	public ItemStack get() {
		return tile.getInventory().getStackInSlot(slotID);
	}
	
	public void setStackInSlot(ItemStack stack) {
		tile.getInventory().setStackInSlot(slotID, stack);
	}
}
