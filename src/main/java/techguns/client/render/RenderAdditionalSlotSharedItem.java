package techguns.client.render;

import java.util.HashMap;

import net.minecraft.client.model.ModelBiped;
import net.minecraft.client.renderer.entity.RenderPlayer;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;

public class RenderAdditionalSlotSharedItem extends RenderAdditionalSlotItem {

	private HashMap<Integer,RenderAdditionalSlotItem> renderMap = new HashMap<>();
	
	public RenderAdditionalSlotSharedItem() {
		super((ModelBiped)null,(ResourceLocation)null);
	}
	
	@Override
	public void render(ItemStack slot, EntityPlayer player, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw, float headPitch,
			float scale, RenderPlayer renderplayer) {

		Integer damageValue = slot.getItemDamage();
		
		RenderAdditionalSlotItem render = renderMap.get(damageValue);
		if(render!=null) {
			render.render(slot, player, limbSwing, limbSwingAmount, partialTicks, ageInTicks, netHeadYaw, headPitch, scale, renderplayer);
		}
	}

	public void addRenderForSharedItem(Integer dmgVal, RenderAdditionalSlotItem render) {
		this.renderMap.put(dmgVal, render);
	}
}
