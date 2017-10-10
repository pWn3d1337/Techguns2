package techguns.util;

import net.minecraft.client.resources.I18n;

public class TextUtil {
	public static String trans(String key){
		return I18n.format(key, new Object[0]);
	}
}
