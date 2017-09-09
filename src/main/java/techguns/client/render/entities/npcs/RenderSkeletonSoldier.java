package techguns.client.render.entities.npcs;

import net.minecraft.client.model.ModelBiped;
import net.minecraft.client.model.ModelZombie;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.entity.layers.LayerBipedArmor;
import net.minecraft.entity.monster.EntityZombie;
import net.minecraft.util.ResourceLocation;
import techguns.Techguns;
import techguns.client.models.npcs.ModelSkeletonSoldier;
import techguns.client.render.entities.LayerHeldItemTranslateGun;
import techguns.entities.npcs.GenericNPC;
import techguns.entities.npcs.SkeletonSoldier;

public class RenderSkeletonSoldier extends RenderGenericNPC<SkeletonSoldier> {

	
	private static final ResourceLocation TEXTURE = new ResourceLocation("textures/entity/skeleton/skeleton.png");

	
	public RenderSkeletonSoldier(RenderManager renderManagerIn) {
		super(renderManagerIn, new ModelSkeletonSoldier(), 0.5f);
		
		this.layerRenderers.remove(this.layerRenderers.size()-1);
		this.addLayer(new LayerHeldItemTranslateGun(this));
		
		LayerBipedArmor layerbipedarmor = new LayerBipedArmor(this)
        {
            protected void initArmor()
            {
                this.modelLeggings = new ModelSkeletonSoldier(0.5F);
                this.modelArmor = new ModelSkeletonSoldier(1.0F);
            }
        };
        this.addLayer(layerbipedarmor);
	}
	
	   @Override
	protected ResourceLocation getEntityTexture(SkeletonSoldier entity) {
		return TEXTURE;
	}
}
