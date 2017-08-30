package techguns.gui.containers;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.IContainerListener;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.SlotItemHandler;
import techguns.gui.widgets.SlotAmmoPressInput;
import techguns.gui.widgets.SlotItemHandlerOutput;
import techguns.gui.widgets.SlotMachineInput;
import techguns.gui.widgets.SlotMachineUpgrade;
import techguns.tileentities.AmmoPressTileEnt;
import techguns.tileentities.MetalPressTileEnt;
import techguns.tileentities.operation.ItemStackHandlerPlus;

public class MetalPressContainer extends BasicMachineContainer {

	protected MetalPressTileEnt tile;
	private byte lastAutoSwitch = 0;
	
	public static final int FIELD_SYNC_ID_SWITCH = FIELD_SYNC_ID_POWER_STORED+1;
	
	public static final int SLOT_INPUT1_X=110;
	public static final int SLOT_INPUT2_X=130;
	public static final int SLOT_INPUTS_Y=17;
	
	public static final int SLOT_OUTPUT_X=120;
	public static final int SLOT_OUTPUTS_Y=60;
	public static final int SLOT_UPGRADE_X=152;
	
	public MetalPressContainer(InventoryPlayer player, MetalPressTileEnt ent) {
		super(player, ent);
		this.tile=ent;
		
		IItemHandler inventory = ent.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, EnumFacing.SOUTH);
		
		if (inventory instanceof ItemStackHandlerPlus) {
		ItemStackHandlerPlus handler = (ItemStackHandlerPlus) inventory;
	
			this.addSlotToContainer(new SlotMachineInput(handler, MetalPressTileEnt.SLOT_INPUT1, SLOT_INPUT1_X, SLOT_INPUTS_Y));
			this.addSlotToContainer(new SlotMachineInput(handler,  MetalPressTileEnt.SLOT_INPUT2, SLOT_INPUT2_X, SLOT_INPUTS_Y));
		
		
			this.addSlotToContainer(new SlotItemHandlerOutput(inventory, MetalPressTileEnt.SLOT_OUTPUT, SLOT_OUTPUT_X, SLOT_OUTPUTS_Y));
			this.addSlotToContainer(new SlotMachineUpgrade(handler, MetalPressTileEnt.SLOT_UPGRADE, SLOT_UPGRADE_X, SLOT_OUTPUTS_Y));
		}
		
		this.addPlayerInventorySlots(player);
		
	}
	
	@Override
	public void detectAndSendChanges() {
		super.detectAndSendChanges();
		for (int j = 0; j < this.listeners.size(); ++j) {
			IContainerListener listener = this.listeners.get(j);
			
			if (this.lastAutoSwitch!=this.tile.getAutoSplitMode()) {
				listener.sendWindowProperty(this, FIELD_SYNC_ID_SWITCH, this.tile.getAutoSplitMode());
			}
			
		}
		this.lastAutoSwitch=this.tile.getAutoSplitMode();
	}

	@Override
	public void updateProgressBar(int id, int data) {
		if (id==FIELD_SYNC_ID_SWITCH) {
			this.tile.setAutoSplitMode((byte) data);
		} else {
			super.updateProgressBar(id, data);
		}
	}
	
	@Override
	public ItemStack transferStackInSlot(EntityPlayer ply, int id) {
		ItemStack stack = ItemStack.EMPTY;
		Slot slot = (Slot) this.inventorySlots.get(id);

			if(slot.getHasStack()){
				ItemStack stack1 = slot.getStack();
				stack=stack1.copy();
				if (!stack.isEmpty()){
					
					if (id >=MetalPressTileEnt.SLOT_INPUT1 && id<=MetalPressTileEnt.SLOT_UPGRADE){
						//PRESSED IN MACHINE GUI
						if (!this.mergeItemStack(stack1, 3, 39, true)) {
							return ItemStack.EMPTY;
						}
						slot.onSlotChange(stack1, stack);
					} else if (id >=3 && id <39){
						
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
