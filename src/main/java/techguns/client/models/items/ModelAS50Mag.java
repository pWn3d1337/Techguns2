package techguns.client.models.items;

import net.minecraft.client.model.ModelRenderer;
import net.minecraft.client.renderer.block.model.ItemCameraTransforms.TransformType;
import net.minecraft.entity.Entity;
import techguns.client.models.ModelMultipart;

public class ModelAS50Mag extends ModelMultipart {

	// fields
	ModelRenderer Magazine01;
	ModelRenderer Magazine03;
	ModelRenderer Bullet2;
	ModelRenderer Magazine02;
	ModelRenderer Bullet;
	boolean empty;

	public ModelAS50Mag(boolean empty) {
		textureWidth = 64;
		textureHeight = 32;

		this.empty = empty;

		Magazine01 = new ModelRenderer(this, 0, 19);
		Magazine01.addBox(0F, 0F, 0F, 2, 2, 1);
		Magazine01.setRotationPoint(-1.5F, 7F, -0.5F);
		Magazine01.setTextureSize(256, 128);
		Magazine01.mirror = true;
		setRotation(Magazine01, 0F, 0F, 0F);
		Magazine03 = new ModelRenderer(this, 17, 10);
		Magazine03.addBox(0F, 0F, 0F, 2, 2, 11);
		Magazine03.setRotationPoint(-1.5F, 6F, -11F);
		Magazine03.setTextureSize(256, 128);
		Magazine03.mirror = true;
		setRotation(Magazine03, -0.0916298F, 0F, 0F);
		if (!empty) {
			Bullet2 = new ModelRenderer(this, 0, 0);
			Bullet2.addBox(0F, 0F, 0F, 1, 1, 5);
			Bullet2.setRotationPoint(-0.5F, -1.1F, -11F);
			Bullet2.setTextureSize(256, 128);
			Bullet2.mirror = true;
			setRotation(Bullet2, 0F, 0F, 0.7853982F);
		}
		Magazine02 = new ModelRenderer(this, 0, 12);
		Magazine02.addBox(0F, 0F, 0F, 2, 8, 12);
		Magazine02.setRotationPoint(-1.5F, 0F, -11.5F);
		Magazine02.setTextureSize(256, 128);
		Magazine02.mirror = true;
		setRotation(Magazine02, 0F, 0F, 0F);
		if (!empty) {
			Bullet = new ModelRenderer(this, 13, 0);
			Bullet.addBox(0F, 0F, 0F, 2, 2, 6);
			Bullet.setRotationPoint(-0.5F, -1.8F, -6F);
			Bullet.setTextureSize(256, 128);
			Bullet.mirror = true;
			setRotation(Bullet, 0F, 0F, 0.7853982F);
		}
	}

	@Override
	public void render(Entity entityIn, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch, float scale, int ammoLeft,
			float reloadProgress, TransformType transformType, int part, float fireProgress, float chargeProgress) {

		Magazine01.render(scale);
		Magazine03.render(scale);
		Magazine02.render(scale);
		if (!empty) {
			Bullet2.render(scale);
			Bullet.render(scale);
		}
	}
}
