package techguns.packets;

import io.netty.buffer.ByteBuf;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumHand;
import net.minecraft.util.datafix.fixes.EntityId;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import techguns.TGPackets;
import techguns.api.guns.IGenericGun;

/**
 * Tells the server that the player who sent this message wants to shoot with his current gun
 *
 */
public class PacketShootGunTarget implements IMessage {
	public boolean isZooming=false;
	public boolean offHand=false;
	public int entityId = -1;
	
	@Override
	public void fromBytes(ByteBuf buf) {
		this.isZooming=buf.readBoolean();
		this.offHand=buf.readBoolean();
		this.entityId = buf.readInt();
	}

	@Override
	public void toBytes(ByteBuf buf) {
		buf.writeBoolean(isZooming);
		buf.writeBoolean(offHand);
		buf.writeInt(entityId);
	}

	public PacketShootGunTarget() {
	}

	public EnumHand getHand(){
		return offHand ? EnumHand.OFF_HAND : EnumHand.MAIN_HAND;
	}
	
	public PacketShootGunTarget(boolean isZooming,EnumHand hand, Entity target) {
		this.isZooming = isZooming;
		this.offHand = hand == EnumHand.OFF_HAND;
		this.entityId = target.getEntityId();
	}

	public static class Handler implements IMessageHandler<PacketShootGunTarget, IMessage> {
		@Override
		public IMessage onMessage(PacketShootGunTarget message, MessageContext ctx) {
			FMLCommonHandler.instance().getWorldThread(ctx.netHandler).addScheduledTask(() -> handle(message, ctx));
			return null;
		}

		private void handle(PacketShootGunTarget message, MessageContext ctx) {
						
			EntityPlayer ply = TGPackets.getPlayerFromContext(ctx);
			ItemStack stack = ply.getHeldItem(message.getHand());
			Entity target = ply.world.getEntityByID(message.entityId);
			if(!stack.isEmpty() && stack.getItem() instanceof IGenericGun){
				((IGenericGun) stack.getItem()).shootGunPrimary(stack, ply.world, ply, message.isZooming, message.getHand(), target);
			}
		}
	}
}
