package techguns.blocks;

import net.minecraft.block.SoundType;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;

public class BlockTGLadder extends GenericBlock {

	public BlockTGLadder(String name) {
		super(name, Material.IRON);
		this.setSoundType(SoundType.METAL);
	}

}
