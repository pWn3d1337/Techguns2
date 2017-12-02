package techguns.items.guns;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.util.SoundEvent;
import net.minecraft.world.World;
import techguns.api.damagesystem.DamageType;
import techguns.damagesystem.TGDamageSource;
import techguns.deatheffects.EntityDeathUtils.DeathType;

public class Shishkebap extends GenericGunMeleeCharge {

	public Shishkebap(String name, ChargedProjectileSelector projectile_selector, boolean semiAuto, int minFiretime,
			int clipsize, int reloadtime, float damage, SoundEvent firesound, SoundEvent reloadsound, int TTL,
			float accuracy, float fullChargeTime, int ammoConsumedOnFullCharge) {
		super(name, projectile_selector, semiAuto, minFiretime, clipsize, reloadtime, damage, firesound, reloadsound, TTL,
				accuracy, fullChargeTime, ammoConsumedOnFullCharge);
		this.setHasCustomAnim(false);
		this.setNoBowAnim();
	}
	
	@Override
	protected TGDamageSource getMeleeDamageSource(EntityPlayer player,ItemStack stack) {
		TGDamageSource src = new TGDamageSource("player", player, player, DamageType.FIRE, DeathType.GORE);
		if(this.getCurrentAmmo(stack)>0){
			src.goreChance=0.35f;
			src.armorPenetration=this.penetration;
			src.knockbackMultiplier=1f;
		} else{
			src.deathType = DeathType.DEFAULT;
		}
		return src;
	}

	@Override
	public ActionResult<ItemStack> onItemRightClick(World worldIn, EntityPlayer player, EnumHand handIn) {
		return new ActionResult<ItemStack>(EnumActionResult.PASS, player.getHeldItem(handIn));
	}

	@Override
	public boolean canCharge() {
		return false;
	}

	@Override
	public void onPlayerStoppedUsing(ItemStack item, World world, EntityLivingBase entityLiving, int timeLeft) {
	}

	@Override
	public int getMaxItemUseDuration(ItemStack stack) {
		return 0;
	}
	
	
}
