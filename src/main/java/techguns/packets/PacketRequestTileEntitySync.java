package techguns.packets;

import io.netty.buffer.ByteBuf;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import techguns.TGPackets;

public class PacketRequestTileEntitySync implements IMessage {

	protected BlockPos pos;
	
	public PacketRequestTileEntitySync(BlockPos pos) {
		super();
		this.pos = pos;
	}
	public PacketRequestTileEntitySync() {
		super();
	}

	@Override
	public void fromBytes(ByteBuf buf) {
		int x = buf.readInt();
		int y = buf.readInt();
		int z = buf.readInt();
		this.pos=new BlockPos(x, y, z);
	}

	@Override
	public void toBytes(ByteBuf buf) {
		buf.writeInt(pos.getX());
		buf.writeInt(pos.getY());
		buf.writeInt(pos.getZ());
	}
	
	public static class Handler extends HandlerTemplate<PacketRequestTileEntitySync>{

		@Override
		protected void handle(PacketRequestTileEntitySync message, MessageContext ctx) {
			EntityPlayer ply = TGPackets.getPlayerFromContext(ctx);
			TileEntity tile = ply.world.getTileEntity(message.pos);
			
			if(tile!=null && ply instanceof EntityPlayerMP) {
				EntityPlayerMP player = (EntityPlayerMP) ply;
				player.connection.sendPacket(tile.getUpdatePacket());
			}			
		}
	}
}
