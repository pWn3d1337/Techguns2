package techguns.client.render.tileentities;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.block.model.ItemCameraTransforms.TransformType;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.client.renderer.tileentity.TileEntityRendererDispatcher;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidStack;
import techguns.Techguns;
import techguns.client.render.TGRenderHelper;
import techguns.client.render.TGRenderHelper.RenderType;
import techguns.tileentities.ReactionChamberTileEntMaster;
import techguns.tileentities.operation.ReactionBeamFocus;

public class RenderReactionChamber extends TileEntitySpecialRenderer<ReactionChamberTileEntMaster>{

	protected static final ResourceLocation heatrayTexture = new ResourceLocation(Techguns.MODID, "textures/fx/heatray.png");
	  
	private static final double ONE_SIXTEENTH=0.0625;
	
	TessellatorCube fluidcube = new TessellatorCube();
	
	@SuppressWarnings("incomplete-switch")
	@Override
	public void render(ReactionChamberTileEntMaster te, double x, double y, double z, float partialTicks,
			int destroyStage, float alpha) {

		if(te.isFormed()) {
			BlockPos centerPos = te.getPos().offset(te.getMultiblockDirection()).up();
			
	
			ItemStack item = ItemStack.EMPTY;
			if(te.isWorking()) {
				item = te.getCurrentReaction().getItemInputI(0);
			} else {
				item = te.input.get();
			}
			
			//System.out.println("RENDER ITEM:"+item);
			if(!item.isEmpty()) {
				
				double px = x+(-te.getPos().getX()+centerPos.getX())+0.5d;
				double py = y+(-te.getPos().getY()+centerPos.getY())+0.5d;
				double pz = z+(-te.getPos().getZ()+centerPos.getZ())+0.5d;
				
				GlStateManager.pushMatrix();
				GlStateManager.translate( px,py,pz);
				GlStateManager.rotate(-90.0f*te.getMultiblockDirection().getOpposite().getHorizontalIndex(), 0f, 1f, 0f);

				Minecraft.getMinecraft().getRenderItem().renderItem(item, TransformType.GROUND);
				
				GlStateManager.popMatrix();
			}
			
			//Render Focus Effect
			if (te.isWorking()) {
				
				GlStateManager.pushMatrix();
				
				double px = x+(-te.getPos().getX()+centerPos.getX())+0.5d;
				double py = y+0.5d;
				double pz = z+(-te.getPos().getZ()+centerPos.getZ())+0.5d;
				GlStateManager.translate(px, py, pz);
	        	GlStateManager.depthMask(false);

				ItemStack stack  = te.getInventory().getStackInSlot(ReactionChamberTileEntMaster.SLOT_FOCUS);
				if (!stack.isEmpty()) {

					 ReactionBeamFocus focus = ReactionBeamFocus.getBeamFocus(stack);
					 renderBeamFocusEffect(focus, te, partialTicks);
				}
		
				GlStateManager.depthMask(true);
				GlStateManager.popMatrix();
			}
			
			//Render Fluid
			
			FluidStack liquid = te.inputTank.getFluid();
			if (liquid!=null){
				bindTexture(TextureMap.LOCATION_BLOCKS_TEXTURE);

				Tessellator tess = Tessellator.getInstance();
				
				BufferBuilder buf = tess.getBuffer();
				//liquidCube.drawCube(tess, 0, 1.5f, 0, 1, 2.5f, 1, false);
				
				double w = 1.875 * 0.5;
				double h1 = 0.5;
				double h2 = 2.9375;
				double level = (double)te.inputTank.getFluidAmount()/(double)te.inputTank.getCapacity();
				
				GlStateManager.pushMatrix();
				GlStateManager.translate(x, y, z);
				
				GlStateManager.enableAlpha();
				GlStateManager.enableBlend();
				GlStateManager.disableLighting();
				
				TextureAtlasSprite tex;
				if (te.isWorking()){
					tex = Minecraft.getMinecraft().getTextureMapBlocks().getTextureExtry(te.inputTank.getFluid().getFluid().getFlowing().toString());
				} else {
					tex = Minecraft.getMinecraft().getTextureMapBlocks().getTextureExtry(te.inputTank.getFluid().getFluid().getStill().toString());
				}
				
				Fluid f = te.inputTank.getFluid().getFluid();
				
				//set fluid color
				int clr = f.getColor(te.inputTank.getFluid());
				
				float r = (clr >> 16 & 0xFF) / 255f;
			    float g = (clr >> 8 & 0xFF) / 255f;
			    float b = (clr & 0xFF) / 255f;
				GlStateManager.color(r, g, b);
			    
				TGRenderHelper.enableFluidGlow(f.getLuminosity(te.inputTank.getFluid()));
				
				/*buf.begin(7, DefaultVertexFormats.POSITION_TEX_NORMAL);
				fluidcube.drawCube(buf, -w, h1, -w, w, (h2*level)+0.5, w, true,tex);
				
				tess.draw();*/
				
				double dx = 0; //(centerPos.getX()-te.getPos().getX())*0.5d;
				double dz = 0; //(centerPos.getZ()-te.getPos().getZ())*0.5d;
					
				switch(te.getMultiblockDirection()) {
				case EAST:
					dx=0.5+ONE_SIXTEENTH;
					dz=-0.5+ONE_SIXTEENTH;
					break;
				case WEST:
					dx=-1.5+ONE_SIXTEENTH;
					dz=-0.5+ONE_SIXTEENTH;
					break;
				case NORTH:
					dx=-0.5+ONE_SIXTEENTH;
					dz=-1.5+ONE_SIXTEENTH;
					break;
				case SOUTH:
					dx=-0.5+ONE_SIXTEENTH;
					dz=0.5+ONE_SIXTEENTH;
					break;
				}
				
				this.drawFluidCubeWithTesselator(tex, dx, h1, dz, 2*w, h2*level,h2);
				
				TGRenderHelper.disableFluidGlow();
				GlStateManager.color(1f, 1f, 1f, 1f);
				GlStateManager.enableLighting();
				GlStateManager.disableBlend();
				GlStateManager.disableAlpha();
				GlStateManager.popMatrix();
			}
		}
		
	}

