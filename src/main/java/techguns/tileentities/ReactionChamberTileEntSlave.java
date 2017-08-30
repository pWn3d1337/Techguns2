package techguns.tileentities;

import net.minecraft.block.Block;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.energy.CapabilityEnergy;
import net.minecraftforge.fluids.capability.CapabilityFluidHandler;
import net.minecraftforge.items.CapabilityItemHandler;
import techguns.TGBlocks;
import techguns.blocks.machines.MultiBlockMachine;
import techguns.util.BlockUtils;

public class ReactionChamberTileEntSlave extends MultiBlockMachineTileEntSlave {
	
	
	public ReactionChamberTileEntSlave() {
		super();
	}

	@Override
	public boolean hasCapability(Capability<?> capability, EnumFacing facing) {
		if(getMaster()!=null) {
			if (type==2 && (capability == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY || capability == CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY)) {
				return getMaster().hasCapability(capability, facing);
			} else if (type==3 && capability == CapabilityEnergy.ENERGY) {
				return getMaster().hasCapability(capability, facing);
			}
		}
		return super.hasCapability(capability, facing);
	}

	@Override
	public <T> T getCapability(Capability<T> capability, EnumFacing facing) {
		if(getMaster()!=null) {
			if (type==2 && (capability == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY || capability == CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY)) {
				return getMaster().getCapability(capability, facing);
			} else if (type==3 && capability == CapabilityEnergy.ENERGY) {
				return getMaster().getCapability(capability, facing);
			}
		}
		return super.getCapability(capability, facing);
	}

	@Override
	public MultiBlockMachine getMachineBlockType() {
		return TGBlocks.MULTIBLOCK_MACHINE;
	}
	
	@Override
	public AxisAlignedBB getFormedCollisionBoundingBox() {
		if(this.hasMaster) {
			MultiBlockMachineTileEntMaster master = this.getMaster();
			if(master!=null) {
				return master.getBBforSlave(this.getPos());
			}
		}
		return Block.FULL_BLOCK_AABB;
	}
}
