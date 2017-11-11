package techguns.client.particle;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import net.minecraft.entity.Entity;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import techguns.client.render.TGRenderHelper;
import techguns.client.render.TGRenderHelper.RenderType;
import techguns.util.MathUtil;

/**
 * the parameters for a particle system and its particles are specified in its type
 */
public class TGParticleSystemType extends TGFXType{
	TGRenderHelper.RenderType renderType = RenderType.SOLID; //Type of Transparency
	ResourceLocation texture = new ResourceLocation("techguns:textures/fx/fireball.png"); //Particle Texture
	int rows = 1; //number of rows in the animated texture (1 for no animation)
	int columns = 1; //number of columns in the animated texture (1 for no animation)
	int frames = 1; //number of animated frames (1 for no animation)
	boolean hasVariations = false; //Frames are used as variation instead of animation
	//boolean randomRotation = false;
	
	boolean streak = false;
	
	float angleMin = 0.0f;
	float angleMax = 0.0f;
	float angleRateMin = 0.0f;
	float angleRateMax = 0.0f;
	float angleRateDampingMin = 0.0f;
	float angleRateDampingMax = 0.0f;
	
	
	int lifetimeMin = 20; //Particle lifetime in ticks
	int lifetimeMax = 20;
	//int systemLifetimeMin = 5; //System lifetime in ticks
	//int systemLifetimeMax = 5;
	int initialDelayMin = 0; //Start after this many ticks.
	int initialDelayMax = 0;
	float animationSpeedMin = 1.0f; //1.0 = 1 full animation over particle lifetime
	float animationSpeedMax = 1.0f;
	
	float sizeMin = 1.0f; //Start size
	float sizeMax = 1.0f;
	float sizeRateMin = 1.0f; //Size increase per tick: Size = Size + (sizeRate * ticks)
	float sizeRateMax = 1.0f;
	float sizeRateDampingMin = 1.0f; //each tick: SizeRate = SizeRate * sizeRateDamping
	float sizeRateDampingMax = 1.0f;
	
	float startSizeRateMin = 0.0f; //particle start size increment per tick
	float startSizeRateMax = 0.0f;
	float startSizeRateDampingMin = 1.0f; //each tick: startSizeRate = startSizeRate * startsizeRateDamping
	float startSizeRateDampingMax = 1.0f;
	
	List<ColorEntry> colorEntries = new ArrayList<ColorEntry>();
	List<AlphaEntry> alphaEntries = new ArrayList<AlphaEntry>();
	
	int particleCountMin = 1; //number of Spawned particles each spawn/burst
	int particleCountMax = 1;
	int spawnDelayMin = 1; //time between spawns
	int spawnDelayMax = 1;
	int systemLifetimeMin = 5; //System's lifetime in ticks.
	int systemLifetimeMax = 5;

	IVelocityType velocityType = VEL_NONE;
	float[] velocityDataMin = new float[3];
	float[] velocityDataMax = new float[3];
	float velocityDampingMin = 1f; //each tick: Velocity = Velocity*VelocityDamping
	float velocityDampingMax = 1f;
	float velocityDampingOnGroundMin = 1f; //additional damping on ground
	float velocityDampingOnGroundMax = 1f;
	float gravity = 0.00f; //-Y motion increase per second
	float systemVelocityFactorMin = 0.0f; //How much of the system's velocity is added to the particle's velocity
	float systemVelocityFactorMax = 0.0f;
	
	Vec3d offset = new Vec3d(0.0, 0.0, 0.0); //TODO! Particles are spawned at this offset
	
	IVolumeType volumeType = VOL_POINT;
	float[] volumeData = new float[3];
	boolean isHollow;
	
	boolean removeOnGround = false; //remove this particle if it touches the ground
	
	boolean particlesMoveWithSystem = false;
	
	boolean groundAligned = false;
	
	String attachedSystem = null;
	boolean particlesStickToSystem = false;
	
	public static class ColorEntry {
		float time;
		float r;
		float g;
		float b;
		
		public ColorEntry(int r, int g, int b, float time) {
			this(((float)r)/255.0f, ((float)g)/255.0f, ((float)b)/255.0f, time); 
		}
		
		public ColorEntry(float r, float g, float b, float time) {
			super();
			this.time = time;
			this.r = r;
			this.g = g;
			this.b = b;
		}
	
	}
	
	public static class AlphaEntry {
		float time;
		float alpha;
		public AlphaEntry(float alpha, float time) {
			super();
			this.time = time;
			this.alpha = alpha;
		}
		public AlphaEntry clone() {
			return new AlphaEntry(this.alpha, this.time);
		}
	}
	
	public static IVelocityType VEL_NONE = new IVelocityType(){
		@Override
		public Vec3d getVelocity(TGParticleSystem sys, float... params) {
			return new Vec3d(0,0,0);
		}		
	};
	
