package techguns.items.additionalslots;

import net.minecraft.init.MobEffects;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionEffect;
import net.minecraftforge.fml.common.gameevent.TickEvent.PlayerTickEvent;
import techguns.api.tginventory.TGSlotType;

public class ItemGasMask extends ItemTGSpecialSlot {

	public ItemGasMask(String unlocalizedName,  int camoCount,int dur) {
		super(unlocalizedName, TGSlotType.FACESLOT, camoCount,dur);
	}
	
	@Override
	public void onPlayerTick(ItemStack item, PlayerTickEvent event) {
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
	
}
