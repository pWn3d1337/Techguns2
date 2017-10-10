package techguns.client.render.entities.projectiles;

import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.entity.Entity;
import net.minecraft.util.ResourceLocation;

public abstract class RenderTextureProjectile<T extends Entity> extends Render<T> {

	protected ResourceLocation textureLoc;
	protected float baseSize=0.1f;
	protected float scale=1.0f;
	
	public RenderTextureProjectile(RenderManager renderManager) {
		super(renderManager);
	}
	
	@Override
	public void doRender(T entity, double x, double y, double z, float entityYaw, float partialTicks) {
		if (textureLoc!=null) {
		  GlStateManager.pushMatrix();
		  GlStateManager.translate(x, y, z);
		  GlStateManager.enableRescaleNormal();
		  GlStateManager.scale(baseSize*scale, baseSize*scale, baseSize*scale);
		  GlStateManager.disableLighting();
          this.bindEntityTexture(entity);
          Tessellator tessellator = Tessellator.getInstance();
          this.drawProjectile(tessellator);
          GlStateManager.enableLighting();
          GlStateManager.disableRescaleNormal();
          GlStateManager.popMatrix();
		}
	}
	
	protected void drawProjectile(Tessellator tessellator)
    {
        float f = 0.f;
        float f1 = 1.f;
        float f2 = 0.f;
        float f3 = 1.f;
   
        float f4 = 1.0F;
        float f5 = 0.5F;
        float f6 = 0.25F;
        GlStateManager.rotate(180.0F - this.renderManager.playerViewY, 0.0F, 1.0F, 0.0F);
        GlStateManager.rotate(-this.renderManager.playerViewX, 1.0F, 0.0F, 0.0F);
        
        BufferBuilder bufferbuilder = tessellator.getBuffer();
        
        bufferbuilder.begin(7, DefaultVertexFormats.POSITION_TEX);
        bufferbuilder.pos((0.0F - f5), (0.0F - f6), 0.0f).tex(f, f3).endVertex();
        bufferbuilder.pos((f4 - f5), (0.0F - f6), 0.0f).tex(f1, f3).endVertex();
        bufferbuilder.pos((f4 - f5), (f4 - f6), 0.0f).tex(f1, f2).endVertex();
        bufferbuilder.pos((0.0f - f5), (f4 - f6), 0.0f).tex(f, f2).endVertex();
        
        tessellator.draw();
    }
	
	@Override
	protected ResourceLocation getEntityTexture(T entity) {
		return textureLoc;
	}

}
