package techguns.gui.containers;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.SlotItemHandler;
import techguns.gui.widgets.SlotArmor;
import techguns.items.armors.GenericArmor;
import techguns.items.armors.GenericShield;
import techguns.tileentities.RepairBenchTileEnt;

public class RepairBenchContainer extends OwnedTileContainer {

	public RepairBenchContainer(InventoryPlayer player, RepairBenchTileEnt ent) {
		super(player, ent);
		
		IItemHandler inventory = ent.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, EnumFacing.SOUTH);
		
		int i;
		for (i = 0; i < 9; ++i) {
			this.addSlotToContainer(new SlotItemHandler(inventory, i, 8 + i * 18, 57));
		}
		this.addSlotToContainer(new SlotItemHandler(inventory, 9, 8 , 18) {
			@Override
			public boolean isItemValid(ItemStack stack) {
				if(!stack.isEmpty()) {
					return stack.getItem() instanceof GenericArmor || stack.getItem() instanceof GenericShield;
				}
				return false;
			}
		});
		
		this.addPlayerInventorySlots(player);
		
		for (i = 0; i < 4; ++i) {
			this.addSlotToContainer(new SlotArmor(player, 39-i, 89+(i*20), 18,i,player.player));
		}
		this.addSlotToContainer(new Slot(player, 40, 64, 18) {
			@Override
			public String getSlotTexture() {
				return "minecraft:items/empty_armor_slot_shield";
			}
		});
	}

	@Override
	public ItemStack transferStackInSlot(EntityPlayer player, int slotid) {
		ItemStack stack = ItemStack.EMPTY;
		Slot slot = (Slot) this.inventorySlots.get(slotid);
		
		//System.out.println("SLOTID:"+slotid);
		
		if(slot.getHasStack()){
			ItemStack stack1 = slot.getStack();
			stack=stack1.copy();
			if (!stack.isEmpty()){
		
				//g
				if(slotid<9){
					//Pressed in machine gui, put in player inv
					
						if(!this.mergeItemStack(stack1, 9, 9+36, false)){
							return ItemStack.EMPTY;
						}
						slot.onSlotChange(stack1, stack);
					
				} else if(slotid>9 && slotid<9+36) {
					
					if(stack1.getItem() instanceof ItemArmor){
						int type=getArmorIntFromEntityEquipmentSlot(((ItemArmor)stack1.getItem()).armorType);
						//put armor into player armor inventory
						if (!this.mergeItemStack(stack1, 9+36+type, 9+36+type+1, false)) {
							return ItemStack.EMPTY;
						}
						slot.onSlotChange(stack1, stack);
					} else {
						if(!this.mergeItemStack(stack1, 0, 9, false)){
							return ItemStack.EMPTY;
						}
						slot.onSlotChange(stack1, stack);
					}
				} else if (slotid>=9+36){
					//pressed in armor gui
				
				//put item back in player inventory
					if (!this.mergeItemStack(stack1, 9, 9+36, false)) {
						return ItemStack.EMPTY;
					}
					slot.onSlotChange(stack1, stack);

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
