package techguns.tileentities;

import static techguns.gui.ButtonConstants.BUTTON_ID_REDSTONE;

import java.util.List;

import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.server.management.PlayerChunkMapEntry;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import techguns.TGBlocks;
import techguns.TGPackets;
import techguns.Techguns;
import techguns.blocks.BlockTGDoor3x3;
import techguns.packets.PacketDoorStateChange;

public class Door3x3TileEntity extends BasicRedstoneTileEnt {

	protected int textureType=0;
	protected long lastStateChangeTime=0L;
	
	/**
	 * 0 - manual
	 * 1 - redstone
	 * 2 - player-sensor
	 */
	protected byte doormode=0;
	
	//protected boolean openWithRightClick=true;
	protected boolean autoClose=true;
	//protected boolean playerDetector=false;
	
	//public static final int BUTTON_ID_RIGHTCLICK=BUTTON_ID_REDSTONE+1;
	public static final int BUTTON_ID_DOORMODE=BUTTON_ID_REDSTONE+1;
	public static final int BUTTON_ID_AUTOCLOSE=BUTTON_ID_REDSTONE+2;
	//public static final int BUTTON_ID_PLAYERDETECTOR=BUTTON_ID_REDSTONE+3;
	
	public Door3x3TileEntity() {
		super(0, false);
	}
	
	public int getDoorType() {
		return 0;
	}
	
	@Override
	public AxisAlignedBB getRenderBoundingBox() {
		IBlockState state = this.world.getBlockState(getPos());
		if(state.getBlock() == TGBlocks.DOOR3x3) {
			if(state.getValue(BlockTGDoor3x3.ZPLANE)) {
				//return new AxisAlignedBB(getPos().north(2).down(), this.getPos().south().up(2));
				return new AxisAlignedBB(getPos()).grow(1, 1, 3);
			} else {
				//return new AxisAlignedBB(getPos().west(2).down(), this.getPos().east().up(2));
				return new AxisAlignedBB(getPos()).grow(3, 1, 1);
			}
		}
		return super.getRenderBoundingBox();
	}

	@Override
	public ITextComponent getDisplayName() {
		return new TextComponentTranslation(Techguns.MODID+".container.door3x3", new Object[0]);
	}

	@Override
	public void readClientDataFromNBT(NBTTagCompound tags) {
		super.readClientDataFromNBT(tags);
		this.textureType=tags.getByte("texturetype");
	//	this.openWithRightClick=tags.getBoolean("openWithRightClick");
		this.autoClose=tags.getBoolean("autoClose");
		this.doormode=tags.getByte("doormode");
	//	this.playerDetector=tags.getBoolean("playerDetector");
	}

	@Override
	public void writeClientDataToNBT(NBTTagCompound tags) {
		super.writeClientDataToNBT(tags);
		tags.setByte("texturetype",(byte) this.textureType);
	//	tags.setBoolean("openWithRightClick", this.openWithRightClick);
		tags.setBoolean("autoClose", this.autoClose);
	//	tags.setBoolean("playerDetector", this.playerDetector);
		tags.setByte("doormode", this.doormode);
	}

	@Override
	public boolean shouldRefresh(World world, BlockPos pos, IBlockState oldState, IBlockState newState) {
		boolean b = !((oldState.getBlock()==newState.getBlock()) && (newState.getValue(BlockTGDoor3x3.MASTER) && oldState.getValue(BlockTGDoor3x3.MASTER) && 
				(oldState.getValue(BlockTGDoor3x3.ZPLANE)==newState.getValue(BlockTGDoor3x3.ZPLANE))));
		return b;
	}
	
	@Override
	public boolean hasFastRenderer() {
		return true;
	}

	public long getLastStateChangeTime() {
		return lastStateChangeTime;
	}
	
	public void setLastStateChangeTime(long lastStateChangeTime) {		
		this.lastStateChangeTime = lastStateChangeTime;
	}

	public boolean isOpenWithRightClick() {
		return this.doormode==0;
	}

	/*public void setOpenWithRightClick(boolean openWithRightClick) {
		this.openWithRightClick = openWithRightClick;
	}*/

	public boolean isAutoClose() {
		return autoClose;
	}

