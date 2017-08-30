package techguns.client.models.machines;

import net.minecraft.client.model.ModelRenderer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.Entity;

public class ModelAmmoPress extends ModelMachine {
	// fields
	ModelRenderer Shape1;
	ModelRenderer Shape4;
	ModelRenderer Shape5;
	ModelRenderer Shape7;
	ModelRenderer Shape8;
	ModelRenderer Shape10;
	ModelRenderer Shape11;
	ModelRenderer Shape12;
	ModelRenderer Shape13;
	ModelRenderer Shape14;
	ModelRenderer MetalPiece;
	ModelRenderer Shape17;
	ModelRenderer Shape18;
	ModelRenderer Shape19;
	ModelRenderer Shape20;
	ModelRenderer Shape21;
	ModelRenderer Shape22;
	ModelRenderer Shape23;
	ModelRenderer Shape24;
	ModelRenderer Shape26;
	ModelRenderer Shape27;
	ModelRenderer Shape28;
	ModelRenderer Shape29;
	ModelRenderer Shape30;
	ModelRenderer Shape31;
	ModelRenderer pb2;
	ModelRenderer Shape32;
	ModelRenderer Shape33;
	ModelRenderer pb1;
	ModelRenderer Shape34;
	ModelRenderer Shape35;
	ModelRenderer pa1;
	ModelRenderer pa2;
	ModelRenderer Shape36;
	ModelRenderer Shape37;
	ModelRenderer Shape38;
	ModelRenderer pc1;
	ModelRenderer bullet1;
	ModelRenderer bullet3;
	ModelRenderer bullet2;

