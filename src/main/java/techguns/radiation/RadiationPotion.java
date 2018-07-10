package techguns.radiation;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import techguns.TGRadiationSystem;
import techguns.api.radiation.TGRadiation;
import techguns.gui.player.TGPlayerInventory;
import techguns.gui.player.TGPlayerInventoryGui;

public class RadiationPotion extends Potion {

	public RadiationPotion() {
		super(true, 0xcff8ff00);
	}

	@Override
	public void renderInventoryEffect(int x, int y, PotionEffect effect, Minecraft mc) {
		super.renderInventoryEffect(x, y, effect, mc);

		if(mc.currentScreen!=null) {
			mc.getTextureManager().bindTexture(TGPlayerInventoryGui.texture);
			mc.currentScreen.drawTexturedModalRect(x+8, y+8, 0, 168, 16, 16);
			mc.getTextureManager().bindTexture(GuiContainer.INVENTORY_BACKGROUND);
		}
	}

	@Override
	public void renderHUDEffect(int x, int y, PotionEffect effect, Minecraft mc, float alpha) {
		super.renderHUDEffect(x, y, effect, mc, alpha);
		
		mc.getTextureManager().bindTexture(TGPlayerInventoryGui.texture);
		
		mc.ingameGUI.drawTexturedModalRect(x+4, y+4, 0, 168, 16, 16);
		mc.getTextureManager().bindTexture(GuiContainer.INVENTORY_BACKGROUND);
	}

	@Override
	public List<ItemStack> getCurativeItems() {
		//Rad can't be cured by milk buckets, return new list
		ArrayList<ItemStack> ret = new ArrayList<ItemStack>();
		return ret;
	}

	@Override
	public void performEffect(EntityLivingBase elb, int amplifier) {
		
		int res = (int) elb.getEntityAttribute(TGRadiation.RADIATION_RESISTANCE).getAttributeValue();
		
		System.out.println("Radiate:"+ (amplifier+1-res));
		
	}

	@Override
	public boolean isReady(int duration, int amplifier) {
		return duration % 20 == 0;
	}

	
	
}
