package techguns.client.models.guns;

import net.minecraft.client.model.ModelRenderer;
import net.minecraft.client.renderer.block.model.ItemCameraTransforms.TransformType;
import net.minecraft.entity.Entity;
import techguns.client.models.ModelMultipart;

public class ModelRocketLauncher extends ModelMultipart {
	// fields
	ModelRenderer Shape1;
	ModelRenderer Shape2;
	ModelRenderer Shape3;
	ModelRenderer Shape4;
	ModelRenderer Shape5;
	ModelRenderer Shape6;
	ModelRenderer Shape7;
	ModelRenderer Shape8;
	ModelRenderer Shape9;
	ModelRenderer Shape10;
	ModelRenderer Shape11;
	ModelRenderer Shape12;
	ModelRenderer Shape13;
	ModelRenderer Shape14;
	ModelRenderer Shape15;
	ModelRenderer Shape16;
	ModelRenderer Shape17;
	ModelRenderer Shape18;
	ModelRenderer Shape19;
	ModelRenderer Shape20;
	ModelRenderer Shape22;
	ModelRenderer Shape21;

	ModelRocket rocket;
	
	public ModelRocketLauncher() {
		textureWidth = 128;
		textureHeight = 64;

		Shape1 = new ModelRenderer(this, 0, 28);
		Shape1.addBox(0F, 0F, 0F, 6, 1, 3);
		Shape1.setRotationPoint(20F, 3.5F, -1F);
		Shape1.setTextureSize(128, 64);
		Shape1.mirror = true;
		setRotation(Shape1, 0F, 0F, 0F);
		Shape2 = new ModelRenderer(this, 0, 55);
		Shape2.addBox(0F, 0F, 0F, 36, 3, 6);
		Shape2.setRotationPoint(-16F, -1F, -2.5F);
		Shape2.setTextureSize(128, 64);
		Shape2.mirror = true;
		setRotation(Shape2, 0F, 0F, 0F);
		Shape3 = new ModelRenderer(this, 0, 24);
		Shape3.addBox(0F, 0F, 0F, 6, 3, 1);
		Shape3.setRotationPoint(20F, -1F, -3.5F);
		Shape3.setTextureSize(128, 64);
		Shape3.mirror = true;
		setRotation(Shape3, 0F, 0F, 0F);
		Shape4 = new ModelRenderer(this, 0, 24);
		Shape4.addBox(0F, 0F, 0F, 6, 3, 1);
		Shape4.setRotationPoint(20F, -1F, 3.5F);
		Shape4.setTextureSize(128, 64);
		Shape4.mirror = true;
		setRotation(Shape4, 0F, 0F, 0F);
		Shape5 = new ModelRenderer(this, 0, 28);
		Shape5.addBox(0F, 0F, 0F, 6, 1, 3);
		Shape5.setRotationPoint(20F, -3.5F, -1F);
		Shape5.setTextureSize(128, 64);
		Shape5.mirror = true;
		setRotation(Shape5, 0F, 0F, 0F);
		Shape6 = new ModelRenderer(this, 1, 46);
		Shape6.addBox(0F, 0F, 0F, 36, 6, 3);
		Shape6.setRotationPoint(-16F, -2.5F, -1F);
		Shape6.setTextureSize(128, 64);
		Shape6.mirror = true;
		setRotation(Shape6, 0F, 0F, 0F);
		Shape7 = new ModelRenderer(this, 99, 22);
		Shape7.addBox(0F, 0F, 0F, 3, 4, 1);
		Shape7.setRotationPoint(-19F, -1.5F, 3.5F);
		Shape7.setTextureSize(128, 64);
		Shape7.mirror = true;
		setRotation(Shape7, 0F, 0F, 0F);
		Shape8 = new ModelRenderer(this, 0, 36);
		Shape8.addBox(0F, 0F, 0F, 36, 5, 5);
		Shape8.setRotationPoint(-16F, -2F, -2F);
		Shape8.setTextureSize(128, 64);
		Shape8.mirror = true;
		setRotation(Shape8, 0F, 0F, 0F);
		Shape9 = new ModelRenderer(this, 81, 20);
		Shape9.addBox(0F, 0F, 0F, 3, 6, 6);
		Shape9.setRotationPoint(-19F, -2.5F, -2.5F);
		Shape9.setTextureSize(128, 64);
		Shape9.mirror = true;
		setRotation(Shape9, 0F, 0F, 0F);
		Shape10 = new ModelRenderer(this, 99, 27);
		Shape10.addBox(0F, 0F, 0F, 3, 1, 4);
		Shape10.setRotationPoint(-19F, -3.5F, -1.5F);
		Shape10.setTextureSize(128, 64);
		Shape10.mirror = true;
		setRotation(Shape10, 0F, 0F, 0F);
		Shape11 = new ModelRenderer(this, 99, 27);
		Shape11.addBox(0F, 0F, 0F, 3, 1, 4);
		Shape11.setRotationPoint(-19F, 3.5F, -1.5F);
		Shape11.setTextureSize(128, 64);
		Shape11.mirror = true;
		setRotation(Shape11, 0F, 0F, 0F);
		Shape12 = new ModelRenderer(this, 99, 22);
		Shape12.addBox(0F, 0F, 0F, 3, 4, 1);
		Shape12.setRotationPoint(-19F, -1.5F, -3.5F);
		Shape12.setTextureSize(128, 64);
		Shape12.mirror = true;
		setRotation(Shape12, 0F, 0F, 0F);
		Shape13 = new ModelRenderer(this, 62, 22);
		Shape13.addBox(0F, 0F, 0F, 6, 5, 1);
		Shape13.setRotationPoint(6F, -2F, 3F);
		Shape13.setTextureSize(128, 64);
		Shape13.mirror = true;
		setRotation(Shape13, 0F, 0F, 0F);
		Shape14 = new ModelRenderer(this, 52, 23);
		Shape14.addBox(-3F, 0F, 0F, 3, 7, 2);
		Shape14.setRotationPoint(10F, 4F, -0.5F);
		Shape14.setTextureSize(128, 64);
		Shape14.mirror = true;
		setRotation(Shape14, 0F, 0F, 0.2974289F);
		Shape15 = new ModelRenderer(this, 62, 22);
		Shape15.addBox(0F, 0F, 0F, 6, 5, 1);
		Shape15.setRotationPoint(6F, -2F, -3F);
		Shape15.setTextureSize(128, 64);
		Shape15.mirror = true;
		setRotation(Shape15, 0F, 0F, 0F);

		Shape16 = new ModelRenderer(this, 35, 27);
		Shape16.addBox(0F, 0F, 0F, 1, 0, 2);
		Shape16.setRotationPoint(10F, -6F, -0.5F);
		Shape16.setTextureSize(128, 64);
		Shape16.mirror = true;
		setRotation(Shape16, 0F, 0F, 0F);
		Shape22 = new ModelRenderer(this, 37, 27);
		Shape22.addBox(0F, 0F, 0F, 1, 3, 0);
		Shape22.setRotationPoint(10F, -6F, -0.5F);
		Shape22.setTextureSize(128, 64);
		Shape22.mirror = true;
		setRotation(Shape22, 0F, 0F, 0F);
		Shape21 = new ModelRenderer(this, 37, 27);
		Shape21.addBox(0F, 0F, 0F, 1, 3, 0);
		Shape21.setRotationPoint(10F, -6F, 1.5F);
		Shape21.setTextureSize(128, 64);
		Shape21.mirror = true;
		setRotation(Shape21, 0F, 0F, 0F);

		Shape17 = new ModelRenderer(this, 52, 21);
		Shape17.addBox(0F, 0F, 0F, 2, 1, 1);
		Shape17.setRotationPoint(9F, 4F, 0F);
		Shape17.setTextureSize(128, 64);
		Shape17.mirror = true;
		setRotation(Shape17, 0F, 0F, 0F);
		Shape18 = new ModelRenderer(this, 30, 20);
		Shape18.addBox(0F, 0F, 0F, 6, 7, 5);
		Shape18.setRotationPoint(6F, -3F, -2F);
		Shape18.setTextureSize(128, 64);
		Shape18.mirror = true;
		setRotation(Shape18, 0F, 0F, 0F);
		Shape19 = new ModelRenderer(this, 62, 28);
		Shape19.addBox(0F, 0F, 0F, 3, 1, 3);
		Shape19.setRotationPoint(7F, -3.5F, -1F);
		Shape19.setTextureSize(128, 64);
		Shape19.mirror = true;
		setRotation(Shape19, 0F, 0F, 0F);
		Shape20 = new ModelRenderer(this, 104, 0);
		Shape20.addBox(0F, 0F, 0F, 6, 6, 6);
		Shape20.setRotationPoint(20F, -2.5F, -2.5F);
		Shape20.setTextureSize(128, 64);
		Shape20.mirror = true;
		setRotation(Shape20, 0F, 0F, 0F);
		
		rocket = new ModelRocket();
	}

	
	
