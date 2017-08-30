package techguns.api.damagesystem;

import com.mojang.realmsclient.gui.ChatFormatting;

import net.minecraft.client.resources.I18n;

public enum DamageType {
	PHYSICAL, //Melee hits
	PROJECTILE, //Arrows, bullets,..
	FIRE, //fire, flamethrower
	EXPLOSION, // Explosions like TNT, Rockets
	ENERGY, // Energy Weapons, Lasers, Magic
	POISON, //gas, slimy stuff
	UNRESISTABLE, //Things that should never get reduced with armor value: starve, falldmg, suffocate...
	ICE, //anything cold related
	LIGHTNING, //electricity, shock, lightning magic
	RADIATION, //
	DARK; // dark energy, black holes, dark magic

	@Override
	public String toString() {
		switch(this){
			
			case PROJECTILE:
				return ChatFormatting.GRAY+I18n.format("techguns.TGDamageType.PROJECTILE", new Object[0]);
			case FIRE:
				return ChatFormatting.RED+I18n.format("techguns.TGDamageType.FIRE",new Object[0]);
			case EXPLOSION:
				return ChatFormatting.DARK_RED+I18n.format("techguns.TGDamageType.EXPLOSION",new Object[0]);
			case ENERGY:
				return ChatFormatting.DARK_AQUA+I18n.format("techguns.TGDamageType.ENERGY",new Object[0]);
			case POISON:
				return ChatFormatting.DARK_GREEN+I18n.format("techguns.TGDamageType.POISON",new Object[0]);
			case UNRESISTABLE:
				return ChatFormatting.BLACK+I18n.format("techguns.TGDamageType.UNRESISTABLE",new Object[0]);
			case ICE:
				return ChatFormatting.AQUA+I18n.format("techguns.TGDamageType.ICE",new Object[0]);
			case LIGHTNING:
				return ChatFormatting.YELLOW+I18n.format("techguns.TGDamageType.LIGHTNING",new Object[0]);
			case RADIATION:
				return ChatFormatting.GREEN+I18n.format("techguns.TGDamageType.RADIATION",new Object[0]);
			case DARK:
				return ChatFormatting.BLACK+I18n.format("techguns.TGDamageType.DARK",new Object[0]);
			case PHYSICAL:
			default:
				return ChatFormatting.DARK_GRAY+I18n.format("techguns.TGDamageType.PHYSICAL",new Object[0]);
		}
	}
	
	
}
