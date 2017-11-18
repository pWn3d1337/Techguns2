package techguns.plugins.crafttweaker;

import crafttweaker.CraftTweakerAPI;
import techguns.packets.ChemLabTweaker;

public class TGCraftTweakerIntegration {

	public static void init() {
		
		CraftTweakerAPI.registerClass(AmmoPressTweaker.class);
		CraftTweakerAPI.registerClass(ChargingStationTweaker.class);
		CraftTweakerAPI.registerClass(MetalPressTweaker.class);
		CraftTweakerAPI.registerClass(ChemLabTweaker.class);
		CraftTweakerAPI.registerClass(FabricatorTweaker.class);
		CraftTweakerAPI.registerClass(ReactionChamberTweaker.class);
		CraftTweakerAPI.registerClass(GunStatTweaker.class);
		CraftTweakerAPI.registerClass(ArmorStatTweaker.class);
	}

}
