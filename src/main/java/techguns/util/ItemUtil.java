package techguns.util;

import java.util.HashMap;
import java.util.List;
import java.util.Set;

import com.mojang.realmsclient.gui.ChatFormatting;

import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.oredict.OreDictionary;

/**
 * utility class with static item related functions
 *
 */
public class ItemUtil {
	
	public static void addShiftExpandedTooltip(List<String> tooltip){
		tooltip.add(TextUtil.trans("techguns.gun.tooltip.shift1")+" "+ChatFormatting.GREEN+TextUtil.trans("techguns.gun.tooltip.shift2")+" "+ChatFormatting.GRAY+TextUtil.trans("techguns.gun.tooltip.shift3"));
	}	
	
	public static void addToolClassesTooltip(HashMap<String,Integer> toolclasses, List tooltip){
		Set<String> ss = toolclasses.keySet();
		if (ss.size()>0) {
			tooltip.add(TextUtil.trans("techguns.tooltip.toolclasses"));
			for (String s: toolclasses.keySet()){
				tooltip.add("  "+s+" : "+toolclasses.get(s));
			}
		}
	}	
	
	public static boolean isItemEqual(ItemStack item1, ItemStack item2){
		if(item1.isEmpty() && item2.isEmpty()){
			return true;
		} else if (item1.isEmpty() || item2.isEmpty()){
			return false;
		} else {
			return OreDictionary.itemMatches(item1, item2, true);
		}
	}
	
	/**
	 * like isItemEqual for FluidStacks
	 * @param item1
	 * @param item2
	 * @return true if both stacks are null or both are !=null AND Fluids are equal
	 */
	public static boolean isFluidEqual(FluidStack f1, FluidStack f2){
		if(f1==null && f2==null){
			return true;
		} else if (f1==null || f2==null){
			return false;
		} else {
		//return ( (item1==null && item2==null) || ( (item1!=null && item2!=null) && (item1.getItem() == item2.getItem()) && (item1.getItemDamage()==item2.getItemDamage()) ) );
			return f1.equals(f2);
		}
	}
	
	/**
	 * Returns if an item exists in OreDictionary with key
	 * @param key
	 * @return
	 */
	public static boolean existsInOredict(String key){
		return OreDictionary.getOres(key).size()>0;
	}
}
