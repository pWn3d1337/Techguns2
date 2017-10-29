package techguns.items.guns;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.util.SoundEvent;
import net.minecraft.world.World;
import techguns.TGSounds;
import techguns.items.guns.ammo.AmmoType;

public class Chainsaw extends GenericGunMeleeCharge {

	public Chainsaw(String name, ChargedProjectileSelector projectile_selector, boolean semiAuto, int minFiretime,
			int clipsize, int reloadtime, float damage, SoundEvent firesound, SoundEvent reloadsound, int TTL,
			float accuracy, float fullChargeTime, int ammoConsumedOnFullCharge) {
		super(name, projectile_selector, semiAuto, minFiretime, clipsize, reloadtime, damage, firesound, reloadsound, TTL,
				accuracy, fullChargeTime, ammoConsumedOnFullCharge);
	}

	@Override
	public ActionResult<ItemStack> onItemRightClick(World worldIn, EntityPlayer player, EnumHand handIn) {
		ItemStack stack = player.getHeldItem(handIn);
		this.shootGunPrimary(stack, worldIn, player, false, handIn);
		return new ActionResult<ItemStack>(EnumActionResult.FAIL, stack);
	}

	@Override
	protected SoundEvent getSwingSound() {
		return TGSounds.CHAINSAW_LOOP;
	}
	
	@Override
	protected SoundEvent getBlockBreakSound() {
		return TGSounds.CHAINSAW_HIT;
	}
	
	@Override
	protected void playSweepSoundEffect(EntityPlayer player) {
		player.world.playSound((EntityPlayer) null, player.posX, player.posY, player.posZ, TGSounds.CHAINSAW_HIT,
				player.getSoundCategory(), 1.0F, 1.0F);
	}

}
