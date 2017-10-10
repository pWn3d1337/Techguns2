package techguns.core;

import java.util.Map;

import net.minecraftforge.fml.relauncher.IFMLLoadingPlugin;

@IFMLLoadingPlugin.Name("Techguns Core")
@IFMLLoadingPlugin.MCVersion("1.12.2")
@IFMLLoadingPlugin.SortingIndex(Integer.MAX_VALUE)
public class TechgunsFMLPlugin implements IFMLLoadingPlugin {
	//public static boolean deobf = false;
		
	@Override
	public String[] getASMTransformerClass() {
		return new String[]{TechgunsASMTransformer.class.getName()};
	}

	@Override
	public String getModContainerClass() {
		return TechgunsCore.class.getName();
	}

	@Override
	public String getSetupClass() {
		return null;
	}

	@Override
	public void injectData(Map<String, Object> data) {
		//deobf =  (Boolean) data.get("runtimeDeobfuscationEnabled");
	}

	@Override
	public String getAccessTransformerClass() {
		return null;
	}

}
