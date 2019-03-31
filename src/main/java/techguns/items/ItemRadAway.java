package techguns.items;

import java.util.List;

import com.mojang.realmsclient.gui.ChatFormatting;

import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.EnumAction;
import net.minecraft.item.ItemFood;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.SoundEvent;
import net.minecraft.world.World;
import techguns.TGItems;
import techguns.TGRadiationSystem;
import techguns.Techguns;
import techguns.util.TextUtil;

public class ItemRadAway extends GenericItemConsumable {

	public ItemRadAway(String name) {
		super(name, 32);
	}
	
	public ItemRadAway(String name, boolean addToItemList) {
		super(name, 32, addToItemList);
	}

	@Override
	protected void onConsumed(ItemStack stack, World worldIn, EntityPlayer player) {
		player.addPotionEffect(new PotionEffect(TGRadiationSystem.radregen_effect, 400, 14, false, false));
		
		if(!worldIn.isRemote) {
			ItemStack bottle = TGItems.newStack(TGItems.INFUSION_BAG, 1);
			if(!player.addItemStackToInventory(bottle)) {
				 worldIn.spawnEntity(new EntityItem(worldIn, player.posX, player.posY, player.posZ, bottle));
			}
		}
	}

	@Override
	protected SoundEvent getConsumedSound() {
		return SoundEvents.ENTITY_PLAYER_BREATH;
	}

	@Override
	public EnumAction getItemUseAction(ItemStack stack) {
		return EnumAction.BOW;
	}

	@Override
	public void addInformation(ItemStack stack, World worldIn, List<String> tooltip, ITooltipFlag flagIn) {
		super.addInformation(stack, worldIn, tooltip, flagIn);
		tooltip.add(ChatFormatting.BLUE+TextUtil.trans("techguns.radregeneration")+" "+TextUtil.trans("potion.potency.9") +" (-300)");
	}
	
}
