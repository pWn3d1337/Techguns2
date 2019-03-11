package techguns.gui.containers;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;
import techguns.gui.widgets.SlotFabricator;
import techguns.gui.widgets.SlotItemHandlerOutput;
import techguns.gui.widgets.SlotMachineInput;
import techguns.gui.widgets.SlotMachineUpgrade;
import techguns.tileentities.FabricatorTileEntMaster;
import techguns.tileentities.operation.FabricatorRecipe;
import techguns.tileentities.operation.ItemStackHandlerPlus;

public class FabricatorContainer extends BasicMachineContainer {
	protected FabricatorTileEntMaster tile;
	
	public static final int SLOT_INPUT1_X=19;
	public static final int SLOT_WIRES_X=47;
	public static final int SLOT_POWDER_X=68;
	public static final int SLOT_PLATE_X=89;
	
	public static final int SLOTS_ROW1_Y=17;
	
	public static final int SLOT_OUTPUT_X=116;
	public static final int SLOT_OUTPUT_Y=50;
	
	public static final int SLOT_UPGRADE_X=150;
	public static final int SLOT_UPGRADE_Y=50;
	
	public FabricatorContainer(InventoryPlayer player, FabricatorTileEntMaster ent) {
		super(player, ent);
		this.tile=ent;
		
		IItemHandler inventory = ent.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, EnumFacing.SOUTH);
		
		if (inventory instanceof ItemStackHandlerPlus) {
		ItemStackHandlerPlus handler = (ItemStackHandlerPlus) inventory;
	
			this.addSlotToContainer(new SlotMachineInput(handler, FabricatorTileEntMaster.SLOT_INPUT1, SLOT_INPUT1_X, SLOTS_ROW1_Y));
			this.addSlotToContainer(new SlotFabricator(handler,  FabricatorTileEntMaster.SLOT_WIRES, SLOT_WIRES_X, SLOTS_ROW1_Y, FabricatorRecipe.items_wireslot,SlotFabricator.FABRICATOR_SLOTTEX_WIRES));
			this.addSlotToContainer(new SlotFabricator(handler,  FabricatorTileEntMaster.SLOT_POWDER, SLOT_POWDER_X, SLOTS_ROW1_Y, FabricatorRecipe.items_powderslot,SlotFabricator.FABRICATOR_SLOTTEX_POWDER));
			this.addSlotToContainer(new SlotFabricator(handler,  FabricatorTileEntMaster.SLOT_PLATE, SLOT_PLATE_X, SLOTS_ROW1_Y, FabricatorRecipe.items_plateslot,SlotFabricator.FABRICATOR_SLOTTEX_PLATE));
		
			this.addSlotToContainer(new SlotItemHandlerOutput(inventory, FabricatorTileEntMaster.SLOT_OUTPUT, SLOT_OUTPUT_X, SLOT_OUTPUT_Y));
			this.addSlotToContainer(new SlotMachineUpgrade(handler,  FabricatorTileEntMaster.SLOT_UPGRADE, SLOT_UPGRADE_X, SLOT_UPGRADE_Y));
		}
		
		this.addPlayerInventorySlots(player);
	}

	@Override
	public ItemStack transferStackInSlot(EntityPlayer ply, int id) {
		int MAXSLOTS = FabricatorTileEntMaster.SLOT_UPGRADE+36+1; //36 = inventory size
		
		ItemStack stack = ItemStack.EMPTY;
		Slot slot = (Slot) this.inventorySlots.get(id);

			if(slot.getHasStack()){
				ItemStack stack1 = slot.getStack();
				stack=stack1.copy();
				if (!stack.isEmpty()){
					
					if (id >=0 && id<=FabricatorTileEntMaster.SLOT_UPGRADE){
						if (!this.mergeItemStack(stack1, FabricatorTileEntMaster.SLOT_UPGRADE+1, MAXSLOTS, false)) {
							return ItemStack.EMPTY;
						}
						slot.onSlotChange(stack1, stack);
					} else if (id >FabricatorTileEntMaster.SLOT_UPGRADE && id <MAXSLOTS){
						
						int validslot = tile.getValidSlotForItemInMachine(stack1);
						//System.out.println("put it in slot"+validslot);
						if (validslot >=0){
							
							if(!this.mergeItemStack(stack1, validslot, validslot+1, false)){
								return ItemStack.EMPTY;
							}
							slot.onSlotChange(stack1, stack);
							
						} else {
							return ItemStack.EMPTY;
						}
						
						
					}

					if (stack1.getCount() == 0) {
						slot.putStack(ItemStack.EMPTY);
					} else {
						slot.onSlotChanged();
					}

					if (stack1.getCount() == stack.getCount()) {
						return ItemStack.EMPTY;
					}

					slot.onTake(ply, stack1);
				}
			}
		
			return stack;
	}

}
