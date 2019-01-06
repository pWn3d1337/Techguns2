package techguns.items.armors;

import techguns.util.TextUtil;

public enum ArmorPowerType {

	STEAM,
	RF;

	@Override
	public String toString() {
		switch(this){
			case STEAM:
				return TextUtil.transTG("tooltip.powertype.Steam");
			case RF:
				return TextUtil.transTG("tooltip.powertype.RF");
			default:
				return "INVALID";
		}
	}
}
