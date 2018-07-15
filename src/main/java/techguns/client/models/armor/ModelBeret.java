package techguns.client.models.armor;

import net.minecraft.client.model.ModelBiped;
import net.minecraft.client.model.ModelRenderer;

public class ModelBeret extends ModelBiped {

	public ModelRenderer berettop;
    public ModelRenderer beretside;

    public ModelBeret() {
        this.textureWidth = 32;
        this.textureHeight = 32;
        
	    this.bipedHead = new ModelRenderer(this, textureWidth, textureHeight);
      //  this.bipedHead.addBox(-4.0F, -8.0F, -4.0F, 8, 8, 8, 0.0f);
        this.bipedHead.setRotationPoint(0.0F, 0.0F, 0.0F);
        
        this.bipedHeadwear = new ModelRenderer(this, textureWidth, textureHeight);
        this.bipedHead.setRotationPoint(0.0F, 0.0F, 0.0F);
        
        
        this.berettop = new ModelRenderer(this, 0, 0);
        this.berettop.setRotationPoint(0.0F, 0.0F, 0.0F);
        this.berettop.addBox(-2.0F, -8.7F, -4.0F, 6, 2, 8, 0.7F); //scale 0.6F  y: -8.5f
        this.beretside = new ModelRenderer(this, 0, 11);
        this.beretside.setRotationPoint(-2.7F, -8.5F, -4.0F);
        this.beretside.addBox(-3.0F, 0.0F, 0.0F, 3, 2, 8, 0.65F); //scale 0.55F  y: -8.3f
        this.setRotateAngle(beretside, 0.0F, 0.0F, -0.6108652381980153F);
        
        this.bipedHead.addChild(berettop);
        this.bipedHead.addChild(beretside);
    }

    public void setRotateAngle(ModelRenderer modelRenderer, float x, float y, float z) {
        modelRenderer.rotateAngleX = x;
        modelRenderer.rotateAngleY = y;
        modelRenderer.rotateAngleZ = z;
    }
}
