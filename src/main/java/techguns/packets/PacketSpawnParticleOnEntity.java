package techguns.packets;

import java.nio.charset.StandardCharsets;

import io.netty.buffer.ByteBuf;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.network.ByteBufUtils;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import techguns.TGPackets;
import techguns.client.ClientProxy;
import techguns.util.TGLogger;

public class PacketSpawnParticleOnEntity implements IMessage {

	String name;
	int entityID;

	
	public PacketSpawnParticleOnEntity() {
		super();
	}	
	
	public PacketSpawnParticleOnEntity(String name, Entity ent) {
		super();
		this.name=name;
		this.entityID=ent.getEntityId();
	}


	@Override
	public void fromBytes(ByteBuf buf) {
		int len = buf.readShort();
		this.name = buf.readCharSequence(len, StandardCharsets.UTF_8).toString();
		
		this.entityID=buf.readInt();
	}

	@Override
	public void toBytes(ByteBuf buf) {
		CharSequence cs = this.name;
		buf.writeShort(cs.length());
		buf.writeCharSequence(name, StandardCharsets.UTF_8);
		
		buf.writeInt(entityID);
	}
	
	public static class Handler implements IMessageHandler<PacketSpawnParticleOnEntity, IMessage> {
		@Override
		public IMessage onMessage(PacketSpawnParticleOnEntity message, MessageContext ctx) {
			FMLCommonHandler.instance().getWorldThread(ctx.netHandler).addScheduledTask(() -> handle(message, ctx));
			return null;
		}

		private void handle(PacketSpawnParticleOnEntity m, MessageContext ctx) {
			Entity ent = Minecraft.getMinecraft().player.world.getEntityByID(m.entityID);
			if (ent!=null){
				ClientProxy.get().createFXOnEntity(m.name, ent);
			} else {
				TGLogger.logger_client.warning("Got Packet for FX "+m.name+" on Entity, but ent was null");
			}
		}
	}
}
