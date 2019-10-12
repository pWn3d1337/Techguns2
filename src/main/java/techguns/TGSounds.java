package techguns;

import java.util.ArrayList;

import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.registry.GameRegistry.ObjectHolder;
import net.minecraftforge.registries.IForgeRegistry;

@ObjectHolder(Techguns.MODID)
public class TGSounds {

	private static ArrayList<SoundEvent> events = new ArrayList<>();
	
	public static SoundEvent M4_FIRE = createSoundEvent("guns.assaultriflefire");
	public static SoundEvent M4_RELOAD = createSoundEvent("guns.assaultriflereload");
	public static SoundEvent M4_SILENCED_FIRE = createSoundEvent("guns.silencedm4Fire");
	
	public static SoundEvent THOMPSON_FIRE = createSoundEvent("guns.thompsonfire");
	public static SoundEvent THOMSPON_RELOAD = createSoundEvent("guns.thompsonreload");
	
	public static SoundEvent PISTOL_FIRE = createSoundEvent("guns.pistolfire");
	public static SoundEvent PISTOL_RELOAD = createSoundEvent("guns.pistolreload");
	
	public static SoundEvent ROCKET_FIRE = createSoundEvent("guns.rocketfire");
	public static SoundEvent ROCKET_RELOAD = createSoundEvent("guns.rocketreload");
	
	public static SoundEvent LMG_FIRE = createSoundEvent("guns.lmgfire");
	public static SoundEvent LMG_RELOAD = createSoundEvent("guns.lmgreload");
	
	public static SoundEvent BOLT_ACTION_FIRE = createSoundEvent("guns.boltactionfire");
	public static SoundEvent BOLT_ACTION_RELOAD = createSoundEvent("guns.boltactionreload");
	public static SoundEvent BOLT_ACTION_RECHAMBER = createSoundEvent("guns.boltactionrechamber");
	
	public static SoundEvent HANDGUN_FIRE = createSoundEvent("guns.handgunfire");
	public static SoundEvent HANDGUN_RELOAD = createSoundEvent("guns.handgunreload");
	
	public static SoundEvent BIOGUN_FIRE = createSoundEvent("guns.biogunfire");
	public static SoundEvent BIOGUN_RELOAD = createSoundEvent("guns.biogunreload");
	public static SoundEvent BIOGUN_CHARGE = createSoundEvent("guns.bioguncharge");
	public static SoundEvent BIOGUN_IMPACT = createSoundEvent("effects.biogunimpact");
	
	public static SoundEvent SAWEDOFF_FIRE = createSoundEvent("guns.sawedofffire");
	public static SoundEvent SAWEDOFF_RELOAD = createSoundEvent("guns.sawedoffreload");
	
	public static SoundEvent FLAMETHROWER_FIRE = createSoundEvent("guns.flamethrowerfire");
	public static SoundEvent FLAMETHROWER_RELOAD = createSoundEvent("guns.flamethrowerreload");
	public static SoundEvent FLAMETHROWER_START = createSoundEvent("guns.flamethrowerstart");
	
	public static SoundEvent AK_FIRE = createSoundEvent("guns.ak47fire");
	public static SoundEvent AK_RELOAD = createSoundEvent("guns.ak47reload");
	
	public static SoundEvent MINIGUN_FIRE = createSoundEvent("guns.minigunfire");
	public static SoundEvent MINIGUN_RELOAD = createSoundEvent("guns.minigunreload");
	
	public static SoundEvent COMBATSHOTGUN_FIRE = createSoundEvent("guns.combatshotgunfire");
	public static SoundEvent COMBATSHOTGUN_RELOAD = createSoundEvent("guns.combatshotgunreload");
	public static SoundEvent COMBATSHOTGUN_RECHAMBER = createSoundEvent("guns.combatshotgunpump");
	
	public static SoundEvent REVOLVER_FIRE = createSoundEvent("guns.revolverfire");
	public static SoundEvent REVOLVER_RELOAD = createSoundEvent("guns.revolverreload");
	public static SoundEvent REVOLVER_GOLDEN_FIRE = createSoundEvent("guns.goldenrevolverfire");
	
	public static SoundEvent AS50_FIRE = createSoundEvent("guns.as50fire");
	public static SoundEvent AS50_RELOAD = createSoundEvent("guns.as50reload");
	
