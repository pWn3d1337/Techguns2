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
import techguns.gui.widgets.SlotFabricator;
import techguns.gui.widgets.SlotItemHandlerOutput;
import techguns.gui.widgets.SlotMachineUpgrade;
import techguns.gui.widgets.SlotTG;
import techguns.tileentities.AmmoPressTileEnt;
import techguns.tileentities.BasicInventoryTileEnt;
import techguns.tileentities.operation.ItemStackHandlerPlus;

public class AmmoPressContainer extends BasicMachineContainer {
	protected AmmoPressTileEnt tile;
	private byte lastBuildPlan = 0;
	
	public static final int FIELD_SYNC_ID_POWER_BUILDPLAN = FIELD_SYNC_ID_POWER_STORED+1;
	
	
	public static final int SLOT_METAL1_X=100;
	public static final int SLOT_METAL2_X=120;
	public static final int SLOT_POWDER_X=140;
	
	public static final int SLOTS_INPUT_Y=17;
	
	public static final int SLOT_OUTPUT_X=120;
	public static final int SLOTS_OUTPUT_Y=60;
	
	public static final int SLOT_UPGRADE_X=152;
	
	public AmmoPressContainer(InventoryPlayer player, AmmoPressTileEnt ent) {
		super(player, ent);
		this.tile=ent;
		
		IItemHandler inventory = ent.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, EnumFacing.SOUTH);
		
		if (inventory instanceof ItemStackHandlerPlus) {
			ItemStackHandlerPlus handler = (ItemStackHandlerPlus) inventory;
		
			this.addSlotToContainer(new SlotAmmoPressInput(handler, AmmoPressTileEnt.SLOT_METAL1, SLOT_METAL1_X, SLOTS_INPUT_Y, SlotTG.INGOTDARKSLOT_TEX));
			this.addSlotToContainer(new SlotAmmoPressInput(handler, AmmoPressTileEnt.SLOT_METAL2, SLOT_METAL2_X, SLOTS_INPUT_Y, SlotTG.INGOTSLOT_TEX));
			this.addSlotToContainer(new SlotAmmoPressInput(handler, AmmoPressTileEnt.SLOT_POWDER, SLOT_POWDER_X, SLOTS_INPUT_Y, SlotFabricator.FABRICATOR_SLOTTEX_POWDER));
		
			this.addSlotToContainer(new SlotItemHandlerOutput(inventory, AmmoPressTileEnt.SLOT_OUTPUT, SLOT_OUTPUT_X, SLOTS_OUTPUT_Y));
			this.addSlotToContainer(new SlotMachineUpgrade(handler, AmmoPressTileEnt.SLOT_UPGRADE, SLOT_UPGRADE_X, SLOTS_OUTPUT_Y));
		}
		
		this.addPlayerInventorySlots(player);
		
	}

	@Override
	public void detectAndSendChanges() {
		super.detectAndSendChanges();
		for (int j = 0; j < this.listeners.size(); ++j) {
			IContainerListener listener = this.listeners.get(j);
			
			if (this.lastBuildPlan!=this.tile.buildPlan) {
				listener.sendWindowProperty(this, FIELD_SYNC_ID_POWER_BUILDPLAN, this.tile.buildPlan);
			}
			
		}
		this.lastBuildPlan=this.tile.buildPlan;
	}

	@Override
	public void updateProgressBar(int id, int data) {
		if (id==FIELD_SYNC_ID_POWER_BUILDPLAN) {
			this.tile.buildPlan=(byte) data;
		} else {
			super.updateProgressBar(id, data);
		}
	}

	@Override
	public ItemStack transferStackInSlot(EntityPlayer ply, int id) {
		ItemStack stack = ItemStack.EMPTY;
		Slot slot =this.inventorySlots.get(id);

			if(slot.getHasStack()){
				ItemStack stack1 = slot.getStack();
				stack=stack1.copy();
				if (!stack.isEmpty()){
					
					if (id >=0 && id<=AmmoPressTileEnt.SLOT_UPGRADE){
						//PRESSED IN MACHINE GUI
						if (!this.mergeItemStack(stack1, AmmoPressTileEnt.SLOT_UPGRADE+1, AmmoPressTileEnt.SLOT_UPGRADE+37, true)) {
							return ItemStack.EMPTY;
						}
						slot.onSlotChange(stack1, stack);
					} else if (id >AmmoPressTileEnt.SLOT_UPGRADE && id <AmmoPressTileEnt.SLOT_UPGRADE+37){
						
						int validslot = AmmoPressTileEnt.getValidSlotForItemInMachine(stack1);
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
