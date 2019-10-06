package techguns.items.guns;

public enum EnumCrosshairStyle {
	VANILLA, //use vanilla crosshair, no TG code at all
	GUN_DYNAMIC, //has special code
	TRI(EnumCrosshairDynamicType.TRI),
	QUAD_CORNERS_DOT(EnumCrosshairDynamicType.X),
	TRI_INV(EnumCrosshairDynamicType.TRI_INV),
	FOUR_PARTS(EnumCrosshairDynamicType.BOTH),
	FOUR_PARTS_SPIKED(EnumCrosshairDynamicType.BOTH),
	HORIZONTAL_TWO_PART(EnumCrosshairDynamicType.HORIZONTAL),
	HORIZONTAL_TWO_PART_LARGE(EnumCrosshairDynamicType.HORIZONTAL),
	HORIZONTAL_TWO_PART_E(EnumCrosshairDynamicType.HORIZONTAL),
	HORIZONTAL_SMALL(EnumCrosshairDynamicType.HORIZONTAL),
	QUAD_NO_CORNERS(EnumCrosshairDynamicType.BOTH);
	
	
	public final EnumCrosshairDynamicType dynamicType;
	
	private EnumCrosshairStyle() {
		this(EnumCrosshairDynamicType.NONE);
	}
	
	private EnumCrosshairStyle(EnumCrosshairDynamicType dynamicType) {
		this.dynamicType=dynamicType;
	}
	
	public int getLocX() {
		if (this!=VANILLA) {
			return (this.ordinal()-1)%16 * 16; 
		}
		
		return 0;
	}
	
	public int getLocY() {
		if (this!=VANILLA) {
			return (this.ordinal()-1)/16; 
		}
		
		return 0;
	}
}