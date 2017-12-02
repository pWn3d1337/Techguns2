package techguns.client.models.armor;

import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;

public class ModelGloves extends ModelAdditionalSlotBase {

	public ModelGloves(float modelSize, boolean smallArms) {
		super(modelSize, 0F, 16, 16);

		if (smallArms) {
			this.bipedLeftArm = new ModelRenderer(this, 0, 0);
			this.bipedLeftArm.mirror=true;
			this.bipedLeftArm.addBox(-1.0F, -2.0F, -2.0F, 3, 12, 4, modelSize);
			this.bipedLeftArm.setRotationPoint(5.0F, 2.5F, 0.0F);
			this.bipedRightArm = new ModelRenderer(this, 0, 0);
			this.bipedRightArm.addBox(-2.0F, -2.0F, -2.0F, 3, 12, 4, modelSize);
			this.bipedRightArm.setRotationPoint(-5.0F, 2.5F, 0.0F);
		} else {
			this.bipedRightArm = new ModelRenderer(this, 0, 0);
	        this.bipedRightArm.addBox(-3.0F, -2.0F, -2.0F, 4, 12, 4, modelSize);
	        this.bipedRightArm.setRotationPoint(-5.0F, 2.0F , 0.0F);
	        this.bipedLeftArm = new ModelRenderer(this, 0, 0);
	        this.bipedLeftArm.mirror = true;
	        this.bipedLeftArm.addBox(-1.0F, -2.0F, -2.0F, 4, 12, 4, modelSize);
	        this.bipedLeftArm.setRotationPoint(5.0F, 2.0F, 0.0F);
		}
	}

	@Override
	public void render(float scale, Entity entityIn) {
		this.bipedLeftArm.render(scale);
		this.bipedRightArm.render(scale);
	}

	@Override
	public void copyRotateAngles() {
	}

}
