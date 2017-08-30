package techguns.events;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.tileentity.TileEntity;

public interface IGuiFactory<T> {
	Object createElement(InventoryPlayer player, T tile);
}
