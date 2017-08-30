package techguns.client.render.tileentities;

import org.lwjgl.opengl.GLSync;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ResourceLocation;
import techguns.Techguns;
import techguns.client.models.machines.ModelFabricator;
import techguns.client.models.machines.ModelMachine;
import techguns.debug.Keybinds;
import techguns.tileentities.BasicMachineTileEnt;
import techguns.tileentities.FabricatorTileEntMaster;

public class RenderFabricator extends TileEntitySpecialRenderer<FabricatorTileEntMaster> {
	private EntityItem entItem = null;
	protected ModelMachine model = new ModelFabricator();
	protected ResourceLocation texture = new ResourceLocation(Techguns.MODID, "textures/blocks/fabricator.png");
	
	@Override
	public void render(FabricatorTileEntMaster te, double x, double y, double z, float partialTicks, int destroyStage,
			float alpha) {

		if (te != null && te.isFormed()) {

			float progress = te.getProgress();
			EnumFacing direction =  te.getMultiblockDirection();

		
			GlStateManager.pushMatrix();
			GlStateManager.translate( x + 0.5d, y + 1.5d, z + 0.5d);
			
			GlStateManager.pushMatrix();
	        GlStateManager.rotate(180F, 0.0F, 0.0F, 1.0F);
	        int dir = 0;
	        if(direction!=null){
	        	dir=direction.getHorizontalIndex();
	        }
	        GlStateManager.rotate(180.0f+dir*90.0f, 0.0F, 1.0F, 0.0F);
		
	        //Render Item
	        ItemStack itemStack = ItemStack.EMPTY;
			if (te.currentOperation!=null){
				
				if(progress<0.8f){
					itemStack=te.currentOperation.getItemInputI(FabricatorTileEntMaster.SLOT_PLATE);
					if(itemStack.isEmpty()) {
						itemStack=te.currentOperation.getItemInputI(FabricatorTileEntMaster.SLOT_INPUT1);
					}
				} else {
					itemStack=te.currentOperation.getItemOutput0();
				}
			} 
			      
	        
	        Minecraft.getMinecraft().renderEngine.bindTexture(texture);
	        
			this.model.render(null, 0f, 0f, 0f, 0f, 0f, 0.0625f, progress);
			
			if (!itemStack.isEmpty()) {
				itemStack.setCount(1);
				
				//int i = te.getWorld().getCombinedLight(te.getPos(), 0);
				
				 if((entItem == null) || entItem.getItem() != itemStack) {
					 entItem = new EntityItem(te.getWorld(), x, y, z, itemStack);
				 }
				 this.entItem.hoverStart = 0.0F;
				 GlStateManager.pushMatrix();
				// GlStateManager.rotate(-90, 0, 0, 1);
				 //GlStateManager.rotate(180, 0, 1, 0);
				 GlStateManager.rotate(+90, 1, 0, 0);
				 //Minecraft.getMinecraft().getRenderManager().doRenderEntity(this.entItem, 0.6, /*-1.25D +0.625D +0.9*/ 0.275, -0.5, 93.5F, 0.0f, false);
				 Minecraft.getMinecraft().getRenderManager().doRenderEntity(this.entItem, -0.5f, 0f, -0.6f, 93.5F, 0.0f, false);
				 GlStateManager.popMatrix();
				/**
				 * FIX LIGHTING AFTER ITEM RENDERING
				 */
				/*int j = i % 65536;
				int k = i / 65536;
				OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, (float) j, (float) k);
				GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);*/
			}	  
			
			GlStateManager.popMatrix();
			GlStateManager.popMatrix();
			
		}
		
	}
	
	

}
