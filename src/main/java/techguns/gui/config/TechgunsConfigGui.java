package techguns.gui.config;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.client.gui.GuiScreen;
import net.minecraftforge.common.config.ConfigElement;
import net.minecraftforge.fml.client.config.GuiConfig;
import net.minecraftforge.fml.client.config.IConfigElement;
import techguns.TGConfig;
import techguns.Techguns;

public class TechgunsConfigGui extends GuiConfig {

	public static List<IConfigElement> getConfigElems(){
		return new ConfigElement(TGConfig.config.getCategory(TGConfig.CLIENTSIDE)).getChildElements();
	}
	
	public TechgunsConfigGui(GuiScreen parentScreen) {
		super(parentScreen, getConfigElems(), Techguns.MODID,Techguns.MODID+".configID",false,false, Techguns.NAME);
	}

	
	
}
