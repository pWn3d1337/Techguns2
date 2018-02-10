package techguns.recipes;

import com.google.gson.JsonObject;
import net.minecraft.util.JsonUtils;
import net.minecraftforge.common.crafting.IConditionFactory;
import net.minecraftforge.common.crafting.JsonContext;
import techguns.TGConfig;
import techguns.Techguns;

import java.util.function.BooleanSupplier;

public class ConfigConditionFactory implements IConditionFactory
{
	public static final String INGOT_COPPER= "addCopperIngots";
	public static final String NUGGET_COPPER= "addCopperNuggets";
	public static final String INGOT_TIN= "addTinIngots";
	public static final String INGOT_BRONZE= "addBronzeIngots";
	public static final String INGOT_LEAD= "addLeadIngots";
	public static final String NUGGET_LEAD= "addLeadNuggets";
	public static final String INGOT_STEEL= "addSteelIngots";
	public static final String NUGGET_STEEL= "addSteelNuggets";
	
	@Override
	public BooleanSupplier parse(JsonContext context, JsonObject json)
	{
		boolean value = JsonUtils.getBoolean(json , "value", true);
		String key = JsonUtils.getString(json, "type");
		
		if(key.equals(Techguns.MODID + ":" +INGOT_COPPER)) {
			return () -> TGConfig.addCopperIngots == value;
		} else if(key.equals(Techguns.MODID + ":" +NUGGET_COPPER)) {
			return () -> TGConfig.addCopperNuggets == value;
		} else if(key.equals(Techguns.MODID + ":" +INGOT_TIN)) {
			return () -> TGConfig.addTinIngots == value;
		} else if(key.equals(Techguns.MODID + ":" +INGOT_BRONZE)) {
			return () -> TGConfig.addBronzeIngots == value;
		} else if(key.equals(Techguns.MODID + ":" +INGOT_LEAD)) {
			return () -> TGConfig.addLeadIngots == value;
		} else if(key.equals(Techguns.MODID + ":" +NUGGET_LEAD)) {
			return () -> TGConfig.addLeadNuggets == value;
		} else if(key.equals(Techguns.MODID + ":" +INGOT_STEEL)) {
			return () -> TGConfig.addSteelIngots == value;
		} else if(key.equals(Techguns.MODID + ":" +NUGGET_STEEL)) {
			return () -> TGConfig.addSteelNuggets == value;
		}
		
		return null;
	}
}
