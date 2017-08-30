package techguns.packets;

import io.netty.buffer.ByteBuf;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import techguns.TGConfig;
import techguns.TGPackets;
import techguns.client.ClientProxy;
import techguns.client.particle.DeathEffect;
import techguns.deatheffects.EntityDeathUtils.DeathType;

public class PacketEntityDeathType implements IMessage{
	
	int entityId;
	int deathtypeId;
	float motionX;
	float motionY;
	float motionZ;
	
	public PacketEntityDeathType() {
		
	}
	
	

	public PacketEntityDeathType(EntityLivingBase entity, DeathType deathtype) {
		super();
		this.entityId = entity.getEntityId();
		if(deathtype!=null){
			this.deathtypeId = deathtype.getValue();
		} else {
			this.deathtypeId=DeathType.DEFAULT.getValue();
		}
		this.motionX = (float) entity.motionX;
		this.motionY = (float) entity.motionY;
		this.motionZ = (float) entity.motionZ;
	}



	@Override
	public void fromBytes(ByteBuf buf) {
		this.entityId = buf.readInt();
		this.deathtypeId = buf.readInt();
		this.motionX = buf.readFloat();
		this.motionY = buf.readFloat();
		this.motionZ = buf.readFloat();
	}

	@Override
	public void toBytes(ByteBuf buf) {
		buf.writeInt(this.entityId);
		buf.writeInt(this.deathtypeId);
		buf.writeFloat(this.motionX);
		buf.writeFloat(this.motionY);
		buf.writeFloat(this.motionZ);
	}

	
	public static class Handler implements  IMessageHandler<PacketEntityDeathType, IMessage> {
		
		@Override
		public IMessage onMessage(PacketEntityDeathType message, MessageContext ctx) {
			FMLCommonHandler.instance().getWorldThread(ctx.netHandler).addScheduledTask(() -> handle(message, ctx));
			return null;
		}

		private void handle(PacketEntityDeathType message, MessageContext ctx) {
			
//			System.out.println("Get Packet!");
			
			//If deathFX are disabled, ignore packet
			if (TGConfig.cl_enableDeathFX){
			
				EntityPlayer ply = TGPackets.getPlayerFromContext(ctx);
				EntityLivingBase entity = (EntityLivingBase) ply.world.getEntityByID(message.entityId);
				DeathType deathtype = DeathType.values()[message.deathtypeId];
				
				if (deathtype!=DeathType.GORE || (deathtype==DeathType.GORE && TGConfig.cl_enableDeathFX_Gore)){
				
					if (entity !=null){
						entity.motionX = message.motionX;
						entity.motionY = message.motionY;
						entity.motionZ = message.motionZ;
						//System.out.printf("(message)EntityMotion: (%.1f/%.1f/%.1f)\n",message.motionX,message.motionY,message.motionZ);
						
						ClientProxy.get().setEntityDeathType(entity, deathtype);
						DeathEffect.createDeathEffect(entity, deathtype, message.motionX, message.motionY, message.motionZ);
					}
				}
			}
		}
		
	}

	
}
