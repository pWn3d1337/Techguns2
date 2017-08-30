package techguns.client.render.entities.npcs;

import net.minecraft.client.model.ModelBiped;
import net.minecraft.client.model.ModelZombie;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.entity.layers.LayerBipedArmor;
import net.minecraft.entity.monster.EntityZombie;
import net.minecraft.util.ResourceLocation;
import techguns.Techguns;
import techguns.client.models.npcs.ModelGenericNPC;
import techguns.entities.npcs.GenericNPC;
import techguns.entities.npcs.ZombieSoldier;

public class RenderZombieSoldier<T extends GenericNPC> extends RenderGenericNPC<ZombieSoldier> {

	
	private static final ResourceLocation TEXTURE = new ResourceLocation(Techguns.MODID,"textures/entity/zombie_soldier.png");

	
	public RenderZombieSoldier(RenderManager renderManagerIn) {
		super(renderManagerIn, new ModelGenericNPC(), 0.5f);
		LayerBipedArmor layerbipedarmor = new LayerBipedArmor(this)
        {
            protected void initArmor()
            {
                this.modelLeggings = new ModelGenericNPC(0.5F, true);
                this.modelArmor = new ModelGenericNPC(1.0F, true);
            }
        };
        this.addLayer(layerbipedarmor);
	}

	
	
	   @Override
	protected ResourceLocation getEntityTexture(GenericNPC entity) {
		return TEXTURE;
	}


	
}
