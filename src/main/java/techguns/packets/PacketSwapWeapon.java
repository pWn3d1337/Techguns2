package techguns.packets;

import io.netty.buffer.ByteBuf;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import techguns.TGPackets;
import techguns.capabilities.TGExtendedPlayer;

public class PacketSwapWeapon implements IMessage {
	
	int plyId;
	
	public PacketSwapWeapon() {
		super();
	}
	
	public PacketSwapWeapon(EntityPlayer ply) {
		super();
		this.plyId=ply.getEntityId();
	}

	@Override
	public void fromBytes(ByteBuf buf) {
		this.plyId = buf.readInt();
	}

	@Override
	public void toBytes(ByteBuf buf) {
		buf.writeInt(plyId);
	}
	
	public static class Handler implements IMessageHandler<PacketSwapWeapon, IMessage> {
		@Override
		public IMessage onMessage(PacketSwapWeapon message, MessageContext ctx) {
			FMLCommonHandler.instance().getWorldThread(ctx.netHandler).addScheduledTask(() -> handle(message, ctx));
			return null;
		}

		private void handle(PacketSwapWeapon m, MessageContext ctx) {
			World w = TGPackets.getPlayerFromContext(ctx).world;
			
			Entity ent = w.getEntityByID(m.plyId);
			if (ent!=null && ent instanceof EntityPlayer) {
				TGExtendedPlayer caps = TGExtendedPlayer.get((EntityPlayer) ent);
				caps.swapAttackTimes();
			}
			
		}
	}
	
}
