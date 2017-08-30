package techguns.client.render.entities.projectiles;

import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.entity.Entity;
import net.minecraft.util.ResourceLocation;
import techguns.Techguns;
import techguns.entities.projectiles.StoneBulletProjectile;

public class RenderStoneBulletProjectile extends RenderTextureProjectile<StoneBulletProjectile> {
	
    public RenderStoneBulletProjectile(RenderManager renderManager)
    {	
    	super(renderManager);
    	textureLoc = new ResourceLocation(Techguns.MODID,"textures/entity/handgunbullet.png");
    	scale=1.0f;
    	baseSize=0.1f;
    }
    
}
