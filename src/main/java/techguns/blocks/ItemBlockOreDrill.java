package techguns.blocks;

import java.util.ArrayList;

import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.world.World;
import techguns.TGOreClusters;
import techguns.TGPackets;
import techguns.blocks.machines.BlockOreDrill;
import techguns.blocks.machines.EnumOreDrillType;
import techguns.blocks.machines.multiblocks.OreDrillDefinition;
import techguns.packets.PacketMultiBlockFormInvalidBlockMessage;
import techguns.util.TextUtil;

public class ItemBlockOreDrill extends GenericItemBlockMetaMachineBlock {
	protected final BlockOreDrill block;
	
	public ItemBlockOreDrill(BlockOreDrill block) {
		super(block);
		this.block=block;
	}

	@Override
	public boolean canPlaceBlockOnSide(World world, BlockPos posIn, EnumFacing side, EntityPlayer player,
			ItemStack stack) {
		IBlockState state = block.getStateFromMeta(stack.getItemDamage());
		
		BlockPos pos = posIn.offset(side);
		
		if(state.getValue(block.MACHINE_TYPE)==EnumOreDrillType.ROD) {
			//System.out.println("Trying to place rod");
			
			BlockPos clusterPos = checkForOreClusterInNeighbourhood(world, pos);			
			if (clusterPos !=null){
				//System.out.println("Nearby cluster");
				
				IBlockState bs = world.getBlockState(clusterPos);

				//Check if cluster has a connected rod already	
				if (getHasConnectedDrillRod(world, clusterPos, new ArrayList<BlockPos>(), bs, this.block.getDefaultState().withProperty(block.MACHINE_TYPE, EnumOreDrillType.ROD))){

					if(world.isRemote){
						//player.addChatMessage(new ChatComponentText(TextUtil.trans("techguns.msg.error.alreadyrod")));
						player.sendStatusMessage(new TextComponentString(TextUtil.trans("techguns.msg.error.alreadyrod")), true);
						//TGPackets.network.sendTo(new PacketMultiBlockFormInvalidBlockMessage(pos,PacketMultiBlockFormInvalidBlockMessage.MSG_TYPE_ORE_DRILL_ROD_PLACEMENT_ERROR), (EntityPlayerMP) player);
					}
					return false;
				} else {
					return super.canPlaceBlockOnSide(world, posIn, side, player, stack);
				}

			} else {
				//System.out.println("No nearby cluster");
				// Check for other rod neighbour
				// System.out.println("No cluster, check for rod");
				if (checkForRodInNeighbourhood(world, pos)) {
					//System.out.println("Nearby rod");
					return super.canPlaceBlockOnSide(world, posIn, side, player, stack);
				} else {
					// System.out.println("No Rod, can't place");
					player.sendStatusMessage(new TextComponentString(TextUtil.trans("techguns.msg.error.rodplacement")), true);
					return false;

				}
			}
		} else if(state.getValue(block.MACHINE_TYPE)==EnumOreDrillType.CONTROLLER) {
			//System.out.println("Trying to place controller");
			
			if( checkForControllerInNeighbourhood(world, pos) ){
				if(world.isRemote){
					//player.addChatMessage(new ChatComponentText(TextUtil.trans("techguns.msg.error.alreadyrod")));
					player.sendStatusMessage(new TextComponentString(TextUtil.transTG("msg.error.oredrill.controllernext")), true);
					
					//TGPackets.network.sendTo(new PacketMultiBlockFormInvalidBlockMessage(pos,PacketMultiBlockFormInvalidBlockMessage.MSG_TYPE_ORE_DRILL_CONTROLLER_PLACEMENT_ERROR), (EntityPlayerMP) player);
				}
				return false;
			} else {
				//System.out.println("Controller is fine");
				return super.canPlaceBlockOnSide(world, posIn, side, player, stack);
			}
			
		} else { 
		
			return super.canPlaceBlockOnSide(world, posIn, side, player, stack);
		}
	}

	
	public static BlockPos checkForOreClusterInNeighbourhood(World w, BlockPos p){
		for(EnumFacing f: EnumFacing.VALUES) {
			if (OreDrillDefinition.isOreCluster(w, p.offset(f))) {
				return p.offset(f);
			}
		}
		return null;
	}
	
	public boolean checkForRodInNeighbourhood(World w, BlockPos p){
		for(EnumFacing f: EnumFacing.VALUES) {
			IBlockState bs = w.getBlockState(p.offset(f));
			if(bs==block.getDefaultState().withProperty(block.MACHINE_TYPE, EnumOreDrillType.ROD)) {
				return true;
			}
		}
		return false;
	}
	
	public boolean checkForControllerInNeighbourhood(World w, BlockPos p){
		for(EnumFacing f: EnumFacing.VALUES) {
			IBlockState bs = w.getBlockState(p.offset(f));
			//System.out.println("Checking:"+f+" ->"+bs);
			if(bs.getBlock()==this.block) {
				return bs.getValue(block.MACHINE_TYPE)== EnumOreDrillType.CONTROLLER;
			}
		}
		return false;
	}
	
	public boolean getHasConnectedDrillRod(World world,BlockPos p, ArrayList<BlockPos> visited, IBlockState clusterblock, IBlockState drillRodBlock){

		//Block target = world.getBlock(x,y,z);
		//int targetMeta = world.getBlockMetadata(x, y, z);
		//BlockCoords block=new BlockCoords(x,y,z);
		
		IBlockState target = world.getBlockState(p);
		if (!visited.contains(p)){
			if (target == clusterblock) {
				visited.add(p);

				boolean b1 = getHasConnectedDrillRod(world, p.offset(EnumFacing.DOWN), visited, clusterblock, drillRodBlock);
				boolean b2 = getHasConnectedDrillRod(world, p.offset(EnumFacing.UP), visited, clusterblock, drillRodBlock);
				boolean b3 = getHasConnectedDrillRod(world, p.offset(EnumFacing.NORTH), visited, clusterblock, drillRodBlock);
				boolean b4 = getHasConnectedDrillRod(world, p.offset(EnumFacing.SOUTH), visited, clusterblock, drillRodBlock);
				boolean b5 = getHasConnectedDrillRod(world, p.offset(EnumFacing.WEST), visited, clusterblock, drillRodBlock);
				boolean b6 = getHasConnectedDrillRod(world, p.offset(EnumFacing.EAST), visited, clusterblock, drillRodBlock);
				
				return b1||b2||b3||b4||b5||b6;
			} else if(target == drillRodBlock) {
				
				return true;
				
			} else {
				//stop recursion, no cluster
				return false;
			}
		} else {
			return false;
		}
	}
}
