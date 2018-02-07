package techguns.gui.player;

import micdoodle8.mods.galacticraft.api.client.tabs.AbstractTab;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.inventory.GuiInventory;
import net.minecraft.client.resources.I18n;
import net.minecraft.item.ItemStack;
import net.minecraft.network.play.client.CPacketCloseWindow;
import techguns.TGItems;
import techguns.TGPackets;
import techguns.packets.PacketOpenPlayerGUI;

public class InventoryTabTechGuns extends AbstractTab
{
	public InventoryTabTechGuns()
	{
		super(0, 0, 0, TGItems.PISTOL_ROUNDS);
	}
	
	@Override
	public void onTabClicked()
	{
		TGPackets.network.sendToServer(new PacketOpenPlayerGUI());
	}
	
	@Override
	public boolean shouldAddToList()
	{
		return true;
	}
}
