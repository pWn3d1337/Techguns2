package techguns.packets;

import io.netty.buffer.ByteBuf;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumHand;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import techguns.TGPackets;
import techguns.api.render.IItemRenderer;
import techguns.client.render.ItemRenderHack;
import techguns.client.render.item.RenderItemBase;
import techguns.items.guns.GenericGun;

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
	
	public static class Handler extends HandlerTemplate<PacketNotifyAmbientEffectChange> {

		@Override
		protected void handle(PacketNotifyAmbientEffectChange message, MessageContext ctx) {
			Entity ent = TGPackets.getPlayerFromContext(ctx).world.getEntityByID(message.entityId);
			if(ent!=null && ent instanceof EntityLivingBase) {
				EntityLivingBase elb = (EntityLivingBase) ent;
				ItemStack stack = elb.getHeldItem(message.hand);
				
				if(!stack.isEmpty() && stack.getItem() instanceof GenericGun) {
					IItemRenderer renderer = ItemRenderHack.getRendererForItem(stack.getItem());
					if(renderer!=null && renderer instanceof RenderItemBase) {
						RenderItemBase itemRenderer = (RenderItemBase) renderer;
						
						
						
					}
				}
				
			}
		}
		
	}
}
