package techguns.items;

import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import techguns.TGItems;
import techguns.Techguns;

public class GenericItem extends Item {
	
	public GenericItem(String name, boolean addToItemList){
		setMaxStackSize(64);
		setCreativeTab(Techguns.tabTechgun);
		setRegistryName(name);
		setUnlocalizedName(Techguns.MODID+"."+name);
		if (addToItemList){
			TGItems.ITEMLIST.add(this);
		}
	}
	
	public GenericItem(String name) {
		this(name,true);
	}
	
	public ResourceLocation getModelLocation() {
		return this.getRegistryName();
	}
	
	@SideOnly(Side.CLIENT)
    public void initModel() {
        ModelLoader.setCustomModelResourceLocation(this, 0, new ModelResourceLocation(getModelLocation(), "inventory"));
    }
}
