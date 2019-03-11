package techguns.gui.player;

import javax.annotation.Nullable;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import techguns.api.tginventory.TGSlotType;
import techguns.capabilities.TGExtendedPlayer;
import techguns.gui.widgets.SlotArmor;
import techguns.gui.widgets.SlotFood;
import techguns.gui.widgets.SlotTG;

public class TGPlayerInventoryContainer extends Container {

	private static final int DEFAULT_INV_ARMOR_START=TGPlayerInventory.NUMSLOTS;
	private static final int DEFAULT_INV_START=DEFAULT_INV_ARMOR_START+4;
	private static final int DEFAULT_INV_HOTBAR_START=DEFAULT_INV_START+27;
	private static final int DEFAULT_INV_SHIELD=DEFAULT_INV_HOTBAR_START+9;
	
	private static final EntityEquipmentSlot[] VALID_EQUIPMENT_SLOTS = new EntityEquipmentSlot[] {EntityEquipmentSlot.HEAD, EntityEquipmentSlot.CHEST, EntityEquipmentSlot.LEGS, EntityEquipmentSlot.FEET};
	
	private static final int FOOD_SLOTS_COUNT=3;
	private static final int AMMO_SLOTS_COUNT=8;
	
	@Override
	public boolean canInteractWith(EntityPlayer player) {
		return true;
	}
	
	public TGPlayerInventoryContainer(EntityPlayer player){
		
		InventoryPlayer inv = player.inventory;
		TGPlayerInventory tgplayerinv = TGExtendedPlayer.get(player).tg_inventory;
		
		//custom slots:
		this.addSlotToContainer(new SlotTG(tgplayerinv,TGPlayerInventory.SLOT_FACE, 77, 8,TGSlotType.FACESLOT));

		this.addSlotToContainer(new SlotTG(tgplayerinv, TGPlayerInventory.SLOT_BACK, 77, 26,TGSlotType.BACKSLOT));
		
		this.addSlotToContainer(new SlotTG(tgplayerinv, TGPlayerInventory.SLOT_HAND, 77, 44,TGSlotType.HANDSLOT));

		this.addSlotToContainer(new SlotFood(tgplayerinv,TGPlayerInventory.SLOTS_AUTOFOOD_START,116,24));
		this.addSlotToContainer(new SlotFood(tgplayerinv,TGPlayerInventory.SLOTS_AUTOFOOD_START+1,116+18,24));
		this.addSlotToContainer(new SlotFood(tgplayerinv,TGPlayerInventory.SLOTS_AUTOFOOD_START+2,116+18*2,24));
		
		this.addSlotToContainer(new SlotTG(tgplayerinv,TGPlayerInventory.SLOT_AUTOHEAL,97,24,TGSlotType.HEALSLOT));
		
		for (int i=0;i<2;i++){
			for(int j=0;j<4;j++){
				this.addSlotToContainer(new SlotTG(tgplayerinv,TGPlayerInventory.SLOTS_AMMO_START+(i*4)+j,98+j*18,44+i*18,TGSlotType.AMMOSLOT));
			}
		}
		
		
		//Default Slots:

		//Armor
		for (int k = 0; k < 4; ++k) {
			EntityEquipmentSlot entityequipmentslot = VALID_EQUIPMENT_SLOTS[k];
			this.addSlotToContainer(new SlotArmor(inv, 36 + (3 - k), 8, 8 + k * 18, entityequipmentslot, player));
		}

		int i,j;
		//Inventory:
        for (i = 0; i < 3; ++i)
        {
            for (j = 0; j < 9; ++j)
            {
                this.addSlotToContainer(new Slot(inv, j + (i + 1) * 9, 8 + j * 18, 84 + i * 18));
            }
        }
		
		//Hotbar:

        for (i = 0; i < 9; ++i)
        {
            this.addSlotToContainer(new Slot(inv, i, 8 + i * 18, 142));
        }
		
        this.addSlotToContainer(new Slot(inv, 40, 77, 62)
        {
            @Nullable
            @SideOnly(Side.CLIENT)
            public String getSlotTexture()
            {
                return "minecraft:items/empty_armor_slot_shield";
            }
        });

	}

