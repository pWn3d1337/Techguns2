package techguns.gui.containers;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;
import techguns.gui.widgets.SlotItemHandlerOutput;
import techguns.gui.widgets.SlotMachineInput;
import techguns.gui.widgets.SlotMachineUpgrade;
import techguns.gui.widgets.SlotTG;
import techguns.tileentities.BasicMachineTileEnt;
import techguns.tileentities.ChargingStationTileEnt;
import techguns.tileentities.operation.ItemStackHandlerPlus;

public class ChargingStationContainer extends BasicMachineContainer {

	public static final int SLOT_INPUT_X=19;
	public static final int SLOT_OUTPUT_X=68;
	public static final int SLOTS_ROW1_Y=17;
	
	public static final int SLOT_UPGRADE_X=MetalPressContainer.SLOT_UPGRADE_X;
	public static final int SLOT_UPGRADE_Y=MetalPressContainer.SLOT_OUTPUTS_Y;
	
	ChargingStationTileEnt tile;
	public ChargingStationContainer(InventoryPlayer player, ChargingStationTileEnt ent) {
		super(player, ent);
		this.tile=ent;
		IItemHandler inventory = ent.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, EnumFacing.SOUTH);
		
		if (inventory instanceof ItemStackHandlerPlus) {
		ItemStackHandlerPlus handler = (ItemStackHandlerPlus) inventory;
	
			this.addSlotToContainer(new SlotMachineInput(handler, ChargingStationTileEnt.SLOT_INPUT, SLOT_INPUT_X, SLOTS_ROW1_Y));
		
			this.addSlotToContainer(new SlotItemHandlerOutput(inventory,ChargingStationTileEnt.SLOT_OUTPUT, SLOT_OUTPUT_X, SLOTS_ROW1_Y));
			this.addSlotToContainer(new SlotMachineUpgrade(handler,  ChargingStationTileEnt.SLOT_UPGRADE, SLOT_UPGRADE_X, SLOT_UPGRADE_Y));
		}
		
		this.addPlayerInventorySlots(player);
	}
	
	@Override
	public ItemStack transferStackInSlot(EntityPlayer ply, int id) {
		int HIGHEST_SLOT=ChargingStationTileEnt.SLOT_UPGRADE; //index +1
		int MAXSLOTS = HIGHEST_SLOT+36+1; //36 = inventory size
		
		ItemStack stack = ItemStack.EMPTY;
		Slot slot = (Slot) this.inventorySlots.get(id);

			if(slot.getHasStack()){
				ItemStack stack1 = slot.getStack();
				stack=stack1.copy();
				if (!stack.isEmpty()){
					
					if (id >=0 && id<=HIGHEST_SLOT){
						if (!this.mergeItemStack(stack1, HIGHEST_SLOT+1, MAXSLOTS, true)) {
							return ItemStack.EMPTY;
						}
						slot.onSlotChange(stack1, stack);
					} else if (id >HIGHEST_SLOT && id <MAXSLOTS){
						
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
