package techguns.plugins.crafttweaker;

import java.util.Iterator;

import crafttweaker.CraftTweakerAPI;
import crafttweaker.IAction;
import crafttweaker.api.item.IItemStack;
import crafttweaker.api.liquid.ILiquidStack;
import crafttweaker.api.minecraft.CraftTweakerMC;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.FluidStack;
import stanhebben.zenscript.annotations.ZenClass;
import stanhebben.zenscript.annotations.ZenMethod;
import techguns.Techguns;
import techguns.blocks.EnumOreClusterType;
import techguns.tileentities.operation.MetalPressRecipes;
import techguns.tileentities.operation.MetalPressRecipes.MetalPressRecipe;
import techguns.util.ItemStackOreDict;
import techguns.util.ItemUtil;

@ZenClass("mods.techguns.OreCluster")
public class OreClusterTweaker {

	
	@ZenMethod
	public static void addOre(String clustertype, IItemStack ore, int weight){
		CraftTweakerAPI.apply(new addInputAction(clustertype, ore, weight));
	}
	
	@ZenMethod
	public static void addFluid(String clustertype, ILiquidStack fluid, int weight){
		CraftTweakerAPI.apply(new addInputAction(clustertype, fluid, weight));
	}
	
	@ZenMethod
	public static void removeOre(String clustertype, IItemStack ore){
		CraftTweakerAPI.apply(new removeInputAction(clustertype, ore));
	}
	
	@ZenMethod
	public static void removeFluid(String clustertype, ILiquidStack fluid){
		CraftTweakerAPI.apply(new removeInputAction(clustertype, fluid));
	}
	
	protected static class addInputAction implements IAction
	{

		protected ItemStack ore;
		protected FluidStack fluid;
		protected EnumOreClusterType cluster;
		protected int weight;
		
		public addInputAction(String cluster, IItemStack ore, int weight) {
			super();
			this.cluster= EnumOreClusterType.getFromString(cluster);
			this.ore = CraftTweakerMC.getItemStack(ore);
			this.fluid=null;
			this.weight=weight;
		}
		
		public addInputAction(String cluster, ILiquidStack fluid, int weight) {
			super();
			this.cluster= EnumOreClusterType.getFromString(cluster);
			this.fluid = CraftTweakerMC.getLiquidStack(fluid);
			this.ore=ItemStack.EMPTY;
			this.weight=weight;
		}
		
		@Override
		public void apply() {
			if(cluster!=null) {
				if(!this.ore.isEmpty()) {
					Techguns.orecluster.addOreToCluster(ore, cluster, weight);
				} else if(this.fluid!=null) {
					Techguns.orecluster.addOreToCluster(fluid, cluster, weight);
				}
			}
		}
		
		@Override
		public String describe() {
			if(this.cluster!=null) {
				if(!this.ore.isEmpty()) {
					return "Adding "+ore+" to Cluster "+cluster+" with weight "+weight;
				} else if(this.fluid!=null){
					return "Adding "+fluid+" to Cluster "+cluster+" with weight "+weight;
				}
			}
			return "Failed to add ore to Cluster";
		}
	}
	
	protected static class removeInputAction implements IAction
	{

		protected ItemStack ore;
		protected FluidStack fluid;
		protected EnumOreClusterType cluster;
		
		public removeInputAction(String cluster, IItemStack ore) {
			super();
			this.cluster= EnumOreClusterType.getFromString(cluster);
			this.ore = CraftTweakerMC.getItemStack(ore);
			this.fluid=null;
		}
		
		public removeInputAction(String cluster, ILiquidStack fluid) {
			super();
			this.cluster= EnumOreClusterType.getFromString(cluster);
			this.ore = ItemStack.EMPTY;
			this.fluid=CraftTweakerMC.getLiquidStack(fluid);
		}
		
		@Override
		public void apply() {
			if(this.cluster!=null) {
				if(!this.ore.isEmpty()) {
					Techguns.orecluster.removeOreFromCluster(ore, cluster);
				} else if (this.fluid!=null) {
					Techguns.orecluster.removeOreFromCluster(fluid, cluster);
				}
			}
		}
		
		@Override
		public String describe() {
			if(this.cluster!=null) {
				if(!this.ore.isEmpty()) {
					Techguns.orecluster.removeOreFromCluster(ore, cluster);
					return "Removing "+ore+" from Cluster "+cluster;
				} else if (this.fluid!=null) {
					Techguns.orecluster.removeOreFromCluster(fluid, cluster);
					return "Removing "+fluid+" from Cluster "+cluster;
				}
			}
			return "Failed to remove ore from Cluster";
		}
		
		
	}
	
}
