package techguns.items.guns.ammo;

import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import techguns.TGItems;
import techguns.init.ITGInitializer;

public class AmmoTypes implements ITGInitializer{

	public static AmmoType STONE_BULLETS;
	public static AmmoType PISTOL_ROUNDS;
	public static AmmoType RIFLE_ROUNDS;
	public static AmmoType SHOTGUN_ROUNDS;
	public static AmmoType ASSAULT_RIFLE_MAGAZINE;
	public static AmmoType SMG_MAGAZINE;
	public static AmmoType PISTOL_MAGAZINE;
	public static AmmoType LMG_MAGAZINE;
	public static AmmoType BIO_TANK;
	public static AmmoType FUEL_TANK;
	public static AmmoType ROCKETS;
	public static AmmoType ROCKETS_NO_NUKES;
	public static AmmoType MINIGUN_AMMO_DRUM;
	public static AmmoType ADVANCED_MAGAZINE;
	public static AmmoType AS50_MAGAZINE;
	public static AmmoType ENERGY_CELL;
	public static AmmoType NETHER_CHARGE;
	public static AmmoType COMPRESSED_AIR_TANK;
	public static AmmoType GRENADES_40MM;
	public static AmmoType NUCLEAR_POWER_CELL;
	public static AmmoType REDSTONE_BATTERY;
	
	public static AmmoType AMMO_GAUSS_RIFLE;
	
	public static final String TYPE_INCENDIARY = "incendiary";
	public static final String TYPE_DEFAULT = "default";
	public static final String TYPE_NUKE = "nuke";
	public static final String TYPE_EXPLOSIVE = "explosive";
	public static final String TYPE_HV = "high_velocity";
	
