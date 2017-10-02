package techguns.gui;

import java.awt.event.KeyEvent;
import java.io.IOException;
import java.util.HashMap;

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
import techguns.world.dungeon.TemplateSegment;
import techguns.world.dungeon.TemplateSegment.SegmentType;

public class DungeonScannerGui extends OwnedTileEntGui {

	public static final ResourceLocation texture = new ResourceLocation(Techguns.MODID,"textures/gui/dungeon_scanner_gui.png");// new
	
	protected DungeonScannerTileEnt tileEnt;
	
	private String textInput = "";
	
	//private HashMap<SegmentType, Boolean> template_filter = new HashMap<>();
	private static final int tb_size = 4; //tb = template button
	private static final int tb_spacing = 2;
	private static final int tb_x = 5;
	private static final int tb_y = 114;
	
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
	protected void mouseClicked(int mouseX, int mouseY, int mouseButton) throws IOException {
		super.mouseClicked(mouseX, mouseY, mouseButton);
		
		int k = (this.width - this.xSize) / 2;
        int l = (this.height - this.ySize) / 2;
		
		int xpos = 75;
		int width=93;
		
		int dy = 0;
        for (String template : DungeonTemplate.dungeonTemplates.keySet()) {
        	/*this.fontRenderer.drawString(template, k+75, l+18+(dy), color);*/
        	if(this.isInRect(mouseX, mouseY, k+xpos, l+18+dy, width, 8)) {
        		this.textInput = template;
        		break;
        	}
        	dy+=8;
        }
        
        for (SegmentType type : TemplateSegment.templateSegments.keySet()) {
        	TemplateSegment seg = TemplateSegment.templateSegments.get(type);
    		int x_ = tb_x + seg.col * (tb_size + tb_spacing);
        	int y_ = tb_y + seg.row * (tb_size + tb_spacing);
        	if (this.isInRect(mouseX, mouseY, k+x_, l+y_, tb_size, tb_size)) {
        		this.tileEnt.template_filter.put(type, !this.tileEnt.template_filter.get(type));
        		break;
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
		
		int xpos = 75;
		int width=93;
		
		 //existing templates
        int dy = 0;
        for (String template : DungeonTemplate.dungeonTemplates.keySet()) {
        	/*this.fontRenderer.drawString(template, k+75, l+18+(dy), color);*/
        	if(this.isInRect(mouseX, mouseY, k+xpos, l+18+dy, width, 8)) {
        		this.drawRect(k+xpos, l+18+dy, k+xpos+width, l+18+dy+8, 0xFFA0A0A0);
        		break;
        	}
        	dy+=8;
        }
        
//        int k = (this.width - this.xSize) / 2;
//        int l = (this.height - this.ySize) / 2;
        for (SegmentType type : TemplateSegment.templateSegments.keySet()) {
        	this.drawSegmentButton(type,k, l, mouseX, mouseY);
//        	TemplateSegment seg = TemplateSegment.templateSegments.get(type);
//        	
//        	int x_ = tb_x + seg.col * (tb_size + tb_spacing);
//        	int y_ = tb_y + seg.row * (tb_size + tb_spacing);
//        	
//        	if (this.template_filter.get(type)) {
//        		if(this.isInRect(mouseX, mouseY, k+x_, l+y_, tb_size, tb_size)) {
//	        		this.drawRect(k+x_, l+y_, k+x_+tb_size, l+y_+tb_size, 0xffa6ffa6);
//	        	}else {
//	        		this.drawRect(k+x_, l+y_, k+x_+tb_size, l+y_+tb_size, 0xff00ff00);
//	        	}
//        	}else {
//	        	if(this.isInRect(mouseX, mouseY, k+x_, l+y_, tb_size, tb_size)) {
//	        		this.drawRect(k+x_, l+y_, k+x_+tb_size, l+y_+tb_size, 0xffdbdbdb);
//	        	}else {
//	        		this.drawRect(k+x_, l+y_, k+x_+tb_size, l+y_+tb_size, 0xff646464);
//	        	}
//        	}
        }
	}

	private void drawSegmentButton(SegmentType type, int k, int l, int mouseX, int mouseY) {
		
		TemplateSegment seg = TemplateSegment.templateSegments.get(type);
		int x_ = tb_x + seg.col * (tb_size + tb_spacing);
    	int y_ = tb_y + seg.row * (tb_size + tb_spacing);
    	
    	int color = 0xff323232;
    	if (this.tileEnt.template_filter.get(type)) color = 0xff008000;
    	
    	this.drawRect(k+x_, l+y_, k+x_+tb_size, l+y_+tb_size, color);
    	
    	
    	color = 0xff646464;
    	if (this.tileEnt.template_filter.get(type)) color = 0xff00ff00;
    	
    	this.drawRect(k+x_+1, l+y_+1, k+x_+tb_size-1, l+y_+tb_size-1, color);
    	
    	if (seg.pattern != null && seg.pattern.length >= 8) {
	    	if (seg.pattern[0]) this.drawRect(k+x_, l+y_, k+x_+1, l+y_+1, color);
	    	if (seg.pattern[1]) this.drawRect(k+x_+1, l+y_, k+x_+3, l+y_+1, color);
	    	if (seg.pattern[2]) this.drawRect(k+x_+3, l+y_, k+x_+4, l+y_+1, color);
	    	if (seg.pattern[3]) this.drawRect(k+x_+3, l+y_+1, k+x_+4, l+y_+3, color);
	    	if (seg.pattern[4]) this.drawRect(k+x_+3, l+y_+3, k+x_+4, l+y_+4, color);  	
	    	if (seg.pattern[5]) this.drawRect(k+x_+1, l+y_+3, k+x_+3, l+y_+4, color);    	
	    	if (seg.pattern[6]) this.drawRect(k+x_, l+y_+3, k+x_+1, l+y_+4, color);
	    	if (seg.pattern[7]) this.drawRect(k+x_, l+y_+1, k+x_+1, l+y_+3, color);
    	}
    	
    	if (isInRect(mouseX, mouseY, k+x_, l+y_, tb_size,tb_size)) {
    		this.drawRect(k+x_, l+y_, k+x_+tb_size, l+y_+tb_size, 0x80ffffff);
    	}
    	
	}
	
	
	@Override
	protected void actionPerformed(GuiButton guibutton) {
		if(textInput.length()>0 && (guibutton.id== DungeonScannerTileEnt.BUTTON_ID_SCAN || guibutton.id == DungeonScannerTileEnt.BUTTON_ID_PLACE)) {
			TGPackets.network.sendToServer(new PacketGuiButtonClick(this.ownedTile,guibutton.id,textInput));
			
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
		
		this.buttonList.add(new GuiButtonExt(DungeonScannerTileEnt.BUTTON_ID_CLEAR, this.guiLeft+5, this.guiTop+149, 12, 12, "C"));
	
		this.buttonList.add(new GuiButtonExt(DungeonScannerTileEnt.BUTTON_ID_COPY_FIRST, this.guiLeft+5, this.guiTop+97, 64, 12, "Copy 1st Segment"));
		
//		for (SegmentType type : TemplateSegment.templateSegments.keySet()) {
//			this.template_filter.put(type, true);
//		}
	}
	
	

}
