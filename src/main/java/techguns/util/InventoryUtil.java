package techguns.util;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.InventoryHelper;
import net.minecraft.item.ItemFood;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.items.ItemStackHandler;
import net.minecraftforge.oredict.OreDictionary;
import techguns.TGItems;
import techguns.capabilities.TGExtendedPlayer;
import techguns.gui.player.TGPlayerInventory;

public class InventoryUtil {

	public static int addAmmoToPlayerInventory(EntityPlayer ply, ItemStack ammo){
    	TGExtendedPlayer props = TGExtendedPlayer.get(ply);
    	
    	if (props!=null){
    		int amount = addItemToInventory(props.tg_inventory.inventory, ammo, props.tg_inventory.SLOTS_AMMO_START, props.tg_inventory.SLOTS_AMMO_END+1);
    		if (amount>0){
    			return addItemToInventory(ply.inventory.mainInventory, ammo, 0, ply.inventory.mainInventory.size());
    		} else {
    			return 0;
    		}
    	} else {
    		return addItemToInventory(ply.inventory.mainInventory, ammo, 0, ply.inventory.mainInventory.size());
    	}
    }
	
	public static int addAmmoToAmmoInventory(EntityPlayer ply, ItemStack ammo){
    	TGExtendedPlayer props = TGExtendedPlayer.get(ply);
    	
    	if (props!=null){
    		int amount = addItemToInventory(props.tg_inventory.inventory, ammo, props.tg_inventory.SLOTS_AMMO_START, props.tg_inventory.SLOTS_AMMO_END+1);
    		return amount;
    	}
    	return ammo.getCount();
    }
	
	/**
     * Try merge itemstack with inventory, return amount of stacksize that could NOT be merged.
     * @param mainInventory
     * @param item2
     * @param startIndex
     * @param endIndex
     * @return
     */
    public static int addItemToInventory(NonNullList<ItemStack> mainInventory, ItemStack item2, int startIndex, int endIndex){
    	ItemStack item = item2.copy();//TGItems.newStack(item2, item2.stackSize);
    	
    	for(int i=startIndex;i<endIndex;i++){
    		//1st try a merge
    		if (OreDictionary.itemMatches(mainInventory.get(i), item, true) && (mainInventory.get(i).getCount()<item.getMaxStackSize())){
    			int diff = (mainInventory.get(i).getCount() + item.getCount()) -item.getMaxStackSize();
    			if (diff < 0){
    				int c = mainInventory.get(i).getCount();
    				mainInventory.get(i).setCount(c+item.getCount());

    				item.setCount(0);
    				return 0;
    			} else {
    				mainInventory.get(i).setCount(item.getMaxStackSize());
    				item.setCount(diff);
    			}
    		}

    	}
    	
    	/**
    	 * could not fully merge, try fill empty slot
    	 */
    	if(item.getCount()>0){
    		for(int i=startIndex;i<endIndex;i++){
        		if(mainInventory.get(i).isEmpty()){
        			mainInventory.set(i,item.copy());
        			item.setCount(0);
        			return 0;
        		}

        	}
    	}
    	
    	//Return number of items that could not be put into inventory
    	return item.getCount();
    }
    
    /**
     * Try merge itemstack with inventory, return amount of stacksize that could NOT be merged.
     * @param mainInventory
     * @param item2
     * @param startIndex
     * @param endIndex
     * @return
     */
    public static int addItemToInventory(ItemStackHandler mainInventory, ItemStack item2, int startIndex, int endIndex){
    	ItemStack item = item2.copy();//TGItems.newStack(item2, item2.stackSize);
    	
    	for(int i=startIndex;i<endIndex;i++){
    		//1st try a merge
    		if (OreDictionary.itemMatches(mainInventory.getStackInSlot(i), item, true) && (mainInventory.getStackInSlot(i).getCount()<item.getMaxStackSize())){
    			int diff = (mainInventory.getStackInSlot(i).getCount() + item.getCount()) -item.getMaxStackSize();
    			if (diff < 0){
    				int c = mainInventory.getStackInSlot(i).getCount();
    				mainInventory.getStackInSlot(i).setCount(c+item.getCount());

    				item.setCount(0);
    				return 0;
    			} else {
    				mainInventory.getStackInSlot(i).setCount(item.getMaxStackSize());
    				item.setCount(diff);
    			}
    		}

    	}
    	
    	/**
    	 * could not fully merge, try fill empty slot
    	 */
    	if(item.getCount()>0){
    		for(int i=startIndex;i<endIndex;i++){
        		if(mainInventory.getStackInSlot(i).isEmpty()){
        			mainInventory.setStackInSlot(i,item.copy());
        			item.setCount(0);
        			return 0;
        		}

        	}
    	}
    	
    	//Return number of items that could not be put into inventory
    	return item.getCount();
    }
    