	public ModelAmmoPress() {
		textureWidth = 128;
		textureHeight = 64;

		Shape1 = new ModelRenderer(this, 48, 36);
		Shape1.addBox(0F, 0F, 0F, 3, 4, 10);
		Shape1.setRotationPoint(-8F, 8F, -5F);
		Shape1.setTextureSize(128, 64);
		Shape1.mirror = true;
		setRotation(Shape1, 0F, 0F, 0F);
		Shape4 = new ModelRenderer(this, 66, 0);
		Shape4.addBox(-1.5F, 0F, -4F, 2, 8, 8);
		Shape4.setRotationPoint(0F, 12F, -6F);
		Shape4.setTextureSize(128, 64);
		Shape4.mirror = true;
		setRotation(Shape4, 0F, -1.570796F, 0F);
		Shape5 = new ModelRenderer(this, 55, 0);
		Shape5.addBox(0F, 0F, 0F, 1, 1, 3);
		Shape5.setRotationPoint(6.5F, 9.5F, -7.5F);
		Shape5.setTextureSize(128, 64);
		Shape5.mirror = true;
		setRotation(Shape5, 0F, 0F, 0F);
		Shape7 = new ModelRenderer(this, 50, 0);
		Shape7.addBox(0F, 0F, 0F, 1, 12, 1);
		Shape7.setRotationPoint(6.5F, 10.5F, -7.5F);
		Shape7.setTextureSize(128, 64);
		Shape7.mirror = true;
		setRotation(Shape7, 0F, 0F, 0F);
		Shape8 = new ModelRenderer(this, 0, 0);
		Shape8.addBox(0F, 0F, 0F, 16, 2, 16);
		Shape8.setRotationPoint(-8F, 22F, -8F);
		Shape8.setTextureSize(128, 64);
		Shape8.mirror = true;
		setRotation(Shape8, 0F, 0F, 0F);
		Shape10 = new ModelRenderer(this, 38, 25);
		Shape10.addBox(-2F, -2F, -3F, 3, 6, 6);
		Shape10.setRotationPoint(0F, 15F, -6F);
		Shape10.setTextureSize(128, 64);
		Shape10.mirror = true;
		setRotation(Shape10, 0F, -1.570796F, 0F);
		Shape11 = new ModelRenderer(this, 51, 18);
		Shape11.addBox(0F, 0F, 0F, 3, 2, 10);
		Shape11.setRotationPoint(-8F, 20F, -5F);
		Shape11.setTextureSize(128, 64);
		Shape11.mirror = true;
		setRotation(Shape11, 0F, 0F, 0F);
		Shape12 = new ModelRenderer(this, 65, 31);
		Shape12.addBox(0F, 0F, 0F, 10, 2, 12);
		Shape12.setRotationPoint(-5F, 20F, -6F);
		Shape12.setTextureSize(128, 64);
		Shape12.mirror = true;
		setRotation(Shape12, 0F, 0F, 0F);
		Shape13 = new ModelRenderer(this, 0, 18);
		Shape13.addBox(0F, 0F, 0F, 1, 3, 8);
		Shape13.setRotationPoint(4F, 8.5F, -4F);
		Shape13.setTextureSize(128, 64);
		Shape13.mirror = true;
		setRotation(Shape13, 0F, 0F, 0F);
		Shape14 = new ModelRenderer(this, 31, 19);
		Shape14.addBox(-2F, -2F, -2F, 1, 6, 6);
		Shape14.setRotationPoint(9F, 15F, -1F);
		Shape14.setTextureSize(128, 64);
		Shape14.mirror = true;
		setRotation(Shape14, 0F, 0F, 0F);
		MetalPiece = new ModelRenderer(this, 110, 46);
		MetalPiece.addBox(-0.5F, 0F, 0F, 1, 5, 5);
		MetalPiece.setRotationPoint(0F, 15.5F, -2.5F);
		MetalPiece.setTextureSize(128, 64);
		MetalPiece.mirror = true;
		setRotation(MetalPiece, 0F, 0F, 0F);
		Shape17 = new ModelRenderer(this, 50, 0);
		Shape17.addBox(0F, 0F, 0F, 1, 12, 1);
		Shape17.setRotationPoint(6.5F, 10.5F, 6.5F);
		Shape17.setTextureSize(128, 64);
		Shape17.mirror = true;
		setRotation(Shape17, 0F, 0F, 0F);
		Shape18 = new ModelRenderer(this, 55, 0);
		Shape18.addBox(0F, 0F, 0F, 1, 1, 3);
		Shape18.setRotationPoint(6.5F, 9.5F, 4.5F);
		Shape18.setTextureSize(128, 64);
		Shape18.mirror = true;
		setRotation(Shape18, 0F, 0F, 0F);
		Shape19 = new ModelRenderer(this, 53, 0);
		Shape19.addBox(-1.5F, 0F, 2.5F, 1, 1, 1);
		Shape19.setRotationPoint(0F, 11F, -6F);
		Shape19.setTextureSize(128, 64);
		Shape19.mirror = true;
		setRotation(Shape19, 0F, -1.570796F, 0F);
		Shape20 = new ModelRenderer(this, 55, 0);
		Shape20.addBox(-1.5F, 0F, -6.5F, 1, 1, 3);
		Shape20.setRotationPoint(0F, 10.5F, -6F);
		Shape20.setTextureSize(128, 64);
		Shape20.mirror = true;
		setRotation(Shape20, 0F, -1.570796F, 0F);
		Shape21 = new ModelRenderer(this, 50, 0);
		Shape21.addBox(0F, 0F, 0F, 1, 12, 1);
		Shape21.setRotationPoint(-7.5F, 10.5F, -7.5F);
		Shape21.setTextureSize(128, 64);
		Shape21.mirror = true;
		setRotation(Shape21, 0F, 0F, 0F);
		Shape22 = new ModelRenderer(this, 50, 0);
		Shape22.addBox(0F, 0F, 0F, 1, 12, 1);
		Shape22.setRotationPoint(-7.5F, 10.5F, 6.5F);
		Shape22.setTextureSize(128, 64);
		Shape22.mirror = true;
		setRotation(Shape22, 0F, 0F, 0F);
		Shape23 = new ModelRenderer(this, 66, 0);
		Shape23.addBox(0F, 0F, 0F, 2, 8, 8);
		Shape23.setRotationPoint(-7.5F, 12F, -4F);
		Shape23.setTextureSize(128, 64);
		Shape23.mirror = true;
		setRotation(Shape23, 0F, 0F, 0F);
		Shape24 = new ModelRenderer(this, 31, 19);
		Shape24.addBox(-2F, -2F, -2F, 1, 6, 6);
		Shape24.setRotationPoint(-6F, 15F, -1F);
		Shape24.setTextureSize(128, 64);
		Shape24.mirror = true;
		setRotation(Shape24, 0F, 0F, 0F);
		Shape26 = new ModelRenderer(this, 55, 0);
		Shape26.addBox(0F, 0F, 0F, 1, 1, 3);
		Shape26.setRotationPoint(-7.5F, 9.5F, -7.5F);
		Shape26.setTextureSize(128, 64);
		Shape26.mirror = true;
		setRotation(Shape26, 0F, 0F, 0F);
		Shape27 = new ModelRenderer(this, 55, 0);
		Shape27.addBox(0F, 0F, 0F, 1, 1, 3);
		Shape27.setRotationPoint(-7.5F, 9.5F, 4.5F);
		Shape27.setTextureSize(128, 64);
		Shape27.mirror = true;
		setRotation(Shape27, 0F, 0F, 0F);
		Shape28 = new ModelRenderer(this, 0, 38);
		Shape28.addBox(-2F, 0F, -5F, 2, 2, 10);
		Shape28.setRotationPoint(0F, 20F, -6F);
		Shape28.setTextureSize(128, 64);
		Shape28.mirror = true;
		setRotation(Shape28, 0F, -1.570796F, 0F);
		Shape29 = new ModelRenderer(this, 55, 0);
		Shape29.addBox(-1.5F, 0F, 3.5F, 1, 1, 3);
		Shape29.setRotationPoint(0F, 10.5F, -6F);
		Shape29.setTextureSize(128, 64);
		Shape29.mirror = true;
		setRotation(Shape29, 0F, -1.570796F, 0F);
		Shape30 = new ModelRenderer(this, 53, 0);
		Shape30.addBox(-1.5F, 0F, -3.5F, 1, 1, 1);
		Shape30.setRotationPoint(0F, 11F, -6F);
		Shape30.setTextureSize(128, 64);
		Shape30.mirror = true;
		setRotation(Shape30, 0F, -1.570796F, 0F);
		Shape31 = new ModelRenderer(this, 66, 0);
		Shape31.addBox(0F, 0F, 0F, 2, 8, 8);
		Shape31.setRotationPoint(5.5F, 12F, -4F);
		Shape31.setTextureSize(128, 64);
		Shape31.mirror = true;
		setRotation(Shape31, 0F, 0F, 0F);
		pb2 = new ModelRenderer(this, 94, 0);
		pb2.addBox(0F, 0F, 0F, 1, 5, 7);
		pb2.setRotationPoint(-3F, 15F, -3.5F);
		pb2.setTextureSize(128, 64);
		pb2.mirror = true;
		setRotation(pb2, 0F, 0F, 0F);
		Shape32 = new ModelRenderer(this, 110, 0);
		Shape32.addBox(0F, 0F, 0F, 2, 6, 7);
		Shape32.setRotationPoint(3.5F, 14F, -3.5F);
		Shape32.setTextureSize(128, 64);
		Shape32.mirror = true;
		setRotation(Shape32, 0F, 0F, 0F);
		Shape33 = new ModelRenderer(this, 110, 0);
		Shape33.addBox(0F, 0F, 0F, 2, 6, 7);
		Shape33.setRotationPoint(-5.5F, 14F, -3.5F);
		Shape33.setTextureSize(128, 64);
		Shape33.mirror = true;
		setRotation(Shape33, 0F, 0F, 0F);
		pb1 = new ModelRenderer(this, 110, 13);
		pb1.addBox(0F, 0F, 0F, 3, 5, 6);
		pb1.setRotationPoint(-6F, 15F, -3F);
		pb1.setTextureSize(128, 64);
		pb1.mirror = true;
		setRotation(pb1, 0F, 0F, 0F);
		Shape34 = new ModelRenderer(this, 68, 45);
		Shape34.addBox(0F, 0F, 0F, 8, 4, 10);
		Shape34.setRotationPoint(-4F, 8F, -5F);
		Shape34.setTextureSize(128, 64);
		Shape34.mirror = true;
		setRotation(Shape34, 0F, 0F, 0F);
		Shape35 = new ModelRenderer(this, 100, 24);
		Shape35.addBox(0F, 0F, 0F, 6, 1, 8);
		Shape35.setRotationPoint(-3F, 12F, -4F);
		Shape35.setTextureSize(128, 64);
		Shape35.mirror = true;
		setRotation(Shape35, 0F, 0F, 0F);
		pa1 = new ModelRenderer(this, 110, 13);
		pa1.addBox(0F, 0F, 0F, 3, 5, 6);
		pa1.setRotationPoint(3F, 15F, -3F);
		pa1.setTextureSize(128, 64);
		pa1.mirror = true;
		setRotation(pa1, 0F, 0F, 0F);
		pa2 = new ModelRenderer(this, 94, 0);
		pa2.addBox(0F, 0F, 0F, 1, 5, 7);
		pa2.setRotationPoint(2F, 15F, -3.5F);
		pa2.setTextureSize(128, 64);
		pa2.mirror = true;
		setRotation(pa2, 0F, 0F, 0F);
		Shape36 = new ModelRenderer(this, 51, 18);
		Shape36.addBox(0F, 0F, 0F, 3, 2, 10);
		Shape36.setRotationPoint(5F, 20F, -5F);
		Shape36.setTextureSize(128, 64);
		Shape36.mirror = true;
		setRotation(Shape36, 0F, 0F, 0F);
		Shape37 = new ModelRenderer(this, 48, 36);
		Shape37.addBox(0F, 0F, 0F, 3, 4, 10);
		Shape37.setRotationPoint(5F, 8F, -5F);
		Shape37.setTextureSize(128, 64);
		Shape37.mirror = true;
		setRotation(Shape37, 0F, 0F, 0F);
		Shape38 = new ModelRenderer(this, 0, 18);
		Shape38.addBox(0F, 0F, 0F, 1, 3, 8);
		Shape38.setRotationPoint(-5F, 8.5F, -4F);
		Shape38.setTextureSize(128, 64);
		Shape38.mirror = true;
		setRotation(Shape38, 0F, 0F, 0F);
		pc1 = new ModelRenderer(this, 108, 33);
		pc1.addBox(0F, 0F, 0F, 4, 3, 6);
		pc1.setRotationPoint(-2F, 11F, -3F);
		pc1.setTextureSize(128, 64);
		pc1.mirror = true;
		setRotation(pc1, 0F, 0F, 0F);
		bullet1 = new ModelRenderer(this, 110, 47);
		bullet1.addBox(-0.5F, 0F, -0.5F, 1, 5, 1);
		bullet1.setRotationPoint(0F, 15.5F, -2F);
		bullet1.setTextureSize(128, 64);
		bullet1.mirror = true;
		setRotation(bullet1, 0F, 0.7853982F, 0F);
		bullet3 = new ModelRenderer(this, 118, 47);
		bullet3.addBox(-0.5F, 0F, -0.5F, 1, 5, 1);
		bullet3.setRotationPoint(0F, 15.5F, 2F);
		bullet3.setTextureSize(128, 64);
		bullet3.mirror = true;
		setRotation(bullet3, 0F, 0.7853982F, 0F);
		bullet2 = new ModelRenderer(this, 114, 47);
		bullet2.addBox(-0.5F, 0F, -0.5F, 1, 5, 1);
		bullet2.setRotationPoint(0F, 15.5F, 0F);
		bullet2.setTextureSize(128, 64);
		bullet2.mirror = true;
		setRotation(bullet2, 0F, 0.7853982F, 0F);
	}

