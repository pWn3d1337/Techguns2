package techguns.events;

import net.minecraft.entity.player.InventoryPlayer;

public interface IGuiFactory<T> {
	Object createElement(InventoryPlayer player, T tile);
}
