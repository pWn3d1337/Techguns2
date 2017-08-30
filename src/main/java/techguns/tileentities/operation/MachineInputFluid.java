package techguns.tileentities.operation;

import net.minecraftforge.fluids.FluidStack;

public class MachineInputFluid implements IMachineInput<FluidStack>{

	FluidStack target;
	
	public MachineInputFluid(FluidStack target) {
		super();
		this.target = target;
	}

	@Override
	public boolean matches(FluidStack other) {
		return other.containsFluid(target);
	}

}
