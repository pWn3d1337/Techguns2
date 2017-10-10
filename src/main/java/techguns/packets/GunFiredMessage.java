package techguns.packets;

import io.netty.buffer.ByteBuf;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import techguns.TGPackets;
import techguns.client.ClientProxy;
import techguns.client.ShooterValues;

public class GunFiredMessage implements IMessage{

	protected int entityID;
	protected int time;
	protected byte attackType;
	protected boolean checkRecoil;
	protected boolean leftGun;
	protected int muzzleflashtime;

	
    public GunFiredMessage() 
    { 
     // need this constructor
    }
    
    public GunFiredMessage(EntityLivingBase shooter, int firetime, int muzzleflashtime, int attackType, boolean checkRecoil, boolean leftGun) 
    { 
    	this.entityID = shooter.getEntityId();
    	this.time = firetime;
    	this.muzzleflashtime=muzzleflashtime;
    	this.attackType=(byte)attackType;
    	this.checkRecoil=checkRecoil;
    	this.leftGun=leftGun;
    }
	
	@Override
	public void fromBytes(ByteBuf buf) {
		entityID=buf.readInt();
		time=buf.readInt();
		muzzleflashtime=buf.readInt();
		attackType=buf.readByte();
		checkRecoil=buf.readBoolean();
		leftGun=buf.readBoolean();
	}

	@Override
	public void toBytes(ByteBuf buf) {
		buf.writeInt(entityID);
		buf.writeInt(time);
		buf.writeInt(muzzleflashtime);
		buf.writeByte(attackType);
		buf.writeBoolean(checkRecoil);
		buf.writeBoolean(leftGun);
	}
	
	public static class Handler implements IMessageHandler<GunFiredMessage, IMessage> {
		@Override
		public IMessage onMessage(GunFiredMessage message, MessageContext ctx) {
			FMLCommonHandler.instance().getWorldThread(ctx.netHandler).addScheduledTask(() -> handle(message, ctx));
			return null;
		}

		private void handle(GunFiredMessage message, MessageContext ctx) {
			
			EntityPlayer ply = TGPackets.getPlayerFromContext(ctx);
			EntityLivingBase shooter = (EntityLivingBase) ply.world.getEntityByID(message.entityID);
			
			boolean offHand = message.leftGun;
			
			if (shooter !=null){
				ClientProxy cp = ClientProxy.get();
				
					if (!message.checkRecoil || !ShooterValues.isStillRecoiling(shooter, message.leftGun,message.attackType)){
						ShooterValues.setRecoiltime(shooter, message.leftGun, System.currentTimeMillis()+message.time, message.time,message.attackType);
						ShooterValues.setMuzzleFlashTime(shooter, message.leftGun, System.currentTimeMillis()+message.muzzleflashtime, message.muzzleflashtime);	
						//System.out.println("Set recoilTime for "+shooter);
						//cp.spawnMuzzleFXForEntity(shooter,message.leftGun);
					}
			}		
			
		}
	}

}
