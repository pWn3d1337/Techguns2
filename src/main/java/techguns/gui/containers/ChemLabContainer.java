package techguns.gui.containers;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.IContainerListener;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;
import techguns.TGPackets;
import techguns.gui.widgets.SlotItemHandlerOutput;
import techguns.gui.widgets.SlotMachineInput;
import techguns.gui.widgets.SlotMachineUpgrade;
import techguns.gui.widgets.SlotTG;
import techguns.packets.PacketUpdateTileEntTanks;
import techguns.tileentities.ChemLabTileEnt;
import techguns.tileentities.operation.ItemStackHandlerPlus;

public class ChemLabContainer extends BasicMachineContainer {

	public static final int FIELD_SYNC_ID_FLUID_TYPE_IN = FIELD_SYNC_ID_POWER_STORED+1;
	public static final int FIELD_SYNC_ID_FLUID_TYPE_OUT = FIELD_SYNC_ID_POWER_STORED+2;
	public static final int FIELD_SYNC_ID_FLUID_AMOUNT_IN = FIELD_SYNC_ID_POWER_STORED+3;
	public static final int FIELD_SYNC_ID_FLUID_AMOUNT_OUT = FIELD_SYNC_ID_POWER_STORED+4;
	
	public static final int SLOT_INPUT1_X=35;
	public static final int SLOT_INPUT2_X=57;
	
	public static final int SLOT_OUTPUT_X=135;
	
	public static final int SLOTS_ROW1_Y=17;
	public static final int SLOTS_ROW2_Y=40;
	
	public static final int SLOT_UPGRADE_X=SLOT_OUTPUT_X;
	public static final int SLOT_UPGRADE_Y=MetalPressContainer.SLOT_OUTPUTS_Y;
	
	FluidStack lastInputtank=null;
	FluidStack lastOutputtank=null;
	
	ChemLabTileEnt tile;
	public ChemLabContainer(InventoryPlayer player, ChemLabTileEnt ent) {
		super(player, ent);
		this.tile=ent;
		
		IItemHandler inventory = ent.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, EnumFacing.SOUTH);
		
		if (inventory instanceof ItemStackHandlerPlus) {
		ItemStackHandlerPlus handler = (ItemStackHandlerPlus) inventory;
	
			this.addSlotToContainer(new SlotMachineInput(handler, ChemLabTileEnt.SLOT_INPUT1, SLOT_INPUT1_X, SLOTS_ROW1_Y));
			this.addSlotToContainer(new SlotMachineInput(handler,  ChemLabTileEnt.SLOT_INPUT2, SLOT_INPUT2_X, SLOTS_ROW1_Y));
			this.addSlotToContainer(new SlotMachineInput(handler,  ChemLabTileEnt.SLOT_BOTTLE, SLOT_INPUT1_X, SLOTS_ROW2_Y) {
				@Override
				public String getSlotTexture() {
					return SlotTG.BOTTLESLOT_TEX.toString();
				}
			});
		
		
			this.addSlotToContainer(new SlotItemHandlerOutput(inventory, ChemLabTileEnt.SLOT_OUTPUT, SLOT_OUTPUT_X, SLOTS_ROW1_Y));
			this.addSlotToContainer(new SlotMachineUpgrade(handler,  ChemLabTileEnt.SLOT_UPGRADE, SLOT_UPGRADE_X, SLOT_UPGRADE_Y));
		}
		
		this.addPlayerInventorySlots(player);
	}
	
	
	
	@Override
	public void detectAndSendChanges() {
		super.detectAndSendChanges();
		
		/*if (this.lastInputtank!=null) {
			System.out.println(this.lastInputtank.getLocalizedName()+" "+this.lastInputtank.amount);
		} else {
			System.out.println("NO INPUT FLUID");
		}*/
		
		//System.out.println("content change:"+tile.getContentsChanged());
		
		/*if (fluidsChanged(lastInputtank, this.tile.getCurrentInputFluid())
				|| fluidsChanged(lastOutputtank, this.tile.getCurrentOutputFluid())) {*/
			if (!this.tile.getWorld().isRemote) {
				for (int j = 0; j < this.listeners.size(); ++j) {
					IContainerListener listener = this.listeners.get(j);
					if (listener instanceof EntityPlayerMP) {
						EntityPlayerMP player = (EntityPlayerMP) listener;
						TGPackets.network.sendTo(new PacketUpdateTileEntTanks(this.tile, this.tile.getPos()), player);
					}
				}
			}

		//}
		/*this.lastInputtank = this.tile.getCurrentInputFluid() != null ? this.tile.getCurrentInputFluid().copy() : null;
		this.lastOutputtank = this.tile.getCurrentOutputFluid() != null ? this.tile.getCurrentOutputFluid().copy()
				: null;*/
	}

	private boolean fluidsChanged(FluidStack f1, FluidStack f2) {
		if (f1==null && f2 == null) {
			return false;
		} if (f1==null || f2 == null) {
			return true;
		} else {
			return !(f1.getFluid() == f2.getFluid() && f1.amount==f2.amount);
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
					
					if (id >=0 && id<5){
						//PRESSED IN MACHINE GUI
						if (!this.mergeItemStack(stack1, 5, 41, false)) {
							return ItemStack.EMPTY;
						}
						slot.onSlotChange(stack1, stack);
					} else if (id >=5 && id <41){
						
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
