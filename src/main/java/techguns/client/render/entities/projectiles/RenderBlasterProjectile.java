package techguns.client.render.entities.projectiles;

import javax.print.attribute.TextSyntax;

import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.util.ResourceLocation;
import techguns.Techguns;
import techguns.client.render.TGRenderHelper;
import techguns.client.render.TGRenderHelper.RenderType;
import techguns.entities.projectiles.BlasterProjectile;

public class RenderBlasterProjectile<T extends BlasterProjectile> extends RenderGenericProjectile<T> {
	private static ResourceLocation textureLoc = new ResourceLocation(Techguns.MODID,"textures/fx/laser3.png");
	
	public RenderBlasterProjectile(RenderManager renderManager) {
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


	
}
