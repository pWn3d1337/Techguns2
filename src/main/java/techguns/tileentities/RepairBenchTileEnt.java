package techguns.tileentities;

import static techguns.gui.ButtonConstants.BUTTON_ID_SECURITY;
import java.util.List;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentTranslation;
import techguns.Techguns;
import techguns.items.armors.GenericArmor;
import techguns.util.InventoryUtil;

public class RepairBenchTileEnt extends BasicOwnedTileEnt {

	public RepairBenchTileEnt() {
		super(9, false);
	}

	@Override
	public ITextComponent getDisplayName() {
		return new TextComponentTranslation(Techguns.MODID+".container.repairbench", new Object[0]);
	}

	@Override
	public void buttonClicked(int id, EntityPlayer ply, String data) {
		if(id>BUTTON_ID_SECURITY && id<=BUTTON_ID_SECURITY+4 && this.isUseableByPlayer(ply)){
			int slot=3-((id-BUTTON_ID_SECURITY)-1);
			ItemStack item = ply.inventory.armorInventory.get(slot);
			if(!item.isEmpty() && item.getItem() instanceof GenericArmor){
				GenericArmor armor = (GenericArmor) item.getItem();
				
				
				List<ItemStack> mats = armor.getRepairMats(item);
				if(mats.size()>0){
				
					boolean canConsume=true;
					for(int i=0; i< mats.size(); i++){
						if (InventoryUtil.canConsumeItem(this.inventory, mats.get(i), 0, this.inventory.getSlots())>0){
							canConsume=false;
							break;
						};
					}
					
					if(canConsume){
						for(int i=0; i< mats.size(); i++){
							InventoryUtil.consumeItem(this.inventory, mats.get(i), 0, this.inventory.getSlots());
						}
						item.setItemDamage(0);
						//this.needUpdate();
					}
				
				}
			}
			
			
			
		} else {
			super.buttonClicked(id, ply, data);
		}
	}
}
