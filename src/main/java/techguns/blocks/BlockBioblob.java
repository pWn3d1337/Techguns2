package techguns.blocks;

import java.util.Arrays;

import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyDirection;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.world.World;

public class BlockBioblob extends GenericBlock {

	public static final PropertyDirection FACING = PropertyDirection.create("facing", Arrays.asList(EnumFacing.VALUES));
	
	
	public BlockBioblob(String name) {
		super(name, Material.CLAY);
	}

	@Override
	public boolean hasTileEntity(IBlockState state) {
		return true;
	}

	@Override
	public TileEntity createTileEntity(World world, IBlockState state) {
		// TODO Auto-generated method stub
		return super.createTileEntity(world, state);
	}

	
}
