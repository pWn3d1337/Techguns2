package techguns.client.models.guns;

import net.minecraft.client.model.ModelRenderer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.block.model.ItemCameraTransforms.TransformType;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import techguns.capabilities.TGExtendedPlayer;
import techguns.client.models.ModelMultipart;
import techguns.client.render.TGRenderHelper;
import techguns.items.guns.GenericGun;

public class ModelGuidedMissileLauncher extends ModelMultipart {
	// fields
	public ModelRenderer Barrel01;
	public ModelRenderer Barrel02;
	public ModelRenderer End01;
	public ModelRenderer End02;
	public ModelRenderer End03;
	public ModelRenderer Optics02;
	public ModelRenderer Optics03;
	public ModelRenderer Optics04;
	public ModelRenderer Optics05;
	public ModelRenderer Optics06;
	public ModelRenderer Optics07;
	public ModelRenderer Holo2;
	public ModelRenderer Optics01;
	public ModelRenderer Holo2_1;
	public ModelRenderer Front02;
	public ModelRenderer Front03;
	public ModelRenderer Front01;
	public ModelRenderer Grip01;
	public ModelRenderer Grip02;
	public ModelRenderer Grip03;
	public ModelRenderer Grip04;
	public ModelRenderer Center01;
	public ModelRenderer Center02;
	public ModelRenderer Alpha01;
	public ModelRenderer Alpha02;
	public ModelRenderer Alpha03;
	public ModelRenderer Alpha04;
	public ModelRenderer Extra01;
	public ModelRenderer Extra02;
	public ModelRenderer Extra03;
	public ModelRenderer Extra04;
	public ModelRenderer Extra05;
	public ModelRenderer Extra06;

