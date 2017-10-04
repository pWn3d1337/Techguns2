package techguns.gui;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fluids.FluidTankInfo;
import net.minecraftforge.fml.client.config.GuiButtonExt;
import techguns.Techguns;
import techguns.gui.containers.ReactionChamberContainer;
import techguns.tileentities.ReactionChamberTileEntMaster;
import techguns.util.TextUtil;

public class ReactionChamberGui extends PoweredTileEntGui {
	ReactionChamberTileEntMaster tile;

	public static final ResourceLocation texture = new ResourceLocation(Techguns.MODID,"textures/gui/reaction_chamber_gui.png");

	public ReactionChamberGui(InventoryPlayer ply, ReactionChamberTileEntMaster tile) {
		super(new ReactionChamberContainer(ply, tile), tile);
		this.tile = tile;
		this.tex=texture;
		this.hasUpgradeSlot=false;
	}

	@Override
	protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY) {
		super.drawGuiContainerBackgroundLayer(partialTicks, mouseX, mouseY);

		int k = (this.width - this.xSize) / 2;
		int l = (this.height - this.ySize) / 2;
		
		this.mc.getTextureManager().bindTexture(TextureMap.LOCATION_BLOCKS_TEXTURE);
        if(this.tile.inputTank.getFluidAmount()>0){
        	int px = this.tile.inputTank.getFluidAmount()*ChemLabGui.TANK_H / this.tile.inputTank.getCapacity();

        	TextureAtlasSprite tex = mc.getTextureMapBlocks().getTextureExtry(tile.inputTank.getFluid().getFluid().getStill().toString());
        	
        	this.drawFluidWithTesselator(tex, k+ChemLabGui.INPUT_TANK_X+1, l+ChemLabGui.TANK_Y, ChemLabGui.TANK_W, ChemLabGui.TANK_H, px);
        }
		
		this.mc.getTextureManager().bindTexture(this.texture);
        
        int px_height = 50-this.tile.getLiquidLevel()*5;

        this.drawTexturedModalRect(k+18, l+17, 176, 32, 12, 52);
        
        this.drawTexturedModalRect(k+19, l+17+px_height, 177, 32, 7, 1);
        //this.drawTexturedModalRect(k+157, l+17, 176, 32, 12, 52);
        
        if (this.tile.isWorking()){
        	
        	int compl = this.tile.getCurrentReaction().completion;
        	int req_compl = this.tile.getCurrentReaction().getRecipe().requiredCompletion;
        	
        	int compl_px = Math.round(((compl*1.0f)/(req_compl*1.0f))*100f);
        	
        	int dur = this.tile.progress;
        	int maxdur = this.tile.totaltime;
        	
        	int dur_px = Math.round(((dur*1.0f)/(maxdur*1.0f))*100f);
        	
        	this.drawTexturedModalRect(k+67, l+61, 0, 167, compl_px, 4);
        	
        	this.drawTexturedModalRect(k+67, l+69, 0, 175, dur_px, 4);
        	//System.out.println("Prog:"+dur+" MaxDur:"+maxdur);
        	
        	  //draw intensity
        	int px = (10-this.tile.getIntensity())*4;
        	
        	if (this.tile.getCurrentReaction().required_intensity!=this.tile.getIntensity()) {
        	
        		this.drawTexturedModalRect(k+114, l+16+px, 198, px, 5, 40-px);

        	} else {
        		this.drawTexturedModalRect(k+114, l+16+px, 190, px, 5, 40-px);
        	}
        	
        	int h = 40-this.tile.getCurrentReaction().required_intensity*4;
            this.drawTexturedModalRect(k+112, l+15+h, 178, 22, 5, 3);
        	
        	
        }  else {
            //draw intensity
        	int px = (10-this.tile.getIntensity())*4;
        	this.drawTexturedModalRect(k+114, l+16+px, 190, px, 5, 40-px);
        	
        }
	}


	@Override
	protected void drawGuiContainerForegroundLayer(int mouseX, int mouseY) {
		super.drawGuiContainerForegroundLayer(mouseX, mouseY);

		int mx = mouseX - (this.width - this.xSize) / 2;
		int my = mouseY - (this.height - this.ySize) / 2;

		int fluidLevel = this.tile.getLiquidLevel() * 10;
		int intensity = this.tile.getIntensity();

		this.fontRenderer.drawString(fluidLevel + "%", 34, 50, 4210752);
		this.fontRenderer.drawString(intensity + "", 121, 18, 4210752);

        if (this.isInRect(mx, my, 18, 16, 11, 51)){
        	FluidTankInfo info = this.tile.inputTank.getInfo();
        	List<String> tooltip = new ArrayList<>();
        	
        	tooltip.add(TextUtil.trans(info.fluid!=null?info.fluid.getFluid().getUnlocalizedName():Techguns.MODID+".gui.empty"));
        	tooltip.add(tile.inputTank.getFluidAmount()+"/"+(info.capacity+"mB"));
        	
        	this.drawHoveringText(tooltip, mx, my);
        	
        } else if(this.isInRect(mx, my, 20, 7, 8, 8)){
        	List<String> tooltip = new ArrayList<>();
        	tooltip.add(TextUtil.trans("techguns.chemlab.dumpinput"));
        	tooltip.add(TextUtil.trans("techguns.chemlab.dumpinput.tooltip"));
        	
        	this.drawHoveringText(tooltip, mx, my);
        }
	       
	}


	@Override
	public void initGui() {
		super.initGui();
		
		int left=33;
		this.buttonList.add(new GuiButtonExt(ReactionChamberTileEntMaster.BUTTON_ID_LIQUIDLEVEL_INC, this.guiLeft+left, this.guiTop+58, 10, 10, "+"));
		this.buttonList.add(new GuiButtonExt(ReactionChamberTileEntMaster.BUTTON_ID_LIQUIDLEVEL_DEC, this.guiLeft+left+10, this.guiTop+58, 10, 10, "-"));
		
		left=91;
		this.buttonList.add(new GuiButtonExt(ReactionChamberTileEntMaster.BUTTON_ID_INTENSITY_INC, this.guiLeft+left, this.guiTop+36, 10, 10, "+"));
		this.buttonList.add(new GuiButtonExt(ReactionChamberTileEntMaster.BUTTON_ID_INTENSTIY_DEC, this.guiLeft+left+10, this.guiTop+36, 10, 10, "-"));
		
		this.buttonList.add(new GuiButtonExt(ReactionChamberTileEntMaster.BUTTON_ID_DUMPTANK, this.guiLeft+20, this.guiTop+7, 8, 8, "x"));
	}

	
}