	@Override
	public void render(Entity entityIn, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch, float scale, int ammoLeft,
			float reloadProgress, TransformType transformType, int part, float fireProgress, float chargeProgress) {
		
		if(part==0) {
			Shape1.render(scale);
			Shape2.render(scale);
			Shape3.render(scale);
			Shape4.render(scale);
			Shape5.render(scale);
			Shape6.render(scale);
			Shape7.render(scale);
			Shape8.render(scale);
			Shape9.render(scale);
			Shape10.render(scale);
			Shape11.render(scale);
			Shape12.render(scale);
			Shape13.render(scale);
			Shape14.render(scale);
			Shape15.render(scale);
			Shape16.render(scale);
			Shape21.render(scale);
			Shape22.render(scale);
			Shape17.render(scale);
			Shape18.render(scale);
			Shape19.render(scale);
			Shape20.render(scale);
		} else if(part==1) {
			if ((reloadProgress ==0 && ammoLeft > 0) || (reloadProgress > 0.5f)) {
				this.rocket.render(entityIn, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, scale, ammoLeft, reloadProgress, transformType, part, fireProgress, chargeProgress);
			}
		}
	}
	
	protected static class ModelRocket extends ModelMultipart {
	
		ModelRenderer R1;
		ModelRenderer R7;
		ModelRenderer R4;
		ModelRenderer R2;
		ModelRenderer R5;
		ModelRenderer R3;
		ModelRenderer R6;
		
