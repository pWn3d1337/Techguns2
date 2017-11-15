package techguns.blocks;

import net.minecraft.block.SoundType;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.BlockFaceShape;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.storage.loot.LootTableList;
import techguns.Techguns;

public class BlockMilitaryCrate extends GenericBlockMetaEnum<EnumMilitaryCrateType>{

	protected static final ResourceLocation loottable_ammo = new ResourceLocation(Techguns.MODID, "blocks/military_crate_ammo");
	protected static final ResourceLocation loottable_gun = new ResourceLocation(Techguns.MODID, "blocks/military_crate_gun");
	protected static final ResourceLocation loottable_armor = new ResourceLocation(Techguns.MODID, "blocks/military_crate_armor");
	protected static final ResourceLocation loottable_medical = new ResourceLocation(Techguns.MODID, "blocks/military_crate_medical");
	protected static final ResourceLocation loottable_explosives = new ResourceLocation(Techguns.MODID, "blocks/military_crate_explosives");
	protected static final ResourceLocation loottable_generic = new ResourceLocation(Techguns.MODID, "blocks/military_crate_generic");
	
	protected static final AxisAlignedBB boundingbox = new AxisAlignedBB(0.03125, 0, 0.03125, 0.96875, 1, 0.96875);
	
	static {
		LootTableList.register(loottable_ammo);
		LootTableList.register(loottable_gun);
		LootTableList.register(loottable_armor);
		LootTableList.register(loottable_medical);
		LootTableList.register(loottable_explosives);
		LootTableList.register(loottable_generic);
	}
	
	public BlockMilitaryCrate(String name, Material mat) {
		super(name, mat, EnumMilitaryCrateType.class);
	}

	public BlockMilitaryCrate(String name, Material mat, MapColor mc, SoundType soundType) {
		super(name, mat, mc, soundType, EnumMilitaryCrateType.class);
	}

	@Override
	public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos) {
		return boundingbox;
	}

	@Override
	public boolean isFullBlock(IBlockState state) {
		return false;
	}

	@Override
	public boolean isOpaqueCube(IBlockState state) {
		return false;
	}

	@Override
	public boolean isNormalCube(IBlockState state, IBlockAccess world, BlockPos pos) {
		return false;
	}

	public ResourceLocation getLootableForState(IBlockState state) {
		switch(state.getValue(this.TYPE)) {
		case AMMO:
			return loottable_ammo;
		case ARMOR:
			return loottable_armor;
		case EXPLOSIVE:
			return loottable_explosives;
		case GUN:
			return loottable_gun;
		case MEDICAL:
			return loottable_medical;
		default:
			return loottable_generic;
		}
	}
	
	@Override
	public BlockFaceShape getBlockFaceShape(IBlockAccess worldIn, IBlockState state, BlockPos pos, EnumFacing face) {
		if (face==EnumFacing.DOWN || face==EnumFacing.UP) {
			return BlockFaceShape.CENTER_BIG;
		}
		return BlockFaceShape.UNDEFINED;
	}
}