	public void setAutoClose(boolean autoClose) {
		if(!this.autoClose && autoClose) {
			this.checkScheduleBlockUpdate();
		}
		this.autoClose = autoClose;
	}

	public boolean isPlayerDetector() {
		return this.doormode==2;
	}

	/*public void setPlayerDetector(boolean playerDetector) {
		if(!this.playerDetector && playerDetector) {
			this.checkScheduleBlockUpdate();
		}
		this.playerDetector = playerDetector;
	}*/

	public void checkScheduleBlockUpdate() {
		if(this.world.isRemote) return;
		
		if(!this.autoClose && !(this.doormode==2)) {
			this.world.scheduleBlockUpdate(getPos(), blockType, BlockTGDoor3x3.BLOCK_UPDATE_DELAY, 0);
		}
	}
	
	public boolean checkAutoCloseDelay() {
		long time = System.currentTimeMillis();
		long diff = time - this.lastStateChangeTime;
		return diff > BlockTGDoor3x3.DOOR_AUTOCLOSE_DELAY;
	}
	
	public boolean checkPlayerSensorAutoCloseDelay() {
		long time = System.currentTimeMillis();
		long diff = time - this.lastStateChangeTime;
		return diff > BlockTGDoor3x3.DOOR_OPEN_TIME;
	}
	
	/**
	 * Send out timing to clients
	 */
	public void changeStateServerSide() {
		if (!this.world.isRemote) {
			ChunkPos cp = this.world.getChunkFromBlockCoords(getPos()).getPos();
			PlayerChunkMapEntry entry = ((WorldServer) this.world).getPlayerChunkMap().getEntry(cp.x, cp.z);
			if (entry != null) {
				try {
					List<EntityPlayerMP> players = (List<EntityPlayerMP>) ReactionChamberTileEntMaster.playerChunkMapEntry_Players.get(entry);
					IMessage packet = new PacketDoorStateChange(this);
					for (EntityPlayerMP entityplayermp : players) {
						TGPackets.network.sendTo(packet, entityplayermp);
					}
				} catch (IllegalArgumentException e) {
					e.printStackTrace();
				} catch (IllegalAccessException e) {
					e.printStackTrace();
				}
			}
		}
	}

	@Override
	public void buttonClicked(int id, EntityPlayer ply, String data) {
		/*if(id==BUTTON_ID_RIGHTCLICK) {
			if(this.isUseableByPlayer(ply)) {
				this.setOpenWithRightClick(!this.openWithRightClick);
				this.needUpdate();
			}
		} else*/ if (id==BUTTON_ID_AUTOCLOSE) {
			if(this.isUseableByPlayer(ply)) {
				this.setAutoClose(!this.autoClose);
				this.needUpdate();
			}
		}	else if ( id==BUTTON_ID_DOORMODE) {
			
			if (this.isUseableByPlayer(ply)) {
				byte mode = (byte) (this.doormode+1);
				if(mode>2) {
					mode=0;
				}
				this.setDoormode(mode);
				this.needUpdate();
			}
			
		/*else if(id==BUTTON_ID_PLAYERDETECTOR) {
			if(this.isUseableByPlayer(ply)) {
				this.setPlayerDetector(!this.playerDetector);
				this.needUpdate();
			}
		} */  } else {
			super.buttonClicked(id, ply, data);
		}
	}

	public byte getDoormode() {
		return doormode;
	}

	public void setDoormode(byte doormode) {
		this.doormode = doormode;
		if(this.doormode==2 || this.doormode==0) {
			this.world.scheduleBlockUpdate(getPos(), blockType, BlockTGDoor3x3.BLOCK_UPDATE_DELAY, 0);
		} /*else if (this.doormode==0) {
			if (this.autoClose) {
				IBlockState state = this.world.getBlockState(getPos());
				if(state.getValue(BlockTGDoor3x3.OPENED)) {
					this.world.scheduleBlockUpdate(getPos(), blockType, BlockTGDoor3x3.BLOCK_UPDATE_DELAY, 0);
				}
			}
		}*/
	}

	public boolean isRedstoneMode() {
		return this.doormode==1;
	}	
}
