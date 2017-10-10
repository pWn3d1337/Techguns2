package techguns.tileentities;

import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.SPacketUpdateTileEntity;
import net.minecraft.server.management.PlayerChunkMapEntry;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.ItemStackHandler;
import techguns.TGBlocks;
import techguns.blocks.machines.BasicMachine;
import techguns.tileentities.operation.ItemStackHandlerPlus;
import techguns.util.InventoryUtil;

public class BasicInventoryTileEnt extends TileEntity {

	protected ItemStackHandlerPlus inventory;

	/**
	 * Has this tileent rotation saved in the tile?
	 */
	protected boolean hasRotation;
	//How the tile is rotated
	public byte rotation=0;
	
	protected boolean contentsChanged=true;
	
	public BasicInventoryTileEnt(int inventorySize, boolean hasRotation) {
		super();
		this.inventory = new ItemStackHandlerPlus(inventorySize) {

			@Override
			protected void onContentsChanged(int slot) {
				super.onContentsChanged(slot);
				setContentsChanged(true);
			}
			
		};
		this.hasRotation=hasRotation;
	}
	
    public ItemStackHandler getInventory() {
		return inventory;
	}
    
    protected int getUseRangeSquared() {
    	return 64;
    }
    
	public boolean isUseableByPlayer(EntityPlayer player)
    {
        if (this.world.getTileEntity(this.pos) != this)
        {
            return false;
        }
        else
        {
            return player.getDistanceSq((double)this.pos.getX() + 0.5D, (double)this.pos.getY() + 0.5D, (double)this.pos.getZ() + 0.5D) <= getUseRangeSquared();
        }
    }

    /**
	 * Sets all inventory to null, called when wrenched, after inv is saved to nbt tags
	 */
	public void emptyContent(){
		for (int i=0;i<inventory.getSlots();++i){
			this.inventory.setStackInSlot(i, ItemStack.EMPTY);
		}
	}
	
	/**
	 * Helper method to check if ItemStack output can be put into slot
	 * @param output
	 * @param slot
	 * @return
	 */
	protected boolean canOutput(ItemStack output, int slot) {
		if (output.isEmpty()) {
			return true;
		}
		if (this.inventory.getStackInSlot(slot).isEmpty()) {
			return output.getCount() <= this.inventory.getSlotLimit(slot);
		} else {
			if (this.inventory.getStackInSlot(slot).isItemEqual(output) && (this.inventory.getStackInSlot(slot).getCount() + output.getCount() <= this.inventory.getStackInSlot(slot).getMaxStackSize())) {
				return true;
			} else {
				return false;
			}
		}
	}
	
	/**
	 * called when the block is broken
	 */
	public void onBlockBreak(){
		InventoryUtil.dropInventoryItems(world, pos, inventory);
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

	/**
	 * write all data the client needs too, to nbt.
	 * @param compound
	 * @return
	 */
	public void writeClientDataToNBT(NBTTagCompound tags) {
		if (this.hasRotation) {
			tags.setByte("rotation", this.rotation);
		}
	}
	
	/**
	 * read all data the client needs from nbt
	 * @param compound
	 */
	public void readClientDataFromNBT(NBTTagCompound tags) {
		if(this.hasRotation) {
			this.rotation =(byte) (tags.getByte("rotation") % 4);
		}
	}
	
	/**
	 * Called when a gui button is clicked, does nothing on default, override in subclasss
	 * @param id
	 * @param ply
	 * @param data
	 */
	public void buttonClicked(int id, EntityPlayer ply, String data){}
	
	/**
	 * called serverside when this tileent should send out updated to client
	 */
	public void needUpdate(){
		//this.world.markBlockRangeForRenderUpdate(pos, pos);
		if(!this.world.isRemote) {
			
			ChunkPos cp = this.world.getChunkFromBlockCoords(getPos()).getPos();
			PlayerChunkMapEntry entry = ((WorldServer)this.world).getPlayerChunkMap().getEntry(cp.x, cp.z);
			if (entry!=null) {
				entry.sendPacket(this.getUpdatePacket());
			}
		}
		this.markDirty();
	}
	
	@Override
	public NBTTagCompound writeToNBT(NBTTagCompound compound) {
		compound.setTag("inventory", inventory.serializeNBT());
		this.writeClientDataToNBT(compound);
		return super.writeToNBT(compound);
	}
	
	@Override
	public void readFromNBT(NBTTagCompound compound) {
		inventory.deserializeNBT(compound.getCompoundTag("inventory"));
		this.readClientDataFromNBT(compound);
		super.readFromNBT(compound);
	}
	
	@Override
	public boolean hasCapability(Capability<?> capability, EnumFacing facing) {
		return capability == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY || super.hasCapability(capability, facing);
	}
	
	@Override
	public <T> T getCapability(Capability<T> capability, EnumFacing facing) {
		return capability == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY ? (T)inventory : super.getCapability(capability, facing);
	}

	public boolean hasRotation() {
		return hasRotation;
	}
	
	public void rotateTile() {
		if(!this.world.isRemote && this.hasRotation) {
			this.rotation = (byte) (this.rotation+1 %4);
			this.needUpdate();
		}
	}
	
	public void rotateTile(EnumFacing sideHit) {
		this.rotateTile();
	}
	
	public boolean canBeWrenchRotated() {
		return true;
	}
	
	public boolean canBeWrenchDismantled() {
		return true;
	}

	/**
	 * @param contentsChanged the contentsChanged to set
	 */
	public void setContentsChanged(boolean contentsChanged) {
		this.contentsChanged = contentsChanged;
	}

	public boolean getContentsChanged() {
		return contentsChanged;
	}

	protected BasicMachine getMachineBlockType() {
		if(this.hasRotation) {
			return TGBlocks.BASIC_MACHINE;
		} else {
			return TGBlocks.SIMPLE_MACHINE;
		}
	}
	
	@Override
	public boolean shouldRefresh(World world, BlockPos pos, IBlockState oldState, IBlockState newState) {
		if(!this.hasRotation) {
			return (oldState.getBlock()!=newState.getBlock()) || (oldState.getValue(this.getMachineBlockType().MACHINE_TYPE) != newState.getValue(this.getMachineBlockType().MACHINE_TYPE));
		} else {
			return super.shouldRefresh(world, pos, oldState, newState);
		}
	}
	
	
}
