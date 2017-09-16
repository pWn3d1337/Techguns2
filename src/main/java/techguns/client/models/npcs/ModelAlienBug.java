package techguns.client.models.npcs;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.MathHelper;
import techguns.entities.npcs.AlienBug;

public class ModelAlienBug extends ModelBase {
    public ModelRenderer JAW_L;
    public ModelRenderer h1;
    public ModelRenderer JAW_U;
    public ModelRenderer h2;
    public ModelRenderer h3;
    public ModelRenderer b4;
    public ModelRenderer b2;
    public ModelRenderer b3;
    public ModelRenderer b1;
    public ModelRenderer L1_A;
    public ModelRenderer L1_B;
    public ModelRenderer L1_A_1;
    public ModelRenderer L1_B_1;
    public ModelRenderer L1_A_2;
    public ModelRenderer L1_B_2;
    public ModelRenderer L1_A_3;
    public ModelRenderer L1_B_3;

    public ModelAlienBug() {
    	this.textureWidth = 64;
        this.textureHeight = 64;
        this.L1_A_1 = new ModelRenderer(this, 0, 4);
        this.L1_A_1.setRotationPoint(1.5F, 17.0F, 4.0F);
        this.L1_A_1.addBox(0.0F, -1.5F, -1.5F, 9, 3, 3, 0.0F);
        this.setRotateAngle(L1_A_1, 0.0F, -0.3490658503988659F, -0.5235987755982988F);
        this.b4 = new ModelRenderer(this, 56, 30);
        this.b4.setRotationPoint(-1.0F, 10.5F, 14.2F);
        this.b4.addBox(0.0F, 0.0F, 0.0F, 2, 2, 2, 0.0F);
        this.b1 = new ModelRenderer(this, 0, 33);
        this.b1.setRotationPoint(-2.5F, 14.0F, -2.0F);
        this.b1.addBox(0.0F, 0.0F, 0.0F, 5, 5, 9, 0.0F);
        this.h1 = new ModelRenderer(this, 20, 9);
        this.h1.setRotationPoint(0.0F, 11.5F, -1.0F);
        this.h1.addBox(-2.5F, -2.5F, -3.0F, 5, 5, 6, 0.0F);
        this.setRotateAngle(h1, 0.136659280431156F, 0.0F, 0.0F);
        this.b2 = new ModelRenderer(this, 23, 33);
        this.b2.setRotationPoint(-3.0F, 13.0F, 6.0F);
        this.b2.addBox(0.0F, 0.0F, 0.0F, 6, 4, 4, 0.0F);
        this.setRotateAngle(b2, 0.5918411493512771F, 0.0F, 0.0F);
        this.b3 = new ModelRenderer(this, 43, 31);
        this.b3.setRotationPoint(-2.0F, 11.0F, 10.0F);
        this.b3.addBox(0.0F, 0.0F, 0.0F, 4, 3, 4, 0.0F);
        this.setRotateAngle(b3, 0.22759093446006054F, 0.0F, 0.0F);
        this.L1_A_3 = new ModelRenderer(this, 0, 4);
        this.L1_A_3.setRotationPoint(1.5F, 17.0F, 0.0F);
        this.L1_A_3.addBox(0.0F, -1.5F, -1.5F, 9, 3, 3, 0.0F);
        this.setRotateAngle(L1_A_3, 0.0F, 0.3490658503988659F, -0.5235987755982988F);
        this.h2 = new ModelRenderer(this, 36, 18);
        this.h2.setRotationPoint(-3.0F, -6.5F, 3.5F);
        this.h2.addBox(0.0F, 0.0F, -7.0F, 6, 3, 8, 0.0F);
        this.setRotateAngle(h2, 0.3490658503988659F, 0.0F, 0.0F);
        this.JAW_U = new ModelRenderer(this, 2, 15);
        this.JAW_U.setRotationPoint(-2.0F, -1.0F, -2.0F);
        this.JAW_U.addBox(0.0F, -2.5F, -10.0F, 4, 4, 10, 0.0F);
        this.setRotateAngle(JAW_U, -0.18203784098300857F, 0.0F, 0.0F);
        this.L1_B_3 = new ModelRenderer(this, 0, 0);
        this.L1_B_3.setRotationPoint(7.5F, 0.0F, 0.0F);
        this.L1_B_3.addBox(0.0F, -1.0F, -1.0F, 11, 2, 2, 0.0F);
        this.setRotateAngle(L1_B_3, 0.0F, 0.0F, 1.7453292519943295F);
        this.L1_A_2 = new ModelRenderer(this, 0, 4);
        this.L1_A_2.setRotationPoint(-1.5F, 17.0F, 4.0F);
        this.L1_A_2.addBox(0.0F, -1.5F, -1.5F, 9, 3, 3, 0.0F);
        this.setRotateAngle(L1_A_2, 0.0F, -2.792526803190927F, 0.5235987755982988F);
        this.JAW_L = new ModelRenderer(this, 21, 20);
        this.JAW_L.setRotationPoint(-1.5F, -1.5F, -2.5F);
        this.JAW_L.addBox(0.0F, 0.0F, -9.0F, 3, 4, 9, 0.0F);
        this.setRotateAngle(JAW_L, 0.4553564018453205F, 0.0F, 0.0F);
        this.h3 = new ModelRenderer(this, 22, 0);
        this.h3.setRotationPoint(-3.0F, -6.5F, 3.5F);
        this.h3.addBox(-4.0F, 1.0F, -7.0F, 14, 0, 14, 0.0F);
        this.setRotateAngle(h3, 0.3490658503988659F, 0.0F, 0.0F);
        this.L1_A = new ModelRenderer(this, 0, 4);
        this.L1_A.setRotationPoint(-1.5F, 17.0F, 0.0F);
        this.L1_A.addBox(0.0F, -1.5F, -1.5F, 9, 3, 3, 0.0F);
        this.setRotateAngle(L1_A, 0.0F, 2.792526803190927F, 0.5235987755982988F);
        this.L1_B_1 = new ModelRenderer(this, 0, 0);
        this.L1_B_1.setRotationPoint(7.5F, 0.0F, 0.0F);
        this.L1_B_1.addBox(0.0F, -1.0F, -1.0F, 11, 2, 2, 0.0F);
        this.setRotateAngle(L1_B_1, 0.0F, 0.0F, 1.7453292519943295F);
        this.L1_B = new ModelRenderer(this, 0, 0);
        this.L1_B.setRotationPoint(7.5F, 0.0F, 0.0F);
        this.L1_B.addBox(0.0F, -1.0F, -1.0F, 11, 2, 2, 0.0F);
        this.setRotateAngle(L1_B, 0.0F, 0.0F, 1.7453292519943295F);
        this.L1_B_2 = new ModelRenderer(this, 0, 0);
        this.L1_B_2.setRotationPoint(7.5F, 0.0F, 0.0F);
        this.L1_B_2.addBox(0.0F, -1.0F, -1.0F, 11, 2, 2, 0.0F);
        this.setRotateAngle(L1_B_2, 0.0F, 0.0F, 1.7453292519943295F);
        this.h1.addChild(this.h2);
        this.h1.addChild(this.JAW_U);
        this.L1_A_3.addChild(this.L1_B_3);
        this.h1.addChild(this.JAW_L);
        this.h1.addChild(this.h3);
        this.L1_A_1.addChild(this.L1_B_1);
        this.L1_A.addChild(this.L1_B);
        this.L1_A_2.addChild(this.L1_B_2);
    }

