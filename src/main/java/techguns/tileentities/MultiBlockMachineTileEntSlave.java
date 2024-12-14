package techguns.tileentities;

import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.SPacketUpdateTileEntity;
import net.minecraft.server.management.PlayerChunkMapEntry;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;
import techguns.blocks.machines.MultiBlockMachine;

public abstract class MultiBlockMachineTileEntSlave extends TileEntity {

	protected int masterX;
	protected int masterY;
	protected int masterZ;
	protected boolean hasMaster;
	
	/**
	 * 0 - unformed, other values depend on tile entity type
	 */
	protected byte type=0;
	
	private MultiBlockMachineTileEntMaster masterTileEnt=null;
    
	protected MultiBlockMachineTileEntMaster getMaster() {
		if(hasMaster) {
			if(masterTileEnt==null) {
				TileEntity tile = this.world.getTileEntity(new BlockPos(this.masterX, this.masterY, this.masterZ));
				if(tile!=null && tile instanceof MultiBlockMachineTileEntMaster) {
					this.masterTileEnt=(MultiBlockMachineTileEntMaster) tile;
					return this.masterTileEnt;
				}
			} else {
				return masterTileEnt;
			}
		}
		return null;
	}

	public void unform() {
		this.hasMaster=false;
		this.masterTileEnt=null;
		this.setType((byte) 0);
	}
	
	public void setType(byte type) {
		this.type = type;
	}
	
	public void form(BlockPos masterPos, byte type) {
		this.hasMaster=true;
		this.masterX=masterPos.getX();
		this.masterY=masterPos.getY();
		this.masterZ=masterPos.getZ();
		this.type=type;
	}
	
	public int getSlaveType() {
		return this.type;
	}
	
	@Override
	public SPacketUpdateTileEntity getUpdatePacket() {
		NBTTagCompound tags = new NBTTagCompound();
		this.writeClientDataToNBT(tags);
		return new SPacketUpdateTileEntity(pos, 1, tags);
	}

	@Override
	public void onDataPacket(NetworkManager net, SPacketUpdateTileEntity packet) {
		this.readClientDataFromNBT(packet.getNbtCompound());
	}

	@Override
	public NBTTagCompound getUpdateTag() {
		NBTTagCompound tags= super.getUpdateTag();
		this.writeClientDataToNBT(tags);
		return tags;
	}

	@Override
	public void handleUpdateTag(NBTTagCompound tag) {
		super.handleUpdateTag(tag);
		this.readClientDataFromNBT(tag);
	}

	@Override
	public NBTTagCompound writeToNBT(NBTTagCompound compound) {
		this.writeClientDataToNBT(compound);
		return super.writeToNBT(compound);
	}
	
	@Override
	public void readFromNBT(NBTTagCompound compound) {
		this.readClientDataFromNBT(compound);
		super.readFromNBT(compound);
	}
	
	/**
	 * write all data the client needs too, to nbt.
	 * @param compound
	 * @return
	 */
	public void writeClientDataToNBT(NBTTagCompound tags) {
		tags.setBoolean("hasMaster", this.hasMaster);
		if(this.hasMaster) {
			tags.setInteger("masterX", this.masterX);
			tags.setInteger("masterY", this.masterY);
			tags.setInteger("masterZ", this.masterZ);
			tags.setByte("type", this.type);
		}
	}
	
	/**
	 * read all data the client needs from nbt
	 * @param compound
	 */
	public void readClientDataFromNBT(NBTTagCompound tags) {
		this.hasMaster=tags.getBoolean("hasMaster");
		if(this.hasMaster) {
			this.masterX=tags.getInteger("masterX");
			this.masterY=tags.getInteger("masterY");
			this.masterZ=tags.getInteger("masterZ");
			this.type=tags.getByte("type");
		} else {
			this.type=0;
		}
	}

	public boolean hasMaster() {
		return hasMaster;
	}
	
	public void onBlockBreak() {
		if(!this.world.isRemote && this.hasMaster) {
			this.getMaster().onMultiBlockBreak();
		}
	}
	
	public BlockPos getMasterPos() {
		return new BlockPos(masterX,masterY,masterZ);
	}
	
	/**
	 * called serverside when this tileent should send out updated to client
	 */
	public void needUpdate(){
		if(!this.world.isRemote) {
			
			ChunkPos cp = this.world.getChunk(getPos()).getPos();
			PlayerChunkMapEntry entry = ((WorldServer)this.world).getPlayerChunkMap().getEntry(cp.x, cp.z);
			if (entry!=null) {
				entry.sendPacket(this.getUpdatePacket());
			}
		}
		this.markDirty();
	}

	public abstract MultiBlockMachine getMachineBlockType();
	
	@Override
	public boolean shouldRefresh(World world, BlockPos pos, IBlockState oldState, IBlockState newState) {
		return  (oldState.getBlock()!=newState.getBlock()) || (oldState.getValue(getMachineBlockType().MACHINE_TYPE) != newState.getValue(getMachineBlockType().MACHINE_TYPE));
		//System.out.println("Refresh?: "+oldState+ " --> "+newState+ " :"+ret);
		//return ret;
	}	

	public AxisAlignedBB getFormedCollisionBoundingBox() {
		return Block.FULL_BLOCK_AABB;
	}
}
