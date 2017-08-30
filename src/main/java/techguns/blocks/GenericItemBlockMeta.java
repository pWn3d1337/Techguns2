package techguns.blocks;

import net.minecraft.block.Block;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import techguns.Techguns;

public class GenericItemBlockMeta extends ItemBlock {

	public GenericItemBlockMeta(Block block) {
		super(block);
		this.setRegistryName(block.getRegistryName());
		this.setUnlocalizedName(block.getUnlocalizedName());
		setCreativeTab(Techguns.tabTechgun);
		
		this.setHasSubtypes(true);
		//this.setMaxDamage(0);
	}

	@Override
	public int getMetadata(int damage) {
		return damage;
	}

	@Override
	public String getUnlocalizedName(ItemStack stack) {
		return super.getUnlocalizedName(stack)+"."+stack.getItemDamage();
	}

	
}
