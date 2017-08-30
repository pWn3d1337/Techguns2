package techguns.packets;

import io.netty.buffer.ByteBuf;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import techguns.TGPackets;
import techguns.Techguns;

public class PacketOpenPlayerGUI implements IMessage {
	private short guiID;

	public PacketOpenPlayerGUI(short guiID) {
		this.guiID = guiID;
	}

	public PacketOpenPlayerGUI() {
	}

	@Override
	public void fromBytes(ByteBuf buf) {
		this.guiID=buf.readShort();
	}

	@Override
	public void toBytes(ByteBuf buf) {
		buf.writeShort(this.guiID);
	}

	public static class Handler implements  IMessageHandler<PacketOpenPlayerGUI, IMessage> {

		@Override
		public IMessage onMessage(PacketOpenPlayerGUI message, MessageContext ctx) {
			FMLCommonHandler.instance().getWorldThread(ctx.netHandler).addScheduledTask(() -> handle(message, ctx));
			return null;
		}

		private void handle(PacketOpenPlayerGUI message, MessageContext ctx) {
			EntityPlayer ply = TGPackets.getPlayerFromContext(ctx);
			if(message.guiID>=0){
				ply.openGui(Techguns.instance, message.guiID, ply.world, (int)ply.posX, (int)ply.posY, (int)ply.posZ);
			}
		}
		
	}
}