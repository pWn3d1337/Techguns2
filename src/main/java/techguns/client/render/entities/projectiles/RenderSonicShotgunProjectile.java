package techguns.client.render.entities.projectiles;

import java.util.Random;

import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.util.ResourceLocation;
import techguns.Techguns;
import techguns.client.render.TGRenderHelper;
import techguns.client.render.TGRenderHelper.RenderType;
import techguns.entities.projectiles.SonicShotgunProjectile;

public class RenderSonicShotgunProjectile extends RenderGenericProjectile<SonicShotgunProjectile> {

	private static final ResourceLocation bulletTexture = new ResourceLocation(Techguns.MODID,"textures/entity/bullet.png");
	private static final ResourceLocation waveTexture_A = new ResourceLocation(Techguns.MODID,"textures/fx/sonicwave4x4.png");
	private static final ResourceLocation waveTexture = new ResourceLocation(Techguns.MODID,"textures/fx/shockwave.png");

	
	public RenderSonicShotgunProjectile(RenderManager renderManager) {
		super(renderManager);
	}


	@Override
	public void doRender(SonicShotgunProjectile proj, double x, double y, double z, float entityYaw,
			float partialTicks) {

		if (proj.ticksExisted >= 3) {

			if (proj.mainProjectile) {
				Random rand = new Random(proj.getEntityId());

				GlStateManager.pushMatrix();
				GlStateManager.translate(x, y, z);

				GlStateManager.rotate(proj.rotationYaw - 90, 0.0F, 1.0F, 0.0F);
				GlStateManager.rotate(proj.rotationPitch, 0.0F, 0.0F, 1.0F);

				Tessellator tessellator = Tessellator.getInstance();

				GlStateManager.enableRescaleNormal();

				double d = ((float) proj.ticksExisted + partialTicks) * 0.3;
				TGRenderHelper.enableBlendMode(RenderType.ADDITIVE);
				;

				GlStateManager.rotate(90.0F, 1.0F, 0.0F, 0.0F);
				// GL11.glNormal3f(0.0F, 0.0F, 0.0125f); ????

				float prog = (float) Math.min(1.0, ((float) proj.ticksExisted + partialTicks) / 20.0f);

				// Render static waves

				float opacity = 1.0f - (float) (Math.pow(prog, 2.0));

				bindTexture(waveTexture);

				BufferBuilder buf = tessellator.getBuffer();
				buf.begin(7, DefaultVertexFormats.POSITION_TEX);

				for (int i = 0; i <= 5; i++) {
					float r = opacity * rand.nextFloat();
					float g = opacity * rand.nextFloat();
					float b = opacity * (0.5f + (rand.nextFloat() * 0.5f));

					GlStateManager.color(r, g, b, opacity);

					double offset = 2.5 - (double) i;
					double size = d * (0.5 + (rand.nextDouble()));

					drawWave(buf, size, offset);
				}

				tessellator.draw();

				TGRenderHelper.disableBlendMode(RenderType.ADDITIVE);
				GlStateManager.disableRescaleNormal();
				GlStateManager.popMatrix();

			} 
		}
	}

	private void drawWave(BufferBuilder buf, double size, double offset) { 
		buf.pos(offset, -size, -size).tex(0, 0).endVertex();
		buf.pos(offset, -size, size).tex(0, 1).endVertex();
		buf.pos(offset, size, size).tex(1, 1).endVertex();
		buf.pos(offset, size, -size).tex(1, 0).endVertex();
	}
	
	
}
