package techguns.client.models.armor;

import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;

public class ModelAntiGravPack extends ModelAdditionalSlotBase {

	public ModelRenderer BackBack;
	public ModelRenderer RocketR;
	public ModelRenderer box1;
	public ModelRenderer Back2;
	public ModelRenderer Back3;
	public ModelRenderer RocketL;
	public ModelRenderer box2;
	public ModelRenderer box3;
	public ModelRenderer box4;

	public ModelAntiGravPack() {
		this.textureWidth = 32;
		this.textureHeight = 32;

		this.BackBack = new ModelRenderer(this, 16, 0);
		this.BackBack.setRotationPoint(-1.0F, 1.0F, 2.0F);
		this.BackBack.addBox(-2.0F, 0.0F, 0.0F, 6, 6, 3, 0.0F);

		this.RocketL = new ModelRenderer(this, 0, 0);
		this.RocketL.setRotationPoint(6.0F, -0.5F, 0.5F);
		this.RocketL.addBox(-2.0F, 0.0F, 0.0F, 2, 7, 2, 0.0F);
		this.BackBack.addChild(RocketL);

		this.box1 = new ModelRenderer(this, 0, 10);
		this.box1.setRotationPoint(5.0F, 3.5F, 2.5F);
		this.box1.addBox(-2.0F, 0.0F, 0.0F, 2, 1, 1, 0.0F);
		this.BackBack.addChild(box1);

		this.Back3 = new ModelRenderer(this, 6, 10);
		this.Back3.setRotationPoint(0.0F, 4.0F, 6.0F);
		this.Back3.addBox(-1.5F, -3.0F, -3.5F, 3, 3, 3, 0.0F);
		this.setRotation(Back3, 0.0F, 0.0F, 0.7853981633974483F);
		this.BackBack.addChild(Back3);

		this.Back2 = new ModelRenderer(this, 20, 11);
		this.Back2.setRotationPoint(-0.5F, 2.0F, 5.0F);
		this.Back2.addBox(-1.0F, -1.0F, -2.0F, 5, 4, 1, 0.0F);
		this.BackBack.addChild(Back2);

		this.box3 = new ModelRenderer(this, 0, 10);
		this.box3.setRotationPoint(-2.0F, 4.5F, 5.5F);
		this.box3.addBox(-1.0F, -1.0F, 2.0F, 2, 1, 1, 0.0F);
		this.setRotation(box3, 0.0F, 3.141592653589793F, 0.0F);
		this.BackBack.addChild(box3);

		this.RocketR = new ModelRenderer(this, 0, 0);
		this.RocketR.setRotationPoint(-2.0F, -0.5F, 0.5F);
		this.RocketR.addBox(-2.0F, 0.0F, 0.0F, 2, 7, 2, 0.0F);
		this.BackBack.addChild(RocketR);

		this.box4 = new ModelRenderer(this, 0, 10);
		this.box4.setRotationPoint(-2.0F, 2.5F, 5.5F);
		this.box4.addBox(-1.0F, -1.0F, 2.0F, 2, 1, 1, 0.0F);
		this.setRotation(box4, 0.0F, 3.141592653589793F, 0.0F);
		this.BackBack.addChild(box4);

		this.box2 = new ModelRenderer(this, 0, 10);
		this.box2.setRotationPoint(5.0F, 1.5F, 2.5F);
		this.box2.addBox(-2.0F, 0.0F, 0.0F, 2, 1, 1, 0.0F);
		this.BackBack.addChild(box2);
	}

	@Override
	public void render(float scale, Entity entityIn) {
		this.BackBack.render(scale);
	}

	@Override
	public void copyRotateAngles() {
		this.BackBack.rotateAngleX = this.bipedBody.rotateAngleX;
	}

}
