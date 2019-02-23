package techguns.events;

import com.mojang.realmsclient.gui.ChatFormatting;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.gui.inventory.GuiInventory;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.settings.GameSettings;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.event.GuiScreenEvent;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.client.event.RenderGameOverlayEvent.ElementType;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import techguns.TGItems;
import techguns.Techguns;
import techguns.api.guns.GunManager;
import techguns.capabilities.TGExtendedPlayer;
import techguns.gui.player.TGGuiTabButton;
import techguns.gui.player.TGPlayerInventory;
import techguns.gui.player.TGPlayerInventoryGui;
import techguns.items.additionalslots.ItemTGSpecialSlotAmmo;
import techguns.items.armors.PoweredArmor;
import techguns.items.guns.GenericGun;
import techguns.util.InventoryUtil;

public class TGGuiEvents extends Gui{

	public static ResourceLocation crosshairs_texture = new ResourceLocation(Techguns.MODID,"textures/gui/crosshairs.png");
	
	@SideOnly(Side.CLIENT)
	@SubscribeEvent(priority=EventPriority.NORMAL, receiveCanceled=false)
	public void onRenderGameOverlyEvent(RenderGameOverlayEvent event){
		if(event.isCancelable() || event.getType() != ElementType.EXPERIENCE)
	    {      
	      return;
	    }


		Minecraft mc = Minecraft.getMinecraft();
		
		EntityPlayer ply = mc.player;
		
		if(ply.isSpectator()) return;
		
		TGExtendedPlayer props = TGExtendedPlayer.get(ply);
		ItemStack item =ply.getHeldItemMainhand();
		ItemStack item_off =ply.getHeldItemOffhand();
		ScaledResolution sr = new ScaledResolution(mc);
		
		int offsetY = sr.getScaledHeight()-32;
		
		boolean showSafemode=false;
		
		if(!item.isEmpty() && item.getItem() instanceof GenericGun){
			GenericGun gun = ((GenericGun) item.getItem());
						
			this.drawGunAmmoCount(mc, sr, gun, item, ply, props, 0);
			showSafemode=true;
		}

		if(!item_off.isEmpty() && item_off.getItem() instanceof GenericGun){
			if (GunManager.canUseOffhand(item,item_off,ply)) {
				GenericGun gun = ((GenericGun) item_off.getItem());
				this.drawGunAmmoCount(mc, sr, gun, item_off, ply, props, -12);
				showSafemode=true;
			}
		}
		
		if(props!=null && props.showTGHudElements && showSafemode){

			mc.getTextureManager().bindTexture(TGPlayerInventoryGui.texture);
			this.drawTexturedModalRect(sr.getScaledWidth()-10, offsetY, 242+7*(props.enableSafemode?1:0), 14, 7,7);

		}
		
		if(props!=null && props.showTGHudElements){
			offsetY -=10;
		
			mc.getTextureManager().bindTexture(TGPlayerInventoryGui.texture);
		
			//draw power icon
			ItemStack chest =ply.inventory.armorInventory.get(2);
			if(chest!=null && chest.getItem() instanceof PoweredArmor){
				this.drawTexturedModalRect(sr.getScaledWidth()-10, offsetY, 249, 35, 7,7);
				
				PoweredArmor pwrchest = (PoweredArmor) chest.getItem();
				double percent = (pwrchest.getPower(chest)*1.0D) / (pwrchest.maxpower*1.0D);
				
				ItemStack ammoitem  = pwrchest.getBattery();
				int count = InventoryUtil.countItemInInv(ply.inventory.mainInventory, ammoitem, 0, ply.inventory.mainInventory.size());
				count += InventoryUtil.countItemInInv(props.tg_inventory.inventory, ammoitem, TGPlayerInventory.SLOTS_AMMO_START, TGPlayerInventory.SLOTS_AMMO_END+1);
				
				String text = ChatFormatting.YELLOW+""+count+"x"+ChatFormatting.WHITE+(int)(percent*100)+"%";
				mc.fontRenderer.drawString(text, sr.getScaledWidth()-2-text.length()*6-8+24, offsetY, 0xFFFFFFFF);
				//mc.fontRenderer.drawString(text2, sr.getScaledWidth()-2-(text.length()+text2.length())*6-8, offsetY, 0xFFFFFFFF);
				offsetY-=10;
				
			}
			
			ItemStack backslot = props.tg_inventory.inventory.get(props.tg_inventory.SLOT_BACK);
			if (backslot !=null){
				
				//TODO: unhardcode this
				if (backslot.getItem() == TGItems.JETPACK || backslot.getItem() == TGItems.JUMPPACK || backslot.getItem() == TGItems.ANTI_GRAV_PACK){

					int x = 242;
					if (props.enableJetpack){
						x+=7;
					}
					//bind again because string drawing fucks it up
					mc.getTextureManager().bindTexture(TGPlayerInventoryGui.texture);
					this.drawTexturedModalRect(sr.getScaledWidth()-10, offsetY, x, 42, 7,7);
			
					double percent = 1.0D-(backslot.getItemDamage()*1.0f) / (backslot.getMaxDamage()*1.0f);
					
					ItemStack ammoitem  = ((ItemTGSpecialSlotAmmo)backslot.getItem()).getAmmo();
					int count = InventoryUtil.countItemInInv(ply.inventory.mainInventory, ammoitem, 0, ply.inventory.mainInventory.size());
					count += InventoryUtil.countItemInInv(props.tg_inventory.inventory, ammoitem, TGPlayerInventory.SLOTS_AMMO_START, TGPlayerInventory.SLOTS_AMMO_END+1);
					
					String text = ChatFormatting.YELLOW+""+count+"x"+ChatFormatting.WHITE+(int)(percent*100)+"%";
				
					//String text= ""+(int)(percent*100)+"%";
					mc.fontRenderer.drawString(text, sr.getScaledWidth()-2-text.length()*6-8+24, offsetY, 0xFFFFFFFF);
					
					offsetY-=10;
					
				}
				
			}
			
			//needs rebind after string drawing
			mc.getTextureManager().bindTexture(TGPlayerInventoryGui.texture);
			if(Techguns.proxy.getHasNightvision()){
				this.drawTexturedModalRect(sr.getScaledWidth()-10, offsetY, 242+7*(props.enableNightVision?1:0), 7, 7,7);
				offsetY-=10;
			}
			if(Techguns.proxy.getHasStepassist()){
				this.drawTexturedModalRect(sr.getScaledWidth()-10, offsetY, 242+7*(props.enableStepAssist?1:0), 21, 7,7);
				offsetY-=10;
			}
			
			//Check armor durability
			byte mode=0;
			for(int i=0;i<4;i++){
				ItemStack armor=ply.inventory.armorInventory.get(i);
				if(armor!=null){
					double dur = ((armor.getMaxDamage()-armor.getItemDamage())*1.0D)/(armor.getMaxDamage()*1.0D);
					if (dur<0.35D && mode<1){
						mode=1;
					} else if(armor.getItemDamage()>=(armor.getMaxDamage()-1)){
						mode=2;
						break;
					}
				}
				
			}
			if(mode==2){
				this.drawTexturedModalRect(sr.getScaledWidth()-10, offsetY, 242+7, 28, 7,7);
				offsetY-=10;
			} else if (mode==1){
				this.drawTexturedModalRect(sr.getScaledWidth()-10, offsetY, 242, 28, 7,7);
				offsetY-=10;
			}
			
			if(props.radlevel>0) {
				String prefix = ChatFormatting.WHITE.toString();
				if(props.radlevel>=1000) {
					prefix = ChatFormatting.RED.toString();
				} else if ( props.radlevel >= 750) {
					prefix = ChatFormatting.GOLD.toString();
				} else if (props.radlevel >= 500 ) {
					prefix = ChatFormatting.YELLOW.toString();
				}
				String radtext = props.radlevel+ " RAD";
				mc.fontRenderer.drawString(prefix+radtext, sr.getScaledWidth()-radtext.length()*6, offsetY, 0xFFFFFFFF);
				
				offsetY-=10;
			}
			
		}

	}
	
