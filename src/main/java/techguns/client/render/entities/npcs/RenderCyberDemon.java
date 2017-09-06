package techguns.client.render.entities.npcs;

import net.minecraft.client.model.ModelBiped;
import net.minecraft.client.model.ModelVillager;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.entity.layers.LayerBipedArmor;
import net.minecraft.entity.monster.EntityZombie;
import net.minecraft.util.ResourceLocation;
import techguns.Techguns;
import techguns.client.models.npcs.ModelGenericNPC;
import techguns.client.models.npcs.ModelCyberDemon;
import techguns.entities.npcs.GenericNPC;
import techguns.entities.npcs.CyberDemon;

public class RenderCyberDemon<T extends GenericNPC> extends RenderGenericNPC<CyberDemon> {

	
	private static final ResourceLocation TEXTURE = new ResourceLocation(Techguns.MODID,"textures/entity/cyberdemon.png");

	public RenderCyberDemon(RenderManager renderManagerIn) {
		super(renderManagerIn, new ModelCyberDemon(), 0.5f);
	}
	
	   @Override
	protected ResourceLocation getEntityTexture(GenericNPC entity) {
		return TEXTURE;
	}
	
}
