package techguns.gui.widgets;

import javax.annotation.Nullable;

import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class SlotArmor extends Slot {   
	private EntityEquipmentSlot armortype;
	private EntityPlayer ply;
	
	public SlotArmor(IInventory inv, int slot, int x,
			int y,EntityEquipmentSlot armorType,EntityPlayer ply) {
		super(inv, slot, x, y);
		this.armortype=armorType;
		this.ply=ply;
	}
	
	public SlotArmor(IInventory inv, int slot, int x,
			int y,int armorType,EntityPlayer ply) {
		super(inv, slot, x, y);
		this.armortype=getArmorTypeFromInt(armorType);
		this.ply=ply;
	}

	protected static EntityEquipmentSlot getArmorTypeFromInt(int slot) {
		switch(slot) {
		case 0:
			return EntityEquipmentSlot.HEAD;
		case 1:
			return EntityEquipmentSlot.CHEST;
		case 2:
			return EntityEquipmentSlot.LEGS;
		default:
			return EntityEquipmentSlot.FEET;
		}
	}
	
    /**
     * Returns the maximum stack size for a given slot (usually the same as getInventoryStackLimit(), but 1
     * in the case of armor slots)
     */
    @Override
    public int getSlotStackLimit()
    {
        return 1;
    }
    /**
     * Check if the stack is allowed to be placed in this slot, used for armor slots as well as furnace
     * fuel.
     */
    @Override
    public boolean isItemValid(ItemStack stack)
    {
        return stack.getItem().isValidArmor(stack, armortype, ply);
    }
    
    /**
     * Return whether this slot's stack can be taken from this slot.
     */
    @Override
    public boolean canTakeStack(EntityPlayer playerIn)
    {
        ItemStack itemstack = this.getStack();
        return !itemstack.isEmpty() && !playerIn.isCreative() && EnchantmentHelper.hasBindingCurse(itemstack) ? false : super.canTakeStack(playerIn);
    }
    
    @Nullable
    @SideOnly(Side.CLIENT)
    public String getSlotTexture()
    {
        return ItemArmor.EMPTY_SLOT_NAMES[armortype.getIndex()];
    }
    
}