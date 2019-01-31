package techguns.items.guns.ammo;

public class DamageModifier {

	public static final DamageModifier DEFAULT_MODIFIER = new DamageModifier();
	
	protected float dmgMul=1.0f;
	protected float dmgAdd=0f;
	
	protected float radiusMul=1.0f;
	protected float radiusAdd=0f;
	
	protected float rangeMul=1.0f;
	protected float rangeAdd=0f;
	
	protected float velocityMul=1.0f;
	protected float velocityAdd=0f;
	
	public DamageModifier setDmg(float mul, float add) {
		this.dmgMul=mul;
		this.dmgAdd=add;
		return this;
	}
	
	public DamageModifier setRadius(float mul, float add) {
		this.radiusMul=mul;
		this.radiusAdd=add;
		return this;
	}
	
	public DamageModifier setRange(float mul, float add) {
		this.rangeMul=mul;
		this.rangeAdd=add;
		return this;
	}
	
	public DamageModifier setVelocity(float mul, float add) {
		this.velocityMul=mul;
		this.velocityAdd=add;
		return this;
	}
	
	public float getDamage(float dmg) {
		return dmg*dmgMul + dmgAdd;
	}
	
	public float getRadius(float rad) {
		return rad*radiusMul+radiusAdd;
	}

	public float getRange(float r) {
		return r*rangeMul+rangeAdd;
	}
	
	public int getTTL(int ttl) {
		return Math.round(ttl*rangeMul +rangeAdd);
	}

	public float getVelocity(float velocity) {
		return velocity*velocityMul+velocityAdd;
	}
	
	public float getDmgMul() {
		return dmgMul;
	}

	public float getDmgAdd() {
		return dmgAdd;
	}

	public float getRadiusMul() {
		return radiusMul;
	}

	public float getRadiusAdd() {
		return radiusAdd;
	}

	public float getRangeMul() {
		return rangeMul;
	}

	public float getRangeAdd() {
		return rangeAdd;
	}

	public float getVelocityMul() {
		return velocityMul;
	}

	public float getVelocityAdd() {
		return velocityAdd;
	}

	
	/*public GenericProjectile applyTo(GenericProjectile proj) {
		if(this.dmgMul!=1 || this.dmgAdd!=0){
			proj.damage = proj.damage*dmgMul +dmgAdd;
		}		
		
		if(this.radiusMul!=1 || this.radiusAdd!=0){
			proj.radius = proj.radius*radiusMul +radiusAdd;
		}
		
		if(this.rangeMult!=1 || this.rangeAdd!=0){
			proj.damageDropStart = proj.damageDropStart*rangeMult +rangeAdd;
			proj.damageDropEnd = proj.damageDropEnd*rangeMult +rangeAdd;
			proj.ticksToLive = Math.round (proj.ticksToLive*rangeMult +rangeAdd);
		}
		
		return proj;
	}*/
}