	private int getAmmoCountOfStack(ItemStack ammoitem, GenericGun gun, EntityPlayer ply, TGExtendedPlayer props) {
		int count = InventoryUtil.countItemInInv(ply.inventory.mainInventory, ammoitem, 0, ply.inventory.mainInventory.size());
		count += InventoryUtil.countItemInInv(props.tg_inventory.inventory, ammoitem, TGPlayerInventory.SLOTS_AMMO_START, TGPlayerInventory.SLOTS_AMMO_END+1);
		
		if (gun.getAmmoCount()>1){
			count = count / gun.getAmmoCount();
		} 
		return count;
	}
	
	private void drawGunAmmoCount(Minecraft mc,ScaledResolution sr, GenericGun gun, ItemStack item, EntityPlayer ply, TGExtendedPlayer props, int offsetY) {
		ItemStack[] ammoitem  = gun.getReloadItem(item);
		int minCount = 0;
		for(ItemStack stack: ammoitem) {
			int c = getAmmoCountOfStack(stack, gun, ply, props); 
			if (c>minCount) {
				minCount = c;
			}
		}
		
		String text= gun.getAmmoLeftCountTooltip(item)+"/"+gun.getClipsizeTooltip() +ChatFormatting.YELLOW+"x" +minCount;
		mc.fontRenderer.drawString(text, sr.getScaledWidth()+1-text.length()*6,sr.getScaledHeight()-mc.fontRenderer.FONT_HEIGHT-2+offsetY , 0xFFFFFFFF);
	}
	
	
/*	@SideOnly(Side.CLIENT)
	@SubscribeEvent(priority=EventPriority.NORMAL, receiveCanceled=false)
	public void onGuiPostInit(GuiScreenEvent.InitGuiEvent.Post event){
		//System.out.println("Init GUI:"+event.getGui());
		if (event.getGui() instanceof GuiInventory){
		    int xSize = 176;
		    int ySize = 166;
			
		    int k = (event.getGui().width - xSize) / 2;
	        int l = (event.getGui().height - ySize) / 2;
			
	       // get this from config file
	       int index = 2;
	       event.getButtonList().add(new TGGuiTabButton(index, k+5, l-26, false,new ItemStack(Blocks.CRAFTING_TABLE,1),0));
	       event.getButtonList().add(new TGGuiTabButton(index, k+5+28, l-26, true,TGItems.newStack(TGItems.PISTOL_ROUNDS,1),0));
			
		}
	}*/
	
