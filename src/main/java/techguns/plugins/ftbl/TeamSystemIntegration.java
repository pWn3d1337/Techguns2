package techguns.plugins.ftbl;

import java.util.UUID;

import com.feed_the_beast.ftbl.api.FTBLibAPI;
import com.feed_the_beast.ftbl.api.IForgePlayer;
import com.feed_the_beast.ftbl.lib.util.misc.EnumPrivacyLevel;

public class TeamSystemIntegration {

	public static boolean isAllied(UUID ply1, UUID ply2) {
		IForgePlayer p1 = FTBLibAPI.API.getUniverse().getPlayer(ply1);
		IForgePlayer p2 = FTBLibAPI.API.getUniverse().getPlayer(ply2);
		
		return p1.canInteract(p2, EnumPrivacyLevel.TEAM);
	}
	
	
}
