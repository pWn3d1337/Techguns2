package techguns.items.additionalslots;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.gameevent.TickEvent.PlayerTickEvent;
import techguns.TGItems;
import techguns.api.tginventory.TGSlotType;
import techguns.capabilities.TGExtendedPlayer;
import techguns.items.armors.TGArmorBonus;

public class ItemNightVisionGoggles extends ItemTGSpecialSlotAmmo {

	public ItemNightVisionGoggles(String unlocalizedName, int camoCount,int dur) {
		super(unlocalizedName, TGSlotType.FACESLOT, camoCount,dur, TGItems.REDSTONE_BATTERY, TGItems.REDSTONE_BATTERY_EMPTY);
	}

	@Override
	public void onPlayerTick(ItemStack item, PlayerTickEvent event) {
		if(item.getItemDamage()<item.getMaxDamage()){
			TGExtendedPlayer extendedPlayer = TGExtendedPlayer.get(event.player);
			if(extendedPlayer.enableNightVision){
				item.setItemDamage(item.getItemDamage()+1);
			}
		} else {
			this.tryReloadAndRepair(item, event.player);
		}
	}
	
	@Override
	public float getBonus(TGArmorBonus type,ItemStack stack, boolean consume, EntityPlayer player){
		if(type==TGArmorBonus.NIGHTVISION){
			if (stack.getItemDamage()< stack.getMaxDamage()){
				return 1.0f;
			}
		}
		return 0.0f;
	}

}