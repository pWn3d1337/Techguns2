package techguns.gui;

import java.awt.event.KeyEvent;
import java.io.IOException;

import net.minecraft.client.gui.GuiButton;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.client.config.GuiButtonExt;
import techguns.TGItems;
import techguns.TGPackets;
import techguns.Techguns;
import techguns.gui.containers.DungeonScannerContainer;
import techguns.gui.widgets.GuiButtonToggleDrainTank;
import techguns.packets.PacketGuiButtonClick;
import techguns.tileentities.BasicOwnedTileEnt;
import techguns.tileentities.ChemLabTileEnt;
import techguns.tileentities.DungeonScannerTileEnt;
import techguns.util.TextUtil;
import techguns.world.dungeon.DungeonTemplate;

public class DungeonScannerGui extends OwnedTileEntGui {

	public static final ResourceLocation texture = new ResourceLocation(Techguns.MODID,"textures/gui/dungeon_scanner_gui.png");// new
	
	protected DungeonScannerTileEnt tileEnt;
	
	private String textInput = "";
	
	public DungeonScannerGui(InventoryPlayer ply, DungeonScannerTileEnt tile) {
		super(new DungeonScannerContainer(ply, tile), tile);
		this.tex=texture;
		this.tileEnt = tile;
	}

	@Override
	protected void drawGuiContainerForegroundLayer(int mouseX, int mouseY) {
		//super.drawGuiContainerForegroundLayer(mouseX, mouseY);
		
		int x = 0;
        int y = 0;
        int mx = mouseX - (this.width-this.xSize)/2;
        int my = mouseY - (this.height-this.ySize)/2;
        int color = 4210752; //0xff101010;
        
        String s2="sizeXZ: "+this.tileEnt.sizeXZ;
        this.fontRenderer.drawString(s2, x+5, y+19, color);
        
        s2="sizeY: "+this.tileEnt.sizeY;
        this.fontRenderer.drawString(s2, x+5, y+41, color);
        
        this.fontRenderer.drawString(textInput, x+6, y+70, 0xffffffff);
        
        //existing templates
        int dy = 0;
        for (String template : DungeonTemplate.dungeonTemplates.keySet()) {
        	this.fontRenderer.drawString(template, x+75, y+18+(dy), color);
        	dy+=8;
        }
	}

	@Override
	protected void keyTyped(char typedChar, int keyCode) throws IOException {
		//super.keyTyped(typedChar, keyCode);
		
		if (keyCode == 1) {
            this.mc.player.closeScreen();
        }else {		
			if (Character.isLetterOrDigit(typedChar) && textInput.length() < 10) {
				textInput+=typedChar;
			}else if (typedChar == '\b') {
				if (textInput.length() > 0) textInput = textInput.substring(0, textInput.length()-1);
			}
        }
	}
	
	
	@Override
	protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY) {
		super.drawGuiContainerBackgroundLayer(partialTicks, mouseX, mouseY);	
		int k = (this.width - this.xSize) / 2;
        int l = (this.height - this.ySize) / 2;
		this.drawRect(k+5, l+69, k+69, l+79, 0xff000000);
		
		this.drawRect(k+74, l+17, k+169, l+158, 0xff888888);
	}

	@Override
	protected void actionPerformed(GuiButton guibutton) {
		int ID_OF_BUTTON_THAT_HAS_STRING = 1337;
		if(guibutton.id== ID_OF_BUTTON_THAT_HAS_STRING) {
			TGPackets.network.sendToServer(new PacketGuiButtonClick(this.ownedTile,guibutton.id,"STRING_PARAM"));
			
		} else {
		
			super.actionPerformed(guibutton);
		}
	}

	@Override
	public void initGui() {
		// TODO Auto-generated method stub
		super.initGui();
		this.buttonList.add(new GuiButtonExt(DungeonScannerTileEnt.BUTTON_ID_GHOST, this.guiLeft+5, this.guiTop+5, 12, 12, "G"));
		
		this.buttonList.add(new GuiButtonExt(DungeonScannerTileEnt.BUTTON_ID_SIZE_XZ_MINUS, this.guiLeft+5, this.guiTop+27, 12, 12, "-"));
		this.buttonList.add(new GuiButtonExt(DungeonScannerTileEnt.BUTTON_ID_SIZE_XZ_PLUS, this.guiLeft+18, this.guiTop+27, 12, 12, "+"));
		
		this.buttonList.add(new GuiButtonExt(DungeonScannerTileEnt.BUTTON_ID_SIZE_Y_MINUS, this.guiLeft+5, this.guiTop+50, 12, 12, "-"));
		this.buttonList.add(new GuiButtonExt(DungeonScannerTileEnt.BUTTON_ID_SIZE_Y_PLUS, this.guiLeft+18, this.guiTop+50, 12, 12, "+"));
		
		this.buttonList.add(new GuiButtonExt(DungeonScannerTileEnt.BUTTON_ID_SCAN, this.guiLeft+5, this.guiTop+81, 31, 12, "scan"));
		this.buttonList.add(new GuiButtonExt(DungeonScannerTileEnt.BUTTON_ID_PLACE, this.guiLeft+38, this.guiTop+81, 31, 12, "load"));
	
	}
	
	

}
