package techguns.client.particle;

import java.util.List;

import net.minecraft.client.Minecraft;
import net.minecraft.client.particle.Particle;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import techguns.client.particle.TGParticleSystemType.DirResult;
import techguns.util.MathUtil;

/**
 * A particle system which spawns particles
 */
@SideOnly(Side.CLIENT)
public class TGParticleSystem extends Particle {
	
	public TGParticleSystemType type;
	
//	public double posX;
//	public double posY;
//	public double posZ;
//	
//	public double motionX;
//	public double motionY;
//	public double motionZ;
	
	int systemLifetime;
	int spawnDelay = 0;
	
	public float scale; //global scale
	public float rotationYaw;
	public float rotationPitch;
	
	public int initialDelay;
	public int ticksExisted = 0;
	
	public float startSizeRate;
	public float startSizeRateDamping;
	public float startSize = 0.0f;
	
	protected TGParticleStreak prevParticle = null;
	
//	private long timediff = 0;
	
	Entity entity; //parent entity (if attached to an entity)
	TGParticle parent; //parent particle (if attached to a particle)
	
	public TGParticleSystem(World worldIn, TGParticleSystemType type, double xCoordIn, double yCoordIn, double zCoordIn, double xSpeedIn, double ySpeedIn, double zSpeedIn) {
		//super(worldIn, xCoordIn, yCoordIn, zCoordIn, 0.0D, 0.0D, 0.0D);
		super(worldIn, xCoordIn, yCoordIn, zCoordIn);
		this.motionX = xSpeedIn;
		this.motionY = ySpeedIn;
		this.motionZ = zSpeedIn;
		this.type = type;
		this.init();
	}
	
	public TGParticleSystem(Entity entity, TGParticleSystemType type) {
		this(entity.world, type, entity.posX,entity.posY,entity.posZ,0,0,0);
		this.entity = entity;
	}
		

	public TGParticleSystem(World worldIn,TGParticle part, TGParticleSystemType type) {
		this(worldIn, type, part.posX(), part.posY(), part.posZ(),0,0,0);
		this.parent = part;
		//System.out.println("Spawn attached system : " + type.name);
	}

	public void renderParticle(BufferBuilder buffer, Entity entityIn, float partialTicks, float rotationX, float rotationZ, float rotationYZ, float rotationXY, float rotationXZ) {
		//This is a ParticleSystem which spawns the actual particles. Don't render anything
	}
	
	protected void init() {
		this.systemLifetime = MathUtil.randomInt(this.rand, type.systemLifetimeMin, type.systemLifetimeMax);
		this.initialDelay = MathUtil.randomInt(this.rand, type.initialDelayMin, type.initialDelayMax);
		this.startSizeRate = MathUtil.randomFloat(this.rand, type.startSizeRateMin, type.startSizeRateMax);
		this.startSizeRateDamping = MathUtil.randomFloat(this.rand, type.startSizeRateDampingMin, type.startSizeRateDampingMax);
		//Minecraft.getMinecraft().effectRenderer.addEffect(this);
		//timediff = System.currentTimeMillis();
	}
	
