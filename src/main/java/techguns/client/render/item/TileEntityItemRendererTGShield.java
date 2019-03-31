package techguns.client.render.item;

import net.minecraft.client.Minecraft;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.renderer.BannerTextures;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.tileentity.TileEntityItemStackRenderer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import techguns.items.armors.GenericShield;

public class TileEntityItemRendererTGShield extends TileEntityItemStackRenderer {

	protected final ModelBase model;
	protected final ResourceLocation[] textures;
	
	public TileEntityItemRendererTGShield(ModelBase model, ResourceLocation... textures) {
		super();
		this.model = model;
		this.textures = textures;
	}

	@Override
	public void renderByItem(ItemStack stack, float partialTicks) {
		if(stack.getItem() instanceof GenericShield) {
			GenericShield s = (GenericShield) stack.getItem();
			int index = s.getCurrentCamoIndex(stack);
			
			if(index>=0 && index <textures.length) {
		        Minecraft.getMinecraft().getTextureManager().bindTexture(textures[index]);
		
		        GlStateManager.pushMatrix();
		        GlStateManager.scale(1.0F, -1.0F, -1.0F);
		        this.model.render(null,0,0,0,0,0,0.0625F);
		        GlStateManager.popMatrix();
			}
		}
	}
	
}
