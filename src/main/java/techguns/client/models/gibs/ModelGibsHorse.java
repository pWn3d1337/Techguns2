package techguns.client.models.gibs;

import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;

public class ModelGibsHorse extends ModelGibs {

    private final ModelRenderer head;
    private final ModelRenderer upperMouth;
    private final ModelRenderer neck;
    private final ModelRenderer body;
    private final ModelRenderer tailBase;
    private final ModelRenderer tailMiddle;
    private final ModelRenderer backLeftLeg;

    private final ModelRenderer backRightLeg;

    private final ModelRenderer frontLeftLeg;

    private final ModelRenderer frontRightLeg;


    protected ModelRenderer[] gibs;

    public ModelGibsHorse()
    {
        this.textureWidth = 128;
        this.textureHeight = 128;
        this.body = new ModelRenderer(this, 0, 34);
        this.body.addBox(-5.0F, -8.0F, -19.0F, 10, 10, 24);
        //this.body.setRotationPoint(0.0F, 11.0F, 9.0F);
        this.tailBase = new ModelRenderer(this, 44, 0);
        this.tailBase.addBox(-1.0F, -1.0F, 0.0F, 2, 2, 3);
        //this.tailBase.setRotationPoint(0.0F, 3.0F, 14.0F);
        //this.tailBase.rotateAngleX = -1.134464F;
        this.tailMiddle = new ModelRenderer(this, 38, 7);
        this.tailMiddle.addBox(-1.5F, -2.0F, 3.0F, 3, 4, 7);
        //this.tailMiddle.setRotationPoint(0.0F, 3.0F, 14.0F);
        //this.tailMiddle.rotateAngleX = -1.134464F;

        this.backLeftLeg = new ModelRenderer(this, 78, 29);
        this.backLeftLeg.addBox(-2.5F, -2.0F, -2.5F, 4, 9, 5);
        //this.backLeftLeg.setRotationPoint(4.0F, 9.0F, 11.0F);

        this.backRightLeg = new ModelRenderer(this, 96, 29);
        this.backRightLeg.addBox(-1.5F, -2.0F, -2.5F, 4, 9, 5);
        //this.backRightLeg.setRotationPoint(-4.0F, 9.0F, 11.0F);

        this.frontLeftLeg = new ModelRenderer(this, 44, 29);
        this.frontLeftLeg.addBox(-1.9F, -1.0F, -2.1F, 3, 8, 4);
        //this.frontLeftLeg.setRotationPoint(4.0F, 9.0F, -8.0F);

        this.frontRightLeg = new ModelRenderer(this, 60, 29);
        this.frontRightLeg.addBox(-1.1F, -1.0F, -2.1F, 3, 8, 4);
        //this.frontRightLeg.setRotationPoint(-4.0F, 9.0F, -8.0F);

        this.head = new ModelRenderer(this, 0, 0);
        this.head.addBox(-2.5F, -10.0F, -1.5F, 5, 5, 7);
        //this.head.setRotationPoint(0.0F, 4.0F, -10.0F);
        //this.head.rotateAngleX = 0.5235988F;
        this.upperMouth = new ModelRenderer(this, 24, 18);
        this.upperMouth.addBox(-2.0F, -10.0F, -7.0F, 4, 3, 6);
        //this.upperMouth.setRotationPoint(0.0F, 3.95F, -10.0F);
        //this.upperMouth.rotateAngleX = 0.5235988F;
 
        //this.head.addChild(this.upperMouth);
       
        this.neck = new ModelRenderer(this, 0, 12);
        this.neck.addBox(-2.05F, -9.8F, -2.0F, 4, 14, 8);
        //this.neck.setRotationPoint(0.0F, 4.0F, -10.0F);
        //this.neck.rotateAngleX = 0.5235988F;
	
        this.gibs = new ModelRenderer[] {
        		this.body,
        		this.tailBase,
        		this.tailMiddle,
        		this.backLeftLeg,
        		this.backRightLeg,
        		this.frontLeftLeg,
        		this.frontRightLeg,
        		this.head,
        		this.upperMouth,
        		this.neck
        };
    }
	
	@Override
	public void render(Entity entityIn, float scale, int part) {
		this.gibs[part].render(scale);
	}

	@Override
	public int getNumGibs() {
		return this.gibs.length;
	}

}
