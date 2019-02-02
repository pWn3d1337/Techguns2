package techguns.plugins.crafttweaker;

import java.util.List;

import crafttweaker.CraftTweakerAPI;
import crafttweaker.IAction;
import net.minecraft.entity.EntityLivingBase;
import net.minecraftforge.fml.common.registry.ForgeRegistries;
import stanhebben.zenscript.annotations.ZenClass;
import stanhebben.zenscript.annotations.ZenMethod;
import techguns.Techguns;
import techguns.deatheffects.EntityDeathUtils;
import techguns.deatheffects.EntityDeathUtils.DeathType;

/**
 * DISABLED
 */

//@ZenClass("mods.techguns.DeathEffect")
public class DeathEffectTweaker {

	//@ZenMethod
	public static void addEntity(String classname) {
		CraftTweakerAPI.apply(new addDeathEffect(classname));
	}
	
	//@ZenMethod
	public static void addEntityQuadruped(String classname, String modelname, int r, int g, int b) {
		CraftTweakerAPI.apply(new addGoreQuadrupedEffect(classname, modelname, r, g, b));
		CraftTweakerAPI.apply(new addDeathEffect(classname));
	}
	
	
	protected static class addGoreQuadrupedEffect implements IAction
	{

		protected String classname=null;
		protected String modelname=null;
		protected int r=0,g=0,b=0;
		
		protected boolean success=false;
		
		public addGoreQuadrupedEffect(String classname, String modelname, int r, int g, int b) {
			super();
			this.classname = classname;
			this.modelname = modelname;
			this.r = r;
			this.g = g;
			this.b = b;
		}

		@Override
		public void apply() {
			//success = Techguns.proxy.addGoreStatsQuadruped(classname, modelname, r, g, b);
		}

		@Override
		public String describe() {
			return "Trying to add "+classname+" with model "+modelname+" to GoreData: "+ (success?"SUCCESS":"FAILED");
		}
		
		
		
	}
	
	
	private static class addDeathEffect implements IAction
	{
		
		Class<? extends EntityLivingBase> clazz;
		
		Exception e = null;
		
		public addDeathEffect(String entity) {
			try {
				Class c = Class.forName(entity);
				if (EntityLivingBase.class.isAssignableFrom(c)) {
					clazz = c;
				} else {
					e = new IllegalArgumentException("passed entity class must be subclass of EntityLivingBase");
				}
			} catch (Exception ex) {
				e=ex;
			}	
		}
		
		
		@Override
		public void apply() {
			if(e==null && clazz!=null) {
				EntityDeathUtils.addEntityToDeathEffectList(clazz);
			}
		}

		@Override
		public String describe() {
			if(e!=null) {
				return "Can't add class to Entity Death Effects: "+e.getMessage();
			} else {
				return "Added class: "+clazz.getName() +" to Entity Death Effects";
			}
		}
	}
}
