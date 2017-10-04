package techguns.packets;

import io.netty.buffer.ByteBuf;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import techguns.TGPackets;
import techguns.Techguns;
import techguns.client.audio.TGSoundCategory;

public class PacketPlaySound implements IMessage {

	String soundname;
	int entityId;
	float volume;
	float pitch;
	boolean repeat;
	boolean moving;
	boolean gunPosition = false;
	boolean playOnOwnPlayer=false;
	int soundx=0;
	short soundy=0;
	int soundz=0;
	TGSoundCategory category;
	
	public PacketPlaySound() {
		
	}

	public PacketPlaySound(SoundEvent soundname, Entity entity, float volume, float pitch, boolean repeat, boolean moving, boolean gunPosition, boolean playOnOwnPlayer, TGSoundCategory category) {
		this(soundname, entity, volume, pitch, repeat, moving, gunPosition, category);
		this.playOnOwnPlayer=playOnOwnPlayer;
	}
	
	public PacketPlaySound(SoundEvent soundname, Entity entity, float volume, float pitch, boolean repeat, boolean moving, boolean gunPosition, TGSoundCategory category) {
		if (entity == null) {
			this.entityId = -1;
		} else {
			this.entityId = entity.getEntityId();
		}
		
		this.soundname = soundname.getRegistryName().toString();
		this.volume = volume;
		this.pitch = pitch;
		this.repeat = repeat;
		this.moving = moving;
		this.gunPosition = gunPosition;
		//this.playOnOwnPlayer=false;
		this.category=category;
	}
	
	public PacketPlaySound(SoundEvent soundname, Entity entity, float volume, float pitch, boolean repeat, boolean moving, TGSoundCategory category) {
		if (entity == null) {
			this.entityId = -1;
		} else {
			this.entityId = entity.getEntityId();
		}
		this.soundname = soundname.getRegistryName().toString();
		this.volume = volume;
		this.pitch = pitch;
		this.repeat = repeat;
		this.moving = moving;
		this.category=category;
	}
	
	public PacketPlaySound(SoundEvent soundname, int posx, int posy, int posz, float volume, float pitch, boolean repeat, TGSoundCategory category){
		this(soundname,null, volume, pitch, repeat, false, false, category);
		this.soundx = posx;
		this.soundy = (short) posy;
		this.soundz = posz;
	}
	
	
	@Override
	public void fromBytes(ByteBuf buf) {
		//this.soundname = buf.read
		this.entityId = buf.readInt();
		this.volume = buf.readFloat();
		this.pitch = buf.readFloat();
		this.repeat = buf.readBoolean();
		this.moving = buf.readBoolean();
		this.gunPosition = buf.readBoolean();
		this.playOnOwnPlayer=buf.readBoolean();
		
		this.soundx = buf.readInt();
		this.soundy = buf.readShort();
		this.soundz = buf.readInt();
		
		this.category=TGSoundCategory.get(buf.readByte());
		byte[] bytes = new byte[buf.readableBytes()];
		buf.readBytes(bytes);
		soundname = new String(bytes);
	}

	@Override
	public void toBytes(ByteBuf buf) {
		buf.writeInt(entityId);
		buf.writeFloat(volume);
		buf.writeFloat(pitch);
		buf.writeBoolean(repeat);
		buf.writeBoolean(moving);
		buf.writeBoolean(gunPosition);
		buf.writeBoolean(playOnOwnPlayer);
		
		buf.writeInt(soundx);
		buf.writeShort(soundy);
		buf.writeInt(soundz);
		
		buf.writeByte(category.getId());
		buf.writeBytes(soundname.getBytes());
	}
	
	public static class Handler extends HandlerTemplate<PacketPlaySound> {
		
		@Override
		protected void handle(PacketPlaySound msg, MessageContext ctx) {
						
			EntityPlayer ply = TGPackets.getPlayerFromContext(ctx);
			
			SoundEvent event = SoundEvent.REGISTRY.getObject(new ResourceLocation(msg.soundname));
			
			if (msg.entityId!=-1){
				Techguns.proxy.handleSoundEvent(ply, msg.entityId, event, msg.volume, msg.pitch, msg.repeat, msg.moving, msg.gunPosition, msg.playOnOwnPlayer, msg.category);
			//	System.out.println("Received sound Event:"+msg.soundname+" play for own:"+msg.playOnOwnPlayer);
			} else {
				Techguns.proxy.playSoundOnPosition(event,msg.soundx, msg.soundy, msg.soundz, msg.volume, msg.pitch, msg.repeat, msg.category);
			}
			
		}
	}

}
