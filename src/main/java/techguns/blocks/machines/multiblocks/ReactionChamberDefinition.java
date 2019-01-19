package techguns.blocks.machines.multiblocks;

import java.util.ArrayList;
import java.util.HashMap;

import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import techguns.TGBlocks;
import techguns.blocks.machines.EnumMultiBlockMachineType;
import techguns.blocks.machines.MultiBlockMachine;
import techguns.packets.PacketMultiBlockFormInvalidBlockMessage;
import techguns.tileentities.MultiBlockMachineTileEntMaster;
import techguns.tileentities.ReactionChamberTileEntMaster;

public class ReactionChamberDefinition extends MultiBlockMachineSchematic {

	
	public static final float BOTTOM_DIFF=0.25f;
	public static final float MIDDLE_DIFF=0.45f;
	public static final float HEIGHT_DIFF=0.35f;
	
	//assumes Multiblock FACING SOUTH
	public static HashMap<SlavePos, AxisAlignedBB> boundingBoxes = new HashMap<>();
	static {
		//SOUTH = +Z;
		//NORTH = -Z;
		//WEST= -X;
		//EAST= +X
		
		//add 4 corners in bottom row
		boundingBoxes.put(new SlavePos(-1, 0, -1), new AxisAlignedBB(BOTTOM_DIFF, 0, BOTTOM_DIFF, 1, 1, 1));
		boundingBoxes.put(new SlavePos(1, 0, -1), new AxisAlignedBB(0, 0, BOTTOM_DIFF, 1-BOTTOM_DIFF, 1, 1));
		boundingBoxes.put(new SlavePos(-1, 0, 1), new AxisAlignedBB(BOTTOM_DIFF, 0, 0, 1, 1, 1-BOTTOM_DIFF));
		boundingBoxes.put(new SlavePos(1, 0, 1), new AxisAlignedBB(0, 0, 0, 1-BOTTOM_DIFF, 1, 1-BOTTOM_DIFF));
		
		//glass Row 1 corners
		boundingBoxes.put(new SlavePos(-1, 1, -1), new AxisAlignedBB(MIDDLE_DIFF, 0, MIDDLE_DIFF, 1, 1, 1));
		boundingBoxes.put(new SlavePos(1, 1, -1), new AxisAlignedBB(0, 0, MIDDLE_DIFF, 1-MIDDLE_DIFF, 1, 1));
		boundingBoxes.put(new SlavePos(-1, 1, 1), new AxisAlignedBB(MIDDLE_DIFF, 0, 0, 1, 1, 1-MIDDLE_DIFF));
		boundingBoxes.put(new SlavePos(1, 1, 1), new AxisAlignedBB(0, 0, 0, 1-MIDDLE_DIFF, 1, 1-MIDDLE_DIFF));
		
		//glass row 1 sides
		boundingBoxes.put(new SlavePos(-1, 1, 0), new AxisAlignedBB(MIDDLE_DIFF, 0, 0, 1, 1, 1));
		boundingBoxes.put(new SlavePos(1, 1, 0), new AxisAlignedBB(0, 0, 0, 1-MIDDLE_DIFF, 1, 1));
		boundingBoxes.put(new SlavePos(0, 1, -1), new AxisAlignedBB(0, 0, MIDDLE_DIFF, 1, 1, 1));
		boundingBoxes.put(new SlavePos(0, 1, 1), new AxisAlignedBB(0, 0, 0, 1, 1, 1-MIDDLE_DIFF));
		
		//glass Row 2 corners
		boundingBoxes.put(new SlavePos(-1, 2, -1), new AxisAlignedBB(MIDDLE_DIFF, 0, MIDDLE_DIFF, 1, 1, 1));
		boundingBoxes.put(new SlavePos(1, 2, -1), new AxisAlignedBB(0, 0, MIDDLE_DIFF, 1-MIDDLE_DIFF, 1, 1));
		boundingBoxes.put(new SlavePos(-1, 2, 1), new AxisAlignedBB(MIDDLE_DIFF, 0, 0, 1, 1, 1-MIDDLE_DIFF));
		boundingBoxes.put(new SlavePos(1, 2, 1), new AxisAlignedBB(0, 0, 0, 1-MIDDLE_DIFF, 1, 1-MIDDLE_DIFF));
		
		//glass row 2 sides
		boundingBoxes.put(new SlavePos(-1, 2, 0), new AxisAlignedBB(MIDDLE_DIFF, 0, 0, 1, 1, 1));
		boundingBoxes.put(new SlavePos(1, 2, 0), new AxisAlignedBB(0, 0, 0, 1-MIDDLE_DIFF, 1, 1));
		boundingBoxes.put(new SlavePos(0, 2, -1), new AxisAlignedBB(0, 0, MIDDLE_DIFF, 1, 1, 1));
		boundingBoxes.put(new SlavePos(0, 2, 1), new AxisAlignedBB(0, 0, 0, 1, 1, 1-MIDDLE_DIFF));
		
		//top row corners
		boundingBoxes.put(new SlavePos(-1, 3, -1), new AxisAlignedBB(MIDDLE_DIFF, 0, MIDDLE_DIFF, 1, 1-HEIGHT_DIFF, 1));
		boundingBoxes.put(new SlavePos(1, 3, -1), new AxisAlignedBB(0, 0, MIDDLE_DIFF, 1-MIDDLE_DIFF, 1-HEIGHT_DIFF, 1));
		boundingBoxes.put(new SlavePos(-1, 3, 1), new AxisAlignedBB(MIDDLE_DIFF, 0, 0, 1, 1-HEIGHT_DIFF, 1-MIDDLE_DIFF));
		boundingBoxes.put(new SlavePos(1, 3, 1), new AxisAlignedBB(0, 0, 0, 1-MIDDLE_DIFF, 1-HEIGHT_DIFF, 1-MIDDLE_DIFF));
		
		//top row sides
		boundingBoxes.put(new SlavePos(-1, 3, 0), new AxisAlignedBB(MIDDLE_DIFF, 0, 0, 1, 1-HEIGHT_DIFF, 1));
		boundingBoxes.put(new SlavePos(1, 3, 0), new AxisAlignedBB(0, 0, 0, 1-MIDDLE_DIFF, 1-HEIGHT_DIFF, 1));
		boundingBoxes.put(new SlavePos(0, 3, -1), new AxisAlignedBB(0, 0, MIDDLE_DIFF, 1, 1-HEIGHT_DIFF, 1));
		boundingBoxes.put(new SlavePos(0, 3, 1), new AxisAlignedBB(0, 0, 0, 1, 1-HEIGHT_DIFF, 1-MIDDLE_DIFF));
		
	}
	
