package techguns;

import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import techguns.init.ITGInitializer;

public class TGFluids implements ITGInitializer {
	
	/**
	 * Same as FluidRegistry.WATER
	 */
	public static Fluid WATER;
	/**
	 * Same as FluidRegistry.LAVA
	 */
	public static Fluid LAVA;
	
	public static Fluid MILK;
	
	public static Fluid OIL;
	
	public static Fluid FUEL;
	
	public static Fluid LIQUID_REDSTONE;
	
	public static Fluid LIQUID_COAL;

	public static Fluid ACID;
	
	public static Fluid LIQUID_ENDER;
	
	public static Fluid BIOFUEL;

	public static boolean addedMilk=false;

	@Override
	public void preInit(FMLPreInitializationEvent event) {
		WATER=FluidRegistry.WATER;
		LAVA=FluidRegistry.LAVA;
		
//		ACID = new Fluid("creeper_acid").setGaseous(false).setLuminosity(0).setUnlocalizedName("creeperAcid").setDensity(100);
		//Registered in TGBlocks after BlockFluid
	}

	@Override
	public void init(FMLInitializationEvent event) {

	}

	@Override
	public void postInit(FMLPostInitializationEvent event) {
		MILK = FluidRegistry.getFluid("milk");
		
		/*	if (MILK==null){
				MILK = new Fluid("milk").setGaseous(false).setDensity(1050).setViscosity(1050).setUnlocalizedName("milk");
				addedMilk=true;
				FluidRegistry.registerFluid(MILK);
				FluidContainerRegistry.registerFluidContainer(FluidRegistry.getFluidStack("milk", FluidContainerRegistry.BUCKET_VOLUME), new ItemStack(Items.milk_bucket), new ItemStack(Items.bucket));
			}	*/
			
			OIL = FluidRegistry.getFluid("oil");
			FUEL = FluidRegistry.getFluid("fuel");

			LIQUID_REDSTONE = FluidRegistry.getFluid("redstone");
			if (LIQUID_REDSTONE==null){
		//		System.out.println("No Liquid Redstone, fallback to lava");
				LIQUID_REDSTONE = LAVA;
			}
			
			LIQUID_COAL = FluidRegistry.getFluid("coal");
			if (LIQUID_COAL==null){
		//		System.out.println("No Liquid coal, fallback to oil/water");
				
				if ( OIL!=null){
					LIQUID_COAL = OIL;
				}
				
				LIQUID_COAL = WATER;
			}
			
			LIQUID_ENDER = FluidRegistry.getFluid("ender");
			if (LIQUID_ENDER==null){
//				System.out.println("No Liquid ender, fallback to lava");
				LIQUID_ENDER = LAVA;
			}
			
			BIOFUEL = FluidRegistry.getFluid("biofuel");
			if (BIOFUEL==null){
		//		System.out.println("No Biofuel, fallback to lava");
				BIOFUEL = LAVA;
			}
	}
}