	@Override
	public void render(Entity entityIn, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch, float scale, float progress) {
		Shape1.render(scale);
		Shape4.render(scale);
		Shape5.render(scale);
		Shape7.render(scale);
		Shape8.render(scale);
		Shape10.render(scale);
		Shape11.render(scale);
		Shape12.render(scale);
		Shape13.render(scale);
		Shape14.render(scale);
		Shape17.render(scale);
		Shape18.render(scale);
		Shape19.render(scale);
		Shape20.render(scale);
		Shape21.render(scale);
		Shape22.render(scale);
		Shape23.render(scale);
		Shape24.render(scale);
		Shape26.render(scale);
		Shape27.render(scale);
		Shape28.render(scale);
		Shape29.render(scale);
		Shape30.render(scale);
		Shape31.render(scale);
		Shape32.render(scale);
		Shape33.render(scale);
		Shape34.render(scale);
		Shape35.render(scale);
		Shape36.render(scale);
		Shape37.render(scale);
		Shape38.render(scale);
		if (progress > 0.0f) {
			// Repeat once
			if (progress > 0.5f) {
				progress -= 0.5f;
			}
			progress = progress * 2.0f;

			float ab = 0;
			float c = 0;

			if (progress < 0.25f) {
				MetalPiece.render(scale);
				float p = progress * 4f;
				ab = (float) Math.sqrt(p);
				// 0,5+(SIN(((B2/2)+0,75)*2*PI())/2)
			} else if (progress < 0.66f) {
				float p = ((progress - 0.25f) * 2.439f);
				ab = (float) (0.5f + (Math.sin((((1f - p) / 2.0f) + 0.75f) * 2.0f * Math.PI) / 2.0f));

				bullet1.render(scale);
				bullet3.render(scale);
				bullet2.render(scale);
			} else {

				float p = Math.min(1.0f, (progress - 0.66f) * 3f);

				c = 0.5f + (float) Math.sin((Math.sqrt(p) + 0.75f) * Math.PI * 2.0f) / 2.0f;

				if (progress < 0.9f) {
					bullet1.render(scale);
					bullet3.render(scale);
					bullet2.render(scale);
				}

			}

			// left
			GlStateManager.pushMatrix();
			GlStateManager.translate((0.125f * -ab), 0f, 0f);
			pa1.render(scale);
			pa2.render(scale);
			GlStateManager.popMatrix();
			// right
			GlStateManager.pushMatrix();
			GlStateManager.translate((0.125f * ab), 0f, 0f);
			pb1.render(scale);
			pb2.render(scale);
			GlStateManager.popMatrix();
			// top
			GlStateManager.pushMatrix();
			GlStateManager.translate(0f, (0.09375f * c), 0f);
			pc1.render(scale);
			GlStateManager.popMatrix();
		} else {
			pa1.render(scale);
			pa2.render(scale);
			pb1.render(scale);
			pb2.render(scale);
			pc1.render(scale);
		}

	}

}
