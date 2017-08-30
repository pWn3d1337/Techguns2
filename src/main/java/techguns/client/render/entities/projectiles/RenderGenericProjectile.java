package techguns.client.render.entities.projectiles;

import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.entity.Entity;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.client.registry.IRenderFactory;
import techguns.Techguns;
import techguns.entities.projectiles.GenericProjectile;

public class RenderGenericProjectile<T extends GenericProjectile> extends Render<T> {

	private static final ResourceLocation bulletTextures = new ResourceLocation(Techguns.MODID,"textures/entity/bullet1.png"); //previously was bullet.png

	public RenderGenericProjectile(RenderManager renderManager) {
		super(renderManager);
	}

	@Override
	protected ResourceLocation getEntityTexture(GenericProjectile entity) {
		return bulletTextures;
	}

	
	protected void enableGLStates() {
	    GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
        GlStateManager.disableLighting();
	}
	
	protected void disableGLStates() {
        GlStateManager.enableLighting();
	}
	
	@Override
	public void doRender(T entity, double x, double y, double z, float entityYaw, float partialTicks) {

		 if (entity.ticksExisted >=2 || (entity.ticksExisted<2 && partialTicks>0.25f)){
		    	
	    	 this.bindEntityTexture(entity);
	         GlStateManager.pushMatrix();
	         
	         this.enableGLStates();
	         
	         GlStateManager.translate((float)x, (float)y, (float)z);
	         GlStateManager.rotate(entity.rotationYaw-90, 0.0F, 1.0F, 0.0F);
	         GlStateManager.rotate(entity.rotationPitch, 0.0F, 0.0F, 1.0F);
	         Tessellator tessellator = Tessellator.getInstance();
	         BufferBuilder bufferbuilder = tessellator.getBuffer();
	         //byte b0 = 0;
//	         float u1 = 0.0F;
//	         float u2 = 0.5F;
//	         float v1 = (float)(0 + b0 * 10) / 32.0F;
//	         float v2 = (float)(5 + b0 * 10) / 32.0F;
	         float u1 = 0.0f;
	         float u2 = 1.0f;
	         float v1 = 0.0f;
	         float v2 = 1.0f;
	         //float f6 = 0.0F;
	         //float f7 = 0.15625F;
	         //float f8 = (float)(5 + b0 * 10) / 32.0F;
	         //float f9 = (float)(10 + b0 * 10) / 32.0F;
	         float f10 = 0.0125F;
	         GlStateManager.enableRescaleNormal();

	         GlStateManager.rotate(45.0F, 1.0F, 0.0F, 0.0F);
	         GlStateManager.scale(f10, f10, f10);

	         double length = 8.0D; //Actually, this is half of length/width
	         double width = 2.0D;
	         
	         for (int i = 0; i < 4; ++i)
	         {
	        	 GlStateManager.rotate(90.0F, 1.0F, 0.0F, 0.0F);
	        	 GlStateManager.glNormal3f(0.0F, 0.0F, f10);
	        	 

	        	 bufferbuilder.begin(7, DefaultVertexFormats.POSITION_TEX);
	        	 
	        	 bufferbuilder.pos(-length, -width, 0.0D).tex(u1,v1).endVertex();
	        	 bufferbuilder.pos(length, -width, 0.0D).tex(u2,v1).endVertex();
	        	 bufferbuilder.pos(length, width, 0.0D).tex(u2,v2).endVertex();
	        	 bufferbuilder.pos(-length, width, 0.0D).tex(u1,v2).endVertex();

	        	 tessellator.draw();
	         }

	         GlStateManager.disableRescaleNormal();
	         
	         this.disableGLStates();
	         
	         GlStateManager.popMatrix();
    	 }
		
	}
	
}
