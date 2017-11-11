package techguns.packets;

import java.nio.charset.StandardCharsets;

import io.netty.buffer.ByteBuf;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import techguns.TGPackets;
import techguns.client.ClientProxy;

public class PacketSpawnParticle implements IMessage {

	String name;
	
	double posX;
	double posY;
	double posZ;
	
	double motionX=0.0D;
	double motionY=0.0D;
	double motionZ=0.0D;
	
	float scale = 1.0f;
	
	public PacketSpawnParticle() {
		super();
	}	
	
	public PacketSpawnParticle(String name, double posX, double posY, double posZ) {
		super();
		this.name=name;
		this.posX = posX;
		this.posY = posY;
		this.posZ = posZ;
	}
	
	public PacketSpawnParticle(String name, double posX, double posY, double posZ, float scale) {
		this(name, posX, posY, posZ);
		this.scale = scale;
	}

	public PacketSpawnParticle(String name, double posX, double posY, double posZ, double motionX, double motionY, double motionZ) {
		super();
		this.name=name;
		this.posX = posX;
		this.posY = posY;
		this.posZ = posZ;
		this.motionX = motionX;
		this.motionY = motionY;
		this.motionZ = motionZ;
	}
	
	public PacketSpawnParticle(String name, double posX, double posY, double posZ, double motionX, double motionY, double motionZ, float scale) {
		this(name, posX, posY, posZ, motionX, motionY, motionZ);
		this.scale = scale;
	}

	@Override
	public void fromBytes(ByteBuf buf) {
		int len = buf.readShort();
		this.name =  buf.readCharSequence(len, StandardCharsets.UTF_8).toString();
		
		this.posX=buf.readDouble();
		this.posY=buf.readDouble();
		this.posZ=buf.readDouble();
		
		this.motionX=buf.readDouble();
		this.motionY=buf.readDouble();
		this.motionZ=buf.readDouble();
		
		this.scale = buf.readFloat();
	}

	@Override
	public void toBytes(ByteBuf buf) {
		CharSequence cs = this.name;
		buf.writeShort(cs.length());
		buf.writeCharSequence(name, StandardCharsets.UTF_8);
		
		buf.writeDouble(posX);
		buf.writeDouble(posY);
		buf.writeDouble(posZ);
		
		buf.writeDouble(motionX);
		buf.writeDouble(motionY);
		buf.writeDouble(motionZ);
		
		buf.writeFloat(scale);
	}
	
	public static class Handler implements IMessageHandler<PacketSpawnParticle, IMessage> {
		@Override
		public IMessage onMessage(PacketSpawnParticle message, MessageContext ctx) {
			FMLCommonHandler.instance().getWorldThread(ctx.netHandler).addScheduledTask(() -> handle(message, ctx));
			return null;
		}

		private void handle(PacketSpawnParticle m, MessageContext ctx) {
			ClientProxy.get().createFX(m.name, TGPackets.getPlayerFromContext(ctx).world, m.posX, m.posY, m.posZ, m.motionX, m.motionY, m.motionZ, m.scale);
		}
	}

}
