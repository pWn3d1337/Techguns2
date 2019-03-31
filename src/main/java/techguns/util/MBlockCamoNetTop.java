package techguns.util;

import net.minecraft.block.state.IBlockState;
import net.minecraft.util.math.BlockPos.MutableBlockPos;
import net.minecraft.world.World;
import techguns.TGBlocks;
import techguns.blocks.BlockTGCamoNet;
import techguns.blocks.BlockTGCamoNetTop;
import techguns.blocks.EnumCamoNetType;
import techguns.world.BlockRotator;
import techguns.world.EnumLootType;
import techguns.world.structures.WorldgenStructure.BiomeColorType;

public class MBlockCamoNetTop extends MBlock {

	protected static IBlockState WOODLAND = TGBlocks.CAMONET_TOP.getDefaultState();
	protected static IBlockState DESERT = TGBlocks.CAMONET_TOP.getDefaultState().withProperty(TGBlocks.CAMONET_TOP.TYPE, EnumCamoNetType.DESERT);
	protected static IBlockState SNOW = TGBlocks.CAMONET_TOP.getDefaultState().withProperty(TGBlocks.CAMONET_TOP.TYPE, EnumCamoNetType.SNOW);;
	
	public MBlockCamoNetTop() {
		super(WOODLAND);
	}

	@Override
	public void setBlock(World w, MutableBlockPos pos, int rotation, EnumLootType loottype, BiomeColorType biome) {
		if(pos.getY()>=1) {
			IBlockState targetState = BlockRotator.getRotatedHorizontal(getCamoTypeState(biome), rotation);
			w.setBlockState(pos, targetState,getPlacementFlags());
			if(this.hasTileEntity) {
				this.tileEntityPostPlacementAction(w, targetState, pos, rotation);
			}
		}
	}

	public IBlockState getCamoTypeState(BiomeColorType camo) {
		switch(camo) {
		case DESERT:
			return DESERT;
		case SNOW:
			return SNOW;
		case WOODLAND:
		default:
			return WOODLAND;
		}
	}
	
}
