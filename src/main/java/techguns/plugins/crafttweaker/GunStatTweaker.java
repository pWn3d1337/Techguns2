package techguns.plugins.crafttweaker;

import crafttweaker.CraftTweakerAPI;
import crafttweaker.IAction;
import net.minecraft.item.Item;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.common.registry.GameRegistry;
import stanhebben.zenscript.annotations.ZenClass;
import stanhebben.zenscript.annotations.ZenMethod;
import techguns.Techguns;
import techguns.items.guns.GenericGun;

@ZenClass("mods.techguns.GunStats")
public class GunStatTweaker {

	@ZenMethod
	public static void setWeaponStat(String weaponname, String fieldname, float value) {
		CraftTweakerAPI.apply(new setGunStatAction(weaponname, fieldname, value));
	}
	
	private static class setGunStatAction implements IAction {

		protected String weaponname;
		protected String fieldname;
		
		protected GenericGun gun=null;
		protected EnumGunStat field=null;
		protected float value;
		
		protected boolean gunOk;
		
		public setGunStatAction(String weaponname, String fieldname, float value) {
			this.fieldname=fieldname;
			this.weaponname=weaponname;
			Item item = GameRegistry.findRegistry(Item.class).getValue(new ResourceLocation(Techguns.MODID, weaponname));
			this.field = EnumGunStat.parseFromString(fieldname);
			this.value = value;

			this.gunOk = item !=null && item instanceof GenericGun;
			this.gun = (GenericGun) item;
		}

		@Override
		public void apply() {
			if(this.field!=null && this.gunOk) {
				this.gun.setGunStat(field, value);
			}
		}

		@Override
		public String describe() {
			if(!gunOk) {
				return "Failed setting ["+fieldname+"] for Weapon: ["+weaponname+"]: ITEM IS NOT A GUN";
			}
			if (field==null) {
				return "Failed setting ["+fieldname+"] for Weapon: ["+weaponname+"]: UNKNOWN FIELD";
			}

			return "Set ["+fieldname+"] for Weapon: ["+weaponname+"] to: "+value;
		}
		
	}
}
