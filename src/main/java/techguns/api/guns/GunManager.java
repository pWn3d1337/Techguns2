package techguns.api.guns;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;

public class GunManager {

	/**
	 * Return if the current off hand weapon can be used depending on dual wield system (1hand,2hand weapons)
	 * @param mainHand
	 * @param offHand
	 * @param ent
	 * @return
	 */
	public static boolean canUseOffhand(EntityLivingBase ent) {
		return canUseOffhand(ent.getHeldItemMainhand(), ent.getHeldItemOffhand(), ent);
	}
	
	/**
	 * Return if the current off hand weapon can be used depending on dual wield system (1hand,2hand weapons)
	 * @param mainHand
	 * @param offHand
	 * @param ent
	 * @return
	 */
	public static boolean canUseOffhand(ItemStack mainHand, ItemStack offHand, EntityLivingBase ent) {
		if (!offHand.isEmpty()&& offHand.getItem() instanceof IGenericGun) {
			IGenericGun gun = (IGenericGun) offHand.getItem();
			if (gun.getGunHandType()==GunHandType.TWO_HANDED) {
				return false; //Don't allow two handed gun in off hand
			}
		}
		if (!mainHand.isEmpty()&& mainHand.getItem() instanceof IGenericGun) {
			IGenericGun gun = (IGenericGun) mainHand.getItem();
			if (gun.getGunHandType()==GunHandType.TWO_HANDED) {
				return false; //Don't allow 1handed gun in offhand, when mainhand has 2h gun
			}
		}
		return true;
	}
	
}
