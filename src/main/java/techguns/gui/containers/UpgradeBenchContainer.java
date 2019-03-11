package techguns.gui.containers;

import java.util.Map;

import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.init.SoundEvents;
import net.minecraft.inventory.ContainerRepair;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.InventoryBasic;
import net.minecraft.inventory.InventoryCraftResult;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.SoundCategory;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.SlotItemHandler;
import techguns.TGConfig;
import techguns.gui.widgets.SlotArmor;
import techguns.items.armors.GenericArmor;
import techguns.tileentities.UpgradeBenchTileEnt;
import techguns.tileentities.BasicOwnedTileEnt;
import techguns.tileentities.operation.UpgradeBenchRecipes;
import techguns.tileentities.operation.UpgradeBenchRecipes.UpgradeBenchRecipe;
import techguns.util.EntityUtil;

public class UpgradeBenchContainer extends OwnedTileContainer {
	protected final UpgradeBenchTileEnt tile;
	
    /** Here comes out item you merged and/or renamed. */
    public final IInventory outputSlot;
    /** The 2slots where you put your items in that you want to merge and/or rename. */
    protected final IInventory inputSlots;
	
    protected int xp_needed = 0;
    
    public static final int SLOT_Y=47;
    public static final int SLOT_INPUT_X=17;
    public static final int SLOT_UPGRADE_X=53;
    public static final int SLOT_OUTPUT_X=116;
    
	protected static final int MAXSLOTS = 3+36+4+1; //36 = inventory size
    protected static final int HIGHEST_SLOT=2;
	
	public UpgradeBenchContainer(InventoryPlayer player, UpgradeBenchTileEnt ent) {
		super(player, ent);
		this.tile=ent;
		
		//IItemHandler inventory = ent.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, EnumFacing.SOUTH);
		
		int i;
		/*for (i = 0; i < 4; ++i) {
			this.addSlotToContainer(new SlotItemHandler(inventory, i, 8 + i * 18, 57));
		}*/
		
		 this.outputSlot = new InventoryCraftResult();
         this.inputSlots = new InventoryBasic("UpgradeBench", true, 2)
        {
            /**
             * For tile entities, ensures the chunk containing the tile entity is saved to disk later - the game won't
             * think it hasn't changed and skip it.
             */
            public void markDirty()
            {
                super.markDirty();
                UpgradeBenchContainer.this.onCraftMatrixChanged(this);
            }
        };

        this.addSlotToContainer(new SlotInput(this.inputSlots, 0, SLOT_INPUT_X, SLOT_Y));
        this.addSlotToContainer(new SlotInput(this.inputSlots, 1, SLOT_UPGRADE_X, SLOT_Y));
        this.addSlotToContainer(new Slot(this.outputSlot, 2, SLOT_OUTPUT_X, SLOT_Y) {

			@Override
			public boolean isItemValid(ItemStack stack) {
				//No inputing in output slot
				return false;
			}

			@Override
			public ItemStack onTake(EntityPlayer thePlayer, ItemStack stack) {
				
				//remove inputs
				UpgradeBenchContainer.this.inputSlots.setInventorySlotContents(0, ItemStack.EMPTY);
				UpgradeBenchContainer.this.inputSlots.getStackInSlot(1).shrink(1);
				
				UpgradeBenchContainer.this.tile.getWorld().playSound(thePlayer, thePlayer.posX, thePlayer.posY, thePlayer.posZ, SoundEvents.ITEM_ARMOR_EQUIP_DIAMOND, SoundCategory.PLAYERS, 1f, 1f);
					
				if(!thePlayer.capabilities.isCreativeMode ) EntityUtil.playerAddExperiencePoints(thePlayer, -xp_needed);
				UpgradeBenchContainer.this.xp_needed=0;
				
				return super.onTake(thePlayer, stack);
			}

			@Override
			public boolean canTakeStack(EntityPlayer playerIn) {
				if(!this.inventory.getStackInSlot(0).isEmpty()) {
					return playerIn.capabilities.isCreativeMode || playerIn.experienceTotal >= UpgradeBenchContainer.this.xp_needed;
				} else {
					return false;
				}
			}
        	
        	
        });
		
		
		this.addPlayerInventorySlots(player);
		
		for (i = 0; i < 4; ++i) {
			this.addSlotToContainer(new SlotArmor(player, 39-i, 17+(i*18), 18,i,player.player));
		}
	}

	public class SlotInput extends Slot {

