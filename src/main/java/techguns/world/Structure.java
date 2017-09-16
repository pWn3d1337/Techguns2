package techguns.world;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import net.minecraft.block.Block;
import net.minecraft.block.BlockHorizontal;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.math.Vec3i;
import net.minecraft.world.World;
import techguns.util.BlockUtils;
import techguns.util.MBlock;

public class Structure implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	int sizeX;
	int sizeY;
	int sizeZ;
	List<MBlock> blocks = new ArrayList<MBlock>();
	List<BlockEntry> blockEntries = new ArrayList<BlockEntry>();
	

	
	public Structure(int sizeX, int sizeY, int sizeZ) {
		this.sizeX = sizeX;
		this.sizeY = sizeY;
		this.sizeZ = sizeZ;
	}

	public static Structure scanBlocks(World world, int x, int y, int z, int sizeX, int sizeY, int sizeZ) {
		Structure structure = new Structure(sizeX, sizeY, sizeZ);
		
		for (int i = 0; i < sizeX; i++) {
			for (int j = 0; j < sizeY; j++) {
				for (int k = 0; k < sizeZ; k++) {
					BlockPos bpos = new BlockPos(x+i, y+j, z+k);
					MBlock mblock = new MBlock(world.getBlockState(bpos));
					int index = structure.blocks.indexOf(mblock);
					if (index > -1) {
						structure.blockEntries.add(structure.new BlockEntry(i,j,k,index));
					} else {
						structure.blocks.add(mblock);
						structure.blockEntries.add(structure.new BlockEntry(i,j,k,structure.blocks.indexOf(mblock)));
					}
				}
			}
		}
		
		return structure;
	}
	
	public void placeBlocks(World world, int x, int y, int z, int rotation) {
		//BlockPos axis = new BlockPos(x+sizeX*0.5, y, z+sizeZ*0.5); //TODO
		Vec3d axis = new Vec3d(x+sizeX*0.5, y, z+sizeZ*0.5);
		
		for (BlockEntry b : blockEntries) {
			MBlock mb = blocks.get(b.blockIndex);
			BlockPos pos = new BlockPos(x+b.x, y+b.y, z+b.z);
			pos = BlockUtils.rotateAroundY(pos, axis, rotation);
			
			IBlockState state = mb.block.getStateFromMeta(mb.meta);
			
			if(state.getProperties().containsKey(BlockHorizontal.FACING)) {
				state = BlockRotator.getRotatedHorizontal(state, rotation);
			}
			
			world.setBlockState(pos, state);
			
			//AxisAlignedBB aabb = new AxisAlignedBB(pos1, axis);
			
		}
	}
	

	class BlockEntry implements Serializable {
		
		private static final long serialVersionUID = 1L;
		
		int x;
		int y;
		int z;
		int blockIndex;
		public BlockEntry(int x, int y, int z, int blockIndex) {
			this.x = x;
			this.y = y;
			this.z = z;
			this.blockIndex = blockIndex;
		}
		
	}
}