	public ModelGuidedMissileLauncher() {
		this.textureWidth = 128;
		this.textureHeight = 64;
		this.Optics07 = new ModelRenderer(this, 68, 9);
		this.Optics07.setRotationPoint(23.0F, -2.0F, 4.0F);
		this.Optics07.addBox(0.0F, 0.0F, 0.0F, 1, 3, 1, 0.0F);
		this.End02 = new ModelRenderer(this, 94, 4);
		this.End02.setRotationPoint(-27.0F, -0.5F, -3.5F);
		this.End02.addBox(0.0F, 0.0F, 0.0F, 2, 6, 6, 0.0F);
		this.End03 = new ModelRenderer(this, 107, 0);
		this.End03.setRotationPoint(-26.9F, -1.0F, -2.0F);
		this.End03.addBox(0.0F, 0.0F, 0.0F, 2, 7, 3, 0.0F);
		this.Optics03 = new ModelRenderer(this, 35, 17);
		this.Optics03.setRotationPoint(17.0F, -1.0F, -1.5F);
		this.Optics03.addBox(0.0F, 0.0F, 0.0F, 8, 1, 2, 0.0F);
		this.Alpha01 = new ModelRenderer(this, 0, 57);
		this.Alpha01.setRotationPoint(16.0F, -0.5F, 2.5F);
		this.Alpha01.addBox(0.0F, 0.0F, 0.0F, 10, 6, 0, 0.0F);
		this.Extra01 = new ModelRenderer(this, 45, 57);
		this.Extra01.setRotationPoint(37.5F, -1.0F, -3.5F);
		this.Extra01.addBox(0.0F, 0.0F, 0.0F, 1, 2, 1, 0.0F);
		this.Center02 = new ModelRenderer(this, 0, 38);
		this.Center02.setRotationPoint(26.0F, -0.5F, -3.5F);
		this.Center02.addBox(0.0F, 0.0F, 0.0F, 1, 6, 6, 0.0F);
		this.Front03 = new ModelRenderer(this, 0, 0);
		this.Front03.setRotationPoint(26.9F, -0.5F, -2.0F);
		this.Front03.addBox(0.0F, 0.0F, 0.0F, 12, 6, 3, 0.0F);
		this.Center01 = new ModelRenderer(this, 0, 38);
		this.Center01.setRotationPoint(15.0F, -0.5F, -3.5F);
		this.Center01.addBox(0.0F, 0.0F, 0.0F, 1, 6, 6, 0.0F);
		this.Optics05 = new ModelRenderer(this, 61, 11);
		this.Optics05.setRotationPoint(23.0F, 3.5F, 3.0F);
		this.Optics05.addBox(0.0F, -2.0F, 0.0F, 1, 2, 3, 0.0F);
		this.setRotateAngle(Optics05, 0.5235987755982988F, 0.0F, 0.0F);
		this.Front02 = new ModelRenderer(this, 0, 10);
		this.Front02.setRotationPoint(27.0F, 0.0F, -3.0F);
		this.Front02.addBox(0.0F, 0.0F, 0.0F, 12, 5, 5, 0.0F);
		this.End01 = new ModelRenderer(this, 110, 5);
		this.End01.setRotationPoint(-26.9F, 1.0F, -4.0F);
		this.End01.addBox(0.0F, 0.0F, 0.0F, 2, 3, 7, 0.0F);
		this.Holo2 = new ModelRenderer(this, 78, 0);
		this.Holo2.setRotationPoint(23.5F, -3.0F, 5.0F);
		this.Holo2.addBox(0.0F, 0.0F, 0.0F, 0, 8, 8, 0.0F);
		this.Optics01 = new ModelRenderer(this, 32, 10);
		this.Optics01.setRotationPoint(18.0F, -3.0F, -0.5F);
		this.Optics01.addBox(0.0F, -1.0F, -1.0F, 7, 2, 2, 0.0F);
		this.setRotateAngle(Optics01, 0.7853981633974483F, 0.0F, 0.0F);
		this.Grip04 = new ModelRenderer(this, 60, 44);
		this.Grip04.setRotationPoint(28.0F, 5.5F, -2.0F);
		this.Grip04.addBox(0.0F, 0.0F, 0.0F, 8, 5, 3, 0.0F);
		this.Alpha04 = new ModelRenderer(this, 0, 57);
		this.Alpha04.setRotationPoint(16.0F, -0.5F, -3.5F);
		this.Alpha04.addBox(0.0F, 0.0F, 0.0F, 10, 6, 0, 0.0F);
		this.Extra06 = new ModelRenderer(this, 45, 57);
		this.Extra06.setRotationPoint(27.5F, -1.0F, 1.5F);
		this.Extra06.addBox(0.0F, 0.0F, 0.0F, 1, 2, 1, 0.0F);
		this.Optics04 = new ModelRenderer(this, 51, 10);
		this.Optics04.setRotationPoint(20.0F, 1.5F, 2.0F);
		this.Optics04.addBox(0.0F, 0.0F, 0.0F, 5, 2, 1, 0.0F);
		this.Grip01 = new ModelRenderer(this, 38, 38);
		this.Grip01.setRotationPoint(16.0F, 5.5F, -1.5F);
		this.Grip01.addBox(0.0F, 0.0F, 0.0F, 2, 6, 2, 0.0F);
		this.setRotateAngle(Grip01, 0.0F, 0.0F, 0.3490658503988659F);
		this.Front01 = new ModelRenderer(this, 31, 0);
		this.Front01.setRotationPoint(26.9F, 1.0F, -3.5F);
		this.Front01.addBox(0.0F, 0.0F, 0.0F, 12, 3, 6, 0.0F);
		this.Grip02 = new ModelRenderer(this, 47, 38);
		this.Grip02.setRotationPoint(16.0F, 5.0F, -2.0F);
		this.Grip02.addBox(0.0F, 0.0F, 0.0F, 12, 2, 3, 0.0F);
		this.Extra03 = new ModelRenderer(this, 45, 57);
		this.Extra03.setRotationPoint(27.5F, -1.0F, -3.5F);
		this.Extra03.addBox(0.0F, 0.0F, 0.0F, 1, 2, 1, 0.0F);
		this.Extra05 = new ModelRenderer(this, 45, 61);
		this.Extra05.setRotationPoint(27.5F, -2.0F, 1.5F);
		this.Extra05.addBox(0.0F, 0.0F, 0.0F, 11, 1, 1, 0.0F);
		this.Grip03 = new ModelRenderer(this, 43, 44);
		this.Grip03.setRotationPoint(24.0F, 5.5F, -2.5F);
		this.Grip03.addBox(0.0F, 0.0F, 0.0F, 4, 5, 4, 0.0F);
		this.Alpha03 = new ModelRenderer(this, -6, 51);
		this.Alpha03.setRotationPoint(16.0F, -0.5F, -3.5F);
		this.Alpha03.addBox(0.0F, 0.0F, 0.0F, 10, 0, 6, 0.0F);
		this.Extra02 = new ModelRenderer(this, 45, 61);
		this.Extra02.setRotationPoint(27.5F, -2.0F, -3.5F);
		this.Extra02.addBox(0.0F, 0.0F, 0.0F, 11, 1, 1, 0.0F);
		this.Barrel01 = new ModelRenderer(this, 0, 21);
		this.Barrel01.setRotationPoint(-25.0F, 1.0F, -3.0F);
		this.Barrel01.addBox(0.0F, 0.0F, 0.0F, 51, 3, 5, 0.0F);
		this.Optics02 = new ModelRenderer(this, 35, 14);
		this.Optics02.setRotationPoint(19.0F, -2.0F, -1.5F);
		this.Optics02.addBox(0.0F, 0.0F, 0.0F, 5, 1, 2, 0.0F);
		this.Holo2_1 = new ModelRenderer(this, 78, -8);
		this.Holo2_1.setRotationPoint(23.5F, -3.0F, 5.0F);
		this.Holo2_1.addBox(0.0F, 0.0F, 0.0F, 0, 8, 8, 0.0F);
		this.Alpha02 = new ModelRenderer(this, -6, 51);
		this.Alpha02.setRotationPoint(16.0F, 5.5F, -3.5F);
		this.Alpha02.addBox(0.0F, 0.0F, 0.0F, 10, 0, 6, 0.0F);
		this.Barrel02 = new ModelRenderer(this, 0, 29);
		this.Barrel02.setRotationPoint(-25.0F, 0.0F, -2.0F);
		this.Barrel02.addBox(0.0F, 0.0F, 0.0F, 51, 5, 3, 0.0F);
		this.Optics06 = new ModelRenderer(this, 67, 14);
		this.Optics06.setRotationPoint(23.0F, 1.0F, 5.0F);
		this.Optics06.addBox(0.0F, 0.0F, 0.0F, 1, 1, 3, 0.0F);
		this.Extra04 = new ModelRenderer(this, 45, 57);
		this.Extra04.setRotationPoint(37.5F, -1.0F, 1.5F);
		this.Extra04.addBox(0.0F, 0.0F, 0.0F, 1, 2, 1, 0.0F);
	}

