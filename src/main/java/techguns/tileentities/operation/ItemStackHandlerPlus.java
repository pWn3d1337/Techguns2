package techguns.tileentities.operation;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.util.NonNullList;
import net.minecraftforge.common.util.Constants;
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
	
	/**
	 * Nasty method, but... merging a list of items to a list of slots is a nasty problem
	 * @param startSlot firstSlot id
	 * @param endSlot lastSlot id
	 * @param stacks list of Stacks to check for merge
	 * @return
	 */
	public boolean canInsertAll(int startSlot, int endSlot, List<ItemStack> stacks) {
		if(endSlot<startSlot) return false;
		
		//create a temporary ItemStackHandler
		ItemStackHandlerPlus temp = new ItemStackHandlerPlus(endSlot-startSlot);
		for(int i=0;i<endSlot-startSlot; i++) {
			temp.setStackInSlot(i, this.getStackInSlot(i+startSlot).copy());
		}
		
		ArrayList<ItemStack> leftover = new ArrayList<ItemStack>(stacks.size());
		stacks.forEach(s -> leftover.add(s.copy()));
		
		for(int j =0; j<leftover.size(); j++) {

			//try merge non.empty slots
			for(int i=startSlot; i< endSlot; i++) {
				if(!temp.getStackInSlot(i-startSlot).isEmpty()) {
					leftover.set(j, temp.insertItemNoCheck(i-startSlot, leftover.get(j), false));
				}
				
			}	
			
			//try merge into empty slot
			for(int i=startSlot; i< endSlot; i++) {
				if(temp.getStackInSlot(i-startSlot).isEmpty()) {
					leftover.set(j, temp.insertItemNoCheck(i-startSlot, leftover.get(j), false));
				}
			}	
			
			//slot could not be merged!
			if(!leftover.get(j).isEmpty()) {
				return false;
			}
		}
		return true;
		
	}
	
	public List<ItemStack> mergeAll(int startSlot, int endSlot, List<ItemStack> stacks) {
		if(endSlot<startSlot) return stacks;
		
		//create a temporary ItemStackHandler
		
		ArrayList<ItemStack> leftover = new ArrayList<ItemStack>(stacks.size());
		stacks.forEach(s -> leftover.add(s.copy()));
		
		for(int j =0; j<leftover.size(); j++) {

			//try merge non.empty slots
			for(int i=startSlot; i< endSlot; i++) {
				if(!this.getStackInSlot(i).isEmpty()) {
					leftover.set(j, this.insertItemNoCheck(i, leftover.get(j), false));
				}
				
			}	
			
			//try merge into empty slot
			for(int i=startSlot; i< endSlot; i++) {
				if(this.getStackInSlot(i).isEmpty()) {
					leftover.set(j, this.insertItemNoCheck(i, leftover.get(j), false));
				}
			}	
		}
		return leftover;
		
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
	
	
	
	@Override
	public void deserializeNBT(NBTTagCompound nbt) {
        //setSize(nbt.hasKey("Size", Constants.NBT.TAG_INT) ? nbt.getInteger("Size") : stacks.size());
		setSize(this.getSlots()); //protect against crashes when updating to a higher inventory size.
		
        NBTTagList tagList = nbt.getTagList("Items", Constants.NBT.TAG_COMPOUND);
        for (int i = 0; i < tagList.tagCount(); i++)
        {
            NBTTagCompound itemTags = tagList.getCompoundTagAt(i);
            int slot = itemTags.getInteger("Slot");

            if (slot >= 0 && slot < stacks.size())
            {
                stacks.set(slot, new ItemStack(itemTags));
            }
        }
        onLoad();
	}

	/**
	 * Extract without check
	 */
	public ItemStack extractWithoutCheck(int slot, int amount, boolean simulate) {
		return super.extractItem(slot, amount, simulate);
	}
}
