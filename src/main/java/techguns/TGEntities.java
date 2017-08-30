package techguns;

import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.registry.EntityRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import techguns.client.render.entities.projectiles.RenderGrenade40mmProjectile;
import techguns.entities.npcs.NPCTurret;
import techguns.entities.npcs.ZombieFarmer;
import techguns.entities.npcs.ZombieSoldier;
import techguns.entities.projectiles.AdvancedBulletProjectile;
import techguns.entities.projectiles.BioGunProjectile;
import techguns.entities.projectiles.BlasterProjectile;
import techguns.entities.projectiles.FlamethrowerProjectile;
import techguns.entities.projectiles.GaussProjectile;
import techguns.entities.projectiles.GenericProjectile;
import techguns.entities.projectiles.Grenade40mmProjectile;
import techguns.entities.projectiles.GrenadeProjectile;
import techguns.entities.projectiles.GuidedMissileProjectile;
import techguns.entities.projectiles.LaserProjectile;
import techguns.entities.projectiles.NDRProjectile;
import techguns.entities.projectiles.RocketProjectile;
import techguns.entities.projectiles.StoneBulletProjectile;
import techguns.entities.projectiles.TeslaProjectile;
import techguns.init.ITGInitializer;

public class TGEntities implements ITGInitializer {

	@Override
	public void preInit(FMLPreInitializationEvent event) {
		
		/**
		 * PROJECTILES
		 */
		int bulletTrackRange = 128;
		
		EntityRegistry.registerModEntity(new ResourceLocation(Techguns.MODID,"GenericProjectile"),GenericProjectile.class, "GenericProjectile", ++Techguns.modEntityID, Techguns.MODID, bulletTrackRange, 1, true);
		EntityRegistry.registerModEntity(new ResourceLocation(Techguns.MODID,"RocketProjectile"),RocketProjectile.class, "RocketProjectile", ++Techguns.modEntityID, Techguns.MODID, bulletTrackRange, 1, true);
		EntityRegistry.registerModEntity(new ResourceLocation(Techguns.MODID,"StoneBulletProjectile"),StoneBulletProjectile.class, "StoneBulletProjectile", ++Techguns.modEntityID, Techguns.MODID, bulletTrackRange, 1, true);
		EntityRegistry.registerModEntity(new ResourceLocation(Techguns.MODID,"BioGunProjectile"),BioGunProjectile.class, "BioGunProjectile", ++Techguns.modEntityID, Techguns.MODID, bulletTrackRange, 1, true);
		EntityRegistry.registerModEntity(new ResourceLocation(Techguns.MODID,"FlamethrowerProjectile"),FlamethrowerProjectile.class, "FlamethrowerProjectile", ++Techguns.modEntityID, Techguns.MODID, bulletTrackRange, 1, true);
		EntityRegistry.registerModEntity(new ResourceLocation(Techguns.MODID,"GrenadeProjectile"),GrenadeProjectile.class, "GrenadeProjectile", ++Techguns.modEntityID, Techguns.MODID, bulletTrackRange, 1, true);
		EntityRegistry.registerModEntity(new ResourceLocation(Techguns.MODID,"Grenade40mmProjectile"),Grenade40mmProjectile.class, "Grenade40mmProjectile", ++Techguns.modEntityID, Techguns.MODID, bulletTrackRange, 1, true);
		EntityRegistry.registerModEntity(new ResourceLocation(Techguns.MODID,"LaserProjectile"),LaserProjectile.class, "LaserProjectile", ++Techguns.modEntityID, Techguns.MODID, bulletTrackRange, 1, true);
		EntityRegistry.registerModEntity(new ResourceLocation(Techguns.MODID,"TeslaProjectile"),TeslaProjectile.class, "TeslaProjectile", ++Techguns.modEntityID, Techguns.MODID, bulletTrackRange, 1, true);
		EntityRegistry.registerModEntity(new ResourceLocation(Techguns.MODID,"NDRProjectile"),NDRProjectile.class, "NDRProjectile", ++Techguns.modEntityID, Techguns.MODID, bulletTrackRange, 1, true);
		EntityRegistry.registerModEntity(new ResourceLocation(Techguns.MODID,"BlasterProjectile"),BlasterProjectile.class, "BlasterProjectile", ++Techguns.modEntityID, Techguns.MODID, bulletTrackRange, 1, true);
		EntityRegistry.registerModEntity(new ResourceLocation(Techguns.MODID,"GaussProjectile"),GaussProjectile.class, "GaussProjectile", ++Techguns.modEntityID, Techguns.MODID, bulletTrackRange, 1, true);
		EntityRegistry.registerModEntity(new ResourceLocation(Techguns.MODID,"AdvancedBulletProjectile"),AdvancedBulletProjectile.class, "AdvancedBulletProjectile", ++Techguns.modEntityID, Techguns.MODID, bulletTrackRange, 1, true);
		EntityRegistry.registerModEntity(new ResourceLocation(Techguns.MODID,"GuidedMissileProjectile"),GuidedMissileProjectile.class, "GuidedMissileProjectile", ++Techguns.modEntityID, Techguns.MODID, bulletTrackRange, 1, true);
		
		
		/**
		 * NPCS
		 */
		EntityRegistry.registerModEntity(new ResourceLocation(Techguns.MODID,"turret"),NPCTurret.class,  Techguns.MODID+".Turret", ++Techguns.modEntityID, Techguns.MODID, 80, 3, false);
		registerModEntityWithEgg(ZombieSoldier.class, "ZombieSoldier",0x757468, 0x38B038);
		registerModEntityWithEgg(ZombieFarmer.class, "ZombieFarmer",0x757468, 0x38B038);
	}

	static void registerModEntityWithEgg(Class parEntityClass, String parEntityName, 
		      int parEggColor, int parEggSpotsColor)
	{
	    EntityRegistry.registerModEntity(new ResourceLocation(Techguns.MODID,parEntityName),parEntityClass, Techguns.MODID+"."+parEntityName, ++Techguns.modEntityID, Techguns.instance, 80, 3, false, parEggColor, parEggSpotsColor);
	}
	
	@Override
	public void init(FMLInitializationEvent event) {
		
	}

	@Override
	public void postInit(FMLPostInitializationEvent event) {
		
	}

	@SideOnly(Side.CLIENT)
    public static void initModels() {
    }
}