    @Override
    public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5) { 
    	this.setRotationAngles(f, f1, f2, f3, f4, f5, entity);
    	this.b3.render(f5);
        this.b2.render(f5);
        this.b1.render(f5);
        this.h1.render(f5);
        //this.h2.render(f5);
        this.L1_A_1.render(f5);
        //this.JAW_U.render(f5);
        //this.JAW_L.render(f5);
        this.L1_A.render(f5);
        this.L1_A_3.render(f5);
        this.L1_A_2.render(f5);
        //this.h3.render(f5);
        this.b4.render(f5);
    }

    /**
     * This is a helper function from Tabula to set the rotation of model parts
     */
    public void setRotateAngle(ModelRenderer modelRenderer, float x, float y, float z) {
        modelRenderer.rotateAngleX = x;
        modelRenderer.rotateAngleY = y;
        modelRenderer.rotateAngleZ = z;
    }
    
    /**
     * Sets the model's various rotation angles. For bipeds, par1 and par2 are used for animating the movement of arms
     * and legs, where par1 represents the time(so that arms and legs swing back and forth) and par2 represents how
     * "far" arms and legs can swing at most.
     */
    public void setRotationAngles(float p1, float p2, float p3, float p4, float p5, float p6, Entity entity)
    {
    	//HEAD
    	this.h1.rotateAngleY = p4 / (180F / (float)Math.PI);
        this.h1.rotateAngleX = p5 / (180F / (float)Math.PI);
    	
    	//LEGS
    	 this.L1_A.rotateAngleY = 2.792526803190927F;
    	 this.L1_A.rotateAngleZ = 0.5235987755982988F;   
    	 this.L1_A.rotateAngleX = 0F;
    	 this.L1_A_1.rotateAngleY = -0.3490658503988659F;
    	 this.L1_A_1.rotateAngleZ = -0.5235987755982988F; 
    	 this.L1_A_1.rotateAngleX = 0F;
    	 this.L1_A_2.rotateAngleY = -2.792526803190927F;
    	 this.L1_A_2.rotateAngleZ = 0.5235987755982988F; 
    	 this.L1_A_2.rotateAngleX = 0F;
    	 this.L1_A_3.rotateAngleY = 0.3490658503988659F;
    	 this.L1_A_3.rotateAngleZ = -0.5235987755982988F;
    	 this.L1_A_3.rotateAngleX = 0F;
    	 
    	 
    	float f9 = (float) (-Math.cos(p1 * 0.6662F * 2.0F + 0.0F) * 0.4F) * p2;
        float f10 = (float) (-(Math.cos(p1 * 0.6662F * 2.0F + (float)Math.PI) * 0.4F) * p2);
        float f11 = (float) (-Math.cos(p1 * 0.6662F * 3.0F + 0.0F) * 0.4F) * p2;
        float f12 = (float) (-(Math.cos(p1 * 0.6662F * 4.0F + (float)Math.PI) * 0.4F) * p2);

    	this.L1_A.rotateAngleY += f9*1.25f;
        this.L1_A_3.rotateAngleY += -f9*1.25f;
        this.L1_A_2.rotateAngleY += f10*1.25f;
        this.L1_A_1.rotateAngleY += -f10*1.25f;
        
        this.L1_A.rotateAngleX += -f9*1f;
        this.L1_A_3.rotateAngleX += f9*1f;
        this.L1_A_2.rotateAngleX += -f10*1f;
        this.L1_A_1.rotateAngleX += f10*1f;
        
        this.L1_A.rotateAngleZ += -f11*0.75f;
        this.L1_A_3.rotateAngleZ += f11*0.75f;
        this.L1_A_2.rotateAngleZ += -f12*0.75f;
        this.L1_A_1.rotateAngleZ += f12*0.75f;
        
    	//System.out.printf("setRotationAngles(%.2f, %.2f, %.2f, %.2f, %.2f, %.2f, ...)\n", p1, p2, p3, p4, p5, p6);
        
        //MOUTH
        this.JAW_L.rotateAngleX = 0.4553564018453205F;
        this.JAW_U.rotateAngleX = -0.18203784098300857F;     
        //IDLE
        this.JAW_U.rotateAngleX += MathHelper.cos(p3 * 0.1F) * 0.1F;
        this.JAW_L.rotateAngleX -= MathHelper.cos(p3 * 0.2F) * 0.1F;
        
        //ATTACK - TODO: improve anim
        if (entity instanceof AlienBug) {
        	int a = ((AlienBug)entity).getAttackTimer();
    		//System.out.println("Bug Attack - a="+a);
        	if (a > 5 && a <= 10) {
        		float f = ((float)(a-5))/5.0f;

        		this.JAW_U.rotateAngleX += (0.5 * (1-Math.cos(f*Math.PI))) * 0.3F;
                this.JAW_L.rotateAngleX -= (0.5 * (1-Math.cos(f*Math.PI))) * 0.4F;
        	}
        } 

    }
}