package techguns.client.render.entities.npcs;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.util.ResourceLocation;
import techguns.client.models.npcs.ModelGhastling;
import techguns.entities.npcs.Ghastling;

public class RenderGhastling extends RenderLiving<Ghastling> {

	private static final ResourceLocation GHAST_TEXTURES = new ResourceLocation("textures/entity/ghast/ghast.png");
	private static final ResourceLocation GHAST_SHOOTING_TEXTURES = new ResourceLocation("textures/entity/ghast/ghast_shooting.png");

	
	public RenderGhastling(RenderManager rendermanagerIn) {
		super(rendermanagerIn, new ModelGhastling(), 0.25f);
	}

	@Override
	protected ResourceLocation getEntityTexture(Ghastling entity) {
		 return entity.isAttacking() ? GHAST_SHOOTING_TEXTURES : GHAST_TEXTURES;
	}
}
