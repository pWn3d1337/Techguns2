package techguns.blocks;

import net.minecraft.block.Block;
import net.minecraft.block.BlockDirectional;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyDirection;
import net.minecraft.item.ItemBlock;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class GenericBlock extends Block implements IGenericBlock {
	
	public static final PropertyDirection FACING_ALL = BlockDirectional.FACING;
	
	public GenericBlock(String name, Material mat, MapColor mc, boolean addToItemList) {
		super(mat, mc);
		this.init(this, name, addToItemList);
		this.setHardness(2.0f);
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
	@Override
	public ItemBlock createItemBlock() {
		return new GenericItemBlock(this);
	}
	
	@Override
	public void registerBlock(RegistryEvent.Register<Block> event) {
		event.getRegistry().register(this);
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public void registerItemBlockModels() {
		
	}
}