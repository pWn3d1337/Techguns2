package techguns.gui.widgets;

import java.util.ArrayList;

import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import techguns.Techguns;
import techguns.tileentities.operation.FabricatorRecipe;
import techguns.tileentities.operation.ItemStackHandlerPlus;
import techguns.util.ItemStackOreDict;

public class SlotFabricator extends SlotMachineInput {

	public static final ResourceLocation FABRICATOR_SLOTTEX_WIRES = new ResourceLocation(Techguns.MODID,"gui/emptyslots/emptyslot_wires");
	public static final ResourceLocation FABRICATOR_SLOTTEX_POWDER = new ResourceLocation(Techguns.MODID,"gui/emptyslots/emptyslot_powder");
	public static final ResourceLocation FABRICATOR_SLOTTEX_PLATE = new ResourceLocation(Techguns.MODID,"gui/emptyslots/emptyslot_plate");
	
	protected ArrayList<ItemStackOreDict> validItems;
	
	protected ResourceLocation bgtexture;
	
	public SlotFabricator(ItemStackHandlerPlus itemHandler, int index, int xPosition, int yPosition, ArrayList<ItemStackOreDict> validItems, ResourceLocation bgtexture) {
		super(itemHandler, index, xPosition, yPosition);
		this.validItems=validItems;
		this.bgtexture=bgtexture;
	}

	@Override
	public boolean isItemValid(ItemStack stack) {
		return FabricatorRecipe.itemStackInList(validItems, stack);
	}

	@Override
	public String getSlotTexture() {
		return bgtexture.toString();
	}

	
	

}
