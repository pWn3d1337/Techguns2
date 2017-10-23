package techguns.client.render.item;

import net.minecraft.client.Minecraft;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import techguns.client.models.ModelMultipart;
import techguns.items.guns.GenericGun;
import techguns.items.guns.GenericGunMeleeCharge;

public class RenderMiningToolMultiTexture extends RenderGunBase90 {

	ResourceLocation[] textures;
	
	public RenderMiningToolMultiTexture(ModelMultipart model, int parts, ResourceLocation[] textures) {
		super(model, parts);
		this.textures = textures;
	}

	@Override
	public void bindTextureForPart(GenericGun gun, int part, ItemStack stack) {
		if (part==1 && gun instanceof GenericGunMeleeCharge) {
			GenericGunMeleeCharge g = (GenericGunMeleeCharge) gun;
			int textureIndex = g.getMiningHeadLevel(stack);
			if(textureIndex < textures.length) {
				Minecraft.getMinecraft().getTextureManager().bindTexture(textures[textureIndex]);
			} else {
				super.bindTextureForPart(gun, part, stack);
			}
		} else {
			super.bindTextureForPart(gun, part, stack);
		}
	}

}
