package techguns;

import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.IAttribute;
import net.minecraft.entity.ai.attributes.RangedAttribute;
import net.minecraft.potion.Potion;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.oredict.OreDictionary;
import techguns.api.radiation.TGRadiation;
import techguns.init.ITGInitializer;
import techguns.radiation.ItemRadiationRegistry;
import techguns.radiation.RadRegenerationPotion;
import techguns.radiation.RadResistancePotion;
import techguns.radiation.RadiationPotion;

public class TGRadiationSystem implements ITGInitializer {

	public static RadiationPotion radiation_effect;
	public static RadResistancePotion radresistance_effect;
	public static RadRegenerationPotion radregen_effect;
	
	public static int MINOR_POISONING=500;
	public static int SEVERE_POISONING=750;
	public static int LETHAL_POISONING=1000;
	
	public static int RADLOST_ON_DEATH=200;
	
	public static final int INVENTORY_RADIATION_INVERVALL=60;
	
	@Override
	public void preInit(FMLPreInitializationEvent event) {
		TGRadiation.RADIATION_RESISTANCE = (new RangedAttribute((IAttribute)null, Techguns.MODID+".radiationResistance", 0.0D, 0.0D, Float.MAX_VALUE)).setShouldWatch(true);

		radiation_effect= new RadiationPotion();
		radiation_effect.setPotionName(Techguns.MODID+".radiation").setRegistryName(new ResourceLocation(Techguns.MODID,"radiation"));
		
		radregen_effect= new RadRegenerationPotion();
		radregen_effect.setPotionName(Techguns.MODID+".radregeneration").setRegistryName(new ResourceLocation(Techguns.MODID,"radregeneration"));
		
		radresistance_effect= new RadResistancePotion();
		radresistance_effect.setPotionName(Techguns.MODID+".radresistance").setRegistryName(new ResourceLocation(Techguns.MODID,"radresistance")).setBeneficial()
			.registerPotionAttributeModifier(TGRadiation.RADIATION_RESISTANCE, "515AD21C-3FB2-4E36-BBCE-88C2ED738DE2", 1D, 0);

	}

	@Override
	public void init(FMLInitializationEvent event) {
	}

	@Override
	public void postInit(FMLPostInitializationEvent event) {
		
		if(!TGConfig.WIP_disableRadiationSystem) {
			ItemRadiationRegistry.addRadiationData(TGItems.ENRICHED_URANIUM, 3, 100);
			ItemRadiationRegistry.addRadiationData(TGItems.YELLOWCAKE, 1, 100);
			
			ItemRadiationRegistry.addRadiationData(TGItems.ANTI_GRAV_CORE, 4, 100);
			ItemRadiationRegistry.addRadiationData(TGItems.PLASMA_GENERATOR, 4, 100);
			
			ItemRadiationRegistry.addRadiationData(TGItems.TACTICAL_NUKE_WARHEAD, 1, 100);
			ItemRadiationRegistry.addRadiationData(TGItems.ROCKET_NUKE, 1, 100);
			
			OreDictionary.getOres("ore_uranium").forEach(o -> {
				ItemRadiationRegistry.addRadiationData(o, 1, 100);
			});
			
			OreDictionary.getOres("ingot_uranium").forEach(o -> {
				ItemRadiationRegistry.addRadiationData(o, 1, 100);
			});
		}
	}

	public void registerPotions(RegistryEvent.Register<Potion> event)
	{

       event.getRegistry().register(radiation_effect);
       event.getRegistry().register(radresistance_effect);
       event.getRegistry().register(radregen_effect);

	}
}
