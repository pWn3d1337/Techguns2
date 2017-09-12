package techguns.gui;

import java.io.IOException;

import net.minecraft.client.gui.GuiButton;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.client.config.GuiButtonExt;
import techguns.TGPackets;
import techguns.Techguns;
import techguns.gui.containers.DungeonGeneratorContainer;
import techguns.packets.PacketGuiButtonClick;
import techguns.tileentities.BasicOwnedTileEnt;
import techguns.tileentities.DungeonGeneratorTileEnt;
import techguns.tileentities.DungeonScannerTileEnt;
import techguns.world.dungeon.DungeonTemplate;

public class DungeonGeneratorGui extends OwnedTileEntGui {

public static final ResourceLocation texture = new ResourceLocation(Techguns.MODID,"textures/gui/dungeon_scanner_gui.png");// new
	
	protected DungeonGeneratorTileEnt tileEnt;
	
	private String textInput = "";
	
	public DungeonGeneratorGui(InventoryPlayer ply, DungeonGeneratorTileEnt tile) {
		super(new DungeonGeneratorContainer(ply, tile), tile);
		this.tex=texture;
		this.tileEnt=tile;
	}
	
	@Override
	protected void drawGuiContainerForegroundLayer(int mouseX, int mouseY) {
			//super.drawGuiContainerForegroundLayer(mouseX, mouseY);
		
		int x = 0;
		int y = 0;
		int mx = mouseX - (this.width-this.xSize)/2;
	    int my = mouseY - (this.height-this.ySize)/2;
	    int color = 4210752; //0xff101010;
		
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
		if(textInput.length()>0 && (guibutton.id== DungeonGeneratorTileEnt.BUTTON_ID_GENERATE)) {
			TGPackets.network.sendToServer(new PacketGuiButtonClick(this.ownedTile,guibutton.id,textInput));
			
		} else {
		
			super.actionPerformed(guibutton);
		}
	}

	@Override
		public void initGui() {
			// TODO Auto-generated method stub
			super.initGui();
			
			this.buttonList.add(new GuiButtonExt(DungeonGeneratorTileEnt.BUTTON_ID_GHOST, this.guiLeft+5, this.guiTop+5, 12, 12, "G"));
			
			this.buttonList.add(new GuiButtonExt(DungeonGeneratorTileEnt.BUTTON_ID_GENERATE, this.guiLeft+5, this.guiTop+81, 64, 12, "generate"));
			
			
			this.buttonList.add(new GuiButtonExt(DungeonGeneratorTileEnt.BUTTON_ID_CLEAR, this.guiLeft+5, this.guiTop+149, 12, 12, "C"));
		}
}
