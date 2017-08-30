package techguns.packets;

import io.netty.buffer.ByteBuf;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumHand;
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
public class PacketShootGun implements IMessage {
	public boolean isZooming=false;
	public boolean offHand=false;
	
	@Override
	public void fromBytes(ByteBuf buf) {
		this.isZooming=buf.readBoolean();
		this.offHand=buf.readBoolean();
	}

	@Override
	public void toBytes(ByteBuf buf) {
		buf.writeBoolean(isZooming);
		buf.writeBoolean(offHand);
	}

	public PacketShootGun() {
	}

	public EnumHand getHand(){
		return offHand ? EnumHand.OFF_HAND : EnumHand.MAIN_HAND;
	}
	
	public PacketShootGun(boolean isZooming,EnumHand hand) {
		this.isZooming = isZooming;
		this.offHand = hand == EnumHand.OFF_HAND;
	}

	public static class Handler implements IMessageHandler<PacketShootGun, IMessage> {
		@Override
		public IMessage onMessage(PacketShootGun message, MessageContext ctx) {
			FMLCommonHandler.instance().getWorldThread(ctx.netHandler).addScheduledTask(() -> handle(message, ctx));
			return null;
		}

		private void handle(PacketShootGun message, MessageContext ctx) {
						
			EntityPlayer ply = TGPackets.getPlayerFromContext(ctx);
			ItemStack stack = ply.getHeldItem(message.getHand());
			if(!stack.isEmpty() && stack.getItem() instanceof IGenericGun){
				((IGenericGun) stack.getItem()).shootGunPrimary(stack, ply.world, ply, message.isZooming, message.getHand());
			}
		}
	}
}
