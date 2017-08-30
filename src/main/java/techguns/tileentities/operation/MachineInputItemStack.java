package techguns.tileentities.operation;

import net.minecraft.item.ItemStack;
import techguns.util.ItemStackOreDict;

public class MachineInputItemStack implements IMachineInput<ItemStack> {
	
	protected ItemStackOreDict target;

	public MachineInputItemStack(String oreDictName) {
		this(new ItemStackOreDict(oreDictName));
	}
	
	public MachineInputItemStack(ItemStack stack) {
		this(new ItemStackOreDict(stack));
	}
	
	public MachineInputItemStack(ItemStackOreDict target) {
		super();
		this.target = target;
	}

	@Override
	public boolean matches(ItemStack other) {
		return target.isEqualWithOreDict(other);
	}

}
