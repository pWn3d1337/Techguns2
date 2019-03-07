package techguns.api.tginventory;

import techguns.util.TextUtil;

public enum TGSlotType {
	NORMAL,
	FACESLOT,
	BACKSLOT,
	HANDSLOT,
	AMMOSLOT,
	FOODSLOT,
	HEALSLOT,
	TURRETARMOR,
	REACTION_CHAMBER_FOCUS,
	DRILL_SMALL,
	DRILL_MEDIUM,
	DRILL_LARGE,
	ARMOR_UPGRADE;

	@Override
	public String toString() {
		switch(this){
			case FACESLOT:
				return trans("face");
			case BACKSLOT:
				return trans("back");
			case HANDSLOT:
				return trans("hands");
			case AMMOSLOT:
				return trans("ammo");
			case FOODSLOT:
				return trans("food");
			case HEALSLOT:
				return trans("healing");
			case TURRETARMOR:
				return trans("turret_armor");
			case REACTION_CHAMBER_FOCUS:
				return trans("reaction_focus");
			case DRILL_SMALL:
				return trans("small_drill");
			case DRILL_MEDIUM:
				return trans("medium_drill");
			case DRILL_LARGE:
				return trans("large_drill");
			case ARMOR_UPGRADE:
				return trans("armor_ugpgrade");
			default:
				return trans("normal_item");
		}
	}
	
	protected String trans(String key) {
		return TextUtil.transTG("tooltip.tgslottype."+key);
	}
	
}