	public static SoundEvent TESLA_FIRE = createSoundEvent("guns.teslafire");
	public static SoundEvent TESLA_RELOAD = createSoundEvent("guns.teslareload");
	
	public static SoundEvent PULSE_RIFLE_FIRE = createSoundEvent("guns.pulseriflefire");
	public static SoundEvent PULSE_RIFEL_RELOAD = createSoundEvent("guns.pulseriflereload");
	
	public static SoundEvent PDW_FIRE = createSoundEvent("guns.pdwfire");
	public static SoundEvent PDW_RELOAD = createSoundEvent("guns.pdwreload");
	
	public static SoundEvent LASERGUN_FIRE = createSoundEvent("guns.lasergunfire");
	public static SoundEvent LASERGUN_RELOAD = createSoundEvent("guns.lasergunreload");
	
	public static SoundEvent ALIENBLASTER_FIRE = createSoundEvent("guns.alienblasterfire");
	public static SoundEvent ALIENBLASTER_RELOAD = createSoundEvent("guns.alienblasterreload");
	
	public static SoundEvent NETHERBLASTER_FIRE = createSoundEvent("guns.cyberdemonblasterfire");
	public static SoundEvent NETHERBLASTER_RELOAD = createSoundEvent("guns.cyberdemonblasterreload");
	
	public static SoundEvent POWERHAMMER_FIRE = createSoundEvent("guns.powerhammerfire");
	public static SoundEvent POWERHAMMER_RELOAD = createSoundEvent("guns.powerhammerreload");
	public static SoundEvent POWERHAMMER_SWING = createSoundEvent("guns.powerhammerswing");
	public static SoundEvent POWERHAMMER_IMPACT = createSoundEvent("guns.powerhammerimpactground");
	public static SoundEvent POWERHAMMER_CHARGE = createSoundEvent("guns.powerhammercharge");
	
	public static SoundEvent BLASTER_RIFLE_FIRE = createSoundEvent("guns.blasterriflefire");
	
	public static SoundEvent GRENADE_LAUNCHER_FIRE = createSoundEvent("guns.grenadelauncherfire");
	public static SoundEvent GRENADE_LAUNCHER_RELOAD = createSoundEvent("guns.grenadelauncherreload");
	
	public static SoundEvent AUG_FIRE = createSoundEvent("guns.augfire");
	public static SoundEvent AUG_RELOAD = createSoundEvent("guns.augreload");
	
	public static SoundEvent SONIC_SHOTGUN_FIRE = createSoundEvent("guns.sonicshotgunfire");
	public static SoundEvent SONIC_SHOTGUN_RELOAD = createSoundEvent("guns.sonicshotgunreload");
	
	public static SoundEvent CHAINSAW_LOOP = createSoundEvent("guns.chainsawloop");
	public static SoundEvent CHAINSAW_LOOP_START= createSoundEvent("guns.chainsawloopstart");
	public static SoundEvent CHAINSAW_SWING = createSoundEvent("guns.chainsawswing");
	public static SoundEvent CHAINSAW_HIT = createSoundEvent("guns.chainsawhit");
	
	public static SoundEvent BEAMGUN_START = createSoundEvent("guns.beamgunstart");
	public static SoundEvent BEAMGUN_FIRE = createSoundEvent("guns.beamgunfire");
	
	public static SoundEvent MAC10_FIRE = createSoundEvent("guns.mac10fire");
	public static SoundEvent MAC10_RELOAD = createSoundEvent("guns.mac10reload");
	
	public static SoundEvent MIBGUN_FIRE = createSoundEvent("guns.mibblasterfire");
	public static SoundEvent MIBGUN_RELOAD = createSoundEvent("guns.mibblasterreload");
	
	public static SoundEvent SCAR_FIRE = createSoundEvent("guns.scarfire");
	public static SoundEvent SCAR_RELOAD = createSoundEvent("guns.scarreload");
	
	public static SoundEvent VECTOR_FIRE = createSoundEvent("guns.vectorfire");
	public static SoundEvent VECTOR_RELOAD = createSoundEvent("guns.vectorreload");
	
	public static SoundEvent GAUSS_RIFLE_FIRE = createSoundEvent("guns.gaussriflefire");
	public static SoundEvent GAUSS_RIFLE_RELOAD = createSoundEvent("guns.gaussriflereload");
	public static SoundEvent GAUSS_RIFLE_RECHAMBER = createSoundEvent("guns.gaussriflerechamber");
	
