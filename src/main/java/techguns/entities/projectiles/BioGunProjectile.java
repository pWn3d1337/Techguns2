package techguns.entities.projectiles;

import io.netty.buffer.ByteBuf;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.init.MobEffects;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.registry.IEntityAdditionalSpawnData;
import techguns.api.damagesystem.DamageType;
import techguns.client.ClientProxy;
import techguns.damagesystem.TGDamageSource;
import techguns.deatheffects.EntityDeathUtils.DeathType;
import techguns.items.guns.GenericGun;
import techguns.items.guns.IChargedProjectileFactory;
import techguns.items.guns.IProjectileFactory;

public class BioGunProjectile extends GenericProjectile implements IEntityAdditionalSpawnData{

	public int level;


	public BioGunProjectile(World worldIn, double posX, double posY, double posZ, float yaw, float pitch, float damage, float speed, int TTL, float spread, int dmgDropStart,
			int dmgDropEnd, float dmgMin, float penetration, boolean blockdamage, EnumBulletFirePos leftGun, double gravity, int level) {
		super(worldIn, posX, posY, posZ, yaw, pitch, damage, speed, TTL, spread, dmgDropStart, dmgDropEnd, dmgMin, penetration, blockdamage, leftGun);
		this.level=level;
		this.gravity=gravity;
	}

	public BioGunProjectile(World par2World, EntityLivingBase p, float damage, float speed, int TTL, float spread, int dmgDropStart, int dmgDropEnd, float dmgMin,
			float penetration, boolean blockdamage, EnumBulletFirePos leftGun, double gravity, int level) {
		super(par2World, p, damage, speed, TTL, spread, dmgDropStart, dmgDropEnd, dmgMin, penetration, blockdamage, leftGun);
		this.level=level;
		this.gravity=gravity;
	}
	
	public BioGunProjectile(World worldIn) {
		super(worldIn);
		ClientProxy.get().createFXOnEntity("BioGunTrail", this);
	}

	 @Override
	protected void onHitEffect(EntityLivingBase ent, RayTraceResult rayTraceResult) {
 		ent.addPotionEffect(new PotionEffect(MobEffects.POISON,100,3,false,true));
	}
	 
	@Override
	protected TGDamageSource getProjectileDamageSource() {
		TGDamageSource src = TGDamageSource.causePoisonDamage(this, this.shooter, DeathType.BIO);
    	src.goreChance = 1.0f;
    	src.armorPenetration = this.penetration;
    	src.setNoKnockback();
    	return src;
	}

	
	
	/*@Override
	protected void hitBlock(MovingObjectPosition mop) {
		 Block blockHit = this.worldObj.getBlock(mop.blockX, mop.blockY, mop.blockZ);
		 
		 if(blockHit == TGBlocks.bioBlobSmall){
			 //System.out.println("Hit Bioblob, increase size");       	
			 
			 TileEntity tile = this.worldObj.getTileEntity(mop.blockX, mop.blockY, mop.blockZ);
			 if(tile!=null && tile instanceof BioblobTileEnt){
				 
				 ((BioblobTileEnt)tile).hitBlob(level, this.shooter);
			 }
			 
			 
		 } else {
			 if (!this.worldObj.isRemote){
    			 int side = mop.sideHit;
    			 int[] offset = {0,0,0};
    			 
    			 if (side==0){
    				 offset[1]=-1;
    			 } else if(side==1){
    				 offset[1]=1;
    			 } else if(side==2){
    				 offset[2]=-1;
    			 } else if(side==3){
    				 offset[2]=1;
    			 } else if(side==4){
    				 offset[0]=-1;
    			 } else if(side==5){
    				 offset[0]=1;
    			 }
    			 
    			 int[] blobPos = {mop.blockX+offset[0], mop.blockY+offset[1], mop.blockZ+offset[2]};
    			 
    			 if (this.worldObj.isAirBlock(blobPos[0],blobPos[1],blobPos[2])){
    				 
    				 boolean canPlace = true;
    				 
    				 if ( this.shooter instanceof EntityPlayer){
	    				 final BlockEvent.PlaceEvent placeEvent = new BlockEvent.PlaceEvent(BlockSnapshot.getBlockSnapshot(worldObj, blobPos[0],blobPos[1],blobPos[2]), blockHit, (EntityPlayer) this.shooter);
	    				 MinecraftForge.EVENT_BUS.post(placeEvent);
	    				 canPlace = !placeEvent.isCanceled();
    				 }
    				 
    				 if (canPlace){
	    				 this.worldObj.setBlock(blobPos[0],blobPos[1],blobPos[2], TGBlocks.bioBlobSmall);
	    				 if (this.level>1){
	    					 TileEntity tile = this.worldObj.getTileEntity(blobPos[0],blobPos[1],blobPos[2]);
	    					 if(tile!=null && tile instanceof BioblobTileEnt){
	    						 ((BioblobTileEnt)tile).hitBlob(level-1, this.shooter);
	    					 }
	    				 }
	    				 this.worldObj.setBlockMetadataWithNotify(blobPos[0],blobPos[1],blobPos[2], side, 3);
    				 }
    			 } else {
    				 
    				 TileEntity tile = this.worldObj.getTileEntity(blobPos[0],blobPos[1],blobPos[2]);
        			 if(tile!=null && tile instanceof BioblobTileEnt){
        				 
        				 ((BioblobTileEnt)tile).hitBlob(level, this.shooter);
        			 }
    				 
    			 }
    			 
    			 
    		 }
		 }
	
	
	    this.worldObj.spawnParticle("slime", this.posX, this.posY, this.posZ, 0.0D, 0.0D, 0.0D);
	}*/

	
	 
