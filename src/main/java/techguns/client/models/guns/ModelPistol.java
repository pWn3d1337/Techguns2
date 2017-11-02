package techguns.client.models.guns;

import net.minecraft.client.model.ModelRenderer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.block.model.ItemCameraTransforms.TransformType;
import net.minecraft.entity.Entity;
import techguns.client.models.ModelMultipart;

public class ModelPistol extends ModelMultipart {

	//fields
    ModelRenderer Top;
    ModelRenderer Grip3;
    ModelRenderer Barrel;
    ModelRenderer IS2;
    ModelRenderer Grip2;
    ModelRenderer IS1;
    ModelRenderer Trigger;
    ModelRenderer Grip1;
    ModelRenderer Slide;
  
  public ModelPistol()
  {
    textureWidth = 64;
    textureHeight = 32;
    
      Top = new ModelRenderer(this, 0, 17);
      Top.addBox(0F, 0F, 0F, 3, 1, 14);
      Top.setRotationPoint(-2F, 2F, -2F);
      Top.setTextureSize(64, 32);
      Top.mirror = true;
      setRotation(Top, 0F, 0F, 0F);
      Grip3 = new ModelRenderer(this, 3, 0);
      Grip3.addBox(0F, 0F, 0F, 2, 7, 3);
      Grip3.setRotationPoint(-1.5F, 3F, 7F);
      Grip3.setTextureSize(64, 32);
      Grip3.mirror = true;
      setRotation(Grip3, 0.2181662F, 0F, 0F);
      Barrel = new ModelRenderer(this, 0, 21);
      Barrel.addBox(0F, 0F, 0F, 1, 1, 6);
      Barrel.setRotationPoint(-0.5F, 0.25F, -3F);
      Barrel.setTextureSize(64, 32);
      Barrel.mirror = true;
      setRotation(Barrel, 0F, 0F, 0.7853982F);
      IS2 = new ModelRenderer(this, 0, 21);
      IS2.addBox(0F, 0F, 0F, 1, 1, 1);
      IS2.setRotationPoint(-1F, -0.5333334F, -0.5F);
      IS2.setTextureSize(64, 32);
      IS2.mirror = true;
      setRotation(IS2, 0F, 0F, 0F);
      Grip2 = new ModelRenderer(this, 20, 5);
      Grip2.addBox(0F, 0F, 0F, 1, 1, 3);
      Grip2.setRotationPoint(-1F, 5F, 5F);
      Grip2.setTextureSize(64, 32);
      Grip2.mirror = true;
      setRotation(Grip2, 0F, 0F, 0F);
      IS1 = new ModelRenderer(this, 0, 24);
      IS1.addBox(0F, 0F, 0F, 1, 1, 1);
      IS1.setRotationPoint(-1F, -0.5F, 9.5F);
      IS1.setTextureSize(64, 32);
      IS1.mirror = true;
      setRotation(IS1, 0F, 0F, 0F);
      Trigger = new ModelRenderer(this, 25, 0);
      Trigger.addBox(0F, 0F, 0F, 1, 2, 1);
      Trigger.setRotationPoint(-1F, 2.5F, 6.5F);
      Trigger.setTextureSize(64, 32);
      Trigger.mirror = true;
      setRotation(Trigger, -0.4089647F, 0F, 0F);
      Grip1 = new ModelRenderer(this, 20, 0);
      Grip1.addBox(0F, 0F, 0F, 1, 3, 1);
      Grip1.setRotationPoint(-1F, 3F, 4F);
      Grip1.setTextureSize(64, 32);
      Grip1.mirror = true;
      setRotation(Grip1, 0F, 0F, 0F);
      Slide = new ModelRenderer(this, 0, 0);
      Slide.addBox(0F, 0F, 0F, 3, 2, 14);
      Slide.setRotationPoint(-2F, 0F, -2F);
      Slide.setTextureSize(64, 32);
      Slide.mirror = true;
      setRotation(Slide, 0F, 0F, 0F);
  }
  
  @Override
  public void render(Entity entityIn, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch, float scale, int ammoLeft, float reloadProgress,
  		TransformType transformType, int part, float fireProgress, float chargeProgress) {
	    if(part==0){
		    Top.render(scale);
		    Grip3.render(scale);
		    Barrel.render(scale);
		    Grip2.render(scale);
		    Trigger.render(scale);
		    Grip1.render(scale);
	    } else {
	    	GlStateManager.pushMatrix();
	    	float progress=fireProgress*2.0f;
	    	if (progress<0.5f){
	    		float prog = progress*2.0f;
	    		GlStateManager.translate(0, 0, 0.15f*prog);
	    	}
		    IS1.render(scale);
		    IS2.render(scale);
		    Slide.render(scale);
		    GlStateManager.popMatrix();
	    }
  }

}
