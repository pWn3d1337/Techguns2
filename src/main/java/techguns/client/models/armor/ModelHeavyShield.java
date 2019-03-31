package techguns.client.models.armor;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;

public class ModelHeavyShield extends ModelBase {
	    public ModelRenderer Plate;
	    public ModelRenderer Handle;
	    public ModelRenderer TopBorder;
	    public ModelRenderer BottomBorder;
	    public ModelRenderer SideBorder0;
	    public ModelRenderer SideBorder1;
	    public ModelRenderer PlateUpper;
	    public ModelRenderer PlateUpperS01;
	    public ModelRenderer PlateUpperS01_1;
	    public ModelRenderer Glass;
	    public ModelRenderer PlateArmor;
	    public ModelRenderer PlateArmor2;
	    public ModelRenderer PlateArmor3;
	    public ModelRenderer ArmorBox;
	    public ModelRenderer ArmorBox2;
	    public ModelRenderer ArmorBox3;
	    public ModelRenderer ArmorBox4;

	    public ModelHeavyShield() {
	        this.textureWidth = 64;
	        this.textureHeight = 32;
	        this.BottomBorder = new ModelRenderer(this, 0, 28);
	        this.BottomBorder.setRotationPoint(-5.0F, 10.0F, -2.5F);
	        this.BottomBorder.addBox(0.0F, 0.0F, 0.0F, 10, 2, 2, 0.0F);
	        this.SideBorder0 = new ModelRenderer(this, 23, 0);
	        this.SideBorder0.setRotationPoint(5.0F, -11.5F, -2.5F);
	        this.SideBorder0.addBox(0.0F, 0.0F, 0.0F, 2, 23, 2, 0.0F);
	        this.PlateUpperS01 = new ModelRenderer(this, 0, 21);
	        this.PlateUpperS01.setRotationPoint(3.0F, -7.0F, -2.0F);
	        this.PlateUpperS01.addBox(0.0F, 0.0F, 0.0F, 2, 3, 1, 0.0F);
	        this.SideBorder1 = new ModelRenderer(this, 23, 0);
	        this.SideBorder1.setRotationPoint(-7.0F, -11.5F, -2.5F);
	        this.SideBorder1.addBox(0.0F, 0.0F, 0.0F, 2, 23, 2, 0.0F);
	        this.PlateUpper = new ModelRenderer(this, 0, 16);
	        this.PlateUpper.setRotationPoint(-5.0F, -10.0F, -2.0F);
	        this.PlateUpper.addBox(0.0F, 0.0F, 0.0F, 10, 3, 1, 0.0F);
	        this.PlateUpperS01_1 = new ModelRenderer(this, 0, 21);
	        this.PlateUpperS01_1.setRotationPoint(-5.0F, -7.0F, -2.0F);
	        this.PlateUpperS01_1.addBox(0.0F, 0.0F, 0.0F, 2, 3, 1, 0.0F);
	        this.PlateArmor = new ModelRenderer(this, 32, 0);
	        this.PlateArmor.setRotationPoint(-5.0F, -2.0F, -2.3F);
	        this.PlateArmor.addBox(0.0F, 0.0F, 0.0F, 10, 2, 1, 0.0F);
	        this.Plate = new ModelRenderer(this, 0, 0);
	        this.Plate.setRotationPoint(-5.0F, -4.0F, -2.0F);
	        this.Plate.addBox(0.0F, 0.0F, 0.0F, 10, 14, 1, 0.0F);
	        this.TopBorder = new ModelRenderer(this, 0, 28);
	        this.TopBorder.setRotationPoint(-5.0F, -12.0F, -2.5F);
	        this.TopBorder.addBox(0.0F, 0.0F, 0.0F, 10, 2, 2, 0.0F);
	        this.Glass = new ModelRenderer(this, 8, 21);
	        this.Glass.setRotationPoint(-3.0F, -7.0F, -2.0F);
	        this.Glass.addBox(0.0F, 0.0F, 0.0F, 6, 3, 1, 0.0F);
	        this.ArmorBox = new ModelRenderer(this, 32, 4);
	        this.ArmorBox.setRotationPoint(4.5F, 3.0F, -3.0F);
	        this.ArmorBox.addBox(0.0F, 0.0F, 0.0F, 3, 6, 3, 0.0F);
	        this.ArmorBox4 = new ModelRenderer(this, 32, 4);
	        this.ArmorBox4.mirror = true;
	        this.ArmorBox4.setRotationPoint(-7.5F, 3.0F, -3.0F);
	        this.ArmorBox4.addBox(0.0F, 0.0F, 0.0F, 3, 6, 3, 0.0F);
	        this.PlateArmor2 = new ModelRenderer(this, 32, 0);
	        this.PlateArmor2.setRotationPoint(-5.0F, 6.0F, -2.3F);
	        this.PlateArmor2.addBox(0.0F, 0.0F, 0.0F, 10, 2, 1, 0.0F);
	        this.ArmorBox3 = new ModelRenderer(this, 32, 4);
	        this.ArmorBox3.mirror = true;
	        this.ArmorBox3.setRotationPoint(-7.5F, -9.0F, -3.0F);
	        this.ArmorBox3.addBox(0.0F, 0.0F, 0.0F, 3, 6, 3, 0.0F);
	        this.Handle = new ModelRenderer(this, 26, 20);
	        this.Handle.setRotationPoint(-1.0F, -3.0F, -1.0F);
	        this.Handle.addBox(0.0F, 0.0F, 0.0F, 2, 6, 6, 0.0F);
	        this.PlateArmor3 = new ModelRenderer(this, 32, 0);
	        this.PlateArmor3.setRotationPoint(-5.0F, 2.0F, -2.3F);
	        this.PlateArmor3.addBox(0.0F, 0.0F, 0.0F, 10, 2, 1, 0.0F);
	        this.ArmorBox2 = new ModelRenderer(this, 32, 4);
	        this.ArmorBox2.setRotationPoint(4.5F, -9.0F, -3.0F);
	        this.ArmorBox2.addBox(0.0F, 0.0F, 0.0F, 3, 6, 3, 0.0F);
	    }

	    @Override
	    public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5) { 
	        this.BottomBorder.render(f5);
	        this.SideBorder0.render(f5);
	        this.PlateUpperS01.render(f5);
	        this.SideBorder1.render(f5);
	        this.PlateUpper.render(f5);
	        this.PlateUpperS01_1.render(f5);
	        this.PlateArmor.render(f5);
	        this.Plate.render(f5);
	        this.TopBorder.render(f5);
	        this.Glass.render(f5);
	        this.ArmorBox.render(f5);
	        this.ArmorBox4.render(f5);
	        this.PlateArmor2.render(f5);
	        this.ArmorBox3.render(f5);
	        this.Handle.render(f5);
	        this.PlateArmor3.render(f5);
	        this.ArmorBox2.render(f5);
	    }

}
