package techguns.client.models.guns;

import net.minecraft.client.model.ModelRenderer;
import net.minecraft.client.renderer.block.model.ItemCameraTransforms.TransformType;
import net.minecraft.entity.Entity;
import techguns.client.models.ModelMultipart;

public class ModelFragGrenade extends ModelMultipart {

    public ModelRenderer Top;
    public ModelRenderer MainRot1;
    public ModelRenderer MainRot2;
    public ModelRenderer Main;
    public ModelRenderer TopRotated;
    public ModelRenderer Bottom;
    public ModelRenderer BottomRotated;
    public ModelRenderer TopPart;
    public ModelRenderer Box01;
    public ModelRenderer Box02;
    public ModelRenderer Ring;

    protected boolean showRing;
    
    public ModelFragGrenade(boolean showRing) {
        this.textureWidth = 32;
        this.textureHeight = 32;
        this.Main = new ModelRenderer(this, 13, 0);
        this.Main.setRotationPoint(-1.5F, -16.0F, -1.5F);
        this.Main.addBox(0.0F, 0.0F, 0.0F, 4, 4, 4, 0.0F);
        this.MainRot2 = new ModelRenderer(this, 13, 16);
        this.MainRot2.setRotationPoint(0.5F, -14.0F, 0.5F);
        this.MainRot2.addBox(-2.0F, 0.0F, -2.0F, 4, 2, 4, -0.1F);
        this.setRotation(MainRot2, 0.0F, 0.7853981633974483F, 0.0F);
        this.BottomRotated = new ModelRenderer(this, 15, 11);
        this.BottomRotated.setRotationPoint(0.5F, -12.0F, 0.5F);
        this.BottomRotated.addBox(-1.5F, 0.0F, -1.5F, 3, 1, 3, -0.1F);
        this.setRotation(BottomRotated, 0.0F, 0.7853981633974483F, 0.0F);
        this.TopPart = new ModelRenderer(this, 15, 23);
        this.TopPart.setRotationPoint(-0.5F, -18.7F, -0.5F);
        this.TopPart.addBox(0.0F, 0.0F, 0.0F, 2, 2, 2, -0.2F);
        this.Box02 = new ModelRenderer(this, 1, 28);
        this.Box02.setRotationPoint(4.1F, -17.3F, -0.5F);
        this.Box02.addBox(0.0F, 0.0F, 0.0F, 5, 1, 2, -0.3F);
        this.setRotation(Box02, 0.0F, 0.0F, 1.5707963267948966F);
        this.TopRotated = new ModelRenderer(this, 15, 11);
        this.TopRotated.setRotationPoint(0.5F, -17.0F, 0.5F);
        this.TopRotated.addBox(-1.5F, 0.0F, -1.5F, 3, 1, 3, -0.1F);
        this.setRotation(TopRotated, 0.0F, 0.7853981633974483F, 0.0F);
        
        this.showRing=showRing;
        if (showRing){
	        this.Ring = new ModelRenderer(this, 0, 7);
	        this.Ring.setRotationPoint(-0.5F, -20.2F, -3.3F);
	        this.Ring.addBox(0.0F, 0.0F, 0.0F, 2, 4, 4, -0.9F);
        }
        
        this.MainRot1 = new ModelRenderer(this, 13, 16);
        this.MainRot1.setRotationPoint(0.5F, -16.0F, 0.5F);
        this.MainRot1.addBox(-2.0F, 0.0F, -2.0F, 4, 2, 4, -0.1F);
        this.setRotation(MainRot1, 0.0F, 0.7853981633974483F, 0.0F);
        this.Top = new ModelRenderer(this, 15, 11);
        this.Top.setRotationPoint(-1.0F, -17.0F, -1.0F);
        this.Top.addBox(0.0F, 0.0F, 0.0F, 3, 1, 3, 0.0F);
        this.Box01 = new ModelRenderer(this, 19, 28);
        this.Box01.setRotationPoint(0.6F, -18.8F, -0.5F);
        this.Box01.addBox(0.0F, 0.0F, 0.0F, 4, 1, 2, -0.3F);
        this.setRotation(Box01, 0.0F, 0.0F, 0.40980330836826856F);
        this.Bottom = new ModelRenderer(this, 15, 11);
        this.Bottom.setRotationPoint(-1.0F, -12.0F, -1.0F);
        this.Bottom.addBox(0.0F, 0.0F, 0.0F, 3, 1, 3, 0.0F);
    }    
    
	@Override
	public void render(Entity entityIn, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw,
			float headPitch, float scale, int ammoLeft, float reloadProgress, TransformType transformType, int part,
			float fireProgress, float chargeProgress) {
		
		this.Main.render(scale);
        this.BottomRotated.render(scale);
        this.MainRot2.render(scale);
        this.Box02.render(scale);
        this.Bottom.render(scale);
        if (showRing && fireProgress<=0.0f){
        	this.Ring.render(scale);
        }
        this.TopPart.render(scale);
        this.MainRot1.render(scale);
        this.Top.render(scale);
        this.TopRotated.render(scale);
        this.Box01.render(scale);
	}

}
