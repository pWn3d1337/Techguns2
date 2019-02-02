package techguns.items.armors;

public enum EnumArmorStat {
	/**
	 * Movement speed bonus
	 */
	 SPEED,
	 /**
	  * jump height bonus
	  */
	 JUMP,
	 /**
	  * fall damage reduction
	  */
	 FALL_DAMAGE,
	 /**
	  * free fall height
	  */
	 FALL_HEIGHT,
	 /**
	  * mining speed bonus
	  */
	 MINING_SPEED,
	 /**
	  * underwater mining speed bonus
	  */
	 WATER_MINING_SPEED,
	 /**
	  * gun accuracy, less random spread
	  */
	 GUN_ACCURACY,
	 /**
	  * extra hp points
	  */
	 EXTRA_HEARTS,
	 /**
	  * When total >=1, player can use nightvision
	  */
	 NIGHTVISION,
	 /**
	  * chance to not get knocked back
	  */
	 KNOCKBACK_RESITANCE,
	 /**
	  * allow steping over 1block height
	  */
	 STEP_ASSIST,
	 /**
	  * when total >=1, oxygen tanks work to give air
	  */
	 OXYGEN_GEAR,
	 /**
	  * provide oxygen from power
	  */
	 WATER_ELECTROLYZER,
	 /**
	  * extinguish player when burning with power cost
	  */
	 COOLING_SYSTEM,
	 /**
	  * underwater speed
	  */
	 WATER_SPEED,
	
	/**
	 * rad resistance entity attribute
	 */
	RAD_RESISTANCE;
	
	public static EnumArmorStat parseFromString(String s) {
		for(EnumArmorStat e :EnumArmorStat.values()) {
			if(e.name().equalsIgnoreCase(s)) {
				return e;
			}
		}
		return null;
	}

}
