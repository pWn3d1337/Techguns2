package techguns.entities.special;

import java.util.List;

import com.google.common.base.Predicate;

import net.minecraft.block.Block;
import net.minecraft.block.material.EnumPushReaction;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.potion.PotionEffect;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.Explosion;
import net.minecraft.world.World;
import techguns.TGRadiationSystem;

public class EntityRadiation extends Entity {
	
	//every second apply rad
	public static final int RAD_INTERVAL = 20;
	
	//lifetime in ticks
	protected int duration;
	
	protected float radius;
	protected float inner_radius;
	
	protected int strength;
	protected int strength_outer;
	
	protected int strength_original;
	protected int strength_outer_original;
	
	public EntityRadiation(World worldIn) {
		super(worldIn);
	}

	
	public EntityRadiation(World worldIn, double posX, double posY, double posZ, int duration, float innter_radius, int strength, float radius, int strength_outer) {
		super(worldIn);
		this.setPosition(posX, posY, posZ);
		this.setRadiationValues(duration, innter_radius, strength, radius, strength_outer);
	}

	public EntityRadiation(Entity otherEntPos, int duration, float innter_radius, int strength, float radius, int strength_outer) {
		super(otherEntPos.world);
		this.setPosition(otherEntPos.posX, otherEntPos.posY, otherEntPos.posZ);
		this.setRadiationValues(duration, innter_radius, strength, radius, strength_outer);
	}
	
	public EntityRadiation(TileEntity tilePos, int duration, float innter_radius, int strength, float radius, int strength_outer) {
		super(tilePos.getWorld());
		this.setPosition(tilePos.getPos().getX()+0.5, tilePos.getPos().getY()+0.5, tilePos.getPos().getZ()+0.5);
		this.setRadiationValues(duration, innter_radius, strength, radius, strength_outer);
	}
	
	public EntityRadiation setRadiationValues(int duration, float innter_radius, int strength, float radius, int strength_outer ) {
		this.duration = duration;
		this.inner_radius = innter_radius;
		this.strength=strength;
		this.radius = radius;
		this.strength_outer = strength_outer;
		this.strength_original = strength;
		this.strength_outer_original= strength_outer;
		return this;
	}
	
	@Override
	protected void entityInit() {
		
	}

	@Override
	protected void readEntityFromNBT(NBTTagCompound compound) {
		this.ticksExisted = compound.getInteger("Age");
        this.duration = compound.getInteger("Duration");
        
        this.radius = compound.getFloat("radius");
        this.inner_radius = compound.getFloat("inner_radius");
        
        this.strength = compound.getShort("strength");
        this.strength_outer = compound.getShort("strength_outer");
        
        this.strength_original = compound.getShort("strength_original");
        this.strength_outer_original = compound.getShort("strength_outer_original");
	}

	@Override
	protected void writeEntityToNBT(NBTTagCompound compound) {
		compound.setInteger("Age", this.ticksExisted);
        compound.setInteger("Duration", this.duration);
        
        compound.setFloat("radius", this.radius);
        compound.setFloat("inner_radius", this.inner_radius);
        
        compound.setShort("strength", (short) this.strength);
        compound.setShort("strength_outer", (short) this.strength_outer);
        
        compound.setShort("strength_original", (short) this.strength_original);
        compound.setShort("strength_outer_original", (short) this.strength_outer_original);
	}

	
	@Override
	public boolean canBeAttackedWithItem() {
		return false;
	}

	@Override
	protected boolean canTriggerWalking() {
		return false;
	}

	@Override
	protected void doWaterSplashEffect() {
		return;
	}
	
	@Override
	public void onEntityUpdate() {
		super.onEntityUpdate();
		
		if(this.ticksExisted>this.duration) {
			this.setDead();
			return;
		}
		
		if(this.ticksExisted % RAD_INTERVAL==0) {
			//apply rad to nearby entities
			
			
			if(!this.world.isRemote) {
				
				
				//update strength;
				if (this.ticksExisted*2 >= this.duration) {
					
					double factor = 1d - ((this.ticksExisted - this.duration*0.5)/(this.duration*0.5));
					this.strength = Math.max((int) Math.round(this.strength_original*factor),0);
					this.strength_outer = Math.max((int) Math.round(this.strength_outer_original*factor),0);
					
				}
				//System.out.println("RADTICK! +Str:"+this.strength+" outer:"+this.strength_outer);
				
				TGRadiationSystem.applyRadToEntities(world, this.posX, this.posY, this.posZ, this.radius, RAD_INTERVAL+2, this.strength, this.inner_radius, this.strength_outer);
				
			}
			
		}
		
		
	}
	
	@Override
    public EnumPushReaction getPushReaction()
    {
        return EnumPushReaction.IGNORE;
    }
	
}
