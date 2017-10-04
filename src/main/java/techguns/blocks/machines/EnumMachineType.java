package techguns.blocks.machines;

import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.EnumBlockRenderType;
import net.minecraft.util.IStringSerializable;
import techguns.api.machines.IMachineType;
import techguns.tileentities.AmmoPressTileEnt;
import techguns.tileentities.ChemLabTileEnt;
import techguns.tileentities.DungeonGeneratorTileEnt;
import techguns.tileentities.DungeonScannerTileEnt;
import techguns.tileentities.MetalPressTileEnt;
import techguns.tileentities.TurretTileEnt;

public enum EnumMachineType implements IStringSerializable, IMachineType<EnumMachineType> {
	AMMO_PRESS(0,AmmoPressTileEnt.class,false,EnumBlockRenderType.ENTITYBLOCK_ANIMATED),
	METAL_PRESS(1,MetalPressTileEnt.class,false,EnumBlockRenderType.ENTITYBLOCK_ANIMATED),
	CHEM_LAB(2,ChemLabTileEnt.class,false,EnumBlockRenderType.ENTITYBLOCK_ANIMATED),
	TURRET(3,TurretTileEnt.class,false,EnumBlockRenderType.ENTITYBLOCK_ANIMATED),
	DUNGEON_SCANNER(4,DungeonScannerTileEnt.class,true,EnumBlockRenderType.MODEL),
	DUNGEON_GENERATOR(5,DungeonGeneratorTileEnt.class,true,EnumBlockRenderType.MODEL);

	/*
	EXPLOSIVE_CHARGE(6,"explosivecharge"),
	CAMP_FLAG(7,"campflag"),
	EXPLOSIVE_CHARGE_ADVANCED(8,"explosivechargeadvanced"),
	CHARGING_STATION(9,"chargingstation");*/

	private int id;
	private String name;
	private Class<? extends TileEntity> tile;
	private boolean isFullCube;
	private EnumBlockRenderType renderType;
	private BlockRenderLayer renderLayer;
	
	EnumMachineType(int id, Class<? extends TileEntity> tile, boolean isFullCube, EnumBlockRenderType renderType) {
		this(id,tile,isFullCube,renderType,BlockRenderLayer.SOLID);
	}
	EnumMachineType(int id, Class<? extends TileEntity> tile, boolean isFullCube, EnumBlockRenderType renderType, BlockRenderLayer layer) {
		this.id=id;
		this.name=this.name().toLowerCase();
		this.tile = tile;
		this.isFullCube=isFullCube;
		this.renderType=renderType;
		this.renderLayer=layer;
	}
	
	@Override
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
	public EnumBlockRenderType getRenderType() {
		return this.renderType;
	}

	@Override
	public boolean isFullCube() {
		return this.isFullCube;
	}
	@Override
	public BlockRenderLayer getBlockRenderLayer() {
		return this.renderLayer;
	}
}
