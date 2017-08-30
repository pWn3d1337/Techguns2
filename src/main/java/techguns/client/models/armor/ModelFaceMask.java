package techguns.client.models.armor;

import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;

public class ModelFaceMask extends ModelAdditionalSlotBase {
	// fields
	ModelRenderer Head;
	ModelRenderer Front;

	public ModelFaceMask(boolean hasFrontPart) {
		textureWidth = 32;
		textureHeight = 32;

	    this.bipedHead = new ModelRenderer(this, textureWidth, textureHeight);
   //     this.bipedHead.addBox(-4.0F, -8.0F, -4.0F, 8, 8, 8, 0.0f);
        this.bipedHead.setRotationPoint(0.0F, 0.0F, 0.0F);
		
		Head = new ModelRenderer(this, 0, 0);
		Head.addBox(-4F, -8F, -4F, 8, 8, 8);
		Head.setRotationPoint(0F, 0F, 0F);
		Head.setTextureSize(32, 32);
		Head.mirror = true;
		setRotation(Head, 0F, 0F, 0F);
		this.bipedHead.addChild(Head);

		if (hasFrontPart) {
			Front = new ModelRenderer(this, 9, 17);
			Front.addBox(0F, 0F, 0F, 4, 3, 1);
			Front.setRotationPoint(-2F, -3F, -5F);
			Front.setTextureSize(32, 32);
			Front.mirror = true;
			setRotation(Front, 0F, 0F, 0F);
			this.bipedHead.addChild(Front);
		}
	}

	@Override
	public void render(float scale, Entity entityIn) {
		this.bipedHead.render(scale);
	}

	@Override
	public void copyRotateAngles() {

	}

}
