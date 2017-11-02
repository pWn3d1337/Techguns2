package techguns.client.models.guns;

import net.minecraft.client.model.ModelRenderer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.block.model.ItemCameraTransforms.TransformType;
import net.minecraft.entity.Entity;
import techguns.client.models.ModelMultipart;

public class ModelAUG extends ModelMultipart {
    public ModelRenderer Barrel;
    public ModelRenderer Receiver03;
    public ModelRenderer Receiver01;
    public ModelRenderer Foregrip;
    public ModelRenderer Barrel2;
    public ModelRenderer Scope03;
    public ModelRenderer Bolt;
    public ModelRenderer Barrel2_1;
    public ModelRenderer Grip2;
    public ModelRenderer Grip3;
    public ModelRenderer Trigger02;
    public ModelRenderer Grip1;
    public ModelRenderer Stock1;
    public ModelRenderer Stock3;
    public ModelRenderer Stock2;
    public ModelRenderer Stock6;
    public ModelRenderer Stock4;
    public ModelRenderer Stock5;
    public ModelRenderer Magazine01;
    public ModelRenderer MagSocket;
    public ModelRenderer Magazine02;
    public ModelRenderer ScopeMount1;
    public ModelRenderer Scope01;
    public ModelRenderer ScopeMount2;

    public ModelAUG() {
        this.textureWidth = 128;
        this.textureHeight = 64;
        this.ScopeMount1 = new ModelRenderer(this, 1, 0);
        this.ScopeMount1.setRotationPoint(-1.5F, -10.0F, -7.0F);
        this.ScopeMount1.addBox(0.0F, 0.0F, 0.0F, 2, 8, 1, 0.0F);
        this.setRotation(ScopeMount1, -0.6108652381980153F, 0.0F, 0.0F);
        this.Scope03 = new ModelRenderer(this, 1, 13);
        this.Scope03.setRotationPoint(-0.5F, -11.5F, 1.0F);
        this.Scope03.addBox(0.0F, 0.0F, 0.0F, 3, 3, 10, 0.0F);
        this.setRotation(Scope03, 0.0F, 0.0F, 0.7853981633974483F);
        this.Stock2 = new ModelRenderer(this, 39, 27);
        this.Stock2.setRotationPoint(-2.0F, -2.0F, 21.0F);
        this.Stock2.addBox(0.0F, 0.0F, 0.0F, 3, 7, 4, 0.0F);
        this.Scope01 = new ModelRenderer(this, 1, 1);
        this.Scope01.setRotationPoint(-0.5F, -11.0F, -8.0F);
        this.Scope01.addBox(0.0F, 0.0F, 0.0F, 2, 2, 9, 0.0F);
        this.setRotation(Scope01, 0.0F, 0.0F, 0.7853981633974483F);
        this.Barrel2_1 = new ModelRenderer(this, 0, 49);
        this.Barrel2_1.setRotationPoint(-0.5F, -4.5F, -41.0F);
        this.Barrel2_1.addBox(0.0F, 0.0F, 0.0F, 2, 2, 3, 0.0F);
        this.setRotation(Barrel2_1, 0.0F, -0.0F, 0.7853981633974483F);
        this.Stock6 = new ModelRenderer(this, 49, 6);
        this.Stock6.setRotationPoint(-2.0F, -2.0F, 8.0F);
        this.Stock6.addBox(0.0F, 0.0F, 0.0F, 3, 2, 4, 0.0F);
        this.Trigger02 = new ModelRenderer(this, 64, 28);
        this.Trigger02.setRotationPoint(-1.0F, 0.5F, -1.0F);
        this.Trigger02.addBox(0.0F, 0.0F, 0.0F, 1, 3, 2, 0.0F);
        this.Stock1 = new ModelRenderer(this, 64, 0);
        this.Stock1.setRotationPoint(-2.5F, -2.0F, 25.0F);
        this.Stock1.addBox(0.0F, 0.0F, 0.0F, 4, 12, 4, 0.0F);
        this.Bolt = new ModelRenderer(this, 1, 33);
        this.Bolt.setRotationPoint(2.5F, -5.0F, -13.0F);
        this.Bolt.addBox(0.0F, 0.0F, 0.0F, 1, 2, 2, 0.0F);
        this.setRotation(Bolt, 0.0F, 0.0F, 0.7853981633974483F);
        this.Grip1 = new ModelRenderer(this, 64, 43);
        this.Grip1.setRotationPoint(-2.0F, 1.0F, 0.0F);
        this.Grip1.addBox(0.0F, 0.0F, 0.0F, 3, 9, 4, 0.0F);
        this.setRotation(Grip1, 0.4461061568097506F, 0.0F, 0.0F);
        this.Barrel = new ModelRenderer(this, 0, 32);
        this.Barrel.setRotationPoint(-0.5F, -4.5F, -38.0F);
        this.Barrel.addBox(0.0F, 0.0F, 0.0F, 2, 2, 23, 0.0F);
        this.setRotation(Barrel, 0.0F, 0.0F, 0.7853981633974483F);
        this.Stock5 = new ModelRenderer(this, 28, 11);
        this.Stock5.setRotationPoint(-2.0F, -2.0F, 11.0F);
        this.Stock5.addBox(0.0F, 0.0F, 0.0F, 3, 5, 10, 0.0F);
        this.Barrel2 = new ModelRenderer(this, 0, 40);
        this.Barrel2.setRotationPoint(-2.0F, -2.7F, -21.0F);
        this.Barrel2.addBox(0.0F, 0.0F, 0.0F, 1, 1, 6, 0.0F);
        this.setRotation(Barrel2, 0.0F, 0.0F, 0.7853981633974483F);
        this.Magazine02 = new ModelRenderer(this, 95, 19);
        this.Magazine02.setRotationPoint(-1.5F, 8.0F, 10.6F);
        this.Magazine02.addBox(0.0F, 0.0F, 0.0F, 2, 4, 6, 0.0F);
        this.setRotation(Magazine02, -0.36826447217080355F, 0.0F, 0.0F);
        this.Grip3 = new ModelRenderer(this, 72, 23);
        this.Grip3.setRotationPoint(-2.0F, 1.0F, -7.0F);
        this.Grip3.addBox(0.0F, 0.0F, 0.0F, 3, 9, 1, 0.0F);
        this.setRotation(Grip3, 0.5918411493512771F, 0.0F, 0.0F);
        this.Stock4 = new ModelRenderer(this, 28, 0);
        this.Stock4.setRotationPoint(-2.0F, -2.0F, 5.0F);
        this.Stock4.addBox(0.0F, 0.0F, 0.0F, 3, 3, 7, 0.0F);
        this.setRotation(Stock4, -0.31869712141416456F, 0.0F, 0.0F);
        this.ScopeMount2 = new ModelRenderer(this, 20, 0);
        this.ScopeMount2.setRotationPoint(-1.5F, -10.0F, 2.0F);
        this.ScopeMount2.addBox(0.0F, 0.0F, 0.0F, 2, 7, 1, 0.0F);
        this.setRotation(ScopeMount2, -0.4363323129985824F, 0.0F, 0.0F);
        this.Receiver03 = new ModelRenderer(this, 85, 37);
        this.Receiver03.setRotationPoint(-2.0F, -1.0F, -13.0F);
        this.Receiver03.addBox(0.0F, 0.0F, 0.0F, 3, 2, 18, 0.0F);
        this.Stock3 = new ModelRenderer(this, 72, 8);
        this.Stock3.setRotationPoint(-2.0F, 1.3F, 18.0F);
        this.Stock3.addBox(0.0F, 0.0F, 0.0F, 3, 3, 10, 0.0F);
        this.setRotation(Stock3, -0.4553564018453205F, 0.0F, 0.0F);
        this.Magazine01 = new ModelRenderer(this, 112, 19);
        this.Magazine01.setRotationPoint(-1.5F, 4.0F, 11.4F);
        this.Magazine01.addBox(0.0F, 0.0F, 0.0F, 2, 5, 6, 0.0F);
        this.setRotation(Magazine01, -0.20943951023931953F, 0.0F, 0.0F);
        this.Receiver01 = new ModelRenderer(this, 10, 15);
        this.Receiver01.setRotationPoint(-0.5F, -5.0F, -15.0F);
        this.Receiver01.addBox(0.0F, 0.0F, 0.0F, 4, 4, 44, 0.0F);
        this.setRotation(Receiver01, 0.0F, 0.0F, 0.7853981633974483F);
        this.Grip2 = new ModelRenderer(this, 64, 34);
        this.Grip2.setRotationPoint(-2.0F, 8.0F, -2.0F);
        this.Grip2.addBox(0.0F, 0.0F, 0.0F, 3, 1, 6, 0.0F);
        this.MagSocket = new ModelRenderer(this, 101, 4);
        this.MagSocket.setRotationPoint(-2.0F, 1.0F, 11.0F);
        this.MagSocket.addBox(0.0F, 0.0F, 0.0F, 3, 3, 8, 0.0F);
        this.setRotation(MagSocket, -0.20943951023931953F, 0.0F, 0.0F);
        this.Foregrip = new ModelRenderer(this, 32, 40);
        this.Foregrip.setRotationPoint(-2.5F, 0.0F, -14.0F);
        this.Foregrip.addBox(0.0F, 0.0F, 0.0F, 3, 9, 3, 0.0F);
        this.setRotation(Foregrip, 0.0F, 0.7853981633974483F, 0.0F);
    }


    	
@Override
public void render(Entity entityIn, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch, float scale, int ammoLeft,
		float reloadProgress, TransformType transformType, int part, float fireProgress, float chargeProgress) {
    	
    	if(part==0){
	        this.ScopeMount1.render(scale);
	        this.Scope03.render(scale);
	        this.Stock2.render(scale);
	        this.Scope01.render(scale);
	        this.Barrel2_1.render(scale);
	        this.Stock6.render(scale);
	        this.Trigger02.render(scale);
	        this.Stock1.render(scale);
	        this.Grip1.render(scale);
	        this.Barrel.render(scale);
	        this.Stock5.render(scale);
	        this.Barrel2.render(scale);
	        this.Magazine02.render(scale);
	        this.Grip3.render(scale);
	        this.Stock4.render(scale);
	        this.ScopeMount2.render(scale);
	        this.Receiver03.render(scale);
	        this.Stock3.render(scale);
	        this.Magazine01.render(scale);
	        this.Receiver01.render(scale);
	        this.Grip2.render(scale);
	        this.MagSocket.render(scale);
	        this.Foregrip.render(scale);
    	} else {
    		GlStateManager.pushMatrix();
			if (fireProgress>0) {
				float movebolt=0f;
				if (fireProgress >0.4f) {
					movebolt = 1.0f-(fireProgress-0.4f)/0.6f;
				} else {
					movebolt = fireProgress/0.4f;
				}
				GlStateManager.translate(0, 0, movebolt*0.5f);
			}
    		this.Bolt.render(scale);
    		GlStateManager.popMatrix();
    	}
    }

}