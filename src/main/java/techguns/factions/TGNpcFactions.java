package techguns.factions;

import java.util.UUID;

import net.minecraft.entity.player.EntityPlayer;
import techguns.Techguns;
import techguns.api.npc.factions.TGNpcFaction;

public class TGNpcFactions {
	//public static IFTBUtilitiesIntegration friendsAPI;
	
	protected static boolean[][] hostilityTable = new boolean[TGNpcFaction.values().length][TGNpcFaction.values().length];
		
	static {
		
		//init hostility table
		setHostile(TGNpcFaction.HOSTILE, false);
		setHostile(TGNpcFaction.TURRET, false);
		setHostile(TGNpcFaction.NEUTRAL, false);
		
		
		setHostile(TGNpcFaction.HOSTILE, TGNpcFaction.TURRET,true);
		setHostile(TGNpcFaction.HOSTILE, TGNpcFaction.NEUTRAL,true);
		setHostile(TGNpcFaction.TURRET, TGNpcFaction.NEUTRAL,false);
	
	}
	
	private static void setHostile(TGNpcFaction f1, TGNpcFaction f2, boolean hostile){
		hostilityTable[f1.ordinal()][f2.ordinal()]=hostile;
		hostilityTable[f2.ordinal()][f1.ordinal()]=hostile;
	}
	
	private static void setHostile(TGNpcFaction f1, boolean hostile){
		hostilityTable[f1.ordinal()][f1.ordinal()]=hostile;
	}
	
	
	public static boolean canAccess(UUID owner, UUID target, int security) {
		if(owner.equals(target)) return true;
		
		switch(security) {
		case 1:
			return isAlliedOrTeamMember(owner, target);
		case 2:
			return isTeamMember(owner, target);
		case 3:
			return owner.equals(target);
		case 0:
		default:
			return false;
				
		}

	}
	
	public static boolean shouldAttack(UUID owner, UUID target, int pvpsetting) {
		if(owner.equals(target)) return false;
		
		switch(pvpsetting) {
		case 1:
			return isEnemy(owner, target);
		case 2:
			return !isAlliedOrTeamMember(owner, target);
		case 3:
			return !isTeamMember(owner, target);
		case 4:
			return !owner.equals(target);
		case 0:
		default:
			return false;
				
		}

	}
	
	
	public static boolean isEnemy(UUID owner, UUID target) {
		if(Techguns.instance.FTBLIB_ENABLED && !owner.equals(target)) {
			return techguns.plugins.ftbl.TeamSystemIntegration.isEnemy(owner, target);
		}
		return false;
	}
	
	public static boolean isAlliedOrTeamMember(UUID owner, UUID target) {
		if(Techguns.instance.FTBLIB_ENABLED && !owner.equals(target)) {
			return techguns.plugins.ftbl.TeamSystemIntegration.isAllied(owner, target);
		}
		return true;
	}
	
	public static boolean isAlliedNoTeamMember(UUID owner, UUID target) {
		if(Techguns.instance.FTBLIB_ENABLED && !owner.equals(target)) {
			return techguns.plugins.ftbl.TeamSystemIntegration.isAlliedNoMember(owner, target);
		}
		return true;
	}
	
	public static boolean isTeamMember(UUID owner, UUID target) {
		if(Techguns.instance.FTBLIB_ENABLED && !owner.equals(target)) {
			return techguns.plugins.ftbl.TeamSystemIntegration.isTeamMember(owner, target);
		}
		return true;		
	}
	
	public static boolean isHostile(EntityPlayer ply1, EntityPlayer ply2) {
		return isHostile(EntityPlayer.getUUID(ply1.getGameProfile()), EntityPlayer.getUUID(ply2.getGameProfile()));
	}
	
	public static boolean isHostile(UUID ply1, UUID ply2){
		//return !friendsAPI.areFriends(ply1, ply2);
		if(Techguns.instance.FTBLIB_ENABLED) {
			return !techguns.plugins.ftbl.TeamSystemIntegration.isAllied(ply1, ply2);
		}
		
		return !ply1.equals(ply2);
	}
	
	public static boolean isHostile(TGNpcFaction faction1, TGNpcFaction faction2){

		return hostilityTable[faction1.ordinal()][faction2.ordinal()];
	}
}
