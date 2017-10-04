package techguns.keybind;

import org.lwjgl.input.Keyboard;

import net.minecraft.client.Minecraft;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumHand;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.InputEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import techguns.TGPackets;
import techguns.api.guns.GunManager;
import techguns.capabilities.TGExtendedPlayer;
import techguns.items.guns.GenericGun;
import techguns.packets.PacketTGKeybindPress;
import techguns.util.InventoryUtil;

@SideOnly(Side.CLIENT)
public class TGKeybinds {
	public static KeyBinding KEY_TOGGLE_NIGHTVISION;
	public static KeyBinding KEY_TOGGLE_SAFEMODE;
	public static KeyBinding KEY_FORCE_RELOAD;
	public static KeyBinding KEY_TOGGLE_JETPACK;
	
	/**
	 * client only
	 */
	public static KeyBinding KEY_TOGGLE_STEPASSIST;
	
	public static void init(){
		KEY_TOGGLE_NIGHTVISION=new KeyBinding("techguns.key.toggleNightvision", Keyboard.KEY_N, "techguns.key.categories.techguns");
		KEY_TOGGLE_SAFEMODE=new KeyBinding("techguns.key.toggleSafemode", Keyboard.KEY_B, "techguns.key.categories.techguns");
		KEY_TOGGLE_STEPASSIST=new KeyBinding("techguns.key.toggleStepassist", Keyboard.KEY_V, "techguns.key.categories.techguns");
		KEY_FORCE_RELOAD=new KeyBinding("techguns.key.forceReload", Keyboard.KEY_R, "techguns.key.categories.techguns");
		KEY_TOGGLE_JETPACK=new KeyBinding("techguns.key.toggleJetpack", Keyboard.KEY_J, "techguns.key.categories.techguns");
		
		ClientRegistry.registerKeyBinding(KEY_TOGGLE_NIGHTVISION);
		ClientRegistry.registerKeyBinding(KEY_TOGGLE_SAFEMODE);
		ClientRegistry.registerKeyBinding(KEY_TOGGLE_STEPASSIST);
		ClientRegistry.registerKeyBinding(KEY_FORCE_RELOAD);
		ClientRegistry.registerKeyBinding(KEY_TOGGLE_JETPACK);
	}
	
	@SubscribeEvent
    public void onKeyInput(InputEvent.KeyInputEvent event) {
    	
        if(KEY_TOGGLE_NIGHTVISION.isPressed()){
        	TGPackets.network.sendToServer(new PacketTGKeybindPress(TGKeybindsID.TOGGLE_NIGHTVISION,true));
        } else if(KEY_TOGGLE_SAFEMODE.isPressed()){
        	TGPackets.network.sendToServer(new PacketTGKeybindPress(TGKeybindsID.TOGGLE_SAFEMODE,true));
        } else if(KEY_TOGGLE_JETPACK.isPressed()){
        	TGPackets.network.sendToServer(new PacketTGKeybindPress(TGKeybindsID.TOGGLE_JETPACK,true));
        } else if(KEY_TOGGLE_STEPASSIST.isPressed()){
        	
        	/*TechgunsExtendedPlayerProperties props = TechgunsExtendedPlayerProperties.get(Minecraft.getMinecraft().thePlayer);
        	if (props!=null){
        		props.enableStepAssist=!props.enableStepAssist;
        	}*/
        	TGPackets.network.sendToServer(new PacketTGKeybindPress(TGKeybindsID.TOGGLE_STEP_ASSIST,true));
        } else if (KEY_FORCE_RELOAD.isPressed()){
        	EntityPlayer ply = Minecraft.getMinecraft().player;
        	
        	TGExtendedPlayer props = TGExtendedPlayer.get(Minecraft.getMinecraft().player);
        	if (props!=null){
        	
	        	ItemStack stack_main = ply.getHeldItemMainhand();
	        	ItemStack stack_off = ply.getHeldItemOffhand();
	        	boolean canReloadMainhand = this.canReloadGun(props, ply,stack_main, EnumHand.MAIN_HAND);
	        	boolean canReloadOffhand = GunManager.canUseOffhand(stack_main, stack_off, ply) && this.canReloadGun(props, ply, stack_off, EnumHand.OFF_HAND);
        	
	        	
	        	if (canReloadMainhand || canReloadOffhand) {
	        		EnumHand hand;
	        		ItemStack gunToReload;
	        		
	        		if(!canReloadOffhand) { //only mainhand can be reloaded
	        			hand =EnumHand.MAIN_HAND;
	        			gunToReload=stack_main;
	        		} else if (!canReloadMainhand) { //only offhand can be reloaded
	        			hand = EnumHand.OFF_HAND;
	        			gunToReload=stack_off;
	        		} else {	//both can be reloaded
	        			
	        			GenericGun gunMain = (GenericGun) stack_main.getItem();
	        			GenericGun gunOff = (GenericGun) stack_main.getItem();
	        			
	        			double ammoPercent = gunMain.getPercentAmmoLeft(stack_main);
	        			double ammoPercent_off = gunOff.getPercentAmmoLeft(stack_off);
	        			
	        			if(ammoPercent_off<ammoPercent) {
	        				hand = EnumHand.OFF_HAND;
		        			gunToReload=stack_off;
	        			} else {
	        				hand = EnumHand.MAIN_HAND;
		        			gunToReload=stack_main;
	        			}
	        			
	        		}
	        		
	        		TGPackets.network.sendToServer(new PacketTGKeybindPress(TGKeybindsID.FORCE_RELOAD,hand));
	        		((GenericGun)gunToReload.getItem()).tryForcedReload(gunToReload, ply.world,ply, hand);
	        		
	        	}
        	}
        	
        }
    }
	
	private boolean canReloadGun(TGExtendedPlayer props, EntityPlayer ply, ItemStack stack, EnumHand hand) {
		if(!stack.isEmpty() && stack.getItem() instanceof GenericGun) {
			GenericGun gun = (GenericGun) stack.getItem();
			if (props.getFireDelay(hand)<=0 && !gun.isFullyLoaded(stack)){
				
				ItemStack ammo = gun.getReloadItem(stack);
				if(InventoryUtil.canConsumeAmmoPlayer(ply, gun.getReloadItem(stack))) {
					return true;
				}
			}
		}
		return false;
	}
}
