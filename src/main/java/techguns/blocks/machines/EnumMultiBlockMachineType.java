package techguns.blocks.machines;

import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.EnumBlockRenderType;
import net.minecraft.util.IStringSerializable;
import techguns.api.machines.IMachineType;
import techguns.tileentities.*;

public enum EnumMultiBlockMachineType implements IStringSerializable, IMachineType<EnumMultiBlockMachineType> {
		FABRICATOR_HOUSING(0,FabricatorTileEntSlave.class,true,EnumBlockRenderType.MODEL),
		FABRICATOR_GLASS(1,FabricatorTileEntSlave.class,false,EnumBlockRenderType.MODEL,BlockRenderLayer.CUTOUT),
		FABRICATOR_CONTROLLER(2,FabricatorTileEntMaster.class,true,EnumBlockRenderType.MODEL),
	
		REACTIONCHAMBER_HOUSING(3,ReactionChamberTileEntSlave.class,true,EnumBlockRenderType.MODEL),
		REACTIONCHAMBER_GLASS(4,ReactionChamberTileEntSlave.class,false,EnumBlockRenderType.MODEL,BlockRenderLayer.CUTOUT),
		REACTIONCHAMBER_CONTROLLER(5,ReactionChamberTileEntMaster.class,true,EnumBlockRenderType.MODEL,BlockRenderLayer.CUTOUT);
		
		private int id;
		private String name;
		private Class<? extends TileEntity> tile;
		private boolean isFullCube;
		private EnumBlockRenderType renderType;
		private BlockRenderLayer renderLayer;

		EnumMultiBlockMachineType(int id, Class<? extends TileEntity> tile, boolean isFullCube, EnumBlockRenderType renderType) {
			this(id,tile,isFullCube,renderType,BlockRenderLayer.SOLID);
		}
		
		EnumMultiBlockMachineType(int id, Class<? extends TileEntity> tile, boolean isFullCube, EnumBlockRenderType renderType, BlockRenderLayer layer) {
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
