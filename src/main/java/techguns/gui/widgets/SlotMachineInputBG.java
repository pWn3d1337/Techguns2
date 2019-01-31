package techguns.gui.widgets;

import net.minecraft.util.ResourceLocation;
import techguns.tileentities.operation.ItemStackHandlerPlus;

public class SlotMachineInputBG extends SlotMachineInput {

	protected ResourceLocation bgtexture;
	
	public SlotMachineInputBG(ItemStackHandlerPlus itemHandler, int index, int xPosition, int yPosition, ResourceLocation bgtexture) {
		super(itemHandler, index, xPosition, yPosition);
		this.bgtexture=bgtexture;
	}

	@Override
	public String getSlotTexture() {
		return bgtexture.toString();
	}	
}
