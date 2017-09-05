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
import techguns.capabilities.TGExtendedPlayer;
import techguns.items.guns.GenericGun;
import techguns.packets.PacketTGKeybindPress;

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
        	
        	//TODO: code to check which hand should be reloaded
        	EnumHand hand = EnumHand.MAIN_HAND;
        	ItemStack currentItem = ply.getHeldItem(hand);
        	if (!currentItem.isEmpty() && currentItem.getItem() instanceof GenericGun){
        		
        		TGExtendedPlayer props = TGExtendedPlayer.get(Minecraft.getMinecraft().player);
            	if (props!=null){
	            	
	        		GenericGun gun = (GenericGun) currentItem.getItem();
	        		if (props.getFireDelay(hand)<=0 && !gun.isFullyLoaded(currentItem)){
		        					
		        		TGPackets.network.sendToServer(new PacketTGKeybindPress(TGKeybindsID.FORCE_RELOAD,hand));
		        		gun.tryForcedReload(currentItem, ply.world,ply, hand);
	        		}
        		}
        	}
        	
        }
    }
}
