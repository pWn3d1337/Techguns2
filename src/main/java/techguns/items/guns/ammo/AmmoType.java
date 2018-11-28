package techguns.items.guns.ammo;

import java.util.ArrayList;
import java.util.HashMap;

import net.minecraft.item.ItemStack;
import techguns.util.ItemUtil;

public class AmmoType {

	protected ItemStack[] emptyMag = {ItemStack.EMPTY};
	protected ArrayList<AmmoVariant> variants = new ArrayList<>();
	
	protected HashMap<String,Integer> ammoVariantIDs = new HashMap<>();
	
	
	protected int bulletsPerMag=0;
	
	public AmmoType(ItemStack... ammo){
		this.variants.add(new AmmoVariant(ammo,ammo));
		ammoVariantIDs.put(AmmoTypes.TYPE_DEFAULT, 0);
	}

	public AmmoType addVariant(String key, ItemStack ammo, ItemStack bullet) {
		return this.addVariant(key, new ItemStack[] {ammo}, new ItemStack[] {bullet});
	}
	
	public AmmoType addVariant(String key, ItemStack... ammo) {
		return this.addVariant(key, ammo, ammo);
	}
	
	public int getIDforVariantKey(String key) {
		if (ammoVariantIDs.containsKey(key)) {
			return ammoVariantIDs.get(key);
		} else {
			return 0;
		}
	}
	
	public AmmoType addVariant(String key, ItemStack[] ammo, ItemStack[] bullet) {
		this.variants.add(new AmmoVariant(key, ammo, bullet));
		ammoVariantIDs.put(key, this.variants.size()-1);
		return this;
	}
	
	public ItemStack[] getAmmo(int variant) {
		return this.variants.get(variant).ammo;
	}

	public ItemStack[] getEmptyMag() {
		return emptyMag;
	}

	public ItemStack[] getBullet(int variant) {
		return this.variants.get(variant).bullet;
	}

	public AmmoType (ItemStack ammo, ItemStack emptyMag, ItemStack bullet, int bulletsPerMag) {
		this(new ItemStack[]{ammo},new ItemStack[]{emptyMag},new ItemStack[]{bullet},bulletsPerMag);
	}
	
	public AmmoType (ItemStack[] ammo, ItemStack[] emptyMag, ItemStack[] bullet, int bulletsPerMag) {
		this.variants.add(new AmmoVariant(ammo,bullet));
		this.emptyMag = emptyMag;

		this.bulletsPerMag = bulletsPerMag;
	}
	
	public String getAmmoVariantKeyfor(ItemStack stack, int index) {
		for (int i=0;i<this.variants.size();i++) {
			if(ItemUtil.isItemEqual(variants.get(i).ammo[index],stack)) {
				return variants.get(i).key;
			}
		}
		return AmmoTypes.TYPE_DEFAULT;
	}
	
	public ArrayList<AmmoVariant> getVariants() {
		return variants;
	}

	public boolean hasMultipleVariants() {
		return this.variants.size()>1;
	}
	
	public float getShotsPerBullet (int clipsize, int ammoCount){
		if(ammoCount==1 && bulletsPerMag==0){
			return clipsize;
		}
		float bpm = bulletsPerMag==0 ? (float)clipsize/(float)ammoCount : bulletsPerMag;
		
		return ((float)clipsize)/bpm;
	}

	public int getBulletsPerMag() {
		return bulletsPerMag;
	}

}
