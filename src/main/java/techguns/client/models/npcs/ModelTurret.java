package techguns.client.models.npcs;

import net.minecraft.client.model.ModelRenderer;
import net.minecraft.client.renderer.block.model.ItemCameraTransforms.TransformType;
import net.minecraft.entity.Entity;
import techguns.client.models.ModelMultipart;

public class ModelTurret extends ModelMultipart {

	 //fields
   ModelRenderer Mount;
   ModelRenderer Base;
   ModelRenderer Stick01;
   ModelRenderer Stick02;
 
 public ModelTurret()
 {
   textureWidth = 64;
   textureHeight = 64;
   
     Mount = new ModelRenderer(this, 34, 12);
     Mount.addBox(0F, 0F, -2F, 4, 1, 4);
     Mount.setRotationPoint(-2F, 13F, 0F);
     Mount.setTextureSize(64, 64);
     Mount.mirror = true;
     setRotation(Mount, 0F, 0F, 0F);
     Base = new ModelRenderer(this, 32, 22);
     Base.addBox(-4F, 0F, -4F, 8, 1, 8);
     Base.setRotationPoint(0F, 23F, 0F);
     Base.setTextureSize(64, 64);
     Base.mirror = true;
     setRotation(Base, 0F, 0F, 0F);
     Stick01 = new ModelRenderer(this, 9, 30);
     Stick01.addBox(0F, 0F, 0F, 2, 11, 2);
     Stick01.setRotationPoint(2F, 12F, -1F);
     Stick01.setTextureSize(64, 64);
     Stick01.mirror = true;
     setRotation(Stick01, 0F, 0F, 0F);
     Stick02 = new ModelRenderer(this, 9, 30);
     Stick02.addBox(0F, 0F, 0F, 2, 11, 2);
     Stick02.setRotationPoint(-4F, 12F, -1F);
     Stick02.setTextureSize(64, 64);
     Stick02.mirror = true;
     setRotation(Stick02, 0F, 0F, 0F);
 }

 
 
	@Override
	public void render(Entity entityIn, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw,
			float headPitch, float scale, int ammoLeft, float reloadProgress, TransformType transformType, int part,
			float fireProgress, float chargeProgress) {
	//	setRotationAngles(limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, scale, entityIn);
		if (part == 0) {

			Base.render(scale);
			Stick01.render(scale);
			Stick02.render(scale);
		} else {
			Mount.render(scale);
		}

	}

}
