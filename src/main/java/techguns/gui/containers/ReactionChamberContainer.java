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
import techguns.gui.widgets.SlotItemHandlerOutput;
import techguns.gui.widgets.SlotMachineInput;
import techguns.gui.widgets.SlotRCFocus;
import techguns.packets.PacketUpdateTileEntTanks;
import techguns.tileentities.BasicMachineTileEnt;
import techguns.tileentities.FabricatorTileEntMaster;
import techguns.tileentities.ReactionChamberTileEntMaster;
import techguns.tileentities.operation.FabricatorRecipe;
import techguns.tileentities.operation.ItemStackHandlerPlus;

public class ReactionChamberContainer extends BasicMachineContainer {
	ReactionChamberTileEntMaster tile;
	
	public static final int SLOT_INPUT_X=35;
	public static final int SLOT_FOCUS_X=93;
	
	public static final int SLOTS_ROW1_Y=17;
	
	public static final int SLOT_OUTPUT_X=134;
	public static final int SLOT_OUTPUT_Y=17;
	
	protected static final int FIELD_SYNC_ID_LIQUIDLEVEL = FIELD_SYNC_ID_POWER_STORED+1;
	protected static final int FIELD_SYNC_ID_INTENSITY = FIELD_SYNC_ID_POWER_STORED+2;
	
	protected int lastLiquidlevel;
	protected int lastIntensity;
	
	public ReactionChamberContainer(InventoryPlayer player, ReactionChamberTileEntMaster ent) {
		super(player, ent);
		this.tile=ent;
		
		IItemHandler inventory = ent.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, EnumFacing.SOUTH);
		
		if (inventory instanceof ItemStackHandlerPlus) {
		ItemStackHandlerPlus handler = (ItemStackHandlerPlus) inventory;
	
			this.addSlotToContainer(new SlotMachineInput(handler, ReactionChamberTileEntMaster.SLOT_INPUT, SLOT_INPUT_X, SLOTS_ROW1_Y));
		
			this.addSlotToContainer(new SlotRCFocus(handler, ReactionChamberTileEntMaster.SLOT_FOCUS, SLOT_FOCUS_X, SLOTS_ROW1_Y));
			
			this.addSlotToContainer(new SlotItemHandlerOutput(inventory, ReactionChamberTileEntMaster.SLOT_OUTPUT, SLOT_OUTPUT_X, SLOT_OUTPUT_Y));
			this.addSlotToContainer(new SlotItemHandlerOutput(inventory, ReactionChamberTileEntMaster.SLOT_OUTPUT+1, SLOT_OUTPUT_X+18, SLOT_OUTPUT_Y));
			this.addSlotToContainer(new SlotItemHandlerOutput(inventory, ReactionChamberTileEntMaster.SLOT_OUTPUT+2, SLOT_OUTPUT_X, SLOT_OUTPUT_Y+18));
			this.addSlotToContainer(new SlotItemHandlerOutput(inventory, ReactionChamberTileEntMaster.SLOT_OUTPUT+3, SLOT_OUTPUT_X+18, SLOT_OUTPUT_Y+18));
		}
		
		this.addPlayerInventorySlots(player);
	}

	
	
	@Override
	public void detectAndSendChanges() {
		super.detectAndSendChanges();
		for (int j = 0; j < this.listeners.size(); ++j)
        {
            IContainerListener listener = this.listeners.get(j);
            
            if (this.lastLiquidlevel!=this.tile.getLiquidLevel()) {
            	listener.sendWindowProperty(this, FIELD_SYNC_ID_LIQUIDLEVEL, this.tile.getLiquidLevel());
            }
            
            if (this.lastIntensity!=this.tile.getIntensity()) {
            	listener.sendWindowProperty(this, FIELD_SYNC_ID_INTENSITY, this.tile.getIntensity());
            }
            

			if (listener instanceof EntityPlayerMP) {
				EntityPlayerMP player = (EntityPlayerMP) listener;
				TGPackets.network.sendTo(new PacketUpdateTileEntTanks(this.tile, this.tile.getPos()), player);
			}
		
            
        }
        this.lastIntensity=this.tile.getIntensity();
        this.lastLiquidlevel=this.tile.getLiquidLevel();
	}



	@Override
	public void updateProgressBar(int id, int data) {
		switch(id) {
		case FIELD_SYNC_ID_LIQUIDLEVEL:
			this.tile.setLiquidLevel((byte) data);
			break;
		case FIELD_SYNC_ID_INTENSITY:
			this.tile.setIntensity((byte) data);
			break;
		default:
			super.updateProgressBar(id, data);
		}
	}



	@Override
	public ItemStack transferStackInSlot(EntityPlayer ply, int id) {
		
		int HIGHEST_MACHINE_SLOT = ReactionChamberTileEntMaster.SLOT_OUTPUT+ReactionChamberTileEntMaster.OUTPUT_SLOTS_COUNT-1;
		ItemStack stack = ItemStack.EMPTY;
		Slot slot = (Slot) this.inventorySlots.get(id);

			if(slot.getHasStack()){
				ItemStack stack1 = slot.getStack();
				stack=stack1.copy();
				if (!stack.isEmpty()){
					
					if (id >=0 && id<=HIGHEST_MACHINE_SLOT){
						//PRESSED IN MACHINE GUI 0-input, 1-focus, 2,3,4,5 -> outputs
						if (!this.mergeItemStack(stack1, HIGHEST_MACHINE_SLOT+1, HIGHEST_MACHINE_SLOT+37, true)) {
							return ItemStack.EMPTY;
						}
						slot.onSlotChange(stack1, stack);
					} else if (id >HIGHEST_MACHINE_SLOT && id <HIGHEST_MACHINE_SLOT+37){
						
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
