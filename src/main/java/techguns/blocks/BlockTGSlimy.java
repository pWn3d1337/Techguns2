package techguns.blocks;

import net.minecraft.block.SoundType;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;

public class BlockTGSlimy extends GenericBlockMetaEnum<EnumTGSlimyType> {

	public BlockTGSlimy(String name, Class<EnumTGSlimyType> clazz) {
		super(name, Material.GROUND, MapColor.GREEN, SoundType.SLIME, clazz);
		this.setHardness(4.0f);
		for(int i=0;i<clazz.getEnumConstants().length;i++) {
			this.setHarvestLevel("pickaxe", 0, this.getStateFromMeta(i));
		}
	}

	
}
