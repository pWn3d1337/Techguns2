package techguns.tileentities.operation;

import net.minecraftforge.fluids.FluidEvent;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidTank;
import techguns.tileentities.BasicInventoryTileEnt;

public class FluidTankPlus extends FluidTank {

	BasicInventoryTileEnt tile;
	
	public FluidTankPlus(BasicInventoryTileEnt tile, int capacity) {
		super(capacity);
		this.tile=tile;
	}

	@Override
	protected void onContentsChanged() {
		super.onContentsChanged();
		this.tile.setContentsChanged(true);
	}
	
	/**
	 * mostly copied from superclass, call onContentsChanged only when they actually change
     */
	@Override
    public int fillInternal(FluidStack resource, boolean doFill)
    {
        if (resource == null || resource.amount <= 0)
        {
            return 0;
        }

        if (!doFill)
        {
            if (fluid == null)
            {
                return Math.min(capacity, resource.amount);
            }

            if (!fluid.isFluidEqual(resource))
            {
                return 0;
            }

            return Math.min(capacity - fluid.amount, resource.amount);
        }

        if (fluid == null)
        {
            fluid = new FluidStack(resource, Math.min(capacity, resource.amount));

            onContentsChanged();

            if (tile != null)
            {
                FluidEvent.fireEvent(new FluidEvent.FluidFillingEvent(fluid, tile.getWorld(), tile.getPos(), this, fluid.amount));
            }
            return fluid.amount;
        }

        if (!fluid.isFluidEqual(resource))
        {
            return 0;
        }
        int filled = capacity - fluid.amount;

        if (resource.amount < filled)
        {
            fluid.amount += resource.amount;
            filled = resource.amount;
        }
        else
        {
            fluid.amount = capacity;
        }
        
        if (filled>0) {
        	onContentsChanged();
        }
        
        if (tile != null)
        {
            FluidEvent.fireEvent(new FluidEvent.FluidFillingEvent(fluid, tile.getWorld(), tile.getPos(), this, filled));
        }
        return filled;
    }
}
