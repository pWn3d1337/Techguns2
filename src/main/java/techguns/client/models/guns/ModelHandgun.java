package techguns.client.models.guns;

import net.minecraft.client.model.ModelRenderer;
import net.minecraft.client.renderer.block.model.ItemCameraTransforms.TransformType;
import net.minecraft.entity.Entity;
import techguns.client.models.ModelMultipart;

public class ModelHandgun extends ModelMultipart
{
  //fields
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
    //ModelRenderer Shape13;
    ModelRenderer Shape14;
    ModelRenderer Shape15;
  
  public ModelHandgun()
  {
    textureWidth = 64;
    textureHeight = 32;
    
      Shape1 = new ModelRenderer(this, 4, 0);
      Shape1.addBox(0F, 0F, 0F, 22, 3, 1);
      Shape1.setRotationPoint(0F, -1F, 2F);
      Shape1.setTextureSize(64, 32);
      Shape1.mirror = true;
      setRotation(Shape1, 0F, 0F, 0F);
      Shape2 = new ModelRenderer(this, 4, 0);
      Shape2.addBox(0F, 0F, 0F, 22, 3, 1);
      Shape2.setRotationPoint(0F, -1F, -2F);
      Shape2.setTextureSize(64, 32);
      Shape2.mirror = true;
      setRotation(Shape2, 0F, 0F, 0F);
      Shape3 = new ModelRenderer(this, 0, 1);
      Shape3.addBox(0F, 0F, 0F, 22, 5, 3);
      Shape3.setRotationPoint(0F, -2F, -1F);
      Shape3.setTextureSize(64, 32);
      Shape3.mirror = true;
      setRotation(Shape3, 0F, 0F, 0F);
      Shape4 = new ModelRenderer(this, 0, 0);
      Shape4.addBox(0F, 0F, 0F, 1, 1, 1);
      Shape4.setRotationPoint(21F, -3F, 0F);
      Shape4.setTextureSize(64, 32);
      Shape4.mirror = true;
      setRotation(Shape4, 0F, 0F, 0F);
      Shape5 = new ModelRenderer(this, 32, 9);
      Shape5.addBox(0F, 0F, 0F, 2, 1, 3);
      Shape5.setRotationPoint(-8F, -3F, -1F);
      Shape5.setTextureSize(64, 32);
      Shape5.mirror = true;
      setRotation(Shape5, 0F, 0F, 0F);
      Shape6 = new ModelRenderer(this, 0, 9);
      Shape6.addBox(0F, 0F, 0F, 2, 5, 5);
      Shape6.setRotationPoint(-8F, -2F, -2F);
      Shape6.setTextureSize(64, 32);
      Shape6.mirror = true;
      setRotation(Shape6, 0F, 0F, 0F);
      Shape7 = new ModelRenderer(this, 14, 9);
      Shape7.addBox(0F, 0F, 0F, 4, 4, 5);
      Shape7.setRotationPoint(-6F, -1F, -2F);
      Shape7.setTextureSize(64, 32);
      Shape7.mirror = true;
      setRotation(Shape7, 0F, 0F, 0F);
      Shape8 = new ModelRenderer(this, 32, 9);
      Shape8.addBox(0F, 0F, 0F, 2, 1, 3);
      Shape8.setRotationPoint(-2F, -3F, -1F);
      Shape8.setTextureSize(64, 32);
      Shape8.mirror = true;
      setRotation(Shape8, 0F, 0F, 0F);
      Shape9 = new ModelRenderer(this, 0, 9);
      Shape9.addBox(0F, 0F, 0F, 2, 5, 5);
      Shape9.setRotationPoint(-2F, -2F, -2F);
      Shape9.setTextureSize(64, 32);
      Shape9.mirror = true;
      setRotation(Shape9, 0F, 0F, 0F);
      Shape10 = new ModelRenderer(this, 44, 14);
      Shape10.addBox(0F, 0F, 0F, 7, 4, 3);
      Shape10.setRotationPoint(-15F, -1F, -1F);
      Shape10.setTextureSize(64, 32);
      Shape10.mirror = true;
      setRotation(Shape10, 0F, 0F, 0F);
      Shape11 = new ModelRenderer(this, 48, 11);
      Shape11.addBox(0F, 0F, 0F, 7, 2, 1);
      Shape11.setRotationPoint(-15F, 0F, -2F);
      Shape11.setTextureSize(64, 32);
      Shape11.mirror = true;
      setRotation(Shape11, 0F, 0F, 0F);
      Shape12 = new ModelRenderer(this, 51, 3);
      Shape12.addBox(0F, 0F, 0F, 2, 1, 3);
      Shape12.setRotationPoint(-15F, 3F, -1F);
      Shape12.setTextureSize(64, 32);
      Shape12.mirror = true;
      setRotation(Shape12, 0F, 0F, 0F);
//      Shape13 = new ModelRenderer(this, 0, 0);
//      Shape13.addBox(0F, 0F, 0F, 22, 5, 3);
//      Shape13.setRotationPoint(0F, -2F, -1F);
//      Shape13.setTextureSize(64, 32);
//      Shape13.mirror = true;
//      setRotation(Shape13, 0F, 0F, 0F);
      Shape14 = new ModelRenderer(this, 48, 11);
      Shape14.addBox(0F, 0F, 0F, 7, 2, 1);
      Shape14.setRotationPoint(-15F, 0F, 2F);
      Shape14.setTextureSize(64, 32);
      Shape14.mirror = true;
      setRotation(Shape14, 0F, 0F, 0F);
      Shape15 = new ModelRenderer(this, 50, 0);
      Shape15.addBox(0F, 0F, 0F, 2, 6, 5);
      Shape15.setRotationPoint(-17F, -1F, -2F);
      Shape15.setTextureSize(64, 32);
      Shape15.mirror = true;
      setRotation(Shape15, 0F, 0F, 0F);
  }
  
  @Override
  public void render(Entity entityIn, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch, float scale, int ammoLeft, float reloadProgress,
  		TransformType transformType, int part, float fireProgress, float chargeProgress) {
    
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
//    Shape13.render(f5);
    Shape14.render(scale);
    Shape15.render(scale);
  }

}