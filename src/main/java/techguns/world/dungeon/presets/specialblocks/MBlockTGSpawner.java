package techguns.world.dungeon.presets.specialblocks;

import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import techguns.entities.npcs.SkeletonSoldier;
import techguns.entities.npcs.ZombieSoldier;
import techguns.tileentities.TGSpawnerTileEnt;
import techguns.util.MBlock;

public class MBlockTGSpawner extends MBlock {

	public MBlockTGSpawner(MBlock other) {
		super(other);
		this.hasTileEntity=true;
	}

	@Override
	public void tileEntityPostPlacementAction(World w, IBlockState state, BlockPos p, int rotation) {
		TileEntity tile = w.getTileEntity(p);
		if(tile!=null && tile instanceof TGSpawnerTileEnt) {
			TGSpawnerTileEnt spawner = (TGSpawnerTileEnt) tile;
			
			spawner.setParams(5, 2, 200, 2);
			spawner.addMobType(SkeletonSoldier.class, 1);
			spawner.addMobType(ZombieSoldier.class, 1);
		}
		
	}

	

}
