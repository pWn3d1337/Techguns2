package techguns.util;

import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityList;
import net.minecraft.entity.monster.EntityCaveSpider;
import net.minecraft.init.Blocks;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityMobSpawner;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class MBlockVanillaSpawner extends MBlock {

	ResourceLocation entityId;
	
	public MBlockVanillaSpawner(Class<? extends Entity> clazz) {
		super(Blocks.MOB_SPAWNER, 0);
		this.hasTileEntity=true;
		this.entityId = EntityList.getKey(clazz);
	}

	@Override
	public void tileEntityPostPlacementAction(World w, IBlockState state, BlockPos p, int rotation) {
		TileEntity tile = w.getTileEntity(p);
		if(tile!=null && tile instanceof TileEntityMobSpawner) {
			TileEntityMobSpawner spawner = (TileEntityMobSpawner) tile;
			spawner.getSpawnerBaseLogic().setEntityId(entityId);
		}
	}
	
}
