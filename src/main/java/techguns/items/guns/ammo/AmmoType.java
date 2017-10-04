package techguns.items.guns.ammo;

import java.util.ArrayList;

import net.minecraft.item.ItemStack;

public class AmmoType {

	protected ItemStack emptyMag = ItemStack.EMPTY;
	protected ArrayList<AmmoVariant> variants = new ArrayList<>();
	
	int bulletsPerMag=0;
	
	public AmmoType(ItemStack ammo){
		this.variants.add(new AmmoVariant(ammo,ammo));
	}
	
	public ItemStack getAmmo(int variant) {
		return this.variants.get(variant).ammo;
	}

	public ItemStack getEmptyMag() {
		return emptyMag;
	}

	public ItemStack getBullet(int variant) {
		return this.variants.get(variant).bullet;
	}

	public AmmoType (ItemStack ammo, ItemStack emptyMag, ItemStack bullet, int bulletsPerMag) {
		this.variants.add(new AmmoVariant(ammo,bullet));
		this.emptyMag = emptyMag;

		this.bulletsPerMag = bulletsPerMag;
	}
	
	public float getShotsPerBullet (int clipsize, int ammoCount){
		if(ammoCount==1 && bulletsPerMag==0){
			return clipsize;
		}
		float bpm = bulletsPerMag==0 ? (float)clipsize/(float)ammoCount : bulletsPerMag;
		
		return ((float)clipsize)/bpm;
	}
	
}
