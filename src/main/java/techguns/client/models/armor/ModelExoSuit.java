package techguns.client.models.armor;

import net.minecraft.client.model.ModelBiped;
import net.minecraft.client.model.ModelRenderer;

public class ModelExoSuit extends ModelBiped {

	ModelRenderer RA4;
	ModelRenderer RA6;
	ModelRenderer RA7;
	ModelRenderer RA8;
	ModelRenderer LA2;
	ModelRenderer LA5;
	ModelRenderer LA6;
	ModelRenderer LA7;
	// ModelRenderer H1;
	// ModelRenderer H4;
	ModelRenderer B2;
	ModelRenderer B3;
	ModelRenderer P1;
	ModelRenderer P3;
	ModelRenderer RL2;
	ModelRenderer RL3;
	ModelRenderer RL4;
	ModelRenderer LL1;
	ModelRenderer LL3;
	ModelRenderer LL4;
	ModelRenderer RB1;
	ModelRenderer RB2;
	ModelRenderer LB1;
	ModelRenderer LB2;

	public ModelExoSuit(int type, float scale) {
		/*
		 * this.bipedCloak = new ModelRenderer(this, 0, 0); this.bipedEars = new
		 * ModelRenderer(this, 24, 0); this.bipedHead = new ModelRenderer(this,
		 * 0, 0); this.bipedHead.setRotationPoint(0.0F, 0.0F, 0.0F);
		 * this.bipedHeadwear = new ModelRenderer(this, 32, 0);
		 * this.bipedHeadwear.setRotationPoint(0.0F, 0.0F, 0.0F); this.bipedBody
		 * = new ModelRenderer(this, 16, 16);
		 * this.bipedBody.setRotationPoint(0.0F, 0.0F, 0.0F); this.bipedRightArm
		 * = new ModelRenderer(this, 40, 16);
		 * this.bipedRightArm.setRotationPoint(-5.0F, 2.0F, 0.0F);
		 * this.bipedLeftArm = new ModelRenderer(this, 40, 16);
		 * this.bipedLeftArm.mirror = true;
		 * this.bipedLeftArm.setRotationPoint(5.0F, 2.0F, 0.0F);
		 * this.bipedRightLeg = new ModelRenderer(this, 0, 16);
		 * this.bipedRightLeg.setRotationPoint(-1.9F, 12.0F, 0.0F);
		 * this.bipedLeftLeg = new ModelRenderer(this, 0, 16);
		 * this.bipedLeftLeg.mirror = true;
		 * this.bipedLeftLeg.setRotationPoint(1.9F, 12.0F, 0.0F);
		 */

		super(scale, 0.0F, 64, 64);

		// ---------
		textureWidth = 64;
		textureHeight = 64;

		float ArmOffsetX = 5.0F;
		float ArmOffsetY = -2.0F;
		float LegOffsetX = 1.9F;
		float LegOffsetY = -12.0F;

		RA4 = new ModelRenderer(this, 24, 32);
		RA4.addBox(0F, 0F, 0F, 6, 2, 6);
		RA4.setRotationPoint(-9F + ArmOffsetX, 6.5F + ArmOffsetY, -3F);
		RA4.setTextureSize(textureWidth, textureHeight);
		RA4.mirror = true;
		setRotation(RA4, 0F, 0F, 0F);

		RA6 = new ModelRenderer(this, 48, 32);
		RA6.addBox(0F, 0F, 0F, 2, 8, 2);
		RA6.setRotationPoint(-9.5F + ArmOffsetX, 3.5F + ArmOffsetY, -1F);
		RA6.setTextureSize(textureWidth, textureHeight);
		RA6.mirror = true;
		setRotation(RA6, 0F, 0F, 0F);

		RA7 = new ModelRenderer(this, 27, 41);
		RA7.addBox(0F, 0F, 0F, 2, 3, 3);
		RA7.setRotationPoint(-10F + ArmOffsetX, 6F + ArmOffsetY, -1.5F);
		RA7.setTextureSize(textureWidth, textureHeight);
		RA7.mirror = true;
		setRotation(RA7, 0F, 0F, 0F);

		RA8 = new ModelRenderer(this, 0, 32);
		RA8.addBox(0F, 0F, 0F, 6, 5, 6);
		RA8.setRotationPoint(-9.5F + ArmOffsetX, -1.5F + ArmOffsetY, -3F);
		RA8.setTextureSize(textureWidth, textureHeight);
		RA8.mirror = true;
		setRotation(RA8, 0F, 0F, 0F);

		LA2 = new ModelRenderer(this, 27, 41);
		LA2.addBox(3F, 4F, -1.5F, 2, 3, 3);
		LA2.setRotationPoint(5F - ArmOffsetX, 2F + ArmOffsetY, 0F);
		LA2.setTextureSize(textureWidth, textureHeight);
		LA2.mirror = true;
		setRotation(LA2, 0F, 0F, 0F);

		LA5 = new ModelRenderer(this, 0, 32);
		LA5.addBox(-1.5F, -1.5F, -3F, 6, 5, 6);
		LA5.setRotationPoint(5F - ArmOffsetX, 0F + ArmOffsetY, 0F);
		LA5.setTextureSize(textureWidth, textureHeight);
		LA5.mirror = true;
		setRotation(LA5, 0F, 0F, 0F);

		LA6 = new ModelRenderer(this, 24, 32);
		LA6.addBox(-2F, 4.5F, -3F, 6, 2, 6);
		LA6.setRotationPoint(5F - ArmOffsetX, 2F + ArmOffsetY, 0F);
		LA6.setTextureSize(textureWidth, textureHeight);
		LA6.mirror = true;
		setRotation(LA6, 0F, 0F, 0F);

		LA7 = new ModelRenderer(this, 48, 32);
		LA7.addBox(2.5F, 0.5F, -1F, 2, 8, 2);
		LA7.setRotationPoint(5F - ArmOffsetX, 3F + ArmOffsetY, 0F);
		LA7.setTextureSize(textureWidth, textureHeight);
		LA7.mirror = true;
		setRotation(LA7, 0F, 0F, 0F);

		B2 = new ModelRenderer(this, 0, 60);
		B2.addBox(-2F, 0F, -2F, 10, 2, 2);
		B2.setRotationPoint(-3F, 1F, 3.533333F);
		B2.setTextureSize(textureWidth, textureHeight);
		B2.mirror = true;
		setRotation(B2, 0F, 0F, 0F);

		B3 = new ModelRenderer(this, 38, 40);
		B3.addBox(-2F, 0F, -2F, 3, 9, 2);
		B3.setRotationPoint(0.5F, 3F, 3.533333F);
		B3.setTextureSize(textureWidth, textureHeight);
		B3.mirror = true;
		setRotation(B3, 0F, 0F, 0F);

		P1 = new ModelRenderer(this, 0, 43);
		P1.addBox(0F, 0F, 0F, 10, 2, 6);
		P1.setRotationPoint(-5F, 11F, -3F);
		P1.setTextureSize(textureWidth, textureHeight);
		P1.mirror = true;
		setRotation(P1, 0F, 0F, 0F);

		P3 = new ModelRenderer(this, 0, 52);
		P3.addBox(0F, 0F, 0F, 2, 2, 6);
		P3.setRotationPoint(-1F, 13F, -3F);
		P3.setTextureSize(textureWidth, textureHeight);
		P3.mirror = true;
		setRotation(P3, 0F, 0F, 0F);

		RL2 = new ModelRenderer(this, 27, 41);
		RL2.addBox(0F, 0F, 0F, 2, 3, 3);
		RL2.setRotationPoint(-6F + LegOffsetX, 16.5F + LegOffsetY, -1.5F);
		RL2.setTextureSize(textureWidth, textureHeight);
		RL2.mirror = true;
		setRotation(RL2, 0F, 0F, 0F);

		RL3 = new ModelRenderer(this, 17, 52);
		RL3.addBox(0F, 0F, 0F, 5, 2, 6);
		RL3.setRotationPoint(-5F + LegOffsetX, 17F + LegOffsetY, -3F);
		RL3.setTextureSize(textureWidth, textureHeight);
		RL3.mirror = true;
		setRotation(RL3, 0F, 0F, 0F);

		RL4 = new ModelRenderer(this, 56, 32);
		RL4.addBox(0F, 0F, 0F, 2, 10, 2);
		RL4.setRotationPoint(-5.5F + LegOffsetX, 12.5F + LegOffsetY, -1F);
		RL4.setTextureSize(textureWidth, textureHeight);
		RL4.mirror = true;
		setRotation(RL4, 0F, 0F, 0F);

		LL1 = new ModelRenderer(this, 56, 32);
		LL1.addBox(0F, 0F, 0F, 2, 10, 2);
		LL1.setRotationPoint(3.5F - LegOffsetX, 12.5F + LegOffsetY, -1F);
		LL1.setTextureSize(textureWidth, textureHeight);
		LL1.mirror = true;
		setRotation(LL1, 0F, 0F, 0F);

		LL3 = new ModelRenderer(this, 27, 41);
		LL3.addBox(0F, 0F, 0F, 2, 3, 3);
		LL3.setRotationPoint(4F - LegOffsetX, 16.5F + LegOffsetY, -1.5F);
		LL3.setTextureSize(textureWidth, textureHeight);
		LL3.mirror = true;
		setRotation(LL3, 0F, 0F, 0F);

		LL4 = new ModelRenderer(this, 17, 52);
		LL4.addBox(0F, 0F, 0F, 5, 2, 6);
		LL4.setRotationPoint(0F - LegOffsetX, 17F + LegOffsetY, -3F);
		LL4.setTextureSize(textureWidth, textureHeight);
		LL4.mirror = true;
		setRotation(LL4, 0F, 0F, 0F);

		RB1 = new ModelRenderer(this, 56, 51);
		RB1.addBox(-2F, 0F, -2F, 3, 3, 1);
		RB1.setRotationPoint(-1.5F + LegOffsetX, 21.5F + LegOffsetY, -1.5F);
		RB1.setTextureSize(textureWidth, textureHeight);
		RB1.mirror = true;
		setRotation(RB1, 0F, 0F, 0F);

		RB2 = new ModelRenderer(this, 40, 56);
		RB2.addBox(0F, 0F, 0F, 6, 2, 6);
		RB2.setRotationPoint(-5F + LegOffsetX, 22F + LegOffsetY, -3F);
		RB2.setTextureSize(textureWidth, textureHeight);
		RB2.mirror = true;
		setRotation(RB2, 0F, 0F, 0F);

		LB1 = new ModelRenderer(this, 56, 51);
		LB1.addBox(-2F, 0F, -2F, 3, 3, 1);
		LB1.setRotationPoint(2.5F - LegOffsetX, 21.5F + LegOffsetY, -1.5F);
		LB1.setTextureSize(textureWidth, textureHeight);
		LB1.mirror = true;
		setRotation(LB1, 0F, 0F, 0F);

		LB2 = new ModelRenderer(this, 40, 56);
		LB2.addBox(0F, 0F, 0F, 6, 2, 6);
		LB2.setRotationPoint(-1F - LegOffsetX, 22F + LegOffsetY, -3F);
		LB2.setTextureSize(textureWidth, textureHeight);
		LB2.mirror = true;
		setRotation(LB2, 0F, 0F, 0F);

		if (type == 0 || type == 2) {

			this.bipedRightArm.addChild(RA4);
			this.bipedRightArm.addChild(RA6);
			this.bipedRightArm.addChild(RA7);
			this.bipedRightArm.addChild(RA8);

			this.bipedLeftArm.addChild(LA2);
			this.bipedLeftArm.addChild(LA5);
			this.bipedLeftArm.addChild(LA6);
			this.bipedLeftArm.addChild(LA7);

			// this.bipedHead.addChild(H1);
			// this.bipedHead.addChild(H4);

			this.bipedBody.addChild(B2);
			this.bipedBody.addChild(B3);

			this.bipedRightLeg.addChild(RB1);
			this.bipedRightLeg.addChild(RB2);

			this.bipedLeftLeg.addChild(LB1);
			this.bipedLeftLeg.addChild(LB2);

		} else {
			this.bipedBody.addChild(P1);

			this.bipedBody.addChild(P3);

			this.bipedRightLeg.addChild(RL2);
			this.bipedRightLeg.addChild(RL3);
			this.bipedRightLeg.addChild(RL4);

			this.bipedLeftLeg.addChild(LL1);
			this.bipedLeftLeg.addChild(LL3);
			this.bipedLeftLeg.addChild(LL4);
		}

	}

	private void setRotation(ModelRenderer model, float x, float y, float z) {
		model.rotateAngleX = x;
		model.rotateAngleY = y;
		model.rotateAngleZ = z;
	}
}
