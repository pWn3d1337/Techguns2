package techguns.packets;

import io.netty.buffer.ByteBuf;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import techguns.TGPackets;
import techguns.tileentities.Door3x3TileEntity;

public class PacketDoorStateChange implements IMessage {

	protected int dim;
	protected int x;
	protected int y;
	protected int z;
	
	public PacketDoorStateChange() {
	}
	
	public PacketDoorStateChange(TileEntity tile) {
		this.x=tile.getPos().getX();
		this.y=tile.getPos().getY();
		this.z=tile.getPos().getZ();
		this.dim = tile.getWorld().provider.getDimension();
	}
	
	@Override
	public void fromBytes(ByteBuf buf) {
		x=buf.readInt();
		y=buf.readInt();
		z=buf.readInt();
		dim=buf.readInt();
	}

	@Override
	public void toBytes(ByteBuf buf) {
		buf.writeInt(x);
		buf.writeInt(y);
		buf.writeInt(z);
		buf.writeInt(dim);
	}

	
	public static class Handler extends HandlerTemplate<PacketDoorStateChange> {

		@Override
		protected void handle(PacketDoorStateChange message, MessageContext ctx) {
			EntityPlayer ply = TGPackets.getPlayerFromContext(ctx);
			World w = ply.world;
			
			if(w.provider.getDimension()==message.dim) {
				TileEntity tile = w.getTileEntity(new BlockPos(message.x,message.y,message.z));
				
				if(tile!=null && tile instanceof Door3x3TileEntity) {
					Door3x3TileEntity door = (Door3x3TileEntity) tile;
					door.setLastStateChangeTime(System.currentTimeMillis());
				}
				
			}
			
		}
		
	}
}
