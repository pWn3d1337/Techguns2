package techguns.events;

import java.util.HashMap;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.IGuiHandler;
import techguns.Techguns;
import techguns.gui.AmmoPressGui;
import techguns.gui.CamoBenchGui;
import techguns.gui.ChargingStationGui;
import techguns.gui.ChemLabGui;
import techguns.gui.DungeonScannerGui;
import techguns.gui.FabricatorGui;
import techguns.gui.MetalPressGui;
import techguns.gui.ReactionChamberGui;
import techguns.gui.RepairBenchGui;
import techguns.gui.TurretGui;
import techguns.gui.containers.AmmoPressContainer;
import techguns.gui.containers.CamoBenchContainer;
import techguns.gui.containers.ChargingStationContainer;
import techguns.gui.containers.ChemLabContainer;
import techguns.gui.containers.DungeonScannerContainer;
import techguns.gui.containers.FabricatorContainer;
import techguns.gui.containers.MetalPressContainer;
import techguns.gui.containers.ReactionChamberContainer;
import techguns.gui.containers.RepairBenchContainer;
import techguns.gui.containers.TurretContainer;
import techguns.gui.player.TGPlayerInventoryContainer;
import techguns.gui.player.TGPlayerInventoryGui;
import techguns.tileentities.AmmoPressTileEnt;
import techguns.tileentities.CamoBenchTileEnt;
import techguns.tileentities.ChargingStationTileEnt;
import techguns.tileentities.ChemLabTileEnt;
import techguns.tileentities.DungeonScannerTileEnt;
import techguns.tileentities.FabricatorTileEntMaster;
import techguns.tileentities.MetalPressTileEnt;
import techguns.tileentities.ReactionChamberTileEntMaster;
import techguns.tileentities.RepairBenchTileEnt;
import techguns.tileentities.TurretTileEnt;

public class TechgunsGuiHandler implements IGuiHandler {

	static {
		GuiHandlerEntry.<CamoBenchTileEnt>addEntry(CamoBenchTileEnt.class, CamoBenchGui::new, CamoBenchContainer::new);
		GuiHandlerEntry.<RepairBenchTileEnt>addEntry(RepairBenchTileEnt.class, RepairBenchGui::new, RepairBenchContainer::new);
		GuiHandlerEntry.<AmmoPressTileEnt>addEntry(AmmoPressTileEnt.class, AmmoPressGui::new, AmmoPressContainer::new);
		GuiHandlerEntry.<MetalPressTileEnt>addEntry(MetalPressTileEnt.class, MetalPressGui::new, MetalPressContainer::new);
		GuiHandlerEntry.<ChemLabTileEnt>addEntry(ChemLabTileEnt.class, ChemLabGui::new, ChemLabContainer::new);
		GuiHandlerEntry.<TurretTileEnt>addEntry(TurretTileEnt.class, TurretGui::new, TurretContainer::new);
		GuiHandlerEntry.<FabricatorTileEntMaster>addEntry(FabricatorTileEntMaster.class, FabricatorGui::new, FabricatorContainer::new);
		GuiHandlerEntry.<ChargingStationTileEnt>addEntry(ChargingStationTileEnt.class, ChargingStationGui::new, ChargingStationContainer::new);
		GuiHandlerEntry.<ReactionChamberTileEntMaster>addEntry(ReactionChamberTileEntMaster.class, ReactionChamberGui::new, ReactionChamberContainer::new);
		GuiHandlerEntry.<DungeonScannerTileEnt>addEntry(DungeonScannerTileEnt.class, DungeonScannerGui::new, DungeonScannerContainer::new);
	}
	
	public static void openGuiForPlayer(EntityPlayer ply, TileEntity tile) {
		int id = getGuiIdForTile(tile.getClass());
		if(id>0) {
			ply.openGui(Techguns.instance, id, tile.getWorld(), tile.getPos().getX(), tile.getPos().getY(), tile.getPos().getZ());
		}
	}
	
	public static int getGuiIdForTile(Class clazz) {
		GuiHandlerEntry entry = GuiHandlerEntry.getByTileClass.get(clazz);
		if (entry!=null) {
			return entry.id;
		}
		return -1;
	}
	
	@Override
	public Object getServerGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
		if (ID ==0) {
			return new TGPlayerInventoryContainer(player);
		} else {
			TileEntity tileEntity = world.getTileEntity(new BlockPos(x,y,z));
			if (tileEntity!=null) {
				GuiHandlerEntry entry = GuiHandlerEntry.getByID.get(ID);
				if (entry!=null) {
					return entry.servergui.createElement(player.inventory, tileEntity);
				}
			}
		}
		
		return null;
	}

	@Override
	public Object getClientGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
		if (ID==0) {
			return new TGPlayerInventoryGui(player.inventoryContainer, player);
		}
		else {
			TileEntity tileEntity = world.getTileEntity(new BlockPos(x,y,z));
			if (tileEntity!=null) {
				GuiHandlerEntry entry = GuiHandlerEntry.getByID.get(ID);
				if (entry!=null) {
					return entry.clientgui.createElement(player.inventory, tileEntity);
				}
			}
		}
		return null;
	}
	
	
	
	private static class GuiHandlerEntry<T> {
		private static int nextId=1;
		
		private static HashMap<Class, GuiHandlerEntry> getByTileClass = new HashMap<>();
		private static HashMap<Integer, GuiHandlerEntry> getByID = new HashMap<>();
		
		protected int id;		
		public Class<T> tileclass;
		protected IGuiFactory<T> clientgui;
		protected IGuiFactory<T> servergui;
		
		public static <T extends TileEntity> void addEntry(Class<? extends T> tile, IGuiFactory<T> clientgui, IGuiFactory<T> servergui) {
			new GuiHandlerEntry(tile, clientgui, servergui);
		}
		
		private GuiHandlerEntry(Class<T> tile, IGuiFactory<T> clientgui, IGuiFactory<T> servergui ) {
			super();
			this.id=nextId++;
			this.tileclass = tile;
			this.clientgui=clientgui;
			this.servergui=servergui;
			
			getByTileClass.put(this.tileclass, this);
			getByID.put(this.id, this);
		}

		public IGuiFactory<T> getClientgui() {
			return clientgui;
		}

		public IGuiFactory<T> getServergui() {
			return servergui;
		}
		
	}
	
}
