package techguns.client.render.entities.npcs;

import net.minecraft.client.model.ModelBiped;
import net.minecraft.client.model.ModelVillager;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.entity.layers.LayerBipedArmor;
import net.minecraft.entity.monster.EntityZombie;
import net.minecraft.util.ResourceLocation;
import techguns.Techguns;
import techguns.client.models.npcs.ModelGenericNPC;
import techguns.client.render.entities.LayerHeldItemTranslateGun;
import techguns.client.models.npcs.ModelCyberDemon;
import techguns.entities.npcs.GenericNPC;
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
