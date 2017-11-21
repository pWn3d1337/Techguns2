package techguns.blocks;

import net.minecraft.block.SoundType;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;

public class BlockTGSandHard extends GenericBlockMetaEnum<EnumTGSandHardTypes> {

	public BlockTGSandHard(String name, Class<EnumTGSandHardTypes> clazz) {
		super(name, Material.SAND, MapColor.SAND, SoundType.SAND, clazz);
		this.setHardness(3.0f);
		for(int i=0;i<clazz.getEnumConstants().length;i++) {
			this.setHarvestLevel("shovel", 0, this.getStateFromMeta(i));
		}
	}

}
