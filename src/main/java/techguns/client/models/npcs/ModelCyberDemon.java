package techguns.client.models.npcs;

import net.minecraft.client.model.ModelBiped;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;

public class ModelCyberDemon extends ModelBiped
	{
	  //fields
	
		ModelRenderer head;
	    ModelRenderer body;
		ModelRenderer rightArm;
		ModelRenderer leftArm;
		ModelRenderer rightLeg;
		ModelRenderer leftLeg;
	
	    ModelRenderer h2;
	    ModelRenderer h3;
	    ModelRenderer h4;
	    ModelRenderer h5;
	    ModelRenderer h6;
	    ModelRenderer h7;
	    ModelRenderer h8;
	    ModelRenderer b2;
	    ModelRenderer ra2;    
	    ModelRenderer la2;    
	    ModelRenderer rl2;
	    ModelRenderer rl3;    
	    ModelRenderer ll2;
	    ModelRenderer ll3;
	  
	  public ModelCyberDemon()
	  {

	    textureWidth = 64;
	    textureHeight = 64;
		    
	    this.bipedHead = new ModelRenderer(this, 0, 0);
	    this.bipedHead.setRotationPoint(0.0F, 0.0F, 0.0F);
	    this.bipedHeadwear = new ModelRenderer(this, 32, 0);
	    this.bipedHeadwear.setRotationPoint(0.0F, 0.0F, 0.0F);
	    this.bipedBody = new ModelRenderer(this, 16, 16);
	    this.bipedBody.setRotationPoint(0.0F, 0.0F, 0.0F);
	    this.bipedRightArm = new ModelRenderer(this, 40, 16);
	    this.bipedRightArm.setRotationPoint(-5.0F, 2.0F, 0.0F);
	    this.bipedLeftArm = new ModelRenderer(this, 40, 16);
	    this.bipedLeftArm.mirror = true;
	    this.bipedLeftArm.setRotationPoint(5.0F, 2.0F, 0.0F);
	    this.bipedRightLeg = new ModelRenderer(this, 0, 16);
	    this.bipedRightLeg.setRotationPoint(-1.9F, 12.0F, 0.0F);
	    this.bipedLeftLeg = new ModelRenderer(this, 0, 16);
	    this.bipedLeftLeg.mirror = true;
	    this.bipedLeftLeg.setRotationPoint(1.9F, 12.0F, 0.0F);
		      
	      h2 = new ModelRenderer(this, 48, 0);
	      h2.addBox(-3F, -5F, -5F, 6, 5, 2);
	      //h2.setRotationPoint(0F, 0F, 0F);
	      h2.setTextureSize(64, 64);
	      h2.mirror = true;
	      setRotation(h2, 0F, 0F, 0F);
	      h3 = new ModelRenderer(this, 52, 7);
	      h3.addBox(4F, -7F, -2F, 2, 4, 4);
	      //h3.setRotationPoint(0F, 0F, 0F);
	      h3.setTextureSize(64, 64);
	      setRotation(h3, 0F, 0F, 0F);
	      head = new ModelRenderer(this, 0, 0);
	      head.addBox(-4F, -8F, -3F, 8, 8, 7);
	      //head.setRotationPoint(0F, 0F, 0F);
	      head.setTextureSize(64, 64);
	      head.mirror = true;
	      setRotation(head, 0F, 0F, 0F);
	      h4 = new ModelRenderer(this, 30, 8);
	      h4.addBox(-1F, 0F, -5F, 2, 2, 5);
	      h4.setRotationPoint(-7.5F, -5.5F, -1F);
	      h4.setTextureSize(64, 64);
	      h4.mirror = true;
	      setRotation(h4, -0.4363323F, 0F, 0F);
	      h5 = new ModelRenderer(this, 52, 7);
	      h5.addBox(-6F, -7F, -2F, 2, 4, 4);
	      //h5.setRotationPoint(0F, 0F, 0F);
	      h5.setTextureSize(64, 64);
	      h5.mirror = true;
	      setRotation(h5, 0F, 0F, 0F);
	      h6 = new ModelRenderer(this, 24, 0);
	      h6.addBox(0F, -1.5F, -3F, 4, 3, 3);
	      h6.setRotationPoint(6F, -5F, 1.5F);
	      h6.setTextureSize(64, 64);
	      setRotation(h6, 0F, 0.6981317F, 0F);
	      h7 = new ModelRenderer(this, 24, 0);
	      h7.addBox(-4F, -1.5F, -3F, 4, 3, 3);
	      h7.setRotationPoint(-6F, -5F, 1.5F);
	      h7.setTextureSize(64, 64);
	      h7.mirror = true;
	      setRotation(h7, 0F, -0.6981317F, 0F);
	      h8 = new ModelRenderer(this, 30, 8);
	      h8.addBox(-1F, 0F, -5F, 2, 2, 5);
	      h8.setRotationPoint(7.5F, -5.5F, -1F);
	      h8.setTextureSize(64, 64);
	      setRotation(h8, -0.4363323F, 0F, 0F);
	      b2 = new ModelRenderer(this, 0, 28);
	      b2.addBox(-4.5F, 7F, -2.5F, 9, 5, 5);
	      //b2.setRotationPoint(0F, 0F, 0F);
	      b2.setTextureSize(64, 64);
	      b2.mirror = true;
	      setRotation(b2, 0F, 0F, 0F);
	      body = new ModelRenderer(this, 0, 15);
	      body.addBox(-6F, 0F, -3F, 12, 7, 6);
	      //body.setRotationPoint(0F, 0F, 0F);
	      body.setTextureSize(64, 64);
	      body.mirror = true;
	      setRotation(body, 0F, 0F, 0F);
	      
	      rightArm = new ModelRenderer(this, 44, 16);
	      rightArm.addBox(-6F, -2F, -2.5F, 5, 7, 5);
	      //rightArm.setRotationPoint(-5F, 2F, 0F);
	      rightArm.setTextureSize(64, 64);
	      rightArm.mirror = true;
	      setRotation(rightArm, 0F, 0F, 0F);      
	      ra2 = new ModelRenderer(this, 48, 28);
	      ra2.addBox(-5.5F, 3F, 0.5F, 4, 8, 4);
	      //ra2.setRotationPoint(-5F, 2F, 0F);
	      ra2.setTextureSize(64, 64);
	      ra2.mirror = true;
	      setRotation(ra2, -0.5205006F, 0F, 0F);
	      
	      
	      la2 = new ModelRenderer(this, 48, 28);
	      la2.addBox(1.5F, 3F, 0.5F, 4, 8, 4);
	      //la2.setRotationPoint(5F, 2F, 0F);
	      la2.setTextureSize(64, 64);
	      setRotation(la2, -0.5205006F, 0F, 0F);	      
	      leftArm = new ModelRenderer(this, 44, 16);
	      leftArm.addBox(1F, -2F, -2.5F, 5, 7, 5);
	      //leftArm.setRotationPoint(5F, 2F, 0F);
	      leftArm.setTextureSize(64, 64);
	      setRotation(leftArm, 0F, 0F, 0F);     
	      
	      rightLeg = new ModelRenderer(this, 0, 38);
	      rightLeg.addBox(-3.6F, -1F, -2.5F, 5, 7, 5);
	      //rightLeg.setRotationPoint(-1.9F, 12F, 0F);
	      rightLeg.setTextureSize(64, 64);
	      rightLeg.mirror = true;
	      setRotation(rightLeg, -0.5235988F, 0F, 0F);
	      rl2 = new ModelRenderer(this, 20, 39);
	      rl2.addBox(-3.1F, 3F, 1.5F, 4, 7, 4);
	      //rl2.setRotationPoint(-1.9F, 12F, 0F);
	      rl2.setTextureSize(64, 64);
	      rl2.mirror = true;
	      setRotation(rl2, -0.5235988F, 0F, 0F);
	      rl3 = new ModelRenderer(this, 0, 50);
	      rl3.addBox(-3.6F, 9F, -4F, 5, 3, 5);
	      //rl3.setRotationPoint(-1.9F, 12F, 0F);
	      rl3.setTextureSize(64, 64);
	      rl3.mirror = true;
	      setRotation(rl3, 0F, 0F, 0F);

	      leftLeg = new ModelRenderer(this, 0, 38);
	      leftLeg.addBox(-1.4F, -1F, -2.5F, 5, 7, 5);
	      //leftLeg.setRotationPoint(1.9F, 12F, 0F);
	      leftLeg.setTextureSize(64, 64);
	      setRotation(leftLeg, -0.5235988F, 0F, 0F);
	      ll2 = new ModelRenderer(this, 20, 39);
	      ll2.addBox(-0.9F, 3F, 1.5F, 4, 7, 4);
	      //ll2.setRotationPoint(1.9F, 12F, 0F);
	      ll2.setTextureSize(64, 64);
	      setRotation(ll2, -0.5235988F, 0F, 0F);
	      ll3 = new ModelRenderer(this, 0, 50);
	      ll3.addBox(-1.4F, 9F, -4F, 5, 3, 5);
	      //ll3.setRotationPoint(1.9F, 12F, 0F);
	      ll3.setTextureSize(64, 64);
	      setRotation(ll3, 0F, 0F, 0F);
	      
	      this.bipedHead.addChild(head);
	      this.bipedHead.addChild(h2);
	      this.bipedHead.addChild(h3);
	      this.bipedHead.addChild(h4);
	      this.bipedHead.addChild(h5);
	      this.bipedHead.addChild(h6);
	      this.bipedHead.addChild(h7);
	      this.bipedHead.addChild(h8);
	      
	      this.bipedBody.addChild(body);
	      this.bipedBody.addChild(b2);
	      
	      this.bipedRightArm.addChild(rightArm);
	      this.bipedRightArm.addChild(ra2);
	      
	      this.bipedLeftArm.addChild(leftArm);
	      this.bipedLeftArm.addChild(la2);
	      
	      this.bipedRightLeg.addChild(rightLeg);
	      this.bipedRightLeg.addChild(rl2);
	      this.bipedRightLeg.addChild(rl3);
	      
	      this.bipedLeftLeg.addChild(leftLeg);
	      this.bipedLeftLeg.addChild(ll2);
	      this.bipedLeftLeg.addChild(ll3);
	      
	  }
	  
	  public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5)
	  {
	    super.render(entity, f, f1, f2, f3, f4, f5);
	    setRotationAngles(f, f1, f2, f3, f4, f5, entity);

	  }
	  
	  private void setRotation(ModelRenderer model, float x, float y, float z)
	  {
	    model.rotateAngleX = x;
	    model.rotateAngleY = y;
	    model.rotateAngleZ = z;
	  }
	  
	  public void setRotationAngles(float f, float f1, float f2, float f3, float f4, float f5, Entity e)
	  {
	    super.setRotationAngles(f, f1, f2, f3, f4, f5, e);
	  }
	
}

