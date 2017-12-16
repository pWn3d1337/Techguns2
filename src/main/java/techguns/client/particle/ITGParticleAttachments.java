package techguns.client.particle;

import java.util.Iterator;
import java.util.List;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.EnumHand;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

/**
 * Implemented by capabilities that store entityAttachedParticle systems
 *
 */
@SideOnly(Side.CLIENT)
public interface ITGParticleAttachments {
	
	@SideOnly(Side.CLIENT)
	public default void tickParticles() {
		if(this.getEntityParticles()!=null) {
			Iterator<ITGParticle> it = getEntityParticles().iterator();
			while(it.hasNext()) {
				ITGParticle p = it.next();
				
				p.updateTick();
				if(p.shouldRemove()) {
					it.remove();
				}
			}
		}
		
		if(this.getEntityParticlesMH()!=null) {
			Iterator<ITGParticle> it = getEntityParticlesMH().iterator();
			while(it.hasNext()) {
				ITGParticle p = it.next();
				
				p.updateTick();
				if(p.shouldRemove()) {
					it.remove();
				}
			}
		}
		
		if(this.getEntityParticlesOH()!=null) {
			Iterator<ITGParticle> it = getEntityParticlesOH().iterator();
			while(it.hasNext()) {
				ITGParticle p = it.next();
				
				p.updateTick();
				if(p.shouldRemove()) {
					it.remove();
				}
			}
		}
		
		if(this.getParticleSysMainhand()!=null) {
			Iterator<TGParticleSystem> it = getParticleSysMainhand().iterator();
			while(it.hasNext()) {
				TGParticleSystem p = it.next();
				
				p.updateTick();
				if(p.shouldRemove()) {
					it.remove();
				}
			}
		}
		if(this.getParticleSysOffhand()!=null) {
			Iterator<TGParticleSystem> it = getParticleSysOffhand().iterator();
			while(it.hasNext()) {
				TGParticleSystem p = it.next();
				
				p.updateTick();
				if(p.shouldRemove()) {
					it.remove();
				}
			}
		}
	}
	
	@SideOnly(Side.CLIENT)
	public default void clearAttachedSystemsHand(EnumHand hand) {
		if(hand==EnumHand.MAIN_HAND) {
			if (this.getParticleSysMainhand()!=null) {
				this.getParticleSysMainhand().clear();
			}
		} else {
			if (this.getParticleSysOffhand()!=null) {
				this.getParticleSysOffhand().clear();
			}
		}
	}
	
	@SideOnly(Side.CLIENT)
	public default void addSystemsHand(EnumHand hand, List<TGParticleSystem> systems) {
		
		if(hand==EnumHand.MAIN_HAND) {
			this.getOrInitParticleSysMainhand().clear();
			this.getParticleSysMainhand().addAll(systems);
		} else {
			this.getOrInitParticleSysOffhand().clear();
			this.getParticleSysOffhand().addAll(systems);
		}
	}
	
	@SideOnly(Side.CLIENT)
	public default void addEffectHand(EnumHand hand, List<ITGParticle> effects) {
		
		if(hand==EnumHand.MAIN_HAND) {
			this.getOrInitEntityParticlesMH().addAll(effects);
		} else {
			this.getOrInitEntityParticlesOH().addAll(effects);
		}
	}
	
	@SideOnly(Side.CLIENT)
	public List<ITGParticle> getEntityParticles();
	
	@SideOnly(Side.CLIENT)
	public List<ITGParticle> getEntityParticlesMH();
	
	@SideOnly(Side.CLIENT)
	public List<ITGParticle> getEntityParticlesOH();
	
	@SideOnly(Side.CLIENT)
	public List<TGParticleSystem> getParticleSysMainhand();
	
	@SideOnly(Side.CLIENT)
	public List<TGParticleSystem> getParticleSysOffhand();
	
	@SideOnly(Side.CLIENT)
	public List<ITGParticle> getOrInitEntityParticles();
	
	@SideOnly(Side.CLIENT)
	public List<ITGParticle> getOrInitEntityParticlesOH();
	
	@SideOnly(Side.CLIENT)
	public List<ITGParticle> getOrInitEntityParticlesMH();
	
	@SideOnly(Side.CLIENT)
	public List<TGParticleSystem> getOrInitParticleSysMainhand();
	
	@SideOnly(Side.CLIENT)
	public List<TGParticleSystem> getOrInitParticleSysOffhand();

}
