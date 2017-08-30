package techguns.client.render.item;

import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.block.model.ItemCameraTransforms.TransformType;
import techguns.client.models.ModelMultipart;

public class RenderGunBaseObj extends RenderGunBase {

	private float rotY;
	
	public RenderGunBaseObj(ModelMultipart model, int parts, float rotY) {
		super(model, parts);
		this.rotY=rotY;
	}

	@Override
	protected void setBaseRotation(TransformType transform) {
		GlStateManager.rotate(rotY, 0f, 1.0f, 0);
	}
	
}