		public ModelRocket() {
			super();
			textureWidth = 64;
			textureHeight = 32;
			
			R1 = new ModelRenderer(this, 0, 0);
			R1.addBox(0F, 0F, 0F, 6, 4, 4);
			R1.setRotationPoint(21F, -1.5F, -1.5F);
			R1.setTextureSize(128, 64);
			R1.mirror = true;
			setRotation(R1, 0F, 0F, 0F);
			R7 = new ModelRenderer(this, 30, 0);
			R7.addBox(0F, 0F, 0F, 2, 1, 6);
			R7.setRotationPoint(22.1F, 0F, -2.5F);
			R7.setTextureSize(128, 64);
			R7.mirror = true;
			setRotation(R7, 0F, 0F, 0F);
			R4 = new ModelRenderer(this, 12, 9);
			R4.addBox(0F, 0F, 0F, 4, 2, 5);
			R4.setRotationPoint(22F, -0.5F, -2F);
			R4.setTextureSize(128, 64);
			R4.mirror = true;
			setRotation(R4, 0F, 0F, 0F);
			R2 = new ModelRenderer(this, 0, 16);
			R2.addBox(0F, 0F, 0F, 10, 3, 3);
			R2.setRotationPoint(18F, -1F, -1F);
			R2.setTextureSize(128, 64);
			R2.mirror = true;
			setRotation(R2, 0F, 0F, 0F);
			R5 = new ModelRenderer(this, 0, 8);
			R5.addBox(0F, 0F, 0F, 4, 5, 2);
			R5.setRotationPoint(22F, -2F, -0.5F);
			R5.setTextureSize(128, 64);
			R5.mirror = true;
			setRotation(R5, 0F, 0F, 0F);
			R3 = new ModelRenderer(this, 20, 3);
			R3.addBox(0F, 0F, 0F, 1, 2, 2);
			R3.setRotationPoint(28F, -0.5F, -0.5F);
			R3.setTextureSize(128, 64);
			R3.mirror = true;
			setRotation(R3, 0F, 0F, 0F);
			R6 = new ModelRenderer(this, 30, 7);
			R6.addBox(0F, 0F, 0F, 2, 6, 1);
			R6.setRotationPoint(22.1F, -2.5F, 0F);
			R6.setTextureSize(128, 64);
			R6.mirror = true;
			setRotation(R6, 0F, 0F, 0F);
		}

		@Override
		public void render(Entity entityIn, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw,
				float headPitch, float scale, int ammoLeft, float reloadProgress, TransformType transformType, int part,
				float fireProgress, float chargeProgress) {
			R1.render(scale);
			R7.render(scale);
			R4.render(scale);
			R2.render(scale);
			R5.render(scale);
			R3.render(scale);
			R6.render(scale);
		}
		
	}

}
