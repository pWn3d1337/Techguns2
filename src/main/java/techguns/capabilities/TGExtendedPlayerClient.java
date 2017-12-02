package techguns.capabilities;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import techguns.TGSounds;
import techguns.client.audio.TGSound;
import techguns.client.audio.TGSoundCategory;
import techguns.client.particle.ITGParticle;
import techguns.client.particle.ITGParticleAttachments;
import techguns.client.particle.TGParticleSystemItemAttached;
import techguns.util.SoundUtil;

public class TGExtendedPlayerClient extends TGExtendedPlayer implements ITGParticleAttachments {
	
	// Client only fields
	public TGSound gliderLoop=null;

	public TGSound jetPackLoop=null;
	
	protected List<ITGParticle> entityParticles = null;
	protected List<TGParticleSystemItemAttached> particleSysMH=null;
	protected List<TGParticleSystemItemAttached> particleSysOH=null;
	
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

	@SideOnly(Side.CLIENT)
	@Override
	public List<ITGParticle> getEntityParticles() {
		return this.entityParticles;
	}

	@SideOnly(Side.CLIENT)
	@Override
	public List<TGParticleSystemItemAttached> getParticleSysMainhand() {
		return this.particleSysMH;
	}

	@SideOnly(Side.CLIENT)
	@Override
	public List<TGParticleSystemItemAttached> getParticleSysOffhand() {
		return this.particleSysOH;
	}

	@SideOnly(Side.CLIENT)
	@Override
	public List<ITGParticle> getOrInitEntityParticles() {
		if (this.entityParticles==null) this.entityParticles = new LinkedList<ITGParticle>();
		return this.entityParticles;
	}

	@SideOnly(Side.CLIENT)
	@Override
	public List<TGParticleSystemItemAttached> getOrInitParticleSysMainhand() {
		if(this.particleSysMH==null) this.particleSysMH = new LinkedList<TGParticleSystemItemAttached>();
		return this.particleSysMH;
	}

	@SideOnly(Side.CLIENT)
	@Override
	public List<TGParticleSystemItemAttached> getOrInitParticleSysOffhand() {
		if(this.particleSysOH==null) this.particleSysOH = new LinkedList<TGParticleSystemItemAttached>();
		return this.particleSysOH;
	}
	
}
