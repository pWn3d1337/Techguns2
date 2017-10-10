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
import techguns.gui.containers.ChemLabContainer;
import techguns.gui.widgets.GuiButtonToggleDrainTank;
import techguns.tileentities.ChemLabTileEnt;
import techguns.util.TextUtil;

public class ChemLabGui extends PoweredTileEntGui {

	public static final ResourceLocation texture = new ResourceLocation(Techguns.MODID,"textures/gui/chem_lab_gui.png");// new
	
	public static final int INPUT_TANK_X = 18;
	public static final int TANK_Y = 17;
	public static final int OUTPUT_TANK_X = 157;
	
	public static final int TANK_W=10;
	public static final int TANK_H=50;
	
	protected ChemLabTileEnt tile;
	
	public ChemLabGui(InventoryPlayer ply, ChemLabTileEnt tileent) {
		super(new ChemLabContainer(ply, tileent),tileent);
		this.tile = tileent;
		this.tex = texture;
		this.upgradeSlotX = ChemLabContainer.SLOT_UPGRADE_X-1;
		this.upgradeSlotY = ChemLabContainer.SLOT_UPGRADE_Y-1;
	}

	@Override
	protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY) {
		super.drawGuiContainerBackgroundLayer(partialTicks, mouseX, mouseY);
		
		int k = (this.width - this.xSize) / 2;
		int l = (this.height - this.ySize) / 2;

		this.mc.getTextureManager().bindTexture(TextureMap.LOCATION_BLOCKS_TEXTURE);
		
        if(this.tile.inputTank.getFluidAmount()>0){
        	int px = this.tile.inputTank.getFluidAmount()*TANK_H / this.tile.inputTank.getCapacity();

        	TextureAtlasSprite tex = mc.getTextureMapBlocks().getTextureExtry(tile.inputTank.getFluid().getFluid().getStill().toString());
        	
        	this.drawFluidWithTesselator(tex, k+INPUT_TANK_X+1, l+TANK_Y, TANK_W, TANK_H, px);
        }
        
        if(this.tile.outputTank.getFluidAmount()>0){
        	int px = this.tile.outputTank.getFluidAmount()*TANK_H / this.tile.outputTank.getCapacity();

        	TextureAtlasSprite tex = mc.getTextureMapBlocks().getTextureExtry(tile.outputTank.getFluid().getFluid().getStill().toString());
        	
        	this.drawFluidWithTesselator(tex, k+OUTPUT_TANK_X+1, l+TANK_Y, TANK_W, TANK_H, px);
        }
		
        this.mc.getTextureManager().bindTexture(this.texture);
		
		this.drawTexturedModalRect(k + INPUT_TANK_X, l + TANK_Y, 176, 32, TANK_W+2, TANK_H+2);
		this.drawTexturedModalRect(k + OUTPUT_TANK_X, l + TANK_Y, 176, 32, TANK_W+2, TANK_H+2);
		
		
		if (this.tile.isWorking()) {

			float prog = this.tile.getProgress();
			int i1 = (int) ((prog < 0.2f ? (prog * 5) : 1.0f) * 25);
			this.drawTexturedModalRect(k + 81, l + 19, 178, 5, 8, i1);

			if (prog >= 0.2f) {
				int i2 = (int) ((prog < 0.4f ? (prog - 0.2f) * 5 : 1.0f) * 9);
				this.drawTexturedModalRect(k + 88, l + 39, 186, 25, i2, 1);
			}

			if (prog >= 0.4f) {
				int i3 = (int) ((prog < 0.6f ? (prog - 0.4f) * 5 : 1.0f) * 20);
				this.drawTexturedModalRect(k + 97, l + 23 + (20 - i3), 194, 9 + (20 - i3), 14, i3);
			}

			if (prog >= 0.6f) {
				int i4 = (int) ((prog < 0.8f ? (prog - 0.6f) * 5 : 1.0f) * 16);
				this.drawTexturedModalRect(k + 108, l + 17, 205, 3, i4, 6);
			}
			if (prog >= 0.8f) {
				int i5 = (int) (((prog - 0.8f) * 5) * 24);
				this.drawTexturedModalRect(k + 117, l + 20, 214, 6, 14, i5 + 1);
			}
		}
		
	}

	@Override
	protected void drawGuiContainerForegroundLayer(int mouseX, int mouseY) {
		
		super.drawGuiContainerForegroundLayer(mouseX, mouseY);
		
		int color = 4210752; // 0xff101010;
		int mx = mouseX - (this.width - this.xSize) / 2;
		int my = mouseY - (this.height - this.ySize) / 2;

		 if (this.isInRect(mx, my, INPUT_TANK_X, TANK_Y-1, TANK_W+1, TANK_H+1)){
			 
        	FluidTankInfo info = this.tile.inputTank.getInfo();
        	List<String> tooltip = new ArrayList<String>();
        	tooltip.add(info.fluid!=null?(info.fluid.getFluid().getLocalizedName(info.fluid)):TextUtil.trans("techguns.gui.empty"));
        	tooltip.add(tile.inputTank.getFluidAmount()+"/"+(info.capacity+"mB"));
        	this.drawHoveringText(tooltip, mx, my);        	
        	
		 } else if (this.isInRect(mx, my, OUTPUT_TANK_X, TANK_Y-1, TANK_W+1, TANK_H+1)){
			 
        	FluidTankInfo info = this.tile.outputTank.getInfo();
			List<String> tooltip = new ArrayList<String>();
        	tooltip.add(info.fluid!=null?(info.fluid.getFluid().getLocalizedName(info.fluid)):TextUtil.trans("techguns.gui.empty"));
        	tooltip.add(tile.outputTank.getFluidAmount()+"/"+(info.capacity+"mB"));
        	this.drawHoveringText(tooltip, mx, my);        	
		 
		 } else if(this.isInRect(mx, my, 20, 7, 8, 8)){
        	
        	List<String> tooltip = new ArrayList<String>();
        	tooltip.add(TextUtil.trans("techguns.chemlab.dumpinput"));
        	tooltip.add(TextUtil.trans("techguns.chemlab.dumpinput.tooltip"));
        	this.drawHoveringText(tooltip, mx, my);
		 
		 } else if(this.isInRect(mx, my, 159, 7, 8, 8)){
			 
        	List<String> tooltip = new ArrayList<String>();
        	tooltip.add(TextUtil.trans("techguns.chemlab.dumpoutput"));
        	tooltip.add(TextUtil.trans("techguns.chemlab.dumpoutput.tooltip"));
        	this.drawHoveringText(tooltip, mx, my);
        	
		 } else if(this.isInRect(mx, my, 156, 68, 14, 14)){
			 	
        	List<String> tooltip = new ArrayList<String>();
        	tooltip.add(TextUtil.trans("techguns.chemlab.toogledrain"));
        	tooltip.add(TextUtil.trans("techguns.chemlab.toogledrain.tooltip."+tile.getDrainMode()));
        	this.drawHoveringText(tooltip, mx, my);
		 }
	}

	@Override
	public void initGui() {
		super.initGui();
		this.buttonList.add(new GuiButtonExt(ChemLabTileEnt.BUTTON_ID_DUMP_INPUT, this.guiLeft+20, this.guiTop+7, 8, 8, "x"));
		this.buttonList.add(new GuiButtonExt(ChemLabTileEnt.BUTTON_ID_DUMP_OUTPUT, this.guiLeft+159, this.guiTop+7, 8, 8, "x"));
		this.buttonList.add(new GuiButtonToggleDrainTank(ChemLabTileEnt.BUTTON_ID_TOGGLE_DRAIN, this.guiLeft+156, this.guiTop+68, 14, 14, this.tile));
	
	}

	
	
}
