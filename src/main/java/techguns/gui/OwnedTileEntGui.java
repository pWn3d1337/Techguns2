package techguns.gui;

import static techguns.gui.ButtonConstants.BUTTON_ID_SECURITY;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import com.mojang.realmsclient.gui.ChatFormatting;

import net.minecraft.client.gui.GuiButton;
import net.minecraft.inventory.Container;
import net.minecraft.util.ResourceLocation;
import techguns.TGPackets;
import techguns.Techguns;
import techguns.client.ClientProxy;
import techguns.gui.widgets.GuiButtonSecurity;
import techguns.packets.PacketGuiButtonClick;
import techguns.tileentities.BasicOwnedTileEnt;
import techguns.util.TextUtil;

public abstract class OwnedTileEntGui extends TGBaseGui {

	protected BasicOwnedTileEnt ownedTile;
	
	protected int heightSecurityButton=40;
	
	public static final ResourceLocation security_texture = new ResourceLocation(Techguns.MODID,"textures/gui/ammo_press_gui.png");
	
	public OwnedTileEntGui(Container inventorySlotsIn, BasicOwnedTileEnt tile) {
		super(inventorySlotsIn);
		this.ownedTile=tile;
	}

	@Override
	protected void drawGuiContainerForegroundLayer(int mouseX, int mouseY) {
		super.drawGuiContainerForegroundLayer(mouseX, mouseY);
		
		this.drawInventoryContainerName(ownedTile);
		
		int mx = mouseX - (this.width-this.xSize)/2;
        int my = mouseY - (this.height-this.ySize)/2;
		
		if (isInRect(mx,my,-22,heightSecurityButton,20,20)){
        	List<String> tooltip = new ArrayList<String>();
        	String color="";
        	byte type =ownedTile.getSecurity(); 
        	switch(type){
	        	case 0:
	        		color +=ChatFormatting.GREEN;
	        		break;
	        	case 1:
	        		color +=ChatFormatting.YELLOW;
	        		break;
	        	case 2:
	        		color +=ChatFormatting.GOLD;
	        		break;
	        	case 3:
	        		color +=ChatFormatting.RED;
	        		break;
        	}
        	
            String own=TextUtil.trans("techguns.container.security.tooltip.unowned");
            UUID owner = ownedTile.getOwner();
            if (owner!=null){
            	own = TextUtil.trans("techguns.container.security.tooltip.owner")+": "+ ClientProxy.get().resolvePlayerNameFromUUID(owner);
            }
            //this.fontRendererObj.drawString(own, 8, this.ySize - 76 + 2, 4210752);
        	
        	tooltip.add(TextUtil.trans("techguns.container.security.owner")+": "+own);
        	tooltip.add(TextUtil.trans("techguns.container.security.tooltip")+": "+color+ TextUtil.trans("techguns.container.security.tooltip."+type));
        	tooltip.add(TextUtil.trans("techguns.container.security.tooltip.description."+type));
        	tooltip.add(TextUtil.trans("techguns.container.security.tooltip.descr2"));
        	this.drawHoveringText(tooltip, mx, my);
        }
	}

	@Override
	protected void actionPerformed(GuiButton guibutton) {
		TGPackets.network.sendToServer(new PacketGuiButtonClick(this.ownedTile,guibutton.id));
	}
	
	@Override
	protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY) {
		super.drawGuiContainerBackgroundLayer(partialTicks, mouseX, mouseY);
		int k = (this.width - this.xSize) / 2;
        int l = (this.height - this.ySize) / 2;
        
        this.mc.getTextureManager().bindTexture(security_texture);

        //draw security button background
        this.drawTexturedModalRect(k-22-5, l+heightSecurityButton-5, 195, 0, 22+5, 30);
	}

	@Override
	public void initGui() {
		super.initGui();
		this.buttonList.add(new GuiButtonSecurity(BUTTON_ID_SECURITY, this.guiLeft-22, this.guiTop+heightSecurityButton, 20, 20, "", ownedTile));
	}
}
