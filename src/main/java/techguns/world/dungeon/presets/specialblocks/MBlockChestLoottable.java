package techguns.world.dungeon.presets.specialblocks;

import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityChest;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import techguns.util.MBlock;

public class MBlockChestLoottable extends MBlock {

	protected ResourceLocation loottable;
	
	public MBlockChestLoottable(Block block, int meta, ResourceLocation loottable) {
		super(block,meta);
		this.loottable=loottable;
		this.hasTileEntity=true;
	}

	@Override
	public void tileEntityPostPlacementAction(World w, IBlockState state, BlockPos p, int rotation) {
		TileEntity tile = w.getTileEntity(p);
		if(tile!=null && tile instanceof TileEntityChest) {
			TileEntityChest chest = (TileEntityChest) tile;
			chest.setLootTable(this.loottable, w.rand.nextLong());
		}
	}
	
}
