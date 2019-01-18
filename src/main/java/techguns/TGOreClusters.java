package techguns;

import java.util.ArrayList;
import java.util.HashMap;

import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
import net.minecraft.util.WeightedRandom;
import net.minecraft.world.World;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.oredict.OreDictionary;
import techguns.blocks.EnumOreClusterType;
import techguns.blocks.EnumOreType;
import techguns.init.ITGInitializer;
import techguns.tileentities.operation.MachineOperation;

public class TGOreClusters implements ITGInitializer{
	protected HashMap<EnumOreClusterType, OreCluster> registry = new HashMap<>();
	
	public OreCluster getClusterForType(EnumOreClusterType type) {
		return registry.get(type);
	}
	
	@Override
	public void preInit(FMLPreInitializationEvent event) {
		registry.put(EnumOreClusterType.COAL, new OreCluster(0, 1d, 1d));
		registry.put(EnumOreClusterType.COMMON_METAL, new OreCluster(0, 1d, 1.2d));
		registry.put(EnumOreClusterType.RARE_METAL, new OreCluster(1, 0.8d, 1.5d));
		registry.put(EnumOreClusterType.SHINY_METAL, new OreCluster(2, 0.5d, 2.0d));
		registry.put(EnumOreClusterType.URANIUM, new OreCluster(3, 0.1d, 2d));
		registry.put(EnumOreClusterType.COMMON_GEM, new OreCluster(1, 0.8d, 1.2d));
		registry.put(EnumOreClusterType.SHINY_GEM, new OreCluster(3, 0.2d, 2d));
		registry.put(EnumOreClusterType.NETHER_CRYSTAL, new OreCluster(2, 0.5d, 1.5d));
	}

	public void RecipeInit() {
		this.addOreToCluster(new ItemStack(Blocks.COAL_ORE), EnumOreClusterType.COAL, 99);
		this.addOreToCluster(new ItemStack(Items.DIAMOND), EnumOreClusterType.COAL, 1);
		
		this.addOreToCluster(new ItemStack(Blocks.IRON_ORE), EnumOreClusterType.COMMON_METAL, 45);
		this.addOreToCluster(new ItemStack(TGBlocks.TG_ORE,1,EnumOreType.ORE_COPPER.ordinal()), EnumOreClusterType.COMMON_METAL, 30);
		this.addOreToCluster(new ItemStack(TGBlocks.TG_ORE,1,EnumOreType.ORE_TIN.ordinal()), EnumOreClusterType.COMMON_METAL, 25);
		
		this.addOreToCluster(new ItemStack(TGBlocks.TG_ORE, 1, EnumOreType.ORE_LEAD.ordinal()), EnumOreClusterType.RARE_METAL, 50);
		
		this.addOreToCluster(new ItemStack(TGBlocks.TG_ORE, 1, EnumOreType.ORE_TITANIUM.ordinal()), EnumOreClusterType.SHINY_METAL, 20);
		this.addOreToCluster(new ItemStack(Blocks.GOLD_ORE), EnumOreClusterType.SHINY_METAL, 40);
		
		this.addOreToCluster(new ItemStack(Blocks.REDSTONE_ORE), EnumOreClusterType.COMMON_GEM, 40);
		this.addOreToCluster(new ItemStack(Blocks.LAPIS_ORE), EnumOreClusterType.COMMON_GEM, 20);
		
		this.addOreToCluster(new ItemStack(Blocks.DIAMOND_ORE), EnumOreClusterType.SHINY_GEM, 40);
		this.addOreToCluster(new ItemStack(Blocks.EMERALD_ORE), EnumOreClusterType.SHINY_GEM, 20);
		
		this.addOreToCluster(new ItemStack(TGBlocks.TG_ORE,1,EnumOreType.ORE_URANIUM.ordinal()), EnumOreClusterType.URANIUM, 50);
		
		this.addOreToCluster(new ItemStack(Blocks.QUARTZ_ORE), EnumOreClusterType.NETHER_CRYSTAL, 50);
		this.addOreToCluster(new ItemStack(Blocks.GLOWSTONE), EnumOreClusterType.NETHER_CRYSTAL, 40);
		this.addOreToCluster(new ItemStack(Items.BLAZE_ROD), EnumOreClusterType.NETHER_CRYSTAL, 10);
		
		
		//add modded ores from oreDict
		this.addOreToCluster("oreSilver", EnumOreClusterType.SHINY_METAL, 40);
		
		this.addOreToCluster("oreOsmium", EnumOreClusterType.RARE_METAL, 50);
		this.addOreToCluster("oreAluminium", EnumOreClusterType.RARE_METAL, 50);
		
		this.addOreToCluster("oreCertusQuartz", EnumOreClusterType.COMMON_GEM, 40);
		this.addOreToCluster("oreChargedCertusQuartz", EnumOreClusterType.COMMON_GEM, 5);
	}
	
