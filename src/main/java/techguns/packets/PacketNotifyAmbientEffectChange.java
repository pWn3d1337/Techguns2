package techguns.packets;

import io.netty.buffer.ByteBuf;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.EnumHand;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;

public class PacketNotifyAmbientEffectChange implements IMessage {
	int entityId;
	EnumHand hand;
	
	public PacketNotifyAmbientEffectChange() {}
	
	public PacketNotifyAmbientEffectChange(EntityLivingBase entity, EnumHand hand) {
		super();
		this.entityId = entity.getEntityId();
		this.hand = hand;
	}

	@Override
	public void fromBytes(ByteBuf buf) {
		boolean offhand = buf.readBoolean();
		if(offhand) {
			this.hand=EnumHand.OFF_HAND;
		} else {
			this.hand=EnumHand.MAIN_HAND;
		}
		this.entityId=buf.readInt();
	}
	
	@Override
	public void toBytes(ByteBuf buf) {
		buf.writeBoolean(hand==EnumHand.OFF_HAND);
		buf.writeInt(entityId);
	}
	
}
