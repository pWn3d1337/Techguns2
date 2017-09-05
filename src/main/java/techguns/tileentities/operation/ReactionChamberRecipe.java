package techguns.tileentities.operation;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import com.mojang.realmsclient.gui.ChatFormatting;

import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidStack;
import techguns.util.ItemStackOreDict;
import techguns.util.ItemUtil;
import techguns.util.TextUtil;

public class ReactionChamberRecipe implements IMachineRecipe {
	
	public String ID;
	
	public ItemStackOreDict input;
	public ReactionBeamFocus beamFocus;
	public Fluid liquidIn;
	public ArrayList<ItemStack> outputs;
	
	public byte ticks; //time of reaction in ReactionTicks
	
	public byte requiredCompletion; //Required completion level for success
	public byte preferredIntensity; //[0-10]
	public byte intensityMargin; //[0-10]
	public byte liquidLevel; //[0-10] in Buckets
	public int liquidConsumtion; // < LiquidLevel; in milliBuckets
	public float instability; //[0.0f-1.0f] chance to change preferredIntensity per ReactionTick
	
	public RiskType risk; //What happens when reaction fails.
	
	/**
	 * RF per Recipe tick
	 */
	public int RFTick;
	
	public static ReactionChamberRecipe getByKey(String key){
		ReactionChamberRecipe rec = recipes.get(key);
		return rec;
	}
	
	private static HashMap<String,ReactionChamberRecipe> recipes = new HashMap();
	
	public static HashMap<String,ReactionChamberRecipe> getRecipes(){
		return recipes;
	}
	
	public static ReactionChamberRecipe addRecipe(String ID,ItemStackOreDict input,
			ItemStack beamFocus, Fluid liquidIn,
			ItemStack output, int ticks, int requiredCompletion,
			int preferredIntensity, int intensityMargin, int liquidLevel,
			int liquidConsumtion, float instability, RiskType risk, int RFTick) {
		ReactionChamberRecipe rec = new ReactionChamberRecipe(ID,input, beamFocus, liquidIn, output, ticks, requiredCompletion, preferredIntensity, intensityMargin, liquidLevel, (short)liquidConsumtion, instability, risk, RFTick);
		recipes.put(rec.ID, rec);
		return rec;
	}
	
	public static ReactionChamberRecipe addRecipe(String ID,ItemStackOreDict input,
			ItemStack beamFocus, Fluid liquidIn,
			ItemStack[] outputs, int ticks, int requiredCompletion,
			int preferredIntensity, int intensityMargin, int liquidLevel,
			int liquidConsumtion, float instability, RiskType risk, int RFTick) {
		ReactionChamberRecipe rec = new ReactionChamberRecipe(ID,input, beamFocus, liquidIn, outputs, ticks, requiredCompletion, preferredIntensity, intensityMargin, liquidLevel, (short)liquidConsumtion, instability, risk, RFTick);
		recipes.put(rec.ID,rec);
		return rec;
	}
	
	private ReactionChamberRecipe(String ID,ItemStackOreDict input,
			ItemStack beamFocus, Fluid liquidIn,
			ItemStack output, int ticks, int requiredCompletion,
			int preferredIntensity, int intensityMargin, int liquidLevel,
			short liquidConsumtion, float instability, RiskType risk, int RFTick) {
		this.input=input;
		this.beamFocus=ReactionBeamFocus.getBeamFocus(beamFocus);
		this.liquidIn=liquidIn;
		this.outputs = new ArrayList<ItemStack>();
		this.outputs.add(output);
		this.ticks = (byte) ticks;
		this.requiredCompletion = (byte) requiredCompletion;
		this.preferredIntensity = (byte) preferredIntensity;
		this.intensityMargin = (byte) intensityMargin;
		this.liquidLevel = (byte) liquidLevel;
		this.liquidConsumtion = liquidConsumtion;
		this.instability = instability;
		this.risk = risk;
		this.RFTick = RFTick;
		
		this.ID = ID;
	}
	
