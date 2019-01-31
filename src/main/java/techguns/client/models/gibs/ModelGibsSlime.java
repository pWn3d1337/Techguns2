package techguns.client.models.gibs;

import java.util.Arrays;

import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;

public class ModelGibsSlime extends ModelGibs {

	public ModelRenderer[] gibs;
	
	public ModelGibsSlime() {
		super();
		gibs = new ModelRenderer[] {
				addGib(),
				addGib(),
				addGib(),
				addGib(),
				addGib(),
				addGib(),
				addGib(),
				addGib()
		};
	}

	protected ModelRenderer addGib() {
		ModelRenderer r = new ModelRenderer(this,0,0);
		r.addBox(0, 0, 0, 0, 0, 0);
		r.setRotationPoint(0, 0, 0);
		return r;
	}
	
	@Override
	public void render(Entity entityIn, float scale, int part) {
		gibs[part].render(scale);
	}

	@Override
	public int getNumGibs() {
		return gibs.length;
	}

}
