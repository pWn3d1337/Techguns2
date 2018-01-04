package techguns.client;

import net.minecraft.client.Minecraft;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import techguns.api.capabilities.AttackTime;
import techguns.api.npc.INPCTechgunsShooter;
import techguns.capabilities.TGExtendedPlayer;
import techguns.capabilities.TGShooterValues;

public class ShooterValues {
	
	protected static AttackTime getAttackTimes(EntityLivingBase ent, boolean offHand){
		if (ent instanceof EntityPlayer){
			TGExtendedPlayer caps = TGExtendedPlayer.get((EntityPlayer) ent);
			if(caps!=null){
				return caps.getAttackTime(offHand);
			}
		} else if (ent instanceof INPCTechgunsShooter){
			TGShooterValues caps = TGShooterValues.get(ent);
			if(caps!=null){
				return caps.getAttackTime(offHand);
			}
			
		}
		
		return null;
	}
	
	
	public static long getRecoiltime(EntityLivingBase ent, boolean offHand){
		AttackTime attack = getAttackTimes(ent,offHand);
		if (attack!=null){
			return attack.getRecoilTime();
		}
		return 0L;
	}
	
	public static boolean isStillRecoiling(EntityLivingBase ent, boolean offHand, byte attacktype){
		if (ent != ClientProxy.get().getPlayerClient()){
			return false;
		} else if (Minecraft.getMinecraft().gameSettings.thirdPersonView!=0){
			return false;
		}

		AttackTime attack = getAttackTimes(ent, offHand);
		
		if(attack==null){
			return false;
		} else {
			if (attack.getAttackType()!=attacktype){
				return false;
			} else if ( attack.getRecoilTime()<=0){
				return false;
			} else {
				boolean b = (System.currentTimeMillis()-attack.getRecoilTime()) < 0; 
				return b;
			}				
		}
		
	}
	
	public static void setRecoiltime(EntityLivingBase ent, boolean offHand, long time, int total,byte attacktype) {
		setRecoiltime(ent, offHand, time, total, attacktype, 0f);
	}
	
	public static void setRecoiltime(EntityLivingBase ent, boolean offHand, long time, int total,byte attacktype, float chargeProgress){
		AttackTime attack = getAttackTimes(ent,offHand);
			
		if (attack!=null){
			attack.setRecoilTime(time);
			attack.setRecoilTimeTotal(total);
			attack.setAttackType(attacktype);
			attack.setRecoilChargeProgress(chargeProgress);
		}
	}
	
	
	/**
	 * TOTAL
	 */
	public static long getRecoiltimeTotal(EntityLivingBase ent, boolean offHand){
		AttackTime attack = getAttackTimes(ent,offHand);
		if (attack!=null){
			return attack.getRecoilTimeTotal();
		}
		return 0L;
	}
	

	public static long getReloadtime(EntityLivingBase ent,boolean offHand){
		AttackTime attack = getAttackTimes(ent,offHand);
		if (attack!=null){
			return attack.getReloadTime();
		}
		return 0L;
	}
	
	public static byte getAttackType(EntityLivingBase ent,boolean offHand){
		AttackTime attack = getAttackTimes(ent,offHand);
		if (attack!=null){
			return attack.getAttackType();
		}
		return (byte)0;
	}
	
	public static void setReloadtime(EntityLivingBase ent, boolean offHand,long time, int total,byte attackType){
		AttackTime attack = getAttackTimes(ent,offHand);
		if (attack!=null){
			attack.setReloadTime(time);
			attack.setReloadTimeTotal(total);
			attack.setAttackType(attackType);
		}
	}

	public static int getReloadtimeTotal(EntityLivingBase ent, boolean offHand){
		AttackTime attack = getAttackTimes(ent,offHand);
		if (attack!=null){
			return attack.getReloadTimeTotal();
		}
		return 0;
	}
	
	public static  boolean getIsCurrentlyUsingGun(EntityLivingBase ent, boolean offHand) {
		AttackTime attack = getAttackTimes(ent,offHand);
		
		return attack.isReloading() || attack.isRecoiling();
	}
	
	
	public static  boolean getPlayerIsReloading(EntityLivingBase ent, boolean offHand) {
		AttackTime attack = getAttackTimes(ent,offHand);
		return attack.isReloading();
	}	
	/*public long getplayerReloadtimeTotal(){
		return getplayerReloadtimeTotal(Minecraft.getMinecraft().thePlayer);
	}*/

	public static void setMuzzleFlashTime(EntityLivingBase ent, boolean offHand,long time, int total){
		AttackTime attack = getAttackTimes(ent,offHand);
		if (attack!=null){
			attack.setMuzzleFlashTime(time);
			attack.setMuzzleFlashTimeTotal(total);
		}
	}

	public static long getMuzzleFlashTime(EntityLivingBase ent, boolean offHand){
		AttackTime attack = getAttackTimes(ent,offHand);
		if (attack!=null){
			return attack.getMuzzleFlashTime();
		}
		return 0L;
	}
	
	public static int getMuzzleFlashTimeTotal(EntityLivingBase ent, boolean offHand){
		AttackTime attack = getAttackTimes(ent,offHand);
		if (attack!=null){
			return attack.getMuzzleFlashTimeTotal();
		}
		return 0;
	}
	
}
