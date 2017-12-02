package techguns.client.render;

import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.AbstractClientPlayer;
import net.minecraft.client.model.ModelBiped;
import net.minecraft.client.renderer.entity.RenderPlayer;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import techguns.items.armors.ICamoChangeable;

public class RenderAdditionalSlotItem {

	protected ModelBiped model;
	protected ModelBiped model_slim=null;
	protected ResourceLocation[] textures;
	protected ResourceLocation[] textures_slim;

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
		this(model,null,texture,null);
	}
	
	public RenderAdditionalSlotItem(ModelBiped model, ModelBiped modelslim, ResourceLocation texture, ResourceLocation texture_slim) {
		this.model = model;
		this.model_slim = modelslim;
		this.textures = new ResourceLocation[] { texture };
		this.textures_slim = new ResourceLocation[] { texture_slim };
	}

	public void render(ItemStack slot, EntityPlayer player, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw, float headPitch,
			float scale, RenderPlayer renderplayer) {
		
		boolean slim=false;
		if(model_slim!=null && player instanceof AbstractClientPlayer) {
			AbstractClientPlayer acp = (AbstractClientPlayer) player;
			if(acp.getSkinType().equals("slim")) {
				slim=true;
			}
		}
		
		ModelBiped m = slim ? model_slim : model;
		
		int tex_index = 0;
		if (slot.getItem() instanceof ICamoChangeable) {
			tex_index = ((ICamoChangeable) slot.getItem()).getCurrentCamoIndex(slot);
		}

		m.setModelAttributes(renderplayer.getMainModel());
		m.setLivingAnimations(player, limbSwing, limbSwingAmount, partialTicks);
		
		Minecraft.getMinecraft().getTextureManager().bindTexture(slim ? textures_slim[tex_index] : textures[tex_index]);

		m.render(player, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, scale);
	}
}