	public static IVelocityType VEL_ORTHO = new IVelocityType(){
		@Override
		public Vec3d getVelocity(TGParticleSystem sys, float... params) {
			Random rand = new Random();
			if (sys.type.velocityDataMin.length >= 3) {
				float x = MathUtil.randomFloat(rand, sys.type.velocityDataMin[0], sys.type.velocityDataMax[0]);
				float y = MathUtil.randomFloat(rand, sys.type.velocityDataMin[1], sys.type.velocityDataMax[1]);
				float z = MathUtil.randomFloat(rand, sys.type.velocityDataMin[2], sys.type.velocityDataMax[2]);
				
				return new Vec3d(x,y,z);				
			}
			return new Vec3d(0,0,0);
		}		
	};
	
	public static IVelocityType VEL_OUTWARD = new IVelocityType(){
		@Override
		public Vec3d getVelocity(TGParticleSystem sys, float... params) {
			Random rand = new Random();
			if (sys.type.velocityDataMin.length >= 2) {
				float r = MathUtil.randomFloat(rand, sys.type.velocityDataMin[0], sys.type.velocityDataMax[0]);
				float y = MathUtil.randomFloat(rand, sys.type.velocityDataMin[1], sys.type.velocityDataMax[1]);
				
				if (params.length == 1 ){ //Cylinder (yaw angle)
					double angle = -params[0];
					//System.out.printf("Outward, angle=%.3f\n",params[0]);
					return new Vec3d( r*Math.cos(angle),  y, r*Math.sin(angle));
				}else if (params.length == 3) { //Sphere or Hemisphere
					return new Vec3d( params[0]*r, params[1]*r, params[2]*r);
				}else  { //e.g. POINT
					double angle = rand.nextDouble()*2.0*Math.PI;
					return new Vec3d( r*Math.cos(angle), y, r*Math.sin(angle));
				}
			}
			return new Vec3d(0,0,0);
		}		
	};
	
	public static IVelocityType VEL_SPHERICAL = new IVelocityType(){
		@Override
		public Vec3d getVelocity(TGParticleSystem sys, float... params) {
			Random rand = new Random();
			if (sys.type.velocityDataMin.length >= 1) {
				float r = MathUtil.randomFloat(rand, sys.type.velocityDataMin[0], sys.type.velocityDataMax[0]);

//				if (dir.x != 0 || dir.y != 0 || dir.z != 0) {
//					return new Vec3d(dir.x*r,  dir.y*r, dir.z*r);
//				}else {
					double angle = rand.nextDouble()*2.0*Math.PI;
					double y = 1.0-(rand.nextDouble()*2.0);
					double a = Math.sqrt(1.0-Math.pow(y, 2));
					return new Vec3d(r*a*Math.cos(angle), r*y, r*a*Math.sin(angle));
	//			}
		
			}
			return new Vec3d(0,0,0);
		}		
	};
	
	public static IVelocityType VEL_HEMISPHERICAL = new IVelocityType(){
		@Override
		public Vec3d getVelocity(TGParticleSystem sys, float...params) {
			Random rand = new Random();
			if (sys.type.velocityDataMin.length >= 1) {
				float r = MathUtil.randomFloat(rand, sys.type.velocityDataMin[0], sys.type.velocityDataMax[0]);

//				if (dir.x != 0 || dir.y != 0 || dir.z != 0) {
//					
//					return new Vec3d(dir.x*r, dir.y*r, dir.z*r);
//				}else {
					double angle = rand.nextDouble()*2.0*Math.PI;
					double y = rand.nextDouble();
					double a = Math.sqrt(1.0-Math.pow(y, 2));
					
					return new Vec3d(r*a*Math.cos(angle), r*a*Math.sin(angle), r*y);
//				}
		
			}
			return new Vec3d(0,0,0);
		}		
	};
	
	public static IVolumeType VOL_POINT = new IVolumeType() {

		@Override
		public Vec3d getPosition(TGParticleSystem sys, DirResult dir, int i, int count) {
			return new Vec3d(0,0,0);
		}
		
	};
	
	
	public static IVolumeType VOL_CYLINDER = new IVolumeType() {
		@Override
		public Vec3d getPosition(TGParticleSystem sys, DirResult dir, int i, int count) {
			Random rand = new Random();
			if (sys.type.volumeData.length >= 2) {
				float r;
				float y;
				float angle = rand.nextFloat()*(float)Math.PI*2.0f;
				if (sys.type.isHollow) {
					r = sys.type.volumeData[0];
				}else {
					r = MathUtil.randomFloat(rand, 0.0f, sys.type.volumeData[0]);
				}
				y = rand.nextFloat()*sys.type.volumeData[1];
				
				Vec3d direction = new Vec3d(1.0, 0.0, 0.0).rotateYaw(angle);
				//System.out.printf("angle=%.3f,  direction = %.3f / %.3f / %.3f\n", angle, direction.x, direction.y, direction.z);
				//dir.setValues((float)direction.x, (float)direction.y, (float)direction.z);
				dir.setValues(angle);
				
				Vec3d position = new Vec3d(direction.x*r, y, direction.z*r);
				return position;
			}else {
				//fail;
				return new Vec3d(0,0,0);
			}	
		}		
	};
	
