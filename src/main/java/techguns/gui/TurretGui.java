package techguns.gui;

import com.mojang.realmsclient.gui.ChatFormatting;

import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.util.ResourceLocation;
import techguns.Techguns;
import techguns.gui.containers.TurretContainer;
import techguns.gui.widgets.GuiButtonTargetAnimals;
import techguns.tileentities.BasicPoweredTileEnt;
import techguns.tileentities.TurretTileEnt;
import techguns.util.TextUtil;

public class TurretGui extends PoweredTileEntGui {
	public static final ResourceLocation texture = new ResourceLocation(Techguns.MODID,"textures/gui/turret_base_gui.png");
	
	protected TurretTileEnt tile;
	
	public TurretGui(InventoryPlayer player, TurretTileEnt tile) {
		super(new TurretContainer(player, tile), tile);
		this.tile=tile;
		this.tex=texture;
		this.hasUpgradeSlot=false;
	}

	@Override
	protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY) {
		super.drawGuiContainerBackgroundLayer(partialTicks, mouseX, mouseY);
		
		int k = (this.width - this.xSize) / 2;
        int l = (this.height - this.ySize) / 2;
		
      //draw animal targeting button background:
        this.drawTexturedModalRect(k-22-5, l+70-5, 195, 0, 22+5, 30);
        
        if (this.tile.turretDeath){
        	int i1= this.tile.getRepairTimeScaled(52);
        	Minecraft.getMinecraft().getTextureManager().bindTexture(texture);
        	this.drawTexturedModalRect(k+116,l+74,177,1,i1+1,4);
        }
		
	}

	@Override
	protected void drawGuiContainerForegroundLayer(int mouseX, int mouseY) {
        super.drawGuiContainerForegroundLayer(mouseX, mouseY);
       
    	int mx = mouseX - (this.width-this.xSize)/2;
        int my = mouseY - (this.height-this.ySize)/2;
        
    	if (this.isInRect(mx, my, 115, 73, 53, 5)){
    		if (this.tile.turretDeath){
    			this.drawHoveringText(TextUtil.trans("techguns.container.repair")+" "+this.tile.repairTime/20+"s", my, my);
    		} else {
    			if(tile.mountedTurret!=null) {
    				this.drawHoveringText(TextUtil.trans("techguns.turret.health")+": "+tile.mountedTurret.getHealth()+"/"+tile.mountedTurret.getMaxHealth(), mx, my);
    			}
			}
    	}
        
        if (this.isInRect(mx, my, -22,70,20,20)){
    		this.drawHoveringText(TextUtil.trans("techguns.turret.targetAnimals")+": "+(this.tile.attackAnimals?ChatFormatting.RED+TextUtil.trans("techguns.yes"):ChatFormatting.GREEN+TextUtil.trans("techguns.no")), mx, my);
    	}
	}

	@Override
	public void initGui() {
		super.initGui();
		this.buttonList.add(new GuiButtonTargetAnimals(TurretTileEnt.BUTTON_ID_TARGET_ANIMALS, this.guiLeft-22, this.guiTop+70,20,20, this.tile));
	}
	
	
}
