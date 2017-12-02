package techguns.capabilities;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import net.minecraft.entity.EntityLivingBase;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import techguns.api.capabilities.AttackTime;
import techguns.api.capabilities.ITGShooterValues;
import techguns.client.particle.ITGParticle;
import techguns.client.particle.ITGParticleAttachments;
import techguns.client.particle.TGParticleSystem;
import techguns.client.particle.TGParticleSystemItemAttached;

public class TGShooterValues implements ITGShooterValues, ITGParticleAttachments {

	protected AttackTime attackTime = new AttackTime();
	
	protected List<ITGParticle> entityParticles = null;
	protected List<TGParticleSystemItemAttached> particleSysMH=null;
	protected List<TGParticleSystemItemAttached> particleSysOH=null;
	
	@Override
	public AttackTime getAttackTime(boolean offHand) {
		return attackTime;
	}
	
	public static TGShooterValues get(EntityLivingBase ent){
		return (TGShooterValues) ent.getCapability(TGShooterValuesCapProvider.TG_SHOOTER_VALUES, null);
	}

	@Override
	public boolean isRecoiling(boolean offHand) {
		return attackTime.isRecoiling();
	}

	@Override
	public boolean isReloading(boolean offHand) {
		return attackTime.isReloading();
	}
	
	@SideOnly(Side.CLIENT)
	public void tickParticles() {
		if(entityParticles!=null) {
			Iterator<ITGParticle> it = entityParticles.iterator();
			while(it.hasNext()) {
				ITGParticle p = it.next();
				
				p.updateTick();
				if(p.shouldRemove()) {
					it.remove();
				}
			}
		}
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