	public static IVolumeType VOL_CYLINDER2 = new IVolumeType() { //Tilted cylinder
		@Override
		public Vec3d getPosition(TGParticleSystem sys, DirResult dir, int i, int count) {
			Random rand = new Random();
			if (sys.type.volumeData.length >= 2) {
				float r;
				float z;
				float angle = rand.nextFloat()*(float)Math.PI*2.0f;
				if (sys.type.isHollow) {
					r = sys.type.volumeData[0];
				}else {
					r = MathUtil.randomFloat(rand, 0.0f, sys.type.volumeData[0]);
				}
				z = rand.nextFloat()*sys.type.volumeData[1];
				
				Vec3d direction = new Vec3d(0.0, 1.0, 0.0).rotatePitch(angle); //.rotateYaw((float)(Math.PI*0.5d));
				dir.setValues((float)direction.x, (float)direction.y, (float)direction.z);
				return new Vec3d(direction.x*r, direction.y*r, z);
			}else {
				//fail;
				return new Vec3d(0,0,0);
			}	
		}		
	};
	
	public static IVolumeType VOL_SPHERE = new IVolumeType() {
		@Override
		public Vec3d getPosition(TGParticleSystem sys, DirResult dir, int i, int count) {
			Random rand = new Random();
			if (sys.type.volumeData.length >= 1) {
				float r;
				double angle = rand.nextDouble()*2.0*Math.PI;
				double y = 1.0-(rand.nextDouble()*2.0);
				double a = Math.sqrt(1.0-Math.pow(y, 2));
				
				if (sys.type.isHollow) {
					r = sys.type.volumeData[0];
				}else {
					r = MathUtil.randomFloat(rand, 0.0f, sys.type.volumeData[0]);
				}

				Vec3d direction = new Vec3d(a*Math.cos(angle),  y, a*Math.sin(angle));
				dir.setValues((float)direction.x, (float)direction.y, (float)direction.z);
				
				return new Vec3d(direction.x*r, direction.y*r, direction.z*r);
			}else {
				//fail;
				return new Vec3d(0,0,0);
			}	
		}		
	};
	
	public static IVolumeType VOL_HEMISPHERE = new IVolumeType() {
		@Override
		public Vec3d getPosition(TGParticleSystem sys, DirResult dir, int i, int count) {
			Random rand = new Random();
			if (sys.type.volumeData.length >= 1) {
				float r;
				double angle = rand.nextDouble()*2.0*Math.PI;
				double y = rand.nextDouble();
				double a = Math.sqrt(1.0-Math.pow(y, 2));
				
				if (sys.type.isHollow) {
					r = sys.type.volumeData[0];
				}else {
					r = MathUtil.randomFloat(rand, 0.0f, sys.type.volumeData[0]);
				}

				Vec3d direction = new Vec3d(a*Math.cos(angle),  y, a*Math.sin(angle));
				dir.setValues((float)direction.x, (float)direction.y, (float)direction.z);
				
				return new Vec3d(direction.x*r, direction.y*r, direction.z*r);
			}else {
				//fail;
				return new Vec3d(0,0,0);
			}	
		}		
	};
	
	public static IVolumeType VOL_TRAIL = new IVolumeType() {
		@Override
		public Vec3d getPosition(TGParticleSystem sys, DirResult dir, int i, int count) {
			Random rand = new Random();
			if (sys.type.volumeData.length >= 1) {
				
				float len;
				
				Entity ent = sys.entity;
				if (ent==null){
					return new Vec3d(sys.posX(), sys.posY(),sys.posZ());
				} else {
					
					Vec3d motion = new Vec3d(ent.motionX, ent.motionY, ent.motionZ).normalize();
					//Vec3d motion = new Vec3d(sys.motionX(), sys.motionY(), sys.motionZ()).normalize();
					
					len = sys.type.volumeData[0];
					float t = (i*1.0f) / (count*1.0f) * len;

					
					dir.setValues((float)motion.x, (float)motion.y, (float)motion.z);
					
					return new Vec3d(-motion.x*t, -motion.y*t, -motion.z*t);
				}

			}else {
				//fail;
				return new Vec3d(0,0,0);
			}	
		}		
	};
	
	public interface IVelocityType {		
		public Vec3d getVelocity(TGParticleSystem sys, float ... params);
	}
	