	@Override
	public void init(FMLInitializationEvent event) {

	}

	@Override
	public void postInit(FMLPostInitializationEvent event) {
		
	}

	public void addOreToCluster(ItemStack ore, EnumOreClusterType type, int weight) {
		OreCluster c = this.registry.get(type);
		if(c!=null) {
			OreClusterWeightedEntry entry = new OreClusterWeightedEntry(ore, weight);
			if(!c.oreEntries.contains(entry)) {
				c.oreEntries.add(entry);
			}
		}
	}
	
	public void addOreToCluster(FluidStack fluid, EnumOreClusterType type, int weight) {
		OreCluster c = this.registry.get(type);
		if(c!=null) {
			OreClusterWeightedEntry entry = new OreClusterWeightedEntry(fluid, weight);
			if(!c.oreEntries.contains(entry)) {
				c.oreEntries.add(entry);
			}
		}
	}
	
	public void addOreToCluster(String oredict, EnumOreClusterType type, int weight) {
		OreCluster c = this.registry.get(type);
		if(c!=null) {
			OreClusterWeightedEntry entry = new OreClusterWeightedEntry(oredict, weight);
			if(!c.oreEntries.contains(entry)) {
				c.oreEntries.add(entry);
			}
		}
	}
	
	public static class OreClusterWeightedEntry extends WeightedRandom.Item {
		ItemStack ore = ItemStack.EMPTY;
		FluidStack fluid= null;
		String oredictname=null;
		
		public OreClusterWeightedEntry(ItemStack ore, int itemWeightIn) {
			super(itemWeightIn);
			this.ore=ore;
		}
		
		public OreClusterWeightedEntry(FluidStack fluid, int itemWeightIn) {
			super(itemWeightIn);
			this.fluid=fluid;
		}
		
		public OreClusterWeightedEntry(String oredictname, int itemWeightIn) {
			super(itemWeightIn);
			NonNullList<ItemStack> ores = OreDictionary.getOres(oredictname);
			if(!ores.isEmpty()) {
				this.ore = ores.get(0).copy();
			}
		}

		@Override
		public boolean equals(Object obj) {
			if(obj instanceof OreClusterWeightedEntry) {
				OreClusterWeightedEntry other = (OreClusterWeightedEntry) obj;
				if(this.oredictname!=null && other.oredictname!=null) {
					return this.oredictname.equals(other.oredictname);
					
				} else if(!this.ore.isEmpty() && !other.ore.isEmpty()) {
					return this.ore.getItem()==other.ore.getItem();
					
				} else if(this.fluid!=null && other.fluid !=null) {
					return this.fluid.getFluid() == other.fluid.getFluid();
				}
			}
			return this==obj;
		}
		
		
	}
	
	public static class OreCluster {
		int mininglevel;
		double multiplier_amount;
		double multiplier_power;
		
		ArrayList<OreClusterWeightedEntry> oreEntries = new ArrayList<>();
		
		public OreCluster(int mininglevel, double multiplier_amount, double multiplier_power) {
			super();
			this.mininglevel = mininglevel;
			this.multiplier_amount = multiplier_amount;
			this.multiplier_power = multiplier_power;
		}

		public int getMininglevel() {
			return mininglevel;
		}

		public double getMultiplier_amount() {
			return multiplier_amount;
		}

		public double getMultiplier_power() {
			return multiplier_power;
		}
		
		public MachineOperation getNewOperation(World w, double orePerHour, int radius) {
			OreClusterWeightedEntry entry = WeightedRandom.getRandomItem(w.rand, oreEntries);
			
			MachineOperation op=null;
			
			if(!entry.ore.isEmpty()) {
				op= new MachineOperation(new ItemStack(entry.ore.getItem(),1,entry.ore.getItemDamage()), null, new ItemStack[0]);
			} else if (entry.fluid!=null){
				op= new MachineOperation(ItemStack.EMPTY, new FluidStack(entry.fluid.getFluid(),Fluid.BUCKET_VOLUME), new ItemStack[0]);
			} 
			if(op!=null) {
				op.setPowerPerTick((int)(80*orePerHour*Math.max(radius, 1)*TGConfig.oreDrillMultiplierPower));
				return op;
			}
			
			return getCobbleStoneOperation();
		}
		
		public MachineOperation getCobbleStoneOperation() {
			MachineOperation op = new MachineOperation(new ItemStack(Blocks.COBBLESTONE), new ItemStack[0]);
			op.setPowerPerTick((int) (240*TGConfig.oreDrillMultiplierPower));
			return op;
		}
	}
}
