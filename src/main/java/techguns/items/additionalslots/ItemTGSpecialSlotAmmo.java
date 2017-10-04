package techguns.items.additionalslots;

import java.util.List;

import com.mojang.realmsclient.gui.ChatFormatting;

import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import techguns.TGItems;
import techguns.api.tginventory.TGSlotType;
import techguns.util.InventoryUtil;
import techguns.util.TextUtil;

public abstract class ItemTGSpecialSlotAmmo extends ItemTGSpecialSlot {

	ItemStack ammo;
	ItemStack ammoEmpty;
	
	public ItemTGSpecialSlotAmmo(String unlocalizedName, TGSlotType slot, int camoCount, int dur, ItemStack ammo, ItemStack ammoEmpty) {
		super(unlocalizedName, slot, camoCount, dur);
		this.ammo=ammo;
		this.ammoEmpty=ammoEmpty;
	}
	
	public ItemStack getAmmo() {
		return ammo;
	}

	public ItemStack getAmmoEmpty() {
		return ammoEmpty;
	}

	@Override
	public void addInformation(ItemStack stack, World worldIn, List<String> tooltip, ITooltipFlag flagIn) {
		super.addInformation(stack, worldIn, tooltip, flagIn);
		tooltip.add(ChatFormatting.AQUA+"Reloads with: "+TextUtil.trans(this.ammo.getUnlocalizedName()+".name"));
	}

	protected void tryReloadAndRepair(ItemStack item, EntityPlayer ply){
		if(InventoryUtil.consumeAmmoPlayer(ply, ammo)){
			item.setItemDamage(0);
			if(InventoryUtil.addAmmoToPlayerInventory(ply, ammoEmpty)>0){
				if(!ply.world.isRemote){
					ply.world.spawnEntity(new EntityItem(ply.world, ply.posX, ply.posY, ply.posZ, TGItems.newStack(ammoEmpty, 1)));
				}
			}
		}
	}
}