package techguns.tileentities.operation;

import java.util.ArrayList;

import net.minecraft.item.ItemStack;
import net.minecraft.util.SoundEvent;

public class ReactionBeamFocus {
	
	protected static ArrayList<ReactionBeamFocus> beamFocusList = new ArrayList<ReactionBeamFocus>();
	
	ItemStack item;
	int powerConsumtion; //RF consumed per ReactionTick
	SoundEvent sound; //Sound played each ReactionTick
	float radiationFactor_failure=0.5f;
	float radiationFactor_success=0f;
	float rad_radius_inner=4.0f;
	float rad_radius_outer=6.0f;
	
	public ReactionBeamFocus(ItemStack item, int powerConsumtion, SoundEvent sound) {
		super();
		this.item = item;
		this.powerConsumtion = powerConsumtion;
		this.sound = sound;
	}		
	
	public ReactionBeamFocus(ItemStack item, int powerConsumtion, SoundEvent sound, float radiation_factor_failure, float radiation_factor_success) {
		super();
		this.item = item;
		this.powerConsumtion = powerConsumtion;
		this.sound = sound;
		this.radiationFactor_failure = radiation_factor_failure;
		this.radiationFactor_success = radiation_factor_success;
	}		
	
	public static void addBeamFocus(ItemStack item, int powerConsumption, SoundEvent sound) {
		beamFocusList.add(new ReactionBeamFocus(item, powerConsumption, sound));
	}
	
	public static ItemStack getItemForType(ReactionBeamFocus type){
		if (type !=null){
			for (ReactionBeamFocus rbf : beamFocusList) {
				if (rbf.equals(type)) {
					return rbf.item;
				}
			}
		}
		return ItemStack.EMPTY;
	}
	
	public static ReactionBeamFocus getBeamFocus(ItemStack item) {
		if (!item.isEmpty()){
			for (ReactionBeamFocus rbf : beamFocusList) {
				if (rbf.item.getItem() == item.getItem() && rbf.item.getItemDamage() == item.getItemDamage()) {
					return rbf;
				}
			}
		}
		return null;
	}

	public SoundEvent getSound() {
		return sound;
	}
	
}