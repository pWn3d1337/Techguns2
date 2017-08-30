package techguns.capabilities;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import techguns.deatheffects.EntityDeathUtils.DeathType;

public class TGDeathTypeCap {

	protected EntityLivingBase ent;
	protected DeathType deathType = DeathType.DEFAULT; 
	
	public TGDeathTypeCap(EntityLivingBase ent) {
		this.ent = ent;
	}

	public EntityLivingBase getEnt() {
		return ent;
	}

	public DeathType getDeathType() {
		return deathType;
	}

	public void setDeathType(DeathType deathType) {
		this.deathType = deathType;
	}

	public static TGDeathTypeCap get(EntityLivingBase elb){
		return (TGDeathTypeCap) elb.getCapability(TGDeathTypeCapProvider.TG_DEATHTYPE_CAP, null);
	}
	
}
