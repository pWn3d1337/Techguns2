package techguns.client.render.entities.npcs;

import net.minecraft.client.model.ModelBiped;
import net.minecraft.client.renderer.entity.RenderBiped;
import net.minecraft.client.renderer.entity.RenderManager;
import techguns.entities.npcs.GenericNPC;

public class RenderGenericNPC<T extends GenericNPC> extends RenderBiped<GenericNPC>{

	public RenderGenericNPC(RenderManager renderManagerIn, ModelBiped modelBipedIn, float shadowSize) {
		super(renderManagerIn, modelBipedIn, shadowSize);
	}

}
