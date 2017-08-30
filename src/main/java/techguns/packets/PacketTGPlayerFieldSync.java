package techguns.packets;

import io.netty.buffer.ByteBuf;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import techguns.capabilities.TGExtendedPlayer;

/**
 * 
 * Used to sync a single variable of playerproperties to a player
 *
 */
public class PacketTGPlayerFieldSync implements IMessage {
	public int entityID;;
	public short id;
	public Object value;
	
	public static final short FIELD_JUMPBUTTONPRESSED=0;
	public static final short FIELD_ENABLEJETPACK=1;
	
	
	public PacketTGPlayerFieldSync() {
		super();
	}

	/**
	 * 
	 * @param ent - the Entity to which the value belongs
	 * @param id - the field ID, mappings: 0->JumpButtonPressed (Jetpack) - boolean
	 * 									   1->enableJetpack - boolean
	 * 				use the constants in this class for field id
	 * @param value - the value the field should be set, type depends on id
	 */
	public PacketTGPlayerFieldSync(EntityLivingBase ent,short id, Object value) {
		super();
		this.entityID = ent.getEntityId();
		this.id = id;
		this.value = value;
	}

	@Override
	public void fromBytes(ByteBuf buf) {
		entityID=buf.readInt();
		id=buf.readShort();
		switch(id){
			case 0:
			case 1:
				value=buf.readBoolean();
				break;
		}
		
	}

	@Override
	public void toBytes(ByteBuf buf) {
		buf.writeInt(entityID);
		buf.writeShort(id);
		switch(id){
			case 0:
			case 1:
				buf.writeBoolean((boolean) value);
				break;
		}
	}

	public static class Handler implements  IMessageHandler<PacketTGPlayerFieldSync, IMessage> {

		@Override
		public IMessage onMessage(PacketTGPlayerFieldSync message, MessageContext ctx) {
			FMLCommonHandler.instance().getWorldThread(ctx.netHandler).addScheduledTask(() -> handle(message, ctx));
			return null;
		}
		
		private void handle(PacketTGPlayerFieldSync message, MessageContext ctx) {
			Entity entity = Minecraft.getMinecraft().world.getEntityByID(message.entityID);			
			
			if(entity!=null){
				
				
				if(message.id==FIELD_JUMPBUTTONPRESSED){
					if(entity instanceof EntityPlayer){
						EntityPlayer ply = (EntityPlayer) entity;
						
						if(ply!=Minecraft.getMinecraft().player){
						
							TGExtendedPlayer props= TGExtendedPlayer.get(ply);
							if (props!=null){
								
								boolean state = (boolean) message.value;
								props.setJumpkeyPressed(state);
								
								
							}
						}
					}
				} else if (message.id == FIELD_ENABLEJETPACK){

					if(entity instanceof EntityPlayer){
						EntityPlayer ply = (EntityPlayer) entity;

							TGExtendedPlayer props= TGExtendedPlayer.get(ply);
							if (props!=null){
								
								boolean state = (boolean) message.value;
								props.enableJetpack = state;
								
							}
					}
					
				}
			}
		}
		
	}
	
}
