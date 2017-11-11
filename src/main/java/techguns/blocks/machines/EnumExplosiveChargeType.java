package techguns.blocks.machines;

import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.EnumBlockRenderType;
import net.minecraft.util.IStringSerializable;
import techguns.api.machines.IMachineType;
import techguns.tileentities.ExplosiveChargeAdvTileEnt;
import techguns.tileentities.ExplosiveChargeTileEnt;

public enum EnumExplosiveChargeType implements IStringSerializable, IMachineType {
	TNT,
	ADVANCED;

	@Override
	public String getName() {
		return name().toLowerCase();
	}

	@Override
	public int getIndex() {
		return this.ordinal();
	}

	@Override
	public int getMaxMachineIndex() {
		return 1;
	}

	@Override
	public TileEntity getTile() {
		if(this==TNT) {
			return new ExplosiveChargeTileEnt();
		} else {
			return new ExplosiveChargeAdvTileEnt();
		}
	}

	@Override
	public Class getTileClass() {
		if(this==TNT) {
			return ExplosiveChargeTileEnt.class;
		} else {
			return ExplosiveChargeAdvTileEnt.class;
		}
	}

	@Override
	public EnumBlockRenderType getRenderType() {
		return EnumBlockRenderType.MODEL;
	}

	@Override
	public boolean isFullCube() {
		return false;
	}

	@Override
	public BlockRenderLayer getBlockRenderLayer() {
		return BlockRenderLayer.SOLID;
	}

}