	private void renderBeamFocusEffect(ReactionBeamFocus focus, ReactionChamberTileEntMaster tile, float ptt) {
		
		//TODO: different effect for other focus
		
		Tessellator tess = Tessellator.getInstance();
		
		//----------------
		//HEAT RAY
		TGRenderHelper.enableFXLighting();
		TGRenderHelper.enableBlendMode(RenderType.ADDITIVE);
		GlStateManager.disableCull();
		
		Minecraft.getMinecraft().getTextureManager().bindTexture(heatrayTexture);	
		
		double d = ((double)(tile.progress % 10)+ptt)/10.0;
		d = 0.875+(0.125*(1.0- Math.cos(Math.PI*d*2.0)));

		double w = 1.875 * 0.5*d;
		double h1 = 0.5;
		double h2 = 2.9375;		
		float b = (((float)tile.getIntensity())/10.0f) *(float)d-0.25f;
		
		double x1 = TileEntityRendererDispatcher.staticPlayerX; //Minecraft.getMinecraft().player.posX;
		double z1 = TileEntityRendererDispatcher.staticPlayerZ; //Minecraft.getMinecraft().player.posZ;
		
		BlockPos pos = tile.getPos().offset(tile.getMultiblockDirection());
		float angle = (float) (Math.atan2(pos.getX()+0.5d - x1, pos.getZ()+0.5d - z1) * 180.0 / Math.PI);

		GlStateManager.rotate(angle, 0f,1f,0f);

		BufferBuilder buf = tess.getBuffer();
		
		
		buf.begin(7, DefaultVertexFormats.POSITION_TEX);
		//buf.color(1f, 1f, 1f, b);
		buf.pos(-w, h1, 0.0D).tex(0, 1).endVertex();
		buf.pos(-w, h2, 0.0D).tex(0, 0).endVertex();
		buf.pos(w, h2, 0.0D).tex(1, 0).endVertex();
		buf.pos(w, h1, 0.0D).tex(1, 1).endVertex();
		tess.draw();
		
		GlStateManager.enableCull();
		TGRenderHelper.disableFXLighting();
		TGRenderHelper.disableBlendMode(RenderType.ADDITIVE);
		//------------------
		
	}
	
