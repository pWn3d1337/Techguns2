package techguns.client.models.guns;

import net.minecraft.client.model.ModelRenderer;
import net.minecraft.client.renderer.block.model.ItemCameraTransforms.TransformType;
import net.minecraft.entity.Entity;
import techguns.client.models.ModelMultipart;

public class ModelStielgranate extends ModelMultipart {

	// fields
	ModelRenderer Granate01;
	ModelRenderer Stiel01;
	ModelRenderer Stiel02;

	public ModelStielgranate() {
		textureWidth = 32;
		textureHeight = 32;

		Granate01 = new ModelRenderer(this, 0, 5);
		Granate01.addBox(0F, 0F, 0F, 2, 12, 2);
		Granate01.setRotationPoint(-1F, -22F, 0.5F);
		Granate01.setTextureSize(32, 32);
		Granate01.mirror = true;
		setRotation(Granate01, 0F, 0.7853982F, 0F);
		Stiel01 = new ModelRenderer(this, 13, 0);
		Stiel01.addBox(0F, 0F, 0F, 4, 5, 4);
		Stiel01.setRotationPoint(-1.5F, -27F, -1.5F);
		Stiel01.setTextureSize(32, 32);
		Stiel01.mirror = true;
		setRotation(Stiel01, 0F, 0F, 0F);
		Stiel02 = new ModelRenderer(this, 0, 0);
		Stiel02.addBox(0F, 0F, 0F, 3, 1, 3);
		Stiel02.setRotationPoint(-1F, -10F, -1F);
		Stiel02.setTextureSize(32, 32);
		Stiel02.mirror = true;
		setRotation(Stiel02, 0F, 0F, 0F);
	}

	@Override
	public void render(Entity entityIn, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch, float scale, int ammoLeft,
			float reloadProgress, TransformType transformType, int part, float fireProgress, float chargeProgress) {
		Granate01.render(scale);
		Stiel01.render(scale);
		Stiel02.render(scale);
	}

}
