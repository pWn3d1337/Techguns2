package techguns.packets;

import io.netty.buffer.ByteBuf;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import techguns.TGPackets;
import techguns.tileentities.BasicInventoryTileEnt;

public class PacketGuiButtonClick implements IMessage {
	private int x;
	private int y;
	private int z;
	private int buttonId;

    public PacketGuiButtonClick() 
    { 
     // need this constructor
    }

    public PacketGuiButtonClick(BasicInventoryTileEnt tileEnt,int buttonId) 
    {
    	this.x = tileEnt.getPos().getX();
    	this.y = tileEnt.getPos().getY();
    	this.z = tileEnt.getPos().getZ();
    	this.buttonId = buttonId;
    }
	
	@Override
	public void fromBytes(ByteBuf buf) {
		this.x=buf.readInt();
		this.y=buf.readInt();
		this.z=buf.readInt();
		this.buttonId=buf.readInt();
	}

	@Override
	public void toBytes(ByteBuf buf) {
		buf.writeInt(x);
		buf.writeInt(y);
		buf.writeInt(z);
		buf.writeInt(buttonId);
	}

	public static class Handler extends HandlerTemplate<PacketGuiButtonClick> {
		
		@Override
		protected void handle(PacketGuiButtonClick message, MessageContext ctx) {
			EntityPlayer ply = TGPackets.getPlayerFromContext(ctx);
			
			TileEntity tile = ply.world.getTileEntity(new BlockPos(message.x, message.y, message.z));
			
			if(tile != null){
				if (tile instanceof BasicInventoryTileEnt){
					BasicInventoryTileEnt tileent = (BasicInventoryTileEnt) tile;
					if (tileent.isUseableByPlayer(ply)) {
						tileent.buttonClicked(message.buttonId, ply);
					}
				}
			}
		}
		
	}
}