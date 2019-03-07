package techguns.damagesystem;

import techguns.api.damagesystem.DamageType;
import techguns.util.MathUtil;

/**
 * Defensive stats of a shield against projectiles
 */
public class ShieldStats {
	
	//damage threshold, damage less than this will have no effect, subtract from reduced damage
	protected double dt=0f;
	//reduction rate for each damage type, 0.0= take full damage, 1.0= take no damage
	protected double[] rates; 
	//used for sound effects
	protected ShieldMaterial mat;
	
	public static ShieldStats VANILLA_SHIELD = new ShieldStats(ShieldMaterial.WOOD, 0d, new double[] {
		1.0d,	//PHYSICAL
		0.35d,	//PROJECTILE
		0.35d,	//FIRE
		0.35d,	//EXPLOSION
		0.35d,	//ENERGY
		0.35d,	//POISON
		0.0d,	//UNRESISTABLE
		0.35d,	//ICE
		0.35d,	//LIGHTNING
		0.0d,	//RADIATION
		0.0d	//DARK
	});
	
	/**
	 * used for modded shields by default
	 */
	public static ShieldStats DEFAULT_STATS = new ShieldStats(ShieldMaterial.METAL, 0d, new double[] {
			1.0d,	//PHYSICAL
			0.5d,	//PROJECTILE
			0.5d,	//FIRE
			0.5d,	//EXPLOSION
			0.5d,	//ENERGY
			0.5d,	//POISON
			0.0d,	//UNRESISTABLE
			0.5d,	//ICE
			0.5d,	//LIGHTNING
			0.25d,	//RADIATION
			0.25d	//DARK
		});
	
	public ShieldStats(double dt, double[] rates) {
		this(ShieldMaterial.METAL,dt,rates);
	}
	
	public ShieldStats(ShieldMaterial mat, double dt, double[] rates) {
		super();
		this.mat=mat;
		this.dt = dt;
		this.rates = rates;
	}
	
	public ShieldStats(double dt, double allrates) {
		this(ShieldMaterial.METAL,dt,allrates);
	}
	
	public ShieldStats(ShieldMaterial mat, double dt, double allrates) {
		super();
		this.mat=mat;
		this.dt = dt;
		this.rates = new double[DamageType.class.getEnumConstants().length];
		for(int i=0;i<rates.length; i++) {
			rates[i]=allrates;
		}
	}
	
	public float getAmount(float amount, TGDamageSource src) {
		
		double d = amount*(1d-rates[src.damageType.ordinal()]);
		
		double effective_dt = Math.max(dt-(src.armorPenetration*2d),0d);
		
		return (float) Math.max(d-effective_dt,0d);
	}

	public static enum ShieldMaterial {
		WOOD,
		STONE,
		METAL,
		GLASS,
		GROUND
	}
}
