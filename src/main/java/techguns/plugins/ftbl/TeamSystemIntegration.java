package techguns.plugins.ftbl;

import java.util.UUID;

import com.feed_the_beast.ftbl.api.FTBLibAPI;
import com.feed_the_beast.ftbl.api.IForgePlayer;
import com.feed_the_beast.ftbl.api.IForgeTeam;
import com.feed_the_beast.ftbl.lib.util.misc.EnumPrivacyLevel;

public class TeamSystemIntegration {

	public static boolean isAllied(UUID ply1, UUID ply2) {
		IForgePlayer p1 = FTBLibAPI.API.getUniverse().getPlayer(ply1);
		IForgePlayer p2 = FTBLibAPI.API.getUniverse().getPlayer(ply2);
		
		if(p1!=null) {
			IForgeTeam t1 = p1.getTeam();
			if(t1!=null) {
				return t1.isMember(p2) || t1.isAlly(p2);
			} else {
				return ply1.equals(ply2);
			}
			
		} else {
			return ply1.equals(ply2);
		}
	}
	
	public static boolean isAlliedNoMember(UUID ply1, UUID ply2) {
		IForgePlayer p1 = FTBLibAPI.API.getUniverse().getPlayer(ply1);
		IForgePlayer p2 = FTBLibAPI.API.getUniverse().getPlayer(ply2);
		
		if(p1!=null) {
			IForgeTeam t1 = p1.getTeam();
			if(t1!=null) {
				return t1.isAlly(p2) && !t1.isMember(p2);
			} else {
				return ply1.equals(ply2);
			}
		} else {
			return ply1.equals(ply2);
		}
	}
	
	public static boolean isTeamMember(UUID ply1, UUID ply2) {
		IForgePlayer p1 = FTBLibAPI.API.getUniverse().getPlayer(ply1);
		IForgePlayer p2 = FTBLibAPI.API.getUniverse().getPlayer(ply2);
		
		if(p1!=null) {
			IForgeTeam t1 = p1.getTeam();
			if(t1!=null) {
				return t1.isMember(p2);
			} else {
				return ply1.equals(ply2);
			}
		} else {
			return ply1.equals(ply2);
		}
	}
	
	public static boolean isEnemy(UUID ply1, UUID ply2) {
		IForgePlayer p1 = FTBLibAPI.API.getUniverse().getPlayer(ply1);
		IForgePlayer p2 = FTBLibAPI.API.getUniverse().getPlayer(ply2);
		
		if(p1!=null) {
			IForgeTeam t1 = p1.getTeam();
			if(t1!=null) {
				return t1.isEnemy(p2);
			} else {
				return false;
			}
		} else {
			return false;
		}
	}
}
