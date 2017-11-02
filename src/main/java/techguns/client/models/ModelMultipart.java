package techguns.client.models;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.client.renderer.block.model.ItemCameraTransforms;
import net.minecraft.entity.Entity;

public abstract class ModelMultipart extends ModelBase {

	@Override
	public void render(Entity entityIn, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch, float scale) {
		render(entityIn, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, scale, 100, -1.0f, ItemCameraTransforms.TransformType.GROUND, 0, 0.0f, 0f);
	}

	public abstract void render(Entity entityIn, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch, float scale, int ammoLeft,
			float reloadProgress, ItemCameraTransforms.TransformType transformType, int part, float fireProgress, float chargeProgress);

	/*
	 * public void setRotationAngles(float f, float f1, float f2, float f3,
	 * float f4, float f5, Entity e) { super.setRotationAngles(f, f1, f2, f3,
	 * f4, f5, e); }
	 */

	protected void setRotation(ModelRenderer model, float x, float y, float z) {
		model.rotateAngleX = x;
		model.rotateAngleY = y;
		model.rotateAngleZ = z;
	}

}
