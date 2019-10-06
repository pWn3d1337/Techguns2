package techguns.plugins.crafttweaker;

public enum EnumGunStat {
	/**
	 * The damage dealt close range
	 */
	DAMAGE,
	/**
	 * the damage dealt at far range
	 */
	DAMAGE_MIN,
	/**
	 * distance (in blocks) when damage starts to drop, between START and END damage is linear interpolated between DAMAGE and DAMAGE_MIN
	 */
	DAMAGE_DROP_START,
	/**
	 * distance (in blocks) when damage drop ends, targets farther away will take DAMAGE_MIN damage,
	 */
	DAMAGE_DROP_END,
	/**
	 * how fast the projectile flies in blocks per tick
	 */
	BULLET_SPEED,
	/**
	 * how far the projectile can fly before it gets deleted
	 */
	BULLET_DISTANCE,
	/**
	 * Gravity strength of the projectile
	 */
	GRAVITY,
	/**
	 * Mining speed, only has an effect on tools
	 */
	MINING_SPEED,
	/**
	 * How much shots randomly divert
	 */
	SPREAD;
	
	public static EnumGunStat parseFromString(String s) {
		for(EnumGunStat e :EnumGunStat.values()) {
			if(e.name().equalsIgnoreCase(s)) {
				return e;
			}
		}
		return null;
	}

}
