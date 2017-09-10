package techguns.entities.projectiles;

import elucent.albedo.lighting.ILightProvider;
import elucent.albedo.lighting.Light;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.init.Blocks;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.Optional;
import techguns.TGPackets;
import techguns.api.damagesystem.DamageType;
import techguns.client.ClientProxy;
import techguns.damagesystem.TGDamageSource;
import techguns.deatheffects.EntityDeathUtils.DeathType;
import techguns.items.guns.GenericGun;
import techguns.items.guns.IProjectileFactory;
import techguns.packets.PacketSpawnParticle;
import techguns.util.MathUtil;

@Optional.Interface(iface="elucent.albedo.lighting.ILightProvider", modid="albedo")
public class FlamethrowerProjectile extends GenericProjectile implements ILightProvider {

	boolean piercing = false;
	
	float chanceToIgnite = 0.5f;
	int entityIgniteTime = 3;
	
	public FlamethrowerProjectile(World worldIn) {
		super(worldIn);
		ClientProxy.get().createFXOnEntity("FlamethrowerTrail", this);
	}

	public FlamethrowerProjectile(World worldIn, double posX, double posY, double posZ, float yaw, float pitch, float damage, float speed, int TTL, float spread, int dmgDropStart,
			int dmgDropEnd, float dmgMin, float penetration, boolean blockdamage, EnumBulletFirePos leftGun, double gravity) {
		super(worldIn, posX, posY, posZ, yaw, pitch, damage, speed, TTL, spread, dmgDropStart, dmgDropEnd, dmgMin, penetration, blockdamage, leftGun);
		this.gravity=gravity;
	}
	
	public FlamethrowerProjectile(World par2World, EntityLivingBase p, float damage, float speed, int TTL, float spread, int dmgDropStart, int dmgDropEnd, float dmgMin,
			float penetration, boolean blockdamage, EnumBulletFirePos leftGun, double gravity) {
		super(par2World, p, damage, speed, TTL, spread, dmgDropStart, dmgDropEnd, dmgMin, penetration, blockdamage, leftGun);
		this.gravity=gravity;
	}

	@Override
	protected TGDamageSource getProjectileDamageSource() {
		TGDamageSource src = TGDamageSource.causeFireDamage(this, this.shooter, DeathType.DEFAULT);
    	src.armorPenetration = this.penetration;
    	src.setNoKnockback();
    	return src;
	}

	@Override
	protected void onHitEffect(EntityLivingBase ent, RayTraceResult rayTraceResult) {
		ent.setFire(this.entityIgniteTime);
	}

	@Override
	protected void hitBlock(RayTraceResult mop) {

		if (this.blockdamage) {
			if (Math.random() <= this.chanceToIgnite) {
				BlockPos hit = mop.getBlockPos();
	
    			switch (mop.sideHit) {
    				case DOWN:
    					if (this.world.isAirBlock(hit.down())) this.world.setBlockState(hit.down(), Blocks.FIRE.getDefaultState());
    					break;
    				case UP:
    					if (this.world.isAirBlock(hit.up())) {
    						if (this.world.getBlockState(hit) == Blocks.FARMLAND) this.world.setBlockState(hit, Blocks.DIRT.getDefaultState());
    						this.world.setBlockState(hit.up(), Blocks.FIRE.getDefaultState());
    					}
    					break;
    				/*case NORTH:	
    					if (this.worldObj.isAirBlock(x, y, z-1)) this.worldObj.setBlock(x, y, z-1, Blocks.FIRE);
    					break;
    				case SOUTH:
		    			if (this.worldObj.isAirBlock(x, y, z+1)) this.worldObj.setBlock(x, y, z+1, Blocks.FIRE);
		    			break;
    				case WEST:
    					if (this.worldObj.isAirBlock(x-1, y, z)) this.worldObj.setBlock(x-1, y, z, Blocks.FIRE);
    					break;
    				case EAST:
		    			if (this.worldObj.isAirBlock(x+1, y, z)) this.worldObj.setBlock(x+1, y, z, Blocks.FIRE);
		    			break;*/
    				default:
    					BlockPos p = hit.offset(mop.sideHit);
    					if(this.world.isAirBlock(p)) {
    						this.world.setBlockState(p, Blocks.FIRE.getDefaultState());
    					}
    			}
    			//TODO: burn blocks ?
	    		//Burn special blocks
	    	/*	Block b = this.worldObj.getBlock(x, y, z);
	    		if (b ==TGBlocks.camoNetRoof || b == TGBlocks.camoNetPane) {
	    			// || b == Blocks.wheat || b == Blocks.pumpkin_stem || b == Blocks.melon_stem || b == Blocks.carrots || b == Blocks.potatoes) {
	    			this.worldObj.spawnParticle("lava", this.posX, this.posY, this.posZ, 0.0D, 0.0D, 0.0D);
	    			this.worldObj.setBlock(x, y, z, Blocks.FIRE);
	    		//}else if (b == Blocks.farmland) {
	    		//	this.worldObj.setBlock(x, y, z, Blocks.dirt);
	    		//	this.worldObj.setBlock(x, y+1, z, Blocks.fire);
	    		}else if (this.worldObj.getBlock(x, y-1, z) == Blocks.FARMLAND) {
	    			this.worldObj.setBlock(x, y-1, z, Blocks.DIRT);
	    			this.worldObj.setBlock(x, y, z, Blocks.FIRE);
	    		}
            }*/
    		
			}

			//this.world.spawnParticle(EnumParticleTypes.LAVA, this.posX, this.posY, this.posZ, 0.0D, 0.0D, 0.0D);
		}
		
		TGPackets.network.sendToAllAround(new PacketSpawnParticle("FlamethrowerImpact", this.posX,this.posY,this.posZ), TGPackets.targetPointAroundEnt(this, 50.0f));

	}
	
	public static class Factory implements IProjectileFactory<FlamethrowerProjectile> {

		@Override
		public FlamethrowerProjectile createProjectile(GenericGun gun, World world, EntityLivingBase p, float damage, float speed, int TTL, float spread, int dmgDropStart, int dmgDropEnd,
				float dmgMin, float penetration, boolean blockdamage, EnumBulletFirePos firePos, float radius, double gravity) {
			return new FlamethrowerProjectile(world,p,damage,speed,TTL,spread,dmgDropStart,dmgDropEnd,dmgMin,penetration,blockdamage,firePos,gravity);
		}

		@Override
		public DamageType getDamageType() {
			return DamageType.FIRE;
		}
		
	}
	
	@Optional.Method(modid="albedo")
	@Override
	public Light provideLight() {
		return Light.builder()
				.pos(MathUtil.getInterpolatedEntityPos(this))
				.color(1.0f, 0.8f, 0f)
				.radius(2.5f)
				.build();
	}
}