	private ReactionChamberRecipe(String ID,ItemStackOreDict input,
			ItemStack beamFocus, Fluid liquidIn,
			ItemStack[] outputs, int ticks, int requiredCompletion,
			int preferredIntensity, int intensityMargin, int liquidLevel,
			short liquidConsumtion, float instability, RiskType risk, int RFTick) {
		this.input=input;
		this.beamFocus=ReactionBeamFocus.getBeamFocus(beamFocus);
		this.liquidIn=liquidIn;
		this.outputs = new ArrayList<ItemStack>();
		for (ItemStack item : outputs) {
			this.outputs.add(item);
		}
		this.ticks = (byte) ticks;
		this.requiredCompletion = (byte) requiredCompletion;
		this.preferredIntensity = (byte) preferredIntensity;
		this.intensityMargin = (byte) intensityMargin;
		this.liquidLevel = (byte) liquidLevel;
		this.liquidConsumtion = liquidConsumtion;
		this.instability = instability;
		this.risk = risk;
		this.RFTick = RFTick;
		
		this.ID = ID;
	}
	
	
	public static ReactionChamberRecipe getMatchingRecipe(ItemStack input, ItemStack focus, FluidStack tank, byte liquidLevel, byte intensity){
		Iterator<String> it = recipes.keySet().iterator();
		while(it.hasNext()) {
			ReactionChamberRecipe rec = recipes.get(it.next());
			if(rec.matches(input, focus, tank, liquidLevel, intensity)) {
				return rec;
			}
		}
		return null;
	}
	
	public boolean matches(ItemStack input, ItemStack focus, FluidStack tank, byte liquidLevel, byte intensity){
		
		if (!input.isEmpty() && this.input.isEqualWithOreDict(input)){
			ReactionBeamFocus f = ReactionBeamFocus.getBeamFocus(focus);
			if (f!=null && f == this.beamFocus){
				if (tank!=null && tank.getFluid()==this.liquidIn && liquidLevel == this.liquidLevel && tank.amount == (this.liquidLevel*Fluid.BUCKET_VOLUME)) {
					if (intensity == this.preferredIntensity) {
						return true;
					}
				}
			}
		}
		return false;	
	}
	
	public String getID() {
		return ID;
	}

	public void addOutput(ItemStack output){
		outputs.add(output);
	}
	
	/**
	 * Returns if this method has the passed itemstack as result, needed for NEI handler
	 * @param result
	 * @return
	 */
	public boolean hasResult(ItemStack result){
		for(int i=0; i<this.outputs.size(); i++){
			if (ItemUtil.isItemEqual(result, this.outputs.get(i))){
				return true;
			}
		}
		return false;
	}

	public boolean usesItem(ItemStack itm){
		return this.input.isEqualWithOreDict(itm);
	}
	
	public enum RiskType {
		NONE, BREAK_ITEM, RAD_LOW, RAD_MEDIUM, RAD_HIGH, EXPLOSION_LOW, EXPLOSION_MEDIUM, EXPLOSION_HIGH, UNFORSEEN_CONSEQUENCES;
		
		@Override
		public String toString() {
			switch(this){
				
				case BREAK_ITEM:
					return ChatFormatting.GREEN+TextUtil.trans("techguns.reactionChamber.RiskType.breakItem");
				case EXPLOSION_LOW:
					return ChatFormatting.YELLOW+TextUtil.trans("techguns.reactionChamber.RiskType.expLow");
				case EXPLOSION_MEDIUM:
					return ChatFormatting.RED+TextUtil.trans("techguns.reactionChamber.RiskType.expMed");
				case RAD_LOW:
				case RAD_MEDIUM:
				case RAD_HIGH:
				case EXPLOSION_HIGH:
				case UNFORSEEN_CONSEQUENCES:
					return "NOT_YET_IMPLEMENTED";
					
				case NONE:
				default:
					return ChatFormatting.WHITE+TextUtil.trans("techguns.reactionChamber.RiskType.none");
			}
		}
		
		public static boolean isFatal(RiskType type) {
			return !(type==EXPLOSION_LOW || type ==EXPLOSION_HIGH || type== EXPLOSION_MEDIUM || type== UNFORSEEN_CONSEQUENCES);
		}
	}

	public boolean isStable() {
		return this.instability==0.0f || this.intensityMargin==0;
	}
	
	@Override
	public List<List<ItemStack>> getItemInputs() {
		ArrayList<List<ItemStack>> list = new ArrayList<>();
		list.add(this.input.getItemStacks());
		
		ArrayList<ItemStack> focus = new ArrayList<>();
		focus.add(this.beamFocus.item);
		list.add(focus);
		
		return list;
	}

	@Override
	public List<List<ItemStack>> getItemOutputs() {
		ArrayList<List<ItemStack>> list = new ArrayList<>();
		this.outputs.forEach(s -> {
			ArrayList<ItemStack> list2 = new ArrayList<>();
			list2.add(s);
			list.add(list2);
		});
		return list;
	}

	@Override
	public List<List<FluidStack>> getFluidInputs() {
		ArrayList<List<FluidStack>> list = new ArrayList<>();
		ArrayList<FluidStack> list2 = new ArrayList<>();
		list2.add(new FluidStack(this.liquidIn, this.liquidLevel*Fluid.BUCKET_VOLUME));
		list.add(list2);
		return list;
	}
	
}