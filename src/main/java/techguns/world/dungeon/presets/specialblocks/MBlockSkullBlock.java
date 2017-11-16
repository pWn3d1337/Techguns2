package techguns.world.dungeon.presets.specialblocks;

import net.minecraft.block.state.IBlockState;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntitySkull;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import techguns.entities.npcs.SkeletonSoldier;
import techguns.entities.npcs.ZombieSoldier;
import techguns.tileentities.TGSpawnerTileEnt;
import techguns.util.MBlock;

public class MBlockSkullBlock extends MBlock {

	public MBlockSkullBlock(MBlock other) {
		super(other);
		this.hasTileEntity=true;
	}

	@Override
	public void tileEntityPostPlacementAction(World w, IBlockState state, BlockPos p, int rotation) {
		TileEntity tile = w.getTileEntity(p);
		if(tile!=null && tile instanceof TileEntitySkull) {
			TileEntitySkull skull = (TileEntitySkull) tile;
			
			//TODO
			skull.setSkullRotation(rotation);
		}
	}

	
}