	protected void drawFluidCubeWithTesselator(TextureAtlasSprite tex, double x, double y, double z, double width, double height, double maxHeight){
		
		if (tex==null){
			return;
		}
		
	
		
        Tessellator tessellator = Tessellator.getInstance();
        BufferBuilder buffer = tessellator.getBuffer();
        
        buffer.begin(7, DefaultVertexFormats.POSITION_TEX);
		
        int interpV=16;
  
        System.out.println("Height:"+height+" MH:"+maxHeight);
        
        if(height < maxHeight*0.5d) {
        
            double factor = height/(maxHeight*0.5d);
    		interpV = (int) (factor*16);
        	
			this.drawFluidTankPlane(buffer, x, y, z, tex,width,height,0,interpV);
			this.drawFluidTankPlane(buffer, x, y, z, tex,0,height,width,interpV);
			this.drawFluidTankPlane(buffer, x+width, y, z+width, tex,-width,height,0,interpV);
			this.drawFluidTankPlane(buffer, x+width, y, z+width, tex,0,height,-width,interpV);
        } else {
        	
        	float mhalf = (float) (maxHeight*0.5f);
        	
        	this.drawFluidTankPlane(buffer, x, y, z, tex,width,mhalf,0,16);
			this.drawFluidTankPlane(buffer, x, y, z, tex,0,mhalf,width,16);
			this.drawFluidTankPlane(buffer, x+width, y, z+width, tex,-width,mhalf,0,16);
			this.drawFluidTankPlane(buffer, x+width, y, z+width, tex,0,mhalf,-width,16);
			
			double h = (height-mhalf);
			double factor = h/mhalf;
	    	interpV = (int) (factor*16);
			
			this.drawFluidTankPlane(buffer, x, y+mhalf, z, tex,width,h,0,interpV);
			this.drawFluidTankPlane(buffer, x, y+mhalf, z, tex,0,h,width,interpV);
			this.drawFluidTankPlane(buffer, x+width, y+mhalf, z+width, tex,-width,h,0,interpV);
			this.drawFluidTankPlane(buffer, x+width, y+mhalf, z+width, tex,0,h,-width,interpV);
        	
        }
		
		buffer.pos(x, height+0.5f, z).tex(tex.getMinU(), tex.getMinV()).endVertex();
		buffer.pos(x, height+0.5f, z+width).tex(tex.getMinU(), tex.getMaxV()).endVertex();
		buffer.pos(x+width, height+0.5f, z+width).tex(tex.getMaxU(), tex.getMaxV()).endVertex();
		buffer.pos(x+width, height+0.5f, z).tex(tex.getMaxU(), tex.getMinV()).endVertex();

        tessellator.draw();
	}
	
	protected void drawFluidTankPlane(BufferBuilder buffer, double x, double y, double z, TextureAtlasSprite icon, double w_x, double h, double w_z, int interp_v)
    {   
        buffer.pos((double)(x + 0), (double)(y + h), (double)z+w_z).tex((double)icon.getMinU(), (double)icon.getInterpolatedV(interp_v)).endVertex();
        buffer.pos((double)(x + w_x), (double)(y + h), (double)z).tex((double)icon.getMaxU(), (double)icon.getInterpolatedV(interp_v)).endVertex();
        buffer.pos((double)(x + w_x), (double)(y + 0), (double)z).tex((double)icon.getMaxU(), (double)icon.getMinV()).endVertex();
        buffer.pos((double)(x + 0), (double)(y + 0), (double)z+w_z).tex((double)icon.getMinU(), (double)icon.getMinV()).endVertex();
    }
}
