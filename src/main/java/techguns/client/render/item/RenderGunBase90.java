package techguns.client.render.item;

import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.block.model.ItemCameraTransforms.TransformType;
import techguns.client.models.ModelMultipart;

public class RenderGunBase90 extends RenderGunBase {

	public RenderGunBase90(ModelMultipart model, int parts) {
		super(model, parts);
	}

	@Override
	protected void setBaseRotation(TransformType transform) {
		GlStateManager.rotate(-180.0f, 1.0f, 0, 0);
		GlStateManager.rotate(-90.0f, 0f, 1.0f, 0);
	}
	
}
