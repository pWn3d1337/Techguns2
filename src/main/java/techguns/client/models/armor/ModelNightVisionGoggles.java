package techguns.client.models.armor;

import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.MobEffects;
import techguns.capabilities.TGExtendedPlayer;

public class ModelNightVisionGoggles extends ModelAdditionalSlotBase {
	// fields
	ModelRenderer Head;
	ModelRenderer Right;
	ModelRenderer Left;
	ModelRenderer Mount;
	ModelRenderer Mid;

	ModelRenderer Right_off;
	ModelRenderer Left_off;
	ModelRenderer Mount_off;
	ModelRenderer Mid_off;

	public ModelNightVisionGoggles() {
		textureWidth = 32;
		textureHeight = 32;

	    this.bipedHead = new ModelRenderer(this, textureWidth, textureHeight);
     //   this.bipedHead.addBox(-4.0F, -8.0F, -4.0F, 8, 8, 8, 0.0f);
        this.bipedHead.setRotationPoint(0.0F, 0.0F, 0.0F);
		
		Head = new ModelRenderer(this, 0, 0);
		Head.addBox(-4F, -8F, -4F, 8, 8, 8);
		Head.setRotationPoint(0F, 0F, 0F);
		Head.setTextureSize(32, 32);
		Head.mirror = true;
		setRotation(Head, 0F, 0F, 0F);
		this.bipedHead.addChild(Head);

		Right = new ModelRenderer(this, 0, 17);
		Right.addBox(-1F, -1F, 0F, 2, 2, 4);
		Right.setRotationPoint(-1.7F, -4F, -8F);
		Right.setTextureSize(32, 32);
		Right.mirror = true;
		setRotation(Right, 0F, 0F, 0.7853982F);
		this.bipedHead.addChild(Right);

		Left = new ModelRenderer(this, 0, 17);
		Left.addBox(-1F, -1F, 0F, 2, 2, 4);
		Left.setRotationPoint(1.7F, -4F, -8F);
		Left.setTextureSize(32, 32);
		Left.mirror = true;
		setRotation(Left, 0F, 0F, 0.7853982F);
		this.bipedHead.addChild(Left);

		Mount = new ModelRenderer(this, 13, 21);
		Mount.addBox(-1F, 0F, 0F, 2, 1, 4);
		Mount.setRotationPoint(0F, -5F, -6.5F);
		Mount.setTextureSize(32, 32);
		Mount.mirror = true;
		setRotation(Mount, 0.7853982F, 0F, 0F);
		this.bipedHead.addChild(Mount);

		Mid = new ModelRenderer(this, 13, 17);
		Mid.addBox(0F, 0F, 0F, 3, 1, 2);
		Mid.setRotationPoint(-1.5F, -5F, -7F);
		Mid.setTextureSize(32, 32);
		Mid.mirror = true;
		setRotation(Mid, 0F, 0F, 0F);
		this.bipedHead.addChild(Mid);

		/**
		 * off parts
		 */
		Right_off = new ModelRenderer(this, 0, 17);
		Right_off.addBox(-1F, -1F, -2F, 2, 2, 4);
		Right_off.setRotationPoint(-1.7F, -9F, -5F);
		Right_off.setTextureSize(32, 32);
		Right_off.mirror = true;
		setRotation(Right_off, -2.356194F, -0.5410521F, 0.6108652F);
		this.bipedHead.addChild(Right_off);

		Left_off = new ModelRenderer(this, 0, 17);
		Left_off.addBox(-1F, -1F, -2F, 2, 2, 4);
		Left_off.setRotationPoint(1.7F, -9F, -5F);
		Left_off.setTextureSize(32, 32);
		Left_off.mirror = true;
		setRotation(Left_off, -2.356194F, -0.5410521F, 0.6108652F);
		this.bipedHead.addChild(Left_off);

		Mount_off = new ModelRenderer(this, 13, 21);
		Mount_off.addBox(-1F, 0F, -4F, 2, 1, 4);
		Mount_off.setRotationPoint(0F, -6F, -3.5F);
		Mount_off.setTextureSize(32, 32);
		Mount_off.mirror = true;
		setRotation(Mount_off, -1.301251F, 0F, 0F);
		this.bipedHead.addChild(Mount_off);

		Mid_off = new ModelRenderer(this, 13, 17);
		Mid_off.addBox(-1F, 0F, -1F, 3, 1, 2);
		Mid_off.setRotationPoint(-0.5F, -10F, -5.5F);
		Mid_off.setTextureSize(32, 32);
		Mid_off.mirror = true;
		setRotation(Mid_off, 0.6108652F, 0F, 0F);
		this.bipedHead.addChild(Mid_off);
	}

	@Override
	public void render(float scale, Entity entityIn) {

		boolean on = false;
		if (entityIn instanceof EntityPlayer) {
			EntityPlayer ply = (EntityPlayer) entityIn;
			if (ply.getActivePotionEffect(MobEffects.NIGHT_VISION) != null) {
				on = true;
			}

		}

		this.Mid_off.isHidden = on;
		this.Mount_off.isHidden = on;
		this.Left_off.isHidden = on;
		this.Right_off.isHidden = on;

		this.Mid.isHidden = !on;
		this.Mount.isHidden = !on;
		this.Left.isHidden = !on;
		this.Right.isHidden = !on;

		this.bipedHead.render(scale);
	}

	@Override
	public void copyRotateAngles() {
	}

}
