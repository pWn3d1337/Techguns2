package techguns.blocks.machines.multiblocks;

import java.util.ArrayList;

import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import techguns.TGPackets;
import techguns.blocks.machines.MultiBlockMachine;
import techguns.packets.PacketMultiBlockFormInvalidBlockMessage;
import techguns.packets.PacketSpawnParticle;
import techguns.tileentities.MultiBlockMachineTileEntMaster;
import techguns.tileentities.MultiBlockMachineTileEntSlave;

public abstract class MultiBlockMachineSchematic {

	protected Class<? extends MultiBlockMachineTileEntMaster> mastertileClazz;
	
	public MultiBlockMachineSchematic(Class<? extends MultiBlockMachineTileEntMaster> mastertileClazz) {
		super();
		this.mastertileClazz = mastertileClazz;
	}

	public abstract boolean checkForm(World w, EntityPlayer player, BlockPos masterPos, EnumFacing direction);
	
	public abstract boolean form(World w, EntityPlayer player, BlockPos masterPos, EnumFacing direction);
	
	public abstract void unform(World w, MultiBlockMachineTileEntMaster master);
	
	public abstract boolean canFormFromSide(EnumFacing side);
	
	protected static boolean allBlocksMatch(World w, EntityPlayer player, ArrayList<BlockPos> positions, IBlockState blockstate) {
		return allBlocksMatch(w, player, positions, blockstate, true);
	}
	
	protected static boolean allBlocksMatch(World w, EntityPlayer player, ArrayList<BlockPos> positions, IBlockState blockstate, boolean message) {
		boolean valid=true;
		for (int i = 0;i<positions.size();i++) {
			BlockPos p = positions.get(i);

			if(w.getBlockState(p)!=blockstate) {
				/*if(!w.isRemote) {
					TGPackets.network.sendTo(new PacketSpawnParticle("MultiblockInvalidPing", p.getX()+0.5d, p.getY()+0.5d, p.getZ()+0.5d), (EntityPlayerMP) player);
					TGPackets.network.sendTo(new PacketMultiBlockFormInvalidBlockMessage(p), (EntityPlayerMP) player);
				}*/
				sendErrorPing(w, p, player,0, message);
				
				valid=false;
			}
		}
		return valid;
	}
	
	protected static void sendErrorPing(World w, BlockPos p, EntityPlayer player, int type, boolean message) {
		if(!w.isRemote) {
			TGPackets.network.sendTo(new PacketSpawnParticle("MultiblockInvalidPing", p.getX()+0.5d, p.getY()+0.5d, p.getZ()+0.5d), (EntityPlayerMP) player);
			if(message)TGPackets.network.sendTo(new PacketMultiBlockFormInvalidBlockMessage(p,type), (EntityPlayerMP) player);
		}
	}
	
	protected static void sendErrorMSG(World w, BlockPos p, EntityPlayer player, int msgID) {
		if(!w.isRemote) {
			//TGPackets.network.sendTo(new PacketSpawnParticle("MultiblockInvalidPing", p.getX()+0.5d, p.getY()+0.5d, p.getZ()+0.5d), (EntityPlayerMP) player);
			TGPackets.network.sendTo(new PacketMultiBlockFormInvalidBlockMessage(p,msgID), (EntityPlayerMP) player);
		}
	}
	
	protected void linkSlave(World w, EntityPlayer p, BlockPos pos, int type, BlockPos masterPos) {
		TileEntity tile = w.getTileEntity(pos);
		if(tile instanceof MultiBlockMachineTileEntSlave) {
			MultiBlockMachineTileEntSlave slave = (MultiBlockMachineTileEntSlave) tile;
			slave.form(masterPos, (byte) type);
			
			if(!w.isRemote) {
				updateBlockStateForm(w, pos,type);
				slave.needUpdate();
			}
		}
	}
	
	protected void updateBlockStateForm(World w, BlockPos pos, int type) {
		w.setBlockState(pos, w.getBlockState(pos).withProperty(MultiBlockMachine.FORMED, true), 3);
	}
	
	protected void updateBlockStateUnform(World w, BlockPos pos, IBlockState bs, int type) {
		w.setBlockState(pos, bs.withProperty(MultiBlockMachine.FORMED, false), 3);
	}
	
	protected void unlinkSlave(World w, BlockPos pos) {
		TileEntity tile = w.getTileEntity(pos);
		if(tile instanceof MultiBlockMachineTileEntSlave) {
			MultiBlockMachineTileEntSlave slave = (MultiBlockMachineTileEntSlave) tile;
			int type = slave.getSlaveType();
			slave.unform();
			if(!w.isRemote) {
				IBlockState bs = w.getBlockState(pos);
				if (bs.getBlock() == slave.getMachineBlockType()) {
					updateBlockStateUnform(w, pos, bs, type);
					slave.needUpdate();
				}
			}
		} 
	}
}