		public SlotInput(IInventory inventoryIn, int index, int xPosition, int yPosition) {
			super(inventoryIn, index, xPosition, yPosition);
		}

		@Override
		public ItemStack onTake(EntityPlayer thePlayer, ItemStack stack) {
			UpgradeBenchContainer.this.xp_needed = UpgradeBenchContainer.this.calculateXPCost();
			return super.onTake(thePlayer, stack);
		}
		
	}
	
	  /**
     * Called when the container is closed.
     */
	@Override
    public void onContainerClosed(EntityPlayer playerIn)
    {
        super.onContainerClosed(playerIn);

        if (!this.tile.getWorld().isRemote)
        {
            this.clearContainer(playerIn, this.tile.getWorld(), this.inputSlots);
        }
    }
	
    /**
     * Callback for when the crafting matrix is changed.
     */
	@Override
    public void onCraftMatrixChanged(IInventory inventoryIn)
    {
        super.onCraftMatrixChanged(inventoryIn);

        if (inventoryIn == this.inputSlots)
        {
            this.updateOutput();
        }
    }
    
    /**
    * called when the Input Slot changes, calculates the new result and puts it in the output slot
    */
   public void updateOutput()
   {
    	
	   UpgradeBenchRecipe r = UpgradeBenchRecipes.getUpgradeRecipeFor(this.inputSlots.getStackInSlot(0), this.inputSlots.getStackInSlot(1));
	   
	   if(r!=null) {
		   ItemStack output = this.inputSlots.getStackInSlot(0).copy();
	       Map<Enchantment, Integer> map = EnchantmentHelper.getEnchantments(this.inputSlots.getStackInSlot(0));
	       
	       this.xp_needed = this.calculateXPCost(r);
	       
	       if(map.containsKey(r.getEnch())) {
	    	   Integer level = map.get(r.getEnch());
	    	   
	    	   if(r.getLevel()>level) {
	    		   map.put(r.getEnch(), r.getLevel());
	    		   EnchantmentHelper.setEnchantments(map, output);
	    		   this.outputSlot.setInventorySlotContents(0, output);
	    	   } else {
	    		   this.outputSlot.setInventorySlotContents(0, ItemStack.EMPTY);
	    	   }
	    	   
	       } else {
	     	   map.put(r.getEnch(), r.getLevel());
    		   EnchantmentHelper.setEnchantments(map, output);
    		   this.outputSlot.setInventorySlotContents(0, output);
	       }
	       
	   } else {
		   this.outputSlot.setInventorySlotContents(0, ItemStack.EMPTY);
	   }
	   
   }

   public int getXp_needed() {
		return xp_needed;
	}

   public int calculateXPCost() {
	   UpgradeBenchRecipe r = UpgradeBenchRecipes.getUpgradeRecipeFor(this.inputSlots.getStackInSlot(0), this.inputSlots.getStackInSlot(1));
	   if(r!=null) {
		   return calculateXPCost(r);
	   } else {
		   return 0;
	   }
   }
   
   public int calculateXPCost(UpgradeBenchRecipe r) {
       Map<Enchantment, Integer> map = EnchantmentHelper.getEnchantments(this.inputSlots.getStackInSlot(0));
       
       if(map.containsKey(r.getEnch())) {
    	   Integer level = map.get(r.getEnch());
    	   
    	   if(r.getLevel()>level) {	   
    		   int dif = r.getLevel()-level;
    		   return TGConfig.upgrade_xp_cost*dif;
    	   } else {
    		   return 0;
    	   }
    	   
       } else {
    	   return TGConfig.upgrade_xp_cost*r.getLevel();
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
					
					if (id >=0 && id<=HIGHEST_SLOT){
						if (!this.mergeItemStack(stack1, HIGHEST_SLOT+1, MAXSLOTS, false)) {
							return ItemStack.EMPTY;
						}
						slot.onSlotChange(stack1, stack);
					} else if (id >HIGHEST_SLOT && id <MAXSLOTS){
						
						int validslot = this.getValidSlotForItemInInventory(stack1);
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

	protected int getValidSlotForItemInInventory(ItemStack stack1) {
		if (!stack1.isEmpty() && stack1.getItem() instanceof GenericArmor) {
			return 0;
		} else if(UpgradeBenchRecipes.getUpgradeRecipeForUpgradeItem(stack1) != null) {
			return 1;
		}  
		return -1;
	}
   
}
