package techguns.packets;

import io.netty.buffer.ByteBuf;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextComponentString;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import techguns.TGPackets;
import techguns.util.TextUtil;

public class PacketMultiBlockFormInvalidBlockMessage implements IMessage {
	
	public static final int MSG_TYPE_ORE_DRILL_FRAME_ERROR = 2;
	public static final int MSG_TYPE_ORE_DRILL_SCAFFOLD_ERROR = 3;
	public static final int MSG_TYPE_ORE_DRILL_ENGINE_ERROR = 1;
	public static final int MSG_TYPE_ORE_DRILL_AIR_ERROR = 4;
	
	protected BlockPos pos;
	protected short type;
	
	public PacketMultiBlockFormInvalidBlockMessage(BlockPos pos, int type) {
		super();
		this.pos = pos;
		this.type = (short) type;
	}

	public PacketMultiBlockFormInvalidBlockMessage() {
		super();
	}

	@Override
	public void fromBytes(ByteBuf buf) {
		this.pos=new BlockPos(buf.readInt(),buf.readInt(),buf.readInt());
		this.type=buf.readShort();
	}

	@Override
	public void toBytes(ByteBuf buf) {
		buf.writeInt(pos.getX());
		buf.writeInt(pos.getY());
		buf.writeInt(pos.getZ());
		buf.writeShort(type);
	}

	public static class Handler extends HandlerTemplate<PacketMultiBlockFormInvalidBlockMessage>{

		@Override
		protected void handle(PacketMultiBlockFormInvalidBlockMessage message, MessageContext ctx) {
			EntityPlayer player = TGPackets.getPlayerFromContext(ctx);
			BlockPos p = message.pos;
			switch(message.type) {
			case MSG_TYPE_ORE_DRILL_ENGINE_ERROR:
				player.sendStatusMessage(new TextComponentString(TextUtil.trans("techguns.multiblock.oredrill.invalid.engines")), false);
				break;
			case MSG_TYPE_ORE_DRILL_FRAME_ERROR:
				player.sendStatusMessage(new TextComponentString(TextUtil.trans("techguns.multiblock.oredrill.invalid.frame")), false);
				break;
			case MSG_TYPE_ORE_DRILL_SCAFFOLD_ERROR:
				player.sendStatusMessage(new TextComponentString(TextUtil.trans("techguns.multiblock.oredrill.invalid.scaffold")), false);
				break;
			case MSG_TYPE_ORE_DRILL_AIR_ERROR:
				player.sendStatusMessage(new TextComponentString(TextUtil.trans("techguns.multiblock.oredrill.invalid.air")), false);
				break;
			default:
				player.sendStatusMessage(new TextComponentString(TextUtil.trans("techguns.multiblock.invalidblock")+": "+p.getX()+ ", "+p.getY()+", "+p.getZ()), false);
			}
			
		}
		
	}
	
}
