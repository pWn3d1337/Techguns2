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
import techguns.tileentities.GrinderTileEnt;
import techguns.tileentities.operation.ItemStackHandlerPlus;

public class GrinderContainer extends BasicMachineContainer {
	protected GrinderTileEnt tile;
	
	public static final int SLOT_INPUT_X=18;
	
	public static final int SLOTS_INPUT_Y=17;
	
	public static final int SLOT_OUTPUT0_X=80;
	
	public static final int SLOT_UPGRADE_Y=60;
	
	public static final int SLOT_UPGRADE_X=152;
	
	public GrinderContainer(InventoryPlayer player, GrinderTileEnt ent) {
		super(player, ent);
		this.tile=ent;
		
		IItemHandler inventory = ent.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, EnumFacing.SOUTH);
		
		if (inventory instanceof ItemStackHandlerPlus) {
			ItemStackHandlerPlus handler = (ItemStackHandlerPlus) inventory;
		
			this.addSlotToContainer(new SlotMachineInput(handler, GrinderTileEnt.SLOT_INPUT, SLOT_INPUT_X, SLOTS_INPUT_Y));
			this.addSlotToContainer(new SlotMachineUpgrade(handler, GrinderTileEnt.SLOT_UPGRADE, SLOT_UPGRADE_X, SLOT_UPGRADE_Y));		
		
			for (int i=0;i<3;i++){
				for(int j=0;j<3;j++){
					//System.out.println("AddSlot: "+(GrinderTileEnt.SLOT_OUTPUT0+i*3+j) +" AT:"+ (SLOT_OUTPUT0_X+i*18) + ","+ (SLOTS_INPUT_Y+i*18));
					this.addSlotToContainer(new SlotItemHandlerOutput(inventory, GrinderTileEnt.SLOT_OUTPUT0+i*3+j, SLOT_OUTPUT0_X+j*18, SLOTS_INPUT_Y+i*18));
				}
			}
			
		}
		
		this.addPlayerInventorySlots(player);
	}

	@Override
	public ItemStack transferStackInSlot(EntityPlayer ply, int id) {
		int MAXSLOTS = GrinderTileEnt.SLOT_OUTPUT_LAST+36; //36 = inventory size
		
		ItemStack stack = ItemStack.EMPTY;
		Slot slot = (Slot) this.inventorySlots.get(id);

			if(slot.getHasStack()){
				ItemStack stack1 = slot.getStack();
				stack=stack1.copy();
				if (!stack.isEmpty()){
					
					if (id >=0 && id<=GrinderTileEnt.SLOT_UPGRADE){
						if (!this.mergeItemStack(stack1, GrinderTileEnt.SLOT_UPGRADE+1, MAXSLOTS, true)) {
							return ItemStack.EMPTY;
						}
						slot.onSlotChange(stack1, stack);
					} else if (id >= GrinderTileEnt.SLOT_OUTPUT0 && id<GrinderTileEnt.SLOT_OUTPUT_LAST) {
						//PRESSED IN MACHINE GUI
						if (!this.mergeItemStack(stack1, GrinderTileEnt.SLOT_OUTPUT_LAST+1, MAXSLOTS, false)) {
							return ItemStack.EMPTY;
						}
						slot.onSlotChange(stack1, stack);
						
					} else if (id >= GrinderTileEnt.SLOT_OUTPUT_LAST && id <=MAXSLOTS){
						
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
