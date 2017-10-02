package techguns.gui.containers;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.IContainerListener;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;
import techguns.api.tginventory.ITGSpecialSlot;
import techguns.api.tginventory.TGSlotType;
import techguns.gui.widgets.SlotItemHandlerOutput;
import techguns.gui.widgets.SlotMachineInput;
import techguns.gui.widgets.SlotTG;
import techguns.gui.widgets.SlotTurretArmor;
import techguns.gui.widgets.SlotTurretGun;
import techguns.items.guns.GenericGun;
import techguns.tileentities.BasicPoweredTileEnt;
import techguns.tileentities.TurretTileEnt;
import techguns.tileentities.operation.ItemStackHandlerPlus;

public class TurretContainer extends RedstoneTileContainer {
	
	protected int lastRepairTime=0;
	protected boolean lastTurretDeath=false;
	protected TurretTileEnt tile;
	protected int lastPowerStored=0;
	
	protected static final int FIELD_SYNC_ID_TURRETDEATH =FIELD_SYNC_ID_REDSTONE+1;
	protected static final int FIELD_SYNC_ID_REPAIRTIME =FIELD_SYNC_ID_REDSTONE+2;
	public static final int FIELD_SYNC_ID_POWER_STORED = FIELD_SYNC_ID_REDSTONE+3;
	
	public TurretContainer(InventoryPlayer player, TurretTileEnt ent) {
		super(player, ent);
		this.tile=ent;
		
		IItemHandler inventory = ent.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, EnumFacing.SOUTH);
		
		if (inventory instanceof ItemStackHandlerPlus) {
		ItemStackHandlerPlus handler = (ItemStackHandlerPlus) inventory;
	
			
			for (int i=0;i<3;i++){
				for(int j=0;j<3;j++){
					this.addSlotToContainer(new SlotMachineInput(handler, (i*3)+j, 18+18*i, 17+18*j) {
						@Override
						public String getSlotTexture() {
							return SlotTG.AMMOSLOT_TEX.toString();
						}
					});
				}
			}
			
			for (int i=0;i<3;i++){
				for(int j=0;j<3;j++){
					this.addSlotToContainer(new SlotItemHandlerOutput(handler, 9+(i*3)+j, 116+18*i, 17+18*j) {
						@Override
						public String getSlotTexture() {
							return SlotTG.AMMOEMPTYSLOT_TEX.toString();
						}
					});
				}
			}
			
			this.addSlotToContainer(new SlotTurretGun(handler, 18, 85, 19));
			
			this.addSlotToContainer(new SlotTurretArmor(handler, 19, 85, 42));
		}
		
		this.addPlayerInventorySlots(player);
	}


	
	@Override
	public void detectAndSendChanges() {
		super.detectAndSendChanges();
		 for (int j = 0; j < this.listeners.size(); ++j)
        {
            IContainerListener listener = this.listeners.get(j);
            
            if (this.lastTurretDeath!=this.tile.turretDeath) {
            	listener.sendWindowProperty(this, FIELD_SYNC_ID_TURRETDEATH, this.tile.turretDeath?1:0);
            }
            if(this.lastRepairTime!=this.tile.repairTime) {
            	listener.sendWindowProperty(this, FIELD_SYNC_ID_REPAIRTIME, this.tile.repairTime);
            }
            if (this.lastPowerStored!=this.tile.getEnergyStorage().getEnergyStored()) {
            	listener.sendWindowProperty(this, FIELD_SYNC_ID_POWER_STORED, this.tile.getEnergyStorage().getEnergyStored());
            }
            
        }
		 this.lastRepairTime=this.tile.repairTime;
		 this.lastTurretDeath=this.tile.turretDeath;
		 this.lastPowerStored=this.tile.getEnergyStorage().getEnergyStored();
	}



	@Override
	public void updateProgressBar(int id, int data) {
		if (id==FIELD_SYNC_ID_REPAIRTIME) {
			this.tile.repairTime=data;
		} else if (id == FIELD_SYNC_ID_TURRETDEATH) {
			this.tile.turretDeath=data!=0;
		} else if (id== FIELD_SYNC_ID_POWER_STORED) {
			this.tile.getEnergyStorage().setEnergyStored(data);
		} else {
			super.updateProgressBar(id, data);
		}
	}

	@Override
	public ItemStack transferStackInSlot(EntityPlayer player, int slotid) {
		ItemStack stack = ItemStack.EMPTY;
		Slot slot = (Slot) this.inventorySlots.get(slotid);
		
		if(slot.getHasStack()){
			ItemStack stack1 = slot.getStack();
			stack=stack1.copy();
			if (!stack.isEmpty()){
		
				//machineslot pressed
				if(slotid<=TurretTileEnt.SLOT_ARMOR){

					//put item back in player inventory
					if (!this.mergeItemStack(stack1, TurretTileEnt.SLOT_ARMOR+1, TurretTileEnt.SLOT_ARMOR+1+36, true)) {
						return ItemStack.EMPTY;
					}
					slot.onSlotChange(stack1, stack);
					
				} else if(slotid>TurretTileEnt.SLOT_ARMOR) {
					//pressed in player gui
					
					if(stack.getItem() instanceof GenericGun){
						
						//put weapon in weaponslot
						if (!this.mergeItemStack(stack1, TurretTileEnt.SLOT_WEAPON, TurretTileEnt.SLOT_WEAPON+1, true)) {
							return ItemStack.EMPTY;
						}
						slot.onSlotChange(stack1, stack);
					} else if(stack.getItem() instanceof ITGSpecialSlot && (((ITGSpecialSlot)stack.getItem()).getSlot(stack)==TGSlotType.TURRETARMOR)){	
						//put in armorslot
						if (!this.mergeItemStack(stack1, TurretTileEnt.SLOT_ARMOR,TurretTileEnt.SLOT_ARMOR+1, true)) {
							return ItemStack.EMPTY;
						}
						slot.onSlotChange(stack1, stack);
					} else {
						//put item in input inventory
						if (!this.mergeItemStack(stack1, TurretTileEnt.SLOT_OUTPUT1, TurretTileEnt.SLOT_INPUT1+TurretTileEnt.INPUTS_SIZE, true)) {
							return ItemStack.EMPTY;
						}
						slot.onSlotChange(stack1, stack);
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

				slot.onTake(player, stack1);
			}
		}
		return stack;
	}

	
	
}
