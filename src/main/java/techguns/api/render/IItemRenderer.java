package techguns.api.render;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import net.minecraft.client.renderer.block.model.ItemCameraTransforms;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;

/**
 * <3 <3 <3  1.7.10  <3 <3 <3
 */
public interface IItemRenderer {

	public void renderItem(@Nonnull ItemCameraTransforms.TransformType transform, @Nonnull ItemStack stack, @Nullable EntityLivingBase elb, boolean leftHanded);
	
}
