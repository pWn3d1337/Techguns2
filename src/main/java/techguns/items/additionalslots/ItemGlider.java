package techguns.items.additionalslots;

import javax.vecmath.Vector2d;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.Vec2f;
import net.minecraft.util.math.Vec3d;
import net.minecraftforge.fml.common.gameevent.TickEvent.PlayerTickEvent;
import techguns.api.tginventory.TGSlotType;
import techguns.capabilities.TGExtendedPlayer;
import techguns.util.MathUtil;

public class ItemGlider extends ItemTGSpecialSlot {

	public ItemGlider(String unlocalizedName, int camoCount, int dur) {
		super(unlocalizedName, TGSlotType.BACKSLOT, camoCount, dur);
	}

	@Override
	public void onPlayerTick(ItemStack item, PlayerTickEvent event) {
		glide(event.player);
	}

	public static void glide(EntityPlayer player) {
		EntityPlayer ply = player;

		if (!ply.onGround && ply.isSneaking()) {
			if (ply.motionY < -0.1f) {

				TGExtendedPlayer extendedPlayer = TGExtendedPlayer.get(ply);
				extendedPlayer.isGliding = true;

				/**
				 * totally stolen from modular powersuits glider code
				 */
				Vec3d lookVec = player.getLookVec();
				MathUtil.Vec2 playerMoveVec = new MathUtil.Vec2(lookVec.x, lookVec.z);

				if (player.motionY < -0.1f) {
					double motion = Math.min(0.08, -0.1 - player.motionY);
					player.motionY += motion;
					player.motionX += playerMoveVec.x * motion;
					player.motionZ += playerMoveVec.y * motion;

					player.jumpMovementFactor += 0.03f;
				}
			}
			player.fallDistance = 0;

		}
	}
	
}
