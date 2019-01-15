package techguns;

import java.util.HashMap;

import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import techguns.blocks.EnumOreClusterType;
import techguns.init.ITGInitializer;
import techguns.tileentities.operation.MachineOperation;

public class TGOreClusters implements ITGInitializer{
	protected HashMap<EnumOreClusterType, OreCluster> registry = new HashMap<>();
	
	public OreCluster getClusterForType(EnumOreClusterType type) {
		return registry.get(type);
	}
	
	@Override
	public void preInit(FMLPreInitializationEvent event) {
		
	}

	@Override
	public void init(FMLInitializationEvent event) {
		
	}

	@Override
	public void postInit(FMLPostInitializationEvent event) {
		
	}

	public static class OreCluster {
		int mininglevel;
		double multiplier_amount;
		double multiplier_power;
		
		public int getMininglevel() {
			return mininglevel;
		}

		public double getMultiplier_amount() {
			return multiplier_amount;
		}

		public double getMultiplier_power() {
			return multiplier_power;
		}
		
		public MachineOperation getNewOperation(World w, double orePerHour) {
			//w.rand;
			return new MachineOperation(ItemStack.EMPTY, new FluidStack(TGFluids.WATER, 0), new ItemStack[0]);
		}
		
		public MachineOperation getCobbleStoneOperation() {
			MachineOperation op = new MachineOperation(new ItemStack(Blocks.COBBLESTONE), new ItemStack[0]);
			op.setPowerPerTick((int) (240*TGConfig.oreDrillMultiplierPower));
			return op;
		}
	}
}