	/**
	 * Get the valid slot in the TG inventory tab
	 * @param stack
	 * @return
	 */
	private int[] getValidSlotForIteminInv(ItemStack stack){
		int[] ret = new int[2];
		ret[0]=-1;
		ret[1]=1;
		
		for (int i=0;i<TGPlayerInventory.NUMSLOTS;++i){
			Slot s=this.inventorySlots.get(i);
			if(s.isItemValid(stack)){
				ret[0]=i;
				
				if(s instanceof SlotTG){
					SlotTG stg = (SlotTG) s;
					if(stg.getType()==TGSlotType.AMMOSLOT){
						ret[1]=AMMO_SLOTS_COUNT;
					} 
				} else if (s instanceof SlotFood){
					ret[1]=FOOD_SLOTS_COUNT;
				}
				
				return ret;
			}
		
		}
		
		
		return ret;
	}
	
	private boolean hasTGSpecialSlot(ItemStack stack){
		return this.getValidSlotForIteminInv(stack)[0]>=0;
	}
	
	@Override
	public ItemStack transferStackInSlot(EntityPlayer player, int id) {
		  int o = TGPlayerInventory.NUMSLOTS-5; //offset compared to Default inv, -5 craftmatrix + TG inventory size

		  
	      ItemStack itemstack = ItemStack.EMPTY;
	        Slot slot = (Slot)this.inventorySlots.get(id);

	        if (slot != null && slot.getHasStack())
	        {
	            ItemStack itemstack1 = slot.getStack();
	            itemstack = itemstack1.copy();

	            if (id < TGPlayerInventory.NUMSLOTS) {
	            	//CLICKED IN TG INVENTORY
	            	if (!this.mergeItemStack(itemstack1, DEFAULT_INV_START, DEFAULT_INV_START+36, false)) {
						return ItemStack.EMPTY;
					}
					slot.onSlotChange(itemstack1, itemstack);
	            	
	            }	
	            /*else if (id >= TGPlayerInventory.NUMSLOTS && id < TGPlayerInventory.NUMSLOTS+4)
	            {
	                if (!this.mergeItemStack(itemstack1, 9+o, 45+o, false))
	                {
	                    return null;
	                }
	            }*/
	            //Try merge Armor
	            else if (itemstack.getItem() instanceof ItemArmor && !((Slot)this.inventorySlots.get(TGPlayerInventory.NUMSLOTS + ((ItemArmor)itemstack.getItem()).armorType.getIndex())).getHasStack())
	            {
	                int j = TGPlayerInventory.NUMSLOTS + ((ItemArmor)itemstack.getItem()).armorType.getIndex();

	                if (!this.mergeItemStack(itemstack1, j, j + 1, false))
	                {
	                    return ItemStack.EMPTY;
	                }
	            } else if ( (id >= DEFAULT_INV_START && id < DEFAULT_INV_START+36) && hasTGSpecialSlot(itemstack) ){
	            	
	            	//Merge into tG inventory
	            	int slots[] = this.getValidSlotForIteminInv(itemstack1);
					int validslot=slots[0];
					int num=slots[1];
	            	if(!this.mergeItemStack(itemstack1, validslot, validslot+num, false)){
						return ItemStack.EMPTY;
					}
					slot.onSlotChange(itemstack1, itemstack);
	            }
	            else if (id >= 9+o && id < 36+o)
	            {
	                if (!this.mergeItemStack(itemstack1, 36+o, 45+o, false))
	                {
	                    return ItemStack.EMPTY;
	                }
	            }
	            else if (id >= 36+o && id < 45+o)
	            {
	                if (!this.mergeItemStack(itemstack1, 9+o, 36+o, false))
	                {
	                    return ItemStack.EMPTY;
	                }
	            }
	            else if (!this.mergeItemStack(itemstack1, 9+o, 45+o, false))
	            {
	                return ItemStack.EMPTY;
	            }

	            if (itemstack1.getCount() == 0)
	            {
	                slot.putStack(ItemStack.EMPTY);
	            }
	            else
	            {
	                slot.onSlotChanged();
	            }

	            if (itemstack1.getCount() == itemstack.getCount())
	            {
	                return ItemStack.EMPTY;
	            }

	            slot.onTake(player, itemstack1);
	            //slot.onPickupFromSlot(player, itemstack1);
	        }

	        return itemstack;
	}
	
}