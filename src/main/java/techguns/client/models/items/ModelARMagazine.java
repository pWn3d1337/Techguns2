package techguns.client.models.items;

import net.minecraft.client.model.ModelRenderer;
import net.minecraft.client.renderer.block.model.ItemCameraTransforms.TransformType;
import net.minecraft.entity.Entity;
import techguns.client.models.ModelMultipart;

public class ModelARMagazine extends ModelMultipart {

	// fields
	ModelRenderer Magazine02;
	ModelRenderer Magazine01;
	ModelRenderer Magazine03;
	ModelRenderer Magazine04;

	protected boolean empty=false;
	
	public ModelARMagazine(boolean empty) {
		textureWidth = 32;
		textureHeight = 32;

		this.empty=empty;
		
		Magazine02 = new ModelRenderer(this, 0, 0);
		Magazine02.addBox(0F, 0F, 0F, 2, 4, 6);
		Magazine02.setRotationPoint(-1.5F, 9F, -4.4F);
		Magazine02.setTextureSize(128, 64);
		Magazine02.mirror = true;
		setRotation(Magazine02, -0.1570796F, 0F, 0F);
		Magazine01 = new ModelRenderer(this, 0, 11);
		Magazine01.addBox(0F, 0F, 0F, 2, 5, 6);
		Magazine01.setRotationPoint(-1.5F, 5F, -4.5F);
		Magazine01.setTextureSize(128, 64);
		Magazine01.mirror = true;
		setRotation(Magazine01, 0F, 0F, 0F);
		Magazine03 = new ModelRenderer(this, 0, 11);
		Magazine03.addBox(0F, 0F, 0F, 2, 2, 6);
		Magazine03.setRotationPoint(-1.5F, 3F, -4.5F);
		Magazine03.setTextureSize(128, 64);
		Magazine03.mirror = true;
		setRotation(Magazine03, 0F, 0F, 0F);
		
		if (!empty){
			Magazine04 = new ModelRenderer(this, 0, 22);
			Magazine04.addBox(0F, 0F, 0F, 1, 1, 5);
			Magazine04.setRotationPoint(-1.25F, 3F, -4F);
			Magazine04.setTextureSize(128, 64);
			Magazine04.mirror = true;
			setRotation(Magazine04, 0F, 0F, -0.7853982F);
		}
	}

	@Override
	public void render(Entity entityIn, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch, float scale, int ammoLeft,
			float reloadProgress, TransformType transformType, int part, float fireProgress, float chargeProgress) {
		Magazine02.render(scale);
		Magazine01.render(scale);
		Magazine03.render(scale);
		if (!empty){
			Magazine04.render(scale);
		}
	}

}
