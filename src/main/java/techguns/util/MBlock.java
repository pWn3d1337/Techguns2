package techguns.util;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public class MBlock implements Serializable {
	
	private static final long serialVersionUID = 1L;	
	
	public transient Block block;
	public int meta;
	
	protected transient IBlockState state;

	protected boolean hasTileEntity=false;
	
	public MBlock(MBlock other) {
		this.block=other.block;
		this.meta=other.meta;
		this.state=other.state;
		this.hasTileEntity=other.hasTileEntity;
	}
	
	public MBlock(IBlockState state) {
		this(state,false);
	}
	
	public MBlock(IBlockState state, boolean hasTileEnt) {
		this.block = state.getBlock();
		this.meta = block.getMetaFromState(state);
		this.state=state;
		this.hasTileEntity=hasTileEnt;
	}

	public MBlock(Block block, int meta) {
		this(block,meta,false);
	}
	
	public MBlock(Block block, int meta, boolean hasTileEnt) {
		this.block = block;
		this.meta = meta;
		this.state = block.getStateFromMeta(meta);
		this.hasTileEntity=hasTileEnt;
	}

	public IBlockState getState() {
		return state;
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
		this.block = Block.getBlockFromName(name);
		this.state= block.getStateFromMeta(this.meta);
		//this.meta = in.read();
	}

	public void tileEntityPostPlacementAction(World w, IBlockState state, BlockPos p, int rotation) {
		
	}

	public boolean hasTileEntity() {
		return hasTileEntity;
	}
	
	
}
