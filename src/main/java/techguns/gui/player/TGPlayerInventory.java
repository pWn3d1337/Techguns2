package techguns.gui.player;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraftforge.items.ItemStackHandler;
import techguns.api.tginventory.ITGSpecialSlot;
import techguns.capabilities.TGExtendedPlayer;

// TechgunsPlayerInventory
public class TGPlayerInventory extends ItemStackHandler {
	public static final int NUMSLOTS = 15;

	public static final int SLOT_FACE = 0;
	public static final int SLOT_BACK = 1;
	public static final int SLOT_HAND = 2;
	public static final int SLOTS_AUTOFOOD_START = 3;
	public static final int SLOTS_AUTOFOOD_END = 5;
	public static final int SLOT_AUTOHEAL = 6;
	public static final int SLOTS_AMMO_START = 7;
	public static final int SLOTS_AMMO_END = 14;

	public EntityPlayer player;

	private ItemStackHandler shadowInventory; // used to keep track of inventory changes

	public TGPlayerInventory(EntityPlayer player) {
		super(NUMSLOTS);
		shadowInventory = new ItemStackHandler(NUMSLOTS);
		this.player = player;
	}

	@Override
	public void onContentsChanged(int slotid){
		super.onContentsChanged(slotid);

		ItemStack oldStack = shadowInventory.getStackInSlot(slotid);
		ItemStack newStack = getStackInSlot(slotid);

		// cancel here if the stack didn't change
		if(oldStack.equals(newStack))
			return;


		if(newStack.getItem() instanceof ITGSpecialSlot) {
			((ITGSpecialSlot) newStack.getItem()).onSlotChanged(oldStack, newStack, player);

			if(!ItemStack.areItemsEqual(oldStack, newStack))
				((ITGSpecialSlot) newStack.getItem()).onEquipped(newStack, player);
		}

		if(oldStack.getItem() instanceof ITGSpecialSlot) {
			((ITGSpecialSlot) oldStack.getItem()).onSlotChanged(oldStack, newStack, player);

			if(!ItemStack.areItemsEqual(oldStack, newStack))
				((ITGSpecialSlot) oldStack.getItem()).onUnequipped(oldStack, player);
		}


		switch(slotid){
			case SLOT_FACE:
				player.getDataManager().set(TGExtendedPlayer.DATA_FACE_SLOT, newStack);
				break;
			case SLOT_BACK:
				player.getDataManager().set(TGExtendedPlayer.DATA_BACK_SLOT, newStack);
				break;
			case SLOT_HAND:
				player.getDataManager().set(TGExtendedPlayer.DATA_HAND_SLOT, newStack);
				break;
		}

		shadowInventory.setStackInSlot(slotid, newStack);
	}

}