package techguns.client.render.item;

import java.util.HashMap;

import net.minecraft.client.Minecraft;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import techguns.Techguns;
import techguns.client.models.ModelMultipart;
import techguns.items.guns.GenericGun;
import techguns.items.guns.ammo.AmmoTypes;

public class RenderRocketLauncher extends RenderGunBase90 {

	protected static final HashMap<String,ResourceLocation> textures = new HashMap<>();
	static {
		textures.put(AmmoTypes.TYPE_DEFAULT, new ResourceLocation(Techguns.MODID,"textures/guns/rocket.png"));
		textures.put(AmmoTypes.TYPE_NUKE, new ResourceLocation(Techguns.MODID,"textures/guns/rocket_nuke.png"));
		textures.put(AmmoTypes.TYPE_HV, new ResourceLocation(Techguns.MODID,"textures/guns/rocket_hv.png"));
	}
	
	public RenderRocketLauncher(ModelMultipart model, int parts) {
		super(model, parts);
	}

	@Override
	public void bindTextureForPart(GenericGun gun, int part, ItemStack stack) {
		if(part==0) {
			super.bindTextureForPart(gun, part, stack);
		} else if (part==1) {
			String variant = gun.getCurrentAmmoVariantKey(stack);
			ResourceLocation tex = textures.get(variant);
			if(tex!=null) {
				Minecraft.getMinecraft().getTextureManager().bindTexture(tex);
			}
		}
	}

	
	
}
