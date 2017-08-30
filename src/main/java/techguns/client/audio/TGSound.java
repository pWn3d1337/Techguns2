package techguns.client.audio;

import net.minecraft.client.audio.MovingSound;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.Vec2f;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import techguns.util.MathUtil;

@SideOnly(Side.CLIENT)
public class TGSound extends MovingSound {

	Entity entity;

	boolean gunPosition;
	boolean moving;
	
	public TGSound(SoundEvent soundname, Entity entity, float volume, float pitch, boolean repeat, boolean moving, boolean gunPosition, TGSoundCategory category) {
		this(soundname, entity, volume, pitch, repeat, moving, category);
		this.gunPosition = gunPosition;
		if (gunPosition) {
			if (entity instanceof EntityLivingBase) {
    			Vec2f pos = MathUtil.polarOffsetXZ(xPosF, zPosF, 1.0f, (float)(((EntityLivingBase)entity).rotationYawHead*Math.PI/180.0f));
    			xPosF = pos.x;
    			zPosF = pos.y;
    			yPosF = yPosF + (entity.height*0.5f);		
    		}
		}
	}
	
	public TGSound(SoundEvent soundname, Entity entity, float volume, float pitch, boolean repeat, boolean moving, TGSoundCategory category) {
		this(soundname, entity, volume, pitch, repeat, category);
		this.moving = moving;
		if (entity != null) {
			xPosF = (float) entity.posX;
	        yPosF = (float) entity.posY;
	        zPosF = (float) entity.posZ;
		}
	}
	
	public TGSound(SoundEvent soundname, Entity entity, float volume, float pitch, boolean repeat, TGSoundCategory category) {
		super(soundname, category.getVanillaCategory());
		this.volume = volume; //TGConfig.getVolumeMultiplier(volume, category);
		this.pitch = pitch;
		this.repeat = repeat;
		this.entity = entity;
		//System.out.println("I'm a sound, lol - " + soundname.getSoundName());
	}
	
	public TGSound(SoundEvent soundname, float posx, float posy, float posz, float volume, float pitch, boolean repeat, TGSoundCategory category){
		this(soundname,null,volume,pitch,repeat, category);
		this.xPosF= posx;
		this.yPosF = posy;
		this.zPosF = posz;
	}
	
//	public TGSound(String soundname, float volume, float pitch, boolean repeat) {
//		this(soundname, null, volume, pitch, repeat);
//		this.field_147666_i = ISound.AttenuationType.NONE;
//	}
	
//	public TGSound(String soundName, float x, float y, float z, float volume, float pitch, boolean repeat) {
//		this(soundName, null, volume, pitch, repeat);
//		this.xPosF = x;
//		this.yPosF = y;
//		this.zPosF = z;
//
//	}

	public void setDonePlaying()
    {
        this.repeat = false;
        this.donePlaying = true;
        this.repeatDelay = 0;
    }

    @Override
    public void update()
    {
       //TODO: Auto-remove?
        if (moving && entity != null) {
	        xPosF = (float) entity.posX;
	        yPosF = (float) entity.posY;
	        zPosF = (float) entity.posZ;
        	if (gunPosition) {
        		if (entity instanceof EntityLivingBase) {
        			Vec2f pos = MathUtil.polarOffsetXZ(xPosF, zPosF, 1.0f, (float) (((EntityLivingBase)entity).rotationYawHead*Math.PI/180.0f));
        			xPosF = pos.x;
        			zPosF = pos.y;
        			yPosF = yPosF + (entity.height*0.5f);
        			
        		}
        		
        	}
        } else if (moving){
        	this.setDonePlaying();
        }
    }

	
	public void setPitch(float pitch) {
		this.pitch = pitch;
	}

	public void setRepeatDelay(int delay)
    {
        this.repeatDelay = delay;
    }

}
