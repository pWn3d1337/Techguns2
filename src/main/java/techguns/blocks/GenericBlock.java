package techguns.blocks;

import java.util.Arrays;

import net.minecraft.block.Block;
import net.minecraft.block.BlockDirectional;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyDirection;
import net.minecraft.item.ItemBlock;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import techguns.TGBlocks;
import techguns.Techguns;

public class GenericBlock extends Block {
	
	public static final PropertyDirection FACING_ALL = BlockDirectional.FACING;
	
	public GenericBlock(String name, Material mat, MapColor mc, boolean addToItemList) {
		super(mat, mc);
		this.setRegistryName(new ResourceLocation(Techguns.MODID,name));
		this.setUnlocalizedName(Techguns.MODID+"."+name);
		this.setCreativeTab(Techguns.tabTechgun);
		this.setHardness(2.0f);
		
		if(addToItemList) {
			TGBlocks.BLOCKLIST.add(this);
		}
	}
	
	public GenericBlock(String name, Material mat) {
		this(name,mat,mat.getMaterialMapColor(),true);
	}
	
	public GenericBlock(String name, Material mat, boolean addToItemList) {
		this(name,mat,mat.getMaterialMapColor(),addToItemList);
	}
	
	public GenericBlock(String name, Material mat, MapColor mc) {
		this(name,mat,mc,true);
	}
	
	/**
	 * return the correct itemblock for initialization
	 */
	public ItemBlock createItemBlock() {
		return new GenericItemBlock(this);
	}
	
	public void registerBlock(RegistryEvent.Register<Block> event) {
		event.getRegistry().register(this);
	}
	
	@SideOnly(Side.CLIENT)
	public void registerItemBlockModels() {
		
	}
}