package techguns.client.models.armor;

import java.util.ArrayList;

import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import techguns.capabilities.TGExtendedPlayer;

public class ModelGlider extends ModelAdditionalSlotBase {

	ModelRenderer BackBack;
	ModelRenderer MidR01;
	ModelRenderer TopR01;
	ModelRenderer TopR02;
	ModelRenderer MidR02;
	ModelRenderer TopL01;
	ModelRenderer TopL02;
	ModelRenderer InnerR02;
	ModelRenderer MidL02;
	ModelRenderer MidL01;
	ModelRenderer InnerR01;
	ModelRenderer InnerL02;
	ModelRenderer InnerL01;

	ArrayList<ModelRenderer> parts = new ArrayList<>();
	
	public ModelGlider() {

		// fields
		textureWidth = 32;
		textureHeight = 32;

		BackBack = new ModelRenderer(this, 0, 0);
		BackBack.addBox(-3F, 0.5F, 2F, 7, 10, 4);
		BackBack.setRotationPoint(-0.5F, 0F, 0F);
		BackBack.setTextureSize(32, 32);
		BackBack.mirror = true;
		setRotation(BackBack, 0F, 0F, 0F);
//		this.bipedBody.addChild(BackBack);

		MidR01 = new ModelRenderer(this, 23, 0);
		MidR01.addBox(0F, -1.0F, 1.0F, 1, 7, 2);
		MidR01.setRotationPoint(-9.5F, 3F, 3F);
		MidR01.setTextureSize(32, 32);
		MidR01.mirror = true;
		setRotation(MidR01, 0F, 0F, 0F);
		// this.BackBack.addChild(MidR01);
//		this.bipedBody.addChild(MidR01);

		TopR01 = new ModelRenderer(this, 13, 15);
		TopR01.addBox(0F, -1.0F, 1.0F, 6, 1, 2);
		TopR01.setRotationPoint(-9.5F, 2F, 3F);
		TopR01.setTextureSize(32, 32);
		TopR01.mirror = true;
		setRotation(TopR01, 0F, 0F, 0F);
//		this.bipedBody.addChild(TopR01);

		TopR02 = new ModelRenderer(this, 13, 15);
		TopR02.addBox(0F, -1.0F, 1.0F, 6, 1, 2);
		TopR02.setRotationPoint(-15.5F, 3F, 3F);
		TopR02.setTextureSize(32, 32);
		TopR02.mirror = true;
		setRotation(TopR02, 0F, 0F, 0F);
//		this.bipedBody.addChild(TopR02);

		MidR02 = new ModelRenderer(this, 21, 19);
		MidR02.addBox(0F, -1.0F, 1.0F, 1, 6, 2);
		MidR02.setRotationPoint(-16.5F, 4F, 3F);
		MidR02.setTextureSize(32, 32);
		MidR02.mirror = true;
		setRotation(MidR02, 0F, 0F, 0F);
//		this.bipedBody.addChild(MidR02);

		TopL01 = new ModelRenderer(this, 13, 15);
		TopL01.addBox(0F, -1.0F, 1.0F, 6, 1, 2);
		TopL01.setRotationPoint(3.5F, 2F, 3F);
		TopL01.setTextureSize(32, 32);
		TopL01.mirror = true;
		setRotation(TopL01, 0F, 0F, 0F);
	//	this.bipedBody.addChild(TopL01);

		TopL02 = new ModelRenderer(this, 13, 15);
		TopL02.addBox(0F, -1.0F, 1.0F, 6, 1, 2);
		TopL02.setRotationPoint(9.5F, 3F, 3F);
		TopL02.setTextureSize(32, 32);
		TopL02.mirror = true;
		setRotation(TopL02, 0F, 0F, 0F);
//		this.bipedBody.addChild(TopL02);

		InnerR02 = new ModelRenderer(this, 0, 24);
		InnerR02.addBox(0F, -1.0F, 1.0F, 6, 6, 1);
		InnerR02.setRotationPoint(-15.5F, 4F, 3.5F);
		InnerR02.setTextureSize(32, 32);
		InnerR02.mirror = true;
		setRotation(InnerR02, 0F, 0F, 0F);
	//	this.bipedBody.addChild(InnerR02);

		MidL02 = new ModelRenderer(this, 21, 19);
		MidL02.addBox(0F, -1.0F, 1.0F, 1, 6, 2);
		MidL02.setRotationPoint(15.5F, 4F, 3F);
		MidL02.setTextureSize(32, 32);
		MidL02.mirror = true;
		setRotation(MidL02, 0F, 0F, 0F);
	//	this.bipedBody.addChild(MidL02);

		MidL01 = new ModelRenderer(this, 23, 0);
		MidL01.addBox(0F, -1.0F, 1.0F, 1, 7, 2);
		MidL01.setRotationPoint(8.5F, 3F, 3F);
		MidL01.setTextureSize(32, 32);
		MidL01.mirror = true;
		setRotation(MidL01, 0F, 0F, 0F);
	//	this.bipedBody.addChild(MidL01);

		InnerR01 = new ModelRenderer(this, 0, 15);
		InnerR01.addBox(0F, -1.0F, 1.0F, 5, 7, 1);
		InnerR01.setRotationPoint(-8.5F, 3F, 3.5F);
		InnerR01.setTextureSize(32, 32);
		InnerR01.mirror = true;
		setRotation(InnerR01, 0F, 0F, 0F);
	//	this.bipedBody.addChild(InnerR01);

		InnerL02 = new ModelRenderer(this, 0, 24);
		InnerL02.addBox(0F, -1.0F, 1.0F, 6, 6, 1);
		InnerL02.setRotationPoint(9.5F, 4F, 3.5F);
		InnerL02.setTextureSize(32, 32);
		InnerL02.mirror = true;
		setRotation(InnerL02, 0F, 0F, 0F);
	//	this.bipedBody.addChild(InnerL02);

		InnerL01 = new ModelRenderer(this, 0, 15);
		InnerL01.addBox(0F, -1.0F, 1.0F, 5, 7, 1);
		InnerL01.setRotationPoint(3.5F, 3F, 3.5F);
		InnerL01.setTextureSize(32, 32);
		InnerL01.mirror = true;
		setRotation(InnerL01, 0F, 0F, 0F);
//		this.bipedBody.addChild(InnerL01);

		parts.add(BackBack);
		parts.add(MidR01);
		parts.add(TopR01);
		parts.add(TopR02);
		parts.add(MidR02);
		parts.add(TopL01);
		parts.add(TopL02);
		parts.add(MidL02);
		parts.add(MidL01);
		parts.add(InnerR01);
		parts.add(InnerR02);
		parts.add(InnerL01);
		parts.add(InnerL02);
	}

	@Override
	public void render(float scale, Entity entityIn) {
		boolean isGliding = false;
		if (entityIn instanceof EntityPlayer) {
			TGExtendedPlayer props = TGExtendedPlayer.get((EntityPlayer) entityIn);
			isGliding = props.isGliding;
		}
		BackBack.render(scale);
		if (isGliding) {
			MidR01.render(scale);
			TopR01.render(scale);
			TopR02.render(scale);
			MidR02.render(scale);
			TopL01.render(scale);
			TopL02.render(scale);
			InnerR02.render(scale);
			MidL02.render(scale);
			MidL01.render(scale);
			InnerR01.render(scale);
			InnerL02.render(scale);
			InnerL01.render(scale);
		}
	}

	@Override
	public void copyRotateAngles() {
		parts.forEach(p -> {
			p.rotateAngleX=this.bipedBody.rotateAngleX;
		});
	}
	
	
}