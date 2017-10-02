package techguns.factions;

import java.util.UUID;

import net.minecraft.entity.player.EntityPlayer;
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
	
	
	public static boolean isHostile(EntityPlayer ply1, EntityPlayer ply2) {
		return isHostile(EntityPlayer.getUUID(ply1.getGameProfile()), EntityPlayer.getUUID(ply2.getGameProfile()));
	}
	
	public static boolean isHostile(UUID ply1, UUID ply2){
		//return !friendsAPI.areFriends(ply1, ply2);
		//TODO Implement friend system
		return true;
	}
	
	public static boolean isHostile(TGNpcFaction faction1, TGNpcFaction faction2){

		return hostilityTable[faction1.ordinal()][faction2.ordinal()];
	}
}
