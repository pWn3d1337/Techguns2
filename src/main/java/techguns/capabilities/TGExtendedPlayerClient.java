package techguns.capabilities;

import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import techguns.TGSounds;
import techguns.client.audio.TGSound;
import techguns.client.audio.TGSoundCategory;
import techguns.util.SoundUtil;

public class TGExtendedPlayerClient extends TGExtendedPlayer {
	
	// Client only fields
	public TGSound gliderLoop=null;

	public TGSound jetPackLoop=null;
	
	
	public TGExtendedPlayerClient(EntityPlayer entity) {
		super(entity);
	}

	public static TGExtendedPlayerClient get(EntityPlayer ply){
		return (TGExtendedPlayerClient) ply.getCapability(TGExtendedPlayerCapProvider.TG_EXTENDED_PLAYER, null);
	}

	@Override
	public void copyFrom(TGExtendedPlayer other) {
		super.copyFrom(other);
	//	if(other instanceof TGExtendedPlayerClient) {
			TGExtendedPlayerClient other_cl = (TGExtendedPlayerClient) other;
			
			this.gliderLoop=other_cl.gliderLoop;
			
			this.jetPackLoop=other_cl.jetPackLoop;
	//	}
	}

	@Override
	public void setJumpkeyPressed(boolean isJumpkeyPressed) {
		
		if(this.entity.world.isRemote){
			if(isJumpkeyPressed==true && jetPackLoop==null){
				jetPackLoop = new TGSound(TGSounds.JETPACK_LOOP, this.entity, 1.0f, 1.0f, true, true, false, TGSoundCategory.PLAYER_EFFECT);
				SoundUtil.playSoundAtEntityPos(this.entity.world, this.entity, TGSounds.JETPACK_START, 1.0f, 1.0f, false, TGSoundCategory.PLAYER_EFFECT);
				Minecraft.getMinecraft().getSoundHandler().playSound(jetPackLoop);
			}
			
			if(isJumpkeyPressed && !this.isJumpkeyPressed){
				//Minecraft.getMinecraft().getSoundHandler().playSound(jetPackLoop);
			} else if ( !isJumpkeyPressed && this.isJumpkeyPressed){
				if(jetPackLoop!=null){
					jetPackLoop.setDonePlaying();
					jetPackLoop=null;
					SoundUtil.playSoundAtEntityPos(this.entity.world, this.entity, TGSounds.JETPACK_END, 1.0f, 1.0f, false, TGSoundCategory.PLAYER_EFFECT);
				}
			}
			
			
		}
		
		this.isJumpkeyPressed = isJumpkeyPressed;
	}
	
	
	
}
