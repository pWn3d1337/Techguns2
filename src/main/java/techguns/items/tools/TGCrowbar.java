package techguns.items.tools;

import java.util.HashMap;
import java.util.List;
import java.util.Set;

import org.lwjgl.input.Keyboard;

import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import techguns.util.ItemUtil;
import techguns.util.TextUtil;

public class TGCrowbar extends TGPickaxe {

	protected HashMap<String, Integer> harvestLevels = new HashMap<String,Integer>();
	
	public TGCrowbar(ToolMaterial mat, String name) {
		super(mat, name);
		harvestLevels.put("default", mat.getHarvestLevel());
		harvestLevels.put("pickaxe", mat.getHarvestLevel());
		harvestLevels.put("wrench", mat.getHarvestLevel());
	}

	@Override
	public Set<String> getToolClasses(ItemStack stack) {
		return harvestLevels.keySet();
	}

	@Override
	public void addInformation(ItemStack stack, World worldIn, List<String> tooltip, ITooltipFlag flagIn) {
		super.addInformation(stack, worldIn, tooltip, flagIn);
		
		if (Keyboard.isKeyDown(Keyboard.KEY_LSHIFT) || Keyboard.isKeyDown(Keyboard.KEY_RSHIFT)){
			ItemUtil.addToolClassesTooltip(harvestLevels, tooltip);
			//tooltip.add(TextUtil.trans("techguns.tooltip.crowbar.destroycluster"));
		} else {
			ItemUtil.addShiftExpandedTooltip(tooltip);
		}
	}

}
