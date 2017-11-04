package techguns.api.capabilities;

/**
 * Container class to group all values for recoil/reload timing together
 */
public class AttackTime {
	private long reloadTime=0;
	private int reloadTimeTotal=0;
	private long recoilTime=0;
	private int recoilTimeTotal=0;
	private byte attackType=0;
	private float recoilChargeProgress=0f;
	
	private long muzzleFlashTime=0;
	private int muzzleFlashTimeTotal=0;
	
	public long getReloadTime() {
		return reloadTime;
	}
	public void setReloadTime(long reloadTime) {
		this.reloadTime = reloadTime;
	}
	public int getReloadTimeTotal() {
		return reloadTimeTotal;
	}
	public void setReloadTimeTotal(int reloadTimeTotal) {
		this.reloadTimeTotal = reloadTimeTotal;
	}
	public long getRecoilTime() {
		return recoilTime;
	}
	public void setRecoilTime(long recoilTime) {
		this.recoilTime = recoilTime;
	}
	public int getRecoilTimeTotal() {
		return recoilTimeTotal;
	}
	public void setRecoilTimeTotal(int recoilTimeTotal) {
		this.recoilTimeTotal = recoilTimeTotal;
	}
	public byte getAttackType() {
		return attackType;
	}
	public void setAttackType(byte attackType) {
		this.attackType = attackType;
	}
	
	public long getMuzzleFlashTime() {
		return muzzleFlashTime;
	}
	public void setMuzzleFlashTime(long muzzleFlashTime) {
		this.muzzleFlashTime = muzzleFlashTime;
	}
	public int getMuzzleFlashTimeTotal() {
		return muzzleFlashTimeTotal;
	}
	public void setMuzzleFlashTimeTotal(int muzzleFlashTimeTotal) {
		this.muzzleFlashTimeTotal = muzzleFlashTimeTotal;
	}
	
	public float getRecoilChargeProgress() {
		return recoilChargeProgress;
	}
	public void setRecoilChargeProgress(float recoilChargeProgress) {
		this.recoilChargeProgress = recoilChargeProgress;
	}
	
	public void reset(){
		reloadTime=0;
		reloadTimeTotal=0;
		recoilTime=0;
		recoilTimeTotal=0;
		attackType=0;
		muzzleFlashTime=0;
		muzzleFlashTimeTotal=0;
		recoilChargeProgress=0;
	}

	public boolean isRecoiling(){
		return this.recoilTime>0;
	}
	public boolean isReloading(){
		return this.reloadTime>0;
	}
}