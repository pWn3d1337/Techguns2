package techguns.client.render.entities.projectiles;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.entity.Entity;
import net.minecraft.util.ResourceLocation;
import techguns.Techguns;
import techguns.client.models.guns.ModelStielgranate;
import techguns.client.models.projectiles.ModelRocket;
import techguns.client.render.item.RenderItemBase;
import techguns.debug.Keybinds;
import techguns.entities.projectiles.GrenadeProjectile;

public class RenderGrenadeProjectile extends Render<GrenadeProjectile> {

	private ResourceLocation textureLoc = new ResourceLocation(Techguns.MODID,"textures/guns/stielgranate.png");
	private ModelBase model = new ModelStielgranate();
	
	public RenderGrenadeProjectile(RenderManager renderManager) {
		super(renderManager);
	}

	@Override
	protected ResourceLocation getEntityTexture(GrenadeProjectile entity) {
		return textureLoc;
	}

	
	
	@Override
	public void doRender(GrenadeProjectile entity, double x, double y, double z, float entityYaw, float partialTicks) {
	

		GlStateManager.pushMatrix();
		
		
		GlStateManager.translate(x,y,z);

       	GlStateManager.rotate(entity.prevRotationYaw + (entity.rotationYaw - entity.prevRotationYaw) * partialTicks - 90.0F, 0.0F, 1.0F, 0.0F);
       	GlStateManager.rotate(entity.prevRotationPitch + (entity.rotationPitch - entity.prevRotationPitch) * partialTicks, 0.0F, 0.0F, 1.0F);
		
       	
        int rotate = entity.ticksExisted % 20;
        float f = (rotate+partialTicks)/20.0f;
       	
       	GlStateManager.rotate(-360.0f*f, 0f, 0f, 1f);
       	
		GlStateManager.scale(0.8f, 0.8f, 0.8f);
		
		GlStateManager.rotate(-180.0f, 1.0f, 0, 0);
		GlStateManager.rotate(180.0f, 0f, 1.0f, 0);
		
		GlStateManager.translate(-RenderItemBase.SCALE*0.5f, 0.83f, -RenderItemBase.SCALE);
		
		this.bindEntityTexture(entity);
		
		model.render(entity, 0, 0, 0, 0, 0, 0.0625F);

		GlStateManager.popMatrix();
	}
	
	
}
