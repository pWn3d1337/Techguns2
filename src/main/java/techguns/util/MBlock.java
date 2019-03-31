package techguns.util;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.HashMap;

import javax.annotation.Nonnull;

import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockPos.MutableBlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import techguns.world.BlockData;
import techguns.world.BlockRotator;
import techguns.world.EnumLootType;
import techguns.world.structures.WorldgenStructure.BiomeColorType;

public class MBlock implements Serializable {
	
	private static final long serialVersionUID = 1L;	
	
	public transient Block block;
	public int meta;
	
	protected transient IBlockState state;

	protected transient boolean hasTileEntity=false;
	
	protected transient int layer=0;
	
	protected static final HashMap<BlockMeta, MBlock> replacementTable = new HashMap<>(); 
	static {
		replacementTable.put(new BlockMeta("chisel:netherbrick", 13), new MBlock(Blocks.NETHER_BRICK,0));
	}
	
	public MBlock(MBlock other) {
		this.block=other.block;
		this.meta=other.meta;
		this.state=other.state;
		this.hasTileEntity=other.hasTileEntity;
		this.layer=other.layer;
	}
	
	public MBlock(IBlockState state) {
		this(state,false);
	}
	
	public MBlock(IBlockState state, boolean hasTileEnt) {
		this.block = state.getBlock();
		this.meta = block.getMetaFromState(state);
		this.state=state;
		this.hasTileEntity=hasTileEnt;
		this.layer=BlockData.getBlockLayer(this);
	}

	public MBlock(Block block, int meta) {
		this(block,meta,false);
	}
	
	public MBlock(String registryname, int meta) {
		this(Block.REGISTRY.getObject(new ResourceLocation(registryname)),meta,false);
	}
	
	public MBlock(Block block, int meta, boolean hasTileEnt) {
		this.block = block;
		this.meta = meta;
		this.state = block.getStateFromMeta(meta);
		this.hasTileEntity=hasTileEnt;
		this.layer=BlockData.getBlockLayer(this);
	}

	public IBlockState getState() {
		return state;
	}
	
	public int getPass() {
		return MathUtil.clamp(layer-1, 0, 1);
	}
	
	@Override
	public boolean equals(Object other) {
		return (other instanceof MBlock) && ((MBlock) other).block == this.block && ((MBlock) other).meta == this.meta;
	}

	/**
	 * REQUIRED FOR SERIALIZATION
	 * @param out
	 * @throws IOException
	 */
	private void writeObject(ObjectOutputStream out) throws IOException {
		out.defaultWriteObject();
		ResourceLocation name = block.getRegistryName();
		out.writeUTF(name.toString());
		//out.writeInt(meta);
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((block == null) ? 0 : block.getRegistryName().hashCode());
		result = prime * result + meta;
		return result;
	}

	/**
	 * REQUIRED FOR SERIALIZATION
	 * @param in
	 * @throws IOException
	 * @throws ClassNotFoundException
	 */
	private void readObject(ObjectInputStream in) throws IOException, ClassNotFoundException {
		in.defaultReadObject();
		String name = in.readUTF();
		//System.out.println("GetBlock:"+name);
		this.block = Block.getBlockFromName(name);
			
		if(this.block!=null) {
		
			this.state= block.getStateFromMeta(this.meta);
		} else {
			MBlock replacement = replacementTable.get(new BlockMeta(name, this.meta));
			
			if(replacement!=null) {
				this.block = replacement.block;
				this.state = replacement.getState();
			} else {
				this.block = Blocks.COBBLESTONE;
				this.state = Blocks.COBBLESTONE.getDefaultState();
			}
		}
		//this.meta = in.read();
	}

	public void tileEntityPostPlacementAction(World w, IBlockState state, BlockPos p, int rotation) {
		
	}

	public boolean hasTileEntity() {
		return hasTileEntity;
	}
	
	protected int getPlacementFlags() {
		return 2;
	}
	
	public void setBlock(World w, MutableBlockPos pos, int rotation) {
		setBlock(w,pos,rotation,null,BiomeColorType.WOODLAND);
	}
	
	public void setBlock(World w, MutableBlockPos pos, int rotation, EnumLootType loottype, BiomeColorType biome) {
		if(pos.getY()>=1) {
			IBlockState targetState = BlockRotator.getRotatedHorizontal(state, rotation);
			w.setBlockState(pos, targetState,getPlacementFlags());
			if(this.hasTileEntity) {
				this.tileEntityPostPlacementAction(w, targetState, pos, rotation);
			}
		}
	}
	
	public void setBlockReplaceableOnly(World w, MutableBlockPos pos, int rotation, EnumLootType loottype, BiomeColorType biome) {
		IBlockState bs = w.getBlockState(pos);
		if (bs.getBlock().isReplaceable(w, pos)) {
			setBlock(w, pos, rotation, loottype, biome);
		}
	}
	
	private static class BlockMeta {
		String blockname;
		int blockid;
		
		public BlockMeta(@Nonnull String blockname, int blockid) {
			super();
			this.blockname = blockname;
			this.blockid = blockid;
		}
		
		@Override
		public int hashCode() {
			final int prime = 31;
			int result = 1;
			result = prime * result + blockid;
			result = prime * result + ((blockname == null) ? 0 : blockname.hashCode());
			return result;
		}
		
		@Override
		public boolean equals(Object obj) {
			if(obj instanceof BlockMeta) {
				BlockMeta other = (BlockMeta) obj;
				return this.blockid==other.blockid && this.blockname.equals(other.blockname);
			}
			return false;
		}
	}
}