	@Override
	public void preInit(FMLPreInitializationEvent event) {
		STONE_BULLETS = new AmmoType(TGItems.STONE_BULLETS);
		PISTOL_ROUNDS = new AmmoType(TGItems.PISTOL_ROUNDS);
		PISTOL_ROUNDS.addVariant(TYPE_INCENDIARY, TGItems.PISTOL_ROUNDS_INCENDIARY);
		
		RIFLE_ROUNDS = new AmmoType(TGItems.RIFLE_ROUNDS);
		RIFLE_ROUNDS.addVariant(TYPE_INCENDIARY, TGItems.RIFLE_ROUNDS_INCENDIARY);
		
		SHOTGUN_ROUNDS = new AmmoType(TGItems.SHOTGUN_ROUNDS);
		SHOTGUN_ROUNDS.addVariant(TYPE_INCENDIARY,TGItems.SHOTGUN_ROUNDS_INCENDIARY);
		
		SMG_MAGAZINE = new AmmoType(TGItems.SMG_MAGAZINE, TGItems.SMG_MAGAZINE_EMPTY, TGItems.PISTOL_ROUNDS,2);
		SMG_MAGAZINE.addVariant(TYPE_INCENDIARY, TGItems.SMG_MAGAZINE_INCENDIARY, TGItems.PISTOL_ROUNDS_INCENDIARY);
		
		ASSAULT_RIFLE_MAGAZINE = new AmmoType(TGItems.ASSAULTRIFLE_MAGAZINE, TGItems.ASSAULTRIFLE_MAGAZINE_EMPTY, TGItems.RIFLE_ROUNDS, 3);
		ASSAULT_RIFLE_MAGAZINE.addVariant(TYPE_INCENDIARY, TGItems.ASSAULTRIFLE_MAGAZINE_INCENDIARY, TGItems.RIFLE_ROUNDS_INCENDIARY);
		
		PISTOL_MAGAZINE = new AmmoType(TGItems.PISTOL_MAGAZINE, TGItems.PISTOL_MAGAZINE_EMPTY, TGItems.PISTOL_ROUNDS,3);
		PISTOL_MAGAZINE.addVariant(TYPE_INCENDIARY, TGItems.PISTOL_MAGAZINE_INCENDIARY, TGItems.PISTOL_ROUNDS_INCENDIARY);
		
		LMG_MAGAZINE = new AmmoType(TGItems.LMG_MAGAZINE, TGItems.LMG_MAGAZINE_EMPTY, TGItems.RIFLE_ROUNDS,8);
		LMG_MAGAZINE.addVariant(TYPE_INCENDIARY, TGItems.LMG_MAGAZINE_INCENDIARY, TGItems.RIFLE_ROUNDS_INCENDIARY);
		
		BIO_TANK = new AmmoType(TGItems.BIO_TANK, TGItems.BIO_TANK_EMPTY, ItemStack.EMPTY, 0);
		FUEL_TANK = new AmmoType(TGItems.FUEL_TANK, TGItems.FUEL_TANK_EMPTY, ItemStack.EMPTY, 0);
		COMPRESSED_AIR_TANK = new AmmoType(TGItems.COMPRESSED_AIR_TANK, TGItems.COMPRESSED_AIR_TANK_EMPTY, ItemStack.EMPTY, 0);
		ENERGY_CELL = new AmmoType(TGItems.ENERGY_CELL, TGItems.ENERGY_CELL_EMPTY, ItemStack.EMPTY, 0);
		REDSTONE_BATTERY = new AmmoType(TGItems.REDSTONE_BATTERY, TGItems.REDSTONE_BATTERY_EMPTY, ItemStack.EMPTY, 0);
		MINIGUN_AMMO_DRUM = new AmmoType(TGItems.MINIGUN_DRUM, TGItems.MINIGUN_DRUM_EMPTY, TGItems.RIFLE_ROUNDS,16);
		MINIGUN_AMMO_DRUM.addVariant(TYPE_INCENDIARY, TGItems.MINIGUN_DRUM_INCENDIARY, TGItems.RIFLE_ROUNDS_INCENDIARY);
		
		ADVANCED_MAGAZINE = new AmmoType(TGItems.ADVANCED_MAGAZINE, TGItems.ADVANCED_MAGAZINE_EMPTY, TGItems.ADVANCED_ROUNDS, 3);
		AS50_MAGAZINE = new AmmoType(TGItems.AS50_MAGAZINE, TGItems.AS50_MAGAZINE_EMPTY, TGItems.SNIPER_ROUNDS,2);
		AS50_MAGAZINE.addVariant(TYPE_INCENDIARY, TGItems.AS50_MAGAZINE_INCENDIARY, TGItems.SNIPER_ROUNDS_INCENDIARY);
		AS50_MAGAZINE.addVariant(TYPE_EXPLOSIVE, TGItems.AS50_MAGAZINE_EXPLOSIVE, TGItems.SNIPER_ROUNDS_EXPLOSIVE);
		
		ROCKETS = new AmmoType(TGItems.ROCKET);
		ROCKETS.addVariant(TYPE_NUKE, TGItems.ROCKET_NUKE);
		ROCKETS.addVariant(TYPE_HV, TGItems.ROCKET_HIGH_VELOCITY);
		
		ROCKETS_NO_NUKES = new AmmoType(TGItems.ROCKET);
		ROCKETS_NO_NUKES.addVariant(TYPE_HV, TGItems.ROCKET_HIGH_VELOCITY);
		
		NETHER_CHARGE = new AmmoType(TGItems.NETHER_CHARGE);
		GRENADES_40MM = new AmmoType(TGItems.GRENADE_40MM);
		NUCLEAR_POWER_CELL = new AmmoType(TGItems.NUCLEAR_POWERCELL, TGItems.NUCLEAR_POWERCELL_EMPTY, ItemStack.EMPTY,0);
		
		AMMO_GAUSS_RIFLE = new AmmoType(new ItemStack[] {TGItems.GAUSSRIFLE_SLUGS, TGItems.ENERGY_CELL}, new ItemStack[] {ItemStack.EMPTY, TGItems.ENERGY_CELL_EMPTY}, new ItemStack[] {TGItems.GAUSSRIFLE_SLUGS, ItemStack.EMPTY},  1);
	}

	@Override
	public void init(FMLInitializationEvent event) {
		
	}

	@Override
	public void postInit(FMLPostInitializationEvent event) {

	}	
}
