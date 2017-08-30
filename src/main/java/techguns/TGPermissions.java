package techguns;

import net.minecraftforge.server.permission.DefaultPermissionLevel;
import net.minecraftforge.server.permission.PermissionAPI;

public class TGPermissions {
	public static final String ALLOW_UNSAFE_MODE = Techguns.MODID+".allowunsafemode";
	
	
	public static void preInit() {
		PermissionAPI.registerNode(ALLOW_UNSAFE_MODE, DefaultPermissionLevel.ALL, "Allow player to use the unsafe mode of weapons.");
	}
}
