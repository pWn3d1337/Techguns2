package techguns.client.render.entities.npcs;

import javax.annotation.Nonnull;

import net.minecraft.client.model.ModelBiped;
import net.minecraft.client.model.ModelZombie;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.entity.layers.LayerBipedArmor;
import net.minecraft.entity.monster.EntityZombie;
import net.minecraft.util.ResourceLocation;
import techguns.Techguns;
import techguns.client.models.npcs.ModelSuperMutant;
import techguns.client.render.entities.LayerHeldItemTranslateGun;
import techguns.entities.npcs.GenericNPC;
import techguns.entities.npcs.SuperMutantBasic;

public class RenderSuperMutant extends RenderGenericNPC<SuperMutantBasic> {
	
	private static final ResourceLocation[] textures = {
			new ResourceLocation(Techguns.MODID, "textures/entity/supermutant_texture_1.png"),
			new ResourceLocation(Techguns.MODID, "textures/entity/supermutant_texture_2.png"),
			new ResourceLocation(Techguns.MODID, "textures/entity/supermutant_texture_3.png")
	};

	
	public RenderSuperMutant(RenderManager renderManagerIn) {
		super(renderManagerIn, new ModelSuperMutant(), 0.5f);
		
		this.layerRenderers.remove(this.layerRenderers.size()-1);
		this.addLayer(new LayerHeldItemTranslateGun(this));
		
		LayerBipedArmor layerbipedarmor = new LayerBipedArmor(this)
        {
            protected void initArmor()
            {
                this.modelLeggings = new ModelSuperMutant();
                this.modelArmor = new ModelSuperMutant();
            }
        };
        this.addLayer(layerbipedarmor);
	}
	
	@Override
	public void doRender(SuperMutantBasic entity, double x, double y, double z, float entityYaw, float partialTicks) {
		super.doRender(entity, x, y+entity.getModelHeightOffset(), z, entityYaw, partialTicks);
	}

	@Override
	protected ResourceLocation getEntityTexture(SuperMutantBasic entity) {
		return textures[entity.gettype()];
	}

}
