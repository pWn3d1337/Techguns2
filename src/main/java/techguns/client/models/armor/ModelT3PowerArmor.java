package techguns.client.models.armor;

import net.minecraft.client.model.ModelBiped;
import net.minecraft.client.model.ModelRenderer;

public class ModelT3PowerArmor extends ModelBiped {

    ModelRenderer RA02;
    ModelRenderer RA03;
    ModelRenderer RA01;
    ModelRenderer RA04;
    ModelRenderer RA05;
    ModelRenderer RA06;
    ModelRenderer LA01;
    ModelRenderer LA02;
    ModelRenderer LA03;
    ModelRenderer LA04;
    ModelRenderer LA05;
    ModelRenderer LA06;
    ModelRenderer H01;
    ModelRenderer H04;
    ModelRenderer H06;
    ModelRenderer H03;
    ModelRenderer H09;
    ModelRenderer H08;
    ModelRenderer H05;
    ModelRenderer H10;
    ModelRenderer H02;
    ModelRenderer H07;
    ModelRenderer B01;
    ModelRenderer B02;
    ModelRenderer B03;
    ModelRenderer B04;
    ModelRenderer B05;
    ModelRenderer B09;
    ModelRenderer B07;
    ModelRenderer B06;
    ModelRenderer B08;
    ModelRenderer P03;
    ModelRenderer P01;
    ModelRenderer P02;
    ModelRenderer RL01;
    ModelRenderer RL02;
    ModelRenderer RL03;
    ModelRenderer RL04;
    ModelRenderer RL05;
    ModelRenderer LL01;
    ModelRenderer LL02;
    ModelRenderer LL03;
    ModelRenderer LL04;
    ModelRenderer LL05;
    ModelRenderer RB01;
    ModelRenderer RB02;
    ModelRenderer LB01;
    ModelRenderer LB02;
  
  
    public ModelT3PowerArmor(int type) {
    	this(type, 0.0f); 
    }
    
