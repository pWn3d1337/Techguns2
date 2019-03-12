package techguns.client.render.entities.projectiles;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.entity.Entity;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import techguns.Techguns;
import techguns.client.models.projectiles.ModelRocket;
import techguns.entities.projectiles.GuidedMissileProjectileHV;
import techguns.entities.projectiles.RocketProjectileHV;
import techguns.entities.projectiles.RocketProjectileNuke;

@SideOnly(Side.CLIENT)
public class RenderRocketProjectile extends Render {

	private float scale;
	private ResourceLocation textureLoc = new ResourceLocation(Techguns.MODID,"textures/guns/rocket.png");
	private ResourceLocation textureLocNuke = new ResourceLocation(Techguns.MODID,"textures/guns/rocket_nuke.png");
	private ResourceLocation textureLocHV = new ResourceLocation(Techguns.MODID,"textures/guns/rocket_hv.png");
	
	private ModelBase model = new ModelRocket();

	public RenderRocketProjectile(RenderManager renderManager) {
		super(renderManager);
		//this.model = model;
		//this.textureLoc = new ResourceLocation(texture);
	}

	public void doRender(Entity par1Entity, double par2, double par4,
			double par6, float par8, float par9) {
		// par1Entity.readFromNBT(par1Entity.getEntityData());
		// float scale = par1Entity.getScale();

		// System.out.println("scale:"+scale);

		GlStateManager.pushMatrix();
		
		
		GlStateManager.translate(par2,par4,par6);

       	GlStateManager.rotate(par1Entity.prevRotationYaw + (par1Entity.rotationYaw - par1Entity.prevRotationYaw) * par9 - 90.0F, 0.0F, 1.0F, 0.0F);
       	GlStateManager.rotate(par1Entity.prevRotationPitch + (par1Entity.rotationPitch - par1Entity.prevRotationPitch) * par9, 0.0F, 0.0F, 1.0F);
		
		GlStateManager.scale(0.9f, 0.9f, 0.9f);
		
		this.bindEntityTexture(par1Entity);

		
		model.render(par1Entity, 0, 0, 0, 0, 0, 0.0625F);

		GlStateManager.popMatrix();
	}

	@Override
	protected ResourceLocation getEntityTexture(Entity entity) {
		if(entity instanceof RocketProjectileNuke) {
			return textureLocNuke;
		} else if (entity instanceof RocketProjectileHV || entity instanceof GuidedMissileProjectileHV) {
			return textureLocHV;
		}
		return textureLoc;
	}
}