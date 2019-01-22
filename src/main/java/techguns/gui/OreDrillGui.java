package techguns.gui;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fluids.FluidTankInfo;
import techguns.TGConfig;
import techguns.Techguns;
import techguns.gui.containers.OreDrillContainer;
import techguns.tileentities.BasicPoweredTileEnt;
import techguns.tileentities.FabricatorTileEntMaster;
import techguns.tileentities.OreDrillTileEntMaster;
import techguns.util.TextUtil;

public class OreDrillGui extends PoweredTileEntGui {
	public static final ResourceLocation texture = new ResourceLocation(Techguns.MODID,"textures/gui/ore_drill_gui.png");

	public static final int INPUT_TANK_X = 15;
	public static final int TANK_Y = 17;
	public static final int OUTPUT_TANK_X = 157;
	
	public static final int TANK_W=10;
	public static final int TANK_H=50;
	
	protected OreDrillTileEntMaster tile;
	
	public OreDrillGui(InventoryPlayer ply, OreDrillTileEntMaster tileent) {
		super(new OreDrillContainer(ply, tileent), tileent);
		this.tex=texture;
		this.tile=tileent;
		this.hasUpgradeSlot=false;
	}

	@Override
	protected void drawGuiContainerForegroundLayer(int mouseX, int mouseY) {
		super.drawGuiContainerForegroundLayer(mouseX, mouseY);
		
		int color = 4210752; // 0xff101010;
		int mx = mouseX - (this.width - this.xSize) / 2;
		int my = mouseY - (this.height - this.ySize) / 2;

		   if(!tile.isFormed()){
	        	String s1= TextUtil.trans("techguns.container.oredrill.broken");
	        	String s2= TextUtil.trans("techguns.container.oredrill.broken2");
	        	int startx = this.xSize/2 - ((s1.length()*6)/2);
	        	int startx2 = this.xSize/2 - ((s2.length()*6)/2);

	            int red = 0xFFFF2020;
	    		
		    	this.fontRenderer.drawString(s1, startx-16, 44, red);
		    	this.fontRenderer.drawString(s2, startx2-16, 52, red);
	        } else {
	        	//this.fontRendererObj.drawString(TextUtil.trans("techguns.MULTIBLOCK OK"), 50, 24, 4210752);
	        }
		   
	        if (this.isInRect(mx, my, 74, 16, 27, 39)){
	        	List<String> tooltip = new ArrayList<String>();
	        	tooltip.add(String.format("%.2f",(this.tile.progress*1.0f/this.tile.totaltime*100.0f))+"%");
	        	tooltip.add(TextUtil.trans("techguns.gui.oredrill.orehour")+": "+String.format("%.2f",1200.0/this.tile.totaltime*60.0));
	        	if(this.tile.getPowerPerTick()>0) {
	        		tooltip.add(TextUtil.trans("techguns.gui.oredrill.rftick")+": "+this.tile.getPowerPerTick());
	        	}
	        	tooltip.add("");
	        	tooltip.add(TextUtil.trans("techguns.oredrill.drillsize")+": "+(tile.getRods()+tile.getEngines()));
	        	tooltip.add(TextUtil.trans("techguns.oredrill.drillradius")+": "+tile.getDrillRadius());
	       	//tooltip.add(TextUtil.trans("techguns.oredrill.enginesize")+": "+tile.getEngines()+"x"+(tile.getRadius()*2+1)+"x"+(tile.getRadius()*2+1)+": "+(tile.getEngines()*(tile.getRadius()*2+1)*(tile.getRadius()*2+1)));
	        	
	        	this.drawHoveringText(tooltip, mx, my);
	        	
	        } else if (this.isInRect(mx, my, INPUT_TANK_X, TANK_Y-1, TANK_W+1, TANK_H+1)){
			 
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
		 
		 } else if (this.isInRect(mx, my, 34, 36, 8, 14)) {
			 if (this.tile.getCurrentFuelBufferMax()>0){
        		int buf = this.tile.getCurrentFuelBufferMax();
        		int fuel = this.tile.getFuelBuffer();
        		
        		int pwrtick = this.tile.getPowerPerTick();
        		float FACTOR=TGConfig.oreDrillMultiplierFuel;

        		int ticksLeft=0;
        		if(FACTOR<=0 || pwrtick<=0) {
        			ticksLeft = -1;
        		} else {
        			ticksLeft = (int)(fuel/(pwrtick/FACTOR)); //pwrtick>0?(int)(fuel/(pwrtick/FACTOR)):-1;
        		}
        		ArrayList<String> tooltip = new ArrayList<String>(2);
        		//tooltip.add("Op:"+tile.currentOperation+" PWRTICK:"+pwrtick+" FUEL"+fuel+" Factor"+FACTOR);
        		tooltip.add(TextUtil.trans("techguns.oredrill.currentfuel")+" "+fuel+"/"+buf);
        		tooltip.add(TextUtil.trans("techguns.oredrill.remaining")+": "+ticksLeft+" "+TextUtil.trans("techguns.oredrill.ticks"));
        		
        		this.drawHoveringText(tooltip, mx, my);
        	} else {
        		this.drawHoveringText(TextUtil.trans("techguns.oredrill.nofuel"),mx,my);
        	}
		 }
        
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
        	
        	//System.out.println("Draw input fluid");
        	this.drawFluidWithTesselator(tex, k+INPUT_TANK_X+1, l+TANK_Y, TANK_W, TANK_H, px);
        }
        
        if(this.tile.outputTank.getFluidAmount()>0){
        	int px = this.tile.outputTank.getFluidAmount()*TANK_H / this.tile.outputTank.getCapacity();

        	TextureAtlasSprite tex = mc.getTextureMapBlocks().getTextureExtry(tile.outputTank.getFluid().getFluid().getStill().toString());
        	
        	this.drawFluidWithTesselator(tex, k+OUTPUT_TANK_X+1, l+TANK_Y, TANK_W, TANK_H, px);
        }
		
        this.mc.getTextureManager().bindTexture(this.texture);
		
		this.drawTexturedModalRect(k + INPUT_TANK_X, l + TANK_Y, 176, 40, TANK_W+2, TANK_H+2);
		this.drawTexturedModalRect(k + OUTPUT_TANK_X, l + TANK_Y, 176, 40, TANK_W+2, TANK_H+2);
		
		if (this.tile.isWorking()) {
			//Minecraft.getMinecraft().getTextureManager().bindTexture(texture);
			int i1 = this.tile.getProgressScaled(36);
	        this.drawTexturedModalRect(k + 73, l + 17, 177, 1, 25, i1+1);
		}

		 if (this.tile.getCurrentFuelBufferMax()>0){
        	int s = 12-this.tile.getFuelBufferScaled(12);
        	this.drawTexturedModalRect(k+35, l+36+s, 188, 39+s, 6, 12-s);
        	
        }
	}

}
