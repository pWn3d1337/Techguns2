package techguns.items.guns;

import java.util.List;

import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.util.SoundEvent;
import net.minecraft.world.World;
import techguns.TGItems;
import techguns.TGSounds;
import techguns.util.ItemUtil;
import techguns.util.TextUtil;

public class MiningDrill extends GenericGunMeleeCharge {

	public MiningDrill(String name, ChargedProjectileSelector projectile_selector, boolean semiAuto, int minFiretime,
			int clipsize, int reloadtime, float damage, SoundEvent firesound, SoundEvent reloadsound, int TTL,
			float accuracy, float fullChargeTime, int ammoConsumedOnFullCharge) {
		super(name, projectile_selector, semiAuto, minFiretime, clipsize, reloadtime, damage, firesound, reloadsound, TTL,
				accuracy, fullChargeTime, ammoConsumedOnFullCharge);
		
		this.setMiningHeads(TGItems.MININGDRILLHEAD_CARBON);
	}

	@Override
	public ActionResult<ItemStack> onItemRightClick(World worldIn, EntityPlayer player, EnumHand handIn) {
		ItemStack stack = player.getHeldItem(handIn);
		this.shootGunPrimary(stack, worldIn, player, false, handIn);
		return new ActionResult<ItemStack>(EnumActionResult.FAIL, stack);
	}

	
	
	@Override
	protected void addMiningTooltip(ItemStack stack, World world, List<String> list, ITooltipFlag flagIn,
			boolean longTooltip) {
		super.addMiningTooltip(stack, world, list, flagIn, longTooltip);
		if (longTooltip) {
			list.add(TextUtil.trans("techguns.tooltip.sneaksilktouch"));
		}
	}

	@Override
	public int getExtraMiningRadius(ItemStack stack) {
		return this.getMiningHeadLevel(stack);
	}

	@Override
	protected SoundEvent getSwingSound() {
		return TGSounds.CHAINSAW_LOOP;
	}
	
	@Override
	protected SoundEvent getBlockBreakSound() {
		return TGSounds.CHAINSAW_HIT;
	}
	
}
