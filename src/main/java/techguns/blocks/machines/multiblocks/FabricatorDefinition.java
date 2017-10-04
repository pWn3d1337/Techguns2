package techguns.blocks.machines.multiblocks;

import java.util.ArrayList;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import techguns.TGBlocks;
import techguns.blocks.machines.EnumMultiBlockMachineType;
import techguns.tileentities.FabricatorTileEntMaster;
import techguns.tileentities.MultiBlockMachineTileEntMaster;

public class FabricatorDefinition extends MultiBlockMachineSchematic {
	
	public FabricatorDefinition() {
		super(FabricatorTileEntMaster.class);
	}
	
	private ArrayList<BlockPos> getBottomRow(BlockPos masterPos, EnumFacing direction){
		masterPos=masterPos.toImmutable();
		ArrayList<BlockPos> bottomRow = new ArrayList<>();
		
		BlockPos behind = masterPos.offset(direction.getOpposite());
		bottomRow.add(behind);
		
		EnumFacing left = direction.rotateY();
		BlockPos leftFront = masterPos.offset(left, 1);
		BlockPos leftBehind = behind.offset(left,1);
		bottomRow.add(leftFront);
		bottomRow.add(leftBehind);
		return bottomRow;
	}
	
	private ArrayList<BlockPos> getTopRow(BlockPos masterPos, EnumFacing direction){
		masterPos=masterPos.toImmutable();
		
		BlockPos behind = masterPos.offset(direction.getOpposite());
		EnumFacing left = direction.rotateY();
		BlockPos leftFront = masterPos.offset(left, 1);
		BlockPos leftBehind = behind.offset(left,1);
		
		ArrayList<BlockPos> topRow = new ArrayList<>();
		topRow.add(masterPos.offset(EnumFacing.UP,1));
		topRow.add(behind.offset(EnumFacing.UP,1));
		topRow.add(leftFront.offset(EnumFacing.UP,1));
		topRow.add(leftBehind.offset(EnumFacing.UP,1));

		return topRow;
	}
	
	@Override
	public boolean checkForm(World w, EntityPlayer ply, BlockPos masterPos, EnumFacing direction) {
		return this.canFormFromSide(direction) &&
				allBlocksMatch(w, ply, getBottomRow(masterPos, direction), TGBlocks.MULTIBLOCK_MACHINE.getDefaultState()
				.withProperty(TGBlocks.MULTIBLOCK_MACHINE.MACHINE_TYPE, EnumMultiBlockMachineType.FABRICATOR_HOUSING)
				.withProperty(TGBlocks.MULTIBLOCK_MACHINE.FORMED, false)
				) && allBlocksMatch(w, ply, getTopRow(masterPos, direction), TGBlocks.MULTIBLOCK_MACHINE.getDefaultState()
						.withProperty(TGBlocks.MULTIBLOCK_MACHINE.MACHINE_TYPE, EnumMultiBlockMachineType.FABRICATOR_GLASS)
						.withProperty(TGBlocks.MULTIBLOCK_MACHINE.FORMED, false)
						);
	}

	
	
	@Override
	public boolean canFormFromSide(EnumFacing side) {
		return side!=null && side!=EnumFacing.UP && side!=EnumFacing.DOWN;
	}

	@Override
	public boolean form(World w, EntityPlayer player, BlockPos masterPos, EnumFacing direction) {
		TileEntity tile = w.getTileEntity(masterPos);
		if(tile!=null && tile instanceof MultiBlockMachineTileEntMaster) {
		
			MultiBlockMachineTileEntMaster master = (MultiBlockMachineTileEntMaster) tile;
			master.form(direction);
			
			ArrayList<BlockPos> glassBlocks = this.getTopRow(masterPos, direction);
			ArrayList<BlockPos> housingBlocks = this.getBottomRow(masterPos, direction);
			
			glassBlocks.forEach(b -> {linkSlave(w,player,b,1,masterPos);
				//TGPackets.network.sendTo(new PacketSpawnParticle("OreClusterPing", b.getX()+0.5d, b.getY()+0.5d, b.getZ()+0.5d), (EntityPlayerMP) player);
			});
			housingBlocks.forEach(b -> {linkSlave(w, player, b, 2, masterPos);
				//TGPackets.network.sendTo(new PacketSpawnParticle("OreClusterPing", b.getX()+0.5d, b.getY()+0.5d, b.getZ()+0.5d), (EntityPlayerMP) player);
			});
			
			//System.out.println("FORMING MULTIBLOCK");
			return true;
		}
		return false;
	}
	
	@Override
	public void unform(World w, MultiBlockMachineTileEntMaster master) {
		BlockPos masterPos = master.getPos();
		EnumFacing direction = master.getMultiblockDirection();
		
		master.unform();
		
		ArrayList<BlockPos> glassBlocks = this.getTopRow(masterPos, direction);
		ArrayList<BlockPos> housingBlocks = this.getBottomRow(masterPos, direction);
		
		glassBlocks.forEach(b -> unlinkSlave(w, b));
		housingBlocks.forEach(b -> unlinkSlave(w, b));
	}
}