    public static boolean consumeAmmoPlayer(EntityPlayer ply, ItemStack[] ammo){
    	if(ammo.length==1) {
    		return consumeAmmoPlayer(ply,ammo[0]);
    	} else {
    	
	    	boolean canconsume=true;
	    	for(int i=0;i<ammo.length;i++) {
	    		if(!canConsumeAmmoPlayer(ply, ammo[i])) {
	    			canconsume=false;
	    			break;
	    		}
	    	}
	    	if(canconsume) {
	    		for(int i=0;i<ammo.length;i++) {
	    			consumeAmmoPlayer(ply, ammo[i]);
	        	}
	    		return true;
	    	} else {
	    		return false;
	    	}
    	}
    }
    
    public static boolean consumeAmmoPlayer(EntityPlayer ply, ItemStack ammo){
    	TGExtendedPlayer props = TGExtendedPlayer.get(ply);

    	if ( props!=null ){
        	int amount = ammo.getCount();
        	if (amount ==1){     
	        	if (consumeAmmo(props.tg_inventory.inventory, ammo,TGPlayerInventory.SLOTS_AMMO_START, TGPlayerInventory.SLOTS_AMMO_END+1)){
	        		return true;
	        	} else {
	        		return consumeAmmo(ply.inventory.mainInventory,ammo,0,ply.inventory.mainInventory.size());
	        	}
        	} else {
        		
        		//Check first if amount can be consumed
        		int needed = canConsumeItem(props.tg_inventory.inventory, ammo,TGPlayerInventory.SLOTS_AMMO_START, TGPlayerInventory.SLOTS_AMMO_END+1);
        		//System.out.println("needed1:"+needed);
        		int needed2 = canConsumeItem(ply.inventory.mainInventory,ammo,0,ply.inventory.mainInventory.size());
        		//System.out.println("needed2:"+needed);
        		if ( needed+needed2 <= amount){
        			
        			int missing = consumeItem(props.tg_inventory.inventory, ammo,TGPlayerInventory.SLOTS_AMMO_START, TGPlayerInventory.SLOTS_AMMO_END+1);
        			
        			if (missing >0){
        				return consumeItem(ply.inventory.mainInventory,TGItems.newStack(ammo, missing),0,ply.inventory.mainInventory.size()) <= 0;
        			} else {
        				return true;
        			}
        			
        			
        		} else {
        			return false;
        		}

        	}
    	} else {
    		return false;
    	}
    	
    }
    
    public static boolean canConsumeAmmoPlayer(EntityPlayer ply, ItemStack ammo){
    	TGExtendedPlayer props = TGExtendedPlayer.get(ply);
    	
    	if ( props!=null ){
        	int amount = ammo.getCount();
        	if (amount ==1){     
	        	if (canConsumeItem(props.tg_inventory.inventory, ammo,TGPlayerInventory.SLOTS_AMMO_START, TGPlayerInventory.SLOTS_AMMO_END+1)<=0){
	        		return true;
	        	} else {
	        		return canConsumeItem(ply.inventory.mainInventory,ammo,0,ply.inventory.mainInventory.size())<=0;
	        	}
        	} else {
        		
        		//Check first if amount can be consumed
        		int needed = canConsumeItem(props.tg_inventory.inventory, ammo,TGPlayerInventory.SLOTS_AMMO_START, TGPlayerInventory.SLOTS_AMMO_END+1);
        		int needed2 = canConsumeItem(ply.inventory.mainInventory,ammo,0,ply.inventory.mainInventory.size());

        		if ( needed+needed2 <= amount){
        			
        			return true;
        			
        		} else {
        			return false;
        		}

        	}
    	} else {
    		return false;
    	}
    	
    }
    
