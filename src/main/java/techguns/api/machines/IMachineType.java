package techguns.api.machines;

import net.minecraft.block.SoundType;
import net.minecraft.block.state.IBlockState;
import net.minecraft.item.Item;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.EnumBlockRenderType;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public interface IMachineType<E extends Enum<E>> {
	public int getIndex();
	public int getMaxMachineIndex();
	public TileEntity getTile();
	public Class<? extends TileEntity> getTileClass();
	public EnumBlockRenderType getRenderType();
	public boolean isFullCube();
	public BlockRenderLayer getBlockRenderLayer();
	public boolean debugOnly();
	public default boolean hideInCreative() {
		return false;
	}
	public default SoundType getSoundType() {
		return SoundType.METAL;
	}
	
	public default boolean hasCustomModelLocation() {
		return false;
	}
	
	@SideOnly(Side.CLIENT)
	public default void setCustomModelLocation(Item itemblock, int meta, ResourceLocation registryName, IBlockState state) {
		//do nothing
	}
}
