package techguns.gui;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import com.mojang.realmsclient.gui.ChatFormatting;

import net.minecraft.inventory.Container;
import net.minecraft.util.ResourceLocation;
import techguns.TGConfig;
import techguns.Techguns;
import techguns.client.ClientProxy;
import techguns.gui.widgets.GuiButtonRedstoneState;
import techguns.gui.widgets.GuiButtonSecurity;
import techguns.tileentities.BasicPoweredTileEnt;
import techguns.util.TextUtil;
import static techguns.gui.ButtonConstants.*;

public abstract class PoweredTileEntGui extends RedstoneTileEntGui {
	public static final String POWER_UNIT ="FE";
	
	protected BasicPoweredTileEnt poweredTileEnt;
	public static final ResourceLocation power_texture = new ResourceLocation(Techguns.MODID,"textures/gui/ammo_press_gui.png");
	
	protected boolean hasUpgradeSlot=true;
	
	protected int upgradeSlotX=151;
	protected int upgradeSlotY=59;
	
	public PoweredTileEntGui(Container container, BasicPoweredTileEnt tile) {
		super(container,tile);
		this.poweredTileEnt=tile;
	}

	@Override
	protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY) {
		super.drawGuiContainerBackgroundLayer(partialTicks, mouseX, mouseY);
		
		int k = (this.width - this.xSize) / 2;
        int l = (this.height - this.ySize) / 2;
        
		 //draw power bar
        int power = (int) (this.poweredTileEnt.getEnergyStorage().getEnergyStored()*48 / this.poweredTileEnt.getEnergyStorage().getMaxEnergyStored());
        int diff=48-power;
        if(TGConfig.machinesNeedNoPower) {
        	diff=0;
        }
        this.mc.getTextureManager().bindTexture(power_texture);
        this.drawTexturedModalRect(k+8, l+17+diff, 251, 1+diff, 4, 48);
        //System.out.println("Power:"+power+" diff:"+diff);
        
        //draw upgrade slot
        if (hasUpgradeSlot) {
        	this.drawTexturedModalRect(k+upgradeSlotX, l+upgradeSlotY, 119, 59, 20, 20);
        }

	}

	@Override
	protected void drawGuiContainerForegroundLayer(int mouseX, int mouseY) {
		super.drawGuiContainerForegroundLayer(mouseX, mouseY);
		
		int mx = mouseX - (this.width-this.xSize)/2;
        int my = mouseY - (this.height-this.ySize)/2;
        
        if (isInRect(mx,my,7,17,5,49)){
        	if(TGConfig.machinesNeedNoPower) {
        		this.drawHoveringText(TextUtil.trans("techguns.container.power"),mx,my);
        	} else {
        		this.drawHoveringText(TextUtil.trans("techguns.container.power")+": "+this.poweredTileEnt.getEnergyStorage().getEnergyStored()+"/"+this.poweredTileEnt.getEnergyStorage().getMaxEnergyStored()+" "+POWER_UNIT, mx, my);
        	}	
        }
	}
	
}
