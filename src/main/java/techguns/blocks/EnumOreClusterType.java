package techguns.blocks;

public enum EnumOreClusterType implements IEnumOreClusterType {
	//Parameter Values are not actually used anymore
	COAL(0,1f),
	COMMON_METAL(0,1f),
	RARE_METAL(1,0.8f),
	SHINY_METAL(2,0.5f),
	URANIUM(3,0.1f),
	COMMON_GEM(1,0.8f),
	SHINY_GEM(3,0.1f),
	NETHER_CRYSTAL(2,0.5f),
	OIL(2,0.5f)
	;

	protected int mininglevel;
	protected float multiplier;
	
	private EnumOreClusterType(int mininglevel, float multiplier) {
		this.mininglevel=mininglevel;
		this.multiplier=multiplier;
	}
	
	@Override
	public String getName() {
		return this.name().toLowerCase();
	}

	@Override
	public int getMiningLevel() {
		return mininglevel;
	}

	@Override
	public float getMultiplier() {
		return multiplier;
	}

	public static EnumOreClusterType getFromString(String str) {
		for(EnumOreClusterType e: EnumOreClusterType.class.getEnumConstants()) {
			if(e.name().equalsIgnoreCase(str)) {
				return e;
			}
		}
		return null;
	}
}
