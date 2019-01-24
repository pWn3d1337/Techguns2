package techguns.tileentities.operation;

import java.util.ArrayList;
import java.util.Arrays;

import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraftforge.common.util.Constants;
import net.minecraftforge.fluids.FluidStack;

public class MachineOperation {

	protected ArrayList<ItemStack> inputs=null;
	protected ArrayList<ItemStack> outputs=null;
	protected ArrayList<FluidStack> fluid_inputs=null;
	protected ArrayList<FluidStack> fluid_outputs=null;
	protected int stackMultiplier=1;
	
	protected int powerPerTick = 0;
	
	public MachineOperation(ItemStack output, ItemStack... inputs) {
		this.outputs=new ArrayList<>();
		outputs.add(output);
		this.inputs = new ArrayList<>();
		Arrays.stream(inputs).forEach(i -> this.inputs.add(i));
	}
	
	public MachineOperation(ItemStack output, FluidStack fluidOut, ItemStack... inputs) {
		this.outputs=new ArrayList<>();
		outputs.add(output);
		this.inputs = new ArrayList<>();
		Arrays.stream(inputs).forEach(i -> this.inputs.add(i));
		this.fluid_outputs=new ArrayList<>();
		this.fluid_outputs.add(fluidOut);
	}
	
	public MachineOperation(FluidStack fluidOut, ItemStack... inputs) {
		this.inputs = new ArrayList<>();
		Arrays.stream(inputs).forEach(i -> this.inputs.add(i));
		this.fluid_outputs=new ArrayList<>();
		this.fluid_outputs.add(fluidOut);
	}
	
	public MachineOperation(ArrayList<ItemStack> inputs, ArrayList<ItemStack> outputs, ArrayList<FluidStack> fluid_inputs, ArrayList<FluidStack> fluid_outputs, int stackMultiplier) {
		super();
		this.inputs = inputs;
		this.outputs = outputs;
		this.fluid_outputs = fluid_outputs;
		this.fluid_inputs = fluid_inputs;
		this.stackMultiplier = stackMultiplier;
	}

	public MachineOperation setStackMultiplier(int mult) {
		this.stackMultiplier=mult;
		return this;
	}
	
	public int getStackMultiplier() {
		return stackMultiplier;
	}
	
	public int getPowerPerTick() {
		return powerPerTick * this.stackMultiplier;
	}

	public void setPowerPerTick(int powerPerTick) {
		this.powerPerTick = powerPerTick;
	}

	public void writeToNBT(NBTTagCompound compound) {
		NBTTagCompound tags = new NBTTagCompound();
		
		tags.setByte("stackMultiplier", (byte) stackMultiplier);
		tags.setInteger("powerPerTick", this.powerPerTick);
		
		if(inputs!=null && inputs.size()>0) {
			
			NBTTagList nbtTagList = new NBTTagList();
	        for (int i = 0; i < inputs.size(); i++)
	        {
	            if (!inputs.get(i).isEmpty())
	            {
	                NBTTagCompound itemTag = new NBTTagCompound();
	               // itemTag.setInteger("Slot", i);
	                inputs.get(i).writeToNBT(itemTag);
	                nbtTagList.appendTag(itemTag);
	            }
	        }
	        tags.setTag("InputItems", nbtTagList);
	        tags.setByte("InputSize", (byte) inputs.size());
			
		}
		if(outputs!=null && outputs.size()>0) {
			
			NBTTagList nbtTagList = new NBTTagList();
	        for (int i = 0; i < outputs.size(); i++)
	        {
	            if (!outputs.get(i).isEmpty())
	            {
	                NBTTagCompound itemTag = new NBTTagCompound();
	                //itemTag.setInteger("Slot", i);
	                outputs.get(i).writeToNBT(itemTag);
	                nbtTagList.appendTag(itemTag);
	            }
	        }
	        tags.setTag("OutputItems", nbtTagList);
	        tags.setByte("OutputSize", (byte) outputs.size());
			
		}
		
		if(fluid_inputs!=null && fluid_inputs.size()>0) {
			NBTTagList nbtTagList = new NBTTagList();
	        for (int i = 0; i < fluid_inputs.size(); i++)
	        {
	            if (fluid_inputs.get(i)!=null)
	            {
	                NBTTagCompound fluidTag = new NBTTagCompound();
	               // fluidTag.setInteger("Slot", i);
	                fluid_inputs.get(i).writeToNBT(fluidTag);
	                nbtTagList.appendTag(fluidTag);
	            }
	        }
	        tags.setTag("FluidsIn", nbtTagList);
	        tags.setByte("FluidSizeIn", (byte) fluid_inputs.size());
		}
		
		if(fluid_outputs!=null && fluid_outputs.size()>0) {
			NBTTagList nbtTagList = new NBTTagList();
	        for (int i = 0; i < fluid_outputs.size(); i++)
	        {
	            if (fluid_outputs.get(i)!=null)
	            {
	                NBTTagCompound fluidTag = new NBTTagCompound();
	               // fluidTag.setInteger("Slot", i);
	                fluid_outputs.get(i).writeToNBT(fluidTag);
	                nbtTagList.appendTag(fluidTag);
	            }
	        }
	        tags.setTag("Fluids", nbtTagList);
	        tags.setByte("FluidSize", (byte) fluid_outputs.size());
		}
		
		this.writeSubclassDataToNBT(tags);
		compound.setTag("operation", tags);
	}
	