	public static SoundEvent DRILLER_LOOP = createSoundEvent("guns.drillerloop");
	public static SoundEvent DRILLER_HIT = createSoundEvent("guns.drillerhit");
	public static SoundEvent DRILLER_SWING = createSoundEvent("guns.drillerswing");
	public static SoundEvent DRILLER_BREAK = createSoundEvent("guns.drillerbreak");	
	
	public static SoundEvent LASER_PISTOL_RELOAD = createSoundEvent("guns.laserpistolreload");
	public static SoundEvent LASER_PISTOL_FIRE = createSoundEvent("guns.laserpistolfire");
	
	public static SoundEvent SHISHKEBAP_SWING = createSoundEvent("guns.shishkebabfire");
	public static SoundEvent SHISHKEBAP_RELOAD = createSoundEvent("guns.shishkebabreload");
	
	public static SoundEvent ANTI_GRAV_START = createSoundEvent("items.antiGravStart");
	public static SoundEvent NIGHTVISION_ON = createSoundEvent("nightvision.on");
	public static SoundEvent NIGHTVISION_OFF = createSoundEvent("nightvision.off");
	public static SoundEvent STEAM_JUMP = createSoundEvent("effects.steamarmorjump");
	public static SoundEvent STEAM_JUMP_2 = createSoundEvent("effects.steamarmorjump2");
	
	public static SoundEvent DEATH_GORE = createSoundEvent("effects.gore");
	public static SoundEvent DEATH_BIO = createSoundEvent("effects.biodeath");
	public static SoundEvent DEATH_LASER = createSoundEvent("effects.disintegrate");
	
	public static SoundEvent JETPACK_START = createSoundEvent("jetpack.start");
	public static SoundEvent JETPACK_END = createSoundEvent("jetpack.end");
	public static SoundEvent JETPACK_LOOP = createSoundEvent("jetpack.loop");
	
	public static SoundEvent AMMO_PRESS_WORK1 = createSoundEvent("machines.ammopresswork1");
	public static SoundEvent AMMO_PRESS_WORK2 = createSoundEvent("machines.ammopresswork2");
	
	public static SoundEvent METAL_PRESS_WORK = createSoundEvent("machines.metalpresswork");
	
	public static SoundEvent CHEM_LAB_WORK = createSoundEvent("machines.chemlabwork");
	
	public static SoundEvent LOCKON_BEEP = createSoundEvent("guns.lockon");
	public static SoundEvent LOCKED_BEEP = createSoundEvent("guns.locked");
	
	public static SoundEvent GUIDEDMISSILE_FIRE = createSoundEvent("guns.guidedmissilelauncherfire");
	
	public static SoundEvent TFG_FIRE = createSoundEvent("guns.tfgfire");
	public static SoundEvent TFG_CHARGE = createSoundEvent("guns.tfgcharge");
	
	public static SoundEvent FABRICATOR_WORK =createSoundEvent("machines.fabricatorwork");
	
	public static SoundEvent CHARGING_STATION_WORK = createSoundEvent("machines.chargingstationwork");
	
	public static SoundEvent REACTION_CHAMBER_HEATRAY_WORK = createSoundEvent("machines.rc_heatrayWork");
	public static SoundEvent REACTION_CHAMBER_BEEP = createSoundEvent("machines.rc_beep");
	public static SoundEvent REACTION_CHAMBER_WARNING = createSoundEvent("machines.rc_warning");
	
	public static SoundEvent BULLET_IMPACT_BRICKS = createSoundEvent("guns.impact.bricks");
	public static SoundEvent BULLET_IMPACT_STONE = createSoundEvent("guns.impact.rock");
	public static SoundEvent BULLET_IMPACT_WOOD = createSoundEvent("guns.impact.wood");
	public static SoundEvent BULLET_IMPACT_GLASS = createSoundEvent("guns.impact.glass");
	public static SoundEvent BULLET_IMPACT_METAL = createSoundEvent("guns.impact.metal");
	public static SoundEvent BULLET_IMPACT_DIRT = createSoundEvent("guns.impact.dirt");
	public static SoundEvent BULLET_IMPACT_WATER = createSoundEvent("guns.impact.water");
	
