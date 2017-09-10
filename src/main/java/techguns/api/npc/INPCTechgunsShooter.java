package techguns.api.npc;

/**
 *	Must only be implemented by subclasses of EntityLivingBase
 */
public interface INPCTechgunsShooter {
	public float getWeaponPosX();
	public float getWeaponPosY();
	public float getWeaponPosZ();
	public default boolean hasWeaponArmPose() {
		return true;
	}
	public default float getGunScale() {
		return 1.0f;
	}
	
	public default float getBulletOffsetSide() {
		return 0f;
	}
	public default float getBulletOffsetHeight() {
		return 0f;
	}
}
