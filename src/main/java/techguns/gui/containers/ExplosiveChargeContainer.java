package techguns.gui.containers;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.item.ItemStack;
import techguns.tileentities.ExplosiveChargeTileEnt;

public class ExplosiveChargeContainer<T extends ExplosiveChargeTileEnt> extends OwnedTileContainer {
	protected T tile;

	public ExplosiveChargeContainer(InventoryPlayer player, T tile){
		super(player, tile);
		this.tile=tile;
	}

	@Override
	public ItemStack transferStackInSlot(EntityPlayer player, int slotid) {
		return null;
	};
	
	@Override
	public boolean canInteractWith(EntityPlayer player) {
		return tile.isUseableByPlayer(player);
	}

}
