package techguns.client.particle;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.entity.Entity;
import net.minecraft.world.World;

public class TGParticleListType extends TGFXType {
	ArrayList<ParticleSystemEntry> particleSystems = new ArrayList<ParticleSystemEntry>();

	
	
	public TGParticleListType() {
		isList = true;
	}

	public void addParticleSystem(String particleSystem) {
		particleSystems.add(new ParticleSystemEntry(particleSystem));
	}
	
	class ParticleSystemEntry {
		//ParticleSystemType type; //Actually is just a string
		String particleSystem;
		//TODO: Delay, Offset, Scale, etc.

		public ParticleSystemEntry(String particleSystem) {
			super();
			this.particleSystem = particleSystem;
		}
	}

	
	@Override
	public List<TGParticleSystem> createParticleSystems(World world, double posX, double posY, double posZ, double motionX, double motionY, double motionZ) {
		ArrayList<TGParticleSystem> list = new ArrayList<TGParticleSystem>();
		for (ParticleSystemEntry system : particleSystems) {		
			if (TGFX.FXList.containsKey(system.particleSystem)) {
				TGFXType fxtype = TGFX.FXList.get(system.particleSystem);
				list.addAll(fxtype.createParticleSystems(world, posX, posY, posZ, motionX, motionY, motionZ));
			}
		}
		return list;
	}

	@Override
	public List<TGParticleSystem> createParticleSystemsOnEntity(Entity ent) {
		ArrayList<TGParticleSystem> list = new ArrayList<TGParticleSystem>();
		for (ParticleSystemEntry system : particleSystems) {		
			if (TGFX.FXList.containsKey(system.particleSystem)) {
				TGFXType fxtype = TGFX.FXList.get(system.particleSystem);
				list.addAll(fxtype.createParticleSystemsOnEntity(ent));
			}
		}
		return list;
	}

	@Override
	public List<TGParticleSystem> createParticleSystemsOnParticle(World worldIn, TGParticle ent) {
		ArrayList<TGParticleSystem> list = new ArrayList<TGParticleSystem>();
		for (ParticleSystemEntry system : particleSystems) {		
			if (TGFX.FXList.containsKey(system.particleSystem)) {
				TGFXType fxtype = TGFX.FXList.get(system.particleSystem);
				list.addAll(fxtype.createParticleSystemsOnParticle(worldIn, ent));
			}
		}
		return list;
	}
}
