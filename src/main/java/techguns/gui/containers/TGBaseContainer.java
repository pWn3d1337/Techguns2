package techguns.gui.containers;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.inventory.Slot;
import techguns.tileentities.BasicInventoryTileEnt;
import techguns.tileentities.CamoBenchTileEnt;

public class TGBaseContainer extends Container {
	protected BasicInventoryTileEnt basictile;

	public TGBaseContainer(InventoryPlayer player, BasicInventoryTileEnt ent) {
		this.basictile = ent;
	}
	
	
	@Override
	public boolean canInteractWith(EntityPlayer playerIn) {
		return basictile.isUseableByPlayer(playerIn);
	}
	
	protected static int getArmorIntFromEntityEquipmentSlot(EntityEquipmentSlot type) {
		switch(type) {
			case CHEST:
				return 1;
			case HEAD:
				return 0;
			case LEGS:
				return 2;
			default:
				return 3;
		}
	}
	
	public void addPlayerInventorySlots(InventoryPlayer player) {
		int i;

		for (i = 0; i < 3; ++i) {
			for (int j = 0; j < 9; ++j) {
				this.addSlotToContainer(new Slot(player, j + i * 9 + 9,
						8 + j * 18, 84 + i * 18));
			}
		}

		for (i = 0; i < 9; ++i) {
			this.addSlotToContainer(new Slot(player, i, 8 + i * 18, 142));
		}
	}
	
}