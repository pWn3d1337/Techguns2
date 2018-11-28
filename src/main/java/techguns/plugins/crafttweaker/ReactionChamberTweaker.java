package techguns.plugins.crafttweaker;

import java.util.Iterator;

import crafttweaker.CraftTweakerAPI;
import crafttweaker.IAction;
import crafttweaker.api.item.IItemStack;
import crafttweaker.api.liquid.ILiquidStack;
import crafttweaker.api.minecraft.CraftTweakerMC;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.FluidStack;
import stanhebben.zenscript.annotations.ZenClass;
import stanhebben.zenscript.annotations.ZenMethod;
import techguns.tileentities.operation.ReactionBeamFocus;
import techguns.tileentities.operation.ReactionChamberRecipe;
import techguns.tileentities.operation.ReactionChamberRecipe.RiskType;
import techguns.util.ItemStackOreDict;
import techguns.util.ItemUtil;
import techguns.util.MathUtil;

@ZenClass("mods.techguns.ReactionChamber")
public class ReactionChamberTweaker {

	@ZenMethod
	public static void addRecipe(String key, IItemStack input,ILiquidStack fluid, IItemStack[] outputs, IItemStack beamFocus, int ticks, int requiredCompletion,
			int preferredIntensity, int intensityMargin, int liquidLevel, int liquidConsumption, float instability, String risk, int RFTick){
		CraftTweakerAPI.apply(new addInputAction(key,TGCraftTweakerHelper.toItemStackOreDict(input),fluid,outputs,
				beamFocus,ticks,requiredCompletion,preferredIntensity,intensityMargin,
				liquidLevel, liquidConsumption, instability, risk, RFTick));
	}
	
	@ZenMethod
	public static void addRecipe(String key,String input,ILiquidStack fluid, IItemStack[] outputs, IItemStack beamFocus, int ticks, int requiredCompletion,
			int preferredIntensity, int intensityMargin, int liquidLevel, int liquidConsumption, float instability, String risk, int RFTick){
		CraftTweakerAPI.apply(new addInputAction(key,TGCraftTweakerHelper.toItemStackOreDict(input),fluid,outputs,
				beamFocus,ticks,requiredCompletion,preferredIntensity,intensityMargin,
				liquidLevel, liquidConsumption, instability, risk, RFTick));
	}
	
	@ZenMethod
	public static void addRecipe(String key,IItemStack input,ILiquidStack fluid, IItemStack output, IItemStack beamFocus, int ticks, int requiredCompletion,
			int preferredIntensity, int intensityMargin, int liquidLevel, int liquidConsumption, float instability, String risk, int RFTick){
		CraftTweakerAPI.apply(new addInputAction(key,TGCraftTweakerHelper.toItemStackOreDict(input),fluid,new IItemStack[]{output},
				beamFocus,ticks,requiredCompletion,preferredIntensity,intensityMargin,
				liquidLevel, liquidConsumption, instability, risk, RFTick));
	}

	@ZenMethod
	public static void addRecipe(String key, String input,ILiquidStack fluid, IItemStack output, IItemStack beamFocus, int ticks, int requiredCompletion,
			int preferredIntensity, int intensityMargin, int liquidLevel, int liquidConsumption, float instability, String risk, int RFTick){
		CraftTweakerAPI.apply(new addInputAction(key,TGCraftTweakerHelper.toItemStackOreDict(input),fluid,new IItemStack[]{output},
				beamFocus,ticks,requiredCompletion,preferredIntensity,intensityMargin,
				liquidLevel, liquidConsumption, instability, risk, RFTick));
	}

	@ZenMethod
	public static void removeRecipe(IItemStack input, ILiquidStack fluid){
		CraftTweakerAPI.apply(new removeInputAction(TGCraftTweakerHelper.toItemStackOreDict(input), fluid));
	}
	
	@ZenMethod
	public static void removeRecipe(String input, ILiquidStack fluid){
		CraftTweakerAPI.apply(new removeInputAction(TGCraftTweakerHelper.toItemStackOreDict(input), fluid));
	}
	
	private static class addInputAction implements IAction
	{
		String key;
		ReactionChamberRecipe added_recipe=null;
		
		ItemStackOreDict input;
		FluidStack fluid;
		ItemStack[] outputs;
		
		ItemStack beamFocus;
		
		byte ticks; //time of reaction in ReactionTicks
		
