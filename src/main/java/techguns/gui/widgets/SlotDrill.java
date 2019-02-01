package techguns.gui.widgets;

import net.minecraft.item.ItemStack;
import techguns.api.tginventory.ITGSpecialSlot;
import techguns.api.tginventory.TGSlotType;
import techguns.tileentities.OreDrillTileEntMaster;
import techguns.tileentities.operation.ItemStackHandlerPlus;
import techguns.tileentities.operation.ReactionBeamFocus;

public class SlotDrill extends SlotMachineInput {

	OreDrillTileEntMaster tile;
	
	public SlotDrill(ItemStackHandlerPlus itemHandler, int index, int xPosition, int yPosition, OreDrillTileEntMaster tile) {
		super(itemHandler, index, xPosition, yPosition);
		this.tile=tile;
	}

	@Override
	public boolean isItemValid(ItemStack item) {
		if(!tile.isFormed()) return false;
		if(!item.isEmpty() && item.getItem() instanceof ITGSpecialSlot) {
			int radius = tile.getDrillRadius();
			
			ITGSpecialSlot itm = (ITGSpecialSlot) item.getItem();
			return (itm.getSlot(item)==TGSlotType.DRILL_SMALL && radius==0) || (itm.getSlot(item)==TGSlotType.DRILL_MEDIUM && radius>0 && radius<3) || (itm.getSlot(item)==TGSlotType.DRILL_LARGE && radius >=3);
		}
		return false;
	}

	@Override
	public int getSlotStackLimit() {
		return 1;
	}	
	
}