	public ReactionChamberDefinition() {
		super(ReactionChamberTileEntMaster.class);
	}

	protected ArrayList<BlockPos> getGlassBlocks(BlockPos masterPos, EnumFacing direction){
		ArrayList<BlockPos> positions = new ArrayList<>();
		BlockPos pos = masterPos.toImmutable();
		
		EnumFacing left = direction.rotateY();
		BlockPos pos1 = pos.offset(left).up();
		
		BlockPos pos2 = pos.offset(left.getOpposite()).offset(direction,2).up(2);
		
		BlockPos.getAllInBox(pos1, pos2).forEach(p -> positions.add(p));
		return positions;
	}
	
	protected ArrayList<BlockPos> getEnergyConnector(BlockPos masterPos, EnumFacing direction){
		ArrayList<BlockPos> positions = new ArrayList<>();
		BlockPos pos = masterPos.toImmutable();
		positions.add(pos.offset(direction).up(3));
		return positions;
	}
	
	protected ArrayList<BlockPos> getSideConnectors(BlockPos masterPos, EnumFacing direction){
		ArrayList<BlockPos> positions = new ArrayList<>();
		BlockPos center = masterPos.toImmutable().offset(direction);
		EnumFacing left = direction.rotateY();
		
		positions.add(center.offset(direction));
		positions.add(center.offset(left));
		positions.add(center.offset(left.getOpposite()));
		return positions;
	}
	
	protected ArrayList<BlockPos>getNoConnectorHousings(BlockPos masterPos, EnumFacing direction){
		ArrayList<BlockPos> positions = new ArrayList<BlockPos>();
		BlockPos pos = masterPos.toImmutable();
		
		EnumFacing left = direction.rotateY();
		BlockPos leftp = pos.offset(left);
		BlockPos rightp = pos.offset(left.getOpposite());
		BlockPos centerp = pos.offset(direction);
		
		positions.add(leftp);
		positions.add(rightp);
		positions.add(leftp.offset(direction,2));
		positions.add(rightp.offset(direction,2));
		positions.add(centerp);
		
		positions.add(leftp.up(3));
		positions.add(rightp.up(3));
		positions.add(leftp.offset(direction,2).up(3));
		positions.add(rightp.offset(direction,2).up(3));
		
		BlockPos centeru = centerp.up(3);
		positions.add(centeru.offset(direction));
		positions.add(centeru.offset(direction.getOpposite()));
		positions.add(centeru.offset(left));
		positions.add(centeru.offset(left.getOpposite()));
		
		return positions;
	}
	
