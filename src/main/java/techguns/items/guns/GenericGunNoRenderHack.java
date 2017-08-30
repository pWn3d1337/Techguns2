package techguns.items.guns;

import net.minecraft.item.ItemStack;
import net.minecraft.util.SoundEvent;
import techguns.items.guns.ammo.AmmoType;

public class GenericGunNoRenderHack extends GenericGun {

	public GenericGunNoRenderHack(String name, IProjectileFactory projectilefactory, AmmoType ammo, boolean semiAuto, int minFiretime, int clipsize, int reloadtime, float damage,
			SoundEvent firesound, SoundEvent reloadsound, int TTL, float accuracy) {
		super(name, projectilefactory, ammo, semiAuto, minFiretime, clipsize, reloadtime, damage, firesound, reloadsound, TTL, accuracy);
	}

	@Override
	public boolean shouldUseRenderHack(ItemStack stack) {
		return false;
	}

}
