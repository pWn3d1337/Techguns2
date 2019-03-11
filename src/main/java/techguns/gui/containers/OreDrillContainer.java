package techguns.gui.containers;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.IContainerListener;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;
import techguns.TGPackets;
import techguns.gui.widgets.SlotDrill;
import techguns.gui.widgets.SlotFurnaceFuelTG;
import techguns.gui.widgets.SlotItemHandlerOutput;
import techguns.gui.widgets.SlotMachineInput;
import techguns.gui.widgets.SlotRCFocus;
import techguns.gui.widgets.SlotTG;
import techguns.packets.PacketUpdateTileEntTanks;
import techguns.tileentities.BasicMachineTileEnt;
import techguns.tileentities.OreDrillTileEntMaster;
import techguns.tileentities.ReactionChamberTileEntMaster;
import techguns.tileentities.operation.ItemStackHandlerPlus;

public class OreDrillContainer extends BasicMachineContainer {

	OreDrillTileEntMaster tile;
	
	public static final int SLOT_DRILL_X=30;
	public static final int SLOTS_ROW1_Y=17;
	public static final int SLOTS_FURNACE_Y= 51;
	
	public static final int SLOTS_OUTPUT_X=102;
	
	protected static final int FIELD_SYNC_ID_FUELBUFFER = FIELD_SYNC_ID_POWER_STORED+1;
	protected static final int FIELD_SYNC_ID_FUELBUFFER_MAX = FIELD_SYNC_ID_POWER_STORED+2;
	
	protected int lastFuelbuffer;
	protected int lastFuelbufferMax;
	
	
	public OreDrillContainer(InventoryPlayer player, OreDrillTileEntMaster ent) {
		super(player, ent);
		tile=ent;
		
		IItemHandler inventory = ent.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, EnumFacing.SOUTH);
		
		if (inventory instanceof ItemStackHandlerPlus) {
			ItemStackHandlerPlus handler = (ItemStackHandlerPlus) inventory;
	
			this.addSlotToContainer(new SlotDrill(handler, OreDrillTileEntMaster.SLOT_DRILL, SLOT_DRILL_X, SLOTS_ROW1_Y, tile));
			this.addSlotToContainer(new SlotFurnaceFuelTG(handler, OreDrillTileEntMaster.SLOT_FURNACE, SLOT_DRILL_X, SLOTS_FURNACE_Y));
		
			for (int i=0;i<3;i++){
				for(int j=0;j<3;j++){
					this.addSlotToContainer(new SlotItemHandlerOutput(handler, OreDrillTileEntMaster.SLOT_OUTPUT1+(i*3)+j, SLOTS_OUTPUT_X+18*i, SLOTS_ROW1_Y+18*j));
				}
			}
		}
		
		
		this.addPlayerInventorySlots(player);
	}

	@Override
	public void detectAndSendChanges() {
		super.detectAndSendChanges();
		for (int j = 0; j < this.listeners.size(); ++j)
        {
            IContainerListener listener = this.listeners.get(j);
            
            if (this.lastFuelbuffer!=this.tile.getFuelBuffer()) {
            	listener.sendWindowProperty(this, FIELD_SYNC_ID_FUELBUFFER, this.tile.getFuelBuffer());
            }
            
            if (this.lastFuelbufferMax!=this.tile.getCurrentFuelBufferMax()) {
            	listener.sendWindowProperty(this, FIELD_SYNC_ID_FUELBUFFER_MAX, this.tile.getCurrentFuelBufferMax());
            }

			if (listener instanceof EntityPlayerMP) {
				EntityPlayerMP player = (EntityPlayerMP) listener;
				TGPackets.network.sendTo(new PacketUpdateTileEntTanks(this.tile, this.tile.getPos()), player);
			}
		
            
        }
        this.lastFuelbuffer=this.tile.getFuelBuffer();
        this.lastFuelbufferMax=this.tile.getCurrentFuelBufferMax();
	}



	@Override
	public void updateProgressBar(int id, int data) {
		switch(id) {
		case FIELD_SYNC_ID_FUELBUFFER:
			this.tile.setFuelBuffer(data);
			break;
		case FIELD_SYNC_ID_FUELBUFFER_MAX:
			this.tile.setCurrentFuelBufferMax(data);
			break;
		default:
			super.updateProgressBar(id, data);
		}
	}

	
	@Override
	public ItemStack transferStackInSlot(EntityPlayer ply, int id) {
		ItemStack stack = ItemStack.EMPTY;
		Slot slot = this.inventorySlots.get(id);

			if(slot.getHasStack()){
				ItemStack stack1 = slot.getStack();
				stack=stack1.copy();
				if (!stack.isEmpty()){
					
					int machine_inventory_end = OreDrillTileEntMaster.SLOT_OUTPUT1+OreDrillTileEntMaster.SLOT_OUTPUT_COUNT;
					
					if (id==OreDrillTileEntMaster.SLOT_DRILL){
						//NO SHIFT+CLICK OUT!!!
						return ItemStack.EMPTY;
					} else if (id>=OreDrillTileEntMaster.SLOT_FURNACE && id < machine_inventory_end) {
						
						//PRESSED IN MACHINE GUI 2-10 output, 1 fuelinput
						if (!this.mergeItemStack(stack1, machine_inventory_end, machine_inventory_end+36, false)) {
							return null;
						}
						slot.onSlotChange(stack1, stack);
					} else if (id >=machine_inventory_end && id <machine_inventory_end+36){
						
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
						return null;
					}

					slot.onTake(ply, stack1);
					//slot.onPickupFromSlot(ply, stack1);
				}
			}
		
			return stack;
	}

	
}
