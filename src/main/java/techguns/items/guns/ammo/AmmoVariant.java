package techguns.items.guns.ammo;

import net.minecraft.item.ItemStack;

public class AmmoVariant {

	protected String key;
	protected ItemStack[] ammo;
	protected ItemStack[] bullet;
	
	public AmmoVariant(ItemStack[] ammo, ItemStack[] bullet) {
		this(AmmoTypes.TYPE_DEFAULT,ammo,bullet);
	}
	
	public AmmoVariant(String key, ItemStack[] ammo, ItemStack[] bullet) {
		super();
		this.key = key;
		this.bullet = bullet;
		this.ammo = ammo;
	}

	public String getKey() {
		return key;
	}
}