	public void onUpdate() {	
//		if (this.ticksExisted == 0) {
//			System.out.println("Timediff: "+(System.currentTimeMillis()-timediff));
//		}
		
		if (type == null) {
			this.setExpired();
			return;
		}

		if (this.entity!=null){
			
			this.posX=entity.posX;
			this.posY=entity.posY;
			this.posZ=entity.posZ;
			this.rotationPitch=entity.rotationPitch;
			this.rotationYaw=entity.rotationYaw;
			this.motionX = entity.motionX;
			this.motionY = entity.motionY;
			this.motionZ = entity.motionZ;
			
		} else if (this.parent != null) {
			this.posX=parent.posX();
			this.posY=parent.posY();
			this.posZ=parent.posZ();
		} else {
			this.posX+=motionX;
			this.posY+=motionY;
			this.posZ+=motionZ;
		}
		
		if (initialDelay-- > 0) {
			return;
		}
		
		this.startSize = this.startSize+this.startSizeRate;
		this.startSizeRate = (this.startSizeRate * this.startSizeRateDamping);
		
		
		if (spawnDelay-- <= 0) {
			int count = MathUtil.randomInt(rand, type.particleCountMin, type.particleCountMax);

			for (int i = 0; i < count; i++) {
				//Get position and motion data
				DirResult dir = this.type.new DirResult();
				Vec3d position = type.volumeType.getPosition(this, dir, i, count);
				//System.out.printf("Dir: %.3f,  %.3f,  %.3f\n", dir[0], dir[1], dir[2]);
				position = position.addVector(type.offset.x, type.offset.y, type.offset.z);
				Vec3d motion = type.velocityType.getVelocity(this, dir.values);
				//apply ParticleSystem's entity rotation
				
				//System.out.println("ParticleSystemRotation - Pitch: "+this.rotationPitch+" - Yaw: "+this.rotationYaw);
				
				//System.out.println(String.format("Entity pitch : %.3f,  yaw : %.3f", this.rotationPitch, this.rotationYaw));				
				
				motion = motion.rotatePitch((float) (-this.rotationPitch*MathUtil.D2R));
				motion = motion.rotateYaw((float) ((-this.rotationYaw)*MathUtil.D2R));
				if (type.volumeType != TGParticleSystemType.VOL_TRAIL) {
					position = position.rotatePitch((float) (-this.rotationPitch*MathUtil.D2R));
					position = position.rotateYaw((float) ((-this.rotationYaw)*MathUtil.D2R));		
				}
				//System.out.println("Motion: ("+motion.x+ ", "+motion.y + ", "+ motion.z+")");
				//System.out.println("Position: ("+position.x+ ", "+position.y + ", "+ position.z+")");
				
				//Spawn particle
				double mf = 0.05D; //Per Second instead of Per Tick
				TGParticle particle;
				if (this.type.streak) {
					TGParticleStreak particleStreak = new TGParticleStreak(this.world, this.posX+position.x, this.posY+position.y, this.posZ+position.z, motion.x*mf, motion.y*mf, motion.z*mf, this);
					if (prevParticle != null) {
						particleStreak.setPrev(prevParticle);
						prevParticle.setNext(particleStreak);
					}
					Minecraft.getMinecraft().effectRenderer.addEffect(particleStreak);
					prevParticle = particleStreak;
					particle = particleStreak;
				}else {
					particle = new TGParticle(this.world, this.posX+position.x, this.posY+position.y, this.posZ+position.z, motion.x*mf, motion.y*mf, motion.z*mf, this);
					Minecraft.getMinecraft().effectRenderer.addEffect(particle);
				}
				if (this.type.attachedSystem != null && !this.type.attachedSystem.equals("")) {
					List<TGParticleSystem> systems = TGFX.createFXOnParticle(this.world, particle, this.type.attachedSystem);
					if (systems!=null) {
						systems.forEach(s -> Minecraft.getMinecraft().effectRenderer.addEffect(s));
					}
				}
			}			
			spawnDelay = MathUtil.randomInt(this.rand, type.spawnDelayMin, type.spawnDelayMax);
		}
		
		if (this.entity!=null){
			if (this.entity.isDead){
				this.setExpired();
			} else if ( this.systemLifetime>0 && ticksExisted>= this.systemLifetime){
				this.setExpired();
			}
			
		} else if (this.parent != null) {
			if (!this.parent.isAlive()) {
				this.setExpired();
			}
		}else {
			if (ticksExisted >= this.systemLifetime) {
				this.setExpired();
			}
		}
		ticksExisted++;
		

	}

	/**
	 * Retrieve what effect layer (what texture) the particle should be rendered
	 * with. 0 for the particle sprite sheet, 1 for the main Texture atlas, and 3
	 * for a custom texture
	 */
	public int getFXLayer() {
		return 1;
	}
	
	public double posX() {
		return posX;
	}
	
	public double posY() {
		return posY;
	}
	
	public double posZ() {
		return posZ;
	}
	
	public double motionX() {
		return motionX;
	}
	
	public double motionY() {
		return motionY;
	}
	
	public double motionZ() {
		return motionZ;
	}

	
//ACTUALLY WE DON'T NEED THIS	
//	@SideOnly(Side.CLIENT)
//	public static class Factory implements IParticleFactory {
//		public Particle createParticle(int particleID, World worldIn, double xCoordIn, double yCoordIn, double zCoordIn, double xSpeedIn, double ySpeedIn, double zSpeedIn, int... parameters) {
//        	if (parameters.length <= 0) return null;
//        	TGParticleSystemType type = TGParticleList.getType(parameters[0]);
//			return new TGParticleSystem(worldIn, xCoordIn, yCoordIn, zCoordIn, xSpeedIn, ySpeedIn, zSpeedIn, type);
//		}
//	}
}