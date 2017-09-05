package techguns.packets;

import io.netty.buffer.ByteBuf;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import techguns.TGPackets;
import techguns.util.TextUtil;

public class PacketMultiBlockFormInvalidBlockMessage implements IMessage {
	protected BlockPos pos;
	
	public PacketMultiBlockFormInvalidBlockMessage(BlockPos pos) {
		super();
		this.pos = pos;
	}

	public PacketMultiBlockFormInvalidBlockMessage() {
		super();
	}

	@Override
	public void fromBytes(ByteBuf buf) {
		this.pos=new BlockPos(buf.readInt(),buf.readInt(),buf.readInt());
	}

	@Override
	public void toBytes(ByteBuf buf) {
		buf.writeInt(pos.getX());
		buf.writeInt(pos.getY());
		buf.writeInt(pos.getZ());
	}

	public static class Handler extends HandlerTemplate<PacketMultiBlockFormInvalidBlockMessage>{

		@Override
		protected void handle(PacketMultiBlockFormInvalidBlockMessage message, MessageContext ctx) {
			EntityPlayer player = TGPackets.getPlayerFromContext(ctx);
			BlockPos p = message.pos;
			player.sendStatusMessage(new TextComponentString(TextUtil.trans("techguns.multiblock.invalidblock")+": "+p.getX()+ ", "+p.getY()+", "+p.getZ()), false);
			
		}
		
	}
	
}
