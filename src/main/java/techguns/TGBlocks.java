package techguns;

import java.util.ArrayList;
import java.util.function.Predicate;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import techguns.blocks.BlockBioblob;
import techguns.blocks.BlockSandbags;
import techguns.blocks.BlockTGLadder;
import techguns.blocks.BlockTGLamp;
import techguns.blocks.BlockTGMetalPanel;
import techguns.blocks.BlockTGOre;
import techguns.blocks.EnumConcreteType;
import techguns.blocks.EnumLadderType;
import techguns.blocks.EnumLampType;
import techguns.blocks.GenericBlock;
import techguns.blocks.GenericBlockMetaEnum;
import techguns.blocks.GenericItemBlock;
import techguns.blocks.machines.BasicMachine;
import techguns.blocks.machines.EnumMachineType;
import techguns.blocks.machines.EnumMultiBlockMachineType;
import techguns.blocks.machines.EnumSimpleMachineType;
import techguns.blocks.machines.MultiBlockMachine;
import techguns.blocks.machines.SimpleMachine;
import techguns.init.ITGInitializer;
import techguns.items.GenericItem;
import techguns.tools.BlockJsonCreator;
import techguns.tools.ItemJsonCreator;

public class TGBlocks implements ITGInitializer{
	public static final ArrayList<GenericBlock> BLOCKLIST = new ArrayList<>();
	
	//Machines
	public static BasicMachine<EnumMachineType> BASIC_MACHINE;
	
	public static SimpleMachine<EnumSimpleMachineType> SIMPLE_MACHINE;
	
	public static MultiBlockMachine<EnumMultiBlockMachineType> MULTIBLOCK_MACHINE;
	
	public static BlockTGOre TG_ORE;
	
	public static BlockBioblob BIOBLOB;
	
	public static BlockSandbags SANDBAGS;
	
	public static BlockTGLamp<EnumLampType> LAMP_0;
	
	public static BlockTGMetalPanel METAL_PANEL;
	public static BlockTGLadder<EnumLadderType> LADDER_0;
	
	public static GenericBlockMetaEnum<EnumConcreteType> CONCRETE;
	
	public void registerBlocks(RegistryEvent.Register<Block> event) {
		BLOCKLIST.forEach(b -> b.registerBlock(event));
	}
	
	public void registerItems(RegistryEvent.Register<Item> event) {
		BLOCKLIST.forEach(b -> event.getRegistry().register(b.createItemBlock()));
	}
	
	@SideOnly(Side.CLIENT)
	public static void initModels() {
		BLOCKLIST.forEach(b -> b.registerItemBlockModels());
	}
	
	@Override
	public void preInit(FMLPreInitializationEvent event) {
		BASIC_MACHINE= new BasicMachine<EnumMachineType>("basicmachine",EnumMachineType.class);
		SIMPLE_MACHINE = new SimpleMachine<EnumSimpleMachineType>("simplemachine",EnumSimpleMachineType.class);
		MULTIBLOCK_MACHINE = new MultiBlockMachine<EnumMultiBlockMachineType>("multiblockmachine", EnumMultiBlockMachineType.class);
		TG_ORE = new BlockTGOre("basicore");
		BIOBLOB= new BlockBioblob("bioblob");
		SANDBAGS = new BlockSandbags("sandbags");
		LAMP_0 = new BlockTGLamp<EnumLampType>("lamp0", EnumLampType.class);
		METAL_PANEL = new BlockTGMetalPanel("metalpanel");
		CONCRETE = new GenericBlockMetaEnum<>("concrete", Material.ROCK, EnumConcreteType.class);
		LADDER_0 = new BlockTGLadder<EnumLadderType>("ladder0", EnumLadderType.class);
		
		
		if(TGItems.WRITE_ITEM_JSON && event.getSide()==Side.CLIENT){
			BLOCKLIST.stream().filter(new Predicate<GenericBlock>() {
				@Override
				public boolean test(GenericBlock t) {
					return t instanceof GenericBlockMetaEnum;
				}
			}).forEach(b -> BlockJsonCreator.writeBlockstateJsonFileForBlock((GenericBlockMetaEnum)b));			
		}
		
	}

	@Override
	public void init(FMLInitializationEvent event) {

	}

	@Override
	public void postInit(FMLPostInitializationEvent event) {

	}


}
