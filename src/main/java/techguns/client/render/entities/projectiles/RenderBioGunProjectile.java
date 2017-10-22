package techguns.client.render.entities.projectiles;

import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.util.ResourceLocation;
import techguns.Techguns;
import techguns.client.render.TGRenderHelper;
import techguns.client.render.TGRenderHelper.RenderType;
import techguns.entities.projectiles.BioGunProjectile;

public class RenderBioGunProjectile extends RenderTextureProjectile<BioGunProjectile>{

	public RenderBioGunProjectile(RenderManager renderManager) {
		super(renderManager);
		textureLoc = new ResourceLocation(Techguns.MODID,"textures/entity/bioblob.png");
		baseSize=0.15f;
		scale=1.0f;
	}

	@Override
	public void doRender(BioGunProjectile entity, double x, double y, double z, float entityYaw, float partialTicks) {
		float scale = 1.0f;
		switch (entity.level) {
		case 1:
			scale = 1.25f;
			break;
		case 2:
			scale = 2.5f;
			break;
		case 3:
			scale = 4.0f;
			break;
		}
		
		  GlStateManager.pushMatrix();

		  GlStateManager.translate(x, y, z);
		  GlStateManager.enableRescaleNormal();
		  GlStateManager.scale(baseSize*scale, baseSize*scale, baseSize*scale);
		  
		  //GlStateManager.disableLighting();
		  TGRenderHelper.enableBlendMode(RenderType.ADDITIVE);
		  
          this.bindEntityTexture(entity);
          Tessellator tessellator = Tessellator.getInstance();
          this.drawProjectile(tessellator);
          
          TGRenderHelper.disableBlendMode(RenderType.ADDITIVE);
          
          //GlStateManager.enableLighting();
          
          GlStateManager.disableRescaleNormal();
          GlStateManager.popMatrix();
		
	}
}
