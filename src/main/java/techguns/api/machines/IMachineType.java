package techguns.api.machines;

import net.minecraft.block.SoundType;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.EnumBlockRenderType;

public interface IMachineType<E extends Enum<E>> {
	public int getIndex();
	public int getMaxMachineIndex();
	public TileEntity getTile();
	public Class<? extends TileEntity> getTileClass();
	public EnumBlockRenderType getRenderType();
	public boolean isFullCube();
	public BlockRenderLayer getBlockRenderLayer();
	public default SoundType getSoundType() {
		return SoundType.METAL;
	}
}
