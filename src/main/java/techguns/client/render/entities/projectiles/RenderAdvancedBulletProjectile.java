package techguns.client.render.entities.projectiles;

import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.util.ResourceLocation;
import techguns.Techguns;
import techguns.client.render.TGRenderHelper;
import techguns.client.render.TGRenderHelper.RenderType;
import techguns.entities.projectiles.AdvancedBulletProjectile;
import techguns.entities.projectiles.GenericProjectile;

public class RenderAdvancedBulletProjectile<T extends AdvancedBulletProjectile> extends RenderGenericProjectile<T> {

	private static ResourceLocation textureLoc = new ResourceLocation(Techguns.MODID,"textures/entity/bullet_blue.png");
	
	public RenderAdvancedBulletProjectile(RenderManager renderManager) {
		super(renderManager);
	}

	@Override
	protected ResourceLocation getEntityTexture(T entity) {
		return textureLoc;
	}

	@Override
	protected void enableGLStates() {
	    GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
		TGRenderHelper.enableBlendMode(RenderType.ADDITIVE);
	}

	@Override
	protected void disableGLStates() {
		TGRenderHelper.disableBlendMode(RenderType.ADDITIVE);
	}

	@Override
	public void doRender(T entity, double x, double y, double z, float entityYaw, float partialTicks) {
		 
		 float delay = 2.5f / ((GenericProjectile)entity).speed;
		
		 if ((float)entity.ticksExisted + partialTicks > delay){
		    	
	    	 this.bindEntityTexture(entity);
	         GlStateManager.pushMatrix();
	         
	         this.enableGLStates();
	         
	         GlStateManager.translate((float)x, (float)y, (float)z);
	         GlStateManager.rotate(entity.rotationYaw-90, 0.0F, 1.0F, 0.0F);
	         GlStateManager.rotate(entity.rotationPitch, 0.0F, 0.0F, 1.0F);
	         Tessellator tessellator = Tessellator.getInstance();
	         BufferBuilder bufferbuilder = tessellator.getBuffer();
	         float u1 = 0.0f;
	         float u2 = 1.0f;
	         float v1 = 0.0f;
	         float v2 = 1.0f;
	         float f10 = 0.0125F;
	         GlStateManager.enableRescaleNormal();

	         GlStateManager.rotate(45.0F, 1.0F, 0.0F, 0.0F);
	         GlStateManager.scale(f10, f10, f10);

	         double length = 16.0D; //Actually, this is half of length/width
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
