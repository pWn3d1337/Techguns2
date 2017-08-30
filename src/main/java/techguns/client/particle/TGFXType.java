package techguns.client.particle;

import java.util.List;

import net.minecraft.entity.Entity;
import net.minecraft.world.World;

public abstract class TGFXType {
	public String name;
	boolean isList = false;
	
	public abstract List<TGParticleSystem> createParticleSystems(World world, double posX, double posY, double posZ, double motionX, double motionY, double motionZ);
	public abstract List<TGParticleSystem> createParticleSystemsOnEntity(Entity ent);
	public abstract List<TGParticleSystem> createParticleSystemsOnParticle(World worldIn, TGParticle part);
}
