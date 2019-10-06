package techguns.plugins.ftbl;

import java.util.UUID;

import com.feed_the_beast.ftblib.lib.data.FTBLibAPI;
import com.feed_the_beast.ftblib.lib.data.ForgePlayer;
import com.feed_the_beast.ftblib.lib.data.ForgeTeam;
import com.feed_the_beast.ftblib.lib.data.Universe;

public class TeamSystemIntegration {

	public static boolean isAllied(UUID ply1, UUID ply2) {
		if(!Universe.loaded()) return false;
		
		Universe u = Universe.get();
		
		ForgePlayer p1 = u.getPlayer(ply1);
		ForgePlayer p2 = u.getPlayer(ply2);
		
		if(p1!=null) {

			if(p1.team!=null) {
				return p1.team.isMember(p2) || p1.team.isAlly(p2);
			} else {
				return ply1.equals(ply2);
			}
			
		} else {
			return ply1.equals(ply2);
		}
	}
	
	public static boolean isAlliedNoMember(UUID ply1, UUID ply2) {
		if(!Universe.loaded()) return false;
		
		Universe u = Universe.get();
		ForgePlayer p1 = u.getPlayer(ply1);
		ForgePlayer p2 = u.getPlayer(ply2);
		
		if(p1!=null) {
			if(p1.team!=null) {
				return p1.team.isAlly(p2) && !p1.team.isMember(p2);
			} else {
				return ply1.equals(ply2);
			}
		} else {
			return ply1.equals(ply2);
		}
	}
	
	public static boolean isTeamMember(UUID ply1, UUID ply2) {
		if(!Universe.loaded()) return false;
		
		Universe u = Universe.get();
		ForgePlayer p1 = u.getPlayer(ply1);
		ForgePlayer p2 = u.getPlayer(ply2);
		
		if(p1!=null) {
			if(p1.team!=null) {
				return p1.team.isMember(p2);
			} else {
				return ply1.equals(ply2);
			}
		} else {
			return ply1.equals(ply2);
		}
	}
	
	public static boolean isEnemy(UUID ply1, UUID ply2) {
		if(!Universe.loaded()) return false;
		
		Universe u = Universe.get();
		ForgePlayer p1 = u.getPlayer(ply1);
		ForgePlayer p2 = u.getPlayer(ply2);
		
		if(p1!=null) {
			if(p1.team!=null) {
				return p1.team.isEnemy(p2);
			} else {
				return false;
			}
		} else {
			return false;
		}
	}
}