	@SideOnly(Side.CLIENT)
	@SubscribeEvent(priority=EventPriority.NORMAL, receiveCanceled=false)
	public void onRenderCrosshairs(RenderGameOverlayEvent event){
		if(event.isCancelable() || event.getType() != ElementType.CROSSHAIRS)
	    {      
	      return;
	    }
		
		Minecraft mc = Minecraft.getMinecraft();
		EntityPlayer player = mc.player;
		//if (player.getActiveItemStack().getItem() instanceof GenericGun) {
		if (player.getHeldItemMainhand().getItem() instanceof GenericGun) {
			
			/*
			 * Render Lock on GUI effect
			 */
			GameSettings gamesettings = mc.gameSettings;
			ScaledResolution sr = new ScaledResolution(mc);
			TGExtendedPlayer epc = TGExtendedPlayer.get(player);
			
			//GenericGun gun = (GenericGun)player.getActiveItemStack().getItem();
			GenericGun gun = (GenericGun)player.getHeldItemMainhand().getItem();
			if (gun.getLockOnTicks() > 0 && epc.lockOnEntity != null && epc.lockOnTicks > 0) {
				float maxTicks = (float)gun.getLockOnTicks(); //TODO: Store in capabilities
				float progress = (float)epc.lockOnTicks/maxTicks;
				
				mc.getTextureManager().bindTexture(crosshairs_texture);
				
				//GlStateManager.blendFunc(1, 0);
				GlStateManager.disableBlend();
				
//				float offset = (Math.max(0.0f, (1.0f-progress)*16.0f))+3.5f;
//				float x = (float)(sr.getScaledWidth_double()*0.5);
//				float y = (float)(sr.getScaledHeight_double()*0.5);
				
				int offset = (int)(Math.max(0.0f, (1.0f-progress)*16.0f))+5;
				int x = sr.getScaledWidth() / 2;
				int y = sr.getScaledHeight() / 2;
				//Outer parts
				
				this.drawTexturedModalRect(x-offset-3, y-offset-3, 0, 0, 7,7);
				this.drawTexturedModalRect(x+offset-3, y-offset-3, 7, 0, 7,7);
				this.drawTexturedModalRect(x-offset-3, y+offset-3, 14, 0, 7,7);
				this.drawTexturedModalRect(x+offset-3, y+offset-3, 21, 0, 7,7);
				
				if (progress < 1.0f) {
					String text = "Locking... : "+epc.lockOnEntity.getName();
					mc.fontRenderer.drawString(text, (int)(sr.getScaledWidth_double()*0.5)+2, (int)(sr.getScaledHeight_double()*0.5)+10, 0xFFFFFFFF);
				}else {
					//this.drawTexturedModalRect(x-6.5f, y-6.5f, 28, 0, 13,13);
					//if (event.getPartialTicks() > 0.5f) {
					this.drawTexturedModalRect(x-6, y-6, 28, 0, 13,13);
					if (Minecraft.getMinecraft().getSystemTime() / 250 % 2 == 0) {
						this.drawTexturedModalRect(x-9, y-9, 41, 0, 19,19);
					}
					
						
					String text = "Locked On: "+epc.lockOnEntity.getName();
					mc.fontRenderer.drawString(text, (int)(sr.getScaledWidth_double()*0.5)+2, (int)(sr.getScaledHeight_double()*0.5)+10, 0xFFFF0000);
				}
				
				//Restore settings
				GlStateManager.enableBlend();
//				if (!(gamesettings.showDebugInfo && !gamesettings.hideGUI && !player.hasReducedDebug() && !gamesettings.reducedDebugInfo))
//	            {
//	                GlStateManager.tryBlendFuncSeparate(GlStateManager.SourceFactor.ONE_MINUS_DST_COLOR, GlStateManager.DestFactor.ONE_MINUS_SRC_COLOR, GlStateManager.SourceFactor.ONE, GlStateManager.DestFactor.ZERO);
//	                GlStateManager.enableAlpha();
//	            }
			}

		}
		
		
	}
}
