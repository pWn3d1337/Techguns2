package techguns.tileentities.operation;

import java.util.ArrayList;

import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.fluids.FluidStack;

public class MachineOperationChance extends MachineOperation {
	protected TileEntity tile;
	protected double[] itemChances;
	protected ArrayList<ItemStack> rolled_outputs;
	
	public MachineOperationChance(ArrayList<ItemStack> inputs, ArrayList<ItemStack> outputs,
			ArrayList<FluidStack> fluid_inputs, ArrayList<FluidStack> fluid_outputs, int stackMultiplier, double[] itemChances) {
		super(inputs, outputs, fluid_inputs, fluid_outputs, stackMultiplier);
		this.itemChances = itemChances;
		rolled_outputs = new ArrayList<>(outputs.size());
		outputs.forEach(o -> rolled_outputs.add(o.copy()));
	}
	
	public void setTile(TileEntity e) {
		this.tile=e;
	}
	
	protected void updateChanceRolls() {
		for(int i=0; i<itemChances.length; i++) {
			double amount = outputs.get(i).getCount()*itemChances[i]*this.stackMultiplier;
		
			int fixed=0;
			while(amount>1f) {
				amount-=1f;
				fixed++;
			}
			if(tile.getWorld().rand.nextDouble()<=amount) {
				fixed++;
			}
			rolled_outputs.get(i).setCount(fixed);
		}
	}
	
	@Override
	public MachineOperation setStackMultiplier(int mult) {
		super.setStackMultiplier(mult);
		updateChanceRolls();
		return this;
	}

	@Override
	public ArrayList<ItemStack> getOutputs() {
		return rolled_outputs;
	}

	@Override
	public ArrayList<ItemStack> getOutputsWithMult() {
		return rolled_outputs;
	}

}
