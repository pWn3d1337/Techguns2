package techguns.plugins.crafttweaker;

import crafttweaker.CraftTweakerAPI;
import crafttweaker.IAction;
import net.minecraft.item.Item;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.common.registry.GameRegistry;
import stanhebben.zenscript.annotations.ZenClass;
import stanhebben.zenscript.annotations.ZenMethod;
import techguns.Techguns;
import techguns.api.damagesystem.DamageType;
import techguns.items.armors.EnumArmorStat;
import techguns.items.armors.GenericArmor;
import techguns.items.armors.PoweredArmor;
import techguns.items.armors.TGArmorMaterial;
import techguns.items.guns.GenericGun;
import techguns.util.MathUtil;

@ZenClass("mods.techguns.ArmorStats")
public class ArmorStatTweaker {

	@ZenMethod
	public static void setArmorStat(String armorname, String stat, float value) {
		CraftTweakerAPI.apply(new setArmorStatAction(armorname, stat, value));
	}
	
	@ZenMethod
	public static void setArmorStat(String armorname, String stat, float powered, float unpowered) {
		CraftTweakerAPI.apply(new setArmorStatAction(armorname, stat, powered, unpowered));
	}
	
	@ZenMethod
	public static void setMaterialArmorValue(String material, String damagetype, float amount) {
		CraftTweakerAPI.apply(new setArmorMaterialValue(material, damagetype, amount));
	}
	
	
	private static class setArmorMaterialValue implements IAction {

		String materialname;
		float value;
		String typename;
		
		DamageType type;
		TGArmorMaterial material;
		
		public setArmorMaterialValue(String materialname, String typename, float value) {
			super();
			this.materialname = materialname;
			this.value = MathUtil.clamp(value, 0f, 20f);
			this.typename = typename;
			
			for(DamageType dt : DamageType.values()) {
				if(dt.name().equalsIgnoreCase(typename)) {
					this.type=dt;
					break;
				}
			}
			
			for(TGArmorMaterial mat: TGArmorMaterial.MATERIALS) {
				if(mat.name.equalsIgnoreCase(materialname)) {
					this.material=mat;
					break;
				}
			}
			
		}

		@Override
		public void apply() {
			if(this.material!=null && this.type!=null) {
				this.material.setArmorValueForType(type, value);
			}
		}

		@Override
		public String describe() {
			if(material==null) {
				return "Failed setting ArmorValue ["+typename+"] for Material: ["+materialname+"]: MATERIAL DOES NOT EXIST!";
			}
			if (type==null) {
				return "Failed setting ArmorValue ["+typename+"] for Material: ["+materialname+"]: UNKNOWN DAMAGE TYPE!";
			}

			return "Set ArmorValue ["+typename+"] for Material: ["+materialname+"] to: "+value;
		}
		
	}
	
	private static class setArmorStatAction implements IAction {

		protected String armorname;
		protected String statname;
		
		protected GenericArmor armor;
		protected EnumArmorStat stat;
		
		protected float value;
		protected float value_unpowered;
		
		protected boolean itemOk=false;
		
		public setArmorStatAction(String armorname, String statname, float value) {
			this(armorname, statname, value, 0f);
		}
		public setArmorStatAction(String armorname, String statname, float value, float value_unpowered) {
			super();
			this.armorname = armorname;
			this.statname = statname;
			this.value = value;
			this.value_unpowered = value_unpowered;
			
			Item item = GameRegistry.findRegistry(Item.class).getValue(new ResourceLocation(Techguns.MODID, armorname));
			this.stat = EnumArmorStat.parseFromString(statname);
		

			this.itemOk = item !=null && item instanceof GenericArmor;
			if(itemOk) {
				this.armor = (GenericArmor) item;
			}
		}

		@Override
		public void apply() {
			if(itemOk && stat!=null) {
				if(this.armor instanceof PoweredArmor) {
					((PoweredArmor)armor).setArmorStat(stat, value, value_unpowered);
				} else {
					this.armor.setArmorStat(stat, value);
				}
			}
		}

		@Override
		public String describe() {
			if(!itemOk) {
				return "Failed setting ["+statname+"] for Weapon: ["+armorname+"]: ITEM IS NOT A TECHGUNS ARMOR";
			}
			if (stat==null) {
				return "Failed setting ["+statname+"] for Weapon: ["+armorname+"]: UNKNOWN STAT";
			}

			return "Set ["+statname+"] for Armor: ["+armorname+"] to: "+value;
		}
		
	}
	
}
