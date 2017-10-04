package techguns.packets;

import io.netty.buffer.ByteBuf;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import techguns.TGPackets;
import techguns.capabilities.TGExtendedPlayer;

public class PacketRequestTGPlayerSync implements IMessage {
	public int entityID;
	
	public PacketRequestTGPlayerSync() {
		super();
	}

	public PacketRequestTGPlayerSync(EntityPlayer ply) {
		super();
		this.entityID = ply.getEntityId();
	}

	@Override
	public void fromBytes(ByteBuf buf) {
		entityID= buf.readInt();
	}

	@Override
	public void toBytes(ByteBuf buf) {
		buf.writeInt(entityID);
	}
	
	public static class Handler extends HandlerTemplate<PacketRequestTGPlayerSync>{

		@Override
		protected void handle(PacketRequestTGPlayerSync message, MessageContext ctx) {
			
			EntityPlayer sender = TGPackets.getPlayerFromContext(ctx);
			Entity ent = sender.world.getEntityByID(message.entityID);
			if(ent!=null && ent instanceof EntityPlayer){
				EntityPlayer tracked = (EntityPlayer) ent;
				TGExtendedPlayer props = TGExtendedPlayer.get(tracked);
				if(props!=null) {
					TGPackets.network.sendTo(new PacketTGExtendedPlayerSync(tracked, props, false), (EntityPlayerMP) sender);
				}
				
			}
		}
		
	}

}