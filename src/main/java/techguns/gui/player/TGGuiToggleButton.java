package techguns.gui.player;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.fml.client.config.GuiUtils;
import techguns.TGPackets;
import techguns.capabilities.TGExtendedPlayer;
import techguns.keybind.TGKeybindsID;
import techguns.packets.PacketTGKeybindPress;

public class TGGuiToggleButton extends GuiButton {
	private static Minecraft mc = Minecraft.getMinecraft();
	
	private byte type;
	
	/**
	 * @param id
	 * @param xPos
	 * @param yPos
	 * @param width
	 * @param height
	 * @param type - 0: showTGHud, 1: Nightvision, 2: safemode, 3: stepassist, 4: toggleJetpack
	 */
	public TGGuiToggleButton(int id, int xPos, int yPos, int type) {
		super(id, xPos, yPos, 11,11, "");
		this.type=(byte) type;
	}

	@Override
	public boolean mousePressed(Minecraft mc, int mx, int my) {
		if (super.mousePressed(mc, mx, my)){
		//	TechgunsExtendedPlayerProperties props;
			switch(this.type){
				case 0:
					/* props = TechgunsExtendedPlayerProperties.get(Minecraft.getMinecraft().thePlayer);
		        	if (props!=null){
		        		props.showTGHudElements=!props.showTGHudElements;
		        	}
					break;*/
					TGPackets.network.sendToServer(new PacketTGKeybindPress(TGKeybindsID.TOGGLE_HUD));
					break;
				case 1:
					TGPackets.network.sendToServer(new PacketTGKeybindPress(TGKeybindsID.TOGGLE_NIGHTVISION));
					break;
				case 2:
					TGPackets.network.sendToServer(new PacketTGKeybindPress(TGKeybindsID.TOGGLE_SAFEMODE));
					break;
				case 3:
					/*props = TechgunsExtendedPlayerProperties.get(Minecraft.getMinecraft().thePlayer);
		        	if (props!=null){
		        		props.enableStepAssist=!props.enableStepAssist;
		        	}*/
					TGPackets.network.sendToServer(new PacketTGKeybindPress(TGKeybindsID.TOGGLE_STEP_ASSIST));
		        	break;
				case 4:
					TGPackets.network.sendToServer(new PacketTGKeybindPress(TGKeybindsID.TOGGLE_JETPACK));
					break;
			}
			return true;
		}

		return false;
	}

	@Override
	public void drawButton(Minecraft mc, int mouseX, int mouseY, float partialTicks) {
		
		  this.hovered = mouseX >= this.x && mouseY >=this.y && mouseX < this.x+this.width && mouseY < this.y + this.height;
          
        int k = this.getHoverState(this.hovered);
        GuiUtils.drawContinuousTexturedBox(BUTTON_TEXTURES, this.x, this.y, 0, 46 + k * 20, this.width, this.height, 200, 20, 2, 3, 2, 2, this.zLevel);
        this.mouseDragged(mc, mouseX, mouseY);
        
        TGExtendedPlayer props = TGExtendedPlayer.get(mc.player);
        
        byte mode = type;
        boolean value=false;
        switch(type){
        	case 0:
        		value = props.showTGHudElements;
        		break;
        	case 1:
        		value = props.enableNightVision;
        		break;
        	case 2:
        		value = props.enableSafemode;
        		break;
        	case 3:
        		value = props.enableStepAssist;
        		break;
        	case 4:
        		value =props.enableJetpack;
        		break;
        	default:
        		break;
        }
        byte state = (byte) (value ? 1:0);
        
        mc.getTextureManager().bindTexture(TGPlayerInventoryGui.texture);
        
        int y = 7*mode;
        if (mode>3){
        	y+=14;
        }
        this.drawTexturedModalRect(this.x+2, this.y+2, 242+(7*state), y, 7, 7);

	}
	

}