  public ModelT3PowerArmor(int type, float scale)
  {
	  super(scale);
	  /*
        this.bipedRightArm = new ModelRenderer(this, 40, 16);
        this.bipedRightArm.addBox(-3.0F, -2.0F, -2.0F, 4, 12, 4, modelSize);
        this.bipedRightArm.setRotationPoint(-5.0F, 2.0F + p_i1149_2_, 0.0F);
        this.bipedLeftArm = new ModelRenderer(this, 40, 16);
        this.bipedLeftArm.mirror = true;
        this.bipedLeftArm.addBox(-1.0F, -2.0F, -2.0F, 4, 12, 4, modelSize);
        this.bipedLeftArm.setRotationPoint(5.0F, 2.0F + p_i1149_2_, 0.0F);
	   */
	  
      //this.bipedCloak = new ModelRenderer(this, 0, 0);
      //this.bipedEars = new ModelRenderer(this, 24, 0);
      this.bipedHead = new ModelRenderer(this, 0, 0);
      this.bipedHead.setRotationPoint(0.0F, 0.0F, 0.0F);
      this.bipedHeadwear = new ModelRenderer(this, 32, 0);
      this.bipedHeadwear.setRotationPoint(0.0F, 0.0F, 0.0F);
      this.bipedBody = new ModelRenderer(this, 16, 16);
      this.bipedBody.setRotationPoint(0.0F, 0.0F, 0.0F);
      this.bipedRightArm = new ModelRenderer(this, 40, 16);
      this.bipedRightArm.setRotationPoint(-5.0F, 2.0F, 0.0F);
      this.bipedLeftArm = new ModelRenderer(this, 40, 16);
      //this.bipedLeftArm.mirror = false;
      this.bipedLeftArm.setRotationPoint(5.0F, 2.0F, 0.0F);
      this.bipedRightLeg = new ModelRenderer(this, 0, 16);
      this.bipedRightLeg.setRotationPoint(-1.9F, 12.0F, 0.0F);
      this.bipedLeftLeg = new ModelRenderer(this, 0, 16);
      //this.bipedLeftLeg.mirror = false;
      this.bipedLeftLeg.setRotationPoint(1.9F, 12.0F, 0.0F);
	  
	  
    textureWidth = 128;
    textureHeight = 64;
    
    
    
    float ArmOffsetX = 5.0F;
    float ArmOffsetY = -2.0F;
    float LegOffsetX = 1.9F;
    float LegOffsetY = -12.0F;
    //float RARotZ = 0.0F;
    
   RA02 = new ModelRenderer(this, 72, 8);
   RA02.addBox(-8F, 0F, 0F, 7, 2, 8);
   RA02.setRotationPoint(-5F+ArmOffsetX, -2F+ArmOffsetY, -4F);
   RA02.setTextureSize(128, 64);
   RA02.mirror = true;
   setRotation(RA02, 0F, 0F, -0.5235988F);
   
   RA03 = new ModelRenderer(this, 70, 27);
   RA03.addBox(0F, 0F, 0F, 3, 3, 6);
   RA03.setRotationPoint(-8F+ArmOffsetX, 0F+ArmOffsetY, -3F);
   RA03.setTextureSize(128, 64);
   RA03.mirror = true;
   setRotation(RA03, 0F, 0F, 0F);
   
   RA01 = new ModelRenderer(this, 69, 38);
   RA01.addBox(0F, 0F, 0F, 5, 13, 5);
   RA01.setRotationPoint(-8.5F+ArmOffsetX, -0.5F+ArmOffsetY, -2.5F);
   RA01.setTextureSize(128, 64);
   RA01.mirror = true;
   setRotation(RA01, 0F, 0F, 0F);
   
   RA04 = new ModelRenderer(this, 73, 19);
   RA04.addBox(-8.5F, 0F, 0F, 7, 1, 6);
   RA04.setRotationPoint(-5F+ArmOffsetX, -3F+ArmOffsetY, -3F);
   RA04.setTextureSize(128, 64);
   RA04.mirror = true;
   setRotation(RA04, 0F, 0F, -0.5235988F);
   
   RA05 = new ModelRenderer(this, 56, 38);
   RA05.addBox(0F, 0F, 0F, 1, 5, 4);
   RA05.setRotationPoint(-9.5F+ArmOffsetX, 6.5F+ArmOffsetY, -2F);
   RA05.setTextureSize(128, 64);
   RA05.mirror = true;
   setRotation(RA05, 0F, 0F, 0F);
   
   RA06 = new ModelRenderer(this, 46, 38);
   RA06.addBox(0F, 0F, 0F, 3, 4, 1);
   RA06.setRotationPoint(-8F+ArmOffsetX, 4F+ArmOffsetY, 2F);
   RA06.setTextureSize(128, 64);
   RA06.mirror = true;
   setRotation(RA06, 0F, 0F, 0F);
   
   LA01 = new ModelRenderer(this, 90, 38);
   LA01.addBox(0F, 0F, 0F, 5, 13, 5);
   LA01.setRotationPoint(3.5F-ArmOffsetX, -0.5F+ArmOffsetY, -2.5F);
   LA01.setTextureSize(128, 64);
   LA01.mirror = true;
   setRotation(LA01, 0F, 0F, 0F);
   
   
   LA02 = new ModelRenderer(this, 72, 8);
   LA02.addBox(1F, 0F, 0F, 7, 2, 8);
   LA02.setRotationPoint(5F-ArmOffsetX, -2F+ArmOffsetY, -4F);
   LA02.setTextureSize(128, 64);
   LA02.mirror = true;
   setRotation(LA02, 0F, 0F, 0.5235988F);
   
   LA03 = new ModelRenderer(this, 70, 27);
   LA03.addBox(0F, 0F, 0F, 3, 3, 6);
   LA03.setRotationPoint(5F-ArmOffsetX, 0F+ArmOffsetY, -3F);
   LA03.setTextureSize(128, 64);
   LA03.mirror = true;
   setRotation(LA03, 0F, 0F, 0F);
   
   LA04 = new ModelRenderer(this, 100, 19);
   LA04.addBox(1.5F, 0F, 0F, 7, 1, 6);
   LA04.setRotationPoint(5F-ArmOffsetX, -3F+ArmOffsetY, -3F);
   LA04.setTextureSize(128, 64);
   LA04.mirror = true;
   setRotation(LA04, 0F, 0F, 0.5235988F);
   
   LA05 = new ModelRenderer(this, 56, 38);
   LA05.addBox(-1.5F, -2.5F, -2F, 1, 5, 4);
   LA05.setRotationPoint(10F-ArmOffsetX, 9F+ArmOffsetY, 0F);
   LA05.setTextureSize(128, 64);
   LA05.mirror = true;
   setRotation(LA05, 0F, 0F, 0F);
   
   LA06 = new ModelRenderer(this, 46, 38);
   LA06.addBox(-1F, -1F, 0F, 3, 4, 1);
   LA06.setRotationPoint(6F-ArmOffsetX, 5F+ArmOffsetY, 2F);
   LA06.setTextureSize(128, 64);
   LA06.mirror = true;
   setRotation(LA06, 0F, 0F, 0F);
   
   H01 = new ModelRenderer(this, 0, 0);
   H01.addBox(0F, 0F, 0F, 9, 9, 9,scale);
   H01.setRotationPoint(-4.5F, -8.5F, -4.5F);
   H01.setTextureSize(128, 64);
   H01.mirror = true;
   setRotation(H01, 0F, 0F, 0F);
   
   H04 = new ModelRenderer(this, 0, 28);
   H04.addBox(-3F, 0F, 0F, 3, 2, 2,scale);
   H04.setRotationPoint(-2F, -2F, -5.5F);
   H04.setTextureSize(128, 64);
   H04.mirror = true;
   setRotation(H04, 0F, 0F, 0F);
   
   H06 = new ModelRenderer(this, 89, 27);
   H06.addBox(0F, 0F, 0F, 1, 2, 8,scale);
   H06.setRotationPoint(-5F, -2F, -3.5F);
   H06.setTextureSize(128, 64);
   H06.mirror = true;
   setRotation(H06, 0F, 0F, 0F);
   
   H03 = new ModelRenderer(this, 0, 33);
   H03.addBox(0F, 0F, 0F, 3, 2, 2,scale);
   H03.setRotationPoint(2F, -2F, -5.5F);
   H03.setTextureSize(128, 64);
   H03.mirror = true;
   setRotation(H03, 0F, 0F, 0F);
   
   H09 = new ModelRenderer(this, 24, 23);
   H09.addBox(0F, 0F, 0F, 4, 3, 1,scale);
   H09.setRotationPoint(-2F, -2.5F, 4.5F);
   H09.setTextureSize(128, 64);
   H09.mirror = true;
   setRotation(H09, 0F, 0F, 0F);
   
   H08 = new ModelRenderer(this, 19, 19);
   H08.addBox(0F, 0F, 0F, 9, 2, 1,scale);
   H08.setRotationPoint(-4.5F, -2F, 4F);
   H08.setTextureSize(128, 64);
   H08.mirror = true;
   setRotation(H08, 0F, 0F, 0F);
   
   H05 = new ModelRenderer(this, 13, 27);
   H05.addBox(0F, 0F, 0F, 1, 2, 8, scale);
   H05.setRotationPoint(4F, -2F, -3.5F);
   H05.setTextureSize(128, 64);
   H05.mirror = true;
   setRotation(H05, 0F, 0F, 0F);
   
   H10 = new ModelRenderer(this, 28, 0);
   H10.addBox(-1F, -1F, 0F, 2, 2, 4,scale);
   H10.setRotationPoint(-5.5F, -6F, -3F);
   H10.setTextureSize(128, 64);
   H10.mirror = true;
   setRotation(H10, 0F, 0F, 0.7853982F);
   
   H02 = new ModelRenderer(this, 0, 19);
   H02.addBox(0F, 0F, 0F, 4, 4, 3,scale);
   H02.setRotationPoint(-2F, -3F, -6F);
   H02.setTextureSize(128, 64);
   H02.mirror = true;
   setRotation(H02, 0F, 0F, 0F);
   
   H07 = new ModelRenderer(this, 0, 5);
   H07.addBox(0F, 0F, 0F, 2, 2, 1,scale);
   H07.setRotationPoint(1F, -8F, -5F);
   H07.setTextureSize(128, 64);
   H07.mirror = true;
   setRotation(H07, 0F, 0F, 0F);
   
   B01 = new ModelRenderer(this, 41, 19);
   B01.addBox(-4F, 0F, -2F, 9, 12, 5);
   B01.setRotationPoint(-0.5F, -0.5F, -0.5F);
   B01.setTextureSize(128, 64);
   B01.mirror = true;
   setRotation(B01, 0F, 0F, 0F);
   
   B02 = new ModelRenderer(this, 48, 0);
   B02.addBox(0F, 0F, 0F, 8, 5, 3);
   B02.setRotationPoint(-4F, 2F, -5F);
   B02.setTextureSize(128, 64);
   B02.mirror = true;
   setRotation(B02, 0F, 0F, 0F);
   
   B03 = new ModelRenderer(this, 71, 0);
   B03.addBox(0F, 0F, 0F, 7, 5, 2);
   B03.setRotationPoint(-3.5F, 7F, -4.5F);
   B03.setTextureSize(128, 64);
   B03.mirror = true;
   setRotation(B03, 0.3665191F, 0F, 0F);
   
   B04 = new ModelRenderer(this, 48, 10);
   B04.addBox(-4F, 0F, -2F, 3, 5, 2);
   B04.setRotationPoint(4.5F, 5F, 4F);
   B04.setTextureSize(128, 64);
   B04.mirror = true;
   setRotation(B04, 0F, 0F, 0F);
   
   B05 = new ModelRenderer(this, 48, 10);
   B05.addBox(-4F, 0F, -2F, 3, 5, 2);
   B05.setRotationPoint(0.5F, 5F, 4F);
   B05.setTextureSize(128, 64);
   B05.mirror = true;
   
   setRotation(B05, 0F, 0F, 0F);
   B09 = new ModelRenderer(this, 64, 10);
   B09.addBox(-1F, -1F, 0F, 2, 2, 1);
   B09.setRotationPoint(0F, 2.5F, 3F);
   B09.setTextureSize(128, 64);
   B09.mirror = true;
   
   setRotation(B09, 0F, 0F, 0.7853982F);
   B07 = new ModelRenderer(this, 59, 10);
   B07.addBox(0F, 0F, 0F, 1, 2, 1);
   B07.setRotationPoint(1.5F, 3F, 2.5F);
   B07.setTextureSize(128, 64);
   B07.mirror = true;
   setRotation(B07, 0F, 0F, 0F);
   
   B06 = new ModelRenderer(this, 59, 10);
   B06.addBox(0F, 0F, 0F, 1, 2, 1);
   B06.setRotationPoint(-2.5F, 3F, 2.5F);
   B06.setTextureSize(128, 64);
   B06.mirror = true;
   setRotation(B06, 0F, 0F, 0F);
   
   B08 = new ModelRenderer(this, 59, 15);
   B08.addBox(0F, 0F, 0F, 5, 1, 1);
   B08.setRotationPoint(-2.5F, 2F, 2.5F);
   B08.setTextureSize(128, 64);
   B08.mirror = true;
   setRotation(B08, 0F, 0F, 0F);
   
   P03 = new ModelRenderer(this, 21, 47);
   P03.addBox(0F, 0F, 0F, 3, 2, 6);
   P03.setRotationPoint(-1.5F, 13F, -3F);
   P03.setTextureSize(128, 64);
   P03.mirror = true;
   setRotation(P03, 0F, 0F, 0F);
   
   P01 = new ModelRenderer(this, 40, 48);
   P01.addBox(0F, 0F, 0F, 9, 3, 5);
   P01.setRotationPoint(-4.5F, 11.5F, -2.5F);
   P01.setTextureSize(128, 64);
   P01.mirror = true;
   setRotation(P01, 0F, 0F, 0F);
   
   P02 = new ModelRenderer(this, 0, 38);
   P02.addBox(0F, 0F, 0F, 10, 2, 6);
   P02.setRotationPoint(-5F, 11F, -3F);
   P02.setTextureSize(128, 64);
   P02.mirror = true;
   setRotation(P02, 0F, 0F, 0F);
   
   RL01 = new ModelRenderer(this, 0, 47);
   RL01.addBox(-2F, 0F, -2F, 5, 9, 5);
   RL01.setRotationPoint(-2.5F+LegOffsetX, 11.5F+LegOffsetY, -0.5F);
   RL01.setTextureSize(128, 64);
   RL01.mirror = true;
   setRotation(RL01, 0F, 0F, 0F);
   
   RL02 = new ModelRenderer(this, 21, 56);
   RL02.addBox(0F, 0F, 0F, 3, 3, 1);
   RL02.setRotationPoint(-2.3F+LegOffsetX, 14.5F+LegOffsetY, -3F);
   RL02.setTextureSize(128, 64);
   RL02.mirror = true;
   setRotation(RL02, 0F, 0F, 0.7853982F);
   
   RL03 = new ModelRenderer(this, 30, 56);
   RL03.addBox(0F, -1F, -1F, 1, 2, 2);
   RL03.setRotationPoint(-5F+LegOffsetX, 16.5F+LegOffsetY, 0F);
   RL03.setTextureSize(128, 64);
   RL03.mirror = true;
   setRotation(RL03, 0.7853982F, 0F, 0F);
   
   RL04 = new ModelRenderer(this, 33, 38);
   RL04.addBox(0F, -3F, -1F, 1, 3, 2);
   RL04.setRotationPoint(-5F+LegOffsetX, 15.5F+LegOffsetY, 0F);
   RL04.setTextureSize(128, 64);
   RL04.mirror = true;
   setRotation(RL04, 0F, 0F, 0.148353F);
   
   RL05 = new ModelRenderer(this, 33, 38);
   RL05.addBox(0F, 0F, -1F, 1, 3, 2);
   RL05.setRotationPoint(-5F+LegOffsetX, 17.5F+LegOffsetY, 0F);
   RL05.setTextureSize(128, 64);
   RL05.mirror = true;
   setRotation(RL05, 0F, 0F, -0.148353F);
   
   LL01 = new ModelRenderer(this, 0, 47);
   LL01.addBox(-2F, 0F, -2F, 5, 9, 5);
   LL01.setRotationPoint(1.5F-LegOffsetX, 11.5F+LegOffsetY, -0.5F);
   LL01.setTextureSize(128, 64);
   LL01.mirror = true;
   setRotation(LL01, 0F, 0F, 0F);
   
   LL02 = new ModelRenderer(this, 21, 56);
   LL02.addBox(0F, 0F, 0F, 3, 3, 1);
   LL02.setRotationPoint(2.3F-LegOffsetX, 14.5F+LegOffsetY, -3F);
   LL02.setTextureSize(128, 64);
   LL02.mirror = true;
   setRotation(LL02, 0F, 0F, 0.7853982F);
   
   LL03 = new ModelRenderer(this, 30, 56);
   LL03.addBox(0F, -1F, -1F, 1, 2, 2);
   LL03.setRotationPoint(4F-LegOffsetX, 16.5F+LegOffsetY, 0F);
   LL03.setTextureSize(128, 64);
   LL03.mirror = true;
   setRotation(LL03, 0.7853982F, 0F, 0F);
   
   LL04 = new ModelRenderer(this, 33, 38);
   LL04.addBox(0F, -3F, -1F, 1, 3, 2);
   LL04.setRotationPoint(4F-LegOffsetX, 15.5F+LegOffsetY, 0F);
   LL04.setTextureSize(128, 64);
   LL04.mirror = true;
   setRotation(LL04, 0F, 0F, -0.148353F);

   LL05 = new ModelRenderer(this, 33, 38);
   LL05.addBox(0F, 0F, -1F, 1, 3, 2);
   LL05.setRotationPoint(4F-LegOffsetX, 17.5F+LegOffsetY, 0F);
   LL05.setTextureSize(128, 64);
   LL05.mirror = true;
   setRotation(LL05, 0F, 0F, 0.148353F);

   RB01 = new ModelRenderer(this, 103, 0);
   RB01.addBox(-2F, 0F, -2F, 5, 4, 5);
   RB01.setRotationPoint(-2.5F+LegOffsetX, 20.5F+LegOffsetY, -0.5F);
   RB01.setTextureSize(128, 64);
   RB01.mirror = true;
   setRotation(RB01, 0F, 0F, 0F);
   
   RB02 = new ModelRenderer(this, 103, 10);
   RB02.addBox(-2F, 0F, -2F, 4, 2, 2);
   RB02.setRotationPoint(-2F+LegOffsetX, 22F+LegOffsetY, -2F);
   RB02.setTextureSize(128, 64);
   RB02.mirror = true;
   setRotation(RB02, 0F, 0F, 0F);
   
   LB01 = new ModelRenderer(this, 103, 0);
   LB01.addBox(2F, 0F, -2F, 5, 4, 5);
   LB01.setRotationPoint(-2.5F-LegOffsetX, 20.5F+LegOffsetY, -0.5F);
   LB01.setTextureSize(128, 64);
   LB01.mirror = true;
   setRotation(LB01, 0F, 0F, 0F);
   
   LB02 = new ModelRenderer(this, 103, 10);
   LB02.addBox(-2F, 0F, -2F, 4, 2, 2);
   LB02.setRotationPoint(2F-LegOffsetX, 22F+LegOffsetY, -2F);
   LB02.setTextureSize(128, 64);
   LB02.mirror = true;
   setRotation(LB02, 0F, 0F, 0F);
      
      if (type==0){
    	  this.bipedLeftArm.addChild(LA01);
    	  this.bipedLeftArm.addChild(LA02);
    	  this.bipedLeftArm.addChild(LA03);
    	  this.bipedLeftArm.addChild(LA04);
    	  this.bipedLeftArm.addChild(LA05);
    	  this.bipedLeftArm.addChild(LA06);

    	  this.bipedRightArm.addChild(RA01);
    	  this.bipedRightArm.addChild(RA02);
    	  this.bipedRightArm.addChild(RA03);
    	  this.bipedRightArm.addChild(RA04);
    	  this.bipedRightArm.addChild(RA05);
    	  this.bipedRightArm.addChild(RA06);
    	  
    	  this.bipedHead.addChild(H01);
    	  this.bipedHead.addChild(H02);
    	  this.bipedHead.addChild(H03);
    	  this.bipedHead.addChild(H04);
    	  this.bipedHead.addChild(H05);
    	  this.bipedHead.addChild(H06);
    	  this.bipedHead.addChild(H07);
    	  this.bipedHead.addChild(H08);
    	  this.bipedHead.addChild(H09);
    	  this.bipedHead.addChild(H10);
    	  
    	  this.bipedBody.addChild(B01);
    	  this.bipedBody.addChild(B02);  
    	  this.bipedBody.addChild(B03);
    	  this.bipedBody.addChild(B04);
    	  this.bipedBody.addChild(B05);
    	  this.bipedBody.addChild(B06);
    	  this.bipedBody.addChild(B07);
    	  this.bipedBody.addChild(B08);
    	  this.bipedBody.addChild(B09);
    	  
    	  this.bipedRightLeg.addChild(RB01);
	      this.bipedRightLeg.addChild(RB02);
	      
	      this.bipedLeftLeg.addChild(LB01);
	      this.bipedLeftLeg.addChild(LB02);
	      
      } else {
    	  this.bipedBody.addChild(P01);
	      this.bipedBody.addChild(P02);
	      this.bipedBody.addChild(P03);
	     

	      this.bipedRightLeg.addChild(RL01);
	      this.bipedRightLeg.addChild(RL02);
	      this.bipedRightLeg.addChild(RL03);
	      this.bipedRightLeg.addChild(RL04);
	      this.bipedRightLeg.addChild(RL05);
	      
	      this.bipedLeftLeg.addChild(LL01);
	      this.bipedLeftLeg.addChild(LL02);
	      this.bipedLeftLeg.addChild(LL03);
	      this.bipedLeftLeg.addChild(LL04);
	      this.bipedLeftLeg.addChild(LL05);
      }
      
  }
  
  private void setRotation(ModelRenderer model, float x, float y, float z)
  {
    model.rotateAngleX = x;
    model.rotateAngleY = y;
    model.rotateAngleZ = z;
  }
  
}
