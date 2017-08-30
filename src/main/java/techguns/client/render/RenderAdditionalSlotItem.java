package techguns.client.render;

import net.minecraft.client.Minecraft;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelBiped;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.entity.RenderPlayer;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import techguns.items.armors.ICamoChangeable;

public class RenderAdditionalSlotItem {

	protected ModelBiped model;
	protected ResourceLocation[] textures;

	public RenderAdditionalSlotItem(ModelBiped model, ResourceLocation... textures) {
		this.model = model;
		this.textures = textures;
	}
	
	public RenderAdditionalSlotItem(ModelBiped model, String modid, String texture_name, int count) {
		this.model = model;
		this.textures = new ResourceLocation[count];
		for (int i=0;i<count;i++) {
			textures[i]=new ResourceLocation(modid, texture_name+(i==0?"":("_"+i))+".png");
		}
	}

	public RenderAdditionalSlotItem(ModelBiped model, ResourceLocation texture) {
		this.model = model;
		this.textures = new ResourceLocation[] { texture };
	}

	public void render(ItemStack slot, EntityPlayer player, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw, float headPitch,
			float scale, RenderPlayer renderplayer) {

		int tex_index = 0;
		if (slot.getItem() instanceof ICamoChangeable) {
			tex_index = ((ICamoChangeable) slot.getItem()).getCurrentCamoIndex(slot);
		}

		model.setModelAttributes(renderplayer.getMainModel());
		model.setLivingAnimations(player, limbSwing, limbSwingAmount, partialTicks);
		
		Minecraft.getMinecraft().getTextureManager().bindTexture(textures[tex_index]);

		model.render(player, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, scale);
	}
}
