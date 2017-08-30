package techguns.client.models.gibs;

import net.minecraft.client.model.ModelBase;
import net.minecraft.entity.Entity;

public abstract class ModelGibs extends ModelBase{
	
	public abstract void render(Entity entityIn, float scale, int part);
	
	public abstract int getNumGibs();
}
