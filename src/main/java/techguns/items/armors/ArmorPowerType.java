package techguns.items.armors;

public enum ArmorPowerType {

	STEAM,
	RF;

	@Override
	public String toString() {
		switch(this){
			case STEAM:
				return "Steam";
			case RF:
				return "RF";
			default:
				return "INVALID";
		}
	}
}
