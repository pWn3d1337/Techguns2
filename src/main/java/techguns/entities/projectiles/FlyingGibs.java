package techguns.entities.projectiles;

import net.minecraft.block.material.Material;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.MoverType;
import net.minecraft.init.SoundEvents;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import techguns.client.particle.DeathEffect.GoreData;
import techguns.client.particle.TGParticleSystem;

public class FlyingGibs extends Entity{

	public int maxTimeToLive = 200;
	public int timeToLive = 200;

	public double gravity = 0.029999999329447746D;
	public float size;
	public Vec3d rotationAxis;
	public int bodypart;
	public int hitGroundTTL = 0;
	
	//the exploding entity
	public EntityLivingBase entity;
	
	public GoreData data;
	
	public TGParticleSystem trail_system;
	
	//public EntityDT entityDT;
	
	public FlyingGibs(World world, EntityLivingBase entity, GoreData data, double posX, double posY, double posZ, double motionX, double motionY, double motionZ, float size, int bodypart) {
		super(world);
		this.setPosition(posX, posY, posZ);
		this.motionX = motionX;
		this.motionY = motionY;
		this.motionZ = motionZ;
		this.size = size;
		this.maxTimeToLive = 75 + this.rand.nextInt(50);
		this.timeToLive = maxTimeToLive;
		this.entity = entity;
		this.bodypart = bodypart;
		//this.entityDT = entityDT;
		this.rotationAxis = new Vec3d(rand.nextDouble(), rand.nextDouble(), rand.nextDouble());
		this.data = data;
		
		trail_system = new TGParticleSystem(this, data.type_trail);
		Minecraft.getMinecraft().effectRenderer.addEffect(trail_system);
	}

	public FlyingGibs(World world) {
		super(world);
	}

	@Override
	public void onUpdate()
    {
        super.onUpdate();

        if (this.timeToLive > 0)
        {
            --timeToLive;
        }else {
        	this.setDead();
        }

        this.prevPosX = this.posX;
        this.prevPosY = this.posY;
        this.prevPosZ = this.posZ;
        this.motionY -= gravity;

        if (this.world.getBlockState(new BlockPos(this)).getMaterial() == Material.LAVA)
        {
            this.motionY = 0.20000000298023224D;
            this.motionX = (double)((this.rand.nextFloat() - this.rand.nextFloat()) * 0.2F);
            this.motionZ = (double)((this.rand.nextFloat() - this.rand.nextFloat()) * 0.2F);
            this.playSound(SoundEvents.ENTITY_GENERIC_BURN, 0.4F, 2.0F + this.rand.nextFloat() * 0.4F);
        }

        
        this.move(MoverType.SELF,this.motionX, this.motionY, this.motionZ);
        float f = 0.98F;

        if (this.onGround)
        {
        	//System.out.println("onGround.");
        	if (hitGroundTTL == 0) {
        		hitGroundTTL = timeToLive;
                trail_system.setExpired();
        	}
            f = (float) (this.world.getBlockState(new BlockPos(this.posX, this.posY, this.posZ)).getBlock().slipperiness * 0.98);

        }

        this.motionX *= (double)f;
        this.motionY *= 0.9800000190734863D;
        this.motionZ *= (double)f;

        if (this.onGround)
        {
            this.motionY *= -0.8999999761581421D;
        }
        
//        this.posX += this.motionX;
//        this.posY += this.motionY;
//        this.posZ += this.motionZ;
        
//        double e = 0.001d;
//        if (motionX >= e || motionY >= e || motionZ >= e) {
//        	
//        }
    }
	

    /**
     * Returns if this entity is in water and will end up adding the waters velocity to the entity
     */
    public boolean handleWaterMovement()
    {
        return this.world.handleMaterialAcceleration(this.getEntityBoundingBox(), Material.WATER, this);
    }

	@Override
	protected void entityInit() {}

	@Override
	protected void readEntityFromNBT(NBTTagCompound compound) {
	}

	@Override
	protected void writeEntityToNBT(NBTTagCompound compound) {
	}
}
