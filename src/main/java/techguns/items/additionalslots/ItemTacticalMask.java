package techguns.items.additionalslots;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.MobEffects;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionEffect;
import net.minecraftforge.fml.common.gameevent.TickEvent.PlayerTickEvent;
import techguns.TGItems;
import techguns.api.tginventory.TGSlotType;
import techguns.capabilities.TGExtendedPlayer;
import techguns.items.armors.TGArmorBonus;

public class ItemTacticalMask extends ItemTGSpecialSlotAmmo {

	public ItemTacticalMask(String unlocalizedName, int camoCount, int dur) {
		super(unlocalizedName, TGSlotType.FACESLOT, camoCount, dur,TGItems.ENERGY_CELL, TGItems.ENERGY_CELL_EMPTY);
	}

	@Override
	public void onPlayerTick(ItemStack item, PlayerTickEvent event) {
		if(item.getItemDamage()<item.getMaxDamage()){
			if(TGExtendedPlayer.get(event.player).enableNightVision){
				item.setItemDamage(item.getItemDamage()+1);
			}
		} else {
			
			this.tryReloadAndRepair(item, event.player);
		}
		
		PotionEffect poison=event.player.getActivePotionEffect(MobEffects.POISON);
		PotionEffect wither=event.player.getActivePotionEffect(MobEffects.WITHER);
		
		if (poison !=null && item.getItemDamage()<item.getMaxDamage()){
			event.player.removePotionEffect(MobEffects.POISON);			
			item.setItemDamage(item.getItemDamage()+1);
		}
		if (wither !=null && item.getItemDamage()<item.getMaxDamage()){
			event.player.removePotionEffect(MobEffects.WITHER);			
			item.setItemDamage(item.getItemDamage()+1);
		}
	}
	
	@Override
	public float getBonus(TGArmorBonus type,ItemStack stack, boolean consume, EntityPlayer player){
		if(type==TGArmorBonus.NIGHTVISION || type == TGArmorBonus.OXYGEN_GEAR){
			if (stack.getItemDamage()< stack.getMaxDamage()){
				return 1.0f;
			}
		}
		return 0.0f;
	}
}