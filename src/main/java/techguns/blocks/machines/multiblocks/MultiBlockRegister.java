package techguns.blocks.machines.multiblocks;

import java.util.HashMap;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import techguns.tileentities.FabricatorTileEntMaster;
import techguns.tileentities.OreDrillTileEntMaster;
import techguns.tileentities.ReactionChamberTileEntMaster;

public class MultiBlockRegister {
	public static HashMap<Class<? extends TileEntity>, MultiBlockMachineSchematic> REGISTER = new HashMap<>();
	
	static {
		REGISTER.put(FabricatorTileEntMaster.class, new FabricatorDefinition());
		REGISTER.put(ReactionChamberTileEntMaster.class, new ReactionChamberDefinition());
		REGISTER.put(OreDrillTileEntMaster.class, new OreDrillDefinition());
	}
	
	public static boolean canFormFromSide(TileEntity tile, EnumFacing sideHit) {
		MultiBlockMachineSchematic multiblock = REGISTER.get(tile.getClass());
		if(multiblock!=null) {
			return multiblock.canFormFromSide(sideHit);
		}
		return false;
	}
	
	public static boolean canForm(TileEntity tile, EntityPlayer ply, EnumFacing sideHit) {
		
		MultiBlockMachineSchematic multiblock = REGISTER.get(tile.getClass());
		if(multiblock!=null) {
			return multiblock.checkForm(tile.getWorld(), ply, tile.getPos(), sideHit);
		}
		
		return false;
	}
	
	public static boolean form(TileEntity tile, EntityPlayer ply, EnumFacing sideHit) {
		MultiBlockMachineSchematic multiblock = REGISTER.get(tile.getClass());
		if(multiblock!=null) {
			return multiblock.form(tile.getWorld(), ply, tile.getPos(), sideHit);
		}
		return false;
	}
}
