package techguns.blocks.machines;

import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.EnumBlockRenderType;
import net.minecraft.util.IStringSerializable;
import techguns.api.machines.IMachineType;
import techguns.tileentities.BasicInventoryTileEnt;
import techguns.tileentities.CamoBenchTileEnt;
import techguns.tileentities.RepairBenchTileEnt;
import techguns.tileentities.ChargingStationTileEnt;

public enum EnumSimpleMachineType implements IStringSerializable, IMachineType<EnumSimpleMachineType> {
	CAMO_BENCH(0, CamoBenchTileEnt.class,true,EnumBlockRenderType.MODEL),
	REPAIR_BENCH(1, RepairBenchTileEnt.class,true,EnumBlockRenderType.MODEL),
	CHARGING_STATION(2, ChargingStationTileEnt.class, false, EnumBlockRenderType.MODEL);
	
	private int id;
	private String name;
	private Class<? extends TileEntity> tile;
	private boolean isFullCube;
	private EnumBlockRenderType renderType;
	private BlockRenderLayer renderLayer;
	
	private EnumSimpleMachineType(int id, Class<? extends TileEntity> tile, boolean isFullCube, EnumBlockRenderType renderType) {
		this(id,tile,isFullCube,renderType, BlockRenderLayer.SOLID);
	}
	
	
	private EnumSimpleMachineType(int id, Class<? extends TileEntity> tile, boolean isFullCube, EnumBlockRenderType renderType, BlockRenderLayer layer) {
		this.id=id;
		this.name=this.name().toLowerCase();
		this.tile = tile;
		this.isFullCube=isFullCube;
		this.renderType=renderType;
		this.renderLayer=layer;
	}
	
	public int getIndex() {
		return id;
	}
	
	@Override
	public String getName() {
		return this.name;
	}

	@Override
	public int getMaxMachineIndex() {
		return this.values().length;
	}

	@Override
	public TileEntity getTile() {
		try {
			return this.tile.newInstance();
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	@Override
	public Class<? extends TileEntity> getTileClass() {
		return this.tile;
	}

	@Override
	public boolean isFullCube() {
		return isFullCube;
	}

	@Override
	public EnumBlockRenderType getRenderType() {
		return renderType;
	}

	@Override
	public BlockRenderLayer getBlockRenderLayer() {
		return this.renderLayer;
	}
	
}
