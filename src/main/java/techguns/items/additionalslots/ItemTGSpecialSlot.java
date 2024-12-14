package techguns.items.additionalslots;

import java.util.List;

import com.mojang.realmsclient.gui.ChatFormatting;

import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import techguns.Techguns;
import techguns.api.tginventory.ITGSpecialSlot;
import techguns.api.tginventory.TGSlotType;
import techguns.capabilities.TGExtendedPlayer;
import techguns.gui.player.TGPlayerInventory;
import techguns.items.GenericItem;
import techguns.items.armors.ICamoChangeable;
import techguns.util.TextUtil;

public abstract class ItemTGSpecialSlot extends GenericItem implements ITGSpecialSlot, ICamoChangeable {
	protected ResourceLocation texture;
	protected byte camoCount;
	
	protected TGSlotType slot;
	
	public ItemTGSpecialSlot(String unlocalizedName, TGSlotType slot, int camoCount,int dur) {
		super(unlocalizedName);
	    this.maxStackSize=1;
	    this.setMaxDamage(dur);
	    this.camoCount = (byte) camoCount;
	    this.slot=slot;
	    
	}
	
	@Override
	public int getCamoCount() {
		return this.camoCount;
	}

	@Override
	public String getCurrentCamoName(ItemStack item) {
		NBTTagCompound tags = item.getTagCompound();
		byte camoID=0;
		if (tags!=null && tags.hasKey("camo")){
			camoID=tags.getByte("camo");
		}
		if(camoID>0){
			return TextUtil.trans(this.getTranslationKey()+".camoname."+camoID);
		} else {
			return TextUtil.trans("techguns.item.defaultcamo");
		}
	}

	@Override
	public TGSlotType getSlot(ItemStack item) {
		return slot;
	}

	@Override
	public void addInformation(ItemStack stack, World worldIn, List<String> tooltip, ITooltipFlag flagIn) {
		super.addInformation(stack, worldIn, tooltip, flagIn);
		tooltip.add(ChatFormatting.GRAY+TextUtil.trans(Techguns.MODID+".tooltip.slot")+": "+this.getSlot(stack));
	}
	
	@Override
	public ActionResult<ItemStack> onItemRightClick(World worldIn, EntityPlayer playerIn, EnumHand handIn) {
		ItemStack stack = playerIn.getHeldItem(handIn);
		
		if(this.getSlot(stack)==TGSlotType.BACKSLOT) {
			TGExtendedPlayer props  = TGExtendedPlayer.get(playerIn);
			if (props.tg_inventory.inventory.get(TGPlayerInventory.SLOT_BACK).isEmpty()){
				props.tg_inventory.inventory.set(TGPlayerInventory.SLOT_BACK,stack.copy());
				stack.setCount(0);
				return new ActionResult<ItemStack>(EnumActionResult.SUCCESS, stack);
			}
		} else if (this.getSlot(stack)==TGSlotType.FACESLOT){
			TGExtendedPlayer props  = TGExtendedPlayer.get(playerIn);
			if (props.tg_inventory.inventory.get(TGPlayerInventory.SLOT_FACE).isEmpty()){
				props.tg_inventory.inventory.set(TGPlayerInventory.SLOT_FACE,stack.copy());
				stack.setCount(0);
				return new ActionResult<ItemStack>(EnumActionResult.SUCCESS, stack);
			}
		} else if (this.getSlot(stack)==TGSlotType.HANDSLOT){
			TGExtendedPlayer props  = TGExtendedPlayer.get(playerIn);
			if (props.tg_inventory.inventory.get(TGPlayerInventory.SLOT_HAND).isEmpty()){
				props.tg_inventory.inventory.set(TGPlayerInventory.SLOT_HAND,stack.copy());
				stack.setCount(0);
				return new ActionResult<ItemStack>(EnumActionResult.SUCCESS, stack);
			}
		}
		return super.onItemRightClick(worldIn, playerIn, handIn);
	}

}