    /**
     * Return missing items, that can not be consumed, 0 == ammount can be consumed
     * @param inv
     * @param item
     * @param startIndex
     * @param endIndex
     * @return
     */
    public static int canConsumeItem(NonNullList<ItemStack> inv,ItemStack item,int startIndex, int endIndex){
    	//ply.consumeInventoryItem(ammo.getItem());
    	int needed = item.getCount();
    	
    	for (int i = startIndex; i < endIndex; ++i)
        {
            if (!inv.get(i).isEmpty() && inv.get(i).getItem() == item.getItem() && inv.get(i).getItemDamage()==item.getItemDamage())
            {
                needed -= inv.get(i).getCount();
                if(needed<=0){
                	return 0;
                }
            }
        }
    	return needed;
    }
    
    /**
     * Return missing items, that can not be consumed, 0 == ammount can be consumed
     * @param inv
     * @param item
     * @param startIndex
     * @param endIndex
     * @return
     */
    public static int canConsumeItem(ItemStackHandler inv,ItemStack item,int startIndex, int endIndex){
    	//ply.consumeInventoryItem(ammo.getItem());
    	int needed = item.getCount();
    	
    	for (int i = startIndex; i < endIndex; ++i)
        {
            if (!inv.getStackInSlot(i).isEmpty() && inv.getStackInSlot(i).getItem() == item.getItem() && inv.getStackInSlot(i).getItemDamage()==item.getItemDamage())
            {
                needed -= inv.getStackInSlot(i).getCount();
                if(needed<=0){
                	return 0;
                }
            }
        }
    	return needed;
    }
    
    /**
     * Consumes from multiple stacks, returns amount that could not be consumed, does not check if total ammount can be consumed,
     * use canConsumeItem() to check that before
     * @param inv
     * @param item
     * @param startIndex
     * @param endIndex
     * @return
     */
    public static int consumeItem(NonNullList<ItemStack> inv,ItemStack item,int startIndex, int endIndex){
    	//ply.consumeInventoryItem(ammo.getItem());
    	int needed=item.getCount();
    	
    	for (int i = startIndex; i < endIndex; ++i)
        {
            if (!inv.get(i).isEmpty() && inv.get(i).getItem() == item.getItem() && inv.get(i).getItemDamage()==item.getItemDamage())
            {
            	if (inv.get(i).getCount()<=needed){
                    needed -= inv.get(i).getCount();
                    inv.set(i, ItemStack.EMPTY);
            	} else {
            		inv.get(i).setCount(inv.get(i).getCount()-needed);
            		//needed=0;
            		return 0;
                }
            }
        }
    	return needed;
    }
    
    /**
     * Consumes from multiple stacks, returns amount that could not be consumed, does not check if total ammount can be consumed,
     * use canConsumeItem() to check that before
     * @param inv
     * @param item
     * @param startIndex
     * @param endIndex
     * @return
     */
    public static int consumeItem(ItemStackHandler inv, ItemStack item,int startIndex, int endIndex){
    	//ply.consumeInventoryItem(ammo.getItem());
    	int needed=item.getCount();
    	
    	for (int i = startIndex; i < endIndex; ++i)
        {
            if (!inv.getStackInSlot(i).isEmpty() && inv.getStackInSlot(i).getItem() == item.getItem() && inv.getStackInSlot(i).getItemDamage()==item.getItemDamage())
            {
            	if (inv.getStackInSlot(i).getCount()<=needed){
                    needed -= inv.getStackInSlot(i).getCount();
                    inv.setStackInSlot(i, ItemStack.EMPTY);
            	} else {
            		inv.getStackInSlot(i).setCount(inv.getStackInSlot(i).getCount()-needed);
            		//needed=0;
            		return 0;
                }
            }
        }
    	return needed;
    }
   
