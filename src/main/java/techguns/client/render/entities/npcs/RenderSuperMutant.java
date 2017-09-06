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
import techguns.entities.npcs.GenericNPC;
import techguns.entities.npcs.SuperMutantBasic;

public class RenderSuperMutant extends RenderLiving<SuperMutantBasic> {
	
	private static final ResourceLocation[] textures = {
			new ResourceLocation(Techguns.MODID, "textures/entity/supermutant_texture_1.png"),
			new ResourceLocation(Techguns.MODID, "textures/entity/supermutant_texture_2.png"),
			new ResourceLocation(Techguns.MODID, "textures/entity/supermutant_texture_3.png")
	};

	
	public RenderSuperMutant(RenderManager renderManagerIn) {
		super(renderManagerIn, new ModelSuperMutant(), 0.5f);
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
	protected ResourceLocation getEntityTexture(SuperMutantBasic entity) {
		return textures[entity.gettype()];
	}
	
}