	protected void writeSubclassDataToNBT(NBTTagCompound tags) {
		
	}
	
	
	public void readFromNBT(NBTTagCompound compound) {
		NBTTagCompound tags = compound.getCompoundTag("operation");
		
		this.stackMultiplier = tags.getByte("stackMultiplier");
		this.powerPerTick= tags.getInteger("powerPerTick");
		
		if(tags.hasKey("InputSize")) {
			if (this.inputs!=null) {
				this.inputs.clear();
			} else {
				this.inputs= new ArrayList<>();
			}
			int size = tags.getByte("InputSize");
			
			NBTTagList tagList = tags.getTagList("InputItems", Constants.NBT.TAG_COMPOUND);
			for (int i=0;i<size;i++) {
				NBTTagCompound itemTags = tagList.getCompoundTagAt(i);
	            this.inputs.add(new ItemStack(itemTags));
			}
			
		}
		if(tags.hasKey("OutputSize")) {
			if (this.outputs!=null) {
				this.outputs.clear();
			} else {
				this.outputs= new ArrayList<>();
			}
			int size = tags.getByte("OutputSize");
			
			NBTTagList tagList = tags.getTagList("OutputItems", Constants.NBT.TAG_COMPOUND);
			for (int i=0;i<size;i++) {
				NBTTagCompound itemTags = tagList.getCompoundTagAt(i);
	            this.outputs.add(new ItemStack(itemTags));
			}
			
		}
		
		if(tags.hasKey("FluidSizeIn")) {
			if (this.fluid_inputs!=null) {
				this.fluid_inputs.clear();
			} else {
				this.fluid_inputs= new ArrayList<>();
			}
			int size = tags.getByte("FluidSizeIn");
			
			NBTTagList tagList = tags.getTagList("FluidsIn", Constants.NBT.TAG_COMPOUND);
			for (int i=0;i<size;i++) {
				NBTTagCompound fluidTags = tagList.getCompoundTagAt(i);
	            this.fluid_inputs.add(FluidStack.loadFluidStackFromNBT(fluidTags));
			}
			
		}
		
		if(tags.hasKey("FluidSize")) {
			if (this.fluid_outputs!=null) {
				this.fluid_outputs.clear();
			} else {
				this.fluid_outputs= new ArrayList<>();
			}
			int size = tags.getByte("FluidSize");
			
			NBTTagList tagList = tags.getTagList("Fluids", Constants.NBT.TAG_COMPOUND);
			for (int i=0;i<size;i++) {
				NBTTagCompound fluidTags = tagList.getCompoundTagAt(i);
	            this.fluid_outputs.add(FluidStack.loadFluidStackFromNBT(fluidTags));
			}
			
		}
		this.readSubClassDataFromNBT(tags);
	}
	
	protected void readSubClassDataFromNBT(NBTTagCompound tags) {
		
	}
	
	/**
	 * get the item input i (without stack multiplier)
	 * @return
	 */
	public ItemStack getItemInputI(int i) {
		if (this.inputs!=null && !this.inputs.isEmpty() && this.inputs.size()>i) {
			ItemStack in = this.inputs.get(i).copy();
			return in;
		} else {
			return ItemStack.EMPTY;
		}
	}
	
	/**
	 * get the output item i, without stack multiplier
	 * @return
	 */
	public ItemStack getItemOutputI(int i) {
		if (this.outputs!=null && !this.outputs.isEmpty() && this.outputs.size()>i) {
			ItemStack out = this.outputs.get(i).copy();
			return out;
		} else {
			return ItemStack.EMPTY;
		}
	}
	
	/**
	 * get the first item output
	 * @return
	 */
	public ItemStack getItemOutput0() {
		if (this.outputs!=null && !this.outputs.isEmpty()) {
			ItemStack out = this.outputs.get(0).copy();
			out.setCount(this.outputs.get(0).getCount()*this.stackMultiplier);
			return out;
		} else {
			return ItemStack.EMPTY;
		}
	}

	public FluidStack getFluidOutput0() {
		if (this.fluid_outputs!=null && !this.fluid_outputs.isEmpty()) {
			FluidStack output = this.fluid_outputs.get(0);
			if(output!=null) {
				FluidStack out = output.copy();
				out.amount = out.amount * this.stackMultiplier;
				return out;
			}
		}
		return null;
	}

	
	public ArrayList<ItemStack> getInputs() {
		return inputs;
	}

	public ArrayList<ItemStack> getOutputs() {
		return outputs;
	}

	public ArrayList<FluidStack> getFluid_inputs() {
		return fluid_inputs;
	}

	public ArrayList<FluidStack> getFluid_outputs() {
		return fluid_outputs;
	}

	public int getNeededAmountItem(int index) {
		if (this.inputs.size()<=index) {
			return 0;
		}
		ItemStack stack = this.inputs.get(index);
		return stack.getCount()*this.stackMultiplier;
	}
	
	public int getNeededAmountFluid(int index) {
		if (this.fluid_inputs.size()<=index) {
			return 0;
		}
		FluidStack fluid = this.getFluid_inputs().get(index);
		return fluid.amount*this.stackMultiplier;
	}
}
