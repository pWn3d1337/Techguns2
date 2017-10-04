package techguns.tileentities;

import net.minecraft.block.Block;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.energy.CapabilityEnergy;
import net.minecraftforge.items.CapabilityItemHandler;
import techguns.TGBlocks;
import techguns.blocks.machines.MultiBlockMachine;

public class FabricatorTileEntSlave extends MultiBlockMachineTileEntSlave {

	protected static final AxisAlignedBB BB_GLASS_FORMED = new AxisAlignedBB(0,0,0,1,0.9f,1);
	
	public FabricatorTileEntSlave() {
		super();
	}

	@Override
	public boolean hasCapability(Capability<?> capability, EnumFacing facing) {
		if (type==2) {
			if (capability == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY || capability == CapabilityEnergy.ENERGY) {
				if(getMaster()!=null) {
					return getMaster().hasCapability(capability, facing);
				}
			}
		}
		return super.hasCapability(capability, facing);
	}

	@Override
	public <T> T getCapability(Capability<T> capability, EnumFacing facing) {
		if (type==2) {
			if (capability == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY || capability == CapabilityEnergy.ENERGY) {
				if(getMaster()!=null) {
					return getMaster().getCapability(capability, facing);
				}
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
		if(this.hasMaster && this.type == 1) {
			return BB_GLASS_FORMED;
		}
		return Block.FULL_BLOCK_AABB;
	}
}
