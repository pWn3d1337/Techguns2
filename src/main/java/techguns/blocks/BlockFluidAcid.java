package techguns.blocks;

import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fluids.BlockFluidClassic;
import net.minecraftforge.fluids.Fluid;
import techguns.damagesystem.TGDamageSource;
import techguns.deatheffects.EntityDeathUtils.DeathType;
import techguns.entities.projectiles.GenericProjectile;

public class BlockFluidAcid extends BlockFluidClassic {

	public BlockFluidAcid(Fluid fluid, Material material) {
		super(fluid, material);
	}

	@Override
	public void onEntityCollision(World w, BlockPos pos, IBlockState state, Entity ent) {
		if (ent instanceof EntityLivingBase && GenericProjectile.BULLET_TARGETS.apply(ent)){
			TGDamageSource acidDamage = TGDamageSource.causePoisonDamage(null, null, DeathType.BIO);
			acidDamage.goreChance=1.0f;
			acidDamage.ignoreHurtresistTime=false;
			acidDamage.knockbackMultiplier=0.0f;
			ent.attackEntityFrom(acidDamage, 2.0f);
		}
	}
    
	
}