	/*private void updateBlockLevel(int x, int y, int z, int level){
		switch (level){
		case 1:
			this.worldObj.setBlock(x, y, z, TGBlocks.bioBlobSmall);
			break;
		/case 2:
			this.worldObj.setBlock(x, y, z, TGBlocks.bioBlobMedium);
			break;
		case 3:
			this.worldObj.setBlock(x, y, z, TGBlocks.bioBlobLarge);
			break;
		}
	}*/
	 
	/*private void setBlockForLevel(int x, int y, int z, int level){
		if (level==0){
			if (this.worldObj.isAirBlock(x, y+1, z)){
				updateBlockLevel(x,y+1,z,this.level);
			}
		} else {
			int newlvl = level+this.level;
			if (newlvl >3){
    			new ProjectileExplosion(this.worldObj, x, y, z, this.shooter, 1.5f, 30, 1.0f, 2.0f).doExplosion(false, this.shooter);
    			this.worldObj.setBlock(x, y, z, Blocks.fire);
			} else {
				updateBlockLevel(x,y,z,newlvl);
			}
		}
		
	};*/
	
	
	@Override
	public void onUpdate() {
		super.onUpdate();
		if (this.isInWater())
        {
            for (int l = 0; l < 4; ++l)
            {
                float f4 = 0.25F;
                this.world.spawnParticle(EnumParticleTypes.WATER_BUBBLE, this.posX - this.motionX * (double)f4, this.posY - this.motionY * (double)f4, this.posZ - this.motionZ * (double)f4, this.motionX, this.motionY, this.motionZ);
            }
            this.setDead();
        } else {
        	//float f5 = 0.1f;
        	//this.worldObj.spawnParticle("slime", this.posX, this.posY, this.posZ, this.motionX*f5, (this.motionY*f5)-this.getGravityVelocity(), this.motionZ*f5);
        	//Techguns.proxy.spawnParticle("BioTrail", this.worldObj, this.posX, this.posY, this.posZ, this.motionX*f5, (this.motionY*f5)-this.getGravityVelocity(), this.motionZ*f5);

        }
	}

	@Override
	public void writeSpawnData(ByteBuf buffer) {
		buffer.writeByte((byte)this.level);
	}

	@Override
	public void readSpawnData(ByteBuf additionalData) {
		this.level=additionalData.readByte();
	}
	
	public static class Factory implements IChargedProjectileFactory<BioGunProjectile> {

		@Override
		public BioGunProjectile createProjectile(GenericGun gun, World world, EntityLivingBase p, float damage, float speed, int TTL, float spread, int dmgDropStart, int dmgDropEnd,
				float dmgMin, float penetration, boolean blockdamage, EnumBulletFirePos firePos, float radius, double gravity) {
			return this.createChargedProjectile(world, p, damage, speed, TTL, spread, dmgDropStart, dmgDropEnd, dmgMin, penetration, blockdamage, firePos, radius, gravity, 0f, 1);
		}

		@Override
		public DamageType getDamageType() {
			return DamageType.POISON;
		}

		@Override
		public BioGunProjectile createChargedProjectile(World world, EntityLivingBase p, float damage, float speed, int TTL, float spread, int dmgDropStart, int dmgDropEnd,
				float dmgMin, float penetration, boolean blockdamage, EnumBulletFirePos firePos, float radius, double gravity, float charge, int ammoConsumed) {
			return new BioGunProjectile(world,p,damage,speed,TTL,spread,dmgDropStart,dmgDropEnd,dmgMin,penetration,blockdamage,firePos,gravity,ammoConsumed);
		}
		
	}
}