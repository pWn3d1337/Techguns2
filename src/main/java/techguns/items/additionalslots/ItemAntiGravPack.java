package techguns.items.additionalslots;

import java.util.List;

import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.gameevent.TickEvent.PlayerTickEvent;
import techguns.TGItems;
import techguns.api.tginventory.TGSlotType;
import techguns.items.armors.TGArmorBonus;
import techguns.util.TextUtil;

public class ItemAntiGravPack extends ItemTGSpecialSlotAmmo {
	
	private static final int CONSUMPTION_PER_TICK =1;
	
	private static final float FLYBONUS=0.25f;
	
	public ItemAntiGravPack(String unlocalizedName, int camoCount,int dur) {
		super(unlocalizedName, TGSlotType.BACKSLOT, camoCount,dur,TGItems.NUCLEAR_POWERCELL,TGItems.NUCLEAR_POWERCELL_EMPTY );
	}

	@Override
	public void onPlayerTick(ItemStack item, PlayerTickEvent event) {
		if (item.getItemDamage()+1>= item.getMaxDamage()){
			this.tryReloadAndRepair(item, event.player);
		}
		if (event.player.capabilities.isFlying && !event.player.capabilities.isCreativeMode){
			if (item.getItemDamage()<item.getMaxDamage()){
				item.setItemDamage(item.getItemDamage()+1);
				//this.doEffect(player.player);
			}
		}
	}

	@Override
	public float getBonus(TGArmorBonus type, ItemStack stack, boolean consume, EntityPlayer player) {
		if (type==TGArmorBonus.CREATIVE_FLIGHT){
			if (stack.getItemDamage()<stack.getMaxDamage()){
				return 1;
			}
		} else if (type==TGArmorBonus.FLYSPEED){
			if (stack.getItemDamage()<stack.getMaxDamage()){
				return FLYBONUS;
			}			
		}
		return 0;
	}
	
	@Override
	public void addInformation(ItemStack stack, World worldIn, List<String> tooltip, ITooltipFlag flagIn) {
		tooltip.add(TextUtil.trans("techguns.armorTooltip.creativeFlight"));
		tooltip.add(TextUtil.trans("techguns.armorTooltip.flyspeed")+": +"+FLYBONUS*100.0f+"%");
	}
}
	