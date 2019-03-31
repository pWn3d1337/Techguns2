package techguns.client.models.armor;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;

public class ModelRiotShield extends ModelBase {
    public ModelRenderer Plate;
    public ModelRenderer Handle;
    public ModelRenderer TopBorder;
    public ModelRenderer BottomBorder;
    public ModelRenderer SideBorder0;
    public ModelRenderer SideBorder1;

    public ModelRiotShield() {
        this.textureWidth = 32;
        this.textureHeight = 32;
        this.BottomBorder = new ModelRenderer(this, 0, 22);
        this.BottomBorder.setRotationPoint(-5.0F, 10.0F, -1.5F);
        this.BottomBorder.addBox(0.0F, 0.0F, 0.0F, 10, 1, 1, 0.0F);
        this.SideBorder1 = new ModelRenderer(this, 27, 0);
        this.SideBorder1.setRotationPoint(-6.0F, -10.5F, -1.5F);
        this.SideBorder1.addBox(0.0F, 0.0F, 0.0F, 1, 21, 1, 0.0F);
        this.Plate = new ModelRenderer(this, 0, 0);
        this.Plate.setRotationPoint(-5.0F, -10.0F, -2.0F);
        this.Plate.addBox(0.0F, 0.0F, 0.0F, 10, 20, 1, 0.0F);
        this.Handle = new ModelRenderer(this, 16, 20);
        this.Handle.setRotationPoint(-1.0F, -3.0F, -1.0F);
        this.Handle.addBox(0.0F, 0.0F, 0.0F, 2, 6, 6, 0.0F);
        this.SideBorder0 = new ModelRenderer(this, 27, 0);
        this.SideBorder0.setRotationPoint(5.0F, -10.5F, -1.5F);
        this.SideBorder0.addBox(0.0F, 0.0F, 0.0F, 1, 21, 1, 0.0F);
        this.TopBorder = new ModelRenderer(this, 0, 22);
        this.TopBorder.setRotationPoint(-5.0F, -11.0F, -1.5F);
        this.TopBorder.addBox(0.0F, 0.0F, 0.0F, 10, 1, 1, 0.0F);
    }

    @Override
    public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5) { 
        this.BottomBorder.render(f5);
        this.SideBorder1.render(f5);
        this.Plate.render(f5);
        this.Handle.render(f5);
        this.SideBorder0.render(f5);
        this.TopBorder.render(f5);
    }

}