	@Override
	public void render(Entity entityIn, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw,
			float headPitch, float scale, int ammoLeft, float reloadProgress, TransformType transformType, int part,
			float fireProgress) {
		this.Optics07.render(scale);
		this.End02.render(scale);
		this.End03.render(scale);
		this.Optics03.render(scale);
		this.Alpha01.render(scale);
		this.Extra01.render(scale);
		this.Center02.render(scale);
		this.Front03.render(scale);
		this.Center01.render(scale);
		this.Optics05.render(scale);
		this.Front02.render(scale);
		this.End01.render(scale);

		this.Optics01.render(scale);
		this.Grip04.render(scale);
		this.Alpha04.render(scale);
		this.Extra06.render(scale);
		this.Optics04.render(scale);
		this.Grip01.render(scale);
		this.Front01.render(scale);
		this.Grip02.render(scale);
		this.Extra03.render(scale);
		this.Extra05.render(scale);
		this.Grip03.render(scale);
		this.Alpha03.render(scale);
		this.Extra02.render(scale);
		this.Barrel01.render(scale);
		this.Optics02.render(scale);

		this.Alpha02.render(scale);
		this.Barrel02.render(scale);
		this.Optics06.render(scale);
		this.Extra04.render(scale);

		TGRenderHelper.enableFXLighting();
		GlStateManager.pushMatrix();
		GlStateManager.translate(this.Holo2.offsetX, this.Holo2.offsetY, this.Holo2.offsetZ);
		GlStateManager.translate(this.Holo2.rotationPointX * scale, this.Holo2.rotationPointY * scale,
				this.Holo2.rotationPointZ * scale);
		GlStateManager.scale(0.5D, 0.5D, 0.5D);
		GlStateManager.translate(-this.Holo2.offsetX, -this.Holo2.offsetY, -this.Holo2.offsetZ);
		GlStateManager.translate(-this.Holo2.rotationPointX * scale, -this.Holo2.rotationPointY * scale,
				-this.Holo2.rotationPointZ * scale);
		boolean locked = false;
		if (transformType == TransformType.FIRST_PERSON_LEFT_HAND || transformType == TransformType.FIRST_PERSON_RIGHT_HAND ) {
			if (entityIn != null && entityIn instanceof EntityPlayer) {
				EntityPlayer player = (EntityPlayer) entityIn;
				TGExtendedPlayer tge = TGExtendedPlayer.get(player);
				if (tge != null && player.getActiveItemStack() != null && player.getActiveItemStack().getItem() instanceof GenericGun) {
					GenericGun gun = (GenericGun)player.getActiveItemStack().getItem();
					if (tge.lockOnTicks >= gun.getLockOnTicks()) {
						locked = true;
					}
				}
			}
		}
		if (locked) {
			this.Holo2_1.render(scale);
		}else {
			this.Holo2.render(scale);
		}
		GlStateManager.popMatrix();
		TGRenderHelper.disableFXLighting();

	}

	/**
	 * This is a helper function from Tabula to set the rotation of model parts
	 */
	public void setRotateAngle(ModelRenderer modelRenderer, float x, float y, float z) {
		modelRenderer.rotateAngleX = x;
		modelRenderer.rotateAngleY = y;
		modelRenderer.rotateAngleZ = z;
	}

}
