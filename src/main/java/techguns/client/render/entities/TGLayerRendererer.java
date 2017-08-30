package techguns.client.render.entities;

import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.entity.RenderPlayer;
import net.minecraft.client.renderer.entity.layers.LayerRenderer;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import techguns.capabilities.TGExtendedPlayer;
import techguns.client.render.AdditionalSlotRenderRegistry;
import techguns.client.render.RenderAdditionalSlotItem;
import techguns.gui.player.TGPlayerInventory;

public class TGLayerRendererer implements LayerRenderer<EntityPlayer>{
	private boolean slimModel;
	RenderPlayer renderplayer;
	public TGLayerRendererer(RenderPlayer renderplayer, boolean slimModel) {
		super();
		this.renderplayer=renderplayer;
		this.slimModel = slimModel;
	}

	@Override
	public void doRenderLayer(EntityPlayer player, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw, float headPitch,
			float scale) {
		TGExtendedPlayer props = TGExtendedPlayer.get(player);
		
		this.renderSlot(props.tg_inventory.inventory.get(TGPlayerInventory.SLOT_FACE), player, limbSwing, limbSwingAmount, partialTicks, ageInTicks, netHeadYaw, headPitch, scale);
		this.renderSlot(props.tg_inventory.inventory.get(TGPlayerInventory.SLOT_BACK), player, limbSwing, limbSwingAmount, partialTicks, ageInTicks, netHeadYaw, headPitch, scale);
		this.renderSlot(props.tg_inventory.inventory.get(TGPlayerInventory.SLOT_HAND), player, limbSwing, limbSwingAmount, partialTicks, ageInTicks, netHeadYaw, headPitch, scale);

	}

	@Override
	public boolean shouldCombineTextures() {
		return false;
	}

	protected void renderSlot(ItemStack slot, EntityPlayer player, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw, float headPitch,
			float scale) {
		if(!slot.isEmpty()){
			RenderAdditionalSlotItem render = AdditionalSlotRenderRegistry.getRenderForItem(slot);
			if (render!=null) {
				render.render(slot, player, limbSwing, limbSwingAmount, partialTicks, ageInTicks, netHeadYaw, headPitch, scale, renderplayer);
			}
		}
		
	}
}
