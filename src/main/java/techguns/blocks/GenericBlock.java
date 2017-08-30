package techguns.blocks;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.item.ItemBlock;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import techguns.TGBlocks;
import techguns.Techguns;

public class GenericBlock extends Block {
	
	public GenericBlock(String name, Material mat, boolean addToItemList) {
		super(mat);
		this.setRegistryName(new ResourceLocation(Techguns.MODID,name));
		this.setUnlocalizedName(Techguns.MODID+"."+name);
		this.setCreativeTab(Techguns.tabTechgun);
		this.setHardness(2.0f);
		
		if(addToItemList) {
			TGBlocks.BLOCKLIST.add(this);
		}
	}
	
	public GenericBlock(String name, Material mat) {
		this(name,mat,true);
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