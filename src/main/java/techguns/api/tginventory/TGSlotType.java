package techguns.api.tginventory;

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
	DRILL_LARGE;

	@Override
	public String toString() {
		switch(this){
			case FACESLOT:
				return "Face";
			case BACKSLOT:
				return "Back";
			case HANDSLOT:
				return "Hands";
			case AMMOSLOT:
				return "Ammo";
			case FOODSLOT:
				return "Food";
			case HEALSLOT:
				return "Healing";
			case TURRETARMOR:
				return "Turret armor";
			case REACTION_CHAMBER_FOCUS:
				return "Reaction Chamber Focus";
			case DRILL_SMALL:
				return "Small Drill";
			case DRILL_MEDIUM:
				return "Medium Drill";
			case DRILL_LARGE:
				return "Large Drill";
			default:
				return "Normal Item";
		}
	}
	
}
