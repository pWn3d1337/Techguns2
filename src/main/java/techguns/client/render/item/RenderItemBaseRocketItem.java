package techguns.client.render.item;

import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.block.model.ItemCameraTransforms.TransformType;
import net.minecraft.util.ResourceLocation;
import techguns.client.models.ModelMultipart;

public class RenderItemBaseRocketItem extends RenderItemBase {

	public RenderItemBaseRocketItem(ModelMultipart model, ResourceLocation texture) {
		super(model, texture);
	}

	@Override
	protected void setBaseRotation(TransformType transform) {
		GlStateManager.rotate(-180.0f, 1.0f, 0, 0);
		GlStateManager.rotate(-90.0f, 0f, 1.0f, 0);
		GlStateManager.rotate(-90.0f, 0f, 0f, 1);
	}
	
}
