package techguns.api.tginventory;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.gameevent.TickEvent.PlayerTickEvent;
import techguns.items.armors.TGArmorBonus;

public interface ITGSpecialSlot {
	/**
	 * The slot type this item belongs
	 * @param item
	 * @return
	 */
	public TGSlotType getSlot(ItemStack item);

	/**
	 * Called each player tick, when worn on back/face/hand slot
	 * @param item
	 * @param player
	 */
	public default void onPlayerTick(ItemStack item, PlayerTickEvent player) {};

	/**
	 * Called when the item gets equipped
	 * this compares only the item! if you swap the item for one with different NBT it wont get called, use onSlotChanged() to detect swapping of unique items
	 * @param stack
	 * @param player
	 */
	public default void onEquipped(ItemStack stack, EntityPlayer player) {};

	/**
	 * Called when the item gets unequipped
	 * this compares only the item! if you swap the item for one with different NBT it wont get called, use onSlotChanged() to detect swapping of unique items
	 * @param stack
	 * @param player
	 */
	public default void onUnequipped(ItemStack stack, EntityPlayer player) {};

	/**
	 * Called when the item slot changes (equipped/unequipped/updated)
	 * gets called for both stacks. oldStack and newStack might be the same itemStack
	 * @param oldStack
	 * @param newStack
	 * @param player
	 */
	public default void onSlotChanged(ItemStack oldStack, ItemStack newStack, EntityPlayer player) {};

	/**
	 * @param type The bonus type
	 * @param stack
	 * @param consume if power should be consumed (the bonus is actively used)
	 * @param player
	 * @return
	 */
	public default float getBonus(TGArmorBonus type, ItemStack stack, boolean consume, EntityPlayer player) {
		return 0f;
	}


}
