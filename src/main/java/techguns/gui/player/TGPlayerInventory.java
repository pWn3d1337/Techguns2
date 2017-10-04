package techguns.gui.player;

import java.util.List;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.ItemStackHelper;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.NonNullList;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentTranslation;

public class TGPlayerInventory implements IInventory {
	private static final String name = "TechgunsPlayerInventory";

	public static final int NUMSLOTS = 15;

	public static final int SLOT_FACE = 0;
	public static final int SLOT_BACK = 1;
	public static final int SLOT_HAND = 2;
	public static final int SLOTS_AUTOFOOD_START = 3;
	public static final int SLOTS_AUTOFOOD_END = 5;
	public static final int SLOT_AUTOHEAL = 6;
	public static final int SLOTS_AMMO_START = 7;
	public static final int SLOTS_AMMO_END = 14;

	public NonNullList<ItemStack> inventory = NonNullList.<ItemStack>withSize(NUMSLOTS, ItemStack.EMPTY);// new
																											// ItemStack[NUMSLOTS];

	public boolean dirty = false;
	public EntityPlayer player;

	public TGPlayerInventory(EntityPlayer player) {
		this.player = player;
	}

	
	public void saveNBTData(NBTTagCompound tags) {
		// NBTTagList nbttaglist = new NBTTagList();
		ItemStackHelper.saveAllItems(tags, this.inventory);

		// tags.setTag(name, nbttaglist);
	}
	public void loadNBTData(NBTTagCompound tags) {
		// NBTTagList nbttaglist = tags.getTagList(name, 10);
		this.inventory.clear(); // = NonNullList.<ItemStack>withSize(NUMSLOTS, ItemStack.EMPTY);

		ItemStackHelper.loadAllItems(tags, this.inventory);
	}

	@Override
	public int getSizeInventory() {
		return NUMSLOTS;
	}

	@Override
	public ItemStack getStackInSlot(int slotid) {
		return slotid >= 0 && slotid < this.inventory.size() ? this.inventory.get(slotid) : ItemStack.EMPTY;
	}

	@Override
	public ItemStack decrStackSize(int index, int count) {
		List<ItemStack> list = this.inventory;

		return list != null && !((ItemStack) list.get(index)).isEmpty() ? ItemStackHelper.getAndSplit(list, index, count) : ItemStack.EMPTY;
	}

	@Override
	public void setInventorySlotContents(int slotid, ItemStack itemstack) {
		this.inventory.set(slotid, itemstack);

		// TODO ??
		/*
		 * if (slotid == SLOT_FACE){
		 * player.getDataWatcher().updateObject(TechgunsExtendedPlayerProperties
		 * .DATA_WATCHER_ID_FACESLOT, this.inventory[slotid]); } else if (slotid
		 * == SLOT_BACK){
		 * player.getDataWatcher().updateObject(TechgunsExtendedPlayerProperties
		 * .DATA_WATCHER_ID_BACKSLOT, this.inventory[slotid]); }
		 */

		// this.markDirty();
	}

	@Override
	public int getInventoryStackLimit() {
		return 64;
	}

	@Override
	public void markDirty() {
		// System.out.println("Marked inv as dirty");
		this.dirty = true;
	}

	@Override
	public boolean isItemValidForSlot(int slotid, ItemStack itemstack) {
		return true;
	}

	@Override
	public String getName() {
		return this.name;
	}

	@Override
	public boolean hasCustomName() {
		return false;
	}

	@Override
	public ITextComponent getDisplayName() {
		return new TextComponentTranslation("techguns.extendedinventory", new Object[0]);
	}

	@Override
	public boolean isEmpty() {
		for (ItemStack itemstack1 : this.inventory) {
			if (!itemstack1.isEmpty()) {
				return false;
			}
		}
		return true;
	}

	@Override
	public ItemStack removeStackFromSlot(int index) {
		NonNullList<ItemStack> nonnulllist = this.inventory;
		if (nonnulllist != null && !((ItemStack) nonnulllist.get(index)).isEmpty()) {
			ItemStack itemstack = nonnulllist.get(index);
			nonnulllist.set(index, ItemStack.EMPTY);
			return itemstack;
		} else {
			return ItemStack.EMPTY;
		}
	}

	@Override
	public void openInventory(EntityPlayer player) {
	}

	@Override
	public void closeInventory(EntityPlayer player) {
	}

	@Override
	public int getField(int id) {
		return 0;
	}

	@Override
	public void setField(int id, int value) {
	}

	@Override
	public int getFieldCount() {
		return 0;
	}

	@Override
	public void clear() {
		this.inventory.clear();
	}

	@Override
	public boolean isUsableByPlayer(EntityPlayer player) {
		if (this.player.isDead) {
			return false;
		} else {
			return player.getDistanceSq(this.player) <= 64.0D;
		}
	}

}