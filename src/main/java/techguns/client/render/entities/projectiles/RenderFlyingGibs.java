package techguns.client.render.entities.projectiles;

import java.lang.reflect.InvocationTargetException;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RenderLivingBase;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.ResourceLocation;
import techguns.client.particle.DeathEffect;
import techguns.client.particle.DeathEffect.GoreData;
import techguns.entities.projectiles.FlyingGibs;

public class RenderFlyingGibs extends Render<FlyingGibs> {

	public RenderFlyingGibs(RenderManager renderManager) {
		super(renderManager);
	}

	@Override
	public void doRender(FlyingGibs par1entity, double x, double y, double z, float par8, float partialTickTime) {

		//FlyingGibs entity = (FlyingGibs) par1entity;
		EntityLivingBase entity = par1entity.entity;

		// this.bindEntityTexture(entity);

		GoreData data = DeathEffect.getGoreData(entity.getClass());

		float scale = 1.0f; // data.scale;

		// Random rand = new Random(entity.getEntityId()+entity.bodypart);
		// scale*=
		// data.minPartScale+(rand.nextFloat()*(data.maxPartScale-data.minPartScale));

		// if (entity.entity.isChild()) {
		// scale*= 0.5f;
		// }

		

		if (data.model != null) {

			//model = new ModelGibs()

			GlStateManager.pushMatrix();
			
			Render render = Minecraft.getMinecraft().getRenderManager().entityRenderMap.get(par1entity.entity.getClass());
			if (render instanceof RenderLivingBase) {
				try {
					if (data.texture == null) {
						DeathEffectEntityRenderer.bindEntityTexture(render, par1entity.entity);
					}else {
						Minecraft.getMinecraft().getRenderManager().renderEngine.bindTexture(data.texture);
					}
					DeathEffectEntityRenderer.preRenderCallback((RenderLivingBase) render, par1entity.entity,
							partialTickTime);
					
				} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
					e.printStackTrace();
				}
			}
			
			GlStateManager.translate(x, y, z);

			float angle = 0.0f;
			float rot_angle = 90.0f;
			if (par1entity.onGround) {
				angle = 5 + ((float) par1entity.hitGroundTTL / (float) par1entity.maxTimeToLive) * 15.0f;
				rot_angle += ((float) (par1entity.maxTimeToLive - par1entity.hitGroundTTL) * angle);

				// rot_angle += (360.0-rot_angle) * (double)(entity.hitGroundTTL-5) /
				// (double)entity.maxTimeToLive;

//				if (par1entity.hitGroundTTL - 20 > par1entity.timeToLive) {
//					float offsetY = ((float) ((par1entity.hitGroundTTL - 20) - par1entity.timeToLive) + partialTickTime)
//							* -0.05f;
//					GlStateManager.translate(0.0f, offsetY, 0.0f);
//				}
				
				if (par1entity.timeToLive <= 20) {
					float offsetY = ((20-par1entity.timeToLive) + partialTickTime) * -0.05f;
					GlStateManager.translate(0.0f, offsetY, 0.0f);
				}

			} else {
				angle = 5 + ((float) par1entity.timeToLive / (float) par1entity.maxTimeToLive) * 15.0f;
				rot_angle += ((float) par1entity.ticksExisted + partialTickTime) * angle;
			}

			GlStateManager.rotate((float) rot_angle, (float) par1entity.rotationAxis.x, (float) par1entity.rotationAxis.y,
					(float) par1entity.rotationAxis.z);

			GlStateManager.disableCull();

			data.model.render(par1entity, 0.0625f * scale, par1entity.bodypart);

			GlStateManager.enableCull();

			GlStateManager.popMatrix();
		}

	}

	@Override
	protected ResourceLocation getEntityTexture(FlyingGibs entity) {
		return DeathEffect.getGoreData((entity).entity.getClass()).texture;

	}

}
