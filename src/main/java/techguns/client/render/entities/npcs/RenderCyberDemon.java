package techguns.client.render.entities.npcs;

import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.util.ResourceLocation;
import techguns.Techguns;
import techguns.client.models.npcs.ModelCyberDemon;
import techguns.client.render.entities.LayerHeldItemTranslateGun;
import techguns.entities.npcs.CyberDemon;

public class RenderCyberDemon extends RenderGenericNPC<CyberDemon> {

	
	private static final ResourceLocation TEXTURE = new ResourceLocation(Techguns.MODID,"textures/entity/cyberdemon.png");

	public RenderCyberDemon(RenderManager renderManagerIn) {
		super(renderManagerIn, new ModelCyberDemon(), 0.5f);
		this.layerRenderers.remove(this.layerRenderers.size()-1);
		this.addLayer(new LayerHeldItemTranslateGun(this));
	}
	
	   @Override
	protected ResourceLocation getEntityTexture(CyberDemon entity) {
		return TEXTURE;
	}
	
}
