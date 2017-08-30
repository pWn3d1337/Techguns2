package techguns.gui;

import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.client.config.GuiButtonExt;
import techguns.TGItems;
import techguns.Techguns;
import techguns.gui.containers.AmmoPressContainer;
import techguns.gui.containers.CamoBenchContainer;
import techguns.tileentities.AmmoPressTileEnt;
import techguns.tileentities.CamoBenchTileEnt;
import techguns.util.TextUtil;

public class AmmoPressGui extends PoweredTileEntGui {
	public static final ResourceLocation texture = new ResourceLocation(Techguns.MODID,"textures/gui/ammo_press_gui.png");// new
	
	protected AmmoPressTileEnt tileent;
	
	public AmmoPressGui(InventoryPlayer ply, AmmoPressTileEnt tileent) {
		super(new AmmoPressContainer(ply, tileent),tileent);
		this.tileent = tileent;
		this.tex = texture;
	}

	@Override
	protected void drawGuiContainerForegroundLayer(int mouseX, int mouseY) {
		
		int x = 0;
        int y = 0;
        int mx = mouseX - (this.width-this.xSize)/2;
        int my = mouseY - (this.height-this.ySize)/2;
        int color = 4210752; //0xff101010;
        
        String s2="";
        switch(this.tileent.buildPlan){
        	case 0:
        		s2=TextUtil.trans(TGItems.PISTOL_ROUNDS.getUnlocalizedName()+".name");
        		break;
        	case 1:
        		s2=TextUtil.trans(TGItems.SHOTGUN_ROUNDS.getUnlocalizedName()+".name");
        		break;
        	case 2:
        		s2=TextUtil.trans(TGItems.RIFLE_ROUNDS.getUnlocalizedName()+".name");
        		break;
        	case 3:
        		s2=TextUtil.trans(TGItems.SNIPER_ROUNDS.getUnlocalizedName()+".name");
        		break;
        }
        this.fontRenderer.drawString(TextUtil.trans(Techguns.MODID+".gui.ammopress.build")+":", x+20, y+30, color);
        this.fontRenderer.drawString(s2, x+20, y+40, color);
		super.drawGuiContainerForegroundLayer(mouseX, mouseY);
	}

	@Override
	protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY) {
		super.drawGuiContainerBackgroundLayer(partialTicks, mouseX, mouseY);
		
		int k = (this.width - this.xSize) / 2;
        int l = (this.height - this.ySize) / 2;
		
		if (this.tileent.isWorking()) {
			int i1 = this.tileent.getProgressScaled(21);
			// this.drawTexturedModalRect(k + 79, l + 34, 176, 14, i1 + 1, 16);
			this.drawTexturedModalRect(k + 119, l + 36, 176, 0, 19, i1 + 1);
		}

	}

	@Override
	public void initGui() {
		super.initGui();
										//id, x, y, width, height, text
		this.buttonList.add(new GuiButtonExt(AmmoPressTileEnt.BUTTON_ID_NEXT, this.guiLeft+40, this.guiTop+50, 20, 20, ">"));
		this.buttonList.add(new GuiButtonExt(AmmoPressTileEnt.BUTTON_ID_PREV, this.guiLeft+20, this.guiTop+50, 20, 20, "<"));
	}
	
	
}
