package techguns.client.render.item;

import java.util.HashMap;

import net.minecraft.client.renderer.block.model.ItemCameraTransforms.TransformType;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import techguns.TGItems;
import techguns.api.render.IItemRenderer;

public class RenderGenericSharedItem3D implements IItemRenderer {

	protected HashMap<String,IItemRenderer> renderEntries = new HashMap<>();
	
	public void addRenderForType(String name, IItemRenderer renderer){
		renderEntries.put(name, renderer);
	}
	
	@Override
	public void renderItem(TransformType transform, ItemStack stack, EntityLivingBase elb, boolean leftHanded) {
		
		String key = TGItems.SHARED_ITEM.get(stack.getItemDamage()).getName();
		IItemRenderer renderer = renderEntries.get(key);
		
		if (renderer !=null){
			renderer.renderItem(transform, stack, elb, leftHanded);			
		}
		
	}

}
