package techguns.client.particle;

import java.util.Iterator;
import java.util.List;

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
		if(this.getParticleSysMainhand()!=null) {
			Iterator<TGParticleSystemItemAttached> it = getParticleSysMainhand().iterator();
			while(it.hasNext()) {
				TGParticleSystemItemAttached p = it.next();
				
				p.updateTick();
				if(p.shouldRemove()) {
					it.remove();
				}
			}
		}
		if(this.getParticleSysOffhand()!=null) {
			Iterator<TGParticleSystemItemAttached> it = getParticleSysOffhand().iterator();
			while(it.hasNext()) {
				TGParticleSystemItemAttached p = it.next();
				
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
	public default void addSystemHand(EnumHand hand, String name) {
		//TODO: particleSystem adding code
		if(hand==EnumHand.MAIN_HAND) {
			
		} else {
			
		}
	}
	
	
	@SideOnly(Side.CLIENT)
	public List<ITGParticle> getEntityParticles();
	
	@SideOnly(Side.CLIENT)
	public List<TGParticleSystemItemAttached> getParticleSysMainhand();
	
	@SideOnly(Side.CLIENT)
	public List<TGParticleSystemItemAttached> getParticleSysOffhand();
	
	@SideOnly(Side.CLIENT)
	public List<ITGParticle> getOrInitEntityParticles();
	
	@SideOnly(Side.CLIENT)
	public List<TGParticleSystemItemAttached> getOrInitParticleSysMainhand();
	
	@SideOnly(Side.CLIENT)
	public List<TGParticleSystemItemAttached> getOrInitParticleSysOffhand();

}
