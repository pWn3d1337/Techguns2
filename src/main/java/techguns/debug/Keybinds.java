package techguns.debug;

import org.lwjgl.input.Keyboard;

import net.minecraft.client.Minecraft;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.util.text.TextComponentString;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.InputEvent;
import techguns.Techguns;
import techguns.client.particle.TGFX;

@Mod.EventBusSubscriber(modid = Techguns.MODID)
public class Keybinds {

	public static float X=0;
	public static float Y=0;
	public static float Z=0;
	
	public static final float D = 0.01f;
	
    public static KeyBinding plusX;
    public static KeyBinding minusX;
    public static KeyBinding plusY;
    public static KeyBinding minusY;
    public static KeyBinding plusZ;
    public static KeyBinding minusZ;
    public static KeyBinding showXYZ;
    public static KeyBinding resetXYZ;
    
    public static KeyBinding reloadFX;
    
    private static final String CATEGORY = "key.categories."+Techguns.MODID;
    
    public static void init() {
    	
        plusX = new KeyBinding("key.+x", Keyboard.KEY_NUMPAD7, CATEGORY);

        minusX = new KeyBinding("key.-x", Keyboard.KEY_NUMPAD8, CATEGORY);
        
        plusY = new KeyBinding("key.+y", Keyboard.KEY_NUMPAD4, CATEGORY);
        minusY = new KeyBinding("key.-y", Keyboard.KEY_NUMPAD5, CATEGORY);
        
        plusZ = new KeyBinding("key.+z", Keyboard.KEY_NUMPAD1, CATEGORY);
        minusZ = new KeyBinding("key.-z", Keyboard.KEY_NUMPAD2, CATEGORY);
        
        showXYZ = new KeyBinding("key.show", Keyboard.KEY_NUMPAD0, CATEGORY);
        
        resetXYZ = new KeyBinding("key.resetXYZ", Keyboard.KEY_NUMPADCOMMA, CATEGORY);
        
        reloadFX = new KeyBinding("key.reloadFX", Keyboard.KEY_NUMPADENTER, CATEGORY);
        
        ClientRegistry.registerKeyBinding(plusX);
        ClientRegistry.registerKeyBinding(plusY);
        ClientRegistry.registerKeyBinding(plusZ);
        ClientRegistry.registerKeyBinding(minusX);
        ClientRegistry.registerKeyBinding(minusY);
        ClientRegistry.registerKeyBinding(minusZ);
        ClientRegistry.registerKeyBinding(showXYZ);
        ClientRegistry.registerKeyBinding(resetXYZ);
        ClientRegistry.registerKeyBinding(reloadFX);

        
    }
    
    @SubscribeEvent
    public static void onKeyInput(InputEvent.KeyInputEvent event) {

        if(Keybinds.plusX.isPressed()){
        	Keybinds.X+=D;
			Minecraft.getMinecraft().player.sendMessage(new TextComponentString("X:"+Keybinds.X));
        }
        if(Keybinds.minusX.isPressed()){
        	Keybinds.X-=D;
        	Minecraft.getMinecraft().player.sendMessage(new TextComponentString("X:"+Keybinds.X));
        }
        if(Keybinds.plusY.isPressed()){
        	Keybinds.Y+=D;
			Minecraft.getMinecraft().player.sendMessage(new TextComponentString("Y:"+Keybinds.Y));
        }
        if(Keybinds.minusY.isPressed()){
        	Keybinds.Y-=D;
        	Minecraft.getMinecraft().player.sendMessage(new TextComponentString("Y:"+Keybinds.Y));
        }
        if(Keybinds.plusZ.isPressed()){
        	Keybinds.Z+=D;
			Minecraft.getMinecraft().player.sendMessage(new TextComponentString("Z:"+Keybinds.Z));
        }
        if(Keybinds.minusZ.isPressed()){
        	Keybinds.Z-=D;
        	Minecraft.getMinecraft().player.sendMessage(new TextComponentString("Z:"+Keybinds.Z));
        }
        if(Keybinds.showXYZ.isPressed()){
        	Minecraft.getMinecraft().player.sendMessage(new TextComponentString("XYZ: "+Keybinds.X+", "+Keybinds.Y+", "+Keybinds.Z));
        }
        if(Keybinds.reloadFX.isPressed()) {
        	Minecraft.getMinecraft().player.sendMessage(new TextComponentString("Reload FX..."));
        	TGFX.loadFXList();
        	Minecraft.getMinecraft().player.sendMessage(new TextComponentString("Reload FX... Done!"));
        }
        if(Keybinds.resetXYZ.isPressed()) {
        	Keybinds.Z=0f;
        	Keybinds.Y=0f;
        	Keybinds.X=0f;
			Minecraft.getMinecraft().player.sendMessage(new TextComponentString("Reset XYZ"));
        }
    }
    
}
