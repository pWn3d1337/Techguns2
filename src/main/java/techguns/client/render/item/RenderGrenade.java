package techguns.client.render.item;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.block.model.ItemCameraTransforms.TransformType;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import techguns.client.models.ModelMultipart;
import techguns.items.guns.GenericGrenade;
import techguns.items.guns.GenericGunCharge;
import techguns.util.MathUtil;

public class RenderGrenade extends RenderItemBase {

	protected float baseRotY=180.0f;
	
	public RenderGrenade(ModelMultipart model, ResourceLocation texture) {
		super(model, texture);
	}
	
	public RenderGrenade(ModelMultipart model, ResourceLocation texture, float baseRotationY) {
		super(model, texture);
		this.baseRotY=baseRotationY;
	}
	
	@Override
	protected void setBaseRotation(TransformType transform) {
		GlStateManager.rotate(-180.0f, 1.0f, 0, 0);
		GlStateManager.rotate(baseRotY, 0f, 1.0f, 0);
	}

	@Override
	public void renderItem(TransformType transform, ItemStack stack, EntityLivingBase elb, boolean leftHanded) {

		float chargeProgress=0;
		
			GlStateManager.pushMatrix();

			Minecraft.getMinecraft().getTextureManager().bindTexture(texture);

			this.applyTranslation(transform);

			GenericGrenade grenade = (GenericGrenade) stack.getItem();
			
			if (TransformType.FIRST_PERSON_LEFT_HAND == transform || TransformType.FIRST_PERSON_RIGHT_HAND == transform) {

				if (!elb.getActiveItemStack().isEmpty() && elb.getActiveItemStack()==stack) {
					
					//float useAnimProgress;
					int dur = stack.getItem().getMaxItemUseDuration(stack)-elb.getItemInUseCount();

					chargeProgress = dur / ((GenericGrenade)stack.getItem()).fullChargeTime;
					
					chargeProgress = MathUtil.clamp(chargeProgress, 0f, 1f);
				
					GlStateManager.rotate(25.0f*chargeProgress, 1f, 0f, 0f);
				}
				
			} else if (TransformType.THIRD_PERSON_LEFT_HAND == transform || TransformType.THIRD_PERSON_RIGHT_HAND == transform) {

			} else if (TransformType.GUI == transform) {
				GlStateManager.rotate(40.0f, 0, 1f, 0);
				GlStateManager.rotate(20.0f, 1f, 0, 0);

			} else if (TransformType.GROUND == transform) {

			} else if (TransformType.FIXED == transform) {
				GlStateManager.rotate(-90.0f, 0, 1.0f, 0);
			}

			this.setBaseScale(elb,transform);
			this.setBaseRotation(transform);
			this.applyBaseTranslation();
			
			for (int i = 0; i < parts; i++) {
				model.render(elb, 0, 0, 0, 0, 0, SCALE, 0, 0, transform, i, chargeProgress);
			}

			GlStateManager.popMatrix();

	}

}