	public interface IVolumeType {
		public Vec3d getPosition(TGParticleSystem sys, DirResult dir, int i, int count);
	}

	public void extend(TGParticleSystemType other) {
		this.alphaEntries = new ArrayList<AlphaEntry>();
		other.alphaEntries.forEach(ae -> this.alphaEntries.add(ae.clone()));
		this.animationSpeedMax = other.animationSpeedMax;
		this.animationSpeedMin = other.animationSpeedMin;
		this.colorEntries = new ArrayList<ColorEntry>();
		other.colorEntries.forEach(ce -> this.colorEntries.add(new ColorEntry(ce.r, ce.g, ce.b, ce.time)));	
		this.columns = other.columns;
		this.frames = other.frames;
		this.gravity = other.gravity;
		this.hasVariations = other.hasVariations;
		this.initialDelayMax = other.initialDelayMin;
		this.isHollow = other.isHollow;
		this.lifetimeMax = other.lifetimeMax;
		this.lifetimeMin = other.lifetimeMin;
		this.systemLifetimeMin = other.systemLifetimeMin;
		this.systemLifetimeMax = other.systemLifetimeMax;
		this.particleCountMax = other.particleCountMax;
		//this.randomRotation = other.randomRotation;
		this.angleMin = other.angleMin;
		this.angleMax = other.angleMax;
		this.angleRateMin = other.angleRateMin;
		this.angleRateMax = other.angleRateMax;
		this.angleRateDampingMin = other.angleRateDampingMin;
		this.angleRateDampingMax = other.angleRateDampingMax;
		this.removeOnGround = other.removeOnGround;
		this.renderType = other.renderType;
		this.rows = other.rows;
		this.sizeMax = other.sizeMax;
		this.sizeMin = other.sizeMin;
		this.sizeRateDampingMax = other.sizeRateDampingMax;
		this.sizeRateDampingMin = other.sizeRateDampingMin;
		this.sizeRateMax = other.sizeRateMax;
		this.sizeRateMin = other.sizeRateMin;
		this.spawnDelayMax = other.spawnDelayMax;
		this.spawnDelayMin = other.spawnDelayMin;
		this.texture = other.texture;
		this.velocityDampingMax = other.velocityDampingMax;
		this.velocityDampingMin = other.velocityDampingMin;
		this.velocityDampingOnGroundMax = other.velocityDampingOnGroundMax;
		this.velocityDampingOnGroundMin = other.velocityDampingOnGroundMin;
		this.velocityDataMax = new float[other.velocityDataMax.length];
		this.velocityDataMin = new float[other.velocityDataMin.length];
		for (int i = 0; i < other.velocityDataMax.length; i++) {
			this.velocityDataMin[i] = other.velocityDataMin[i];
			this.velocityDataMax[i] = other.velocityDataMax[i];
		}	
		this.velocityType = other.velocityType;
		this.volumeData = new float[other.volumeData.length];
		for (int i = 0; i < other.volumeData.length; i++) {
			this.volumeData[i] = other.volumeData[i];
		}
		this.volumeType = other.volumeType;
		this.offset = new Vec3d(other.offset.x, other.offset.y, other.offset.z);
		this.particlesMoveWithSystem = other.particlesMoveWithSystem;
		this.streak = other.streak;
		this.groundAligned = other.groundAligned;
		this.attachedSystem = other.attachedSystem;
		this.systemVelocityFactorMin = other.systemVelocityFactorMin;
		this.systemVelocityFactorMax = other.systemVelocityFactorMax;
	}


	@Override
	public List<TGParticleSystem> createParticleSystems(World world, double posX, double posY, double posZ,
			double motionX, double motionY, double motionZ) {
		ArrayList<TGParticleSystem> list = new ArrayList<TGParticleSystem>();
		list.add(new TGParticleSystem(world, this, posX, posY, posZ, motionX, motionY, motionZ));
		return list;
	}

	@Override
	public List<TGParticleSystem> createParticleSystemsOnEntity(Entity ent) {
		ArrayList<TGParticleSystem> list = new ArrayList<TGParticleSystem>();
		list.add(new TGParticleSystem(ent,this));
		return list;
	}
	
	@Override
	public List<TGParticleSystem> createParticleSystemsOnParticle(World worldIn, TGParticle ent) {
		ArrayList<TGParticleSystem> list = new ArrayList<TGParticleSystem>();
		list.add(new TGParticleSystem(worldIn, ent,this));
		return list;
	}
	
	
	 /**
	 * z' = z*cos( angle) - x*sin (angle)
	 * x' = z*sin( angle) + x*cos (angle)
	 */
	
	class DirResult {
		public float[] values;
		public void setValues(float...params) {
			values = params;
		}
		public DirResult(float... params) {
			this.values = params;
		}
	}
}
