package techguns.client.models.armor;

import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;

public class ModelOxygenTanks extends ModelAdditionalSlotBase {

	// fields
	ModelRenderer RocketTopR;
	ModelRenderer BackBack;
	ModelRenderer RocketR;
	ModelRenderer RocketL;
	ModelRenderer RocketTopL;

	public ModelOxygenTanks() {
		textureWidth = 32;
		textureHeight = 32;

		BackBack = new ModelRenderer(this, 16, 0);
		BackBack.addBox(-2F, 0F, 0F, 2, 6, 3);
		BackBack.setRotationPoint(1F, 1F, 2F);
		BackBack.setTextureSize(32, 32);
		BackBack.mirror = true;
		setRotation(BackBack, 0F, 0F, 0F);

		RocketTopR = new ModelRenderer(this, 0, 13);
		RocketTopR.addBox(-2F, -1F, -3F, 2, 1, 2);
		RocketTopR.setRotationPoint(-2.5F, -1F, 4F);
		RocketTopR.setTextureSize(32, 32);
		RocketTopR.mirror = true;
		setRotation(RocketTopR, 0F, 0F, 0F);
		this.BackBack.addChild(RocketTopR);

		RocketR = new ModelRenderer(this, 0, 0);
		RocketR.addBox(-4F, 0F, 0F, 3, 9, 3);
		RocketR.setRotationPoint(-1F, -1F, 0.5F);
		RocketR.setTextureSize(32, 32);
		RocketR.mirror = true;
		setRotation(RocketR, 0F, 0F, 0F);
		this.BackBack.addChild(RocketR);

		RocketL = new ModelRenderer(this, 0, 0);
		RocketL.addBox(-4F, 0F, 0F, 3, 9, 3);
		RocketL.setRotationPoint(4F, -1F, 0.5F);
		RocketL.setTextureSize(32, 32);
		RocketL.mirror = true;
		setRotation(RocketL, 0F, 0F, 0F);
		this.BackBack.addChild(RocketL);

		RocketTopL = new ModelRenderer(this, 0, 13);
		RocketTopL.addBox(-2F, -1F, -3F, 2, 1, 2);
		RocketTopL.setRotationPoint(2.5F, -1F, 4F);
		RocketTopL.setTextureSize(32, 32);
		RocketTopL.mirror = true;
		setRotation(RocketTopL, 0F, 0F, 0F);
		this.BackBack.addChild(RocketTopL);
	}

	@Override
	public void render(float scale, Entity entityIn) {
		BackBack.render(scale);
	}

	@Override
	public void copyRotateAngles() {
		this.BackBack.rotateAngleX = this.bipedBody.rotateAngleX;
	}
}
