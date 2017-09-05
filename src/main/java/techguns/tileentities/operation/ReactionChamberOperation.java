package techguns.tileentities.operation;

import java.util.ArrayList;
import java.util.Random;

import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.fluids.FluidStack;
import techguns.tileentities.ReactionChamberTileEntMaster;

public class ReactionChamberOperation extends MachineOperation {

	public static final byte RECIPE_TICKRATE=60;

	protected ReactionChamberRecipe recipe;
	
	public byte completion;
	public byte required_intensity;
	public byte nextTick;
	
	Random rnd;
	
	public ReactionChamberOperation(NBTTagCompound tags, ReactionChamberTileEntMaster tile) {
		super(new ArrayList<ItemStack>(), new ArrayList<ItemStack>(), null, null, 1);
		this.readFromNBT(tags);
		this.rnd=new Random();
	}
	
	
	public ReactionChamberOperation(ReactionChamberRecipe r, ReactionChamberTileEntMaster tile) {
		super(new ArrayList<ItemStack>(), new ArrayList<ItemStack>(), null, null, 1);
		ItemStack input =tile.input.get().copy();
		input.setCount(1);
		this.inputs.add(input);
		this.outputs.addAll(r.outputs);
		if(r.liquidConsumtion>0) {
			this.fluid_inputs = new ArrayList<FluidStack>();
			this.fluid_inputs.add(new FluidStack(r.liquidIn,r.liquidConsumtion));
		}
		this.recipe=r;
		this.completion=0;
		this.nextTick=RECIPE_TICKRATE;
		this.required_intensity=r.preferredIntensity;
		this.rnd=new Random();
	}

	@Override
	protected void writeSubclassDataToNBT(NBTTagCompound tags) {
		super.writeSubclassDataToNBT(tags);
		if(recipe!=null) {
			tags.setString("ReactionID", recipe.ID);
		}
		tags.setByte("completion", completion);
		tags.setByte("required_intensity", required_intensity);
		tags.setByte("nextTick", nextTick);
	}

	@Override
	protected void readSubClassDataFromNBT(NBTTagCompound tags) {
		super.readSubClassDataFromNBT(tags);
		String recID = tags.getString("ReactionID");
		this.recipe = ReactionChamberRecipe.getByKey(recID);
		if(this.recipe==null) {
			System.out.println("WARNING: RECIPE UNKNOWN:"+recID);
		}
		this.completion=tags.getByte("completion");
		this.required_intensity = tags.getByte("required_intensity");
		this.nextTick=tags.getByte("nextTick");
	}

	public ReactionChamberRecipe getRecipe() {
		return recipe;
	}

	public boolean focusMatches(ItemStack item){
		return this.recipe.beamFocus == ReactionBeamFocus.getBeamFocus(item);
	}
	
	public boolean tick(byte intensity,byte liquidLevel, boolean client, ReactionChamberTileEntMaster tile, int RFTick){
		int progress = tile.progress;
		int totalTime = tile.totaltime;
		
		nextTick--;
		if (!client && nextTick==0 && progress<totalTime){
			boolean powered = tile.consumePower(RFTick);
			
			boolean focusMatch = focusMatches(tile.getInventory().getStackInSlot(ReactionChamberTileEntMaster.SLOT_FOCUS));
			
			//do recipe tick
			if (powered && focusMatch && this.required_intensity==intensity && this.recipe.liquidLevel==liquidLevel){
				completion++;
			}
			
			if(!focusMatch) {
				//instantly finish operation on focus mismatch and remove all completion
				tile.progress=tile.totaltime;
				this.completion=0;
			} else {
			
				if(this.recipe.instability>0.0f && rnd.nextFloat() < this.recipe.instability) {
					//rolled a change
					
					int val = 1;
					if(this.recipe.intensityMargin>1) {
						val += rnd.nextInt(this.recipe.intensityMargin);
					}
					
					if(rnd.nextBoolean()) {
						this.required_intensity-=val;
						if(this.required_intensity<0) {
							this.required_intensity=0;
						}
					} else {
						this.required_intensity+=val;
						if(this.required_intensity>10) {
							this.required_intensity=10;
						}
					}
					
				}
			}
			nextTick=RECIPE_TICKRATE;
			
			return true;
		} else {
			return this.isFinished(tile);
		}
	}
	
	public boolean isFinished(ReactionChamberTileEntMaster master){
		return master.progress>=master.totaltime || this.completion>=this.getRecipe().requiredCompletion;
	}
	
	public boolean isSuccess(){
		return this.completion>=this.getRecipe().requiredCompletion;
	}
	
	public boolean isFailure(ReactionChamberTileEntMaster master){
		return master.progress>=master.totaltime;
	}
	
	public int getCurrentPreferedIntensity(){
		return this.required_intensity;
	}

}
