package techguns.items.additionalslots;

import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.gameevent.TickEvent.PlayerTickEvent;
import techguns.TGItems;
import techguns.TGPackets;
import techguns.Techguns;
import techguns.api.tginventory.TGSlotType;
import techguns.capabilities.TGExtendedPlayer;
import techguns.keybind.TGKeybindsID;
import techguns.packets.PacketTGKeybindPress;

public class ItemJetpack extends ItemTGSpecialSlotAmmo {

	public ItemJetpack(String unlocalizedName, int camoCount, int dur) {
		super(unlocalizedName, TGSlotType.BACKSLOT, camoCount, dur, TGItems.FUEL_TANK, TGItems.FUEL_TANK_EMPTY);
	}

	@Override
	public void onPlayerTick(ItemStack item, PlayerTickEvent event) {

		if (item.getItemDamage() >= item.getMaxDamage()) {
			tryReloadAndRepair(item, event.player);
		}

		TGExtendedPlayer extendedPlayer = TGExtendedPlayer.get(event.player);

		if (event.player.world.isRemote) {

			EntityPlayer self = Techguns.proxy.getPlayerClient();// Minecraft.getMinecraft().thePlayer;
			if (event.player == self) {

				if (extendedPlayer.enableJetpack) {
					boolean last_state = extendedPlayer.isJumpkeyPressed();
					boolean jump_pressed = Minecraft.getMinecraft().gameSettings.keyBindJump.isKeyDown();

					if (jump_pressed && !last_state) {
						extendedPlayer.setJumpkeyPressed(true);
						TGPackets.network.sendToServer(new PacketTGKeybindPress(TGKeybindsID.JETPACK_BOOST_START));
					} else if (!jump_pressed && last_state) {
						extendedPlayer.setJumpkeyPressed(false);
						TGPackets.network.sendToServer(new PacketTGKeybindPress(TGKeybindsID.JETPACK_BOOST_STOP));
					}
				}

				if (item.getItemDamage() < item.getMaxDamage()) {

					if (extendedPlayer.isJumpkeyPressed()) {
						applyBoostToPlayer(event.player, item);
					}
					// also act like glider
					ItemGlider.glide(event.player);

				} else {
					if (extendedPlayer.isJumpkeyPressed()) {
						extendedPlayer.setJumpkeyPressed(false);
						TGPackets.network.sendToServer(new PacketTGKeybindPress(TGKeybindsID.JETPACK_BOOST_STOP));
					}
				}

			}
		} else {

			// sever side
			if (item.getItemDamage() < item.getMaxDamage()) {

				if (extendedPlayer.isJumpkeyPressed()) {
					applyBoostToPlayer(event.player, item);
				}
				// also act like glider
				ItemGlider.glide(event.player);

			}

		}
	}

	protected void applyBoostToPlayer(EntityPlayer ply, ItemStack item) {
		ply.motionY = Math.min(ply.motionY + 0.15f, 0.4f);
		ply.fallDistance = 0;
		item.setItemDamage(item.getItemDamage() + 1);

	}

	protected void descendPlayer(EntityPlayer ply, ItemStack item) {
		ply.motionY = Math.max(ply.motionY - 0.15f, -0.4f);
		ply.fallDistance = 0;
		item.setItemDamage(item.getItemDamage() + 1);

	}

	protected void hoverPlayer(EntityPlayer ply, ItemStack item, boolean forwardPressed) {
		if (ply.motionY > 0) {
			ply.motionY = Math.max(0, ply.motionY - 0.2f);
		} else {
			ply.motionY = Math.min(0, ply.motionY + 0.2f);
		}
		ply.fallDistance = 0;

		item.setItemDamage(item.getItemDamage() + 1);
	}

}