package techguns.blocks;

import net.minecraft.block.Block;
import net.minecraft.item.ItemBlock;
import techguns.Techguns;

public class GenericItemBlock extends ItemBlock {

	public GenericItemBlock(Block block) {
		super(block);
		this.setRegistryName(block.getRegistryName());
		this.setTranslationKey(block.getTranslationKey());
		setCreativeTab(Techguns.tabTechgun);
	}

}