	public static SoundEvent CYBERDEMON_IDLE = createSoundEvent("npcs.cyberdemonidle");
	public static SoundEvent CYBERDEMON_HURT = createSoundEvent("npcs.cyberdemonhurt");
	public static SoundEvent CYBERDEMON_DEATH = createSoundEvent("npcs.cyberdemondeath");
	public static SoundEvent CYBERDEMON_STEP = createSoundEvent("npcs.cyberdemonstep");
	public static SoundEvent CYBERDEMON_AGGRO = createSoundEvent("npcs.cyberdemonaggro");
	
	public static SoundEvent ALIENBUG_IDLE = createSoundEvent("npcs.alienbugidle");
	public static SoundEvent ALIENBUG_HURT = createSoundEvent("npcs.alienbughurt");
	public static SoundEvent ALIENBUG_DEATH = createSoundEvent("npcs.alienbugdeath");
	public static SoundEvent ALIENBUG_STEP = createSoundEvent("npcs.alienbugstep");
	public static SoundEvent ALIENBUG_AGGRO = createSoundEvent("npcs.alienbugaggro");
	public static SoundEvent ALIENBUG_BITE = createSoundEvent("npcs.alienbugbite");
	
	public static SoundEvent GRENADE_PIN = createSoundEvent("guns.grenade_pin");
	
	public static SoundEvent HELICOPTER_HIT = createSoundEvent("npcs.apachehit");
	public static SoundEvent HELICOPTER_ROTOR = createSoundEvent("npcs.apacherotor");
	public static SoundEvent HELICOPTER_BURST = createSoundEvent("npcs.apacheburst");
	public static SoundEvent HELICOPTER_DEATH = createSoundEvent("npcs.apachedie");
	public static SoundEvent HELICOPTER_EXPLODE = createSoundEvent("npcs.apacheexplode");
	
	public static SoundEvent BUNKER_DOOR_OPEN = createSoundEvent("blocks.metaldooropen");
	
	public static SoundEvent NUKE_EXPLOSION = createSoundEvent("effects.nukeexplosion");
	
	public static SoundEvent TFG_EXPLOSION = createSoundEvent("effects.tfgexplosion");
	public static SoundEvent TFG_EXPLOSION_ECHO = createSoundEvent("effects.tfgexplosionecho");
	
	
	public static SoundEvent TNT_INIT = createSoundEvent("tntcharge.init");
	public static SoundEvent TNT_TICK = createSoundEvent("tntcharge.tick");
	public static SoundEvent TNT_PLANT = createSoundEvent("tntcharge.plant");
	
	public static SoundEvent C4_INIT = createSoundEvent("c4charge.init");
	public static SoundEvent C4_TICK = createSoundEvent("c4charge.tick");
	public static SoundEvent C4_PLANT = createSoundEvent("c4charge.plant");
	
	public static SoundEvent GLIDER_LOOP = createSoundEvent("glider.loop");
	
	
	public static SoundEvent GEIGER_LOW = createSoundEvent("effects.geiger.low");
	public static SoundEvent GEIGER_HIGH = createSoundEvent("effects.geiger.high");
	
	public static SoundEvent ORE_DRILL_WORK_SMALL = createSoundEvent("machines.oredrillsmallwork");
	public static SoundEvent ORE_DRILL_WORK_MEDIUM = createSoundEvent("machines.oredrillmediumwork");
	public static SoundEvent ORE_DRILL_WORK_LARGE = createSoundEvent("machines.oredrilllargework");
	
	public static SoundEvent GRINDER_START = createSoundEvent("machines.grinder.start");
	public static SoundEvent GRINDER_WORK = createSoundEvent("machines.grinder.work");
	
	public static SoundEvent TECHDOOR_OPEN = createSoundEvent("blocks.techdoor.open");
	public static SoundEvent TECHDOOR_CLOSE = createSoundEvent("blocks.techdoor.close");
	public static SoundEvent TECHDOOR_STATE_FINISHED = createSoundEvent("blocks.techdoor.statefinished");
	
	public static void registerSounds(RegistryEvent.Register<SoundEvent> event){
		IForgeRegistry<SoundEvent> reg = event.getRegistry();
		
		events.forEach(ev -> reg.register(ev));
		
		//can be deleted afterwards
		events = null;
	}
	
	private static SoundEvent createSoundEvent(String name){
		SoundEvent event = new SoundEvent(new ResourceLocation(Techguns.MODID, name));
		event.setRegistryName(Techguns.MODID, name);
		events.add(event);
		return event;
	}

}
