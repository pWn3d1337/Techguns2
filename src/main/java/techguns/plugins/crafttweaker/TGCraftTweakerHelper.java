package techguns.plugins.crafttweaker;

import crafttweaker.api.item.IIngredient;
import crafttweaker.api.item.IItemStack;
import crafttweaker.api.minecraft.CraftTweakerMC;
import techguns.util.ItemStackOreDict;

public class TGCraftTweakerHelper {
	public static String getItemNames(IIngredient ingr){
		boolean first=true;
		StringBuilder sb = new StringBuilder();
		sb.append("[");
		for(IItemStack st: ingr.getItems()){
			if(!first){
				sb.append(",");
			} else {
				first=false;
			}
			sb.append(st.getName());
		}
		sb.append("]");
		return sb.toString();
	}
	
	public static ItemStackOreDict toItemStackOreDict(IItemStack stack){
		return new ItemStackOreDict(CraftTweakerMC.getItemStack(stack));
	}
	
	public static ItemStackOreDict toItemStackOreDict(String oreDictName){
		return new ItemStackOreDict(oreDictName);
	}
	
	public static ItemStackOreDict toItemStackOreDict(Object o){
		//System.out.println("OBJECT:"+o);
		if (o instanceof IItemStack){
			return new ItemStackOreDict(CraftTweakerMC.getItemStack((IItemStack)o));
		} else if (o instanceof String){
			return new ItemStackOreDict((String)o);
		}
		return ItemStackOreDict.EMPTY;
	}
}
