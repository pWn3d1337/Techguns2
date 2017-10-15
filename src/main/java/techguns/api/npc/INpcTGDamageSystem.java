package techguns.api.npc;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import techguns.damagesystem.TGDamageSource;

public interface INpcTGDamageSystem {
	public float getTotalArmorAgainstType(TGDamageSource dmgsrc);
	public float getPenetrationResistance(TGDamageSource dmgsrc);

	/**
	 * @param elb - should be considered "this", passed as param to allow default implementation
	 * @param src
	 * @return
	 */
	public default float getToughnessAfterPentration(EntityLivingBase elb, TGDamageSource src) {
		float toughness = (float)elb.getEntityAttribute(SharedMonsterAttributes.ARMOR_TOUGHNESS).getAttributeValue();
		float pen = Math.max(src.armorPenetration-this.getPenetrationResistance(src),0f)*4f; //per part for players, so *4 for total
		return Math.max(toughness-pen,0f);
	}
}

