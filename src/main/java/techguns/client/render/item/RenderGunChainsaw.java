package techguns.client.render.item;

import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.item.ItemStack;
import techguns.client.models.ModelMultipart;
import techguns.items.guns.Chainsaw;
import techguns.items.guns.GenericGun;

public class RenderGunChainsaw extends RenderGunBase90 {

	public RenderGunChainsaw(ModelMultipart model, int parts) {
		super(model, parts);
	}

	@Override
	protected void setGLColorForPart(GenericGun gun, int part, ItemStack stack) {
		if(part==1) {
			Chainsaw c = (Chainsaw) gun;
			int i =c.getMiningHeadLevel(stack);
			//System.out.println("LEVEL:"+i);
			if(i==1) {
				GlStateManager.color(0.9f, 0.55f, 1.0f, 1.0f);
			} else if (i==2) {
				GlStateManager.color(0.4f, 0.4f, 0.4f, 1.0f);
			}
		}
	}

	
}