		byte requiredCompletion; //Required completion level for success
		byte preferredIntensity; //[0-10]
		byte intensityMargin; //[0-10]
		byte liquidLevel; //[0-10] in Buckets

		short liquidConsumtion; // < LiquidLevel; in milliBuckets
		float instability; //[0.0f-1.0f] chance to change preferredIntensity per ReactionTick
		
		String risk_str;
		RiskType risk;

		int RFTick;
		
		boolean valid=true;
		String msg;
			
		public addInputAction(String key,ItemStackOreDict input, ILiquidStack fluid, IItemStack[] outputs, IItemStack beamFocus, int ticks, int requiredCompletion,
				int preferredIntensity, int intensityMargin, int liquidLevel, int liquidConsumtion, float instability, String risk, int RFTick) {
			super();
			this.key=key;
			this.input = input;
			this.fluid = CraftTweakerMC.getLiquidStack(fluid);
			this.outputs = CraftTweakerMC.getItemStacks(outputs);
			this.beamFocus = CraftTweakerMC.getItemStack(beamFocus);
			this.ticks = (byte) ticks;
			this.requiredCompletion = (byte) requiredCompletion;
			this.preferredIntensity = (byte) preferredIntensity;
			this.intensityMargin = (byte) intensityMargin;
			this.liquidLevel = (byte) liquidLevel;
			this.liquidConsumtion = (short) liquidConsumtion;
			this.instability = instability;
			this.risk_str = risk;//RiskType.valueOf(risk);
			this.RFTick = RFTick;
			
			this.valid=checkValid();
		}
		
		@Override
		public void apply() {
			if (valid){
				added_recipe = ReactionChamberRecipe.addRecipe(key,input,beamFocus,fluid.getFluid(),
						outputs,ticks,requiredCompletion,preferredIntensity,intensityMargin,
						liquidLevel,liquidConsumtion,instability,risk,RFTick);
			}
		}

		@Override
		public String describe() {
			if (valid){
				return "Add Recipe for "+outputs[0]+" to ReactionChamber";
			} else {
				return "ERROR Adding ReactionChamber Recipe: "+msg;
			}
		}

		private boolean checkValid(){
			
			if (ReactionBeamFocus.getBeamFocus(beamFocus)==null){
				msg="Item passed as Beam Focus is not valid!";
				return false;
			}
			
			if (!MathUtil.inRange(preferredIntensity, 0, 10)){
				msg="Preferred Intensity must be in [0-10]!";
				return false;
			}
			if (!MathUtil.inRange(intensityMargin, 0, 10)){
				msg="intensityMargin must be in [0-10]!";
				return false;
			}
			
			if (!MathUtil.inRange(liquidLevel, 0, 10)){
				msg="liquidLeve must be in [0-10]!";
				return false;
			}
			
			if (!MathUtil.inRange(liquidConsumtion, 0, liquidLevel*1000)){
				msg="liquidConsumption must not be negative and lower than 1000*liquidLevel!";
				return false;
			}
			
			if (instability<0f || instability >1f){
				msg="instability must be between 0.0 and 1.0";
				return false;
			}
			
			try {
				this.risk = RiskType.valueOf(risk_str);
			} catch (IllegalArgumentException ex){
				msg="Invalid risktype: "+ risk_str;
				return false;
			}
			
			return true;
		}
	}
	
	private static class removeInputAction implements IAction
	{
		
		ItemStackOreDict input;
		FluidStack fluid;
		
		int removed =0;
		
		public removeInputAction(ItemStackOreDict input, ILiquidStack fluid) {
			super();
			this.input = input;
			this.fluid = CraftTweakerMC.getLiquidStack(fluid);
		}

		@Override
		public void apply() {
			
			Iterator<String> iter = ReactionChamberRecipe.getRecipes().keySet().iterator();
			
			while (iter.hasNext()){
				String key = iter.next();
				ReactionChamberRecipe rec = ReactionChamberRecipe.getRecipes().get(key);
								
				if (rec.input.matches(input) && ItemUtil.isFluidEqual(new FluidStack(rec.liquidIn,1000), fluid)){
					this.removed++;
					iter.remove();
				}
			}
		}
		
		@Override
		public String describe() {
			return "Removed "+removed+" Recipe(s) using "+input+"/"+fluid.getUnlocalizedName()+" from ReactionChamber";
		}

	}
}
