package techguns;

import com.google.common.base.Predicate;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.IAttribute;
import net.minecraft.entity.ai.attributes.RangedAttribute;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.potion.PotionType;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.oredict.OreDictionary;
import techguns.api.radiation.TGRadiation;
import techguns.entities.special.EntityRadiation;
import techguns.init.ITGInitializer;
import techguns.radiation.ItemRadiationRegistry;
import techguns.radiation.RadRegenerationPotion;
import techguns.radiation.RadResistancePotion;
import techguns.radiation.RadiationPotion;

public class TGRadiationSystem implements ITGInitializer {

	public static final Predicate<Entity> RADIATION_TARGETS = new Predicate<Entity>() {
		@Override
		public boolean apply(Entity input) {
			return input instanceof EntityLivingBase;
		}
	};
	
	public static RadiationPotion radiation_effect;
	public static RadResistancePotion radresistance_effect;
	public static RadRegenerationPotion radregen_effect;
	
	public static int MINOR_POISONING=500;
	public static int SEVERE_POISONING=750;
	public static int LETHAL_POISONING=1000;
	
	public static int RADLOST_ON_DEATH=200;
	
	public static final int INVENTORY_RADIATION_INVERVALL=60;
	
	public static PotionType RAD_POTION;
	public static PotionType RAD_RESISTANCE_POTION;
	public static PotionType RAD_REGENERATION_POTION;
	public static PotionType RAD_POTION_SEVERE;
	
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

	public static boolean isEnabled() {
		return !TGConfig.WIP_disableRadiationSystem;
	}
	
	@Override
	public void init(FMLInitializationEvent event) {
	}

	@Override
	public void postInit(FMLPostInitializationEvent event) {
		
		if(TGRadiationSystem.isEnabled()) {
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
	
	public static void applyRadToEntities(TileEntity tile, double radius, int duration, int strength, double inner_radius, int strength_outer) {
		applyRadToEntities(tile.getWorld(), tile.getPos().getX()+0.5, tile.getPos().getY()+0.5, tile.getPos().getZ()+0.5,  radius, duration, strength, inner_radius, strength_outer);
	}	
	
	public static void applyRadToEntities(World world, double posX, double posY, double posZ, double radius, int duration, int strength, double inner_radius, int strength_outer) {
		Vec3d offset= new Vec3d(posX+radius, posY+radius, posZ+radius);
		Vec3d offset2= new Vec3d(posX-radius, posY-radius, posZ-radius);
		AxisAlignedBB bb = new AxisAlignedBB(offset, offset2);
		
		Vec3d pos = new Vec3d(posX, posY, posZ);
		world.getEntitiesWithinAABB(EntityLivingBase.class ,bb, RADIATION_TARGETS).forEach(e -> {
			
			double distance = pos.distanceTo(new Vec3d(e.posX, e.posY, e.posZ));
			if (distance < radius) {
			
				int str = strength;
				if(distance > inner_radius) {
					double factor = (distance-inner_radius)/(radius-inner_radius);
					str = (int) Math.round(strength_outer + (strength-strength_outer)*factor);
				}
				
				EntityLivingBase elb = (EntityLivingBase) e; //RADIATION_TARGETS only applies to EntityLivingBase
				elb.addPotionEffect(new PotionEffect(TGRadiationSystem.radiation_effect, duration, 
						str, true, true));
			
			}
			
		});
	}
}
