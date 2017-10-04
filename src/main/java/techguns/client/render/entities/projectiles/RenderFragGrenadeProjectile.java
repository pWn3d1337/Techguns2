package techguns.client.render.entities.projectiles;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.util.ResourceLocation;
import techguns.Techguns;
import techguns.client.models.guns.ModelFragGrenade;
import techguns.client.render.item.RenderItemBase;
import techguns.entities.projectiles.FragGrenadeProjectile;

public class RenderFragGrenadeProjectile extends Render<FragGrenadeProjectile> {

	private ResourceLocation textureLoc = new ResourceLocation(Techguns.MODID,"textures/guns/frag_grenade_texture.png");
	private ModelBase model = new ModelFragGrenade(false);
	
	public RenderFragGrenadeProjectile(RenderManager renderManager) {
		super(renderManager);
	}

	@Override
	protected ResourceLocation getEntityTexture(FragGrenadeProjectile entity) {
		return textureLoc;
	}

	
	
	@Override
	public void doRender(FragGrenadeProjectile entity, double x, double y, double z, float entityYaw, float partialTicks) {
	

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