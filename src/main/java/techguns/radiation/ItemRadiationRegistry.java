package techguns.radiation;

import java.util.HashMap;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class ItemRadiationRegistry {
	public static HashMap<Item, HashMap<Short,ItemRadiationData>> registry = new HashMap<>();
	
	
	public static void addRadiationData(ItemStack stack, int radamount, int radduration) {
		addRadiationDataInternal(stack.getItem(), (short)stack.getItemDamage(), (short)radamount, (short)radduration);
	}
	
	public static void addRadiationData(Item item, int damagevalue, int radamount, int radduration) {
		addRadiationDataInternal(item, (short)damagevalue, (short)radamount, (short)radduration);
	}
		
	protected static void addRadiationDataInternal(Item item, short damagevalue, short radamount, short radduration) {
		HashMap<Short, ItemRadiationData> entry = registry.get(item);
		if ( entry!=null) {
			if(entry.containsKey(damagevalue)) {
				System.out.println("Trying to add radiation data to item "+item+" with meta:"+damagevalue+". Data for this item already exists!");
			} else {
				entry.put(damagevalue, new ItemRadiationData(radamount, radduration));
			}
		} else {
			HashMap<Short, ItemRadiationData> newEntry = new HashMap<>(4);
			newEntry.put(damagevalue, new ItemRadiationData(radamount, radduration));
			registry.put(item, newEntry);
		}
	}
	
	public static ItemRadiationData getRadiationDataFor(ItemStack stack) {
		if(stack.isEmpty()) return null;
		return getRadiationDataFor(stack.getItem(), (short)stack.getItemDamage());
	}
		
	public static ItemRadiationData getRadiationDataFor(Item item, short damagevalue) {
		if(registry.containsKey(item)) {
			return registry.get(item).get(damagevalue);
		} else {
			return null;
		}
	}
}
