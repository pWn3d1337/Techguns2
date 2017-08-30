package techguns.gui.player;

import net.minecraft.client.Minecraft;
import net.minecraft.client.audio.SoundHandler;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.inventory.GuiInventory;
import net.minecraft.client.network.NetHandlerPlayClient;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.RenderItem;
import net.minecraft.item.ItemStack;
import net.minecraft.network.play.client.CPacketCloseWindow;
import net.minecraft.util.ResourceLocation;
import techguns.TGPackets;
import techguns.Techguns;
import techguns.packets.PacketOpenPlayerGUI;

public class TGGuiTabButton extends GuiButton {
	//private static final ResourceLocation tabButtonTextures = new ResourceLocation(Techguns.MODID,"textures/gui/tgplayerinventory.png");
	private static final ResourceLocation tabs_texture = new ResourceLocation("minecraft", "textures/gui/container/creative_inventory/tabs.png");
	RenderItem renderItem= Minecraft.getMinecraft().getRenderItem();
	ItemStack iconItem;
	private final short GUI_ID;

	public TGGuiTabButton(int id, int xPos, int yPos,boolean enabled, ItemStack iconItem,int guid) {
		super(id, xPos, yPos, 28, 26, "");
		this.enabled=enabled;
		this.iconItem = iconItem;
		this.GUI_ID=(short) guid;
	}

	@Override
	public boolean mousePressed(Minecraft mc, int mx, int my) {
		if(this.enabled){
			if (super.mousePressed(mc, mx, my)){
				
				if(GUI_ID<0){
					
					mc.player.connection.sendPacket(new CPacketCloseWindow(mc.player.openContainer.windowId));
					Minecraft.getMinecraft().displayGuiScreen(new GuiInventory(mc.player));
					
				} else {
					TGPackets.network.sendToServer(new PacketOpenPlayerGUI(GUI_ID));
				}
				return true;
			}
		}
		return false;
	}

	
	
	@Override
	public void playPressSound(SoundHandler soundHandlerIn) {
		if (this.enabled) {
			super.playPressSound(soundHandlerIn);
		}
	}

	
	
	@Override
	public void drawButton(Minecraft mc, int mouseX, int mouseY, float partialTicks) {
		if (this.visible)
        {
            mc.getTextureManager().bindTexture(tabs_texture);
            //GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
            GlStateManager.color(1f, 1f, 1f, 1f);
            
            this.hovered = mouseX >= this.x && mouseY >=this.y && mouseX < this.x+this.width && mouseY < this.y + this.height;
            

       /*     GL11.glEnable(GL11.GL_BLEND);
            OpenGlHelper.glBlendFunc(770, 771, 1, 0);
            GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);*/
            
            int i=0;
            if (this.enabled){
            	i=1;
            }
            
            if (!this.enabled) {
            	this.drawTexturedModalRect(this.x, this.y, 0, 32, this.width, this.height);
            } else {
            	this.drawTexturedModalRect(this.x, this.y, 0, 2, this.width, this.height);
            }
            
            if(!this.enabled){
            	this.drawTexturedModalRect(this.x+1, this.y+this.height, 29, 60, this.width, 4);
            }
            
            RenderHelper.enableStandardItemLighting();
            renderItem.renderItemIntoGUI(this.iconItem, this.x+6, this.y+6);
            RenderHelper.disableStandardItemLighting();
            
            this.mouseDragged(mc, mouseX, mouseY);

        }
	}
	
	
	

}