	protected ArrayList<BlockPos> getHousingBlocks(BlockPos masterPos, EnumFacing direction){
		ArrayList<BlockPos> positions = this.getNoConnectorHousings(masterPos, direction);
		positions.addAll(this.getEnergyConnector(masterPos, direction));
		positions.addAll(this.getSideConnectors(masterPos, direction));
		return positions;
	}
	
	
	@Override
	public boolean checkForm(World w, EntityPlayer player, BlockPos masterPos, EnumFacing direction) {
		EnumFacing dir = direction.getOpposite();
		
		//IBlockState unformedController = TGBlocks.MULTIBLOCK_MACHINE.getDefaultState().withProperty(TGBlocks.MULTIBLOCK_MACHINE.MACHINE_TYPE, EnumMultiBlockMachineType.REACTIONCHAMBER_CONTROLLER).withProperty(MultiBlockMachine.FORMED, false);
		IBlockState unformedHousing = TGBlocks.MULTIBLOCK_MACHINE.getDefaultState().withProperty(TGBlocks.MULTIBLOCK_MACHINE.MACHINE_TYPE, EnumMultiBlockMachineType.REACTIONCHAMBER_HOUSING).withProperty(MultiBlockMachine.FORMED, false);
		IBlockState unformedGlass = TGBlocks.MULTIBLOCK_MACHINE.getDefaultState().withProperty(TGBlocks.MULTIBLOCK_MACHINE.MACHINE_TYPE, EnumMultiBlockMachineType.REACTIONCHAMBER_GLASS).withProperty(MultiBlockMachine.FORMED, false);
		
		if (this.canFormFromSide(dir) && 
			   allBlocksMatch(w, player, this.getGlassBlocks(masterPos, dir),unformedGlass) && 
			   allBlocksMatch(w, player, this.getHousingBlocks(masterPos, dir),unformedHousing)) {
			return true;
		} else {
			sendErrorMSG(w, masterPos, player, PacketMultiBlockFormInvalidBlockMessage.MSG_TYPE_MULTIBLOCK_ERROR);
			return false;
		}
	}

	@Override
	public boolean form(World w, EntityPlayer player, BlockPos masterPos, EnumFacing direction) {
		EnumFacing dir = direction.getOpposite();
		TileEntity tile = w.getTileEntity(masterPos);
		if(tile!=null && tile instanceof MultiBlockMachineTileEntMaster) {
		
			MultiBlockMachineTileEntMaster master = (MultiBlockMachineTileEntMaster) tile;
			master.form(dir);
		
			ArrayList<BlockPos> housings_EnergyConnector = this.getEnergyConnector(masterPos, dir);
			ArrayList<BlockPos> housings_SideConnectors = this.getSideConnectors(masterPos, dir);
			ArrayList<BlockPos> housings_noConnection = this.getNoConnectorHousings(masterPos, dir);
			
			ArrayList<BlockPos> glassBlocks = this.getGlassBlocks(masterPos, dir);
			
			glassBlocks.forEach(b -> linkSlave(w,player,b,1,masterPos));
			housings_noConnection.forEach(b -> linkSlave(w,player,b,1,masterPos));
			
			housings_SideConnectors.forEach(b -> linkSlave(w,player,b,2,masterPos));
			housings_EnergyConnector.forEach(b -> linkSlave(w,player,b,3,masterPos));
			
		}
		return false;
	}

	@Override
	public void unform(World w, MultiBlockMachineTileEntMaster master) {
		BlockPos masterPos = master.getPos();
		EnumFacing direction = master.getMultiblockDirection();
		
		master.unform();
		
		ArrayList<BlockPos> glassBlocks = this.getGlassBlocks(masterPos, direction);
		ArrayList<BlockPos> housingBlocks = this.getHousingBlocks(masterPos, direction);
		
		glassBlocks.forEach(b -> unlinkSlave(w, b));
		housingBlocks.forEach(b -> unlinkSlave(w, b));
	}

	@Override
	public boolean canFormFromSide(EnumFacing side) {
		return side!=null && side!=EnumFacing.UP && side!=EnumFacing.DOWN;
	}

}
