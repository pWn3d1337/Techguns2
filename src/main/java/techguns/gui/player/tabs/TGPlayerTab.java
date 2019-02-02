package techguns.gui.player.tabs;

import micdoodle8.mods.galacticraft.api.client.tabs.AbstractTab;
import net.minecraft.item.ItemStack;
import techguns.TGItems;
import techguns.TGPackets;
import techguns.packets.PacketOpenPlayerGUI;

public class TGPlayerTab extends AbstractTab {

	public TGPlayerTab() {
		super(0,0,0, TGItems.PISTOL_ROUNDS);
	}

	@Override
	public void onTabClicked() {
		TGPackets.network.sendToServer(new PacketOpenPlayerGUI());
	}

	@Override
	public boolean shouldAddToList() {
		return true;
	}

}