    /**
     * Search for item in inventory with same damage value;
     * @param inv
     * @param stack
     * @return
     */
    private static int searchItem(NonNullList<ItemStack> inv,ItemStack stack,int startIndex, int endIndex)
    {
        for (int i = startIndex; i < endIndex; ++i)
        {
            if (!inv.get(i).isEmpty() && inv.get(i).getItem() == stack.getItem() && inv.get(i).getItemDamage()==stack.getItemDamage())
            {
                return i;
            }
        }

        return -1;
    }
    
    /**
     * Search for item in inventory with same damage value;
     * @param inv
     * @param stack
     * @return
     */
    private static int searchItem(ItemStackHandler inv,ItemStack stack,int startIndex, int endIndex)
    {
        for (int i = startIndex; i < endIndex; ++i)
        {
            if (!inv.getStackInSlot(i).isEmpty() && inv.getStackInSlot(i).getItem() == stack.getItem() && inv.getStackInSlot(i).getItemDamage()==stack.getItemDamage())
            {
                return i;
            }
        }

        return -1;
    }
    
    
    public static boolean consumeAmmo(NonNullList<ItemStack> inv,ItemStack ammo,int startIndex, int endIndex){
    	//ply.consumeInventoryItem(ammo.getItem());
    	
    	int i = searchItem(inv, ammo, startIndex, endIndex);

        if (i < 0)
        {
            return false;
        }
        else
        {
        	inv.get(i).setCount(inv.get(i).getCount()-1);
        	if (inv.get(i).getCount()<=0){
        		inv.set(i, ItemStack.EMPTY);
        	}

            return true;
        }
    }
    
    public static boolean consumeAmmo(ItemStackHandler inv,ItemStack ammo,int startIndex, int endIndex){
    	//ply.consumeInventoryItem(ammo.getItem());
    	
    	int i = searchItem(inv, ammo, startIndex, endIndex);

        if (i < 0)
        {
            return false;
        }
        else
        {
        	inv.getStackInSlot(i).shrink(1);
            return true;
        }
    }
    
    public static ItemStack consumeFood(NonNullList<ItemStack> inv,int startIndex, int endIndex){
    	for (int i = startIndex; i < endIndex; ++i)
        {
            if (!inv.get(i).isEmpty() && inv.get(i).getItem() instanceof ItemFood)
            {
            	ItemStack food =TGItems.newStack(inv.get(i), 1);
            	inv.get(i).setCount(inv.get(i).getCount()-1);
            	if (inv.get(i).getCount()<=0){
            		inv.set(i, ItemStack.EMPTY);
            	}
            	return food;
            }
        }
    	return ItemStack.EMPTY;
    }
    
    public static int countItemInInv(NonNullList<ItemStack> inv, ItemStack stackToSearchFor ,int startIndex, int endIndex){
    	int count=0;
    	
        for (int i = startIndex; i < endIndex; ++i)
        {
            if (!inv.get(i).isEmpty() && inv.get(i).getItem() == stackToSearchFor.getItem() && inv.get(i).getItemDamage()==stackToSearchFor.getItemDamage())
            {
                count+=inv.get(i).getCount();
            }
        }
        return count;
    }

    /**
     * 
     * @param worldIn
     * @param pos
     * @param inventory
     */
    public static void dropInventoryItems(World worldIn, BlockPos pos, ItemStackHandler inventory) {
        double x =pos.getX()+0.5d;
        double y= pos.getY()+0.5d;
        double z= pos.getZ()+0.5d;
        
        for (int i = 0; i < inventory.getSlots(); ++i)
        {
            ItemStack itemstack = inventory.getStackInSlot(i);

            if (!itemstack.isEmpty())
            {
                InventoryHelper.spawnItemStack(worldIn, x, y, z, itemstack);
            }
        }
    }
    
}
