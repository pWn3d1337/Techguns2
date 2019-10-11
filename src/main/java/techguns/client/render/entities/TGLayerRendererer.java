package techguns.client.render.entities;

import net.minecraft.client.renderer.entity.RenderPlayer;
import net.minecraft.client.renderer.entity.layers.LayerRenderer;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import techguns.capabilities.TGExtendedPlayer;
import techguns.client.render.AdditionalSlotRenderRegistry;
import techguns.client.render.RenderAdditionalSlotItem;
import techguns.gui.player.TGPlayerInventory;
import techguns.items.armors.GenericArmor;

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
		
		if(isSlotVisible(TGPlayerInventory.SLOT_FACE,player)) {
			this.renderSlot(props.tg_inventory.getStackInSlot(TGPlayerInventory.SLOT_FACE), player, limbSwing, limbSwingAmount, partialTicks, ageInTicks, netHeadYaw, headPitch, scale);
		}
		if(isSlotVisible(TGPlayerInventory.SLOT_BACK,player)) {
			this.renderSlot(props.tg_inventory.getStackInSlot(TGPlayerInventory.SLOT_BACK), player, limbSwing, limbSwingAmount, partialTicks, ageInTicks, netHeadYaw, headPitch, scale);
		}
		if(isSlotVisible(TGPlayerInventory.SLOT_HAND,player)) {
			this.renderSlot(props.tg_inventory.getStackInSlot(TGPlayerInventory.SLOT_HAND), player, limbSwing, limbSwingAmount, partialTicks, ageInTicks, netHeadYaw, headPitch, scale);
		}
	}

	protected boolean isSlotVisible(int slot, EntityPlayer ply) {
		switch(slot) {
		case TGPlayerInventory.SLOT_FACE:
			ItemStack head = ply.inventory.armorInventory.get(3);
			if(!head.isEmpty() && head.getItem() instanceof GenericArmor) {
				GenericArmor a = (GenericArmor) head.getItem();
				return !a.isHideFaceslot();
			}
			break;
		case TGPlayerInventory.SLOT_BACK:
			ItemStack body = ply.inventory.armorInventory.get(2);
			if(!body.isEmpty() && body.getItem() instanceof GenericArmor) {
				GenericArmor a = (GenericArmor) body.getItem();
				return !a.isHideBackslot();
			}
			break;
		case TGPlayerInventory.SLOT_HAND:
			ItemStack b = ply.inventory.armorInventory.get(2);
			if(!b.isEmpty() && b.getItem() instanceof GenericArmor) {
				GenericArmor a = (GenericArmor) b.getItem();
				return !a.isHideGloveslot();
			}
			break;
			
		}
		return true;
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
