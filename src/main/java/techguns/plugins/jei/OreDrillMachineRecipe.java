package techguns.plugins.jei;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
import net.minecraft.util.WeightedRandom;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidStack;
import techguns.TGBlocks;
import techguns.TGFluids;
import techguns.TGItems;
import techguns.Techguns;
import techguns.TGOreClusters.OreClusterWeightedEntry;
import techguns.blocks.EnumOreClusterType;
import techguns.tileentities.operation.IMachineRecipe;

public class OreDrillMachineRecipe implements IMachineRecipe {

	protected final OreClusterWeightedEntry entry;
	
	protected static List<ItemStack> furnaceFuels=null;
	protected static ArrayList<FluidStack>fuelList = null;
	protected static ArrayList<ItemStack>drills=null;
	protected EnumOreClusterType type;
	protected float chance;
	
	public OreDrillMachineRecipe(OreClusterWeightedEntry entry, EnumOreClusterType type, List<ItemStack> furnaceFuels) {
		super();
		this.entry = entry;
		this.type=type;
		this.furnaceFuels=furnaceFuels;
		
		int totalweight = WeightedRandom.getTotalWeight(Techguns.orecluster.getClusterForType(type).getOreEntries());
		this.chance = (1.0f*entry.itemWeight) / (totalweight*1.0f); 
	}

	@Override
	public List<List<ItemStack>> getItemInputs() {
		 List<List<ItemStack>> list = IMachineRecipe.super.getItemInputs();
		 	list.add(getDrills());
			list.add(NonNullList.withSize(1, new ItemStack(TGBlocks.ORE_CLUSTER,1,TGBlocks.ORE_CLUSTER.getMetaFromState(TGBlocks.ORE_CLUSTER.getDefaultState().withProperty(TGBlocks.ORE_CLUSTER.TYPE, type)))));
			list.add(furnaceFuels);
		return list;
	}

	@Override
	public List<List<ItemStack>> getItemOutputs() {
		List<List<ItemStack>> list = IMachineRecipe.super.getItemOutputs();
		if(!entry.getOre().isEmpty()) {
			List<ItemStack> ore = NonNullList.withSize(1, entry.getOre());
			list.add(ore);
		}
		return list;
	}

	public EnumOreClusterType getType() {
		return type;
	}

	protected ArrayList<ItemStack> getDrills(){
		if(drills==null) {
			drills = new ArrayList<ItemStack>(9);
			drills.add(TGItems.newStack(TGItems.OREDRILLHEAD_STEEL, 1));
			drills.add(TGItems.newStack(TGItems.OREDRILLHEAD_OBSIDIANSTEEL, 1));
			drills.add(TGItems.newStack(TGItems.OREDRILLHEAD_CARBON, 1));
			drills.add(TGItems.newStack(TGItems.OREDRILLHEAD_MEDIUM_STEEL, 1));
			drills.add(TGItems.newStack(TGItems.OREDRILLHEAD_MEDIUM_OBSIDIANSTEEL, 1));
			drills.add(TGItems.newStack(TGItems.OREDRILLHEAD_MEDIUM_CARBON, 1));
			drills.add(TGItems.newStack(TGItems.OREDRILLHEAD_LARGE_STEEL, 1));
			drills.add(TGItems.newStack(TGItems.OREDRILLHEAD_LARGE_OBSIDIANSTEEL, 1));
			drills.add(TGItems.newStack(TGItems.OREDRILLHEAD_LARGE_CARBON, 1));
		}
		return drills;
	}

	public float getChance() {
		return chance;
	}

	@Override
	public List<List<FluidStack>> getFluidInputs() {
		List<List<FluidStack>> list = IMachineRecipe.super.getFluidInputs();
			
		if(fuelList==null) {
			fuelList = new ArrayList<FluidStack>(TGFluids.fuels.size());
			for(Fluid f: TGFluids.fuels) {
				fuelList.add(new FluidStack(f,Fluid.BUCKET_VOLUME*16));
			}
		}
		list.add(fuelList);
		
		return list;
	}

	@Override
	public List<List<FluidStack>> getFluidOutputs() {
		List<List<FluidStack>> list = IMachineRecipe.super.getFluidOutputs();
		if(entry.getFluid()!=null) {
			List<FluidStack> f = NonNullList.withSize(1, entry.getFluid());
			list.add(f);
		}
		return list;
	}

}
